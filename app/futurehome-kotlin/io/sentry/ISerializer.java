package io.sentry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public interface ISerializer {
   <T> T deserialize(Reader var1, Class<T> var2);

   <T, R> T deserializeCollection(Reader var1, Class<T> var2, JsonDeserializer<R> var3);

   SentryEnvelope deserializeEnvelope(InputStream var1);

   String serialize(Map<String, Object> var1) throws Exception;

   void serialize(SentryEnvelope var1, OutputStream var2) throws Exception;

   <T> void serialize(T var1, Writer var2) throws IOException;
}
