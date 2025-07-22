package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import org.reactivestreams.Subscriber;

public final class FlowableLift<R, T> extends AbstractFlowableWithUpstream<T, R> {
   final FlowableOperator<? extends R, ? super T> operator;

   public FlowableLift(Flowable<T> var1, FlowableOperator<? extends R, ? super T> var2) {
      super(var1);
      this.operator = var2;
   }

   @Override
   public void subscribeActual(Subscriber<? super R> param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/reactivex/internal/operators/flowable/FlowableLift.operator Lio/reactivex/FlowableOperator;
      // 04: aload 1
      // 05: invokeinterface io/reactivex/FlowableOperator.apply (Lorg/reactivestreams/Subscriber;)Lorg/reactivestreams/Subscriber; 2
      // 0a: astore 1
      // 0b: aload 1
      // 0c: ifnull 18
      // 0f: aload 0
      // 10: getfield io/reactivex/internal/operators/flowable/FlowableLift.source Lio/reactivex/Flowable;
      // 13: aload 1
      // 14: invokevirtual io/reactivex/Flowable.subscribe (Lorg/reactivestreams/Subscriber;)V
      // 17: return
      // 18: new java/lang/NullPointerException
      // 1b: astore 1
      // 1c: new java/lang/StringBuilder
      // 1f: astore 2
      // 20: aload 2
      // 21: ldc "Operator "
      // 23: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 26: aload 2
      // 27: aload 0
      // 28: getfield io/reactivex/internal/operators/flowable/FlowableLift.operator Lio/reactivex/FlowableOperator;
      // 2b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 2e: pop
      // 2f: aload 2
      // 30: ldc " returned a null Subscriber"
      // 32: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 35: pop
      // 36: aload 1
      // 37: aload 2
      // 38: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3b: invokespecial java/lang/NullPointerException.<init> (Ljava/lang/String;)V
      // 3e: aload 1
      // 3f: athrow
      // 40: astore 2
      // 41: aload 2
      // 42: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
      // 45: aload 2
      // 46: invokestatic io/reactivex/plugins/RxJavaPlugins.onError (Ljava/lang/Throwable;)V
      // 49: new java/lang/NullPointerException
      // 4c: dup
      // 4d: ldc "Actually not, but can't throw other exceptions due to RS"
      // 4f: invokespecial java/lang/NullPointerException.<init> (Ljava/lang/String;)V
      // 52: astore 1
      // 53: aload 1
      // 54: aload 2
      // 55: invokevirtual java/lang/NullPointerException.initCause (Ljava/lang/Throwable;)Ljava/lang/Throwable;
      // 58: pop
      // 59: aload 1
      // 5a: athrow
      // 5b: astore 1
      // 5c: aload 1
      // 5d: athrow
   }
}
