package io.reactivex.exceptions;

public final class OnErrorNotImplementedException extends RuntimeException {
   private static final long serialVersionUID = -6298857009889503852L;

   public OnErrorNotImplementedException(String var1, Throwable var2) {
      if (var2 == null) {
         var2 = new NullPointerException();
      }

      super(var1, (Throwable)var2);
   }

   public OnErrorNotImplementedException(Throwable var1) {
      StringBuilder var2 = new StringBuilder(
         "The exception was not handled due to missing onError handler in the subscribe() method call. Further reading: https://github.com/ReactiveX/RxJava/wiki/Error-Handling | "
      );
      var2.append(var1);
      this(var2.toString(), var1);
   }
}
