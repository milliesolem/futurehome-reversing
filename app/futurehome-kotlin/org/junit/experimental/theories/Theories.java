package org.junit.experimental.theories;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.experimental.theories.internal.Assignments;
import org.junit.experimental.theories.internal.ParameterizedAssertionError;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class Theories extends BlockJUnit4ClassRunner {
   public Theories(Class<?> var1) throws InitializationError {
      super(var1);
   }

   private void validateDataPointFields(List<Throwable> var1) {
      for (Field var4 : this.getTestClass().getJavaClass().getDeclaredFields()) {
         if (var4.getAnnotation(DataPoint.class) != null || var4.getAnnotation(DataPoints.class) != null) {
            if (!Modifier.isStatic(var4.getModifiers())) {
               StringBuilder var6 = new StringBuilder("DataPoint field ");
               var6.append(var4.getName());
               var6.append(" must be static");
               var1.add(new Error(var6.toString()));
            }

            if (!Modifier.isPublic(var4.getModifiers())) {
               StringBuilder var7 = new StringBuilder("DataPoint field ");
               var7.append(var4.getName());
               var7.append(" must be public");
               var1.add(new Error(var7.toString()));
            }
         }
      }
   }

   private void validateDataPointMethods(List<Throwable> var1) {
      for (Method var4 : this.getTestClass().getJavaClass().getDeclaredMethods()) {
         if (var4.getAnnotation(DataPoint.class) != null || var4.getAnnotation(DataPoints.class) != null) {
            if (!Modifier.isStatic(var4.getModifiers())) {
               StringBuilder var6 = new StringBuilder("DataPoint method ");
               var6.append(var4.getName());
               var6.append(" must be static");
               var1.add(new Error(var6.toString()));
            }

            if (!Modifier.isPublic(var4.getModifiers())) {
               StringBuilder var7 = new StringBuilder("DataPoint method ");
               var7.append(var4.getName());
               var7.append(" must be public");
               var1.add(new Error(var7.toString()));
            }
         }
      }
   }

   private void validateParameterSupplier(Class<? extends ParameterSupplier> var1, List<Throwable> var2) {
      Constructor[] var3 = var1.getConstructors();
      if (var3.length != 1) {
         StringBuilder var4 = new StringBuilder("ParameterSupplier ");
         var4.append(var1.getName());
         var4.append(" must have only one constructor (either empty or taking only a TestClass)");
         var2.add(new Error(var4.toString()));
      } else {
         Class[] var5 = var3[0].getParameterTypes();
         if (var5.length != 0 && !var5[0].equals(TestClass.class)) {
            StringBuilder var6 = new StringBuilder("ParameterSupplier ");
            var6.append(var1.getName());
            var6.append(" constructor must take either nothing or a single TestClass instance");
            var2.add(new Error(var6.toString()));
         }
      }
   }

   protected void collectInitializationErrors(List<Throwable> var1) {
      super.collectInitializationErrors(var1);
      this.validateDataPointFields(var1);
      this.validateDataPointMethods(var1);
   }

   protected List<FrameworkMethod> computeTestMethods() {
      ArrayList var2 = new ArrayList(super.computeTestMethods());
      List var1 = this.getTestClass().getAnnotatedMethods(Theory.class);
      var2.removeAll(var1);
      var2.addAll(var1);
      return var2;
   }

   public Statement methodBlock(FrameworkMethod var1) {
      return new Theories.TheoryAnchor(var1, this.getTestClass());
   }

   protected void validateConstructor(List<Throwable> var1) {
      this.validateOnlyOneConstructor(var1);
   }

   protected void validateTestMethods(List<Throwable> var1) {
      for (FrameworkMethod var3 : this.computeTestMethods()) {
         if (var3.getAnnotation(Theory.class) != null) {
            var3.validatePublicVoid(false, var1);
            var3.validateNoTypeParametersOnArgs(var1);
         } else {
            var3.validatePublicVoidNoArg(false, var1);
         }

         Iterator var4 = ParameterSignature.signatures(var3.getMethod()).iterator();

         while (var4.hasNext()) {
            ParametersSuppliedBy var5 = ((ParameterSignature)var4.next()).findDeepAnnotation(ParametersSuppliedBy.class);
            if (var5 != null) {
               this.validateParameterSupplier(var5.value(), var1);
            }
         }
      }
   }

   public static class TheoryAnchor extends Statement {
      private List<AssumptionViolatedException> fInvalidParameters;
      private int successes = 0;
      private final TestClass testClass;
      private final FrameworkMethod testMethod;

      public TheoryAnchor(FrameworkMethod var1, TestClass var2) {
         this.fInvalidParameters = new ArrayList<>();
         this.testMethod = var1;
         this.testClass = var2;
      }

      private TestClass getTestClass() {
         return this.testClass;
      }

      private Statement methodCompletesWithParameters(FrameworkMethod var1, Assignments var2, Object var3) {
         return new Statement(this, var2, var1, var3) {
            final Theories.TheoryAnchor this$0;
            final Assignments val$complete;
            final Object val$freshInstance;
            final FrameworkMethod val$method;

            {
               this.this$0 = var1;
               this.val$complete = var2x;
               this.val$method = var3x;
               this.val$freshInstance = var4;
            }

            public void evaluate() throws Throwable {
               Object[] var1x = this.val$complete.getMethodArguments();
               if (!this.this$0.nullsOk()) {
                  Assume.assumeNotNull(var1x);
               }

               this.val$method.invokeExplosively(this.val$freshInstance, var1x);
            }
         };
      }

      private boolean nullsOk() {
         Theory var1 = this.testMethod.getMethod().getAnnotation(Theory.class);
         return var1 == null ? false : var1.nullsAccepted();
      }

      public void evaluate() throws Throwable {
         this.runWithAssignment(Assignments.allUnassigned(this.testMethod.getMethod(), this.getTestClass()));
         boolean var1;
         if (this.testMethod.getAnnotation(Theory.class) != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (this.successes == 0 && var1) {
            StringBuilder var2 = new StringBuilder("Never found parameters that satisfied method assumptions.  Violated assumptions: ");
            var2.append(this.fInvalidParameters);
            Assert.fail(var2.toString());
         }
      }

      protected void handleAssumptionViolation(AssumptionViolatedException var1) {
         this.fInvalidParameters.add(var1);
      }

      protected void handleDataPointSuccess() {
         this.successes++;
      }

      protected void reportParameterizedError(Throwable var1, Object... var2) throws Throwable {
         if (var2.length == 0) {
            throw var1;
         } else {
            throw new ParameterizedAssertionError(var1, this.testMethod.getName(), var2);
         }
      }

      protected void runWithAssignment(Assignments var1) throws Throwable {
         if (!var1.isComplete()) {
            this.runWithIncompleteAssignment(var1);
         } else {
            this.runWithCompleteAssignment(var1);
         }
      }

      protected void runWithCompleteAssignment(Assignments var1) throws Throwable {
         (new BlockJUnit4ClassRunner(this, this.getTestClass().getJavaClass(), var1) {
               final Theories.TheoryAnchor this$0;
               final Assignments val$complete;

               {
                  this.this$0 = var1;
                  this.val$complete = var3;
               }

               protected void collectInitializationErrors(List<Throwable> var1) {
               }

               public Object createTest() throws Exception {
                  Object[] var1x = this.val$complete.getConstructorArguments();
                  if (!this.this$0.nullsOk()) {
                     Assume.assumeNotNull(var1x);
                  }

                  return this.getTestClass().getOnlyConstructor().newInstance(var1x);
               }

               public Statement methodBlock(FrameworkMethod var1) {
                  return new Statement(this, super.methodBlock(var1)) {
                     final <unrepresentable> this$1;
                     final Statement val$statement;

                     {
                        this.this$1 = var1;
                        this.val$statement = var2;
                     }

                     // $VF: Could not inline inconsistent finally blocks
                     // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
                     public void evaluate() throws Throwable {
                        AssumptionViolatedException var1x;
                        try {
                           try {
                              this.val$statement.evaluate();
                              this.this$1.this$0.handleDataPointSuccess();
                              return;
                           } catch (AssumptionViolatedException var4) {
                              var1x = var4;
                           }
                        } catch (Throwable var5) {
                           this.this$1.this$0.reportParameterizedError(var5, this.this$1.val$complete.getArgumentStrings(this.this$1.this$0.nullsOk()));
                           return;
                        }

                        this.this$1.this$0.handleAssumptionViolation(var1x);
                     }
                  };
               }

               protected Statement methodInvoker(FrameworkMethod var1, Object var2) {
                  return this.this$0.methodCompletesWithParameters(var1, this.val$complete, var2);
               }
            })
            .methodBlock(this.testMethod)
            .evaluate();
      }

      protected void runWithIncompleteAssignment(Assignments var1) throws Throwable {
         Iterator var2 = var1.potentialsForNextUnassigned().iterator();

         while (var2.hasNext()) {
            this.runWithAssignment(var1.assignNext((PotentialAssignment)var2.next()));
         }
      }
   }
}
