plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

dependencies {
    implementation("org.ow2.asm:asm:8.0.1")
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        register("extra-java-module-info") {
            id = "extra-java-module-info"
            implementationClass = "org.gradle.sample.transform.javamodules.ExtraModuleInfoPlugin"
        }
    }
}
