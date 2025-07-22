package kotlin

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import kotlin.reflect.KClass

@Retention(RetentionPolicy.CLASS)
@Target([ElementType.TYPE])
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@kotlin.annotation.Target(allowedTargets = [AnnotationTarget.CLASS])
annotation class SubclassOptInRequired(
   val markerClass: Array<out KClass<out Annotation>>
)
