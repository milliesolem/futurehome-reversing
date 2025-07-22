package okio

import java.io.Closeable
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

public abstract class FileHandle : Closeable {
   private final var closed: Boolean
   public final val lock: ReentrantLock
   private final var openStreamCount: Int
   public final val readWrite: Boolean

   open fun FileHandle(var1: Boolean) {
      this.readWrite = var1;
      this.lock = _JvmPlatformKt.newLock();
   }

   private fun readNoCloseCheck(fileOffset: Long, sink: Buffer, byteCount: Long): Long {
      if (var4 < 0L) {
         val var12: StringBuilder = new StringBuilder("byteCount < 0: ");
         var12.append(var4);
         throw new IllegalArgumentException(var12.toString().toString());
      } else {
         val var7: Long = var4 + var1;
         var4 = var1;

         while (var4 < var7) {
            val var11: Segment = var3.writableSegment$okio(1);
            val var6: Int = this.protectedRead(var4, var11.data, var11.limit, (int)Math.min(var7 - var4, (long)(8192 - var11.limit)));
            if (var6 == -1) {
               if (var11.pos == var11.limit) {
                  var3.head = var11.pop();
                  SegmentPool.recycle(var11);
               }

               if (var1 == var4) {
                  return -1L;
               }
               break;
            }

            var11.limit += var6;
            var4 += var6;
            var3.setSize$okio(var3.size() + (long)var6);
         }

         return var4 - var1;
      }
   }

   private fun writeNoCloseCheck(fileOffset: Long, source: Buffer, byteCount: Long) {
      _SegmentedByteString.checkOffsetAndCount(var3.size(), 0L, var4);
      val var7: Long = var4 + var1;

      while (var1 < var7) {
         val var11: Segment = var3.head;
         val var6: Int = (int)Math.min(var7 - var1, (long)(var11.limit - var11.pos));
         this.protectedWrite(var1, var11.data, var11.pos, var6);
         var11.pos += var6;
         var4 = var1 + var6;
         var3.setSize$okio(var3.size() - (long)var6);
         var1 = var4;
         if (var11.pos == var11.limit) {
            var3.head = var11.pop();
            SegmentPool.recycle(var11);
            var1 = var4;
         }
      }
   }

   @Throws(java/io/IOException::class)
   public fun appendingSink(): Sink {
      return this.sink(this.size());
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
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
      // 01: getfield okio/FileHandle.lock Ljava/util/concurrent/locks/ReentrantLock;
      // 04: checkcast java/util/concurrent/locks/Lock
      // 07: astore 3
      // 08: aload 3
      // 09: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
      // 0e: aload 0
      // 0f: getfield okio/FileHandle.closed Z
      // 12: istore 2
      // 13: iload 2
      // 14: ifeq 1e
      // 17: aload 3
      // 18: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // 1d: return
      // 1e: aload 0
      // 1f: bipush 1
      // 20: putfield okio/FileHandle.closed Z
      // 23: aload 0
      // 24: getfield okio/FileHandle.openStreamCount I
      // 27: istore 1
      // 28: iload 1
      // 29: ifeq 33
      // 2c: aload 3
      // 2d: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // 32: return
      // 33: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 36: astore 4
      // 38: aload 3
      // 39: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // 3e: aload 0
      // 3f: invokevirtual okio/FileHandle.protectedClose ()V
      // 42: return
      // 43: astore 4
      // 45: aload 3
      // 46: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // 4b: aload 4
      // 4d: athrow
   }

   @Throws(java/io/IOException::class)
   public fun flush() {
      label23:
      if (this.readWrite) {
         val var1: Lock = this.lock;
         this.lock.lock();

         try {
            if (this.closed) {
               throw new IllegalStateException("closed".toString());
            }
         } catch (var3: java.lang.Throwable) {
            var1.unlock();
         }

         var1.unlock();
      } else {
         throw new IllegalStateException("file handle is read-only".toString());
      }
   }

