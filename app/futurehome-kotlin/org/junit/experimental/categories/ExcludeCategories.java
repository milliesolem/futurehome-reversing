package org.junit.experimental.categories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.manipulation.Filter;

public final class ExcludeCategories extends CategoryFilterFactory {
   @Override
   protected Filter createFilter(List<Class<?>> var1) {
      return new ExcludeCategories.ExcludesAny(var1);
   }

   private static class ExcludesAny extends Categories.CategoryFilter {
      public ExcludesAny(List<Class<?>> var1) {
         this(new HashSet<>(var1));
      }

      public ExcludesAny(Set<Class<?>> var1) {
         super(true, null, true, var1);
      }

      @Override
      public String describe() {
         StringBuilder var1 = new StringBuilder("excludes ");
         var1.append(super.describe());
         return var1.toString();
      }
   }
}
