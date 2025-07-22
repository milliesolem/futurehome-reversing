package io.sentry.android.replay.capture

import android.view.MotionEvent
import io.sentry.Breadcrumb
import io.sentry.DateUtils
import io.sentry.IHub
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.SentryReplayEvent
import io.sentry.SentryReplayEvent.ReplayType
import io.sentry.android.replay.ReplayCache
import io.sentry.android.replay.ScreenshotRecorderConfig
import io.sentry.android.replay.capture.CaptureStrategy.ReplaySegment
import io.sentry.android.replay.gestures.ReplayGestureConverter
import io.sentry.android.replay.util.ExecutorsKt
import io.sentry.protocol.SentryId
import io.sentry.rrweb.RRWebEvent
import io.sentry.transport.ICurrentDateProvider
import java.io.File
import java.util.Date
import java.util.Deque
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function3
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal abstract class BaseCaptureStrategy : CaptureStrategy {
   protected final var cache: ReplayCache?
      internal set

   protected final val currentEvents: Deque<RRWebEvent>

   public open var currentReplayId: SentryId
      public open get() {
         return this.currentReplayId$delegate.getValue(this, $$delegatedProperties[3]) as SentryId;
      }

      public open set(<set-?>) {
         this.currentReplayId$delegate.setValue(this, $$delegatedProperties[3], var1);
      }


   public open var currentSegment: Int
      public open get() {
         return (this.currentSegment$delegate.getValue(this, $$delegatedProperties[4]) as java.lang.Number).intValue();
      }

      public open set(<set-?>) {
         this.currentSegment$delegate.setValue(this, $$delegatedProperties[4], var1);
      }


   private final val dateProvider: ICurrentDateProvider
   private final val gestureConverter: ReplayGestureConverter
   private final val hub: IHub?
   protected final val isTerminating: AtomicBoolean
   private final val options: SentryOptions

   private final val persistingExecutor: ScheduledExecutorService
      private final get() {
         val var1: Any = this.persistingExecutor$delegate.getValue();
         return var1 as ScheduledExecutorService;
      }


   protected final var recorderConfig: ScreenshotRecorderConfig
      protected final get() {
         return this.recorderConfig$delegate.getValue(this, $$delegatedProperties[0]) as ScreenshotRecorderConfig;
      }

      protected final set(<set-?>) {
         this.recorderConfig$delegate.setValue(this, $$delegatedProperties[0], var1);
      }


   public open val replayCacheDir: File?
      public open get() {
         val var2: File;
         if (this.cache != null) {
            var2 = this.cache.getReplayCacheDir$sentry_android_replay_release();
         } else {
            var2 = null;
         }

         return var2;
      }


   private final val replayCacheProvider: ((SentryId) -> ReplayCache)?
   protected final val replayExecutor: ScheduledExecutorService
   protected final val replayStartTimestamp: AtomicLong

   public open var replayType: ReplayType
      public open get() {
         return this.replayType$delegate.getValue(this, $$delegatedProperties[5]) as SentryReplayEvent.ReplayType;
      }

      public open set(<set-?>) {
         this.replayType$delegate.setValue(this, $$delegatedProperties[5], var1);
      }


   protected final var screenAtStart: String?
      protected final get() {
         return this.screenAtStart$delegate.getValue(this, $$delegatedProperties[2]) as java.lang.String;
      }

      protected final set(<set-?>) {
         this.screenAtStart$delegate.setValue(this, $$delegatedProperties[2], var1);
      }


   public open var segmentTimestamp: Date?
      public open get() {
         return this.segmentTimestamp$delegate.getValue(this, $$delegatedProperties[1]) as Date;
      }

      public open set(<set-?>) {
         this.segmentTimestamp$delegate.setValue(this, $$delegatedProperties[1], var1);
      }


   open fun BaseCaptureStrategy(var1: SentryOptions, var2: IHub, var3: ICurrentDateProvider, var4: ScheduledExecutorService, var5: (SentryId?) -> ReplayCache) {
      this.options = var1;
      this.hub = var2;
      this.dateProvider = var3;
      this.replayExecutor = var4;
      this.replayCacheProvider = var5;
      this.persistingExecutor$delegate = LazyKt.lazy(<unrepresentable>.INSTANCE);
      this.gestureConverter = new ReplayGestureConverter(var3);
      this.isTerminating = new AtomicBoolean(false);
      this.recorderConfig$delegate = new ReadWriteProperty<Object, ScreenshotRecorderConfig>(null, this, "", this) {
         final java.lang.String $propertyName;
         final BaseCaptureStrategy this$0;
         final BaseCaptureStrategy this$0$inline_fun;
         private final AtomicReference<ScreenshotRecorderConfig> value;

         {
            this.this$0$inline_fun = var2;
            this.$propertyName = var3;
            this.this$0 = var4;
            this.value = new AtomicReference<>((ScreenshotRecorderConfig)var1);
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         private final void runInBackground(Function0<Unit> var1) {
            if (BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun).getMainThreadChecker().isMainThread()) {
               ExecutorsKt.submitSafely(
                  BaseCaptureStrategy.access$getPersistingExecutor(this.this$0$inline_fun),
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun),
                  "CaptureStrategy.runInBackground",
                  new Runnable(var1) {
                     final Function0 $task;

                     {
                        this.$task = var1;
                     }

                     @Override
                     public final void run() {
                        this.$task.invoke();
                     }
                  }
               );
            } else {
               try {
                  var1.invoke();
               } catch (var2: java.lang.Throwable) {
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun)
                     .getLogger()
                     .log(SentryLevel.ERROR, "Failed to execute task CaptureStrategy.runInBackground", var2);
                  return;
               }
            }
         }

         @Override
         public ScreenshotRecorderConfig getValue(Object var1, KProperty<?> var2) {
            return this.value.get();
         }

         @Override
         public void setValue(Object var1, KProperty<?> var2, ScreenshotRecorderConfig var3) {
            var1 = this.value.getAndSet((ScreenshotRecorderConfig)var3);
            if (!(var1 == var3)) {
               this.runInBackground((new Function0<Unit>(this.$propertyName, var1, var3, this.this$0) {
                  final Object $oldValue;
                  final java.lang.String $propertyName;
                  final Object $value;
                  final BaseCaptureStrategy this$0;

                  {
                     super(0);
                     this.$propertyName = var1;
                     this.$oldValue = var2x;
                     this.$value = var3x;
                     this.this$0 = var4;
                  }

                  public final void invoke() {
                     val var1x: ScreenshotRecorderConfig = this.$value as ScreenshotRecorderConfig;
                     val var3x: ScreenshotRecorderConfig = this.$oldValue as ScreenshotRecorderConfig;
                     if (var1x != null) {
                        val var4: ReplayCache = this.this$0.getCache();
                        if (var4 != null) {
                           var4.persistSegmentValues("config.height", java.lang.String.valueOf(var1x.getRecordingHeight()));
                        }

                        val var5: ReplayCache = this.this$0.getCache();
                        if (var5 != null) {
                           var5.persistSegmentValues("config.width", java.lang.String.valueOf(var1x.getRecordingWidth()));
                        }

                        val var6: ReplayCache = this.this$0.getCache();
                        if (var6 != null) {
                           var6.persistSegmentValues("config.frame-rate", java.lang.String.valueOf(var1x.getFrameRate()));
                        }

                        val var7: ReplayCache = this.this$0.getCache();
                        if (var7 != null) {
                           var7.persistSegmentValues("config.bit-rate", java.lang.String.valueOf(var1x.getBitRate()));
                        }
                     }
                  }
               }) as () -> Unit);
            }
         }
      };
      this.segmentTimestamp$delegate = new ReadWriteProperty<Object, Date>(null, this, "segment.timestamp", this) {
         final java.lang.String $propertyName;
         final BaseCaptureStrategy this$0;
         final BaseCaptureStrategy this$0$inline_fun;
         private final AtomicReference<Date> value;

         {
            this.this$0$inline_fun = var2;
            this.$propertyName = var3;
            this.this$0 = var4;
            this.value = new AtomicReference<>((Date)var1);
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         private final void runInBackground(Function0<Unit> var1) {
            if (BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun).getMainThreadChecker().isMainThread()) {
               ExecutorsKt.submitSafely(
                  BaseCaptureStrategy.access$getPersistingExecutor(this.this$0$inline_fun),
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun),
                  "CaptureStrategy.runInBackground",
                  new Runnable(var1) {
                     final Function0 $task;

                     {
                        this.$task = var1;
                     }

                     @Override
                     public final void run() {
                        this.$task.invoke();
                     }
                  }
               );
            } else {
               try {
                  var1.invoke();
               } catch (var2: java.lang.Throwable) {
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun)
                     .getLogger()
                     .log(SentryLevel.ERROR, "Failed to execute task CaptureStrategy.runInBackground", var2);
                  return;
               }
            }
         }

         @Override
         public Date getValue(Object var1, KProperty<?> var2) {
            return this.value.get();
         }

         @Override
         public void setValue(Object var1, KProperty<?> var2, Date var3) {
            var1 = this.value.getAndSet((Date)var3);
            if (!(var1 == var3)) {
               this.runInBackground((new Function0<Unit>(this.$propertyName, var1, var3, this.this$0) {
                  final Object $oldValue;
                  final java.lang.String $propertyName;
                  final Object $value;
                  final BaseCaptureStrategy this$0;

                  {
                     super(0);
                     this.$propertyName = var1;
                     this.$oldValue = var2x;
                     this.$value = var3x;
                     this.this$0 = var4;
                  }

                  public final void invoke() {
                     val var1x: Date = this.$value as Date;
                     val var4: Date = this.$oldValue as Date;
                     val var5: ReplayCache = this.this$0.getCache();
                     if (var5 != null) {
                        val var3x: java.lang.String;
                        if (var1x == null) {
                           var3x = null;
                        } else {
                           var3x = DateUtils.getTimestamp(var1x);
                        }

                        var5.persistSegmentValues("segment.timestamp", var3x);
                     }
                  }
               }) as () -> Unit);
            }
         }
      };
      this.replayStartTimestamp = new AtomicLong();
      this.screenAtStart$delegate = new ReadWriteProperty<Object, java.lang.String>(null, this, "replay.screen-at-start", this, "replay.screen-at-start") {
         final java.lang.String $propertyName;
         final java.lang.String $propertyName$inlined;
         final BaseCaptureStrategy this$0;
         final BaseCaptureStrategy this$0$inline_fun;
         private final AtomicReference<java.lang.String> value;

         {
            this.this$0$inline_fun = var2;
            this.$propertyName = var3;
            this.this$0 = var4;
            this.$propertyName$inlined = var5;
            this.value = new AtomicReference<>((java.lang.String)var1);
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         private final void runInBackground(Function0<Unit> var1) {
            if (BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun).getMainThreadChecker().isMainThread()) {
               ExecutorsKt.submitSafely(
                  BaseCaptureStrategy.access$getPersistingExecutor(this.this$0$inline_fun),
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun),
                  "CaptureStrategy.runInBackground",
                  new Runnable(var1) {
                     final Function0 $task;

                     {
                        this.$task = var1;
                     }

                     @Override
                     public final void run() {
                        this.$task.invoke();
                     }
                  }
               );
            } else {
               try {
                  var1.invoke();
               } catch (var2: java.lang.Throwable) {
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun)
                     .getLogger()
                     .log(SentryLevel.ERROR, "Failed to execute task CaptureStrategy.runInBackground", var2);
                  return;
               }
            }
         }

         @Override
         public java.lang.String getValue(Object var1, KProperty<?> var2) {
            return this.value.get();
         }

         @Override
         public void setValue(Object var1, KProperty<?> var2, java.lang.String var3) {
            var1 = this.value.getAndSet((java.lang.String)var3);
            if (!(var1 == var3)) {
               this.runInBackground((new Function0<Unit>(this.$propertyName, var1, var3, this.this$0, this.$propertyName$inlined) {
                  final Object $oldValue;
                  final java.lang.String $propertyName;
                  final java.lang.String $propertyName$inlined;
                  final Object $value;
                  final BaseCaptureStrategy this$0;

                  {
                     super(0);
                     this.$propertyName = var1;
                     this.$oldValue = var2x;
                     this.$value = var3x;
                     this.this$0 = var4;
                     this.$propertyName$inlined = var5;
                  }

                  public final void invoke() {
                     val var2x: Any = this.$value;
                     val var1x: ReplayCache = this.this$0.getCache();
                     if (var1x != null) {
                        var1x.persistSegmentValues(this.$propertyName$inlined, java.lang.String.valueOf(var2x));
                     }
                  }
               }) as () -> Unit);
            }
         }
      };
      this.currentReplayId$delegate = new ReadWriteProperty<Object, SentryId>(SentryId.EMPTY_ID, this, "replay.id", this, "replay.id") {
         final java.lang.String $propertyName;
         final java.lang.String $propertyName$inlined;
         final BaseCaptureStrategy this$0;
         final BaseCaptureStrategy this$0$inline_fun;
         private final AtomicReference<SentryId> value;

         {
            this.this$0$inline_fun = var2;
            this.$propertyName = var3;
            this.this$0 = var4;
            this.$propertyName$inlined = var5;
            this.value = new AtomicReference<>((SentryId)var1);
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         private final void runInBackground(Function0<Unit> var1) {
            if (BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun).getMainThreadChecker().isMainThread()) {
               ExecutorsKt.submitSafely(
                  BaseCaptureStrategy.access$getPersistingExecutor(this.this$0$inline_fun),
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun),
                  "CaptureStrategy.runInBackground",
                  new Runnable(var1) {
                     final Function0 $task;

                     {
                        this.$task = var1;
                     }

                     @Override
                     public final void run() {
                        this.$task.invoke();
                     }
                  }
               );
            } else {
               try {
                  var1.invoke();
               } catch (var2: java.lang.Throwable) {
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun)
                     .getLogger()
                     .log(SentryLevel.ERROR, "Failed to execute task CaptureStrategy.runInBackground", var2);
                  return;
               }
            }
         }

         @Override
         public SentryId getValue(Object var1, KProperty<?> var2) {
            return this.value.get();
         }

         @Override
         public void setValue(Object var1, KProperty<?> var2, SentryId var3) {
            var1 = this.value.getAndSet((SentryId)var3);
            if (!(var1 == var3)) {
               this.runInBackground((new Function0<Unit>(this.$propertyName, var1, var3, this.this$0, this.$propertyName$inlined) {
                  final Object $oldValue;
                  final java.lang.String $propertyName;
                  final java.lang.String $propertyName$inlined;
                  final Object $value;
                  final BaseCaptureStrategy this$0;

                  {
                     super(0);
                     this.$propertyName = var1;
                     this.$oldValue = var2x;
                     this.$value = var3x;
                     this.this$0 = var4;
                     this.$propertyName$inlined = var5;
                  }

                  public final void invoke() {
                     val var1x: Any = this.$value;
                     val var2x: ReplayCache = this.this$0.getCache();
                     if (var2x != null) {
                        var2x.persistSegmentValues(this.$propertyName$inlined, java.lang.String.valueOf(var1x));
                     }
                  }
               }) as () -> Unit);
            }
         }
      };
      this.currentSegment$delegate = new ReadWriteProperty<Object, Integer>(-1, this, "segment.id", this, "segment.id") {
         final java.lang.String $propertyName;
         final java.lang.String $propertyName$inlined;
         final BaseCaptureStrategy this$0;
         final BaseCaptureStrategy this$0$inline_fun;
         private final AtomicReference<Integer> value;

         {
            this.this$0$inline_fun = var2;
            this.$propertyName = var3;
            this.this$0 = var4;
            this.$propertyName$inlined = var5;
            this.value = new AtomicReference<>((Integer)var1);
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         private final void runInBackground(Function0<Unit> var1) {
            if (BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun).getMainThreadChecker().isMainThread()) {
               ExecutorsKt.submitSafely(
                  BaseCaptureStrategy.access$getPersistingExecutor(this.this$0$inline_fun),
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun),
                  "CaptureStrategy.runInBackground",
                  new Runnable(var1) {
                     final Function0 $task;

                     {
                        this.$task = var1;
                     }

                     @Override
                     public final void run() {
                        this.$task.invoke();
                     }
                  }
               );
            } else {
               try {
                  var1.invoke();
               } catch (var2: java.lang.Throwable) {
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun)
                     .getLogger()
                     .log(SentryLevel.ERROR, "Failed to execute task CaptureStrategy.runInBackground", var2);
                  return;
               }
            }
         }

         @Override
         public Integer getValue(Object var1, KProperty<?> var2) {
            return this.value.get();
         }

         @Override
         public void setValue(Object var1, KProperty<?> var2, Integer var3) {
            var1 = this.value.getAndSet((Integer)var3);
            if (!(var1 == var3)) {
               this.runInBackground((new Function0<Unit>(this.$propertyName, var1, var3, this.this$0, this.$propertyName$inlined) {
                  final Object $oldValue;
                  final java.lang.String $propertyName;
                  final java.lang.String $propertyName$inlined;
                  final Object $value;
                  final BaseCaptureStrategy this$0;

                  {
                     super(0);
                     this.$propertyName = var1;
                     this.$oldValue = var2x;
                     this.$value = var3x;
                     this.this$0 = var4;
                     this.$propertyName$inlined = var5;
                  }

                  public final void invoke() {
                     val var1x: Any = this.$value;
                     val var2x: ReplayCache = this.this$0.getCache();
                     if (var2x != null) {
                        var2x.persistSegmentValues(this.$propertyName$inlined, java.lang.String.valueOf(var1x));
                     }
                  }
               }) as () -> Unit);
            }
         }
      };
      this.replayType$delegate = new ReadWriteProperty<Object, SentryReplayEvent.ReplayType>(null, this, "replay.type", this, "replay.type") {
         final java.lang.String $propertyName;
         final java.lang.String $propertyName$inlined;
         final BaseCaptureStrategy this$0;
         final BaseCaptureStrategy this$0$inline_fun;
         private final AtomicReference<SentryReplayEvent.ReplayType> value;

         {
            this.this$0$inline_fun = var2;
            this.$propertyName = var3;
            this.this$0 = var4;
            this.$propertyName$inlined = var5;
            this.value = new AtomicReference<>((SentryReplayEvent.ReplayType)var1);
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         private final void runInBackground(Function0<Unit> var1) {
            if (BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun).getMainThreadChecker().isMainThread()) {
               ExecutorsKt.submitSafely(
                  BaseCaptureStrategy.access$getPersistingExecutor(this.this$0$inline_fun),
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun),
                  "CaptureStrategy.runInBackground",
                  new Runnable(var1) {
                     final Function0 $task;

                     {
                        this.$task = var1;
                     }

                     @Override
                     public final void run() {
                        this.$task.invoke();
                     }
                  }
               );
            } else {
               try {
                  var1.invoke();
               } catch (var2: java.lang.Throwable) {
                  BaseCaptureStrategy.access$getOptions$p(this.this$0$inline_fun)
                     .getLogger()
                     .log(SentryLevel.ERROR, "Failed to execute task CaptureStrategy.runInBackground", var2);
                  return;
               }
            }
         }

         @Override
         public SentryReplayEvent.ReplayType getValue(Object var1, KProperty<?> var2) {
            return this.value.get();
         }

         @Override
         public void setValue(Object var1, KProperty<?> var2, SentryReplayEvent.ReplayType var3) {
            var1 = this.value.getAndSet((SentryReplayEvent.ReplayType)var3);
            if (!(var1 == var3)) {
               this.runInBackground((new Function0<Unit>(this.$propertyName, var1, var3, this.this$0, this.$propertyName$inlined) {
                  final Object $oldValue;
                  final java.lang.String $propertyName;
                  final java.lang.String $propertyName$inlined;
                  final Object $value;
                  final BaseCaptureStrategy this$0;

                  {
                     super(0);
                     this.$propertyName = var1;
                     this.$oldValue = var2x;
                     this.$value = var3x;
                     this.this$0 = var4;
                     this.$propertyName$inlined = var5;
                  }

                  public final void invoke() {
                     val var1x: Any = this.$value;
                     val var2x: ReplayCache = this.this$0.getCache();
                     if (var2x != null) {
                        var2x.persistSegmentValues(this.$propertyName$inlined, java.lang.String.valueOf(var1x));
                     }
                  }
               }) as () -> Unit);
            }
         }
      };
      this.currentEvents = new ConcurrentLinkedDeque<>();
   }

   private inline fun <T> persistableAtomic(
      initialValue: T? = null,
      propertyName: String,
      crossinline onChange: (String?, T?, T?) -> Unit = (new Function3<java.lang.String, T, T, Unit>(var0, var2) {
            final java.lang.String $propertyName;
            final BaseCaptureStrategy this$0;

            {
               super(3);
               this.this$0 = var1;
               this.$propertyName = var2;
            }

            public final void invoke(java.lang.String var1, T var2, T var3) {
               val var4: ReplayCache = this.this$0.getCache();
               if (var4 != null) {
                  var4.persistSegmentValues(this.$propertyName, java.lang.String.valueOf(var3));
               }
            }
         }) as Function3
   ): ReadWriteProperty<Any?, T> {
      return new ReadWriteProperty<Object, T>(var1, this, var3, var2) {
         final Function3<java.lang.String, T, T, Unit> $onChange;
         final java.lang.String $propertyName;
         final BaseCaptureStrategy this$0;
         private final AtomicReference<T> value;

         {
            this.this$0 = var2;
            this.$onChange = var3;
            this.$propertyName = var4;
            this.value = (AtomicReference<T>)(new AtomicReference<>(var1));
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         private final void runInBackground(Function0<Unit> var1) {
            if (BaseCaptureStrategy.access$getOptions$p(this.this$0).getMainThreadChecker().isMainThread()) {
               ExecutorsKt.submitSafely(
                  BaseCaptureStrategy.access$getPersistingExecutor(this.this$0),
                  BaseCaptureStrategy.access$getOptions$p(this.this$0),
                  "CaptureStrategy.runInBackground",
                  new Runnable(var1) {
                     final Function0<Unit> $task;

                     {
                        this.$task = var1;
                     }

                     @Override
                     public final void run() {
                        this.$task.invoke();
                     }
                  }
               );
            } else {
               try {
                  var1.invoke();
               } catch (var2: java.lang.Throwable) {
                  BaseCaptureStrategy.access$getOptions$p(this.this$0)
                     .getLogger()
                     .log(SentryLevel.ERROR, "Failed to execute task CaptureStrategy.runInBackground", var2);
                  return;
               }
            }
         }

         @Override
         public T getValue(Object var1, KProperty<?> var2) {
            return (T)this.value.get();
         }

         @Override
         public void setValue(Object var1, KProperty<?> var2, T var3) {
            var1 = this.value.getAndSet(var3);
            if (!(var1 == var3)) {
               this.runInBackground((new Function0<Unit>(this.$onChange, this.$propertyName, var1, var3) {
                  final T $oldValue;
                  final Function3<java.lang.String, T, T, Unit> $onChange;
                  final java.lang.String $propertyName;
                  final T $value;

                  {
                     super(0);
                     this.$onChange = var1;
                     this.$propertyName = var2x;
                     this.$oldValue = (T)var3x;
                     this.$value = (T)var4;
                  }

                  public final void invoke() {
                     this.$onChange.invoke(this.$propertyName, this.$oldValue, this.$value);
                  }
               }) as () -> Unit);
            }
         }
      };
   }

   private inline fun <T> persistableAtomic(crossinline onChange: (String?, T?, T?) -> Unit): ReadWriteProperty<Any?, T> {
      return new ReadWriteProperty<Object, T>(null, this, var1, "") {
         final Function3<java.lang.String, T, T, Unit> $onChange;
         final java.lang.String $propertyName;
         final BaseCaptureStrategy this$0;
         private final AtomicReference<T> value;

         {
            this.this$0 = var2;
            this.$onChange = var3;
            this.$propertyName = var4;
            this.value = (AtomicReference<T>)(new AtomicReference<>(var1));
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         private final void runInBackground(Function0<Unit> var1) {
            if (BaseCaptureStrategy.access$getOptions$p(this.this$0).getMainThreadChecker().isMainThread()) {
               ExecutorsKt.submitSafely(
                  BaseCaptureStrategy.access$getPersistingExecutor(this.this$0),
                  BaseCaptureStrategy.access$getOptions$p(this.this$0),
                  "CaptureStrategy.runInBackground",
                  new Runnable(var1) {
                     final Function0<Unit> $task;

                     {
                        this.$task = var1;
                     }

                     @Override
                     public final void run() {
                        this.$task.invoke();
                     }
                  }
               );
            } else {
               try {
                  var1.invoke();
               } catch (var2: java.lang.Throwable) {
                  BaseCaptureStrategy.access$getOptions$p(this.this$0)
                     .getLogger()
                     .log(SentryLevel.ERROR, "Failed to execute task CaptureStrategy.runInBackground", var2);
                  return;
               }
            }
         }

         @Override
         public T getValue(Object var1, KProperty<?> var2) {
            return (T)this.value.get();
         }

         @Override
         public void setValue(Object var1, KProperty<?> var2, T var3) {
            var1 = this.value.getAndSet(var3);
            if (!(var1 == var3)) {
               this.runInBackground((new Function0<Unit>(this.$onChange, this.$propertyName, var1, var3) {
                  final T $oldValue;
                  final Function3<java.lang.String, T, T, Unit> $onChange;
                  final java.lang.String $propertyName;
                  final T $value;

                  {
                     super(0);
                     this.$onChange = var1;
                     this.$propertyName = var2x;
                     this.$oldValue = (T)var3x;
                     this.$value = (T)var4;
                  }

                  public final void invoke() {
                     this.$onChange.invoke(this.$propertyName, this.$oldValue, this.$value);
                  }
               }) as () -> Unit);
            }
         }
      };
   }

   private inline fun <T> persistableAtomicNullable(
      initialValue: T? = null,
      propertyName: String,
      crossinline onChange: (String?, T?, T?) -> Unit = (new Function3<java.lang.String, T, T, Unit>(var0, var2) {
            final java.lang.String $propertyName;
            final BaseCaptureStrategy this$0;

            {
               super(3);
               this.this$0 = var1;
               this.$propertyName = var2;
            }

            public final void invoke(java.lang.String var1, T var2, T var3) {
               val var4: ReplayCache = this.this$0.getCache();
               if (var4 != null) {
                  var4.persistSegmentValues(this.$propertyName, java.lang.String.valueOf(var3));
               }
            }
         }) as Function3
   ): ReadWriteProperty<Any?, T?> {
      return new ReadWriteProperty<Object, T>(var1, this, var3, var2) {
         final Function3<java.lang.String, T, T, Unit> $onChange;
         final java.lang.String $propertyName;
         final BaseCaptureStrategy this$0;
         private final AtomicReference<T> value;

         {
            this.this$0 = var2;
            this.$onChange = var3;
            this.$propertyName = var4;
            this.value = (AtomicReference<T>)(new AtomicReference<>(var1));
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         private final void runInBackground(Function0<Unit> var1) {
            if (BaseCaptureStrategy.access$getOptions$p(this.this$0).getMainThreadChecker().isMainThread()) {
               ExecutorsKt.submitSafely(
                  BaseCaptureStrategy.access$getPersistingExecutor(this.this$0),
                  BaseCaptureStrategy.access$getOptions$p(this.this$0),
                  "CaptureStrategy.runInBackground",
                  new Runnable(var1) {
                     final Function0<Unit> $task;

                     {
                        this.$task = var1;
                     }

                     @Override
                     public final void run() {
                        this.$task.invoke();
                     }
                  }
               );
            } else {
               try {
                  var1.invoke();
               } catch (var2: java.lang.Throwable) {
                  BaseCaptureStrategy.access$getOptions$p(this.this$0)
                     .getLogger()
                     .log(SentryLevel.ERROR, "Failed to execute task CaptureStrategy.runInBackground", var2);
                  return;
               }
            }
         }

         @Override
         public T getValue(Object var1, KProperty<?> var2) {
            return (T)this.value.get();
         }

         @Override
         public void setValue(Object var1, KProperty<?> var2, T var3) {
            var1 = this.value.getAndSet(var3);
            if (!(var1 == var3)) {
               this.runInBackground((new Function0<Unit>(this.$onChange, this.$propertyName, var1, var3) {
                  final T $oldValue;
                  final Function3<java.lang.String, T, T, Unit> $onChange;
                  final java.lang.String $propertyName;
                  final T $value;

                  {
                     super(0);
                     this.$onChange = var1;
                     this.$propertyName = var2x;
                     this.$oldValue = (T)var3x;
                     this.$value = (T)var4;
                  }

                  public final void invoke() {
                     this.$onChange.invoke(this.$propertyName, this.$oldValue, this.$value);
                  }
               }) as () -> Unit);
            }
         }
      };
   }

   protected fun createSegmentInternal(
      duration: Long,
      currentSegmentTimestamp: Date,
      replayId: SentryId,
      segmentId: Int,
      height: Int,
      width: Int,
      replayType: ReplayType = var0.getReplayType(),
      cache: ReplayCache? = var0.cache,
      frameRate: Int = var0.getRecorderConfig().getFrameRate(),
      bitRate: Int = var0.getRecorderConfig().getBitRate(),
      screenAtStart: String? = var0.getScreenAtStart(),
      breadcrumbs: List<Breadcrumb>? = null,
      events: Deque<RRWebEvent> = var0.currentEvents
   ): ReplaySegment {
      return CaptureStrategy.Companion.createSegment(this.hub, this.options, var1, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14);
   }

   public override fun onConfigurationChanged(recorderConfig: ScreenshotRecorderConfig) {
      this.setRecorderConfig(var1);
   }

   override fun onScreenChanged(var1: java.lang.String) {
      CaptureStrategy.DefaultImpls.onScreenChanged(this, var1);
   }

   public override fun onTouchEvent(event: MotionEvent) {
      val var2: java.util.List = this.gestureConverter.convert(var1, this.getRecorderConfig());
      if (var2 != null) {
         CollectionsKt.addAll(this.currentEvents, var2);
      }
   }

   public override fun pause() {
   }

   public override fun resume() {
      this.setSegmentTimestamp(DateUtils.getCurrentDateTime());
   }

   public override fun start(recorderConfig: ScreenshotRecorderConfig, segmentId: Int, replayId: SentryId, replayType: ReplayType?) {
      var var8: ReplayCache;
      label18: {
         if (this.replayCacheProvider != null) {
            val var6: ReplayCache = this.replayCacheProvider.invoke(var3);
            var8 = var6;
            if (var6 != null) {
               break label18;
            }
         }

         var8 = new ReplayCache(this.options, var3);
      }

      this.cache = var8;
      this.setCurrentReplayId(var3);
      this.setCurrentSegment(var2);
      var var7: SentryReplayEvent.ReplayType = var4;
      if (var4 == null) {
         if (this is SessionCaptureStrategy) {
            var7 = SentryReplayEvent.ReplayType.SESSION;
         } else {
            var7 = SentryReplayEvent.ReplayType.BUFFER;
         }
      }

      this.setReplayType(var7);
      this.setRecorderConfig(var1);
      this.setSegmentTimestamp(DateUtils.getCurrentDateTime());
      this.replayStartTimestamp.set(this.dateProvider.getCurrentTimeMillis());
   }

   public override fun stop() {
      if (this.cache != null) {
         this.cache.close();
      }

      this.setCurrentSegment(-1);
      this.replayStartTimestamp.set(0L);
      this.setSegmentTimestamp(null);
      val var2: SentryId = SentryId.EMPTY_ID;
      this.setCurrentReplayId(var2);
   }

   internal companion object {
      private const val TAG: String
   }

   private class ReplayPersistingExecutorServiceThreadFactory : ThreadFactory {
      private final var cnt: Int

      public override fun newThread(r: Runnable): Thread {
         val var3: StringBuilder = new StringBuilder("SentryReplayPersister-");
         var3.append(this.cnt++);
         val var4: Thread = new Thread(var1, var3.toString());
         var4.setDaemon(true);
         return var4;
      }
   }
}
