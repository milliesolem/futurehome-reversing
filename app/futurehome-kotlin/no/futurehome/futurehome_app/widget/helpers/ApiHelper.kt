package no.futurehome.futurehome_app.widget.helpers

import java.util.ArrayList
import no.futurehome.futurehome_app.widget.models.AuthCredentials
import no.futurehome.futurehome_app.widget.models.Mode
import no.futurehome.futurehome_app.widget.models.ModeKt
import no.futurehome.futurehome_app.widget.models.RuntimeEnv
import no.futurehome.futurehome_app.widget.models.SiteM
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject

public class ApiHelper {
   private final val client: OkHttpClient = new OkHttpClient()

   public fun checkAccessToken(accessTokenHash: String, runtimeEnv: RuntimeEnv): Boolean? {
      val var4: Request.Builder = new Request.Builder().url(var2.getHeimdallCheckEndpoint());
      val var7: StringBuilder = new StringBuilder("Bearer ");
      var7.append(var1);
      val var8: Request = var4.addHeader("Authorization", var7.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier()).head().build();
      var var6: java.lang.Boolean = null;

      try {
         var10 = this.client.newCall(var8).execute();
      } catch (var5: Exception) {
         val var11: Logger.Companion = Logger.Companion;
         val var9: StringBuilder = new StringBuilder("checkAccessToken, call error: ");
         var9.append(var5);
         var11.e("ApiHelper", var9.toString());
         return null;
      }

      val var3: Int = var10.code();
      if (var3 != 200) {
         if (var3 == 401) {
            var6 = false;
         }
      } else {
         var6 = true;
      }

      return var6;
   }

   public fun getActiveMode(siteId: String, siteTokenHash: String, runtimeEnv: RuntimeEnv): GetActiveModeResult {
      val var18: Request.Builder = new Request.Builder().url(var3.getBifrostGetModeEndpoint(var1)).addHeader("Content-Type", "application/json");
      val var8: StringBuilder = new StringBuilder("Bearer ");
      var8.append(var2);
      val var9: Request = var18.addHeader("Authorization", var8.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier()).get().build();

      try {
         var15 = this.client.newCall(var9).execute();
      } catch (var7: Exception) {
         val var19: Logger.Companion = Logger.Companion;
         val var14: StringBuilder = new StringBuilder("getActiveMode, call error: ");
         var14.append(var7);
         var19.e("ApiHelper", var14.toString());
         return GetActiveModeResult.FetchError;
      }

      val var10: ResponseBody = var15.body();
      if (var10 != null) {
         var1 = var10.string();
      } else {
         var1 = null;
      }

      if (var1 == null) {
         Logger.Companion.e("ApiHelper", "getActiveMode, response body is null");
         return GetActiveModeResult.FetchError;
      } else {
         try {
            var21 = new JSONObject(var1);
         } catch (var6: Exception) {
            val var20: Logger.Companion = Logger.Companion;
            val var16: StringBuilder = new StringBuilder("getActiveMode, getting JSON from String failed, error: ");
            var16.append(var6);
            var20.e("ApiHelper", var16.toString());
            return GetActiveModeResult.FetchError;
         }

         if (var15.code() != 200) {
            if (var15.code() == 500 && JsonOptsKt.optStringFromJson(var21, "cause") == "HUB_TIMEOUT") {
               Logger.Companion.d("ApiHelper", "getActiveMode, hub timeout");
               return GetActiveModeResult.HubTimeout;
            } else {
               val var23: Logger.Companion = Logger.Companion;
               val var4: Int = var15.code();
               val var5: StringBuilder = new StringBuilder("getActiveMode, response code: ");
               var5.append(var4);
               var5.append(", res: ");
               var5.append(var15);
               var5.append(", body: ");
               var5.append(var1);
               var23.e("ApiHelper", var5.toString());
               return GetActiveModeResult.FetchError;
            }
         } else {
            var2 = JsonOptsKt.optStringFromJson(var21, "mode");
            if (var2 == null) {
               Logger.Companion.e("ApiHelper", "getActiveMode, response has no value for mode key");
               return GetActiveModeResult.FetchError;
            } else {
               switch (var2.hashCode()) {
                  case -1685839139:
                     if (var2.equals("vacation")) {
                        return GetActiveModeResult.VacationMode;
                     }
                     break;
                  case 3007214:
                     if (var2.equals("away")) {
                        return GetActiveModeResult.AwayMode;
                     }
                     break;
                  case 3208415:
                     if (var2.equals("home")) {
                        return GetActiveModeResult.HomeMode;
                     }
                     break;
                  case 109522647:
                     if (var2.equals("sleep")) {
                        return GetActiveModeResult.SleepMode;
                     }
                  default:
               }

               val var22: Logger.Companion = Logger.Companion;
               val var13: StringBuilder = new StringBuilder("getActiveMode, unknown mode: ");
               var13.append(var2);
               var22.e("ApiHelper", var13.toString());
               return GetActiveModeResult.FetchError;
            }
         }
      }
   }

