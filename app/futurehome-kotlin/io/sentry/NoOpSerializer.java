package io.sentry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

final class NoOpSerializer implements ISerializer {
   private static final NoOpSerializer instance = new NoOpSerializer();

   private NoOpSerializer() {
   }

   public static NoOpSerializer getInstance() {
      return instance;
   }

   @Override
   public <T> T deserialize(Reader var1, Class<T> var2) {
      return null;
   }

   @Override
   public <T, R> T deserializeCollection(Reader var1, Class<T> var2, JsonDeserializer<R> var3) {
      return null;
   }

   @Override
   public SentryEnvelope deserializeEnvelope(InputStream var1) {
      return null;
   }

   @Override
   public String serialize(Map<String, Object> var1) throws Exception {
      return "";
   }

   @Override
   public void serialize(SentryEnvelope var1, OutputStream var2) throws Exception {
   }

   @Override
   public <T> void serialize(T var1, Writer var2) throws IOException {
   }
}
