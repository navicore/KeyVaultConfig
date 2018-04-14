package onextent.azure.keyvault.config.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.scalalogging.LazyLogging
import onextent.azure.keyvault.config.ErrorSupport
import onextent.azure.keyvault.config.models.JsonSupport

object ConfigRoute
    extends JsonSupport
    with LazyLogging
    with Directives
    with ErrorSupport {

  def apply: Route =
    path(urlpath) {
      handleErrors {
        get {
          logger.debug(s"get $urlpath")
          complete(
            HttpEntity(ContentTypes.`application/json`,
                       "{\"msg\": \"Say hello to Key Vault Config\"}\n"))
        }
      }
    }
}
