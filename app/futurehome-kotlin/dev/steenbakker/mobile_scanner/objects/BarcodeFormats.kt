package dev.steenbakker.mobile_scanner.objects

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class BarcodeFormats(intValue: Int) {
   ALL_FORMATS(0),
   AZTEC(4096),
   CODABAR(8),
   CODE_128(1),
   CODE_39(2),
   CODE_93(4),
   DATA_MATRIX(16),
   EAN_13(32),
   EAN_8(64),
   ITF(128),
   PDF417(2048),
   QR_CODE(256),
   UNKNOWN(-1),
   UPC_A(512),
   UPC_E(1024)
   public final val intValue: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private BarcodeFormats[] $VALUES;
   @JvmStatic
   public BarcodeFormats.Companion Companion = new BarcodeFormats.Companion(null);

   @JvmStatic
   fun {
      val var0: Array<BarcodeFormats> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.intValue = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<BarcodeFormats> {
      return $ENTRIES;
   }

   public companion object {
      public fun fromRawValue(rawValue: Int): BarcodeFormats {
         var var2: BarcodeFormats;
         if (var1 != -1) {
            if (var1 != 0) {
               if (var1 != 1) {
                  if (var1 != 2) {
                     switch (var1) {
                        case 4:
                           var2 = BarcodeFormats.CODE_93;
                           break;
                        case 8:
                           var2 = BarcodeFormats.CODABAR;
                           break;
                        case 16:
                           var2 = BarcodeFormats.DATA_MATRIX;
                           break;
                        case 32:
                           var2 = BarcodeFormats.EAN_13;
                           break;
                        case 64:
                           var2 = BarcodeFormats.EAN_8;
                           break;
                        case 128:
                           var2 = BarcodeFormats.ITF;
                           break;
                        case 256:
                           var2 = BarcodeFormats.QR_CODE;
                           break;
                        case 512:
                           var2 = BarcodeFormats.UPC_A;
                           break;
                        case 1024:
                           var2 = BarcodeFormats.UPC_E;
                           break;
                        case 2048:
                           var2 = BarcodeFormats.PDF417;
                           break;
                        case 4096:
                           var2 = BarcodeFormats.AZTEC;
                           break;
                        default:
                           var2 = BarcodeFormats.UNKNOWN;
                     }
                  } else {
                     var2 = BarcodeFormats.CODE_39;
                  }
               } else {
                  var2 = BarcodeFormats.CODE_128;
               }
            } else {
               var2 = BarcodeFormats.ALL_FORMATS;
            }
         } else {
            var2 = BarcodeFormats.UNKNOWN;
         }

         return var2;
      }
   }
}
