package io.sentry.android.replay

import android.view.View

public fun View.sentryReplayMask() {
   var0.setTag(R.id.sentry_privacy, "mask");
}

public fun View.sentryReplayUnmask() {
   var0.setTag(R.id.sentry_privacy, "unmask");
}
