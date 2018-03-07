import com.typesafe.sbt.SbtNativePackager.autoImport._
import com.typesafe.sbt.packager.jdkpackager.JDKPackagerPlugin.autoImport._

name := "$name$"


lazy val root = (project in file(".")).
  enablePlugins(JavaAppPackaging, JDKPackagerPlugin).
  settings(
    organization := "$organization$",
    scalaVersion := "2.12.4",
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

    mainClass in Compile := Some("$package$.app.AppStarter"),

    // This becomes the Start Menu subdirectory for the windows installers.
    maintainer := "RE:invent Software, Leonard Daume",

    packageSummary := "Binary Guice Application",

    packageDescription := "An easy starting point for a java application with guice.",

    bashScriptExtraDefines ++= Seq(
      """#ulimit -n 99999;
        |if [[ -z "\${JAVA_HOME}" ]];
        |then
        |   echo "Will download java";
        |
        |   curl -s "https://get.sdkman.io" | bash;
        |
        |   source "\$HOME/.sdkman/bin/sdkman-init.sh";
        |
        |   sdk install java < /dev/null;
        |else
        |   echo "Will use java from \$JAVA_HOME";
        |fi""".stripMargin
    )
  )
