package io.sentry;

import io.sentry.vendor.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Writer;

public final class JsonObjectWriter implements ObjectWriter {
   private final JsonObjectSerializer jsonObjectSerializer;
   private final JsonWriter jsonWriter;

   public JsonObjectWriter(Writer var1, int var2) {
      this.jsonWriter = new JsonWriter(var1);
      this.jsonObjectSerializer = new JsonObjectSerializer(var2);
   }

   public JsonObjectWriter beginArray() throws IOException {
      this.jsonWriter.beginArray();
      return this;
   }

   public JsonObjectWriter beginObject() throws IOException {
      this.jsonWriter.beginObject();
      return this;
   }

   public JsonObjectWriter endArray() throws IOException {
      this.jsonWriter.endArray();
      return this;
   }

   public JsonObjectWriter endObject() throws IOException {
      this.jsonWriter.endObject();
      return this;
   }

   @Override
   public ObjectWriter jsonValue(String var1) throws IOException {
      this.jsonWriter.jsonValue(var1);
      return this;
   }

   public JsonObjectWriter name(String var1) throws IOException {
      this.jsonWriter.name(var1);
      return this;
   }

   public JsonObjectWriter nullValue() throws IOException {
      this.jsonWriter.nullValue();
      return this;
   }

   public void setIndent(String var1) {
      this.jsonWriter.setIndent(var1);
   }

   @Override
   public void setLenient(boolean var1) {
      this.jsonWriter.setLenient(var1);
   }

   public JsonObjectWriter value(double var1) throws IOException {
      this.jsonWriter.value(var1);
      return this;
   }

   public JsonObjectWriter value(long var1) throws IOException {
      this.jsonWriter.value(var1);
      return this;
   }

   public JsonObjectWriter value(ILogger var1, Object var2) throws IOException {
      this.jsonObjectSerializer.serialize(this, var1, var2);
      return this;
   }

   public JsonObjectWriter value(Boolean var1) throws IOException {
      this.jsonWriter.value(var1);
      return this;
   }

   public JsonObjectWriter value(Number var1) throws IOException {
      this.jsonWriter.value(var1);
      return this;
   }

   public JsonObjectWriter value(String var1) throws IOException {
      this.jsonWriter.value(var1);
      return this;
   }

   public JsonObjectWriter value(boolean var1) throws IOException {
      this.jsonWriter.value(var1);
      return this;
   }
}
