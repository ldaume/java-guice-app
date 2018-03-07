libraryDependencies ++= Seq(

  "com.beust" % "jcommander" % "1.72",

  // CSV
  "com.univocity" % "univocity-parsers" % "2.6.0",

  // DB
  // MySQL
  "mysql" % "mysql-connector-java" % "5.1.45",
  "org.jooq" % "jooq" % "3.10.5",
  "org.jooq" % "jooq-meta" % "3.10.5",
  "com.zaxxer" % "HikariCP" % "2.7.8",

  // Memcached
  // "com.googlecode.xmemcached" % "xmemcached" % "2.1.0",

  // Commons
  "software.reinvent" % "commons" % "0.3.9",
  "org.zeroturnaround" % "zt-zip" % "1.12",
  "io.reactivex.rxjava2" % "rxjava" % "2.1.10",


  // TEST
  "org.assertj" % "assertj-core" % "3.9.1" % "test",
  "org.assertj" % "assertj-guava" % "3.1.0" % "test" exclude("com.google.guava", "guava"),
  "com.novocode" % "junit-interface" % "0.11" % "test->default",
  "org.jukito" % "jukito" % "1.5" % "test",
  "info.debatty" % "java-string-similarity" % "1.0.1" % "test",
  "de.flapdoodle.embed" % "de.flapdoodle.embed.memcached" % "1.06.4" % "test",
  "com.wix" % "wix-embedded-mysql" % "4.1.0" % "test",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.4" % "test"

)

dependencyUpdatesFailBuild := true

dependencyUpdatesFilter -=
    moduleFilter(organization = "mysql")
