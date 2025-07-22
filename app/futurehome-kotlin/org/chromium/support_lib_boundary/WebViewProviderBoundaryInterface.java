package org.chromium.support_lib_boundary;

import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import java.lang.reflect.InvocationHandler;

public interface WebViewProviderBoundaryInterface {
   InvocationHandler addDocumentStartJavaScript(String var1, String[] var2);

   void addWebMessageListener(String var1, String[] var2, InvocationHandler var3);

   InvocationHandler[] createWebMessageChannel();

   InvocationHandler getProfile();

   WebChromeClient getWebChromeClient();

   WebViewClient getWebViewClient();

   InvocationHandler getWebViewRenderer();

   InvocationHandler getWebViewRendererClient();

   void insertVisualStateCallback(long var1, InvocationHandler var3);

   boolean isAudioMuted();

   void postMessageToMainFrame(InvocationHandler var1, Uri var2);

   void removeWebMessageListener(String var1);

   void setAudioMuted(boolean var1);

   void setProfile(String var1);

   void setWebViewRendererClient(InvocationHandler var1);
}
