package dev.fluttercommunity.plus.share

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build.VERSION
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.util.ArrayList

internal class Share(context: Context, activity: Activity?, manager: ShareSuccessManager) {
   private final val context: Context
   private final var activity: Activity?
   private final val manager: ShareSuccessManager

   private final val providerAuthority: String
      private final get() {
         return this.providerAuthority$delegate.getValue() as java.lang.String;
      }


   private final val shareCacheFolder: File
      private final get() {
         return new File(this.getContext().getCacheDir(), "share_plus");
      }


   private final val immutabilityIntentFlags: Int
      private final get() {
         return (this.immutabilityIntentFlags$delegate.getValue() as java.lang.Number).intValue();
      }


   init {
      this.context = var1;
      this.activity = var2;
      this.manager = var3;
      this.providerAuthority$delegate = LazyKt.lazy(new Share$$ExternalSyntheticLambda1(this));
      this.immutabilityIntentFlags$delegate = LazyKt.lazy(new Share$$ExternalSyntheticLambda2());
   }

   private fun clearShareCacheFolder() {
      val var4: File = this.getShareCacheFolder();
      val var3: Array<File> = var4.listFiles();
      if (var4.exists() && var3 != null && var3.length != 0) {
         val var2: Int = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].delete();
         }

         var4.delete();
      }
   }

   @Throws(java/io/IOException::class)
   private fun copyToShareCacheFolder(file: File): File {
      var var2: File = this.getShareCacheFolder();
      if (!var2.exists()) {
         var2.mkdirs();
      }

      var2 = new File(var2, var1.getName());
      FilesKt.copyTo$default(var1, var2, true, 0, 4, null);
      return var2;
   }

   private fun fileIsInShareCache(file: File): Boolean {
      var var3: Boolean;
      try {
         val var6: java.lang.String = var1.getCanonicalPath();
         val var4: java.lang.String = this.getShareCacheFolder().getCanonicalPath();
         var3 = StringsKt.startsWith$default(var6, var4, false, 2, null);
      } catch (var5: IOException) {
         return false;
      }

      return var3;
   }

   private fun getContext(): Context {
      val var1: Activity = this.activity;
      val var2: Context;
      if (this.activity != null) {
         var2 = var1 as Context;
      } else {
         var2 = this.context;
      }

      return var2;
   }

   private fun getMimeTypeBase(mimeType: String?): String {
      if (var1 != null) {
         val var2: java.lang.CharSequence = var1;
         if (StringsKt.contains$default(var1, "/", false, 2, null)) {
            var1 = var1.substring(0, StringsKt.indexOf$default(var2, "/", 0, false, 6, null));
            return var1;
         }
      }

      return "*";
   }

   @Throws(java/io/IOException::class)
   private fun getUrisForPaths(paths: List<String>): ArrayList<Uri> {
      val var2: ArrayList = new ArrayList(var1.size());
      val var4: java.util.Iterator = var1.iterator();

      while (var4.hasNext()) {
         val var3: File = new File(var4.next() as java.lang.String);
         if (this.fileIsInShareCache(var3)) {
            val var5: java.lang.String = this.getShareCacheFolder().getCanonicalPath();
            val var6: StringBuilder = new StringBuilder("Shared file can not be located in '");
            var6.append(var5);
            var6.append("'");
            throw new IOException(var6.toString());
         }

         var2.add(FileProvider.getUriForFile(this.getContext(), this.getProviderAuthority(), this.copyToShareCacheFolder(var3)));
      }

      return var2;
   }

   @JvmStatic
   fun `immutabilityIntentFlags_delegate$lambda$1`(): Int {
      val var0: Int;
      if (VERSION.SDK_INT >= 23) {
         var0 = 33554432;
      } else {
         var0 = 0;
      }

      return var0;
   }

   @JvmStatic
   fun `providerAuthority_delegate$lambda$0`(var0: Share): java.lang.String {
      val var2: java.lang.String = var0.getContext().getPackageName();
      val var1: StringBuilder = new StringBuilder();
      var1.append(var2);
      var1.append(".flutter.share_provider");
      return var1.toString();
   }

   private fun reduceMimeTypes(mimeTypes: List<String>?): String {
      var var5: java.lang.String = "*/*";
      if (var1 != null) {
         var5 = "*/*";
         if (!var1.isEmpty()) {
            var var3: Int = var1.size();
            var var2: Int = 1;
            if (var3 == 1) {
               return CollectionsKt.first(var1);
            }

            var5 = CollectionsKt.first(var1);
            var3 = CollectionsKt.getLastIndex(var1);
            var var4: java.lang.String = var5;
            if (1 <= var3) {
               var4 = var5;

               while (true) {
                  var5 = var4;
                  if (!(var4 == var1.get(var2))) {
                     if (!(this.getMimeTypeBase(var4) == this.getMimeTypeBase(var1.get(var2) as java.lang.String))) {
                        return "*/*";
                     }

                     var4 = this.getMimeTypeBase(var1.get(var2) as java.lang.String);
                     val var12: StringBuilder = new StringBuilder();
                     var12.append(var4);
                     var12.append("/*");
                     var5 = var12.toString();
                  }

                  var4 = var5;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var4 = var5;
               }
            }

            var5 = var4;
         }
      }

      return var5;
   }

   private fun startActivity(intent: Intent, withResult: Boolean) {
      val var3: Activity = this.activity;
      if (this.activity != null) {
         if (var2) {
            var3.startActivityForResult(var1, 22643);
         } else {
            var3.startActivity(var1);
         }
      } else {
         var1.addFlags(268435456);
         if (var2) {
            this.manager.unavailable();
         }

         this.context.startActivity(var1);
      }
   }

   public fun setActivity(activity: Activity?) {
      this.activity = var1;
   }

   public fun share(text: String, subject: String?, withResult: Boolean) {
      val var4: Intent = new Intent();
      var4.setAction("android.intent.action.SEND");
      var4.setType("text/plain");
      var4.putExtra("android.intent.extra.TEXT", var1);
      if (var2 != null) {
         var4.putExtra("android.intent.extra.SUBJECT", var2);
      }

      val var5: Intent;
      if (var3 && VERSION.SDK_INT >= 22) {
         var5 = Share$$ExternalSyntheticApiModelOutline0.m(
            var4,
            null,
            PendingIntent.getBroadcast(this.context, 0, new Intent(this.context, SharePlusPendingIntent.class), 134217728 or this.getImmutabilityIntentFlags())
               .getIntentSender()
         );
      } else {
         var5 = Intent.createChooser(var4, null);
      }

      this.startActivity(var5, var3);
   }

   @Throws(java/io/IOException::class)
   public fun shareFiles(paths: List<String>, mimeTypes: List<String>?, text: String?, subject: String?, withResult: Boolean) {
      this.clearShareCacheFolder();
      val var6: ArrayList = this.getUrisForPaths(var1);
      val var7: Intent = new Intent();
      if (var6.isEmpty() && var3 != null && !StringsKt.isBlank(var3)) {
         this.share(var3, var4, var5);
         return;
      } else {
         if (var6.size() == 1) {
            val var10: java.lang.String;
            if (var2 != null && !var2.isEmpty()) {
               var10 = CollectionsKt.first(var2);
            } else {
               var10 = "*/*";
            }

            var7.setAction("android.intent.action.SEND");
            var7.setType(var10);
            var7.putExtra("android.intent.extra.STREAM", CollectionsKt.first(var6));
         } else {
            var7.setAction("android.intent.action.SEND_MULTIPLE");
            var7.setType(this.reduceMimeTypes(var2));
            var7.putParcelableArrayListExtra("android.intent.extra.STREAM", var6);
         }

         if (var3 != null) {
            var7.putExtra("android.intent.extra.TEXT", var3);
         }

         if (var4 != null) {
            var7.putExtra("android.intent.extra.SUBJECT", var4);
         }

         var7.addFlags(1);
         val var11: Intent;
         if (var5 && VERSION.SDK_INT >= 22) {
            var11 = Share$$ExternalSyntheticApiModelOutline0.m(
               var7,
               null,
               PendingIntent.getBroadcast(
                     this.context, 0, new Intent(this.context, SharePlusPendingIntent.class), 134217728 or this.getImmutabilityIntentFlags()
                  )
                  .getIntentSender()
            );
         } else {
            var11 = Intent.createChooser(var7, null);
         }

         var2 = this.getContext().getPackageManager().queryIntentActivities(var11, 65536);
         val var14: java.util.Iterator = var2.iterator();

         while (var14.hasNext()) {
            val var13: java.lang.String = (var14.next() as ResolveInfo).activityInfo.packageName;

            for (Uri var15 : var6) {
               this.getContext().grantUriPermission(var13, var15, 3);
            }
         }

         this.startActivity(var11, var5);
      }
   }
}
