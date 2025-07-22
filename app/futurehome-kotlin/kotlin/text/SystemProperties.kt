package kotlin.text

private object SystemProperties {
   public final val LINE_SEPARATOR: String

   @JvmStatic
   fun {
      val var0: java.lang.String = System.getProperty("line.separator");
      LINE_SEPARATOR = var0;
   }
}
