package org.chromium.support_lib_boundary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.Set;

public interface WebSettingsBoundaryInterface {
   int getAttributionBehavior();

   boolean getBackForwardCacheEnabled();

   int getDisabledActionModeMenuItems();

   boolean getEnterpriseAuthenticationAppLinkPolicyEnabled();

   int getForceDark();

   int getForceDarkBehavior();

   boolean getOffscreenPreRaster();

   Set<String> getRequestedWithHeaderOriginAllowList();

   boolean getSafeBrowsingEnabled();

   int getSpeculativeLoadingStatus();

   Map<String, Object> getUserAgentMetadataMap();

   int getWebViewMediaIntegrityApiDefaultStatus();

   Map<String, Integer> getWebViewMediaIntegrityApiOverrideRules();

   int getWebauthnSupport();

   boolean getWillSuppressErrorPage();

   boolean isAlgorithmicDarkeningAllowed();

   void setAlgorithmicDarkeningAllowed(boolean var1);

   void setAttributionBehavior(int var1);

   void setBackForwardCacheEnabled(boolean var1);

   void setDisabledActionModeMenuItems(int var1);

   void setEnterpriseAuthenticationAppLinkPolicyEnabled(boolean var1);

   void setForceDark(int var1);

   void setForceDarkBehavior(int var1);

   void setOffscreenPreRaster(boolean var1);

   void setRequestedWithHeaderOriginAllowList(Set<String> var1);

   void setSafeBrowsingEnabled(boolean var1);

   void setSpeculativeLoadingStatus(int var1);

   void setUserAgentMetadataFromMap(Map<String, Object> var1);

   void setWebViewMediaIntegrityApiStatus(int var1, Map<String, Integer> var2);

   void setWebauthnSupport(int var1);

   void setWillSuppressErrorPage(boolean var1);

   @Retention(RetentionPolicy.SOURCE)
   public @interface AttributionBehavior {
      int APP_SOURCE_AND_APP_TRIGGER = 3;
      int APP_SOURCE_AND_WEB_TRIGGER = 1;
      int DISABLED = 0;
      int WEB_SOURCE_AND_WEB_TRIGGER = 2;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface ForceDarkBehavior {
      int FORCE_DARK_ONLY = 0;
      int MEDIA_QUERY_ONLY = 1;
      int PREFER_MEDIA_QUERY_OVER_FORCE_DARK = 2;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface SpeculativeLoadingStatus {
      int DISABLED = 0;
      int PRERENDER_ENABLED = 1;
   }

   @Retention(RetentionPolicy.SOURCE)
   @Target({ElementType.TYPE_USE})
   public @interface WebViewMediaIntegrityApiStatus {
      int DISABLED = 0;
      int ENABLED = 2;
      int ENABLED_WITHOUT_APP_IDENTITY = 1;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface WebauthnSupport {
      int APP = 1;
      int BROWSER = 2;
      int NONE = 0;
   }
}
