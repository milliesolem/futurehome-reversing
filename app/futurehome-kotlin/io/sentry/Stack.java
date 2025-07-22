package io.sentry;

import io.sentry.util.Objects;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

final class Stack {
   private final Deque<Stack.StackItem> items;
   private final ILogger logger;

   public Stack(ILogger var1, Stack.StackItem var2) {
      LinkedBlockingDeque var3 = new LinkedBlockingDeque();
      this.items = var3;
      this.logger = Objects.requireNonNull(var1, "logger is required");
      var3.push(Objects.requireNonNull(var2, "rootStackItem is required"));
   }

   public Stack(Stack var1) {
      this(var1.logger, new Stack.StackItem(var1.items.getLast()));
      Iterator var2 = var1.items.descendingIterator();
      if (var2.hasNext()) {
         var2.next();
      }

      while (var2.hasNext()) {
         this.push(new Stack.StackItem((Stack.StackItem)var2.next()));
      }
   }

   Stack.StackItem peek() {
      return this.items.peek();
   }

   void pop() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/Stack.items Ljava/util/Deque;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/Stack.items Ljava/util/Deque;
      // 0b: invokeinterface java/util/Deque.size ()I 1
      // 10: bipush 1
      // 11: if_icmpeq 21
      // 14: aload 0
      // 15: getfield io/sentry/Stack.items Ljava/util/Deque;
      // 18: invokeinterface java/util/Deque.pop ()Ljava/lang/Object; 1
      // 1d: pop
      // 1e: goto 33
      // 21: aload 0
      // 22: getfield io/sentry/Stack.logger Lio/sentry/ILogger;
      // 25: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 28: ldc "Attempt to pop the root scope."
      // 2a: bipush 0
      // 2b: anewarray 4
      // 2e: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 33: aload 1
      // 34: monitorexit
      // 35: return
      // 36: astore 2
      // 37: aload 1
      // 38: monitorexit
      // 39: aload 2
      // 3a: athrow
   }

   void push(Stack.StackItem var1) {
      this.items.push(var1);
   }

   int size() {
      return this.items.size();
   }

   static final class StackItem {
      private volatile ISentryClient client;
      private final SentryOptions options;
      private volatile IScope scope;

      StackItem(SentryOptions var1, ISentryClient var2, IScope var3) {
         this.client = Objects.requireNonNull(var2, "ISentryClient is required.");
         this.scope = Objects.requireNonNull(var3, "Scope is required.");
         this.options = Objects.requireNonNull(var1, "Options is required");
      }

      StackItem(Stack.StackItem var1) {
         this.options = var1.options;
         this.client = var1.client;
         this.scope = var1.scope.clone();
      }

      public ISentryClient getClient() {
         return this.client;
      }

      public SentryOptions getOptions() {
         return this.options;
      }

      public IScope getScope() {
         return this.scope;
      }

      public void setClient(ISentryClient var1) {
         this.client = var1;
      }
   }
}
