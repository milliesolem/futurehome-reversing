package org.chromium.support_lib_boundary;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface WebMessagePayloadBoundaryInterface extends FeatureFlagHolderBoundaryInterface {
   byte[] getAsArrayBuffer();

   String getAsString();

   int getType();

   @Retention(RetentionPolicy.SOURCE)
   public @interface WebMessagePayloadType {
      int TYPE_ARRAY_BUFFER = 1;
      int TYPE_STRING = 0;
   }
}
