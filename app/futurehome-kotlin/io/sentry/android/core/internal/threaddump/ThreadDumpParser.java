package io.sentry.android.core.internal.threaddump;

import io.sentry.SentryLevel;
import io.sentry.SentryLockReason;
import io.sentry.SentryOptions;
import io.sentry.SentryStackTraceFactory;
import io.sentry.protocol.SentryStackFrame;
import io.sentry.protocol.SentryStackTrace;
import io.sentry.protocol.SentryThread;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadDumpParser {
   private static final Pattern BEGIN_MANAGED_THREAD_RE = Pattern.compile("\"(.*)\" (.*) ?prio=(\\d+)\\s+tid=(\\d+)\\s*(.*)");
   private static final Pattern BEGIN_UNMANAGED_NATIVE_THREAD_RE = Pattern.compile("\"(.*)\" (.*) ?sysTid=(\\d+)");
   private static final Pattern BLANK_RE = Pattern.compile("\\s+");
   private static final Pattern JAVA_RE = Pattern.compile(" *at (?:(.+)\\.)?([^.]+)\\.([^.]+)\\((.*):([\\d-]+)\\)");
   private static final Pattern JNI_RE = Pattern.compile(" *at (?:(.+)\\.)?([^.]+)\\.([^.]+)\\(Native method\\)");
   private static final Pattern LOCKED_RE = Pattern.compile(" *- locked \\<([0x0-9a-fA-F]{1,16})\\> \\(a (?:(.+)\\.)?([^.]+)\\)");
   private static final Pattern NATIVE_NO_LOC_RE = Pattern.compile(" *(?:native: )?#\\d+ \\S+ [0-9a-fA-F]+\\s+(.*)\\s*\\(?(.*)\\)?(?: \\(.*\\))?");
   private static final Pattern NATIVE_RE = Pattern.compile(" *(?:native: )?#\\d+ \\S+ [0-9a-fA-F]+\\s+(.*?)\\s+\\((.*)\\+(\\d+)\\)(?: \\(.*\\))?");
   private static final Pattern SLEEPING_ON_RE = Pattern.compile(" *- sleeping on \\<([0x0-9a-fA-F]{1,16})\\> \\(a (?:(.+)\\.)?([^.]+)\\)");
   private static final Pattern WAITING_ON_RE = Pattern.compile(" *- waiting on \\<([0x0-9a-fA-F]{1,16})\\> \\(a (?:(.+)\\.)?([^.]+)\\)");
   private static final Pattern WAITING_TO_LOCK_HELD_RE = Pattern.compile(
      " *- waiting to lock \\<([0x0-9a-fA-F]{1,16})\\> \\(a (?:(.+)\\.)?([^.]+)\\)(?: held by thread (\\d+))"
   );
   private static final Pattern WAITING_TO_LOCK_RE = Pattern.compile(" *- waiting to lock \\<([0x0-9a-fA-F]{1,16})\\> \\(a (?:(.+)\\.)?([^.]+)\\)");
   private static final Pattern WAITING_TO_LOCK_UNKNOWN_RE = Pattern.compile(" *- waiting to lock an unknown object");
   private final boolean isBackground;
   private final SentryOptions options;
   private final SentryStackTraceFactory stackTraceFactory;

   public ThreadDumpParser(SentryOptions var1, boolean var2) {
      this.options = var1;
      this.isBackground = var2;
      this.stackTraceFactory = new SentryStackTraceFactory(var1);
   }

   private void combineThreadLocks(SentryThread var1, SentryLockReason var2) {
      Map var4 = var1.getHeldLocks();
      Object var3 = var4;
      if (var4 == null) {
         var3 = new HashMap();
      }

      SentryLockReason var5 = (SentryLockReason)var3.get(var2.getAddress());
      if (var5 != null) {
         var5.setType(Math.max(var5.getType(), var2.getType()));
      } else {
         var3.put(var2.getAddress(), new SentryLockReason(var2));
      }

      var1.setHeldLocks((Map<String, SentryLockReason>)var3);
   }

   private Integer getInteger(Matcher var1, int var2, Integer var3) {
      String var4 = var1.group(var2);
      return var4 != null && var4.length() != 0 ? Integer.parseInt(var4) : var3;
   }

   private Long getLong(Matcher var1, int var2, Long var3) {
      String var4 = var1.group(var2);
      return var4 != null && var4.length() != 0 ? Long.parseLong(var4) : var3;
   }

   private Integer getUInteger(Matcher var1, int var2, Integer var3) {
      String var4 = var1.group(var2);
      Integer var5 = var3;
      if (var4 != null) {
         if (var4.length() == 0) {
            var5 = var3;
         } else {
            var2 = Integer.parseInt(var4);
            Integer var7 = var2;
            var7.getClass();
            var5 = var3;
            if (var2 >= 0) {
               var5 = var7;
            }
         }
      }

      return var5;
   }

   private boolean matches(Matcher var1, String var2) {
      var1.reset(var2);
      return var1.matches();
   }

   private SentryStackTrace parseStacktrace(Lines var1, SentryThread var2) {
      ArrayList var14 = new ArrayList();
      Matcher var5 = NATIVE_RE.matcher("");
      Matcher var11 = NATIVE_NO_LOC_RE.matcher("");
      Matcher var13 = JAVA_RE.matcher("");
      Matcher var15 = JNI_RE.matcher("");
      Matcher var7 = LOCKED_RE.matcher("");
      Matcher var8 = WAITING_ON_RE.matcher("");
      Matcher var9 = SLEEPING_ON_RE.matcher("");
      Matcher var12 = WAITING_TO_LOCK_HELD_RE.matcher("");
      Matcher var16 = WAITING_TO_LOCK_RE.matcher("");
      Matcher var10 = WAITING_TO_LOCK_UNKNOWN_RE.matcher("");
      Matcher var6 = BLANK_RE.matcher("");
      SentryStackFrame var4 = null;

      while (var1.hasNext()) {
         Line var3 = var1.next();
         if (var3 == null) {
            this.options.getLogger().log(SentryLevel.WARNING, "Internal error while parsing thread dump.");
            break;
         }

         label71: {
            String var17 = var3.text;
            if (this.matches(var5, var17)) {
               SentryStackFrame var19 = new SentryStackFrame();
               var19.setPackage(var5.group(1));
               var19.setFunction(var5.group(2));
               var19.setLineno(this.getInteger(var5, 3, null));
               var14.add(var19);
            } else {
               if (!this.matches(var11, var17)) {
                  if (this.matches(var13, var17)) {
                     var21 = new SentryStackFrame();
                     String var28 = String.format("%s.%s", var13.group(1), var13.group(2));
                     var21.setModule(var28);
                     var21.setFunction(var13.group(3));
                     var21.setFilename(var13.group(4));
                     var21.setLineno(this.getUInteger(var13, 5, null));
                     var21.setInApp(this.stackTraceFactory.isInApp(var28));
                     var14.add(var21);
                  } else if (this.matches(var15, var17)) {
                     var21 = new SentryStackFrame();
                     String var29 = String.format("%s.%s", var15.group(1), var15.group(2));
                     var21.setModule(var29);
                     var21.setFunction(var15.group(3));
                     var21.setInApp(this.stackTraceFactory.isInApp(var29));
                     var14.add(var21);
                  } else if (this.matches(var7, var17)) {
                     var21 = var4;
                     if (var4 != null) {
                        SentryLockReason var22 = new SentryLockReason();
                        var22.setType(1);
                        var22.setAddress(var7.group(1));
                        var22.setPackageName(var7.group(2));
                        var22.setClassName(var7.group(3));
                        var4.setLock(var22);
                        this.combineThreadLocks(var2, var22);
                        var21 = var4;
                     }
                  } else if (this.matches(var8, var17)) {
                     var21 = var4;
                     if (var4 != null) {
                        SentryLockReason var23 = new SentryLockReason();
                        var23.setType(2);
                        var23.setAddress(var8.group(1));
                        var23.setPackageName(var8.group(2));
                        var23.setClassName(var8.group(3));
                        var4.setLock(var23);
                        this.combineThreadLocks(var2, var23);
                        var21 = var4;
                     }
                  } else if (this.matches(var9, var17)) {
                     var21 = var4;
                     if (var4 != null) {
                        SentryLockReason var24 = new SentryLockReason();
                        var24.setType(4);
                        var24.setAddress(var9.group(1));
                        var24.setPackageName(var9.group(2));
                        var24.setClassName(var9.group(3));
                        var4.setLock(var24);
                        this.combineThreadLocks(var2, var24);
                        var21 = var4;
                     }
                  } else {
                     if (this.matches(var12, var17)) {
                        var21 = var4;
                        if (var4 == null) {
                           break label71;
                        }

                        SentryLockReason var25 = new SentryLockReason();
                        var25.setType(8);
                        var25.setAddress(var12.group(1));
                        var25.setPackageName(var12.group(2));
                        var25.setClassName(var12.group(3));
                        var25.setThreadId(this.getLong(var12, 4, null));
                        var4.setLock(var25);
                        this.combineThreadLocks(var2, var25);
                     } else if (this.matches(var16, var17)) {
                        if (var4 != null) {
                           SentryLockReason var26 = new SentryLockReason();
                           var26.setType(8);
                           var26.setAddress(var16.group(1));
                           var26.setPackageName(var16.group(2));
                           var26.setClassName(var16.group(3));
                           var4.setLock(var26);
                           this.combineThreadLocks(var2, var26);
                        }
                     } else {
                        if (!this.matches(var10, var17)) {
                           if (var17.length() == 0) {
                              break;
                           }

                           var21 = var4;
                           if (this.matches(var6, var17)) {
                              break;
                           }
                           break label71;
                        }

                        if (var4 != null) {
                           SentryLockReason var27 = new SentryLockReason();
                           var27.setType(8);
                           var4.setLock(var27);
                           this.combineThreadLocks(var2, var27);
                        }
                     }

                     var21 = var4;
                  }
                  break label71;
               }

               SentryStackFrame var20 = new SentryStackFrame();
               var20.setPackage(var11.group(1));
               var20.setFunction(var11.group(2));
               var14.add(var20);
            }

            var21 = null;
         }

         var4 = var21;
      }

      Collections.reverse(var14);
      SentryStackTrace var18 = new SentryStackTrace(var14);
      var18.setSnapshot(true);
      return var18;
   }

   private SentryThread parseThread(Lines var1) {
      SentryThread var5 = new SentryThread();
      Matcher var6 = BEGIN_MANAGED_THREAD_RE.matcher("");
      Matcher var7 = BEGIN_UNMANAGED_NATIVE_THREAD_RE.matcher("");
      if (!var1.hasNext()) {
         return null;
      } else {
         Line var8 = var1.next();
         boolean var3 = false;
         if (var8 == null) {
            this.options.getLogger().log(SentryLevel.WARNING, "Internal error while parsing thread dump.");
            return null;
         } else {
            if (this.matches(var6, var8.text)) {
               Long var12 = this.getLong(var6, 4, null);
               if (var12 == null) {
                  this.options.getLogger().log(SentryLevel.DEBUG, "No thread id in the dump, skipping thread.");
                  return null;
               }

               var5.setId(var12);
               var5.setName(var6.group(1));
               String var9 = var6.group(5);
               if (var9 != null) {
                  if (var9.contains(" ")) {
                     var5.setState(var9.substring(0, var9.indexOf(32)));
                  } else {
                     var5.setState(var9);
                  }
               }
            } else if (this.matches(var7, var8.text)) {
               Long var10 = this.getLong(var7, 3, null);
               if (var10 == null) {
                  this.options.getLogger().log(SentryLevel.DEBUG, "No thread id in the dump, skipping thread.");
                  return null;
               }

               var5.setId(var10);
               var5.setName(var7.group(1));
            }

            String var11 = var5.getName();
            if (var11 != null) {
               boolean var4 = var11.equals("main");
               var5.setMain(var4);
               var5.setCrashed(var4);
               boolean var2 = var3;
               if (var4) {
                  var2 = var3;
                  if (!this.isBackground) {
                     var2 = true;
                  }
               }

               var5.setCurrent(var2);
            }

            var5.setStacktrace(this.parseStacktrace(var1, var5));
            return var5;
         }
      }
   }

   public List<SentryThread> parse(Lines var1) {
      ArrayList var3 = new ArrayList();
      Matcher var4 = BEGIN_MANAGED_THREAD_RE.matcher("");
      Matcher var2 = BEGIN_UNMANAGED_NATIVE_THREAD_RE.matcher("");

      while (var1.hasNext()) {
         Line var5 = var1.next();
         if (var5 == null) {
            this.options.getLogger().log(SentryLevel.WARNING, "Internal error while parsing thread dump.");
            return var3;
         }

         String var6 = var5.text;
         if (this.matches(var4, var6) || this.matches(var2, var6)) {
            var1.rewind();
            SentryThread var7 = this.parseThread(var1);
            if (var7 != null) {
               var3.add(var7);
            }
         }
      }

      return var3;
   }
}
