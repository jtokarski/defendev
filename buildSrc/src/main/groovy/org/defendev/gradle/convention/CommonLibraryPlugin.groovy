package org.defendev.gradle.convention

import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.Publication
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.*
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.compile.CompileOptions
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.api.tasks.testing.Test
import org.gradle.external.javadoc.StandardJavadocDocletOptions
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin



class CommonLibraryPlugin implements Plugin<Project> {

    /*
     * The apply() method gets called immediately when we apply a plugin on a project. Usually,
     * it's the first thing we do in the build.gradle script. Therefore, we really need
     * the lazy initialization feature of org.gradle.api.provider.Property as I use it in CommonLibraryExtension.
     * The capability of passing them over like
     *    pom.description = extension.pomDescription
     * is very convenient here.
     *
     */
    @Override
    void apply(Project project) {

        final CommonLibraryExtension extension = project.extensions.create('defendevCommonLibDsl', CommonLibraryExtension)

        project.group = 'org.defendev'

        project.getPluginManager().apply(JavaLibraryPlugin)

        final JavaPluginExtension javaPluginExtension = project.getExtensions().getByType(JavaPluginExtension)
        javaPluginExtension.sourceCompatibility = JavaVersion.VERSION_17
        javaPluginExtension.targetCompatibility = JavaVersion.VERSION_17
        javaPluginExtension.withJavadocJar();
        javaPluginExtension.withSourcesJar();

        final TaskCollection<JavaCompile> javaCompileTasks = project.tasks.withType(JavaCompile)
        javaCompileTasks.configureEach { JavaCompile javaCompileTask ->
            final CompileOptions options = javaCompileTask.options
            options.compilerArgs.addAll('-Xlint:deprecation', '-Xlint:unchecked')
        }

        final TaskCollection<Javadoc> javadocTasks = project.tasks.withType(Javadoc)
        javadocTasks.configureEach { Javadoc javadocTask -> (javadocTask.options as StandardJavadocDocletOptions)
            .addBooleanOption('html5', true)
        }

        project.repositories.mavenCentral()
        project.repositories.maven({ mavenRepo ->
            mavenRepo.name = 'GitHubPackages'
            mavenRepo.url = 'https://maven.pkg.github.com/jtokarski/defendev'
            mavenRepo.allowInsecureProtocol = false
            mavenRepo.credentials({ PasswordCredentials credentials ->
                credentials.username = project.property('gitHubPackagesReadUsername')
                credentials.password = project.property('gitHubPackagesReadPassword')
            } as Action<PasswordCredentials>)
        } as Action<MavenArtifactRepository>)

        /*
         * The below is meant to be type-safe (type-explicit) equivalent of
         *
         *   project.test {
         *       useJUnitPlatform()
         *   }
         *
         * todo: What's the difference between .configureEach() and .all()
         *
         */
        final TaskCollection<Test> testTasks = project.tasks.withType(Test)
        testTasks.configureEach { Test testTask -> testTask.useJUnitPlatform() }

        project.dependencies.add(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME,
            'org.junit.jupiter:junit-jupiter:5.9.1')
        // See: https://docs.gradle.org/8.3/userguide/upgrading_version_8.html#test_framework_implementation_dependencies
        project.dependencies.add(JavaPlugin.TEST_RUNTIME_ONLY_CONFIGURATION_NAME,
            'org.junit.platform:junit-platform-launcher')

        project.dependencies.add(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME,
            'org.assertj:assertj-core:3.23.1')

        final Configuration compileOnlyConfig = project.configurations
            .getByName(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME)
        final Configuration testImplementationConfig = project.configurations
            .getByName(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME)
        // See: https://stackoverflow.com/a/61784124/509276
        testImplementationConfig.extendsFrom(compileOnlyConfig)


        project.getPluginManager().apply(MavenPublishPlugin)
        final PublishingExtension publishingExtension = project.getExtensions().getByType(PublishingExtension)

        final MavenPublication mavenPublication = publishingExtension.publications
            .create('defendevLibJarMvn', MavenPublication)

        final SoftwareComponent javaComponent = project.components.getByName('java')
        mavenPublication.from(javaComponent)

        final MavenPom pom = mavenPublication.getPom()
        pom.name = extension.pomName
        pom.description = extension.pomDescription
        pom.url = 'https://github.com/jtokarski/defendev'
        pom.licenses({ MavenPomLicenseSpec licenseSpec ->
            licenseSpec.license({ MavenPomLicense license ->
                license.name = 'The Apache License, Version 2.0'
                license.url = 'http://www.apache.org/licenses/LICENSE-2.0.txt'
            } as Action<MavenPomLicense>)
        } as Action<MavenPomLicenseSpec>)
        pom.developers({ MavenPomDeveloperSpec developerSpec ->
            developerSpec.developer({ MavenPomDeveloper developer ->
                developer.id = 'jk_tokarski'
                developer.name = 'Jozef Tokarski'
                developer.email = 'jozef.tokarski@gmail.com'
            } as Action<MavenPomDeveloper>)
        } as Action<MavenPomDeveloperSpec>)
        pom.scm({ MavenPomScm scm ->
            scm.connection = 'scm:git:git://github.com/jtokarski/defendev.git'
            scm.developerConnection = 'scm:git:ssh://github.com:jtokarski/defendev.git'
            scm.url = 'http://github.com/jtokarski/defendev/tree/master'
        } as Action<MavenPomScm>)

        publishingExtension.repositories.maven({ MavenArtifactRepository mavenRepo ->
            mavenRepo.name = 'sonatypeOssrh'
            if (project.version.endsWith('-SNAPSHOT')) {
                mavenRepo.url = 'https://s01.oss.sonatype.org/content/repositories/snapshots/'
            } else {
                mavenRepo.url = 'https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/'
            }
            mavenRepo.allowInsecureProtocol = false;
            mavenRepo.credentials({ PasswordCredentials credentials ->
                credentials.username = project.property('ossrhUsername')
                credentials.password = project.property('ossrhPassword')
            } as Action<PasswordCredentials>)
        } as Action<MavenArtifactRepository>);

        publishingExtension.repositories.maven({ MavenArtifactRepository mavenRepo ->
            mavenRepo.name = 'GitHubPackages'
            mavenRepo.url = 'https://maven.pkg.github.com/jtokarski/defendev'
            mavenRepo.allowInsecureProtocol = false;
            mavenRepo.credentials({ PasswordCredentials credentials ->
                credentials.username = project.property('gitHubPackagesUsername')
                credentials.password = project.property('gitHubPackagesPassword')
            } as Action<PasswordCredentials>)
        } as Action<MavenArtifactRepository>);

        project.getPluginManager().apply(SigningPlugin)
        final SigningExtension signingExtension = project.getExtensions().getByType(SigningExtension)
        signingExtension.useGpgCmd()
        final Publication defendevLibJarMvn = publishingExtension.publications.getByName('defendevLibJarMvn')
        signingExtension.sign(defendevLibJarMvn)
    }

}
