package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryLockReason;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class SentryStackFrame implements JsonUnknown, JsonSerializable {
   private Boolean _native;
   private String _package;
   private String absPath;
   private Integer colno;
   private String contextLine;
   private String filename;
   private List<Integer> framesOmitted;
   private String function;
   private String imageAddr;
   private Boolean inApp;
   private String instructionAddr;
   private Integer lineno;
   private SentryLockReason lock;
   private String module;
   private String platform;
   private List<String> postContext;
   private List<String> preContext;
   private String rawFunction;
   private String symbol;
   private String symbolAddr;
   private Map<String, Object> unknown;
   private Map<String, String> vars;

   public String getAbsPath() {
      return this.absPath;
   }

   public Integer getColno() {
      return this.colno;
   }

   public String getContextLine() {
      return this.contextLine;
   }

   public String getFilename() {
      return this.filename;
   }

   public List<Integer> getFramesOmitted() {
      return this.framesOmitted;
   }

   public String getFunction() {
      return this.function;
   }

   public String getImageAddr() {
      return this.imageAddr;
   }

   public String getInstructionAddr() {
      return this.instructionAddr;
   }

   public Integer getLineno() {
      return this.lineno;
   }

   public SentryLockReason getLock() {
      return this.lock;
   }

   public String getModule() {
      return this.module;
   }

   public String getPackage() {
      return this._package;
   }

   public String getPlatform() {
      return this.platform;
   }

   public List<String> getPostContext() {
      return this.postContext;
   }

   public List<String> getPreContext() {
      return this.preContext;
   }

   public String getRawFunction() {
      return this.rawFunction;
   }

   public String getSymbol() {
      return this.symbol;
   }

   public String getSymbolAddr() {
      return this.symbolAddr;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public Map<String, String> getVars() {
      return this.vars;
   }

   public Boolean isInApp() {
      return this.inApp;
   }

   public Boolean isNative() {
      return this._native;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.filename != null) {
         var1.name("filename").value(this.filename);
      }

      if (this.function != null) {
         var1.name("function").value(this.function);
      }

      if (this.module != null) {
         var1.name("module").value(this.module);
      }

      if (this.lineno != null) {
         var1.name("lineno").value(this.lineno);
      }

      if (this.colno != null) {
         var1.name("colno").value(this.colno);
      }

      if (this.absPath != null) {
         var1.name("abs_path").value(this.absPath);
      }

      if (this.contextLine != null) {
         var1.name("context_line").value(this.contextLine);
      }

      if (this.inApp != null) {
         var1.name("in_app").value(this.inApp);
      }

      if (this._package != null) {
         var1.name("package").value(this._package);
      }

      if (this._native != null) {
         var1.name("native").value(this._native);
      }

      if (this.platform != null) {
         var1.name("platform").value(this.platform);
      }

      if (this.imageAddr != null) {
         var1.name("image_addr").value(this.imageAddr);
      }

      if (this.symbolAddr != null) {
         var1.name("symbol_addr").value(this.symbolAddr);
      }

      if (this.instructionAddr != null) {
         var1.name("instruction_addr").value(this.instructionAddr);
      }

      if (this.rawFunction != null) {
         var1.name("raw_function").value(this.rawFunction);
      }

      if (this.symbol != null) {
         var1.name("symbol").value(this.symbol);
      }

      if (this.lock != null) {
         var1.name("lock").value(var2, this.lock);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setAbsPath(String var1) {
      this.absPath = var1;
   }

   public void setColno(Integer var1) {
      this.colno = var1;
   }

   public void setContextLine(String var1) {
      this.contextLine = var1;
   }

   public void setFilename(String var1) {
      this.filename = var1;
   }

   public void setFramesOmitted(List<Integer> var1) {
      this.framesOmitted = var1;
   }

   public void setFunction(String var1) {
      this.function = var1;
   }

   public void setImageAddr(String var1) {
      this.imageAddr = var1;
   }

   public void setInApp(Boolean var1) {
      this.inApp = var1;
   }

   public void setInstructionAddr(String var1) {
      this.instructionAddr = var1;
   }

   public void setLineno(Integer var1) {
      this.lineno = var1;
   }

   public void setLock(SentryLockReason var1) {
      this.lock = var1;
   }

   public void setModule(String var1) {
      this.module = var1;
   }

   public void setNative(Boolean var1) {
      this._native = var1;
   }

   public void setPackage(String var1) {
      this._package = var1;
   }

   public void setPlatform(String var1) {
      this.platform = var1;
   }

   public void setPostContext(List<String> var1) {
      this.postContext = var1;
   }

   public void setPreContext(List<String> var1) {
      this.preContext = var1;
   }

   public void setRawFunction(String var1) {
      this.rawFunction = var1;
   }

   public void setSymbol(String var1) {
      this.symbol = var1;
   }

   public void setSymbolAddr(String var1) {
      this.symbolAddr = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setVars(Map<String, String> var1) {
      this.vars = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryStackFrame> {
      public SentryStackFrame deserialize(ObjectReader var1, ILogger var2) throws Exception {
         SentryStackFrame var7 = new SentryStackFrame();
         var1.beginObject();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1443345323:
                  if (var8.equals("image_addr")) {
                     var3 = 0;
                  }
                  break;
               case -1184392185:
                  if (var8.equals("in_app")) {
                     var3 = 1;
                  }
                  break;
               case -1113875953:
                  if (var8.equals("raw_function")) {
                     var3 = 2;
                  }
                  break;
               case -1102671691:
                  if (var8.equals("lineno")) {
                     var3 = 3;
                  }
                  break;
               case -1068784020:
                  if (var8.equals("module")) {
                     var3 = 4;
                  }
                  break;
               case -1052618729:
                  if (var8.equals("native")) {
                     var3 = 5;
                  }
                  break;
               case -887523944:
                  if (var8.equals("symbol")) {
                     var3 = 6;
                  }
                  break;
               case -807062458:
                  if (var8.equals("package")) {
                     var3 = 7;
                  }
                  break;
               case -734768633:
                  if (var8.equals("filename")) {
                     var3 = 8;
                  }
                  break;
               case -330260936:
                  if (var8.equals("symbol_addr")) {
                     var3 = 9;
                  }
                  break;
               case 3327275:
                  if (var8.equals("lock")) {
                     var3 = 10;
                  }
                  break;
               case 94842689:
                  if (var8.equals("colno")) {
                     var3 = 11;
                  }
                  break;
               case 410194178:
                  if (var8.equals("instruction_addr")) {
                     var3 = 12;
                  }
                  break;
               case 1116694660:
                  if (var8.equals("context_line")) {
                     var3 = 13;
                  }
                  break;
               case 1380938712:
                  if (var8.equals("function")) {
                     var3 = 14;
                  }
                  break;
               case 1713445842:
                  if (var8.equals("abs_path")) {
                     var3 = 15;
                  }
                  break;
               case 1874684019:
                  if (var8.equals("platform")) {
                     var3 = 16;
                  }
            }

            switch (var3) {
               case 0:
                  var7.imageAddr = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.inApp = var1.nextBooleanOrNull();
                  break;
               case 2:
                  var7.rawFunction = var1.nextStringOrNull();
                  break;
               case 3:
                  var7.lineno = var1.nextIntegerOrNull();
                  break;
               case 4:
                  var7.module = var1.nextStringOrNull();
                  break;
               case 5:
                  var7._native = var1.nextBooleanOrNull();
                  break;
               case 6:
                  var7.symbol = var1.nextStringOrNull();
                  break;
               case 7:
                  var7._package = var1.nextStringOrNull();
                  break;
               case 8:
                  var7.filename = var1.nextStringOrNull();
                  break;
               case 9:
                  var7.symbolAddr = var1.nextStringOrNull();
                  break;
               case 10:
                  var7.lock = var1.nextOrNull(var2, new SentryLockReason.Deserializer());
                  break;
               case 11:
                  var7.colno = var1.nextIntegerOrNull();
                  break;
               case 12:
                  var7.instructionAddr = var1.nextStringOrNull();
                  break;
               case 13:
                  var7.contextLine = var1.nextStringOrNull();
                  break;
               case 14:
                  var7.function = var1.nextStringOrNull();
                  break;
               case 15:
                  var7.absPath = var1.nextStringOrNull();
                  break;
               case 16:
                  var7.platform = var1.nextStringOrNull();
                  break;
               default:
                  ConcurrentHashMap var6 = var5;
                  if (var5 == null) {
                     var6 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var6, var8);
                  var5 = var6;
            }
         }

         var7.setUnknown(var5);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String ABS_PATH = "abs_path";
      public static final String COLNO = "colno";
      public static final String CONTEXT_LINE = "context_line";
      public static final String FILENAME = "filename";
      public static final String FUNCTION = "function";
      public static final String IMAGE_ADDR = "image_addr";
      public static final String INSTRUCTION_ADDR = "instruction_addr";
      public static final String IN_APP = "in_app";
      public static final String LINENO = "lineno";
      public static final String LOCK = "lock";
      public static final String MODULE = "module";
      public static final String NATIVE = "native";
      public static final String PACKAGE = "package";
      public static final String PLATFORM = "platform";
      public static final String RAW_FUNCTION = "raw_function";
      public static final String SYMBOL = "symbol";
      public static final String SYMBOL_ADDR = "symbol_addr";
   }
}
