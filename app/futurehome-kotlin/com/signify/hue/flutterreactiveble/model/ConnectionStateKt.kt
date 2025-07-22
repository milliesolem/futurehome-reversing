package com.signify.hue.flutterreactiveble.model

import com.polidea.rxandroidble2.RxBleConnection.RxBleConnectionState

public fun RxBleConnectionState.toConnectionState(): ConnectionState {
   val var1: java.lang.String = var0.name();
   switch (var1.hashCode()) {
      case -2087582999:
         if (var1.equals("CONNECTED")) {
            return ConnectionState.CONNECTED;
         }
         break;
      case -1052098138:
         if (var1.equals("DISCONNECTING")) {
            return ConnectionState.DISCONNECTING;
         }
         break;
      case -290559304:
         if (var1.equals("CONNECTING")) {
            return ConnectionState.CONNECTING;
         }
         break;
      case 935892539:
         if (var1.equals("DISCONNECTED")) {
            return ConnectionState.DISCONNECTED;
         }
      default:
   }

   return ConnectionState.UNKNOWN;
}
