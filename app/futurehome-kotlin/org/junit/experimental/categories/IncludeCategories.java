package org.junit.experimental.categories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.manipulation.Filter;

public final class IncludeCategories extends CategoryFilterFactory {
   @Override
   protected Filter createFilter(List<Class<?>> var1) {
      return new IncludeCategories.IncludesAny(var1);
   }

   private static class IncludesAny extends Categories.CategoryFilter {
      public IncludesAny(List<Class<?>> var1) {
         this(new HashSet<>(var1));
      }

      public IncludesAny(Set<Class<?>> var1) {
         super(true, var1, true, null);
      }

      @Override
      public String describe() {
         StringBuilder var1 = new StringBuilder("includes ");
         var1.append(super.describe());
         return var1.toString();
      }
   }
}
