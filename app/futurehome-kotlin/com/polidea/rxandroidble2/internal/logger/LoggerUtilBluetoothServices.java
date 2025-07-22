package com.polidea.rxandroidble2.internal.logger;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.util.CharacteristicPropertiesParser;
import com.polidea.rxandroidble2.utils.StandardUUIDsParser;
import java.util.Iterator;

public class LoggerUtilBluetoothServices {
   private final CharacteristicPropertiesParser characteristicPropertiesParser;

   @Inject
   LoggerUtilBluetoothServices(CharacteristicPropertiesParser var1) {
      this.characteristicPropertiesParser = var1;
   }

   private static void appendCharacteristicNameHeader(StringBuilder var0, BluetoothGattCharacteristic var1) {
      var0.append('\n');
      var0.append('\t');
      var0.append("* ");
      var0.append(createCharacteristicName(var1));
      var0.append(" (");
      var0.append(LoggerUtil.getUuidToLog(var1.getUuid()));
      var0.append(")");
   }

   private void appendCharacteristicProperties(StringBuilder var1, BluetoothGattCharacteristic var2) {
      var1.append('\n');
      var1.append('\t');
      var1.append("  ");
      var1.append("Properties: ");
      var1.append(this.characteristicPropertiesParser.propertiesIntToString(var2.getProperties()));
   }

   private static void appendDescriptorNameHeader(StringBuilder var0, BluetoothGattDescriptor var1) {
      var0.append('\n');
      var0.append('\t');
      var0.append('\t');
      var0.append("* ");
      var0.append(createDescriptorName(var1));
      var0.append(" (");
      var0.append(LoggerUtil.getUuidToLog(var1.getUuid()));
      var0.append(")");
   }

   private static void appendDescriptors(StringBuilder var0, BluetoothGattCharacteristic var1) {
      if (!var1.getDescriptors().isEmpty()) {
         appendDescriptorsHeader(var0);
         Iterator var2 = var1.getDescriptors().iterator();

         while (var2.hasNext()) {
            appendDescriptorNameHeader(var0, (BluetoothGattDescriptor)var2.next());
         }
      }
   }

   private static void appendDescriptorsHeader(StringBuilder var0) {
      var0.append('\n');
      var0.append('\t');
      var0.append("  ");
      var0.append("-> Descriptors: ");
   }

   private static void appendDeviceHeader(BluetoothDevice var0, StringBuilder var1) {
      var1.append("--------------- ====== Printing peripheral content ====== ---------------\n");
      var1.append(LoggerUtil.commonMacMessage(var0.getAddress()));
      var1.append('\n');
      var1.append("PERIPHERAL NAME: ");
      var1.append(var0.getName());
      var1.append('\n');
      var1.append("-------------------------------------------------------------------------");
   }

   private void appendServiceDescription(StringBuilder var1, BluetoothGattService var2) {
      appendServiceHeader(var1, var2);
      var1.append("-> Characteristics:");

      for (BluetoothGattCharacteristic var3 : var2.getCharacteristics()) {
         appendCharacteristicNameHeader(var1, var3);
         this.appendCharacteristicProperties(var1, var3);
         appendDescriptors(var1, var3);
      }
   }

   private static void appendServiceHeader(StringBuilder var0, BluetoothGattService var1) {
      var0.append("\n");
      var0.append(createServiceType(var1));
      var0.append(" - ");
      var0.append(createServiceName(var1));
      var0.append(" (");
      var0.append(LoggerUtil.getUuidToLog(var1.getUuid()));
      var0.append(")\n");
      var0.append("Instance ID: ");
      var0.append(var1.getInstanceId());
      var0.append('\n');
   }

   private static String createCharacteristicName(BluetoothGattCharacteristic var0) {
      String var1 = StandardUUIDsParser.getCharacteristicName(var0.getUuid());
      String var2 = var1;
      if (var1 == null) {
         var2 = "Unknown characteristic";
      }

      return var2;
   }

   private static String createDescriptorName(BluetoothGattDescriptor var0) {
      String var1 = StandardUUIDsParser.getDescriptorName(var0.getUuid());
      String var2 = var1;
      if (var1 == null) {
         var2 = "Unknown descriptor";
      }

      return var2;
   }

   private static String createServiceName(BluetoothGattService var0) {
      String var1 = StandardUUIDsParser.getServiceName(var0.getUuid());
      String var2 = var1;
      if (var1 == null) {
         var2 = "Unknown service";
      }

      return var2;
   }

   private static String createServiceType(BluetoothGattService var0) {
      return var0.getType() == 0 ? "Primary Service" : "Secondary Service";
   }

   private String prepareServicesDescription(RxBleDeviceServices var1, BluetoothDevice var2) {
      StringBuilder var3 = new StringBuilder();
      appendDeviceHeader(var2, var3);

      for (BluetoothGattService var4 : var1.getBluetoothGattServices()) {
         var3.append('\n');
         this.appendServiceDescription(var3, var4);
      }

      var3.append("\n--------------- ====== Finished peripheral content ====== ---------------");
      return var3.toString();
   }

   public void log(RxBleDeviceServices var1, BluetoothDevice var2) {
      if (RxBleLog.isAtLeast(2)) {
         RxBleLog.v("Preparing services description");
         RxBleLog.v(this.prepareServicesDescription(var1, var2));
      }
   }
}
