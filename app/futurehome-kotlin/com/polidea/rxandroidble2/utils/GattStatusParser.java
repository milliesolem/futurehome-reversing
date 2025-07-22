package com.polidea.rxandroidble2.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GattStatusParser {
   private static final Map<Integer, String> GATT_STATUS;

   static {
      HashMap var0 = new HashMap();
      var0.put(0, "GATT_SUCCESS");
      var0.put(1, "GATT_INVALID_HANDLE");
      var0.put(2, "GATT_READ_NOT_PERMIT");
      var0.put(3, "GATT_WRITE_NOT_PERMIT");
      var0.put(4, "GATT_INVALID_PDU");
      var0.put(5, "GATT_INSUF_AUTHENTICATION");
      var0.put(6, "GATT_REQ_NOT_SUPPORTED");
      var0.put(7, "GATT_INVALID_OFFSET");
      var0.put(8, "GATT_INSUF_AUTHORIZATION or GATT_CONN_TIMEOUT");
      var0.put(9, "GATT_PREPARE_Q_FULL");
      var0.put(10, "GATT_NOT_FOUND");
      var0.put(11, "GATT_NOT_LONG");
      var0.put(12, "GATT_INSUF_KEY_SIZE");
      var0.put(13, "GATT_INVALID_ATTR_LEN");
      var0.put(14, "GATT_ERR_UNLIKELY");
      var0.put(15, "GATT_INSUF_ENCRYPTION");
      var0.put(16, "GATT_UNSUPPORT_GRP_TYPE");
      var0.put(17, "GATT_INSUF_RESOURCE");
      var0.put(19, "GATT_CONN_TERMINATE_PEER_USER");
      var0.put(22, "GATT_CONN_TERMINATE_LOCAL_HOST");
      var0.put(34, "GATT_CONN_LMP_TIMEOUT");
      var0.put(62, "GATT_CONN_FAIL_ESTABLISH");
      var0.put(135, "GATT_ILLEGAL_PARAMETER");
      var0.put(128, "GATT_NO_RESOURCES");
      var0.put(129, "GATT_INTERNAL_ERROR");
      var0.put(130, "GATT_WRONG_STATE");
      var0.put(131, "GATT_DB_FULL");
      var0.put(132, "GATT_BUSY");
      var0.put(133, "GATT_ERROR");
      var0.put(134, "GATT_CMD_STARTED");
      var0.put(136, "GATT_PENDING");
      var0.put(137, "GATT_AUTH_FAIL");
      var0.put(138, "GATT_MORE");
      var0.put(139, "GATT_INVALID_CFG");
      var0.put(140, "GATT_SERVICE_STARTED");
      var0.put(141, "GATT_ENCRYPED_NO_MITM");
      var0.put(142, "GATT_NOT_ENCRYPTED");
      var0.put(143, "GATT_CONGESTED");
      var0.put(253, "GATT_CCC_CFG_ERR");
      var0.put(254, "GATT_PRC_IN_PROGRESS");
      var0.put(255, "GATT_OUT_OF_RANGE");
      var0.put(256, "GATT_CONN_CANCEL");
      GATT_STATUS = Collections.unmodifiableMap(var0);
   }

   private GattStatusParser() {
   }

   public static String getGattCallbackStatusDescription(int var0) {
      String var2 = GATT_STATUS.get(var0);
      String var1 = var2;
      if (var2 == null) {
         var1 = "UNKNOWN";
      }

      return var1;
   }
}
