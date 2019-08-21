package edu.wpi.first.wpilib.repositories

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import java.nio.file.Files

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class MavenLocalReleaseRepositoryTests extends MavenTestBase {
    @Test
    public void 'Setting useLocalReleaseRepo works'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        ext.addLocalReleaseRepository(project)

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = new File(it.url.path).absolutePath
            def expectedPath = new File(localBase + releaseExtension).absolutePath
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useLocalReleaseRepo works with changed repo'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        def expectedPath = "https://localhost/test"

        ext.mavenLocalReleaseUrl.set(expectedPath)

        ext.addLocalReleaseRepository(project)

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = it.url.toString()
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useLocalReleaseRepo works with changed repo lazy'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        def expectedPath = "https://localhost/test"

        ext.addLocalReleaseRepository(project)

        ext.mavenLocalReleaseUrl.set(expectedPath)

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = it.url.toString()
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
}
