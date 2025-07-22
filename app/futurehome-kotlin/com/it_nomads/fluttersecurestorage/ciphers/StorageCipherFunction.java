package com.it_nomads.fluttersecurestorage.ciphers;

import android.content.Context;

@FunctionalInterface
interface StorageCipherFunction {
   StorageCipher apply(Context var1, KeyCipher var2) throws Exception;
}
