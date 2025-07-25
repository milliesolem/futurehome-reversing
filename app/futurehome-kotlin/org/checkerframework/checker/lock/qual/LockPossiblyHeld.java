package org.checkerframework.checker.lock.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.DefaultFor;
import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.InvisibleQualifier;
import org.checkerframework.framework.qual.LiteralKind;
import org.checkerframework.framework.qual.QualifierForLiterals;
import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.TypeUseLocation;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
@DefaultFor(
   types = {Void.class},
   value = {TypeUseLocation.LOWER_BOUND}
)
@DefaultQualifierInHierarchy
@InvisibleQualifier
@QualifierForLiterals({LiteralKind.NULL})
@SubtypeOf({})
public @interface LockPossiblyHeld {
}
