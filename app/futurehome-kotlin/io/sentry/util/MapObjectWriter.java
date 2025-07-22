package io.sentry.util;

import io.sentry.DateUtils;
import io.sentry.ILogger;
import io.sentry.JsonSerializable;
import io.sentry.ObjectWriter;
import io.sentry.SentryLevel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class MapObjectWriter implements ObjectWriter {
   final Map<String, Object> root;
   final ArrayDeque<Object> stack;

   public MapObjectWriter(Map<String, Object> var1) {
      this.root = var1;
      ArrayDeque var2 = new ArrayDeque();
      this.stack = var2;
      var2.addLast(var1);
   }

   private Map<String, Object> peekObject() {
      Object var1 = this.stack.peekLast();
      if (var1 != null) {
         if (var1 instanceof Map) {
            return (Map<String, Object>)var1;
         } else {
            throw new IllegalStateException("Stack element is not a Map.");
         }
      } else {
         throw new IllegalStateException("Stack is empty.");
      }
   }

   private void postValue(Object var1) {
      Object var2 = this.stack.peekLast();
      if (var2 instanceof List) {
         ((List)var2).add(var1);
      } else {
         if (!(var2 instanceof String)) {
            throw new IllegalStateException("Invalid stack state, expected array or string on top");
         }

         var2 = (String)this.stack.removeLast();
         this.peekObject().put((String)var2, var1);
      }
   }

   private void serializeCollection(ILogger var1, Collection<?> var2) throws IOException {
      this.beginArray();
      Iterator var3 = var2.iterator();

      while (var3.hasNext()) {
         this.value(var1, var3.next());
      }

      this.endArray();
   }

   private void serializeDate(ILogger var1, Date var2) throws IOException {
      try {
         this.value(DateUtils.getTimestamp(var2));
      } catch (Exception var3) {
         var1.log(SentryLevel.ERROR, "Error when serializing Date", var3);
         this.nullValue();
      }
   }

   private void serializeMap(ILogger var1, Map<?, ?> var2) throws IOException {
      this.beginObject();

      for (Object var4 : var2.keySet()) {
         if (var4 instanceof String) {
            this.name((String)var4);
            this.value(var1, var2.get(var4));
         }
      }

      this.endObject();
   }

   private void serializeTimeZone(ILogger var1, TimeZone var2) throws IOException {
      try {
         this.value(var2.getID());
      } catch (Exception var3) {
         var1.log(SentryLevel.ERROR, "Error when serializing TimeZone", var3);
         this.nullValue();
      }
   }

   public MapObjectWriter beginArray() throws IOException {
      this.stack.add(new ArrayList());
      return this;
   }

   public MapObjectWriter beginObject() throws IOException {
      this.stack.addLast(new HashMap());
      return this;
   }

   public MapObjectWriter endArray() throws IOException {
      this.endObject();
      return this;
   }

   public MapObjectWriter endObject() throws IOException {
      this.postValue(this.stack.removeLast());
      return this;
   }

   @Override
   public ObjectWriter jsonValue(String var1) throws IOException {
      return this;
   }

   public MapObjectWriter name(String var1) throws IOException {
      this.stack.add(var1);
      return this;
   }

   public MapObjectWriter nullValue() throws IOException {
      this.postValue(null);
      return this;
   }

   @Override
   public void setLenient(boolean var1) {
   }

   public MapObjectWriter value(double var1) throws IOException {
      this.postValue(var1);
      return this;
   }

   public MapObjectWriter value(long var1) throws IOException {
      this.postValue(var1);
      return this;
   }

   public MapObjectWriter value(ILogger var1, Object var2) throws IOException {
      if (var2 == null) {
         this.nullValue();
      } else if (var2 instanceof Character) {
         this.value(Character.toString((Character)var2));
      } else if (var2 instanceof String) {
         this.value((String)var2);
      } else if (var2 instanceof Boolean) {
         this.value(((Boolean)var2).booleanValue());
      } else if (var2 instanceof Number) {
         this.value((Number)var2);
      } else if (var2 instanceof Date) {
         this.serializeDate(var1, (Date)var2);
      } else if (var2 instanceof TimeZone) {
         this.serializeTimeZone(var1, (TimeZone)var2);
      } else if (var2 instanceof JsonSerializable) {
         ((JsonSerializable)var2).serialize(this, var1);
      } else if (var2 instanceof Collection) {
         this.serializeCollection(var1, (Collection<?>)var2);
      } else if (var2.getClass().isArray()) {
         this.serializeCollection(var1, Arrays.asList((Object[])var2));
      } else if (var2 instanceof Map) {
         this.serializeMap(var1, (Map<?, ?>)var2);
      } else if (var2 instanceof Locale) {
         this.value(var2.toString());
      } else if (var2 instanceof AtomicIntegerArray) {
         this.serializeCollection(var1, JsonSerializationUtils.atomicIntegerArrayToList((AtomicIntegerArray)var2));
      } else if (var2 instanceof AtomicBoolean) {
         this.value(((AtomicBoolean)var2).get());
      } else if (var2 instanceof URI) {
         this.value(var2.toString());
      } else if (var2 instanceof InetAddress) {
         this.value(var2.toString());
      } else if (var2 instanceof UUID) {
         this.value(var2.toString());
      } else if (var2 instanceof Currency) {
         this.value(var2.toString());
      } else if (var2 instanceof Calendar) {
         this.serializeMap(var1, JsonSerializationUtils.calendarToMap((Calendar)var2));
      } else if (var2.getClass().isEnum()) {
         this.value(var2.toString());
      } else {
         var1.log(SentryLevel.WARNING, "Failed serializing unknown object.", var2);
      }

      return this;
   }

   public MapObjectWriter value(Boolean var1) throws IOException {
      this.postValue(var1);
      return this;
   }

   public MapObjectWriter value(Number var1) throws IOException {
      this.postValue(var1);
      return this;
   }

   public MapObjectWriter value(String var1) throws IOException {
      this.postValue(var1);
      return this;
   }

   public MapObjectWriter value(boolean var1) throws IOException {
      this.postValue(var1);
      return this;
   }
}
