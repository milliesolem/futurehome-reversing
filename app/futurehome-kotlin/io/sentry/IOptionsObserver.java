package io.sentry;

import io.sentry.protocol.SdkVersion;
import java.util.Map;

public interface IOptionsObserver {
   void setDist(String var1);

   void setEnvironment(String var1);

   void setProguardUuid(String var1);

   void setRelease(String var1);

   void setReplayErrorSampleRate(Double var1);

   void setSdkVersion(SdkVersion var1);

   void setTags(Map<String, String> var1);
}
