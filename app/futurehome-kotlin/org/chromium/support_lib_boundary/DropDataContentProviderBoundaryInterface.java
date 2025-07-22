package org.chromium.support_lib_boundary;

import android.content.ContentProvider;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import java.io.FileNotFoundException;

public interface DropDataContentProviderBoundaryInterface {
   Uri cache(byte[] var1, String var2, String var3);

   Bundle call(String var1, String var2, Bundle var3);

   String[] getStreamTypes(Uri var1, String var2);

   String getType(Uri var1);

   boolean onCreate();

   void onDragEnd(boolean var1);

   ParcelFileDescriptor openFile(ContentProvider var1, Uri var2) throws FileNotFoundException;

   Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5);

   void setClearCachedDataIntervalMs(int var1);
}
