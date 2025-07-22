package org.chromium.support_lib_boundary;

import android.net.Uri;
import android.webkit.WebView;
import java.lang.reflect.InvocationHandler;

public interface WebMessageListenerBoundaryInterface extends FeatureFlagHolderBoundaryInterface {
   void onPostMessage(WebView var1, InvocationHandler var2, Uri var3, boolean var4, InvocationHandler var5);
}
