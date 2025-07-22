package io.flutter.plugins.webviewflutter;

import android.content.Context;
import android.view.View;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

class FlutterViewFactory extends PlatformViewFactory {
   private final AndroidWebkitLibraryPigeonInstanceManager instanceManager;

   FlutterViewFactory(AndroidWebkitLibraryPigeonInstanceManager var1) {
      super(StandardMessageCodec.INSTANCE);
      this.instanceManager = var1;
   }

   @Override
   public PlatformView create(Context var1, int var2, Object var3) {
      Integer var5 = (Integer)var3;
      if (var5 != null) {
         Object var6 = this.instanceManager.getInstance(var5.intValue());
         if (var6 instanceof PlatformView) {
            return (PlatformView)var6;
         } else if (var6 instanceof View) {
            return new PlatformView(this, var6) {
               final FlutterViewFactory this$0;
               final Object val$instance;

               {
                  this.this$0 = var1;
                  this.val$instance = var2x;
               }

               @Override
               public void dispose() {
               }

               @Override
               public View getView() {
                  return (View)this.val$instance;
               }
            };
         } else {
            StringBuilder var4 = new StringBuilder("Unable to find a PlatformView or View instance: ");
            var4.append(var3);
            var4.append(", ");
            var4.append(var6);
            throw new IllegalStateException(var4.toString());
         }
      } else {
         throw new IllegalStateException("An identifier is required to retrieve a View instance.");
      }
   }
}
