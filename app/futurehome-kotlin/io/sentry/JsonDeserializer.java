package io.sentry;

public interface JsonDeserializer<T> {
   T deserialize(ObjectReader var1, ILogger var2) throws Exception;
}
