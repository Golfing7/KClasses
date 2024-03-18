plugins {
    id("java")
}

group = "com.golfing8"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    compileOnly("net.techcable.tacospigot:WineSpigot:1.8.8-R0.2-SNAPSHOT")
    compileOnly(group = "com.golfing8", name = "KCommon", version = "1.0").isChanging = true
}

val deployDirectory = "C:\\Users\\Andrew\\Documents\\Server-1.20.2\\plugins"
tasks.create("deploy") {
    dependsOn(tasks.jar)

    doFirst {
        val outputFile = tasks.getByName("jar").outputs.files.first()
        val targetFile = File(deployDirectory, "${project.name}-${project.version}.jar")

        outputFile.copyTo(targetFile, overwrite = true)
    }
}

tasks.test {
    useJUnitPlatform()
}