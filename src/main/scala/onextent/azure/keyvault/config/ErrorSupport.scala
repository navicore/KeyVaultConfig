package onextent.azure.keyvault.config

import java.io.IOException

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.HttpOrigin
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive, ExceptionHandler, RejectionHandler, Route}
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._

trait ErrorSupport extends LazyLogging {

  val conf: Config = ConfigFactory.load()
  val corsOriginList: List[HttpOrigin] = conf
    .getStringList("main.corsOrigin")
    .asScala
    .iterator
    .toList
    .map(origin => HttpOrigin(origin))
  val urlpath: String = conf.getString("main.path")
  val port: Int = conf.getInt("main.port")

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

  def HealthCheck: Route =
    path("healthcheck") {
      get {
        complete("ok")
      }
    }
}