   @Throws(java/io/IOException::class)
   public fun position(sink: Sink): Long {
      val var2: Long;
      if (var1 is RealBufferedSink) {
         var2 = (var1 as RealBufferedSink).bufferField.size();
         var1 = (var1 as RealBufferedSink).sink;
      } else {
         var2 = 0L;
      }

      if (var1 is FileHandle.FileHandleSink && (var1 as FileHandle.FileHandleSink).getFileHandle() === this) {
         val var5: FileHandle.FileHandleSink = var1 as FileHandle.FileHandleSink;
         if (!(var1 as FileHandle.FileHandleSink).getClosed()) {
            return var5.getPosition() + var2;
         } else {
            throw new IllegalStateException("closed".toString());
         }
      } else {
         throw new IllegalArgumentException("sink was not created by this FileHandle".toString());
      }
   }

   @Throws(java/io/IOException::class)
   public fun position(source: Source): Long {
      val var2: Long;
      if (var1 is RealBufferedSource) {
         var2 = (var1 as RealBufferedSource).bufferField.size();
         var1 = (var1 as RealBufferedSource).source;
      } else {
         var2 = 0L;
      }

      if (var1 is FileHandle.FileHandleSource && (var1 as FileHandle.FileHandleSource).getFileHandle() === this) {
         val var5: FileHandle.FileHandleSource = var1 as FileHandle.FileHandleSource;
         if (!(var1 as FileHandle.FileHandleSource).getClosed()) {
            return var5.getPosition() - var2;
         } else {
            throw new IllegalStateException("closed".toString());
         }
      } else {
         throw new IllegalArgumentException("source was not created by this FileHandle".toString());
      }
   }

   @Throws(java/io/IOException::class)
   protected abstract fun protectedClose() {
   }

   @Throws(java/io/IOException::class)
   protected abstract fun protectedFlush() {
   }

   @Throws(java/io/IOException::class)
   protected abstract fun protectedRead(fileOffset: Long, array: ByteArray, arrayOffset: Int, byteCount: Int): Int {
   }

   @Throws(java/io/IOException::class)
   protected abstract fun protectedResize(size: Long) {
   }

   @Throws(java/io/IOException::class)
   protected abstract fun protectedSize(): Long {
   }

   @Throws(java/io/IOException::class)
   protected abstract fun protectedWrite(fileOffset: Long, array: ByteArray, arrayOffset: Int, byteCount: Int) {
   }

   @Throws(java/io/IOException::class)
   public fun read(fileOffset: Long, array: ByteArray, arrayOffset: Int, byteCount: Int): Int {
      label21: {
         val var6: Lock = this.lock;
         this.lock.lock();

         try {
            if (this.closed) {
               throw new IllegalStateException("closed".toString());
            }
         } catch (var8: java.lang.Throwable) {
            var6.unlock();
         }

         var6.unlock();
      }
   }

   @Throws(java/io/IOException::class)
   public fun read(fileOffset: Long, sink: Buffer, byteCount: Long): Long {
      label21: {
         val var6: Lock = this.lock;
         this.lock.lock();

         try {
            if (this.closed) {
               throw new IllegalStateException("closed".toString());
            }
         } catch (var8: java.lang.Throwable) {
            var6.unlock();
         }

         var6.unlock();
      }
   }

   @Throws(java/io/IOException::class)
   public fun reposition(sink: Sink, position: Long) {
      if (var1 is RealBufferedSink) {
         val var6: RealBufferedSink = var1 as RealBufferedSink;
         val var4: Sink = (var1 as RealBufferedSink).sink;
         if ((var1 as RealBufferedSink).sink !is FileHandle.FileHandleSink
            || ((var1 as RealBufferedSink).sink as FileHandle.FileHandleSink).getFileHandle() != this) {
            throw new IllegalArgumentException("sink was not created by this FileHandle".toString());
         }

         val var7: FileHandle.FileHandleSink = var4 as FileHandle.FileHandleSink;
         if ((var4 as FileHandle.FileHandleSink).getClosed()) {
            throw new IllegalStateException("closed".toString());
         }

         var6.emit();
         var7.setPosition(var2);
      } else {
         if (var1 !is FileHandle.FileHandleSink || (var1 as FileHandle.FileHandleSink).getFileHandle() != this) {
            throw new IllegalArgumentException("sink was not created by this FileHandle".toString());
         }

         val var5: FileHandle.FileHandleSink = var1 as FileHandle.FileHandleSink;
         if ((var1 as FileHandle.FileHandleSink).getClosed()) {
            throw new IllegalStateException("closed".toString());
         }

         var5.setPosition(var2);
      }
   }

