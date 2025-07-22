package org.junit.experimental.categories;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runners.model.FrameworkMethod;
import org.junit.validator.AnnotationValidator;

public final class CategoryValidator extends AnnotationValidator {
   private static final Set<Class<? extends Annotation>> INCOMPATIBLE_ANNOTATIONS = Collections.unmodifiableSet(
      new HashSet<>(Arrays.asList(BeforeClass.class, AfterClass.class, Before.class, After.class))
   );

   private void addErrorMessage(List<Exception> var1, Class<?> var2) {
      var1.add(new Exception(String.format("@%s can not be combined with @Category", var2.getSimpleName())));
   }

   public List<Exception> validateAnnotatedMethod(FrameworkMethod var1) {
      ArrayList var4 = new ArrayList();

      for (Annotation var7 : var1.getAnnotations()) {
         for (Class var8 : INCOMPATIBLE_ANNOTATIONS) {
            if (var7.annotationType().isAssignableFrom(var8)) {
               this.addErrorMessage(var4, var8);
            }
         }
      }

      return Collections.unmodifiableList(var4);
   }
}
