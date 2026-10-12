import scala.collection.Seq

ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.14"

val zioVersion      = "2.1.9"
val zioKafkaVersion = "2.8.2"
val tapirVersion    = "1.11.7"

lazy val root = (project in file("."))
  .settings(
    name := "payroll",
    libraryDependencies ++= Seq(
      // ZIO core (zio-http убираем — теперь не нужен)
      "dev.zio" %% "zio"         % zioVersion,
      "dev.zio" %% "zio-streams" % zioVersion,

      // Kafka
      "dev.zio" %% "zio-kafka"         % zioKafkaVersion,
      "dev.zio" %% "zio-kafka-testkit" % zioKafkaVersion % Test,

      // Tapir + Netty + ZIO
      "com.softwaremill.sttp.tapir" %% "tapir-netty-server-zio"  % tapirVersion, // ← заменили
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe"        % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,

      // Circe
      "io.circe" %% "circe-core"    % "0.14.9",
      "io.circe" %% "circe-generic" % "0.14.9",
      "io.circe" %% "circe-parser"  % "0.14.9",

      // Doobie
      "org.typelevel" %% "doobie-core"      % "1.0.0-RC13",
      "org.typelevel" %% "doobie-postgres"  % "1.0.0-RC13",
      "org.typelevel" %% "doobie-specs2"    % "1.0.0-RC13" % Test,
      "org.typelevel" %% "doobie-scalatest" % "1.0.0-RC13" % Test,
    ),

    dependencyOverrides ++= Seq(
      "dev.zio"  %% "zio"            % zioVersion,
      "dev.zio"  %% "zio-streams"    % zioVersion,
      "io.circe" %% "circe-core"     % "0.14.9",
      "io.circe" %% "circe-generic"  % "0.14.9",
      "io.circe" %% "circe-parser"   % "0.14.9",
    )
  )