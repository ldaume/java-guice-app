import com.typesafe.sbt.SbtNativePackager.autoImport._
import com.typesafe.sbt.packager.jdkpackager.JDKPackagerPlugin.autoImport._

name := """$name$"""


lazy val root = (project in file(".")).
  enablePlugins(JavaAppPackaging, JDKPackagerPlugin).
  settings(
    organization := "software.reinvent",
    scalaVersion := "2.12.1",
    version := "0.1.0-SNAPSHOT",
    jdkPackagerType := "all",
    jdkPackagerToolkit := JavaFXToolkit,
    jdkPackagerJVMArgs := Seq("-Xmx20g", "-Xms1g", "-XX:+UseG1GC", "-Xdiag", "-server", "-Xdoclint:none"),
    jdkPackagerProperties := Map("app.name" -> name.value, "app.version" -> version.value),

    javaOptions in Universal ++= Seq(
      // -J params will be added as jvm parameters
      // "-J-Xmx60g",
      // "-J-Xms4g",
      //      "-J-XX:+UseG1GC",
      "-J-server"
    ),

    mainClass in Compile := Some("software.reinvent.guice.application.app.AppStarter"),

    // This becomes the Start Menu subdirectory for the windows installers.
    maintainer := "RE:invent Software, Leonard Daume",

    packageSummary := "Binary Guice Application",

    packageDescription := "An easy starting point for a java application with guice.",

    bashScriptExtraDefines ++= Seq(
      """#ulimit -n 99999;
        |if [[ -v JAVA_HOME ]];
        |then
        |   echo "Will use java from \$JAVA_HOME";
        |else
        |   echo "Will download java";
        |
        |   curl -s "https://get.sdkman.io" | bash;
        |
        |   source "\$HOME/.sdkman/bin/sdkman-init.sh";
        |
        |   sdk install java < /dev/null;
        |fi""".stripMargin
    )
  )


libraryDependencies ++= Seq(
  "software.reinvent" % "commons" % "0.3.2",

  "com.beust" % "jcommander" % "1.60",

  // CSV
  "com.univocity" % "univocity-parsers" % "2.3.0",

  // DB
  // MySQL
  // "mysql" % "mysql-connector-java" % "5.1.39",
  // "org.jooq" % "jooq" % "3.8.4",
  // "or.jooq" % "jooq-meta" % "3.8.4",
  // "org.jooq" % "jooq-codegen" % "3.8.4",
  // "com.zaxxer" % "HikariCP" % "2.5.1",

  // Memcached
  // "com.googlecode.xmemcached" % "xmemcached" % "2.1.0",

  // Commons
  "org.apache.commons" % "commons-lang3" % "3.5",
  "com.google.guava" % "guava" % "21.0",
  "org.apache.commons" % "commons-collections4" % "4.1",
  "commons-io" % "commons-io" % "2.5",
  "com.typesafe" % "config" % "1.3.1",
  "com.google.inject" % "guice" % "4.1.0",
  "org.zeroturnaround" % "zt-zip" % "1.10",
  "org.unbescape" % "unbescape" % "1.1.4.RELEASE",
  "io.reactivex.rxjava2" % "rxjava" % "2.0.5",
  "com.github.rholder" % "guava-retrying" % "2.0.0" exclude("com.google.guava", "guava"),
  // READABILITY
  "com.github.mfornos" % "humanize-slim" % "1.2.2" exclude("com.google.guava", "guava"),


  // LOGGING
  "ch.qos.logback" % "logback-classic" % "1.1.9",
  "ch.qos.logback" % "logback-core" % "1.1.9",


  // TEST
  "org.assertj" % "assertj-core" % "3.6.1" % "test",
  "org.assertj" % "assertj-guava" % "3.1.0" % "test" exclude("com.google.guava", "guava"),
  "com.novocode" % "junit-interface" % "0.11" % "test->default",
  "org.jukito" % "jukito" % "1.5" % "test",
  "info.debatty" % "java-string-similarity" % "0.22" % "test",
  "de.flapdoodle.embed" % "de.flapdoodle.embed.memcached" % "1.06.4" % "test",
  "com.wix" % "wix-embedded-mysql" % "2.1.4" % "test",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.6" % "test"

)

resolvers ++= Seq(
  Resolver.mavenLocal,
  "ReInvent Software OSS" at "https://maven.reinvent-software.de/nexus/content/groups/public"
)

scalacOptions in Test ++= Seq("-Yrangepos")
//testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")


dependencyUpdatesFailBuild := true

//publishMavenStyle := true

//crossPaths := false

//autoScalaLibrary := false
