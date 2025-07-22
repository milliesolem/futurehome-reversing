package io.sentry.android.replay

internal enum class ReplayState {
   CLOSED,
   INITIAL,
   PAUSED,
   RESUMED,
   STARTED,
   STOPPED   @JvmStatic
   private ReplayState[] $VALUES = $values();
}
