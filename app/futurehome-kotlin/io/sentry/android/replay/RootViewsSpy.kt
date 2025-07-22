package io.sentry.android.replay

import android.os.Handler
import android.os.Looper
import android.view.View
import java.io.Closeable
import java.util.ArrayList
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.jvm.functions.Function1

internal class RootViewsSpy private constructor() : Closeable {
   private final val delegatingViewList: ArrayList<View>
   private final val isClosed: AtomicBoolean = new AtomicBoolean(false)
   public final val listeners: CopyOnWriteArrayList<OnRootViewsChangedListener>
   private final val viewListLock: Any = new Object()

   init {
      this.listeners = new CopyOnWriteArrayList<OnRootViewsChangedListener>(this) {
         final RootViewsSpy this$0;

         {
            this.this$0 = var1;
         }

         public boolean add(OnRootViewsChangedListener param1) {
            // $VF: Couldn't be decompiled
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
            //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
            //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
            //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
            //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
            //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
            //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
            //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
            //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
            //
            // Bytecode:
            // 00: aload 0
            // 01: getfield io/sentry/android/replay/RootViewsSpy$listeners$1.this$0 Lio/sentry/android/replay/RootViewsSpy;
            // 04: invokestatic io/sentry/android/replay/RootViewsSpy.access$getViewListLock$p (Lio/sentry/android/replay/RootViewsSpy;)Ljava/lang/Object;
            // 07: astore 2
            // 08: aload 0
            // 09: getfield io/sentry/android/replay/RootViewsSpy$listeners$1.this$0 Lio/sentry/android/replay/RootViewsSpy;
            // 0c: astore 3
            // 0d: aload 2
            // 0e: monitorenter
            // 0f: aload 3
            // 10: invokestatic io/sentry/android/replay/RootViewsSpy.access$getDelegatingViewList$p (Lio/sentry/android/replay/RootViewsSpy;)Ljava/util/ArrayList;
            // 13: checkcast java/lang/Iterable
            // 16: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
            // 1b: astore 3
            // 1c: aload 3
            // 1d: invokeinterface java/util/Iterator.hasNext ()Z 1
            // 22: ifeq 40
            // 25: aload 3
            // 26: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
            // 2b: checkcast android/view/View
            // 2e: astore 4
            // 30: aload 1
            // 31: ifnull 1c
            // 34: aload 1
            // 35: aload 4
            // 37: bipush 1
            // 38: invokeinterface io/sentry/android/replay/OnRootViewsChangedListener.onRootViewsChanged (Landroid/view/View;Z)V 3
            // 3d: goto 1c
            // 40: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
            // 43: astore 3
            // 44: aload 2
            // 45: monitorexit
            // 46: aload 0
            // 47: aload 1
            // 48: invokespecial java/util/concurrent/CopyOnWriteArrayList.add (Ljava/lang/Object;)Z
            // 4b: ireturn
            // 4c: astore 1
            // 4d: aload 2
            // 4e: monitorexit
            // 4f: aload 1
            // 50: athrow
         }
      };
      this.delegatingViewList = new ArrayList<View>(this) {
         final RootViewsSpy this$0;

         {
            this.this$0 = var1;
         }

         public boolean add(View var1) {
            val var2: java.util.Iterator = this.this$0.getListeners().iterator();

            while (var2.hasNext()) {
               (var2.next() as OnRootViewsChangedListener).onRootViewsChanged(var1, true);
            }

            return super.add(var1);
         }

         @Override
         public boolean addAll(java.util.Collection<? extends View> var1) {
            for (OnRootViewsChangedListener var3 : this.this$0.getListeners()) {
               val var4: java.util.Iterator = var1.iterator();

               while (var4.hasNext()) {
                  var3.onRootViewsChanged(var4.next() as View, true);
               }
            }

            return super.addAll(var1);
         }

         public View removeAt(int var1) {
            var var2: View = (View)super.remove(var1);
            var2 = var2;
            val var3: java.util.Iterator = this.this$0.getListeners().iterator();

            while (var3.hasNext()) {
               (var3.next() as OnRootViewsChangedListener).onRootViewsChanged(var2, false);
            }

            return var2;
         }
      };
   }

   public override fun close() {
      this.isClosed.set(true);
      this.listeners.clear();
   }

   public companion object {
      @JvmStatic
      fun `install$lambda$1$lambda$0`(var0: RootViewsSpy) {
         if (!RootViewsSpy.access$isClosed$p(var0).get()) {
            WindowManagerSpy.INSTANCE.swapWindowManagerGlobalMViews((new Function1<ArrayList<View>, ArrayList<View>>(var0) {
               final RootViewsSpy $this_apply;

               {
                  super(1);
                  this.$this_apply = var1;
               }

               public final ArrayList<View> invoke(ArrayList<View> var1) {
                  label13: {
                     val var2: Any = RootViewsSpy.access$getViewListLock$p(this.$this_apply);
                     val var3: RootViewsSpy = this.$this_apply;
                     synchronized (var2){} // $VF: monitorenter 

                     try {
                        RootViewsSpy.access$getDelegatingViewList$p(var3).addAll(var1);
                     } catch (var4: java.lang.Throwable) {
                        // $VF: monitorexit
                     }

                     // $VF: monitorexit
                  }
               }
            }) as (ArrayList<View>?) -> ArrayList<View>);
         }
      }

      public fun install(): RootViewsSpy {
         val var1: RootViewsSpy = new RootViewsSpy(null);
         new Handler(Looper.getMainLooper()).postAtFrontOfQueue(new RootViewsSpy$Companion$$ExternalSyntheticLambda0(var1));
         return var1;
      }
   }
}
