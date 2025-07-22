package io.flutter.plugin.editing;

import android.graphics.Insets;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.WindowInsets.Builder;
import android.view.WindowInsetsAnimation.Callback;
import androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.util.Iterator;
import java.util.List;

class ImeSyncDeferringInsetsCallback {
   private boolean animating;
   private ImeSyncDeferringInsetsCallback.AnimationCallback animationCallback;
   private final int deferredInsetTypes = ExternalSyntheticApiModelOutline0.m();
   private ImeSyncDeferringInsetsCallback.InsetsListener insetsListener;
   private WindowInsets lastWindowInsets;
   private boolean needsSave;
   private View view;

   ImeSyncDeferringInsetsCallback(View var1) {
      this.animating = false;
      this.needsSave = false;
      this.view = var1;
      this.animationCallback = new ImeSyncDeferringInsetsCallback.AnimationCallback(this);
      this.insetsListener = new ImeSyncDeferringInsetsCallback.InsetsListener(this);
   }

   Callback getAnimationCallback() {
      return this.animationCallback;
   }

   OnApplyWindowInsetsListener getInsetsListener() {
      return this.insetsListener;
   }

   void install() {
      ExternalSyntheticApiModelOutline0.m(this.view, this.animationCallback);
      this.view.setOnApplyWindowInsetsListener(this.insetsListener);
   }

   void remove() {
      ExternalSyntheticApiModelOutline0.m(this.view, null);
      this.view.setOnApplyWindowInsetsListener(null);
   }

   private class AnimationCallback extends Callback {
      final ImeSyncDeferringInsetsCallback this$0;

      AnimationCallback(ImeSyncDeferringInsetsCallback var1) {
         super(1);
         this.this$0 = var1;
      }

      public void onEnd(WindowInsetsAnimation var1) {
         if (this.this$0.animating && (ExternalSyntheticApiModelOutline0.m(var1) & this.this$0.deferredInsetTypes) != 0) {
            this.this$0.animating = false;
            if (this.this$0.lastWindowInsets != null && this.this$0.view != null) {
               this.this$0.view.dispatchApplyWindowInsets(this.this$0.lastWindowInsets);
            }
         }
      }

      public void onPrepare(WindowInsetsAnimation var1) {
         this.this$0.needsSave = true;
         if ((ExternalSyntheticApiModelOutline0.m(var1) & this.this$0.deferredInsetTypes) != 0) {
            this.this$0.animating = true;
         }
      }

      public WindowInsets onProgress(WindowInsets var1, List<WindowInsetsAnimation> var2) {
         if (this.this$0.animating && !this.this$0.needsSave) {
            Iterator var5 = var2.iterator();
            int var3 = 0;

            while (var5.hasNext()) {
               if ((
                     ExternalSyntheticApiModelOutline0.m(androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0.m(var5.next()))
                        & this.this$0.deferredInsetTypes
                  )
                  != 0) {
                  var3 = 1;
               }
            }

            if (!var3) {
               return var1;
            }

            var3 = this.this$0.view.getWindowSystemUiVisibility();
            if ((var3 & 512) == 0 && (var3 & 2) == 0) {
               var3 = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(
                  androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0.m$1(
                     var1, androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0.m$1()
                  )
               );
            } else {
               var3 = 0;
            }

            AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1();
            Builder var4 = androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0.m(this.this$0.lastWindowInsets);
            Insets var6 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
               0,
               0,
               0,
               Math.max(
                  androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(
                        androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0.m$1(var1, this.this$0.deferredInsetTypes)
                     )
                     - var3,
                  0
               )
            );
            androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0.m(var4, this.this$0.deferredInsetTypes, var6);
            this.this$0.view.onApplyWindowInsets(androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0.m(var4));
         }

         return var1;
      }
   }

   private class InsetsListener implements OnApplyWindowInsetsListener {
      final ImeSyncDeferringInsetsCallback this$0;

      private InsetsListener(ImeSyncDeferringInsetsCallback var1) {
         this.this$0 = var1;
      }

      public WindowInsets onApplyWindowInsets(View var1, WindowInsets var2) {
         this.this$0.view = var1;
         if (this.this$0.needsSave) {
            this.this$0.lastWindowInsets = var2;
            this.this$0.needsSave = false;
         }

         return this.this$0.animating ? androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0.m() : var1.onApplyWindowInsets(var2);
      }
   }
}
