Binary Guice Application
==========

# Abstract

An easy starting point for a java application with guice as a [Giter8](http://www.foundweekends.org/giter8/) template which can be used with [sbt](http://www.scala-sbt.org/).

# Template Usage
`sbt new ldaume/java-guice-app.g8 --name=binary-guice-application`

# Build

`sbt clean universal:packageBin` creates a zip file under `target/universal/` which contains the binary and libraries.

# Binary Usage

One can get usage information's by executing `bin/guice-aplication`.
