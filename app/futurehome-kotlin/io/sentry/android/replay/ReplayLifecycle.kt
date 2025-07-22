package io.sentry.android.replay

internal class ReplayLifecycle {
   internal final var currentState: ReplayState = ReplayState.INITIAL

   public fun isAllowed(newState: ReplayState): Boolean {
      switch (ReplayLifecycle.WhenMappings.$EnumSwitchMapping$0[this.currentState.ordinal()]) {
         case 1:
            if (var1 === ReplayState.STARTED) {
               return true;
            }

            if (var1 === ReplayState.CLOSED) {
               return true;
            }
            break;
         case 2:
            if (var1 === ReplayState.PAUSED) {
               return true;
            }

            if (var1 === ReplayState.STOPPED) {
               return true;
            }

            if (var1 === ReplayState.CLOSED) {
               return true;
            }
            break;
         case 3:
            if (var1 === ReplayState.PAUSED) {
               return true;
            }

            if (var1 === ReplayState.STOPPED) {
               return true;
            }

            if (var1 === ReplayState.CLOSED) {
               return true;
            }
            break;
         case 4:
            if (var1 === ReplayState.RESUMED) {
               return true;
            }

            if (var1 === ReplayState.STOPPED) {
               return true;
            }

            if (var1 === ReplayState.CLOSED) {
               return true;
            }
            break;
         case 5:
            if (var1 === ReplayState.STARTED) {
               return true;
            }

            if (var1 === ReplayState.CLOSED) {
               return true;
            }
         case 6:
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return false;
   }

   public fun isTouchRecordingAllowed(): Boolean {
      val var1: Boolean;
      if (this.currentState != ReplayState.STARTED && this.currentState != ReplayState.RESUMED) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
