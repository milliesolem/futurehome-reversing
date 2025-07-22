package io.flutter.plugins.webviewflutter

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class FileChooserMode(raw: Int) {
   OPEN(0),
   OPEN_MULTIPLE(1),
   SAVE(2),
   UNKNOWN(3)
   public final val raw: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private FileChooserMode[] $VALUES;
   @JvmStatic
   public FileChooserMode.Companion Companion = new FileChooserMode.Companion(null);

   @JvmStatic
   fun {
      val var0: Array<FileChooserMode> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.raw = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<FileChooserMode> {
      return $ENTRIES;
   }

   public companion object {
      public fun ofRaw(raw: Int): FileChooserMode? {
         val var5: Array<FileChooserMode> = FileChooserMode.values();
         val var3: Int = var5.length;
         var var2: Int = 0;

         var var4: FileChooserMode;
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
