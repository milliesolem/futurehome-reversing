package com.it_nomads.fluttersecurestorage.ciphers;

import java.security.Key;

public interface KeyCipher {
   Key unwrap(byte[] var1, String var2) throws Exception;

   byte[] wrap(Key var1) throws Exception;
}
