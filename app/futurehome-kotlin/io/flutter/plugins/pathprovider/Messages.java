package io.flutter.plugins.pathprovider;

import android.util.Log;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Messages {
   protected static ArrayList<Object> wrapError(Throwable var0) {
      ArrayList var1 = new ArrayList(3);
      if (var0 instanceof Messages.FlutterError) {
         Messages.FlutterError var3 = (Messages.FlutterError)var0;
         var1.add(var3.code);
         var1.add(var3.getMessage());
         var1.add(var3.details);
      } else {
         var1.add(var0.toString());
         var1.add(var0.getClass().getSimpleName());
         StringBuilder var2 = new StringBuilder("Cause: ");
         var2.append(var0.getCause());
         var2.append(", Stacktrace: ");
         var2.append(Log.getStackTraceString(var0));
         var1.add(var2.toString());
      }

      return var1;
   }

   public static class FlutterError extends RuntimeException {
      public final String code;
      public final Object details;

      public FlutterError(String var1, String var2, Object var3) {
         super(var2);
         this.code = var1;
         this.details = var3;
      }
   }

   public interface PathProviderApi {
      String getApplicationCachePath();

      String getApplicationDocumentsPath();

      String getApplicationSupportPath();

      List<String> getExternalCachePaths();

      String getExternalStoragePath();

      List<String> getExternalStoragePaths(Messages.StorageDirectory var1);

      String getTemporaryPath();
   }

   private static class PigeonCodec extends StandardMessageCodec {
      public static final Messages.PigeonCodec INSTANCE = new Messages.PigeonCodec();

      @Override
      protected Object readValueOfType(byte var1, ByteBuffer var2) {
         if (var1 != -127) {
            return super.readValueOfType(var1, var2);
         } else {
            Object var3 = this.readValue(var2);
            if (var3 == null) {
               var3 = null;
            } else {
               var3 = Messages.StorageDirectory.values()[((Long)var3).intValue()];
            }

            return var3;
         }
      }

      @Override
      protected void writeValue(ByteArrayOutputStream var1, Object var2) {
         if (var2 instanceof Messages.StorageDirectory) {
            var1.write(129);
            if (var2 == null) {
               var2 = null;
            } else {
               var2 = ((Messages.StorageDirectory)var2).index;
            }

            this.writeValue(var1, var2);
         } else {
            super.writeValue(var1, var2);
         }
      }
   }

   public static enum StorageDirectory {
      ALARMS(4),
      DCIM(9),
      DOCUMENTS(10),
      DOWNLOADS(8),
      MOVIES(7),
      MUSIC(1),
      NOTIFICATIONS(5),
      PICTURES(6),
      PODCASTS(2),
      RINGTONES(3),
      ROOT(0);

      private static final Messages.StorageDirectory[] $VALUES = $values();
      final int index;

      private StorageDirectory(int var3) {
         this.index = var3;
      }
   }
}
