## 项目简介

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2. Add the dependency
```
dependencies {
        implementation 'com.github.lvfaqiang:CoreKtx:Tag'
}
```
Tag: [![](https://jitpack.io/v/lvfaqiang/CoreKtx.svg)](https://jitpack.io/#lvfaqiang/CoreKtx)

### Step 3. Add the following configuration

Add it in your app build.gradle at the android block

```
android {
    ...

    packagingOptions {
        exclude 'META-INF/core-ktx_release.kotlin_module'
    }
}
```
### How To Use :
#### 1. setup in your Application :
```
CoreKtxProvider.get()
    .setXXXX
    ...
    .build(context)

```
#### 2. Add it in your app build.gradle.
```
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation "androidx.core:core-ktx:$rootProject.ext.coreKtx.v"
    implementation "androidx.appcompat:appcompat:$rootProject.ext.appcompat.v"
    implementation "com.google.android.material:material:$rootProject.ext.googleMaterial.v"

    implementation "androidx.lifecycle:lifecycle-extensions:$rootProject.ext.lifecycle.lifecycleExt"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$rootProject.ext.lifecycle.liveDataKtx"

    implementation "androidx.navigation:navigation-fragment:$rootProject.ext.navigation.v"
    implementation "androidx.navigation:navigation-ui:$rootProject.ext.navigation.v"
    implementation "androidx.navigation:navigation-fragment-ktx:$rootProject.ext.navigation.v"
    implementation "androidx.navigation:navigation-ui-ktx:$rootProject.ext.navigation.v"

    /**
     * https://github.com/Kotlin/kotlinx.coroutines
     * Kotlin 协程
     */
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$rootProject.ext.coroutines.v"

    /**
     * https://github.com/square/retrofit
     * https://github.com/square/retrofit/wiki/Converters
     */
    implementation "com.squareup.retrofit2:retrofit:$rootProject.ext.retrofit.v"
    implementation "com.squareup.retrofit2:converter-gson:$rootProject.ext.retrofit.v"
    implementation "com.squareup.retrofit2:converter-scalars:$rootProject.ext.retrofit.v"

    // local Storage
    implementation "com.orhanobut:hawk:2.0.1"
```