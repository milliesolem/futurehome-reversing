package okio

public abstract class ForwardingSink : Sink {
   public final val delegate: Sink

   open fun ForwardingSink(var1: Sink) {
      this.delegate = var1;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "delegate", imports = []))
   public fun delegate(): Sink {
      return this.delegate;
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      this.delegate.close();
   }

   @Throws(java/io/IOException::class)
   public override fun flush() {
      this.delegate.flush();
   }

   public override fun timeout(): Timeout {
      return this.delegate.timeout();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.getClass().getSimpleName());
      var1.append('(');
      var1.append(this.delegate);
      var1.append(')');
      return var1.toString();
   }

   @Throws(java/io/IOException::class)
   public override fun write(source: Buffer, byteCount: Long) {
      this.delegate.write(var1, var2);
   }
}
