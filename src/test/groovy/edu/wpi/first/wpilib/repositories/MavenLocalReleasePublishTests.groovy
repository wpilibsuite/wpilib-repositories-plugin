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
class MavenLocalReleasePublishTests extends MavenTestBase {
    @Test
    public void 'Setting useLocalReleasePublish works after applied publish extension'() {
        def project = createProjectInstance()

        project.pluginManager.apply 'maven-publish'
        project.extensions.getByType(WPILibRepositoriesPluginExtension).addLocalReleasePublishing(project)

        def repos = project.extensions.getByType(PublishingExtension).repositories
        assertEquals(1, repos.size())
        repos.all {
            def path = new File(it.url.path).absolutePath
            def expectedPath = new File(localBase + releaseExtension).absolutePath
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useLocalReleasePublish works before applied publish extension'() {
        def project = createProjectInstance()

        project.extensions.getByType(WPILibRepositoriesPluginExtension).addLocalReleasePublishing(project)

        project.pluginManager.apply 'maven-publish'

        def repos = project.extensions.getByType(PublishingExtension).repositories
        assertEquals(1, repos.size())
        repos.all {
            def path = new File(it.url.path).absolutePath
            def expectedPath = new File(localBase + releaseExtension).absolutePath
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'mavenLocalReleaseUrl defaults correctly'() {
        def project = createProjectInstance()
        def path = new File(project.extensions.getByType(WPILibRepositoriesPluginExtension).mavenLocalReleaseUrl.get()).absolutePath
        def expectedPath = new File(localBase + releaseExtension).absolutePath
        assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
    }

    @Test
    public void 'Setting useLocalReleasePublish works after manualSet'() {
        def project = createProjectInstance()

        def expectedPath = "https://localhost/test"

        project.pluginManager.apply 'maven-publish'

        project.extensions.getByType(WPILibRepositoriesPluginExtension).mavenLocalReleaseUrl.set(expectedPath)
        project.extensions.getByType(WPILibRepositoriesPluginExtension).addLocalReleasePublishing(project)

        def repos = project.extensions.getByType(PublishingExtension).repositories
        assertEquals(1, repos.size())
        repos.all {
            def path = it.url.toString()
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }
}
