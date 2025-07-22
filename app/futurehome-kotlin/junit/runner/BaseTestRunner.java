package junit.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.NumberFormat;
import java.util.Properties;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;
import junit.framework.TestSuite;

public abstract class BaseTestRunner implements TestListener {
   public static final String SUITE_METHODNAME = "suite";
   private static Properties fPreferences;
   static boolean fgFilterStack;
   static int fgMaxMessageLength = getPreference("maxmessage", 500);
   boolean fLoading = true;

   static boolean filterLine(String var0) {
      for (int var1 = 0; var1 < 8; var1++) {
         if (var0.indexOf(
               new String[]{
                  "junit.framework.TestCase",
                  "junit.framework.TestResult",
                  "junit.framework.TestSuite",
                  "junit.framework.Assert.",
                  "junit.swingui.TestRunner",
                  "junit.awtui.TestRunner",
                  "junit.textui.TestRunner",
                  "java.lang.reflect.Method.invoke("
               }[var1]
            )
            > 0) {
            return true;
         }
      }

      return false;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static String getFilteredTrace(String var0) {
      if (showStackRaw()) {
         return var0;
      } else {
         StringWriter var1 = new StringWriter();
         PrintWriter var2 = new PrintWriter(var1);
         BufferedReader var3 = new BufferedReader(new StringReader(var0));

         while (true) {
            String var4;
            try {
               var4 = var3.readLine();
            } catch (Exception var6) {
               break;
            }

            if (var4 == null) {
               var0 = var1.toString();
               break;
            }

            try {
               if (!filterLine(var4)) {
                  var2.println(var4);
               }
            } catch (Exception var5) {
               break;
            }
         }

         return var0;
      }
   }

   public static String getFilteredTrace(Throwable var0) {
      StringWriter var1 = new StringWriter();
      var0.printStackTrace(new PrintWriter(var1));
      return getFilteredTrace(var1.toString());
   }

   public static int getPreference(String var0, int var1) {
      var0 = getPreference(var0);
      if (var0 == null) {
         return var1;
      } else {
         int var2;
         try {
            var2 = Integer.parseInt(var0);
         } catch (NumberFormatException var3) {
            return var1;
         }

         return var2;
      }
   }

   public static String getPreference(String var0) {
      return getPreferences().getProperty(var0);
   }

   protected static Properties getPreferences() {
      if (fPreferences == null) {
         Properties var0 = new Properties();
         fPreferences = var0;
         var0.put("loading", "true");
         fPreferences.put("filterstack", "true");
         readPreferences();
      }

      return fPreferences;
   }

   private static File getPreferencesFile() {
      return new File(System.getProperty("user.home"), "junit.properties");
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static void readPreferences() {
      Object var1 = null;

      Object var0;
      label116: {
         label107: {
            label106: {
               try {
                  var0 = new FileInputStream(getPreferencesFile());
                  break label106;
               } catch (IOException var21) {
               } finally {
                  ;
               }

               var0 = var1;
               break label107;
            }

            try {
               var1 = new Properties(getPreferences());
               setPreferences((Properties)var1);
               getPreferences().load((InputStream)var0);
               break label116;
            } catch (IOException var19) {
               var1 = var19;
            } finally {
               if (var0 != null) {
                  try {
                     var0.close();
                  } catch (IOException var16) {
                  }
               }

               throw var1;
            }
         }

         if (var0 != null) {
            try {
               var0.close();
            } catch (IOException var18) {
            }

            return;
         }

         return;
      }

      try {
         var0.close();
      } catch (IOException var17) {
      }
   }

   public static void savePreferences() throws IOException {
      FileOutputStream var1 = new FileOutputStream(getPreferencesFile());

      try {
         getPreferences().store(var1, "");
      } finally {
         var1.close();
      }
   }

   public static void setPreference(String var0, String var1) {
      getPreferences().put(var0, var1);
   }

   protected static void setPreferences(Properties var0) {
      fPreferences = var0;
   }

   protected static boolean showStackRaw() {
      boolean var0;
      if (getPreference("filterstack").equals("true") && fgFilterStack) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   public static String truncate(String var0) {
      String var1 = var0;
      if (fgMaxMessageLength != -1) {
         var1 = var0;
         if (var0.length() > fgMaxMessageLength) {
            StringBuilder var2 = new StringBuilder();
            var2.append(var0.substring(0, fgMaxMessageLength));
            var2.append("...");
            var1 = var2.toString();
         }
      }

      return var1;
   }

   @Override
   public void addError(Test param1, Throwable param2) {
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
      // 03: bipush 1
      // 04: aload 1
      // 05: aload 2
      // 06: invokevirtual junit/runner/BaseTestRunner.testFailed (ILjunit/framework/Test;Ljava/lang/Throwable;)V
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }

   @Override
   public void addFailure(Test param1, AssertionFailedError param2) {
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
      // 03: bipush 2
      // 04: aload 1
      // 05: aload 2
      // 06: invokevirtual junit/runner/BaseTestRunner.testFailed (ILjunit/framework/Test;Ljava/lang/Throwable;)V
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }

   protected void clearStatus() {
   }

   public String elapsedTimeAsString(long var1) {
      return NumberFormat.getInstance().format(var1 / 1000.0);
   }

   @Override
   public void endTest(Test param1) {
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
      // 03: aload 1
      // 04: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 07: invokevirtual junit/runner/BaseTestRunner.testEnded (Ljava/lang/String;)V
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: return
      // 0d: astore 1
      // 0e: aload 0
      // 0f: monitorexit
      // 10: aload 1
      // 11: athrow
   }

   public String extractClassName(String var1) {
      String var2 = var1;
      if (var1.startsWith("Default package for")) {
         var2 = var1.substring(var1.lastIndexOf(".") + 1);
      }

      return var2;
   }

   public Test getTest(String var1) {
      if (var1.length() <= 0) {
         this.clearStatus();
         return null;
      } else {
         Class var13;
         try {
            var13 = this.loadSuiteClass(var1);
         } catch (ClassNotFoundException var6) {
            String var11 = var6.getMessage();
            if (var11 != null) {
               var1 = var11;
            }

            StringBuilder var12 = new StringBuilder("Class not found \"");
            var12.append(var1);
            var12.append("\"");
            this.runFailed(var12.toString());
            return null;
         } catch (Exception var7) {
            StringBuilder var2 = new StringBuilder("Error: ");
            var2.append(var7.toString());
            this.runFailed(var2.toString());
            return null;
         }

         try {
            var8 = var13.getMethod("suite", null);
         } catch (Exception var5) {
            this.clearStatus();
            return new TestSuite(var13);
         }

         if (!Modifier.isStatic(var8.getModifiers())) {
            this.runFailed("Suite() method must be static");
            return null;
         } else {
            try {
               var10 = (Test)var8.invoke(null, null);
            } catch (InvocationTargetException var3) {
               StringBuilder var14 = new StringBuilder("Failed to invoke suite():");
               var14.append(var3.getTargetException().toString());
               this.runFailed(var14.toString());
               return null;
            } catch (IllegalAccessException var4) {
               StringBuilder var9 = new StringBuilder("Failed to invoke suite():");
               var9.append(var4.toString());
               this.runFailed(var9.toString());
               return null;
            }

            if (var10 == null) {
               return var10;
            } else {
               this.clearStatus();
               return var10;
            }
         }
      }
   }

   protected Class<?> loadSuiteClass(String var1) throws ClassNotFoundException {
      return Class.forName(var1);
   }

   protected String processArguments(String[] var1) {
      String var4 = null;

      for (int var2 = 0; var2 < var1.length; var2++) {
         if (var1[var2].equals("-noloading")) {
            this.setLoading(false);
         } else if (var1[var2].equals("-nofilterstack")) {
            fgFilterStack = false;
         } else if (var1[var2].equals("-c")) {
            int var3 = var1.length;
            if (var3 > ++var2) {
               var4 = this.extractClassName(var1[var2]);
            } else {
               System.out.println("Missing Test class name");
            }
         } else {
            var4 = var1[var2];
         }
      }

      return var4;
   }

   protected abstract void runFailed(String var1);

   public void setLoading(boolean var1) {
      this.fLoading = var1;
   }

   @Override
   public void startTest(Test param1) {
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
      // 03: aload 1
      // 04: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 07: invokevirtual junit/runner/BaseTestRunner.testStarted (Ljava/lang/String;)V
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: return
      // 0d: astore 1
      // 0e: aload 0
      // 0f: monitorexit
      // 10: aload 1
      // 11: athrow
   }

   public abstract void testEnded(String var1);

   public abstract void testFailed(int var1, Test var2, Throwable var3);

   public abstract void testStarted(String var1);

   protected boolean useReloadingTestSuiteLoader() {
      boolean var1;
      if (getPreference("loading").equals("true") && this.fLoading) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
