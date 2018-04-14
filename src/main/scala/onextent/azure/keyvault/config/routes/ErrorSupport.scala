package onextent.azure.keyvault.config.routes

import java.io.IOException

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive, ExceptionHandler, RejectionHandler}
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

trait ErrorSupport extends LazyLogging {

  val conf: Config = ConfigFactory.load()
  val urlpath: String = "config"

  val rejectionHandler: RejectionHandler = RejectionHandler.default

  val exceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: NoSuchElementException =>
      complete(StatusCodes.NotFound -> e.getMessage)
    case e: IllegalArgumentException =>
      logger.warn(s"IllegalArgument $e")
      complete(StatusCodes.BadRequest -> e.getMessage)
    case e: IOException =>
      logger.warn(s"IOException $e")
      complete(StatusCodes.Forbidden -> e.getMessage)
  }

  val handleErrors
    : Directive[Unit] = handleRejections(rejectionHandler) & handleExceptions(
    exceptionHandler)

}
