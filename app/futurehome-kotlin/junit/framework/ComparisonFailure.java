package junit.framework;

public class ComparisonFailure extends AssertionFailedError {
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
      return new ComparisonCompactor(20, this.fExpected, this.fActual).compact(super.getMessage());
   }
}
