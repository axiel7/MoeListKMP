# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class * extends androidx.room.RoomDatabase { <init>(); }

# Suppress OkHttp warnings for optional dependencies
-dontwarn okhttp3.internal.graal.**
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
-dontwarn com.oracle.svm.core.annotate.**
-dontwarn org.graalvm.nativeimage.**

# Suppress Ktor/Internal warnings
-dontwarn android.util.Log
-dontwarn io.ktor.utils.io.jvm.javaio.**

# General rule to allow the build to continue despite warnings
# (Use with caution, but usually required for OkHttp in KMP)
#-ignorewarnings

# ktor

# Ignore Ktor's internal logging warnings
-dontwarn io.ktor.client.plugins.logging.LoggerJvmKt

# Most of volatile fields are updated with AtomicFU and should not be mangled/removed
-keepclassmembers class io.ktor.** {
    volatile <fields>;
}

-keepclassmembernames class io.ktor.** {
    volatile <fields>;
}

# client engines are loaded using ServiceLoader so we need to keep them
-keep class io.ktor.client.engine.** implements io.ktor.client.HttpClientEngineContainer

# kotlinx.serialization

-keepclassmembers @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}
#-keep @kotlinx.serialization.Serializable class **
-keepattributes *Annotation*, InnerClasses, Signature, Exceptions, SourceFile, LineNumberTable
-keep class kotlinx.serialization.json.** { *; }
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}

# Keep `Companion` object field of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-keepclassmembers @kotlinx.serialization.Serializable class ** {
static ** Companion;
}

# Keep names for named companion object from obfuscation
# Names of a class and of a field are important in lookup of named companion in runtime
-if @kotlinx.serialization.internal.NamedCompanion class *
-keepclassmembers class * {
    static <1> *;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

# Don't print notes about potential mistakes or omissions in the configuration for kotlinx-serialization classes
# See also https://github.com/Kotlin/kotlinx.serialization/issues/1900
-dontnote kotlinx.serialization.**
# Serialization core uses `java.lang.ClassValue` for caching inside these specified classes.
# If there is no `java.lang.ClassValue` (for example, in Android), then R8/ProGuard will print a warning.
# However, since in this case they will not be used, we can disable these warnings
-dontwarn kotlinx.serialization.internal.ClassValueReferences

# disable optimisation for descriptor field because in some versions of ProGuard, optimization generates incorrect bytecode that causes a verification error
# see https://github.com/Kotlin/kotlinx.serialization/issues/2719
-keepclassmembers public class **$$serializer {
    private ** descriptor;
}

-keep class io.ktor.serialization.kotlinx.KotlinxSerializationExtensionProvider { *; }
-keep class io.ktor.serialization.kotlinx.json.KotlinxSerializationJsonExtensionProvider { *; }

# navigation 3

-keep class androidx.navigation3.** { *; }
-keep interface androidx.navigation3.** { *; }

# Prevent ProGuard from merging or incorrectly optimizing Navigation internal classes
-keepnames class androidx.navigation3.** { *; }
-dontwarn androidx.navigation3.**

# Keep NavigationEvent and SceneInfo types distinct
-keep class androidx.navigation3.scene.SceneInfo { *; }
-keep class androidx.navigationevent.** { *; }

# If the error persists, you may need to disable optimization for these specifically
-keep,allowoptimization class androidx.navigation3.** { *; }

# Proguard Kotlin Example https://github.com/Guardsquare/proguard/blob/master/examples/application-kotlin/proguard.pro

-keepattributes *Annotation*

#-keep class kotlin.Metadata { *; }

# Kotlin

-keep class kotlin.reflect.jvm.internal.** { *; }
-keep class kotlin.text.RegexOption { *; }

# Kotlinx Coroutines https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/resources/META-INF/proguard/coroutines.pro

# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Same story for the standard library's SafeContinuation that also uses AtomicReferenceFieldUpdater
-keepclassmembers class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}

# coil

-keep class coil3.util.DecoderServiceLoaderTarget { *; }
-keep class coil3.util.FetcherServiceLoaderTarget { *; }
-keep class coil3.util.ServiceLoaderComponentRegistry { *; }
-keep class * implements coil3.util.DecoderServiceLoaderTarget { *; }
-keep class * implements coil3.util.FetcherServiceLoaderTarget { *; }

# Prevent ProGuard from stripping out the Unsafe functionality
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.Unsafe

# Common rules for Kotlin Coroutines which often trigger this error
-keepclassmembernames class kotlinx.coroutines.android.HandlerContext {
    private static volatile java.lang.Object _relauncher;
}

# If you use Coroutines Swing (which you have in your dependencies)
-keep class kotlinx.coroutines.swing.** { *; }

# General Compose Desktop requirements
-keepattributes Signature,Annotation,SourceFile,LineNumberTable