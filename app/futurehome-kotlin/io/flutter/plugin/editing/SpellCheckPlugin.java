package io.flutter.plugin.editing;

import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener;
import io.flutter.embedding.engine.systemchannels.SpellCheckChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.localization.LocalizationPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class SpellCheckPlugin implements SpellCheckChannel.SpellCheckMethodHandler, SpellCheckerSessionListener {
   public static final String END_INDEX_KEY = "endIndex";
   private static final int MAX_SPELL_CHECK_SUGGESTIONS = 5;
   public static final String START_INDEX_KEY = "startIndex";
   public static final String SUGGESTIONS_KEY = "suggestions";
   private final SpellCheckChannel mSpellCheckChannel;
   private SpellCheckerSession mSpellCheckerSession;
   private final TextServicesManager mTextServicesManager;
   MethodChannel.Result pendingResult;

   public SpellCheckPlugin(TextServicesManager var1, SpellCheckChannel var2) {
      this.mTextServicesManager = var1;
      this.mSpellCheckChannel = var2;
      var2.setSpellCheckMethodHandler(this);
   }

   public void destroy() {
      this.mSpellCheckChannel.setSpellCheckMethodHandler(null);
      SpellCheckerSession var1 = this.mSpellCheckerSession;
      if (var1 != null) {
         var1.close();
      }
   }

   @Override
   public void initiateSpellCheck(String var1, String var2, MethodChannel.Result var3) {
      if (this.pendingResult != null) {
         var3.error("error", "Previous spell check request still pending.", null);
      } else {
         this.pendingResult = var3;
         this.performSpellCheck(var1, var2);
      }
   }

   public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] var1) {
      if (var1.length == 0) {
         this.pendingResult.success(new ArrayList());
         this.pendingResult = null;
      } else {
         ArrayList var6 = new ArrayList();
         SentenceSuggestionsInfo var7 = var1[0];
         if (var7 == null) {
            this.pendingResult.success(new ArrayList());
            this.pendingResult = null;
         } else {
            for (int var2 = 0; var2 < var7.getSuggestionsCount(); var2++) {
               SuggestionsInfo var8 = var7.getSuggestionsInfoAt(var2);
               int var5 = var8.getSuggestionsCount();
               if (var5 > 0) {
                  HashMap var10 = new HashMap();
                  int var3 = var7.getOffsetAt(var2);
                  int var4 = var7.getLengthAt(var2);
                  var10.put("startIndex", var3);
                  var10.put("endIndex", var4 + var3);
                  ArrayList var9 = new ArrayList();
                  var3 = 0;

                  for (var13 = false; var3 < var5; var3++) {
                     String var11 = var8.getSuggestionAt(var3);
                     if (!var11.equals("")) {
                        var9.add(var11);
                        var13 = true;
                     }
                  }

                  if (var13) {
                     var10.put("suggestions", var9);
                     var6.add(var10);
                  }
               }
            }

            this.pendingResult.success(var6);
            this.pendingResult = null;
         }
      }
   }

   public void onGetSuggestions(SuggestionsInfo[] var1) {
   }

   public void performSpellCheck(String var1, String var2) {
      Locale var3 = LocalizationPlugin.localeFromString(var1);
      if (this.mSpellCheckerSession == null) {
         this.mSpellCheckerSession = this.mTextServicesManager.newSpellCheckerSession(null, var3, this, true);
      }

      TextInfo var4 = new TextInfo(var2);
      this.mSpellCheckerSession.getSentenceSuggestions(new TextInfo[]{var4}, 5);
   }
}
