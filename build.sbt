name := "actor"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"


libraryDependencies += "com.marekkadek" %% "scrawler" % "0.0.3"

// available for Scala 2.11.8, 2.12.0
libraryDependencies += "co.fs2" %% "fs2-core" % "0.9.4"

// optional I/O library
libraryDependencies += "co.fs2" %% "fs2-io" % "0.9.4"

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.3.0"
