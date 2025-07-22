package io.sentry.cache;

import io.sentry.ISerializer;
import io.sentry.SentryEnvelope;
import io.sentry.SentryEnvelopeItem;
import io.sentry.SentryItemType;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.Session;
import io.sentry.clientreport.DiscardReason;
import io.sentry.util.LazyEvaluator;
import io.sentry.util.Objects;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

abstract class CacheStrategy {
   protected static final Charset UTF_8 = Charset.forName("UTF-8");
   protected final File directory;
   private final int maxSize;
   protected SentryOptions options;
   protected final LazyEvaluator<ISerializer> serializer = new LazyEvaluator<>(new CacheStrategy$$ExternalSyntheticLambda0(this));

   CacheStrategy(SentryOptions var1, String var2, int var3) {
      Objects.requireNonNull(var2, "Directory is required.");
      this.options = Objects.requireNonNull(var1, "SentryOptions is required.");
      this.directory = new File(var2);
      this.maxSize = var3;
   }

   private SentryEnvelope buildNewEnvelope(SentryEnvelope var1, SentryEnvelopeItem var2) {
      ArrayList var3 = new ArrayList();
      Iterator var4 = var1.getItems().iterator();

      while (var4.hasNext()) {
         var3.add((SentryEnvelopeItem)var4.next());
      }

      var3.add(var2);
      return new SentryEnvelope(var1.getHeader(), var3);
   }

   private Session getFirstSession(SentryEnvelope var1) {
      for (SentryEnvelopeItem var2 : var1.getItems()) {
         if (this.isSessionType(var2)) {
            return this.readSession(var2);
         }
      }

      return null;
   }

   private boolean isSessionType(SentryEnvelopeItem var1) {
      return var1 == null ? false : var1.getHeader().getType().equals(SentryItemType.Session);
   }

   private boolean isValidEnvelope(SentryEnvelope var1) {
      return var1.getItems().iterator().hasNext();
   }

