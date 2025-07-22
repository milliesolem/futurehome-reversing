package no.futurehome.futurehome_app.widget.helpers

import java.util.ArrayList
import no.futurehome.futurehome_app.widget.models.AuthCredentials
import no.futurehome.futurehome_app.widget.models.Mode
import no.futurehome.futurehome_app.widget.models.ModeKt
import no.futurehome.futurehome_app.widget.models.RuntimeEnv
import no.futurehome.futurehome_app.widget.models.SiteM
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject

public class ApiHelper {
   private final val client: OkHttpClient = OkHttpClient();

   // reverse-engineered by cross-refrencing different decompiler outputs

   //checks access token by sending API request
   public fun checkAccessToken(accessTokenHash: String, runtimeEnv: RuntimeEnv): Boolean? {
      val apiRequestBuilder: Request.Builder = Request
         .Builder()
         .url(runtimeEnv.getHeimdallCheckEndpoint());
      val tokenHeader: String = "Bearer $accessTokenHash";
      val request: Request = apiRequestBuilder
         .addHeader("Authorization", tokenHeader)
         .addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier())
         .head().build();

      val apiResponse: Response? = callApi("checkAccessToken", request);
      if (apiResponse == null){
         return null;
      }

      val respCode: Int = apiResponse.code;
      var isTokenValid: Boolean = (respCode == 200);
      /*
      if (respCode != 200) {
         if (respCode == 401) {
            isTokenValid = false;
         }
      } else {
         isTokenValid = true;
      }*/

