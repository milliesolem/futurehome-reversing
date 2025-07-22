package io.reactivex.disposables;

final class RunnableDisposable extends ReferenceDisposable<Runnable> {
   private static final long serialVersionUID = -8219729196779211169L;

   RunnableDisposable(Runnable var1) {
      super(var1);
   }

   protected void onDisposed(Runnable var1) {
      var1.run();
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("RunnableDisposable(disposed=");
      var1.append(this.isDisposed());
      var1.append(", ");
      var1.append(this.get());
      var1.append(")");
      return var1.toString();
   }
}
