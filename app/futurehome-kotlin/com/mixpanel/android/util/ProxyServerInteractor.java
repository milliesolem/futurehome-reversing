package com.mixpanel.android.util;

import java.util.Map;

public interface ProxyServerInteractor {
   Map<String, String> getProxyRequestHeaders();

   void onProxyResponse(String var1, int var2);
}
