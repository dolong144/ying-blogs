package primary.filter

import business.domain.user.{UserCreation, UserRepository}
import play.api.mvc.{ActionBuilder, AnyContent, BodyParsers, Request, Result, WrappedRequest}
import primary.common.Response

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

case class UserRequest[A](user: UserCreation, request: Request[A]) extends WrappedRequest[A](request)

class AuthMiddleware @Inject()(val parser: BodyParsers.Default = null, val userRepository: UserRepository)(implicit val ec: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent] {
  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
    val maybeEmail = request.session.get("PLAY_SESSION")
    maybeEmail match {
      case None => Future.successful(Response.forbidden("Authenticated failed"))
      case _ =>
        userRepository.findByEmail(maybeEmail.get) match {
          case Some(user) => block(UserRequest(UserCreation(user.username, user.email, user.password), request))
          case None => Future.successful(Response.forbidden("Authenticated failed"))
        }
      
    }
  }

  override protected def executionContext: ExecutionContext = ec
}