package io.flutter.plugins.sharedpreferences;

import android.util.Log;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

   private static class PigeonCodec extends StandardMessageCodec {
      public static final Messages.PigeonCodec INSTANCE = new Messages.PigeonCodec();

      @Override
      protected Object readValueOfType(byte var1, ByteBuffer var2) {
         return super.readValueOfType(var1, var2);
      }

      @Override
      protected void writeValue(ByteArrayOutputStream var1, Object var2) {
         super.writeValue(var1, var2);
      }
   }

   public interface SharedPreferencesApi {
      Boolean clear(String var1, List<String> var2);

      Map<String, Object> getAll(String var1, List<String> var2);

      Boolean remove(String var1);

      Boolean setBool(String var1, Boolean var2);

      Boolean setDeprecatedStringList(String var1, List<String> var2);

      Boolean setDouble(String var1, Double var2);

      Boolean setEncodedStringList(String var1, String var2);

      Boolean setInt(String var1, Long var2);

      Boolean setString(String var1, String var2);
   }
}
