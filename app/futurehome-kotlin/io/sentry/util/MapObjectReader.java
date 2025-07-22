package io.sentry.util;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.ObjectReader;
import io.sentry.ObjectReader$_CC;
import io.sentry.SentryLevel;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.DesugarTimeZone;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public final class MapObjectReader implements ObjectReader {
   private final Deque<Entry<String, Object>> stack;

   public MapObjectReader(Map<String, Object> var1) {
      ArrayDeque var2 = new ArrayDeque();
      this.stack = var2;
      var2.addLast(new SimpleEntry<>(null, var1));
   }

   private <T> T nextValueOrNull() throws IOException {
      try {
         return this.nextValueOrNull(null, null);
      } catch (Exception var2) {
         throw new IOException(var2);
      }
   }

   private <T> T nextValueOrNull(ILogger var1, JsonDeserializer<T> var2) throws Exception {
      Entry var3 = this.stack.peekLast();
      if (var3 == null) {
         return null;
      } else {
         Object var4 = var3.getValue();
         if (var2 != null && var1 != null) {
            return (T)var2.deserialize(this, var1);
         } else {
            this.stack.removeLast();
            return (T)var4;
         }
      }
   }

   @Override
   public void beginArray() throws IOException {
      Entry var2 = this.stack.removeLast();
      if (var2 == null) {
         throw new IOException("No more entries");
      } else {
         Object var4 = var2.getValue();
         if (!(var4 instanceof List)) {
            throw new IOException("Current token is not an object");
         } else {
            this.stack.addLast(new SimpleEntry<>(null, JsonToken.END_ARRAY));
            List var3 = (List)var4;

            for (int var1 = var3.size() - 1; var1 >= 0; var1--) {
               var4 = var3.get(var1);
               this.stack.addLast(new SimpleEntry<>(null, var4));
            }
         }
      }
   }

   @Override
   public void beginObject() throws IOException {
      Entry var1 = this.stack.removeLast();
      if (var1 == null) {
         throw new IOException("No more entries");
      } else {
         Object var3 = var1.getValue();
         if (!(var3 instanceof Map)) {
            throw new IOException("Current token is not an object");
         } else {
            this.stack.addLast(new SimpleEntry<>(null, JsonToken.END_OBJECT));

            for (var3 : ((Map)var3).entrySet()) {
               this.stack.addLast((Entry<String, Object>)var3);
            }
         }
      }
   }

   @Override
   public void close() throws IOException {
      this.stack.clear();
   }

   @Override
   public void endArray() throws IOException {
      if (this.stack.size() > 1) {
         this.stack.removeLast();
      }
   }

   @Override
   public void endObject() throws IOException {
      if (this.stack.size() > 1) {
         this.stack.removeLast();
      }
   }

   @Override
   public boolean hasNext() throws IOException {
      return this.stack.isEmpty() ^ true;
   }

   @Override
   public boolean nextBoolean() throws IOException {
      Boolean var1 = this.nextValueOrNull();
      if (var1 != null) {
         return var1;
      } else {
         throw new IOException("Expected boolean");
      }
   }

   @Override
   public Boolean nextBooleanOrNull() throws IOException {
      return this.nextValueOrNull();
   }

   @Override
   public Date nextDateOrNull(ILogger var1) throws IOException {
      return ObjectReader$_CC.dateOrNull(this.nextStringOrNull(), var1);
   }

   @Override
   public double nextDouble() throws IOException {
      Object var1 = this.nextValueOrNull();
      if (var1 instanceof Number) {
         return ((Number)var1).doubleValue();
      } else {
         throw new IOException("Expected double");
      }
   }

   @Override
   public Double nextDoubleOrNull() throws IOException {
      Object var1 = this.nextValueOrNull();
      return var1 instanceof Number ? ((Number)var1).doubleValue() : null;
   }

   @Override
   public float nextFloat() throws IOException {
      Object var1 = this.nextValueOrNull();
      if (var1 instanceof Number) {
         return ((Number)var1).floatValue();
      } else {
         throw new IOException("Expected float");
      }
   }

   @Override
   public Float nextFloatOrNull() throws IOException {
      Object var1 = this.nextValueOrNull();
      return var1 instanceof Number ? ((Number)var1).floatValue() : null;
   }

   @Override
   public int nextInt() throws IOException {
      Object var1 = this.nextValueOrNull();
      if (var1 instanceof Number) {
         return ((Number)var1).intValue();
      } else {
         throw new IOException("Expected int");
      }
   }

   @Override
   public Integer nextIntegerOrNull() throws IOException {
      Object var1 = this.nextValueOrNull();
      return var1 instanceof Number ? ((Number)var1).intValue() : null;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public <T> List<T> nextListOrNull(ILogger var1, JsonDeserializer<T> var2) throws IOException {
      if (this.peek() == JsonToken.NULL) {
         this.nextNull();
         return null;
      } else {
         boolean var3;
         ArrayList var5;
         try {
            this.beginArray();
            var5 = new ArrayList();
            var3 = this.hasNext();
         } catch (Exception var9) {
            throw new IOException(var9);
         }

         if (var3) {
            while (true) {
               try {
                  var5.add(var2.deserialize(this, var1));
               } catch (Exception var8) {
                  Exception var4 = var8;

                  try {
                     var1.log(SentryLevel.WARNING, "Failed to deserialize object in list.", var4);
                  } catch (Exception var7) {
                     throw new IOException(var7);
                  }
               }

               try {
                  if (this.peek() != JsonToken.BEGIN_OBJECT) {
                     break;
                  }
               } catch (Exception var10) {
                  throw new IOException(var10);
               }
            }
         }

         try {
            this.endArray();
            return var5;
         } catch (Exception var6) {
            throw new IOException(var6);
         }
      }
   }

   @Override
   public long nextLong() throws IOException {
      Object var1 = this.nextValueOrNull();
      if (var1 instanceof Number) {
         return ((Number)var1).longValue();
      } else {
         throw new IOException("Expected long");
      }
   }

   @Override
   public Long nextLongOrNull() throws IOException {
      Object var1 = this.nextValueOrNull();
      return var1 instanceof Number ? ((Number)var1).longValue() : null;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public <T> Map<String, List<T>> nextMapOfListOrNull(ILogger var1, JsonDeserializer<T> var2) throws IOException {
      if (this.peek() == JsonToken.NULL) {
         this.nextNull();
         return null;
      } else {
         HashMap var5 = new HashMap();

         label41: {
            try {
               this.beginObject();
               if (!this.hasNext()) {
                  break label41;
               }
            } catch (Exception var10) {
               throw new IOException(var10);
            }

            while (true) {
               String var3;
               List var4;
               try {
                  var3 = this.nextName();
                  var4 = this.nextListOrNull(var1, var2);
               } catch (Exception var8) {
                  throw new IOException(var8);
               }

               if (var4 != null) {
                  try {
                     var5.put(var3, var4);
                  } catch (Exception var7) {
                     throw new IOException(var7);
                  }
               }

               try {
                  if (this.peek() != JsonToken.BEGIN_OBJECT && this.peek() != JsonToken.NAME) {
                     break;
                  }
               } catch (Exception var9) {
                  throw new IOException(var9);
               }
            }
         }

         try {
            this.endObject();
            return var5;
         } catch (Exception var6) {
            throw new IOException(var6);
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public <T> Map<String, T> nextMapOrNull(ILogger var1, JsonDeserializer<T> var2) throws IOException {
      if (this.peek() == JsonToken.NULL) {
         this.nextNull();
         return null;
      } else {
         boolean var3;
         HashMap var5;
         try {
            this.beginObject();
            var5 = new HashMap();
            var3 = this.hasNext();
         } catch (Exception var9) {
            throw new IOException(var9);
         }

         if (var3) {
            while (true) {
               try {
                  var5.put(this.nextName(), var2.deserialize(this, var1));
               } catch (Exception var8) {
                  Exception var4 = var8;

                  try {
                     var1.log(SentryLevel.WARNING, "Failed to deserialize object in map.", var4);
                  } catch (Exception var7) {
                     throw new IOException(var7);
                  }
               }

               try {
                  if (this.peek() != JsonToken.BEGIN_OBJECT && this.peek() != JsonToken.NAME) {
                     break;
                  }
               } catch (Exception var10) {
                  throw new IOException(var10);
               }
            }
         }

         try {
            this.endObject();
            return var5;
         } catch (Exception var6) {
            throw new IOException(var6);
         }
      }
   }

   @Override
   public String nextName() throws IOException {
      Entry var1 = this.stack.peekLast();
      if (var1 != null && var1.getKey() != null) {
         return (String)var1.getKey();
      } else {
         StringBuilder var2 = new StringBuilder("Expected a name but was ");
         var2.append(this.peek());
         throw new IOException(var2.toString());
      }
   }

   @Override
   public void nextNull() throws IOException {
      if (this.nextValueOrNull() != null) {
         StringBuilder var1 = new StringBuilder("Expected null but was ");
         var1.append(this.peek());
         throw new IOException(var1.toString());
      }
   }

   @Override
   public Object nextObjectOrNull() throws IOException {
      return this.nextValueOrNull();
   }

   @Override
   public <T> T nextOrNull(ILogger var1, JsonDeserializer<T> var2) throws Exception {
      return this.nextValueOrNull(var1, var2);
   }

   @Override
   public String nextString() throws IOException {
      String var1 = this.nextValueOrNull();
      if (var1 != null) {
         return var1;
      } else {
         throw new IOException("Expected string");
      }
   }

   @Override
   public String nextStringOrNull() throws IOException {
      return this.nextValueOrNull();
   }

   @Override
   public TimeZone nextTimeZoneOrNull(ILogger var1) throws IOException {
      String var2 = this.nextStringOrNull();
      TimeZone var3;
      if (var2 != null) {
         var3 = DesugarTimeZone.getTimeZone(var2);
      } else {
         var3 = null;
      }

      return var3;
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
      if (this.stack.isEmpty()) {
         return JsonToken.END_DOCUMENT;
      } else {
         Entry var1 = this.stack.peekLast();
         if (var1 == null) {
            return JsonToken.END_DOCUMENT;
         } else if (var1.getKey() != null) {
            return JsonToken.NAME;
         } else {
            Object var2 = var1.getValue();
            if (var2 instanceof Map) {
               return JsonToken.BEGIN_OBJECT;
            } else if (var2 instanceof List) {
               return JsonToken.BEGIN_ARRAY;
            } else if (var2 instanceof String) {
               return JsonToken.STRING;
            } else if (var2 instanceof Number) {
               return JsonToken.NUMBER;
            } else if (var2 instanceof Boolean) {
               return JsonToken.BOOLEAN;
            } else {
               return var2 instanceof JsonToken ? (JsonToken)var2 : JsonToken.END_DOCUMENT;
            }
         }
      }
   }

   @Override
   public void setLenient(boolean var1) {
   }

   @Override
   public void skipValue() throws IOException {
   }
}
