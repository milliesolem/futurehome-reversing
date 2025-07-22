package org.chromium.support_lib_boundary;

import java.io.OutputStream;
import java.util.Collection;
import java.util.concurrent.Executor;

public interface TracingControllerBoundaryInterface {
   boolean isTracing();

   void start(int var1, Collection<String> var2, int var3) throws IllegalStateException, IllegalArgumentException;

   boolean stop(OutputStream var1, Executor var2);
}
