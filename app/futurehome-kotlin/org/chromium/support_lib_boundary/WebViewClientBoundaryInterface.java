package org.chromium.support_lib_boundary;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import java.lang.reflect.InvocationHandler;

public interface WebViewClientBoundaryInterface extends FeatureFlagHolderBoundaryInterface {
   void onPageCommitVisible(WebView var1, String var2);

   void onReceivedError(WebView var1, WebResourceRequest var2, InvocationHandler var3);

   void onReceivedHttpError(WebView var1, WebResourceRequest var2, WebResourceResponse var3);

   void onSafeBrowsingHit(WebView var1, WebResourceRequest var2, int var3, InvocationHandler var4);

   boolean shouldOverrideUrlLoading(WebView var1, WebResourceRequest var2);
}
