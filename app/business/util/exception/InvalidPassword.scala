package business.util.exception

class InvalidPassword (message: String="This password is invalid") extends Exception {
  override def getMessage: String = message
}
