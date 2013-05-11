import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

scalaVersion := "2.10.1"

libraryDependencies ++= Seq(
    "org.neo4j" % "neo4j" % "2.0.0-M02",
    "org.mapdb" % "mapdb" % "0.9.1"
  )

