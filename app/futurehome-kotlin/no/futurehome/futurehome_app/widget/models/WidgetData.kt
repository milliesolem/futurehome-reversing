package no.futurehome.futurehome_app.widget.models

import com.signify.hue.flutterreactiveble.utils.Duration$$ExternalSyntheticBackport0

public data class WidgetData(isConnected: Boolean = false,
   language: Language = Language.EN,
   isDark: Boolean = false,
   showSites: Boolean = false,
   sites: List<SiteM>? = null,
   activeSite: SiteM? = null,
   siteTokenHash: String? = null,
   activeMode: Mode? = null
) {
   public final var isConnected: Boolean
      internal set

   public final var language: Language
      internal set

   public final var isDark: Boolean
      internal set

   public final var showSites: Boolean
      internal set

   public final var sites: List<SiteM>?
      internal set

   public final var activeSite: SiteM?
      internal set

   public final var siteTokenHash: String?
      internal set

   public final var activeMode: Mode?
      internal set

   fun WidgetData() {
      this(false, null, false, false, null, null, null, null, 255, null);
   }

   init {
      this.isConnected = var1;
      this.language = var2;
      this.isDark = var3;
      this.showSites = var4;
      this.sites = var5;
      this.activeSite = var6;
      this.siteTokenHash = var7;
      this.activeMode = var8;
   }

   public operator fun component1(): Boolean {
      return this.isConnected;
   }

   public operator fun component2(): Language {
      return this.language;
   }

   public operator fun component3(): Boolean {
      return this.isDark;
   }

   public operator fun component4(): Boolean {
      return this.showSites;
   }

   public operator fun component5(): List<SiteM>? {
      return this.sites;
   }

   public operator fun component6(): SiteM? {
      return this.activeSite;
   }

   public operator fun component7(): String? {
      return this.siteTokenHash;
   }

   public operator fun component8(): Mode? {
      return this.activeMode;
   }

   public fun copy(
      isConnected: Boolean = var0.isConnected,
      language: Language = var0.language,
      isDark: Boolean = var0.isDark,
      showSites: Boolean = var0.showSites,
      sites: List<SiteM>? = var0.sites,
      activeSite: SiteM? = var0.activeSite,
      siteTokenHash: String? = var0.siteTokenHash,
      activeMode: Mode? = var0.activeMode
   ): WidgetData {
      return new WidgetData(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is WidgetData) {
         return false;
      } else {
         var1 = var1;
         if (this.isConnected != var1.isConnected) {
            return false;
         } else if (this.language != var1.language) {
            return false;
         } else if (this.isDark != var1.isDark) {
            return false;
         } else if (this.showSites != var1.showSites) {
            return false;
         } else if (!(this.sites == var1.sites)) {
            return false;
         } else if (!(this.activeSite == var1.activeSite)) {
            return false;
         } else if (!(this.siteTokenHash == var1.siteTokenHash)) {
            return false;
         } else {
            return this.activeMode === var1.activeMode;
         }
      }
   }

   public override fun hashCode(): Int {
      val var7: Int = Duration$$ExternalSyntheticBackport0.m(this.isConnected);
      val var5: Int = this.language.hashCode();
      val var8: Int = Duration$$ExternalSyntheticBackport0.m(this.isDark);
      val var6: Int = Duration$$ExternalSyntheticBackport0.m(this.showSites);
      var var4: Int = 0;
      val var1: Int;
      if (this.sites == null) {
         var1 = 0;
      } else {
         var1 = this.sites.hashCode();
      }

      val var2: Int;
      if (this.activeSite == null) {
         var2 = 0;
      } else {
         var2 = this.activeSite.hashCode();
      }

      val var3: Int;
      if (this.siteTokenHash == null) {
         var3 = 0;
      } else {
         var3 = this.siteTokenHash.hashCode();
      }

      if (this.activeMode != null) {
         var4 = this.activeMode.hashCode();
      }

      return ((((((var7 * 31 + var5) * 31 + var8) * 31 + var6) * 31 + var1) * 31 + var2) * 31 + var3) * 31 + var4;
   }

   public override fun toString(): String {
      val var3: Boolean = this.isConnected;
      val var6: Language = this.language;
      val var2: Boolean = this.isDark;
      val var1: Boolean = this.showSites;
      val var7: java.util.List = this.sites;
      val var9: SiteM = this.activeSite;
      val var8: java.lang.String = this.siteTokenHash;
      val var5: Mode = this.activeMode;
      val var4: StringBuilder = new StringBuilder("WidgetData(isConnected=");
      var4.append(var3);
      var4.append(", language=");
      var4.append(var6);
      var4.append(", isDark=");
      var4.append(var2);
      var4.append(", showSites=");
      var4.append(var1);
      var4.append(", sites=");
      var4.append(var7);
      var4.append(", activeSite=");
      var4.append(var9);
      var4.append(", siteTokenHash=");
      var4.append(var8);
      var4.append(", activeMode=");
      var4.append(var5);
      var4.append(")");
      return var4.toString();
   }
}
