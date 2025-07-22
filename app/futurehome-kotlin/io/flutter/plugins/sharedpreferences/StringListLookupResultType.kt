package io.flutter.plugins.sharedpreferences

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class StringListLookupResultType(raw: Int) {
   JSON_ENCODED(1),
   PLATFORM_ENCODED(0),
   UNEXPECTED_STRING(2)
   public final val raw: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private StringListLookupResultType[] $VALUES;
   @JvmStatic
   public StringListLookupResultType.Companion Companion = new StringListLookupResultType.Companion(null);

   @JvmStatic
   fun {
      val var0: Array<StringListLookupResultType> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.raw = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<StringListLookupResultType> {
      return $ENTRIES;
   }

   public companion object {
      public fun ofRaw(raw: Int): StringListLookupResultType? {
         val var5: Array<StringListLookupResultType> = StringListLookupResultType.values();
         val var3: Int = var5.length;
         var var2: Int = 0;

         var var4: StringListLookupResultType;
         while (true) {
            if (var2 >= var3) {
               var4 = null;
               break;
            }

            var4 = var5[var2];
            if (var5[var2].getRaw() == var1) {
               break;
            }

            var2++;
         }

         return var4;
      }
   }
}
