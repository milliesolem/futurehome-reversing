package io.reactivex.disposables;

import io.reactivex.functions.Action;
import io.reactivex.internal.util.ExceptionHelper;

final class ActionDisposable extends ReferenceDisposable<Action> {
   private static final long serialVersionUID = -8219729196779211169L;

   ActionDisposable(Action var1) {
      super(var1);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   protected void onDisposed(Action var1) {
      try {
         var1.run();
      } catch (Throwable var3) {
         throw ExceptionHelper.wrapOrThrow(var3);
      }
   }
}
