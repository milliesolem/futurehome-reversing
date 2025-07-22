package kotlin.internal.jdk8

import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0
import java.util.regex.MatchResult
import java.util.regex.Matcher
import kotlin.internal.jdk7.JDK7PlatformImplementations
import kotlin.random.Random
import kotlin.random.jdk8.PlatformThreadLocalRandom

internal open class JDK8PlatformImplementations : JDK7PlatformImplementations {
   private fun sdkIsNullOrAtLeast(version: Int): Boolean {
      val var2: Boolean;
      if (JDK8PlatformImplementations.ReflectSdkVersion.sdkVersion != null && JDK8PlatformImplementations.ReflectSdkVersion.sdkVersion < var1) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public override fun defaultPlatformRandom(): Random {
      val var1: Random;
      if (this.sdkIsNullOrAtLeast(34)) {
         var1 = new PlatformThreadLocalRandom();
      } else {
         var1 = super.defaultPlatformRandom();
      }

      return var1;
   }

   public override fun getMatchResultNamedGroup(matchResult: MatchResult, name: String): MatchGroup? {
      val var3: Boolean = var1 is Matcher;
      var var4: MatchGroup = null;
      val var6: Matcher;
      if (var3) {
         var6 = var1 as Matcher;
      } else {
         var6 = null;
      }

      if (var6 != null) {
         val var5: IntRange = new IntRange(
            AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var6, var2), AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(var6, var2) - 1
         );
         if (var5.getStart() >= 0) {
            val var7: java.lang.String = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var6, var2);
            var4 = new MatchGroup(var7, var5);
         }

         return var4;
      } else {
         throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
      }
   }

   private object ReflectSdkVersion {
      public final val sdkVersion: Int?
   }
}
