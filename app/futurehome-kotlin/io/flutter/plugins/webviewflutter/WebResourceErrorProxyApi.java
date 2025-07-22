package io.flutter.plugins.webviewflutter;

import android.webkit.WebResourceError;
import dev.fluttercommunity.plus.share.Share$$ExternalSyntheticApiModelOutline0;

public class WebResourceErrorProxyApi extends PigeonApiWebResourceError {
   public WebResourceErrorProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public String description(WebResourceError var1) {
      return Share$$ExternalSyntheticApiModelOutline0.m(var1).toString();
   }

   @Override
   public long errorCode(WebResourceError var1) {
      return Share$$ExternalSyntheticApiModelOutline0.m(var1);
   }
}
