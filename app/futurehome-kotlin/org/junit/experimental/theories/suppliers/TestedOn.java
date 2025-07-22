package org.junit.experimental.theories.suppliers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.experimental.theories.ParametersSuppliedBy;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@ParametersSuppliedBy(TestedOnSupplier.class)
public @interface TestedOn {
   int[] ints();
}
