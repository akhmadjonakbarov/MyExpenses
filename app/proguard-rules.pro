# Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class uz.akbarovdev.myexpenses.**$$serializer { *; }
-keepclassmembers class uz.akbarovdev.myexpenses.** {
    *** Companion;
}
-keepclasseswithmembers class uz.akbarovdev.myexpenses.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Koin
-keepclassmembers class * {
    @org.koin.core.annotation.KoinInternalApi *;
}
-keep class org.koin.** { *; }

# Retrofit / OkHttp
-keepattributes Signature
-keepattributes Exceptions
-keep class retrofit2.** { *; }
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn okhttp3.**
-dontwarn okio.**

# Coil
-keep class coil.** { *; }

# Compose
-keep class androidx.compose.** { *; }

# Gson
-keepattributes Signature
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep enum classes
-keepclassmembers enum * { *; }

# Keep custom entities for Room type converters
-keep class uz.akbarovdev.myexpenses.features.dashboard.daos.** { *; }
-keep class uz.akbarovdev.myexpenses.features.debt.daos.** { *; }

# Keep ViewModels (reflection used by Koin)
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Firebase
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**
-keepattributes *Annotation*
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**
