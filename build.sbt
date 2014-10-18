import NativePackagerKeys._

packageArchetype.java_application

name := "test-api"

version := "1.0"

libraryDependencies += "io.dropwizard" % "dropwizard-core" % "0.7.1"

libraryDependencies += "io.dropwizard" % "dropwizard-assets" % "0.7.1"

{ // Needed for Heroku's stage. Uses sbt-native-packager plugin, defined in project/plugins.sbt
  val stage = TaskKey[Unit]("stage", "Prepares the project to be run, in environments that deploy source trees rather than packages.")
  stage in Compile := {}
}

mainClass in Compile := Some("org.tomaszjaneczko.testpoc.api.TestAPIApplication")
