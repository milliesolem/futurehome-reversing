package io.flutter.plugins.sharedpreferences

import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec

public interface SharedPreferencesAsyncApi {
   public abstract fun clear(allowList: List<String>?, options: SharedPreferencesPigeonOptions) {
   }

   public abstract fun getAll(allowList: List<String>?, options: SharedPreferencesPigeonOptions): Map<String, Any> {
   }

   public abstract fun getBool(key: String, options: SharedPreferencesPigeonOptions): Boolean? {
   }

   public abstract fun getDouble(key: String, options: SharedPreferencesPigeonOptions): Double? {
   }

   public abstract fun getInt(key: String, options: SharedPreferencesPigeonOptions): Long? {
   }

   public abstract fun getKeys(allowList: List<String>?, options: SharedPreferencesPigeonOptions): List<String> {
   }

   public abstract fun getPlatformEncodedStringList(key: String, options: SharedPreferencesPigeonOptions): List<String>? {
   }

   public abstract fun getString(key: String, options: SharedPreferencesPigeonOptions): String? {
   }

   public abstract fun getStringList(key: String, options: SharedPreferencesPigeonOptions): StringListResult? {
   }

   public abstract fun setBool(key: String, value: Boolean, options: SharedPreferencesPigeonOptions) {
   }

   public abstract fun setDeprecatedStringList(key: String, value: List<String>, options: SharedPreferencesPigeonOptions) {
   }

   public abstract fun setDouble(key: String, value: Double, options: SharedPreferencesPigeonOptions) {
   }

   public abstract fun setEncodedStringList(key: String, value: String, options: SharedPreferencesPigeonOptions) {
   }

   public abstract fun setInt(key: String, value: Long, options: SharedPreferencesPigeonOptions) {
   }

   public abstract fun setString(key: String, value: String, options: SharedPreferencesPigeonOptions) {
   }

