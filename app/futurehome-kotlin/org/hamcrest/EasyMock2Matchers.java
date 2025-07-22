package org.hamcrest;

import org.hamcrest.core.IsEqual;
import org.hamcrest.integration.EasyMock2Adapter;

public class EasyMock2Matchers {
   public static String equalTo(String var0) {
      EasyMock2Adapter.adapt(IsEqual.equalTo(var0));
      return null;
   }
}
