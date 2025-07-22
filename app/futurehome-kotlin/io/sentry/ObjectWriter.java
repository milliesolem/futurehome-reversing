package io.sentry;

import java.io.IOException;

public interface ObjectWriter {
   ObjectWriter beginArray() throws IOException;

   ObjectWriter beginObject() throws IOException;

   ObjectWriter endArray() throws IOException;

   ObjectWriter endObject() throws IOException;

   ObjectWriter jsonValue(String var1) throws IOException;

   ObjectWriter name(String var1) throws IOException;

   ObjectWriter nullValue() throws IOException;

   void setLenient(boolean var1);

   ObjectWriter value(double var1) throws IOException;

   ObjectWriter value(long var1) throws IOException;

   ObjectWriter value(ILogger var1, Object var2) throws IOException;

   ObjectWriter value(Boolean var1) throws IOException;

   ObjectWriter value(Number var1) throws IOException;

   ObjectWriter value(String var1) throws IOException;

   ObjectWriter value(boolean var1) throws IOException;
}
