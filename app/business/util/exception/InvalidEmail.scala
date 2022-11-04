package business.util.exception

class InvalidEmail (message: String="This email is invalid") extends Exception {
  override def getMessage: String = message
}

