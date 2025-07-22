package com.polidea.rxandroidble2.exceptions;

import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.utils.GattStatusParser;

public class BleDisconnectedException extends BleException {
   public static final int UNKNOWN_STATUS = -1;
   public final String bluetoothDeviceAddress;
   public final int state;

   @Deprecated
   public BleDisconnectedException() {
      this("", -1);
   }

   @Deprecated
   public BleDisconnectedException(String var1) {
      this(var1, -1);
   }

   public BleDisconnectedException(String var1, int var2) {
      super(createMessage(var1, var2));
      this.bluetoothDeviceAddress = var1;
      this.state = var2;
   }

   @Deprecated
   public BleDisconnectedException(Throwable var1, String var2) {
      this(var1, var2, -1);
   }

   public BleDisconnectedException(Throwable var1, String var2, int var3) {
      super(createMessage(var2, var3), var1);
      this.bluetoothDeviceAddress = var2;
      this.state = var3;
   }

   public static BleDisconnectedException adapterDisabled(String var0) {
      return new BleDisconnectedException(new BleAdapterDisabledException(), var0, -1);
   }

   private static String createMessage(String var0, int var1) {
      String var3 = GattStatusParser.getGattCallbackStatusDescription(var1);
      StringBuilder var2 = new StringBuilder("Disconnected from ");
      var2.append(LoggerUtil.commonMacMessage(var0));
      var2.append(" with status ");
      var2.append(var1);
      var2.append(" (");
      var2.append(var3);
      var2.append(")");
      return var2.toString();
   }
}
