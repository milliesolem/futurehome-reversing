package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SpreadBuilder {
   private final ArrayList<Object> list;

   public SpreadBuilder(int var1) {
      this.list = new ArrayList<>(var1);
   }

   public void add(Object var1) {
      this.list.add(var1);
   }

   public void addSpread(Object var1) {
      if (var1 != null) {
         if (var1 instanceof Object[]) {
            var1 = var1;
            if (var1.length > 0) {
               ArrayList var2 = this.list;
               var2.ensureCapacity(var2.size() + var1.length);
               Collections.addAll(this.list, var1);
            }
         } else if (var1 instanceof Collection) {
            this.list.addAll((Collection<? extends Object>)var1);
         } else if (var1 instanceof Iterable) {
            for (Object var6 : (Iterable)var1) {
               this.list.add(var6);
            }
         } else {
            if (!(var1 instanceof Iterator)) {
               StringBuilder var7 = new StringBuilder("Don't know how to spread ");
               var7.append(var1.getClass());
               throw new UnsupportedOperationException(var7.toString());
            }

            Iterator var5 = (Iterator)var1;

            while (var5.hasNext()) {
               this.list.add(var5.next());
            }
         }
      }
   }

   public int size() {
      return this.list.size();
   }

   public Object[] toArray(Object[] var1) {
      return this.list.toArray(var1);
   }
}
