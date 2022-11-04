package business.util.exception

class ExistedUser(message: String="This email is registered") extends Exception {
  override def getMessage: String = message
}
