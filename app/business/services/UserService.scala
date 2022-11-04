package business.services

import business.domain.user.{User, UserCreation, UserId, UserRepository}
import business.services.common.{ Valid, ValidateEmailPassword}
import primary.common.HashPasswordBCrypt
import javax.inject.Inject
import scala.util.Try

sealed trait AuthLoginResult

case class Authenticated(user: User) extends AuthLoginResult
case object UserNotFound extends AuthLoginResult
case object PasswordMismatch extends AuthLoginResult
sealed trait AuthRegisterResult
case class ValidInfo(userId: UserId) extends AuthRegisterResult
case object EmailExisted extends AuthRegisterResult
case object EmailInvalid extends AuthRegisterResult
case object PasswordInvalid extends AuthRegisterResult
case object UserNameInvalid extends AuthRegisterResult

case class UserService @Inject() ( userRepository: UserRepository, validateEmailPassword: ValidateEmailPassword){
  def login(email: String, password: String): AuthLoginResult  ={
    val user = findByEmail(email)
    user match {
      case None => UserNotFound
      case Some(value) =>
        if (HashPasswordBCrypt.check(password, value.password))
          Authenticated(value)
        else PasswordMismatch

    }
  }
  def register(username: String, email: String, password: String):AuthRegisterResult ={
    username.length match {
      case x if x < 1 => UserNameInvalid
      case x if x > 50 => UserNameInvalid
      case _ =>
        findByEmail(email) match {
          case Some(value) => EmailExisted
          case None =>
            if (validateEmailPassword.validateEmail(email)) {
              validateEmailPassword.validatePassword(password) match {
                case Valid =>
                  ValidInfo(addNew(UserCreation(username, email, HashPasswordBCrypt.hash(password))).get)
                case _ => PasswordInvalid
              }
            } else {
              EmailInvalid
            }

        }

    }

  }
  def findById(id: UserId): Option[User] = {
    userRepository.findById(id)
  }

  def findByEmail(email: String): Option[User] = {
    userRepository.findByEmail(email)
  }

  def findByUserName(username: String): Option[User] = {
    userRepository.findByUserName(username)
  }

  def addNew(user: UserCreation):Try[UserId] = {
    userRepository.addNew(user)
  }

  def update(user: User): Boolean = {
    userRepository.update(user)
  }

  def deleteById(id: UserId): Boolean = {
    userRepository.deleteById(id)
  }
}
