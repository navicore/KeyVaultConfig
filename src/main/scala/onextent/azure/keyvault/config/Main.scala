package onextent.azure.keyvault.config

import java.io._

import com.typesafe.config.{Config, ConfigFactory}

object Main extends App {

  val conf: Config = ConfigFactory.load()

  val configFile = new File(conf.getString("main.fileLoc"))
  val pw = new PrintWriter(new FileOutputStream( configFile, true))

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
