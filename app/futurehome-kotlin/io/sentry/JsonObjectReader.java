package io.sentry;

import io.sentry.vendor.gson.stream.JsonReader;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.DesugarTimeZone;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public final class JsonObjectReader implements ObjectReader {
   private final JsonReader jsonReader;

   public JsonObjectReader(Reader var1) {
      this.jsonReader = new JsonReader(var1);
   }

   @Override
   public void beginArray() throws IOException {
      this.jsonReader.beginArray();
   }

   @Override
   public void beginObject() throws IOException {
      this.jsonReader.beginObject();
   }

   @Override
   public void close() throws IOException {
      this.jsonReader.close();
   }

   @Override
   public void endArray() throws IOException {
      this.jsonReader.endArray();
   }

   @Override
   public void endObject() throws IOException {
      this.jsonReader.endObject();
   }

   @Override
   public boolean hasNext() throws IOException {
      return this.jsonReader.hasNext();
   }

   @Override
   public boolean nextBoolean() throws IOException {
      return this.jsonReader.nextBoolean();
   }

   @Override
   public Boolean nextBooleanOrNull() throws IOException {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         return this.jsonReader.nextBoolean();
      }
   }

   @Override
   public Date nextDateOrNull(ILogger var1) throws IOException {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         return ObjectReader$_CC.dateOrNull(this.jsonReader.nextString(), var1);
      }
   }

   @Override
   public double nextDouble() throws IOException {
      return this.jsonReader.nextDouble();
   }

   @Override
   public Double nextDoubleOrNull() throws IOException {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         return this.jsonReader.nextDouble();
      }
   }

   @Override
   public float nextFloat() throws IOException {
      return (float)this.jsonReader.nextDouble();
   }

   @Override
   public Float nextFloatOrNull() throws IOException {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         return this.nextFloat();
      }
   }

   @Override
   public int nextInt() throws IOException {
      return this.jsonReader.nextInt();
   }

   @Override
   public Integer nextIntegerOrNull() throws IOException {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         return this.jsonReader.nextInt();
      }
   }

   @Override
   public <T> List<T> nextListOrNull(ILogger var1, JsonDeserializer<T> var2) throws IOException {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         this.jsonReader.beginArray();
         ArrayList var3 = new ArrayList();
         if (this.jsonReader.hasNext()) {
            do {
               try {
                  var3.add(var2.deserialize(this, var1));
               } catch (Exception var5) {
                  var1.log(SentryLevel.WARNING, "Failed to deserialize object in list.", var5);
               }
            } while (this.jsonReader.peek() == JsonToken.BEGIN_OBJECT);
         }

         this.jsonReader.endArray();
         return var3;
      }
   }

   @Override
   public long nextLong() throws IOException {
      return this.jsonReader.nextLong();
   }

   @Override
   public Long nextLongOrNull() throws IOException {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         return this.jsonReader.nextLong();
      }
   }

   @Override
   public <T> Map<String, List<T>> nextMapOfListOrNull(ILogger var1, JsonDeserializer<T> var2) throws IOException {
      if (this.peek() == JsonToken.NULL) {
         this.nextNull();
         return null;
      } else {
         HashMap var3 = new HashMap();
         this.beginObject();
         if (this.hasNext()) {
            do {
               String var5 = this.nextName();
               List var4 = this.nextListOrNull(var1, var2);
               if (var4 != null) {
                  var3.put(var5, var4);
               }
            } while (this.peek() == JsonToken.BEGIN_OBJECT || this.peek() == JsonToken.NAME);
         }

         this.endObject();
         return var3;
      }
   }

   @Override
   public <T> Map<String, T> nextMapOrNull(ILogger var1, JsonDeserializer<T> var2) throws IOException {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         this.jsonReader.beginObject();
         HashMap var3 = new HashMap();
         if (this.jsonReader.hasNext()) {
            do {
               try {
                  var3.put(this.jsonReader.nextName(), var2.deserialize(this, var1));
               } catch (Exception var5) {
                  var1.log(SentryLevel.WARNING, "Failed to deserialize object in map.", var5);
               }
            } while (this.jsonReader.peek() == JsonToken.BEGIN_OBJECT || this.jsonReader.peek() == JsonToken.NAME);
         }

         this.jsonReader.endObject();
         return var3;
      }
   }

   @Override
   public String nextName() throws IOException {
      return this.jsonReader.nextName();
   }

   @Override
   public void nextNull() throws IOException {
      this.jsonReader.nextNull();
   }

   @Override
   public Object nextObjectOrNull() throws IOException {
      return new JsonObjectDeserializer().deserialize(this);
   }

   @Override
   public <T> T nextOrNull(ILogger var1, JsonDeserializer<T> var2) throws Exception {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         return (T)var2.deserialize(this, var1);
      }
   }

   @Override
   public String nextString() throws IOException {
      return this.jsonReader.nextString();
   }

   @Override
   public String nextStringOrNull() throws IOException {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         return this.jsonReader.nextString();
      }
   }

   @Override
   public TimeZone nextTimeZoneOrNull(ILogger var1) throws IOException {
      if (this.jsonReader.peek() == JsonToken.NULL) {
         this.jsonReader.nextNull();
         return null;
      } else {
         try {
            return DesugarTimeZone.getTimeZone(this.jsonReader.nextString());
         } catch (Exception var3) {
            var1.log(SentryLevel.ERROR, "Error when deserializing TimeZone", var3);
            return null;
         }
      }
   }

   @Override
   public void nextUnknown(ILogger var1, Map<String, Object> var2, String var3) {
      try {
         var2.put(var3, this.nextObjectOrNull());
      } catch (Exception var4) {
         var1.log(SentryLevel.ERROR, var4, "Error deserializing unknown key: %s", var3);
      }
   }

   @Override
   public JsonToken peek() throws IOException {
      return this.jsonReader.peek();
   }

   @Override
   public void setLenient(boolean var1) {
      this.jsonReader.setLenient(var1);
   }

   @Override
   public void skipValue() throws IOException {
      this.jsonReader.skipValue();
   }
}
