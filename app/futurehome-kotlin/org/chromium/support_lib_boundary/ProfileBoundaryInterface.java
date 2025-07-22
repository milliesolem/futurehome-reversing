package org.chromium.support_lib_boundary;

import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ServiceWorkerController;
import android.webkit.ValueCallback;
import android.webkit.WebStorage;
import java.lang.reflect.InvocationHandler;

public interface ProfileBoundaryInterface {
   void cancelPrefetch(String var1);

   void clearPrefetch(String var1, ValueCallback<InvocationHandler> var2);

   CookieManager getCookieManager();

   GeolocationPermissions getGeoLocationPermissions();

   String getName();

   ServiceWorkerController getServiceWorkerController();

   WebStorage getWebStorage();

   void prefetchUrl(String var1, ValueCallback<InvocationHandler> var2);

   void prefetchUrl(String var1, InvocationHandler var2, ValueCallback<InvocationHandler> var3);
}
