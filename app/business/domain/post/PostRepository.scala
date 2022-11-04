package business.domain.post

import business.domain.common.Paged
import scala.util.Try

trait PostRepository {
  def findAll(): Seq[Post]

  def findById(id: PostId): Option[Post]

  def save(post: PostCreation): Try[PostId]

  def update(post: Post): Boolean

  def deleteById(id: PostId): Boolean

  def pagination(limit:  Int, page: Int):Try[Paged[Post]]
}
