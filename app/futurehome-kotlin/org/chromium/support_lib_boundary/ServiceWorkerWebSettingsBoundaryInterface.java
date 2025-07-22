package org.chromium.support_lib_boundary;

import java.util.Set;

public interface ServiceWorkerWebSettingsBoundaryInterface {
   boolean getAllowContentAccess();

   boolean getAllowFileAccess();

   boolean getBlockNetworkLoads();

   int getCacheMode();

   Set<String> getRequestedWithHeaderOriginAllowList();

   void setAllowContentAccess(boolean var1);

   void setAllowFileAccess(boolean var1);

   void setBlockNetworkLoads(boolean var1);

   void setCacheMode(int var1);

   void setRequestedWithHeaderOriginAllowList(Set<String> var1);
}
