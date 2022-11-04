package business.util.exception

class WrongPassword (message: String="This password is wrong!") extends Exception {
  override def getMessage: String = message
}
