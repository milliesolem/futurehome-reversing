package io.sentry.rrweb;

import io.sentry.ILogger;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.Objects;
import java.io.IOException;

public abstract class RRWebEvent {
   private long timestamp;
   private RRWebEventType type;

   protected RRWebEvent() {
      this(RRWebEventType.Custom);
   }

   protected RRWebEvent(RRWebEventType var1) {
      this.type = var1;
      this.timestamp = System.currentTimeMillis();
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof RRWebEvent)) {
         return false;
      } else {
         var1 = var1;
         if (this.timestamp != var1.timestamp || this.type != var1.type) {
            var2 = false;
         }

         return var2;
      }
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public RRWebEventType getType() {
      return this.type;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.type, this.timestamp);
   }

   public void setTimestamp(long var1) {
      this.timestamp = var1;
   }

   public void setType(RRWebEventType var1) {
      this.type = var1;
   }

   public static final class Deserializer {
      public boolean deserializeValue(RRWebEvent var1, String var2, ObjectReader var3, ILogger var4) throws Exception {
         var2.hashCode();
         if (!var2.equals("type")) {
            if (!var2.equals("timestamp")) {
               return false;
            } else {
               var1.timestamp = var3.nextLong();
               return true;
            }
         } else {
            var1.type = Objects.requireNonNull(var3.nextOrNull(var4, new RRWebEventType.Deserializer()), "");
            return true;
         }
      }
   }

   public static final class JsonKeys {
      public static final String TAG = "tag";
      public static final String TIMESTAMP = "timestamp";
      public static final String TYPE = "type";
   }

   public static final class Serializer {
      public void serialize(RRWebEvent var1, ObjectWriter var2, ILogger var3) throws IOException {
         var2.name("type").value(var3, var1.type);
         var2.name("timestamp").value(var1.timestamp);
      }
   }
}
