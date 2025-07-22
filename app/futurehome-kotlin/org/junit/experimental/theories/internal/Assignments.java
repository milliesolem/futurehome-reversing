package org.junit.experimental.theories.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.runners.model.TestClass;

public class Assignments {
   private final List<PotentialAssignment> assigned;
   private final TestClass clazz;
   private final List<ParameterSignature> unassigned;

   private Assignments(List<PotentialAssignment> var1, List<ParameterSignature> var2, TestClass var3) {
      this.unassigned = var2;
      this.assigned = var1;
      this.clazz = var3;
   }

   public static Assignments allUnassigned(Method var0, TestClass var1) {
      List var2 = ParameterSignature.signatures(var1.getOnlyConstructor());
      var2.addAll(ParameterSignature.signatures(var0));
      return new Assignments(new ArrayList<>(), var2, var1);
   }

   private ParameterSupplier buildParameterSupplierFromClass(Class<? extends ParameterSupplier> var1) throws Exception {
      for (Constructor var6 : var1.getConstructors()) {
         Class[] var5 = var6.getParameterTypes();
         if (var5.length == 1 && var5[0].equals(TestClass.class)) {
            return (ParameterSupplier)var6.newInstance(this.clazz);
         }
      }

      return (ParameterSupplier)var1.newInstance();
   }

   private List<PotentialAssignment> generateAssignmentsFromTypeAlone(ParameterSignature var1) {
      Class var2 = var1.getType();
      if (var2.isEnum()) {
         return new EnumSupplier(var2).getValueSources(var1);
      } else {
         return !var2.equals(Boolean.class) && !var2.equals(boolean.class) ? Collections.emptyList() : new BooleanSupplier().getValueSources(var1);
      }
   }

   private int getConstructorParameterCount() {
      return ParameterSignature.signatures(this.clazz.getOnlyConstructor()).size();
   }

   private ParameterSupplier getSupplier(ParameterSignature var1) throws Exception {
      ParametersSuppliedBy var2 = var1.findDeepAnnotation(ParametersSuppliedBy.class);
      return (ParameterSupplier)(var2 != null ? this.buildParameterSupplierFromClass(var2.value()) : new AllMembersSupplier(this.clazz));
   }

   public Assignments assignNext(PotentialAssignment var1) {
      ArrayList var2 = new ArrayList<>(this.assigned);
      var2.add(var1);
      List var3 = this.unassigned;
      return new Assignments(var2, var3.subList(1, var3.size()), this.clazz);
   }

   public Object[] getActualValues(int var1, int var2) throws PotentialAssignment.CouldNotGenerateValueException {
      Object[] var4 = new Object[var2 - var1];

      for (int var3 = var1; var3 < var2; var3++) {
         var4[var3 - var1] = this.assigned.get(var3).getValue();
      }

      return var4;
   }

   public Object[] getAllArguments() throws PotentialAssignment.CouldNotGenerateValueException {
      return this.getActualValues(0, this.assigned.size());
   }

   public Object[] getArgumentStrings(boolean var1) throws PotentialAssignment.CouldNotGenerateValueException {
      int var3 = this.assigned.size();
      Object[] var4 = new Object[var3];

      for (int var2 = 0; var2 < var3; var2++) {
         var4[var2] = this.assigned.get(var2).getDescription();
      }

      return var4;
   }

   public Object[] getConstructorArguments() throws PotentialAssignment.CouldNotGenerateValueException {
      return this.getActualValues(0, this.getConstructorParameterCount());
   }

   public Object[] getMethodArguments() throws PotentialAssignment.CouldNotGenerateValueException {
      return this.getActualValues(this.getConstructorParameterCount(), this.assigned.size());
   }

   public boolean isComplete() {
      boolean var1;
      if (this.unassigned.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public ParameterSignature nextUnassigned() {
      return this.unassigned.get(0);
   }

   public List<PotentialAssignment> potentialsForNextUnassigned() throws Throwable {
      ParameterSignature var3 = this.nextUnassigned();
      List var2 = this.getSupplier(var3).getValueSources(var3);
      List var1 = var2;
      if (var2.size() == 0) {
         var1 = this.generateAssignmentsFromTypeAlone(var3);
      }

      return var1;
   }
}
