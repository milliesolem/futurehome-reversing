package com.it_nomads.fluttersecurestorage.ciphers;

import android.content.Context;

// $VF: synthetic class
public final class KeyCipherAlgorithm$$ExternalSyntheticLambda1 implements KeyCipherFunction {
   @Override
   public final KeyCipher apply(Context var1) {
      return new RSACipherOAEPImplementation(var1);
   }
}
