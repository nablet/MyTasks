plugins {
	id(Plugins.androidApplication)
	kotlin(KotlinPlugins.android)
	kotlin(KotlinPlugins.kapt)
	id(Plugins.hilt)
	kotlin(KotlinPlugins.serialization) version Kotlin.version
}

android {
	compileSdk = 32
	defaultConfig {
		applicationId = Application.appId
		minSdk = Application.minSdk
		targetSdk = Application.targetSdk
		versionCode = Application.versionCode
		versionName = Application.versionName
	}
	signingConfigs {
		create("release") {
			val tempFilePath = System.getProperty("user.home") + "/work/_temp/keystore"
			println("tempFilePath = $tempFilePath")
			val allFilesFromDir = File(tempFilePath).listFiles()
			println("allFilesFromDir = $allFilesFromDir")
			if (allFilesFromDir != null) {
				val keystoreFile = allFilesFromDir.first()
				keystoreFile.renameTo(file("$tempFilePath/MyTasks.jks"))
			}
			storeFile = file("$tempFilePath/MyTasks.jks")
			storePassword = System.getenv("SIGNING_STORE_PASSWORD")
			keyAlias = System.getenv("SIGNING_KEY_ALIAS")
			keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
		}
	}
	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			signingConfig = signingConfigs.getByName("release")
		}
	}
	buildFeatures {
		compose = true
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	composeOptions {
		kotlinCompilerExtensionVersion = Compose.composeVersion
	}
}

dependencies {
	implementation(project(":shared"))
	
	implementation(Accompanist.coil)
	
	implementation(AndroidX.appCompat)
	implementation(AndroidX.fragmentKtx)
	
	implementation(Compose.runtime)
	implementation(Compose.runtimeLiveData)
	implementation(Compose.ui)
	implementation(Compose.material)
	implementation(Compose.uiTooling)
	implementation(Compose.foundation)
	implementation(Compose.compiler)
	implementation(Compose.constraintLayout)
	implementation(Compose.activity)
	implementation(Compose.navigation)
	
	implementation(Google.material)
	
	implementation(Hilt.hiltAndroid)
	implementation(Hilt.hiltNavigation)
	kapt(Hilt.hiltCompiler)
	
	implementation(Kotlinx.datetime)
	
	implementation(Ktor.android)
	
	debugImplementation(SquareUp.leakCanary)
}

// HAHAHAxd