   public companion object {
      public final val codec: MessageCodec<Any?> by LazyKt.lazy(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda6())
         public final get() {
            return codec$delegate.getValue();
         }


      @JvmStatic
      fun `codec_delegate$lambda$0`(): MessagesAsyncPigeonCodec {
         return new MessagesAsyncPigeonCodec();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$10$lambda$9`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         var var3: Any = var4.get(1);
         var3 = var3 as java.lang.String;
         var var11: Any = var4.get(2);
         var11 = var11 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var0.setEncodedStringList(var1, (java.lang.String)var3, (SharedPreferencesPigeonOptions)var11);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = MessagesAsync_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$12$lambda$11`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         var var4: Any = var3.get(1);
         var4 = var4 as java.util.List;
         var var10: Any = var3.get(2);
         var10 = var10 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var0.setDeprecatedStringList(var1, (java.util.List<java.lang.String>)var4, (SharedPreferencesPigeonOptions)var10);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = MessagesAsync_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$14$lambda$13`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         var var9: Any = var3.get(1);
         var9 = var9 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var6 = CollectionsKt.listOf(var0.getString(var1, (SharedPreferencesPigeonOptions)var9));
         } catch (var4: java.lang.Throwable) {
            var6 = MessagesAsync_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$16$lambda$15`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         var var9: Any = var3.get(1);
         var9 = var9 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var6 = CollectionsKt.listOf(var0.getBool(var1, (SharedPreferencesPigeonOptions)var9));
         } catch (var4: java.lang.Throwable) {
            var6 = MessagesAsync_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$18$lambda$17`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         var var9: Any = var3.get(1);
         var9 = var9 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var6 = CollectionsKt.listOf(var0.getDouble(var1, (SharedPreferencesPigeonOptions)var9));
         } catch (var4: java.lang.Throwable) {
            var6 = MessagesAsync_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$2$lambda$1`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         val var5: Any = var4.get(1);
         val var3: Boolean = var5 as java.lang.Boolean;
         var var11: Any = var4.get(2);
         var11 = var11 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var0.setBool(var1, var3, (SharedPreferencesPigeonOptions)var11);
            var8 = CollectionsKt.listOf(null);
         } catch (var6: java.lang.Throwable) {
            var8 = MessagesAsync_gKt.access$wrapError(var6);
            break label12;
         }

         var2.reply(var8);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$20$lambda$19`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         var var9: Any = var3.get(1);
         var9 = var9 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var6 = CollectionsKt.listOf(var0.getInt(var1, (SharedPreferencesPigeonOptions)var9));
         } catch (var4: java.lang.Throwable) {
            var6 = MessagesAsync_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$22$lambda$21`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         var var9: Any = var3.get(1);
         var9 = var9 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var6 = CollectionsKt.listOf(var0.getPlatformEncodedStringList(var1, (SharedPreferencesPigeonOptions)var9));
         } catch (var4: java.lang.Throwable) {
            var6 = MessagesAsync_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$24$lambda$23`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         var var9: Any = var3.get(1);
         var9 = var9 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var6 = CollectionsKt.listOf(var0.getStringList(var1, (SharedPreferencesPigeonOptions)var9));
         } catch (var4: java.lang.Throwable) {
            var6 = MessagesAsync_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$26$lambda$25`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var7: java.util.List = (var1 as java.util.List).get(0) as java.util.List;
         var var8: Any = (var1 as java.util.List).get(1);
         var8 = var8 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var0.clear(var7, (SharedPreferencesPigeonOptions)var8);
            var6 = CollectionsKt.listOf(null);
         } catch (var4: java.lang.Throwable) {
            var6 = MessagesAsync_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$28$lambda$27`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var7: java.util.List = (var1 as java.util.List).get(0) as java.util.List;
         var var8: Any = (var1 as java.util.List).get(1);
         var8 = var8 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var6 = CollectionsKt.listOf(var0.getAll(var7, (SharedPreferencesPigeonOptions)var8));
         } catch (var4: java.lang.Throwable) {
            var6 = MessagesAsync_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$30$lambda$29`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var7: java.util.List = (var1 as java.util.List).get(0) as java.util.List;
         var var8: Any = (var1 as java.util.List).get(1);
         var8 = var8 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var6 = CollectionsKt.listOf(var0.getKeys(var7, (SharedPreferencesPigeonOptions)var8));
         } catch (var4: java.lang.Throwable) {
            var6 = MessagesAsync_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$4$lambda$3`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         var var3: Any = var4.get(1);
         var3 = var3 as java.lang.String;
         var var11: Any = var4.get(2);
         var11 = var11 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var0.setString(var1, (java.lang.String)var3, (SharedPreferencesPigeonOptions)var11);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = MessagesAsync_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$6$lambda$5`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var5: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         val var6: Any = var5.get(1);
         val var3: Long = var6 as java.lang.Long;
         var var12: Any = var5.get(2);
         var12 = var12 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var0.setInt(var1, var3, (SharedPreferencesPigeonOptions)var12);
            var9 = CollectionsKt.listOf(null);
         } catch (var7: java.lang.Throwable) {
            var9 = MessagesAsync_gKt.access$wrapError(var7);
            break label12;
         }

