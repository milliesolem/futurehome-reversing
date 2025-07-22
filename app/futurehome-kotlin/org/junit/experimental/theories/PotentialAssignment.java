package org.junit.experimental.theories;

public abstract class PotentialAssignment {
   public static PotentialAssignment forValue(String var0, Object var1) {
      return new PotentialAssignment(var1, var0) {
         final String val$name;
         final Object val$value;

         {
            this.val$value = var1;
            this.val$name = var2;
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         @Override
         public String getDescription() {
            Object var1x = this.val$value;
            if (var1x == null) {
               var1x = "null";
            } else {
               try {
                  var1x = String.format("\"%s\"", var1x);
               } catch (Throwable var3) {
                  var1x = String.format("[toString() threw %s: %s]", var3.getClass().getSimpleName(), var3.getMessage());
                  return String.format("%s <from %s>", var1x, this.val$name);
               }
            }

            return String.format("%s <from %s>", var1x, this.val$name);
         }

         @Override
         public Object getValue() {
            return this.val$value;
         }

         @Override
         public String toString() {
            return String.format("[%s]", this.val$value);
         }
      };
   }

   public abstract String getDescription() throws PotentialAssignment.CouldNotGenerateValueException;

   public abstract Object getValue() throws PotentialAssignment.CouldNotGenerateValueException;

   public static class CouldNotGenerateValueException extends Exception {
      private static final long serialVersionUID = 1L;

      public CouldNotGenerateValueException() {
      }

      public CouldNotGenerateValueException(Throwable var1) {
         super(var1);
      }
   }
}
