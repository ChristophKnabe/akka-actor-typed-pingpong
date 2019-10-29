name := "akka-actor-typed-pingpong"

scalaVersion := "2.12.10"

lazy val akkaVersion = "2.5.26"

scalacOptions ++= Seq("-feature", "-deprecation", "-encoding", "UTF-8", "-unchecked")

libraryDependencies ++= Seq(
  //vendor %% scalaVersionDependentArtifact % version % scope
  // Akka
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % "test",
  // ScalaTest
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)
/*The operator %% builds an artifact name from the specified scalaVersionDependentArtifact name, 
* an underscore sign, and the upper mentioned scalaVersion. 
* So the artifact name will result here in scalatest_2.12, as the last number in a Scala version is not API relevant.
*/

//See http://www.scalatest.org/user_guide/using_scalatest_with_sbt
logBuffered in Test := false
