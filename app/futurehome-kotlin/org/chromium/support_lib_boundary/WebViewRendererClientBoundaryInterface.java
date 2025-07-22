package org.chromium.support_lib_boundary;

import android.webkit.WebView;
import java.lang.reflect.InvocationHandler;

public interface WebViewRendererClientBoundaryInterface extends FeatureFlagHolderBoundaryInterface {
   void onRendererResponsive(WebView var1, InvocationHandler var2);

   void onRendererUnresponsive(WebView var1, InvocationHandler var2);
}
