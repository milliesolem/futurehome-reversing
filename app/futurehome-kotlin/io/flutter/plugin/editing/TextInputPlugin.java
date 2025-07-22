package io.flutter.plugin.editing;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.text.Editable;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0;
import androidx.core.view.inputmethod.EditorInfoCompat;
import io.flutter.Log;
import io.flutter.embedding.android.KeyboardManager;
import io.flutter.embedding.engine.systemchannels.TextInputChannel;
import io.flutter.plugin.platform.PlatformViewsController;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.util.ArrayList;
import java.util.HashMap;

public class TextInputPlugin implements ListenableEditingState.EditingStateWatcher {
   private static final String TAG = "TextInputPlugin";
   private final AutofillManager afm;
   private SparseArray<TextInputChannel.Configuration> autofillConfiguration;
   private TextInputChannel.Configuration configuration;
   private ImeSyncDeferringInsetsCallback imeSyncCallback;
   private TextInputPlugin.InputTarget inputTarget = new TextInputPlugin.InputTarget(TextInputPlugin.InputTarget.Type.NO_TARGET, 0);
   private boolean isInputConnectionLocked;
   private Rect lastClientRect;
   private InputConnection lastInputConnection;
   private ListenableEditingState mEditable;
   private final InputMethodManager mImm;
   private TextInputChannel.TextEditState mLastKnownFrameworkTextEditingState;
   private boolean mRestartInputPending;
   private final View mView;
   private PlatformViewsController platformViewsController;
   private final TextInputChannel textInputChannel;

   public TextInputPlugin(View var1, TextInputChannel var2, PlatformViewsController var3) {
      this.mView = var1;
      this.mEditable = new ListenableEditingState(null, var1);
      this.mImm = (InputMethodManager)var1.getContext().getSystemService("input_method");
      if (VERSION.SDK_INT >= 26) {
         this.afm = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
            ExternalSyntheticApiModelOutline0.m(var1.getContext(), AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m())
         );
      } else {
         this.afm = null;
      }

      if (VERSION.SDK_INT >= 30) {
         ImeSyncDeferringInsetsCallback var4 = new ImeSyncDeferringInsetsCallback(var1);
         this.imeSyncCallback = var4;
         var4.install();
      }

