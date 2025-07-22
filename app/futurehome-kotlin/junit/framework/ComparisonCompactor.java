package junit.framework;

public class ComparisonCompactor {
   private static final String DELTA_END = "]";
   private static final String DELTA_START = "[";
   private static final String ELLIPSIS = "...";
   private String fActual;
   private int fContextLength;
   private String fExpected;
   private int fPrefix;
   private int fSuffix;

   public ComparisonCompactor(int var1, String var2, String var3) {
      this.fContextLength = var1;
      this.fExpected = var2;
      this.fActual = var3;
   }

   private boolean areStringsEqual() {
      return this.fExpected.equals(this.fActual);
   }

   private String compactString(String var1) {
      StringBuilder var2 = new StringBuilder("[");
      var2.append(var1.substring(this.fPrefix, var1.length() - this.fSuffix + 1));
      var2.append("]");
      String var5 = var2.toString();
      var1 = var5;
      if (this.fPrefix > 0) {
         StringBuilder var4 = new StringBuilder();
         var4.append(this.computeCommonPrefix());
         var4.append(var5);
         var1 = var4.toString();
      }

      String var6 = var1;
      if (this.fSuffix > 0) {
         var2 = new StringBuilder();
         var2.append(var1);
         var2.append(this.computeCommonSuffix());
         var6 = var2.toString();
      }

      return var6;
   }

   private String computeCommonPrefix() {
      StringBuilder var2 = new StringBuilder();
      String var1;
      if (this.fPrefix > this.fContextLength) {
         var1 = "...";
      } else {
         var1 = "";
      }

      var2.append(var1);
      var2.append(this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix));
      return var2.toString();
   }

   private String computeCommonSuffix() {
      int var1 = Math.min(this.fExpected.length() - this.fSuffix + 1 + this.fContextLength, this.fExpected.length());
      StringBuilder var3 = new StringBuilder();
      String var2 = this.fExpected;
      var3.append(var2.substring(var2.length() - this.fSuffix + 1, var1));
      if (this.fExpected.length() - this.fSuffix + 1 < this.fExpected.length() - this.fContextLength) {
         var2 = "...";
      } else {
         var2 = "";
      }

      var3.append(var2);
      return var3.toString();
   }

   private void findCommonPrefix() {
      this.fPrefix = 0;
      int var2 = Math.min(this.fExpected.length(), this.fActual.length());

      while (true) {
         int var1 = this.fPrefix;
         if (var1 >= var2 || this.fExpected.charAt(var1) != this.fActual.charAt(this.fPrefix)) {
            return;
         }

         this.fPrefix++;
      }
   }

   private void findCommonSuffix() {
      int var1 = this.fExpected.length() - 1;
      int var2 = this.fActual.length() - 1;

      while (true) {
         int var3 = this.fPrefix;
         if (var2 < var3 || var1 < var3 || this.fExpected.charAt(var1) != this.fActual.charAt(var2)) {
            this.fSuffix = this.fExpected.length() - var1;
            return;
         }

         var2--;
         var1--;
      }
   }

   public String compact(String var1) {
      if (this.fExpected != null && this.fActual != null && !this.areStringsEqual()) {
         this.findCommonPrefix();
         this.findCommonSuffix();
         return Assert.format(var1, this.compactString(this.fExpected), this.compactString(this.fActual));
      } else {
         return Assert.format(var1, this.fExpected, this.fActual);
      }
   }
}
