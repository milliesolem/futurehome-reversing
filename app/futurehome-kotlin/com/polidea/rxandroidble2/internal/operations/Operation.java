package com.polidea.rxandroidble2.internal.operations;

import com.polidea.rxandroidble2.internal.Priority;
import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import io.reactivex.Observable;

public interface Operation<T> extends Comparable<Operation<?>> {
   Priority definedPriority();

   Observable<T> run(QueueReleaseInterface var1);
}
