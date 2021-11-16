plugins {
    java
    kotlin("jvm") version "1.4.31" apply false
    id("nu.studer.jooq") version "6.0.1" apply false
    `kotlin-dsl`
    application
}

group = "org.xendv.java.edumail"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // compile
    compile("org.jooq:jooq:3.15.4")

    // annotations
    implementation ("org.jetbrains:annotations:13.0")
    implementation("org.projectlombok:lombok:1.18.4")
    annotationProcessor("org.projectlombok:lombok:1.18.4")

    // testing
    testImplementation("org.mockito:mockito-core:2.24.0")

    testImplementation("junit:junit:4.12")

    testImplementation("org.hamcrest:hamcrest-all:1.3")

    // db
    implementation("org.flywaydb:flyway-core:8.0.1")
    implementation("org.postgresql:postgresql:42.2.9")
    // jooq
    implementation("org.jooq:jooq:3.15.4")
    implementation("org.jooq:jooq-codegen:3.15.4")
    implementation("org.jooq:jooq-meta:3.15.4")
    implementation(project(":jooq-generated"))

    // json
    implementation("com.google.code.gson:gson:2.8.8")
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("nu.studer.jooq")
    }

    group = "org.xendv.java.edumail"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
// compile
        compile("org.jooq:jooq:3.15.4")

        // annotations
        implementation ("org.jetbrains:annotations:13.0")
        implementation("org.projectlombok:lombok:1.18.4")
        annotationProcessor("org.projectlombok:lombok:1.18.4")

        // testing
        testImplementation("org.mockito:mockito-core:2.24.0")

        testImplementation("junit:junit:4.12")

        testImplementation("org.hamcrest:hamcrest-all:1.3")

        // db
        implementation("org.flywaydb:flyway-core:8.0.1")
        implementation("org.postgresql:postgresql:42.2.9")
        // jooq
        implementation("org.jooq:jooq:3.15.4")
        implementation("org.jooq:jooq-codegen:3.15.4")
        implementation("org.jooq:jooq-meta:3.15.4")

        // json
        implementation("com.google.code.gson:gson:2.8.8")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"]="db.jooq.MainApplication"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

task<JavaExec>("execute") {
    mainClass.set("db.jooq.MainApplication")
    classpath = java.sourceSets["main"].runtimeClasspath
    standardInput = System.`in`
}