package primary.common

import business.domain.post.{Post, PostCreation}
import business.domain.user.{User, UserCreation}
import play.api.libs.json.{Json, OFormat, Writes}


object FormatJson {
  implicit val postWriter: Writes[Post] = (o: Post) =>
      Json.obj(
        "id" -> o.id.value,
        "title" -> o.title,
        "content" -> o.content,
        "thumbnail" -> o.thumbnail,
        "shortContent" -> o.content.take(200),
        "author" -> o.author,
        "createAt" -> o.createAt,
        "modifyAt" -> o.modifyAt
      )

  implicit val postWrites: OFormat[PostCreation] = Json.format[PostCreation]

  implicit val userWriter: Writes[User] = (o: User) =>
    Json.obj(
      "id" -> o.id.value,
      "username" -> o.username,
      "email" -> o.email,
      "password" -> o.password,
      "createAt" -> o.createAt
    )

  implicit val userWrites: OFormat[UserCreation] = Json.format[UserCreation]

}
