package io.sentry;

import io.sentry.util.Objects;
import java.net.InetAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

final class HostnameCache {
   private static final long GET_HOSTNAME_TIMEOUT = TimeUnit.SECONDS.toMillis(1L);
   private static final long HOSTNAME_CACHE_DURATION = TimeUnit.HOURS.toMillis(5L);
   private static HostnameCache INSTANCE;
   private final long cacheDuration;
   private final ExecutorService executorService;
   private volatile long expirationTimestamp;
   private final Callable<InetAddress> getLocalhost;
   private volatile String hostname;
   private final AtomicBoolean updateRunning = new AtomicBoolean(false);

   private HostnameCache() {
      this(HOSTNAME_CACHE_DURATION);
   }

   HostnameCache(long var1) {
      this(var1, new HostnameCache$$ExternalSyntheticLambda0());
   }

   HostnameCache(long var1, Callable<InetAddress> var3) {
      this.executorService = Executors.newSingleThreadExecutor(new HostnameCache.HostnameCacheThreadFactory());
      this.cacheDuration = var1;
      this.getLocalhost = Objects.requireNonNull(var3, "getLocalhost is required");
      this.updateCache();
   }

   static HostnameCache getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new HostnameCache();
      }

      return INSTANCE;
   }

   private void handleCacheUpdateFailure() {
      this.expirationTimestamp = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1L);
   }

   private void updateCache() {
      HostnameCache$$ExternalSyntheticLambda1 var1 = new HostnameCache$$ExternalSyntheticLambda1(this);

      try {
         this.executorService.submit(var1).get(GET_HOSTNAME_TIMEOUT, TimeUnit.MILLISECONDS);
      } catch (InterruptedException var2) {
         Thread.currentThread().interrupt();
         this.handleCacheUpdateFailure();
      } catch (TimeoutException | RuntimeException | ExecutionException var3) {
         this.handleCacheUpdateFailure();
      }
   }

   void close() {
      this.executorService.shutdown();
   }

   String getHostname() {
      if (this.expirationTimestamp < System.currentTimeMillis() && this.updateRunning.compareAndSet(false, true)) {
         this.updateCache();
      }

      return this.hostname;
   }

   boolean isClosed() {
      return this.executorService.isShutdown();
   }

   private static final class HostnameCacheThreadFactory implements ThreadFactory {
      private int cnt;

      private HostnameCacheThreadFactory() {
      }

      @Override
      public Thread newThread(Runnable var1) {
         StringBuilder var3 = new StringBuilder("SentryHostnameCache-");
         int var2 = this.cnt++;
         var3.append(var2);
         Thread var4 = new Thread(var1, var3.toString());
         var4.setDaemon(true);
         return var4;
      }
   }
}
