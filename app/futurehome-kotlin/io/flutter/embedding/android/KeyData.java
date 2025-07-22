package io.flutter.embedding.android;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class KeyData {
   private static final int BYTES_PER_FIELD = 8;
   public static final String CHANNEL = "flutter/keydata";
   private static final int FIELD_COUNT = 6;
   private static final String TAG = "KeyData";
   String character;
   KeyData.DeviceType deviceType;
   long logicalKey;
   long physicalKey;
   boolean synthesized;
   long timestamp;
   KeyData.Type type;

   public KeyData() {
   }

   public KeyData(ByteBuffer var1) {
      long var3 = var1.getLong();
      this.timestamp = var1.getLong();
      this.type = KeyData.Type.fromLong(var1.getLong());
      this.physicalKey = var1.getLong();
      this.logicalKey = var1.getLong();
      boolean var5;
      if (var1.getLong() != 0L) {
         var5 = true;
      } else {
         var5 = false;
      }

      this.synthesized = var5;
      this.deviceType = KeyData.DeviceType.fromLong(var1.getLong());
      if (var1.remaining() == var3) {
         this.character = null;
         if (var3 != 0L) {
            int var2 = (int)var3;
            byte[] var6 = new byte[var2];
            var1.get(var6, 0, var2);

            try {
               String var8 = new String(var6, "UTF-8");
               this.character = var8;
            } catch (UnsupportedEncodingException var7) {
               throw new AssertionError("UTF-8 unsupported");
            }
         }
      } else {
         throw new AssertionError(
            String.format(
               "Unexpected char length: charSize is %d while buffer has position %d, capacity %d, limit %d",
               var3,
               var1.position(),
               var1.capacity(),
               var1.limit()
            )
         );
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   ByteBuffer toBytes() {
      String var4;
      try {
         var4 = this.character;
      } catch (UnsupportedEncodingException var7) {
         throw new AssertionError("UTF-8 not supported");
      }

      byte[] var8;
      if (var4 == null) {
         var8 = null;
      } else {
         try {
            var8 = var4.getBytes("UTF-8");
         } catch (UnsupportedEncodingException var6) {
            throw new AssertionError("UTF-8 not supported");
         }
      }

      int var1;
      if (var8 == null) {
         var1 = 0;
      } else {
         var1 = var8.length;
      }

      ByteBuffer var5 = ByteBuffer.allocateDirect(var1 + 56);
      var5.order(ByteOrder.LITTLE_ENDIAN);
      var5.putLong(var1);
      var5.putLong(this.timestamp);
      var5.putLong(this.type.getValue());
      var5.putLong(this.physicalKey);
      var5.putLong(this.logicalKey);
      long var2;
      if (this.synthesized) {
         var2 = 1L;
      } else {
         var2 = 0L;
      }

      var5.putLong(var2);
      var5.putLong(this.deviceType.getValue());
      if (var8 != null) {
         var5.put(var8);
      }

      return var5;
   }

   public static enum DeviceType {
      kDirectionalPad(1L),
      kGamepad(2L),
      kHdmi(4L),
      kJoystick(3L),
      kKeyboard(0L);

      private static final KeyData.DeviceType[] $VALUES = $values();
      private final long value;

      private DeviceType(long var3) {
         this.value = var3;
      }

      static KeyData.DeviceType fromLong(long var0) {
         int var2 = (int)var0;
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  if (var2 != 3) {
                     if (var2 == 4) {
                        return kHdmi;
                     } else {
                        throw new AssertionError("Unexpected DeviceType value");
                     }
                  } else {
                     return kJoystick;
                  }
               } else {
                  return kGamepad;
               }
            } else {
               return kDirectionalPad;
            }
         } else {
            return kKeyboard;
         }
      }

      public long getValue() {
         return this.value;
      }
   }

   public static enum Type {
      kDown(0L),
      kRepeat(2L),
      kUp(1L);

      private static final KeyData.Type[] $VALUES = $values();
      private long value;

      private Type(long var3) {
         this.value = var3;
      }

      static KeyData.Type fromLong(long var0) {
         int var2 = (int)var0;
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 == 2) {
                  return kRepeat;
               } else {
                  throw new AssertionError("Unexpected Type value");
               }
            } else {
               return kUp;
            }
         } else {
            return kDown;
         }
      }

      public long getValue() {
         return this.value;
      }
   }
}
