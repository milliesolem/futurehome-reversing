package org.junit.experimental.max;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class MaxHistory implements Serializable {
   private static final long serialVersionUID = 1L;
   private final Map<String, Long> fDurations = new HashMap<>();
   private final Map<String, Long> fFailureTimestamps = new HashMap<>();
   private final File fHistoryStore;

   private MaxHistory(File var1) {
      this.fHistoryStore = var1;
   }

   public static MaxHistory forFolder(File var0) {
      if (var0.exists()) {
         try {
            return readHistory(var0);
         } catch (CouldNotReadCoreException var2) {
            var2.printStackTrace();
            var0.delete();
         }
      }

      return new MaxHistory(var0);
   }

   private static MaxHistory readHistory(File param0) throws CouldNotReadCoreException {
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
      // 00: new java/io/FileInputStream
      // 03: astore 1
      // 04: aload 1
      // 05: aload 0
      // 06: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 09: new java/io/ObjectInputStream
      // 0c: astore 0
      // 0d: aload 0
      // 0e: aload 1
      // 0f: invokespecial java/io/ObjectInputStream.<init> (Ljava/io/InputStream;)V
      // 12: aload 0
      // 13: invokevirtual java/io/ObjectInputStream.readObject ()Ljava/lang/Object;
      // 16: checkcast org/junit/experimental/max/MaxHistory
      // 19: astore 2
      // 1a: aload 0
      // 1b: invokevirtual java/io/ObjectInputStream.close ()V
      // 1e: aload 1
      // 1f: invokevirtual java/io/FileInputStream.close ()V
      // 22: aload 2
      // 23: areturn
      // 24: astore 2
      // 25: aload 0
      // 26: invokevirtual java/io/ObjectInputStream.close ()V
      // 29: aload 2
      // 2a: athrow
      // 2b: astore 0
      // 2c: aload 1
      // 2d: invokevirtual java/io/FileInputStream.close ()V
      // 30: aload 0
      // 31: athrow
      // 32: astore 0
      // 33: new org/junit/experimental/max/CouldNotReadCoreException
      // 36: dup
      // 37: aload 0
      // 38: invokespecial org/junit/experimental/max/CouldNotReadCoreException.<init> (Ljava/lang/Throwable;)V
      // 3b: athrow
   }

   private void save() throws IOException {
      ObjectOutputStream var1 = new ObjectOutputStream(new FileOutputStream(this.fHistoryStore));
      var1.writeObject(this);
      var1.close();
   }

   Long getFailureTimestamp(Description var1) {
      return this.fFailureTimestamps.get(var1.toString());
   }

   Long getTestDuration(Description var1) {
      return this.fDurations.get(var1.toString());
   }

   boolean isNewTest(Description var1) {
      return this.fDurations.containsKey(var1.toString()) ^ true;
   }

   public RunListener listener() {
      return new MaxHistory.RememberingListener(this);
   }

   void putTestDuration(Description var1, long var2) {
      this.fDurations.put(var1.toString(), var2);
   }

   void putTestFailureTimestamp(Description var1, long var2) {
      this.fFailureTimestamps.put(var1.toString(), var2);
   }

   public Comparator<Description> testComparator() {
      return new MaxHistory.TestComparator(this);
   }

   private final class RememberingListener extends RunListener {
      private long overallStart;
      private Map<Description, Long> starts;
      final MaxHistory this$0;

      private RememberingListener(MaxHistory var1) {
         this.this$0 = var1;
         this.overallStart = System.currentTimeMillis();
         this.starts = new HashMap<>();
      }

      public void testFailure(Failure var1) throws Exception {
         this.this$0.putTestFailureTimestamp(var1.getDescription(), this.overallStart);
      }

      public void testFinished(Description var1) throws Exception {
         long var2 = System.nanoTime();
         long var4 = this.starts.get(var1);
         this.this$0.putTestDuration(var1, var2 - var4);
      }

      public void testRunFinished(Result var1) throws Exception {
         this.this$0.save();
      }

      public void testStarted(Description var1) throws Exception {
         this.starts.put(var1, System.nanoTime());
      }
   }

   private class TestComparator implements Comparator<Description> {
      final MaxHistory this$0;

      private TestComparator(MaxHistory var1) {
         this.this$0 = var1;
      }

      private Long getFailure(Description var1) {
         Long var2 = this.this$0.getFailureTimestamp(var1);
         Long var3 = var2;
         if (var2 == null) {
            var3 = 0L;
         }

         return var3;
      }

      public int compare(Description var1, Description var2) {
         if (this.this$0.isNewTest(var1)) {
            return -1;
         } else if (this.this$0.isNewTest(var2)) {
            return 1;
         } else {
            int var3 = this.getFailure(var2).compareTo(this.getFailure(var1));
            if (var3 == 0) {
               var3 = this.this$0.getTestDuration(var1).compareTo(this.this$0.getTestDuration(var2));
            }

            return var3;
         }
      }
   }
}
