package secondary.post

import business.domain.common.Paged
import business.domain.post.{Post, PostCreation, PostId, PostRepository}
import secondary.DAO.SkinnyPostDAO
import javax.inject.Inject
import scala.util.{Failure, Success, Try}
import skinny.Pagination

import java.time.LocalDateTime

class SkinnyPostRepository @Inject()(skinnyPostDAO: SkinnyPostDAO) extends PostRepository {
  // Find all Post
  override def findAll(): Seq[Post] = {
    skinnyPostDAO.findAll()
  }

  // Find post by Id
  override def findById(id: PostId): Option[Post] = {
    skinnyPostDAO.findById((id))
  }

  // Create and save a new post
  override def save(post: PostCreation): Try[PostId] = {
    Try {
      skinnyPostDAO.createWithAttributes(
        Symbol("author") -> post.author,
        Symbol("title") -> post.title,
        Symbol("content") -> post.content,
        Symbol("thumbnail") -> post.thumbnail,
        Symbol("createAt") -> LocalDateTime.now(),
        Symbol("modifyAt") -> LocalDateTime.now()
      )
    }
  }

  //Modify a post
  override def update(post: Post): Boolean =
    Try {
      skinnyPostDAO.updateById(post.id)
        .withAttributes(
          Symbol("title") -> post.title,
          Symbol("content") -> post.content,
          Symbol("thumbnail") -> post.thumbnail,
          Symbol("author") -> post.author,
          Symbol("createAt") -> post.createAt,
          Symbol("modifyAt") -> post.modifyAt
        )
    } match {
      case Success(changes) => changes > 0
      case Failure(_) => false
    }

  //Delete a post by id
  override def deleteById(id: PostId): Boolean = {
    if (skinnyPostDAO.deleteById(id) > 0) true
    else false

  }

  //Paging with number of post in a page and page index
  override def pagination(limit: Int, page: Int): Try[Paged[Post]] = Try {
    val posts = skinnyPostDAO.paginate(Pagination(limit, page)).orderBy(skinnyPostDAO.defaultAlias.id.desc).apply()
    val total = skinnyPostDAO.countAllModels()
    Paged(
      total, posts, page, perPage = limit
    )
  }

}
