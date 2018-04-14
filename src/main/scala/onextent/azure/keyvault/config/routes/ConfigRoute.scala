package onextent.azure.keyvault.config.routes

import java.io.StringWriter

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.scalalogging.LazyLogging
import onextent.azure.keyvault.config.ConfigWriter

object ConfigRoute extends LazyLogging with Directives with ErrorSupport {

  val configContentType = ContentType(
    MediaType
      .customWithFixedCharset("application", "hocon", HttpCharsets.`UTF-8`))

  def apply: Route =
    path(urlpath) {
      logRequest(urlpath)
      handleErrors {
        get {
          logger.debug(s"get $urlpath")
          val sw = new StringWriter()
          ConfigWriter(sw)
          complete(HttpEntity(configContentType, sw.toString))
        }
      }
    }
}