   @Throws(java/io/IOException::class)
   public fun reposition(source: Source, position: Long) {
      if (var1 is RealBufferedSource) {
         val var10: RealBufferedSource = var1 as RealBufferedSource;
         val var8: Source = (var1 as RealBufferedSource).source;
         if ((var1 as RealBufferedSource).source !is FileHandle.FileHandleSource
            || ((var1 as RealBufferedSource).source as FileHandle.FileHandleSource).getFileHandle() != this) {
            throw new IllegalArgumentException("source was not created by this FileHandle".toString());
         }

         val var11: FileHandle.FileHandleSource = var8 as FileHandle.FileHandleSource;
         if ((var8 as FileHandle.FileHandleSource).getClosed()) {
            throw new IllegalStateException("closed".toString());
         }

         val var4: Long = var10.bufferField.size();
         val var6: Long = var2 - (var11.getPosition() - var4);
         if (0L <= var6 && var6 < var4) {
            var10.skip(var6);
         } else {
            var10.bufferField.clear();
            var11.setPosition(var2);
         }
      } else {
         if (var1 !is FileHandle.FileHandleSource || (var1 as FileHandle.FileHandleSource).getFileHandle() != this) {
            throw new IllegalArgumentException("source was not created by this FileHandle".toString());
         }

         val var9: FileHandle.FileHandleSource = var1 as FileHandle.FileHandleSource;
         if ((var1 as FileHandle.FileHandleSource).getClosed()) {
            throw new IllegalStateException("closed".toString());
         }

         var9.setPosition(var2);
      }
   }

   @Throws(java/io/IOException::class)
   public fun resize(size: Long) {
      label23:
      if (this.readWrite) {
         val var3: Lock = this.lock;
         this.lock.lock();

         try {
            if (this.closed) {
               throw new IllegalStateException("closed".toString());
            }
         } catch (var5: java.lang.Throwable) {
            var3.unlock();
         }

         var3.unlock();
      } else {
         throw new IllegalStateException("file handle is read-only".toString());
      }
   }

   @Throws(java/io/IOException::class)
   public fun sink(fileOffset: Long = 0L): Sink {
      label23:
      if (this.readWrite) {
         val var3: Lock = this.lock;
         this.lock.lock();

         try {
            if (this.closed) {
               throw new IllegalStateException("closed".toString());
            }

            this.openStreamCount++;
         } catch (var5: java.lang.Throwable) {
            var3.unlock();
         }

         var3.unlock();
      } else {
         throw new IllegalStateException("file handle is read-only".toString());
      }
   }

   @Throws(java/io/IOException::class)
   public fun size(): Long {
      label21: {
         val var1: Lock = this.lock;
         this.lock.lock();

         try {
            if (this.closed) {
               throw new IllegalStateException("closed".toString());
            }
         } catch (var3: java.lang.Throwable) {
            var1.unlock();
         }

         var1.unlock();
      }
   }

   @Throws(java/io/IOException::class)
   public fun source(fileOffset: Long = 0L): Source {
      label21: {
         val var3: Lock = this.lock;
         this.lock.lock();

         try {
            if (this.closed) {
               throw new IllegalStateException("closed".toString());
            }

            this.openStreamCount++;
         } catch (var5: java.lang.Throwable) {
            var3.unlock();
         }

         var3.unlock();
      }
   }

