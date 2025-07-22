package com.mixpanel.android.util;

import android.content.Context;
import java.io.IOException;
import java.util.Map;
import javax.net.ssl.SSLSocketFactory;

public interface RemoteService {
   void checkIsMixpanelBlocked();

   boolean isOnline(Context var1, OfflineMode var2);

   byte[] performRequest(String var1, ProxyServerInteractor var2, Map<String, Object> var3, SSLSocketFactory var4) throws RemoteService.ServiceUnavailableException, IOException;

   public static class ServiceUnavailableException extends Exception {
      private final int mRetryAfter;

      public ServiceUnavailableException(String var1, String var2) {
         super(var1);

         int var3;
         try {
            var3 = Integer.parseInt(var2);
         } catch (NumberFormatException var4) {
            var3 = 0;
         }

         this.mRetryAfter = var3;
      }

      public int getRetryAfter() {
         return this.mRetryAfter;
      }
   }
}
