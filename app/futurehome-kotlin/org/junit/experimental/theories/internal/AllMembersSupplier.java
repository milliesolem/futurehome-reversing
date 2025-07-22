package org.junit.experimental.theories.internal;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Assume;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class AllMembersSupplier extends ParameterSupplier {
   private final TestClass clazz;

   public AllMembersSupplier(TestClass var1) {
      this.clazz = var1;
   }

   private void addArrayValues(ParameterSignature var1, String var2, List<PotentialAssignment> var3, Object var4) {
      for (int var5 = 0; var5 < Array.getLength(var4); var5++) {
         Object var6 = Array.get(var4, var5);
         if (var1.canAcceptValue(var6)) {
            StringBuilder var7 = new StringBuilder();
            var7.append(var2);
            var7.append("[");
            var7.append(var5);
            var7.append("]");
            var3.add(PotentialAssignment.forValue(var7.toString(), var6));
         }
      }
   }

   private void addDataPointsValues(Class<?> var1, ParameterSignature var2, String var3, List<PotentialAssignment> var4, Object var5) {
      if (var1.isArray()) {
         this.addArrayValues(var2, var3, var4, var5);
      } else if (Iterable.class.isAssignableFrom(var1)) {
         this.addIterableValues(var2, var3, var4, (Iterable<?>)var5);
      }
   }

   private void addIterableValues(ParameterSignature var1, String var2, List<PotentialAssignment> var3, Iterable<?> var4) {
      Iterator var7 = var4.iterator();

      for (int var5 = 0; var7.hasNext(); var5++) {
         Object var6 = var7.next();
         if (var1.canAcceptValue(var6)) {
            StringBuilder var8 = new StringBuilder();
            var8.append(var2);
            var8.append("[");
            var8.append(var5);
            var8.append("]");
            var3.add(PotentialAssignment.forValue(var8.toString(), var6));
         }
      }
   }

   private void addMultiPointFields(ParameterSignature var1, List<PotentialAssignment> var2) {
      for (Field var4 : this.getDataPointsFields(var1)) {
         this.addDataPointsValues(var4.getType(), var1, var4.getName(), var2, this.getStaticFieldValue(var4));
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void addMultiPointMethods(ParameterSignature var1, List<PotentialAssignment> var2) throws Throwable {
      for (FrameworkMethod var3 : this.getDataPointsMethods(var1)) {
         Class var5 = var3.getReturnType();
         if (var5.isArray() && var1.canPotentiallyAcceptType(var5.getComponentType()) || Iterable.class.isAssignableFrom(var5)) {
            try {
               this.addDataPointsValues(var5, var1, var3.getName(), var2, var3.invokeExplosively(null, new Object[0]));
            } catch (Throwable var7) {
               DataPoints var8 = (DataPoints)var3.getAnnotation(DataPoints.class);
               if (var8 != null && isAssignableToAnyOf(var8.ignoredExceptions(), var7)) {
                  return;
               }

               throw var7;
            }
         }
      }
   }

   private void addSinglePointFields(ParameterSignature var1, List<PotentialAssignment> var2) {
      for (Field var5 : this.getSingleDataPointFields(var1)) {
         Object var4 = this.getStaticFieldValue(var5);
         if (var1.canAcceptValue(var4)) {
            var2.add(PotentialAssignment.forValue(var5.getName(), var4));
         }
      }
   }

   private void addSinglePointMethods(ParameterSignature var1, List<PotentialAssignment> var2) {
      for (FrameworkMethod var3 : this.getSingleDataPointMethods(var1)) {
         if (var1.canAcceptType(var3.getType())) {
            var2.add(new AllMembersSupplier.MethodParameterValue(var3));
         }
      }
   }

   private Object getStaticFieldValue(Field var1) {
      try {
         return var1.get(null);
      } catch (IllegalArgumentException var2) {
         throw new RuntimeException("unexpected: field from getClass doesn't exist on object");
      } catch (IllegalAccessException var3) {
         throw new RuntimeException("unexpected: getFields returned an inaccessible field");
      }
   }

   private static boolean isAssignableToAnyOf(Class<?>[] var0, Object var1) {
      int var3 = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var0[var2].isAssignableFrom(var1.getClass())) {
            return true;
         }
      }

      return false;
   }

   protected Collection<Field> getDataPointsFields(ParameterSignature var1) {
      List var2 = this.clazz.getAnnotatedFields(DataPoints.class);
      ArrayList var3 = new ArrayList();
      Iterator var4 = var2.iterator();

      while (var4.hasNext()) {
         var3.add(((FrameworkField)var4.next()).getField());
      }

      return var3;
   }

   protected Collection<FrameworkMethod> getDataPointsMethods(ParameterSignature var1) {
      return this.clazz.getAnnotatedMethods(DataPoints.class);
   }

   protected Collection<Field> getSingleDataPointFields(ParameterSignature var1) {
      List var2 = this.clazz.getAnnotatedFields(DataPoint.class);
      ArrayList var3 = new ArrayList();
      Iterator var4 = var2.iterator();

      while (var4.hasNext()) {
         var3.add(((FrameworkField)var4.next()).getField());
      }

      return var3;
   }

   protected Collection<FrameworkMethod> getSingleDataPointMethods(ParameterSignature var1) {
      return this.clazz.getAnnotatedMethods(DataPoint.class);
   }

   @Override
   public List<PotentialAssignment> getValueSources(ParameterSignature var1) throws Throwable {
      ArrayList var2 = new ArrayList();
      this.addSinglePointFields(var1, var2);
      this.addMultiPointFields(var1, var2);
      this.addSinglePointMethods(var1, var2);
      this.addMultiPointMethods(var1, var2);
      return var2;
   }

   static class MethodParameterValue extends PotentialAssignment {
      private final FrameworkMethod method;

      private MethodParameterValue(FrameworkMethod var1) {
         this.method = var1;
      }

      @Override
      public String getDescription() throws PotentialAssignment.CouldNotGenerateValueException {
         return this.method.getName();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public Object getValue() throws PotentialAssignment.CouldNotGenerateValueException {
         boolean var1 = false;

         try {
            try {
               return this.method.invokeExplosively(null, new Object[0]);
            } catch (IllegalArgumentException var7) {
            } catch (IllegalAccessException var8) {
               throw new RuntimeException("unexpected: getMethods returned an inaccessible method");
            }
         } catch (Throwable var9) {
            DataPoint var2 = (DataPoint)this.method.getAnnotation(DataPoint.class);
            if (var2 == null || !AllMembersSupplier.isAssignableToAnyOf(var2.ignoredExceptions(), var9)) {
               var1 = true;
            }

            Assume.assumeTrue(var1);
            throw new PotentialAssignment.CouldNotGenerateValueException(var9);
         }

         throw new RuntimeException("unexpected: argument length is checked");
      }
   }
}
