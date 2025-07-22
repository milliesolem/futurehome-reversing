package com.it_nomads.fluttersecurestorage.ciphers;

import android.content.Context;

// $VF: synthetic class
public final class StorageCipherAlgorithm$$ExternalSyntheticLambda1 implements StorageCipherFunction {
   @Override
   public final StorageCipher apply(Context var1, KeyCipher var2) {
      return new StorageCipherGCMImplementation(var1, var2);
   }
}