      this.textInputChannel = var2;
      var2.setTextInputMethodHandler(new TextInputChannel.TextInputMethodHandler(this) {
         final TextInputPlugin this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void clearClient() {
            this.this$0.clearTextInputClient();
         }

         @Override
         public void finishAutofillContext(boolean var1) {
            if (VERSION.SDK_INT >= 26 && this.this$0.afm != null) {
               if (var1) {
                  AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.this$0.afm);
               } else {
                  AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(this.this$0.afm);
               }
            }
         }

         @Override
         public void hide() {
            if (this.this$0.inputTarget.type == TextInputPlugin.InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW) {
               this.this$0.notifyViewExited();
            } else {
               TextInputPlugin var1x = this.this$0;
               var1x.hideTextInput(var1x.mView);
            }
         }

         @Override
         public void requestAutofill() {
            this.this$0.notifyViewEntered();
         }

         @Override
         public void sendAppPrivateCommand(String var1, Bundle var2x) {
            this.this$0.sendTextInputAppPrivateCommand(var1, var2x);
         }

         @Override
         public void setClient(int var1, TextInputChannel.Configuration var2x) {
            this.this$0.setTextInputClient(var1, var2x);
         }

         @Override
         public void setEditableSizeAndTransform(double var1, double var3x, double[] var5) {
            this.this$0.saveEditableSizeAndTransform(var1, var3x, var5);
         }

         @Override
         public void setEditingState(TextInputChannel.TextEditState var1) {
            TextInputPlugin var2x = this.this$0;
            var2x.setTextInputEditingState(var2x.mView, var1);
         }

         @Override
         public void setPlatformViewClient(int var1, boolean var2x) {
            this.this$0.setPlatformViewTextInputClient(var1, var2x);
         }

         @Override
         public void show() {
            TextInputPlugin var1x = this.this$0;
            var1x.showTextInput(var1x.mView);
         }
      });
      var2.requestExistingInputState();
      this.platformViewsController = var3;
      var3.attachTextInputPlugin(this);
   }

   private static boolean composingChanged(TextInputChannel.TextEditState var0, TextInputChannel.TextEditState var1) {
      int var3 = var0.composingEnd - var0.composingStart;
      if (var3 != var1.composingEnd - var1.composingStart) {
         return true;
      } else {
         for (int var2 = 0; var2 < var3; var2++) {
            if (var0.text.charAt(var0.composingStart + var2) != var1.text.charAt(var1.composingStart + var2)) {
               return true;
            }
         }

         return false;
      }
   }

   private void hideTextInput(View var1) {
      this.notifyViewExited();
      this.mImm.hideSoftInputFromWindow(var1.getApplicationWindowToken(), 0);
   }

   private static int inputTypeFromTextInputType(
      TextInputChannel.InputType var0, boolean var1, boolean var2, boolean var3, boolean var4, TextInputChannel.TextCapitalization var5
   ) {
      if (var0.type == TextInputChannel.TextInputType.DATETIME) {
         return 4;
      } else if (var0.type == TextInputChannel.TextInputType.NUMBER) {
         short var10;
         if (var0.isSigned) {
            var10 = 4098;
         } else {
            var10 = 2;
         }

         int var12 = var10;
         if (var0.isDecimal) {
            var12 = var10 | 8192;
         }

         return var12;
      } else if (var0.type == TextInputChannel.TextInputType.PHONE) {
         return 3;
      } else if (var0.type == TextInputChannel.TextInputType.NONE) {
         return 0;
      } else {
         int var6;
         if (var0.type == TextInputChannel.TextInputType.MULTILINE) {
            var6 = 131073;
         } else if (var0.type == TextInputChannel.TextInputType.EMAIL_ADDRESS) {
            var6 = 33;
         } else if (var0.type == TextInputChannel.TextInputType.URL) {
            var6 = 17;
         } else if (var0.type == TextInputChannel.TextInputType.VISIBLE_PASSWORD) {
            var6 = 145;
         } else if (var0.type == TextInputChannel.TextInputType.NAME) {
            var6 = 97;
         } else if (var0.type == TextInputChannel.TextInputType.POSTAL_ADDRESS) {
            var6 = 113;
         } else {
            var6 = 1;
         }

         int var8;
         label66: {
            int var7;
            if (var1) {
               var7 = 524416;
            } else {
               var7 = var6;
               if (var2) {
                  var7 = var6 | 32768;
               }

               var8 = var7;
               if (var3) {
                  break label66;
               }

               var8 = 524432;
               var6 = var7;
               var7 = var8;
            }

            var8 = var6 | var7;
         }

         if (var5 == TextInputChannel.TextCapitalization.CHARACTERS) {
            var6 = var8 | 4096;
         } else if (var5 == TextInputChannel.TextCapitalization.WORDS) {
            var6 = var8 | 8192;
         } else {
            var6 = var8;
            if (var5 == TextInputChannel.TextCapitalization.SENTENCES) {
               var6 = var8 | 16384;
            }
         }

         return var6;
      }
   }

   private boolean needsAutofill() {
      boolean var1;
      if (this.autofillConfiguration != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void notifyValueChanged(String var1) {
      if (VERSION.SDK_INT >= 26 && this.afm != null && this.needsAutofill()) {
         String var2 = this.configuration.autofill.uniqueIdentifier;
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
            this.afm, this.mView, var2.hashCode(), AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m((CharSequence)var1)
         );
      }
   }

   private void notifyViewEntered() {
      if (VERSION.SDK_INT >= 26 && this.afm != null && this.needsAutofill()) {
         String var3 = this.configuration.autofill.uniqueIdentifier;
         int[] var2 = new int[2];
         this.mView.getLocationOnScreen(var2);
         Rect var1 = new Rect(this.lastClientRect);
         var1.offset(var2[0], var2[1]);
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.afm, this.mView, var3.hashCode(), var1);
      }
   }

   private void notifyViewExited() {
      if (VERSION.SDK_INT >= 26 && this.afm != null) {
         TextInputChannel.Configuration var1 = this.configuration;
         if (var1 != null && var1.autofill != null && this.needsAutofill()) {
            String var2 = this.configuration.autofill.uniqueIdentifier;
            AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.afm, this.mView, var2.hashCode());
         }
      }
   }

   private void saveEditableSizeAndTransform(double var1, double var3, double[] var5) {
      double[] var15 = new double[4];
      boolean var14;
      if (var5[3] == 0.0 && var5[7] == 0.0 && var5[15] == 1.0) {
         var14 = true;
      } else {
         var14 = false;
      }

      double var8 = var5[12];
      double var6 = var5[15];
      var8 /= var6;
      var15[1] = var8;
      var15[0] = var8;
      var6 = var5[13] / var6;
      var15[3] = var6;
      var15[2] = var6;
      TextInputPlugin.MinMax var21 = new TextInputPlugin.MinMax(this, var14, var5, var15) {
         final TextInputPlugin this$0;
         final boolean val$isAffine;
         final double[] val$matrix;
         final double[] val$minMax;

         {
            this.this$0 = var1;
            this.val$isAffine = var2;
            this.val$matrix = var3x;
            this.val$minMax = var4;
         }

         @Override
         public void inspect(double var1, double var3x) {
            boolean var9 = this.val$isAffine;
            double var5x = 1.0;
            if (!var9) {
               double[] var10 = this.val$matrix;
               var5x = 1.0 / (var10[3] * var1 + var10[7] * var3x + var10[15]);
            }

            double[] var12 = this.val$matrix;
            double var7 = (var12[0] * var1 + var12[4] * var3x + var12[12]) * var5x;
            var1 = (var12[1] * var1 + var12[5] * var3x + var12[13]) * var5x;
            var12 = this.val$minMax;
            if (var7 < var12[0]) {
               var12[0] = var7;
            } else if (var7 > var12[1]) {
               var12[1] = var7;
            }

            if (var1 < var12[2]) {
               var12[2] = var1;
            } else if (var1 > var12[3]) {
               var12[3] = var1;
            }
         }
      };
      var21.inspect(var1, 0.0);
      var21.inspect(var1, var3);
      var21.inspect(0.0, var3);
      float var10 = this.mView.getContext().getResources().getDisplayMetrics().density;
      Float var22 = var10;
      var3 = var15[0];
      var22.getClass();
      var1 = var10;
      int var11 = (int)(var3 * var1);
      var3 = var15[2];
      var22.getClass();
      int var12 = (int)(var3 * var1);
      var3 = var15[1];
      var22.getClass();
      int var13 = (int)Math.ceil(var3 * var1);
      var3 = var15[3];
      var22.getClass();
      this.lastClientRect = new Rect(var11, var12, var13, (int)Math.ceil(var3 * var1));
   }

   private void setPlatformViewTextInputClient(int var1, boolean var2) {
      if (var2) {
         this.mView.requestFocus();
         this.inputTarget = new TextInputPlugin.InputTarget(TextInputPlugin.InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW, var1);
         this.mImm.restartInput(this.mView);
         this.mRestartInputPending = false;
      } else {
         this.inputTarget = new TextInputPlugin.InputTarget(TextInputPlugin.InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW, var1);
         this.lastInputConnection = null;
      }
   }

   private void updateAutofillConfigurationIfNeeded(TextInputChannel.Configuration var1) {
      if (VERSION.SDK_INT >= 26) {
         if (var1 != null && var1.autofill != null) {
            TextInputChannel.Configuration[] var4 = var1.fields;
            SparseArray var5 = new SparseArray();
            this.autofillConfiguration = var5;
            if (var4 == null) {
               var5.put(var1.autofill.uniqueIdentifier.hashCode(), var1);
            } else {
               for (TextInputChannel.Configuration var7 : var4) {
                  TextInputChannel.Configuration.Autofill var6 = var7.autofill;
                  if (var6 != null) {
                     this.autofillConfiguration.put(var6.uniqueIdentifier.hashCode(), var7);
                     AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
                        this.afm,
                        this.mView,
                        var6.uniqueIdentifier.hashCode(),
                        AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m((CharSequence)var6.editState.text)
                     );
                  }
               }
            }
         } else {
            this.autofillConfiguration = null;
         }
      }
   }

   public void autofill(SparseArray<AutofillValue> var1) {
      if (VERSION.SDK_INT >= 26) {
         TextInputChannel.Configuration var4 = this.configuration;
         if (var4 != null && this.autofillConfiguration != null && var4.autofill != null) {
            TextInputChannel.Configuration.Autofill var5 = this.configuration.autofill;
            HashMap var8 = new HashMap();

            for (int var2 = 0; var2 < var1.size(); var2++) {
               int var3 = var1.keyAt(var2);
               TextInputChannel.Configuration var6 = (TextInputChannel.Configuration)this.autofillConfiguration.get(var3);
               if (var6 != null && var6.autofill != null) {
                  TextInputChannel.Configuration.Autofill var9 = var6.autofill;
                  String var7 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
                        AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1.valueAt(var2))
                     )
                     .toString();
                  TextInputChannel.TextEditState var10 = new TextInputChannel.TextEditState(var7, var7.length(), var7.length(), -1, -1);
                  if (var9.uniqueIdentifier.equals(var5.uniqueIdentifier)) {
                     this.mEditable.setEditingState(var10);
                  } else {
                     var8.put(var9.uniqueIdentifier, var10);
                  }
               }
            }

            this.textInputChannel.updateEditingStateWithTag(this.inputTarget.id, var8);
         }
      }
   }

   public void clearPlatformViewClient(int var1) {
      if ((
            this.inputTarget.type == TextInputPlugin.InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW
               || this.inputTarget.type == TextInputPlugin.InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW
         )
         && this.inputTarget.id == var1) {
         this.inputTarget = new TextInputPlugin.InputTarget(TextInputPlugin.InputTarget.Type.NO_TARGET, 0);
         this.notifyViewExited();
         this.mImm.hideSoftInputFromWindow(this.mView.getApplicationWindowToken(), 0);
         this.mImm.restartInput(this.mView);
         this.mRestartInputPending = false;
      }
   }

   void clearTextInputClient() {
      if (this.inputTarget.type != TextInputPlugin.InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
         this.mEditable.removeEditingStateListener(this);
         this.notifyViewExited();
         this.configuration = null;
         this.updateAutofillConfigurationIfNeeded(null);
         this.inputTarget = new TextInputPlugin.InputTarget(TextInputPlugin.InputTarget.Type.NO_TARGET, 0);
         this.unlockPlatformViewInputConnection();
         this.lastClientRect = null;
      }
   }

   public InputConnection createInputConnection(View var1, KeyboardManager var2, EditorInfo var3) {
      if (this.inputTarget.type == TextInputPlugin.InputTarget.Type.NO_TARGET) {
         this.lastInputConnection = null;
         return null;
      } else if (this.inputTarget.type == TextInputPlugin.InputTarget.Type.PHYSICAL_DISPLAY_PLATFORM_VIEW) {
         return null;
      } else if (this.inputTarget.type == TextInputPlugin.InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
         if (this.isInputConnectionLocked) {
            return this.lastInputConnection;
         } else {
            InputConnection var6 = this.platformViewsController.getPlatformViewById(this.inputTarget.id).onCreateInputConnection(var3);
            this.lastInputConnection = var6;
            return var6;
         }
      } else {
         var3.inputType = inputTypeFromTextInputType(
            this.configuration.inputType,
            this.configuration.obscureText,
            this.configuration.autocorrect,
            this.configuration.enableSuggestions,
            this.configuration.enableIMEPersonalizedLearning,
            this.configuration.textCapitalization
         );
         var3.imeOptions = 33554432;
         if (VERSION.SDK_INT >= 26 && !this.configuration.enableIMEPersonalizedLearning) {
            var3.imeOptions |= 16777216;
         }

         int var4;
         if (this.configuration.inputAction == null) {
            if ((131072 & var3.inputType) != 0) {
               var4 = 1;
            } else {
               var4 = 6;
            }
         } else {
            var4 = this.configuration.inputAction;
         }

         if (this.configuration.actionLabel != null) {
            var3.actionLabel = this.configuration.actionLabel;
            var3.actionId = var4;
         }

         var3.imeOptions |= var4;
         if (this.configuration.contentCommitMimeTypes != null) {
            EditorInfoCompat.setContentMimeTypes(var3, this.configuration.contentCommitMimeTypes);
         }

         InputConnectionAdaptor var5 = new InputConnectionAdaptor(var1, this.inputTarget.id, this.textInputChannel, var2, this.mEditable, var3);
         var3.initialSelStart = this.mEditable.getSelectionStart();
         var3.initialSelEnd = this.mEditable.getSelectionEnd();
         this.lastInputConnection = var5;
         return var5;
      }
   }

   public void destroy() {
      this.platformViewsController.detachTextInputPlugin();
      this.textInputChannel.setTextInputMethodHandler(null);
      this.notifyViewExited();
      this.mEditable.removeEditingStateListener(this);
      ImeSyncDeferringInsetsCallback var1 = this.imeSyncCallback;
      if (var1 != null) {
         var1.remove();
      }
   }

   @Override
   public void didChangeEditingState(boolean var1, boolean var2, boolean var3) {
      if (var1) {
         this.notifyValueChanged(this.mEditable.toString());
      }

      int var7 = this.mEditable.getSelectionStart();
      int var4 = this.mEditable.getSelectionEnd();
      int var6 = this.mEditable.getComposingStart();
      int var5 = this.mEditable.getComposingEnd();
      ArrayList var9 = this.mEditable.extractBatchTextEditingDeltas();
      if (this.mLastKnownFrameworkTextEditingState != null
         && (
            !this.mEditable.toString().equals(this.mLastKnownFrameworkTextEditingState.text)
               || var7 != this.mLastKnownFrameworkTextEditingState.selectionStart
               || var4 != this.mLastKnownFrameworkTextEditingState.selectionEnd
               || var6 != this.mLastKnownFrameworkTextEditingState.composingStart
               || var5 != this.mLastKnownFrameworkTextEditingState.composingEnd
         )) {
         StringBuilder var8 = new StringBuilder("send EditingState to flutter: ");
         var8.append(this.mEditable.toString());
         Log.v("TextInputPlugin", var8.toString());
         if (this.configuration.enableDeltaModel) {
            this.textInputChannel.updateEditingStateWithDeltas(this.inputTarget.id, var9);
            this.mEditable.clearBatchDeltas();
         } else {
            this.textInputChannel.updateEditingState(this.inputTarget.id, this.mEditable.toString(), var7, var4, var6, var5);
         }

         this.mLastKnownFrameworkTextEditingState = new TextInputChannel.TextEditState(this.mEditable.toString(), var7, var4, var6, var5);
      } else {
         this.mEditable.clearBatchDeltas();
      }
   }

   Editable getEditable() {
      return this.mEditable;
   }

   ImeSyncDeferringInsetsCallback getImeSyncCallback() {
      return this.imeSyncCallback;
   }

   public InputMethodManager getInputMethodManager() {
      return this.mImm;
   }

   public InputConnection getLastInputConnection() {
      return this.lastInputConnection;
   }

   public boolean handleKeyEvent(KeyEvent var1) {
      if (this.getInputMethodManager().isAcceptingText()) {
         InputConnection var3 = this.lastInputConnection;
         if (var3 != null) {
            boolean var2;
            if (var3 instanceof InputConnectionAdaptor) {
               var2 = ((InputConnectionAdaptor)var3).handleKeyEvent(var1);
            } else {
               var2 = var3.sendKeyEvent(var1);
            }

            return var2;
         }
      }

      return false;
   }

   public void lockPlatformViewInputConnection() {
      if (this.inputTarget.type == TextInputPlugin.InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
         this.isInputConnectionLocked = true;
      }
   }

   public void onProvideAutofillVirtualStructure(ViewStructure var1, int var2) {
      if (VERSION.SDK_INT >= 26 && this.needsAutofill()) {
         String var7 = this.configuration.autofill.uniqueIdentifier;
         AutofillId var5 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1);

         for (int var9 = 0; var9 < this.autofillConfiguration.size(); var9++) {
            int var3 = this.autofillConfiguration.keyAt(var9);
            TextInputChannel.Configuration.Autofill var4 = ((TextInputChannel.Configuration)this.autofillConfiguration.valueAt(var9)).autofill;
            if (var4 != null) {
               AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 1);
               ViewStructure var6 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, var9);
               AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var6, var5, var3);
               if (var4.hints.length > 0) {
                  AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var6, var4.hints);
               }

               AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var6, 1);
               AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(var6, 0);
               if (var4.hintText != null) {
                  AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var6, var4.hintText);
               }

               if (var7.hashCode() == var3) {
                  Rect var8 = this.lastClientRect;
                  if (var8 != null) {
                     AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
                        var6, var8.left, this.lastClientRect.top, 0, 0, this.lastClientRect.width(), this.lastClientRect.height()
                     );
                     AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
                        var6, AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m((CharSequence)this.mEditable)
                     );
                     continue;
                  }
               }

               AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var6, 0, 0, 0, 0, 1, 1);
               AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
                  var6, AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m((CharSequence)var4.editState.text)
               );
            }
         }
      }
   }

   public void sendTextInputAppPrivateCommand(String var1, Bundle var2) {
      this.mImm.sendAppPrivateCommand(this.mView, var1, var2);
   }

   void setTextInputClient(int var1, TextInputChannel.Configuration var2) {
      this.notifyViewExited();
      this.configuration = var2;
      this.inputTarget = new TextInputPlugin.InputTarget(TextInputPlugin.InputTarget.Type.FRAMEWORK_CLIENT, var1);
      this.mEditable.removeEditingStateListener(this);
      TextInputChannel.TextEditState var3;
      if (var2.autofill != null) {
         var3 = var2.autofill.editState;
      } else {
         var3 = null;
      }

      this.mEditable = new ListenableEditingState(var3, this.mView);
      this.updateAutofillConfigurationIfNeeded(var2);
      this.mRestartInputPending = true;
      this.unlockPlatformViewInputConnection();
      this.lastClientRect = null;
      this.mEditable.addEditingStateListener(this);
   }

   void setTextInputEditingState(View var1, TextInputChannel.TextEditState var2) {
      if (!this.mRestartInputPending) {
         TextInputChannel.TextEditState var4 = this.mLastKnownFrameworkTextEditingState;
         if (var4 != null && var4.hasComposing()) {
            boolean var3 = composingChanged(this.mLastKnownFrameworkTextEditingState, var2);
            this.mRestartInputPending = var3;
            if (var3) {
               Log.i("TextInputPlugin", "Composing region changed by the framework. Restarting the input method.");
            }
         }
      }

      this.mLastKnownFrameworkTextEditingState = var2;
      this.mEditable.setEditingState(var2);
      if (this.mRestartInputPending) {
         this.mImm.restartInput(var1);
         this.mRestartInputPending = false;
      }
   }

   void showTextInput(View var1) {
      TextInputChannel.Configuration var2 = this.configuration;
      if (var2 != null && var2.inputType != null && this.configuration.inputType.type == TextInputChannel.TextInputType.NONE) {
         this.hideTextInput(var1);
      } else {
         var1.requestFocus();
         this.mImm.showSoftInput(var1, 0);
      }
   }

   public void unlockPlatformViewInputConnection() {
      if (this.inputTarget.type == TextInputPlugin.InputTarget.Type.VIRTUAL_DISPLAY_PLATFORM_VIEW) {
         this.isInputConnectionLocked = false;
      }
   }

   private static class InputTarget {
      int id;
      TextInputPlugin.InputTarget.Type type;

      public InputTarget(TextInputPlugin.InputTarget.Type var1, int var2) {
         this.type = var1;
         this.id = var2;
      }

      static enum Type {
         FRAMEWORK_CLIENT,
         NO_TARGET,
         PHYSICAL_DISPLAY_PLATFORM_VIEW,
         VIRTUAL_DISPLAY_PLATFORM_VIEW;
         private static final TextInputPlugin.InputTarget.Type[] $VALUES = $values();
      }
   }

   private interface MinMax {
      void inspect(double var1, double var3);
   }
}
