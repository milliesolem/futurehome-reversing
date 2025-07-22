package io.flutter.plugins.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class LegacySharedPreferencesPlugin implements FlutterPlugin, Messages.SharedPreferencesApi {
   private static final String BIG_INTEGER_PREFIX = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBCaWdJbnRlZ2Vy";
   private static final String DOUBLE_PREFIX = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBEb3VibGUu";
   private static final String JSON_LIST_IDENTIFIER = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu!";
   private static final String LIST_IDENTIFIER = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu";
   private static final String SHARED_PREFERENCES_NAME = "FlutterSharedPreferences";
   private static final String TAG = "SharedPreferencesPlugin";
   private final SharedPreferencesListEncoder listEncoder;
   private SharedPreferences preferences;

   public LegacySharedPreferencesPlugin() {
      this(new LegacySharedPreferencesPlugin.ListEncoder());
   }

   LegacySharedPreferencesPlugin(SharedPreferencesListEncoder var1) {
      this.listEncoder = var1;
   }

   private Map<String, Object> getAllPrefs(String var1, Set<String> var2) throws RuntimeException {
      Map var4 = this.preferences.getAll();
      HashMap var6 = new HashMap();

      for (String var5 : var4.keySet()) {
         if (var5.startsWith(var1) && (var2 == null || var2.contains(var5))) {
            var6.put(var5, this.transformPref(var5, Objects.requireNonNull(var4.get(var5))));
         }
      }

      return var6;
   }

   private void setUp(BinaryMessenger var1, Context var2) {
      this.preferences = var2.getSharedPreferences("FlutterSharedPreferences", 0);

      try {
         Messages$SharedPreferencesApi$_CC.setUp(var1, this);
      } catch (Exception var3) {
         Log.e("SharedPreferencesPlugin", "Received exception while setting up SharedPreferencesPlugin", var3);
      }
   }

   private Object transformPref(String var1, Object var2) {
      if (var2 instanceof String) {
         var1 = (String)var2;
         if (var1.startsWith("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu")) {
            if (var1.startsWith("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu!")) {
               return var2;
            }

            return this.listEncoder.decode(var1.substring(40));
         }

         if (var1.startsWith("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBCaWdJbnRlZ2Vy")) {
            return new BigInteger(var1.substring(44), 36);
         }

         if (var1.startsWith("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBEb3VibGUu")) {
            return Double.valueOf(var1.substring(40));
         }
      } else if (var2 instanceof Set) {
         ArrayList var4 = new ArrayList((Set)var2);
         Editor var3 = this.preferences.edit().remove(var1);
         var2 = new StringBuilder("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu");
         var2.append(this.listEncoder.encode(var4));
         var3.putString(var1, var2.toString()).apply();
         return var4;
      }

      return var2;
   }

   @Override
   public Boolean clear(String var1, List<String> var2) throws RuntimeException {
      Editor var3 = this.preferences.edit();
      Map var5 = this.preferences.getAll();
      ArrayList var4 = new ArrayList();

      for (String var6 : var5.keySet()) {
         if (var6.startsWith(var1) && (var2 == null || var2.contains(var6))) {
            var4.add(var6);
         }
      }

      Iterator var7 = var4.iterator();

      while (var7.hasNext()) {
         var3.remove((String)var7.next());
      }

      return var3.commit();
   }

   @Override
   public Map<String, Object> getAll(String var1, List<String> var2) throws RuntimeException {
      HashSet var3;
      if (var2 == null) {
         var3 = null;
      } else {
         var3 = new HashSet(var2);
      }

      return this.getAllPrefs(var1, var3);
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.setUp(var1.getBinaryMessenger(), var1.getApplicationContext());
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      Messages$SharedPreferencesApi$_CC.setUp(var1.getBinaryMessenger(), null);
   }

   @Override
   public Boolean remove(String var1) {
      return this.preferences.edit().remove(var1).commit();
   }

   @Override
   public Boolean setBool(String var1, Boolean var2) {
      return this.preferences.edit().putBoolean(var1, var2).commit();
   }

   @Deprecated
   @Override
   public Boolean setDeprecatedStringList(String var1, List<String> var2) throws RuntimeException {
      Editor var4 = this.preferences.edit();
      StringBuilder var3 = new StringBuilder("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu");
      var3.append(this.listEncoder.encode(var2));
      return var4.putString(var1, var3.toString()).commit();
   }

   @Override
   public Boolean setDouble(String var1, Double var2) {
      String var4 = Double.toString(var2);
      Editor var3 = this.preferences.edit();
      StringBuilder var5 = new StringBuilder("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBEb3VibGUu");
      var5.append(var4);
      return var3.putString(var1, var5.toString()).commit();
   }

   @Override
   public Boolean setEncodedStringList(String var1, String var2) throws RuntimeException {
      return this.preferences.edit().putString(var1, var2).commit();
   }

   @Override
   public Boolean setInt(String var1, Long var2) {
      return this.preferences.edit().putLong(var1, var2).commit();
   }

   @Override
   public Boolean setString(String var1, String var2) {
      if (!var2.startsWith("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu")
         && !var2.startsWith("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBCaWdJbnRlZ2Vy")
         && !var2.startsWith("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBEb3VibGUu")) {
         return this.preferences.edit().putString(var1, var2).commit();
      } else {
         throw new RuntimeException("StorageError: This string cannot be stored as it clashes with special identifier prefixes");
      }
   }

   static class ListEncoder implements SharedPreferencesListEncoder {
      @Override
      public List<String> decode(String var1) throws RuntimeException {
         try {
            ByteArrayInputStream var2 = new ByteArrayInputStream(Base64.decode(var1, 0));
            StringListObjectInputStream var3 = new StringListObjectInputStream(var2);
            return (List<String>)var3.readObject();
         } catch (IOException var4) {
            var6 = var4;
         } catch (ClassNotFoundException var5) {
            var6 = var5;
         }

         throw new RuntimeException((Throwable)var6);
      }

      @Override
      public String encode(List<String> var1) throws RuntimeException {
         try {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            ObjectOutputStream var3 = new ObjectOutputStream(var2);
            var3.writeObject(var1);
            var3.flush();
            return Base64.encodeToString(var2.toByteArray(), 0);
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }
   }
}
