package okio

public abstract class ForwardingSource : Source {
   public final val delegate: Source

   open fun ForwardingSource(var1: Source) {
      this.delegate = var1;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "delegate", imports = []))
   public fun delegate(): Source {
      return this.delegate;
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      this.delegate.close();
   }

   @Throws(java/io/IOException::class)
   public override fun read(sink: Buffer, byteCount: Long): Long {
      return this.delegate.read(var1, var2);
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
}
