package io.sentry;

import io.sentry.exception.SentryEnvelopeException;
import io.sentry.protocol.SdkVersion;
import io.sentry.protocol.SentryId;
import io.sentry.util.Objects;
import java.io.IOException;
import java.util.ArrayList;

public final class SentryEnvelope {
   private final SentryEnvelopeHeader header;
   private final Iterable<SentryEnvelopeItem> items;

   public SentryEnvelope(SentryEnvelopeHeader var1, Iterable<SentryEnvelopeItem> var2) {
      this.header = Objects.requireNonNull(var1, "SentryEnvelopeHeader is required.");
      this.items = Objects.requireNonNull(var2, "SentryEnvelope items are required.");
   }

   public SentryEnvelope(SentryId var1, SdkVersion var2, SentryEnvelopeItem var3) {
      Objects.requireNonNull(var3, "SentryEnvelopeItem is required.");
      this.header = new SentryEnvelopeHeader(var1, var2);
      ArrayList var4 = new ArrayList(1);
      var4.add(var3);
      this.items = var4;
   }

   public SentryEnvelope(SentryId var1, SdkVersion var2, Iterable<SentryEnvelopeItem> var3) {
      this.header = new SentryEnvelopeHeader(var1, var2);
      this.items = Objects.requireNonNull(var3, "SentryEnvelope items are required.");
   }

   public static SentryEnvelope from(ISerializer var0, ProfilingTraceData var1, long var2, SdkVersion var4) throws SentryEnvelopeException {
      Objects.requireNonNull(var0, "Serializer is required.");
      Objects.requireNonNull(var1, "Profiling trace data is required.");
      return new SentryEnvelope(new SentryId(var1.getProfileId()), var4, SentryEnvelopeItem.fromProfilingTrace(var1, var2, var0));
   }

   public static SentryEnvelope from(ISerializer var0, SentryBaseEvent var1, SdkVersion var2) throws IOException {
      Objects.requireNonNull(var0, "Serializer is required.");
      Objects.requireNonNull(var1, "item is required.");
      return new SentryEnvelope(var1.getEventId(), var2, SentryEnvelopeItem.fromEvent(var0, var1));
   }

   public static SentryEnvelope from(ISerializer var0, Session var1, SdkVersion var2) throws IOException {
      Objects.requireNonNull(var0, "Serializer is required.");
      Objects.requireNonNull(var1, "session is required.");
      return new SentryEnvelope(null, var2, SentryEnvelopeItem.fromSession(var0, var1));
   }

   public SentryEnvelopeHeader getHeader() {
      return this.header;
   }

   public Iterable<SentryEnvelopeItem> getItems() {
      return this.items;
   }
}
