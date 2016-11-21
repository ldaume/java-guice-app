import com.typesafe.sbt.SbtNativePackager.autoImport._
import com.typesafe.sbt.packager.jdkpackager.JDKPackagerPlugin.autoImport._

name := """guice-application"""


lazy val root = (project in file(".")).
  enablePlugins(JavaAppPackaging, JDKPackagerPlugin).
  settings(
    organization := "software.reinvent",
    scalaVersion := "2.12.0",
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
      """ulimit -c unlimited;
        |if [[ -v JAVA_HOME ]];
        |then
        |   echo "Will use jre from $JAVA_HOME";
        |else
        |   echo "Will download jre";
        |   INSTALL_DIR="$(getent passwd $USER | awk -F ':' '{print $6}')/java"
        |   mkdir -p ${INSTALL_DIR}
        |   wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u102-b14/jre-8u102-linux-x64.tar.gz -O ${INSTALL_DIR}/jre.tar.gz;
        |   tar xfvz ${INSTALL_DIR}/jre.tar.gz -C ${INSTALL_DIR};
        |   JAVA_HOME=${INSTALL_DIR}/jre1.8.0_102/;
        |   export JAVA_HOME=$JAVA_HOME;
        |   grep -q -F "export JAVA_HOME=$JAVA_HOME" $HOME/.profile || echo "export JAVA_HOME=$JAVA_HOME" >> $HOME/.profile;
        |fi""".stripMargin
    )
  )


libraryDependencies ++= Seq(
  "com.beust" % "jcommander" % "1.58",


  // CSV
  "com.univocity" % "univocity-parsers" % "2.2.3",

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
  "com.google.guava" % "guava" % "20.0",
  "org.apache.commons" % "commons-collections4" % "4.1",
  "commons-io" % "commons-io" % "2.5",
  "com.typesafe" % "config" % "1.3.1",
  "com.google.inject" % "guice" % "4.1.0",
  "org.zeroturnaround" % "zt-zip" % "1.10",
  "org.unbescape" % "unbescape" % "1.1.4.RELEASE",
  "io.reactivex" % "rxjava" % "1.2.2",
  "com.github.rholder" % "guava-retrying" % "2.0.0" exclude("com.google.guava", "guava"),
  // READABILITY
  "com.github.mfornos" % "humanize-slim" % "1.2.2" exclude("com.google.guava", "guava"),


  // LOGGING
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "ch.qos.logback" % "logback-core" % "1.1.7",


  // TEST
  "org.assertj" % "assertj-core" % "3.6.0" % "test",
  "org.assertj" % "assertj-guava" % "3.1.0" % "test" exclude("com.google.guava", "guava"),
  "com.novocode" % "junit-interface" % "0.11" % "test->default",
  "org.jukito" % "jukito" % "1.4.1" % "test",
  "info.debatty" % "java-string-similarity" % "0.19" % "test",
  "de.flapdoodle.embed" % "de.flapdoodle.embed.memcached" % "1.06.4" % "test",
  "com.wix" % "wix-embedded-mysql" % "2.1.1" % "test",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.5" % "test"

)

scalacOptions in Test ++= Seq("-Yrangepos")
//testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

resolvers += Resolver.mavenLocal

//publishMavenStyle := true

//crossPaths := false

//autoScalaLibrary := false
