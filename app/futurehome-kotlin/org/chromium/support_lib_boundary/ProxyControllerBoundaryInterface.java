package org.chromium.support_lib_boundary;

import java.util.concurrent.Executor;

public interface ProxyControllerBoundaryInterface {
   void clearProxyOverride(Runnable var1, Executor var2);

   void setProxyOverride(String[][] var1, String[] var2, Runnable var3, Executor var4);

   void setProxyOverride(String[][] var1, String[] var2, Runnable var3, Executor var4, boolean var5);
}
