package org.chromium.support_lib_boundary;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface PrefetchStatusCodeBoundaryInterface {
   int FAILURE = 1;
   int SUCCESS = 0;
}
