package io.sentry;

import io.sentry.protocol.SentryId;

public interface ReplayController {
   void captureReplay(Boolean var1);

   ReplayBreadcrumbConverter getBreadcrumbConverter();

   SentryId getReplayId();

   boolean isRecording();

   void pause();

   void resume();

   void setBreadcrumbConverter(ReplayBreadcrumbConverter var1);

   void start();

   void stop();
}
