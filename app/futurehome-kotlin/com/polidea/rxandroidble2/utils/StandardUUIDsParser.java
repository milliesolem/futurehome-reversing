package com.polidea.rxandroidble2.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StandardUUIDsParser {
   private static final Map<String, String> CHARACTERISTIC_UUIDS;
   private static final Map<String, String> DESCRIPTOR_UUIDS;
   private static final Map<String, String> SERVICE_UUIDS;

   static {
      HashMap var0 = new HashMap();
      var0.put("1811", "Alert Notification Service");
      var0.put("180F", "Battery Service");
      var0.put("1810", "Blood Pressure");
      var0.put("181B", "Body Composition");
      var0.put("181E", "Bond Management");
      var0.put("181F", "Continuous Glucose Monitoring");
      var0.put("1805", "Current Time Service");
      var0.put("1818", "Cycling Power");
      var0.put("1816", "Cycling Speed and Cadence");
      var0.put("180A", "Device Information");
      var0.put("181A", "Environmental Sensing");
      var0.put("1800", "Generic Access");
      var0.put("1801", "Generic Attribute");
      var0.put("1808", "Glucose");
      var0.put("1809", "Health Thermometer");
      var0.put("180D", "Heart Rate");
      var0.put("1812", "Human Interface Device");
      var0.put("1802", "Immediate Alert");
      var0.put("1803", "Link Loss");
      var0.put("1819", "Location and Navigation");
      var0.put("1820", "Internet Protocol Support");
      var0.put("1807", "Next DST Change Service");
      var0.put("180E", "Phone Alert Status Service");
      var0.put("1806", "Reference Time Update Service");
      var0.put("1814", "Running Speed and Cadence");
      var0.put("1813", "Scan Parameters");
      var0.put("1804", "Tx Power");
      var0.put("181C", "User Data");
      var0.put("181D", "Weight Scale");
      var0.put("1815", "Automation IO");
      var0.put("1802", "Immediate Alert Service 1.1");
      SERVICE_UUIDS = Collections.unmodifiableMap(var0);
      var0 = new HashMap();
      var0.put("2A7E", "Aerobic Heart Rate Lower Limit");
      var0.put("2A84", "Aerobic Heart Rate Upper Limit");
      var0.put("2A7F", "Aerobic Threshold");
      var0.put("2A80", "Age");
      var0.put("2A43", "Alert Category ID");
      var0.put("2A42", "Alert Category ID Bit Mask");
      var0.put("2A06", "Alert Level");
      var0.put("2A44", "Alert Notification Control Point");
      var0.put("2A3F", "Alert Status");
      var0.put("2A81", "Anaerobic Heart Rate Lower Limit");
      var0.put("2A82", "Anaerobic Heart Rate Upper Limit");
      var0.put("2A83", "Anaerobic Threshold");
      var0.put("2A73", "Apparent Wind Direction");
      var0.put("2A72", "Apparent Wind Speed");
      var0.put("2A01", "Appearance");
      var0.put("2AA3", "Barometric Pressure Trend");
      var0.put("2A19", "Battery Level");
      var0.put("2A49", "Blood Pressure Feature");
      var0.put("2A35", "Blood Pressure Measurement");
      var0.put("2A9B", "Body Composition Feature");
      var0.put("2A9C", "Body Composition Measurement");
      var0.put("2A38", "Body Sensor Location");
      var0.put("2AA4", "Bond Management Control Point");
      var0.put("2AA5", "Bond Management Feature");
      var0.put("2A22", "Boot Keyboard Input Report");
      var0.put("2A32", "Boot Keyboard Output Report");
      var0.put("2A33", "Boot Mouse Input Report");
      var0.put("2AA6", "Central Address Resolution");
      var0.put("2AA8", "CGM Feature");
      var0.put("2AA7", "CGM Measurement");
      var0.put("2AAB", "CGM Session Run Time");
      var0.put("2AAA", "CGM Session Start Time");
      var0.put("2AAC", "CGM Specific Ops Control Point");
      var0.put("2AA9", "CGM Status");
      var0.put("2A5C", "CSC Feature");
      var0.put("2A5B", "CSC Measurement");
      var0.put("2A2B", "Current Time");
      var0.put("2A66", "Cycling Power Control Point");
      var0.put("2A65", "Cycling Power Feature");
      var0.put("2A63", "Cycling Power Measurement");
      var0.put("2A64", "Cycling Power Vector");
      var0.put("2A99", "Database Change Increment");
      var0.put("2A85", "Date of Birth");
      var0.put("2A86", "Date of Threshold Assessment ");
      var0.put("2A08", "Date Time");
      var0.put("2A0A", "Day Date Time");
      var0.put("2A09", "Day of Week");
      var0.put("2A7D", "Descriptor Value Changed");
      var0.put("2A00", "Device Name");
      var0.put("2A7B", "Dew Point");
      var0.put("2A0D", "DST Offset");
      var0.put("2A6C", "Elevation");
      var0.put("2A87", "Email Address");
      var0.put("2A0C", "Exact Time 256");
      var0.put("2A88", "Fat Burn Heart Rate Lower Limit");
      var0.put("2A89", "Fat Burn Heart Rate Upper Limit");
      var0.put("2A26", "Firmware Revision String");
      var0.put("2A8A", "First Name");
      var0.put("2A8B", "Five Zone Heart Rate Limits");
      var0.put("2A8C", "Gender");
      var0.put("2A51", "Glucose Feature");
      var0.put("2A18", "Glucose Measurement");
      var0.put("2A34", "Glucose Measurement Context");
      var0.put("2A74", "Gust Factor");
      var0.put("2A27", "Hardware Revision String");
      var0.put("2A39", "Heart Rate Control Point");
      var0.put("2A8D", "Heart Rate Max");
      var0.put("2A37", "Heart Rate Measurement");
      var0.put("2A7A", "Heat Index");
      var0.put("2A8E", "Height");
      var0.put("2A4C", "HID Control Point");
      var0.put("2A4A", "HID Information");
      var0.put("2A8F", "Hip Circumference");
      var0.put("2A6F", "Humidity");
      var0.put("2A2A", "IEEE 11073-20601 Regulatory Certification Data List");
      var0.put("2A36", "Intermediate Cuff Pressure");
      var0.put("2A1E", "Intermediate Temperature");
      var0.put("2A77", "Irradiance");
      var0.put("2AA2", "Language");
      var0.put("2A90", "Last Name");
      var0.put("2A6B", "LN Control Point");
      var0.put("2A6A", "LN Feature");
      var0.put("2A0F", "Local Time Information");
      var0.put("2A67", "Location and Speed");
      var0.put("2A2C", "Magnetic Declination");
      var0.put("2AA0", "Magnetic Flux Density - 2D");
      var0.put("2AA1", "Magnetic Flux Density - 3D");
      var0.put("2A29", "Manufacturer Name String");
      var0.put("2A91", "Maximum Recommended Heart Rate");
      var0.put("2A21", "Measurement Interval");
      var0.put("2A24", "Model Number String");
      var0.put("2A68", "Navigation");
      var0.put("2A46", "New Alert");
      var0.put("2A04", "Peripheral Preferred Connection Parameters");
      var0.put("2A02", "Peripheral Privacy Flag");
      var0.put("2A50", "PnP ID");
      var0.put("2A75", "Pollen Concentration");
      var0.put("2A69", "Position Quality");
      var0.put("2A6D", "Pressure");
      var0.put("2A4E", "Protocol Mode");
      var0.put("2A78", "Rainfall");
      var0.put("2A03", "Reconnection Address");
      var0.put("2A52", "Record Access Control Point");
      var0.put("2A14", "Reference Time Information");
      var0.put("2A4D", "Report");
      var0.put("2A4B", "Report Map");
      var0.put("2A92", "Resting Heart Rate");
      var0.put("2A40", "Ringer Control Point");
      var0.put("2A41", "Ringer Setting");
      var0.put("2A54", "RSC Feature");
      var0.put("2A53", "RSC Measurement");
      var0.put("2A55", "SC Control Point");
      var0.put("2A4F", "Scan Interval Window");
      var0.put("2A31", "Scan Refresh");
      var0.put("2A5D", "Sensor Location");
      var0.put("2A25", "Serial Number String");
      var0.put("2A05", "Service Changed");
      var0.put("2A28", "Software Revision String");
      var0.put("2A93", "Sport Type for Aerobic and Anaerobic Thresholds");
      var0.put("2A47", "Supported New Alert Category");
      var0.put("2A48", "Supported Unread Alert Category");
      var0.put("2A23", "System ID");
      var0.put("2A6E", "Temperature");
      var0.put("2A1C", "Temperature Measurement");
      var0.put("2A1D", "Temperature Type");
      var0.put("2A94", "Three Zone Heart Rate Limits");
      var0.put("2A12", "Time Accuracy");
      var0.put("2A13", "Time Source");
      var0.put("2A16", "Time Update Control Point");
      var0.put("2A17", "Time Update State");
      var0.put("2A11", "Time with DST");
      var0.put("2A0E", "Time Zone");
      var0.put("2A71", "True Wind Direction");
      var0.put("2A70", "True Wind Speed");
      var0.put("2A95", "Two Zone Heart Rate Limit");
      var0.put("2A07", "Tx Power Level");
      var0.put("2A45", "Unread Alert Status");
      var0.put("2A9F", "User Control Point");
      var0.put("2A9A", "User Index");
      var0.put("2A76", "UV Index");
      var0.put("2A96", "VO2 Max");
      var0.put("2A97", "Waist Circumference");
      var0.put("2A98", "Weight");
      var0.put("2A9D", "Weight Measurement");
      var0.put("2A9E", "Weight Scale Feature");
      var0.put("2A79", "Wind Chill");
      var0.put("2A5A", "Aggregate");
      var0.put("2A58", "Analog");
      var0.put("2A56", "Digital");
      CHARACTERISTIC_UUIDS = Collections.unmodifiableMap(var0);
      var0 = new HashMap();
      var0.put("2900", "Characteristic Extended Properties");
      var0.put("2901", "Characteristic User Description");
      var0.put("2902", "Client Characteristic Configuration");
      var0.put("2903", "Server Characteristic Configuration");
      var0.put("2904", "Characteristic Presentation Format");
      var0.put("2905", "Characteristic Aggregate Format");
      var0.put("2906", "Valid Range");
      var0.put("2907", "External Report Reference");
      var0.put("2908", "Report Reference");
      var0.put("290B", "Environmental Sensing Configuration");
      var0.put("290C", "Environmental Sensing Measurement");
      var0.put("290D", "Environmental Sensing Trigger Setting");
      var0.put("2909", "Number of Digitals");
      var0.put("290A", "Value Trigger Setting");
      var0.put("290E", "Time Trigger Setting");
      DESCRIPTOR_UUIDS = Collections.unmodifiableMap(var0);
   }

   private StandardUUIDsParser() {
   }

   public static String getCharacteristicName(UUID var0) {
      String var1 = getStandardizedUUIDComponent(var0);
      String var2;
      if (var1 != null) {
         var2 = CHARACTERISTIC_UUIDS.get(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public static String getDescriptorName(UUID var0) {
      String var1 = getStandardizedUUIDComponent(var0);
      String var2;
      if (var1 != null) {
         var2 = DESCRIPTOR_UUIDS.get(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public static String getServiceName(UUID var0) {
      String var1 = getStandardizedUUIDComponent(var0);
      String var2;
      if (var1 != null) {
         var2 = SERVICE_UUIDS.get(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   private static String getStandardizedUUIDComponent(UUID var0) {
      String var1 = var0.toString().toUpperCase();
      String var2;
      if (isStandardizedUUID(var1)) {
         var2 = var1.substring(4, 8);
      } else {
         var2 = null;
      }

      return var2;
   }

   private static boolean isStandardizedUUID(String var0) {
      boolean var1;
      if (var0.startsWith("0000") && var0.endsWith("-0000-1000-8000-00805F9B34FB")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
