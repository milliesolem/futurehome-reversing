package kotlin

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE])
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(allowedTargets = [AnnotationTarget.CLASS])
annotation class Metadata(
   val kind: Int = 1,
   val metadataVersion: IntArray = {},
   val bytecodeVersion: IntArray = {1, 0, 3},
   val data1: Array<String> = {},
   val data2: Array<String> = {},
   val extraString: String = "",
   val packageName: String = "",
   val extraInt: Int = 0
) {
   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
