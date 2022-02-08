plugins {
	kotlin(KotlinPlugins.multiplatform)
	kotlin(KotlinPlugins.cocoapods)
	kotlin(KotlinPlugins.serialization) version Kotlin.version
	id(Plugins.androidLibrary)
	id(Plugins.sqlDelight)
}

version = "1.0"

kotlin {
	android()
	iosX64()
	iosArm64()
	iosSimulatorArm64() // sure all ios dependencies support this target

	cocoapods {
		summary = "Some description for the Shared Module"
		homepage = "Link to the Shared Module homepage"
		ios.deploymentTarget = "14.1"
		podfile = project.file("../iosApp/Podfile")
		framework {
			baseName = "shared"
		}
	}

	sourceSets {
		val commonMain by getting {
			dependencies {
				implementation(Ktor.core)
				implementation(Ktor.clientSerialization)
				implementation(Ktor.contentNegotiation)
				implementation(Ktor.serialization)
				implementation(Ktor.logging)
				implementation(Kotlinx.datetime)
				implementation(Kotlinx.nativeMultithreadedCoroutines) {
					version {
						strictly(Kotlinx.nativeMultithreadedCoroutinesVersion)
					}
				}
				implementation(SQLDelight.runtime)
			}
		}
		val commonTest by getting {
			dependencies {
				implementation(kotlin("test-common"))
				implementation(kotlin("test-annotations-common"))
			}
		}
		val androidMain by getting {
			dependencies {
				implementation(Ktor.android)
				implementation(SQLDelight.androidDriver)
			}
		}
		val androidTest by getting {
			dependencies {
				implementation(kotlin("test-junit"))
				implementation("junit:junit:4.13.2")
			}
		}
		val iosX64Main by getting
		val iosArm64Main by getting
		val iosSimulatorArm64Main by getting
		val iosMain by creating {
			dependencies {
				implementation(Ktor.ios)
				implementation(SQLDelight.nativeDriver)
			}
			dependsOn(commonMain)
			iosX64Main.dependsOn(this)
			iosArm64Main.dependsOn(this)
			iosSimulatorArm64Main.dependsOn(this)
		}
		val iosX64Test by getting
		val iosArm64Test by getting
		val iosSimulatorArm64Test by getting
		val iosTest by creating {
			dependsOn(commonTest)
			iosX64Test.dependsOn(this)
			iosArm64Test.dependsOn(this)
			iosSimulatorArm64Test.dependsOn(this)
		}
	}
}

android {
	compileSdk = Application.compileSdk
	sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
	defaultConfig {
		minSdk = Application.minSdk
		targetSdk = Application.targetSdk
	}
	buildTypes {
		getByName("release") {
			signingConfig = signingConfigs.getByName("debug")
		}
	}
}

sqldelight {
	database("TasksDatabase") {
		packageName = "com.nablet.mytasks.datasource.cache"
		sourceFolders = listOf("sqldelight")
	}
}
