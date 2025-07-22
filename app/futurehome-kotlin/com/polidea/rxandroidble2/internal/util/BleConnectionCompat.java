package com.polidea.rxandroidble2.internal.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.os.Build.VERSION;
import androidx.webkit.internal.ApiHelperForN..ExternalSyntheticApiModelOutline4;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.RxBleLog;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BleConnectionCompat {
   private final Context context;

   @Inject
   public BleConnectionCompat(Context var1) {
      this.context = var1;
   }

   private BluetoothGatt connectGattCompat(BluetoothGattCallback var1, BluetoothDevice var2, boolean var3) {
      RxBleLog.v("Connecting without reflection");
      return VERSION.SDK_INT >= 23 ? ExternalSyntheticApiModelOutline4.m(var2, this.context, var3, var1, 2) : var2.connectGatt(this.context, var3, var1);
   }

   private static boolean connectUsingReflection(BluetoothGatt var0, BluetoothGattCallback var1, boolean var2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
      RxBleLog.v("Connecting using reflection");
      setAutoConnectValue(var0, var2);
      Method var3 = var0.getClass().getDeclaredMethod("connect", Boolean.class, BluetoothGattCallback.class);
      var3.setAccessible(true);
      return (Boolean)var3.invoke(var0, true, var1);
   }

   private BluetoothGatt createBluetoothGatt(Object var1, BluetoothDevice var2) throws IllegalAccessException, InvocationTargetException, InstantiationException {
      Constructor var3 = BluetoothGatt.class.getDeclaredConstructors()[0];
      var3.setAccessible(true);
      StringBuilder var4 = new StringBuilder("Found constructor with args count = ");
      var4.append(var3.getParameterTypes().length);
      RxBleLog.v(var4.toString());
      return var3.getParameterTypes().length == 4
         ? (BluetoothGatt)var3.newInstance(this.context, var1, var2, 2)
         : (BluetoothGatt)var3.newInstance(this.context, var1, var2);
   }

   private static Object getIBluetoothGatt(Object var0) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      return var0 == null ? null : getMethodFromClass(var0.getClass(), "getBluetoothGatt").invoke(var0, null);
   }

   private static Object getIBluetoothManager() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      BluetoothAdapter var0 = BluetoothAdapter.getDefaultAdapter();
      return var0 == null ? null : getMethodFromClass(var0.getClass(), "getBluetoothManager").invoke(var0, null);
   }

   private static Method getMethodFromClass(Class<?> var0, String var1) throws NoSuchMethodException {
      Method var2 = var0.getDeclaredMethod(var1, null);
      var2.setAccessible(true);
      return var2;
   }

   private static void setAutoConnectValue(BluetoothGatt var0, boolean var1) throws NoSuchFieldException, IllegalAccessException {
      Field var2 = var0.getClass().getDeclaredField("mAutoConnect");
      var2.setAccessible(true);
      var2.setBoolean(var0, var1);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public BluetoothGatt connectGatt(BluetoothDevice var1, boolean var2, BluetoothGattCallback var3) {
      if (var1 == null) {
         return null;
      } else if (VERSION.SDK_INT < 24 && var2) {
         BluetoothGatt var4;
         label94: {
            try {
               RxBleLog.v("Trying to connectGatt using reflection.");
               var4 = getIBluetoothGatt(getIBluetoothManager());
            } catch (NoSuchMethodException var29) {
               var4 = var29;
               break label94;
            } catch (IllegalAccessException var30) {
               var4 = var30;
               break label94;
            } catch (IllegalArgumentException var31) {
               var4 = var31;
               break label94;
            } catch (InvocationTargetException var32) {
               var4 = var32;
               break label94;
            } catch (InstantiationException var33) {
               var4 = var33;
               break label94;
            } catch (NoSuchFieldException var34) {
               var4 = var34;
               break label94;
            }

            if (var4 == null) {
               try {
                  RxBleLog.w("Couldn't get iBluetoothGatt object");
                  return this.connectGattCompat(var3, var1, true);
               } catch (NoSuchMethodException var5) {
                  var4 = var5;
               } catch (IllegalAccessException var6) {
                  var4 = var6;
               } catch (IllegalArgumentException var7) {
                  var4 = var7;
               } catch (InvocationTargetException var8) {
                  var4 = var8;
               } catch (InstantiationException var9) {
                  var4 = var9;
               } catch (NoSuchFieldException var10) {
                  var4 = var10;
               }
            } else {
               label90: {
                  try {
                     var4 = this.createBluetoothGatt(var4, var1);
                  } catch (NoSuchMethodException var23) {
                     var4 = var23;
                     break label90;
                  } catch (IllegalAccessException var24) {
                     var4 = var24;
                     break label90;
                  } catch (IllegalArgumentException var25) {
                     var4 = var25;
                     break label90;
                  } catch (InvocationTargetException var26) {
                     var4 = var26;
                     break label90;
                  } catch (InstantiationException var27) {
                     var4 = var27;
                     break label90;
                  } catch (NoSuchFieldException var28) {
                     var4 = var28;
                     break label90;
                  }

                  if (var4 == null) {
                     try {
                        RxBleLog.w("Couldn't create BluetoothGatt object");
                        return this.connectGattCompat(var3, var1, true);
                     } catch (NoSuchMethodException var11) {
                        var4 = var11;
                     } catch (IllegalAccessException var12) {
                        var4 = var12;
                     } catch (IllegalArgumentException var13) {
                        var4 = var13;
                     } catch (InvocationTargetException var14) {
                        var4 = var14;
                     } catch (InstantiationException var15) {
                        var4 = var15;
                     } catch (NoSuchFieldException var16) {
                        var4 = var16;
                     }
                  } else {
                     try {
                        if (!connectUsingReflection(var4, var3, true)) {
                           RxBleLog.w("Connection using reflection failed, closing gatt");
                           var4.close();
                        }

                        return var4;
                     } catch (NoSuchMethodException var17) {
                        var4 = var17;
                     } catch (IllegalAccessException var18) {
                        var4 = var18;
                     } catch (IllegalArgumentException var19) {
                        var4 = var19;
                     } catch (InvocationTargetException var20) {
                        var4 = var20;
                     } catch (InstantiationException var21) {
                        var4 = var21;
                     } catch (NoSuchFieldException var22) {
                        var4 = var22;
                     }
                  }
               }
            }
         }

         RxBleLog.w(var4, "Error while trying to connect via reflection");
         return this.connectGattCompat(var3, var1, true);
      } else {
         return this.connectGattCompat(var3, var1, var2);
      }
   }
}
