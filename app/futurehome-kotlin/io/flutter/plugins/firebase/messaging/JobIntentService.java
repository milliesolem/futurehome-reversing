package io.flutter.plugins.firebase.messaging;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.app.job.JobInfo.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Build.VERSION;
import android.os.PowerManager.WakeLock;
import dev.fluttercommunity.plus.share.Share$$ExternalSyntheticApiModelOutline0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

abstract class JobIntentService extends Service {
   static final boolean DEBUG = false;
   static final String TAG = "JobIntentService";
   static final HashMap<JobIntentService.ComponentNameWithWakeful, JobIntentService.WorkEnqueuer> sClassWorkEnqueuer = new HashMap<>();
   static final Object sLock = new Object();
   final ArrayList<JobIntentService.CompatWorkItem> mCompatQueue;
   JobIntentService.WorkEnqueuer mCompatWorkEnqueuer;
   JobIntentService.CommandProcessor mCurProcessor;
   boolean mDestroyed;
   JobIntentService.CompatJobEngine mJobImpl;
   boolean mStopped = false;

   public JobIntentService() {
      this.mDestroyed = false;
      this.mCompatQueue = new ArrayList<>();
   }

   public static void enqueueWork(Context param0, ComponentName param1, int param2, Intent param3, boolean param4) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 3
      // 01: ifnull 46
      // 04: getstatic io/flutter/plugins/firebase/messaging/JobIntentService.sLock Ljava/lang/Object;
      // 07: astore 5
      // 09: aload 5
      // 0b: monitorenter
      // 0c: aload 0
      // 0d: aload 1
      // 0e: bipush 1
      // 0f: iload 2
      // 10: iload 4
      // 12: invokestatic io/flutter/plugins/firebase/messaging/JobIntentService.getWorkEnqueuer (Landroid/content/Context;Landroid/content/ComponentName;ZIZ)Lio/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer;
      // 15: astore 6
      // 17: aload 6
      // 19: iload 2
      // 1a: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer.ensureJobId (I)V
      // 1d: aload 6
      // 1f: aload 3
      // 20: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer.enqueueWork (Landroid/content/Intent;)V
      // 23: goto 39
      // 26: astore 6
      // 28: iload 4
      // 2a: ifeq 3d
      // 2d: aload 0
      // 2e: aload 1
      // 2f: bipush 1
      // 30: iload 2
      // 31: bipush 0
      // 32: invokestatic io/flutter/plugins/firebase/messaging/JobIntentService.getWorkEnqueuer (Landroid/content/Context;Landroid/content/ComponentName;ZIZ)Lio/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer;
      // 35: aload 3
      // 36: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer.enqueueWork (Landroid/content/Intent;)V
      // 39: aload 5
      // 3b: monitorexit
      // 3c: return
      // 3d: aload 6
      // 3f: athrow
      // 40: astore 0
      // 41: aload 5
      // 43: monitorexit
      // 44: aload 0
      // 45: athrow
      // 46: new java/lang/IllegalArgumentException
      // 49: dup
      // 4a: ldc "work must not be null"
      // 4c: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 4f: athrow
   }

   public static void enqueueWork(Context var0, Class var1, int var2, Intent var3, boolean var4) {
      enqueueWork(var0, new ComponentName(var0, var1), var2, var3, var4);
   }

   static JobIntentService.WorkEnqueuer getWorkEnqueuer(Context var0, ComponentName var1, boolean var2, int var3, boolean var4) {
      JobIntentService.ComponentNameWithWakeful var8 = new JobIntentService.ComponentNameWithWakeful(var1, var4);
      HashMap var7 = sClassWorkEnqueuer;
      JobIntentService.WorkEnqueuer var6 = (JobIntentService.WorkEnqueuer)var7.get(var8);
      Object var5 = var6;
      if (var6 == null) {
         Object var9;
         if (VERSION.SDK_INT >= 26 && !var4) {
            if (!var2) {
               throw new IllegalArgumentException("Can't be here without a job id");
            }

            var9 = new JobIntentService.JobWorkEnqueuer(var0, var1, var3);
         } else {
            var9 = new JobIntentService.CompatWorkEnqueuer(var0, var1);
         }

         var7.put(var8, var9);
         var5 = var9;
      }

      return (JobIntentService.WorkEnqueuer)var5;
   }

   JobIntentService.GenericWorkItem dequeueWork() {
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
      // 01: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mJobImpl Lio/flutter/plugins/firebase/messaging/JobIntentService$CompatJobEngine;
      // 04: astore 1
      // 05: aload 1
      // 06: ifnull 16
      // 09: aload 1
      // 0a: invokeinterface io/flutter/plugins/firebase/messaging/JobIntentService$CompatJobEngine.dequeueWork ()Lio/flutter/plugins/firebase/messaging/JobIntentService$GenericWorkItem; 1
      // 0f: astore 1
      // 10: aload 1
      // 11: ifnull 16
      // 14: aload 1
      // 15: areturn
      // 16: aload 0
      // 17: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatQueue Ljava/util/ArrayList;
      // 1a: astore 1
      // 1b: aload 1
      // 1c: monitorenter
      // 1d: aload 0
      // 1e: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatQueue Ljava/util/ArrayList;
      // 21: invokevirtual java/util/ArrayList.size ()I
      // 24: ifle 37
      // 27: aload 0
      // 28: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatQueue Ljava/util/ArrayList;
      // 2b: bipush 0
      // 2c: invokevirtual java/util/ArrayList.remove (I)Ljava/lang/Object;
      // 2f: checkcast io/flutter/plugins/firebase/messaging/JobIntentService$GenericWorkItem
      // 32: astore 2
      // 33: aload 1
      // 34: monitorexit
      // 35: aload 2
      // 36: areturn
      // 37: aload 1
      // 38: monitorexit
      // 39: aconst_null
      // 3a: areturn
      // 3b: astore 2
      // 3c: aload 1
      // 3d: monitorexit
      // 3e: aload 2
      // 3f: athrow
   }

   boolean doStopCurrentWork() {
      JobIntentService.CommandProcessor var1 = this.mCurProcessor;
      if (var1 != null) {
         var1.cancel();
      }

      this.mStopped = true;
      return this.onStopCurrentWork();
   }

   void ensureProcessorRunningLocked(boolean var1) {
      if (this.mCurProcessor == null) {
         this.mCurProcessor = new JobIntentService.CommandProcessor(this);
         JobIntentService.WorkEnqueuer var2 = this.mCompatWorkEnqueuer;
         if (var2 != null && var1) {
            var2.serviceProcessingStarted();
         }

         this.mCurProcessor.execute();
      }
   }

   public boolean isStopped() {
      return this.mStopped;
   }

   public IBinder onBind(Intent var1) {
      JobIntentService.CompatJobEngine var2 = this.mJobImpl;
      return var2 != null ? var2.compatGetBinder() : null;
   }

   public void onCreate() {
      super.onCreate();
      if (VERSION.SDK_INT >= 26) {
         this.mJobImpl = new JobIntentService.JobServiceEngineImpl(this);
         this.mCompatWorkEnqueuer = null;
      }

      this.mCompatWorkEnqueuer = getWorkEnqueuer(this, new ComponentName(this, this.getClass()), false, 0, true);
   }

   public void onDestroy() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokespecial android/app/Service.onDestroy ()V
      // 04: aload 0
      // 05: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService.doStopCurrentWork ()Z
      // 08: pop
      // 09: aload 0
      // 0a: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatQueue Ljava/util/ArrayList;
      // 0d: astore 1
      // 0e: aload 1
      // 0f: monitorenter
      // 10: aload 0
      // 11: bipush 1
      // 12: putfield io/flutter/plugins/firebase/messaging/JobIntentService.mDestroyed Z
      // 15: aload 0
      // 16: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatWorkEnqueuer Lio/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer;
      // 19: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer.serviceProcessingFinished ()V
      // 1c: aload 1
      // 1d: monitorexit
      // 1e: return
      // 1f: astore 2
      // 20: aload 1
      // 21: monitorexit
      // 22: aload 2
      // 23: athrow
   }

   protected abstract void onHandleWork(Intent var1);

   public int onStartCommand(Intent param1, int param2, int param3) {
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
      // 01: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatWorkEnqueuer Lio/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer;
      // 04: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer.serviceStartReceived ()V
      // 07: aload 0
      // 08: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatQueue Ljava/util/ArrayList;
      // 0b: astore 4
      // 0d: aload 4
      // 0f: monitorenter
      // 10: aload 0
      // 11: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatQueue Ljava/util/ArrayList;
      // 14: astore 6
      // 16: new io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkItem
      // 19: astore 5
      // 1b: aload 1
      // 1c: ifnull 22
      // 1f: goto 2a
      // 22: new android/content/Intent
      // 25: dup
      // 26: invokespecial android/content/Intent.<init> ()V
      // 29: astore 1
      // 2a: aload 5
      // 2c: aload 0
      // 2d: aload 1
      // 2e: iload 3
      // 2f: invokespecial io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkItem.<init> (Lio/flutter/plugins/firebase/messaging/JobIntentService;Landroid/content/Intent;I)V
      // 32: aload 6
      // 34: aload 5
      // 36: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 39: pop
      // 3a: aload 0
      // 3b: bipush 1
      // 3c: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService.ensureProcessorRunningLocked (Z)V
      // 3f: aload 4
      // 41: monitorexit
      // 42: bipush 3
      // 43: ireturn
      // 44: astore 1
      // 45: aload 4
      // 47: monitorexit
      // 48: aload 1
      // 49: athrow
   }

   public boolean onStopCurrentWork() {
      return true;
   }

   void processorFinished() {
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
      // 01: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatQueue Ljava/util/ArrayList;
      // 04: astore 1
      // 05: aload 1
      // 06: ifnull 40
      // 09: aload 1
      // 0a: monitorenter
      // 0b: aload 0
      // 0c: aconst_null
      // 0d: putfield io/flutter/plugins/firebase/messaging/JobIntentService.mCurProcessor Lio/flutter/plugins/firebase/messaging/JobIntentService$CommandProcessor;
      // 10: aload 0
      // 11: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatQueue Ljava/util/ArrayList;
      // 14: astore 2
      // 15: aload 2
      // 16: ifnull 28
      // 19: aload 2
      // 1a: invokevirtual java/util/ArrayList.size ()I
      // 1d: ifle 28
      // 20: aload 0
      // 21: bipush 0
      // 22: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService.ensureProcessorRunningLocked (Z)V
      // 25: goto 36
      // 28: aload 0
      // 29: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mDestroyed Z
      // 2c: ifne 36
      // 2f: aload 0
      // 30: getfield io/flutter/plugins/firebase/messaging/JobIntentService.mCompatWorkEnqueuer Lio/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer;
      // 33: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService$WorkEnqueuer.serviceProcessingFinished ()V
      // 36: aload 1
      // 37: monitorexit
      // 38: goto 40
      // 3b: astore 2
      // 3c: aload 1
      // 3d: monitorexit
      // 3e: aload 2
      // 3f: athrow
      // 40: return
   }

   final class CommandProcessor {
      private final Executor executor;
      private final Handler handler;
      final JobIntentService this$0;

      CommandProcessor(JobIntentService var1) {
         this.this$0 = var1;
         this.executor = Executors.newSingleThreadExecutor();
         this.handler = new Handler(Looper.getMainLooper());
      }

      public void cancel() {
         this.this$0.processorFinished();
      }

      public void execute() {
         this.executor.execute(new Runnable(this) {
            final JobIntentService.CommandProcessor this$1;

            {
               this.this$1 = var1;
            }

            @Override
            public void run() {
               while (true) {
                  JobIntentService.GenericWorkItem var1 = this.this$1.this$0.dequeueWork();
                  if (var1 == null) {
                     this.this$1.handler.post(new Runnable(this) {
                        final <unrepresentable> this$2;

                        {
                           this.this$2 = var1;
                        }

                        @Override
                        public void run() {
                           this.this$2.this$1.this$0.processorFinished();
                        }
                     });
                     return;
                  }

                  this.this$1.this$0.onHandleWork(var1.getIntent());
                  var1.complete();
               }
            }
         });
      }
   }

   interface CompatJobEngine {
      IBinder compatGetBinder();

      JobIntentService.GenericWorkItem dequeueWork();
   }

   static final class CompatWorkEnqueuer extends JobIntentService.WorkEnqueuer {
      private final Context mContext;
      private final WakeLock mLaunchWakeLock;
      boolean mLaunchingService;
      private final WakeLock mRunWakeLock;
      boolean mServiceProcessing;

      CompatWorkEnqueuer(Context var1, ComponentName var2) {
         super(var2);
         this.mContext = var1.getApplicationContext();
         PowerManager var4 = (PowerManager)var1.getSystemService("power");
         StringBuilder var3 = new StringBuilder();
         var3.append(var2.getClassName());
         var3.append(":launch");
         WakeLock var6 = var4.newWakeLock(1, var3.toString());
         this.mLaunchWakeLock = var6;
         var6.setReferenceCounted(false);
         var3 = new StringBuilder();
         var3.append(var2.getClassName());
         var3.append(":run");
         WakeLock var5 = var4.newWakeLock(1, var3.toString());
         this.mRunWakeLock = var5;
         var5.setReferenceCounted(false);
      }

      @Override
      void enqueueWork(Intent param1) {
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
         // 00: new android/content/Intent
         // 03: dup
         // 04: aload 1
         // 05: invokespecial android/content/Intent.<init> (Landroid/content/Intent;)V
         // 08: astore 1
         // 09: aload 1
         // 0a: aload 0
         // 0b: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mComponentName Landroid/content/ComponentName;
         // 0e: invokevirtual android/content/Intent.setComponent (Landroid/content/ComponentName;)Landroid/content/Intent;
         // 11: pop
         // 12: aload 0
         // 13: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mContext Landroid/content/Context;
         // 16: aload 1
         // 17: invokevirtual android/content/Context.startService (Landroid/content/Intent;)Landroid/content/ComponentName;
         // 1a: ifnull 46
         // 1d: aload 0
         // 1e: monitorenter
         // 1f: aload 0
         // 20: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mLaunchingService Z
         // 23: ifne 3c
         // 26: aload 0
         // 27: bipush 1
         // 28: putfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mLaunchingService Z
         // 2b: aload 0
         // 2c: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mServiceProcessing Z
         // 2f: ifne 3c
         // 32: aload 0
         // 33: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mLaunchWakeLock Landroid/os/PowerManager$WakeLock;
         // 36: ldc2_w 60000
         // 39: invokevirtual android/os/PowerManager$WakeLock.acquire (J)V
         // 3c: aload 0
         // 3d: monitorexit
         // 3e: goto 46
         // 41: astore 1
         // 42: aload 0
         // 43: monitorexit
         // 44: aload 1
         // 45: athrow
         // 46: return
      }

      @Override
      public void serviceProcessingFinished() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.util.collections.fixed.FastFixedSet.contains(Object)" because "predset" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.lambda$removeErroneousNodes$1(FastExtendedPostdominanceHelper.java:231)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.iterateReachability(FastExtendedPostdominanceHelper.java:373)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.removeErroneousNodes(FastExtendedPostdominanceHelper.java:207)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.getExtendedPostdominators(FastExtendedPostdominanceHelper.java:63)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.findGeneralStatement(DomHelper.java:537)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:472)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:379)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:208)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mServiceProcessing Z
         // 06: ifeq 26
         // 09: aload 0
         // 0a: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mLaunchingService Z
         // 0d: ifeq 1a
         // 10: aload 0
         // 11: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mLaunchWakeLock Landroid/os/PowerManager$WakeLock;
         // 14: ldc2_w 60000
         // 17: invokevirtual android/os/PowerManager$WakeLock.acquire (J)V
         // 1a: aload 0
         // 1b: bipush 0
         // 1c: putfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mServiceProcessing Z
         // 1f: aload 0
         // 20: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mRunWakeLock Landroid/os/PowerManager$WakeLock;
         // 23: invokevirtual android/os/PowerManager$WakeLock.release ()V
         // 26: aload 0
         // 27: monitorexit
         // 28: return
         // 29: astore 1
         // 2a: aload 0
         // 2b: monitorexit
         // 2c: aload 1
         // 2d: athrow
      }

      @Override
      public void serviceProcessingStarted() {
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
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mServiceProcessing Z
         // 06: ifne 1f
         // 09: aload 0
         // 0a: bipush 1
         // 0b: putfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mServiceProcessing Z
         // 0e: aload 0
         // 0f: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mRunWakeLock Landroid/os/PowerManager$WakeLock;
         // 12: ldc2_w 600000
         // 15: invokevirtual android/os/PowerManager$WakeLock.acquire (J)V
         // 18: aload 0
         // 19: getfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mLaunchWakeLock Landroid/os/PowerManager$WakeLock;
         // 1c: invokevirtual android/os/PowerManager$WakeLock.release ()V
         // 1f: aload 0
         // 20: monitorexit
         // 21: return
         // 22: astore 1
         // 23: aload 0
         // 24: monitorexit
         // 25: aload 1
         // 26: athrow
      }

      @Override
      public void serviceStartReceived() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 0: aload 0
         // 1: monitorenter
         // 2: aload 0
         // 3: bipush 0
         // 4: putfield io/flutter/plugins/firebase/messaging/JobIntentService$CompatWorkEnqueuer.mLaunchingService Z
         // 7: aload 0
         // 8: monitorexit
         // 9: return
         // a: astore 1
         // b: aload 0
         // c: monitorexit
         // d: aload 1
         // e: athrow
      }
   }

   final class CompatWorkItem implements JobIntentService.GenericWorkItem {
      final Intent mIntent;
      final int mStartId;
      final JobIntentService this$0;

      CompatWorkItem(JobIntentService var1, Intent var2, int var3) {
         this.this$0 = var1;
         this.mIntent = var2;
         this.mStartId = var3;
      }

      @Override
      public void complete() {
         this.this$0.stopSelf(this.mStartId);
      }

      @Override
      public Intent getIntent() {
         return this.mIntent;
      }
   }

   private static class ComponentNameWithWakeful {
      private ComponentName componentName;
      private boolean useWakefulService;

      ComponentNameWithWakeful(ComponentName var1, boolean var2) {
         this.componentName = var1;
         this.useWakefulService = var2;
      }
   }

   interface GenericWorkItem {
      void complete();

      Intent getIntent();
   }

   static final class JobServiceEngineImpl extends JobServiceEngine implements JobIntentService.CompatJobEngine {
      static final boolean DEBUG = false;
      static final String TAG = "JobServiceEngineImpl";
      final Object mLock = new Object();
      JobParameters mParams;
      final JobIntentService mService;

      JobServiceEngineImpl(JobIntentService var1) {
         super(var1);
         this.mService = var1;
      }

      @Override
      public IBinder compatGetBinder() {
         return JobIntentService$JobServiceEngineImpl$$ExternalSyntheticApiModelOutline2.m(this);
      }

      @Override
      public JobIntentService.GenericWorkItem dequeueWork() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.RuntimeException: parsing failure!
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl.mLock Ljava/lang/Object;
         // 04: astore 1
         // 05: aload 1
         // 06: monitorenter
         // 07: aload 0
         // 08: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl.mParams Landroid/app/job/JobParameters;
         // 0b: astore 2
         // 0c: aload 2
         // 0d: ifnonnull 14
         // 10: aload 1
         // 11: monitorexit
         // 12: aconst_null
         // 13: areturn
         // 14: aload 2
         // 15: invokestatic dev/fluttercommunity/plus/share/Share$$ExternalSyntheticApiModelOutline0.m (Landroid/app/job/JobParameters;)Landroid/app/job/JobWorkItem;
         // 18: astore 2
         // 19: aload 1
         // 1a: monitorexit
         // 1b: aload 2
         // 1c: ifnull 37
         // 1f: aload 2
         // 20: invokestatic dev/fluttercommunity/plus/share/Share$$ExternalSyntheticApiModelOutline0.m (Landroid/app/job/JobWorkItem;)Landroid/content/Intent;
         // 23: aload 0
         // 24: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl.mService Lio/flutter/plugins/firebase/messaging/JobIntentService;
         // 27: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService.getClassLoader ()Ljava/lang/ClassLoader;
         // 2a: invokevirtual android/content/Intent.setExtrasClassLoader (Ljava/lang/ClassLoader;)V
         // 2d: new io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl$WrapperWorkItem
         // 30: dup
         // 31: aload 0
         // 32: aload 2
         // 33: invokespecial io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl$WrapperWorkItem.<init> (Lio/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl;Landroid/app/job/JobWorkItem;)V
         // 36: areturn
         // 37: aconst_null
         // 38: areturn
         // 39: astore 2
         // 3a: ldc "JobServiceEngineImpl"
         // 3c: ldc "Failed to run mParams.dequeueWork()!"
         // 3e: aload 2
         // 3f: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
         // 42: pop
         // 43: aload 1
         // 44: monitorexit
         // 45: aconst_null
         // 46: areturn
         // 47: astore 2
         // 48: aload 1
         // 49: monitorexit
         // 4a: aload 2
         // 4b: athrow
      }

      public boolean onStartJob(JobParameters var1) {
         this.mParams = var1;
         this.mService.ensureProcessorRunningLocked(false);
         return true;
      }

      public boolean onStopJob(JobParameters param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl.mService Lio/flutter/plugins/firebase/messaging/JobIntentService;
         // 04: invokevirtual io/flutter/plugins/firebase/messaging/JobIntentService.doStopCurrentWork ()Z
         // 07: istore 2
         // 08: aload 0
         // 09: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl.mLock Ljava/lang/Object;
         // 0c: astore 3
         // 0d: aload 3
         // 0e: monitorenter
         // 0f: aload 0
         // 10: aconst_null
         // 11: putfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl.mParams Landroid/app/job/JobParameters;
         // 14: aload 3
         // 15: monitorexit
         // 16: iload 2
         // 17: ireturn
         // 18: astore 1
         // 19: aload 3
         // 1a: monitorexit
         // 1b: aload 1
         // 1c: athrow
      }

      final class WrapperWorkItem implements JobIntentService.GenericWorkItem {
         final JobWorkItem mJobWork;
         final JobIntentService.JobServiceEngineImpl this$0;

         WrapperWorkItem(JobIntentService.JobServiceEngineImpl var1, JobWorkItem var2) {
            this.this$0 = var1;
            this.mJobWork = var2;
         }

         @Override
         public void complete() {
            // $VF: Couldn't be decompiled
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            // java.lang.RuntimeException: parsing failure!
            //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
            //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
            //
            // Bytecode:
            // 00: aload 0
            // 01: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl$WrapperWorkItem.this$0 Lio/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl;
            // 04: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl.mLock Ljava/lang/Object;
            // 07: astore 1
            // 08: aload 1
            // 09: monitorenter
            // 0a: aload 0
            // 0b: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl$WrapperWorkItem.this$0 Lio/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl;
            // 0e: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl.mParams Landroid/app/job/JobParameters;
            // 11: astore 2
            // 12: aload 2
            // 13: ifnull 3e
            // 16: aload 0
            // 17: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl$WrapperWorkItem.this$0 Lio/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl;
            // 1a: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl.mParams Landroid/app/job/JobParameters;
            // 1d: aload 0
            // 1e: getfield io/flutter/plugins/firebase/messaging/JobIntentService$JobServiceEngineImpl$WrapperWorkItem.mJobWork Landroid/app/job/JobWorkItem;
            // 21: invokestatic dev/fluttercommunity/plus/share/Share$$ExternalSyntheticApiModelOutline0.m (Landroid/app/job/JobParameters;Landroid/app/job/JobWorkItem;)V
            // 24: goto 3e
            // 27: astore 2
            // 28: ldc "JobServiceEngineImpl"
            // 2a: ldc "IllegalArgumentException: Failed to run mParams.completeWork(mJobWork)!"
            // 2c: aload 2
            // 2d: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
            // 30: pop
            // 31: goto 3e
            // 34: astore 2
            // 35: ldc "JobServiceEngineImpl"
            // 37: ldc "SecurityException: Failed to run mParams.completeWork(mJobWork)!"
            // 39: aload 2
            // 3a: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
            // 3d: pop
            // 3e: aload 1
            // 3f: monitorexit
            // 40: return
            // 41: astore 2
            // 42: aload 1
            // 43: monitorexit
            // 44: aload 2
            // 45: athrow
         }

         @Override
         public Intent getIntent() {
            return Share$$ExternalSyntheticApiModelOutline0.m(this.mJobWork);
         }
      }
   }

   static final class JobWorkEnqueuer extends JobIntentService.WorkEnqueuer {
      private final JobInfo mJobInfo;
      private final JobScheduler mJobScheduler;

      JobWorkEnqueuer(Context var1, ComponentName var2, int var3) {
         super(var2);
         this.ensureJobId(var3);
         this.mJobInfo = new Builder(var3, this.mComponentName).setOverrideDeadline(0L).build();
         this.mJobScheduler = (JobScheduler)var1.getApplicationContext().getSystemService("jobscheduler");
      }

      @Override
      void enqueueWork(Intent var1) {
         Share$$ExternalSyntheticApiModelOutline0.m(this.mJobScheduler, this.mJobInfo, Share$$ExternalSyntheticApiModelOutline0.m(var1));
      }
   }

   abstract static class WorkEnqueuer {
      final ComponentName mComponentName;
      boolean mHasJobId;
      int mJobId;

      WorkEnqueuer(ComponentName var1) {
         this.mComponentName = var1;
      }

      abstract void enqueueWork(Intent var1);

      void ensureJobId(int var1) {
         if (!this.mHasJobId) {
            this.mHasJobId = true;
            this.mJobId = var1;
         } else if (this.mJobId != var1) {
            StringBuilder var2 = new StringBuilder("Given job ID ");
            var2.append(var1);
            var2.append(" is different than previous ");
            var2.append(this.mJobId);
            throw new IllegalArgumentException(var2.toString());
         }
      }

      public void serviceProcessingFinished() {
      }

      public void serviceProcessingStarted() {
      }

      public void serviceStartReceived() {
      }
   }
}
