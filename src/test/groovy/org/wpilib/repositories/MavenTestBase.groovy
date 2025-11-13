package org.wpilib.repositories

import org.gradle.testfixtures.ProjectBuilder

class MavenTestBase {
    String remoteBase = "https://frcmaven.wpi.edu/artifactory/";
    String localBase = System.getProperty("user.home") + "/releases/maven/";
    String devExtension = "development";
    String releaseExtension = "release";

    static def createProjectInstance() {
        def project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'org.wpilib.WPILibRepositoriesPlugin'

        return project
    }
}
