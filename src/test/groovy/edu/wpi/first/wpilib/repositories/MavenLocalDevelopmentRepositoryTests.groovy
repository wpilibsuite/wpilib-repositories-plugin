package edu.wpi.first.wpilib.repositories

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import java.nio.file.Files

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class MavenLocalDevelopmentRepositoryTests extends MavenTestBase {
    @Test
    public void 'Setting useLocalDevelopmentRepo works'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        ext.addLocalDevelopmentRepository(project)

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = new File(it.url.path).absolutePath
            def expectedPath = new File(localBase + devExtension).absolutePath
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useLocalDevelopmentRepo works with changed repo'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        def expectedPath = "https://localhost/test"

        ext.mavenLocalDevelopmentUrl.set(expectedPath)

        ext.addLocalDevelopmentRepository(project)

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = it.url.toString()
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'Setting useLocalDevelopmentRepo works with changed repo lazy'() {
        def project = createProjectInstance()

        def ext = project.extensions.getByType(WPILibRepositoriesPluginExtension);

        def expectedPath = "https://localhost/test"

        ext.addLocalDevelopmentRepository(project)

        ext.mavenLocalDevelopmentUrl.set(expectedPath)

        def repos = project.repositories

        assertEquals(1, repos.size())

        repos.all {
            def path = it.url.toString()
            assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
        }
    }

    @Test
    public void 'mavenLocalDevelopmentUrl defaults correctly'() {
        def project = createProjectInstance()
        def path = new File(project.extensions.getByType(WPILibRepositoriesPluginExtension).mavenLocalDevelopmentUrl.get()).absolutePath
        def expectedPath = new File(localBase + devExtension).absolutePath
        assertTrue("Search string is $path, expected is $expectedPath", (boolean) path.equals(expectedPath))
    }
}
