package io.flutter.plugins.webviewflutter;

import android.webkit.WebResourceRequest;
import dev.fluttercommunity.plus.share.Share$$ExternalSyntheticApiModelOutline0;
import java.util.Collections;
import java.util.Map;

public class WebResourceRequestProxyApi extends PigeonApiWebResourceRequest {
   public WebResourceRequestProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   public ProxyApiRegistrar getPigeonRegistrar() {
      return (ProxyApiRegistrar)super.getPigeonRegistrar();
   }

   @Override
   public boolean hasGesture(WebResourceRequest var1) {
      return var1.hasGesture();
   }

   @Override
   public boolean isForMainFrame(WebResourceRequest var1) {
      return var1.isForMainFrame();
   }

   @Override
   public Boolean isRedirect(WebResourceRequest var1) {
      return this.getPigeonRegistrar().sdkIsAtLeast(24) ? Share$$ExternalSyntheticApiModelOutline0.m(var1) : null;
   }

   @Override
   public String method(WebResourceRequest var1) {
      return var1.getMethod();
   }

   @Override
   public Map<String, String> requestHeaders(WebResourceRequest var1) {
      return var1.getRequestHeaders() == null ? Collections.emptyMap() : var1.getRequestHeaders();
   }

   @Override
   public String url(WebResourceRequest var1) {
      return var1.getUrl().toString();
   }
}
