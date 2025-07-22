package io.sentry.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class HttpUtils {
   public static final String COOKIE_HEADER_NAME = "Cookie";
   private static final List<String> SECURITY_COOKIES = Arrays.asList(
      "JSESSIONID", "JSESSIONIDSSO", "JSSOSESSIONID", "SESSIONID", "SID", "CSRFTOKEN", "XSRF-TOKEN"
   );
   private static final List<String> SENSITIVE_HEADERS = Arrays.asList(
      "X-FORWARDED-FOR",
      "AUTHORIZATION",
      "COOKIE",
      "SET-COOKIE",
      "X-API-KEY",
      "X-REAL-IP",
      "REMOTE-ADDR",
      "FORWARDED",
      "PROXY-AUTHORIZATION",
      "X-CSRF-TOKEN",
      "X-CSRFTOKEN",
      "X-XSRF-TOKEN"
   );

   public static boolean containsSensitiveHeader(String var0) {
      return SENSITIVE_HEADERS.contains(var0.toUpperCase(Locale.ROOT));
   }

   public static String filterOutSecurityCookies(String param0, List<String> param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: ifnonnull 06
      // 04: aconst_null
      // 05: areturn
      // 06: aload 0
      // 07: ldc ";"
      // 09: bipush -1
      // 0a: invokevirtual java/lang/String.split (Ljava/lang/String;I)[Ljava/lang/String;
      // 0d: astore 0
      // 0e: new java/lang/StringBuilder
      // 11: astore 5
      // 13: aload 5
      // 15: invokespecial java/lang/StringBuilder.<init> ()V
      // 18: aload 0
      // 19: arraylength
      // 1a: istore 4
      // 1c: bipush 1
      // 1d: istore 2
      // 1e: bipush 0
      // 1f: istore 3
      // 20: iload 3
      // 21: iload 4
      // 23: if_icmpge 8f
      // 26: aload 0
      // 27: iload 3
      // 28: aaload
      // 29: astore 7
      // 2b: iload 2
      // 2c: ifne 37
      // 2f: aload 5
      // 31: ldc ";"
      // 33: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 36: pop
      // 37: aload 7
      // 39: ldc "="
      // 3b: bipush -1
      // 3c: invokevirtual java/lang/String.split (Ljava/lang/String;I)[Ljava/lang/String;
      // 3f: bipush 0
      // 40: aaload
      // 41: astore 6
      // 43: aload 6
      // 45: invokevirtual java/lang/String.trim ()Ljava/lang/String;
      // 48: aload 1
      // 49: invokestatic io/sentry/util/HttpUtils.isSecurityCookie (Ljava/lang/String;Ljava/util/List;)Z
      // 4c: ifeq 7f
      // 4f: new java/lang/StringBuilder
      // 52: astore 7
      // 54: aload 7
      // 56: invokespecial java/lang/StringBuilder.<init> ()V
      // 59: aload 7
      // 5b: aload 6
      // 5d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 60: pop
      // 61: aload 7
      // 63: ldc "="
      // 65: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 68: pop
      // 69: aload 7
      // 6b: ldc "[Filtered]"
      // 6d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 70: pop
      // 71: aload 5
      // 73: aload 7
      // 75: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 78: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 7b: pop
      // 7c: goto 87
      // 7f: aload 5
      // 81: aload 7
      // 83: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 86: pop
      // 87: iinc 3 1
      // 8a: bipush 0
      // 8b: istore 2
      // 8c: goto 20
      // 8f: aload 5
      // 91: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 94: astore 0
      // 95: aload 0
      // 96: areturn
      // 97: astore 0
      // 98: aconst_null
      // 99: areturn
   }

   public static List<String> filterOutSecurityCookiesFromHeader(Enumeration<String> var0, String var1, List<String> var2) {
      return var0 == null ? null : filterOutSecurityCookiesFromHeader(Collections.list(var0), var1, var2);
   }

   public static List<String> filterOutSecurityCookiesFromHeader(List<String> var0, String var1, List<String> var2) {
      if (var0 == null) {
         return null;
      } else if (var1 != null && !"Cookie".equalsIgnoreCase(var1)) {
         return var0;
      } else {
         ArrayList var4 = new ArrayList();
         Iterator var3 = var0.iterator();

         while (var3.hasNext()) {
            var4.add(filterOutSecurityCookies((String)var3.next(), var2));
         }

         return var4;
      }
   }

   public static boolean isSecurityCookie(String var0, List<String> var1) {
      var0 = var0.toUpperCase(Locale.ROOT);
      if (SECURITY_COOKIES.contains(var0)) {
         return true;
      } else {
         if (var1 != null) {
            Iterator var3 = var1.iterator();

            while (var3.hasNext()) {
               if (((String)var3.next()).toUpperCase(Locale.ROOT).equals(var0)) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
