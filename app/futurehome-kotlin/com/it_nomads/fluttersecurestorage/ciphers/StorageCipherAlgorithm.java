package com.it_nomads.fluttersecurestorage.ciphers;

enum StorageCipherAlgorithm {
   AES_CBC_PKCS7Padding(new StorageCipherAlgorithm$$ExternalSyntheticLambda0(), 1),
   AES_GCM_NoPadding(new StorageCipherAlgorithm$$ExternalSyntheticLambda1(), 23);

   private static final StorageCipherAlgorithm[] $VALUES = $values();
   final int minVersionCode;
   final StorageCipherFunction storageCipher;

   private StorageCipherAlgorithm(StorageCipherFunction var3, int var4) {
      this.storageCipher = var3;
      this.minVersionCode = var4;
   }
}
