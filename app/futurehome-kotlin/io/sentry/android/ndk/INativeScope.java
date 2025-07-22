package io.sentry.android.ndk;

interface INativeScope {
   void addBreadcrumb(String var1, String var2, String var3, String var4, String var5, String var6);

   void removeExtra(String var1);

   void removeTag(String var1);

   void removeUser();

   void setExtra(String var1, String var2);

   void setTag(String var1, String var2);

   void setUser(String var1, String var2, String var3, String var4);
}
