package edu.wpi.first.wpilib.repositories

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import java.nio.file.Files

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class MavenRemoteReleaseRepositoryTests extends MavenTestBase {
    @Test
    public void 'Setting useRemoteReleaseRepo works'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        ext.addRemoteReleaseRepository()

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = it.url.toString()
            def expectedPath = remoteBase + releaseExtension
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useRemoteReleaseRepo works with changed repo'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        def expectedPath = "https://localhost/test"

        ext.mavenRemoteReleaseUrl.set(expectedPath)

        ext.addRemoteReleaseRepository()

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = it.url.toString()
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useRemoteReleaseRepo works with changed repo lazy'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        def expectedPath = "https://localhost/test"

        ext.addRemoteReleaseRepository()

        ext.mavenRemoteReleaseUrl.set(expectedPath)

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = it.url.toString()
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'mavenRemoteReleaseUrl defaults correctly'() {
        def project = createProjectInstance()
        def path = project.extensions.getByType(WPILibRepositoriesPluginExtension).mavenRemoteReleaseUrl.get()
        def expectedPath = remoteBase + releaseExtension
        assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
    }
}