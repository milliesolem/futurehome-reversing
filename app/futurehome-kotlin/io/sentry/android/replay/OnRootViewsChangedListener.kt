package io.sentry.android.replay

import android.view.View

internal fun interface OnRootViewsChangedListener {
   public abstract fun onRootViewsChanged(view: View, added: Boolean) {
   }
}
