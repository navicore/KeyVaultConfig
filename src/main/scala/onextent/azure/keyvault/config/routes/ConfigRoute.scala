package onextent.azure.keyvault.config.routes

import java.io.StringWriter

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.scalalogging.LazyLogging
import onextent.azure.keyvault.config.ConfigWriter

object ConfigRoute extends LazyLogging with Directives with ErrorSupport {

  def apply: Route =
    path(urlpath) {
      handleErrors {
        get {
          logger.debug(s"get $urlpath")
          val sw = new StringWriter()
          ConfigWriter(sw)
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, sw.toString))
        }
      }
    }
}
