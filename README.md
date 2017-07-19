| Dependencies UpToDate | License |
|:---------------------:|:-------:|
| [![Dependencies UpToDate](https://ci.reinvent-software.de/buildStatus/icon?job=java-guice-app-template-DependencyCheck)](https://ci.reinvent-software.de/job/java-guice-app-template-DependencyCheck) | [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) |

Binary Guice Application
==========

# Abstract

An easy starting point for a java application with guice as a [Giter8](http://www.foundweekends.org/giter8/) template which can be used with [sbt](http://www.scala-sbt.org/).

# Template Usage
`sbt new ldaume/java-guice-app.g8`

# Build

`sbt clean universal:packageBin` creates a zip file under `target/universal/` which contains the binary and libraries.

# Binary Usage

One can get usage information's by executing `bin/guice-aplication`.
