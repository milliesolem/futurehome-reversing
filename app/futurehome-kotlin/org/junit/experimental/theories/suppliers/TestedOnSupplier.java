package org.junit.experimental.theories.suppliers;

import java.util.ArrayList;
import java.util.List;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class TestedOnSupplier extends ParameterSupplier {
   @Override
   public List<PotentialAssignment> getValueSources(ParameterSignature var1) {
      ArrayList var4 = new ArrayList();
      int[] var5 = var1.getAnnotation(TestedOn.class).ints();
      int var3 = var5.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(PotentialAssignment.forValue("ints", var5[var2]));
      }

      return var4;
   }
}
