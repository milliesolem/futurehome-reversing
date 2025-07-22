package com.it_nomads.fluttersecurestorage.ciphers;

enum KeyCipherAlgorithm {
   RSA_ECB_OAEPwithSHA_256andMGF1Padding(new KeyCipherAlgorithm$$ExternalSyntheticLambda1(), 23),
   RSA_ECB_PKCS1Padding(new KeyCipherAlgorithm$$ExternalSyntheticLambda0(), 1);

   private static final KeyCipherAlgorithm[] $VALUES = $values();
   final KeyCipherFunction keyCipher;
   final int minVersionCode;

   private KeyCipherAlgorithm(KeyCipherFunction var3, int var4) {
      this.keyCipher = var3;
      this.minVersionCode = var4;
   }
}
