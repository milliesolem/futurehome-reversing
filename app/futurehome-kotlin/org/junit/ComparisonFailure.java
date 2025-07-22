package org.junit;

public class ComparisonFailure extends AssertionError {
   private static final int MAX_CONTEXT_LENGTH = 20;
   private static final long serialVersionUID = 1L;
   private String fActual;
   private String fExpected;

   public ComparisonFailure(String var1, String var2, String var3) {
      super(var1);
      this.fExpected = var2;
      this.fActual = var3;
   }

   public String getActual() {
      return this.fActual;
   }

   public String getExpected() {
      return this.fExpected;
   }

   @Override
   public String getMessage() {
      return new ComparisonFailure.ComparisonCompactor(20, this.fExpected, this.fActual).compact(super.getMessage());
   }

   private static class ComparisonCompactor {
      private static final String DIFF_END = "]";
      private static final String DIFF_START = "[";
      private static final String ELLIPSIS = "...";
      private final String actual;
      private final int contextLength;
      private final String expected;

      public ComparisonCompactor(int var1, String var2, String var3) {
         this.contextLength = var1;
         this.expected = var2;
         this.actual = var3;
      }

      private String sharedPrefix() {
         int var2 = Math.min(this.expected.length(), this.actual.length());

         for (int var1 = 0; var1 < var2; var1++) {
            if (this.expected.charAt(var1) != this.actual.charAt(var1)) {
               return this.expected.substring(0, var1);
            }
         }

         return this.expected.substring(0, var2);
      }

      private String sharedSuffix(String var1) {
         int var3 = Math.min(this.expected.length() - var1.length(), this.actual.length() - var1.length());

         int var2;
         for (var2 = 0; var2 <= var3 - 1; var2++) {
            var1 = this.expected;
            char var4 = var1.charAt(var1.length() - 1 - var2);
            var1 = this.actual;
            if (var4 != var1.charAt(var1.length() - 1 - var2)) {
               break;
            }
         }

         var1 = this.expected;
         return var1.substring(var1.length() - var2);
      }

      public String compact(String var1) {
         String var3 = this.expected;
         if (var3 != null) {
            String var2 = this.actual;
            if (var2 != null && !var3.equals(var2)) {
               ComparisonFailure.ComparisonCompactor.DiffExtractor var7 = new ComparisonFailure.ComparisonCompactor.DiffExtractor(this);
               String var4 = var7.compactPrefix();
               var3 = var7.compactSuffix();
               StringBuilder var5 = new StringBuilder();
               var5.append(var4);
               var5.append(var7.expectedDiff());
               var5.append(var3);
               String var9 = var5.toString();
               StringBuilder var6 = new StringBuilder();
               var6.append(var4);
               var6.append(var7.actualDiff());
               var6.append(var3);
               return Assert.format(var1, var9, var6.toString());
            }
         }

         return Assert.format(var1, this.expected, this.actual);
      }

      private class DiffExtractor {
         private final String sharedPrefix;
         private final String sharedSuffix;
         final ComparisonFailure.ComparisonCompactor this$0;

         private DiffExtractor(ComparisonFailure.ComparisonCompactor var1) {
            this.this$0 = var1;
            String var2 = var1.sharedPrefix();
            this.sharedPrefix = var2;
            this.sharedSuffix = var1.sharedSuffix(var2);
         }

         private String extractDiff(String var1) {
            StringBuilder var2 = new StringBuilder("[");
            var2.append(var1.substring(this.sharedPrefix.length(), var1.length() - this.sharedSuffix.length()));
            var2.append("]");
            return var2.toString();
         }

         public String actualDiff() {
            return this.extractDiff(this.this$0.actual);
         }

         public String compactPrefix() {
            if (this.sharedPrefix.length() <= this.this$0.contextLength) {
               return this.sharedPrefix;
            } else {
               StringBuilder var2 = new StringBuilder("...");
               String var1 = this.sharedPrefix;
               var2.append(var1.substring(var1.length() - this.this$0.contextLength));
               return var2.toString();
            }
         }

         public String compactSuffix() {
            if (this.sharedSuffix.length() <= this.this$0.contextLength) {
               return this.sharedSuffix;
            } else {
               StringBuilder var1 = new StringBuilder();
               var1.append(this.sharedSuffix.substring(0, this.this$0.contextLength));
               var1.append("...");
               return var1.toString();
            }
         }

         public String expectedDiff() {
            return this.extractDiff(this.this$0.expected);
         }
      }
   }
}