   private boolean isValidSession(Session var1) {
      boolean var3 = var1.getStatus().equals(Session.State.Ok);
      boolean var2 = false;
      if (!var3) {
         return false;
      } else {
         if (var1.getSessionId() != null) {
            var2 = true;
         }

         return var2;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void moveInitFlagIfNecessary(File var1, File[] var2) {
      SentryEnvelope var16 = this.readEnvelope(var1);
      if (var16 != null && this.isValidEnvelope(var16)) {
         this.options.getClientReportRecorder().recordLostEnvelope(DiscardReason.CACHE_OVERFLOW, var16);
         Session var9 = this.getFirstSession(var16);
         if (var9 != null && this.isValidSession(var9)) {
            Boolean var17 = var9.getInit();
            if (var17 != null && var17) {
               for (File var10 : var2) {
                  SentryEnvelope var11 = this.readEnvelope(var10);
                  if (var11 != null && this.isValidEnvelope(var11)) {
                     Iterator var12 = var11.getItems().iterator();

                     while (true) {
                        boolean var7 = var12.hasNext();
                        var18 = null;
                        Object var8 = null;
                        if (!var7) {
                           break;
                        }

                        SentryEnvelopeItem var19 = (SentryEnvelopeItem)var12.next();
                        if (this.isSessionType(var19)) {
                           Session var13 = this.readSession(var19);
                           if (var13 != null && this.isValidSession(var13)) {
                              Boolean var20 = var13.getInit();
                              if (var20 != null && var20) {
                                 this.options.getLogger().log(SentryLevel.ERROR, "Session %s has 2 times the init flag.", var9.getSessionId());
                                 return;
                              }

                              if (var9.getSessionId() != null && var9.getSessionId().equals(var13.getSessionId())) {
                                 var13.setInitAsTrue();
                                 var18 = (SentryEnvelopeItem)var8;

                                 try {
                                    var8 = SentryEnvelopeItem.fromSession(this.serializer.getValue(), var13);
                                 } catch (IOException var15) {
                                    this.options
                                       .getLogger()
                                       .log(SentryLevel.ERROR, var15, "Failed to create new envelope item for the session %s", var9.getSessionId());
                                    break;
                                 }

                                 var18 = (SentryEnvelopeItem)var8;

                                 try {
                                    var12.remove();
                                 } catch (IOException var14) {
                                    this.options
                                       .getLogger()
                                       .log(SentryLevel.ERROR, var14, "Failed to create new envelope item for the session %s", var9.getSessionId());
                                    break;
                                 }

                                 var18 = (SentryEnvelopeItem)var8;
                                 break;
                              }
                           }
                        }
                     }

                     if (var18 != null) {
                        SentryEnvelope var21 = this.buildNewEnvelope(var11, var18);
                        long var5 = var10.lastModified();
                        if (!var10.delete()) {
                           this.options.getLogger().log(SentryLevel.WARNING, "File can't be deleted: %s", var10.getAbsolutePath());
                        }

                        this.saveNewEnvelope(var21, var10, var5);
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryEnvelope readEnvelope(File var1) {
      BufferedInputStream var2;
      try {
         FileInputStream var3 = new FileInputStream(var1);
         var2 = new BufferedInputStream(var3);
      } catch (IOException var19) {
         this.options.getLogger().log(SentryLevel.ERROR, "Failed to deserialize the envelope.", var19);
         return null;
      }

      try {
         var23 = this.serializer.getValue().deserializeEnvelope(var2);
      } catch (Throwable var21) {
         Throwable var22 = var21;

         try {
            var2.close();
         } catch (Throwable var20) {
            Throwable var24 = var20;

            label60:
            try {
               var22.addSuppressed(var24);
               break label60;
            } catch (IOException var17) {
               this.options.getLogger().log(SentryLevel.ERROR, "Failed to deserialize the envelope.", var17);
               return null;
            }
         }

         try {
            throw var22;
         } catch (IOException var16) {
            this.options.getLogger().log(SentryLevel.ERROR, "Failed to deserialize the envelope.", var16);
            return null;
         }
      }

      try {
         var2.close();
         return var23;
      } catch (IOException var18) {
         this.options.getLogger().log(SentryLevel.ERROR, "Failed to deserialize the envelope.", var18);
         return null;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private Session readSession(SentryEnvelopeItem var1) {
      BufferedReader var2;
      try {
         ByteArrayInputStream var4 = new ByteArrayInputStream(var1.getData());
         InputStreamReader var3 = new InputStreamReader(var4, UTF_8);
         var2 = new BufferedReader(var3);
      } catch (Throwable var44) {
         this.options.getLogger().log(SentryLevel.ERROR, "Failed to deserialize the session.", var44);
         return null;
      }

      try {
         var48 = this.serializer.getValue().deserialize(var2, Session.class);
      } catch (Throwable var46) {
         Throwable var47 = var46;

         try {
            var2.close();
         } catch (Throwable var45) {
            Throwable var49 = var45;

            label128:
            try {
               var47.addSuppressed(var49);
               break label128;
            } catch (Throwable var42) {
               this.options.getLogger().log(SentryLevel.ERROR, "Failed to deserialize the session.", var42);
               return null;
            }
         }

         try {
            throw var47;
         } catch (Throwable var41) {
            this.options.getLogger().log(SentryLevel.ERROR, "Failed to deserialize the session.", var41);
            return null;
         }
      }

      try {
         var2.close();
         return var48;
      } catch (Throwable var43) {
         this.options.getLogger().log(SentryLevel.ERROR, "Failed to deserialize the session.", var43);
         return null;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void saveNewEnvelope(SentryEnvelope var1, File var2, long var3) {
      FileOutputStream var5;
      try {
         var5 = new FileOutputStream(var2);
      } catch (Throwable var45) {
         this.options.getLogger().log(SentryLevel.ERROR, "Failed to serialize the new envelope to the disk.", var45);
         return;
      }

      try {
         this.serializer.getValue().serialize(var1, var5);
         var2.setLastModified(var3);
      } catch (Throwable var47) {
         Throwable var48 = var47;

         try {
            var5.close();
         } catch (Throwable var46) {
            Throwable var49 = var46;

            label150:
            try {
               var48.addSuppressed(var49);
               break label150;
            } catch (Throwable var44) {
               this.options.getLogger().log(SentryLevel.ERROR, "Failed to serialize the new envelope to the disk.", var44);
               return;
            }
         }

         try {
            throw var48;
         } catch (Throwable var43) {
            this.options.getLogger().log(SentryLevel.ERROR, "Failed to serialize the new envelope to the disk.", var43);
            return;
         }
      }

      try {
         var5.close();
      } catch (Throwable var42) {
         this.options.getLogger().log(SentryLevel.ERROR, "Failed to serialize the new envelope to the disk.", var42);
         return;
      }
   }

   private void sortFilesOldestToNewest(File[] var1) {
      if (var1.length > 1) {
         Arrays.sort(var1, new CacheStrategy$$ExternalSyntheticLambda1());
      }
   }

   protected boolean isDirectoryValid() {
      if (this.directory.isDirectory() && this.directory.canWrite() && this.directory.canRead()) {
         return true;
      } else {
         this.options.getLogger().log(SentryLevel.ERROR, "The directory for caching files is inaccessible.: %s", this.directory.getAbsolutePath());
         return false;
      }
   }

   protected void rotateCacheIfNeeded(File[] var1) {
      int var2 = var1.length;
      if (var2 >= this.maxSize) {
         this.options.getLogger().log(SentryLevel.WARNING, "Cache folder if full (respecting maxSize). Rotating files");
         int var3 = var2 - this.maxSize + 1;
         this.sortFilesOldestToNewest(var1);
         File[] var4 = Arrays.copyOfRange(var1, var3, var2);

         for (int var6 = 0; var6 < var3; var6++) {
            File var5 = var1[var6];
            this.moveInitFlagIfNecessary(var5, var4);
            if (!var5.delete()) {
               this.options.getLogger().log(SentryLevel.WARNING, "File can't be deleted: %s", var5.getAbsolutePath());
            }
         }
      }
   }
}
