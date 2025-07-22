package io.flutter.embedding.engine.plugins.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import io.flutter.embedding.android.ExclusiveAppComponent;

public interface ActivityControlSurface {
   void attachToActivity(ExclusiveAppComponent<Activity> var1, Lifecycle var2);

   void detachFromActivity();

   void detachFromActivityForConfigChanges();

   boolean onActivityResult(int var1, int var2, Intent var3);

   void onNewIntent(Intent var1);

   boolean onRequestPermissionsResult(int var1, String[] var2, int[] var3);

   void onRestoreInstanceState(Bundle var1);

   void onSaveInstanceState(Bundle var1);

   void onUserLeaveHint();
}
