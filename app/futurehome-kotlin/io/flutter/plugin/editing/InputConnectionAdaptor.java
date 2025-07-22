package io.flutter.plugin.editing;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.CursorAnchorInfo.Builder;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.systemchannels.TextInputChannel;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class InputConnectionAdaptor extends BaseInputConnection implements ListenableEditingState.EditingStateWatcher {
   private static final String TAG = "InputConnectionAdaptor";
   private int batchEditNestDepth;
   private FlutterTextUtils flutterTextUtils;
   private final InputConnectionAdaptor.KeyboardDelegate keyboardDelegate;
   private final int mClient;
   private Builder mCursorAnchorInfoBuilder;
   private final ListenableEditingState mEditable;
   private final EditorInfo mEditorInfo;
   private ExtractedTextRequest mExtractRequest;
   private ExtractedText mExtractedText;
   private final View mFlutterView;
   private InputMethodManager mImm;
   private final Layout mLayout;
   private boolean mMonitorCursorUpdate = false;
   private final TextInputChannel textInputChannel;

   public InputConnectionAdaptor(
      View var1, int var2, TextInputChannel var3, InputConnectionAdaptor.KeyboardDelegate var4, ListenableEditingState var5, EditorInfo var6
   ) {
      this(var1, var2, var3, var4, var5, var6, new FlutterJNI());
   }

   public InputConnectionAdaptor(
      View var1, int var2, TextInputChannel var3, InputConnectionAdaptor.KeyboardDelegate var4, ListenableEditingState var5, EditorInfo var6, FlutterJNI var7
   ) {
      super(var1, true);
      this.mExtractedText = new ExtractedText();
      this.batchEditNestDepth = 0;
      this.mFlutterView = var1;
      this.mClient = var2;
      this.textInputChannel = var3;
      this.mEditable = var5;
      var5.addEditingStateListener(this);
      this.mEditorInfo = var6;
      this.keyboardDelegate = var4;
      this.flutterTextUtils = new FlutterTextUtils(var7);
      this.mLayout = new DynamicLayout(var5, new TextPaint(), Integer.MAX_VALUE, Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
      this.mImm = (InputMethodManager)var1.getContext().getSystemService("input_method");
   }

   private static int clampIndexToEditable(int var0, Editable var1) {
      int var2 = Math.max(0, Math.min(var1.length(), var0));
      if (var2 != var0) {
         StringBuilder var3 = new StringBuilder("Text selection index was clamped (");
         var3.append(var0);
         var3.append("->");
         var3.append(var2);
         var3.append(") to remain in bounds. This may not be your fault, as some keyboards may select outside of bounds.");
         Log.d("flutter", var3.toString());
      }

      return var2;
   }

   private boolean doPerformContextMenuAction(int var1) {
      if (var1 == 16908319) {
         this.setSelection(0, this.mEditable.length());
         return true;
      } else if (var1 == 16908320) {
         int var11 = Selection.getSelectionStart(this.mEditable);
         int var13 = Selection.getSelectionEnd(this.mEditable);
         if (var11 != var13) {
            var1 = Math.min(var11, var13);
            var11 = Math.max(var11, var13);
            CharSequence var16 = this.mEditable.subSequence(var1, var11);
            ((ClipboardManager)this.mFlutterView.getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text label?", var16));
            this.mEditable.delete(var1, var11);
            this.setSelection(var1, var1);
         }

         return true;
      } else if (var1 == 16908321) {
         int var10 = Selection.getSelectionStart(this.mEditable);
         var1 = Selection.getSelectionEnd(this.mEditable);
         if (var10 != var1) {
            CharSequence var15 = this.mEditable.subSequence(Math.min(var10, var1), Math.max(var10, var1));
            ((ClipboardManager)this.mFlutterView.getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text label?", var15));
         }

         return true;
      } else if (var1 == 16908322) {
         ClipData var4 = ((ClipboardManager)this.mFlutterView.getContext().getSystemService("clipboard")).getPrimaryClip();
         if (var4 != null) {
            CharSequence var14 = var4.getItemAt(0).coerceToText(this.mFlutterView.getContext());
            int var3 = Math.max(0, Selection.getSelectionStart(this.mEditable));
            int var2 = Math.max(0, Selection.getSelectionEnd(this.mEditable));
            var1 = Math.min(var3, var2);
            var2 = Math.max(var3, var2);
            if (var1 != var2) {
               this.mEditable.delete(var1, var2);
            }

            this.mEditable.insert(var1, var14);
            var1 += var14.length();
            this.setSelection(var1, var1);
         }

         return true;
      } else {
         return false;
      }
   }

   private CursorAnchorInfo getCursorAnchorInfo() {
      Builder var3 = this.mCursorAnchorInfoBuilder;
      if (var3 == null) {
         this.mCursorAnchorInfoBuilder = new Builder();
      } else {
         var3.reset();
      }

      this.mCursorAnchorInfoBuilder.setSelectionRange(this.mEditable.getSelectionStart(), this.mEditable.getSelectionEnd());
      int var1 = this.mEditable.getComposingStart();
      int var2 = this.mEditable.getComposingEnd();
      if (var1 >= 0 && var2 > var1) {
         this.mCursorAnchorInfoBuilder.setComposingText(var1, this.mEditable.toString().subSequence(var1, var2));
      } else {
         this.mCursorAnchorInfoBuilder.setComposingText(-1, "");
      }

      return this.mCursorAnchorInfoBuilder.build();
   }

   private ExtractedText getExtractedText(ExtractedTextRequest var1) {
      this.mExtractedText.startOffset = 0;
      this.mExtractedText.partialStartOffset = -1;
      this.mExtractedText.partialEndOffset = -1;
      this.mExtractedText.selectionStart = this.mEditable.getSelectionStart();
      this.mExtractedText.selectionEnd = this.mEditable.getSelectionEnd();
      ExtractedText var2 = this.mExtractedText;
      Object var3;
      if (var1 != null && (var1.flags & 1) != 0) {
         var3 = this.mEditable;
      } else {
         var3 = this.mEditable.toString();
      }

      var2.text = (CharSequence)var3;
      return this.mExtractedText;
   }

   private boolean handleHorizontalMovement(boolean var1, boolean var2) {
      int var5 = Selection.getSelectionStart(this.mEditable);
      int var4 = Selection.getSelectionEnd(this.mEditable);
      if (var5 >= 0 && var4 >= 0) {
         int var3;
         if (var1) {
            var3 = Math.max(this.flutterTextUtils.getOffsetBefore(this.mEditable, var4), 0);
         } else {
            var3 = Math.min(this.flutterTextUtils.getOffsetAfter(this.mEditable, var4), this.mEditable.length());
         }

         if (var5 == var4 && !var2) {
            this.setSelection(var3, var3);
         } else {
            this.setSelection(var5, var3);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean handleVerticalMovement(boolean var1, boolean var2) {
      int var6 = Selection.getSelectionStart(this.mEditable);
      int var5 = Selection.getSelectionEnd(this.mEditable);
      boolean var4 = false;
      if (var6 >= 0 && var5 >= 0) {
         int var3 = var4;
         if (var6 == var5) {
            var3 = var4;
            if (!var2) {
               var3 = 1;
            }
         }

         this.beginBatchEdit();
         if (var3) {
            if (var1) {
               Selection.moveUp(this.mEditable, this.mLayout);
            } else {
               Selection.moveDown(this.mEditable, this.mLayout);
            }

            var3 = Selection.getSelectionStart(this.mEditable);
            this.setSelection(var3, var3);
         } else {
            if (var1) {
               Selection.extendUp(this.mEditable, this.mLayout);
            } else {
               Selection.extendDown(this.mEditable, this.mLayout);
            }

            this.setSelection(Selection.getSelectionStart(this.mEditable), Selection.getSelectionEnd(this.mEditable));
         }

         this.endBatchEdit();
         return true;
      } else {
         return false;
      }
   }

   private byte[] readStreamFully(InputStream var1, int var2) {
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      byte[] var5 = new byte[var2];

      while (true) {
         try {
            var2 = var1.read(var5);
         } catch (IOException var6) {
            var2 = -1;
         }

         if (var2 == -1) {
            return var4.toByteArray();
         }

         var4.write(var5, 0, var2);
      }
   }

   public boolean beginBatchEdit() {
      this.mEditable.beginBatchEdit();
      this.batchEditNestDepth++;
      return super.beginBatchEdit();
   }

   public boolean clearMetaKeyStates(int var1) {
      return super.clearMetaKeyStates(var1);
   }

   public void closeConnection() {
      super.closeConnection();
      this.mEditable.removeEditingStateListener(this);

      while (this.batchEditNestDepth > 0) {
         this.endBatchEdit();
         this.batchEditNestDepth--;
      }
   }

   public boolean commitContent(InputContentInfo var1, int var2, Bundle var3) {
      if (VERSION.SDK_INT >= 25 && (var2 & 1) != 0) {
         try {
            AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1);
         } catch (Exception var8) {
            return false;
         }

         if (AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1).getMimeTypeCount() > 0) {
            AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1);
            Uri var9 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1);
            String var4 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1).getMimeType(0);
            Context var5 = this.mFlutterView.getContext();
            if (var9 != null) {
               try {
                  var10 = var5.getContentResolver().openInputStream(var9);
               } catch (FileNotFoundException var7) {
                  AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(var1);
                  return false;
               }

               if (var10 != null) {
                  byte[] var11 = this.readStreamFully(var10, 65536);
                  HashMap var6 = new HashMap();
                  var6.put("mimeType", var4);
                  var6.put("data", var11);
                  var6.put("uri", var9.toString());
                  this.textInputChannel.commitContent(this.mClient, var6);
                  AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(var1);
                  return true;
               }
            }

            AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(var1);
         }
      }

      return false;
   }

   public boolean commitText(CharSequence var1, int var2) {
      return super.commitText(var1, var2);
   }

   public boolean deleteSurroundingText(int var1, int var2) {
      return this.mEditable.getSelectionStart() == -1 ? true : super.deleteSurroundingText(var1, var2);
   }

   public boolean deleteSurroundingTextInCodePoints(int var1, int var2) {
      return super.deleteSurroundingTextInCodePoints(var1, var2);
   }

   @Override
   public void didChangeEditingState(boolean var1, boolean var2, boolean var3) {
      this.mImm
         .updateSelection(
            this.mFlutterView,
            this.mEditable.getSelectionStart(),
            this.mEditable.getSelectionEnd(),
            this.mEditable.getComposingStart(),
            this.mEditable.getComposingEnd()
         );
      ExtractedTextRequest var4 = this.mExtractRequest;
      if (var4 != null) {
         this.mImm.updateExtractedText(this.mFlutterView, var4.token, this.getExtractedText(this.mExtractRequest));
      }

      if (this.mMonitorCursorUpdate) {
         CursorAnchorInfo var5 = this.getCursorAnchorInfo();
         this.mImm.updateCursorAnchorInfo(this.mFlutterView, var5);
      }
   }

   public boolean endBatchEdit() {
      boolean var1 = super.endBatchEdit();
      this.batchEditNestDepth--;
      this.mEditable.endBatchEdit();
      return var1;
   }

   public boolean finishComposingText() {
      return super.finishComposingText();
   }

   public Editable getEditable() {
      return this.mEditable;
   }

   public ExtractedText getExtractedText(ExtractedTextRequest var1, int var2) {
      boolean var3 = true;
      boolean var5;
      if ((var2 & 1) != 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (this.mExtractRequest != null) {
         var3 = false;
      }

      if (var5 == var3) {
         String var4;
         if (var5) {
            var4 = "on";
         } else {
            var4 = "off";
         }

         Log.d("InputConnectionAdaptor", "The input method toggled text monitoring ".concat(var4));
      }

      ExtractedTextRequest var6;
      if (var5) {
         var6 = var1;
      } else {
         var6 = null;
      }

      this.mExtractRequest = var6;
      return this.getExtractedText(var1);
   }

   public boolean handleKeyEvent(KeyEvent var1) {
      if (var1.getAction() == 0) {
         if (var1.getKeyCode() == 21) {
            return this.handleHorizontalMovement(true, var1.isShiftPressed());
         }

         if (var1.getKeyCode() == 22) {
            return this.handleHorizontalMovement(false, var1.isShiftPressed());
         }

         if (var1.getKeyCode() == 19) {
            return this.handleVerticalMovement(true, var1.isShiftPressed());
         }

         if (var1.getKeyCode() == 20) {
            return this.handleVerticalMovement(false, var1.isShiftPressed());
         }

         if ((var1.getKeyCode() == 66 || var1.getKeyCode() == 160) && (this.mEditorInfo.inputType & 131072) == 0) {
            this.performEditorAction(this.mEditorInfo.imeOptions & 0xFF);
            return true;
         }

         int var5 = Selection.getSelectionStart(this.mEditable);
         int var4 = Selection.getSelectionEnd(this.mEditable);
         int var3 = var1.getUnicodeChar();
         if (var5 >= 0 && var4 >= 0 && var3 != 0) {
            int var2 = Math.min(var5, var4);
            var4 = Math.max(var5, var4);
            this.beginBatchEdit();
            if (var2 != var4) {
               this.mEditable.delete(var2, var4);
            }

            this.mEditable.insert(var2, String.valueOf((char)var3));
            this.setSelection(++var2, var2);
            this.endBatchEdit();
            return true;
         }
      }

      return false;
   }

   public boolean performContextMenuAction(int var1) {
      this.beginBatchEdit();
      boolean var2 = this.doPerformContextMenuAction(var1);
      this.endBatchEdit();
      return var2;
   }

   public boolean performEditorAction(int var1) {
      if (var1 != 0) {
         if (var1 != 1) {
            if (var1 != 2) {
               if (var1 != 3) {
                  if (var1 != 4) {
                     if (var1 != 5) {
                        if (var1 != 7) {
                           this.textInputChannel.done(this.mClient);
                        } else {
                           this.textInputChannel.previous(this.mClient);
                        }
                     } else {
                        this.textInputChannel.next(this.mClient);
                     }
                  } else {
                     this.textInputChannel.send(this.mClient);
                  }
               } else {
                  this.textInputChannel.search(this.mClient);
               }
            } else {
               this.textInputChannel.go(this.mClient);
            }
         } else {
            this.textInputChannel.newline(this.mClient);
         }
      } else {
         this.textInputChannel.unspecifiedAction(this.mClient);
      }

      return true;
   }

   public boolean performPrivateCommand(String var1, Bundle var2) {
      this.textInputChannel.performPrivateCommand(this.mClient, var1, var2);
      return true;
   }

   public boolean requestCursorUpdates(int var1) {
      if ((var1 & 1) != 0) {
         this.mImm.updateCursorAnchorInfo(this.mFlutterView, this.getCursorAnchorInfo());
      }

      boolean var2;
      if ((var1 & 2) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2 != this.mMonitorCursorUpdate) {
         String var3;
         if (var2) {
            var3 = "on";
         } else {
            var3 = "off";
         }

         Log.d("InputConnectionAdaptor", "The input method toggled cursor monitoring ".concat(var3));
      }

      this.mMonitorCursorUpdate = var2;
      return true;
   }

   public boolean sendKeyEvent(KeyEvent var1) {
      return this.keyboardDelegate.handleEvent(var1);
   }

   public boolean setComposingRegion(int var1, int var2) {
      return super.setComposingRegion(var1, var2);
   }

   public boolean setComposingText(CharSequence var1, int var2) {
      this.beginBatchEdit();
      boolean var3;
      if (var1.length() == 0) {
         var3 = super.commitText(var1, var2);
      } else {
         var3 = super.setComposingText(var1, var2);
      }

      this.endBatchEdit();
      return var3;
   }

   public boolean setSelection(int var1, int var2) {
      this.beginBatchEdit();
      boolean var3 = super.setSelection(var1, var2);
      this.endBatchEdit();
      return var3;
   }

   public interface KeyboardDelegate {
      boolean handleEvent(KeyEvent var1);
   }
}
