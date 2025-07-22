package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.junit.internal.MethodSorter;

public class TestSuite implements Test {
   private String fName;
   private Vector<Test> fTests = new Vector<>(10);

   public TestSuite() {
   }

   public TestSuite(Class<?> var1) {
      this.addTestsFromTestCase(var1);
   }

   public TestSuite(Class<? extends TestCase> var1, String var2) {
      this(var1);
      this.setName(var2);
   }

   public TestSuite(String var1) {
      this.setName(var1);
   }

   public TestSuite(Class<?>... var1) {
      int var3 = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         this.addTest(this.testCaseForClass(var1[var2]));
      }
   }

   public TestSuite(Class<? extends TestCase>[] var1, String var2) {
      this(var1);
      this.setName(var2);
   }

   private void addTestMethod(Method var1, List<String> var2, Class<?> var3) {
      String var4 = var1.getName();
      if (!var2.contains(var4)) {
         if (!this.isPublicTestMethod(var1)) {
            if (this.isTestMethod(var1)) {
               StringBuilder var5 = new StringBuilder("Test method isn't public: ");
               var5.append(var1.getName());
               var5.append("(");
               var5.append(var3.getCanonicalName());
               var5.append(")");
               this.addTest(warning(var5.toString()));
            }
         } else {
            var2.add(var4);
            this.addTest(createTest(var3, var4));
         }
      }
   }

   private void addTestsFromTestCase(Class<?> var1) {
      this.fName = var1.getName();

      try {
         getTestConstructor(var1);
      } catch (NoSuchMethodException var7) {
         StringBuilder var4 = new StringBuilder("Class ");
         var4.append(var1.getName());
         var4.append(" has no public constructor TestCase(String name) or TestCase()");
         this.addTest(warning(var4.toString()));
         return;
      }

      if (!Modifier.isPublic(var1.getModifiers())) {
         StringBuilder var10 = new StringBuilder("Class ");
         var10.append(var1.getName());
         var10.append(" is not public");
         this.addTest(warning(var10.toString()));
      } else {
         ArrayList var5 = new ArrayList();

         for (Class var8 = var1; Test.class.isAssignableFrom(var8); var8 = var8.getSuperclass()) {
            Method[] var6 = MethodSorter.getDeclaredMethods(var8);
            int var3 = var6.length;

            for (int var2 = 0; var2 < var3; var2++) {
               this.addTestMethod(var6[var2], var5, var1);
            }
         }

         if (this.fTests.size() == 0) {
            StringBuilder var9 = new StringBuilder("No tests found in ");
            var9.append(var1.getName());
            this.addTest(warning(var9.toString()));
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static Test createTest(Class<?> var0, String var1) {
      Constructor var2;
      try {
         var2 = getTestConstructor(var0);
      } catch (NoSuchMethodException var6) {
         StringBuilder var20 = new StringBuilder("Class ");
         var20.append(var0.getName());
         var20.append(" has no public constructor TestCase(String name) or TestCase()");
         return warning(var20.toString());
      }

      label57: {
         try {
            if (var2.getParameterTypes().length != 0) {
               break label57;
            }

            var22 = var2.newInstance(null);
         } catch (InstantiationException var10) {
            StringBuilder var21 = new StringBuilder("Cannot instantiate test case: ");
            var21.append(var1);
            var21.append(" (");
            var21.append(exceptionToString(var10));
            var21.append(")");
            return warning(var21.toString());
         } catch (InvocationTargetException var11) {
            StringBuilder var14 = new StringBuilder("Exception in constructor: ");
            var14.append(var1);
            var14.append(" (");
            var14.append(exceptionToString(var11.getTargetException()));
            var14.append(")");
            return warning(var14.toString());
         } catch (IllegalAccessException var12) {
            StringBuilder var13 = new StringBuilder("Cannot access test case: ");
            var13.append(var1);
            var13.append(" (");
            var13.append(exceptionToString(var12));
            var13.append(")");
            return warning(var13.toString());
         }

         StringBuilder var15 = (StringBuilder)var22;

         try {
            if (!(var22 instanceof TestCase)) {
               return (Test)var15;
            }

            ((TestCase)var22).setName(var1);
         } catch (InstantiationException var7) {
            StringBuilder var23 = new StringBuilder("Cannot instantiate test case: ");
            var23.append(var1);
            var23.append(" (");
            var23.append(exceptionToString(var7));
            var23.append(")");
            return warning(var23.toString());
         } catch (InvocationTargetException var8) {
            var15 = new StringBuilder("Exception in constructor: ");
            var15.append(var1);
            var15.append(" (");
            var15.append(exceptionToString(var8.getTargetException()));
            var15.append(")");
            return warning(var15.toString());
         } catch (IllegalAccessException var9) {
            var15 = new StringBuilder("Cannot access test case: ");
            var15.append(var1);
            var15.append(" (");
            var15.append(exceptionToString(var9));
            var15.append(")");
            return warning(var15.toString());
         }

         return (Test)var22;
      }

      try {
         var25 = var2.newInstance(var1);
      } catch (InstantiationException var3) {
         StringBuilder var24 = new StringBuilder("Cannot instantiate test case: ");
         var24.append(var1);
         var24.append(" (");
         var24.append(exceptionToString(var3));
         var24.append(")");
         return warning(var24.toString());
      } catch (InvocationTargetException var4) {
         StringBuilder var19 = new StringBuilder("Exception in constructor: ");
         var19.append(var1);
         var19.append(" (");
         var19.append(exceptionToString(var4.getTargetException()));
         var19.append(")");
         return warning(var19.toString());
      } catch (IllegalAccessException var5) {
         StringBuilder var18 = new StringBuilder("Cannot access test case: ");
         var18.append(var1);
         var18.append(" (");
         var18.append(exceptionToString(var5));
         var18.append(")");
         return warning(var18.toString());
      }

      return (Test)var25;
   }

   private static String exceptionToString(Throwable var0) {
      StringWriter var1 = new StringWriter();
      var0.printStackTrace(new PrintWriter(var1));
      return var1.toString();
   }

   public static Constructor<?> getTestConstructor(Class<?> var0) throws NoSuchMethodException {
      try {
         return var0.getConstructor(String.class);
      } catch (NoSuchMethodException var2) {
         return var0.getConstructor(null);
      }
   }

   private boolean isPublicTestMethod(Method var1) {
      boolean var2;
      if (this.isTestMethod(var1) && Modifier.isPublic(var1.getModifiers())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private boolean isTestMethod(Method var1) {
      boolean var2;
      if (var1.getParameterTypes().length == 0 && var1.getName().startsWith("test") && var1.getReturnType().equals(void.class)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private Test testCaseForClass(Class<?> var1) {
      if (TestCase.class.isAssignableFrom(var1)) {
         return new TestSuite(var1.asSubclass(TestCase.class));
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1.getCanonicalName());
         var2.append(" does not extend TestCase");
         return warning(var2.toString());
      }
   }

   public static Test warning(String var0) {
      return new TestCase("warning", var0) {
         final String val$message;

         {
            this.val$message = var2;
         }

         @Override
         protected void runTest() {
            fail(this.val$message);
         }
      };
   }

   public void addTest(Test var1) {
      this.fTests.add(var1);
   }

   public void addTestSuite(Class<? extends TestCase> var1) {
      this.addTest(new TestSuite(var1));
   }

   @Override
   public int countTestCases() {
      Iterator var2 = this.fTests.iterator();
      int var1 = 0;

      while (var2.hasNext()) {
         var1 += ((Test)var2.next()).countTestCases();
      }

      return var1;
   }

   public String getName() {
      return this.fName;
   }

   @Override
   public void run(TestResult var1) {
      for (Test var3 : this.fTests) {
         if (var1.shouldStop()) {
            break;
         }

         this.runTest(var3, var1);
      }
   }

   public void runTest(Test var1, TestResult var2) {
      var1.run(var2);
   }

   public void setName(String var1) {
      this.fName = var1;
   }

   public Test testAt(int var1) {
      return this.fTests.get(var1);
   }

   public int testCount() {
      return this.fTests.size();
   }

   public Enumeration<Test> tests() {
      return this.fTests.elements();
   }

   @Override
   public String toString() {
      return this.getName() != null ? this.getName() : super.toString();
   }
}