         var2.reply(var9);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUp$lambda$8$lambda$7`(var0: SharedPreferencesAsyncApi, var1: Any, var2: BasicMessageChannel.Reply) {
         val var5: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as java.lang.String;
         val var6: Any = var5.get(1);
         val var3: Double = var6 as java.lang.Double;
         var var12: Any = var5.get(2);
         var12 = var12 as SharedPreferencesPigeonOptions;

         label12:
         try {
            var0.setDouble(var1, var3, (SharedPreferencesPigeonOptions)var12);
            var9 = CollectionsKt.listOf(null);
         } catch (var7: java.lang.Throwable) {
            var9 = MessagesAsync_gKt.access$wrapError(var7);
            break label12;
         }

         var2.reply(var9);
      }

      fun setUp(var1: BinaryMessenger, var2: SharedPreferencesAsyncApi) {
         setUp$default(this, var1, var2, null, 4, null);
      }

      public fun setUp(binaryMessenger: BinaryMessenger, api: SharedPreferencesAsyncApi?, messageChannelSuffix: String = "") {
         if (var3.length() > 0) {
            val var4: StringBuilder = new StringBuilder(".");
            var4.append(var3);
            var3 = var4.toString();
         } else {
            var3 = "";
         }

         val var8: BinaryMessenger.TaskQueue = var1.makeBackgroundTaskQueue();
         val var5: SharedPreferencesAsyncApi.Companion = this;
         val var9: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.setBool");
         var9.append(var3);
         val var10: BasicMessageChannel = new BasicMessageChannel<>(var1, var9.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var10.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda0(var2));
         } else {
            var10.setMessageHandler(null);
         }

         val var11: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.setString");
         var11.append(var3);
         val var12: BasicMessageChannel = new BasicMessageChannel<>(var1, var11.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var12.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda12(var2));
         } else {
            var12.setMessageHandler(null);
         }

         val var13: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.setInt");
         var13.append(var3);
         val var14: BasicMessageChannel = new BasicMessageChannel<>(var1, var13.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var14.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda13(var2));
         } else {
            var14.setMessageHandler(null);
         }

         val var15: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.setDouble");
         var15.append(var3);
         val var16: BasicMessageChannel = new BasicMessageChannel<>(var1, var15.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var16.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda14(var2));
         } else {
            var16.setMessageHandler(null);
         }

         val var17: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.setEncodedStringList");
         var17.append(var3);
         val var18: BasicMessageChannel = new BasicMessageChannel<>(var1, var17.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var18.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda15(var2));
         } else {
            var18.setMessageHandler(null);
         }

         val var19: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.setDeprecatedStringList");
         var19.append(var3);
         val var20: BasicMessageChannel = new BasicMessageChannel<>(var1, var19.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var20.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda1(var2));
         } else {
            var20.setMessageHandler(null);
         }

         val var21: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.getString");
         var21.append(var3);
         val var22: BasicMessageChannel = new BasicMessageChannel<>(var1, var21.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var22.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda2(var2));
         } else {
            var22.setMessageHandler(null);
         }

         val var23: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.getBool");
         var23.append(var3);
         val var24: BasicMessageChannel = new BasicMessageChannel<>(var1, var23.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var24.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda3(var2));
         } else {
            var24.setMessageHandler(null);
         }

         val var25: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.getDouble");
         var25.append(var3);
         val var26: BasicMessageChannel = new BasicMessageChannel<>(var1, var25.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var26.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda4(var2));
         } else {
            var26.setMessageHandler(null);
         }

         val var27: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.getInt");
         var27.append(var3);
         val var28: BasicMessageChannel = new BasicMessageChannel<>(var1, var27.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var28.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda5(var2));
         } else {
            var28.setMessageHandler(null);
         }

         val var29: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.getPlatformEncodedStringList");
         var29.append(var3);
         val var30: BasicMessageChannel = new BasicMessageChannel<>(var1, var29.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var30.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda7(var2));
         } else {
            var30.setMessageHandler(null);
         }

         val var31: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.getStringList");
         var31.append(var3);
         val var32: BasicMessageChannel = new BasicMessageChannel<>(var1, var31.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var32.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda8(var2));
         } else {
            var32.setMessageHandler(null);
         }

         val var33: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.clear");
         var33.append(var3);
         val var34: BasicMessageChannel = new BasicMessageChannel<>(var1, var33.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var34.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda9(var2));
         } else {
            var34.setMessageHandler(null);
         }

         val var35: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.getAll");
         var35.append(var3);
         val var36: BasicMessageChannel = new BasicMessageChannel<>(var1, var35.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var36.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda10(var2));
         } else {
            var36.setMessageHandler(null);
         }

         val var37: StringBuilder = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesAsyncApi.getKeys");
         var37.append(var3);
         val var6: BasicMessageChannel = new BasicMessageChannel<>(var1, var37.toString(), this.getCodec(), var8);
         if (var2 != null) {
            var6.setMessageHandler(new SharedPreferencesAsyncApi$Companion$$ExternalSyntheticLambda11(var2));
         } else {
            var6.setMessageHandler(null);
         }
      }
   }
}
