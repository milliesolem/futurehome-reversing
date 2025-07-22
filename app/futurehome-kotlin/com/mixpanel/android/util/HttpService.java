package com.mixpanel.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Map;
import javax.net.ssl.SSLSocketFactory;

public class HttpService implements RemoteService {
   private static final String LOGTAG = "MixpanelAPI.Message";
   private static final int MAX_UNAVAILABLE_HTTP_RESPONSE_CODE = 599;
   private static final int MIN_UNAVAILABLE_HTTP_RESPONSE_CODE = 500;
   private static boolean sIsMixpanelBlocked;

   private static boolean isProxyRequest(String var0) {
      return var0.toLowerCase().contains("https://api.mixpanel.com".toLowerCase()) ^ true;
   }

   private boolean onOfflineMode(OfflineMode var1) {
      boolean var3 = false;
      boolean var2 = var3;
      if (var1 != null) {
         boolean var4;
         try {
            var4 = var1.isOffline();
         } catch (Exception var5) {
            MPLog.v("MixpanelAPI.Message", "Client State should not throw exception, will assume is not on offline mode", var5);
            return var3;
         }

         var2 = var3;
         if (var4) {
            var2 = true;
         }
      }

      return var2;
   }

   private static byte[] slurp(InputStream var0) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      byte[] var3 = new byte[8192];

