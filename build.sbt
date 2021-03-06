import ReleaseTransformations._


homepage := Some(url("https://github.com/cibotech/leaflet-facade"))

headerLicense := Some(HeaderLicense.Custom(
  s"""/* Copyright (c) 2018 CiBO Technologies - All Rights Reserved
     | * You may use, distribute, and modify this code under the
     | * terms of the BSD 3-Clause license.
     | *
     | * A copy of the license can be found on the root of this repository,
     | * at ${homepage.value.get.toString}/LICENSE.md,
     | * or at https://opensource.org/licenses/BSD-3-Clause
     | */
     |
       |""".stripMargin)
)

// How does this now work !?
//commentStyle := CommentStyle.cStyleBlockComment

lazy val globalSettings = Seq(
  organization := "com.cibo",
  scmInfo := Some(ScmInfo(
    url("https://github.com/cibotech/leaflet-facade"),
    "scm:git:git@github.com/cibotech/leaflet-facade.git",
    Some("scm:git:git@github.com/cibotech/leaflet-facade.git"))),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  publishArtifact in IntegrationTest := false,
  scalaVersion := "2.12.4",
  crossScalaVersions := Seq("2.11.8", "2.12.4"),
  releaseCrossBuild := true,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    releaseStepCommand("createHeaders"),
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    pushChanges
  ),
  bintrayOrganization := Some("cibotech"),
  bintrayRepository := "public",
  bintrayPackageLabels := Seq("scala", "scala.js", "leaflet"),
  requiresDOM := true,
  licenses += ("BSD Simplified", url("https://opensource.org/licenses/BSD-3-Clause"))
)

lazy val root = project.in(file("."))
  .aggregate(`leaflet-facade`, `leaflet-draw`)
  .settings(
    crossScalaVersions := Seq("2.11.8", "2.12.4"),
    releaseCrossBuild := true,
    publishArtifact := false,
    publish := {}
  )

lazy val `leaflet-facade` = project.in(file("leaflet"))
  .settings(globalSettings)
  .settings(
    crossScalaVersions := Seq("2.11.8", "2.12.4"),
    releaseCrossBuild := true,
    name := "leaflet-facade",
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.4"
    ),
    jsDependencies ++= Seq(
      "org.webjars.npm" % "leaflet" % "1.3.1" / "1.3.1/dist/leaflet.js"
    )
  ) enablePlugins (ScalaJSPlugin)

lazy val `leaflet-draw` = project.in(file("leaflet-draw"))
  .settings(globalSettings)
  .settings(
    crossScalaVersions := Seq("2.11.8", "2.12.4"),
    releaseCrossBuild := true,
    name := "leaflet-draw-facade",
    jsDependencies ++= Seq(
      "org.webjars.npm" % "leaflet-draw" % "1.0.2" / "leaflet.draw.js"
    )
  ) dependsOn (`leaflet-facade`) enablePlugins (ScalaJSPlugin)