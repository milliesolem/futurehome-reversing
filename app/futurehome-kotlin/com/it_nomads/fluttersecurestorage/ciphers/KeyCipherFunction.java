package com.it_nomads.fluttersecurestorage.ciphers;

import android.content.Context;

@FunctionalInterface
interface KeyCipherFunction {
   KeyCipher apply(Context var1) throws Exception;
}
