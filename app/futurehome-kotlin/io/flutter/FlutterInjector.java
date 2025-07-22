package io.flutter;

import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.deferredcomponents.DeferredComponentManager;
import io.flutter.embedding.engine.loader.FlutterLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class FlutterInjector {
   private static boolean accessed;
   private static FlutterInjector instance;
   private DeferredComponentManager deferredComponentManager;
   private ExecutorService executorService;
   private FlutterJNI.Factory flutterJniFactory;
   private FlutterLoader flutterLoader;

   private FlutterInjector(FlutterLoader var1, DeferredComponentManager var2, FlutterJNI.Factory var3, ExecutorService var4) {
      this.flutterLoader = var1;
      this.deferredComponentManager = var2;
      this.flutterJniFactory = var3;
      this.executorService = var4;
   }

   public static FlutterInjector instance() {
      accessed = true;
      if (instance == null) {
         instance = new FlutterInjector.Builder().build();
      }

      return instance;
   }

   public static void reset() {
      accessed = false;
      instance = null;
   }

   public static void setInstance(FlutterInjector var0) {
      if (!accessed) {
         instance = var0;
      } else {
         throw new IllegalStateException(
            "Cannot change the FlutterInjector instance once it's been read. If you're trying to dependency inject, be sure to do so at the beginning of the program"
         );
      }
   }

   public DeferredComponentManager deferredComponentManager() {
      return this.deferredComponentManager;
   }

   public ExecutorService executorService() {
      return this.executorService;
   }

   public FlutterLoader flutterLoader() {
      return this.flutterLoader;
   }

   public FlutterJNI.Factory getFlutterJNIFactory() {
      return this.flutterJniFactory;
   }

   public static final class Builder {
      private DeferredComponentManager deferredComponentManager;
      private ExecutorService executorService;
      private FlutterJNI.Factory flutterJniFactory;
      private FlutterLoader flutterLoader;

      private void fillDefaults() {
         if (this.flutterJniFactory == null) {
            this.flutterJniFactory = new FlutterJNI.Factory();
         }

         if (this.executorService == null) {
            this.executorService = Executors.newCachedThreadPool(new FlutterInjector.Builder.NamedThreadFactory(this));
         }

         if (this.flutterLoader == null) {
            this.flutterLoader = new FlutterLoader(this.flutterJniFactory.provideFlutterJNI(), this.executorService);
         }
      }

      public FlutterInjector build() {
         this.fillDefaults();
         return new FlutterInjector(this.flutterLoader, this.deferredComponentManager, this.flutterJniFactory, this.executorService);
      }

      public FlutterInjector.Builder setDeferredComponentManager(DeferredComponentManager var1) {
         this.deferredComponentManager = var1;
         return this;
      }

      public FlutterInjector.Builder setExecutorService(ExecutorService var1) {
         this.executorService = var1;
         return this;
      }

      public FlutterInjector.Builder setFlutterJNIFactory(FlutterJNI.Factory var1) {
         this.flutterJniFactory = var1;
         return this;
      }

      public FlutterInjector.Builder setFlutterLoader(FlutterLoader var1) {
         this.flutterLoader = var1;
         return this;
      }

      private class NamedThreadFactory implements ThreadFactory {
         final FlutterInjector.Builder this$0;
         private int threadId;

         private NamedThreadFactory(FlutterInjector.Builder var1) {
            this.this$0 = var1;
            this.threadId = 0;
         }

         @Override
         public Thread newThread(Runnable var1) {
            Thread var3 = new Thread(var1);
            StringBuilder var4 = new StringBuilder("flutter-worker-");
            int var2 = this.threadId++;
            var4.append(var2);
            var3.setName(var4.toString());
            return var3;
         }
      }
   }
}
