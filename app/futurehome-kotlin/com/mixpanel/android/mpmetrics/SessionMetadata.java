package com.mixpanel.android.mpmetrics;

import com.mixpanel.android.util.MPLog;
import java.security.SecureRandom;
import org.json.JSONException;
import org.json.JSONObject;

class SessionMetadata {
   private long mEventsCounter;
   private long mPeopleCounter;
   private final SecureRandom mRandom;
   private String mSessionID;
   private long mSessionStartEpoch;

   SessionMetadata() {
      this.initSession();
      this.mRandom = new SecureRandom();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private JSONObject getNewMetadata(boolean var1) {
      JSONObject var5 = new JSONObject();

      try {
         var5.put("$mp_event_id", Long.toHexString(this.mRandom.nextLong()));
         var5.put("$mp_session_id", this.mSessionID);
      } catch (JSONException var11) {
         MPLog.e(ConfigurationChecker.LOGTAG, "Cannot create session metadata JSON object", var11);
         return var5;
      }

      long var2;
      if (var1) {
         try {
            var2 = this.mEventsCounter;
         } catch (JSONException var10) {
            MPLog.e(ConfigurationChecker.LOGTAG, "Cannot create session metadata JSON object", var10);
            return var5;
         }
      } else {
         try {
            var2 = this.mPeopleCounter;
         } catch (JSONException var9) {
            MPLog.e(ConfigurationChecker.LOGTAG, "Cannot create session metadata JSON object", var9);
            return var5;
         }
      }

      try {
         var5.put("$mp_session_seq_id", var2);
         var5.put("$mp_session_start_sec", this.mSessionStartEpoch);
      } catch (JSONException var8) {
         MPLog.e(ConfigurationChecker.LOGTAG, "Cannot create session metadata JSON object", var8);
         return var5;
      }

      if (var1) {
         try {
            this.mEventsCounter++;
         } catch (JSONException var7) {
            MPLog.e(ConfigurationChecker.LOGTAG, "Cannot create session metadata JSON object", var7);
         }
      } else {
         try {
            this.mPeopleCounter++;
         } catch (JSONException var6) {
            MPLog.e(ConfigurationChecker.LOGTAG, "Cannot create session metadata JSON object", var6);
         }
      }

      return var5;
   }

   public JSONObject getMetadataForEvent() {
      return this.getNewMetadata(true);
   }

   public JSONObject getMetadataForPeople() {
      return this.getNewMetadata(false);
   }

   protected void initSession() {
      this.mEventsCounter = 0L;
      this.mPeopleCounter = 0L;
      this.mSessionID = Long.toHexString(new SecureRandom().nextLong());
      this.mSessionStartEpoch = System.currentTimeMillis() / 1000L;
   }
}
