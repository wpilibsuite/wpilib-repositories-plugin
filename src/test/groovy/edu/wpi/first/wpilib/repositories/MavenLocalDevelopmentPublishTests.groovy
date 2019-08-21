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
class MavenLocalDevelopmentPublishTests extends MavenTestBase {
    @Test
    public void 'Setting useLocalDevelopmentPublish works after applied publish extension'() {
        def project = createProjectInstance()

        project.pluginManager.apply 'maven-publish'
        project.extensions.getByType(WPILibRepositoriesPluginExtension).addLocalDevelopmentPublishing()

        def repos = project.extensions.getByType(PublishingExtension).repositories
        assertEquals(1, repos.size())
        repos.all {
            def path = new File(it.url.path).absolutePath
            def expectedPath = new File(localBase + devExtension).absolutePath
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useLocalDevelopmentPublish works before applied publish extension'() {
        def project = createProjectInstance()

        project.extensions.getByType(WPILibRepositoriesPluginExtension).addLocalDevelopmentPublishing()

        project.pluginManager.apply 'maven-publish'

        def repos = project.extensions.getByType(PublishingExtension).repositories
        assertEquals(1, repos.size())
        repos.all {
            def path = new File(it.url.path).absolutePath
            def expectedPath = new File(localBase + devExtension).absolutePath
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useLocalDevelopmentPublish works after manualSet'() {
        def project = createProjectInstance()

        def expectedPath = "https://localhost/test"

        project.pluginManager.apply 'maven-publish'

        project.extensions.getByType(WPILibRepositoriesPluginExtension).mavenLocalDevelopmentUrl.set(expectedPath)
        project.extensions.getByType(WPILibRepositoriesPluginExtension).addLocalDevelopmentPublishing()

        def repos = project.extensions.getByType(PublishingExtension).repositories
        assertEquals(1, repos.size())
        repos.all {
            def path = it.url.toString()
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }
}