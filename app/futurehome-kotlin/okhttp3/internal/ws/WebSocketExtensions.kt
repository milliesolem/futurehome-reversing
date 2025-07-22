package okhttp3.internal.ws

import kotlin.jvm.internal.Intrinsics
import okhttp3.Headers
import okhttp3.internal.Util

public data class WebSocketExtensions(perMessageDeflate: Boolean = false,
   clientMaxWindowBits: Int? = null as Int,
   clientNoContextTakeover: Boolean = false,
   serverMaxWindowBits: Int? = null as Int,
   serverNoContextTakeover: Boolean = false,
   unknownValues: Boolean = false
) {
   public final val clientMaxWindowBits: Int?
   public final val clientNoContextTakeover: Boolean
   public final val perMessageDeflate: Boolean
   public final val serverMaxWindowBits: Int?
   public final val serverNoContextTakeover: Boolean
   public final val unknownValues: Boolean

   fun WebSocketExtensions() {
      this(false, null, false, null, false, false, 63, null);
   }

   init {
      this.perMessageDeflate = var1;
      this.clientMaxWindowBits = var2;
      this.clientNoContextTakeover = var3;
      this.serverMaxWindowBits = var4;
      this.serverNoContextTakeover = var5;
      this.unknownValues = var6;
   }

   public operator fun component1(): Boolean {
      return this.perMessageDeflate;
   }

   public operator fun component2(): Int? {
      return this.clientMaxWindowBits;
   }

   public operator fun component3(): Boolean {
      return this.clientNoContextTakeover;
   }

   public operator fun component4(): Int? {
      return this.serverMaxWindowBits;
   }

   public operator fun component5(): Boolean {
      return this.serverNoContextTakeover;
   }

   public operator fun component6(): Boolean {
      return this.unknownValues;
   }

   public fun copy(
      perMessageDeflate: Boolean = var0.perMessageDeflate,
      clientMaxWindowBits: Int? = var0.clientMaxWindowBits,
      clientNoContextTakeover: Boolean = var0.clientNoContextTakeover,
      serverMaxWindowBits: Int? = var0.serverMaxWindowBits,
      serverNoContextTakeover: Boolean = var0.serverNoContextTakeover,
      unknownValues: Boolean = var0.unknownValues
   ): WebSocketExtensions {
      return new WebSocketExtensions(var1, var2, var3, var4, var5, var6);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this != var1) {
         if (var1 !is WebSocketExtensions) {
            return false;
         }

         if (this.perMessageDeflate != (var1 as WebSocketExtensions).perMessageDeflate
            || !(this.clientMaxWindowBits == (var1 as WebSocketExtensions).clientMaxWindowBits)
            || this.clientNoContextTakeover != (var1 as WebSocketExtensions).clientNoContextTakeover
            || !(this.serverMaxWindowBits == (var1 as WebSocketExtensions).serverMaxWindowBits)
            || this.serverNoContextTakeover != (var1 as WebSocketExtensions).serverNoContextTakeover
            || this.unknownValues != (var1 as WebSocketExtensions).unknownValues) {
            return false;
         }
      }

      return true;
   }

   public override fun hashCode(): Int {
      var var6: Byte = 1;
      var var1: Byte = this.perMessageDeflate;
      if (this.perMessageDeflate != 0) {
         var1 = 1;
      }

      var var4: Int = 0;
      val var9: Int;
      if (this.clientMaxWindowBits != null) {
         var9 = this.clientMaxWindowBits.hashCode();
      } else {
         var9 = 0;
      }

      var var3: Byte = this.clientNoContextTakeover;
      if (this.clientNoContextTakeover != 0) {
         var3 = 1;
      }

      if (this.serverMaxWindowBits != null) {
         var4 = this.serverMaxWindowBits.hashCode();
      }

      var var10: Byte = this.serverNoContextTakeover;
      if (this.serverNoContextTakeover != 0) {
         var10 = 1;
      }

      if (this.unknownValues == 0) {
         var6 = this.unknownValues;
      }

      return ((((var1 * 31 + var9) * 31 + var3) * 31 + var4) * 31 + var10) * 31 + var6;
   }

   public fun noContextTakeover(clientOriginated: Boolean): Boolean {
      return if (var1) this.clientNoContextTakeover else this.serverNoContextTakeover;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("WebSocketExtensions(perMessageDeflate=");
      var1.append(this.perMessageDeflate);
      var1.append(", clientMaxWindowBits=");
      var1.append(this.clientMaxWindowBits);
      var1.append(", clientNoContextTakeover=");
      var1.append(this.clientNoContextTakeover);
      var1.append(", serverMaxWindowBits=");
      var1.append(this.serverMaxWindowBits);
      var1.append(", serverNoContextTakeover=");
      var1.append(this.serverNoContextTakeover);
      var1.append(", unknownValues=");
      var1.append(this.unknownValues);
      var1.append(")");
      return var1.toString();
   }

   public companion object {
      private const val HEADER_WEB_SOCKET_EXTENSION: String

      @Throws(java/io/IOException::class)
      public fun parse(responseHeaders: Headers): WebSocketExtensions {
         Intrinsics.checkParameterIsNotNull(var1, "responseHeaders");
         var var16: Int = null as Int;
         val var5: Int = var1.size();
         var var19: Int = null;
         var var18: Int = null;
         var var3: Int = 0;
         var var13: Boolean = false;
         var var9: Boolean = false;
         var var10: Boolean = false;
         var var8: Boolean = false;

         while (var3 < var5) {
            var var11: Boolean;
            var var12: Boolean;
            val var14: Boolean;
            val var15: Boolean;
            var var17: Int;
            if (!StringsKt.equals(var1.name(var3), "Sec-WebSocket-Extensions", true)) {
               var12 = var13;
               var16 = var19;
               var15 = var9;
               var17 = var18;
               var11 = var10;
               var14 = var8;
            } else {
               val var21: java.lang.String = var1.value(var3);
               var var2: Int = 0;

               while (true) {
                  var12 = var13;
                  var16 = var19;
                  var15 = var9;
                  var17 = var18;
                  var11 = var10;
                  var14 = var8;
                  if (var2 >= var21.length()) {
                     break;
                  }

                  val var6: Int = Util.delimiterOffset$default(var21, ',', var2, 0, 4, null);
                  var var4: Int = Util.delimiterOffset(var21, ';', var2, var6);
                  val var30: java.lang.String = Util.trimSubstring(var21, var2, var4);
                  var4++;
                  if (!StringsKt.equals(var30, "permessage-deflate", true)) {
                     var2 = var4;
                     var8 = true;
                     var16 = var18;
                     var17 = var19;
                  } else {
                     var2 = var4;
                     var17 = var19;
                     var12 = var9;
                     var16 = var18;
                     var11 = var10;
                     if (var13) {
                        var8 = true;
                        var11 = var10;
                        var16 = var18;
                        var12 = var9;
                        var17 = var19;
                        var2 = var4;
                     }

                     while (var2 < var6) {
                        var4 = Util.delimiterOffset(var21, ';', var2, var6);
                        val var7: Int = Util.delimiterOffset(var21, '=', var2, var4);
                        val var22: java.lang.String = Util.trimSubstring(var21, var2, var7);
                        val var20: java.lang.String;
                        if (var7 < var4) {
                           var20 = StringsKt.removeSurrounding(Util.trimSubstring(var21, var7 + 1, var4), "\"");
                        } else {
                           var20 = null;
                        }

                        label93: {
                           if (StringsKt.equals(var22, "client_max_window_bits", true)) {
                              if (var17 != null) {
                                 var8 = true;
                              }

                              if (var20 != null) {
                                 var17 = StringsKt.toIntOrNull(var20);
                              } else {
                                 var17 = null;
                              }

                              var18 = var17;
                              var9 = var12;
                              var19 = var16;
                              var10 = var11;
                              if (var17 != null) {
                                 break label93;
                              }

                              var18 = var17;
                              var19 = var16;
                           } else {
                              if (StringsKt.equals(var22, "client_no_context_takeover", true)) {
                                 if (var12) {
                                    var8 = true;
                                 }

                                 if (var20 != null) {
                                    var8 = true;
                                 }

                                 var9 = true;
                                 var18 = var17;
                                 var19 = var16;
                                 var10 = var11;
                                 break label93;
                              }

                              if (StringsKt.equals(var22, "server_max_window_bits", true)) {
                                 if (var16 != null) {
                                    var8 = true;
                                 }

                                 if (var20 != null) {
                                    var16 = StringsKt.toIntOrNull(var20);
                                 } else {
                                    var16 = null;
                                 }

                                 var18 = var17;
                                 var9 = var12;
                                 var19 = var16;
                                 var10 = var11;
                                 if (var16 != null) {
                                    break label93;
                                 }

                                 var18 = var17;
                                 var19 = var16;
                              } else {
                                 var18 = var17;
                                 var19 = var16;
                                 if (StringsKt.equals(var22, "server_no_context_takeover", true)) {
                                    if (var11) {
                                       var8 = true;
                                    }

                                    if (var20 != null) {
                                       var8 = true;
                                    }

                                    var10 = true;
                                    var18 = var17;
                                    var9 = var12;
                                    var19 = var16;
                                    break label93;
                                 }
                              }
                           }

                           var8 = true;
                           var10 = var11;
                           var9 = var12;
                        }

                        var2 = var4 + 1;
                        var17 = var18;
                        var12 = var9;
                        var16 = var19;
                        var11 = var10;
                     }

                     var13 = true;
                     var9 = var12;
                     var10 = var11;
                  }

                  var19 = var17;
                  var18 = var16;
               }
            }

            var3++;
            var13 = var12;
            var19 = var16;
            var9 = var15;
            var18 = var17;
            var10 = var11;
            var8 = var14;
         }

         return new WebSocketExtensions(var13, var19, var9, var18, var10, var8);
      }
   }
}
