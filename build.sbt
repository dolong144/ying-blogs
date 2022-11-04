ThisBuild / scalaVersion := "2.13.9"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """long_dv_bbs_backend""",
    libraryDependencies ++= Seq(
      evolutions,
      jdbc,
      guice,
      specs2 % Test,
      "mysql"                       % "mysql-connector-java"                % "8.0.29",
      "org.scalatestplus.play"      %% "scalatestplus-play"                 % "5.1.0" % Test,
      "org.scalikejdbc"             %% "scalikejdbc"                        % "3.5.0",
      "org.scalikejdbc"             %% "scalikejdbc-config"                 % "3.5.0",
      "org.scalikejdbc"             %% "scalikejdbc-play-initializer"       % "2.8.0-scalikejdbc-3.5",
      "com.typesafe.play"           %% "play-guice"                         % "2.8.15",
      "org.skinny-framework"        %% "skinny-orm"                         % "3.1.0",
      "io.jvm.uuid"                 %% "scala-uuid"                         % "0.3.1",
//      "com.github.t3hnar"           %% "scala-bcrypt"                       % "4.3.0"
      "org.mindrot"                 % "jbcrypt"                             % "0.4",
       "org.webjars"                % "bootstrap"                           % "3.3.6",
      "org.webjars"                 % "requirejs"                           % "2.2.0",
      javaJpa,
      "org.hibernate"               % "hibernate-core"                      % "5.4.32.Final"
    ),
    routesGenerator := InjectedRoutesGenerator
  )