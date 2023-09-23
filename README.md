# <p align="center"> Sweety - Dessert App 🍰 </p>

![Sweety](https://github.com/TugceAras/Sweety/assets/79931228/88a2c30e-a598-45b1-8ea5-c2149676f5c0)


<!-- Screenshots -->
## 📸 Screenshots
<p align="center">
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/92b384e8-58ec-4825-b771-fc811ac6ce5e"/>
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/8d905e32-2174-4de8-a0f7-d305f39b0180"/> 
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/42027d44-b13f-4717-9e5c-fef96976d408"/> 
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/5eec328c-2ea2-4c95-8477-0a177accc7f0"/> <br>
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/42088cce-1a4f-47dc-8357-5e684a59a22b"/>
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/a27b72c1-f06b-457f-89ff-336db57a66f2"/>
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/c258a788-39eb-48c4-ac2f-2e1753dba2cc"/>
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/5b9355ed-a423-4a27-83a3-b640c2bb56c6"/> <br>
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/52ccd3e8-fd7b-4567-8bdc-649b727eeece"/>
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/134e32f4-9acb-4be9-ac83-b78d764942f8"/>
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/e05c08ed-3b81-4906-ae89-2c0d8b611bd5"/>
  <img src="https://github.com/TugceAras/Sweety/assets/79931228/b6861320-dc51-4280-ad02-c5ad86d6be51"/>
</p>

<br>

## 📽 Video
- https://drive.google.com/file/d/1LzEyyBFaktMYflawpot98a8OiQZK_yxt/view?usp=sharing

## ✨ Presentation
- [Sweety.pptx](https://github.com/TugceAras/Sweety/files/12705142/Sweety.pptx)

<!-- Technologies -->
## :point_down: Structures Used
- MVVM
- Firebase (Auth)
- Navigation
- Dependency Injection | Hilt
- Coroutines
- Retrofit
- Room Db
- View Binding 
- Chucker
- Glide
- SDP/SSP Library

For animation : Lottie used
<br> 
For icon (Favorite and Cart Screen) : Flat Icon 

## :pencil2: Dependency
```
    dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // SSP-SDP
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    implementation("com.intuit.sdp:sdp-android:1.1.0")

    // Navigation
    val navVersion = "2.7.2"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Lottie
    implementation("com.airbnb.android:lottie:5.2.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // GLide
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    ksp("com.google.dagger:hilt-compiler:2.47")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-auth-ktx")

    // Chucker
    implementation("com.github.chuckerteam.chucker:library:4.0.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
}
```

app build.gradle:

```
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
}

buildFeatures {
      viewBinding = true
 }
```
project build.gradle:

```
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("androidx.navigation.safeargs") version "2.7.2" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}
```

