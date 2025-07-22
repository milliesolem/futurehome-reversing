package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SpanContext;
import io.sentry.util.HintUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;

public final class Contexts extends ConcurrentHashMap<String, Object> implements JsonSerializable {
   public static final String REPLAY_ID = "replay_id";
   private static final long serialVersionUID = 252445813254943011L;
   private final Object responseLock = new Object();

   public Contexts() {
   }

   public Contexts(Contexts var1) {
      for (Entry var4 : var1.entrySet()) {
         if (var4 != null) {
            Object var3 = var4.getValue();
            if ("app".equals(var4.getKey()) && var3 instanceof App) {
               this.setApp(new App((App)var3));
            } else if ("browser".equals(var4.getKey()) && var3 instanceof Browser) {
               this.setBrowser(new Browser((Browser)var3));
            } else if ("device".equals(var4.getKey()) && var3 instanceof Device) {
               this.setDevice(new Device((Device)var3));
            } else if ("os".equals(var4.getKey()) && var3 instanceof OperatingSystem) {
               this.setOperatingSystem(new OperatingSystem((OperatingSystem)var3));
            } else if ("runtime".equals(var4.getKey()) && var3 instanceof SentryRuntime) {
               this.setRuntime(new SentryRuntime((SentryRuntime)var3));
            } else if ("gpu".equals(var4.getKey()) && var3 instanceof Gpu) {
               this.setGpu(new Gpu((Gpu)var3));
            } else if ("trace".equals(var4.getKey()) && var3 instanceof SpanContext) {
               this.setTrace(new SpanContext((SpanContext)var3));
            } else if ("response".equals(var4.getKey()) && var3 instanceof Response) {
               this.setResponse(new Response((Response)var3));
            } else {
               this.put((String)var4.getKey(), var3);
            }
         }
      }
   }

   private <T> T toContextType(String var1, Class<T> var2) {
      Object var3 = this.get(var1);
      if (var2.isInstance(var3)) {
         var3 = var2.cast(var3);
      } else {
         var3 = null;
      }

      return (T)var3;
   }

   public App getApp() {
      return this.toContextType("app", App.class);
   }

   public Browser getBrowser() {
      return this.toContextType("browser", Browser.class);
   }

   public Device getDevice() {
      return this.toContextType("device", Device.class);
   }

   public Gpu getGpu() {
      return this.toContextType("gpu", Gpu.class);
   }

   public OperatingSystem getOperatingSystem() {
      return this.toContextType("os", OperatingSystem.class);
   }

   public Response getResponse() {
      return this.toContextType("response", Response.class);
   }

   public SentryRuntime getRuntime() {
      return this.toContextType("runtime", SentryRuntime.class);
   }

   public SpanContext getTrace() {
      return this.toContextType("trace", SpanContext.class);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      ArrayList var3 = Collections.list(this.keys());
      Collections.sort(var3);

      for (String var5 : var3) {
         Object var4 = this.get(var5);
         if (var4 != null) {
            var1.name(var5).value(var2, var4);
         }
      }

      var1.endObject();
   }

   public void setApp(App var1) {
      this.put("app", var1);
   }

   public void setBrowser(Browser var1) {
      this.put("browser", var1);
   }

   public void setDevice(Device var1) {
      this.put("device", var1);
   }

   public void setGpu(Gpu var1) {
      this.put("gpu", var1);
   }

   public void setOperatingSystem(OperatingSystem var1) {
      this.put("os", var1);
   }

   public void setResponse(Response param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/protocol/Contexts.responseLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: ldc "response"
      // 0a: aload 1
      // 0b: invokevirtual io/sentry/protocol/Contexts.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      // 0e: pop
      // 0f: aload 2
      // 10: monitorexit
      // 11: return
      // 12: astore 1
      // 13: aload 2
      // 14: monitorexit
      // 15: aload 1
      // 16: athrow
   }

