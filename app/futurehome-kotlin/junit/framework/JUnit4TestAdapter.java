package junit.framework;

import java.util.Iterator;
import java.util.List;
import org.junit.Ignore;
import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.Request;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;

public class JUnit4TestAdapter implements Test, Filterable, Sortable, Describable {
   private final JUnit4TestAdapterCache fCache;
   private final Class<?> fNewTestClass;
   private final Runner fRunner;

   public JUnit4TestAdapter(Class<?> var1) {
      this(var1, JUnit4TestAdapterCache.getDefault());
   }

   public JUnit4TestAdapter(Class<?> var1, JUnit4TestAdapterCache var2) {
      this.fCache = var2;
      this.fNewTestClass = var1;
      this.fRunner = Request.classWithoutSuiteMethod(var1).getRunner();
   }

   private boolean isIgnored(Description var1) {
      boolean var2;
      if (var1.getAnnotation(Ignore.class) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private Description removeIgnored(Description var1) {
      if (this.isIgnored(var1)) {
         return Description.EMPTY;
      } else {
         Description var2 = var1.childlessCopy();
         Iterator var3 = var1.getChildren().iterator();

         while (var3.hasNext()) {
            var1 = this.removeIgnored((Description)var3.next());
            if (!var1.isEmpty()) {
               var2.addChild(var1);
            }
         }

         return var2;
      }
   }

   @Override
   public int countTestCases() {
      return this.fRunner.testCount();
   }

   public void filter(Filter var1) throws NoTestsRemainException {
      var1.apply(this.fRunner);
   }

   public Description getDescription() {
      return this.removeIgnored(this.fRunner.getDescription());
   }

   public Class<?> getTestClass() {
      return this.fNewTestClass;
   }

   public List<Test> getTests() {
      return this.fCache.asTestList(this.getDescription());
   }

   @Override
   public void run(TestResult var1) {
      this.fRunner.run(this.fCache.getNotifier(var1, this));
   }

   public void sort(Sorter var1) {
      var1.apply(this.fRunner);
   }

   @Override
   public String toString() {
      return this.fNewTestClass.getName();
   }
}
