# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep Retrofit classes and members
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keep,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-keep class com.google.gson.** { *; }

-keepattributes *Annotation*

# Keep custom Parcelable implementations (if applicable)
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep methods for Android lifecycle callbacks (if applicable)
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# Keep methods for custom Application classes (if applicable)
-keepclassmembers class * extends android.app.Application {
    public void onCreate();
}