package secondary.user

import business.domain.user.{User, UserCreation, UserId, UserRepository}
import scalikejdbc.sqls
import secondary.DAO.SkinnyUserDAO
import javax.inject.Inject
import scala.util.{Failure, Success, Try}

case class SkinnyUserRepository @Inject()(skinnyUserDAO: SkinnyUserDAO) extends UserRepository {
  // Find user by Id
  override def findById(id: UserId): Option[User] = {
    skinnyUserDAO.findById(id)
  }

  // Find user By Email
  override def findByEmail(email: String): Option[User] = {
    skinnyUserDAO.findBy(sqls.eq(skinnyUserDAO.column.email, email))
  }

  //Find user by Username
  override def findByUserName(username: String): Option[User] = {
    skinnyUserDAO.findBy(sqls.eq(skinnyUserDAO.column.username, username))
  }

  // create a new user
  override def addNew(user: UserCreation): Try[UserId] = {
    Try {
      skinnyUserDAO.createWithAttributes(
        Symbol("username") -> user.username,
        Symbol("email") -> user.email,
        Symbol("password") -> user.password
      )
    }
  }

  // Modify a user
  override def update(user: User): Boolean = {
    Try {
      skinnyUserDAO
        .updateById(user.id)
        .withAttributes(
          Symbol("username") -> user.username,
          Symbol("email") -> user.email,
          Symbol("password") -> user.password,
          Symbol("createAt") -> user.createAt
        )
    } match {
      case Success(changes) => changes > 0
      case Failure(_) => false
    }
  }

  // Delete a user
  override def deleteById(id: UserId): Boolean = {
    if (skinnyUserDAO.deleteById(id) > 0)
      true
    else
      false
  }
}
