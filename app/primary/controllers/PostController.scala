package primary.controllers

import business.domain.post.{Post, PostCreation, PostId}
import business.services.{PostService, UserService}
import play.api.data._
import play.api.data.Forms._
import play.api.libs.Files
import play.api.libs.json.Json
import play.api.mvc._
import primary.common.FormatJson._
import primary.common.{HandleImage, Response, UuidGeneratorService}
import primary.filter.AuthMiddleware
import java.time.LocalDateTime
import javax.inject._

@Singleton
class PostController @Inject()(postService: PostService,
                               userService: UserService,
                               handleImage: HandleImage,
                               idGeneratorService: UuidGeneratorService,
                               authMiddleware: AuthMiddleware,
                               val controllerComponents: ControllerComponents) extends BaseController {

  val createForm: Form[PostCreation] = Form[PostCreation](
    mapping(
      "title" -> nonEmptyText,
      "content" -> nonEmptyText,
      "author" -> nonEmptyText,
      "thumbnail" -> nonEmptyText,
    )(PostCreation.apply)(PostCreation.unapply)
  )

  // Find post by Id
  def findById(id: String) = Action {
    postService.findById(PostId(id)) match {
      case Some(post) => Ok(Json.toJson(post))
      case None => Response.notFound("Post is not exist!")
    }
  }

//   Create new post
//   implicit createForm.isValid { postDto =>
//   postService.save(valid).map(value => {
//                            Ok(Json.obj("id" -> value.value))
//                        }).getOrElse(BadRequest(Json.obj("message" -> "Can not create a new post!")))
//   }
  def addNewPost = authMiddleware(parse.multipartFormData) { request =>
    handleImage.imageUploaded(request, idGeneratorService) match {
      case None => Response.notFound("File not found!")
      case Some(filePath) => {

        println("Filepath", filePath)

        createForm.bindFromRequest(request.body.asFormUrlEncoded ++ Map("thumbnail" -> Seq(filePath)))
          .fold(
            formErrors => {
              Response.badRequest("Data is invalid!")
            },
            valid => {
              postService.save(valid).map(value => {
//                Thread.sleep(500)
                Ok(Json.obj("id" -> value.value))
              }).getOrElse(Response.badRequest("Can not create a new post!"))

            }
          )
      }
    }
  }

  // get list post by limit(post/page) and page index
  def pagination(limit: Int, page: Int) = Action {
    val posts = postService.pagination(limit, page).get
    val data = Json.obj(
      "totalPages" -> posts.totalPages,
      "currentPage" -> page,
      "data" -> posts.items
    )
    Ok(data)
  }


  // delete post by id
  def deleteById(id: String) = Action {
    if (postService.deleteById(PostId(id))) {
      Response.ok("Delete success!")
    } else {
      Response.badRequest("Delete failure!")
    }
  }

  // modify post
  def updatePost: Action[MultipartFormData[Files.TemporaryFile]] = Action(parse.multipartFormData) {
      request => {
      val data = request.body.asFormUrlEncoded
      val id = data("id").head
      val title = data("title").head
      val content = data("content").head
      val author = data("author").head
      val pathThumbnail = handleImage.imageUploaded(request, idGeneratorService) match {
        case value => value
        case None => Response.notFound("File not found")
      }
      val createAt = LocalDateTime.parse(data("createAt").head)
      val modifyAt = LocalDateTime.now()
      if (postService.update(Post(PostId(id), title, content, author, pathThumbnail.toString, createAt, modifyAt))) {
        Response.ok("Update success!")
      } else {
        Response.badRequest("Update error!")
      }
    }
  }

}
