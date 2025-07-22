package org.junit.experimental.categories;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class Categories extends Suite {
   public Categories(Class<?> var1, RunnerBuilder var2) throws InitializationError {
      super(var1, var2);

      try {
         Set var3 = getIncludedCategory(var1);
         Set var5 = getExcludedCategory(var1);
         this.filter(Categories.CategoryFilter.categoryFilter(isAnyIncluded(var1), var3, isAnyExcluded(var1), var5));
      } catch (NoTestsRemainException var4) {
         throw new InitializationError(var4);
      }

      assertNoCategorizedDescendentsOfUncategorizeableParents(this.getDescription());
   }

   private static void assertNoCategorizedDescendentsOfUncategorizeableParents(Description var0) throws InitializationError {
      if (!canHaveCategorizedChildren(var0)) {
         assertNoDescendantsHaveCategoryAnnotations(var0);
      }

      Iterator var1 = var0.getChildren().iterator();

      while (var1.hasNext()) {
         assertNoCategorizedDescendentsOfUncategorizeableParents((Description)var1.next());
      }
   }

   private static void assertNoDescendantsHaveCategoryAnnotations(Description var0) throws InitializationError {
      for (Description var1 : var0.getChildren()) {
         if (var1.getAnnotation(Category.class) != null) {
            throw new InitializationError("Category annotations on Parameterized classes are not supported on individual methods.");
         }

         assertNoDescendantsHaveCategoryAnnotations(var1);
      }
   }

   private static boolean canHaveCategorizedChildren(Description var0) {
      Iterator var1 = var0.getChildren().iterator();

      while (var1.hasNext()) {
         if (((Description)var1.next()).getTestClass() == null) {
            return false;
         }
      }

      return true;
   }

   private static Set<Class<?>> createSet(Class<?>... var0) {
      HashSet var1 = new HashSet();
      if (var0 != null) {
         Collections.addAll(var1, var0);
      }

      return var1;
   }

   private static Set<Class<?>> getExcludedCategory(Class<?> var0) {
      Categories.ExcludeCategory var1 = var0.getAnnotation(Categories.ExcludeCategory.class);
      Class[] var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.value();
      }

      return createSet(var2);
   }

   private static Set<Class<?>> getIncludedCategory(Class<?> var0) {
      Categories.IncludeCategory var1 = var0.getAnnotation(Categories.IncludeCategory.class);
      Class[] var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.value();
      }

      return createSet(var2);
   }

   private static boolean hasAssignableTo(Set<Class<?>> var0, Class<?> var1) {
      Iterator var2 = var0.iterator();

      while (var2.hasNext()) {
         if (var1.isAssignableFrom((Class<?>)var2.next())) {
            return true;
         }
      }

      return false;
   }

   private static boolean isAnyExcluded(Class<?> var0) {
      Categories.ExcludeCategory var2 = var0.getAnnotation(Categories.ExcludeCategory.class);
      boolean var1;
      if (var2 != null && !var2.matchAny()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private static boolean isAnyIncluded(Class<?> var0) {
      Categories.IncludeCategory var2 = var0.getAnnotation(Categories.IncludeCategory.class);
      boolean var1;
      if (var2 != null && !var2.matchAny()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static class CategoryFilter extends Filter {
      private final Set<Class<?>> excluded;
      private final boolean excludedAny;
      private final Set<Class<?>> included;
      private final boolean includedAny;

      protected CategoryFilter(boolean var1, Set<Class<?>> var2, boolean var3, Set<Class<?>> var4) {
         this.includedAny = var1;
         this.excludedAny = var3;
         this.included = copyAndRefine(var2);
         this.excluded = copyAndRefine(var4);
      }

      private static Set<Class<?>> categories(Description var0) {
         HashSet var1 = new HashSet();
         Collections.addAll(var1, directCategories(var0));
         Collections.addAll(var1, directCategories(parentDescription(var0)));
         return var1;
      }

      public static Categories.CategoryFilter categoryFilter(boolean var0, Set<Class<?>> var1, boolean var2, Set<Class<?>> var3) {
         return new Categories.CategoryFilter(var0, var1, var2, var3);
      }

      private static Set<Class<?>> copyAndRefine(Set<Class<?>> var0) {
         HashSet var1 = new HashSet();
         if (var0 != null) {
            var1.addAll(var0);
         }

         var1.remove(null);
         return var1;
      }

      private static Class<?>[] directCategories(Description var0) {
         if (var0 == null) {
            return new Class[0];
         } else {
            Category var1 = (Category)var0.getAnnotation(Category.class);
            Class[] var2;
            if (var1 == null) {
               var2 = new Class[0];
            } else {
               var2 = var1.value();
            }

            return var2;
         }
      }

      public static Categories.CategoryFilter exclude(Class<?> var0) {
         return exclude(true, var0);
      }

      public static Categories.CategoryFilter exclude(boolean var0, Class<?>... var1) {
         if (!hasNull(var1)) {
            return categoryFilter(true, null, var0, Categories.createSet(var1));
         } else {
            throw new NullPointerException("has null category");
         }
      }

      public static Categories.CategoryFilter exclude(Class<?>... var0) {
         return exclude(true, var0);
      }

      private boolean hasCorrectCategoryAnnotation(Description var1) {
         Set var2 = categories(var1);
         if (var2.isEmpty()) {
            return this.included.isEmpty();
         } else {
            if (!this.excluded.isEmpty()) {
               if (this.excludedAny) {
                  if (this.matchesAnyParentCategories(var2, this.excluded)) {
                     return false;
                  }
               } else if (this.matchesAllParentCategories(var2, this.excluded)) {
                  return false;
               }
            }

            if (this.included.isEmpty()) {
               return true;
            } else {
               return this.includedAny ? this.matchesAnyParentCategories(var2, this.included) : this.matchesAllParentCategories(var2, this.included);
            }
         }
      }

      private static boolean hasNull(Class<?>... var0) {
         if (var0 == null) {
            return false;
         } else {
            int var2 = var0.length;

            for (int var1 = 0; var1 < var2; var1++) {
               if (var0[var1] == null) {
                  return true;
               }
            }

            return false;
         }
      }

      public static Categories.CategoryFilter include(Class<?> var0) {
         return include(true, var0);
      }

      public static Categories.CategoryFilter include(boolean var0, Class<?>... var1) {
         if (!hasNull(var1)) {
            return categoryFilter(var0, Categories.createSet(var1), true, null);
         } else {
            throw new NullPointerException("has null category");
         }
      }

      public static Categories.CategoryFilter include(Class<?>... var0) {
         return include(true, var0);
      }

      private boolean matchesAllParentCategories(Set<Class<?>> var1, Set<Class<?>> var2) {
         Iterator var3 = var2.iterator();

         while (var3.hasNext()) {
            if (!Categories.hasAssignableTo(var1, (Class<?>)var3.next())) {
               return false;
            }
         }

         return true;
      }

      private boolean matchesAnyParentCategories(Set<Class<?>> var1, Set<Class<?>> var2) {
         Iterator var3 = var2.iterator();

         while (var3.hasNext()) {
            if (Categories.hasAssignableTo(var1, (Class<?>)var3.next())) {
               return true;
            }
         }

         return false;
      }

      private static Description parentDescription(Description var0) {
         Class var1 = var0.getTestClass();
         if (var1 == null) {
            var0 = null;
         } else {
            var0 = Description.createSuiteDescription(var1);
         }

         return var0;
      }

      public String describe() {
         return this.toString();
      }

      public boolean shouldRun(Description var1) {
         if (this.hasCorrectCategoryAnnotation(var1)) {
            return true;
         } else {
            Iterator var2 = var1.getChildren().iterator();

            while (var2.hasNext()) {
               if (this.shouldRun((Description)var2.next())) {
                  return true;
               }
            }

            return false;
         }
      }

      public String toString() {
         StringBuilder var2 = new StringBuilder("categories ");
         Object var1;
         if (this.included.isEmpty()) {
            var1 = "[all]";
         } else {
            var1 = this.included;
         }

         var2.append(var1);
         if (!this.excluded.isEmpty()) {
            var2.append(" - ");
            var2.append(this.excluded);
         }

         return var2.toString();
      }
   }

   @Retention(RetentionPolicy.RUNTIME)
   public @interface ExcludeCategory {
      boolean matchAny() default true;

      Class<?>[] value() default {};
   }

   @Retention(RetentionPolicy.RUNTIME)
   public @interface IncludeCategory {
      boolean matchAny() default true;

      Class<?>[] value() default {};
   }
}
