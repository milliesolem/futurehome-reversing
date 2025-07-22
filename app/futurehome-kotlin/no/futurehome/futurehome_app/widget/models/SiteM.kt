package no.futurehome.futurehome_app.widget.models

import com.signify.hue.flutterreactiveble.utils.Duration$$ExternalSyntheticBackport0
import java.util.ArrayList
import no.futurehome.futurehome_app.widget.helpers.JsonOptsKt
import no.futurehome.futurehome_app.widget.helpers.Logger
import org.json.JSONArray
import org.json.JSONObject

public data class SiteM(id: String, name: String, address: String, shortcuts: List<ShortcutM>, hasHub: Boolean, hasOnlineHub: Boolean) {
   public final val id: String
   public final val name: String
   public final val address: String
   public final val shortcuts: List<ShortcutM>
   public final val hasHub: Boolean
   public final val hasOnlineHub: Boolean

   init {
      this.id = var1;
      this.name = var2;
      this.address = var3;
      this.shortcuts = var4;
      this.hasHub = var5;
      this.hasOnlineHub = var6;
   }

   public operator fun component1(): String {
      return this.id;
   }

   public operator fun component2(): String {
      return this.name;
   }

   public operator fun component3(): String {
      return this.address;
   }

   public operator fun component4(): List<ShortcutM> {
      return this.shortcuts;
   }

   public operator fun component5(): Boolean {
      return this.hasHub;
   }

   public operator fun component6(): Boolean {
      return this.hasOnlineHub;
   }

