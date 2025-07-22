package com.google.thirdparty.publicsuffix;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Deque;

final class TrieParser {
   private static final Joiner PREFIX_JOINER = Joiner.on("");

   private static int doParseTrieToBuilder(Deque<CharSequence> var0, CharSequence var1, int var2, Builder<String, PublicSuffixType> var3) {
      int var8 = var1.length();
      char var4 = 0;
      int var6 = var2;

      char var5;
      while (true) {
         var5 = var4;
         if (var6 >= var8) {
            break;
         }

         var4 = var1.charAt(var6);
         var5 = var4;
         if (var4 == '&') {
            break;
         }

         var5 = var4;
         if (var4 == '?') {
            break;
         }

         var5 = var4;
         if (var4 == '!') {
            break;
         }

         var5 = var4;
         if (var4 == ':') {
            break;
         }

         if (var4 == ',') {
            var5 = var4;
            break;
         }

         var6++;
      }

      var0.push(reverse(var1.subSequence(var2, var6)));
      if (var5 == '!' || var5 == '?' || var5 == ':' || var5 == ',') {
         String var9 = PREFIX_JOINER.join(var0);
         if (var9.length() > 0) {
            var3.put(var9, PublicSuffixType.fromCode(var5));
         }
      }

      int var11 = ++var6;
      if (var5 != '?') {
         var11 = var6;
         label29:
         if (var5 != ',') {
            do {
               var11 = var6;
               if (var6 >= var8) {
                  break label29;
               }

               var11 = var6 + doParseTrieToBuilder(var0, var1, var6, var3);
               if (var1.charAt(var11) == '?') {
                  break;
               }

               var6 = var11;
            } while (var1.charAt(var11) != ',');

            var11++;
         }
      }

      var0.pop();
      return var11 - var2;
   }

   static ImmutableMap<String, PublicSuffixType> parseTrie(CharSequence var0) {
      Builder var3 = ImmutableMap.builder();
      int var2 = var0.length();
      int var1 = 0;

      while (var1 < var2) {
         var1 += doParseTrieToBuilder(Queues.newArrayDeque(), var0, var1, var3);
      }

      return var3.buildOrThrow();
   }

   private static CharSequence reverse(CharSequence var0) {
      return new StringBuilder(var0).reverse();
   }
}
