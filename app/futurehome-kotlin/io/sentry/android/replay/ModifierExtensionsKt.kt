package io.sentry.android.replay

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsModifierKt

public fun Modifier.sentryReplayMask(): Modifier {
   return SemanticsModifierKt.semantics$default(var0, false, <unrepresentable>.INSTANCE, 1, null);
}

public fun Modifier.sentryReplayUnmask(): Modifier {
   return SemanticsModifierKt.semantics$default(var0, false, <unrepresentable>.INSTANCE, 1, null);
}
