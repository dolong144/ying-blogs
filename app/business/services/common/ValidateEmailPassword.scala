package business.services.common
sealed trait ValidatePasswordResult

case object Valid extends ValidatePasswordResult
case object PasswordTooLong extends ValidatePasswordResult
case object PasswordTooShort extends ValidatePasswordResult
case object PasswordTooWeak extends ValidatePasswordResult
class ValidateEmailPassword {

  def validateEmail(email:String):Boolean = {
    """(?=[^\s]+)(?=(\w+)@([\w\.]+))""".r.findFirstIn(email).isDefined
  }

  def validatePassword(password: String): ValidatePasswordResult = {
    if(password.length >20)
      PasswordTooLong
    else if (password.length < 12)
      PasswordTooShort
      else if(!(password.matches(raw".*\W.*") &&
                password.matches(raw".*[a-zA-Z].*") &&
                password.matches(raw".*[0-9].*")))
      PasswordTooWeak
    else Valid

  }



}
