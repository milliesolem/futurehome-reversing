package io.flutter.plugin.editing;

import io.flutter.Log;
import org.json.JSONException;
import org.json.JSONObject;

public final class TextEditingDelta {
   private static final String TAG = "TextEditingDelta";
   private int deltaEnd;
   private int deltaStart;
   private CharSequence deltaText;
   private int newComposingEnd;
   private int newComposingStart;
   private int newSelectionEnd;
   private int newSelectionStart;
   private CharSequence oldText;

   public TextEditingDelta(CharSequence var1, int var2, int var3, int var4, int var5) {
      this.newSelectionStart = var2;
      this.newSelectionEnd = var3;
      this.newComposingStart = var4;
      this.newComposingEnd = var5;
      this.setDeltas(var1, "", -1, -1);
   }

   public TextEditingDelta(CharSequence var1, int var2, int var3, CharSequence var4, int var5, int var6, int var7, int var8) {
      this.newSelectionStart = var5;
      this.newSelectionEnd = var6;
      this.newComposingStart = var7;
      this.newComposingEnd = var8;
      this.setDeltas(var1, var4.toString(), var2, var3);
   }

   private void setDeltas(CharSequence var1, CharSequence var2, int var3, int var4) {
      this.oldText = var1;
      this.deltaText = var2;
      this.deltaStart = var3;
      this.deltaEnd = var4;
   }

   public int getDeltaEnd() {
      return this.deltaEnd;
   }

   public int getDeltaStart() {
      return this.deltaStart;
   }

   public CharSequence getDeltaText() {
      return this.deltaText;
   }

   public int getNewComposingEnd() {
      return this.newComposingEnd;
   }

   public int getNewComposingStart() {
      return this.newComposingStart;
   }

   public int getNewSelectionEnd() {
      return this.newSelectionEnd;
   }

   public int getNewSelectionStart() {
      return this.newSelectionStart;
   }

   public CharSequence getOldText() {
      return this.oldText;
   }

   public JSONObject toJSON() {
      JSONObject var2 = new JSONObject();

      try {
         var2.put("oldText", this.oldText.toString());
         var2.put("deltaText", this.deltaText.toString());
         var2.put("deltaStart", this.deltaStart);
         var2.put("deltaEnd", this.deltaEnd);
         var2.put("selectionBase", this.newSelectionStart);
         var2.put("selectionExtent", this.newSelectionEnd);
         var2.put("composingBase", this.newComposingStart);
         var2.put("composingExtent", this.newComposingEnd);
      } catch (JSONException var4) {
         StringBuilder var3 = new StringBuilder("unable to create JSONObject: ");
         var3.append(var4);
         Log.e("TextEditingDelta", var3.toString());
      }

      return var2;
   }
}
