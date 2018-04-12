package onextent.k8s.azure.keyvault.configmap

import java.io._

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

object Main extends App with LazyLogging {



  val conf = {

    new java.io.File("/opt/config/secrets.yaml") match {

      case x: File if x.exists() =>
        logger.info(s"loading overrides of application.conf from $")
        val overrides: Config = ConfigFactory.load()
        overrides.withFallback(ConfigFactory.load())

      case _ => ConfigFactory.load()

    }
  }

  val pw = new PrintWriter(new File(conf.getString("main.fileLoc")))

  val specs = conf.getString("main.secretsSpec").split(",")

  specs.foreach  (spec => {

    val srec = spec.split(":")
    if (srec.length == 2) {
      val configName = srec(0)
      val vaultName = srec(1)
      val secretValue = vaultName // todo: vault value
      pw.println(s"""$configName = "$secretValue"""")
    }
  })

  pw.close()

}
