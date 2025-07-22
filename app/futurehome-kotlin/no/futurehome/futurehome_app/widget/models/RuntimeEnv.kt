package no.futurehome.futurehome_app.widget.models

public class RuntimeEnv private constructor(clientId: String,
   clientSecret: String,
   niflheimEndpoint: String,
   heimdallCheckEndpoint: String,
   bifrostGetModeEndpoint: String,
   heimdallGetSiteTokenHash: String,
   bifrostChangeModeEndpoint: String,
   bifrostTriggerShortcutEndpoint: String
) {
   public final val clientId: String
   public final val clientSecret: String
   public final val niflheimEndpoint: String
   public final val heimdallCheckEndpoint: String
   private final val bifrostGetModeEndpoint: String
   private final val heimdallGetSiteTokenHash: String
   private final val bifrostChangeModeEndpoint: String
   private final val bifrostTriggerShortcutEndpoint: String

   init {
      this.clientId = clientId;
      this.clientSecret = clientSecret;
      this.niflheimEndpoint = niflheimEndpoint;
      this.heimdallCheckEndpoint = heimdallCheckEndpoint;
      this.bifrostGetModeEndpoint = bifrostGetModeEndpoint;
      this.heimdallGetSiteTokenHash = heimdallGetSiteTokenHash;
      this.bifrostChangeModeEndpoint = bifrostChangeModeEndpoint;
      this.bifrostTriggerShortcutEndpoint = bifrostTriggerShortcutEndpoint;
   }

   public fun getBifrostChangeModeEndpoint(siteId: String, modeString: String): String {
      return "${this.bifrostChangeModeEndpoint}/sites/$siteId/widget/modes/$modeString";
   }

   public fun getBifrostGetModeEndpoint(siteId: String): String {
      return "${this.bifrostGetModeEndpoint}/sites/$siteId/widget/mode";
   }

   public fun getBifrostTriggerShortcutEndpoint(siteId: String, shortcutId: String): String {
      return "${this.bifrostTriggerShortcutEndpoint}/sites/$siteId/widget/shortcuts/$shortcutId";
   }

   public fun getHeimdallGetSiteTokenHash(siteId: String): String {
      return "${this.heimdallGetSiteTokenHash}/auth/exchange/$siteId";
   }

   public companion object {
      public final val beta: RuntimeEnv = RuntimeEnv("LuywhcHkYkuqXauj", "P70ImeXB5yvLOgTLfKZhVXMxzZze4t0B", "https://niflheim-beta.futurehome.io", "https://heimdall-beta.futurehome.io/auth/check", "https://bifrost-beta.futurehome.io", "https://heimdall-beta.futurehome.io", "https://bifrost-beta.futurehome.io", "https://bifrost-beta.futurehome.io");
      public final val prod: RuntimeEnv = RuntimeEnv("KqpPAhpTbLbDODiz", "5UBzu037kP8Qu33T8ZHELbA22owrMePp", "https://niflheim.futurehome.io", "https://heimdall.futurehome.io/auth/check", "https://bifrost.futurehome.io", "https://heimdall.futurehome.io", "https://bifrost.futurehome.io", "https://bifrost.futurehome.io");
   }
}
