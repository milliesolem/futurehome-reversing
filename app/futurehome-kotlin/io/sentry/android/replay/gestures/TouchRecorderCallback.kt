package io.sentry.android.replay.gestures

import android.view.MotionEvent

public interface TouchRecorderCallback {
   public abstract fun onTouchEvent(event: MotionEvent) {
   }
}
