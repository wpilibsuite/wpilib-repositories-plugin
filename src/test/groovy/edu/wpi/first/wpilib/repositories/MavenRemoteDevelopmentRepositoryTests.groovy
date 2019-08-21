package edu.wpi.first.wpilib.repositories

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import java.nio.file.Files

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class MavenRemoteDevelopmentRepositoryTests extends MavenTestBase {
    @Test
    public void 'Setting useRemoteDevelopmentRepo works'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        ext.addRemoteDevelopmentRepository()

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = it.url.toString()
            def expectedPath = remoteBase + devExtension
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useRemoteDevelopmentRepo works with changed repo'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        def expectedPath = "https://localhost/test"

        ext.mavenRemoteDevelopmentUrl.set(expectedPath)

        ext.addRemoteDevelopmentRepository()

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = it.url.toString()
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useRemoteDevelopmentRepo works with changed repo lazy'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        def expectedPath = "https://localhost/test"

        ext.addRemoteDevelopmentRepository()

        ext.mavenRemoteDevelopmentUrl.set(expectedPath)

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = it.url.toString()
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'mavenRemoteDevelopmentUrl defaults correctly'() {
        def project = createProjectInstance()
        def path = project.extensions.getByType(WPILibRepositoriesPluginExtension).mavenRemoteDevelopmentUrl.get()
        def expectedPath = remoteBase + devExtension
        assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
    }
}