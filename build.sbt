name := "KeyVaultConfigMap"

fork := true
javaOptions in test ++= Seq(
  "-Xms128M", "-Xmx256M",
  "-XX:MaxPermSize=256M",
  "-XX:+CMSClassUnloadingEnabled"
)

parallelExecution in test := false

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++=
  Seq(
    "com.typesafe" % "config" % "1.3.1",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",

    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )

mainClass in assembly := Some("onextent.k8s.azure.keyvault.configmap.Main")
assemblyJarName in assembly := "KeyVaultConfigMap.jar"

assemblyMergeStrategy in assembly := {
  case PathList("reference.conf") => MergeStrategy.concat
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case PathList("META-INF", _ @ _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

