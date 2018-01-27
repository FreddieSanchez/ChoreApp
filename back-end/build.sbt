organization := "io.github.freddiesanchez"
name := "choreapp"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.4"

val Http4sVersion = "0.17.6"
val LogbackVersion = "1.2.3"

libraryDependencies ++= Seq(
  "org.http4s"     %% "http4s-blaze-server"  % Http4sVersion,
  "org.http4s"     %% "http4s-circe"         % Http4sVersion,
  "org.http4s"     %% "http4s-dsl"           % Http4sVersion,
  "ch.qos.logback" %  "logback-classic"      % LogbackVersion,
  // auto-derivation of JSON codecs
  "io.circe" %% "circe-generic" % "0.8.0",
  // string interpolation to JSON model
  "io.circe" %% "circe-literal" % "0.8.0"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)


