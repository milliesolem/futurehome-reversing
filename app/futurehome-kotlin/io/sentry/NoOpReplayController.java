package io.sentry;

import io.sentry.protocol.SentryId;

public final class NoOpReplayController implements ReplayController {
   private static final NoOpReplayController instance = new NoOpReplayController();

   private NoOpReplayController() {
   }

   public static NoOpReplayController getInstance() {
      return instance;
   }

   @Override
   public void captureReplay(Boolean var1) {
   }

   @Override
   public ReplayBreadcrumbConverter getBreadcrumbConverter() {
      return NoOpReplayBreadcrumbConverter.getInstance();
   }

   @Override
   public SentryId getReplayId() {
      return SentryId.EMPTY_ID;
   }

   @Override
   public boolean isRecording() {
      return false;
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() {
   }

   @Override
   public void setBreadcrumbConverter(ReplayBreadcrumbConverter var1) {
   }

   @Override
   public void start() {
   }

   @Override
   public void stop() {
   }
}
