package io.flutter.plugins.webviewflutter;

import android.webkit.WebSettings;

public class WebSettingsProxyApi extends PigeonApiWebSettings {
   public WebSettingsProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public String getUserAgentString(WebSettings var1) {
      return var1.getUserAgentString();
   }

   @Override
   public void setAllowContentAccess(WebSettings var1, boolean var2) {
      var1.setAllowContentAccess(var2);
   }

   @Override
   public void setAllowFileAccess(WebSettings var1, boolean var2) {
      var1.setAllowFileAccess(var2);
   }

   @Override
   public void setBuiltInZoomControls(WebSettings var1, boolean var2) {
      var1.setBuiltInZoomControls(var2);
   }

   @Override
   public void setDisplayZoomControls(WebSettings var1, boolean var2) {
      var1.setDisplayZoomControls(var2);
   }

   @Override
   public void setDomStorageEnabled(WebSettings var1, boolean var2) {
      var1.setDomStorageEnabled(var2);
   }

   @Override
   public void setGeolocationEnabled(WebSettings var1, boolean var2) {
      var1.setGeolocationEnabled(var2);
   }

   @Override
   public void setJavaScriptCanOpenWindowsAutomatically(WebSettings var1, boolean var2) {
      var1.setJavaScriptCanOpenWindowsAutomatically(var2);
   }

   @Override
   public void setJavaScriptEnabled(WebSettings var1, boolean var2) {
      var1.setJavaScriptEnabled(var2);
   }

   @Override
   public void setLoadWithOverviewMode(WebSettings var1, boolean var2) {
      var1.setLoadWithOverviewMode(var2);
   }

   @Override
   public void setMediaPlaybackRequiresUserGesture(WebSettings var1, boolean var2) {
      var1.setMediaPlaybackRequiresUserGesture(var2);
   }

   @Override
   public void setSupportMultipleWindows(WebSettings var1, boolean var2) {
      var1.setSupportMultipleWindows(var2);
   }

   @Override
   public void setSupportZoom(WebSettings var1, boolean var2) {
      var1.setSupportZoom(var2);
   }

   @Override
   public void setTextZoom(WebSettings var1, long var2) {
      var1.setTextZoom((int)var2);
   }

   @Override
   public void setUseWideViewPort(WebSettings var1, boolean var2) {
      var1.setUseWideViewPort(var2);
   }

   @Override
   public void setUserAgentString(WebSettings var1, String var2) {
      var1.setUserAgentString(var2);
   }
}
