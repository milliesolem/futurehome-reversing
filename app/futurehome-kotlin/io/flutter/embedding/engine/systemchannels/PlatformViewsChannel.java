package io.flutter.embedding.engine.systemchannels;

import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

public class PlatformViewsChannel {
   private static final String TAG = "PlatformViewsChannel";
   private final MethodChannel channel;
   private PlatformViewsChannel.PlatformViewsHandler handler;
   private final MethodChannel.MethodCallHandler parsingHandler;

   public PlatformViewsChannel(DartExecutor var1) {
      MethodChannel.MethodCallHandler var2 = new MethodChannel.MethodCallHandler(this) {
         final PlatformViewsChannel this$0;

         {
            this.this$0 = var1;
         }

         private void clearFocus(MethodCall var1, MethodChannel.Result var2x) {
            int var3 = var1.<Integer>arguments();

            try {
               this.this$0.handler.clearFocus(var3);
               var2x.success(null);
            } catch (IllegalStateException var4) {
               var2x.error("error", PlatformViewsChannel.detailedExceptionString(var4), null);
            }
         }

         // $VF: Duplicated exception handlers to handle obfuscated exceptions
         private void create(MethodCall var1, MethodChannel.Result var2x) {
            Map var13 = var1.arguments();
            boolean var11 = var13.containsKey("hybrid");
            int var8 = 1;
            boolean var7;
            if (var11 && (Boolean)var13.get("hybrid")) {
               var7 = true;
            } else {
               var7 = false;
            }

            ByteBuffer var27;
            if (var13.containsKey("params")) {
               var27 = ByteBuffer.wrap((byte[])var13.get("params"));
            } else {
               var27 = null;
            }

            if (var7) {
               try {
                  PlatformViewsChannel.PlatformViewCreationRequest var12 = new PlatformViewsChannel.PlatformViewCreationRequest(
                     (Integer)var13.get("id"),
                     (String)var13.get("viewType"),
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     (Integer)var13.get("direction"),
                     PlatformViewsChannel.PlatformViewCreationRequest.RequestedDisplayMode.HYBRID_ONLY,
                     var27
                  );
                  this.this$0.handler.createForPlatformViewLayer(var12);
                  var2x.success(null);
               } catch (IllegalStateException var18) {
                  var2x.error("error", PlatformViewsChannel.detailedExceptionString(var18), null);
               }
            } else {
               label103: {
                  label102: {
                     try {
                        if (var13.containsKey("hybridFallback") && (Boolean)var13.get("hybridFallback")) {
                           break label102;
                        }
                     } catch (IllegalStateException var26) {
                        var2x.error("error", PlatformViewsChannel.detailedExceptionString(var26), null);
                        return;
                     }

                     var7 = false;
                     break label103;
                  }

                  var7 = (boolean)var8;
               }

               PlatformViewsChannel.PlatformViewCreationRequest.RequestedDisplayMode var32;
               if (var7) {
                  try {
                     var32 = PlatformViewsChannel.PlatformViewCreationRequest.RequestedDisplayMode.TEXTURE_WITH_HYBRID_FALLBACK;
                  } catch (IllegalStateException var25) {
                     var2x.error("error", PlatformViewsChannel.detailedExceptionString(var25), null);
                     return;
                  }
               } else {
                  try {
                     var32 = PlatformViewsChannel.PlatformViewCreationRequest.RequestedDisplayMode.TEXTURE_WITH_VIRTUAL_FALLBACK;
                  } catch (IllegalStateException var24) {
                     var2x.error("error", PlatformViewsChannel.detailedExceptionString(var24), null);
                     return;
                  }
               }

               PlatformViewsChannel.PlatformViewCreationRequest var14;
               String var15;
               try {
                  var14 = new PlatformViewsChannel.PlatformViewCreationRequest;
                  var8 = (Integer)var13.get("id");
                  var15 = (String)var13.get("viewType");
                  var11 = var13.containsKey("top");
               } catch (IllegalStateException var23) {
                  var2x.error("error", PlatformViewsChannel.detailedExceptionString(var23), null);
                  return;
               }

               double var5 = 0.0;
               double var3;
               if (var11) {
                  try {
                     var3 = (Double)var13.get("top");
                  } catch (IllegalStateException var22) {
                     var2x.error("error", PlatformViewsChannel.detailedExceptionString(var22), null);
                     return;
                  }
               } else {
                  var3 = 0.0;
               }

               try {
                  if (var13.containsKey("left")) {
                     var5 = (Double)var13.get("left");
                  }
               } catch (IllegalStateException var21) {
                  var2x.error("error", PlatformViewsChannel.detailedExceptionString(var21), null);
                  return;
               }

               long var9;
               try {
                  var14./* $VF: Unable to resugar constructor */<init>(
                     var8, var15, var3, var5, (Double)var13.get("width"), (Double)var13.get("height"), (Integer)var13.get("direction"), var32, var27
                  );
                  var9 = this.this$0.handler.createForTextureLayer(var14);
               } catch (IllegalStateException var20) {
                  var2x.error("error", PlatformViewsChannel.detailedExceptionString(var20), null);
                  return;
               }

               if (var9 == -2L) {
                  if (var7) {
                     try {
                        var2x.success(null);
                     } catch (IllegalStateException var17) {
                        var2x.error("error", PlatformViewsChannel.detailedExceptionString(var17), null);
                     }
                  } else {
                     try {
                        AssertionError var28 = new AssertionError("Platform view attempted to fall back to hybrid mode when not requested.");
                        throw var28;
                     } catch (IllegalStateException var19) {
                        var2x.error("error", PlatformViewsChannel.detailedExceptionString(var19), null);
                     }
                  }
               } else {
                  try {
                     var2x.success(var9);
                  } catch (IllegalStateException var16) {
                     var2x.error("error", PlatformViewsChannel.detailedExceptionString(var16), null);
                  }
               }
            }
         }

         private void dispose(MethodCall var1, MethodChannel.Result var2x) {
            int var3 = (Integer)var1.<Map>arguments().get("id");

            try {
               this.this$0.handler.dispose(var3);
               var2x.success(null);
            } catch (IllegalStateException var4) {
               var2x.error("error", PlatformViewsChannel.detailedExceptionString(var4), null);
            }
         }

         private void offset(MethodCall var1, MethodChannel.Result var2x) {
            Map var4 = var1.arguments();

            try {
               this.this$0.handler.offset((Integer)var4.get("id"), (Double)var4.get("top"), (Double)var4.get("left"));
               var2x.success(null);
            } catch (IllegalStateException var3) {
               var2x.error("error", PlatformViewsChannel.detailedExceptionString(var3), null);
            }
         }

         private void resize(MethodCall var1, MethodChannel.Result var2x) {
            Map var6 = var1.arguments();
            PlatformViewsChannel.PlatformViewResizeRequest var3 = new PlatformViewsChannel.PlatformViewResizeRequest(
               (Integer)var6.get("id"), (Double)var6.get("width"), (Double)var6.get("height")
            );

            try {
               PlatformViewsChannel.PlatformViewsHandler var7 = this.this$0.handler;
               PlatformViewsChannel$1$$ExternalSyntheticLambda0 var4 = new PlatformViewsChannel$1$$ExternalSyntheticLambda0(var2x);
               var7.resize(var3, var4);
            } catch (IllegalStateException var5) {
               var2x.error("error", PlatformViewsChannel.detailedExceptionString(var5), null);
            }
         }

         private void setDirection(MethodCall var1, MethodChannel.Result var2x) {
            Map var6 = var1.arguments();
            int var4 = (Integer)var6.get("id");
            int var3 = (Integer)var6.get("direction");

            try {
               this.this$0.handler.setDirection(var4, var3);
               var2x.success(null);
            } catch (IllegalStateException var5) {
               var2x.error("error", PlatformViewsChannel.detailedExceptionString(var5), null);
            }
         }

         private void synchronizeToNativeViewHierarchy(MethodCall var1, MethodChannel.Result var2x) {
            boolean var3 = var1.<Boolean>arguments();

            try {
               this.this$0.handler.synchronizeToNativeViewHierarchy(var3);
               var2x.success(null);
            } catch (IllegalStateException var4) {
               var2x.error("error", PlatformViewsChannel.detailedExceptionString(var4), null);
            }
         }

         private void touch(MethodCall var1, MethodChannel.Result var2x) {
            List var5 = var1.arguments();
            PlatformViewsChannel.PlatformViewTouch var6 = new PlatformViewsChannel.PlatformViewTouch(
               (Integer)var5.get(0),
               (Number)var5.get(1),
               (Number)var5.get(2),
               (Integer)var5.get(3),
               (Integer)var5.get(4),
               var5.get(5),
               var5.get(6),
               (Integer)var5.get(7),
               (Integer)var5.get(8),
               (float)((Double)var5.get(9)).doubleValue(),
               (float)((Double)var5.get(10)).doubleValue(),
               (Integer)var5.get(11),
               (Integer)var5.get(12),
               (Integer)var5.get(13),
               (Integer)var5.get(14),
               ((Number)var5.get(15)).longValue()
            );

            label20: {
               try {
                  this.this$0.handler.onTouch(var6);
               } catch (IllegalStateException var4) {
                  var7 = var4;
                  break label20;
               }

               try {
                  var2x.success(null);
                  return;
               } catch (IllegalStateException var3) {
                  var7 = var3;
               }
            }

            var2x.error("error", PlatformViewsChannel.detailedExceptionString(var7), null);
         }

         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            if (this.this$0.handler != null) {
               StringBuilder var5 = new StringBuilder("Received '");
               var5.append(var1.method);
               var5.append("' message.");
               Log.v("PlatformViewsChannel", var5.toString());
               String var6 = var1.method;
               var6.hashCode();
               int var4 = var6.hashCode();
               byte var3 = -1;
               switch (var4) {
                  case -1352294148:
                     if (var6.equals("create")) {
                        var3 = 0;
                     }
                     break;
                  case -1019779949:
                     if (var6.equals("offset")) {
                        var3 = 1;
                     }
                     break;
                  case -934437708:
                     if (var6.equals("resize")) {
                        var3 = 2;
                     }
                     break;
                  case -756050293:
                     if (var6.equals("clearFocus")) {
                        var3 = 3;
                     }
                     break;
                  case -308988850:
                     if (var6.equals("synchronizeToNativeViewHierarchy")) {
                        var3 = 4;
                     }
                     break;
                  case 110550847:
                     if (var6.equals("touch")) {
                        var3 = 5;
                     }
                     break;
                  case 576796989:
                     if (var6.equals("setDirection")) {
                        var3 = 6;
                     }
                     break;
                  case 1671767583:
                     if (var6.equals("dispose")) {
                        var3 = 7;
                     }
               }

               switch (var3) {
                  case 0:
                     this.create(var1, var2x);
                     break;
                  case 1:
                     this.offset(var1, var2x);
                     break;
                  case 2:
                     this.resize(var1, var2x);
                     break;
                  case 3:
                     this.clearFocus(var1, var2x);
                     break;
                  case 4:
                     this.synchronizeToNativeViewHierarchy(var1, var2x);
                     break;
                  case 5:
                     this.touch(var1, var2x);
                     break;
                  case 6:
                     this.setDirection(var1, var2x);
                     break;
                  case 7:
                     this.dispose(var1, var2x);
                     break;
                  default:
                     var2x.notImplemented();
               }
            }
         }
      };
      this.parsingHandler = var2;
      MethodChannel var3 = new MethodChannel(var1, "flutter/platform_views", StandardMethodCodec.INSTANCE);
      this.channel = var3;
      var3.setMethodCallHandler(var2);
   }

   private static String detailedExceptionString(Exception var0) {
      return Log.getStackTraceString(var0);
   }

   public void invokeViewFocused(int var1) {
      MethodChannel var2 = this.channel;
      if (var2 != null) {
         var2.invokeMethod("viewFocused", var1);
      }
   }

   public void setPlatformViewsHandler(PlatformViewsChannel.PlatformViewsHandler var1) {
      this.handler = var1;
   }

   public interface PlatformViewBufferResized {
      void run(PlatformViewsChannel.PlatformViewBufferSize var1);
   }

   public static class PlatformViewBufferSize {
      public final int height;
      public final int width;

      public PlatformViewBufferSize(int var1, int var2) {
         this.width = var1;
         this.height = var2;
      }
   }

   public static class PlatformViewCreationRequest {
      public final int direction;
      public final PlatformViewsChannel.PlatformViewCreationRequest.RequestedDisplayMode displayMode;
      public final double logicalHeight;
      public final double logicalLeft;
      public final double logicalTop;
      public final double logicalWidth;
      public final ByteBuffer params;
      public final int viewId;
      public final String viewType;

      public PlatformViewCreationRequest(
         int var1,
         String var2,
         double var3,
         double var5,
         double var7,
         double var9,
         int var11,
         PlatformViewsChannel.PlatformViewCreationRequest.RequestedDisplayMode var12,
         ByteBuffer var13
      ) {
         this.viewId = var1;
         this.viewType = var2;
         this.logicalTop = var3;
         this.logicalLeft = var5;
         this.logicalWidth = var7;
         this.logicalHeight = var9;
         this.direction = var11;
         this.displayMode = var12;
         this.params = var13;
      }

      public PlatformViewCreationRequest(int var1, String var2, double var3, double var5, double var7, double var9, int var11, ByteBuffer var12) {
         this(
            var1,
            var2,
            var3,
            var5,
            var7,
            var9,
            var11,
            PlatformViewsChannel.PlatformViewCreationRequest.RequestedDisplayMode.TEXTURE_WITH_VIRTUAL_FALLBACK,
            var12
         );
      }

      public static enum RequestedDisplayMode {
         HYBRID_ONLY,
         TEXTURE_WITH_HYBRID_FALLBACK,
         TEXTURE_WITH_VIRTUAL_FALLBACK;
         private static final PlatformViewsChannel.PlatformViewCreationRequest.RequestedDisplayMode[] $VALUES = $values();
      }
   }

   public static class PlatformViewResizeRequest {
      public final double newLogicalHeight;
      public final double newLogicalWidth;
      public final int viewId;

      public PlatformViewResizeRequest(int var1, double var2, double var4) {
         this.viewId = var1;
         this.newLogicalWidth = var2;
         this.newLogicalHeight = var4;
      }
   }

   public static class PlatformViewTouch {
      public final int action;
      public final int buttonState;
      public final int deviceId;
      public final Number downTime;
      public final int edgeFlags;
      public final Number eventTime;
      public final int flags;
      public final int metaState;
      public final long motionEventId;
      public final int pointerCount;
      public final Object rawPointerCoords;
      public final Object rawPointerPropertiesList;
      public final int source;
      public final int viewId;
      public final float xPrecision;
      public final float yPrecision;

      public PlatformViewTouch(
         int var1,
         Number var2,
         Number var3,
         int var4,
         int var5,
         Object var6,
         Object var7,
         int var8,
         int var9,
         float var10,
         float var11,
         int var12,
         int var13,
         int var14,
         int var15,
         long var16
      ) {
         this.viewId = var1;
         this.downTime = var2;
         this.eventTime = var3;
         this.action = var4;
         this.pointerCount = var5;
         this.rawPointerPropertiesList = var6;
         this.rawPointerCoords = var7;
         this.metaState = var8;
         this.buttonState = var9;
         this.xPrecision = var10;
         this.yPrecision = var11;
         this.deviceId = var12;
         this.edgeFlags = var13;
         this.source = var14;
         this.flags = var15;
         this.motionEventId = var16;
      }
   }

   public interface PlatformViewsHandler {
      long NON_TEXTURE_FALLBACK = -2L;

      void clearFocus(int var1);

      void createForPlatformViewLayer(PlatformViewsChannel.PlatformViewCreationRequest var1);

      long createForTextureLayer(PlatformViewsChannel.PlatformViewCreationRequest var1);

      void dispose(int var1);

      void offset(int var1, double var2, double var4);

      void onTouch(PlatformViewsChannel.PlatformViewTouch var1);

      void resize(PlatformViewsChannel.PlatformViewResizeRequest var1, PlatformViewsChannel.PlatformViewBufferResized var2);

      void setDirection(int var1, int var2);

      void synchronizeToNativeViewHierarchy(boolean var1);
   }
}
