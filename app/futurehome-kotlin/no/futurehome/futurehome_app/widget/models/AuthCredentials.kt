package no.futurehome.futurehome_app.widget.models

import com.signify.hue.flutterreactiveble.utils.Duration$$ExternalSyntheticBackport0
import no.futurehome.futurehome_app.widget.helpers.JsonOptsKt
import no.futurehome.futurehome_app.widget.helpers.Logger
import org.json.JSONObject

public data class AuthCredentials(accessToken: String,
   refreshToken: String,
   tokenEndpoint: String,
   accessTokenHash: String,
   scope: String,
   expiresAtMillis: Long
) {
   public final val accessToken: String
   public final val refreshToken: String
   public final val tokenEndpoint: String
   public final val accessTokenHash: String
   public final val scope: String
   public final val expiresAtMillis: Long

   init {
      this.accessToken = var1;
      this.refreshToken = var2;
      this.tokenEndpoint = var3;
      this.accessTokenHash = var4;
      this.scope = var5;
      this.expiresAtMillis = var6;
   }

   public operator fun component1(): String {
      return this.accessToken;
   }

   public operator fun component2(): String {
      return this.refreshToken;
   }

   public operator fun component3(): String {
      return this.tokenEndpoint;
   }

   public operator fun component4(): String {
      return this.accessTokenHash;
   }

   public operator fun component5(): String {
      return this.scope;
   }

   public operator fun component6(): Long {
      return this.expiresAtMillis;
   }

   public fun copy(
      accessToken: String = var0.accessToken,
      refreshToken: String = var0.refreshToken,
      tokenEndpoint: String = var0.tokenEndpoint,
      accessTokenHash: String = var0.accessTokenHash,
      scope: String = var0.scope,
      expiresAtMillis: Long = var0.expiresAtMillis
   ): AuthCredentials {
      return new AuthCredentials(var1, var2, var3, var4, var5, var6);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is AuthCredentials) {
         return false;
      } else {
         var1 = var1;
         if (!(this.accessToken == var1.accessToken)) {
            return false;
         } else if (!(this.refreshToken == var1.refreshToken)) {
            return false;
         } else if (!(this.tokenEndpoint == var1.tokenEndpoint)) {
            return false;
         } else if (!(this.accessTokenHash == var1.accessTokenHash)) {
            return false;
         } else if (!(this.scope == var1.scope)) {
            return false;
         } else {
            return this.expiresAtMillis == var1.expiresAtMillis;
         }
      }
   }

   public override fun hashCode(): Int {
      return (
               (((this.accessToken.hashCode() * 31 + this.refreshToken.hashCode()) * 31 + this.tokenEndpoint.hashCode()) * 31 + this.accessTokenHash.hashCode())
                     * 31
                  + this.scope.hashCode()
            )
            * 31
         + Duration$$ExternalSyntheticBackport0.m(this.expiresAtMillis);
   }

   public fun toPrefsJsonString(): String {
      var var3: java.lang.String = this.accessToken;
      var var4: StringBuilder = new StringBuilder("{\"access_token\":\"");
      var4.append(var3);
      var4.append("\",");
      var3 = var4.toString();
      var var5: java.lang.String = this.refreshToken;
      var4 = new StringBuilder();
      var4.append(var3);
      var4.append("\"refresh_token\":\"");
      var4.append(var5);
      var4.append("\",");
      val var13: java.lang.String = var4.toString();
      var3 = this.accessTokenHash;
      val var18: StringBuilder = new StringBuilder();
      var18.append(var13);
      var18.append("\"access_token_hash\":\"");
      var18.append(var3);
      var18.append("\",");
      val var14: java.lang.String = var18.toString();
      var5 = this.tokenEndpoint;
      val var8: StringBuilder = new StringBuilder();
      var8.append(var14);
      var8.append("\"token_endpoint\":\"");
      var8.append(var5);
      var8.append("\",");
      val var15: java.lang.String = var8.toString();
      var5 = this.scope;
      val var9: StringBuilder = new StringBuilder();
      var9.append(var15);
      var9.append("\"scope\":\"");
      var9.append(var5);
      var9.append("\",");
      var3 = var9.toString();
      val var1: Long = this.expiresAtMillis;
      var4 = new StringBuilder();
      var4.append(var3);
      var4.append("\"expires_at_millis\":");
      var4.append(var1);
      var3 = var4.toString();
      var4 = new StringBuilder();
      var4.append(var3);
      var4.append("}");
      return var4.toString();
   }

   public override fun toString(): String {
      val var6: java.lang.String = this.accessToken;
      val var5: java.lang.String = this.refreshToken;
      val var3: java.lang.String = this.tokenEndpoint;
      val var8: java.lang.String = this.accessTokenHash;
      val var4: java.lang.String = this.scope;
      val var1: Long = this.expiresAtMillis;
      val var7: StringBuilder = new StringBuilder("AuthCredentials(accessToken=");
      var7.append(var6);
      var7.append(", refreshToken=");
      var7.append(var5);
      var7.append(", tokenEndpoint=");
      var7.append(var3);
      var7.append(", accessTokenHash=");
      var7.append(var8);
      var7.append(", scope=");
      var7.append(var4);
      var7.append(", expiresAtMillis=");
      var7.append(var1);
      var7.append(")");
      return var7.toString();
   }

   public companion object {
      private const val TAG: String
      public const val expirationGrace: Int

      public fun fromPrefsString(authCredentialsString: String): AuthCredentials? {
         var1 = StringsKt.replace$default(StringsKt.trim(var1, new char[]{'"'}), "\\", "", false, 4, null);

         var var6: JSONObject;
         try {
            var6 = new JSONObject(var1);
         } catch (var7: Exception) {
            val var2: Logger.Companion = Logger.Companion;
            val var3: StringBuilder = new StringBuilder("fromPrefsString, error: creating JSON from string: ");
            var3.append(var1);
            var2.e("AuthCredentials", var3.toString());
            return null;
         }

         var1 = JsonOptsKt.optStringFromJson(var6, "access_token");
         if (var1 == null) {
            Logger.Companion.e("AuthCredentials", "fromPrefsString, accessToken is null");
            return null;
         } else {
            val var5: java.lang.String = JsonOptsKt.optStringFromJson(var6, "refresh_token");
            if (var5 == null) {
               Logger.Companion.e("AuthCredentials", "fromPrefsString, refreshToken is null");
               return null;
            } else {
               val var4: java.lang.String = JsonOptsKt.optStringFromJson(var6, "access_token_hash");
               if (var4 == null) {
                  Logger.Companion.e("AuthCredentials", "fromPrefsString, accessTokenHash is null");
                  return null;
               } else {
                  val var10: java.lang.String = JsonOptsKt.optStringFromJson(var6, "token_endpoint");
                  if (var10 == null) {
                     Logger.Companion.e("AuthCredentials", "fromPrefsString, tokenEndpoint is null");
                     return null;
                  } else {
                     val var11: java.lang.String = JsonOptsKt.optStringFromJson(var6, "scope");
                     if (var11 == null) {
                        Logger.Companion.e("AuthCredentials", "fromPrefsString, scope is null");
                        return null;
                     } else {
                        val var12: java.lang.Long = JsonOptsKt.optLongFromJson(var6, "expires_at_millis");
                        if (var12 == null) {
                           Logger.Companion.e("AuthCredentials", "fromPrefsString, expiresAtMillis is null");
                           return null;
                        } else {
                           return new AuthCredentials(var1, var5, var10, var4, var11, var12);
                        }
                     }
                  }
               }
            }
         }
      }

      public fun fromResponseString(responseString: String, tokenEndpoint: String): AuthCredentials? {
         var var10: JSONObject;
         try {
            var10 = new JSONObject(var1);
         } catch (var7: Exception) {
            val var3: Logger.Companion = Logger.Companion;
            val var9: StringBuilder = new StringBuilder("fromResponseString, creating JSON from string: ");
            var9.append(var1);
            var3.e("AuthCredentials", var9.toString());
            return null;
         }

         val var5: java.lang.String = JsonOptsKt.optStringFromJson(var10, "access_token");
         if (var5 == null) {
            Logger.Companion.e("AuthCredentials", "fromResponseString, accessToken is null");
            return null;
         } else {
            val var6: java.lang.String = JsonOptsKt.optStringFromJson(var10, "refresh_token");
            if (var6 == null) {
               Logger.Companion.e("AuthCredentials", "fromResponseString, refreshToken is null");
               return null;
            } else {
               val var4: java.lang.String = JsonOptsKt.optStringFromJson(var10, "access_token_hash");
               if (var4 == null) {
                  Logger.Companion.e("AuthCredentials", "fromResponseString, accessTokenHash is null");
                  return null;
               } else {
                  var1 = JsonOptsKt.optStringFromJson(var10, "scope");
                  if (var1 == null) {
                     Logger.Companion.e("AuthCredentials", "fromResponseString, scope is null");
                     return null;
                  } else {
                     val var11: java.lang.Long = JsonOptsKt.optLongFromJson(var10, "expires_in");
                     if (var11 == null) {
                        Logger.Companion.e("AuthCredentials", "fromResponseString, expiresIn is null");
                        return null;
                     } else {
                        return new AuthCredentials(var5, var6, var2, var4, var1, System.currentTimeMillis() + var11 * 1000 - 300000);
                     }
                  }
               }
            }
         }
      }
   }
}
