package business.services

import business.domain.common.Paged
import business.domain.post.{Post, PostCreation, PostId, PostRepository}
import javax.inject.Inject
import scala.util.Try

class PostService @Inject() (postRepository: PostRepository){
  def findAll(): Seq[Post] = {
    postRepository.findAll()
  }

  def findById(id: PostId): Option[Post] = {
    postRepository.findById(id)
  }

  def save(post: PostCreation): Try[PostId] = {
    postRepository.save(post)
  }

  def update(post: Post): Boolean = {
    postRepository.update(post)
  }

  def deleteById(id: PostId):Boolean = {
    postRepository.deleteById(id)
  }

  def pagination(limit: Int, page: Int): Try[Paged[Post]] = {
    postRepository.pagination(limit, page)
  }
}
