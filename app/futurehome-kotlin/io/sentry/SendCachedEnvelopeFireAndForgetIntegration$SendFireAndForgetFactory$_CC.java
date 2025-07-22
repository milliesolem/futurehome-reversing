package io.sentry;

import java.io.File;

// $VF: synthetic class
public final class SendCachedEnvelopeFireAndForgetIntegration$SendFireAndForgetFactory$_CC {
   public static boolean $default$hasValidPath(SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetFactory var0, String var1, ILogger var2) {
      if (var1 != null && !var1.isEmpty()) {
         return true;
      } else {
         var2.log(SentryLevel.INFO, "No cached dir path is defined in options.");
         return false;
      }
   }

   public static SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForget $default$processDir(
      SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetFactory var0, DirectoryProcessor var1, String var2, ILogger var3
   ) {
      return new SendCachedEnvelopeFireAndForgetIntegration$SendFireAndForgetFactory$$ExternalSyntheticLambda0(var3, var2, var1, new File(var2));
   }
}
