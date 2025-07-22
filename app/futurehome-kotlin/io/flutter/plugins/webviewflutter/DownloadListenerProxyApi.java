package io.flutter.plugins.webviewflutter;

import android.webkit.DownloadListener;

public class DownloadListenerProxyApi extends PigeonApiDownloadListener {
   public DownloadListenerProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   public ProxyApiRegistrar getPigeonRegistrar() {
      return (ProxyApiRegistrar)super.getPigeonRegistrar();
   }

   @Override
   public DownloadListener pigeon_defaultConstructor() {
      return new DownloadListenerProxyApi.DownloadListenerImpl(this);
   }

   public static class DownloadListenerImpl implements DownloadListener {
      private final DownloadListenerProxyApi api;

      public DownloadListenerImpl(DownloadListenerProxyApi var1) {
         this.api = var1;
      }

      public void onDownloadStart(String var1, String var2, String var3, String var4, long var5) {
         this.api
            .getPigeonRegistrar()
            .runOnMainThread(new DownloadListenerProxyApi$DownloadListenerImpl$$ExternalSyntheticLambda1(this, var1, var2, var3, var4, var5));
      }
   }
}
