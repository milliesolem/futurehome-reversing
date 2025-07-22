package io.sentry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Hint {
   private static final Map<String, Class<?>> PRIMITIVE_MAPPINGS;
   private final List<Attachment> attachments;
   private final Map<String, Object> internalStorage = new HashMap<>();
   private ReplayRecording replayRecording;
   private Attachment screenshot;
   private Attachment threadDump;
   private Attachment viewHierarchy;

   static {
      HashMap var0 = new HashMap();
      PRIMITIVE_MAPPINGS = var0;
      var0.put("boolean", Boolean.class);
      var0.put("char", Character.class);
      var0.put("byte", Byte.class);
      var0.put("short", Short.class);
      var0.put("int", Integer.class);
      var0.put("long", Long.class);
      var0.put("float", Float.class);
      var0.put("double", Double.class);
   }

   public Hint() {
      this.attachments = new ArrayList<>();
      this.screenshot = null;
      this.viewHierarchy = null;
      this.threadDump = null;
      this.replayRecording = null;
   }

   private boolean isCastablePrimitive(Object var1, Class<?> var2) {
      Class var4 = PRIMITIVE_MAPPINGS.get(var2.getCanonicalName());
      boolean var3;
      if (var1 != null && var2.isPrimitive() && var4 != null && var4.isInstance(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static Hint withAttachment(Attachment var0) {
      Hint var1 = new Hint();
      var1.addAttachment(var0);
      return var1;
   }

   public static Hint withAttachments(List<Attachment> var0) {
      Hint var1 = new Hint();
      var1.addAttachments(var0);
      return var1;
   }

   public void addAttachment(Attachment var1) {
      if (var1 != null) {
         this.attachments.add(var1);
      }
   }

   public void addAttachments(List<Attachment> var1) {
      if (var1 != null) {
         this.attachments.addAll(var1);
      }
   }

   public void clear() {
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
      // 03: getfield io/sentry/Hint.internalStorage Ljava/util/Map;
      // 06: invokeinterface java/util/Map.entrySet ()Ljava/util/Set; 1
      // 0b: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 10: astore 2
      // 11: aload 2
      // 12: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 17: ifeq 47
      // 1a: aload 2
      // 1b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 20: checkcast java/util/Map$Entry
      // 23: astore 1
      // 24: aload 1
      // 25: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 2a: ifnull 3e
      // 2d: aload 1
      // 2e: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 33: checkcast java/lang/String
      // 36: ldc "sentry:"
      // 38: invokevirtual java/lang/String.startsWith (Ljava/lang/String;)Z
      // 3b: ifne 11
      // 3e: aload 2
      // 3f: invokeinterface java/util/Iterator.remove ()V 1
      // 44: goto 11
      // 47: aload 0
      // 48: monitorexit
      // 49: return
      // 4a: astore 1
      // 4b: aload 0
      // 4c: monitorexit
      // 4d: aload 1
      // 4e: athrow
   }

   public void clearAttachments() {
      this.attachments.clear();
   }

   public Object get(String param1) {
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
      // 03: getfield io/sentry/Hint.internalStorage Ljava/util/Map;
      // 06: aload 1
      // 07: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: areturn
      // 11: astore 1
      // 12: aload 0
      // 13: monitorexit
      // 14: aload 1
      // 15: athrow
   }

   public <T> T getAs(String param1, Class<T> param2) {
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
      // 03: getfield io/sentry/Hint.internalStorage Ljava/util/Map;
      // 06: aload 1
      // 07: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0c: astore 1
      // 0d: aload 2
      // 0e: aload 1
      // 0f: invokevirtual java/lang/Class.isInstance (Ljava/lang/Object;)Z
      // 12: istore 3
      // 13: iload 3
      // 14: ifeq 1b
      // 17: aload 0
      // 18: monitorexit
      // 19: aload 1
      // 1a: areturn
      // 1b: aload 0
      // 1c: aload 1
      // 1d: aload 2
      // 1e: invokespecial io/sentry/Hint.isCastablePrimitive (Ljava/lang/Object;Ljava/lang/Class;)Z
      // 21: istore 3
      // 22: iload 3
      // 23: ifeq 2a
      // 26: aload 0
      // 27: monitorexit
      // 28: aload 1
      // 29: areturn
      // 2a: aload 0
      // 2b: monitorexit
      // 2c: aconst_null
      // 2d: areturn
      // 2e: astore 1
      // 2f: aload 0
      // 30: monitorexit
      // 31: aload 1
      // 32: athrow
   }

   public List<Attachment> getAttachments() {
      return new ArrayList<>(this.attachments);
   }

   public ReplayRecording getReplayRecording() {
      return this.replayRecording;
   }

   public Attachment getScreenshot() {
      return this.screenshot;
   }

   public Attachment getThreadDump() {
      return this.threadDump;
   }

   public Attachment getViewHierarchy() {
      return this.viewHierarchy;
   }

   public void remove(String param1) {
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
      // 03: getfield io/sentry/Hint.internalStorage Ljava/util/Map;
      // 06: aload 1
      // 07: invokeinterface java/util/Map.remove (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0c: pop
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: return
      // 10: astore 1
      // 11: aload 0
      // 12: monitorexit
      // 13: aload 1
      // 14: athrow
   }

   public void replaceAttachments(List<Attachment> var1) {
      this.clearAttachments();
      this.addAttachments(var1);
   }

   public void set(String param1, Object param2) {
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
      // 03: getfield io/sentry/Hint.internalStorage Ljava/util/Map;
      // 06: aload 1
      // 07: aload 2
      // 08: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0d: pop
      // 0e: aload 0
      // 0f: monitorexit
      // 10: return
      // 11: astore 1
      // 12: aload 0
      // 13: monitorexit
      // 14: aload 1
      // 15: athrow
   }

   public void setReplayRecording(ReplayRecording var1) {
      this.replayRecording = var1;
   }

   public void setScreenshot(Attachment var1) {
      this.screenshot = var1;
   }

   public void setThreadDump(Attachment var1) {
      this.threadDump = var1;
   }

   public void setViewHierarchy(Attachment var1) {
      this.viewHierarchy = var1;
   }
}
