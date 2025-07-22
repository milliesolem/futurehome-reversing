package io.flutter.embedding.engine.systemchannels;

import android.os.Bundle;
import android.os.Build.VERSION;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.editing.TextEditingDelta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TextInputChannel {
   private static final String TAG = "TextInputChannel";
   public final MethodChannel channel;
   final MethodChannel.MethodCallHandler parsingMethodHandler;
   private TextInputChannel.TextInputMethodHandler textInputMethodHandler;

   public TextInputChannel(DartExecutor var1) {
      MethodChannel.MethodCallHandler var2 = new MethodChannel.MethodCallHandler(this) {
         final TextInputChannel this$0;

         {
            this.this$0 = var1;
         }

         // $VF: Duplicated exception handlers to handle obfuscated exceptions
         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            if (this.this$0.textInputMethodHandler != null) {
               String var11 = var1.method;
               JSONObject var23 = (JSONObject)var1.arguments;
               StringBuilder var12 = new StringBuilder("Received '");
               var12.append(var11);
               var12.append("' message.");
               Log.v("TextInputChannel", var12.toString());
               var11.hashCode();
               int var9 = var11.hashCode();
               byte var8 = 0;
               int var7 = -1;
               switch (var9) {
                  case -1779068172:
                     if (var11.equals("TextInput.setPlatformViewClient")) {
                        var7 = 0;
                     }
                     break;
                  case -1015421462:
                     if (var11.equals("TextInput.setEditingState")) {
                        var7 = 1;
                     }
                     break;
                  case -37561188:
                     if (var11.equals("TextInput.setClient")) {
                        var7 = 2;
                     }
                     break;
                  case 270476819:
                     if (var11.equals("TextInput.hide")) {
                        var7 = 3;
                     }
                     break;
                  case 270803918:
                     if (var11.equals("TextInput.show")) {
                        var7 = 4;
                     }
                     break;
                  case 649192816:
                     if (var11.equals("TextInput.sendAppPrivateCommand")) {
                        var7 = 5;
                     }
                     break;
                  case 1204752139:
                     if (var11.equals("TextInput.setEditableSizeAndTransform")) {
                        var7 = 6;
                     }
                     break;
                  case 1727570905:
                     if (var11.equals("TextInput.finishAutofillContext")) {
                        var7 = 7;
                     }
                     break;
                  case 1904427655:
                     if (var11.equals("TextInput.clearClient")) {
                        var7 = 8;
                     }
                     break;
                  case 2113369584:
                     if (var11.equals("TextInput.requestAutofill")) {
                        var7 = 9;
                     }
               }

               switch (var7) {
                  case 0:
                     try {
                        var23 = var23;
                        var7 = var23.getInt("platformViewId");
                        boolean var10 = var23.optBoolean("usesVirtualDisplay", false);
                        this.this$0.textInputMethodHandler.setPlatformViewClient(var7, var10);
                        var2x.success(null);
                     } catch (JSONException var13) {
                        var2x.error("error", var13.getMessage(), null);
                     }
                     break;
                  case 1:
                     try {
                        var23 = var23;
                        this.this$0.textInputMethodHandler.setEditingState(TextInputChannel.TextEditState.fromJson(var23));
                        var2x.success(null);
                     } catch (JSONException var14) {
                        var2x.error("error", var14.getMessage(), null);
                     }
                     break;
                  case 2:
                     try {
                        JSONArray var29 = (JSONArray)var23;
                        var7 = var29.getInt(0);
                        var23 = var29.getJSONObject(1);
                        this.this$0.textInputMethodHandler.setClient(var7, TextInputChannel.Configuration.fromJson(var23));
                        var2x.success(null);
                        break;
                     } catch (JSONException var21) {
                        var23 = var21;
                     } catch (NoSuchFieldException var22) {
                        var23 = var22;
                     }

                     var2x.error("error", var23.getMessage(), null);
                     break;
                  case 3:
                     this.this$0.textInputMethodHandler.hide();
                     var2x.success(null);
                     break;
                  case 4:
                     this.this$0.textInputMethodHandler.show();
                     var2x.success(null);
                     break;
                  case 5:
                     try {
                        var23 = var23;
                        var11 = var23.getString("action");
                        var38 = var23.getString("data");
                     } catch (JSONException var19) {
                        var2x.error("error", var19.getMessage(), null);
                        break;
                     }

                     label111: {
                        if (var38 != null) {
                           try {
                              if (!var38.isEmpty()) {
                                 var27 = new Bundle();
                                 var27.putString("data", var38);
                                 break label111;
                              }
                           } catch (JSONException var17) {
                              var2x.error("error", var17.getMessage(), null);
                              break;
                           }
                        }

                        var27 = null;
                     }

                     try {
                        this.this$0.textInputMethodHandler.sendAppPrivateCommand(var11, var27);
                        var2x.success(null);
                     } catch (JSONException var15) {
                        var2x.error("error", var15.getMessage(), null);
                     }
                     break;
                  case 6:
                     double var3;
                     double var5;
                     try {
                        var23 = var23;
                        var5 = var23.getDouble("width");
                        var3 = var23.getDouble("height");
                        var25 = var23.getJSONArray("transform");
                        var36 = new double[16];
                     } catch (JSONException var20) {
                        var2x.error("error", var20.getMessage(), null);
                        break;
                     }

                     for (int var33 = var8; var33 < 16; var33++) {
                        try {
                           var36[var33] = var25.getDouble(var33);
                        } catch (JSONException var18) {
                           var2x.error("error", var18.getMessage(), null);
                           return;
                        }
                     }

                     try {
                        this.this$0.textInputMethodHandler.setEditableSizeAndTransform(var5, var3, var36);
                        var2x.success(null);
                     } catch (JSONException var16) {
                        var2x.error("error", var16.getMessage(), null);
                     }
                     break;
                  case 7:
                     this.this$0.textInputMethodHandler.finishAutofillContext((Boolean)var23);
                     var2x.success(null);
                     break;
                  case 8:
                     this.this$0.textInputMethodHandler.clearClient();
                     var2x.success(null);
                     break;
                  case 9:
                     this.this$0.textInputMethodHandler.requestAutofill();
                     var2x.success(null);
                     break;
                  default:
                     var2x.notImplemented();
               }
            }
         }
      };
      this.parsingMethodHandler = var2;
      MethodChannel var3 = new MethodChannel(var1, "flutter/textinput", JSONMethodCodec.INSTANCE);
      this.channel = var3;
      var3.setMethodCallHandler(var2);
   }

   private static HashMap<Object, Object> createEditingDeltaJSON(ArrayList<TextEditingDelta> var0) {
      HashMap var1 = new HashMap();
      JSONArray var2 = new JSONArray();
      Iterator var3 = var0.iterator();

      while (var3.hasNext()) {
         var2.put(((TextEditingDelta)var3.next()).toJSON());
      }

      var1.put("deltas", var2);
      return var1;
   }

   private static HashMap<Object, Object> createEditingStateJSON(String var0, int var1, int var2, int var3, int var4) {
      HashMap var5 = new HashMap();
      var5.put("text", var0);
      var5.put("selectionBase", var1);
      var5.put("selectionExtent", var2);
      var5.put("composingBase", var3);
      var5.put("composingExtent", var4);
      return var5;
   }

   public void commitContent(int var1, Map<String, Object> var2) {
      Log.v("TextInputChannel", "Sending 'commitContent' message.");
      this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(var1, "TextInputAction.commitContent", var2));
   }

   public void done(int var1) {
      Log.v("TextInputChannel", "Sending 'done' message.");
      this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(var1, "TextInputAction.done"));
   }

   public void go(int var1) {
      Log.v("TextInputChannel", "Sending 'go' message.");
      this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(var1, "TextInputAction.go"));
   }

   public void newline(int var1) {
      Log.v("TextInputChannel", "Sending 'newline' message.");
      this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(var1, "TextInputAction.newline"));
   }

   public void next(int var1) {
      Log.v("TextInputChannel", "Sending 'next' message.");
      this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(var1, "TextInputAction.next"));
   }

   public void performPrivateCommand(int var1, String var2, Bundle var3) {
      HashMap var4 = new HashMap();
      var4.put("action", var2);
      if (var3 != null) {
         HashMap var6 = new HashMap();

         for (String var8 : var3.keySet()) {
            Object var5 = var3.get(var8);
            if (var5 instanceof byte[]) {
               var6.put(var8, var3.getByteArray(var8));
            } else if (var5 instanceof Byte) {
               var6.put(var8, var3.getByte(var8));
            } else if (var5 instanceof char[]) {
               var6.put(var8, var3.getCharArray(var8));
            } else if (var5 instanceof Character) {
               var6.put(var8, var3.getChar(var8));
            } else if (var5 instanceof CharSequence[]) {
               var6.put(var8, var3.getCharSequenceArray(var8));
            } else if (var5 instanceof CharSequence) {
               var6.put(var8, var3.getCharSequence(var8));
            } else if (var5 instanceof float[]) {
               var6.put(var8, var3.getFloatArray(var8));
            } else if (var5 instanceof Float) {
               var6.put(var8, var3.getFloat(var8));
            }
         }

         var4.put("data", var6);
      }

      this.channel.invokeMethod("TextInputClient.performPrivateCommand", Arrays.asList(var1, var4));
   }

   public void previous(int var1) {
      Log.v("TextInputChannel", "Sending 'previous' message.");
      this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(var1, "TextInputAction.previous"));
   }

   public void requestExistingInputState() {
      this.channel.invokeMethod("TextInputClient.requestExistingInputState", null);
   }

   public void search(int var1) {
      Log.v("TextInputChannel", "Sending 'search' message.");
      this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(var1, "TextInputAction.search"));
   }

   public void send(int var1) {
      Log.v("TextInputChannel", "Sending 'send' message.");
      this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(var1, "TextInputAction.send"));
   }

   public void setTextInputMethodHandler(TextInputChannel.TextInputMethodHandler var1) {
      this.textInputMethodHandler = var1;
   }

   public void unspecifiedAction(int var1) {
      Log.v("TextInputChannel", "Sending 'unspecified' message.");
      this.channel.invokeMethod("TextInputClient.performAction", Arrays.asList(var1, "TextInputAction.unspecified"));
   }

   public void updateEditingState(int var1, String var2, int var3, int var4, int var5, int var6) {
      StringBuilder var7 = new StringBuilder("Sending message to update editing state: \nText: ");
      var7.append(var2);
      var7.append("\nSelection start: ");
      var7.append(var3);
      var7.append("\nSelection end: ");
      var7.append(var4);
      var7.append("\nComposing start: ");
      var7.append(var5);
      var7.append("\nComposing end: ");
      var7.append(var6);
      Log.v("TextInputChannel", var7.toString());
      HashMap var8 = createEditingStateJSON(var2, var3, var4, var5, var6);
      this.channel.invokeMethod("TextInputClient.updateEditingState", Arrays.asList(var1, var8));
   }

   public void updateEditingStateWithDeltas(int var1, ArrayList<TextEditingDelta> var2) {
      StringBuilder var3 = new StringBuilder("Sending message to update editing state with deltas: \nNumber of deltas: ");
      var3.append(var2.size());
      Log.v("TextInputChannel", var3.toString());
      HashMap var4 = createEditingDeltaJSON(var2);
      this.channel.invokeMethod("TextInputClient.updateEditingStateWithDeltas", Arrays.asList(var1, var4));
   }

   public void updateEditingStateWithTag(int var1, HashMap<String, TextInputChannel.TextEditState> var2) {
      StringBuilder var3 = new StringBuilder("Sending message to update editing state for ");
      var3.append(String.valueOf(var2.size()));
      var3.append(" field(s).");
      Log.v("TextInputChannel", var3.toString());
      HashMap var7 = new HashMap();

      for (Entry var6 : var2.entrySet()) {
         TextInputChannel.TextEditState var5 = (TextInputChannel.TextEditState)var6.getValue();
         var7.put((String)var6.getKey(), createEditingStateJSON(var5.text, var5.selectionStart, var5.selectionEnd, -1, -1));
      }

      this.channel.invokeMethod("TextInputClient.updateEditingStateWithTag", Arrays.asList(var1, var7));
   }

   public static class Configuration {
      public final String actionLabel;
      public final boolean autocorrect;
      public final TextInputChannel.Configuration.Autofill autofill;
      public final String[] contentCommitMimeTypes;
      public final boolean enableDeltaModel;
      public final boolean enableIMEPersonalizedLearning;
      public final boolean enableSuggestions;
      public final TextInputChannel.Configuration[] fields;
      public final Integer inputAction;
      public final TextInputChannel.InputType inputType;
      public final boolean obscureText;
      public final TextInputChannel.TextCapitalization textCapitalization;

      public Configuration(
         boolean var1,
         boolean var2,
         boolean var3,
         boolean var4,
         boolean var5,
         TextInputChannel.TextCapitalization var6,
         TextInputChannel.InputType var7,
         Integer var8,
         String var9,
         TextInputChannel.Configuration.Autofill var10,
         String[] var11,
         TextInputChannel.Configuration[] var12
      ) {
         this.obscureText = var1;
         this.autocorrect = var2;
         this.enableSuggestions = var3;
         this.enableIMEPersonalizedLearning = var4;
         this.enableDeltaModel = var5;
         this.textCapitalization = var6;
         this.inputType = var7;
         this.inputAction = var8;
         this.actionLabel = var9;
         this.autofill = var10;
         this.contentCommitMimeTypes = var11;
         this.fields = var12;
      }

      public static TextInputChannel.Configuration fromJson(JSONObject var0) throws JSONException, NoSuchFieldException {
         String var10 = var0.getString("inputAction");
         if (var10 == null) {
            throw new JSONException("Configuration JSON missing 'inputAction' property.");
         } else {
            boolean var4 = var0.isNull("fields");
            byte var2 = 0;
            Object var11 = null;
            TextInputChannel.Configuration[] var9;
            if (!var4) {
               JSONArray var12 = var0.getJSONArray("fields");
               int var3 = var12.length();
               var9 = new TextInputChannel.Configuration[var3];

               for (int var1 = 0; var1 < var3; var1++) {
                  var9[var1] = fromJson(var12.getJSONObject(var1));
               }
            } else {
               var9 = null;
            }

            Integer var21 = inputActionFromTextInputAction(var10);
            ArrayList var13 = new ArrayList();
            JSONArray var19;
            if (var0.isNull("contentCommitMimeTypes")) {
               var19 = null;
            } else {
               var19 = var0.getJSONArray("contentCommitMimeTypes");
            }

            if (var19 != null) {
               for (int var17 = var2; var17 < var19.length(); var17++) {
                  var13.add(var19.optString(var17));
               }
            }

            var4 = var0.optBoolean("obscureText");
            boolean var5 = var0.optBoolean("autocorrect", true);
            boolean var6 = var0.optBoolean("enableSuggestions");
            boolean var7 = var0.optBoolean("enableIMEPersonalizedLearning");
            boolean var8 = var0.optBoolean("enableDeltaModel");
            TextInputChannel.TextCapitalization var14 = TextInputChannel.TextCapitalization.fromValue(var0.getString("textCapitalization"));
            TextInputChannel.InputType var15 = TextInputChannel.InputType.fromJson(var0.getJSONObject("inputType"));
            if (var0.isNull("actionLabel")) {
               var10 = null;
            } else {
               var10 = var0.getString("actionLabel");
            }

            TextInputChannel.Configuration.Autofill var16;
            if (var0.isNull("autofill")) {
               var16 = (TextInputChannel.Configuration.Autofill)var11;
            } else {
               var16 = TextInputChannel.Configuration.Autofill.fromJson(var0.getJSONObject("autofill"));
            }

            return new TextInputChannel.Configuration(
               var4, var5, var6, var7, var8, var14, var15, var21, var10, var16, var13.toArray(new String[var13.size()]), var9
            );
         }
      }

      private static Integer inputActionFromTextInputAction(String var0) {
         byte var1;
         Integer var3;
         Integer var4;
         label56: {
            var0.hashCode();
            int var2 = var0.hashCode();
            var1 = 1;
            var3 = 1;
            var4 = 0;
            switch (var2) {
               case -810971940:
                  if (var0.equals("TextInputAction.unspecified")) {
                     var1 = 0;
                     break label56;
                  }
                  break;
               case -737377923:
                  if (var0.equals("TextInputAction.done")) {
                     break label56;
                  }
                  break;
               case -737089298:
                  if (var0.equals("TextInputAction.next")) {
                     var1 = 2;
                     break label56;
                  }
                  break;
               case -737080013:
                  if (var0.equals("TextInputAction.none")) {
                     var1 = 3;
                     break label56;
                  }
                  break;
               case -736940669:
                  if (var0.equals("TextInputAction.send")) {
                     var1 = 4;
                     break label56;
                  }
                  break;
               case 469250275:
                  if (var0.equals("TextInputAction.search")) {
                     var1 = 5;
                     break label56;
                  }
                  break;
               case 1241689507:
                  if (var0.equals("TextInputAction.go")) {
                     var1 = 6;
                     break label56;
                  }
                  break;
               case 1539450297:
                  if (var0.equals("TextInputAction.newline")) {
                     var1 = 7;
                     break label56;
                  }
                  break;
               case 2110497650:
                  if (var0.equals("TextInputAction.previous")) {
                     var1 = 8;
                     break label56;
                  }
            }

            var1 = -1;
         }

         switch (var1) {
            case 0:
               return var4;
            case 1:
               return 6;
            case 2:
               return 5;
            case 3:
               return var3;
            case 4:
               return 4;
            case 5:
               return 3;
            case 6:
               return 2;
            case 7:
               return var3;
            case 8:
               return 7;
            default:
               return var4;
         }
      }

      public static class Autofill {
         public final TextInputChannel.TextEditState editState;
         public final String hintText;
         public final String[] hints;
         public final String uniqueIdentifier;

         public Autofill(String var1, String[] var2, String var3, TextInputChannel.TextEditState var4) {
            this.uniqueIdentifier = var1;
            this.hints = var2;
            this.hintText = var3;
            this.editState = var4;
         }

         public static TextInputChannel.Configuration.Autofill fromJson(JSONObject var0) throws JSONException, NoSuchFieldException {
            String var4 = var0.getString("uniqueIdentifier");
            JSONArray var3 = var0.getJSONArray("hints");
            String var2;
            if (var0.isNull("hintText")) {
               var2 = null;
            } else {
               var2 = var0.getString("hintText");
            }

            JSONObject var5 = var0.getJSONObject("editingValue");
            String[] var6 = new String[var3.length()];

            for (int var1 = 0; var1 < var3.length(); var1++) {
               var6[var1] = translateAutofillHint(var3.getString(var1));
            }

            return new TextInputChannel.Configuration.Autofill(var4, var6, var2, TextInputChannel.TextEditState.fromJson(var5));
         }

         private static String translateAutofillHint(String var0) {
            int var2 = VERSION.SDK_INT;
            byte var1 = 26;
            if (var2 < 26) {
               return var0;
            } else {
               label194: {
                  var0.hashCode();
                  switch (var0.hashCode()) {
                     case -2058889126:
                        if (var0.equals("birthdayYear")) {
                           var1 = 0;
                           break label194;
                        }
                        break;
                     case -1917283616:
                        if (var0.equals("oneTimeCode")) {
                           var1 = 1;
                           break label194;
                        }
                        break;
                     case -1844815832:
                        if (var0.equals("creditCardExpirationMonth")) {
                           var1 = 2;
                           break label194;
                        }
                        break;
                     case -1825589953:
                        if (var0.equals("telephoneNumberNational")) {
                           var1 = 3;
                           break label194;
                        }
                        break;
                     case -1821235109:
                        if (var0.equals("newPassword")) {
                           var1 = 4;
                           break label194;
                        }
                        break;
                     case -1757573738:
                        if (var0.equals("creditCardSecurityCode")) {
                           var1 = 5;
                           break label194;
                        }
                        break;
                     case -1682373820:
                        if (var0.equals("creditCardExpirationDay")) {
                           var1 = 6;
                           break label194;
                        }
                        break;
                     case -1658955742:
                        if (var0.equals("fullStreetAddress")) {
                           var1 = 7;
                           break label194;
                        }
                        break;
                     case -1567118045:
                        if (var0.equals("telephoneNumberDevice")) {
                           var1 = 8;
                           break label194;
                        }
                        break;
                     case -1476752575:
                        if (var0.equals("countryName")) {
                           var1 = 9;
                           break label194;
                        }
                        break;
                     case -1413737489:
                        if (var0.equals("middleInitial")) {
                           var1 = 10;
                           break label194;
                        }
                        break;
                     case -1377792129:
                        if (var0.equals("addressCity")) {
                           var1 = 11;
                           break label194;
                        }
                        break;
                     case -1249512767:
                        if (var0.equals("gender")) {
                           var1 = 12;
                           break label194;
                        }
                        break;
                     case -1186060294:
                        if (var0.equals("postalAddressExtendedPostalCode")) {
                           var1 = 13;
                           break label194;
                        }
                        break;
                     case -1151034798:
                        if (var0.equals("creditCardNumber")) {
                           var1 = 14;
                           break label194;
                        }
                        break;
                     case -835992323:
                        if (var0.equals("namePrefix")) {
                           var1 = 15;
                           break label194;
                        }
                        break;
                     case -818219584:
                        if (var0.equals("middleName")) {
                           var1 = 16;
                           break label194;
                        }
                        break;
                     case -747304516:
                        if (var0.equals("nameSuffix")) {
                           var1 = 17;
                           break label194;
                        }
                        break;
                     case -613980922:
                        if (var0.equals("creditCardExpirationDate")) {
                           var1 = 18;
                           break label194;
                        }
                        break;
                     case -613352043:
                        if (var0.equals("creditCardExpirationYear")) {
                           var1 = 19;
                           break label194;
                        }
                        break;
                     case -549230602:
                        if (var0.equals("telephoneNumberCountryCode")) {
                           var1 = 20;
                           break label194;
                        }
                        break;
                     case -265713450:
                        if (var0.equals("username")) {
                           var1 = 21;
                           break label194;
                        }
                        break;
                     case 3373707:
                        if (var0.equals("name")) {
                           var1 = 22;
                           break label194;
                        }
                        break;
                     case 96619420:
                        if (var0.equals("email")) {
                           var1 = 23;
                           break label194;
                        }
                        break;
                     case 253202685:
                        if (var0.equals("addressState")) {
                           var1 = 24;
                           break label194;
                        }
                        break;
                     case 588174851:
                        if (var0.equals("birthdayMonth")) {
                           var1 = 25;
                           break label194;
                        }
                        break;
                     case 798554127:
                        if (var0.equals("familyName")) {
                           break label194;
                        }
                        break;
                     case 892233837:
                        if (var0.equals("telephoneNumber")) {
                           var1 = 27;
                           break label194;
                        }
                        break;
                     case 991032982:
                        if (var0.equals("newUsername")) {
                           var1 = 28;
                           break label194;
                        }
                        break;
                     case 1069376125:
                        if (var0.equals("birthday")) {
                           var1 = 29;
                           break label194;
                        }
                        break;
                     case 1216985755:
                        if (var0.equals("password")) {
                           var1 = 30;
                           break label194;
                        }
                        break;
                     case 1469046696:
                        if (var0.equals("givenName")) {
                           var1 = 31;
                           break label194;
                        }
                        break;
                     case 1662667945:
                        if (var0.equals("postalAddress")) {
                           var1 = 32;
                           break label194;
                        }
                        break;
                     case 1921869058:
                        if (var0.equals("postalAddressExtended")) {
                           var1 = 33;
                           break label194;
                        }
                        break;
                     case 2011152728:
                        if (var0.equals("postalCode")) {
                           var1 = 34;
                           break label194;
                        }
                        break;
                     case 2011773919:
                        if (var0.equals("birthdayDay")) {
                           var1 = 35;
                           break label194;
                        }
                  }

                  var1 = -1;
               }

               switch (var1) {
                  case 0:
                     return "birthDateYear";
                  case 1:
                     return "smsOTPCode";
                  case 2:
                     return "creditCardExpirationMonth";
                  case 3:
                     return "phoneNational";
                  case 4:
                     return "newPassword";
                  case 5:
                     return "creditCardSecurityCode";
                  case 6:
                     return "creditCardExpirationDay";
                  case 7:
                     return "streetAddress";
                  case 8:
                     return "phoneNumberDevice";
                  case 9:
                     return "addressCountry";
                  case 10:
                     return "personMiddleInitial";
                  case 11:
                     return "addressLocality";
                  case 12:
                     return "gender";
                  case 13:
                     return "extendedPostalCode";
                  case 14:
                     return "creditCardNumber";
                  case 15:
                     return "personNamePrefix";
                  case 16:
                     return "personMiddleName";
                  case 17:
                     return "personNameSuffix";
                  case 18:
                     return "creditCardExpirationDate";
                  case 19:
                     return "creditCardExpirationYear";
                  case 20:
                     return "phoneCountryCode";
                  case 21:
                     return "username";
                  case 22:
                     return "personName";
                  case 23:
                     return "emailAddress";
                  case 24:
                     return "addressRegion";
                  case 25:
                     return "birthDateMonth";
                  case 26:
                     return "personFamilyName";
                  case 27:
                     return "phoneNumber";
                  case 28:
                     return "newUsername";
                  case 29:
                     return "birthDateFull";
                  case 30:
                     return "password";
                  case 31:
                     return "personGivenName";
                  case 32:
                     return "postalAddress";
                  case 33:
                     return "extendedAddress";
                  case 34:
                     return "postalCode";
                  case 35:
                     return "birthDateDay";
                  default:
                     return var0;
               }
            }
         }
      }
   }

   public static class InputType {
      public final boolean isDecimal;
      public final boolean isSigned;
      public final TextInputChannel.TextInputType type;

      public InputType(TextInputChannel.TextInputType var1, boolean var2, boolean var3) {
         this.type = var1;
         this.isSigned = var2;
         this.isDecimal = var3;
      }

      public static TextInputChannel.InputType fromJson(JSONObject var0) throws JSONException, NoSuchFieldException {
         return new TextInputChannel.InputType(
            TextInputChannel.TextInputType.fromValue(var0.getString("name")), var0.optBoolean("signed", false), var0.optBoolean("decimal", false)
         );
      }
   }

   public static enum TextCapitalization {
      CHARACTERS("TextCapitalization.characters"),
      NONE("TextCapitalization.none"),
      SENTENCES("TextCapitalization.sentences"),
      WORDS("TextCapitalization.words");

      private static final TextInputChannel.TextCapitalization[] $VALUES = $values();
      private final String encodedName;

      private TextCapitalization(String var3) {
         this.encodedName = var3;
      }

      static TextInputChannel.TextCapitalization fromValue(String var0) throws NoSuchFieldException {
         for (TextInputChannel.TextCapitalization var3 : values()) {
            if (var3.encodedName.equals(var0)) {
               return var3;
            }
         }

         StringBuilder var5 = new StringBuilder("No such TextCapitalization: ");
         var5.append(var0);
         throw new NoSuchFieldException(var5.toString());
      }
   }

   public static class TextEditState {
      public final int composingEnd;
      public final int composingStart;
      public final int selectionEnd;
      public final int selectionStart;
      public final String text;

      public TextEditState(String var1, int var2, int var3, int var4, int var5) throws IndexOutOfBoundsException {
         if (var2 == -1 && var3 == -1 || var2 >= 0 && var3 >= 0) {
            if (var4 == -1 && var5 == -1 || var4 >= 0 && var4 <= var5) {
               if (var5 > var1.length()) {
                  StringBuilder var10 = new StringBuilder("invalid composing start: ");
                  var10.append(String.valueOf(var4));
                  throw new IndexOutOfBoundsException(var10.toString());
               } else if (var2 > var1.length()) {
                  StringBuilder var9 = new StringBuilder("invalid selection start: ");
                  var9.append(String.valueOf(var2));
                  throw new IndexOutOfBoundsException(var9.toString());
               } else if (var3 <= var1.length()) {
                  this.text = var1;
                  this.selectionStart = var2;
                  this.selectionEnd = var3;
                  this.composingStart = var4;
                  this.composingEnd = var5;
               } else {
                  StringBuilder var8 = new StringBuilder("invalid selection end: ");
                  var8.append(String.valueOf(var3));
                  throw new IndexOutOfBoundsException(var8.toString());
               }
            } else {
               StringBuilder var7 = new StringBuilder("invalid composing range: (");
               var7.append(String.valueOf(var4));
               var7.append(", ");
               var7.append(String.valueOf(var5));
               var7.append(")");
               throw new IndexOutOfBoundsException(var7.toString());
            }
         } else {
            StringBuilder var6 = new StringBuilder("invalid selection: (");
            var6.append(String.valueOf(var2));
            var6.append(", ");
            var6.append(String.valueOf(var3));
            var6.append(")");
            throw new IndexOutOfBoundsException(var6.toString());
         }
      }

      public static TextInputChannel.TextEditState fromJson(JSONObject var0) throws JSONException {
         return new TextInputChannel.TextEditState(
            var0.getString("text"), var0.getInt("selectionBase"), var0.getInt("selectionExtent"), var0.getInt("composingBase"), var0.getInt("composingExtent")
         );
      }

      public boolean hasComposing() {
         int var1 = this.composingStart;
         boolean var2;
         if (var1 >= 0 && this.composingEnd > var1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean hasSelection() {
         boolean var1;
         if (this.selectionStart >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   public interface TextInputMethodHandler {
      void clearClient();

      void finishAutofillContext(boolean var1);

      void hide();

      void requestAutofill();

      void sendAppPrivateCommand(String var1, Bundle var2);

      void setClient(int var1, TextInputChannel.Configuration var2);

      void setEditableSizeAndTransform(double var1, double var3, double[] var5);

      void setEditingState(TextInputChannel.TextEditState var1);

      void setPlatformViewClient(int var1, boolean var2);

      void show();
   }

   public static enum TextInputType {
      DATETIME("TextInputType.datetime"),
      EMAIL_ADDRESS("TextInputType.emailAddress"),
      MULTILINE("TextInputType.multiline"),
      NAME("TextInputType.name"),
      NONE("TextInputType.none"),
      NUMBER("TextInputType.number"),
      PHONE("TextInputType.phone"),
      POSTAL_ADDRESS("TextInputType.address"),
      TEXT("TextInputType.text"),
      URL("TextInputType.url"),
      VISIBLE_PASSWORD("TextInputType.visiblePassword");

      private static final TextInputChannel.TextInputType[] $VALUES = $values();
      private final String encodedName;

      private TextInputType(String var3) {
         this.encodedName = var3;
      }

      static TextInputChannel.TextInputType fromValue(String var0) throws NoSuchFieldException {
         for (TextInputChannel.TextInputType var4 : values()) {
            if (var4.encodedName.equals(var0)) {
               return var4;
            }
         }

         StringBuilder var5 = new StringBuilder("No such TextInputType: ");
         var5.append(var0);
         throw new NoSuchFieldException(var5.toString());
      }
   }
}
