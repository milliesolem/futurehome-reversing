package com.it_nomads.fluttersecurestorage.ciphers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import java.util.Map;

public class StorageCipherFactory {
   private static final KeyCipherAlgorithm DEFAULT_KEY_ALGORITHM = KeyCipherAlgorithm.RSA_ECB_PKCS1Padding;
   private static final StorageCipherAlgorithm DEFAULT_STORAGE_ALGORITHM = StorageCipherAlgorithm.AES_CBC_PKCS7Padding;
   private static final String ELEMENT_PREFERENCES_ALGORITHM_KEY = "FlutterSecureSAlgorithmKey";
   private static final String ELEMENT_PREFERENCES_ALGORITHM_PREFIX = "FlutterSecureSAlgorithm";
   private static final String ELEMENT_PREFERENCES_ALGORITHM_STORAGE = "FlutterSecureSAlgorithmStorage";
   private final KeyCipherAlgorithm currentKeyAlgorithm;
   private final StorageCipherAlgorithm currentStorageAlgorithm;
   private final KeyCipherAlgorithm savedKeyAlgorithm;
   private final StorageCipherAlgorithm savedStorageAlgorithm;

   public StorageCipherFactory(SharedPreferences var1, Map<String, Object> var2) {
      KeyCipherAlgorithm var4 = DEFAULT_KEY_ALGORITHM;
      this.savedKeyAlgorithm = KeyCipherAlgorithm.valueOf(var1.getString("FlutterSecureSAlgorithmKey", var4.name()));
      StorageCipherAlgorithm var3 = DEFAULT_STORAGE_ALGORITHM;
      this.savedStorageAlgorithm = StorageCipherAlgorithm.valueOf(var1.getString("FlutterSecureSAlgorithmStorage", var3.name()));
      KeyCipherAlgorithm var5 = KeyCipherAlgorithm.valueOf(this.getFromOptionsWithDefault(var2, "keyCipherAlgorithm", var4.name()));
      KeyCipherAlgorithm var6 = var4;
      if (var5.minVersionCode <= VERSION.SDK_INT) {
         var6 = var5;
      }

      this.currentKeyAlgorithm = var6;
      StorageCipherAlgorithm var8 = StorageCipherAlgorithm.valueOf(this.getFromOptionsWithDefault(var2, "storageCipherAlgorithm", var3.name()));
      StorageCipherAlgorithm var7 = var3;
      if (var8.minVersionCode <= VERSION.SDK_INT) {
         var7 = var8;
      }

      this.currentStorageAlgorithm = var7;
   }

   private String getFromOptionsWithDefault(Map<String, Object> var1, String var2, String var3) {
      Object var4 = var1.get(var2);
      if (var4 != null) {
         var3 = var4.toString();
      }

      return var3;
   }

   public StorageCipher getCurrentStorageCipher(Context var1) throws Exception {
      KeyCipher var2 = this.currentKeyAlgorithm.keyCipher.apply(var1);
      return this.currentStorageAlgorithm.storageCipher.apply(var1, var2);
   }

   public StorageCipher getSavedStorageCipher(Context var1) throws Exception {
      KeyCipher var2 = this.savedKeyAlgorithm.keyCipher.apply(var1);
      return this.savedStorageAlgorithm.storageCipher.apply(var1, var2);
   }

   public void removeCurrentAlgorithms(Editor var1) {
      var1.remove("FlutterSecureSAlgorithmKey");
      var1.remove("FlutterSecureSAlgorithmStorage");
   }

   public boolean requiresReEncryption() {
      boolean var1;
      if (this.savedKeyAlgorithm == this.currentKeyAlgorithm && this.savedStorageAlgorithm == this.currentStorageAlgorithm) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void storeCurrentAlgorithms(Editor var1) {
      var1.putString("FlutterSecureSAlgorithmKey", this.currentKeyAlgorithm.name());
      var1.putString("FlutterSecureSAlgorithmStorage", this.currentStorageAlgorithm.name());
   }
}
