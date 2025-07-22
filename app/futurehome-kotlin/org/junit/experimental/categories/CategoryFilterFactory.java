package org.junit.experimental.categories;

import java.util.ArrayList;
import java.util.List;
import org.junit.internal.Classes;
import org.junit.runner.FilterFactory;
import org.junit.runner.FilterFactoryParams;
import org.junit.runner.FilterFactory.FilterNotCreatedException;
import org.junit.runner.manipulation.Filter;

abstract class CategoryFilterFactory implements FilterFactory {
   private List<Class<?>> parseCategories(String var1) throws ClassNotFoundException {
      ArrayList var4 = new ArrayList();
      String[] var5 = var1.split(",");
      int var3 = var5.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(Classes.getClass(var5[var2]));
      }

      return var4;
   }

   protected abstract Filter createFilter(List<Class<?>> var1);

   public Filter createFilter(FilterFactoryParams var1) throws FilterNotCreatedException {
      try {
         return this.createFilter(this.parseCategories(var1.getArgs()));
      } catch (ClassNotFoundException var2) {
         throw new FilterNotCreatedException(var2);
      }
   }
}
