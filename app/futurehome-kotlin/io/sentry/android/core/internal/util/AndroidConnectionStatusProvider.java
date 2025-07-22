package io.sentry.android.core.internal.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.ConnectivityManager.NetworkCallback;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.sentry.IConnectionStatusProvider;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.android.core.BuildInfoProvider;
import io.sentry.android.core.ContextUtils;
import java.util.HashMap;
import java.util.Map;

public final class AndroidConnectionStatusProvider implements IConnectionStatusProvider {
   private final BuildInfoProvider buildInfoProvider;
   private final Context context;
   private final ILogger logger;
   private final Map<IConnectionStatusProvider.IConnectionStatusObserver, NetworkCallback> registeredCallbacks;

   public AndroidConnectionStatusProvider(Context var1, ILogger var2, BuildInfoProvider var3) {
      this.context = ContextUtils.getApplicationContext(var1);
      this.logger = var2;
      this.buildInfoProvider = var3;
      this.registeredCallbacks = new HashMap<>();
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static IConnectionStatusProvider.ConnectionStatus getConnectionStatus(Context var0, ConnectivityManager var1, ILogger var2) {
      if (!Permissions.hasPermission(var0, "android.permission.ACCESS_NETWORK_STATE")) {
         var2.log(SentryLevel.INFO, "No permission (ACCESS_NETWORK_STATE) to check network status.");
         return IConnectionStatusProvider.ConnectionStatus.NO_PERMISSION;
      } else {
         try {
            var33 = var1.getActiveNetworkInfo();
         } catch (Throwable var31) {
            var2.log(SentryLevel.WARNING, "Could not retrieve Connection Status", var31);
            return IConnectionStatusProvider.ConnectionStatus.UNKNOWN;
         }

         if (var33 == null) {
            try {
               var2.log(SentryLevel.INFO, "NetworkInfo is null, there's no active network.");
               return IConnectionStatusProvider.ConnectionStatus.DISCONNECTED;
            } catch (Throwable var28) {
               var2.log(SentryLevel.WARNING, "Could not retrieve Connection Status", var28);
               return IConnectionStatusProvider.ConnectionStatus.UNKNOWN;
            }
         } else {
            label135: {
               try {
                  if (var33.isConnected()) {
                     var34 = IConnectionStatusProvider.ConnectionStatus.CONNECTED;
                     break label135;
                  }
               } catch (Throwable var32) {
                  var2.log(SentryLevel.WARNING, "Could not retrieve Connection Status", var32);
                  return IConnectionStatusProvider.ConnectionStatus.UNKNOWN;
               }

               try {
                  var34 = IConnectionStatusProvider.ConnectionStatus.DISCONNECTED;
               } catch (Throwable var30) {
                  var2.log(SentryLevel.WARNING, "Could not retrieve Connection Status", var30);
                  return IConnectionStatusProvider.ConnectionStatus.UNKNOWN;
               }
            }

            try {
               return var34;
            } catch (Throwable var29) {
               var2.log(SentryLevel.WARNING, "Could not retrieve Connection Status", var29);
               return IConnectionStatusProvider.ConnectionStatus.UNKNOWN;
            }
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static String getConnectionType(Context var0, ILogger var1, BuildInfoProvider var2) {
      ConnectivityManager var8 = getConnectivityManager(var0, var1);
      if (var8 == null) {
         return null;
      } else {
         boolean var6 = Permissions.hasPermission(var0, "android.permission.ACCESS_NETWORK_STATE");
         boolean var4 = false;
         boolean var5 = false;
         if (!var6) {
            var1.log(SentryLevel.INFO, "No permission (ACCESS_NETWORK_STATE) to check network status.");
            return null;
         } else {
            int var3;
            try {
               var3 = var2.getSdkInfoVersion();
            } catch (Throwable var98) {
               var1.log(SentryLevel.ERROR, "Failed to retrieve network info", var98);
               return null;
            }

            var6 = true;
            if (var3 >= 23) {
               try {
                  var99 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var8);
               } catch (Throwable var97) {
                  var1.log(SentryLevel.ERROR, "Failed to retrieve network info", var97);
                  return null;
               }

               if (var99 == null) {
                  try {
                     var1.log(SentryLevel.INFO, "Network is null and cannot check network status");
                     return null;
                  } catch (Throwable var92) {
                     var1.log(SentryLevel.ERROR, "Failed to retrieve network info", var92);
                     return null;
                  }
               }

               try {
                  var100 = var8.getNetworkCapabilities(var99);
               } catch (Throwable var96) {
                  var1.log(SentryLevel.ERROR, "Failed to retrieve network info", var96);
                  return null;
               }

               if (var100 == null) {
                  try {
                     var1.log(SentryLevel.INFO, "NetworkCapabilities is null and cannot check network type");
                     return null;
                  } catch (Throwable var91) {
                     var1.log(SentryLevel.ERROR, "Failed to retrieve network info", var91);
                     return null;
                  }
               }

               try {
                  var5 = var100.hasTransport(3);
                  var4 = var100.hasTransport(1);
                  var6 = var100.hasTransport(0);
               } catch (Throwable var95) {
                  var1.log(SentryLevel.ERROR, "Failed to retrieve network info", var95);
                  return null;
               }
            } else {
               try {
                  var101 = var8.getActiveNetworkInfo();
               } catch (Throwable var94) {
                  var1.log(SentryLevel.ERROR, "Failed to retrieve network info", var94);
                  return null;
               }

               if (var101 == null) {
                  try {
                     var1.log(SentryLevel.INFO, "NetworkInfo is null, there's no active network.");
                     return null;
                  } catch (Throwable var90) {
                     var1.log(SentryLevel.ERROR, "Failed to retrieve network info", var90);
                     return null;
                  }
               }

               try {
                  var3 = var101.getType();
               } catch (Throwable var93) {
                  var1.log(SentryLevel.ERROR, "Failed to retrieve network info", var93);
                  return null;
               }

               if (var3 != 0) {
                  if (var3 != 1) {
                     if (var3 != 9) {
                        var4 = false;
                     } else {
                        var4 = false;
                        var5 = true;
                     }
                  } else {
                     var4 = true;
                  }

                  var6 = false;
               } else {
                  boolean var7 = false;
                  var5 = var4;
                  var4 = var7;
               }
            }

            if (var5) {
               return "ethernet";
            } else if (var4) {
               return "wifi";
            } else {
               return var6 ? "cellular" : null;
            }
         }
      }
   }

   public static String getConnectionType(NetworkCapabilities var0, BuildInfoProvider var1) {
      if (var1.getSdkInfoVersion() < 21) {
         return null;
      } else if (var0.hasTransport(3)) {
         return "ethernet";
      } else if (var0.hasTransport(1)) {
         return "wifi";
      } else {
         return var0.hasTransport(0) ? "cellular" : null;
      }
   }

   private static ConnectivityManager getConnectivityManager(Context var0, ILogger var1) {
      ConnectivityManager var2 = (ConnectivityManager)var0.getSystemService("connectivity");
      if (var2 == null) {
         var1.log(SentryLevel.INFO, "ConnectivityManager is null and cannot check network status");
      }

      return var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static boolean registerNetworkCallback(Context var0, ILogger var1, BuildInfoProvider var2, NetworkCallback var3) {
      if (var2.getSdkInfoVersion() < 24) {
         var1.log(SentryLevel.DEBUG, "NetworkCallbacks need Android N+.");
         return false;
      } else {
         ConnectivityManager var6 = getConnectivityManager(var0, var1);
         if (var6 == null) {
            return false;
         } else if (!Permissions.hasPermission(var0, "android.permission.ACCESS_NETWORK_STATE")) {
            var1.log(SentryLevel.INFO, "No permission (ACCESS_NETWORK_STATE) to check network status.");
            return false;
         } else {
            try {
               AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var6, var3);
               return true;
            } catch (Throwable var5) {
               var1.log(SentryLevel.WARNING, "registerDefaultNetworkCallback failed", var5);
               return false;
            }
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void unregisterNetworkCallback(Context var0, ILogger var1, BuildInfoProvider var2, NetworkCallback var3) {
      if (var2.getSdkInfoVersion() >= 21) {
         ConnectivityManager var6 = getConnectivityManager(var0, var1);
         if (var6 != null) {
            try {
               var6.unregisterNetworkCallback(var3);
            } catch (Throwable var5) {
               var1.log(SentryLevel.WARNING, "unregisterNetworkCallback failed", var5);
               return;
            }
         }
      }
   }

   @Override
   public boolean addConnectionStatusObserver(IConnectionStatusProvider.IConnectionStatusObserver var1) {
      if (this.buildInfoProvider.getSdkInfoVersion() < 21) {
         this.logger.log(SentryLevel.DEBUG, "addConnectionStatusObserver requires Android 5+.");
         return false;
      } else {
         NetworkCallback var2 = new NetworkCallback(this, var1) {
            final AndroidConnectionStatusProvider this$0;
            final IConnectionStatusProvider.IConnectionStatusObserver val$observer;

            {
               this.this$0 = var1;
               this.val$observer = var2x;
            }

            public void onAvailable(Network var1) {
               this.val$observer.onConnectionStatusChanged(this.this$0.getConnectionStatus());
            }

            public void onLosing(Network var1, int var2x) {
               this.val$observer.onConnectionStatusChanged(this.this$0.getConnectionStatus());
            }

            public void onLost(Network var1) {
               this.val$observer.onConnectionStatusChanged(this.this$0.getConnectionStatus());
            }

            public void onUnavailable() {
               this.val$observer.onConnectionStatusChanged(this.this$0.getConnectionStatus());
            }
         };
         this.registeredCallbacks.put(var1, var2);
         return registerNetworkCallback(this.context, this.logger, this.buildInfoProvider, var2);
      }
   }

   @Override
   public IConnectionStatusProvider.ConnectionStatus getConnectionStatus() {
      ConnectivityManager var1 = getConnectivityManager(this.context, this.logger);
      return var1 == null ? IConnectionStatusProvider.ConnectionStatus.UNKNOWN : getConnectionStatus(this.context, var1, this.logger);
   }

   @Override
   public String getConnectionType() {
      return getConnectionType(this.context, this.logger, this.buildInfoProvider);
   }

   public Map<IConnectionStatusProvider.IConnectionStatusObserver, NetworkCallback> getRegisteredCallbacks() {
      return this.registeredCallbacks;
   }

   @Override
   public void removeConnectionStatusObserver(IConnectionStatusProvider.IConnectionStatusObserver var1) {
      NetworkCallback var2 = this.registeredCallbacks.remove(var1);
      if (var2 != null) {
         unregisterNetworkCallback(this.context, this.logger, this.buildInfoProvider, var2);
      }
   }
}
