package secondary.DAO

import business.domain.post.{Post, PostId}
import scalikejdbc.{AutoSession, WrappedResultSet}
import skinny.orm.{Alias, SkinnyCRUDMapperWithId}

import java.time.LocalDateTime

class SkinnyPostDAO extends SkinnyCRUDMapperWithId[PostId, Post] {

  implicit val session: AutoSession = AutoSession

  override def tableName: String = "post"

  override def idToRawValue(id: PostId): Any = id.value

  override def rawValueToId(value: Any): PostId = PostId(value.toString)

  override def defaultAlias: Alias[Post] = createAlias("post")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[Post]): Post = {
    Post(
      id = PostId(rs.get[String](n.id)),
      title = rs.get[String](n.title),
      content = rs.get[String](n.content),
      thumbnail = rs.get[String](n.thumbnail),
      author = rs.get[String](n.author),
      createAt = rs.get[LocalDateTime](n.createAt),
      modifyAt = rs.get[LocalDateTime](n.modifyAt)
    )
  }
}


