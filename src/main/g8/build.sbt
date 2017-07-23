import com.typesafe.sbt.SbtNativePackager.autoImport._
import com.typesafe.sbt.packager.jdkpackager.JDKPackagerPlugin.autoImport._

name := "$name$"


lazy val root = (project in file(".")).
  enablePlugins(JavaAppPackaging, JDKPackagerPlugin).
  settings(
    organization := "$organization$",
    scalaVersion := "2.12.2",
    version := "$version$",
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

    mainClass in Compile := Some("$package$.guice.application.app.AppStarter"),

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

  "com.beust" % "jcommander" % "1.72",

  // CSV
  "com.univocity" % "univocity-parsers" % "2.4.1",

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
  "software.reinvent" % "commons" % "0.3.3",
  "org.zeroturnaround" % "zt-zip" % "1.11",
  "io.reactivex.rxjava2" % "rxjava" % "2.1.2",


  // TEST
  "org.assertj" % "assertj-core" % "3.8.0" % "test",
  "org.assertj" % "assertj-guava" % "3.1.0" % "test" exclude("com.google.guava", "guava"),
  "com.novocode" % "junit-interface" % "0.11" % "test->default",
  "org.jukito" % "jukito" % "1.5" % "test",
  "info.debatty" % "java-string-similarity" % "0.24" % "test",
  "de.flapdoodle.embed" % "de.flapdoodle.embed.memcached" % "1.06.4" % "test",
  "com.wix" % "wix-embedded-mysql" % "2.4.6" % "test",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.9" % "test"

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
