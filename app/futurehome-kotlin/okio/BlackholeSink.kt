package okio

private class BlackholeSink : Sink {
   public override fun close() {
   }

   public override fun flush() {
   }

   public override fun timeout(): Timeout {
      return Timeout.NONE;
   }

   public override fun write(source: Buffer, byteCount: Long) {
      var1.skip(var2);
   }
}
