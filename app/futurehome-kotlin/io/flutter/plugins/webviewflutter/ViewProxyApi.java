package io.flutter.plugins.webviewflutter;

import android.view.View;

public class ViewProxyApi extends PigeonApiView {
   public ViewProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public WebViewPoint getScrollPosition(View var1) {
      return new WebViewPoint(var1.getScrollX(), var1.getScrollY());
   }

   @Override
   public void scrollBy(View var1, long var2, long var4) {
      var1.scrollBy((int)var2, (int)var4);
   }

   @Override
   public void scrollTo(View var1, long var2, long var4) {
      var1.scrollTo((int)var2, (int)var4);
   }
}
