name := "KeyVaultConfig"

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
    "com.typesafe" % "config" % "1.3.2",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "com.microsoft.azure" % "azure" % "1.8.0",

    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )

libraryDependencies ~= { _.map(_.exclude("org.slf4j", "slf4j-simple")) }

mainClass in assembly := Some("onextent.k8s.azure.keyvault.configmap.Main")
assemblyJarName in assembly := "KeyVaultConfig.jar"

assemblyMergeStrategy in assembly := {
  case PathList("reference.conf") => MergeStrategy.concat
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case PathList("META-INF", _ @ _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

