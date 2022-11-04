package business.domain.user

import java.time.LocalDateTime

case class User (
                  id: UserId,
                  username: String,
                  email: String,
                  password: String,
                  createAt: LocalDateTime
                )

