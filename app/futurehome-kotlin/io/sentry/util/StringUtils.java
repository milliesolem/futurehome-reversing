package io.sentry.util;

import io.sentry.ILogger;
import io.sentry.SentryLevel;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.StringCharacterIterator;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

public final class StringUtils {
   private static final String CORRUPTED_NIL_UUID = "0000-0000";
   private static final Pattern PATTERN_WORD_SNAKE_CASE = Pattern.compile("[\\W_]+");
   private static final String PROPER_NIL_UUID = "00000000-0000-0000-0000-000000000000";
   private static final Charset UTF_8 = Charset.forName("UTF-8");

   private StringUtils() {
   }

   public static String byteCountToString(long var0) {
      if (-1000L < var0 && var0 < 1000L) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" B");
         return var3.toString();
      } else {
         StringCharacterIterator var2 = new StringCharacterIterator("kMGTPE");

         while (var0 <= -999950L || var0 >= 999950L) {
            var0 /= 1000L;
            var2.next();
         }

         return String.format(Locale.ROOT, "%.1f %cB", var0 / 1000.0, var2.current());
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static String calculateStringHash(String var0, ILogger var1) {
      if (var0 != null && !var0.isEmpty()) {
         try {
            try {
               byte[] var3 = MessageDigest.getInstance("SHA-1").digest(var0.getBytes(UTF_8));
               BigInteger var2 = new BigInteger(1, var3);
               StringBuilder var8 = new StringBuilder(var2.toString(16));
               return var8.toString();
            } catch (NoSuchAlgorithmException var6) {
               var0 = var6;
            }
         } catch (Throwable var7) {
            var1.log(SentryLevel.INFO, "string: %s could not calculate its hash", var7, var0);
            return null;
         }

         var1.log(SentryLevel.INFO, "SHA-1 isn't available to calculate the hash.", (Throwable)var0);
      }

      return null;
   }

   public static String camelCase(String var0) {
      String var3 = var0;
      if (var0 != null) {
         if (var0.isEmpty()) {
            var3 = var0;
         } else {
            String[] var5 = PATTERN_WORD_SNAKE_CASE.split(var0, -1);
            StringBuilder var4 = new StringBuilder();
            int var2 = var5.length;

            for (int var1 = 0; var1 < var2; var1++) {
               var4.append(capitalize(var5[var1]));
            }

            var3 = var4.toString();
         }
      }

      return var3;
   }

   public static String capitalize(String var0) {
      String var1 = var0;
      if (var0 != null) {
         if (var0.isEmpty()) {
            var1 = var0;
         } else {
            StringBuilder var2 = new StringBuilder();
            var2.append(var0.substring(0, 1).toUpperCase(Locale.ROOT));
            var2.append(var0.substring(1).toLowerCase(Locale.ROOT));
            var1 = var2.toString();
         }
      }

      return var1;
   }

   public static int countOf(String var0, char var1) {
      int var2 = 0;
      int var4 = 0;

      while (var2 < var0.length()) {
         int var3 = var4;
         if (var0.charAt(var2) == var1) {
            var3 = var4 + 1;
         }

         var2++;
         var4 = var3;
      }

      return var4;
   }

   public static String getStringAfterDot(String var0) {
      if (var0 != null) {
         int var2 = var0.lastIndexOf(".");
         String var3 = var0;
         if (var2 >= 0) {
            int var1 = var0.length();
            var2++;
            var3 = var0;
            if (var1 > var2) {
               var3 = var0.substring(var2);
            }
         }

         return var3;
      } else {
         return null;
      }
   }

   public static String join(CharSequence var0, Iterable<? extends CharSequence> var1) {
      StringBuilder var2 = new StringBuilder();
      Iterator var3 = var1.iterator();
      if (var3.hasNext()) {
         var2.append((CharSequence)var3.next());

         while (var3.hasNext()) {
            var2.append(var0);
            var2.append((CharSequence)var3.next());
         }
      }

      return var2.toString();
   }

   public static String normalizeUUID(String var0) {
      String var1 = var0;
      if (var0.equals("0000-0000")) {
         var1 = "00000000-0000-0000-0000-000000000000";
      }

      return var1;
   }

   public static String removePrefix(String var0, String var1) {
      if (var0 == null) {
         return "";
      } else {
         String var2 = var0;
         if (var0.indexOf(var1) == 0) {
            var2 = var0.substring(var1.length());
         }

         return var2;
      }
   }

   public static String removeSurrounding(String var0, String var1) {
      String var2 = var0;
      if (var0 != null) {
         var2 = var0;
         if (var1 != null) {
            var2 = var0;
            if (var0.startsWith(var1)) {
               var2 = var0;
               if (var0.endsWith(var1)) {
                  var2 = var0.substring(var1.length(), var0.length() - var1.length());
               }
            }
         }
      }

      return var2;
   }

   public static String substringBefore(String var0, String var1) {
      if (var0 == null) {
         return "";
      } else {
         int var2 = var0.indexOf(var1);
         var1 = var0;
         if (var2 >= 0) {
            var1 = var0.substring(0, var2);
         }

         return var1;
      }
   }

   public static String toString(Object var0) {
      return var0 == null ? null : var0.toString();
   }
}
