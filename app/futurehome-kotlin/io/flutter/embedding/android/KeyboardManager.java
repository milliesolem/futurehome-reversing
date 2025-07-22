package io.flutter.embedding.android;

import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import io.flutter.Log;
import io.flutter.embedding.engine.systemchannels.KeyEventChannel;
import io.flutter.embedding.engine.systemchannels.KeyboardChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.editing.InputConnectionAdaptor;
import java.util.HashSet;
import java.util.Map;

public class KeyboardManager implements InputConnectionAdaptor.KeyboardDelegate, KeyboardChannel.KeyboardMethodHandler {
   private static final String TAG = "KeyboardManager";
   private final HashSet<KeyEvent> redispatchedEvents = new HashSet<>();
   protected final KeyboardManager.Responder[] responders;
   private final KeyboardManager.ViewDelegate viewDelegate;

   public KeyboardManager(KeyboardManager.ViewDelegate var1) {
      this.viewDelegate = var1;
      this.responders = new KeyboardManager.Responder[]{
         new KeyEmbedderResponder(var1.getBinaryMessenger()), new KeyChannelResponder(new KeyEventChannel(var1.getBinaryMessenger()))
      };
      new KeyboardChannel(var1.getBinaryMessenger()).setKeyboardMethodHandler(this);
   }

   private void onUnhandled(KeyEvent var1) {
      KeyboardManager.ViewDelegate var2 = this.viewDelegate;
      if (var2 != null && !var2.onTextInputKeyEvent(var1)) {
         this.redispatchedEvents.add(var1);
         this.viewDelegate.redispatch(var1);
         if (this.redispatchedEvents.remove(var1)) {
            Log.w("KeyboardManager", "A redispatched key event was consumed before reaching KeyboardManager");
         }
      }
   }

   public void destroy() {
      int var1 = this.redispatchedEvents.size();
      if (var1 > 0) {
         StringBuilder var2 = new StringBuilder("A KeyboardManager was destroyed with ");
         var2.append(String.valueOf(var1));
         var2.append(" unhandled redispatch event(s).");
         Log.w("KeyboardManager", var2.toString());
      }
   }

   @Override
   public Map<Long, Long> getKeyboardState() {
      return ((KeyEmbedderResponder)this.responders[0]).getPressedState();
   }

   @Override
   public boolean handleEvent(KeyEvent var1) {
      boolean var4 = this.redispatchedEvents.remove(var1);
      int var2 = 0;
      if (var4) {
         return false;
      } else {
         if (this.responders.length > 0) {
            KeyboardManager.PerEventCallbackBuilder var6 = new KeyboardManager.PerEventCallbackBuilder(this, var1);
            KeyboardManager.Responder[] var5 = this.responders;

            for (int var3 = var5.length; var2 < var3; var2++) {
               var5[var2].handleEvent(var1, var6.buildCallback());
            }
         } else {
            this.onUnhandled(var1);
         }

         return true;
      }
   }

   public static class CharacterCombiner {
      private int combiningCharacter = 0;

      Character applyCombiningCharacterToBaseCharacter(int var1) {
         char var3 = (char)var1;
         char var2;
         if ((-2147483648 & var1) != 0) {
            int var4 = var1 & 2147483647;
            var1 = this.combiningCharacter;
            if (var1 != 0) {
               this.combiningCharacter = KeyCharacterMap.getDeadChar(var1, var4);
               var2 = var3;
            } else {
               this.combiningCharacter = var4;
               var2 = var3;
            }
         } else {
            int var7 = this.combiningCharacter;
            var2 = var3;
            if (var7 != 0) {
               var1 = KeyCharacterMap.getDeadChar(var7, var1);
               var2 = var3;
               if (var1 > 0) {
                  var2 = (char)var1;
               }

               this.combiningCharacter = 0;
            }
         }

         return var2;
      }
   }

   private class PerEventCallbackBuilder {
      boolean isEventHandled;
      final KeyEvent keyEvent;
      final KeyboardManager this$0;
      int unrepliedCount;

      PerEventCallbackBuilder(KeyboardManager var1, KeyEvent var2) {
         this.this$0 = var1;
         this.unrepliedCount = var1.responders.length;
         this.isEventHandled = false;
         this.keyEvent = var2;
      }

      public KeyboardManager.Responder.OnKeyEventHandledCallback buildCallback() {
         return new KeyboardManager.PerEventCallbackBuilder.Callback(this);
      }

      private class Callback implements KeyboardManager.Responder.OnKeyEventHandledCallback {
         boolean isCalled;
         final KeyboardManager.PerEventCallbackBuilder this$1;

         private Callback(KeyboardManager.PerEventCallbackBuilder var1) {
            this.this$1 = var1;
            this.isCalled = false;
         }

         @Override
         public void onKeyEventHandled(boolean var1) {
            if (!this.isCalled) {
               this.isCalled = true;
               KeyboardManager.PerEventCallbackBuilder var2 = this.this$1;
               var2.unrepliedCount--;
               var2 = this.this$1;
               var2.isEventHandled |= var1;
               if (this.this$1.unrepliedCount == 0 && !this.this$1.isEventHandled) {
                  this.this$1.this$0.onUnhandled(this.this$1.keyEvent);
               }
            } else {
               throw new IllegalStateException("The onKeyEventHandledCallback should be called exactly once.");
            }
         }
      }
   }

   public interface Responder {
      void handleEvent(KeyEvent var1, KeyboardManager.Responder.OnKeyEventHandledCallback var2);

      public interface OnKeyEventHandledCallback {
         void onKeyEventHandled(boolean var1);
      }
   }

   public interface ViewDelegate {
      BinaryMessenger getBinaryMessenger();

      boolean onTextInputKeyEvent(KeyEvent var1);

      void redispatch(KeyEvent var1);
   }
}
