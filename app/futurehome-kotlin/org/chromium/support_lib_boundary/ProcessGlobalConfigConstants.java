package org.chromium.support_lib_boundary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class ProcessGlobalConfigConstants {
   public static final String CACHE_DIRECTORY_BASE_PATH = "CACHE_DIRECTORY_BASE_PATH";
   public static final String CONFIGURE_PARTITIONED_COOKIES = "CONFIGURE_PARTITIONED_COOKIES";
   public static final String DATA_DIRECTORY_BASE_PATH = "DATA_DIRECTORY_BASE_PATH";
   public static final String DATA_DIRECTORY_SUFFIX = "DATA_DIRECTORY_SUFFIX";

   private ProcessGlobalConfigConstants() {
   }

   @Retention(RetentionPolicy.SOURCE)
   @Target({ElementType.PARAMETER, ElementType.METHOD})
   public @interface ProcessGlobalConfigMapKey {
   }
}
