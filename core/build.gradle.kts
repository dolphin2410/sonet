repositories {
    mavenCentral()
}
dependencies {
    testImplementation(platform("org.junit:junit-bom:5.7.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.jetbrains:annotations:20.1.0")
    compileOnly(project(":api"))
    compileOnly("io.github.teamcheeze:jaw:1.0.2")
    shade("io.github.teamcheeze:jaw:1.0.2")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}