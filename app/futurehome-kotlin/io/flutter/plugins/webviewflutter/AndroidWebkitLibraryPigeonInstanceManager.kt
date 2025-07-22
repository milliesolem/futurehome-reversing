package io.flutter.plugins.webviewflutter

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.HashMap
import java.util.WeakHashMap
import kotlin.jvm.internal.TypeIntrinsics

public class AndroidWebkitLibraryPigeonInstanceManager(finalizationListener: io.flutter.plugins.webviewflutter.AndroidWebkitLibraryPigeonInstanceManager.PigeonFinalizationListener
) {
   private final val finalizationListener: io.flutter.plugins.webviewflutter.AndroidWebkitLibraryPigeonInstanceManager.PigeonFinalizationListener
   private final val identifiers: WeakHashMap<Any, Long>
   private final val weakInstances: HashMap<Long, WeakReference<Any>>
   private final val strongInstances: HashMap<Long, Any>
   private final val referenceQueue: ReferenceQueue<Any>
   private final val weakReferencesToIdentifiers: HashMap<WeakReference<Any>, Long>
   private final val handler: Handler
   private final var nextIdentifier: Long
   private final var hasFinalizationListenerStopped: Boolean

   public final var clearFinalizedWeakReferencesInterval: Long
      internal final set(value) {
         this.handler.removeCallbacks(new AndroidWebkitLibraryPigeonInstanceManager$$ExternalSyntheticLambda2(this));
         this.clearFinalizedWeakReferencesInterval = var1;
         this.releaseAllFinalizedInstances();
      }


   init {
      this.finalizationListener = var1;
      this.identifiers = new WeakHashMap<>();
      this.weakInstances = new HashMap<>();
      this.strongInstances = new HashMap<>();
      this.referenceQueue = new ReferenceQueue<>();
      this.weakReferencesToIdentifiers = new HashMap<>();
      val var2: Handler = new Handler(Looper.getMainLooper());
      this.handler = var2;
      this.nextIdentifier = 65536L;
      this.clearFinalizedWeakReferencesInterval = 3000L;
      var2.postDelayed(new AndroidWebkitLibraryPigeonInstanceManager$$ExternalSyntheticLambda0(this), this.clearFinalizedWeakReferencesInterval);
   }

   @JvmStatic
   fun `_init_$lambda$1`(var0: AndroidWebkitLibraryPigeonInstanceManager) {
      var0.releaseAllFinalizedInstances();
   }

   @JvmStatic
   fun `_set_clearFinalizedWeakReferencesInterval_$lambda$0`(var0: AndroidWebkitLibraryPigeonInstanceManager) {
      var0.releaseAllFinalizedInstances();
   }

   private fun addInstance(instance: Any, identifier: Long) {
      if (var2 >= 0L) {
         if (!this.weakInstances.containsKey(var2)) {
            val var4: WeakReference = new WeakReference<>(var1, this.referenceQueue);
            this.identifiers.put(var1, var2);
            this.weakInstances.put(var2, var4);
            this.weakReferencesToIdentifiers.put(var4, var2);
            this.strongInstances.put(var2, var1);
         } else {
            var1 = new StringBuilder("Identifier has already been added: ");
            var1.append(var2);
            throw new IllegalArgumentException(var1.toString().toString());
         }
      } else {
         var1 = new StringBuilder("Identifier must be >= 0: ");
         var1.append(var2);
         throw new IllegalArgumentException(var1.toString().toString());
      }
   }

   private fun logWarningIfFinalizationListenerHasStopped() {
      if (this.hasFinalizationListenerStopped()) {
         Log.w("PigeonInstanceManager", "The manager was used after calls to the PigeonFinalizationListener has been stopped.");
      }
   }

   private fun releaseAllFinalizedInstances() {
      if (!this.hasFinalizationListenerStopped()) {
         while (true) {
            val var1: WeakReference = this.referenceQueue.poll() as WeakReference;
            if (var1 == null) {
               this.handler
                  .postDelayed(new AndroidWebkitLibraryPigeonInstanceManager$$ExternalSyntheticLambda3(this), this.clearFinalizedWeakReferencesInterval);
               return;
            }

            val var2: java.lang.Long = TypeIntrinsics.asMutableMap(this.weakReferencesToIdentifiers).remove(var1) as java.lang.Long;
            if (var2 != null) {
               this.weakInstances.remove(var2);
               this.strongInstances.remove(var2);
               this.finalizationListener.onFinalize(var2);
            }
         }
      }
   }

   @JvmStatic
   fun `releaseAllFinalizedInstances$lambda$5`(var0: AndroidWebkitLibraryPigeonInstanceManager) {
      var0.releaseAllFinalizedInstances();
   }

   @JvmStatic
   fun `stopFinalizationListener$lambda$3`(var0: AndroidWebkitLibraryPigeonInstanceManager) {
      var0.releaseAllFinalizedInstances();
   }

   public fun addDartCreatedInstance(instance: Any, identifier: Long) {
      this.logWarningIfFinalizationListenerHasStopped();
      this.addInstance(var1, var2);
   }

   public fun addHostCreatedInstance(instance: Any): Long {
      this.logWarningIfFinalizationListenerHasStopped();
      if (!this.containsInstance(var1)) {
         val var2: Long = (long)(this.nextIdentifier++);
         this.addInstance(var1, var2);
         return var2;
      } else {
         var1 = var1.getClass();
         val var4: StringBuilder = new StringBuilder("Instance of ");
         var4.append(var1);
         var4.append(" has already been added.");
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   public fun clear() {
      this.identifiers.clear();
      this.weakInstances.clear();
      this.strongInstances.clear();
      this.weakReferencesToIdentifiers.clear();
   }

   public fun containsInstance(instance: Any?): Boolean {
      this.logWarningIfFinalizationListenerHasStopped();
      return this.identifiers.containsKey(var1);
   }

   public fun getIdentifierForStrongReference(instance: Any?): Long? {
      this.logWarningIfFinalizationListenerHasStopped();
      val var3: java.lang.Long = this.identifiers.get(var1);
      if (var3 != null) {
         val var2: java.util.Map = this.strongInstances;
         var2.put(var3, var1);
      }

      return var3;
   }

   public fun <T> getInstance(identifier: Long): T? {
      this.logWarningIfFinalizationListenerHasStopped();
      val var3: WeakReference = this.weakInstances.get(var1);
      val var4: Any;
      if (var3 != null) {
         var4 = var3.get();
      } else {
         var4 = null;
      }

      return (T)var4;
   }

   public fun hasFinalizationListenerStopped(): Boolean {
      return this.hasFinalizationListenerStopped;
   }

   public fun <T> remove(identifier: Long): T? {
      this.logWarningIfFinalizationListenerHasStopped();
      val var3: Any = this.getInstance(var1);
      if (var3 is WebViewProxyApi.WebViewPlatformView) {
         (var3 as WebViewProxyApi.WebViewPlatformView).destroy();
      }

      return (T)this.strongInstances.remove(var1);
   }

   public fun stopFinalizationListener() {
      this.handler.removeCallbacks(new AndroidWebkitLibraryPigeonInstanceManager$$ExternalSyntheticLambda1(this));
      this.hasFinalizationListenerStopped = true;
   }

   public companion object {
      private const val minHostCreatedIdentifier: Long
      private const val tag: String

      public fun create(finalizationListener: io.flutter.plugins.webviewflutter.AndroidWebkitLibraryPigeonInstanceManager.PigeonFinalizationListener): AndroidWebkitLibraryPigeonInstanceManager {
         return new AndroidWebkitLibraryPigeonInstanceManager(var1);
      }
   }

   public interface PigeonFinalizationListener {
      public abstract fun onFinalize(identifier: Long) {
      }
   }
}
