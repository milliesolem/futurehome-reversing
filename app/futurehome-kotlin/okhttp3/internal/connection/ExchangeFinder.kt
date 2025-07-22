package okhttp3.internal.connection

import java.io.IOException
import kotlin.jvm.internal.Intrinsics
import okhttp3.Address
import okhttp3.EventListener
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Route
import okhttp3.internal.connection.RouteSelector.Selection
import okhttp3.internal.http.ExchangeCodec
import okhttp3.internal.http.RealInterceptorChain
import okhttp3.internal.http2.ConnectionShutdownException
import okhttp3.internal.http2.ErrorCode
import okhttp3.internal.http2.StreamResetException

public class ExchangeFinder(connectionPool: RealConnectionPool, address: Address, call: RealCall, eventListener: EventListener) {
   internal final val address: Address
   private final val call: RealCall
   private final val connectionPool: RealConnectionPool
   private final var connectionShutdownCount: Int
   private final val eventListener: EventListener
   private final var nextRouteToTry: Route?
   private final var otherFailureCount: Int
   private final var refusedStreamCount: Int
   private final var routeSelection: Selection?
   private final var routeSelector: RouteSelector?

   init {
      Intrinsics.checkParameterIsNotNull(var1, "connectionPool");
      Intrinsics.checkParameterIsNotNull(var2, "address");
      Intrinsics.checkParameterIsNotNull(var3, "call");
      Intrinsics.checkParameterIsNotNull(var4, "eventListener");
      super();
      this.connectionPool = var1;
      this.address = var2;
      this.call = var3;
      this.eventListener = var4;
   }

