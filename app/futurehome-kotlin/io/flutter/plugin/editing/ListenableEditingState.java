package io.flutter.plugin.editing;

import android.text.Editable;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import io.flutter.Log;
import io.flutter.embedding.engine.systemchannels.TextInputChannel;
import java.util.ArrayList;
import java.util.Iterator;

class ListenableEditingState extends SpannableStringBuilder {
   private static final String TAG = "ListenableEditingState";
   private int mBatchEditNestDepth = 0;
   private ArrayList<TextEditingDelta> mBatchTextEditingDeltas;
   private int mChangeNotificationDepth = 0;
   private int mComposingEndWhenBeginBatchEdit;
   private int mComposingStartWhenBeginBatchEdit;
   private BaseInputConnection mDummyConnection;
   private ArrayList<ListenableEditingState.EditingStateWatcher> mListeners = new ArrayList<>();
   private ArrayList<ListenableEditingState.EditingStateWatcher> mPendingListeners = new ArrayList<>();
   private int mSelectionEndWhenBeginBatchEdit;
   private int mSelectionStartWhenBeginBatchEdit;
   private String mTextWhenBeginBatchEdit;
   private String mToStringCache;

   public ListenableEditingState(TextInputChannel.TextEditState var1, View var2) {
      this.mBatchTextEditingDeltas = new ArrayList<>();
      this.mDummyConnection = new BaseInputConnection(this, var2, true, this) {
         final ListenableEditingState this$0;
         final Editable val$self;

         {
            this.this$0 = var1;
            this.val$self = var4;
         }

         public Editable getEditable() {
            return this.val$self;
         }
      };
      if (var1 != null) {
         this.setEditingState(var1);
      }
   }

   private void notifyListener(ListenableEditingState.EditingStateWatcher var1, boolean var2, boolean var3, boolean var4) {
      this.mChangeNotificationDepth++;
      var1.didChangeEditingState(var2, var3, var4);
      this.mChangeNotificationDepth--;
   }

   private void notifyListenersIfNeeded(boolean var1, boolean var2, boolean var3) {
      if (var1 || var2 || var3) {
         Iterator var4 = this.mListeners.iterator();

         while (var4.hasNext()) {
            this.notifyListener((ListenableEditingState.EditingStateWatcher)var4.next(), var1, var2, var3);
         }
      }
   }

   public void addEditingStateListener(ListenableEditingState.EditingStateWatcher var1) {
      if (this.mChangeNotificationDepth > 0) {
         StringBuilder var2 = new StringBuilder("adding a listener ");
         var2.append(var1.toString());
         var2.append(" in a listener callback");
         Log.e("ListenableEditingState", var2.toString());
      }

      if (this.mBatchEditNestDepth > 0) {
         Log.w("ListenableEditingState", "a listener was added to EditingState while a batch edit was in progress");
         this.mPendingListeners.add(var1);
      } else {
         this.mListeners.add(var1);
      }
   }

   public void beginBatchEdit() {
      this.mBatchEditNestDepth++;
      if (this.mChangeNotificationDepth > 0) {
         Log.e("ListenableEditingState", "editing state should not be changed in a listener callback");
      }

      if (this.mBatchEditNestDepth == 1 && !this.mListeners.isEmpty()) {
         this.mTextWhenBeginBatchEdit = this.toString();
         this.mSelectionStartWhenBeginBatchEdit = this.getSelectionStart();
         this.mSelectionEndWhenBeginBatchEdit = this.getSelectionEnd();
         this.mComposingStartWhenBeginBatchEdit = this.getComposingStart();
         this.mComposingEndWhenBeginBatchEdit = this.getComposingEnd();
      }
   }

   public void clearBatchDeltas() {
      this.mBatchTextEditingDeltas.clear();
   }

   public void endBatchEdit() {
      int var1 = this.mBatchEditNestDepth;
      if (var1 == 0) {
         Log.e("ListenableEditingState", "endBatchEdit called without a matching beginBatchEdit");
      } else {
         if (var1 == 1) {
            Iterator var6 = this.mPendingListeners.iterator();

            while (var6.hasNext()) {
               this.notifyListener((ListenableEditingState.EditingStateWatcher)var6.next(), true, true, true);
            }

            if (!this.mListeners.isEmpty()) {
               StringBuilder var8 = new StringBuilder("didFinishBatchEdit with ");
               var8.append(String.valueOf(this.mListeners.size()));
               var8.append(" listener(s)");
               Log.v("ListenableEditingState", var8.toString());
               boolean var5 = this.toString().equals(this.mTextWhenBeginBatchEdit);
               int var2 = this.mSelectionStartWhenBeginBatchEdit;
               var1 = this.getSelectionStart();
               boolean var4 = false;
               boolean var3;
               if (var2 == var1 && this.mSelectionEndWhenBeginBatchEdit == this.getSelectionEnd()) {
                  var3 = false;
               } else {
                  var3 = true;
               }

               if (this.mComposingStartWhenBeginBatchEdit != this.getComposingStart() || this.mComposingEndWhenBeginBatchEdit != this.getComposingEnd()) {
                  var4 = true;
               }

               this.notifyListenersIfNeeded(var5 ^ true, var3, var4);
            }
         }

         this.mListeners.addAll(this.mPendingListeners);
         this.mPendingListeners.clear();
         this.mBatchEditNestDepth--;
      }
   }

