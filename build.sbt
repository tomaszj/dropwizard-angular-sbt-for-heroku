import NativePackagerKeys._

packageArchetype.java_application

name := "test-api"

version := "1.0"

libraryDependencies += "io.dropwizard" % "dropwizard-core" % "0.7.1"

libraryDependencies += "io.dropwizard" % "dropwizard-assets" % "0.7.1"

libraryDependencies ++= Seq(
  "io.dropwizard" % "dropwizard-testing" % "0.7.1" % Test,
  "io.dropwizard" % "dropwizard-client" % "0.7.1" % Test,
  "org.hamcrest" % "hamcrest-all" % "1.3" % Test,
  "org.mockito" % "mockito-core" % "1.10.8" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test
)

{ // Needed for Heroku
  val stage = TaskKey[Unit]("stage", "Prepares the project to be run, in environments that deploy source trees rather than packages.")
  stage in Compile := {}
}

mainClass in Compile := Some("org.tomaszjaneczko.testpoc.api.TestAPIApplication")
