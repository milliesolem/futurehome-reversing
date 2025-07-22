package com.google.protobuf;

final class TextFormatEscaper {
   private TextFormatEscaper() {
   }

   static String escapeBytes(ByteString var0) {
      return escapeBytes(new TextFormatEscaper.ByteSequence(var0) {
         final ByteString val$input;

         {
            this.val$input = var1;
         }

         @Override
         public byte byteAt(int var1) {
            return this.val$input.byteAt(var1);
         }

         @Override
         public int size() {
            return this.val$input.size();
         }
      });
   }

   static String escapeBytes(TextFormatEscaper.ByteSequence var0) {
      StringBuilder var3 = new StringBuilder(var0.size());

      for (int var1 = 0; var1 < var0.size(); var1++) {
         byte var2 = var0.byteAt(var1);
         if (var2 != 34) {
            if (var2 != 39) {
               if (var2 != 92) {
                  switch (var2) {
                     case 7:
                        var3.append("\\a");
                        break;
                     case 8:
                        var3.append("\\b");
                        break;
                     case 9:
                        var3.append("\\t");
                        break;
                     case 10:
                        var3.append("\\n");
                        break;
                     case 11:
                        var3.append("\\v");
                        break;
                     case 12:
                        var3.append("\\f");
                        break;
                     case 13:
                        var3.append("\\r");
                        break;
                     default:
                        if (var2 >= 32 && var2 <= 126) {
                           var3.append((char)var2);
                        } else {
                           var3.append('\\');
                           var3.append((char)((var2 >>> 6 & 3) + 48));
                           var3.append((char)((var2 >>> 3 & 7) + 48));
                           var3.append((char)((var2 & 7) + 48));
                        }
                  }
               } else {
                  var3.append("\\\\");
               }
            } else {
               var3.append("\\'");
            }
         } else {
            var3.append("\\\"");
         }
      }

      return var3.toString();
   }

   static String escapeBytes(byte[] var0) {
      return escapeBytes(new TextFormatEscaper.ByteSequence(var0) {
         final byte[] val$input;

         {
            this.val$input = var1;
         }

         @Override
         public byte byteAt(int var1) {
            return this.val$input[var1];
         }

         @Override
         public int size() {
            return this.val$input.length;
         }
      });
   }

   static String escapeDoubleQuotesAndBackslashes(String var0) {
      return var0.replace("\\", "\\\\").replace("\"", "\\\"");
   }

   static String escapeText(String var0) {
      return escapeBytes(ByteString.copyFromUtf8(var0));
   }

   private interface ByteSequence {
      byte byteAt(int var1);

      int size();
   }
}
