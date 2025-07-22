package io.sentry;

import java.io.IOException;
import java.io.InputStream;

public interface IEnvelopeReader {
   SentryEnvelope read(InputStream var1) throws IOException;
}
