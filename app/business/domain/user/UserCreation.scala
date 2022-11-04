package business.domain.user

import play.api.libs.json.Json

case class UserCreation(
                    username: String,
                    email: String,
                    password: String
                  )
object UserCreation {
  implicit val reader = Json.reads[UserCreation]
}
