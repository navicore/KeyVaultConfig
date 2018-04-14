package onextent.azure.keyvault.config

import java.io._

import com.typesafe.config.{Config, ConfigFactory}

object ConfigWriter {

  val conf: Config = ConfigFactory.load()

  val specs: Array[String] = conf.getString("main.secretsSpec").split(",")

  def apply(pw: PrintWriter): Unit = {
    specs.foreach(spec => {
      val srec = spec.split(":")
      if (srec.length == 2) {
        val configName = srec(0)
        val secretName = srec(1)
        val secretValue = SecretValue(secretName)
        pw.println(s"""$configName = "$secretValue"""")
      }
    })
  }

}
