package kotlin.system

public inline fun exitProcess(status: Int): Nothing {
   System.exit(var0);
   throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
}
