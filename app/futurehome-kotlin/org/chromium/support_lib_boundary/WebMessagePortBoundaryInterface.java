package org.chromium.support_lib_boundary;

import android.os.Handler;
import java.lang.reflect.InvocationHandler;

public interface WebMessagePortBoundaryInterface {
   void close();

   void postMessage(InvocationHandler var1);

   void setWebMessageCallback(InvocationHandler var1);

   void setWebMessageCallback(InvocationHandler var1, Handler var2);
}
