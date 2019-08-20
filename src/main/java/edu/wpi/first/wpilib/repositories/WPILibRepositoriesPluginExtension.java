package edu.wpi.first.wpilib.repositories;

import javax.inject.Inject;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.provider.Property;
import org.gradle.api.publish.PublishingExtension;

public class WPILibRepositoriesPluginExtension {

    private final Property<String> mavenLocalDevelopmentUrl;
    private final Property<String> mavenLocalReleaseUrl;
    private final Property<String> mavenRemoteDevelopmentUrl;
    private final Property<String> mavenRemoteReleaseUrl;

    private final Project m_project;

    @Inject
    public WPILibRepositoriesPluginExtension(Project project) {
        String remoteBase = "https://maven.wpilib.org/artifactory/";
        String localBase = System.getProperty("user.home") + "/releases/maven/";
        String devExtension = "development";
        String releaseExtension = "release";
        mavenLocalDevelopmentUrl = project.getObjects().property(String.class);
        mavenLocalDevelopmentUrl.set(localBase + devExtension);
        mavenLocalReleaseUrl = project.getObjects().property(String.class);
        mavenLocalReleaseUrl.set(localBase + releaseExtension);
        mavenRemoteDevelopmentUrl = project.getObjects().property(String.class);
        mavenRemoteDevelopmentUrl.set(remoteBase + devExtension);
        mavenRemoteReleaseUrl = project.getObjects().property(String.class);
        mavenRemoteReleaseUrl.set(remoteBase + releaseExtension);
        m_project = project;
    }

    private void addInternalLocalPublishing(Property<String> urlProperty, String name) {
        m_project.getPluginManager().withPlugin("maven-publish", plugin -> {
            PublishingExtension ext = m_project.getExtensions().getByType(PublishingExtension.class);
            ext.getRepositories().maven(repo -> {
                repo.setName(name);
                repo.setUrl(urlProperty);
            });
        });
    }

    public Property<String> getMavenLocalDevelopmentUrl() {
        return mavenLocalDevelopmentUrl;
    }

    public Property<String> getMavenLocalReleaseUrl() {
        return mavenLocalReleaseUrl;
    }

    public Property<String> getMavenRemoteDevelopmentUrl() {
        return mavenRemoteDevelopmentUrl;
    }

    public Property<String> getMavenRemoteReleaseUrl() {
        return mavenRemoteReleaseUrl;
    }

    public void addLocalDevelopmentPublishing() {
        addInternalLocalPublishing(mavenLocalDevelopmentUrl, "Local Development Publishing");
    }

    public void addLocalReleasePublishing() {
        addInternalLocalPublishing(mavenLocalReleaseUrl, "Local Release Publishing");
    }

    private void addRepositoryInternal(Action<MavenArtifactRepository> configureAction, Property<String> url, String name) {
        m_project.getRepositories().maven(repo -> {
            repo.setName(name);
            repo.setUrl(url);
            if (configureAction != null) {
                configureAction.execute(repo);
            }
        });
    }

    public void addLocalReleaseRepository() {
        addLocalReleaseRepository(null);
    }

    public void addLocalReleaseRepository(Action<MavenArtifactRepository> configureAction) {
        addRepositoryInternal(configureAction, mavenLocalReleaseUrl, "Local Release");
    }

    public void addLocalDevelopmentRepository() {
        addLocalDevelopmentRepository(null);
    }

    public void addLocalDevelopmentRepository(Action<MavenArtifactRepository> configureAction) {
        addRepositoryInternal(configureAction, mavenLocalDevelopmentUrl, "Local Development");
    }

    public void addRemoteReleaseRepository() {
        addRemoteReleaseRepository(null);
    }

    public void addRemoteReleaseRepository(Action<MavenArtifactRepository> configureAction) {
        addRepositoryInternal(configureAction, mavenRemoteReleaseUrl, "Remote Release");
    }

    public void addRemoteDevelopmentRepository() {
        addRemoteDevelopmentRepository(null);
    }

    public void addRemoteDevelopmentRepository(Action<MavenArtifactRepository> configureAction) {
        addRepositoryInternal(configureAction, mavenRemoteDevelopmentUrl, "Remote Development");
    }
}