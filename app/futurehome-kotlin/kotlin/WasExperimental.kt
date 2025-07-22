package kotlin

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import kotlin.reflect.KClass

@Retention(RetentionPolicy.CLASS)
@Target([ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR])
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@kotlin.annotation.Target(allowedTargets = [AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.TYPEALIAS])
annotation class WasExperimental(
   val markerClass: Array<out KClass<out Annotation>>
)
