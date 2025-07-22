package com.it_nomads.fluttersecurestorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.util.Base64;
import android.util.Log;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme;
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme;
import androidx.security.crypto.MasterKey.Builder;
import com.baseflow.geocoding.Geocoding..ExternalSyntheticApiModelOutline0;
import com.it_nomads.fluttersecurestorage.ciphers.StorageCipher;
import com.it_nomads.fluttersecurestorage.ciphers.StorageCipherFactory;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FlutterSecureStorage {
   protected String ELEMENT_PREFERENCES_KEY_PREFIX;
   private String SHARED_PREFERENCES_NAME;
   private final String TAG = "SecureStorageAndroid";
   private final Context applicationContext;
   private final Charset charset;
   private Boolean failedToUseEncryptedSharedPreferences;
   protected Map<String, Object> options;
   private SharedPreferences preferences;
   private StorageCipher storageCipher;
   private StorageCipherFactory storageCipherFactory;

   public FlutterSecureStorage(Context var1, Map<String, Object> var2) {
      this.ELEMENT_PREFERENCES_KEY_PREFIX = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIHNlY3VyZSBzdG9yYWdlCg";
      this.SHARED_PREFERENCES_NAME = "FlutterSecureStorage";
      this.failedToUseEncryptedSharedPreferences = false;
      this.options = var2;
      this.applicationContext = var1.getApplicationContext();
      this.charset = StandardCharsets.UTF_8;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void checkAndMigrateToEncrypted(SharedPreferences var1, SharedPreferences var2) {
      Iterator var3;
      try {
         var3 = var1.getAll().entrySet().iterator();
      } catch (Exception var7) {
         Log.e("SecureStorageAndroid", "Data migration failed", var7);
         return;
      }

      while (true) {
         try {
            while (var3.hasNext()) {
               Entry var4 = (Entry)var3.next();
               Object var5 = var4.getValue();
               String var10 = (String)var4.getKey();
               if (var5 instanceof String && var10.contains(this.ELEMENT_PREFERENCES_KEY_PREFIX)) {
                  var5 = this.decodeRawValue((String)var5);
                  var2.edit().putString(var10, (String)var5).apply();
                  var1.edit().remove(var10).apply();
               }
            }
         } catch (Exception var8) {
            Log.e("SecureStorageAndroid", "Data migration failed", var8);
            break;
         }

         try {
            Editor var9 = var1.edit();
            this.storageCipherFactory.removeCurrentAlgorithms(var9);
            var9.apply();
         } catch (Exception var6) {
            Log.e("SecureStorageAndroid", "Data migration failed", var6);
         }
         break;
      }
   }

   private String decodeRawValue(String var1) throws Exception {
      if (var1 == null) {
         return null;
      } else {
         byte[] var2 = Base64.decode(var1, 0);
         return new String(this.storageCipher.decrypt(var2), this.charset);
      }
   }

   private void ensureInitialized() {
      this.ensureOptions();
      SharedPreferences var1 = this.applicationContext.getSharedPreferences(this.SHARED_PREFERENCES_NAME, 0);
      if (this.storageCipher == null) {
         try {
            this.initStorageCipher(var1);
         } catch (Exception var4) {
            Log.e("SecureStorageAndroid", "StorageCipher initialization failed", var4);
         }
      }

      if (this.getUseEncryptedSharedPreferences() && VERSION.SDK_INT >= 23) {
         try {
            SharedPreferences var2 = this.initializeEncryptedSharedPreferencesManager(this.applicationContext);
            this.preferences = var2;
            this.checkAndMigrateToEncrypted(var1, var2);
         } catch (Exception var3) {
            Log.e("SecureStorageAndroid", "EncryptedSharedPreferences initialization failed", var3);
            this.preferences = var1;
            this.failedToUseEncryptedSharedPreferences = true;
         }
      } else {
         this.preferences = var1;
      }
   }

   private boolean getUseEncryptedSharedPreferences() {
      boolean var1 = this.failedToUseEncryptedSharedPreferences;
      boolean var2 = false;
      if (var1) {
         return false;
      } else {
         var1 = var2;
         if (this.options.containsKey("encryptedSharedPreferences")) {
            var1 = var2;
            if (this.options.get("encryptedSharedPreferences").equals("true")) {
               var1 = var2;
               if (VERSION.SDK_INT >= 23) {
                  var1 = true;
               }
            }
         }

         return var1;
      }
   }

   private void initStorageCipher(SharedPreferences var1) throws Exception {
      this.storageCipherFactory = new StorageCipherFactory(var1, this.options);
      if (this.getUseEncryptedSharedPreferences()) {
         this.storageCipher = this.storageCipherFactory.getSavedStorageCipher(this.applicationContext);
      } else if (this.storageCipherFactory.requiresReEncryption()) {
         this.reEncryptPreferences(this.storageCipherFactory, var1);
      } else {
         this.storageCipher = this.storageCipherFactory.getCurrentStorageCipher(this.applicationContext);
      }
   }

   private SharedPreferences initializeEncryptedSharedPreferencesManager(Context var1) throws GeneralSecurityException, IOException {
      MasterKey var2 = new Builder(var1)
         .setKeyGenParameterSpec(
            ExternalSyntheticApiModelOutline0.m(
               ExternalSyntheticApiModelOutline0.m(
                  ExternalSyntheticApiModelOutline0.m$1(
                     ExternalSyntheticApiModelOutline0.m(ExternalSyntheticApiModelOutline0.m("_androidx_security_master_key_", 3), new String[]{"NoPadding"}),
                     new String[]{"GCM"}
                  ),
                  256
               )
            )
         )
         .build();
      return EncryptedSharedPreferences.create(
         var1, this.SHARED_PREFERENCES_NAME, var2, PrefKeyEncryptionScheme.AES256_SIV, PrefValueEncryptionScheme.AES256_GCM
      );
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void reEncryptPreferences(StorageCipherFactory var1, SharedPreferences var2) throws Exception {
      HashMap var3;
      Iterator var4;
      try {
         this.storageCipher = var1.getSavedStorageCipher(this.applicationContext);
         var3 = new HashMap();
         var4 = var2.getAll().entrySet().iterator();
      } catch (Exception var10) {
         Log.e("SecureStorageAndroid", "re-encryption failed", var10);
         this.storageCipher = var1.getSavedStorageCipher(this.applicationContext);
         return;
      }

      while (true) {
         try {
            while (var4.hasNext()) {
               Entry var6 = (Entry)var4.next();
               Object var5 = var6.getValue();
               String var16 = (String)var6.getKey();
               if (var5 instanceof String && var16.contains(this.ELEMENT_PREFERENCES_KEY_PREFIX)) {
                  var3.put(var16, this.decodeRawValue((String)var5));
               }
            }
         } catch (Exception var11) {
            Log.e("SecureStorageAndroid", "re-encryption failed", var11);
            this.storageCipher = var1.getSavedStorageCipher(this.applicationContext);
            break;
         }

         try {
            this.storageCipher = var1.getCurrentStorageCipher(this.applicationContext);
            var12 = var2.edit();
            var13 = var3.entrySet().iterator();
         } catch (Exception var8) {
            Log.e("SecureStorageAndroid", "re-encryption failed", var8);
            this.storageCipher = var1.getSavedStorageCipher(this.applicationContext);
            break;
         }

         while (true) {
            try {
               if (!var13.hasNext()) {
                  break;
               }

               Entry var15 = (Entry)var13.next();
               byte[] var14 = this.storageCipher.encrypt(((String)var15.getValue()).getBytes(this.charset));
               var12.putString((String)var15.getKey(), Base64.encodeToString(var14, 0));
            } catch (Exception var9) {
               Log.e("SecureStorageAndroid", "re-encryption failed", var9);
               this.storageCipher = var1.getSavedStorageCipher(this.applicationContext);
               return;
            }
         }

         try {
            var1.storeCurrentAlgorithms(var12);
            var12.apply();
         } catch (Exception var7) {
            Log.e("SecureStorageAndroid", "re-encryption failed", var7);
            this.storageCipher = var1.getSavedStorageCipher(this.applicationContext);
         }
         break;
      }
   }

   public String addPrefixToKey(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.ELEMENT_PREFERENCES_KEY_PREFIX);
      var2.append("_");
      var2.append(var1);
      return var2.toString();
   }

   public boolean containsKey(String var1) {
      this.ensureInitialized();
      return this.preferences.contains(var1);
   }

   public void delete(String var1) {
      this.ensureInitialized();
      Editor var2 = this.preferences.edit();
      var2.remove(var1);
      var2.apply();
   }

   public void deleteAll() {
      this.ensureInitialized();
      Editor var1 = this.preferences.edit();
      var1.clear();
      if (!this.getUseEncryptedSharedPreferences()) {
         this.storageCipherFactory.storeCurrentAlgorithms(var1);
      }

      var1.apply();
   }

   protected void ensureOptions() {
      if (this.options.containsKey("sharedPreferencesName") && !((String)this.options.get("sharedPreferencesName")).isEmpty()) {
         this.SHARED_PREFERENCES_NAME = (String)this.options.get("sharedPreferencesName");
      }

      if (this.options.containsKey("preferencesKeyPrefix") && !((String)this.options.get("preferencesKeyPrefix")).isEmpty()) {
         this.ELEMENT_PREFERENCES_KEY_PREFIX = (String)this.options.get("preferencesKeyPrefix");
      }
   }

   boolean getResetOnError() {
      boolean var1;
      if (this.options.containsKey("resetOnError") && this.options.get("resetOnError").equals("true")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String read(String var1) throws Exception {
      this.ensureInitialized();
      var1 = this.preferences.getString(var1, null);
      return this.getUseEncryptedSharedPreferences() ? var1 : this.decodeRawValue(var1);
   }

   public Map<String, String> readAll() throws Exception {
      this.ensureInitialized();
      Map var2 = this.preferences.getAll();
      HashMap var1 = new HashMap();

      for (Entry var3 : var2.entrySet()) {
         if (((String)var3.getKey()).contains(this.ELEMENT_PREFERENCES_KEY_PREFIX)) {
            String var5 = (String)var3.getKey();
            StringBuilder var4 = new StringBuilder();
            var4.append(this.ELEMENT_PREFERENCES_KEY_PREFIX);
            var4.append('_');
            String var7 = var5.replaceFirst(var4.toString(), "");
            if (this.getUseEncryptedSharedPreferences()) {
               var1.put(var7, (String)var3.getValue());
            } else {
               var1.put(var7, this.decodeRawValue((String)var3.getValue()));
            }
         }
      }

      return var1;
   }

   public void write(String var1, String var2) throws Exception {
      this.ensureInitialized();
      Editor var3 = this.preferences.edit();
      if (this.getUseEncryptedSharedPreferences()) {
         var3.putString(var1, var2);
      } else {
         var3.putString(var1, Base64.encodeToString(this.storageCipher.encrypt(var2.getBytes(this.charset)), 0));
      }

      var3.apply();
   }
}
