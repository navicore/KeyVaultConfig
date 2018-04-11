name := "Keyvaultconfigmap"

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
    "org.typelevel" %% "cats-core" % "1.0.1",
    "com.chuusai" %% "shapeless" % "2.3.2",
    "io.github.mkotsur" %% "aws-lambda-scala" % "0.0.10",

    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )

mainClass in assembly := Some("onextent.k8s.azure.keyvault.configmap.Main")
assemblyJarName in assembly := "Keyvaultconfigmap.jar"

assemblyMergeStrategy in assembly := {
  case PathList("reference.conf") => MergeStrategy.concat
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case PathList("META-INF", _ @ _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

