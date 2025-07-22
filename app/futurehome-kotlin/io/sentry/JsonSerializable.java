package io.sentry;

import java.io.IOException;

public interface JsonSerializable {
   void serialize(ObjectWriter var1, ILogger var2) throws IOException;
}