   public void setRuntime(SentryRuntime var1) {
      this.put("runtime", var1);
   }

   public void setTrace(SpanContext var1) {
      Objects.requireNonNull(var1, "traceContext is required");
      this.put("trace", var1);
   }

   public void withResponse(HintUtils.SentryConsumer<Response> param1) {
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
      // 01: getfield io/sentry/protocol/Contexts.responseLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/protocol/Contexts.getResponse ()Lio/sentry/protocol/Response;
      // 0b: astore 3
      // 0c: aload 3
      // 0d: ifnull 1a
      // 10: aload 1
      // 11: aload 3
      // 12: invokeinterface io/sentry/util/HintUtils$SentryConsumer.accept (Ljava/lang/Object;)V 2
      // 17: goto 2e
      // 1a: new io/sentry/protocol/Response
      // 1d: astore 3
      // 1e: aload 3
      // 1f: invokespecial io/sentry/protocol/Response.<init> ()V
      // 22: aload 0
      // 23: aload 3
      // 24: invokevirtual io/sentry/protocol/Contexts.setResponse (Lio/sentry/protocol/Response;)V
      // 27: aload 1
      // 28: aload 3
      // 29: invokeinterface io/sentry/util/HintUtils$SentryConsumer.accept (Ljava/lang/Object;)V 2
      // 2e: aload 2
      // 2f: monitorexit
      // 30: return
      // 31: astore 1
      // 32: aload 2
      // 33: monitorexit
      // 34: aload 1
      // 35: athrow
   }

   public static final class Deserializer implements JsonDeserializer<Contexts> {
      public Contexts deserialize(ObjectReader var1, ILogger var2) throws Exception {
         Contexts var7 = new Contexts();
         var1.beginObject();

         while (var1.peek() == JsonToken.NAME) {
            String var5 = var1.nextName();
            var5.hashCode();
            int var4 = var5.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1335157162:
                  if (var5.equals("device")) {
                     var3 = 0;
                  }
                  break;
               case -340323263:
                  if (var5.equals("response")) {
                     var3 = 1;
                  }
                  break;
               case 3556:
                  if (var5.equals("os")) {
                     var3 = 2;
                  }
                  break;
               case 96801:
                  if (var5.equals("app")) {
                     var3 = 3;
                  }
                  break;
               case 102572:
                  if (var5.equals("gpu")) {
                     var3 = 4;
                  }
                  break;
               case 110620997:
                  if (var5.equals("trace")) {
                     var3 = 5;
                  }
                  break;
               case 150940456:
                  if (var5.equals("browser")) {
                     var3 = 6;
                  }
                  break;
               case 1550962648:
                  if (var5.equals("runtime")) {
                     var3 = 7;
                  }
            }

            switch (var3) {
               case 0:
                  var7.setDevice(new Device.Deserializer().deserialize(var1, var2));
                  break;
               case 1:
                  var7.setResponse(new Response.Deserializer().deserialize(var1, var2));
                  break;
               case 2:
                  var7.setOperatingSystem(new OperatingSystem.Deserializer().deserialize(var1, var2));
                  break;
               case 3:
                  var7.setApp(new App.Deserializer().deserialize(var1, var2));
                  break;
               case 4:
                  var7.setGpu(new Gpu.Deserializer().deserialize(var1, var2));
                  break;
               case 5:
                  var7.setTrace(new SpanContext.Deserializer().deserialize(var1, var2));
                  break;
               case 6:
                  var7.setBrowser(new Browser.Deserializer().deserialize(var1, var2));
                  break;
               case 7:
                  var7.setRuntime(new SentryRuntime.Deserializer().deserialize(var1, var2));
                  break;
               default:
                  Object var6 = var1.nextObjectOrNull();
                  if (var6 != null) {
                     var7.put(var5, var6);
                  }
            }
         }

         var1.endObject();
         return var7;
      }
   }
}
