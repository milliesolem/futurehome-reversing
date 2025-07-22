package io.flutter.plugins.firebase.messaging;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;

public class FlutterFirebaseMessagingInitProvider extends ContentProvider {
   public void attachInfo(Context var1, ProviderInfo var2) {
      super.attachInfo(var1, var2);
   }

   public int delete(Uri var1, String var2, String[] var3) {
      return 0;
   }

   public String getType(Uri var1) {
      return null;
   }

   public Uri insert(Uri var1, ContentValues var2) {
      return null;
   }

   public boolean onCreate() {
      if (ContextHolder.getApplicationContext() == null) {
         Context var2 = this.getContext();
         Context var1 = var2;
         if (var2 != null) {
            var1 = var2;
            if (var2.getApplicationContext() != null) {
               var1 = var2.getApplicationContext();
            }
         }

         ContextHolder.setApplicationContext(var1);
      }

      return false;
   }

   public Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      return null;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      return 0;
   }
}
