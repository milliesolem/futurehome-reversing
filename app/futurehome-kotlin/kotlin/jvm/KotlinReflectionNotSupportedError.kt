package kotlin.jvm

public open class KotlinReflectionNotSupportedError : Error {
   public constructor() : super("Kotlin reflection implementation is not found at runtime. Make sure you have kotlin-reflect.jar in the classpath")
   public constructor(message: String?) : super(var1)
   public constructor(message: String?, cause: Throwable?) : super(var1, var2)
   public constructor(cause: Throwable?) : super(var1)}