   @Throws(java/io/IOException::class)
   private fun findConnection(connectTimeout: Int, readTimeout: Int, writeTimeout: Int, pingIntervalMillis: Int, connectionRetryEnabled: Boolean): RealConnection {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 004: invokevirtual okhttp3/internal/connection/RealCall.isCanceled ()Z
      // 007: ifne 2ef
      // 00a: aload 0
      // 00b: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 00e: invokevirtual okhttp3/internal/connection/RealCall.getConnection ()Lokhttp3/internal/connection/RealConnection;
      // 011: astore 8
      // 013: bipush 1
      // 014: istore 6
      // 016: aload 8
      // 018: ifnull 0ae
      // 01b: aconst_null
      // 01c: checkcast java/net/Socket
      // 01f: astore 7
      // 021: aload 8
      // 023: monitorenter
      // 024: aload 8
      // 026: invokevirtual okhttp3/internal/connection/RealConnection.getNoNewExchanges ()Z
      // 029: ifne 047
      // 02c: aload 0
      // 02d: aload 8
      // 02f: invokevirtual okhttp3/internal/connection/RealConnection.route ()Lokhttp3/Route;
      // 032: invokevirtual okhttp3/Route.address ()Lokhttp3/Address;
      // 035: invokevirtual okhttp3/Address.url ()Lokhttp3/HttpUrl;
      // 038: invokevirtual okhttp3/internal/connection/ExchangeFinder.sameHostAndPort (Lokhttp3/HttpUrl;)Z
      // 03b: ifne 041
      // 03e: goto 047
      // 041: aconst_null
      // 042: astore 7
      // 044: goto 050
      // 047: aload 0
      // 048: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 04b: invokevirtual okhttp3/internal/connection/RealCall.releaseConnectionNoEvents$okhttp ()Ljava/net/Socket;
      // 04e: astore 7
      // 050: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 053: astore 9
      // 055: aload 8
      // 057: monitorexit
      // 058: aload 0
      // 059: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 05c: invokevirtual okhttp3/internal/connection/RealCall.getConnection ()Lokhttp3/internal/connection/RealConnection;
      // 05f: ifnull 086
      // 062: aload 7
      // 064: ifnonnull 06d
      // 067: iload 6
      // 069: istore 1
      // 06a: goto 06f
      // 06d: bipush 0
      // 06e: istore 1
      // 06f: iload 1
      // 070: ifeq 076
      // 073: aload 8
      // 075: areturn
      // 076: new java/lang/IllegalStateException
      // 079: dup
      // 07a: ldc "Check failed."
      // 07c: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 07f: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 082: checkcast java/lang/Throwable
      // 085: athrow
      // 086: aload 7
      // 088: ifnull 090
      // 08b: aload 7
      // 08d: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/net/Socket;)V
      // 090: aload 0
      // 091: getfield okhttp3/internal/connection/ExchangeFinder.eventListener Lokhttp3/EventListener;
      // 094: aload 0
      // 095: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 098: checkcast okhttp3/Call
      // 09b: aload 8
      // 09d: checkcast okhttp3/Connection
      // 0a0: invokevirtual okhttp3/EventListener.connectionReleased (Lokhttp3/Call;Lokhttp3/Connection;)V
      // 0a3: goto 0ae
      // 0a6: astore 7
      // 0a8: aload 8
      // 0aa: monitorexit
      // 0ab: aload 7
      // 0ad: athrow
      // 0ae: aload 0
      // 0af: bipush 0
      // 0b0: putfield okhttp3/internal/connection/ExchangeFinder.refusedStreamCount I
      // 0b3: aload 0
      // 0b4: bipush 0
      // 0b5: putfield okhttp3/internal/connection/ExchangeFinder.connectionShutdownCount I
      // 0b8: aload 0
      // 0b9: bipush 0
      // 0ba: putfield okhttp3/internal/connection/ExchangeFinder.otherFailureCount I
      // 0bd: aload 0
      // 0be: getfield okhttp3/internal/connection/ExchangeFinder.connectionPool Lokhttp3/internal/connection/RealConnectionPool;
      // 0c1: aload 0
      // 0c2: getfield okhttp3/internal/connection/ExchangeFinder.address Lokhttp3/Address;
      // 0c5: aload 0
      // 0c6: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 0c9: aconst_null
      // 0ca: bipush 0
      // 0cb: invokevirtual okhttp3/internal/connection/RealConnectionPool.callAcquirePooledConnection (Lokhttp3/Address;Lokhttp3/internal/connection/RealCall;Ljava/util/List;Z)Z
      // 0ce: ifeq 0f8
      // 0d1: aload 0
      // 0d2: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 0d5: invokevirtual okhttp3/internal/connection/RealCall.getConnection ()Lokhttp3/internal/connection/RealConnection;
      // 0d8: astore 7
      // 0da: aload 7
      // 0dc: ifnonnull 0e2
      // 0df: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 0e2: aload 0
      // 0e3: getfield okhttp3/internal/connection/ExchangeFinder.eventListener Lokhttp3/EventListener;
      // 0e6: aload 0
      // 0e7: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 0ea: checkcast okhttp3/Call
      // 0ed: aload 7
      // 0ef: checkcast okhttp3/Connection
      // 0f2: invokevirtual okhttp3/EventListener.connectionAcquired (Lokhttp3/Call;Lokhttp3/Connection;)V
      // 0f5: aload 7
      // 0f7: areturn
      // 0f8: aload 0
      // 0f9: getfield okhttp3/internal/connection/ExchangeFinder.nextRouteToTry Lokhttp3/Route;
      // 0fc: astore 7
      // 0fe: aload 7
      // 100: ifnull 122
      // 103: aconst_null
      // 104: checkcast java/util/List
      // 107: astore 8
      // 109: aload 7
      // 10b: ifnonnull 111
      // 10e: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 111: aconst_null
      // 112: checkcast okhttp3/Route
      // 115: astore 8
      // 117: aload 0
      // 118: aconst_null
      // 119: putfield okhttp3/internal/connection/ExchangeFinder.nextRouteToTry Lokhttp3/Route;
      // 11c: aconst_null
      // 11d: astore 8
      // 11f: goto 1f3
      // 122: aload 0
      // 123: getfield okhttp3/internal/connection/ExchangeFinder.routeSelection Lokhttp3/internal/connection/RouteSelector$Selection;
      // 126: astore 7
      // 128: aload 7
      // 12a: ifnull 15b
      // 12d: aload 7
      // 12f: ifnonnull 135
      // 132: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 135: aload 7
      // 137: invokevirtual okhttp3/internal/connection/RouteSelector$Selection.hasNext ()Z
      // 13a: ifeq 15b
      // 13d: aconst_null
      // 13e: checkcast java/util/List
      // 141: astore 7
      // 143: aload 0
      // 144: getfield okhttp3/internal/connection/ExchangeFinder.routeSelection Lokhttp3/internal/connection/RouteSelector$Selection;
      // 147: astore 7
      // 149: aload 7
      // 14b: ifnonnull 151
      // 14e: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 151: aload 7
      // 153: invokevirtual okhttp3/internal/connection/RouteSelector$Selection.next ()Lokhttp3/Route;
      // 156: astore 7
      // 158: goto 11c
      // 15b: aload 0
      // 15c: getfield okhttp3/internal/connection/ExchangeFinder.routeSelector Lokhttp3/internal/connection/RouteSelector;
      // 15f: astore 8
      // 161: aload 8
      // 163: astore 7
      // 165: aload 8
      // 167: ifnonnull 192
      // 16a: new okhttp3/internal/connection/RouteSelector
      // 16d: dup
      // 16e: aload 0
      // 16f: getfield okhttp3/internal/connection/ExchangeFinder.address Lokhttp3/Address;
      // 172: aload 0
      // 173: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 176: invokevirtual okhttp3/internal/connection/RealCall.getClient ()Lokhttp3/OkHttpClient;
      // 179: invokevirtual okhttp3/OkHttpClient.getRouteDatabase ()Lokhttp3/internal/connection/RouteDatabase;
      // 17c: aload 0
      // 17d: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 180: checkcast okhttp3/Call
      // 183: aload 0
      // 184: getfield okhttp3/internal/connection/ExchangeFinder.eventListener Lokhttp3/EventListener;
      // 187: invokespecial okhttp3/internal/connection/RouteSelector.<init> (Lokhttp3/Address;Lokhttp3/internal/connection/RouteDatabase;Lokhttp3/Call;Lokhttp3/EventListener;)V
      // 18a: astore 7
      // 18c: aload 0
      // 18d: aload 7
      // 18f: putfield okhttp3/internal/connection/ExchangeFinder.routeSelector Lokhttp3/internal/connection/RouteSelector;
      // 192: aload 7
      // 194: invokevirtual okhttp3/internal/connection/RouteSelector.next ()Lokhttp3/internal/connection/RouteSelector$Selection;
      // 197: astore 7
      // 199: aload 0
      // 19a: aload 7
      // 19c: putfield okhttp3/internal/connection/ExchangeFinder.routeSelection Lokhttp3/internal/connection/RouteSelector$Selection;
      // 19f: aload 7
      // 1a1: invokevirtual okhttp3/internal/connection/RouteSelector$Selection.getRoutes ()Ljava/util/List;
      // 1a4: astore 8
      // 1a6: aload 0
      // 1a7: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 1aa: invokevirtual okhttp3/internal/connection/RealCall.isCanceled ()Z
      // 1ad: ifne 2e2
      // 1b0: aload 0
      // 1b1: getfield okhttp3/internal/connection/ExchangeFinder.connectionPool Lokhttp3/internal/connection/RealConnectionPool;
      // 1b4: aload 0
      // 1b5: getfield okhttp3/internal/connection/ExchangeFinder.address Lokhttp3/Address;
      // 1b8: aload 0
      // 1b9: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 1bc: aload 8
      // 1be: bipush 0
      // 1bf: invokevirtual okhttp3/internal/connection/RealConnectionPool.callAcquirePooledConnection (Lokhttp3/Address;Lokhttp3/internal/connection/RealCall;Ljava/util/List;Z)Z
      // 1c2: ifeq 1ec
      // 1c5: aload 0
      // 1c6: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 1c9: invokevirtual okhttp3/internal/connection/RealCall.getConnection ()Lokhttp3/internal/connection/RealConnection;
      // 1cc: astore 7
      // 1ce: aload 7
      // 1d0: ifnonnull 1d6
      // 1d3: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 1d6: aload 0
      // 1d7: getfield okhttp3/internal/connection/ExchangeFinder.eventListener Lokhttp3/EventListener;
      // 1da: aload 0
      // 1db: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 1de: checkcast okhttp3/Call
      // 1e1: aload 7
      // 1e3: checkcast okhttp3/Connection
      // 1e6: invokevirtual okhttp3/EventListener.connectionAcquired (Lokhttp3/Call;Lokhttp3/Connection;)V
      // 1e9: aload 7
      // 1eb: areturn
      // 1ec: aload 7
      // 1ee: invokevirtual okhttp3/internal/connection/RouteSelector$Selection.next ()Lokhttp3/Route;
      // 1f1: astore 7
      // 1f3: new okhttp3/internal/connection/RealConnection
      // 1f6: dup
      // 1f7: aload 0
      // 1f8: getfield okhttp3/internal/connection/ExchangeFinder.connectionPool Lokhttp3/internal/connection/RealConnectionPool;
      // 1fb: aload 7
      // 1fd: invokespecial okhttp3/internal/connection/RealConnection.<init> (Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Route;)V
      // 200: astore 9
      // 202: aload 0
      // 203: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 206: aload 9
      // 208: invokevirtual okhttp3/internal/connection/RealCall.setConnectionToCancel (Lokhttp3/internal/connection/RealConnection;)V
      // 20b: aload 9
      // 20d: iload 1
      // 20e: iload 2
      // 20f: iload 3
      // 210: iload 4
      // 212: iload 5
      // 214: aload 0
      // 215: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 218: checkcast okhttp3/Call
      // 21b: aload 0
      // 21c: getfield okhttp3/internal/connection/ExchangeFinder.eventListener Lokhttp3/EventListener;
      // 21f: invokevirtual okhttp3/internal/connection/RealConnection.connect (IIIIZLokhttp3/Call;Lokhttp3/EventListener;)V
      // 222: aload 0
      // 223: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 226: astore 10
      // 228: aconst_null
      // 229: checkcast okhttp3/internal/connection/RealConnection
      // 22c: astore 11
      // 22e: aload 10
      // 230: aconst_null
      // 231: invokevirtual okhttp3/internal/connection/RealCall.setConnectionToCancel (Lokhttp3/internal/connection/RealConnection;)V
      // 234: aload 0
      // 235: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 238: invokevirtual okhttp3/internal/connection/RealCall.getClient ()Lokhttp3/OkHttpClient;
      // 23b: invokevirtual okhttp3/OkHttpClient.getRouteDatabase ()Lokhttp3/internal/connection/RouteDatabase;
      // 23e: aload 9
      // 240: invokevirtual okhttp3/internal/connection/RealConnection.route ()Lokhttp3/Route;
      // 243: invokevirtual okhttp3/internal/connection/RouteDatabase.connected (Lokhttp3/Route;)V
      // 246: aload 0
      // 247: getfield okhttp3/internal/connection/ExchangeFinder.connectionPool Lokhttp3/internal/connection/RealConnectionPool;
      // 24a: aload 0
      // 24b: getfield okhttp3/internal/connection/ExchangeFinder.address Lokhttp3/Address;
      // 24e: aload 0
      // 24f: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 252: aload 8
      // 254: bipush 1
      // 255: invokevirtual okhttp3/internal/connection/RealConnectionPool.callAcquirePooledConnection (Lokhttp3/Address;Lokhttp3/internal/connection/RealCall;Ljava/util/List;Z)Z
      // 258: ifeq 290
      // 25b: aload 0
      // 25c: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 25f: invokevirtual okhttp3/internal/connection/RealCall.getConnection ()Lokhttp3/internal/connection/RealConnection;
      // 262: astore 8
      // 264: aload 8
      // 266: ifnonnull 26c
      // 269: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 26c: aload 0
      // 26d: aload 7
      // 26f: putfield okhttp3/internal/connection/ExchangeFinder.nextRouteToTry Lokhttp3/Route;
      // 272: aload 9
      // 274: invokevirtual okhttp3/internal/connection/RealConnection.socket ()Ljava/net/Socket;
      // 277: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/net/Socket;)V
      // 27a: aload 0
      // 27b: getfield okhttp3/internal/connection/ExchangeFinder.eventListener Lokhttp3/EventListener;
      // 27e: aload 0
      // 27f: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 282: checkcast okhttp3/Call
      // 285: aload 8
      // 287: checkcast okhttp3/Connection
      // 28a: invokevirtual okhttp3/EventListener.connectionAcquired (Lokhttp3/Call;Lokhttp3/Connection;)V
      // 28d: aload 8
      // 28f: areturn
      // 290: aload 9
      // 292: monitorenter
      // 293: aload 0
      // 294: getfield okhttp3/internal/connection/ExchangeFinder.connectionPool Lokhttp3/internal/connection/RealConnectionPool;
      // 297: aload 9
      // 299: invokevirtual okhttp3/internal/connection/RealConnectionPool.put (Lokhttp3/internal/connection/RealConnection;)V
      // 29c: aload 0
      // 29d: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 2a0: aload 9
      // 2a2: invokevirtual okhttp3/internal/connection/RealCall.acquireConnectionNoEvents (Lokhttp3/internal/connection/RealConnection;)V
      // 2a5: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 2a8: astore 7
      // 2aa: aload 9
      // 2ac: monitorexit
      // 2ad: aload 0
      // 2ae: getfield okhttp3/internal/connection/ExchangeFinder.eventListener Lokhttp3/EventListener;
      // 2b1: aload 0
      // 2b2: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 2b5: checkcast okhttp3/Call
      // 2b8: aload 9
      // 2ba: checkcast okhttp3/Connection
      // 2bd: invokevirtual okhttp3/EventListener.connectionAcquired (Lokhttp3/Call;Lokhttp3/Connection;)V
      // 2c0: aload 9
      // 2c2: areturn
      // 2c3: astore 7
      // 2c5: aload 9
      // 2c7: monitorexit
      // 2c8: aload 7
      // 2ca: athrow
      // 2cb: astore 9
      // 2cd: aload 0
      // 2ce: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 2d1: astore 7
      // 2d3: aconst_null
      // 2d4: checkcast okhttp3/internal/connection/RealConnection
      // 2d7: astore 8
      // 2d9: aload 7
      // 2db: aconst_null
      // 2dc: invokevirtual okhttp3/internal/connection/RealCall.setConnectionToCancel (Lokhttp3/internal/connection/RealConnection;)V
      // 2df: aload 9
      // 2e1: athrow
      // 2e2: new java/io/IOException
      // 2e5: dup
      // 2e6: ldc "Canceled"
      // 2e8: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 2eb: checkcast java/lang/Throwable
      // 2ee: athrow
      // 2ef: new java/io/IOException
      // 2f2: dup
      // 2f3: ldc "Canceled"
      // 2f5: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 2f8: checkcast java/lang/Throwable
      // 2fb: athrow
   }

