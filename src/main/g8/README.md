Binary Guice Application
==========

# Abstract

An easy starting point for a java application with guice as a [template](https://www.lightbend.com/activator/template/binary-guice-application) which can be used with [Lightbend Activator](https://www.lightbend.com/activator/templates)

# Template Usage
`activator new PROJECTNAME binary-guice-application`

# Build

`sbt clean universal:packageBin` creates a zip file under `target/universal/` which contains the binary and libraries.

# Binary Usage

One can get usage information's by executing `bin/guice-aplication`.