      while (true) {
         int var1 = var0.read(var3, 0, 8192);
         if (var1 == -1) {
            var2.flush();
            return var2.toByteArray();
         }

         var2.write(var3, 0, var1);
      }
   }

   @Override
   public void checkIsMixpanelBlocked() {
      new Thread(new Runnable(this) {
         final HttpService this$0;

         {
            this.this$0 = var1;
         }

         // $VF: Duplicated exception handlers to handle obfuscated exceptions
         @Override
         public void run() {
            boolean var1;
            label28: {
               label27: {
                  try {
                     InetAddress var2 = InetAddress.getByName("api.mixpanel.com");
                     if (!var2.isLoopbackAddress() && !var2.isAnyLocalAddress()) {
                        break label27;
                     }
                  } catch (Exception var4) {
                     return;
                  }

                  var1 = true;
                  break label28;
               }

               var1 = false;
            }

            try {
               HttpService.sIsMixpanelBlocked = var1;
               if (HttpService.sIsMixpanelBlocked) {
                  MPLog.v("MixpanelAPI.Message", "AdBlocker is enabled. Won't be able to use Mixpanel services.");
               }
            } catch (Exception var3) {
            }
         }
      }).start();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public boolean isOnline(Context var1, OfflineMode var2) {
      if (sIsMixpanelBlocked) {
         return false;
      } else if (this.onOfflineMode(var2)) {
         return false;
      } else {
         boolean var3 = true;

         try {
            var9 = ((ConnectivityManager)var1.getSystemService("connectivity")).getActiveNetworkInfo();
         } catch (SecurityException var8) {
            MPLog.v("MixpanelAPI.Message", "Don't have permission to check connectivity, will assume we are online");
            return var3;
         }

         if (var9 == null) {
            try {
               MPLog.v("MixpanelAPI.Message", "A default network has not been set so we cannot be certain whether we are offline");
            } catch (SecurityException var5) {
               MPLog.v("MixpanelAPI.Message", "Don't have permission to check connectivity, will assume we are online");
            }
         } else {
            boolean var4;
            try {
               var4 = var9.isConnectedOrConnecting();
               var11 = new StringBuilder("ConnectivityManager says we ");
            } catch (SecurityException var7) {
               MPLog.v("MixpanelAPI.Message", "Don't have permission to check connectivity, will assume we are online");
               return var3;
            }

            String var10;
            if (var4) {
               var10 = "are";
            } else {
               var10 = "are not";
            }

            try {
               var11.append(var10);
               var11.append(" online");
               MPLog.v("MixpanelAPI.Message", var11.toString());
            } catch (SecurityException var6) {
               MPLog.v("MixpanelAPI.Message", "Don't have permission to check connectivity, will assume we are online");
               return var3;
            }

            var3 = var4;
         }

         return var3;
      }
   }

   @Override
   public byte[] performRequest(String param1, ProxyServerInteractor param2, Map<String, Object> param3, SSLSocketFactory param4) throws RemoteService.ServiceUnavailableException, IOException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: new java/lang/StringBuilder
      // 003: dup
      // 004: ldc "Attempting request to "
      // 006: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 009: astore 8
      // 00b: aload 8
      // 00d: aload 1
      // 00e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 011: pop
      // 012: ldc "MixpanelAPI.Message"
      // 014: aload 8
      // 016: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 019: invokestatic com/mixpanel/android/util/MPLog.v (Ljava/lang/String;Ljava/lang/String;)V
      // 01c: aconst_null
      // 01d: astore 14
      // 01f: aconst_null
      // 020: astore 15
      // 022: bipush 0
      // 023: istore 5
      // 025: aconst_null
      // 026: astore 9
      // 028: bipush 0
      // 029: istore 6
      // 02b: iload 5
      // 02d: bipush 3
      // 02e: if_icmpge 357
      // 031: iload 6
      // 033: ifne 357
      // 036: new java/net/URL
      // 039: astore 8
      // 03b: aload 8
      // 03d: aload 1
      // 03e: invokespecial java/net/URL.<init> (Ljava/lang/String;)V
      // 041: aload 8
      // 043: invokevirtual java/net/URL.openConnection ()Ljava/net/URLConnection;
      // 046: checkcast java/net/HttpURLConnection
      // 049: astore 8
      // 04b: aload 4
      // 04d: ifnull 081
      // 050: aload 8
      // 052: instanceof javax/net/ssl/HttpsURLConnection
      // 055: ifeq 081
      // 058: aload 8
      // 05a: checkcast javax/net/ssl/HttpsURLConnection
      // 05d: aload 4
      // 05f: invokevirtual javax/net/ssl/HttpsURLConnection.setSSLSocketFactory (Ljavax/net/ssl/SSLSocketFactory;)V
      // 062: goto 081
      // 065: astore 2
      // 066: goto 251
      // 069: astore 1
      // 06a: aconst_null
      // 06b: astore 3
      // 06c: aconst_null
      // 06d: astore 10
      // 06f: aconst_null
      // 070: astore 2
      // 071: aload 8
      // 073: astore 4
      // 075: goto 269
      // 078: astore 10
      // 07a: aload 8
      // 07c: astore 12
      // 07e: goto 2b1
      // 081: aload 2
      // 082: ifnull 0d9
      // 085: aload 1
      // 086: invokestatic com/mixpanel/android/util/HttpService.isProxyRequest (Ljava/lang/String;)Z
      // 089: ifeq 0d9
      // 08c: aload 2
      // 08d: invokeinterface com/mixpanel/android/util/ProxyServerInteractor.getProxyRequestHeaders ()Ljava/util/Map; 1
      // 092: astore 10
      // 094: aload 10
      // 096: ifnull 0d9
      // 099: aload 10
      // 09b: invokeinterface java/util/Map.entrySet ()Ljava/util/Set; 1
      // 0a0: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 0a5: astore 10
      // 0a7: aload 10
      // 0a9: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0ae: ifeq 0d9
      // 0b1: aload 10
      // 0b3: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0b8: checkcast java/util/Map$Entry
      // 0bb: astore 11
      // 0bd: aload 8
      // 0bf: aload 11
      // 0c1: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 0c6: checkcast java/lang/String
      // 0c9: aload 11
      // 0cb: invokeinterface java/util/Map$Entry.getValue ()Ljava/lang/Object; 1
      // 0d0: checkcast java/lang/String
      // 0d3: invokevirtual java/net/HttpURLConnection.setRequestProperty (Ljava/lang/String;Ljava/lang/String;)V
      // 0d6: goto 0a7
      // 0d9: aload 8
      // 0db: sipush 2000
      // 0de: invokevirtual java/net/HttpURLConnection.setConnectTimeout (I)V
      // 0e1: aload 8
      // 0e3: sipush 30000
      // 0e6: invokevirtual java/net/HttpURLConnection.setReadTimeout (I)V
      // 0e9: aload 3
      // 0ea: ifnull 1e2
      // 0ed: new android/net/Uri$Builder
      // 0f0: astore 10
      // 0f2: aload 10
      // 0f4: invokespecial android/net/Uri$Builder.<init> ()V
      // 0f7: aload 3
      // 0f8: invokeinterface java/util/Map.entrySet ()Ljava/util/Set; 1
      // 0fd: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 102: astore 12
      // 104: aload 12
      // 106: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 10b: ifeq 137
      // 10e: aload 12
      // 110: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 115: checkcast java/util/Map$Entry
      // 118: astore 11
      // 11a: aload 10
      // 11c: aload 11
      // 11e: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 123: checkcast java/lang/String
      // 126: aload 11
      // 128: invokeinterface java/util/Map$Entry.getValue ()Ljava/lang/Object; 1
      // 12d: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 130: invokevirtual android/net/Uri$Builder.appendQueryParameter (Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri$Builder;
      // 133: pop
      // 134: goto 104
      // 137: aload 10
      // 139: invokevirtual android/net/Uri$Builder.build ()Landroid/net/Uri;
      // 13c: invokevirtual android/net/Uri.getEncodedQuery ()Ljava/lang/String;
      // 13f: astore 12
      // 141: aload 8
      // 143: aload 12
      // 145: invokevirtual java/lang/String.getBytes ()[B
      // 148: arraylength
      // 149: invokevirtual java/net/HttpURLConnection.setFixedLengthStreamingMode (I)V
      // 14c: aload 8
      // 14e: bipush 1
      // 14f: invokevirtual java/net/HttpURLConnection.setDoOutput (Z)V
      // 152: aload 8
      // 154: ldc "POST"
      // 156: invokevirtual java/net/HttpURLConnection.setRequestMethod (Ljava/lang/String;)V
      // 159: aload 8
      // 15b: invokevirtual java/net/HttpURLConnection.getOutputStream ()Ljava/io/OutputStream;
      // 15e: astore 10
      // 160: new java/io/BufferedOutputStream
      // 163: astore 11
      // 165: aload 11
      // 167: aload 10
      // 169: invokespecial java/io/BufferedOutputStream.<init> (Ljava/io/OutputStream;)V
      // 16c: aload 11
      // 16e: aload 12
      // 170: ldc_w "UTF-8"
      // 173: invokevirtual java/lang/String.getBytes (Ljava/lang/String;)[B
      // 176: invokevirtual java/io/BufferedOutputStream.write ([B)V
      // 179: aload 11
      // 17b: invokevirtual java/io/BufferedOutputStream.flush ()V
      // 17e: aload 11
      // 180: invokevirtual java/io/BufferedOutputStream.close ()V
      // 183: aload 10
      // 185: invokevirtual java/io/OutputStream.close ()V
      // 188: goto 1e2
      // 18b: astore 2
      // 18c: aconst_null
      // 18d: astore 1
      // 18e: aload 11
      // 190: astore 9
      // 192: goto 322
      // 195: astore 1
      // 196: aconst_null
      // 197: astore 3
      // 198: aload 11
      // 19a: astore 2
      // 19b: goto 071
      // 19e: astore 12
      // 1a0: aconst_null
      // 1a1: astore 16
      // 1a3: aload 9
      // 1a5: astore 13
      // 1a7: aload 8
      // 1a9: astore 12
      // 1ab: aload 16
      // 1ad: astore 8
      // 1af: aload 11
      // 1b1: astore 9
      // 1b3: goto 2c2
      // 1b6: astore 2
      // 1b7: aconst_null
      // 1b8: astore 1
      // 1b9: aload 14
      // 1bb: astore 3
      // 1bc: goto 325
      // 1bf: astore 1
      // 1c0: aconst_null
      // 1c1: astore 3
      // 1c2: aconst_null
      // 1c3: astore 2
      // 1c4: goto 071
      // 1c7: astore 11
      // 1c9: aconst_null
      // 1ca: astore 16
      // 1cc: aconst_null
      // 1cd: astore 11
      // 1cf: aload 9
      // 1d1: astore 13
      // 1d3: aload 8
      // 1d5: astore 12
      // 1d7: aload 16
      // 1d9: astore 8
      // 1db: aload 11
      // 1dd: astore 9
      // 1df: goto 2c2
      // 1e2: aload 2
      // 1e3: ifnull 1f9
      // 1e6: aload 1
      // 1e7: invokestatic com/mixpanel/android/util/HttpService.isProxyRequest (Ljava/lang/String;)Z
      // 1ea: ifeq 1f9
      // 1ed: aload 2
      // 1ee: aload 1
      // 1ef: aload 8
      // 1f1: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 1f4: invokeinterface com/mixpanel/android/util/ProxyServerInteractor.onProxyResponse (Ljava/lang/String;I)V 3
      // 1f9: aload 8
      // 1fb: invokevirtual java/net/HttpURLConnection.getInputStream ()Ljava/io/InputStream;
      // 1fe: astore 10
      // 200: aload 10
      // 202: invokestatic com/mixpanel/android/util/HttpService.slurp (Ljava/io/InputStream;)[B
      // 205: astore 11
      // 207: aload 11
      // 209: astore 9
      // 20b: aload 10
      // 20d: invokevirtual java/io/InputStream.close ()V
      // 210: aload 8
      // 212: ifnull 21a
      // 215: aload 8
      // 217: invokevirtual java/net/HttpURLConnection.disconnect ()V
      // 21a: bipush 1
      // 21b: istore 6
      // 21d: aload 11
      // 21f: astore 9
      // 221: goto 02b
      // 224: astore 2
      // 225: aconst_null
      // 226: astore 4
      // 228: aload 14
      // 22a: astore 3
      // 22b: aload 10
      // 22d: astore 1
      // 22e: aload 4
      // 230: astore 10
      // 232: goto 325
      // 235: astore 1
      // 236: aload 10
      // 238: astore 3
      // 239: goto 06c
      // 23c: astore 11
      // 23e: aload 9
      // 240: astore 11
      // 242: aload 8
      // 244: astore 12
      // 246: aload 10
      // 248: astore 8
      // 24a: goto 2b8
      // 24d: astore 2
      // 24e: aconst_null
      // 24f: astore 8
      // 251: aconst_null
      // 252: astore 1
      // 253: aconst_null
      // 254: astore 10
      // 256: aload 14
      // 258: astore 3
      // 259: goto 325
      // 25c: astore 1
      // 25d: aconst_null
      // 25e: astore 3
      // 25f: aconst_null
      // 260: astore 10
      // 262: aload 10
      // 264: astore 2
      // 265: aload 15
      // 267: astore 4
      // 269: aload 4
      // 26b: ifnull 299
      // 26e: aload 4
      // 270: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 273: sipush 500
      // 276: if_icmplt 299
      // 279: aload 4
      // 27b: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 27e: sipush 599
      // 281: if_icmpgt 299
      // 284: new com/mixpanel/android/util/RemoteService$ServiceUnavailableException
      // 287: astore 1
      // 288: aload 1
      // 289: ldc_w "Service Unavailable"
      // 28c: aload 4
      // 28e: ldc_w "Retry-After"
      // 291: invokevirtual java/net/HttpURLConnection.getHeaderField (Ljava/lang/String;)Ljava/lang/String;
      // 294: invokespecial com/mixpanel/android/util/RemoteService$ServiceUnavailableException.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 297: aload 1
      // 298: athrow
      // 299: aload 1
      // 29a: athrow
      // 29b: astore 11
      // 29d: aload 4
      // 29f: astore 8
      // 2a1: aload 3
      // 2a2: astore 1
      // 2a3: aload 2
      // 2a4: astore 9
      // 2a6: aload 11
      // 2a8: astore 2
      // 2a9: goto 322
      // 2ac: astore 8
      // 2ae: aconst_null
      // 2af: astore 12
      // 2b1: aconst_null
      // 2b2: astore 8
      // 2b4: aload 9
      // 2b6: astore 11
      // 2b8: aconst_null
      // 2b9: astore 10
      // 2bb: aconst_null
      // 2bc: astore 9
      // 2be: aload 11
      // 2c0: astore 13
      // 2c2: ldc "MixpanelAPI.Message"
      // 2c4: ldc_w "Failure to connect, likely caused by a known issue with Android lib. Retrying."
      // 2c7: invokestatic com/mixpanel/android/util/MPLog.d (Ljava/lang/String;Ljava/lang/String;)V
      // 2ca: iload 5
      // 2cc: bipush 1
      // 2cd: iadd
      // 2ce: istore 7
      // 2d0: aload 9
      // 2d2: ifnull 2df
      // 2d5: aload 9
      // 2d7: invokevirtual java/io/BufferedOutputStream.close ()V
      // 2da: goto 2df
      // 2dd: astore 9
      // 2df: aload 10
      // 2e1: ifnull 2ee
      // 2e4: aload 10
      // 2e6: invokevirtual java/io/OutputStream.close ()V
      // 2e9: goto 2ee
      // 2ec: astore 9
      // 2ee: aload 8
      // 2f0: ifnull 2fd
      // 2f3: aload 8
      // 2f5: invokevirtual java/io/InputStream.close ()V
      // 2f8: goto 2fd
      // 2fb: astore 8
      // 2fd: iload 7
      // 2ff: istore 5
      // 301: aload 13
      // 303: astore 9
      // 305: aload 12
      // 307: ifnull 02b
      // 30a: aload 12
      // 30c: invokevirtual java/net/HttpURLConnection.disconnect ()V
      // 30f: iload 7
      // 311: istore 5
      // 313: aload 13
      // 315: astore 9
      // 317: goto 02b
      // 31a: astore 2
      // 31b: aload 8
      // 31d: astore 1
      // 31e: aload 12
      // 320: astore 8
      // 322: aload 9
      // 324: astore 3
      // 325: aload 3
      // 326: ifnull 331
      // 329: aload 3
      // 32a: invokevirtual java/io/BufferedOutputStream.close ()V
      // 32d: goto 331
      // 330: astore 3
      // 331: aload 10
      // 333: ifnull 33f
      // 336: aload 10
      // 338: invokevirtual java/io/OutputStream.close ()V
      // 33b: goto 33f
      // 33e: astore 3
      // 33f: aload 1
      // 340: ifnull 34b
      // 343: aload 1
      // 344: invokevirtual java/io/InputStream.close ()V
      // 347: goto 34b
      // 34a: astore 1
      // 34b: aload 8
      // 34d: ifnull 355
      // 350: aload 8
      // 352: invokevirtual java/net/HttpURLConnection.disconnect ()V
      // 355: aload 2
      // 356: athrow
      // 357: iload 5
      // 359: bipush 3
      // 35a: if_icmplt 365
      // 35d: ldc "MixpanelAPI.Message"
      // 35f: ldc_w "Could not connect to Mixpanel service after three retries."
      // 362: invokestatic com/mixpanel/android/util/MPLog.v (Ljava/lang/String;Ljava/lang/String;)V
      // 365: aload 9
      // 367: areturn
   }
}
