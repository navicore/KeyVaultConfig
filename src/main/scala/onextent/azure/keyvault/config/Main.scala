package onextent.azure.keyvault.config

import java.io._

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
import onextent.azure.keyvault.config.routes.ConfigRoute

import scala.concurrent.ExecutionContextExecutor

object Main extends App {

  val conf: Config = ConfigFactory.load()
  if (conf.getBoolean("main.isSidecar")) {

    implicit val system: ActorSystem = ActorSystem("KeyVaultConfig-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val route = ConfigRoute.apply

    Http().bindAndHandle(route, "0.0.0.0", conf.getInt("main.sidecarPort"))

  } else {

    val configFile = new File(conf.getString("main.fileLoc"))
    val pw = new PrintWriter(new FileOutputStream(configFile, true))

    ConfigWriter(pw)

    pw.close()
  }

}
