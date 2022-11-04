package business.util.exception

class UserNotExist(message: String="This user is not exist!") extends Exception {
  override def getMessage: String = message
}
