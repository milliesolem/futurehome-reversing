package org.chromium.support_lib_boundary;

import java.lang.reflect.InvocationHandler;
import java.util.List;

public interface ProfileStoreBoundaryInterface {
   boolean deleteProfile(String var1);

   List<String> getAllProfileNames();

   InvocationHandler getOrCreateProfile(String var1);

   InvocationHandler getProfile(String var1);
}
