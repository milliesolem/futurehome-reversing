package com.it_nomads.fluttersecurestorage.ciphers;

import android.content.Context;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;

public class StorageCipherGCMImplementation extends StorageCipher18Implementation {
   private static final int AUTHENTICATION_TAG_SIZE = 128;

   public StorageCipherGCMImplementation(Context var1, KeyCipher var2) throws Exception {
      super(var1, var2);
   }

   @Override
   protected String getAESPreferencesKey() {
      return "VGhpcyBpcyB0aGUga2V5IGZvcihBIHNlY3XyZZBzdG9yYWdlIEFFUyBLZXkK";
   }

   @Override
   protected Cipher getCipher() throws Exception {
      return Cipher.getInstance("AES/GCM/NoPadding");
   }

   @Override
   protected int getIvSize() {
      return 12;
   }

   @Override
   protected AlgorithmParameterSpec getParameterSpec(byte[] var1) {
      return new GCMParameterSpec(128, var1);
   }
}
