package io.sentry.android.core;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import io.sentry.android.core.internal.util.ContentProviderSecurityChecker;

abstract class EmptySecureContentProvider extends ContentProvider {
   private final ContentProviderSecurityChecker securityChecker = new ContentProviderSecurityChecker();

   public final int delete(Uri var1, String var2, String[] var3) {
      this.securityChecker.checkPrivilegeEscalation(this);
      return 0;
   }

   public final Uri insert(Uri var1, ContentValues var2) {
      this.securityChecker.checkPrivilegeEscalation(this);
      return null;
   }

   public final Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      this.securityChecker.checkPrivilegeEscalation(this);
      return null;
   }

   public final int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      this.securityChecker.checkPrivilegeEscalation(this);
      return 0;
   }
}
