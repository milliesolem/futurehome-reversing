package io.flutter.plugin.common;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StandardMessageCodec implements MessageCodec<Object> {
   private static final byte BIGINT = 5;
   private static final byte BYTE_ARRAY = 8;
   private static final byte DOUBLE = 6;
   private static final byte DOUBLE_ARRAY = 11;
   private static final byte FALSE = 2;
   private static final byte FLOAT_ARRAY = 14;
   public static final StandardMessageCodec INSTANCE = new StandardMessageCodec();
   private static final byte INT = 3;
   private static final byte INT_ARRAY = 9;
   private static final byte LIST = 12;
   private static final boolean LITTLE_ENDIAN;
   private static final byte LONG = 4;
   private static final byte LONG_ARRAY = 10;
   private static final byte MAP = 13;
   private static final byte NULL = 0;
   private static final byte STRING = 7;
   private static final String TAG = "StandardMessageCodec#";
   private static final byte TRUE = 1;
   private static final Charset UTF8;

   static {
      boolean var0;
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         var0 = true;
      } else {
         var0 = false;
      }

      LITTLE_ENDIAN = var0;
      UTF8 = Charset.forName("UTF8");
   }

   protected static final void readAlignment(ByteBuffer var0, int var1) {
      int var2 = var0.position() % var1;
      if (var2 != 0) {
         ((Buffer)var0).position(var0.position() + var1 - var2);
      }
   }

   protected static final byte[] readBytes(ByteBuffer var0) {
      byte[] var1 = new byte[readSize(var0)];
      var0.get(var1);
      return var1;
   }

   protected static final int readSize(ByteBuffer var0) {
      if (var0.hasRemaining()) {
         int var1 = var0.get() & 255;
         if (var1 < 254) {
            return var1;
         } else {
            return var1 == 254 ? var0.getChar() : var0.getInt();
         }
      } else {
         throw new IllegalArgumentException("Message corrupted");
      }
   }

   protected static final void writeAlignment(ByteArrayOutputStream var0, int var1) {
      int var3 = var0.size() % var1;
      if (var3 != 0) {
         for (int var2 = 0; var2 < var1 - var3; var2++) {
            var0.write(0);
         }
      }
   }

   protected static final void writeBytes(ByteArrayOutputStream var0, byte[] var1) {
      writeSize(var0, var1.length);
      var0.write(var1, 0, var1.length);
   }

   protected static final void writeChar(ByteArrayOutputStream var0, int var1) {
      if (LITTLE_ENDIAN) {
         var0.write(var1);
         var0.write(var1 >>> 8);
      } else {
         var0.write(var1 >>> 8);
         var0.write(var1);
      }
   }

   protected static final void writeDouble(ByteArrayOutputStream var0, double var1) {
      writeLong(var0, Double.doubleToLongBits(var1));
   }

   protected static final void writeFloat(ByteArrayOutputStream var0, float var1) {
      writeInt(var0, Float.floatToIntBits(var1));
   }

   protected static final void writeInt(ByteArrayOutputStream var0, int var1) {
      if (LITTLE_ENDIAN) {
         var0.write(var1);
         var0.write(var1 >>> 8);
         var0.write(var1 >>> 16);
         var0.write(var1 >>> 24);
      } else {
         var0.write(var1 >>> 24);
         var0.write(var1 >>> 16);
         var0.write(var1 >>> 8);
         var0.write(var1);
      }
   }

   protected static final void writeLong(ByteArrayOutputStream var0, long var1) {
      if (LITTLE_ENDIAN) {
         var0.write((byte)var1);
         var0.write((byte)(var1 >>> 8));
         var0.write((byte)(var1 >>> 16));
         var0.write((byte)(var1 >>> 24));
         var0.write((byte)(var1 >>> 32));
         var0.write((byte)(var1 >>> 40));
         var0.write((byte)(var1 >>> 48));
         var0.write((byte)(var1 >>> 56));
      } else {
         var0.write((byte)(var1 >>> 56));
         var0.write((byte)(var1 >>> 48));
         var0.write((byte)(var1 >>> 40));
         var0.write((byte)(var1 >>> 32));
         var0.write((byte)(var1 >>> 24));
         var0.write((byte)(var1 >>> 16));
         var0.write((byte)(var1 >>> 8));
         var0.write((byte)var1);
      }
   }

   protected static final void writeSize(ByteArrayOutputStream var0, int var1) {
      if (var1 < 254) {
         var0.write(var1);
      } else if (var1 <= 65535) {
         var0.write(254);
         writeChar(var0, var1);
      } else {
         var0.write(255);
         writeInt(var0, var1);
      }
   }

   @Override
   public Object decodeMessage(ByteBuffer var1) {
      if (var1 == null) {
         return null;
      } else {
         var1.order(ByteOrder.nativeOrder());
         Object var2 = this.readValue(var1);
         if (!var1.hasRemaining()) {
            return var2;
         } else {
            throw new IllegalArgumentException("Message corrupted");
         }
      }
   }

   @Override
   public ByteBuffer encodeMessage(Object var1) {
      if (var1 == null) {
         return null;
      } else {
         StandardMessageCodec.ExposedByteArrayOutputStream var2 = new StandardMessageCodec.ExposedByteArrayOutputStream();
         this.writeValue(var2, var1);
         var1 = ByteBuffer.allocateDirect(var2.size());
         var1.put(var2.buffer(), 0, var2.size());
         return var1;
      }
   }

   protected final Object readValue(ByteBuffer var1) {
      if (var1.hasRemaining()) {
         return this.readValueOfType(var1.get(), var1);
      } else {
         throw new IllegalArgumentException("Message corrupted");
      }
   }

   protected Object readValueOfType(byte var1, ByteBuffer var2) {
      int var4 = 0;
      int var3 = 0;
      switch (var1) {
         case 0:
            return null;
         case 1:
            return true;
         case 2:
            return false;
         case 3:
            return var2.getInt();
         case 4:
            return var2.getLong();
         case 5:
            return new BigInteger(new String(readBytes(var2), UTF8), 16);
         case 6:
            readAlignment(var2, 8);
            return var2.getDouble();
         case 7:
            return new String(readBytes(var2), UTF8);
         case 8:
            return readBytes(var2);
         case 9:
            var1 = readSize(var2);
            int[] var19 = new int[var1];
            readAlignment(var2, 4);
            var2.asIntBuffer().get(var19);
            ((Buffer)var2).position(var2.position() + var1 * 4);
            return var19;
         case 10:
            var1 = readSize(var2);
            long[] var18 = new long[var1];
            readAlignment(var2, 8);
            var2.asLongBuffer().get(var18);
            ((Buffer)var2).position(var2.position() + var1 * 8);
            return var18;
         case 11:
            var1 = readSize(var2);
            double[] var17 = new double[var1];
            readAlignment(var2, 8);
            var2.asDoubleBuffer().get(var17);
            ((Buffer)var2).position(var2.position() + var1 * 8);
            return var17;
         case 12:
            var3 = readSize(var2);
            ArrayList var20 = new ArrayList(var3);
            var1 = var4;

            while (true) {
               if (var1 >= var3) {
                  return var20;
               }

               var20.add(this.readValue(var2));
               var1++;
            }
         case 13:
            var4 = readSize(var2);
            HashMap var6 = new HashMap();
            var1 = var3;

            while (true) {
               if (var1 >= var4) {
                  return var6;
               }

               var6.put(this.readValue(var2), this.readValue(var2));
               var1++;
            }
         case 14:
            var1 = readSize(var2);
            float[] var5 = new float[var1];
            readAlignment(var2, 4);
            var2.asFloatBuffer().get(var5);
            ((Buffer)var2).position(var2.position() + var1 * 4);
            return var5;
         default:
            throw new IllegalArgumentException("Message corrupted");
      }
   }

   protected void writeValue(ByteArrayOutputStream var1, Object var2) {
      int var3 = 0;
      int var5 = 0;
      byte var6 = 0;
      int var4 = 0;
      if (var2 == null || var2.equals(null)) {
         var1.write(0);
      } else if (var2 instanceof Boolean) {
         byte var18;
         if ((Boolean)var2) {
            var18 = 1;
         } else {
            var18 = 2;
         }

         var1.write(var18);
      } else if (var2 instanceof Number) {
         if (var2 instanceof Integer || var2 instanceof Short || var2 instanceof Byte) {
            var1.write(3);
            writeInt(var1, ((Number)var2).intValue());
         } else if (var2 instanceof Long) {
            var1.write(4);
            writeLong(var1, (Long)var2);
         } else if (!(var2 instanceof Float) && !(var2 instanceof Double)) {
            if (!(var2 instanceof BigInteger)) {
               StringBuilder var8 = new StringBuilder("Unsupported Number type: ");
               var8.append(var2.getClass());
               throw new IllegalArgumentException(var8.toString());
            }

            var1.write(5);
            writeBytes(var1, ((BigInteger)var2).toString(16).getBytes(UTF8));
         } else {
            var1.write(6);
            writeAlignment(var1, 8);
            writeDouble(var1, ((Number)var2).doubleValue());
         }
      } else if (var2 instanceof CharSequence) {
         var1.write(7);
         writeBytes(var1, var2.toString().getBytes(UTF8));
      } else if (var2 instanceof byte[]) {
         var1.write(8);
         writeBytes(var1, (byte[])var2);
      } else if (var2 instanceof int[]) {
         var1.write(9);
         var2 = var2;
         writeSize(var1, var2.length);
         writeAlignment(var1, 4);
         var5 = var2.length;

         for (int var19 = var4; var19 < var5; var19++) {
            writeInt(var1, var2[var19]);
         }
      } else if (var2 instanceof long[]) {
         var1.write(10);
         long[] var11 = (long[])var2;
         writeSize(var1, var11.length);
         writeAlignment(var1, 8);

         for (int var22 = var11.length; var3 < var22; var3++) {
            writeLong(var1, var11[var3]);
         }
      } else if (var2 instanceof double[]) {
         var1.write(11);
         double[] var12 = (double[])var2;
         writeSize(var1, var12.length);
         writeAlignment(var1, 8);
         var4 = var12.length;

         for (int var20 = var5; var20 < var4; var20++) {
            writeDouble(var1, var12[var20]);
         }
      } else if (var2 instanceof List) {
         var1.write(12);
         List var13 = (List)var2;
         writeSize(var1, var13.size());
         Iterator var14 = var13.iterator();

         while (var14.hasNext()) {
            this.writeValue(var1, var14.next());
         }
      } else if (var2 instanceof Map) {
         var1.write(13);
         Map var15 = (Map)var2;
         writeSize(var1, var15.size());

         for (Entry var16 : var15.entrySet()) {
            this.writeValue(var1, var16.getKey());
            this.writeValue(var1, var16.getValue());
         }
      } else {
         if (!(var2 instanceof float[])) {
            StringBuilder var9 = new StringBuilder("Unsupported value: '");
            var9.append(var2);
            var9.append("' of type '");
            var9.append(var2.getClass());
            var9.append("'");
            throw new IllegalArgumentException(var9.toString());
         }

         var1.write(14);
         float[] var17 = (float[])var2;
         writeSize(var1, var17.length);
         writeAlignment(var1, 4);
         var4 = var17.length;

         for (int var21 = var6; var21 < var4; var21++) {
            writeFloat(var1, var17[var21]);
         }
      }
   }

   static final class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
      byte[] buffer() {
         return this.buf;
      }
   }
}
