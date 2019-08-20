package edu.wpi.first.wpilib.repositories

import org.gradle.testfixtures.ProjectBuilder

class MavenTestBase {
    String remoteBase = "https://maven.wpilib.org/artifactory/";
    String localBase = System.getProperty("user.home") + "/releases/maven/";
    String devExtension = "development";
    String releaseExtension = "release";

    static def createProjectInstance() {
        def project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'edu.wpi.first.wpilib.repositories.WPILibRepositoriesPlugin'
        
        return project
    }
}