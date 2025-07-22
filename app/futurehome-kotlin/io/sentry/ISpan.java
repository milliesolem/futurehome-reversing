package io.sentry;

import io.sentry.metrics.LocalMetricsAggregator;
import java.util.List;

public interface ISpan {
   void finish();

   void finish(SpanStatus var1);

   void finish(SpanStatus var1, SentryDate var2);

   Object getData(String var1);

   String getDescription();

   SentryDate getFinishDate();

   LocalMetricsAggregator getLocalMetricsAggregator();

   String getOperation();

   SpanContext getSpanContext();

   SentryDate getStartDate();

   SpanStatus getStatus();

   String getTag(String var1);

   Throwable getThrowable();

   boolean isFinished();

   boolean isNoOp();

   void setData(String var1, Object var2);

   void setDescription(String var1);

   void setMeasurement(String var1, Number var2);

   void setMeasurement(String var1, Number var2, MeasurementUnit var3);

   void setOperation(String var1);

   void setStatus(SpanStatus var1);

   void setTag(String var1, String var2);

   void setThrowable(Throwable var1);

   ISpan startChild(String var1);

   ISpan startChild(String var1, String var2);

   ISpan startChild(String var1, String var2, SentryDate var3, Instrumenter var4);

   ISpan startChild(String var1, String var2, SentryDate var3, Instrumenter var4, SpanOptions var5);

   ISpan startChild(String var1, String var2, SpanOptions var3);

   BaggageHeader toBaggageHeader(List<String> var1);

   SentryTraceHeader toSentryTrace();

   TraceContext traceContext();

   boolean updateEndDate(SentryDate var1);
}
