import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.14"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.14"

val zioVersion      = "2.1.9"
val zioKafkaVersion = "2.8.2"

lazy val root = (project in file("."))
  .settings(
    name := "payroll",
    libraryDependencies ++= Seq(
      "dev.zio"        %% "zio"               % zioVersion,
      "dev.zio"        %% "zio-streams"       % zioVersion,
      "dev.zio"        %% "zio-kafka"         % zioKafkaVersion,
      "dev.zio"        %% "zio-kafka-testkit" % zioKafkaVersion % Test,
      "dev.zio" %% "zio-http"         % "3.0.1",
      "io.circe" %% "circe-core" % "0.14.9",
      "io.circe" %% "circe-generic" % "0.14.9",
      "io.circe" %% "circe-parser" % "0.14.9"
    ),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "doobie-core"      % "1.0.0-RC13",
      "org.typelevel" %% "doobie-postgres"  % "1.0.0-RC13",
      "org.typelevel" %% "doobie-specs2"    % "1.0.0-RC13" % "test",
      "org.typelevel" %% "doobie-scalatest" % "1.0.0-RC13" % "test"

    ),
    dependencyOverrides ++= Seq(
      "dev.zio" %% "zio"         % zioVersion,
      "dev.zio" %% "zio-streams" % zioVersion
    )
  )