      return isTokenValid;
   }

   public fun getActiveMode(siteId: String, siteTokenHash: String, runtimeEnv: RuntimeEnv): GetActiveModeResult {
      val requestBuilder: Request.Builder = Request
         .Builder().url(runtimeEnv.getBifrostGetModeEndpoint(siteId))
         .addHeader("Content-Type", "application/json");
      val tokenHeader: String = "Bearer $siteTokenHash";
      val request: Request = requestBuilder
         .addHeader("Authorization", tokenHeader)
         .addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier())
         .get().build();


      val apiResponse: Response? = callApi("getActiveMode", request);
      if (apiResponse == null){
         return GetActiveModeResult.FetchError;
      }

      val responseBody: ResponseBody? = apiResponse.body;
      val responseBodyStr: String? = responseBody?.string();

      if (responseBodyStr == null) {
         Logger.Companion.e("ApiHelper", "getActiveMode, response body is null");
         return GetActiveModeResult.FetchError;
      } else {
         val responseJson: JSONObject;
         try {
            responseJson = JSONObject(responseBodyStr);
         } catch (apiRespError: Exception) {
            logError("ApiHelper", "getActiveMode, getting JSON from String failed, error: $apiRespError");
            return GetActiveModeResult.FetchError;
         }

         val responseCode: Int = apiResponse.code;
         if (responseCode != 200) {
            if (responseCode == 500 && JsonOptsKt.optStringFromJson(responseJson, "cause") == "HUB_TIMEOUT") {
               Logger.Companion.d("ApiHelper", "getActiveMode, hub timeout");
               return GetActiveModeResult.HubTimeout;
            } else {
               logError("ApiHelper", "getActiveMode, response code: $responseCode, res: $apiResponse, body: $responseBodyStr");
               return GetActiveModeResult.FetchError;
            }
         } else {
            val mode: String = JsonOptsKt.optStringFromJson(responseJson, "mode");
            if (mode == null) {
               Logger.Companion.e("ApiHelper", "getActiveMode, response has no value for mode key");
               return GetActiveModeResult.FetchError;
            } else {
               when (mode) {
                  "vacation" -> return GetActiveModeResult.VacationMode;
                  "away" -> return GetActiveModeResult.AwayMode;
                  "home" -> return GetActiveModeResult.HomeMode;
                  "sleep" -> return GetActiveModeResult.SleepMode;
               }

               logError("ApiHelper", "getActiveMode, unknown mode: $mode");
               return GetActiveModeResult.FetchError;
            }
         }
      }
   }

   public fun getSiteTokenHash(siteId: String, accessTokenHash: String, runtimeEnv: RuntimeEnv): String? {
      val var8: Request.Builder = Request.Builder().url(runtimeEnv.getHeimdallGetSiteTokenHash(siteId)).addHeader("Content-Type", "application/json");
      val tokenHeader: String = "Bearer $accessTokenHash";
      val request: Request = var8.addHeader("Authorization", tokenHeader).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier()).get().build();


      val apiResponse: Response? = callApi("getSiteTokenHash", request);
      if (apiResponse == null){
         return null;
      }

      if (apiResponse.code != 200) {
         logError("ApiHelper","getSiteTokenHash, response code: ${apiResponse.code}, res: $apiResponse, body: ${apiResponse.body?.string()}");
         return null;
      } else {
         val var11: ResponseBody = apiResponse.body();
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

      val queryByteArray: ByteArray = byteArrayOf(123, 32, 32, 32, 34, 113, 117, 101, 114, 121, 34, 58, 32, 34, 123, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 115, 105, 116, 101, 115, 32, 123, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 110, 97, 109, 101, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 105, 100, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 97, 100, 100, 114, 101, 115, 115, 32, 123, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 97, 100, 100, 114, 101, 115, 115, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 99, 105, 116, 121, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 112, 111, 115, 116, 97, 108, 67, 111, 100, 101, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 125, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 103, 97, 116, 101, 119, 97, 121, 115, 32, 123, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 111, 110, 108, 105, 110, 101, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 97, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 115, 32, 123, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 99, 111, 110, 102, 105, 103, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 125, 92, 110, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 125, 32, 92, 110, 32, 32, 32, 32, 32, 32, 32, 125, 32, 92, 110, 32, 32, 32, 125, 34, 125);
      var requestQuery: RequestBody = RequestBody.create(
         "application/json; charset=utf-8".toMediaType(),
         queryByteArray);
      val var16: Request.Builder = Request.Builder()
         .url(runtimeEnv.getNiflheimEndpoint())
         .addHeader("Content-Type", "application/json");
      var tokenHeader: String = "Bearer $accessTokenHash";
      val request: Request = var16
         .addHeader("Authorization", tokenHeader)
         .addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier())
         .post(requestQuery)
         .build();

      val apiResponse: Response? = callApi("getAvailableSites", request);
      if (apiResponse == null){
         return null;
      }

      if (apiResponse.code != 200) {
         logApiCallError("getAvailableSites",apiResponse);
         return null;
      } else {
         val apiResponseBody: ResponseBody? = apiResponse.body;
         val respBodyStr: String;
         if (apiResponseBody != null) {
            respBodyStr = apiResponseBody.string();
         } else {
            respBodyStr = null;
         }

         if (respBodyStr == null) {
            Logger.Companion.e("ApiHelper", "getAvailableSites, response body is null");
            return null;
         } else {
            try {
               var25 = new JSONObject(respBodyStr);
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
               var28.append(apiResponse);
               var28.append(", body: ");
               var28.append(respBodyStr);
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
                        requestQuery = (RequestBody)var22.get(var3);
                        val var27: SiteM = var30.fromJson(requestQuery as JSONObject);
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
      val var8: Request = Request.Builder()
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
         ).build();

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
      val tokenHeader: String = "Bearer $siteTokenHash";
      val request: Request = Request.Builder()
         .url(runtimeEnv.getBifrostTriggerShortcutEndpoint(siteId, "$shortcutId"))
         .addHeader("Content-Type", "application/json")
         .addHeader("Authorization", tokenHeader)
         .addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier())
         .put(RequestBody.Companion.create(null, byteArrayOf(), 0, 0))
         .build();

      val apiResponse: Response? = callApi("checkAccessToken", request);
      if (apiResponse == null){
         return false;
      }

      if (apiResponse.code != 200) {
         logApiCallError("triggerShortcut", apiResponse);
         return false;
      } else {
         return true;
      }
   }

   public companion object {
      private const val TAG: String = "ApiHelper";
   }


   private fun logError(tag: String, msg: String){
      val loggerComp: Logger.Companion = Logger.Companion;
      loggerComp.e(tag , msg);
   }
   private fun callApi(functionName: String, request: Request): Response? {
      try {
         val apiResponse: Response = this.client.newCall(request).execute();
         return apiResponse;
      } catch (apiRespError: Exception) {
         logError("ApiHelper", "$functionName, call error: $apiRespError");
         return null;
      }
   }
   private fun logApiCallError(functionName: String, response: Response){
      logError("ApiHelper","$functionName, response code: ${response.code}, res: $response, body: ${response.body?.string()}");
   }
}
