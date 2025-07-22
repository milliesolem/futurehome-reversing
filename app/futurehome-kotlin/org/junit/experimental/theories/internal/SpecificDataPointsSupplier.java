package org.junit.experimental.theories.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class SpecificDataPointsSupplier extends AllMembersSupplier {
   public SpecificDataPointsSupplier(TestClass var1) {
      super(var1);
   }

   @Override
   protected Collection<Field> getDataPointsFields(ParameterSignature var1) {
      Collection var3 = super.getDataPointsFields(var1);
      String var2 = var1.getAnnotation(FromDataPoints.class).value();
      ArrayList var5 = new ArrayList();

      for (Field var6 : var3) {
         if (Arrays.asList(var6.getAnnotation(DataPoints.class).value()).contains(var2)) {
            var5.add(var6);
         }
      }

      return var5;
   }

   @Override
   protected Collection<FrameworkMethod> getDataPointsMethods(ParameterSignature var1) {
      Collection var3 = super.getDataPointsMethods(var1);
      String var2 = var1.getAnnotation(FromDataPoints.class).value();
      ArrayList var5 = new ArrayList();

      for (FrameworkMethod var4 : var3) {
         if (Arrays.asList(((DataPoints)var4.getAnnotation(DataPoints.class)).value()).contains(var2)) {
            var5.add(var4);
         }
      }

      return var5;
   }

   @Override
   protected Collection<Field> getSingleDataPointFields(ParameterSignature var1) {
      Collection var3 = super.getSingleDataPointFields(var1);
      String var2 = var1.getAnnotation(FromDataPoints.class).value();
      ArrayList var5 = new ArrayList();

      for (Field var4 : var3) {
         if (Arrays.asList(var4.getAnnotation(DataPoint.class).value()).contains(var2)) {
            var5.add(var4);
         }
      }

      return var5;
   }

   @Override
   protected Collection<FrameworkMethod> getSingleDataPointMethods(ParameterSignature var1) {
      Collection var3 = super.getSingleDataPointMethods(var1);
      String var5 = var1.getAnnotation(FromDataPoints.class).value();
      ArrayList var2 = new ArrayList();

      for (FrameworkMethod var6 : var3) {
         if (Arrays.asList(((DataPoint)var6.getAnnotation(DataPoint.class)).value()).contains(var5)) {
            var2.add(var6);
         }
      }

      return var2;
   }
}
