package org.chromium.support_lib_boundary;

import android.content.Context;
import android.net.Uri;
import android.webkit.ValueCallback;
import java.util.List;
import java.util.Set;

public interface StaticsBoundaryInterface {
   Uri getSafeBrowsingPrivacyPolicyUrl();

   String getVariationsHeader();

   void initSafeBrowsing(Context var1, ValueCallback<Boolean> var2);

   boolean isMultiProcessEnabled();

   void setSafeBrowsingAllowlist(Set<String> var1, ValueCallback<Boolean> var2);

   void setSafeBrowsingWhitelist(List<String> var1, ValueCallback<Boolean> var2);
}
