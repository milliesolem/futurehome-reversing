package junit.framework;

public class AssertionFailedError extends AssertionError {
   private static final long serialVersionUID = 1L;

   public AssertionFailedError() {
   }

   public AssertionFailedError(String var1) {
      super(defaultString(var1));
   }

   private static String defaultString(String var0) {
      String var1 = var0;
      if (var0 == null) {
         var1 = "";
      }

      return var1;
   }
}
