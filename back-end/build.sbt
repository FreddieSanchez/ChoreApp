organization := "io.github.freddiesanchez"
name := "choreapp"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.4"

val Http4sVersion = "0.17.6"
val LogbackVersion = "1.2.3"
val CirceVersion   = "0.8.0"
val ScalaTestVersion = "3.0.4"
val DoobieVersion = "0.4.4"
val H2Version = "1.4.194"

libraryDependencies ++= Seq(
  "org.http4s"     %% "http4s-blaze-server"  % Http4sVersion,
  "org.http4s"     %% "http4s-circe"         % Http4sVersion,
  "org.http4s"     %% "http4s-dsl"           % Http4sVersion,
  "ch.qos.logback" %  "logback-classic"      % LogbackVersion,
  "io.circe"       %% "circe-generic"        % CirceVersion, // auto-derivation of JSON codecs
  "io.circe"       %% "circe-literal"        % CirceVersion, // string interpolation to JSON model
  "org.scalatest"  %% "scalatest" % "3.0.4"  % "test",
  "org.tpolecat"   %% "doobie-core-cats"     % DoobieVersion,// cats   + fs2
  "org.tpolecat"   %% "doobie-h2"            % DoobieVersion,
  "com.h2database" %  "h2"                   % H2Version
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)


