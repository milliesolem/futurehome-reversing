package io.sentry;

import io.sentry.util.JsonSerializationUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class JsonObjectSerializer {
   public static final String OBJECT_PLACEHOLDER = "[OBJECT]";
   public final JsonReflectionObjectSerializer jsonReflectionObjectSerializer;

   public JsonObjectSerializer(int var1) {
      this.jsonReflectionObjectSerializer = new JsonReflectionObjectSerializer(var1);
   }

   private void serializeCollection(ObjectWriter var1, ILogger var2, Collection<?> var3) throws IOException {
      var1.beginArray();
      Iterator var4 = var3.iterator();

      while (var4.hasNext()) {
         this.serialize(var1, var2, var4.next());
      }

      var1.endArray();
   }

   private void serializeDate(ObjectWriter var1, ILogger var2, Date var3) throws IOException {
      try {
         var1.value(DateUtils.getTimestamp(var3));
      } catch (Exception var4) {
         var2.log(SentryLevel.ERROR, "Error when serializing Date", var4);
         var1.nullValue();
      }
   }

   private void serializeMap(ObjectWriter var1, ILogger var2, Map<?, ?> var3) throws IOException {
      var1.beginObject();

      for (Object var5 : var3.keySet()) {
         if (var5 instanceof String) {
            var1.name((String)var5);
            this.serialize(var1, var2, var3.get(var5));
         }
      }

      var1.endObject();
   }

   private void serializeTimeZone(ObjectWriter var1, ILogger var2, TimeZone var3) throws IOException {
      try {
         var1.value(var3.getID());
      } catch (Exception var4) {
         var2.log(SentryLevel.ERROR, "Error when serializing TimeZone", var4);
         var1.nullValue();
      }
   }

   public void serialize(ObjectWriter var1, ILogger var2, Object var3) throws IOException {
      if (var3 == null) {
         var1.nullValue();
      } else if (var3 instanceof Character) {
         var1.value(Character.toString((Character)var3));
      } else if (var3 instanceof String) {
         var1.value((String)var3);
      } else if (var3 instanceof Boolean) {
         var1.value(((Boolean)var3).booleanValue());
      } else if (var3 instanceof Number) {
         var1.value((Number)var3);
      } else if (var3 instanceof Date) {
         this.serializeDate(var1, var2, (Date)var3);
      } else if (var3 instanceof TimeZone) {
         this.serializeTimeZone(var1, var2, (TimeZone)var3);
      } else if (var3 instanceof JsonSerializable) {
         ((JsonSerializable)var3).serialize(var1, var2);
      } else if (var3 instanceof Collection) {
         this.serializeCollection(var1, var2, (Collection<?>)var3);
      } else if (var3.getClass().isArray()) {
         this.serializeCollection(var1, var2, Arrays.asList((Object[])var3));
      } else if (var3 instanceof Map) {
         this.serializeMap(var1, var2, (Map<?, ?>)var3);
      } else if (var3 instanceof Locale) {
         var1.value(var3.toString());
      } else if (var3 instanceof AtomicIntegerArray) {
         this.serializeCollection(var1, var2, JsonSerializationUtils.atomicIntegerArrayToList((AtomicIntegerArray)var3));
      } else if (var3 instanceof AtomicBoolean) {
         var1.value(((AtomicBoolean)var3).get());
      } else if (var3 instanceof URI) {
         var1.value(var3.toString());
      } else if (var3 instanceof InetAddress) {
         var1.value(var3.toString());
      } else if (var3 instanceof UUID) {
         var1.value(var3.toString());
      } else if (var3 instanceof Currency) {
         var1.value(var3.toString());
      } else if (var3 instanceof Calendar) {
         this.serializeMap(var1, var2, JsonSerializationUtils.calendarToMap((Calendar)var3));
      } else if (var3.getClass().isEnum()) {
         var1.value(var3.toString());
      } else {
         try {
            this.serialize(var1, var2, this.jsonReflectionObjectSerializer.serialize(var3, var2));
         } catch (Exception var4) {
            var2.log(SentryLevel.ERROR, "Failed serializing unknown object.", var4);
            var1.value("[OBJECT]");
         }
      }
   }
}
