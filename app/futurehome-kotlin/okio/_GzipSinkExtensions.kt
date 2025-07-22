package okio

public inline fun Sink.gzip(): GzipSink {
   return new GzipSink(var0);
}
