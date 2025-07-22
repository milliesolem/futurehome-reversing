package io.flutter.plugins.webviewflutter;

import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.os.Build.VERSION;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

class DisplayListenerProxy {
   private static final String TAG = "DisplayListenerProxy";
   private ArrayList<DisplayListener> listenersBeforeWebView;

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static ArrayList<DisplayListener> yoinkDisplayListeners(DisplayManager var0) {
      if (VERSION.SDK_INT >= 28) {
         return new ArrayList<>();
      } else {
         label55: {
            ArrayList var2;
            Iterator var3;
            try {
               Field var1 = DisplayManager.class.getDeclaredField("mGlobal");
               var1.setAccessible(true);
               ArrayList var14 = (ArrayList)var1.get(var0);
               var1 = var14.getClass().getDeclaredField("mDisplayListeners");
               var1.setAccessible(true);
               var14 = (ArrayList)var1.get(var14);
               var2 = new ArrayList();
               var3 = var14.iterator();
            } catch (NoSuchFieldException var11) {
               var13 = var11;
               break label55;
            } catch (IllegalAccessException var12) {
               var13 = var12;
               break label55;
            }

            Field var18 = null;

            while (true) {
               Object var4;
               try {
                  if (!var3.hasNext()) {
                     return var2;
                  }

                  var4 = var3.next();
               } catch (NoSuchFieldException var9) {
                  var13 = var9;
                  break;
               } catch (IllegalAccessException var10) {
                  var13 = var10;
                  break;
               }

               Field var16 = var18;
               if (var18 == null) {
                  try {
                     var16 = var4.getClass().getField("mListener");
                     var16.setAccessible(true);
                  } catch (NoSuchFieldException var7) {
                     var13 = var7;
                     break;
                  } catch (IllegalAccessException var8) {
                     var13 = var8;
                     break;
                  }
               }

               try {
                  var2.add((DisplayListener)var16.get(var4));
               } catch (NoSuchFieldException var5) {
                  var13 = var5;
                  break;
               } catch (IllegalAccessException var6) {
                  var13 = var6;
                  break;
               }

               var18 = var16;
            }
         }

         StringBuilder var19 = new StringBuilder("Could not extract WebView's display listeners. ");
         var19.append(var13);
         Log.w("DisplayListenerProxy", var19.toString());
         return new ArrayList<>();
      }
   }

   void onPostWebViewInitialization(DisplayManager var1) {
      ArrayList var2 = yoinkDisplayListeners(var1);
      var2.removeAll(this.listenersBeforeWebView);
      if (!var2.isEmpty()) {
         Iterator var3 = var2.iterator();

         while (var3.hasNext()) {
            var1.unregisterDisplayListener((DisplayListener)var3.next());
            var1.registerDisplayListener(new DisplayListener(this, var2, var1) {
               final DisplayListenerProxy this$0;
               final DisplayManager val$displayManager;
               final ArrayList val$webViewListeners;

               {
                  this.this$0 = var1;
                  this.val$webViewListeners = var2x;
                  this.val$displayManager = var3x;
               }

               public void onDisplayAdded(int var1) {
                  Iterator var2x = this.val$webViewListeners.iterator();

                  while (var2x.hasNext()) {
                     ((DisplayListener)var2x.next()).onDisplayAdded(var1);
                  }
               }

               public void onDisplayChanged(int var1) {
                  if (this.val$displayManager.getDisplay(var1) != null) {
                     Iterator var2x = this.val$webViewListeners.iterator();

                     while (var2x.hasNext()) {
                        ((DisplayListener)var2x.next()).onDisplayChanged(var1);
                     }
                  }
               }

               public void onDisplayRemoved(int var1) {
                  Iterator var2x = this.val$webViewListeners.iterator();

                  while (var2x.hasNext()) {
                     ((DisplayListener)var2x.next()).onDisplayRemoved(var1);
                  }
               }
            }, null);
         }
      }
   }

   void onPreWebViewInitialization(DisplayManager var1) {
      this.listenersBeforeWebView = yoinkDisplayListeners(var1);
   }
}
