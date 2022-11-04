package business.domain.post

import play.api.libs.json.Json


case class PostCreation(
                    title: String,
                    content: String,
                    author: String,
                    thumbnail: String
                  )
object PostCreation { implicit val reader = Json.reads[PostCreation] }