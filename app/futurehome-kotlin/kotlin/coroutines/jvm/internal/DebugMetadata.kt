package kotlin.coroutines.jvm.internal

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE])
@kotlin.annotation.Target(allowedTargets = [AnnotationTarget.CLASS])
annotation class DebugMetadata(
   val version: Int = 1,
   val sourceFile: String = "",
   val lineNumbers: IntArray = {},
   val localNames: Array<String> = {},
   val spilled: Array<String> = {},
   val indexToLabel: IntArray = {},
   val methodName: String = "",
   val className: String = ""
)
