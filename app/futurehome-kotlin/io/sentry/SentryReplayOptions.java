package io.sentry;

import io.sentry.protocol.SdkVersion;
import io.sentry.util.SampleRateUtils;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class SentryReplayOptions {
   public static final String ANDROIDX_MEDIA_VIEW_CLASS_NAME = "androidx.media3.ui.PlayerView";
   public static final String EXOPLAYER_CLASS_NAME = "com.google.android.exoplayer2.ui.PlayerView";
   public static final String EXOPLAYER_STYLED_CLASS_NAME = "com.google.android.exoplayer2.ui.StyledPlayerView";
   public static final String IMAGE_VIEW_CLASS_NAME = "android.widget.ImageView";
   public static final String TEXT_VIEW_CLASS_NAME = "android.widget.TextView";
   public static final String VIDEO_VIEW_CLASS_NAME = "android.widget.VideoView";
   public static final String WEB_VIEW_CLASS_NAME = "android.webkit.WebView";
   private long errorReplayDuration;
   private int frameRate;
   private Set<String> maskViewClasses = new CopyOnWriteArraySet<>();
   private String maskViewContainerClass;
   private Double onErrorSampleRate;
   private SentryReplayOptions.SentryReplayQuality quality;
   private SdkVersion sdkVersion;
   private long sessionDuration;
   private Double sessionSampleRate;
   private long sessionSegmentDuration;
   private boolean trackOrientationChange;
   private Set<String> unmaskViewClasses = new CopyOnWriteArraySet<>();
   private String unmaskViewContainerClass;

   public SentryReplayOptions(Double var1, Double var2, SdkVersion var3) {
      this(false, var3);
      this.sessionSampleRate = var1;
      this.onErrorSampleRate = var2;
      this.sdkVersion = var3;
   }

   public SentryReplayOptions(boolean var1, SdkVersion var2) {
      this.maskViewContainerClass = null;
      this.unmaskViewContainerClass = null;
      this.quality = SentryReplayOptions.SentryReplayQuality.MEDIUM;
      this.frameRate = 1;
      this.errorReplayDuration = 30000L;
      this.sessionSegmentDuration = 5000L;
      this.sessionDuration = 3600000L;
      this.trackOrientationChange = true;
      if (!var1) {
         this.setMaskAllText(true);
         this.setMaskAllImages(true);
         this.maskViewClasses.add("android.webkit.WebView");
         this.maskViewClasses.add("android.widget.VideoView");
         this.maskViewClasses.add("androidx.media3.ui.PlayerView");
         this.maskViewClasses.add("com.google.android.exoplayer2.ui.PlayerView");
         this.maskViewClasses.add("com.google.android.exoplayer2.ui.StyledPlayerView");
         this.sdkVersion = var2;
      }
   }

   public void addMaskViewClass(String var1) {
      this.maskViewClasses.add(var1);
   }

   public void addUnmaskViewClass(String var1) {
      this.unmaskViewClasses.add(var1);
   }

   public long getErrorReplayDuration() {
      return this.errorReplayDuration;
   }

   public int getFrameRate() {
      return this.frameRate;
   }

   public Set<String> getMaskViewClasses() {
      return this.maskViewClasses;
   }

   public String getMaskViewContainerClass() {
      return this.maskViewContainerClass;
   }

   public Double getOnErrorSampleRate() {
      return this.onErrorSampleRate;
   }

   public SentryReplayOptions.SentryReplayQuality getQuality() {
      return this.quality;
   }

   public SdkVersion getSdkVersion() {
      return this.sdkVersion;
   }

   public long getSessionDuration() {
      return this.sessionDuration;
   }

   public Double getSessionSampleRate() {
      return this.sessionSampleRate;
   }

   public long getSessionSegmentDuration() {
      return this.sessionSegmentDuration;
   }

   public Set<String> getUnmaskViewClasses() {
      return this.unmaskViewClasses;
   }

   public String getUnmaskViewContainerClass() {
      return this.unmaskViewContainerClass;
   }

   public boolean isSessionReplayEnabled() {
      boolean var1;
      if (this.getSessionSampleRate() != null && this.getSessionSampleRate() > 0.0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isSessionReplayForErrorsEnabled() {
      boolean var1;
      if (this.getOnErrorSampleRate() != null && this.getOnErrorSampleRate() > 0.0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isTrackOrientationChange() {
      return this.trackOrientationChange;
   }

   public void setMaskAllImages(boolean var1) {
      if (var1) {
         this.addMaskViewClass("android.widget.ImageView");
         this.unmaskViewClasses.remove("android.widget.ImageView");
      } else {
         this.addUnmaskViewClass("android.widget.ImageView");
         this.maskViewClasses.remove("android.widget.ImageView");
      }
   }

   public void setMaskAllText(boolean var1) {
      if (var1) {
         this.addMaskViewClass("android.widget.TextView");
         this.unmaskViewClasses.remove("android.widget.TextView");
      } else {
         this.addUnmaskViewClass("android.widget.TextView");
         this.maskViewClasses.remove("android.widget.TextView");
      }
   }

   public void setMaskViewContainerClass(String var1) {
      this.addMaskViewClass(var1);
      this.maskViewContainerClass = var1;
   }

   public void setOnErrorSampleRate(Double var1) {
      if (SampleRateUtils.isValidSampleRate(var1)) {
         this.onErrorSampleRate = var1;
      } else {
         StringBuilder var2 = new StringBuilder("The value ");
         var2.append(var1);
         var2.append(" is not valid. Use null to disable or values >= 0.0 and <= 1.0.");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void setQuality(SentryReplayOptions.SentryReplayQuality var1) {
      this.quality = var1;
   }

   public void setSdkVersion(SdkVersion var1) {
      this.sdkVersion = var1;
   }

   public void setSessionSampleRate(Double var1) {
      if (SampleRateUtils.isValidSampleRate(var1)) {
         this.sessionSampleRate = var1;
      } else {
         StringBuilder var2 = new StringBuilder("The value ");
         var2.append(var1);
         var2.append(" is not valid. Use null to disable or values >= 0.0 and <= 1.0.");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void setTrackOrientationChange(boolean var1) {
      this.trackOrientationChange = var1;
   }

   public void setUnmaskViewContainerClass(String var1) {
      this.unmaskViewContainerClass = var1;
   }

   public static enum SentryReplayQuality {
      HIGH(1.0F, 100000, 50),
      LOW(0.8F, 50000, 10),
      MEDIUM(1.0F, 75000, 30);

      private static final SentryReplayOptions.SentryReplayQuality[] $VALUES = $values();
      public final int bitRate;
      public final int screenshotQuality;
      public final float sizeScale;

      private SentryReplayQuality(float var3, int var4, int var5) {
         this.sizeScale = var3;
         this.bitRate = var4;
         this.screenshotQuality = var5;
      }

      public String serializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
