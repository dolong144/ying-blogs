package business.services.common

trait HashPassword {

  def hash(password: String): String

  def check(password: String, hashedPassword: String): Boolean
}
