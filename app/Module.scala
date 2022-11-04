import business.domain.post.PostRepository
import business.domain.user.UserRepository
import com.google.inject.AbstractModule
import secondary.post.SkinnyPostRepository
import secondary.user.SkinnyUserRepository

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRepository]).to(classOf[SkinnyUserRepository])
    bind(classOf[PostRepository]).to(classOf[SkinnyPostRepository])
  }
}
