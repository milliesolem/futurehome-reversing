package org.chromium.support_lib_boundary;

import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import java.lang.reflect.InvocationHandler;

public interface WebkitToCompatConverterBoundaryInterface {
   InvocationHandler convertCookieManager(Object var1);

   Object convertSafeBrowsingResponse(InvocationHandler var1);

   InvocationHandler convertSafeBrowsingResponse(Object var1);

   Object convertServiceWorkerSettings(InvocationHandler var1);

   InvocationHandler convertServiceWorkerSettings(Object var1);

   InvocationHandler convertSettings(WebSettings var1);

   Object convertWebMessagePort(InvocationHandler var1);

   InvocationHandler convertWebMessagePort(Object var1);

   Object convertWebResourceError(InvocationHandler var1);

   InvocationHandler convertWebResourceError(Object var1);

   InvocationHandler convertWebResourceRequest(WebResourceRequest var1);
}
