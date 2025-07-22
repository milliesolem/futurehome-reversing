package io.sentry.android.ndk;

import io.sentry.Breadcrumb;
import io.sentry.ISentryExecutorService;
import io.sentry.ScopeObserverAdapter;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.protocol.User;
import io.sentry.util.Objects;

public final class NdkScopeObserver extends ScopeObserverAdapter {
   private final INativeScope nativeScope;
   private final SentryOptions options;

   public NdkScopeObserver(SentryOptions var1) {
      this(var1, new NativeScope());
   }

   NdkScopeObserver(SentryOptions var1, INativeScope var2) {
      this.options = Objects.requireNonNull(var1, "The SentryOptions object is required.");
      this.nativeScope = Objects.requireNonNull(var2, "The NativeScope object is required.");
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void addBreadcrumb(Breadcrumb var1) {
      try {
         ISentryExecutorService var2 = this.options.getExecutorService();
         NdkScopeObserver$$ExternalSyntheticLambda0 var3 = new NdkScopeObserver$$ExternalSyntheticLambda0(this, var1);
         var2.submit(var3);
      } catch (Throwable var5) {
         this.options.getLogger().log(SentryLevel.ERROR, var5, "Scope sync addBreadcrumb has an error.");
         return;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void removeExtra(String var1) {
      try {
         ISentryExecutorService var3 = this.options.getExecutorService();
         NdkScopeObserver$$ExternalSyntheticLambda4 var2 = new NdkScopeObserver$$ExternalSyntheticLambda4(this, var1);
         var3.submit(var2);
      } catch (Throwable var5) {
         this.options.getLogger().log(SentryLevel.ERROR, var5, "Scope sync removeExtra(%s) has an error.", var1);
         return;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void removeTag(String var1) {
      try {
         ISentryExecutorService var3 = this.options.getExecutorService();
         NdkScopeObserver$$ExternalSyntheticLambda2 var2 = new NdkScopeObserver$$ExternalSyntheticLambda2(this, var1);
         var3.submit(var2);
      } catch (Throwable var5) {
         this.options.getLogger().log(SentryLevel.ERROR, var5, "Scope sync removeTag(%s) has an error.", var1);
         return;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void setExtra(String var1, String var2) {
      try {
         ISentryExecutorService var4 = this.options.getExecutorService();
         NdkScopeObserver$$ExternalSyntheticLambda1 var3 = new NdkScopeObserver$$ExternalSyntheticLambda1(this, var1, var2);
         var4.submit(var3);
      } catch (Throwable var6) {
         this.options.getLogger().log(SentryLevel.ERROR, var6, "Scope sync setExtra(%s) has an error.", var1);
         return;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void setTag(String var1, String var2) {
      try {
         ISentryExecutorService var4 = this.options.getExecutorService();
         NdkScopeObserver$$ExternalSyntheticLambda3 var3 = new NdkScopeObserver$$ExternalSyntheticLambda3(this, var1, var2);
         var4.submit(var3);
      } catch (Throwable var6) {
         this.options.getLogger().log(SentryLevel.ERROR, var6, "Scope sync setTag(%s) has an error.", var1);
         return;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void setUser(User var1) {
      try {
         ISentryExecutorService var2 = this.options.getExecutorService();
         NdkScopeObserver$$ExternalSyntheticLambda5 var3 = new NdkScopeObserver$$ExternalSyntheticLambda5(this, var1);
         var2.submit(var3);
      } catch (Throwable var5) {
         this.options.getLogger().log(SentryLevel.ERROR, var5, "Scope sync setUser has an error.");
         return;
      }
   }
}