   public fun getSiteTokenHash(siteId: String, accessTokenHash: String, runtimeEnv: RuntimeEnv): String? {
      val var8: Request.Builder = new Request.Builder().url(var3.getHeimdallGetSiteTokenHash(var1)).addHeader("Content-Type", "application/json");
      val var20: StringBuilder = new StringBuilder("Bearer ");
      var20.append(var2);
      val var9: Request = var8.addHeader("Authorization", var20.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier()).get().build();

      try {
         var22 = this.client.newCall(var9).execute();
      } catch (var7: Exception) {
         val var21: Logger.Companion = Logger.Companion;
         val var10: StringBuilder = new StringBuilder("getSiteTokenHash, call error: ");
         var10.append(var7);
         var21.e("ApiHelper", var10.toString());
         return null;
      }

      if (var22.code() != 200) {
         val var19: Logger.Companion = Logger.Companion;
         val var4: Int = var22.code();
         val var16: ResponseBody = var22.body();
         if (var16 != null) {
            var1 = var16.string();
         } else {
            var1 = null;
         }

         val var5: StringBuilder = new StringBuilder("getSiteTokenHash, response code: ");
         var5.append(var4);
         var5.append(", res: ");
         var5.append(var22);
         var5.append(", body: ");
         var5.append(var1);
         var19.e("ApiHelper", var5.toString());
         return null;
      } else {
         val var11: ResponseBody = var22.body();
         if (var11 != null) {
            var1 = var11.string();
         } else {
            var1 = null;
         }

         if (var1 == null) {
            Logger.Companion.e("ApiHelper", "getSiteTokenHash, response body is null");
            return null;
         } else {
            try {
               var14 = new JSONObject(var1);
            } catch (var6: Exception) {
               val var13: Logger.Companion = Logger.Companion;
               val var18: StringBuilder = new StringBuilder("getSiteTokenHash, getting JSON from String failed, error: ");
               var18.append(var6);
               var13.e("ApiHelper", var18.toString());
               return null;
            }

            var1 = JsonOptsKt.optStringFromJson(var14, "site_token_hash");
            if (var1 == null) {
               Logger.Companion.e("ApiHelper", "getSiteTokenHash, response has no value for site_token_hash key");
               return null;
            } else {
               return var1;
            }
         }
      }
   }

   public fun getSites(accessTokenHash: String, runtimeEnv: RuntimeEnv): List<SiteM>? {
      var var5: RequestBody = RequestBody.Companion
         .create(
            "{   \"query\": \"{ \\n       sites { \\n          name \\n          id \\n          address { \\n               address \\n               city \\n               postalCode \\n          } \\n          gateways { \\n               online \\n               applications { \\n                   config\\n               }\\n          } \\n       } \\n   }\"}",
            MediaType.Companion.get("application/json; charset=utf-8")
         );
      val var16: Request.Builder = new Request.Builder().url(var2.getNiflheimEndpoint()).addHeader("Content-Type", "application/json");
      var var6: StringBuilder = new StringBuilder("Bearer ");
      var6.append(var1);
      val var9: Request = var16.addHeader("Authorization", var6.toString())
         .addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier())
         .post(var5)
         .build();

