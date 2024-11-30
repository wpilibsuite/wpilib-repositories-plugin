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
    private final Property<String> repoSuffix;

    @Inject
    public WPILibRepositoriesPluginExtension(Project project) {
        String remoteBase = "https://frcmaven.wpi.edu/artifactory/";
        String localBase = System.getProperty("user.home") + "/releases/maven/";
        String devExtension = "development";
        String releaseExtension = "release";
        repoSuffix = project.getObjects().property(String.class);
        repoSuffix.set("");
        mavenLocalDevelopmentUrl = project.getObjects().property(String.class);
        mavenLocalDevelopmentUrl.set(localBase + devExtension);
        mavenLocalReleaseUrl = project.getObjects().property(String.class);
        mavenLocalReleaseUrl.set(localBase + releaseExtension);
        mavenRemoteDevelopmentUrl = project.getObjects().property(String.class);
        mavenRemoteDevelopmentUrl.set(project.provider(() -> {
            return remoteBase + devExtension + repoSuffix.get();
        }));
        mavenRemoteReleaseUrl = project.getObjects().property(String.class);
        mavenRemoteReleaseUrl.set(project.provider(() -> {
            return remoteBase + releaseExtension + repoSuffix.get();
        }));
    }

    private void addInternalLocalPublishing(Project project, Property<String> urlProperty, String name) {
        project.getPluginManager().withPlugin("maven-publish", plugin -> {
            PublishingExtension ext = project.getExtensions().getByType(PublishingExtension.class);
            ext.getRepositories().maven(repo -> {
                repo.setName(name);
                repo.setUrl(urlProperty);
            });
        });
    }

    public void useExistingRepos() {
        repoSuffix.set("");
    }

    public void use2027Repos() {
        repoSuffix.set("-2027");
    }

    public Property<String> getRepoSuffix() {
        return repoSuffix;
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

    public void addLocalDevelopmentPublishing(Project project) {
        addInternalLocalPublishing(project, mavenLocalDevelopmentUrl, "Local-Development-Publishing");
    }

    public void addLocalReleasePublishing(Project project) {
        addInternalLocalPublishing(project, mavenLocalReleaseUrl, "Local-Release-Publishing");
    }

    private void addRepositoryInternal(Project project, Action<MavenArtifactRepository> configureAction,
            Property<String> url, String name) {
        project.getRepositories().maven(repo -> {
            repo.setName(name);
            repo.setUrl(url);
            if (configureAction != null) {
                configureAction.execute(repo);
            }
        });
    }

    public void addLocalReleaseRepository(Project project) {
        addLocalReleaseRepository(project, null);
    }

    public void addLocalReleaseRepository(Project project, Action<MavenArtifactRepository> configureAction) {
        addRepositoryInternal(project, configureAction, mavenLocalReleaseUrl, "Local-Release");
    }

    public void addLocalDevelopmentRepository(Project project) {
        addLocalDevelopmentRepository(project, null);
    }

    public void addLocalDevelopmentRepository(Project project, Action<MavenArtifactRepository> configureAction) {
        addRepositoryInternal(project, configureAction, mavenLocalDevelopmentUrl, "Local-Development");
    }

    public void addRemoteReleaseRepository(Project project) {
        addRemoteReleaseRepository(project, null);
    }

    public void addRemoteReleaseRepository(Project project, Action<MavenArtifactRepository> configureAction) {
        addRepositoryInternal(project, configureAction, mavenRemoteReleaseUrl, "Remote-Release");
    }

    public void addRemoteDevelopmentRepository(Project project) {
        addRemoteDevelopmentRepository(project, null);
    }

    public void addRemoteDevelopmentRepository(Project project, Action<MavenArtifactRepository> configureAction) {
        addRepositoryInternal(project, configureAction, mavenRemoteDevelopmentUrl, "Remote-Development");
    }

    public void addAllDevelopmentRepositories(Project project) {
        addLocalDevelopmentPublishing(project);
        addLocalDevelopmentRepository(project);
        addRemoteDevelopmentRepository(project);
    }

    public void addAllReleaseRepositories(Project project) {
        addLocalReleasePublishing(project);
        addLocalReleaseRepository(project);
        addRemoteReleaseRepository(project);
    }
}