   @Throws(java/io/IOException::class)
   private fun findHealthyConnection(
      connectTimeout: Int,
      readTimeout: Int,
      writeTimeout: Int,
      pingIntervalMillis: Int,
      connectionRetryEnabled: Boolean,
      doExtensiveHealthChecks: Boolean
   ): RealConnection {
      while (true) {
         val var9: RealConnection = this.findConnection(var1, var2, var3, var4, var5);
         if (var9.isHealthy(var6)) {
            return var9;
         }

         var9.noNewExchanges$okhttp();
         if (this.nextRouteToTry == null) {
            var var7: Boolean;
            if (this.routeSelection != null) {
               var7 = this.routeSelection.hasNext();
            } else {
               var7 = true;
            }

            if (!var7) {
               var7 = true;
               if (this.routeSelector != null) {
                  var7 = this.routeSelector.hasNext();
               }

               if (!var7) {
                  throw (new IOException("exhausted all routes")) as java.lang.Throwable;
               }
            }
         }
      }
   }

   private fun retryRoute(): Route? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield okhttp3/internal/connection/ExchangeFinder.refusedStreamCount I
      // 04: bipush 1
      // 05: if_icmpgt 64
      // 08: aload 0
      // 09: getfield okhttp3/internal/connection/ExchangeFinder.connectionShutdownCount I
      // 0c: bipush 1
      // 0d: if_icmpgt 64
      // 10: aload 0
      // 11: getfield okhttp3/internal/connection/ExchangeFinder.otherFailureCount I
      // 14: ifle 1a
      // 17: goto 64
      // 1a: aload 0
      // 1b: getfield okhttp3/internal/connection/ExchangeFinder.call Lokhttp3/internal/connection/RealCall;
      // 1e: invokevirtual okhttp3/internal/connection/RealCall.getConnection ()Lokhttp3/internal/connection/RealConnection;
      // 21: astore 3
      // 22: aload 3
      // 23: ifnull 64
      // 26: aload 3
      // 27: monitorenter
      // 28: aload 3
      // 29: invokevirtual okhttp3/internal/connection/RealConnection.getRouteFailureCount$okhttp ()I
      // 2c: istore 1
      // 2d: iload 1
      // 2e: ifeq 35
      // 31: aload 3
      // 32: monitorexit
      // 33: aconst_null
      // 34: areturn
      // 35: aload 3
      // 36: invokevirtual okhttp3/internal/connection/RealConnection.route ()Lokhttp3/Route;
      // 39: invokevirtual okhttp3/Route.address ()Lokhttp3/Address;
      // 3c: invokevirtual okhttp3/Address.url ()Lokhttp3/HttpUrl;
      // 3f: aload 0
      // 40: getfield okhttp3/internal/connection/ExchangeFinder.address Lokhttp3/Address;
      // 43: invokevirtual okhttp3/Address.url ()Lokhttp3/HttpUrl;
      // 46: invokestatic okhttp3/internal/Util.canReuseConnectionFor (Lokhttp3/HttpUrl;Lokhttp3/HttpUrl;)Z
      // 49: istore 2
      // 4a: iload 2
      // 4b: ifne 52
      // 4e: aload 3
      // 4f: monitorexit
      // 50: aconst_null
      // 51: areturn
      // 52: aload 3
      // 53: invokevirtual okhttp3/internal/connection/RealConnection.route ()Lokhttp3/Route;
      // 56: astore 4
      // 58: aload 3
      // 59: monitorexit
      // 5a: aload 4
      // 5c: areturn
      // 5d: astore 4
      // 5f: aload 3
      // 60: monitorexit
      // 61: aload 4
      // 63: athrow
      // 64: aconst_null
      // 65: areturn
   }

   public fun find(client: OkHttpClient, chain: RealInterceptorChain): ExchangeCodec {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "chain");

      try {
         return this.findHealthyConnection(
               var2.getConnectTimeoutMillis$okhttp(),
               var2.getReadTimeoutMillis$okhttp(),
               var2.getWriteTimeoutMillis$okhttp(),
               var1.pingIntervalMillis(),
               var1.retryOnConnectionFailure(),
               var2.getRequest$okhttp().method() == "GET" xor true
            )
            .newCodec$okhttp(var1, var2);
      } catch (var3: RouteException) {
         this.trackFailure(var3.getLastConnectException());
         throw var3 as java.lang.Throwable;
      } catch (var4: IOException) {
         this.trackFailure(var4);
         throw (new RouteException(var4)) as java.lang.Throwable;
      }
   }

   public fun retryAfterFailure(): Boolean {
      if (this.refusedStreamCount == 0 && this.connectionShutdownCount == 0 && this.otherFailureCount == 0) {
         return false;
      } else if (this.nextRouteToTry != null) {
         return true;
      } else {
         val var1: Route = this.retryRoute();
         if (var1 != null) {
            this.nextRouteToTry = var1;
            return true;
         } else if (this.routeSelection != null && this.routeSelection.hasNext()) {
            return true;
         } else {
            return this.routeSelector == null || this.routeSelector.hasNext();
         }
      }
   }

   public fun sameHostAndPort(url: HttpUrl): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "url");
      val var3: HttpUrl = this.address.url();
      val var2: Boolean;
      if (var1.port() == var3.port() && var1.host() == var3.host()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public fun trackFailure(e: IOException) {
      Intrinsics.checkParameterIsNotNull(var1, "e");
      val var2: Route = null as Route;
      this.nextRouteToTry = null;
      if (var1 is StreamResetException && (var1 as StreamResetException).errorCode === ErrorCode.REFUSED_STREAM) {
         this.refusedStreamCount++;
      } else if (var1 is ConnectionShutdownException) {
         this.connectionShutdownCount++;
      } else {
         this.otherFailureCount++;
      }
   }
}
