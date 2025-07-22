package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation

internal final val EMPTY_RESUMES: Array<Continuation<Unit>?>
public Continuation<Unit>[] EMPTY_RESUMES = new Continuation[0];
