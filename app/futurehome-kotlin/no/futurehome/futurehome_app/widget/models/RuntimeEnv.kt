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
      this.clientId = var1;
      this.clientSecret = var2;
      this.niflheimEndpoint = var3;
      this.heimdallCheckEndpoint = var4;
      this.bifrostGetModeEndpoint = var5;
      this.heimdallGetSiteTokenHash = var6;
      this.bifrostChangeModeEndpoint = var7;
      this.bifrostTriggerShortcutEndpoint = var8;
   }

   public fun getBifrostChangeModeEndpoint(siteId: String, modeString: String): String {
      val var4: java.lang.String = this.bifrostChangeModeEndpoint;
      val var3: StringBuilder = new StringBuilder();
      var3.append(var4);
      var3.append("/sites/");
      var3.append(var1);
      var3.append("/widget/modes/");
      var3.append(var2);
      return var3.toString();
   }

   public fun getBifrostGetModeEndpoint(siteId: String): String {
      val var3: java.lang.String = this.bifrostGetModeEndpoint;
      val var2: StringBuilder = new StringBuilder();
      var2.append(var3);
      var2.append("/sites/");
      var2.append(var1);
      var2.append("/widget/mode");
      return var2.toString();
   }

   public fun getBifrostTriggerShortcutEndpoint(siteId: String, shortcutId: String): String {
      val var3: java.lang.String = this.bifrostTriggerShortcutEndpoint;
      val var4: StringBuilder = new StringBuilder();
      var4.append(var3);
      var4.append("/sites/");
      var4.append(var1);
      var4.append("/widget/shortcuts/");
      var4.append(var2);
      return var4.toString();
   }

   public fun getHeimdallGetSiteTokenHash(siteId: String): String {
      val var3: java.lang.String = this.heimdallGetSiteTokenHash;
      val var2: StringBuilder = new StringBuilder();
      var2.append(var3);
      var2.append("/auth/exchange/");
      var2.append(var1);
      return var2.toString();
   }

   public companion object {
      public final val beta: RuntimeEnv
      public final val prod: RuntimeEnv
   }
}
