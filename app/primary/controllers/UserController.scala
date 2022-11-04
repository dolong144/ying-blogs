package primary.controllers

import business.domain.user.{User, UserCreation, UserId}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import business.services.{Authenticated, EmailExisted, EmailInvalid, PasswordInvalid, UserNameInvalid, UserService, ValidInfo}
import primary.common.FormatJson._
import play.api.libs.json.Json
import primary.common._
import primary.filter.AuthMiddleware

import java.time.LocalDateTime
import javax.inject._
import scala.util.{Failure, Success, Try}

@Singleton
class UserController @Inject()(val userService: UserService,
                               val authMiddleware: AuthMiddleware,
                               val controllerComponents: ControllerComponents)
  extends BaseController {
//  implicit val successMessageWrites: Writes[SuccessMessage] = Json.writes
//  implicit val errorMessageWrites: Writes[ErrorMessage] = Json.writes

  // create a new user
  def registerNewUser(): Action[UserCreation] = Action(parse.json[UserCreation]) { request =>

      val data = request.body
      userService.register(data.username, data.email, data.password) match {
      case ValidInfo(userId) => Response.ok("Register success!")
      case EmailExisted => Response.badRequest("Email has been existed!")
      case EmailInvalid => Response.badRequest("Email is invalid!")
      case PasswordInvalid => Response.badRequest("Password is invalid!")
      case UserNameInvalid => Response.badRequest("Username is invalid")
    }
  }

  // using email, password to login a account
  def login(): Action[AnyContent] = Action { request =>
    //    Try {
    val json = request.body.asJson.get
    val email = json("email").as[String]
    val password = json("password").as[String]
    userService.login(email, password) match {
      case Authenticated(user) => Ok(Json.obj(
        "userId" -> user.id.value,
        "username" -> user.username
      )).withSession("PLAY_SESSION" -> user.email)
      case _ => Response.badRequest("Password or email is wrong!")
    }
  }

  // find user by Id
  def findUserById(id: String): Action[AnyContent] = Action {
    userService.findById(UserId(id)) match {
      case Some(user) => Ok(Json.toJson(user))
      case None =>Response.notFound(s"Uid $id not found!")
    }
  }

  // Update a post
  def update(): Action[AnyContent] = Action {
    implicit request => {
      val json = request.body.asJson.get
      val user = User(
        id = UserId(json("id").as[String]),
        email = json("email").as[String],
        username = json("username").as[String],
        password = json("password").as[String],
        createAt = json("createAt").as[LocalDateTime]
      )
      if (userService.update(user)) {
        Response.ok("Update success!")
      } else {
        Response.badRequest("Can not update!")
      }
    }
  }

  // delete a post by Id
  def deleteById(id: String): Action[AnyContent] = Action {
    if (userService.deleteById(UserId(id))) {
      Response.ok("Delete Success!")
    } else {
      Response.badRequest("Delete failure!")
    }
  }
  // logout a account
  def logout(): Action[AnyContent] = Action {
    Response.ok("Logout Success!").withNewSession
  }
  def getUserByCookie: Action[AnyContent] = authMiddleware { request => {
    val maybeEmail = request.session.get("PLAY_SESSION")
    val user = userService.findByEmail(maybeEmail.get)
    Ok(Json.obj(
      "id" -> user.get.id.value,
      "username" -> user.get.username,
      "email" -> user.get.email
    ))
  }
  }
}