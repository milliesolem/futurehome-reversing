package okhttp3.internal.cache

import java.io.Closeable
import java.io.File
import java.io.FileNotFoundException
import java.io.Flushable
import java.io.IOException
import java.util.ArrayList
import java.util.LinkedHashMap
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okhttp3.internal.concurrent.Task
import okhttp3.internal.concurrent.TaskQueue
import okhttp3.internal.concurrent.TaskRunner
import okhttp3.internal.io.FileSystem
import okio.BufferedSink
import okio.ForwardingSource
import okio.Okio
import okio.Sink
import okio.Source

public class DiskLruCache internal constructor(fileSystem: FileSystem, directory: File, appVersion: Int, valueCount: Int, maxSize: Long, taskRunner: TaskRunner) :
   Closeable,
   Flushable {
   private final val appVersion: Int
   private final var civilizedFileSystem: Boolean
   private final val cleanupQueue: TaskQueue
   private final val cleanupTask: <unrepresentable>
   internal final var closed: Boolean
   public final val directory: File
   internal final val fileSystem: FileSystem
   private final var hasJournalErrors: Boolean
   private final var initialized: Boolean
   private final val journalFile: File
   private final val journalFileBackup: File
   private final val journalFileTmp: File
   private final var journalWriter: BufferedSink?
   internal final val lruEntries: LinkedHashMap<String, okhttp3.internal.cache.DiskLruCache.Entry>

   public final var maxSize: Long
      public final get() {
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
         //
         // Bytecode:
         // 0: aload 0
         // 1: monitorenter
         // 2: aload 0
         // 3: getfield okhttp3/internal/cache/DiskLruCache.maxSize J
         // 6: lstore 1
         // 7: aload 0
         // 8: monitorexit
         // 9: lload 1
         // a: lreturn
         // b: astore 3
         // c: aload 0
         // d: monitorexit
         // e: aload 3
         // f: athrow
      }

      public final set(value) {
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
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: lload 1
         // 04: putfield okhttp3/internal/cache/DiskLruCache.maxSize J
         // 07: aload 0
         // 08: getfield okhttp3/internal/cache/DiskLruCache.initialized Z
         // 0b: ifeq 1f
         // 0e: aload 0
         // 0f: getfield okhttp3/internal/cache/DiskLruCache.cleanupQueue Lokhttp3/internal/concurrent/TaskQueue;
         // 12: aload 0
         // 13: getfield okhttp3/internal/cache/DiskLruCache.cleanupTask Lokhttp3/internal/cache/DiskLruCache$cleanupTask$1;
         // 16: checkcast okhttp3/internal/concurrent/Task
         // 19: lconst_0
         // 1a: bipush 2
         // 1b: aconst_null
         // 1c: invokestatic okhttp3/internal/concurrent/TaskQueue.schedule$default (Lokhttp3/internal/concurrent/TaskQueue;Lokhttp3/internal/concurrent/Task;JILjava/lang/Object;)V
         // 1f: aload 0
         // 20: monitorexit
         // 21: return
         // 22: astore 3
         // 23: aload 0
         // 24: monitorexit
         // 25: aload 3
         // 26: athrow
      }


   private final var mostRecentRebuildFailed: Boolean
   private final var mostRecentTrimFailed: Boolean
   private final var nextSequenceNumber: Long
   private final var redundantOpCount: Int
   private final var size: Long
   internal final val valueCount: Int

   init {
      Intrinsics.checkParameterIsNotNull(var1, "fileSystem");
      Intrinsics.checkParameterIsNotNull(var2, "directory");
      Intrinsics.checkParameterIsNotNull(var7, "taskRunner");
      super();
      this.fileSystem = var1;
      this.directory = var2;
      this.appVersion = var3;
      this.valueCount = var4;
      this.maxSize = var5;
      this.lruEntries = new LinkedHashMap<>(0, 0.75F, true);
      this.cleanupQueue = var7.newQueue();
      val var9: StringBuilder = new StringBuilder();
      var9.append(Util.okHttpName);
      var9.append(" Cache");
      this.cleanupTask = new Task(this, var9.toString()) {
         final DiskLruCache this$0;

         {
            super(var2, false, 2, null);
            this.this$0 = var1;
         }

         @Override
         public long runOnce() {
            // $VF: Couldn't be decompiled
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            // java.lang.RuntimeException: parsing failure!
            //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
            //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
            //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
            //
            // Bytecode:
            // 00: aload 0
            // 01: getfield okhttp3/internal/cache/DiskLruCache$cleanupTask$1.this$0 Lokhttp3/internal/cache/DiskLruCache;
            // 04: astore 2
            // 05: aload 2
            // 06: monitorenter
            // 07: aload 0
            // 08: getfield okhttp3/internal/cache/DiskLruCache$cleanupTask$1.this$0 Lokhttp3/internal/cache/DiskLruCache;
            // 0b: invokestatic okhttp3/internal/cache/DiskLruCache.access$getInitialized$p (Lokhttp3/internal/cache/DiskLruCache;)Z
            // 0e: ifeq 6b
            // 11: aload 0
            // 12: getfield okhttp3/internal/cache/DiskLruCache$cleanupTask$1.this$0 Lokhttp3/internal/cache/DiskLruCache;
            // 15: invokevirtual okhttp3/internal/cache/DiskLruCache.getClosed$okhttp ()Z
            // 18: istore 1
            // 19: iload 1
            // 1a: ifeq 20
            // 1d: goto 6b
            // 20: aload 0
            // 21: getfield okhttp3/internal/cache/DiskLruCache$cleanupTask$1.this$0 Lokhttp3/internal/cache/DiskLruCache;
            // 24: invokevirtual okhttp3/internal/cache/DiskLruCache.trimToSize ()V
            // 27: goto 33
            // 2a: astore 3
            // 2b: aload 0
            // 2c: getfield okhttp3/internal/cache/DiskLruCache$cleanupTask$1.this$0 Lokhttp3/internal/cache/DiskLruCache;
            // 2f: bipush 1
            // 30: invokestatic okhttp3/internal/cache/DiskLruCache.access$setMostRecentTrimFailed$p (Lokhttp3/internal/cache/DiskLruCache;Z)V
            // 33: aload 0
            // 34: getfield okhttp3/internal/cache/DiskLruCache$cleanupTask$1.this$0 Lokhttp3/internal/cache/DiskLruCache;
            // 37: invokestatic okhttp3/internal/cache/DiskLruCache.access$journalRebuildRequired (Lokhttp3/internal/cache/DiskLruCache;)Z
            // 3a: ifeq 65
            // 3d: aload 0
            // 3e: getfield okhttp3/internal/cache/DiskLruCache$cleanupTask$1.this$0 Lokhttp3/internal/cache/DiskLruCache;
            // 41: invokevirtual okhttp3/internal/cache/DiskLruCache.rebuildJournal$okhttp ()V
            // 44: aload 0
            // 45: getfield okhttp3/internal/cache/DiskLruCache$cleanupTask$1.this$0 Lokhttp3/internal/cache/DiskLruCache;
            // 48: bipush 0
            // 49: invokestatic okhttp3/internal/cache/DiskLruCache.access$setRedundantOpCount$p (Lokhttp3/internal/cache/DiskLruCache;I)V
            // 4c: goto 65
            // 4f: astore 3
            // 50: aload 0
            // 51: getfield okhttp3/internal/cache/DiskLruCache$cleanupTask$1.this$0 Lokhttp3/internal/cache/DiskLruCache;
            // 54: bipush 1
            // 55: invokestatic okhttp3/internal/cache/DiskLruCache.access$setMostRecentRebuildFailed$p (Lokhttp3/internal/cache/DiskLruCache;Z)V
            // 58: aload 0
            // 59: getfield okhttp3/internal/cache/DiskLruCache$cleanupTask$1.this$0 Lokhttp3/internal/cache/DiskLruCache;
            // 5c: invokestatic okio/Okio.blackhole ()Lokio/Sink;
            // 5f: invokestatic okio/Okio.buffer (Lokio/Sink;)Lokio/BufferedSink;
            // 62: invokestatic okhttp3/internal/cache/DiskLruCache.access$setJournalWriter$p (Lokhttp3/internal/cache/DiskLruCache;Lokio/BufferedSink;)V
            // 65: aload 2
            // 66: monitorexit
            // 67: ldc2_w -1
            // 6a: lreturn
            // 6b: aload 2
            // 6c: monitorexit
            // 6d: ldc2_w -1
            // 70: lreturn
            // 71: astore 3
            // 72: aload 2
            // 73: monitorexit
            // 74: aload 3
            // 75: athrow
         }
      };
      var var10: Boolean;
      if (var5 > 0L) {
         var10 = true;
      } else {
         var10 = false;
      }

      if (var10) {
         var10 = false;
         if (var4 > 0) {
            var10 = true;
         }

         if (var10) {
            this.journalFile = new File(var2, JOURNAL_FILE);
            this.journalFileTmp = new File(var2, JOURNAL_FILE_TEMP);
            this.journalFileBackup = new File(var2, JOURNAL_FILE_BACKUP);
         } else {
            throw (new IllegalArgumentException("valueCount <= 0".toString())) as java.lang.Throwable;
         }
      } else {
         throw (new IllegalArgumentException("maxSize <= 0".toString())) as java.lang.Throwable;
      }
   }

   private fun checkNotClosed() {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okhttp3/internal/cache/DiskLruCache.closed Z
      // 06: istore 1
      // 07: iload 1
      // 08: ifne 0e
      // 0b: aload 0
      // 0c: monitorexit
      // 0d: return
      // 0e: new java/lang/IllegalStateException
      // 11: astore 2
      // 12: aload 2
      // 13: ldc_w "cache is closed"
      // 16: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 19: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 1c: aload 2
      // 1d: checkcast java/lang/Throwable
      // 20: athrow
      // 21: astore 2
      // 22: aload 0
      // 23: monitorexit
      // 24: aload 2
      // 25: athrow
   }

   private fun journalRebuildRequired(): Boolean {
      val var2: Boolean;
      if (this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Throws(java/io/FileNotFoundException::class)
   private fun newJournalWriter(): BufferedSink {
      return Okio.buffer(new FaultHidingSink(this.fileSystem.appendingSink(this.journalFile), (new Function1<IOException, Unit>(this) {
         final DiskLruCache this$0;

         {
            super(1);
            this.this$0 = var1;
         }

         public final void invoke(IOException var1) {
            Intrinsics.checkParameterIsNotNull(var1, "it");
            val var2: DiskLruCache = this.this$0;
            if (Util.assertionsEnabled && !Thread.holdsLock(this.this$0)) {
               val var3: StringBuilder = new StringBuilder("Thread ");
               val var4: Thread = Thread.currentThread();
               Intrinsics.checkExpressionValueIsNotNull(var4, "Thread.currentThread()");
               var3.append(var4.getName());
               var3.append(" MUST hold lock on ");
               var3.append(var2);
               throw (new AssertionError(var3.toString())) as java.lang.Throwable;
            } else {
               DiskLruCache.access$setHasJournalErrors$p(this.this$0, true);
            }
         }
      }) as (IOException?) -> Unit));
   }

   @Throws(java/io/IOException::class)
   private fun processJournal() {
      this.fileSystem.delete(this.journalFileTmp);
      val var4: java.util.Iterator = this.lruEntries.values().iterator();

      while (var4.hasNext()) {
         val var5: Any = var4.next();
         Intrinsics.checkExpressionValueIsNotNull(var5, "i.next()");
         val var9: DiskLruCache.Entry = var5 as DiskLruCache.Entry;
         var var6: DiskLruCache.Editor = (var5 as DiskLruCache.Entry).getCurrentEditor$okhttp();
         var var1: Int = 0;
         if (var6 == null) {
            for (int var8 = this.valueCount; var1 < var8; var1++) {
               this.size = this.size + var9.getLengths$okhttp()[var1];
            }
         } else {
            var6 = null as DiskLruCache.Editor;
            var9.setCurrentEditor$okhttp(null);
            val var3: Int = this.valueCount;

            for (int var7 = 0; var7 < var3; var7++) {
               this.fileSystem.delete(var9.getCleanFiles$okhttp().get(var7));
               this.fileSystem.delete(var9.getDirtyFiles$okhttp().get(var7));
            }

            var4.remove();
         }
      }
   }

   @Throws(java/io/IOException::class)
   private fun readJournal() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 004: aload 0
      // 005: getfield okhttp3/internal/cache/DiskLruCache.journalFile Ljava/io/File;
      // 008: invokeinterface okhttp3/internal/io/FileSystem.source (Ljava/io/File;)Lokio/Source; 2
      // 00d: invokestatic okio/Okio.buffer (Lokio/Source;)Lokio/BufferedSource;
      // 010: checkcast java/io/Closeable
      // 013: astore 2
      // 014: aconst_null
      // 015: checkcast java/lang/Throwable
      // 018: astore 3
      // 019: aload 2
      // 01a: checkcast okio/BufferedSource
      // 01d: astore 7
      // 01f: aload 7
      // 021: invokeinterface okio/BufferedSource.readUtf8LineStrict ()Ljava/lang/String; 1
      // 026: astore 4
      // 028: aload 7
      // 02a: invokeinterface okio/BufferedSource.readUtf8LineStrict ()Ljava/lang/String; 1
      // 02f: astore 5
      // 031: aload 7
      // 033: invokeinterface okio/BufferedSource.readUtf8LineStrict ()Ljava/lang/String; 1
      // 038: astore 8
      // 03a: aload 7
      // 03c: invokeinterface okio/BufferedSource.readUtf8LineStrict ()Ljava/lang/String; 1
      // 041: astore 3
      // 042: aload 7
      // 044: invokeinterface okio/BufferedSource.readUtf8LineStrict ()Ljava/lang/String; 1
      // 049: astore 6
      // 04b: getstatic okhttp3/internal/cache/DiskLruCache.MAGIC Ljava/lang/String;
      // 04e: aload 4
      // 050: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 053: ifeq 0d1
      // 056: getstatic okhttp3/internal/cache/DiskLruCache.VERSION_1 Ljava/lang/String;
      // 059: aload 5
      // 05b: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 05e: ifeq 0d1
      // 061: aload 0
      // 062: getfield okhttp3/internal/cache/DiskLruCache.appVersion I
      // 065: invokestatic java/lang/String.valueOf (I)Ljava/lang/String;
      // 068: aload 8
      // 06a: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 06d: ifeq 0d1
      // 070: aload 0
      // 071: getfield okhttp3/internal/cache/DiskLruCache.valueCount I
      // 074: invokestatic java/lang/String.valueOf (I)Ljava/lang/String;
      // 077: aload 3
      // 078: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 07b: ifeq 0d1
      // 07e: aload 6
      // 080: checkcast java/lang/CharSequence
      // 083: invokeinterface java/lang/CharSequence.length ()I 1
      // 088: istore 1
      // 089: iload 1
      // 08a: ifgt 0d1
      // 08d: bipush 0
      // 08e: istore 1
      // 08f: aload 0
      // 090: aload 7
      // 092: invokeinterface okio/BufferedSource.readUtf8LineStrict ()Ljava/lang/String; 1
      // 097: invokespecial okhttp3/internal/cache/DiskLruCache.readJournalLine (Ljava/lang/String;)V
      // 09a: iinc 1 1
      // 09d: goto 08f
      // 0a0: astore 3
      // 0a1: aload 0
      // 0a2: iload 1
      // 0a3: aload 0
      // 0a4: getfield okhttp3/internal/cache/DiskLruCache.lruEntries Ljava/util/LinkedHashMap;
      // 0a7: invokevirtual java/util/LinkedHashMap.size ()I
      // 0aa: isub
      // 0ab: putfield okhttp3/internal/cache/DiskLruCache.redundantOpCount I
      // 0ae: aload 7
      // 0b0: invokeinterface okio/BufferedSource.exhausted ()Z 1
      // 0b5: ifne 0bf
      // 0b8: aload 0
      // 0b9: invokevirtual okhttp3/internal/cache/DiskLruCache.rebuildJournal$okhttp ()V
      // 0bc: goto 0c7
      // 0bf: aload 0
      // 0c0: aload 0
      // 0c1: invokespecial okhttp3/internal/cache/DiskLruCache.newJournalWriter ()Lokio/BufferedSink;
      // 0c4: putfield okhttp3/internal/cache/DiskLruCache.journalWriter Lokio/BufferedSink;
      // 0c7: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0ca: astore 3
      // 0cb: aload 2
      // 0cc: aconst_null
      // 0cd: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0d0: return
      // 0d1: new java/io/IOException
      // 0d4: astore 8
      // 0d6: new java/lang/StringBuilder
      // 0d9: astore 7
      // 0db: aload 7
      // 0dd: ldc_w "unexpected journal header: ["
      // 0e0: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 0e3: aload 7
      // 0e5: aload 4
      // 0e7: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0ea: pop
      // 0eb: aload 7
      // 0ed: ldc_w ", "
      // 0f0: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0f3: pop
      // 0f4: aload 7
      // 0f6: aload 5
      // 0f8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0fb: pop
      // 0fc: aload 7
      // 0fe: ldc_w ", "
      // 101: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 104: pop
      // 105: aload 7
      // 107: aload 3
      // 108: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 10b: pop
      // 10c: aload 7
      // 10e: ldc_w ", "
      // 111: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 114: pop
      // 115: aload 7
      // 117: aload 6
      // 119: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 11c: pop
      // 11d: aload 7
      // 11f: bipush 93
      // 121: invokevirtual java/lang/StringBuilder.append (C)Ljava/lang/StringBuilder;
      // 124: pop
      // 125: aload 8
      // 127: aload 7
      // 129: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 12c: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 12f: aload 8
      // 131: checkcast java/lang/Throwable
      // 134: athrow
      // 135: astore 3
      // 136: aload 3
      // 137: athrow
      // 138: astore 4
      // 13a: aload 2
      // 13b: aload 3
      // 13c: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 13f: aload 4
      // 141: athrow
   }

   @Throws(java/io/IOException::class)
   private fun readJournalLine(line: String) {
      val var5: java.lang.CharSequence = var1;
      val var4: Int = StringsKt.indexOf$default(var1, ' ', 0, false, 6, null);
      if (var4 != -1) {
         val var3: Int = var4 + 1;
         val var2: Int = StringsKt.indexOf$default(var5, ' ', var4 + 1, false, 4, null);
         var var6: java.lang.String;
         if (var2 == -1) {
            if (var1 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            val var11: java.lang.String = var1.substring(var3);
            Intrinsics.checkExpressionValueIsNotNull(var11, "(this as java.lang.String).substring(startIndex)");
            val var7: java.lang.String = REMOVE;
            var6 = var11;
            if (var4 == REMOVE.length()) {
               var6 = var11;
               if (StringsKt.startsWith$default(var1, var7, false, 2, null)) {
                  this.lruEntries.remove(var11);
                  return;
               }
            }
         } else {
            if (var1 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var6 = var1.substring(var3, var2);
            Intrinsics.checkExpressionValueIsNotNull(var6, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         }

         val var18: DiskLruCache.Entry = this.lruEntries.get(var6);
         var var12: DiskLruCache.Entry = var18;
         if (var18 == null) {
            var12 = new DiskLruCache.Entry(this, var6);
            this.lruEntries.put(var6, var12);
         }

         if (var2 != -1) {
            var6 = CLEAN;
            if (var4 == CLEAN.length() && StringsKt.startsWith$default(var1, var6, false, 2, null)) {
               if (var1 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var1 = var1.substring(var2 + 1);
               Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
               val var9: java.util.List = StringsKt.split$default(var1, new char[]{' '}, false, 0, 6, null);
               var12.setReadable$okhttp(true);
               val var17: DiskLruCache.Editor = null as DiskLruCache.Editor;
               var12.setCurrentEditor$okhttp(null);
               var12.setLengths$okhttp(var9);
               return;
            }
         }

         if (var2 == -1) {
            var6 = DIRTY;
            if (var4 == DIRTY.length() && StringsKt.startsWith$default(var1, var6, false, 2, null)) {
               var12.setCurrentEditor$okhttp(new DiskLruCache.Editor(this, var12));
               return;
            }
         }

         if (var2 == -1) {
            val var13: java.lang.String = READ;
            if (var4 == READ.length() && StringsKt.startsWith$default(var1, var13, false, 2, null)) {
               return;
            }
         }

         val var14: StringBuilder = new StringBuilder("unexpected journal line: ");
         var14.append(var1);
         throw (new IOException(var14.toString())) as java.lang.Throwable;
      } else {
         val var10: StringBuilder = new StringBuilder("unexpected journal line: ");
         var10.append(var1);
         throw (new IOException(var10.toString())) as java.lang.Throwable;
      }
   }

   private fun removeOldestEntry(): Boolean {
      for (DiskLruCache.Entry var1 : this.lruEntries.values()) {
         if (!var1.getZombie$okhttp()) {
            Intrinsics.checkExpressionValueIsNotNull(var1, "toEvict");
            this.removeEntry$okhttp(var1);
            return true;
         }
      }

      return false;
   }

   private fun validateKey(key: String) {
      if (!LEGAL_KEY_PATTERN.matches(var1)) {
         val var2: StringBuilder = new StringBuilder("keys must match regex [a-z0-9_-]{1,120}: \"");
         var2.append(var1);
         var2.append('"');
         throw (new IllegalArgumentException(var2.toString().toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okhttp3/internal/cache/DiskLruCache.initialized Z
      // 06: ifeq 99
      // 09: aload 0
      // 0a: getfield okhttp3/internal/cache/DiskLruCache.closed Z
      // 0d: ifeq 13
      // 10: goto 99
      // 13: aload 0
      // 14: getfield okhttp3/internal/cache/DiskLruCache.lruEntries Ljava/util/LinkedHashMap;
      // 17: invokevirtual java/util/LinkedHashMap.values ()Ljava/util/Collection;
      // 1a: astore 3
      // 1b: aload 3
      // 1c: ldc_w "lruEntries.values"
      // 1f: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 22: bipush 0
      // 23: istore 1
      // 24: aload 3
      // 25: bipush 0
      // 26: anewarray 18
      // 29: invokeinterface java/util/Collection.toArray ([Ljava/lang/Object;)[Ljava/lang/Object; 2
      // 2e: astore 3
      // 2f: aload 3
      // 30: ifnull 8c
      // 33: aload 3
      // 34: checkcast [Lokhttp3/internal/cache/DiskLruCache$Entry;
      // 37: astore 3
      // 38: aload 3
      // 39: arraylength
      // 3a: istore 2
      // 3b: iload 1
      // 3c: iload 2
      // 3d: if_icmpge 64
      // 40: aload 3
      // 41: iload 1
      // 42: aaload
      // 43: astore 4
      // 45: aload 4
      // 47: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getCurrentEditor$okhttp ()Lokhttp3/internal/cache/DiskLruCache$Editor;
      // 4a: ifnull 5e
      // 4d: aload 4
      // 4f: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getCurrentEditor$okhttp ()Lokhttp3/internal/cache/DiskLruCache$Editor;
      // 52: astore 4
      // 54: aload 4
      // 56: ifnull 5e
      // 59: aload 4
      // 5b: invokevirtual okhttp3/internal/cache/DiskLruCache$Editor.detach$okhttp ()V
      // 5e: iinc 1 1
      // 61: goto 3b
      // 64: aload 0
      // 65: invokevirtual okhttp3/internal/cache/DiskLruCache.trimToSize ()V
      // 68: aload 0
      // 69: getfield okhttp3/internal/cache/DiskLruCache.journalWriter Lokio/BufferedSink;
      // 6c: astore 3
      // 6d: aload 3
      // 6e: ifnonnull 74
      // 71: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 74: aload 3
      // 75: invokeinterface okio/BufferedSink.close ()V 1
      // 7a: aconst_null
      // 7b: checkcast okio/BufferedSink
      // 7e: astore 3
      // 7f: aload 0
      // 80: aconst_null
      // 81: putfield okhttp3/internal/cache/DiskLruCache.journalWriter Lokio/BufferedSink;
      // 84: aload 0
      // 85: bipush 1
      // 86: putfield okhttp3/internal/cache/DiskLruCache.closed Z
      // 89: aload 0
      // 8a: monitorexit
      // 8b: return
      // 8c: new kotlin/TypeCastException
      // 8f: astore 3
      // 90: aload 3
      // 91: ldc_w "null cannot be cast to non-null type kotlin.Array<T>"
      // 94: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
      // 97: aload 3
      // 98: athrow
      // 99: aload 0
      // 9a: bipush 1
      // 9b: putfield okhttp3/internal/cache/DiskLruCache.closed Z
      // 9e: aload 0
      // 9f: monitorexit
      // a0: return
      // a1: astore 3
      // a2: aload 0
      // a3: monitorexit
      // a4: aload 3
      // a5: athrow
   }

   @Throws(java/io/IOException::class)
   internal fun completeEdit(editor: okhttp3.internal.cache.DiskLruCache.Editor, success: Boolean) {
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
      //
      // Bytecode:
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 1
      // 003: ldc_w "editor"
      // 006: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 009: aload 1
      // 00a: invokevirtual okhttp3/internal/cache/DiskLruCache$Editor.getEntry$okhttp ()Lokhttp3/internal/cache/DiskLruCache$Entry;
      // 00d: astore 10
      // 00f: aload 10
      // 011: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getCurrentEditor$okhttp ()Lokhttp3/internal/cache/DiskLruCache$Editor;
      // 014: aload 1
      // 015: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 018: ifeq 227
      // 01b: bipush 0
      // 01c: istore 4
      // 01e: iload 2
      // 01f: ifeq 0a4
      // 022: aload 10
      // 024: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getReadable$okhttp ()Z
      // 027: ifne 0a4
      // 02a: aload 0
      // 02b: getfield okhttp3/internal/cache/DiskLruCache.valueCount I
      // 02e: istore 5
      // 030: bipush 0
      // 031: istore 3
      // 032: iload 3
      // 033: iload 5
      // 035: if_icmpge 0a4
      // 038: aload 1
      // 039: invokevirtual okhttp3/internal/cache/DiskLruCache$Editor.getWritten$okhttp ()[Z
      // 03c: astore 11
      // 03e: aload 11
      // 040: ifnonnull 046
      // 043: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 046: aload 11
      // 048: iload 3
      // 049: baload
      // 04a: ifeq 074
      // 04d: aload 0
      // 04e: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 051: aload 10
      // 053: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getDirtyFiles$okhttp ()Ljava/util/List;
      // 056: iload 3
      // 057: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 05c: checkcast java/io/File
      // 05f: invokeinterface okhttp3/internal/io/FileSystem.exists (Ljava/io/File;)Z 2
      // 064: ifne 06e
      // 067: aload 1
      // 068: invokevirtual okhttp3/internal/cache/DiskLruCache$Editor.abort ()V
      // 06b: aload 0
      // 06c: monitorexit
      // 06d: return
      // 06e: iinc 3 1
      // 071: goto 032
      // 074: aload 1
      // 075: invokevirtual okhttp3/internal/cache/DiskLruCache$Editor.abort ()V
      // 078: new java/lang/IllegalStateException
      // 07b: astore 1
      // 07c: new java/lang/StringBuilder
      // 07f: astore 10
      // 081: aload 10
      // 083: invokespecial java/lang/StringBuilder.<init> ()V
      // 086: aload 10
      // 088: ldc_w "Newly created entry didn't create value for index "
      // 08b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 08e: pop
      // 08f: aload 10
      // 091: iload 3
      // 092: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 095: pop
      // 096: aload 1
      // 097: aload 10
      // 099: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 09c: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 09f: aload 1
      // 0a0: checkcast java/lang/Throwable
      // 0a3: athrow
      // 0a4: aload 0
      // 0a5: getfield okhttp3/internal/cache/DiskLruCache.valueCount I
      // 0a8: istore 5
      // 0aa: iload 4
      // 0ac: istore 3
      // 0ad: iload 3
      // 0ae: iload 5
      // 0b0: if_icmpge 137
      // 0b3: aload 10
      // 0b5: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getDirtyFiles$okhttp ()Ljava/util/List;
      // 0b8: iload 3
      // 0b9: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 0be: checkcast java/io/File
      // 0c1: astore 1
      // 0c2: iload 2
      // 0c3: ifeq 127
      // 0c6: aload 10
      // 0c8: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getZombie$okhttp ()Z
      // 0cb: ifne 127
      // 0ce: aload 0
      // 0cf: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 0d2: aload 1
      // 0d3: invokeinterface okhttp3/internal/io/FileSystem.exists (Ljava/io/File;)Z 2
      // 0d8: ifeq 131
      // 0db: aload 10
      // 0dd: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getCleanFiles$okhttp ()Ljava/util/List;
      // 0e0: iload 3
      // 0e1: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 0e6: checkcast java/io/File
      // 0e9: astore 11
      // 0eb: aload 0
      // 0ec: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 0ef: aload 1
      // 0f0: aload 11
      // 0f2: invokeinterface okhttp3/internal/io/FileSystem.rename (Ljava/io/File;Ljava/io/File;)V 3
      // 0f7: aload 10
      // 0f9: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getLengths$okhttp ()[J
      // 0fc: iload 3
      // 0fd: laload
      // 0fe: lstore 6
      // 100: aload 0
      // 101: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 104: aload 11
      // 106: invokeinterface okhttp3/internal/io/FileSystem.size (Ljava/io/File;)J 2
      // 10b: lstore 8
      // 10d: aload 10
      // 10f: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getLengths$okhttp ()[J
      // 112: iload 3
      // 113: lload 8
      // 115: lastore
      // 116: aload 0
      // 117: aload 0
      // 118: getfield okhttp3/internal/cache/DiskLruCache.size J
      // 11b: lload 6
      // 11d: lsub
      // 11e: lload 8
      // 120: ladd
      // 121: putfield okhttp3/internal/cache/DiskLruCache.size J
      // 124: goto 131
      // 127: aload 0
      // 128: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 12b: aload 1
      // 12c: invokeinterface okhttp3/internal/io/FileSystem.delete (Ljava/io/File;)V 2
      // 131: iinc 3 1
      // 134: goto 0ad
      // 137: aconst_null
      // 138: checkcast okhttp3/internal/cache/DiskLruCache$Editor
      // 13b: astore 1
      // 13c: aload 10
      // 13e: aconst_null
      // 13f: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.setCurrentEditor$okhttp (Lokhttp3/internal/cache/DiskLruCache$Editor;)V
      // 142: aload 10
      // 144: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getZombie$okhttp ()Z
      // 147: ifeq 154
      // 14a: aload 0
      // 14b: aload 10
      // 14d: invokevirtual okhttp3/internal/cache/DiskLruCache.removeEntry$okhttp (Lokhttp3/internal/cache/DiskLruCache$Entry;)Z
      // 150: pop
      // 151: aload 0
      // 152: monitorexit
      // 153: return
      // 154: aload 0
      // 155: aload 0
      // 156: getfield okhttp3/internal/cache/DiskLruCache.redundantOpCount I
      // 159: bipush 1
      // 15a: iadd
      // 15b: putfield okhttp3/internal/cache/DiskLruCache.redundantOpCount I
      // 15e: aload 0
      // 15f: getfield okhttp3/internal/cache/DiskLruCache.journalWriter Lokio/BufferedSink;
      // 162: astore 1
      // 163: aload 1
      // 164: ifnonnull 16a
      // 167: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 16a: aload 10
      // 16c: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getReadable$okhttp ()Z
      // 16f: ifne 1af
      // 172: iload 2
      // 173: ifeq 179
      // 176: goto 1af
      // 179: aload 0
      // 17a: getfield okhttp3/internal/cache/DiskLruCache.lruEntries Ljava/util/LinkedHashMap;
      // 17d: aload 10
      // 17f: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getKey$okhttp ()Ljava/lang/String;
      // 182: invokevirtual java/util/LinkedHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 185: pop
      // 186: aload 1
      // 187: getstatic okhttp3/internal/cache/DiskLruCache.REMOVE Ljava/lang/String;
      // 18a: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 18f: bipush 32
      // 191: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 196: pop
      // 197: aload 1
      // 198: aload 10
      // 19a: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getKey$okhttp ()Ljava/lang/String;
      // 19d: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 1a2: pop
      // 1a3: aload 1
      // 1a4: bipush 10
      // 1a6: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 1ab: pop
      // 1ac: goto 1fa
      // 1af: aload 10
      // 1b1: bipush 1
      // 1b2: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.setReadable$okhttp (Z)V
      // 1b5: aload 1
      // 1b6: getstatic okhttp3/internal/cache/DiskLruCache.CLEAN Ljava/lang/String;
      // 1b9: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 1be: bipush 32
      // 1c0: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 1c5: pop
      // 1c6: aload 1
      // 1c7: aload 10
      // 1c9: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getKey$okhttp ()Ljava/lang/String;
      // 1cc: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 1d1: pop
      // 1d2: aload 10
      // 1d4: aload 1
      // 1d5: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.writeLengths$okhttp (Lokio/BufferedSink;)V
      // 1d8: aload 1
      // 1d9: bipush 10
      // 1db: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 1e0: pop
      // 1e1: iload 2
      // 1e2: ifeq 1fa
      // 1e5: aload 0
      // 1e6: getfield okhttp3/internal/cache/DiskLruCache.nextSequenceNumber J
      // 1e9: lstore 6
      // 1eb: aload 0
      // 1ec: lconst_1
      // 1ed: lload 6
      // 1ef: ladd
      // 1f0: putfield okhttp3/internal/cache/DiskLruCache.nextSequenceNumber J
      // 1f3: aload 10
      // 1f5: lload 6
      // 1f7: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.setSequenceNumber$okhttp (J)V
      // 1fa: aload 1
      // 1fb: invokeinterface okio/BufferedSink.flush ()V 1
      // 200: aload 0
      // 201: getfield okhttp3/internal/cache/DiskLruCache.size J
      // 204: aload 0
      // 205: getfield okhttp3/internal/cache/DiskLruCache.maxSize J
      // 208: lcmp
      // 209: ifgt 213
      // 20c: aload 0
      // 20d: invokespecial okhttp3/internal/cache/DiskLruCache.journalRebuildRequired ()Z
      // 210: ifeq 224
      // 213: aload 0
      // 214: getfield okhttp3/internal/cache/DiskLruCache.cleanupQueue Lokhttp3/internal/concurrent/TaskQueue;
      // 217: aload 0
      // 218: getfield okhttp3/internal/cache/DiskLruCache.cleanupTask Lokhttp3/internal/cache/DiskLruCache$cleanupTask$1;
      // 21b: checkcast okhttp3/internal/concurrent/Task
      // 21e: lconst_0
      // 21f: bipush 2
      // 220: aconst_null
      // 221: invokestatic okhttp3/internal/concurrent/TaskQueue.schedule$default (Lokhttp3/internal/concurrent/TaskQueue;Lokhttp3/internal/concurrent/Task;JILjava/lang/Object;)V
      // 224: aload 0
      // 225: monitorexit
      // 226: return
      // 227: new java/lang/IllegalStateException
      // 22a: astore 1
      // 22b: aload 1
      // 22c: ldc_w "Check failed."
      // 22f: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 232: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 235: aload 1
      // 236: checkcast java/lang/Throwable
      // 239: athrow
      // 23a: astore 1
      // 23b: aload 0
      // 23c: monitorexit
      // 23d: aload 1
      // 23e: athrow
   }

   @Throws(java/io/IOException::class)
   public fun delete() {
      this.close();
      this.fileSystem.deleteContents(this.directory);
   }

   @Throws(java/io/IOException::class)
   fun edit(var1: java.lang.String): DiskLruCache.Editor {
      return edit$default(this, var1, 0L, 2, null);
   }

   @Throws(java/io/IOException::class)
   public fun edit(key: String, expectedSequenceNumber: Long = ANY_SEQUENCE_NUMBER): okhttp3.internal.cache.DiskLruCache.Editor? {
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
      //
      // Bytecode:
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 1
      // 003: ldc_w "key"
      // 006: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 009: aload 0
      // 00a: invokevirtual okhttp3/internal/cache/DiskLruCache.initialize ()V
      // 00d: aload 0
      // 00e: invokespecial okhttp3/internal/cache/DiskLruCache.checkNotClosed ()V
      // 011: aload 0
      // 012: aload 1
      // 013: invokespecial okhttp3/internal/cache/DiskLruCache.validateKey (Ljava/lang/String;)V
      // 016: aload 0
      // 017: getfield okhttp3/internal/cache/DiskLruCache.lruEntries Ljava/util/LinkedHashMap;
      // 01a: aload 1
      // 01b: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 01e: checkcast okhttp3/internal/cache/DiskLruCache$Entry
      // 021: astore 9
      // 023: lload 2
      // 024: getstatic okhttp3/internal/cache/DiskLruCache.ANY_SEQUENCE_NUMBER J
      // 027: lcmp
      // 028: ifeq 042
      // 02b: aload 9
      // 02d: ifnull 03e
      // 030: aload 9
      // 032: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getSequenceNumber$okhttp ()J
      // 035: lstore 5
      // 037: lload 5
      // 039: lload 2
      // 03a: lcmp
      // 03b: ifeq 042
      // 03e: aload 0
      // 03f: monitorexit
      // 040: aconst_null
      // 041: areturn
      // 042: aload 9
      // 044: ifnull 051
      // 047: aload 9
      // 049: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getCurrentEditor$okhttp ()Lokhttp3/internal/cache/DiskLruCache$Editor;
      // 04c: astore 8
      // 04e: goto 054
      // 051: aconst_null
      // 052: astore 8
      // 054: aload 8
      // 056: ifnull 05d
      // 059: aload 0
      // 05a: monitorexit
      // 05b: aconst_null
      // 05c: areturn
      // 05d: aload 9
      // 05f: ifnull 072
      // 062: aload 9
      // 064: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getLockingSourceCount$okhttp ()I
      // 067: istore 4
      // 069: iload 4
      // 06b: ifeq 072
      // 06e: aload 0
      // 06f: monitorexit
      // 070: aconst_null
      // 071: areturn
      // 072: aload 0
      // 073: getfield okhttp3/internal/cache/DiskLruCache.mostRecentTrimFailed Z
      // 076: ifne 100
      // 079: aload 0
      // 07a: getfield okhttp3/internal/cache/DiskLruCache.mostRecentRebuildFailed Z
      // 07d: ifeq 083
      // 080: goto 100
      // 083: aload 0
      // 084: getfield okhttp3/internal/cache/DiskLruCache.journalWriter Lokio/BufferedSink;
      // 087: astore 8
      // 089: aload 8
      // 08b: ifnonnull 091
      // 08e: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 091: aload 8
      // 093: getstatic okhttp3/internal/cache/DiskLruCache.DIRTY Ljava/lang/String;
      // 096: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 09b: bipush 32
      // 09d: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 0a2: aload 1
      // 0a3: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 0a8: bipush 10
      // 0aa: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 0af: pop
      // 0b0: aload 8
      // 0b2: invokeinterface okio/BufferedSink.flush ()V 1
      // 0b7: aload 0
      // 0b8: getfield okhttp3/internal/cache/DiskLruCache.hasJournalErrors Z
      // 0bb: istore 7
      // 0bd: iload 7
      // 0bf: ifeq 0c6
      // 0c2: aload 0
      // 0c3: monitorexit
      // 0c4: aconst_null
      // 0c5: areturn
      // 0c6: aload 9
      // 0c8: astore 8
      // 0ca: aload 9
      // 0cc: ifnonnull 0eb
      // 0cf: new okhttp3/internal/cache/DiskLruCache$Entry
      // 0d2: astore 8
      // 0d4: aload 8
      // 0d6: aload 0
      // 0d7: aload 1
      // 0d8: invokespecial okhttp3/internal/cache/DiskLruCache$Entry.<init> (Lokhttp3/internal/cache/DiskLruCache;Ljava/lang/String;)V
      // 0db: aload 0
      // 0dc: getfield okhttp3/internal/cache/DiskLruCache.lruEntries Ljava/util/LinkedHashMap;
      // 0df: checkcast java/util/Map
      // 0e2: aload 1
      // 0e3: aload 8
      // 0e5: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0ea: pop
      // 0eb: new okhttp3/internal/cache/DiskLruCache$Editor
      // 0ee: astore 1
      // 0ef: aload 1
      // 0f0: aload 0
      // 0f1: aload 8
      // 0f3: invokespecial okhttp3/internal/cache/DiskLruCache$Editor.<init> (Lokhttp3/internal/cache/DiskLruCache;Lokhttp3/internal/cache/DiskLruCache$Entry;)V
      // 0f6: aload 8
      // 0f8: aload 1
      // 0f9: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.setCurrentEditor$okhttp (Lokhttp3/internal/cache/DiskLruCache$Editor;)V
      // 0fc: aload 0
      // 0fd: monitorexit
      // 0fe: aload 1
      // 0ff: areturn
      // 100: aload 0
      // 101: getfield okhttp3/internal/cache/DiskLruCache.cleanupQueue Lokhttp3/internal/concurrent/TaskQueue;
      // 104: aload 0
      // 105: getfield okhttp3/internal/cache/DiskLruCache.cleanupTask Lokhttp3/internal/cache/DiskLruCache$cleanupTask$1;
      // 108: checkcast okhttp3/internal/concurrent/Task
      // 10b: lconst_0
      // 10c: bipush 2
      // 10d: aconst_null
      // 10e: invokestatic okhttp3/internal/concurrent/TaskQueue.schedule$default (Lokhttp3/internal/concurrent/TaskQueue;Lokhttp3/internal/concurrent/Task;JILjava/lang/Object;)V
      // 111: aload 0
      // 112: monitorexit
      // 113: aconst_null
      // 114: areturn
      // 115: astore 1
      // 116: aload 0
      // 117: monitorexit
      // 118: aload 1
      // 119: athrow
   }

   @Throws(java/io/IOException::class)
   public fun evictAll() {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: invokevirtual okhttp3/internal/cache/DiskLruCache.initialize ()V
      // 06: aload 0
      // 07: getfield okhttp3/internal/cache/DiskLruCache.lruEntries Ljava/util/LinkedHashMap;
      // 0a: invokevirtual java/util/LinkedHashMap.values ()Ljava/util/Collection;
      // 0d: astore 3
      // 0e: aload 3
      // 0f: ldc_w "lruEntries.values"
      // 12: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 15: aload 3
      // 16: bipush 0
      // 17: anewarray 18
      // 1a: invokeinterface java/util/Collection.toArray ([Ljava/lang/Object;)[Ljava/lang/Object; 2
      // 1f: astore 3
      // 20: aload 3
      // 21: ifnull 55
      // 24: aload 3
      // 25: checkcast [Lokhttp3/internal/cache/DiskLruCache$Entry;
      // 28: astore 4
      // 2a: aload 4
      // 2c: arraylength
      // 2d: istore 2
      // 2e: bipush 0
      // 2f: istore 1
      // 30: iload 1
      // 31: iload 2
      // 32: if_icmpge 4d
      // 35: aload 4
      // 37: iload 1
      // 38: aaload
      // 39: astore 3
      // 3a: aload 3
      // 3b: ldc_w "entry"
      // 3e: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 41: aload 0
      // 42: aload 3
      // 43: invokevirtual okhttp3/internal/cache/DiskLruCache.removeEntry$okhttp (Lokhttp3/internal/cache/DiskLruCache$Entry;)Z
      // 46: pop
      // 47: iinc 1 1
      // 4a: goto 30
      // 4d: aload 0
      // 4e: bipush 0
      // 4f: putfield okhttp3/internal/cache/DiskLruCache.mostRecentTrimFailed Z
      // 52: aload 0
      // 53: monitorexit
      // 54: return
      // 55: new kotlin/TypeCastException
      // 58: astore 3
      // 59: aload 3
      // 5a: ldc_w "null cannot be cast to non-null type kotlin.Array<T>"
      // 5d: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
      // 60: aload 3
      // 61: athrow
      // 62: astore 3
      // 63: aload 0
      // 64: monitorexit
      // 65: aload 3
      // 66: athrow
   }

   @Throws(java/io/IOException::class)
   public override fun flush() {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okhttp3/internal/cache/DiskLruCache.initialized Z
      // 06: istore 1
      // 07: iload 1
      // 08: ifne 0e
      // 0b: aload 0
      // 0c: monitorexit
      // 0d: return
      // 0e: aload 0
      // 0f: invokespecial okhttp3/internal/cache/DiskLruCache.checkNotClosed ()V
      // 12: aload 0
      // 13: invokevirtual okhttp3/internal/cache/DiskLruCache.trimToSize ()V
      // 16: aload 0
      // 17: getfield okhttp3/internal/cache/DiskLruCache.journalWriter Lokio/BufferedSink;
      // 1a: astore 2
      // 1b: aload 2
      // 1c: ifnonnull 22
      // 1f: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 22: aload 2
      // 23: invokeinterface okio/BufferedSink.flush ()V 1
      // 28: aload 0
      // 29: monitorexit
      // 2a: return
      // 2b: astore 2
      // 2c: aload 0
      // 2d: monitorexit
      // 2e: aload 2
      // 2f: athrow
   }

   @Throws(java/io/IOException::class)
   public operator fun get(key: String): okhttp3.internal.cache.DiskLruCache.Snapshot? {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: ldc_w "key"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: invokevirtual okhttp3/internal/cache/DiskLruCache.initialize ()V
      // 0d: aload 0
      // 0e: invokespecial okhttp3/internal/cache/DiskLruCache.checkNotClosed ()V
      // 11: aload 0
      // 12: aload 1
      // 13: invokespecial okhttp3/internal/cache/DiskLruCache.validateKey (Ljava/lang/String;)V
      // 16: aload 0
      // 17: getfield okhttp3/internal/cache/DiskLruCache.lruEntries Ljava/util/LinkedHashMap;
      // 1a: aload 1
      // 1b: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 1e: checkcast okhttp3/internal/cache/DiskLruCache$Entry
      // 21: astore 2
      // 22: aload 2
      // 23: ifnull 8a
      // 26: aload 2
      // 27: ldc_w "lruEntries[key] ?: return null"
      // 2a: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 2d: aload 2
      // 2e: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.snapshot$okhttp ()Lokhttp3/internal/cache/DiskLruCache$Snapshot;
      // 31: astore 2
      // 32: aload 2
      // 33: ifnull 86
      // 36: aload 0
      // 37: aload 0
      // 38: getfield okhttp3/internal/cache/DiskLruCache.redundantOpCount I
      // 3b: bipush 1
      // 3c: iadd
      // 3d: putfield okhttp3/internal/cache/DiskLruCache.redundantOpCount I
      // 40: aload 0
      // 41: getfield okhttp3/internal/cache/DiskLruCache.journalWriter Lokio/BufferedSink;
      // 44: astore 3
      // 45: aload 3
      // 46: ifnonnull 4c
      // 49: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 4c: aload 3
      // 4d: getstatic okhttp3/internal/cache/DiskLruCache.READ Ljava/lang/String;
      // 50: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 55: bipush 32
      // 57: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 5c: aload 1
      // 5d: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 62: bipush 10
      // 64: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 69: pop
      // 6a: aload 0
      // 6b: invokespecial okhttp3/internal/cache/DiskLruCache.journalRebuildRequired ()Z
      // 6e: ifeq 82
      // 71: aload 0
      // 72: getfield okhttp3/internal/cache/DiskLruCache.cleanupQueue Lokhttp3/internal/concurrent/TaskQueue;
      // 75: aload 0
      // 76: getfield okhttp3/internal/cache/DiskLruCache.cleanupTask Lokhttp3/internal/cache/DiskLruCache$cleanupTask$1;
      // 79: checkcast okhttp3/internal/concurrent/Task
      // 7c: lconst_0
      // 7d: bipush 2
      // 7e: aconst_null
      // 7f: invokestatic okhttp3/internal/concurrent/TaskQueue.schedule$default (Lokhttp3/internal/concurrent/TaskQueue;Lokhttp3/internal/concurrent/Task;JILjava/lang/Object;)V
      // 82: aload 0
      // 83: monitorexit
      // 84: aload 2
      // 85: areturn
      // 86: aload 0
      // 87: monitorexit
      // 88: aconst_null
      // 89: areturn
      // 8a: aload 0
      // 8b: monitorexit
      // 8c: aconst_null
      // 8d: areturn
      // 8e: astore 1
      // 8f: aload 0
      // 90: monitorexit
      // 91: aload 1
      // 92: athrow
   }

   @Throws(java/io/IOException::class)
   public fun initialize() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
      //
      // Bytecode:
      // 000: aload 0
      // 001: monitorenter
      // 002: getstatic okhttp3/internal/Util.assertionsEnabled Z
      // 005: ifeq 053
      // 008: aload 0
      // 009: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
      // 00c: ifeq 012
      // 00f: goto 053
      // 012: new java/lang/AssertionError
      // 015: astore 3
      // 016: new java/lang/StringBuilder
      // 019: astore 2
      // 01a: aload 2
      // 01b: ldc_w "Thread "
      // 01e: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 021: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 024: astore 4
      // 026: aload 4
      // 028: ldc_w "Thread.currentThread()"
      // 02b: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 02e: aload 2
      // 02f: aload 4
      // 031: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
      // 034: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 037: pop
      // 038: aload 2
      // 039: ldc_w " MUST hold lock on "
      // 03c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 03f: pop
      // 040: aload 2
      // 041: aload 0
      // 042: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 045: pop
      // 046: aload 3
      // 047: aload 2
      // 048: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 04b: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // 04e: aload 3
      // 04f: checkcast java/lang/Throwable
      // 052: athrow
      // 053: aload 0
      // 054: getfield okhttp3/internal/cache/DiskLruCache.initialized Z
      // 057: istore 1
      // 058: iload 1
      // 059: ifeq 05f
      // 05c: aload 0
      // 05d: monitorexit
      // 05e: return
      // 05f: aload 0
      // 060: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 063: aload 0
      // 064: getfield okhttp3/internal/cache/DiskLruCache.journalFileBackup Ljava/io/File;
      // 067: invokeinterface okhttp3/internal/io/FileSystem.exists (Ljava/io/File;)Z 2
      // 06c: ifeq 0a0
      // 06f: aload 0
      // 070: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 073: aload 0
      // 074: getfield okhttp3/internal/cache/DiskLruCache.journalFile Ljava/io/File;
      // 077: invokeinterface okhttp3/internal/io/FileSystem.exists (Ljava/io/File;)Z 2
      // 07c: ifeq 08f
      // 07f: aload 0
      // 080: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 083: aload 0
      // 084: getfield okhttp3/internal/cache/DiskLruCache.journalFileBackup Ljava/io/File;
      // 087: invokeinterface okhttp3/internal/io/FileSystem.delete (Ljava/io/File;)V 2
      // 08c: goto 0a0
      // 08f: aload 0
      // 090: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 093: aload 0
      // 094: getfield okhttp3/internal/cache/DiskLruCache.journalFileBackup Ljava/io/File;
      // 097: aload 0
      // 098: getfield okhttp3/internal/cache/DiskLruCache.journalFile Ljava/io/File;
      // 09b: invokeinterface okhttp3/internal/io/FileSystem.rename (Ljava/io/File;Ljava/io/File;)V 3
      // 0a0: aload 0
      // 0a1: aload 0
      // 0a2: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 0a5: aload 0
      // 0a6: getfield okhttp3/internal/cache/DiskLruCache.journalFileBackup Ljava/io/File;
      // 0a9: invokestatic okhttp3/internal/Util.isCivilized (Lokhttp3/internal/io/FileSystem;Ljava/io/File;)Z
      // 0ac: putfield okhttp3/internal/cache/DiskLruCache.civilizedFileSystem Z
      // 0af: aload 0
      // 0b0: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 0b3: aload 0
      // 0b4: getfield okhttp3/internal/cache/DiskLruCache.journalFile Ljava/io/File;
      // 0b7: invokeinterface okhttp3/internal/io/FileSystem.exists (Ljava/io/File;)Z 2
      // 0bc: istore 1
      // 0bd: iload 1
      // 0be: ifeq 129
      // 0c1: aload 0
      // 0c2: invokespecial okhttp3/internal/cache/DiskLruCache.readJournal ()V
      // 0c5: aload 0
      // 0c6: invokespecial okhttp3/internal/cache/DiskLruCache.processJournal ()V
      // 0c9: aload 0
      // 0ca: bipush 1
      // 0cb: putfield okhttp3/internal/cache/DiskLruCache.initialized Z
      // 0ce: aload 0
      // 0cf: monitorexit
      // 0d0: return
      // 0d1: astore 2
      // 0d2: getstatic okhttp3/internal/platform/Platform.Companion Lokhttp3/internal/platform/Platform$Companion;
      // 0d5: invokevirtual okhttp3/internal/platform/Platform$Companion.get ()Lokhttp3/internal/platform/Platform;
      // 0d8: astore 4
      // 0da: new java/lang/StringBuilder
      // 0dd: astore 3
      // 0de: aload 3
      // 0df: ldc_w "DiskLruCache "
      // 0e2: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 0e5: aload 3
      // 0e6: aload 0
      // 0e7: getfield okhttp3/internal/cache/DiskLruCache.directory Ljava/io/File;
      // 0ea: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 0ed: pop
      // 0ee: aload 3
      // 0ef: ldc_w " is corrupt: "
      // 0f2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0f5: pop
      // 0f6: aload 3
      // 0f7: aload 2
      // 0f8: invokevirtual java/io/IOException.getMessage ()Ljava/lang/String;
      // 0fb: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0fe: pop
      // 0ff: aload 3
      // 100: ldc_w ", removing"
      // 103: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 106: pop
      // 107: aload 4
      // 109: aload 3
      // 10a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 10d: bipush 5
      // 10e: aload 2
      // 10f: checkcast java/lang/Throwable
      // 112: invokevirtual okhttp3/internal/platform/Platform.log (Ljava/lang/String;ILjava/lang/Throwable;)V
      // 115: aload 0
      // 116: invokevirtual okhttp3/internal/cache/DiskLruCache.delete ()V
      // 119: aload 0
      // 11a: bipush 0
      // 11b: putfield okhttp3/internal/cache/DiskLruCache.closed Z
      // 11e: goto 129
      // 121: astore 2
      // 122: aload 0
      // 123: bipush 0
      // 124: putfield okhttp3/internal/cache/DiskLruCache.closed Z
      // 127: aload 2
      // 128: athrow
      // 129: aload 0
      // 12a: invokevirtual okhttp3/internal/cache/DiskLruCache.rebuildJournal$okhttp ()V
      // 12d: aload 0
      // 12e: bipush 1
      // 12f: putfield okhttp3/internal/cache/DiskLruCache.initialized Z
      // 132: aload 0
      // 133: monitorexit
      // 134: return
      // 135: astore 2
      // 136: aload 0
      // 137: monitorexit
      // 138: aload 2
      // 139: athrow
   }

   public fun isClosed(): Boolean {
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
      //
      // Bytecode:
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: getfield okhttp3/internal/cache/DiskLruCache.closed Z
      // 6: istore 1
      // 7: aload 0
      // 8: monitorexit
      // 9: iload 1
      // a: ireturn
      // b: astore 2
      // c: aload 0
      // d: monitorexit
      // e: aload 2
      // f: athrow
   }

   @Throws(java/io/IOException::class)
   internal fun rebuildJournal() {
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
      //
      // Bytecode:
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 0
      // 003: getfield okhttp3/internal/cache/DiskLruCache.journalWriter Lokio/BufferedSink;
      // 006: astore 1
      // 007: aload 1
      // 008: ifnull 011
      // 00b: aload 1
      // 00c: invokeinterface okio/BufferedSink.close ()V 1
      // 011: aload 0
      // 012: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 015: aload 0
      // 016: getfield okhttp3/internal/cache/DiskLruCache.journalFileTmp Ljava/io/File;
      // 019: invokeinterface okhttp3/internal/io/FileSystem.sink (Ljava/io/File;)Lokio/Sink; 2
      // 01e: invokestatic okio/Okio.buffer (Lokio/Sink;)Lokio/BufferedSink;
      // 021: checkcast java/io/Closeable
      // 024: astore 1
      // 025: aconst_null
      // 026: checkcast java/lang/Throwable
      // 029: astore 2
      // 02a: aload 1
      // 02b: checkcast okio/BufferedSink
      // 02e: astore 3
      // 02f: aload 3
      // 030: getstatic okhttp3/internal/cache/DiskLruCache.MAGIC Ljava/lang/String;
      // 033: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 038: bipush 10
      // 03a: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 03f: pop
      // 040: aload 3
      // 041: getstatic okhttp3/internal/cache/DiskLruCache.VERSION_1 Ljava/lang/String;
      // 044: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 049: bipush 10
      // 04b: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 050: pop
      // 051: aload 3
      // 052: aload 0
      // 053: getfield okhttp3/internal/cache/DiskLruCache.appVersion I
      // 056: i2l
      // 057: invokeinterface okio/BufferedSink.writeDecimalLong (J)Lokio/BufferedSink; 3
      // 05c: bipush 10
      // 05e: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 063: pop
      // 064: aload 3
      // 065: aload 0
      // 066: getfield okhttp3/internal/cache/DiskLruCache.valueCount I
      // 069: i2l
      // 06a: invokeinterface okio/BufferedSink.writeDecimalLong (J)Lokio/BufferedSink; 3
      // 06f: bipush 10
      // 071: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 076: pop
      // 077: aload 3
      // 078: bipush 10
      // 07a: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 07f: pop
      // 080: aload 0
      // 081: getfield okhttp3/internal/cache/DiskLruCache.lruEntries Ljava/util/LinkedHashMap;
      // 084: invokevirtual java/util/LinkedHashMap.values ()Ljava/util/Collection;
      // 087: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 08c: astore 2
      // 08d: aload 2
      // 08e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 093: ifeq 101
      // 096: aload 2
      // 097: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 09c: checkcast okhttp3/internal/cache/DiskLruCache$Entry
      // 09f: astore 4
      // 0a1: aload 4
      // 0a3: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getCurrentEditor$okhttp ()Lokhttp3/internal/cache/DiskLruCache$Editor;
      // 0a6: ifnull 0d2
      // 0a9: aload 3
      // 0aa: getstatic okhttp3/internal/cache/DiskLruCache.DIRTY Ljava/lang/String;
      // 0ad: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 0b2: bipush 32
      // 0b4: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 0b9: pop
      // 0ba: aload 3
      // 0bb: aload 4
      // 0bd: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getKey$okhttp ()Ljava/lang/String;
      // 0c0: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 0c5: pop
      // 0c6: aload 3
      // 0c7: bipush 10
      // 0c9: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 0ce: pop
      // 0cf: goto 08d
      // 0d2: aload 3
      // 0d3: getstatic okhttp3/internal/cache/DiskLruCache.CLEAN Ljava/lang/String;
      // 0d6: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 0db: bipush 32
      // 0dd: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 0e2: pop
      // 0e3: aload 3
      // 0e4: aload 4
      // 0e6: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getKey$okhttp ()Ljava/lang/String;
      // 0e9: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
      // 0ee: pop
      // 0ef: aload 4
      // 0f1: aload 3
      // 0f2: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.writeLengths$okhttp (Lokio/BufferedSink;)V
      // 0f5: aload 3
      // 0f6: bipush 10
      // 0f8: invokeinterface okio/BufferedSink.writeByte (I)Lokio/BufferedSink; 2
      // 0fd: pop
      // 0fe: goto 08d
      // 101: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 104: astore 2
      // 105: aload 1
      // 106: aconst_null
      // 107: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 10a: aload 0
      // 10b: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 10e: aload 0
      // 10f: getfield okhttp3/internal/cache/DiskLruCache.journalFile Ljava/io/File;
      // 112: invokeinterface okhttp3/internal/io/FileSystem.exists (Ljava/io/File;)Z 2
      // 117: ifeq 12b
      // 11a: aload 0
      // 11b: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 11e: aload 0
      // 11f: getfield okhttp3/internal/cache/DiskLruCache.journalFile Ljava/io/File;
      // 122: aload 0
      // 123: getfield okhttp3/internal/cache/DiskLruCache.journalFileBackup Ljava/io/File;
      // 126: invokeinterface okhttp3/internal/io/FileSystem.rename (Ljava/io/File;Ljava/io/File;)V 3
      // 12b: aload 0
      // 12c: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 12f: aload 0
      // 130: getfield okhttp3/internal/cache/DiskLruCache.journalFileTmp Ljava/io/File;
      // 133: aload 0
      // 134: getfield okhttp3/internal/cache/DiskLruCache.journalFile Ljava/io/File;
      // 137: invokeinterface okhttp3/internal/io/FileSystem.rename (Ljava/io/File;Ljava/io/File;)V 3
      // 13c: aload 0
      // 13d: getfield okhttp3/internal/cache/DiskLruCache.fileSystem Lokhttp3/internal/io/FileSystem;
      // 140: aload 0
      // 141: getfield okhttp3/internal/cache/DiskLruCache.journalFileBackup Ljava/io/File;
      // 144: invokeinterface okhttp3/internal/io/FileSystem.delete (Ljava/io/File;)V 2
      // 149: aload 0
      // 14a: aload 0
      // 14b: invokespecial okhttp3/internal/cache/DiskLruCache.newJournalWriter ()Lokio/BufferedSink;
      // 14e: putfield okhttp3/internal/cache/DiskLruCache.journalWriter Lokio/BufferedSink;
      // 151: aload 0
      // 152: bipush 0
      // 153: putfield okhttp3/internal/cache/DiskLruCache.hasJournalErrors Z
      // 156: aload 0
      // 157: bipush 0
      // 158: putfield okhttp3/internal/cache/DiskLruCache.mostRecentRebuildFailed Z
      // 15b: aload 0
      // 15c: monitorexit
      // 15d: return
      // 15e: astore 3
      // 15f: aload 3
      // 160: athrow
      // 161: astore 2
      // 162: aload 1
      // 163: aload 3
      // 164: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 167: aload 2
      // 168: athrow
      // 169: astore 1
      // 16a: aload 0
      // 16b: monitorexit
      // 16c: aload 1
      // 16d: athrow
   }

   @Throws(java/io/IOException::class)
   public fun remove(key: String): Boolean {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: ldc_w "key"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: invokevirtual okhttp3/internal/cache/DiskLruCache.initialize ()V
      // 0d: aload 0
      // 0e: invokespecial okhttp3/internal/cache/DiskLruCache.checkNotClosed ()V
      // 11: aload 0
      // 12: aload 1
      // 13: invokespecial okhttp3/internal/cache/DiskLruCache.validateKey (Ljava/lang/String;)V
      // 16: aload 0
      // 17: getfield okhttp3/internal/cache/DiskLruCache.lruEntries Ljava/util/LinkedHashMap;
      // 1a: aload 1
      // 1b: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 1e: checkcast okhttp3/internal/cache/DiskLruCache$Entry
      // 21: astore 1
      // 22: aload 1
      // 23: ifnull 4c
      // 26: aload 1
      // 27: ldc_w "lruEntries[key] ?: return false"
      // 2a: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 2d: aload 0
      // 2e: aload 1
      // 2f: invokevirtual okhttp3/internal/cache/DiskLruCache.removeEntry$okhttp (Lokhttp3/internal/cache/DiskLruCache$Entry;)Z
      // 32: istore 2
      // 33: iload 2
      // 34: ifeq 48
      // 37: aload 0
      // 38: getfield okhttp3/internal/cache/DiskLruCache.size J
      // 3b: aload 0
      // 3c: getfield okhttp3/internal/cache/DiskLruCache.maxSize J
      // 3f: lcmp
      // 40: ifgt 48
      // 43: aload 0
      // 44: bipush 0
      // 45: putfield okhttp3/internal/cache/DiskLruCache.mostRecentTrimFailed Z
      // 48: aload 0
      // 49: monitorexit
      // 4a: iload 2
      // 4b: ireturn
      // 4c: aload 0
      // 4d: monitorexit
      // 4e: bipush 0
      // 4f: ireturn
      // 50: astore 1
      // 51: aload 0
      // 52: monitorexit
      // 53: aload 1
      // 54: athrow
   }

   @Throws(java/io/IOException::class)
   internal fun removeEntry(entry: okhttp3.internal.cache.DiskLruCache.Entry): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "entry");
      if (!this.civilizedFileSystem) {
         if (var1.getLockingSourceCount$okhttp() > 0) {
            val var4: BufferedSink = this.journalWriter;
            if (this.journalWriter != null) {
               this.journalWriter.writeUtf8(DIRTY);
               var4.writeByte(32);
               var4.writeUtf8(var1.getKey$okhttp());
               var4.writeByte(10);
               var4.flush();
            }
         }

         if (var1.getLockingSourceCount$okhttp() > 0 || var1.getCurrentEditor$okhttp() != null) {
            var1.setZombie$okhttp(true);
            return true;
         }
      }

      val var5: DiskLruCache.Editor = var1.getCurrentEditor$okhttp();
      if (var5 != null) {
         var5.detach$okhttp();
      }

      val var3: Int = this.valueCount;

      for (int var2 = 0; var2 < var3; var2++) {
         this.fileSystem.delete(var1.getCleanFiles$okhttp().get(var2));
         this.size = this.size - var1.getLengths$okhttp()[var2];
         var1.getLengths$okhttp()[var2] = 0L;
      }

      this.redundantOpCount++;
      val var6: BufferedSink = this.journalWriter;
      if (this.journalWriter != null) {
         this.journalWriter.writeUtf8(REMOVE);
         var6.writeByte(32);
         var6.writeUtf8(var1.getKey$okhttp());
         var6.writeByte(10);
      }

      this.lruEntries.remove(var1.getKey$okhttp());
      if (this.journalRebuildRequired()) {
         TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
      }

      return true;
   }

   @Throws(java/io/IOException::class)
   public fun size(): Long {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: invokevirtual okhttp3/internal/cache/DiskLruCache.initialize ()V
      // 06: aload 0
      // 07: getfield okhttp3/internal/cache/DiskLruCache.size J
      // 0a: lstore 1
      // 0b: aload 0
      // 0c: monitorexit
      // 0d: lload 1
      // 0e: lreturn
      // 0f: astore 3
      // 10: aload 0
      // 11: monitorexit
      // 12: aload 3
      // 13: athrow
   }

   @Throws(java/io/IOException::class)
   public fun snapshots(): MutableIterator<okhttp3.internal.cache.DiskLruCache.Snapshot> {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: invokevirtual okhttp3/internal/cache/DiskLruCache.initialize ()V
      // 06: new okhttp3/internal/cache/DiskLruCache$snapshots$1
      // 09: astore 1
      // 0a: aload 1
      // 0b: aload 0
      // 0c: invokespecial okhttp3/internal/cache/DiskLruCache$snapshots$1.<init> (Lokhttp3/internal/cache/DiskLruCache;)V
      // 0f: aload 1
      // 10: checkcast java/util/Iterator
      // 13: astore 1
      // 14: aload 0
      // 15: monitorexit
      // 16: aload 1
      // 17: areturn
      // 18: astore 1
      // 19: aload 0
      // 1a: monitorexit
      // 1b: aload 1
      // 1c: athrow
   }

   @Throws(java/io/IOException::class)
   public fun trimToSize() {
      while (this.size > this.maxSize) {
         if (!this.removeOldestEntry()) {
            return;
         }
      }

      this.mostRecentTrimFailed = false;
   }

   public companion object {
      public final val ANY_SEQUENCE_NUMBER: Long
      public final val CLEAN: String
      public final val DIRTY: String
      public final val JOURNAL_FILE: String
      public final val JOURNAL_FILE_BACKUP: String
      public final val JOURNAL_FILE_TEMP: String
      public final val LEGAL_KEY_PATTERN: Regex
      public final val MAGIC: String
      public final val READ: String
      public final val REMOVE: String
      public final val VERSION_1: String
   }

   public inner class Editor internal constructor(entry: okhttp3.internal.cache.DiskLruCache.Entry) {
      private final var done: Boolean
      internal final val entry: okhttp3.internal.cache.DiskLruCache.Entry
      internal final val written: BooleanArray?

      init {
         Intrinsics.checkParameterIsNotNull(var2, "entry");
         this.this$0 = var1;
         super();
         this.entry = var2;
         val var3: BooleanArray;
         if (var2.getReadable$okhttp()) {
            var3 = null;
         } else {
            var3 = new boolean[var1.getValueCount$okhttp()];
         }

         this.written = var3;
      }

      @Throws(java/io/IOException::class)
      public fun abort() {
         synchronized (this.this$0) {
            if (this.done) {
               throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
            }

            val var2: DiskLruCache.Editor = this.entry.getCurrentEditor$okhttp();
            val var3: DiskLruCache.Editor = this;
            if (var2 == this) {
               this.this$0.completeEdit$okhttp(this, false);
            }

            this.done = true;
         }
      }

      @Throws(java/io/IOException::class)
      public fun commit() {
         synchronized (this.this$0) {
            if (this.done) {
               throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
            }

            val var2: DiskLruCache.Editor = this.entry.getCurrentEditor$okhttp();
            val var3: DiskLruCache.Editor = this;
            if (var2 == this) {
               this.this$0.completeEdit$okhttp(this, true);
            }

            this.done = true;
         }
      }

      internal fun detach() {
         val var2: DiskLruCache.Editor = this.entry.getCurrentEditor$okhttp();
         val var1: DiskLruCache.Editor = this;
         if (var2 == this) {
            if (DiskLruCache.access$getCivilizedFileSystem$p(this.this$0)) {
               this.this$0.completeEdit$okhttp(this, false);
            } else {
               this.entry.setZombie$okhttp(true);
            }
         }
      }

      public fun newSink(index: Int): Sink {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.RuntimeException: parsing failure!
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield okhttp3/internal/cache/DiskLruCache$Editor.this$0 Lokhttp3/internal/cache/DiskLruCache;
         // 04: astore 2
         // 05: aload 2
         // 06: monitorenter
         // 07: aload 0
         // 08: getfield okhttp3/internal/cache/DiskLruCache$Editor.done Z
         // 0b: ifne 93
         // 0e: aload 0
         // 0f: getfield okhttp3/internal/cache/DiskLruCache$Editor.entry Lokhttp3/internal/cache/DiskLruCache$Entry;
         // 12: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getCurrentEditor$okhttp ()Lokhttp3/internal/cache/DiskLruCache$Editor;
         // 15: astore 3
         // 16: aload 0
         // 17: checkcast okhttp3/internal/cache/DiskLruCache$Editor
         // 1a: astore 4
         // 1c: aload 3
         // 1d: aload 0
         // 1e: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 21: ifne 2c
         // 24: invokestatic okio/Okio.blackhole ()Lokio/Sink;
         // 27: astore 3
         // 28: aload 2
         // 29: monitorexit
         // 2a: aload 3
         // 2b: areturn
         // 2c: aload 0
         // 2d: getfield okhttp3/internal/cache/DiskLruCache$Editor.entry Lokhttp3/internal/cache/DiskLruCache$Entry;
         // 30: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getReadable$okhttp ()Z
         // 33: ifne 46
         // 36: aload 0
         // 37: getfield okhttp3/internal/cache/DiskLruCache$Editor.written [Z
         // 3a: astore 3
         // 3b: aload 3
         // 3c: ifnonnull 42
         // 3f: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
         // 42: aload 3
         // 43: iload 1
         // 44: bipush 1
         // 45: bastore
         // 46: aload 0
         // 47: getfield okhttp3/internal/cache/DiskLruCache$Editor.entry Lokhttp3/internal/cache/DiskLruCache$Entry;
         // 4a: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getDirtyFiles$okhttp ()Ljava/util/List;
         // 4d: iload 1
         // 4e: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
         // 53: checkcast java/io/File
         // 56: astore 3
         // 57: aload 0
         // 58: getfield okhttp3/internal/cache/DiskLruCache$Editor.this$0 Lokhttp3/internal/cache/DiskLruCache;
         // 5b: invokevirtual okhttp3/internal/cache/DiskLruCache.getFileSystem$okhttp ()Lokhttp3/internal/io/FileSystem;
         // 5e: aload 3
         // 5f: invokeinterface okhttp3/internal/io/FileSystem.sink (Ljava/io/File;)Lokio/Sink; 2
         // 64: astore 4
         // 66: new okhttp3/internal/cache/FaultHidingSink
         // 69: astore 3
         // 6a: new okhttp3/internal/cache/DiskLruCache$Editor$newSink$$inlined$synchronized$lambda$1
         // 6d: astore 5
         // 6f: aload 5
         // 71: aload 0
         // 72: iload 1
         // 73: invokespecial okhttp3/internal/cache/DiskLruCache$Editor$newSink$$inlined$synchronized$lambda$1.<init> (Lokhttp3/internal/cache/DiskLruCache$Editor;I)V
         // 76: aload 3
         // 77: aload 4
         // 79: aload 5
         // 7b: checkcast kotlin/jvm/functions/Function1
         // 7e: invokespecial okhttp3/internal/cache/FaultHidingSink.<init> (Lokio/Sink;Lkotlin/jvm/functions/Function1;)V
         // 81: aload 3
         // 82: checkcast okio/Sink
         // 85: astore 3
         // 86: aload 2
         // 87: monitorexit
         // 88: aload 3
         // 89: areturn
         // 8a: astore 3
         // 8b: invokestatic okio/Okio.blackhole ()Lokio/Sink;
         // 8e: astore 3
         // 8f: aload 2
         // 90: monitorexit
         // 91: aload 3
         // 92: areturn
         // 93: new java/lang/IllegalStateException
         // 96: astore 3
         // 97: aload 3
         // 98: ldc "Check failed."
         // 9a: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
         // 9d: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
         // a0: aload 3
         // a1: checkcast java/lang/Throwable
         // a4: athrow
         // a5: astore 3
         // a6: aload 2
         // a7: monitorexit
         // a8: aload 3
         // a9: athrow
      }

      public fun newSource(index: Int): Source? {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.RuntimeException: parsing failure!
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield okhttp3/internal/cache/DiskLruCache$Editor.this$0 Lokhttp3/internal/cache/DiskLruCache;
         // 04: astore 5
         // 06: aload 5
         // 08: monitorenter
         // 09: aload 0
         // 0a: getfield okhttp3/internal/cache/DiskLruCache$Editor.done Z
         // 0d: ifne 70
         // 10: aload 0
         // 11: getfield okhttp3/internal/cache/DiskLruCache$Editor.entry Lokhttp3/internal/cache/DiskLruCache$Entry;
         // 14: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getReadable$okhttp ()Z
         // 17: istore 2
         // 18: aconst_null
         // 19: astore 3
         // 1a: iload 2
         // 1b: ifeq 6b
         // 1e: aload 0
         // 1f: getfield okhttp3/internal/cache/DiskLruCache$Editor.entry Lokhttp3/internal/cache/DiskLruCache$Entry;
         // 22: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getCurrentEditor$okhttp ()Lokhttp3/internal/cache/DiskLruCache$Editor;
         // 25: astore 6
         // 27: aload 0
         // 28: checkcast okhttp3/internal/cache/DiskLruCache$Editor
         // 2b: astore 4
         // 2d: aload 6
         // 2f: aload 0
         // 30: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 33: ifeq 6b
         // 36: aload 0
         // 37: getfield okhttp3/internal/cache/DiskLruCache$Editor.entry Lokhttp3/internal/cache/DiskLruCache$Entry;
         // 3a: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getZombie$okhttp ()Z
         // 3d: istore 2
         // 3e: iload 2
         // 3f: ifeq 45
         // 42: goto 6b
         // 45: aload 0
         // 46: getfield okhttp3/internal/cache/DiskLruCache$Editor.this$0 Lokhttp3/internal/cache/DiskLruCache;
         // 49: invokevirtual okhttp3/internal/cache/DiskLruCache.getFileSystem$okhttp ()Lokhttp3/internal/io/FileSystem;
         // 4c: aload 0
         // 4d: getfield okhttp3/internal/cache/DiskLruCache$Editor.entry Lokhttp3/internal/cache/DiskLruCache$Entry;
         // 50: invokevirtual okhttp3/internal/cache/DiskLruCache$Entry.getCleanFiles$okhttp ()Ljava/util/List;
         // 53: iload 1
         // 54: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
         // 59: checkcast java/io/File
         // 5c: invokeinterface okhttp3/internal/io/FileSystem.source (Ljava/io/File;)Lokio/Source; 2
         // 61: astore 4
         // 63: aload 4
         // 65: astore 3
         // 66: aload 5
         // 68: monitorexit
         // 69: aload 3
         // 6a: areturn
         // 6b: aload 5
         // 6d: monitorexit
         // 6e: aconst_null
         // 6f: areturn
         // 70: new java/lang/IllegalStateException
         // 73: astore 3
         // 74: aload 3
         // 75: ldc "Check failed."
         // 77: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
         // 7a: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
         // 7d: aload 3
         // 7e: checkcast java/lang/Throwable
         // 81: athrow
         // 82: astore 3
         // 83: aload 5
         // 85: monitorexit
         // 86: aload 3
         // 87: athrow
         // 88: astore 4
         // 8a: goto 66
      }
   }

   internal inner class Entry internal constructor(key: String) {
      internal final val cleanFiles: MutableList<File>
      internal final var currentEditor: okhttp3.internal.cache.DiskLruCache.Editor?
      internal final val dirtyFiles: MutableList<File>
      internal final val key: String
      internal final val lengths: LongArray
      internal final var lockingSourceCount: Int
      internal final var readable: Boolean
      internal final var sequenceNumber: Long
      internal final var zombie: Boolean

      init {
         Intrinsics.checkParameterIsNotNull(var2, "key");
         this.this$0 = var1;
         super();
         this.key = var2;
         this.lengths = new long[var1.getValueCount$okhttp()];
         this.cleanFiles = new ArrayList<>();
         this.dirtyFiles = new ArrayList<>();
         val var6: StringBuilder = new StringBuilder(var2);
         var6.append('.');
         val var5: Int = var6.length();
         val var4: Int = var1.getValueCount$okhttp();

         for (int var3 = 0; var3 < var4; var3++) {
            var6.append(var3);
            this.cleanFiles.add(new File(var1.getDirectory(), var6.toString()));
            var6.append(".tmp");
            this.dirtyFiles.add(new File(var1.getDirectory(), var6.toString()));
            var6.setLength(var5);
         }
      }

      @Throws(java/io/IOException::class)
      private fun invalidLengths(strings: List<String>): Nothing {
         val var2: StringBuilder = new StringBuilder("unexpected journal line: ");
         var2.append(var1);
         throw (new IOException(var2.toString())) as java.lang.Throwable;
      }

      private fun newSource(index: Int): Source {
         val var2: Source = this.this$0.getFileSystem$okhttp().source(this.cleanFiles.get(var1));
         if (DiskLruCache.access$getCivilizedFileSystem$p(this.this$0)) {
            return var2;
         } else {
            this.lockingSourceCount++;
            return new ForwardingSource(this, var2, var2) {
               final Source $fileSource;
               private boolean closed;
               final DiskLruCache.Entry this$0;

               {
                  super(var3);
                  this.this$0 = var1;
                  this.$fileSource = var2;
               }

               @Override
               public void close() {
                  label25: {
                     super.close();
                     if (!this.closed) {
                        this.closed = true;
                        val var1: DiskLruCache = this.this$0.this$0;
                        synchronized (this.this$0.this$0){} // $VF: monitorenter 

                        try {
                           this.this$0.setLockingSourceCount$okhttp(this.this$0.getLockingSourceCount$okhttp() - 1);
                           if (this.this$0.getLockingSourceCount$okhttp() == 0 && this.this$0.getZombie$okhttp()) {
                              this.this$0.this$0.removeEntry$okhttp(this.this$0);
                           }
                        } catch (var3: java.lang.Throwable) {
                           // $VF: monitorexit
                        }

                        // $VF: monitorexit
                     }
                  }
               }
            };
         }
      }

      @Throws(java/io/IOException::class)
      internal fun setLengths(strings: List<String>) {
         Intrinsics.checkParameterIsNotNull(var1, "strings");
         if (var1.size() != this.this$0.getValueCount$okhttp()) {
            this.invalidLengths(var1);
            throw null;
         } else {
            var var3: Int;
            try {
               var3 = var1.size();
            } catch (var6: NumberFormatException) {
               this.invalidLengths(var1);
               throw null;
            }

            for (int var2 = 0; var2 < var3; var2++) {
               try {
                  this.lengths[var2] = java.lang.Long.parseLong(var1.get(var2) as java.lang.String);
               } catch (var5: NumberFormatException) {
                  this.invalidLengths(var1);
                  throw null;
               }
            }
         }
      }

      internal fun snapshot(): okhttp3.internal.cache.DiskLruCache.Snapshot? {
         val var3: DiskLruCache = this.this$0;
         if (Util.assertionsEnabled && !Thread.holdsLock(this.this$0)) {
            val var12: StringBuilder = new StringBuilder("Thread ");
            val var5: Thread = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var5, "Thread.currentThread()");
            var12.append(var5.getName());
            var12.append(" MUST hold lock on ");
            var12.append(var3);
            throw (new AssertionError(var12.toString())) as java.lang.Throwable;
         } else if (!this.readable) {
            return null;
         } else if (DiskLruCache.access$getCivilizedFileSystem$p(this.this$0) || this.currentEditor == null && !this.zombie) {
            val var10: java.util.List = new ArrayList();
            val var4: LongArray = this.lengths.clone() as LongArray;

            label74: {
               var var2: Int;
               try {
                  var2 = this.this$0.getValueCount$okhttp();
               } catch (var9: FileNotFoundException) {
                  var11 = var10.iterator();
                  break label74;
               }

               for (int var1 = 0; var1 < var2; var1++) {
                  try {
                     var10.add(this.newSource(var1));
                  } catch (var8: FileNotFoundException) {
                     var11 = var10.iterator();
                     break label74;
                  }
               }

               try {
                  return this.this$0.new Snapshot(this.this$0, (long)this.key, this.sequenceNumber, (long[])var10, var4);
               } catch (var7: FileNotFoundException) {
                  var11 = var10.iterator();
               }
            }

            while (var11.hasNext()) {
               Util.closeQuietly(var11.next() as Source);
            }

            try {
               this.this$0.removeEntry$okhttp(this);
            } catch (var6: IOException) {
            }

            return null;
         } else {
            return null;
         }
      }

      @Throws(java/io/IOException::class)
      internal fun writeLengths(writer: BufferedSink) {
         Intrinsics.checkParameterIsNotNull(var1, "writer");
         val var6: LongArray = this.lengths;
         val var3: Int = this.lengths.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var1.writeByte(32).writeDecimalLong(var6[var2]);
         }
      }
   }

   public inner class Snapshot internal constructor(key: String, sequenceNumber: Long, sources: List<Source>, lengths: LongArray) : Closeable {
      private final val key: String
      private final val lengths: LongArray
      private final val sequenceNumber: Long
      private final val sources: List<Source>

      init {
         Intrinsics.checkParameterIsNotNull(var2, "key");
         Intrinsics.checkParameterIsNotNull(var5, "sources");
         Intrinsics.checkParameterIsNotNull(var6, "lengths");
         this.this$0 = var1;
         super();
         this.key = var2;
         this.sequenceNumber = var3;
         this.sources = var5;
         this.lengths = var6;
      }

      public override fun close() {
         val var1: java.util.Iterator = this.sources.iterator();

         while (var1.hasNext()) {
            Util.closeQuietly(var1.next() as Source);
         }
      }

      @Throws(java/io/IOException::class)
      public fun edit(): okhttp3.internal.cache.DiskLruCache.Editor? {
         return this.this$0.edit(this.key, this.sequenceNumber);
      }

      public fun getLength(index: Int): Long {
         return this.lengths[var1];
      }

      public fun getSource(index: Int): Source {
         return this.sources.get(var1);
      }

      public fun key(): String {
         return this.key;
      }
   }
}