   @Throws(java/io/IOException::class)
   public fun write(fileOffset: Long, source: Buffer, byteCount: Long) {
      label23:
      if (this.readWrite) {
         val var6: Lock = this.lock;
         this.lock.lock();

         try {
            if (this.closed) {
               throw new IllegalStateException("closed".toString());
            }
         } catch (var8: java.lang.Throwable) {
            var6.unlock();
         }

         var6.unlock();
      } else {
         throw new IllegalStateException("file handle is read-only".toString());
      }
   }

   public fun write(fileOffset: Long, array: ByteArray, arrayOffset: Int, byteCount: Int) {
      label23:
      if (this.readWrite) {
         val var6: Lock = this.lock;
         this.lock.lock();

         try {
            if (this.closed) {
               throw new IllegalStateException("closed".toString());
            }
         } catch (var8: java.lang.Throwable) {
            var6.unlock();
         }

         var6.unlock();
      } else {
         throw new IllegalStateException("file handle is read-only".toString());
      }
   }

   private class FileHandleSink(fileHandle: FileHandle, position: Long) : Sink {
      public final var closed: Boolean
         internal set

      public final val fileHandle: FileHandle

      public final var position: Long
         internal set

      init {
         this.fileHandle = var1;
         this.position = var2;
      }

      public override fun close() {
         if (!this.closed) {
            label47: {
               this.closed = true;
               val var1: Lock = this.fileHandle.getLock();
               var1.lock();

               label24: {
                  try {
                     FileHandle.access$setOpenStreamCount$p(this.fileHandle, FileHandle.access$getOpenStreamCount$p(this.fileHandle) - 1);
                     if (FileHandle.access$getOpenStreamCount$p(this.fileHandle) == 0 && FileHandle.access$getClosed$p(this.fileHandle)) {
                        break label24;
                     }
                  } catch (var3: java.lang.Throwable) {
                     var1.unlock();
                  }

                  var1.unlock();
               }

               var1.unlock();
            }
         }
      }

      public override fun flush() {
         if (!this.closed) {
            this.fileHandle.protectedFlush();
         } else {
            throw new IllegalStateException("closed".toString());
         }
      }

      public override fun timeout(): Timeout {
         return Timeout.NONE;
      }

      public override fun write(source: Buffer, byteCount: Long) {
         if (!this.closed) {
            FileHandle.access$writeNoCloseCheck(this.fileHandle, this.position, var1, var2);
            this.position += var2;
         } else {
            throw new IllegalStateException("closed".toString());
         }
      }
   }

   private class FileHandleSource(fileHandle: FileHandle, position: Long) : Source {
      public final var closed: Boolean
         internal set

      public final val fileHandle: FileHandle

      public final var position: Long
         internal set

      init {
         this.fileHandle = var1;
         this.position = var2;
      }

      public override fun close() {
         if (!this.closed) {
            label47: {
               this.closed = true;
               val var1: Lock = this.fileHandle.getLock();
               var1.lock();

               label24: {
                  try {
                     FileHandle.access$setOpenStreamCount$p(this.fileHandle, FileHandle.access$getOpenStreamCount$p(this.fileHandle) - 1);
                     if (FileHandle.access$getOpenStreamCount$p(this.fileHandle) == 0 && FileHandle.access$getClosed$p(this.fileHandle)) {
                        break label24;
                     }
                  } catch (var3: java.lang.Throwable) {
                     var1.unlock();
                  }

                  var1.unlock();
               }

               var1.unlock();
            }
         }
      }

      public override fun read(sink: Buffer, byteCount: Long): Long {
         if (!this.closed) {
            var2 = FileHandle.access$readNoCloseCheck(this.fileHandle, this.position, var1, var2);
            if (var2 != -1L) {
               this.position += var2;
            }

            return var2;
         } else {
            throw new IllegalStateException("closed".toString());
         }
      }

      public override fun timeout(): Timeout {
         return Timeout.NONE;
      }
   }
}
