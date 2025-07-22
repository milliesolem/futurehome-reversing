package com.it_nomads.fluttersecurestorage.ciphers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class StorageCipher18Implementation implements StorageCipher {
   private static final String KEY_ALGORITHM = "AES";
   private static final String SHARED_PREFERENCES_NAME = "FlutterSecureKeyStorage";
   private static final int keySize = 16;
   private final Cipher cipher;
   private Key secretKey;
   private final SecureRandom secureRandom = new SecureRandom();

   public StorageCipher18Implementation(Context var1, KeyCipher var2) throws Exception {
      String var3 = this.getAESPreferencesKey();
      SharedPreferences var4 = var1.getSharedPreferences("FlutterSecureKeyStorage", 0);
      Editor var6 = var4.edit();
      String var7 = var4.getString(var3, null);
      this.cipher = this.getCipher();
      if (var7 != null) {
         try {
            this.secretKey = var2.unwrap(Base64.decode(var7, 0), "AES");
            return;
         } catch (Exception var5) {
            Log.e("StorageCipher18Impl", "unwrap key failed", var5);
         }
      }

      byte[] var8 = new byte[16];
      this.secureRandom.nextBytes(var8);
      SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
      this.secretKey = var9;
      var6.putString(var3, Base64.encodeToString(var2.wrap(var9), 0));
      var6.apply();
   }

   @Override
   public byte[] decrypt(byte[] var1) throws Exception {
      int var3 = this.getIvSize();
      byte[] var4 = new byte[var3];
      System.arraycopy(var1, 0, var4, 0, var3);
      AlgorithmParameterSpec var6 = this.getParameterSpec(var4);
      int var2 = var1.length - this.getIvSize();
      byte[] var5 = new byte[var2];
      System.arraycopy(var1, var3, var5, 0, var2);
      this.cipher.init(2, this.secretKey, var6);
      return this.cipher.doFinal(var5);
   }

   @Override
   public byte[] encrypt(byte[] var1) throws Exception {
      int var2 = this.getIvSize();
      byte[] var3 = new byte[var2];
      this.secureRandom.nextBytes(var3);
      AlgorithmParameterSpec var4 = this.getParameterSpec(var3);
      this.cipher.init(1, this.secretKey, var4);
      byte[] var6 = this.cipher.doFinal(var1);
      var1 = new byte[var6.length + var2];
      System.arraycopy(var3, 0, var1, 0, var2);
      System.arraycopy(var6, 0, var1, var2, var6.length);
      return var1;
   }

   protected String getAESPreferencesKey() {
      return "VGhpcyBpcyB0aGUga2V5IGZvciBhIHNlY3VyZSBzdG9yYWdlIEFFUyBLZXkK";
   }

   protected Cipher getCipher() throws Exception {
      return Cipher.getInstance("AES/CBC/PKCS7Padding");
   }

   protected int getIvSize() {
      return 16;
   }

   protected AlgorithmParameterSpec getParameterSpec(byte[] var1) {
      return new IvParameterSpec(var1);
   }
}
