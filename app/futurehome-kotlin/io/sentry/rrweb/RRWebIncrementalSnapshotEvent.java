package io.sentry.rrweb;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.Objects;
import java.io.IOException;

public abstract class RRWebIncrementalSnapshotEvent extends RRWebEvent {
   private RRWebIncrementalSnapshotEvent.IncrementalSource source;

   public RRWebIncrementalSnapshotEvent(RRWebIncrementalSnapshotEvent.IncrementalSource var1) {
      super(RRWebEventType.IncrementalSnapshot);
      this.source = var1;
   }

   public RRWebIncrementalSnapshotEvent.IncrementalSource getSource() {
      return this.source;
   }

   public void setSource(RRWebIncrementalSnapshotEvent.IncrementalSource var1) {
      this.source = var1;
   }

   public static final class Deserializer {
      public boolean deserializeValue(RRWebIncrementalSnapshotEvent var1, String var2, ObjectReader var3, ILogger var4) throws Exception {
         if (var2.equals("source")) {
            var1.source = Objects.requireNonNull(var3.nextOrNull(var4, new RRWebIncrementalSnapshotEvent.IncrementalSource.Deserializer()), "");
            return true;
         } else {
            return false;
         }
      }
   }

   public static enum IncrementalSource implements JsonSerializable {
      AdoptedStyleSheet,
      CanvasMutation,
      CustomElement,
      Drag,
      Font,
      Input,
      Log,
      MediaInteraction,
      MouseInteraction,
      MouseMove,
      Mutation,
      Scroll,
      Selection,
      StyleDeclaration,
      StyleSheetRule,
      TouchMove,
      ViewportResize;
      private static final RRWebIncrementalSnapshotEvent.IncrementalSource[] $VALUES = $values();

      @Override
      public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
         var1.value((long)this.ordinal());
      }

      public static final class Deserializer implements JsonDeserializer<RRWebIncrementalSnapshotEvent.IncrementalSource> {
         public RRWebIncrementalSnapshotEvent.IncrementalSource deserialize(ObjectReader var1, ILogger var2) throws Exception {
            return RRWebIncrementalSnapshotEvent.IncrementalSource.values()[var1.nextInt()];
         }
      }
   }

   public static final class JsonKeys {
      public static final String SOURCE = "source";
   }

   public static final class Serializer {
      public void serialize(RRWebIncrementalSnapshotEvent var1, ObjectWriter var2, ILogger var3) throws IOException {
         var2.name("source").value(var3, var1.source);
      }
   }
}
