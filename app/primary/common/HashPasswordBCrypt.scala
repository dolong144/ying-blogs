package primary.common

import business.services.common.HashPassword
import org.mindrot.jbcrypt.BCrypt

object HashPasswordBCrypt extends HashPassword {
  override def hash(password: String): String = {
    BCrypt.hashpw(password, BCrypt.gensalt())
  }

  override def check(password: String, hashedPassword: String): Boolean = {
    BCrypt.checkpw(password, hashedPassword)
  }
}
