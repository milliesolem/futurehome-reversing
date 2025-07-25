package io.reactivex;

import org.reactivestreams.Subscriber;

public interface FlowableOperator<Downstream, Upstream> {
   Subscriber<? super Upstream> apply(Subscriber<? super Downstream> var1) throws Exception;
}