   public fun copy(
      id: String = var0.id,
      name: String = var0.name,
      address: String = var0.address,
      shortcuts: List<ShortcutM> = var0.shortcuts,
      hasHub: Boolean = var0.hasHub,
      hasOnlineHub: Boolean = var0.hasOnlineHub
   ): SiteM {
      return new SiteM(var1, var2, var3, var4, var5, var6);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is SiteM) {
         return false;
      } else {
         var1 = var1;
         if (!(this.id == var1.id)) {
            return false;
         } else if (!(this.name == var1.name)) {
            return false;
         } else if (!(this.address == var1.address)) {
            return false;
         } else if (!(this.shortcuts == var1.shortcuts)) {
            return false;
         } else if (this.hasHub != var1.hasHub) {
            return false;
         } else {
            return this.hasOnlineHub == var1.hasOnlineHub;
         }
      }
   }

   public override fun hashCode(): Int {
      return (
               (((this.id.hashCode() * 31 + this.name.hashCode()) * 31 + this.address.hashCode()) * 31 + this.shortcuts.hashCode()) * 31
                  + Duration$$ExternalSyntheticBackport0.m(this.hasHub)
            )
            * 31
         + Duration$$ExternalSyntheticBackport0.m(this.hasOnlineHub);
   }

   public override fun toString(): String {
      val var4: java.lang.String = this.id;
      val var5: java.lang.String = this.name;
      val var3: java.lang.String = this.address;
      val var7: java.util.List = this.shortcuts;
      val var1: Boolean = this.hasHub;
      val var2: Boolean = this.hasOnlineHub;
      val var6: StringBuilder = new StringBuilder("SiteM(id=");
      var6.append(var4);
      var6.append(", name=");
      var6.append(var5);
      var6.append(", address=");
      var6.append(var3);
      var6.append(", shortcuts=");
      var6.append(var7);
      var6.append(", hasHub=");
      var6.append(var1);
      var6.append(", hasOnlineHub=");
      var6.append(var2);
      var6.append(")");
      return var6.toString();
   }

   public companion object {
      private const val TAG: String

      private fun getShortcuts(json: JSONObject): List<ShortcutM>? {
         val var11: java.util.List = new ArrayList();
         val var12: JSONArray = JsonOptsKt.optJsonArrayFromJson(var1, "gateways");
         if (var12 == null) {
            return var11;
         } else {
            val var5: Int = var12.length();

            for (int var2 = 0; var2 < var5; var2++) {
               var var16: Any = var12.get(var2);
               val var13: JSONArray = JsonOptsKt.optJsonArrayFromJson(var16 as JSONObject, "applications");
               if (var13 != null) {
                  val var6: Int = var13.length();

                  for (int var3 = 0; var3 < var6; var3++) {
                     var16 = var13.get(var3);
                     var16 = JsonOptsKt.optJsonFromJson(var16 as JSONObject, "config");
                     if (var16 != null) {
                        val var14: JSONArray = JsonOptsKt.optJsonArrayFromJson((JSONObject)var16, "shortcuts");
                        if (var14 != null) {
                           val var7: Int = var14.length();

                           for (int var4 = 0; var4 < var7; var4++) {
                              var16 = var14.get(var4);
                              val var20: JSONObject = var16 as JSONObject;
                              val var9: Int = JsonOptsKt.optIntFromJson(var16 as JSONObject, "id");
                              if (var9 != null) {
                                 val var8: Int = var9;
                                 var16 = JsonOptsKt.optJsonFromJson(var20, "client");
                                 val var10: Any = null;
                                 if (var16 != null) {
                                    var16 = JsonOptsKt.optStringFromJson((JSONObject)var16, "name");
                                 } else {
                                    var16 = null;
                                 }

                                 val var15: java.util.Iterator = var11.iterator();

                                 do {
                                    var23 = var10;
                                    if (!var15.hasNext()) {
                                       break;
                                    }

                                    var23 = var15.next();
                                 } while (((ShortcutM)var23).getId() != var8);

                                 if (var23 == null) {
                                    var11.add(new ShortcutM(var8, (java.lang.String)var16));
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            return var11;
         }
      }

      private fun isSomeHubOnline(gateways: JSONArray): Boolean {
         val var3: Int = var1.length();
         var var2: Int = 0;

         var var4: Boolean;
         for (var4 = false; var2 < var3; var2++) {
            val var5: Any = var1.get(var2);
            if (JsonOptsKt.optBooleanFromJson(var5 as JSONObject, "online") == true) {
               var4 = true;
            }
         }

         return var4;
      }

      public fun fromJson(json: JSONObject): SiteM? {
         val var7: java.lang.String = JsonOptsKt.optStringFromJson(var1, "id");
         if (var7 == null) {
            Logger.Companion.e("SiteM", "fromJson, id is null");
            return null;
         } else {
            val var6: java.lang.String = JsonOptsKt.optStringFromJson(var1, "name");
            if (var6 == null) {
               Logger.Companion.e("SiteM", "fromJson, name is null");
               return null;
            } else {
               val var5: JSONObject = JsonOptsKt.optJsonFromJson(var1, "address");
               if (var5 == null) {
                  Logger.Companion.e("SiteM", "fromJson, addressJson is null");
                  return null;
               } else {
                  var var4: java.lang.String = JsonOptsKt.optStringFromJson(var5, "address");
                  var var3: java.lang.String = var4;
                  if (var4 == null) {
                     var3 = "";
                  }

                  val var12: java.lang.String = JsonOptsKt.optStringFromJson(var5, "city");
                  var4 = var12;
                  if (var12 == null) {
                     var4 = "";
                  }

                  val var13: JSONArray = JsonOptsKt.optJsonArrayFromJson(var1, "gateways");
                  if (var13 == null) {
                     Logger.Companion.e("SiteM", "fromJson, gateways are null");
                     return null;
                  } else {
                     val var8: java.util.List = this.getShortcuts(var1);
                     if (var8 == null) {
                        Logger.Companion.e("SiteM", "fromJson, shortcuts are null");
                        return null;
                     } else {
                        val var9: java.lang.String;
                        if (!(var4 == "") && !(var3 == "")) {
                           val var10: StringBuilder = new StringBuilder();
                           var10.append(var3);
                           var10.append(", ");
                           var10.append(var4);
                           var9 = var10.toString();
                        } else if (!(var4 == "")) {
                           var9 = var4;
                        } else if (!(var3 == "")) {
                           var9 = var3;
                        } else {
                           var9 = "";
                        }

                        val var2: Boolean;
                        if (var13.length() > 0) {
                           var2 = true;
                        } else {
                           var2 = false;
                        }

                        return new SiteM(var7, var6, var9, var8, var2, this.isSomeHubOnline(var13));
                     }
                  }
               }
            }
         }
      }
   }
}
