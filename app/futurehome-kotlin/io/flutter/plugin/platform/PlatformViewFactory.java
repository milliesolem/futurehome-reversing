package io.flutter.plugin.platform;

import android.content.Context;
import io.flutter.plugin.common.MessageCodec;

public abstract class PlatformViewFactory {
   private final MessageCodec<Object> createArgsCodec;

   public PlatformViewFactory(MessageCodec<Object> var1) {
      this.createArgsCodec = var1;
   }

   public abstract PlatformView create(Context var1, int var2, Object var3);

   public final MessageCodec<Object> getCreateArgsCodec() {
      return this.createArgsCodec;
   }
}