      try {
         var18 = this.client.newCall(var9).execute();
      } catch (var8: Exception) {
         val var10: Logger.Companion = Logger.Companion;
         val var17: StringBuilder = new StringBuilder("getAvailableSites, call error: ");
         var17.append(var8);
         var10.e("ApiHelper", var17.toString());
         return null;
      }

      if (var18.code() != 200) {
         val var29: Logger.Companion = Logger.Companion;
         val var23: Int = var18.code();
         val var14: ResponseBody = var18.body();
         if (var14 != null) {
            var1 = var14.string();
         } else {
            var1 = null;
         }

         var6 = new StringBuilder("getAvailableSites, response code: ");
         var6.append(var23);
         var6.append(", res: ");
         var6.append(var18);
         var6.append(", body: ");
         var6.append(var1);
         var29.e("ApiHelper", var6.toString());
         return null;
      } else {
         val var11: ResponseBody = var18.body();
         if (var11 != null) {
            var1 = var11.string();
         } else {
            var1 = null;
         }

         if (var1 == null) {
            Logger.Companion.e("ApiHelper", "getAvailableSites, response body is null");
            return null;
         } else {
            try {
               var25 = new JSONObject(var1);
            } catch (var7: Exception) {
               val var19: Logger.Companion = Logger.Companion;
               val var24: StringBuilder = new StringBuilder("getting JSON from String failed, error: ");
               var24.append(var7);
               var19.e("ApiHelper", var24.toString());
               return null;
            }

            if (var25.has("errors")) {
               val var31: Logger.Companion = Logger.Companion;
               val var28: StringBuilder = new StringBuilder("getAvailableSites, response contains errors, res: ");
               var28.append(var18);
               var28.append(", body: ");
               var28.append(var1);
               var31.e("ApiHelper", var28.toString());
               return null;
            } else if (!var25.has("data")) {
               Logger.Companion.e("ApiHelper", "resJson doesn't contain data key");
               return null;
            } else {
               val var20: JSONObject = var25.getJSONObject("data");
               if (!var20.has("sites")) {
                  Logger.Companion.e("ApiHelper", "resJson doesn't contain sites key");
                  return null;
               } else {
                  val var13: java.util.List = new ArrayList();
                  val var21: Any = var20.opt("sites");
                  if (var21 == JSONObject.NULL) {
                     return var13;
                  } else if (var21 !is JSONArray) {
                     Logger.Companion.e("ApiHelper", "sites key isn't a json array");
                     return null;
                  } else {
                     val var22: JSONArray = var21 as JSONArray;
                     val var4: Int = (var21 as JSONArray).length();

                     for (int var3 = 0; var3 < var4; var3++) {
                        val var30: SiteM.Companion = SiteM.Companion;
                        var5 = (RequestBody)var22.get(var3);
                        val var27: SiteM = var30.fromJson(var5 as JSONObject);
                        if (var27 == null) {
                           Logger.Companion.e("ApiHelper", "getAvailableSites, error: SiteM is equal to null");
                           return null;
                        }

                        var13.add(var27);
                     }

                     return var13;
                  }
               }
            }
         }
      }
   }

   public fun refreshAccessToken(refreshToken: String, scope: String, tokenEndpoint: String, runtimeEnv: RuntimeEnv): AuthCredentials? {
      val var8: Request = new Request.Builder()
         .url(var3)
         .addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier())
         .post(
            new FormBody.Builder(null, 1, null)
               .add("grant_type", "refresh_token")
               .add("refresh_token", var1)
               .add("scope", var2)
               .add("client_id", var4.getClientId())
               .add("client_secret", var4.getClientSecret())
               .build()
         )
         .build();

      try {
         var16 = this.client.newCall(var8).execute();
      } catch (var6: Exception) {
         val var9: Logger.Companion = Logger.Companion;
         val var15: StringBuilder = new StringBuilder("refreshAccessToken, call error: ");
         var15.append(var6);
         var9.e("ApiHelper", var15.toString());
         return null;
      }

      if (var16.code() != 200) {
         val var17: Logger.Companion = Logger.Companion;
         val var5: Int = var16.code();
         val var13: ResponseBody = var16.body();
         if (var13 != null) {
            var1 = var13.string();
         } else {
            var1 = null;
         }

         val var18: StringBuilder = new StringBuilder("refreshAccessToken, response code: ");
         var18.append(var5);
         var18.append(", res: ");
         var18.append(var16);
         var18.append(", body: ");
         var18.append(var1);
         var17.e("ApiHelper", var18.toString());
         return null;
      } else {
         val var10: ResponseBody = var16.body();
         if (var10 != null) {
            var1 = var10.string();
         } else {
            var1 = null;
         }

         if (var1 == null) {
            Logger.Companion.e("ApiHelper", "refreshAccessToken, response body is null");
            return null;
         } else {
            val var12: AuthCredentials = AuthCredentials.Companion.fromResponseString(var1, var3);
            if (var12 == null) {
               Logger.Companion.e("ApiHelper", "refreshAccessToken, AuthCredentials.fromResponseString() returned null");
               return null;
            } else {
               return var12;
            }
         }
      }
   }

   public fun setActiveMode(mode: Mode, siteId: String, siteTokenHash: String, runtimeEnv: RuntimeEnv): Boolean {
      val var11: Request.Builder = new Request.Builder()
         .url(var4.getBifrostChangeModeEndpoint(var2, ModeKt.modeToString(var1)))
         .addHeader("Content-Type", "application/json");
      val var8: StringBuilder = new StringBuilder("Bearer ");
      var8.append(var3);
      val var16: Request.Builder = var11.addHeader("Authorization", var8.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier());
      var var9: java.lang.String = null;
      val var13: Request = var16.put(RequestBody.Companion.create(new byte[0], null, 0, 0)).build();

      try {
         var15 = this.client.newCall(var13).execute();
      } catch (var6: Exception) {
         val var14: Logger.Companion = Logger.Companion;
         val var10: StringBuilder = new StringBuilder("setActiveMode, call error: ");
         var10.append(var6);
         var14.e("ApiHelper", var10.toString());
         return false;
      }

      if (var15.code() != 200) {
         val var17: Logger.Companion = Logger.Companion;
         val var5: Int = var15.code();
         val var18: ResponseBody = var15.body();
         if (var18 != null) {
            var9 = var18.string();
         }

         val var19: StringBuilder = new StringBuilder("setActiveMode, response code: ");
         var19.append(var5);
         var19.append(", res: ");
         var19.append(var15);
         var19.append(", body: ");
         var19.append(var9);
         var17.e("ApiHelper", var19.toString());
         return false;
      } else {
         return true;
      }
   }

   public fun triggerShortcut(shortcutId: Int, siteId: String, siteTokenHash: String, runtimeEnv: RuntimeEnv): Boolean {
      val var9: Request.Builder = new Request.Builder()
         .url(var4.getBifrostTriggerShortcutEndpoint(var2, java.lang.String.valueOf(var1)))
         .addHeader("Content-Type", "application/json");
      val var16: StringBuilder = new StringBuilder("Bearer ");
      var16.append(var3);
      val var17: Request.Builder = var9.addHeader("Authorization", var16.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier());
      var2 = null;
      val var13: Request = var17.put(RequestBody.Companion.create(new byte[0], null, 0, 0)).build();

      try {
         var15 = this.client.newCall(var13).execute();
      } catch (var6: Exception) {
         val var14: Logger.Companion = Logger.Companion;
         val var11: StringBuilder = new StringBuilder("triggerShortcut, call error: ");
         var11.append(var6);
         var14.e("ApiHelper", var11.toString());
         return false;
      }

      if (var15.code() != 200) {
         val var18: Logger.Companion = Logger.Companion;
         var1 = var15.code();
         val var5: ResponseBody = var15.body();
         if (var5 != null) {
            var2 = var5.string();
         }

         val var19: StringBuilder = new StringBuilder("triggerShortcut, response code: ");
         var19.append(var1);
         var19.append(", res: ");
         var19.append(var15);
         var19.append(", body: ");
         var19.append(var2);
         var18.e("ApiHelper", var19.toString());
         return false;
      } else {
         return true;
      }
   }

   public companion object {
      private const val TAG: String
   }
}
