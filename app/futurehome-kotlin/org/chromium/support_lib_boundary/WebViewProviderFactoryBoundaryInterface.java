package org.chromium.support_lib_boundary;

import android.webkit.WebView;
import java.lang.reflect.InvocationHandler;

public interface WebViewProviderFactoryBoundaryInterface {
   InvocationHandler createWebView(WebView var1);

   InvocationHandler getDropDataProvider();

   InvocationHandler getProfileStore();

   InvocationHandler getProxyController();

   InvocationHandler getServiceWorkerController();

   InvocationHandler getStatics();

   String[] getSupportedFeatures();

   InvocationHandler getTracingController();

   InvocationHandler getWebkitToCompatConverter();
}
