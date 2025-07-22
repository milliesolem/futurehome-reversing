package io.sentry;

import io.sentry.vendor.gson.stream.JsonToken;
import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public interface ObjectReader extends Closeable {
   void beginArray() throws IOException;

   void beginObject() throws IOException;

   void endArray() throws IOException;

   void endObject() throws IOException;

   boolean hasNext() throws IOException;

   boolean nextBoolean() throws IOException;

   Boolean nextBooleanOrNull() throws IOException;

   Date nextDateOrNull(ILogger var1) throws IOException;

   double nextDouble() throws IOException;

   Double nextDoubleOrNull() throws IOException;

   float nextFloat() throws IOException;

   Float nextFloatOrNull() throws IOException;

   int nextInt() throws IOException;

   Integer nextIntegerOrNull() throws IOException;

   <T> List<T> nextListOrNull(ILogger var1, JsonDeserializer<T> var2) throws IOException;

   long nextLong() throws IOException;

   Long nextLongOrNull() throws IOException;

   <T> Map<String, List<T>> nextMapOfListOrNull(ILogger var1, JsonDeserializer<T> var2) throws IOException;

   <T> Map<String, T> nextMapOrNull(ILogger var1, JsonDeserializer<T> var2) throws IOException;

   String nextName() throws IOException;

   void nextNull() throws IOException;

   Object nextObjectOrNull() throws IOException;

   <T> T nextOrNull(ILogger var1, JsonDeserializer<T> var2) throws Exception;

   String nextString() throws IOException;

   String nextStringOrNull() throws IOException;

   TimeZone nextTimeZoneOrNull(ILogger var1) throws IOException;

   void nextUnknown(ILogger var1, Map<String, Object> var2, String var3);

   JsonToken peek() throws IOException;

   void setLenient(boolean var1);

   void skipValue() throws IOException;
}
