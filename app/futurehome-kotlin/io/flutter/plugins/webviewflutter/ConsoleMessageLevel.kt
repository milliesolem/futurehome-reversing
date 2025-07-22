package io.flutter.plugins.webviewflutter

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class ConsoleMessageLevel(raw: Int) {
   DEBUG(0),
   ERROR(1),
   LOG(2),
   TIP(3),
   UNKNOWN(5),
   WARNING(4)
   public final val raw: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private ConsoleMessageLevel[] $VALUES;
   @JvmStatic
   public ConsoleMessageLevel.Companion Companion = new ConsoleMessageLevel.Companion(null);

   @JvmStatic
   fun {
      val var0: Array<ConsoleMessageLevel> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.raw = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<ConsoleMessageLevel> {
      return $ENTRIES;
   }

   public companion object {
      public fun ofRaw(raw: Int): ConsoleMessageLevel? {
         val var5: Array<ConsoleMessageLevel> = ConsoleMessageLevel.values();
         val var3: Int = var5.length;
         var var2: Int = 0;

         var var4: ConsoleMessageLevel;
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
