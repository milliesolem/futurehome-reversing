package com.mixpanel.android.mpmetrics;

import android.R.drawable;
import android.R.id;
import android.content.Context;
import android.util.SparseArray;
import com.mixpanel.android.util.MPLog;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ResourceReader implements ResourceIds {
   private static final String LOGTAG = "MixpanelAPI.RsrcReader";
   private final Context mContext;
   private final Map<String, Integer> mIdNameToId;
   private final SparseArray<String> mIdToIdName;

   protected ResourceReader(Context var1) {
      this.mContext = var1;
      this.mIdNameToId = new HashMap<>();
      this.mIdToIdName = new SparseArray();
   }

   private static void readClassIds(Class<?> param0, String param1, Map<String, Integer> param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokevirtual java/lang/Class.getFields ()[Ljava/lang/reflect/Field;
      // 04: astore 6
      // 06: bipush 0
      // 07: istore 3
      // 08: iload 3
      // 09: aload 6
      // 0b: arraylength
      // 0c: if_icmpge d0
      // 0f: aload 6
      // 11: iload 3
      // 12: aaload
      // 13: astore 7
      // 15: aload 7
      // 17: invokevirtual java/lang/reflect/Field.getModifiers ()I
      // 1a: invokestatic java/lang/reflect/Modifier.isStatic (I)Z
      // 1d: ifeq ac
      // 20: aload 7
      // 22: invokevirtual java/lang/reflect/Field.getType ()Ljava/lang/Class;
      // 25: astore 8
      // 27: getstatic java/lang/Integer.TYPE Ljava/lang/Class;
      // 2a: astore 5
      // 2c: aload 8
      // 2e: aload 5
      // 30: if_acmpne ac
      // 33: aload 7
      // 35: invokevirtual java/lang/reflect/Field.getName ()Ljava/lang/String;
      // 38: astore 5
      // 3a: aload 7
      // 3c: aconst_null
      // 3d: invokevirtual java/lang/reflect/Field.getInt (Ljava/lang/Object;)I
      // 40: istore 4
      // 42: aload 1
      // 43: ifnonnull 49
      // 46: goto 71
      // 49: new java/lang/StringBuilder
      // 4c: astore 7
      // 4e: aload 7
      // 50: invokespecial java/lang/StringBuilder.<init> ()V
      // 53: aload 7
      // 55: aload 1
      // 56: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 59: pop
      // 5a: aload 7
      // 5c: ldc ":"
      // 5e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 61: pop
      // 62: aload 7
      // 64: aload 5
      // 66: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 69: pop
      // 6a: aload 7
      // 6c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 6f: astore 5
      // 71: aload 2
      // 72: aload 5
      // 74: iload 4
      // 76: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 79: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 7e: pop
      // 7f: goto ac
      // 82: astore 5
      // 84: new java/lang/StringBuilder
      // 87: astore 7
      // 89: aload 7
      // 8b: invokespecial java/lang/StringBuilder.<init> ()V
      // 8e: aload 7
      // 90: ldc "Can't read built-in id name from "
      // 92: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 95: pop
      // 96: aload 7
      // 98: aload 0
      // 99: invokevirtual java/lang/Class.getName ()Ljava/lang/String;
      // 9c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 9f: pop
      // a0: ldc "MixpanelAPI.RsrcReader"
      // a2: aload 7
      // a4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // a7: aload 5
      // a9: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      // ac: iinc 3 1
      // af: goto 08
      // b2: astore 1
      // b3: new java/lang/StringBuilder
      // b6: dup
      // b7: ldc "Can't read built-in id names from "
      // b9: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // bc: astore 2
      // bd: aload 2
      // be: aload 0
      // bf: invokevirtual java/lang/Class.getName ()Ljava/lang/String;
      // c2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // c5: pop
      // c6: ldc "MixpanelAPI.RsrcReader"
      // c8: aload 2
      // c9: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // cc: aload 1
      // cd: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      // d0: return
   }

   protected abstract String getLocalClassName(Context var1);

   protected abstract Class<?> getSystemClass();

   @Override
   public int idFromName(String var1) {
      return this.mIdNameToId.get(var1);
   }

   protected void initialize() {
      this.mIdNameToId.clear();
      this.mIdToIdName.clear();
      readClassIds(this.getSystemClass(), "android", this.mIdNameToId);
      String var1 = this.getLocalClassName(this.mContext);

      try {
         readClassIds(Class.forName(var1), null, this.mIdNameToId);
      } catch (ClassNotFoundException var3) {
         StringBuilder var2 = new StringBuilder("Can't load names for Android view ids from '");
         var2.append(var1);
         var2.append("', ids by name will not be available in the events editor.");
         MPLog.w("MixpanelAPI.RsrcReader", var2.toString());
         MPLog.i(
            "MixpanelAPI.RsrcReader",
            "You may be missing a Resources class for your package due to your proguard configuration, or you may be using an applicationId in your build that isn't the same as the package declared in your AndroidManifest.xml file.\nIf you're using proguard, you can fix this issue by adding the following to your proguard configuration:\n\n-keep class **.R$* {\n    <fields>;\n}\n\nIf you're not using proguard, or if your proguard configuration already contains the directive above, you can add the following to your AndroidManifest.xml file to explicitly point the Mixpanel library to the appropriate library for your resources class:\n\n<meta-data android:name=\"com.mixpanel.android.MPConfig.ResourcePackageName\" android:value=\"YOUR_PACKAGE_NAME\" />\n\nwhere YOUR_PACKAGE_NAME is the same string you use for the \"package\" attribute in your <manifest> tag."
         );
      }

      for (Entry var4 : this.mIdNameToId.entrySet()) {
         this.mIdToIdName.put((Integer)var4.getValue(), var4.getKey());
      }
   }

   @Override
   public boolean knownIdName(String var1) {
      return this.mIdNameToId.containsKey(var1);
   }

   @Override
   public String nameForId(int var1) {
      return (String)this.mIdToIdName.get(var1);
   }

   public static class Drawables extends ResourceReader {
      private final String mResourcePackageName;

      protected Drawables(String var1, Context var2) {
         super(var2);
         this.mResourcePackageName = var1;
         this.initialize();
      }

      @Override
      protected String getLocalClassName(Context var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append(this.mResourcePackageName);
         var2.append(".R$drawable");
         return var2.toString();
      }

      @Override
      protected Class<?> getSystemClass() {
         return drawable.class;
      }
   }

   public static class Ids extends ResourceReader {
      private final String mResourcePackageName;

      public Ids(String var1, Context var2) {
         super(var2);
         this.mResourcePackageName = var1;
         this.initialize();
      }

      @Override
      protected String getLocalClassName(Context var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append(this.mResourcePackageName);
         var2.append(".R$id");
         return var2.toString();
      }

      @Override
      protected Class<?> getSystemClass() {
         return id.class;
      }
   }
}
