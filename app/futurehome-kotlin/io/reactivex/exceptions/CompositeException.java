package io.reactivex.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public final class CompositeException extends RuntimeException {
   private static final long serialVersionUID = 3026362227162912146L;
   private Throwable cause;
   private final List<Throwable> exceptions;
   private final String message;

   public CompositeException(Iterable<? extends Throwable> var1) {
      LinkedHashSet var2 = new LinkedHashSet();
      ArrayList var3 = new ArrayList();
      if (var1 != null) {
         for (Throwable var4 : var1) {
            if (var4 instanceof CompositeException) {
               var2.addAll(((CompositeException)var4).getExceptions());
            } else if (var4 != null) {
               var2.add(var4);
            } else {
               var2.add(new NullPointerException("Throwable was null!"));
            }
         }
      } else {
         var2.add(new NullPointerException("errors was null"));
      }

      if (!var2.isEmpty()) {
         var3.addAll(var2);
         List var6 = Collections.unmodifiableList(var3);
         this.exceptions = var6;
         StringBuilder var7 = new StringBuilder();
         var7.append(var6.size());
         var7.append(" exceptions occurred. ");
         this.message = var7.toString();
      } else {
         throw new IllegalArgumentException("errors is empty");
      }
   }

   public CompositeException(Throwable... var1) {
      List var2;
      if (var1 == null) {
         var2 = Collections.singletonList(new NullPointerException("exceptions was null"));
      } else {
         var2 = Arrays.asList(var1);
      }

      this(var2);
   }

   private void appendStackTrace(StringBuilder var1, Throwable var2, String var3) {
      var1.append(var3);
      var1.append(var2);
      var1.append('\n');

      for (StackTraceElement var6 : var2.getStackTrace()) {
         var1.append("\t\tat ");
         var1.append(var6);
         var1.append('\n');
      }

      if (var2.getCause() != null) {
         var1.append("\tCaused by: ");
         this.appendStackTrace(var1, var2.getCause(), "");
      }
   }

   private List<Throwable> getListOfCauses(Throwable var1) {
      ArrayList var4 = new ArrayList();
      Throwable var3 = var1.getCause();
      if (var3 != null) {
         Throwable var2 = var3;
         if (var3 != var1) {
            while (true) {
               var4.add(var2);
               var1 = var2.getCause();
               if (var1 == null || var1 == var2) {
                  break;
               }

               var2 = var1;
            }
         }
      }

      return var4;
   }

   private void printStackTrace(CompositeException.PrintStreamOrWriter var1) {
      StringBuilder var4 = new StringBuilder(128);
      var4.append(this);
      var4.append('\n');

      for (StackTraceElement var6 : this.getStackTrace()) {
         var4.append("\tat ");
         var4.append(var6);
         var4.append('\n');
      }

      Iterator var8 = this.exceptions.iterator();

      for (int var7 = 1; var8.hasNext(); var7++) {
         Throwable var9 = (Throwable)var8.next();
         var4.append("  ComposedException ");
         var4.append(var7);
         var4.append(" :\n");
         this.appendStackTrace(var4, var9, "\t");
      }

      var1.println(var4.toString());
   }

   @Override
   public Throwable getCause() {
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
      // 03: getfield io/reactivex/exceptions/CompositeException.cause Ljava/lang/Throwable;
      // 06: ifnonnull b0
      // 09: new io/reactivex/exceptions/CompositeException$CompositeExceptionCausalChain
      // 0c: astore 3
      // 0d: aload 3
      // 0e: invokespecial io/reactivex/exceptions/CompositeException$CompositeExceptionCausalChain.<init> ()V
      // 11: new java/util/HashSet
      // 14: astore 5
      // 16: aload 5
      // 18: invokespecial java/util/HashSet.<init> ()V
      // 1b: aload 0
      // 1c: getfield io/reactivex/exceptions/CompositeException.exceptions Ljava/util/List;
      // 1f: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 24: astore 4
      // 26: aload 3
      // 27: astore 1
      // 28: aload 4
      // 2a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2f: ifeq ab
      // 32: aload 4
      // 34: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 39: checkcast java/lang/Throwable
      // 3c: astore 2
      // 3d: aload 5
      // 3f: aload 2
      // 40: invokeinterface java/util/Set.contains (Ljava/lang/Object;)Z 2
      // 45: ifeq 4b
      // 48: goto 28
      // 4b: aload 5
      // 4d: aload 2
      // 4e: invokeinterface java/util/Set.add (Ljava/lang/Object;)Z 2
      // 53: pop
      // 54: aload 0
      // 55: aload 2
      // 56: invokespecial io/reactivex/exceptions/CompositeException.getListOfCauses (Ljava/lang/Throwable;)Ljava/util/List;
      // 59: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 5e: astore 6
      // 60: aload 6
      // 62: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 67: ifeq 9c
      // 6a: aload 6
      // 6c: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 71: checkcast java/lang/Throwable
      // 74: astore 7
      // 76: aload 5
      // 78: aload 7
      // 7a: invokeinterface java/util/Set.contains (Ljava/lang/Object;)Z 2
      // 7f: ifeq 8f
      // 82: new java/lang/RuntimeException
      // 85: dup
      // 86: ldc "Duplicate found in causal chain so cropping to prevent loop ..."
      // 88: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;)V
      // 8b: astore 2
      // 8c: goto 60
      // 8f: aload 5
      // 91: aload 7
      // 93: invokeinterface java/util/Set.add (Ljava/lang/Object;)Z 2
      // 98: pop
      // 99: goto 60
      // 9c: aload 1
      // 9d: aload 2
      // 9e: invokevirtual java/lang/Throwable.initCause (Ljava/lang/Throwable;)Ljava/lang/Throwable;
      // a1: pop
      // a2: aload 0
      // a3: aload 1
      // a4: invokevirtual io/reactivex/exceptions/CompositeException.getRootCause (Ljava/lang/Throwable;)Ljava/lang/Throwable;
      // a7: astore 1
      // a8: goto 28
      // ab: aload 0
      // ac: aload 3
      // ad: putfield io/reactivex/exceptions/CompositeException.cause Ljava/lang/Throwable;
      // b0: aload 0
      // b1: getfield io/reactivex/exceptions/CompositeException.cause Ljava/lang/Throwable;
      // b4: astore 1
      // b5: aload 0
      // b6: monitorexit
      // b7: aload 1
      // b8: areturn
      // b9: astore 1
      // ba: aload 0
      // bb: monitorexit
      // bc: aload 1
      // bd: athrow
      // be: astore 2
      // bf: goto a2
   }

   public List<Throwable> getExceptions() {
      return this.exceptions;
   }

   @Override
   public String getMessage() {
      return this.message;
   }

   Throwable getRootCause(Throwable var1) {
      Throwable var3 = var1.getCause();
      if (var3 != null) {
         Throwable var2 = var3;
         if (var1 != var3) {
            while (true) {
               var1 = var2.getCause();
               if (var1 == null || var1 == var2) {
                  return var2;
               }

               var2 = var1;
            }
         }
      }

      return var1;
   }

   @Override
   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   @Override
   public void printStackTrace(PrintStream var1) {
      this.printStackTrace(new CompositeException.WrappedPrintStream(var1));
   }

   @Override
   public void printStackTrace(PrintWriter var1) {
      this.printStackTrace(new CompositeException.WrappedPrintWriter(var1));
   }

   public int size() {
      return this.exceptions.size();
   }

   static final class CompositeExceptionCausalChain extends RuntimeException {
      static final String MESSAGE = "Chain of Causes for CompositeException In Order Received =>";
      private static final long serialVersionUID = 3875212506787802066L;

      @Override
      public String getMessage() {
         return "Chain of Causes for CompositeException In Order Received =>";
      }
   }

   abstract static class PrintStreamOrWriter {
      abstract void println(Object var1);
   }

   static final class WrappedPrintStream extends CompositeException.PrintStreamOrWriter {
      private final PrintStream printStream;

      WrappedPrintStream(PrintStream var1) {
         this.printStream = var1;
      }

      @Override
      void println(Object var1) {
         this.printStream.println(var1);
      }
   }

   static final class WrappedPrintWriter extends CompositeException.PrintStreamOrWriter {
      private final PrintWriter printWriter;

      WrappedPrintWriter(PrintWriter var1) {
         this.printWriter = var1;
      }

      @Override
      void println(Object var1) {
         this.printWriter.println(var1);
      }
   }
}
