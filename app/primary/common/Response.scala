package primary.common

import play.api.libs.json.Json
import play.api.mvc.{Result, Results}
import play.api.mvc.Results.{BadRequest, NotFound, Ok, Forbidden}

abstract class Response

object Response {
  def ok(message: String): Result = {
    Ok(Json.obj("message" -> message))
  }

  def badRequest(message: String): Result = {
    BadRequest(Json.obj(
      "error" -> "true",
      "message" -> message
    ))
  }

  def notFound(message: String): Result = {
    NotFound(Json.obj(
      "error" -> "true",
      "message" -> message
    ))
  }

  def forbidden(message: String): Result = {
    Forbidden(Json.obj(
      "error" -> "true",
      "message" -> message
    ))
  }

  def handleException(e: Throwable): Result = {
    Results.InternalServerError(
      Json.obj("error" -> true, "message" -> e.getMessage)
    )
  }
}

//case class SuccessMessage(message: String) extends Response {
//
//}
//
//case class ErrorMessage(message: String) extends Response {
//
//}