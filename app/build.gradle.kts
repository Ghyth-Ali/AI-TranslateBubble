import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
        id("com.android.application")
            id("org.jetbrains.kotlin.android")
                id("org.jetbrains.kotlin.plugin.compose")
                    id("com.google.devtools.ksp")
}

android {
        namespace = "com.aitranslatebubble"
            compileSdk = 37

                defaultConfig {
                            applicationId = "com.aitranslatebubble"
                                    minSdk = 26
                                            targetSdk = 37
                                                    versionCode = 1
                                                            versionName = "1.0"

                                                                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                    buildTypes {
                                release {
                                                isMinifyEnabled = false
                                                            proguardFiles(
                                                                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                                                                                "proguard-rules.pro"
                                                            )
                                }
                    }

                        compileOptions {
                                    sourceCompatibility = JavaVersion.VERSION_17
                                            targetCompatibility = JavaVersion.VERSION_17
                        }

                            buildFeatures {
                                        compose = true
                            }
}

// Kotlin 2.2+ removed the old `android { kotlinOptions { ... } }` block.
// JVM target now lives in its own top-level `kotlin {}` extension.
kotlin {
        compilerOptions {
                    jvmTarget = JvmTarget.JVM_17
        }
}

dependencies {
        // --- Jetpack Compose (individual library versions are managed by the BOM) ---
            val composeBom = platform("androidx.compose:compose-bom:2026.08.00")
                implementation(composeBom)
                    androidTestImplementation(composeBom)

                        implementation("androidx.compose.ui:ui")
                            implementation("androidx.compose.ui:ui-graphics")
                                implementation("androidx.compose.ui:ui-tooling-preview")
                                    implementation("androidx.compose.material3:material3")
                                        implementation("androidx.activity:activity-compose:1.13.0")
                                            implementation("androidx.core:core-ktx:1.17.0")
                                                debugImplementation("androidx.compose.ui:ui-tooling")

                                                    // --- Room database (KSP annotation processing, not kapt) ---
                                                        val roomVersion = "2.8.4"
                                                            implementation("androidx.room:room-runtime:$roomVersion")
                                                                implementation("androidx.room:room-ktx:$roomVersion")
                                                                    ksp("androidx.room:room-compiler:$roomVersion")

                                                                        // --- Coroutines ---
                                                                            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
                                                                                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.11.0")

                                                                                    // --- ML Kit Text Recognition ---
                                                                                        // Bundled/on-device Latin-script model (statically linked, ~4MB, works offline immediately).
                                                                                            implementation("com.google.mlkit:text-recognition:16.0.1")
                                                                                                // Need other scripts too? Add e.g. "com.google.mlkit:text-recognition-chinese:16.0.1"

                                                                                                    // --- Testing ---
                                                                                                        testImplementation("junit:junit:4.13.2")
                                                                                                            androidTestImplementation("androidx.test.ext:junit:1.2.1")
                                                                                                                androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
}
        }
}
                            }
                        }
                                                            )
                                }
                    }
                }
}
}