package io.sentry.internal.gestures;

import io.sentry.util.Objects;
import java.lang.ref.WeakReference;

public final class UiElement {
   final String className;
   final String origin;
   final String resourceName;
   final String tag;
   final WeakReference<Object> viewRef;

   public UiElement(Object var1, String var2, String var3, String var4, String var5) {
      this.viewRef = new WeakReference<>(var1);
      this.className = var2;
      this.resourceName = var3;
      this.tag = var4;
      this.origin = var5;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.className, var1.className) || !Objects.equals(this.resourceName, var1.resourceName) || !Objects.equals(this.tag, var1.tag)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getClassName() {
      return this.className;
   }

   public String getIdentifier() {
      String var1 = this.resourceName;
      return var1 != null ? var1 : Objects.requireNonNull(this.tag, "UiElement.tag can't be null");
   }

   public String getOrigin() {
      return this.origin;
   }

   public String getResourceName() {
      return this.resourceName;
   }

   public String getTag() {
      return this.tag;
   }

   public Object getView() {
      return this.viewRef.get();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.viewRef, this.resourceName, this.tag);
   }

   public static enum Type {
      CLICKABLE,
      SCROLLABLE;
      private static final UiElement.Type[] $VALUES = $values();
   }
}
