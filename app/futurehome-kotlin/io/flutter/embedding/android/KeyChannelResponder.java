package io.flutter.embedding.android;

import android.view.KeyEvent;
import io.flutter.embedding.engine.systemchannels.KeyEventChannel;

public class KeyChannelResponder implements KeyboardManager.Responder {
   private static final String TAG = "KeyChannelResponder";
   private final KeyboardManager.CharacterCombiner characterCombiner = new KeyboardManager.CharacterCombiner();
   private final KeyEventChannel keyEventChannel;

   public KeyChannelResponder(KeyEventChannel var1) {
      this.keyEventChannel = var1;
   }

   @Override
   public void handleEvent(KeyEvent var1, KeyboardManager.Responder.OnKeyEventHandledCallback var2) {
      int var3 = var1.getAction();
      boolean var4 = false;
      if (var3 != 0 && var3 != 1) {
         var2.onKeyEventHandled(false);
      } else {
         KeyEventChannel.FlutterKeyEvent var5 = new KeyEventChannel.FlutterKeyEvent(
            var1, this.characterCombiner.applyCombiningCharacterToBaseCharacter(var1.getUnicodeChar())
         );
         if (var3 != 0) {
            var4 = true;
         }

         this.keyEventChannel.sendFlutterKeyEvent(var5, var4, new KeyChannelResponder$$ExternalSyntheticLambda0(var2));
      }
   }
}
