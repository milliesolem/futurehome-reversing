package io.sentry.android.ndk;

final class NativeScope implements INativeScope {
   public static native void nativeAddBreadcrumb(String var0, String var1, String var2, String var3, String var4, String var5);

   public static native void nativeRemoveExtra(String var0);

   public static native void nativeRemoveTag(String var0);

   public static native void nativeRemoveUser();

   public static native void nativeSetExtra(String var0, String var1);

   public static native void nativeSetTag(String var0, String var1);

   public static native void nativeSetUser(String var0, String var1, String var2, String var3);

   @Override
   public void addBreadcrumb(String var1, String var2, String var3, String var4, String var5, String var6) {
      nativeAddBreadcrumb(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public void removeExtra(String var1) {
      nativeRemoveExtra(var1);
   }

   @Override
   public void removeTag(String var1) {
      nativeRemoveTag(var1);
   }

   @Override
   public void removeUser() {
      nativeRemoveUser();
   }

   @Override
   public void setExtra(String var1, String var2) {
      nativeSetExtra(var1, var2);
   }

   @Override
   public void setTag(String var1, String var2) {
      nativeSetTag(var1, var2);
   }

   @Override
   public void setUser(String var1, String var2, String var3, String var4) {
      nativeSetUser(var1, var2, var3, var4);
   }
}
