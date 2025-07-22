package io.flutter.embedding.engine.plugins.lifecycle;

import androidx.lifecycle.Lifecycle;

public class HiddenLifecycleReference {
   private final Lifecycle lifecycle;

   public HiddenLifecycleReference(Lifecycle var1) {
      this.lifecycle = var1;
   }

   public Lifecycle getLifecycle() {
      return this.lifecycle;
   }
}
