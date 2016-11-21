lazy val root = Project("plugins", file(".")) dependsOn (packager)

lazy val packager = file("..").getAbsoluteFile.toURI

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.5")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.2.0")
