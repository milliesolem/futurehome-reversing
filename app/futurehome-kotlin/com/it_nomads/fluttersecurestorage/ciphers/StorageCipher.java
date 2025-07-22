package com.it_nomads.fluttersecurestorage.ciphers;

public interface StorageCipher {
   byte[] decrypt(byte[] var1) throws Exception;

   byte[] encrypt(byte[] var1) throws Exception;
}
