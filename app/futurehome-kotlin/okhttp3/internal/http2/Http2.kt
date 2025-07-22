package okhttp3.internal.http2

import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okio.ByteString

public object Http2 {
   private final val BINARY: Array<String>
   public final val CONNECTION_PREFACE: ByteString = ByteString.Companion.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n")
   private final val FLAGS: Array<String?>
   public const val FLAG_ACK: Int = 1
   public const val FLAG_COMPRESSED: Int = 32
   public const val FLAG_END_HEADERS: Int = 4
   public const val FLAG_END_PUSH_PROMISE: Int = 4
   public const val FLAG_END_STREAM: Int = 1
   public const val FLAG_NONE: Int = 0
   public const val FLAG_PADDED: Int = 8
   public const val FLAG_PRIORITY: Int = 32
   private final val FRAME_NAMES: Array<String>
   public const val INITIAL_MAX_FRAME_SIZE: Int = 16384
   public const val TYPE_CONTINUATION: Int = 9
   public const val TYPE_DATA: Int = 0
   public const val TYPE_GOAWAY: Int = 7
   public const val TYPE_HEADERS: Int = 1
   public const val TYPE_PING: Int = 6
   public const val TYPE_PRIORITY: Int = 2
   public const val TYPE_PUSH_PROMISE: Int = 5
   public const val TYPE_RST_STREAM: Int = 3
   public const val TYPE_SETTINGS: Int = 4
   public const val TYPE_WINDOW_UPDATE: Int = 8

   @JvmStatic
   fun {
      val var5: Array<java.lang.String> = new java.lang.String[256];

      for (int var0 = 0; var0 < 256; var0++) {
         val var6: java.lang.String = Integer.toBinaryString(var0);
         Intrinsics.checkExpressionValueIsNotNull(var6, "Integer.toBinaryString(it)");
         var5[var0] = StringsKt.replace$default(Util.format("%8s", var6), ' ', '0', false, 4, null);
      }

      BINARY = var5;
      var var14: Array<java.lang.String> = FLAGS;
      FLAGS[0] = "";
      FLAGS[1] = "END_STREAM";
      val var12: IntArray = new int[]{1};
      FLAGS[8] = "PADDED";
      FLAGS[var12[0] or 8] = Intrinsics.stringPlus(FLAGS[var12[0]], "|PADDED");
      var14[4] = "END_HEADERS";
      var14[32] = "PRIORITY";
      var14[36] = "END_HEADERS|PRIORITY";

      for (int var9 = 0; var9 < 3; var9++) {
         val var3: Int = new int[]{4, 32, 36}[var9];
         val var2: Int = var12[0];
         var14 = FLAGS;
         val var4: Int = var2 or var3;
         var var7: StringBuilder = new StringBuilder();
         var7.append(var14[var2]);
         var7.append("|");
         var7.append(var14[var3]);
         var14[var4] = var7.toString();
         var7 = new StringBuilder();
         var7.append(var14[var2]);
         var7.append("|");
         var7.append(var14[var3]);
         var7.append("|PADDED");
         var14[var4 or 8] = var7.toString();
      }

      val var11: Int = FLAGS.length;

      for (int var10 = 0; var10 < var11; var10++) {
         if (FLAGS[var10] == null) {
            FLAGS[var10] = BINARY[var10];
         }
      }
   }

   public fun formatFlags(type: Int, flags: Int): String {
      if (var2 == 0) {
         return "";
      } else {
         if (var1 != 2 && var1 != 3) {
            if (var1 == 4 || var1 == 6) {
               val var7: java.lang.String;
               if (var2 == 1) {
                  var7 = "ACK";
               } else {
                  var7 = BINARY[var2];
               }

               return var7;
            }

            if (var1 != 7 && var1 != 8) {
               var var5: java.lang.String;
               if (var2 < FLAGS.length) {
                  val var4: java.lang.String = FLAGS[var2];
                  var5 = FLAGS[var2];
                  if (var4 == null) {
                     Intrinsics.throwNpe();
                     var5 = var4;
                  }
               } else {
                  var5 = BINARY[var2];
               }

               if (var1 == 5 && (var2 and 4) != 0) {
                  var5 = StringsKt.replace$default(var5, "HEADERS", "PUSH_PROMISE", false, 4, null);
               } else {
                  var5 = var5;
                  if (var1 == 0) {
                     var5 = var5;
                     if ((var2 and 32) != 0) {
                        var5 = StringsKt.replace$default(var5, "PRIORITY", "COMPRESSED", false, 4, null);
                     }
                  }
               }

               return var5;
            }
         }

         return BINARY[var2];
      }
   }

   internal fun formattedType(type: Int): String {
      val var3: java.lang.String;
      if (var1 < FRAME_NAMES.length) {
         var3 = FRAME_NAMES[var1];
      } else {
         var3 = Util.format("0x%02x", var1);
      }

      return var3;
   }

   public fun frameLog(inbound: Boolean, streamId: Int, length: Int, type: Int, flags: Int): String {
      val var7: java.lang.String = this.formattedType$okhttp(var4);
      val var8: java.lang.String = this.formatFlags(var4, var5);
      val var6: java.lang.String;
      if (var1) {
         var6 = "<<";
      } else {
         var6 = ">>";
      }

      return Util.format("%s 0x%08x %5d %-13s %s", var6, var2, var3, var7, var8);
   }
}
