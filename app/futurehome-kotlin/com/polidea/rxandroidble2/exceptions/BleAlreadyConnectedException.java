package com.polidea.rxandroidble2.exceptions;

public class BleAlreadyConnectedException extends BleException {
   public BleAlreadyConnectedException(String var1) {
      StringBuilder var2 = new StringBuilder("Already connected to device with MAC address ");
      var2.append(var1);
      super(var2.toString());
   }
}
