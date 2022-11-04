package business.domain.user

import scala.util.Try

trait UserRepository {

  def findById(id: UserId): Option[User]

  def findByEmail(email: String): Option[User]

  def findByUserName(username: String): Option[User]

  def addNew(user: UserCreation): Try[UserId]

  def update(user: User): Boolean

  def deleteById(id: UserId): Boolean

}
