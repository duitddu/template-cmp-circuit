import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import java.util.regex.Pattern

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlin.parcelize)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.navigation.compose)
            implementation(libs.compose.adaptive)
            implementation(libs.kotlinx.coroutines)
            implementation(libs.coil.network.ktor)
            implementation(libs.bundles.ktor)
            implementation(libs.coil.compose)
            implementation(libs.koin.core)
            api(libs.koin.annotations)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.okio)
            implementation(libs.multiplatformSettings.no.arg)
            implementation(libs.bundles.circuit)
        }

        commonTest.dependencies {
            implementation(kotlin("test-annotations-common"))
            implementation(libs.kotlin.test)
            implementation(libs.multiplatformSettings.test)
            @OptIn(ExperimentalComposeLibrary::class) implementation(compose.uiTest)
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.koin.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
        }

        androidUnitTest.dependencies {
            implementation(libs.mockk)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
        }

        dependencies {
            ksp(libs.androidx.room.compiler)
        }

        room {
            schemaDirectory("$projectDir/schemas")
        }

        sourceSets.named("commonMain").configure {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
    }
}

kotlin {
    targets.configureEach {
        val isAndroidTarget = platformType == KotlinPlatformType.androidJvm
        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    if (isAndroidTarget) {
                        freeCompilerArgs.addAll(
                            "-P",
                            "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.circuit.cmp.Parcelize",
                        )
                    }
                }
            }
        }
    }
}

android {
    namespace = "com.circuit.cmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.circuit.cmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    flavorDimensions.add("variant")

    productFlavors {
        create("dev") {
            dimension = "variant"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "_dev"
        }

        create("prod") {
            isDefault = true
            dimension = "variant"
        }
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
    arg("KOIN_DEFAULT_MODULE", "false")
}

dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    add("kspAndroid", libs.koin.ksp.compiler)
    add("kspIosX64", libs.koin.ksp.compiler)
    add("kspIosArm64", libs.koin.ksp.compiler)
    add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
    debugImplementation(compose.uiTooling)
}

project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
    if(name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

// Ref: https://sujanpoudel.me/blogs/managing-configurations-for-different-environments-in-kmp/
project.extra.set("buildkonfig.flavor", currentBuildVariant())
buildkonfig {
    packageName = "com.circuit.cmp"
    objectName = "CircuitCMPConfig"
    exposeObjectWithName = "CircuitCMPConfig"

    defaultConfigs {

    }

    defaultConfigs("dev") {

    }

    defaultConfigs("prod") {

    }
}

fun Project.getAndroidBuildVariantOrNull(): String? {
    val variants = setOf("dev", "prod")
    val taskRequestsStr = gradle.startParameter.taskRequests.toString()
    val pattern: Pattern = if (taskRequestsStr.contains("assemble")) {
        Pattern.compile("assemble(\\w+)(Release|Debug)")
    } else {
        Pattern.compile("bundle(\\w+)(Release|Debug)")
    }

    val matcher = pattern.matcher(taskRequestsStr)
    val variant = if (matcher.find()) matcher.group(1) else null
    return if (variant in variants) {
        variant
    } else {
        null
    }
}

private fun Project.currentBuildVariant(): String {
    val variants = setOf("dev", "prod")
    return getAndroidBuildVariantOrNull() ?: System.getenv()["VARIANT"].toString()
        .takeIf { it in variants } ?: "dev"
}