   public ArrayList<TextEditingDelta> extractBatchTextEditingDeltas() {
      ArrayList var1 = new ArrayList<>(this.mBatchTextEditingDeltas);
      this.mBatchTextEditingDeltas.clear();
      return var1;
   }

   public final int getComposingEnd() {
      return BaseInputConnection.getComposingSpanEnd(this);
   }

   public final int getComposingStart() {
      return BaseInputConnection.getComposingSpanStart(this);
   }

   public final int getSelectionEnd() {
      return Selection.getSelectionEnd(this);
   }

   public final int getSelectionStart() {
      return Selection.getSelectionStart(this);
   }

   public void removeEditingStateListener(ListenableEditingState.EditingStateWatcher var1) {
      if (this.mChangeNotificationDepth > 0) {
         StringBuilder var2 = new StringBuilder("removing a listener ");
         var2.append(var1.toString());
         var2.append(" in a listener callback");
         Log.e("ListenableEditingState", var2.toString());
      }

      this.mListeners.remove(var1);
      if (this.mBatchEditNestDepth > 0) {
         this.mPendingListeners.remove(var1);
      }
   }

   public SpannableStringBuilder replace(int var1, int var2, CharSequence var3, int var4, int var5) {
      if (this.mChangeNotificationDepth > 0) {
         Log.e("ListenableEditingState", "editing state should not be changed in a listener callback");
      }

      String var13 = this.toString();
      int var8 = var2 - var1;
      boolean var10;
      if (var8 != var5 - var4) {
         var10 = true;
      } else {
         var10 = false;
      }

      for (int var6 = 0; var6 < var8 && !var10; var6++) {
         boolean var7;
         if (this.charAt(var1 + var6) != var3.charAt(var4 + var6)) {
            var7 = true;
         } else {
            var7 = false;
         }

         var10 |= var7;
      }

      if (var10) {
         this.mToStringCache = null;
      }

      var8 = this.getSelectionStart();
      int var15 = this.getSelectionEnd();
      int var9 = this.getComposingStart();
      int var16 = this.getComposingEnd();
      SpannableStringBuilder var14 = super.replace(var1, var2, var3, var4, var5);
      this.mBatchTextEditingDeltas
         .add(new TextEditingDelta(var13, var1, var2, var3, this.getSelectionStart(), this.getSelectionEnd(), this.getComposingStart(), this.getComposingEnd()));
      if (this.mBatchEditNestDepth > 0) {
         return var14;
      } else {
         boolean var11;
         if (this.getSelectionStart() == var8 && this.getSelectionEnd() == var15) {
            var11 = false;
         } else {
            var11 = true;
         }

         boolean var12;
         if (this.getComposingStart() == var9 && this.getComposingEnd() == var16) {
            var12 = false;
         } else {
            var12 = true;
         }

         this.notifyListenersIfNeeded(var10, var11, var12);
         return var14;
      }
   }

   public void setComposingRange(int var1, int var2) {
      if (var1 >= 0 && var1 < var2) {
         this.mDummyConnection.setComposingRegion(var1, var2);
      } else {
         BaseInputConnection.removeComposingSpans(this);
      }
   }

   public void setEditingState(TextInputChannel.TextEditState var1) {
      this.beginBatchEdit();
      this.replace(0, this.length(), var1.text);
      if (var1.hasSelection()) {
         Selection.setSelection(this, var1.selectionStart, var1.selectionEnd);
      } else {
         Selection.removeSelection(this);
      }

      this.setComposingRange(var1.composingStart, var1.composingEnd);
      this.clearBatchDeltas();
      this.endBatchEdit();
   }

   public void setSpan(Object var1, int var2, int var3, int var4) {
      super.setSpan(var1, var2, var3, var4);
      this.mBatchTextEditingDeltas
         .add(new TextEditingDelta(this.toString(), this.getSelectionStart(), this.getSelectionEnd(), this.getComposingStart(), this.getComposingEnd()));
   }

   public String toString() {
      String var1 = this.mToStringCache;
      if (var1 == null) {
         var1 = super.toString();
         this.mToStringCache = var1;
      }

      return var1;
   }

   interface EditingStateWatcher {
      void didChangeEditingState(boolean var1, boolean var2, boolean var3);
   }
}
