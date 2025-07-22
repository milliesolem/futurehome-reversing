package org.hamcrest;

import java.io.IOException;

public class StringDescription extends BaseDescription {
   private final Appendable out;

   public StringDescription() {
      this(new StringBuilder());
   }

   public StringDescription(Appendable var1) {
      this.out = var1;
   }

   public static String asString(SelfDescribing var0) {
      return toString(var0);
   }

   public static String toString(SelfDescribing var0) {
      return new StringDescription().appendDescriptionOf(var0).toString();
   }

   @Override
   protected void append(char var1) {
      try {
         this.out.append(var1);
      } catch (IOException var3) {
         throw new RuntimeException("Could not write description", var3);
      }
   }

   @Override
   protected void append(String var1) {
      try {
         this.out.append(var1);
      } catch (IOException var2) {
         throw new RuntimeException("Could not write description", var2);
      }
   }

   @Override
   public String toString() {
      return this.out.toString();
   }
}
