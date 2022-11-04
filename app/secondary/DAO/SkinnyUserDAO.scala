package secondary.DAO

import business.domain.user.{User, UserId}
import scalikejdbc.{AutoSession, WrappedResultSet}
import skinny.orm.{Alias, SkinnyCRUDMapperWithId}

import java.time.LocalDateTime

class SkinnyUserDAO extends SkinnyCRUDMapperWithId[UserId, User] {

  implicit val session: AutoSession = AutoSession

  override def tableName: String = "user"

  override def idToRawValue(id: UserId): Any = id.value

  override def rawValueToId(value: Any): UserId = UserId(value.toString)

  override def defaultAlias: Alias[User] = createAlias("user")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[User]): User = {
    User(
      id = UserId(rs.get[String](n.id)),
      username = rs.get[String](n.username),
      email = rs.get[String](n.email),
      password = rs.get[String](n.password),
      createAt = rs.get[LocalDateTime](n.createAt)
    )
  }
}


