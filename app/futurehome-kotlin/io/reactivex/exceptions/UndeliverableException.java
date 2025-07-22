package io.reactivex.exceptions;

public final class UndeliverableException extends IllegalStateException {
   private static final long serialVersionUID = 1644750035281290266L;

   public UndeliverableException(Throwable var1) {
      StringBuilder var2 = new StringBuilder(
         "The exception could not be delivered to the consumer because it has already canceled/disposed the flow or the exception has nowhere to go to begin with. Further reading: https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling | "
      );
      var2.append(var1);
      super(var2.toString(), var1);
   }
}
