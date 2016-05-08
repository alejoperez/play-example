name := """general-app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "mysql" % "mysql-connector-java" % "5.1.38",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final"
)

PlayKeys.externalizeResources := false
