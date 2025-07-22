package com.mixpanel.mixpanel_flutter;

import android.content.Context;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MixpanelFlutterPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {
   private static final Map<String, Object> EMPTY_HASHMAP = new HashMap<>();
   private MethodChannel channel;
   private Context context;
   private MixpanelAPI mixpanel;
   private JSONObject mixpanelProperties;

   public MixpanelFlutterPlugin() {
   }

   public MixpanelFlutterPlugin(Context var1) {
      this.context = var1;
   }

   private void handleAddGroup(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("groupKey");
      Object var4 = var1.argument("groupID");
      this.mixpanel.addGroup(var3, var4);
      var2.success(null);
   }

   private void handleAlias(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("distinctId");
      String var4 = var1.argument("alias");
      this.mixpanel.alias(var4, var3);
      var2.success(null);
   }

   private void handleAppend(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("name");
      Object var4 = var1.argument("value");
      this.mixpanel.getPeople().append(var3, var4);
      var2.success(null);
   }

   private void handleClearCharges(MethodCall var1, MethodChannel.Result var2) {
      this.mixpanel.getPeople().clearCharges();
      var2.success(null);
   }

   private void handleClearSuperProperties(MethodCall var1, MethodChannel.Result var2) {
      this.mixpanel.clearSuperProperties();
      var2.success(null);
   }

   private void handleDeleteGroup(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("groupKey");
      Object var4 = var1.argument("groupID");
      this.mixpanel.getGroup(var3, var4).deleteGroup();
      var2.success(null);
   }

   private void handleDeleteUser(MethodCall var1, MethodChannel.Result var2) {
      this.mixpanel.getPeople().deleteUser();
      var2.success(null);
   }

   private void handleEventElapsedTime(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("eventName");
      var2.success(this.mixpanel.eventElapsedTime(var3));
   }

   private void handleFlush(MethodCall var1, MethodChannel.Result var2) {
      this.mixpanel.flush();
      var2.success(null);
   }

   private void handleGetDistinctId(MethodCall var1, MethodChannel.Result var2) {
      var2.success(this.mixpanel.getDistinctId());
   }

   private void handleGetSuperProperties(MethodCall var1, MethodChannel.Result var2) {
      try {
         var2.success(MixpanelFlutterHelper.toMap(this.mixpanel.getSuperProperties()));
      } catch (JSONException var3) {
         var2.error("MixpanelFlutterException", var3.getLocalizedMessage(), null);
         var2.success(null);
      }
   }

   private void handleGroupRemovePropertyValue(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("groupKey");
      Object var4 = var1.argument("groupID");
      String var5 = var1.argument("name");
      Object var6 = var1.argument("value");
      this.mixpanel.getGroup(var3, var4).remove(var5, var6);
      var2.success(null);
   }

   private void handleGroupSetProperties(MethodCall var1, MethodChannel.Result var2) {
      String var4 = var1.argument("groupKey");
      Object var5 = var1.argument("groupID");
      Map var3 = var1.argument("properties");
      Map var6 = var3;
      if (var3 == null) {
         var6 = EMPTY_HASHMAP;
      }

      JSONObject var7 = new JSONObject(var6);
      this.mixpanel.getGroup(var4, var5).set(var7);
      var2.success(null);
   }

   private void handleGroupSetPropertyOnce(MethodCall var1, MethodChannel.Result var2) {
      String var4 = var1.argument("groupKey");
      Object var5 = var1.argument("groupID");
      Map var3 = var1.argument("properties");
      Map var6 = var3;
      if (var3 == null) {
         var6 = EMPTY_HASHMAP;
      }

      JSONObject var7 = new JSONObject(var6);
      this.mixpanel.getGroup(var4, var5).setOnce(var7);
      var2.success(null);
   }

   private void handleGroupUnionProperty(MethodCall var1, MethodChannel.Result var2) {
      String var4 = var1.argument("groupKey");
      Object var5 = var1.argument("groupID");
      String var3 = var1.argument("name");
      ArrayList var6 = var1.argument("value");
      this.mixpanel.getGroup(var4, var5).union(var3, new JSONArray(var6));
      var2.success(null);
   }

   private void handleGroupUnsetProperty(MethodCall var1, MethodChannel.Result var2) {
      String var4 = var1.argument("groupKey");
      Object var3 = var1.argument("groupID");
      String var5 = var1.argument("propertyName");
      this.mixpanel.getGroup(var4, var3).unset(var5);
      var2.success(null);
   }

   private void handleHasOptedOutTracking(MethodCall var1, MethodChannel.Result var2) {
      var2.success(this.mixpanel.hasOptedOutTracking());
   }

   private void handleIdentify(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("distinctId");
      this.mixpanel.identify(var3);
      var2.success(null);
   }

   private void handleIncrement(MethodCall var1, MethodChannel.Result var2) {
      Map var3 = var1.argument("properties");
      this.mixpanel.getPeople().increment(var3);
      var2.success(null);
   }

   private void handleInitialize(MethodCall var1, MethodChannel.Result var2) {
      String var6 = var1.argument("token");
      if (var6 != null) {
         Map var5 = var1.argument("mixpanelProperties");
         Map var4 = var5;
         if (var5 == null) {
            var4 = EMPTY_HASHMAP;
         }

         this.mixpanelProperties = new JSONObject(var4);
         var5 = var1.argument("superProperties");
         var4 = var5;
         if (var5 == null) {
            var4 = EMPTY_HASHMAP;
         }

         JSONObject var12 = new JSONObject(var4);

         try {
            var13 = MixpanelFlutterHelper.getMergedProperties(var12, this.mixpanelProperties);
         } catch (JSONException var8) {
            var2.error("MixpanelFlutterException", var8.getLocalizedMessage(), null);
            return;
         }

         Boolean var15 = var1.argument("optOutTrackingDefault");
         Boolean var7 = var1.argument("trackAutomaticEvents");
         Context var9 = this.context;
         boolean var3;
         if (var15 == null) {
            var3 = false;
         } else {
            var3 = var15;
         }

         MixpanelAPI var10 = MixpanelAPI.getInstance(var9, var6, var3, var13, null, var7);
         this.mixpanel = var10;
         var2.success(Integer.toString(var10.hashCode()));
      } else {
         throw new RuntimeException("Your Mixpanel Token was not set");
      }
   }

   private void handleOptInTracking(MethodCall var1, MethodChannel.Result var2) {
      this.mixpanel.optInTracking(null, this.mixpanelProperties);
      var2.success(null);
   }

   private void handleOptOutTracking(MethodCall var1, MethodChannel.Result var2) {
      this.mixpanel.optOutTracking();
      var2.success(null);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void handleRegisterSuperProperties(MethodCall var1, MethodChannel.Result var2) {
      Map var3 = var1.argument("properties");

      JSONObject var4;
      try {
         var4 = new JSONObject;
      } catch (JSONException var7) {
         var2.error("MixpanelFlutterException", var7.getLocalizedMessage(), null);
         return;
      }

      Map var8 = var3;
      if (var3 == null) {
         try {
            var8 = EMPTY_HASHMAP;
         } catch (JSONException var6) {
            var2.error("MixpanelFlutterException", var6.getLocalizedMessage(), null);
            return;
         }
      }

      try {
         var4./* $VF: Unable to resugar constructor */<init>(var8);
         var9 = MixpanelFlutterHelper.getMergedProperties(var4, this.mixpanelProperties);
      } catch (JSONException var5) {
         var2.error("MixpanelFlutterException", var5.getLocalizedMessage(), null);
         return;
      }

      this.mixpanel.registerSuperProperties(var9);
      var2.success(null);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void handleRegisterSuperPropertiesOnce(MethodCall var1, MethodChannel.Result var2) {
      Map var3 = var1.argument("properties");

      JSONObject var4;
      try {
         var4 = new JSONObject;
      } catch (JSONException var7) {
         var2.error("MixpanelFlutterException", var7.getLocalizedMessage(), null);
         return;
      }

      Map var8 = var3;
      if (var3 == null) {
         try {
            var8 = EMPTY_HASHMAP;
         } catch (JSONException var6) {
            var2.error("MixpanelFlutterException", var6.getLocalizedMessage(), null);
            return;
         }
      }

      try {
         var4./* $VF: Unable to resugar constructor */<init>(var8);
         var9 = MixpanelFlutterHelper.getMergedProperties(var4, this.mixpanelProperties);
      } catch (JSONException var5) {
         var2.error("MixpanelFlutterException", var5.getLocalizedMessage(), null);
         return;
      }

      this.mixpanel.registerSuperPropertiesOnce(var9);
      var2.success(null);
   }

   private void handleRemove(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("name");
      Object var4 = var1.argument("value");
      this.mixpanel.getPeople().remove(var3, var4);
      var2.success(null);
   }

   private void handleRemoveGroup(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("groupKey");
      Object var4 = var1.argument("groupID");
      this.mixpanel.removeGroup(var3, var4);
      var2.success(null);
   }

   private void handleReset(MethodCall var1, MethodChannel.Result var2) {
      this.mixpanel.reset();
      var2.success(null);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void handleSet(MethodCall var1, MethodChannel.Result var2) {
      Map var3 = var1.argument("properties");

      JSONObject var4;
      try {
         var4 = new JSONObject;
      } catch (JSONException var7) {
         var2.error("MixpanelFlutterException", var7.getLocalizedMessage(), null);
         return;
      }

      Map var8 = var3;
      if (var3 == null) {
         try {
            var8 = EMPTY_HASHMAP;
         } catch (JSONException var6) {
            var2.error("MixpanelFlutterException", var6.getLocalizedMessage(), null);
            return;
         }
      }

      try {
         var4./* $VF: Unable to resugar constructor */<init>(var8);
         var9 = MixpanelFlutterHelper.getMergedProperties(var4, this.mixpanelProperties);
      } catch (JSONException var5) {
         var2.error("MixpanelFlutterException", var5.getLocalizedMessage(), null);
         return;
      }

      this.mixpanel.getPeople().set(var9);
      var2.success(null);
   }

   private void handleSetFlushBatchSize(MethodCall var1, MethodChannel.Result var2) {
      int var3 = var1.<Integer>argument("flushBatchSize");
      this.mixpanel.setFlushBatchSize(var3);
      var2.success(null);
   }

   private void handleSetGroup(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("groupKey");
      Object var4 = var1.argument("groupID");
      this.mixpanel.setGroup(var3, var4);
      var2.success(null);
   }

   private void handleSetLoggingEnabled(MethodCall var1, MethodChannel.Result var2) {
      Boolean var3 = var1.argument("loggingEnabled");
      this.mixpanel.setEnableLogging(var3);
      var2.success(null);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void handleSetOnce(MethodCall var1, MethodChannel.Result var2) {
      Map var3 = var1.argument("properties");

      JSONObject var4;
      try {
         var4 = new JSONObject;
      } catch (JSONException var7) {
         var2.error("MixpanelFlutterException", var7.getLocalizedMessage(), null);
         return;
      }

      Map var8 = var3;
      if (var3 == null) {
         try {
            var8 = EMPTY_HASHMAP;
         } catch (JSONException var6) {
            var2.error("MixpanelFlutterException", var6.getLocalizedMessage(), null);
            return;
         }
      }

      try {
         var4./* $VF: Unable to resugar constructor */<init>(var8);
         var9 = MixpanelFlutterHelper.getMergedProperties(var4, this.mixpanelProperties);
      } catch (JSONException var5) {
         var2.error("MixpanelFlutterException", var5.getLocalizedMessage(), null);
         return;
      }

      this.mixpanel.getPeople().setOnce(var9);
      var2.success(null);
   }

   private void handleSetServerURL(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("serverURL");
      this.mixpanel.setServerURL(var3);
      var2.success(null);
   }

   private void handleSetUseIpAddressForGeolocation(MethodCall var1, MethodChannel.Result var2) {
      Boolean var3 = var1.argument("useIpAddressForGeolocation");
      this.mixpanel.setUseIpAddressForGeolocation(var3);
      var2.success(null);
   }

   private void handleTimeEvent(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("eventName");
      this.mixpanel.timeEvent(var3);
      var2.success(null);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void handleTrack(MethodCall var1, MethodChannel.Result var2) {
      String var4 = var1.argument("eventName");
      Map var3 = var1.argument("properties");

      JSONObject var5;
      try {
         var5 = new JSONObject;
      } catch (JSONException var8) {
         var2.error("MixpanelFlutterException", var8.getLocalizedMessage(), null);
         return;
      }

      Map var9 = var3;
      if (var3 == null) {
         try {
            var9 = EMPTY_HASHMAP;
         } catch (JSONException var7) {
            var2.error("MixpanelFlutterException", var7.getLocalizedMessage(), null);
            return;
         }
      }

      try {
         var5./* $VF: Unable to resugar constructor */<init>(var9);
         var10 = MixpanelFlutterHelper.getMergedProperties(var5, this.mixpanelProperties);
      } catch (JSONException var6) {
         var2.error("MixpanelFlutterException", var6.getLocalizedMessage(), null);
         return;
      }

      this.mixpanel.track(var4, var10);
      var2.success(null);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void handleTrackCharge(MethodCall var1, MethodChannel.Result var2) {
      double var3 = var1.<Double>argument("amount");
      Map var5 = var1.argument("properties");

      JSONObject var6;
      try {
         var6 = new JSONObject;
      } catch (JSONException var9) {
         var2.error("MixpanelFlutterException", var9.getLocalizedMessage(), null);
         return;
      }

      Map var10 = var5;
      if (var5 == null) {
         try {
            var10 = EMPTY_HASHMAP;
         } catch (JSONException var8) {
            var2.error("MixpanelFlutterException", var8.getLocalizedMessage(), null);
            return;
         }
      }

      try {
         var6./* $VF: Unable to resugar constructor */<init>(var10);
         var11 = MixpanelFlutterHelper.getMergedProperties(var6, this.mixpanelProperties);
      } catch (JSONException var7) {
         var2.error("MixpanelFlutterException", var7.getLocalizedMessage(), null);
         return;
      }

      this.mixpanel.getPeople().trackCharge(var3, var11);
      var2.success(null);
   }

   private void handleTrackWithGroups(MethodCall var1, MethodChannel.Result var2) {
      String var4 = var1.argument("eventName");
      Map var3 = var1.argument("properties");
      Map var5 = var1.argument("groups");
      this.mixpanel.trackWithGroups(var4, var3, var5);
      var2.success(null);
   }

   private void handleUnion(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("name");
      ArrayList var4 = var1.argument("value");
      this.mixpanel.getPeople().union(var3, new JSONArray(var4));
      var2.success(null);
   }

   private void handleUnregisterSuperProperty(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("propertyName");
      this.mixpanel.unregisterSuperProperty(var3);
      var2.success(null);
   }

   private void handleUnset(MethodCall var1, MethodChannel.Result var2) {
      String var3 = var1.argument("name");
      this.mixpanel.getPeople().unset(var3);
      var2.success(null);
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.channel = new MethodChannel(var1.getBinaryMessenger(), "mixpanel_flutter", new StandardMethodCodec(new MixpanelMessageCodec()));
      this.context = var1.getApplicationContext();
      this.channel.setMethodCallHandler(this);
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.channel.setMethodCallHandler(null);
   }

   @Override
   public void onMethodCall(MethodCall var1, MethodChannel.Result var2) {
      String var5 = var1.method;
      var5.hashCode();
      int var4 = var5.hashCode();
      byte var3 = -1;
      switch (var4) {
         case -2031738579:
            if (var5.equals("getDistinctId")) {
               var3 = 0;
            }
            break;
         case -1411068134:
            if (var5.equals("append")) {
               var3 = 1;
            }
            break;
         case -1368303227:
            if (var5.equals("groupUnionProperty")) {
               var3 = 2;
            }
            break;
         case -1339651217:
            if (var5.equals("increment")) {
               var3 = 3;
            }
            break;
         case -1258042786:
            if (var5.equals("addGroup")) {
               var3 = 4;
            }
            break;
         case -1191829767:
            if (var5.equals("groupSetPropertyOnce")) {
               var3 = 5;
            }
            break;
         case -1155578643:
            if (var5.equals("hasOptedOutTracking")) {
               var3 = 6;
            }
            break;
         case -1144040556:
            if (var5.equals("deleteGroup")) {
               var3 = 7;
            }
            break;
         case -1140038764:
            if (var5.equals("unregisterSuperProperty")) {
               var3 = 8;
            }
            break;
         case -984009243:
            if (var5.equals("trackWithGroups")) {
               var3 = 9;
            }
            break;
         case -981701777:
            if (var5.equals("eventElapsedTime")) {
               var3 = 10;
            }
            break;
         case -944270602:
            if (var5.equals("groupSetProperties")) {
               var3 = 11;
            }
            break;
         case -934610812:
            if (var5.equals("remove")) {
               var3 = 12;
            }
            break;
         case -608772238:
            if (var5.equals("optOutTracking")) {
               var3 = 13;
            }
            break;
         case -367675015:
            if (var5.equals("groupRemovePropertyValue")) {
               var3 = 14;
            }
            break;
         case -315768741:
            if (var5.equals("removeGroup")) {
               var3 = 15;
            }
            break;
         case -300506609:
            if (var5.equals("optInTracking")) {
               var3 = 16;
            }
            break;
         case -135762164:
            if (var5.equals("identify")) {
               var3 = 17;
            }
            break;
         case 113762:
            if (var5.equals("set")) {
               var3 = 18;
            }
            break;
         case 12707789:
            if (var5.equals("timeEvent")) {
               var3 = 19;
            }
            break;
         case 92902992:
            if (var5.equals("alias")) {
               var3 = 20;
            }
            break;
         case 97532676:
            if (var5.equals("flush")) {
               var3 = 21;
            }
            break;
         case 108404047:
            if (var5.equals("reset")) {
               var3 = 22;
            }
            break;
         case 110621003:
            if (var5.equals("track")) {
               var3 = 23;
            }
            break;
         case 111433423:
            if (var5.equals("union")) {
               var3 = 24;
            }
            break;
         case 111442729:
            if (var5.equals("unset")) {
               var3 = 25;
            }
            break;
         case 215518245:
            if (var5.equals("setUseIpAddressForGeolocation")) {
               var3 = 26;
            }
            break;
         case 567596004:
            if (var5.equals("setLoggingEnabled")) {
               var3 = 27;
            }
            break;
         case 707505932:
            if (var5.equals("registerSuperPropertiesOnce")) {
               var3 = 28;
            }
            break;
         case 715248946:
            if (var5.equals("clearCharges")) {
               var3 = 29;
            }
            break;
         case 785292255:
            if (var5.equals("trackCharge")) {
               var3 = 30;
            }
            break;
         case 871091088:
            if (var5.equals("initialize")) {
               var3 = 31;
            }
            break;
         case 884472065:
            if (var5.equals("clearSuperProperties")) {
               var3 = 32;
            }
            break;
         case 907033785:
            if (var5.equals("setFlushBatchSize")) {
               var3 = 33;
            }
            break;
         case 935528939:
            if (var5.equals("registerSuperProperties")) {
               var3 = 34;
            }
            break;
         case 1341939946:
            if (var5.equals("setServerURL")) {
               var3 = 35;
            }
            break;
         case 1393342269:
            if (var5.equals("setGroup")) {
               var3 = 36;
            }
            break;
         case 1764628502:
            if (var5.equals("deleteUser")) {
               var3 = 37;
            }
            break;
         case 1815071960:
            if (var5.equals("getSuperProperties")) {
               var3 = 38;
            }
            break;
         case 1847362527:
            if (var5.equals("groupUnsetProperty")) {
               var3 = 39;
            }
            break;
         case 1984843267:
            if (var5.equals("setOnce")) {
               var3 = 40;
            }
      }

      switch (var3) {
         case 0:
            this.handleGetDistinctId(var1, var2);
            break;
         case 1:
            this.handleAppend(var1, var2);
            break;
         case 2:
            this.handleGroupUnionProperty(var1, var2);
            break;
         case 3:
            this.handleIncrement(var1, var2);
            break;
         case 4:
            this.handleAddGroup(var1, var2);
            break;
         case 5:
            this.handleGroupSetPropertyOnce(var1, var2);
            break;
         case 6:
            this.handleHasOptedOutTracking(var1, var2);
            break;
         case 7:
            this.handleDeleteGroup(var1, var2);
            break;
         case 8:
            this.handleUnregisterSuperProperty(var1, var2);
            break;
         case 9:
            this.handleTrackWithGroups(var1, var2);
            break;
         case 10:
            this.handleEventElapsedTime(var1, var2);
            break;
         case 11:
            this.handleGroupSetProperties(var1, var2);
            break;
         case 12:
            this.handleRemove(var1, var2);
            break;
         case 13:
            this.handleOptOutTracking(var1, var2);
            break;
         case 14:
            this.handleGroupRemovePropertyValue(var1, var2);
            break;
         case 15:
            this.handleRemoveGroup(var1, var2);
            break;
         case 16:
            this.handleOptInTracking(var1, var2);
            break;
         case 18:
            this.handleSet(var1, var2);
            break;
         case 19:
            this.handleTimeEvent(var1, var2);
            break;
         case 20:
            this.handleAlias(var1, var2);
            break;
         case 21:
            this.handleFlush(var1, var2);
            break;
         case 22:
            this.handleReset(var1, var2);
            break;
         case 23:
            this.handleTrack(var1, var2);
            break;
         case 24:
            this.handleUnion(var1, var2);
            break;
         case 25:
            this.handleUnset(var1, var2);
            break;
         case 26:
            this.handleSetUseIpAddressForGeolocation(var1, var2);
            break;
         case 27:
            this.handleSetLoggingEnabled(var1, var2);
            break;
         case 28:
            this.handleRegisterSuperPropertiesOnce(var1, var2);
            break;
         case 29:
            this.handleClearCharges(var1, var2);
            break;
         case 30:
            this.handleTrackCharge(var1, var2);
            break;
         case 31:
            this.handleInitialize(var1, var2);
            break;
         case 32:
            this.handleClearSuperProperties(var1, var2);
            break;
         case 33:
            this.handleSetFlushBatchSize(var1, var2);
         case 17:
            this.handleIdentify(var1, var2);
            break;
         case 34:
            this.handleRegisterSuperProperties(var1, var2);
            break;
         case 35:
            this.handleSetServerURL(var1, var2);
            break;
         case 36:
            this.handleSetGroup(var1, var2);
            break;
         case 37:
            this.handleDeleteUser(var1, var2);
            break;
         case 38:
            this.handleGetSuperProperties(var1, var2);
            break;
         case 39:
            this.handleGroupUnsetProperty(var1, var2);
            break;
         case 40:
            this.handleSetOnce(var1, var2);
            break;
         default:
            var2.notImplemented();
      }
   }
}
