package org.junit.experimental.theories.internal;

import java.util.ArrayList;
import java.util.List;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class EnumSupplier extends ParameterSupplier {
   private Class<?> enumType;

   public EnumSupplier(Class<?> var1) {
      this.enumType = var1;
   }

   @Override
   public List<PotentialAssignment> getValueSources(ParameterSignature var1) {
      Object[] var6 = this.enumType.getEnumConstants();
      ArrayList var5 = new ArrayList();

      for (Object var4 : var6) {
         var5.add(PotentialAssignment.forValue(var4.toString(), var4));
      }

      return var5;
   }
}
