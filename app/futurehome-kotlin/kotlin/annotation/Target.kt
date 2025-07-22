package kotlin.annotation

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

@Documented
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target([ElementType.ANNOTATION_TYPE])
@MustBeDocumented
@Target(allowedTargets = [AnnotationTarget.ANNOTATION_CLASS])
annotation class Target(
   val allowedTargets: Array<out AnnotationTarget>
)
