package kotlin.annotation

import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target([ElementType.ANNOTATION_TYPE])
@Target(allowedTargets = [AnnotationTarget.ANNOTATION_CLASS])
annotation class Retention(
   val value: AnnotationRetention = AnnotationRetention.RUNTIME
)
