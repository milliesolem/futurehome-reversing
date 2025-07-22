package io.flutter.plugin.common;

import io.flutter.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class StandardMethodCodec implements MethodCodec {
   public static final StandardMethodCodec INSTANCE = new StandardMethodCodec(StandardMessageCodec.INSTANCE);
   private final StandardMessageCodec messageCodec;

   public StandardMethodCodec(StandardMessageCodec var1) {
      this.messageCodec = var1;
   }

   @Override
   public Object decodeEnvelope(ByteBuffer var1) {
      var1.order(ByteOrder.nativeOrder());
      byte var2 = var1.get();
      if (var2 != 0) {
         if (var2 != 1) {
            throw new IllegalArgumentException("Envelope corrupted");
         }
      } else {
         Object var3 = this.messageCodec.readValue(var1);
         if (!var1.hasRemaining()) {
            return var3;
         }
      }

      Object var6 = this.messageCodec.readValue(var1);
      Object var4 = this.messageCodec.readValue(var1);
      Object var5 = this.messageCodec.readValue(var1);
      if (var6 instanceof String && (var4 == null || var4 instanceof String) && !var1.hasRemaining()) {
         throw new FlutterException((String)var6, (String)var4, var5);
      } else {
         throw new IllegalArgumentException("Envelope corrupted");
      }
   }

   @Override
   public MethodCall decodeMethodCall(ByteBuffer var1) {
      var1.order(ByteOrder.nativeOrder());
      Object var3 = this.messageCodec.readValue(var1);
      Object var2 = this.messageCodec.readValue(var1);
      if (var3 instanceof String && !var1.hasRemaining()) {
         return new MethodCall((String)var3, var2);
      } else {
         throw new IllegalArgumentException("Method call corrupted");
      }
   }

   @Override
   public ByteBuffer encodeErrorEnvelope(String var1, String var2, Object var3) {
      StandardMessageCodec.ExposedByteArrayOutputStream var4 = new StandardMessageCodec.ExposedByteArrayOutputStream();
      var4.write(1);
      this.messageCodec.writeValue(var4, var1);
      this.messageCodec.writeValue(var4, var2);
      if (var3 instanceof Throwable) {
         this.messageCodec.writeValue(var4, Log.getStackTraceString((Throwable)var3));
      } else {
         this.messageCodec.writeValue(var4, var3);
      }

      ByteBuffer var5 = ByteBuffer.allocateDirect(var4.size());
      var5.put(var4.buffer(), 0, var4.size());
      return var5;
   }

   @Override
   public ByteBuffer encodeErrorEnvelopeWithStacktrace(String var1, String var2, Object var3, String var4) {
      StandardMessageCodec.ExposedByteArrayOutputStream var5 = new StandardMessageCodec.ExposedByteArrayOutputStream();
      var5.write(1);
      this.messageCodec.writeValue(var5, var1);
      this.messageCodec.writeValue(var5, var2);
      if (var3 instanceof Throwable) {
         this.messageCodec.writeValue(var5, Log.getStackTraceString((Throwable)var3));
      } else {
         this.messageCodec.writeValue(var5, var3);
      }

      this.messageCodec.writeValue(var5, var4);
      ByteBuffer var6 = ByteBuffer.allocateDirect(var5.size());
      var6.put(var5.buffer(), 0, var5.size());
      return var6;
   }

   @Override
   public ByteBuffer encodeMethodCall(MethodCall var1) {
      StandardMessageCodec.ExposedByteArrayOutputStream var2 = new StandardMessageCodec.ExposedByteArrayOutputStream();
      this.messageCodec.writeValue(var2, var1.method);
      this.messageCodec.writeValue(var2, var1.arguments);
      ByteBuffer var3 = ByteBuffer.allocateDirect(var2.size());
      var3.put(var2.buffer(), 0, var2.size());
      return var3;
   }

   @Override
   public ByteBuffer encodeSuccessEnvelope(Object var1) {
      StandardMessageCodec.ExposedByteArrayOutputStream var2 = new StandardMessageCodec.ExposedByteArrayOutputStream();
      var2.write(0);
      this.messageCodec.writeValue(var2, var1);
      var1 = ByteBuffer.allocateDirect(var2.size());
      var1.put(var2.buffer(), 0, var2.size());
      return var1;
   }
}
