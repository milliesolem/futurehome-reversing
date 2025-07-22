package com.google.protobuf;

import java.nio.Buffer;

final class Java8Compatibility {
   private Java8Compatibility() {
   }

   static void clear(Buffer var0) {
      var0.clear();
   }

   static void flip(Buffer var0) {
      var0.flip();
   }

   static void limit(Buffer var0, int var1) {
      var0.limit(var1);
   }

   static void mark(Buffer var0) {
      var0.mark();
   }

   static void position(Buffer var0, int var1) {
      var0.position(var1);
   }

   static void reset(Buffer var0) {
      var0.reset();
   }
}
