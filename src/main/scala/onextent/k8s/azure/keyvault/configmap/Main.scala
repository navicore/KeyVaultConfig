package onextent.k8s.azure.keyvault.configmap

import java.io._

import com.typesafe.config.{Config, ConfigFactory}

object Main extends App {

  val conf: Config = ConfigFactory.load()

  val pw = new PrintWriter(new File(conf.getString("main.fileLoc")))

  val specs = conf.getString("main.secretsSpec").split(",")

  specs.foreach  (spec => {

    val srec = spec.split(":")
    if (srec.length == 2) {
      val configName = srec(0)
      val secretName = srec(1)

      val secretValue = SecretValue(secretName)

      pw.println(s"""$configName = "$secretValue"""")
    }
  })

  pw.close()

}
