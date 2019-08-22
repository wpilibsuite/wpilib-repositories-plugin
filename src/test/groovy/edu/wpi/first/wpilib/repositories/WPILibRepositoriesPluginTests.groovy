package edu.wpi.first.wpilib.repositories

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import java.nio.file.Files

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

/**
 * Tests for the wpilib repositories plugin
 */
class WPILibRepositoriesPluginTests {
    @Test
    public void 'Applying plugin creates extension'() {
        def project = createProjectInstance()
        project.evaluate()
        assertTrue(project.extensions.getByName('wpilibRepositories') instanceof WPILibRepositoriesPluginExtension)
    }

    static def createProjectInstance() {
        def project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'edu.wpi.first.wpilib.repositories.WPILibRepositoriesPlugin'

        return project
    }
}
