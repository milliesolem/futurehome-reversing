package kotlin

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

@Retention(RetentionPolicy.CLASS)
@Target([ElementType.ANNOTATION_TYPE])
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@kotlin.annotation.Target(allowedTargets = [AnnotationTarget.ANNOTATION_CLASS])
annotation class RequiresOptIn(
   val message: String = "",
   val level: kotlin.RequiresOptIn.Level = RequiresOptIn.Level.ERROR
) {
   public enum class Level {
      ERROR,
      WARNING      @JvmStatic
      private EnumEntries $ENTRIES;
      @JvmStatic
      private RequiresOptIn.Level[] $VALUES;

      @JvmStatic
      fun {
         val var0: Array<RequiresOptIn.Level> = $values();
         $VALUES = var0;
         $ENTRIES = EnumEntriesKt.enumEntries(var0);
      }

      @JvmStatic
      fun getEntries(): EnumEntries<RequiresOptIn.Level> {
         return $ENTRIES;
      }
   }
}
