package com.mixpanel.android.mpmetrics;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.mixpanel.android.util.MPLog;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class MPDbAdapter {
   private static final String ANONYMOUS_PEOPLE_TIME_INDEX;
   public static final int AUTOMATIC_DATA_COLUMN_INDEX = 3;
   public static final int CREATED_AT_COLUMN_INDEX = 2;
   private static final String CREATE_ANONYMOUS_PEOPLE_TABLE;
   private static final String CREATE_EVENTS_TABLE;
   private static final String CREATE_GROUPS_TABLE;
   private static final String CREATE_PEOPLE_TABLE;
   private static final String DATABASE_NAME = "mixpanel";
   private static final int DATABASE_VERSION = 7;
   public static final int DATA_COLUMN_INDEX = 1;
   public static final int DB_OUT_OF_MEMORY_ERROR = -2;
   public static final int DB_UNDEFINED_CODE = -3;
   public static final int DB_UPDATE_ERROR = -1;
   private static final String EVENTS_TIME_INDEX;
   private static final String GROUPS_TIME_INDEX;
   public static final int ID_COLUMN_INDEX = 0;
   public static final String KEY_AUTOMATIC_DATA = "automatic_data";
   public static final String KEY_CREATED_AT = "created_at";
   public static final String KEY_DATA = "data";
   public static final String KEY_TOKEN = "token";
   private static final String LOGTAG = "MixpanelAPI.Database";
   private static final int MAX_DB_VERSION = 7;
   private static final int MIN_DB_VERSION = 4;
   private static final String PEOPLE_TIME_INDEX;
   public static final int TOKEN_COLUMN_INDEX = 4;
   private static final Map<String, MPDbAdapter> sInstances = new HashMap<>();
   private final MPDbAdapter.MPDatabaseHelper mDb;

   static {
      StringBuilder var0 = new StringBuilder("CREATE TABLE ");
      var0.append(MPDbAdapter.Table.EVENTS.getName());
      var0.append(
         " (_id INTEGER PRIMARY KEY AUTOINCREMENT, data STRING NOT NULL, created_at INTEGER NOT NULL, automatic_data INTEGER DEFAULT 0, token STRING NOT NULL DEFAULT '')"
      );
      CREATE_EVENTS_TABLE = var0.toString();
      var0 = new StringBuilder("CREATE TABLE ");
      var0.append(MPDbAdapter.Table.PEOPLE.getName());
      var0.append(
         " (_id INTEGER PRIMARY KEY AUTOINCREMENT, data STRING NOT NULL, created_at INTEGER NOT NULL, automatic_data INTEGER DEFAULT 0, token STRING NOT NULL DEFAULT '')"
      );
      CREATE_PEOPLE_TABLE = var0.toString();
      var0 = new StringBuilder("CREATE TABLE ");
      var0.append(MPDbAdapter.Table.GROUPS.getName());
      var0.append(
         " (_id INTEGER PRIMARY KEY AUTOINCREMENT, data STRING NOT NULL, created_at INTEGER NOT NULL, automatic_data INTEGER DEFAULT 0, token STRING NOT NULL DEFAULT '')"
      );
      CREATE_GROUPS_TABLE = var0.toString();
      var0 = new StringBuilder("CREATE TABLE ");
      var0.append(MPDbAdapter.Table.ANONYMOUS_PEOPLE.getName());
      var0.append(
         " (_id INTEGER PRIMARY KEY AUTOINCREMENT, data STRING NOT NULL, created_at INTEGER NOT NULL, automatic_data INTEGER DEFAULT 0, token STRING NOT NULL DEFAULT '')"
      );
      CREATE_ANONYMOUS_PEOPLE_TABLE = var0.toString();
      var0 = new StringBuilder("CREATE INDEX IF NOT EXISTS time_idx ON ");
      var0.append(MPDbAdapter.Table.EVENTS.getName());
      var0.append(" (created_at);");
      EVENTS_TIME_INDEX = var0.toString();
      var0 = new StringBuilder("CREATE INDEX IF NOT EXISTS time_idx ON ");
      var0.append(MPDbAdapter.Table.PEOPLE.getName());
      var0.append(" (created_at);");
      PEOPLE_TIME_INDEX = var0.toString();
      var0 = new StringBuilder("CREATE INDEX IF NOT EXISTS time_idx ON ");
      var0.append(MPDbAdapter.Table.GROUPS.getName());
      var0.append(" (created_at);");
      GROUPS_TIME_INDEX = var0.toString();
      var0 = new StringBuilder("CREATE INDEX IF NOT EXISTS time_idx ON ");
      var0.append(MPDbAdapter.Table.ANONYMOUS_PEOPLE.getName());
      var0.append(" (created_at);");
      ANONYMOUS_PEOPLE_TIME_INDEX = var0.toString();
   }

   public MPDbAdapter(Context var1, MPConfig var2) {
      this(var1, getDbName(var2.getInstanceName()), var2);
   }

   public MPDbAdapter(Context var1, String var2, MPConfig var3) {
      this.mDb = new MPDbAdapter.MPDatabaseHelper(var1, var2, var3);
   }

   private static String getDbName(String var0) {
      if (var0 != null && !var0.trim().isEmpty()) {
         StringBuilder var1 = new StringBuilder("mixpanel_");
         var1.append(var0);
         var0 = var1.toString();
      } else {
         var0 = "mixpanel";
      }

      return var0;
   }

   public static MPDbAdapter getInstance(Context param0, MPConfig param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter.sInstances Ljava/util/Map;
      // 03: astore 2
      // 04: aload 2
      // 05: monitorenter
      // 06: aload 0
      // 07: invokevirtual android/content/Context.getApplicationContext ()Landroid/content/Context;
      // 0a: astore 3
      // 0b: aload 1
      // 0c: invokevirtual com/mixpanel/android/mpmetrics/MPConfig.getInstanceName ()Ljava/lang/String;
      // 0f: astore 4
      // 11: aload 2
      // 12: aload 4
      // 14: invokeinterface java/util/Map.containsKey (Ljava/lang/Object;)Z 2
      // 19: ifne 33
      // 1c: new com/mixpanel/android/mpmetrics/MPDbAdapter
      // 1f: astore 0
      // 20: aload 0
      // 21: aload 3
      // 22: aload 1
      // 23: invokespecial com/mixpanel/android/mpmetrics/MPDbAdapter.<init> (Landroid/content/Context;Lcom/mixpanel/android/mpmetrics/MPConfig;)V
      // 26: aload 2
      // 27: aload 4
      // 29: aload 0
      // 2a: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 2f: pop
      // 30: goto 3f
      // 33: aload 2
      // 34: aload 4
      // 36: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 3b: checkcast com/mixpanel/android/mpmetrics/MPDbAdapter
      // 3e: astore 0
      // 3f: aload 2
      // 40: monitorexit
      // 41: aload 0
      // 42: areturn
      // 43: astore 0
      // 44: aload 2
      // 45: monitorexit
      // 46: aload 0
      // 47: athrow
   }

   protected boolean aboveMemThreshold() {
      return this.mDb.aboveMemThreshold();
   }

   public int addJSON(JSONObject param1, String param2, MPDbAdapter.Table param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.aboveMemThreshold ()Z
      // 004: ifeq 011
      // 007: ldc "MixpanelAPI.Database"
      // 009: ldc "There is not enough space left on the device or the data was over the maximum size limit so it was discarded"
      // 00b: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;)V
      // 00e: bipush -2
      // 010: ireturn
      // 011: aload 3
      // 012: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$Table.getName ()Ljava/lang/String;
      // 015: astore 8
      // 017: aconst_null
      // 018: astore 6
      // 01a: aconst_null
      // 01b: astore 5
      // 01d: aconst_null
      // 01e: astore 7
      // 020: aload 7
      // 022: astore 3
      // 023: aload 0
      // 024: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 027: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.getWritableDatabase ()Landroid/database/sqlite/SQLiteDatabase;
      // 02a: astore 9
      // 02c: aload 7
      // 02e: astore 3
      // 02f: new android/content/ContentValues
      // 032: astore 10
      // 034: aload 7
      // 036: astore 3
      // 037: aload 10
      // 039: invokespecial android/content/ContentValues.<init> ()V
      // 03c: aload 7
      // 03e: astore 3
      // 03f: aload 10
      // 041: ldc "data"
      // 043: aload 1
      // 044: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
      // 047: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 04a: aload 7
      // 04c: astore 3
      // 04d: aload 10
      // 04f: ldc "created_at"
      // 051: invokestatic java/lang/System.currentTimeMillis ()J
      // 054: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 057: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/Long;)V
      // 05a: aload 7
      // 05c: astore 3
      // 05d: aload 10
      // 05f: ldc "token"
      // 061: aload 2
      // 062: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 065: aload 7
      // 067: astore 3
      // 068: aload 9
      // 06a: aload 8
      // 06c: aconst_null
      // 06d: aload 10
      // 06f: invokevirtual android/database/sqlite/SQLiteDatabase.insert (Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
      // 072: pop2
      // 073: aload 7
      // 075: astore 3
      // 076: new java/lang/StringBuilder
      // 079: astore 1
      // 07a: aload 7
      // 07c: astore 3
      // 07d: aload 1
      // 07e: ldc "SELECT COUNT(*) FROM "
      // 080: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 083: aload 7
      // 085: astore 3
      // 086: aload 1
      // 087: aload 8
      // 089: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 08c: pop
      // 08d: aload 7
      // 08f: astore 3
      // 090: aload 1
      // 091: ldc " WHERE token='"
      // 093: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 096: pop
      // 097: aload 7
      // 099: astore 3
      // 09a: aload 1
      // 09b: aload 2
      // 09c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 09f: pop
      // 0a0: aload 7
      // 0a2: astore 3
      // 0a3: aload 1
      // 0a4: ldc "'"
      // 0a6: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0a9: pop
      // 0aa: aload 7
      // 0ac: astore 3
      // 0ad: aload 9
      // 0af: aload 1
      // 0b0: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0b3: aconst_null
      // 0b4: invokevirtual android/database/sqlite/SQLiteDatabase.rawQuery (Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
      // 0b7: astore 1
      // 0b8: aload 1
      // 0b9: astore 2
      // 0ba: aload 1
      // 0bb: invokeinterface android/database/Cursor.moveToFirst ()Z 1
      // 0c0: pop
      // 0c1: aload 1
      // 0c2: astore 2
      // 0c3: aload 1
      // 0c4: bipush 0
      // 0c5: invokeinterface android/database/Cursor.getInt (I)I 2
      // 0ca: istore 4
      // 0cc: aload 1
      // 0cd: ifnull 0d6
      // 0d0: aload 1
      // 0d1: invokeinterface android/database/Cursor.close ()V 1
      // 0d6: aload 0
      // 0d7: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 0da: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 0dd: goto 13a
      // 0e0: astore 2
      // 0e1: goto 0ea
      // 0e4: astore 1
      // 0e5: aload 3
      // 0e6: astore 2
      // 0e7: goto 13e
      // 0ea: aload 1
      // 0eb: astore 3
      // 0ec: ldc "MixpanelAPI.Database"
      // 0ee: ldc_w "Out of memory when adding Mixpanel data to table"
      // 0f1: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;)V
      // 0f4: aload 1
      // 0f5: ifnull 0fe
      // 0f8: aload 1
      // 0f9: invokeinterface android/database/Cursor.close ()V 1
      // 0fe: aload 0
      // 0ff: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 102: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 105: goto 137
      // 108: astore 1
      // 109: aconst_null
      // 10a: astore 1
      // 10b: aload 1
      // 10c: astore 2
      // 10d: ldc "MixpanelAPI.Database"
      // 10f: ldc_w "Could not add Mixpanel data to table"
      // 112: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;)V
      // 115: aload 1
      // 116: ifnull 127
      // 119: aload 1
      // 11a: astore 2
      // 11b: aload 1
      // 11c: invokeinterface android/database/Cursor.close ()V 1
      // 121: aload 5
      // 123: astore 1
      // 124: goto 127
      // 127: aload 1
      // 128: astore 3
      // 129: aload 0
      // 12a: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 12d: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.deleteDatabase ()V
      // 130: aload 1
      // 131: ifnull 0fe
      // 134: goto 0f8
      // 137: bipush -1
      // 138: istore 4
      // 13a: iload 4
      // 13c: ireturn
      // 13d: astore 1
      // 13e: aload 2
      // 13f: ifnull 148
      // 142: aload 2
      // 143: invokeinterface android/database/Cursor.close ()V 1
      // 148: aload 0
      // 149: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 14c: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 14f: aload 1
      // 150: athrow
      // 151: astore 1
      // 152: aload 6
      // 154: astore 1
      // 155: goto 0ea
      // 158: astore 2
      // 159: goto 10b
   }

   public void cleanupAllEvents(MPDbAdapter.Table var1, String var2) {
      String var9 = var1.getName();

      try {
         SQLiteDatabase var4 = this.mDb.getWritableDatabase();
         StringBuilder var10 = new StringBuilder("token = '");
         var10.append(var2);
         var10.append("'");
         var4.delete(var9, var10.toString(), null);
      } catch (SQLiteException var7) {
         StringBuilder var3 = new StringBuilder("Could not clean timed-out Mixpanel records from ");
         var3.append(var9);
         var3.append(". Re-initializing database.");
         MPLog.e("MixpanelAPI.Database", var3.toString(), var7);
         this.mDb.deleteDatabase();
      } finally {
         this.mDb.close();
      }
   }

   public void cleanupEvents(long var1, MPDbAdapter.Table var3) {
      String var10 = var3.getName();

      try {
         SQLiteDatabase var11 = this.mDb.getWritableDatabase();
         StringBuilder var4 = new StringBuilder("created_at <= ");
         var4.append(var1);
         var11.delete(var10, var4.toString(), null);
      } catch (SQLiteException var8) {
         StringBuilder var5 = new StringBuilder("Could not clean timed-out Mixpanel records from ");
         var5.append(var10);
         var5.append(". Re-initializing database.");
         MPLog.e("MixpanelAPI.Database", var5.toString(), var8);
         this.mDb.deleteDatabase();
      } finally {
         this.mDb.close();
      }
   }

   public void cleanupEvents(String var1, MPDbAdapter.Table var2, String var3) {
      String var15 = var2.getName();

      try {
         SQLiteDatabase var6 = this.mDb.getWritableDatabase();
         StringBuilder var5 = new StringBuilder("_id <= ");
         var5.append(var1);
         var5.append(" AND token = '");
         var5.append(var3);
         var5.append("'");
         StringBuffer var4 = new StringBuffer(var5.toString());
         var6.delete(var15, var4.toString(), null);
      } catch (SQLiteException var10) {
         StringBuilder var14 = new StringBuilder("Could not clean sent Mixpanel records from ");
         var14.append(var15);
         var14.append(". Re-initializing database.");
         MPLog.e("MixpanelAPI.Database", var14.toString(), var10);
         this.mDb.deleteDatabase();
      } catch (Exception var11) {
         StringBuilder var13 = new StringBuilder("Unknown exception. Could not clean sent Mixpanel records from ");
         var13.append(var15);
         var13.append(".Re-initializing database.");
         MPLog.e("MixpanelAPI.Database", var13.toString(), var11);
         this.mDb.deleteDatabase();
      } finally {
         this.mDb.close();
      }
   }

   public void deleteDB() {
      this.mDb.deleteDatabase();
   }

   public String[] generateDataString(MPDbAdapter.Table param1, String param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 1
      // 001: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$Table.getName ()Ljava/lang/String;
      // 004: astore 9
      // 006: aload 0
      // 007: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 00a: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.getReadableDatabase ()Landroid/database/sqlite/SQLiteDatabase;
      // 00d: astore 4
      // 00f: aconst_null
      // 010: astore 5
      // 012: new java/lang/StringBuffer
      // 015: astore 1
      // 016: new java/lang/StringBuilder
      // 019: astore 6
      // 01b: aload 6
      // 01d: ldc_w "SELECT * FROM "
      // 020: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 023: aload 6
      // 025: aload 9
      // 027: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 02a: pop
      // 02b: aload 6
      // 02d: ldc_w " WHERE token = '"
      // 030: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 033: pop
      // 034: aload 6
      // 036: aload 2
      // 037: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 03a: pop
      // 03b: aload 6
      // 03d: ldc_w "' "
      // 040: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 043: pop
      // 044: aload 1
      // 045: aload 6
      // 047: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 04a: invokespecial java/lang/StringBuffer.<init> (Ljava/lang/String;)V
      // 04d: new java/lang/StringBuffer
      // 050: astore 6
      // 052: new java/lang/StringBuilder
      // 055: astore 7
      // 057: aload 7
      // 059: ldc "SELECT COUNT(*) FROM "
      // 05b: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 05e: aload 7
      // 060: aload 9
      // 062: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 065: pop
      // 066: aload 7
      // 068: ldc_w " WHERE token = '"
      // 06b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 06e: pop
      // 06f: aload 7
      // 071: aload 2
      // 072: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 075: pop
      // 076: aload 7
      // 078: ldc_w "' "
      // 07b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 07e: pop
      // 07f: aload 6
      // 081: aload 7
      // 083: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 086: invokespecial java/lang/StringBuffer.<init> (Ljava/lang/String;)V
      // 089: new java/lang/StringBuilder
      // 08c: astore 2
      // 08d: aload 2
      // 08e: ldc_w "ORDER BY created_at ASC LIMIT "
      // 091: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 094: aload 2
      // 095: aload 0
      // 096: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 099: invokestatic com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.access$800 (Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;)Lcom/mixpanel/android/mpmetrics/MPConfig;
      // 09c: invokevirtual com/mixpanel/android/mpmetrics/MPConfig.getFlushBatchSize ()I
      // 09f: invokestatic java/lang/Integer.toString (I)Ljava/lang/String;
      // 0a2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0a5: pop
      // 0a6: aload 1
      // 0a7: aload 2
      // 0a8: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0ab: invokevirtual java/lang/StringBuffer.append (Ljava/lang/String;)Ljava/lang/StringBuffer;
      // 0ae: pop
      // 0af: aload 4
      // 0b1: aload 1
      // 0b2: invokevirtual java/lang/StringBuffer.toString ()Ljava/lang/String;
      // 0b5: aconst_null
      // 0b6: invokevirtual android/database/sqlite/SQLiteDatabase.rawQuery (Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
      // 0b9: astore 1
      // 0ba: aload 4
      // 0bc: aload 6
      // 0be: invokevirtual java/lang/StringBuffer.toString ()Ljava/lang/String;
      // 0c1: aconst_null
      // 0c2: invokevirtual android/database/sqlite/SQLiteDatabase.rawQuery (Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
      // 0c5: astore 7
      // 0c7: aload 7
      // 0c9: astore 4
      // 0cb: aload 1
      // 0cc: astore 5
      // 0ce: aload 7
      // 0d0: invokeinterface android/database/Cursor.moveToFirst ()Z 1
      // 0d5: pop
      // 0d6: aload 7
      // 0d8: astore 4
      // 0da: aload 1
      // 0db: astore 5
      // 0dd: aload 7
      // 0df: bipush 0
      // 0e0: invokeinterface android/database/Cursor.getInt (I)I 2
      // 0e5: invokestatic java/lang/String.valueOf (I)Ljava/lang/String;
      // 0e8: astore 8
      // 0ea: aload 7
      // 0ec: astore 4
      // 0ee: aload 1
      // 0ef: astore 5
      // 0f1: new org/json/JSONArray
      // 0f4: astore 6
      // 0f6: aload 7
      // 0f8: astore 4
      // 0fa: aload 1
      // 0fb: astore 5
      // 0fd: aload 6
      // 0ff: invokespecial org/json/JSONArray.<init> ()V
      // 102: aconst_null
      // 103: astore 2
      // 104: aload 7
      // 106: astore 4
      // 108: aload 1
      // 109: astore 5
      // 10b: aload 1
      // 10c: invokeinterface android/database/Cursor.moveToNext ()Z 1
      // 111: ifeq 1b4
      // 114: aload 7
      // 116: astore 4
      // 118: aload 1
      // 119: astore 5
      // 11b: aload 1
      // 11c: invokeinterface android/database/Cursor.isLast ()Z 1
      // 121: ifeq 15c
      // 124: aload 7
      // 126: astore 4
      // 128: aload 1
      // 129: astore 5
      // 12b: aload 1
      // 12c: ldc_w "_id"
      // 12f: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 134: iflt 14b
      // 137: aload 7
      // 139: astore 4
      // 13b: aload 1
      // 13c: astore 5
      // 13e: aload 1
      // 13f: ldc_w "_id"
      // 142: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 147: istore 3
      // 148: goto 14d
      // 14b: bipush 0
      // 14c: istore 3
      // 14d: aload 7
      // 14f: astore 4
      // 151: aload 1
      // 152: astore 5
      // 154: aload 1
      // 155: iload 3
      // 156: invokeinterface android/database/Cursor.getString (I)Ljava/lang/String; 2
      // 15b: astore 2
      // 15c: aload 7
      // 15e: astore 4
      // 160: aload 1
      // 161: astore 5
      // 163: aload 1
      // 164: ldc "data"
      // 166: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 16b: iflt 181
      // 16e: aload 7
      // 170: astore 4
      // 172: aload 1
      // 173: astore 5
      // 175: aload 1
      // 176: ldc "data"
      // 178: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 17d: istore 3
      // 17e: goto 183
      // 181: bipush 1
      // 182: istore 3
      // 183: aload 7
      // 185: astore 4
      // 187: aload 1
      // 188: astore 5
      // 18a: new org/json/JSONObject
      // 18d: astore 10
      // 18f: aload 7
      // 191: astore 4
      // 193: aload 1
      // 194: astore 5
      // 196: aload 10
      // 198: aload 1
      // 199: iload 3
      // 19a: invokeinterface android/database/Cursor.getString (I)Ljava/lang/String; 2
      // 19f: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 1a2: aload 7
      // 1a4: astore 4
      // 1a6: aload 1
      // 1a7: astore 5
      // 1a9: aload 6
      // 1ab: aload 10
      // 1ad: invokevirtual org/json/JSONArray.put (Ljava/lang/Object;)Lorg/json/JSONArray;
      // 1b0: pop
      // 1b1: goto 104
      // 1b4: aload 7
      // 1b6: astore 4
      // 1b8: aload 1
      // 1b9: astore 5
      // 1bb: aload 6
      // 1bd: invokevirtual org/json/JSONArray.length ()I
      // 1c0: ifle 1d8
      // 1c3: aload 7
      // 1c5: astore 4
      // 1c7: aload 1
      // 1c8: astore 5
      // 1ca: aload 6
      // 1cc: invokevirtual org/json/JSONArray.toString ()Ljava/lang/String;
      // 1cf: astore 6
      // 1d1: aload 6
      // 1d3: astore 4
      // 1d5: goto 1db
      // 1d8: aconst_null
      // 1d9: astore 4
      // 1db: aload 0
      // 1dc: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 1df: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 1e2: aload 1
      // 1e3: ifnull 1ec
      // 1e6: aload 1
      // 1e7: invokeinterface android/database/Cursor.close ()V 1
      // 1ec: aload 8
      // 1ee: astore 6
      // 1f0: aload 2
      // 1f1: astore 5
      // 1f3: aload 4
      // 1f5: astore 1
      // 1f6: aload 7
      // 1f8: ifnull 2bc
      // 1fb: aload 7
      // 1fd: invokeinterface android/database/Cursor.close ()V 1
      // 202: aload 8
      // 204: astore 6
      // 206: aload 2
      // 207: astore 5
      // 209: aload 4
      // 20b: astore 1
      // 20c: goto 2bc
      // 20f: astore 6
      // 211: aload 8
      // 213: astore 2
      // 214: goto 23d
      // 217: astore 6
      // 219: aconst_null
      // 21a: astore 2
      // 21b: goto 23d
      // 21e: astore 2
      // 21f: aconst_null
      // 220: astore 4
      // 222: goto 2de
      // 225: astore 6
      // 227: goto 238
      // 22a: astore 1
      // 22b: aconst_null
      // 22c: astore 4
      // 22e: aload 5
      // 230: astore 2
      // 231: goto 2e6
      // 234: astore 6
      // 236: aconst_null
      // 237: astore 1
      // 238: aconst_null
      // 239: astore 7
      // 23b: aconst_null
      // 23c: astore 2
      // 23d: aload 7
      // 23f: astore 4
      // 241: aload 1
      // 242: astore 5
      // 244: new java/lang/StringBuilder
      // 247: astore 8
      // 249: aload 7
      // 24b: astore 4
      // 24d: aload 1
      // 24e: astore 5
      // 250: aload 8
      // 252: invokespecial java/lang/StringBuilder.<init> ()V
      // 255: aload 7
      // 257: astore 4
      // 259: aload 1
      // 25a: astore 5
      // 25c: aload 8
      // 25e: ldc_w "Could not pull records for Mixpanel out of database "
      // 261: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 264: pop
      // 265: aload 7
      // 267: astore 4
      // 269: aload 1
      // 26a: astore 5
      // 26c: aload 8
      // 26e: aload 9
      // 270: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 273: pop
      // 274: aload 7
      // 276: astore 4
      // 278: aload 1
      // 279: astore 5
      // 27b: aload 8
      // 27d: ldc_w ". Waiting to send."
      // 280: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 283: pop
      // 284: aload 7
      // 286: astore 4
      // 288: aload 1
      // 289: astore 5
      // 28b: ldc "MixpanelAPI.Database"
      // 28d: aload 8
      // 28f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 292: aload 6
      // 294: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      // 297: aload 0
      // 298: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 29b: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 29e: aload 1
      // 29f: ifnull 2a8
      // 2a2: aload 1
      // 2a3: invokeinterface android/database/Cursor.close ()V 1
      // 2a8: aload 7
      // 2aa: ifnull 2b4
      // 2ad: aload 7
      // 2af: invokeinterface android/database/Cursor.close ()V 1
      // 2b4: aconst_null
      // 2b5: astore 1
      // 2b6: aconst_null
      // 2b7: astore 5
      // 2b9: aload 2
      // 2ba: astore 6
      // 2bc: aload 5
      // 2be: ifnull 2d8
      // 2c1: aload 1
      // 2c2: ifnull 2d8
      // 2c5: bipush 3
      // 2c6: anewarray 155
      // 2c9: dup
      // 2ca: bipush 0
      // 2cb: aload 5
      // 2cd: aastore
      // 2ce: dup
      // 2cf: bipush 1
      // 2d0: aload 1
      // 2d1: aastore
      // 2d2: dup
      // 2d3: bipush 2
      // 2d4: aload 6
      // 2d6: aastore
      // 2d7: areturn
      // 2d8: aconst_null
      // 2d9: areturn
      // 2da: astore 2
      // 2db: aload 5
      // 2dd: astore 1
      // 2de: aload 1
      // 2df: astore 5
      // 2e1: aload 2
      // 2e2: astore 1
      // 2e3: aload 5
      // 2e5: astore 2
      // 2e6: aload 0
      // 2e7: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 2ea: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 2ed: aload 2
      // 2ee: ifnull 2f7
      // 2f1: aload 2
      // 2f2: invokeinterface android/database/Cursor.close ()V 1
      // 2f7: aload 4
      // 2f9: ifnull 303
      // 2fc: aload 4
      // 2fe: invokeinterface android/database/Cursor.close ()V 1
      // 303: aload 1
      // 304: athrow
      // 305: astore 4
      // 307: goto 104
   }

   public File getDatabaseFile() {
      return this.mDb.mDatabaseFile;
   }

   int pushAnonymousUpdatesToPeopleDb(String param1, String param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.aboveMemThreshold ()Z
      // 004: ifeq 011
      // 007: ldc "MixpanelAPI.Database"
      // 009: ldc "There is not enough space left on the device or the data was over the maximum size limit so it was discarded"
      // 00b: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;)V
      // 00e: bipush -2
      // 010: ireturn
      // 011: aconst_null
      // 012: astore 8
      // 014: aconst_null
      // 015: astore 9
      // 017: bipush -1
      // 018: istore 3
      // 019: aload 9
      // 01b: astore 7
      // 01d: aload 0
      // 01e: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 021: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.getWritableDatabase ()Landroid/database/sqlite/SQLiteDatabase;
      // 024: astore 10
      // 026: aload 9
      // 028: astore 7
      // 02a: new java/lang/StringBuffer
      // 02d: astore 12
      // 02f: aload 9
      // 031: astore 7
      // 033: new java/lang/StringBuilder
      // 036: astore 11
      // 038: aload 9
      // 03a: astore 7
      // 03c: aload 11
      // 03e: ldc_w "SELECT * FROM "
      // 041: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 044: aload 9
      // 046: astore 7
      // 048: aload 11
      // 04a: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.ANONYMOUS_PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
      // 04d: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$Table.getName ()Ljava/lang/String;
      // 050: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 053: pop
      // 054: aload 9
      // 056: astore 7
      // 058: aload 11
      // 05a: ldc_w " WHERE token = '"
      // 05d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 060: pop
      // 061: aload 9
      // 063: astore 7
      // 065: aload 11
      // 067: aload 1
      // 068: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 06b: pop
      // 06c: aload 9
      // 06e: astore 7
      // 070: aload 11
      // 072: ldc "'"
      // 074: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 077: pop
      // 078: aload 9
      // 07a: astore 7
      // 07c: aload 12
      // 07e: aload 11
      // 080: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 083: invokespecial java/lang/StringBuffer.<init> (Ljava/lang/String;)V
      // 086: aload 9
      // 088: astore 7
      // 08a: aload 10
      // 08c: aload 12
      // 08e: invokevirtual java/lang/StringBuffer.toString ()Ljava/lang/String;
      // 091: aconst_null
      // 092: invokevirtual android/database/sqlite/SQLiteDatabase.rawQuery (Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
      // 095: astore 1
      // 096: iload 3
      // 097: istore 4
      // 099: aload 1
      // 09a: astore 7
      // 09c: aload 10
      // 09e: invokevirtual android/database/sqlite/SQLiteDatabase.beginTransaction ()V
      // 0a1: aload 1
      // 0a2: invokeinterface android/database/Cursor.moveToNext ()Z 1
      // 0a7: istore 6
      // 0a9: iload 6
      // 0ab: ifeq 1ec
      // 0ae: new android/content/ContentValues
      // 0b1: astore 9
      // 0b3: aload 9
      // 0b5: invokespecial android/content/ContentValues.<init> ()V
      // 0b8: aload 1
      // 0b9: ldc "created_at"
      // 0bb: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 0c0: iflt 0d0
      // 0c3: aload 1
      // 0c4: ldc "created_at"
      // 0c6: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 0cb: istore 4
      // 0cd: goto 0d3
      // 0d0: bipush 2
      // 0d1: istore 4
      // 0d3: aload 9
      // 0d5: ldc "created_at"
      // 0d7: aload 1
      // 0d8: iload 4
      // 0da: invokeinterface android/database/Cursor.getLong (I)J 2
      // 0df: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 0e2: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/Long;)V
      // 0e5: aload 1
      // 0e6: ldc "automatic_data"
      // 0e8: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 0ed: iflt 0fd
      // 0f0: aload 1
      // 0f1: ldc "automatic_data"
      // 0f3: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 0f8: istore 4
      // 0fa: goto 100
      // 0fd: bipush 3
      // 0fe: istore 4
      // 100: aload 9
      // 102: ldc "automatic_data"
      // 104: aload 1
      // 105: iload 4
      // 107: invokeinterface android/database/Cursor.getInt (I)I 2
      // 10c: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 10f: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/Integer;)V
      // 112: aload 1
      // 113: ldc "token"
      // 115: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 11a: iflt 12a
      // 11d: aload 1
      // 11e: ldc "token"
      // 120: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 125: istore 4
      // 127: goto 12d
      // 12a: bipush 4
      // 12b: istore 4
      // 12d: aload 9
      // 12f: ldc "token"
      // 131: aload 1
      // 132: iload 4
      // 134: invokeinterface android/database/Cursor.getString (I)Ljava/lang/String; 2
      // 139: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 13c: aload 1
      // 13d: ldc "data"
      // 13f: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 144: iflt 154
      // 147: aload 1
      // 148: ldc "data"
      // 14a: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 14f: istore 4
      // 151: goto 157
      // 154: bipush 1
      // 155: istore 4
      // 157: new org/json/JSONObject
      // 15a: astore 7
      // 15c: aload 7
      // 15e: aload 1
      // 15f: iload 4
      // 161: invokeinterface android/database/Cursor.getString (I)Ljava/lang/String; 2
      // 166: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 169: aload 7
      // 16b: ldc_w "$distinct_id"
      // 16e: aload 2
      // 16f: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 172: pop
      // 173: aload 9
      // 175: ldc "data"
      // 177: aload 7
      // 179: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
      // 17c: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 17f: aload 10
      // 181: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
      // 184: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$Table.getName ()Ljava/lang/String;
      // 187: aconst_null
      // 188: aload 9
      // 18a: invokevirtual android/database/sqlite/SQLiteDatabase.insert (Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
      // 18d: pop2
      // 18e: aload 1
      // 18f: ldc_w "_id"
      // 192: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 197: iflt 1a8
      // 19a: aload 1
      // 19b: ldc_w "_id"
      // 19e: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 1a3: istore 4
      // 1a5: goto 1ab
      // 1a8: bipush 0
      // 1a9: istore 4
      // 1ab: aload 1
      // 1ac: iload 4
      // 1ae: invokeinterface android/database/Cursor.getInt (I)I 2
      // 1b3: istore 4
      // 1b5: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.ANONYMOUS_PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
      // 1b8: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$Table.getName ()Ljava/lang/String;
      // 1bb: astore 7
      // 1bd: new java/lang/StringBuilder
      // 1c0: astore 9
      // 1c2: aload 9
      // 1c4: invokespecial java/lang/StringBuilder.<init> ()V
      // 1c7: aload 9
      // 1c9: ldc_w "_id = "
      // 1cc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1cf: pop
      // 1d0: aload 9
      // 1d2: iload 4
      // 1d4: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1d7: pop
      // 1d8: aload 10
      // 1da: aload 7
      // 1dc: aload 9
      // 1de: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1e1: aconst_null
      // 1e2: invokevirtual android/database/sqlite/SQLiteDatabase.delete (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
      // 1e5: pop
      // 1e6: iinc 3 1
      // 1e9: goto 0a1
      // 1ec: aload 10
      // 1ee: invokevirtual android/database/sqlite/SQLiteDatabase.setTransactionSuccessful ()V
      // 1f1: iload 3
      // 1f2: istore 4
      // 1f4: aload 1
      // 1f5: astore 7
      // 1f7: aload 10
      // 1f9: invokevirtual android/database/sqlite/SQLiteDatabase.endTransaction ()V
      // 1fc: iload 3
      // 1fd: istore 5
      // 1ff: aload 1
      // 200: ifnull 2a5
      // 203: aload 1
      // 204: invokeinterface android/database/Cursor.close ()V 1
      // 209: iload 3
      // 20a: istore 5
      // 20c: goto 2a5
      // 20f: astore 2
      // 210: iload 3
      // 211: istore 4
      // 213: aload 1
      // 214: astore 7
      // 216: aload 10
      // 218: invokevirtual android/database/sqlite/SQLiteDatabase.endTransaction ()V
      // 21b: iload 3
      // 21c: istore 4
      // 21e: aload 1
      // 21f: astore 7
      // 221: aload 2
      // 222: athrow
      // 223: astore 2
      // 224: goto 231
      // 227: astore 1
      // 228: goto 2b0
      // 22b: astore 2
      // 22c: aconst_null
      // 22d: astore 1
      // 22e: iload 3
      // 22f: istore 4
      // 231: aload 1
      // 232: astore 7
      // 234: new java/lang/StringBuilder
      // 237: astore 9
      // 239: aload 1
      // 23a: astore 7
      // 23c: aload 9
      // 23e: invokespecial java/lang/StringBuilder.<init> ()V
      // 241: aload 1
      // 242: astore 7
      // 244: aload 9
      // 246: ldc_w "Could not push anonymous updates records from "
      // 249: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 24c: pop
      // 24d: aload 1
      // 24e: astore 7
      // 250: aload 9
      // 252: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.ANONYMOUS_PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
      // 255: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$Table.getName ()Ljava/lang/String;
      // 258: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 25b: pop
      // 25c: aload 1
      // 25d: astore 7
      // 25f: aload 9
      // 261: ldc_w ". Re-initializing database."
      // 264: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 267: pop
      // 268: aload 1
      // 269: astore 7
      // 26b: ldc "MixpanelAPI.Database"
      // 26d: aload 9
      // 26f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 272: aload 2
      // 273: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      // 276: aload 1
      // 277: ifnull 289
      // 27a: aload 1
      // 27b: astore 7
      // 27d: aload 1
      // 27e: invokeinterface android/database/Cursor.close ()V 1
      // 283: aload 8
      // 285: astore 1
      // 286: goto 289
      // 289: aload 1
      // 28a: astore 7
      // 28c: aload 0
      // 28d: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 290: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.deleteDatabase ()V
      // 293: iload 4
      // 295: istore 5
      // 297: aload 1
      // 298: ifnull 2a5
      // 29b: aload 1
      // 29c: invokeinterface android/database/Cursor.close ()V 1
      // 2a1: iload 4
      // 2a3: istore 5
      // 2a5: aload 0
      // 2a6: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 2a9: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 2ac: iload 5
      // 2ae: ireturn
      // 2af: astore 1
      // 2b0: aload 7
      // 2b2: ifnull 2bc
      // 2b5: aload 7
      // 2b7: invokeinterface android/database/Cursor.close ()V 1
      // 2bc: aload 0
      // 2bd: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 2c0: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 2c3: aload 1
      // 2c4: athrow
      // 2c5: astore 7
      // 2c7: goto 0a1
   }

   int rewriteEventDataWithProperties(Map<String, String> param1, String param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.aboveMemThreshold ()Z
      // 004: ifeq 011
      // 007: ldc "MixpanelAPI.Database"
      // 009: ldc "There is not enough space left on the device or the data was over the maximum size limit so it was discarded"
      // 00b: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;)V
      // 00e: bipush -2
      // 010: ireturn
      // 011: bipush 0
      // 012: istore 3
      // 013: aconst_null
      // 014: astore 8
      // 016: aconst_null
      // 017: astore 7
      // 019: aload 7
      // 01b: astore 6
      // 01d: aload 0
      // 01e: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 021: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.getWritableDatabase ()Landroid/database/sqlite/SQLiteDatabase;
      // 024: astore 9
      // 026: aload 7
      // 028: astore 6
      // 02a: new java/lang/StringBuffer
      // 02d: astore 10
      // 02f: aload 7
      // 031: astore 6
      // 033: new java/lang/StringBuilder
      // 036: astore 11
      // 038: aload 7
      // 03a: astore 6
      // 03c: aload 11
      // 03e: ldc_w "SELECT * FROM "
      // 041: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 044: aload 7
      // 046: astore 6
      // 048: aload 11
      // 04a: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.EVENTS Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
      // 04d: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$Table.getName ()Ljava/lang/String;
      // 050: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 053: pop
      // 054: aload 7
      // 056: astore 6
      // 058: aload 11
      // 05a: ldc_w " WHERE token = '"
      // 05d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 060: pop
      // 061: aload 7
      // 063: astore 6
      // 065: aload 11
      // 067: aload 2
      // 068: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 06b: pop
      // 06c: aload 7
      // 06e: astore 6
      // 070: aload 11
      // 072: ldc "'"
      // 074: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 077: pop
      // 078: aload 7
      // 07a: astore 6
      // 07c: aload 10
      // 07e: aload 11
      // 080: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 083: invokespecial java/lang/StringBuffer.<init> (Ljava/lang/String;)V
      // 086: aload 7
      // 088: astore 6
      // 08a: aload 9
      // 08c: aload 10
      // 08e: invokevirtual java/lang/StringBuffer.toString ()Ljava/lang/String;
      // 091: aconst_null
      // 092: invokevirtual android/database/sqlite/SQLiteDatabase.rawQuery (Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
      // 095: astore 7
      // 097: aload 7
      // 099: astore 2
      // 09a: aload 9
      // 09c: invokevirtual android/database/sqlite/SQLiteDatabase.beginTransaction ()V
      // 09f: bipush 0
      // 0a0: istore 3
      // 0a1: aload 7
      // 0a3: invokeinterface android/database/Cursor.moveToNext ()Z 1
      // 0a8: istore 5
      // 0aa: iload 5
      // 0ac: ifeq 1a9
      // 0af: new android/content/ContentValues
      // 0b2: astore 2
      // 0b3: aload 2
      // 0b4: invokespecial android/content/ContentValues.<init> ()V
      // 0b7: aload 7
      // 0b9: ldc "data"
      // 0bb: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 0c0: iflt 0d1
      // 0c3: aload 7
      // 0c5: ldc "data"
      // 0c7: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 0cc: istore 4
      // 0ce: goto 0d4
      // 0d1: bipush 1
      // 0d2: istore 4
      // 0d4: new org/json/JSONObject
      // 0d7: astore 11
      // 0d9: aload 11
      // 0db: aload 7
      // 0dd: iload 4
      // 0df: invokeinterface android/database/Cursor.getString (I)Ljava/lang/String; 2
      // 0e4: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 0e7: aload 11
      // 0e9: ldc_w "properties"
      // 0ec: invokevirtual org/json/JSONObject.getJSONObject (Ljava/lang/String;)Lorg/json/JSONObject;
      // 0ef: astore 6
      // 0f1: aload 1
      // 0f2: invokeinterface java/util/Map.entrySet ()Ljava/util/Set; 1
      // 0f7: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 0fc: astore 12
      // 0fe: aload 12
      // 100: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 105: ifeq 131
      // 108: aload 12
      // 10a: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 10f: checkcast java/util/Map$Entry
      // 112: astore 10
      // 114: aload 6
      // 116: aload 10
      // 118: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 11d: checkcast java/lang/String
      // 120: aload 10
      // 122: invokeinterface java/util/Map$Entry.getValue ()Ljava/lang/Object; 1
      // 127: checkcast java/lang/String
      // 12a: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 12d: pop
      // 12e: goto 0fe
      // 131: aload 11
      // 133: ldc_w "properties"
      // 136: aload 6
      // 138: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 13b: pop
      // 13c: aload 2
      // 13d: ldc "data"
      // 13f: aload 11
      // 141: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
      // 144: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
      // 147: aload 7
      // 149: ldc_w "_id"
      // 14c: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 151: iflt 163
      // 154: aload 7
      // 156: ldc_w "_id"
      // 159: invokeinterface android/database/Cursor.getColumnIndex (Ljava/lang/String;)I 2
      // 15e: istore 4
      // 160: goto 166
      // 163: bipush 0
      // 164: istore 4
      // 166: aload 7
      // 168: iload 4
      // 16a: invokeinterface android/database/Cursor.getInt (I)I 2
      // 16f: istore 4
      // 171: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.EVENTS Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
      // 174: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$Table.getName ()Ljava/lang/String;
      // 177: astore 10
      // 179: new java/lang/StringBuilder
      // 17c: astore 6
      // 17e: aload 6
      // 180: invokespecial java/lang/StringBuilder.<init> ()V
      // 183: aload 6
      // 185: ldc_w "_id = "
      // 188: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 18b: pop
      // 18c: aload 6
      // 18e: iload 4
      // 190: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 193: pop
      // 194: aload 9
      // 196: aload 10
      // 198: aload 2
      // 199: aload 6
      // 19b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 19e: aconst_null
      // 19f: invokevirtual android/database/sqlite/SQLiteDatabase.update (Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
      // 1a2: pop
      // 1a3: iinc 3 1
      // 1a6: goto 0a1
      // 1a9: aload 9
      // 1ab: invokevirtual android/database/sqlite/SQLiteDatabase.setTransactionSuccessful ()V
      // 1ae: aload 7
      // 1b0: astore 2
      // 1b1: aload 9
      // 1b3: invokevirtual android/database/sqlite/SQLiteDatabase.endTransaction ()V
      // 1b6: aload 7
      // 1b8: ifnull 1c2
      // 1bb: aload 7
      // 1bd: invokeinterface android/database/Cursor.close ()V 1
      // 1c2: aload 0
      // 1c3: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 1c6: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 1c9: goto 22b
      // 1cc: astore 1
      // 1cd: aload 7
      // 1cf: astore 2
      // 1d0: aload 9
      // 1d2: invokevirtual android/database/sqlite/SQLiteDatabase.endTransaction ()V
      // 1d5: aload 7
      // 1d7: astore 2
      // 1d8: aload 1
      // 1d9: athrow
      // 1da: astore 6
      // 1dc: aload 7
      // 1de: astore 1
      // 1df: goto 1f2
      // 1e2: astore 6
      // 1e4: aload 7
      // 1e6: astore 1
      // 1e7: goto 1f2
      // 1ea: astore 1
      // 1eb: goto 231
      // 1ee: astore 6
      // 1f0: aconst_null
      // 1f1: astore 1
      // 1f2: aload 1
      // 1f3: astore 2
      // 1f4: ldc "MixpanelAPI.Database"
      // 1f6: ldc_w "Could not re-write events history. Re-initializing database."
      // 1f9: aload 6
      // 1fb: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      // 1fe: aload 1
      // 1ff: ifnull 210
      // 202: aload 1
      // 203: astore 2
      // 204: aload 1
      // 205: invokeinterface android/database/Cursor.close ()V 1
      // 20a: aload 8
      // 20c: astore 1
      // 20d: goto 210
      // 210: aload 1
      // 211: astore 6
      // 213: aload 0
      // 214: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 217: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.deleteDatabase ()V
      // 21a: aload 1
      // 21b: ifnull 224
      // 21e: aload 1
      // 21f: invokeinterface android/database/Cursor.close ()V 1
      // 224: aload 0
      // 225: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 228: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 22b: iload 3
      // 22c: ireturn
      // 22d: astore 1
      // 22e: aload 2
      // 22f: astore 6
      // 231: aload 6
      // 233: ifnull 23d
      // 236: aload 6
      // 238: invokeinterface android/database/Cursor.close ()V 1
      // 23d: aload 0
      // 23e: getfield com/mixpanel/android/mpmetrics/MPDbAdapter.mDb Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;
      // 241: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.close ()V
      // 244: aload 1
      // 245: athrow
      // 246: astore 2
      // 247: goto 0a1
   }

   private static class MPDatabaseHelper extends SQLiteOpenHelper {
      private final MPConfig mConfig;
      private final Context mContext;
      private final File mDatabaseFile;

      MPDatabaseHelper(Context var1, String var2, MPConfig var3) {
         super(var1, var2, null, 7);
         this.mDatabaseFile = var1.getDatabasePath(var2);
         this.mConfig = var3;
         this.mContext = var1;
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      private void migrateTableFrom4To5(SQLiteDatabase var1) {
         StringBuilder var6 = new StringBuilder("ALTER TABLE ");
         var6.append(MPDbAdapter.Table.EVENTS.getName());
         var6.append(" ADD COLUMN automatic_data INTEGER DEFAULT 0");
         var1.execSQL(var6.toString());
         var6 = new StringBuilder("ALTER TABLE ");
         var6.append(MPDbAdapter.Table.PEOPLE.getName());
         var6.append(" ADD COLUMN automatic_data INTEGER DEFAULT 0");
         var1.execSQL(var6.toString());
         var6 = new StringBuilder("ALTER TABLE ");
         var6.append(MPDbAdapter.Table.EVENTS.getName());
         var6.append(" ADD COLUMN token STRING NOT NULL DEFAULT ''");
         var1.execSQL(var6.toString());
         var6 = new StringBuilder("ALTER TABLE ");
         var6.append(MPDbAdapter.Table.PEOPLE.getName());
         var6.append(" ADD COLUMN token STRING NOT NULL DEFAULT ''");
         var1.execSQL(var6.toString());
         var6 = new StringBuilder("SELECT * FROM ");
         var6.append(MPDbAdapter.Table.EVENTS.getName());
         Cursor var40 = var1.rawQuery(var6.toString(), null);

         while (true) {
            boolean var5 = var40.moveToNext();
            byte var4 = 0;
            if (!var5) {
               var6 = new StringBuilder("SELECT * FROM ");
               var6.append(MPDbAdapter.Table.PEOPLE.getName());
               Cursor var42 = var1.rawQuery(var6.toString(), null);

               while (var42.moveToNext()) {
                  int var2;
                  label219: {
                     label138: {
                        try {
                           if (var42.getColumnIndex("data") >= 0) {
                              var2 = var42.getColumnIndex("data");
                              break label138;
                           }
                        } catch (JSONException var13) {
                           var2 = 0;
                           break label219;
                        }

                        var2 = 1;
                     }

                     String var85;
                     label128: {
                        try {
                           JSONObject var63 = new JSONObject(var42.getString(var2));
                           var85 = var63.getString("$token");
                           if (var42.getColumnIndex("_id") >= 0) {
                              var2 = var42.getColumnIndex("_id");
                              break label128;
                           }
                        } catch (JSONException var12) {
                           var2 = 0;
                           break label219;
                        }

                        var2 = 0;
                     }

                     try {
                        var2 = var42.getInt(var2);
                     } catch (JSONException var11) {
                        var2 = 0;
                        break label219;
                     }

                     try {
                        StringBuilder var65 = new StringBuilder();
                        var65.append("UPDATE ");
                        var65.append(MPDbAdapter.Table.PEOPLE.getName());
                        var65.append(" SET ");
                        var65.append("token");
                        var65.append(" = '");
                        var65.append(var85);
                        var65.append("' WHERE _id = ");
                        var65.append(var2);
                        var1.execSQL(var65.toString());
                        continue;
                     } catch (JSONException var10) {
                     }
                  }

                  String var86 = MPDbAdapter.Table.PEOPLE.getName();
                  StringBuilder var64 = new StringBuilder("_id = ");
                  var64.append(var2);
                  var1.delete(var86, var64.toString(), null);
               }

               return;
            }

            int var3;
            label207: {
               label215: {
                  try {
                     if (var40.getColumnIndex("data") < 0) {
                        break label215;
                     }
                  } catch (JSONException var31) {
                     String var7 = MPDbAdapter.Table.EVENTS.getName();
                     StringBuilder var8 = new StringBuilder("_id = ");
                     var8.append((int)var4);
                     var1.delete(var7, var8.toString(), null);
                     continue;
                  }

                  try {
                     var3 = var40.getColumnIndex("data");
                     break label207;
                  } catch (JSONException var29) {
                     String var43 = MPDbAdapter.Table.EVENTS.getName();
                     StringBuilder var66 = new StringBuilder("_id = ");
                     var66.append((int)var4);
                     var1.delete(var43, var66.toString(), null);
                     continue;
                  }
               }

               var3 = 1;
            }

            JSONObject var45;
            try {
               var45 = new JSONObject;
            } catch (JSONException var28) {
               String var44 = MPDbAdapter.Table.EVENTS.getName();
               StringBuilder var67 = new StringBuilder("_id = ");
               var67.append((int)var4);
               var1.delete(var44, var67.toString(), null);
               continue;
            }

            try {
               var45./* $VF: Unable to resugar constructor */<init>(var40.getString(var3));
            } catch (JSONException var27) {
               String var46 = MPDbAdapter.Table.EVENTS.getName();
               StringBuilder var68 = new StringBuilder("_id = ");
               var68.append((int)var4);
               var1.delete(var46, var68.toString(), null);
               continue;
            }

            try {
               var48 = var45.getJSONObject("properties").getString("token");
            } catch (JSONException var26) {
               String var47 = MPDbAdapter.Table.EVENTS.getName();
               StringBuilder var69 = new StringBuilder("_id = ");
               var69.append((int)var4);
               var1.delete(var47, var69.toString(), null);
               continue;
            }

            label199: {
               label216: {
                  try {
                     if (var40.getColumnIndex("_id") < 0) {
                        break label216;
                     }
                  } catch (JSONException var30) {
                     String var49 = MPDbAdapter.Table.EVENTS.getName();
                     StringBuilder var70 = new StringBuilder("_id = ");
                     var70.append((int)var4);
                     var1.delete(var49, var70.toString(), null);
                     continue;
                  }

                  try {
                     var3 = var40.getColumnIndex("_id");
                     break label199;
                  } catch (JSONException var25) {
                     String var50 = MPDbAdapter.Table.EVENTS.getName();
                     StringBuilder var71 = new StringBuilder("_id = ");
                     var71.append((int)var4);
                     var1.delete(var50, var71.toString(), null);
                     continue;
                  }
               }

               var3 = 0;
            }

            try {
               var3 = var40.getInt(var3);
            } catch (JSONException var24) {
               String var51 = MPDbAdapter.Table.EVENTS.getName();
               StringBuilder var72 = new StringBuilder("_id = ");
               var72.append((int)var4);
               var1.delete(var51, var72.toString(), null);
               continue;
            }

            StringBuilder var74;
            try {
               var74 = new StringBuilder;
            } catch (JSONException var23) {
               String var52 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var52, var74.toString(), null);
               continue;
            }

            try {
               var74./* $VF: Unable to resugar constructor */<init>();
            } catch (JSONException var22) {
               String var53 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var53, var74.toString(), null);
               continue;
            }

            try {
               var74.append("UPDATE ");
            } catch (JSONException var21) {
               String var54 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var54, var74.toString(), null);
               continue;
            }

            try {
               var74.append(MPDbAdapter.Table.EVENTS.getName());
            } catch (JSONException var20) {
               String var55 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var55, var74.toString(), null);
               continue;
            }

            try {
               var74.append(" SET ");
            } catch (JSONException var19) {
               String var56 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var56, var74.toString(), null);
               continue;
            }

            try {
               var74.append("token");
            } catch (JSONException var18) {
               String var57 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var57, var74.toString(), null);
               continue;
            }

            try {
               var74.append(" = '");
            } catch (JSONException var17) {
               String var58 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var58, var74.toString(), null);
               continue;
            }

            try {
               var74.append(var48);
            } catch (JSONException var16) {
               String var59 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var59, var74.toString(), null);
               continue;
            }

            try {
               var74.append("' WHERE _id = ");
            } catch (JSONException var15) {
               String var60 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var60, var74.toString(), null);
               continue;
            }

            try {
               var74.append(var3);
            } catch (JSONException var14) {
               String var61 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var61, var74.toString(), null);
               continue;
            }

            try {
               var1.execSQL(var74.toString());
            } catch (JSONException var9) {
               String var62 = MPDbAdapter.Table.EVENTS.getName();
               var74 = new StringBuilder("_id = ");
               var74.append(var3);
               var1.delete(var62, var74.toString(), null);
            }
         }
      }

      private void migrateTableFrom5To6(SQLiteDatabase var1) {
         var1.execSQL(MPDbAdapter.CREATE_GROUPS_TABLE);
         var1.execSQL(MPDbAdapter.GROUPS_TIME_INDEX);
      }

      private void migrateTableFrom6To7(SQLiteDatabase param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.RuntimeException: parsing failure!
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
         //
         // Bytecode:
         // 000: aload 1
         // 001: invokestatic com/mixpanel/android/mpmetrics/MPDbAdapter.access$300 ()Ljava/lang/String;
         // 004: invokevirtual android/database/sqlite/SQLiteDatabase.execSQL (Ljava/lang/String;)V
         // 007: aload 1
         // 008: invokestatic com/mixpanel/android/mpmetrics/MPDbAdapter.access$700 ()Ljava/lang/String;
         // 00b: invokevirtual android/database/sqlite/SQLiteDatabase.execSQL (Ljava/lang/String;)V
         // 00e: new java/io/File
         // 011: dup
         // 012: aload 0
         // 013: getfield com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.mContext Landroid/content/Context;
         // 016: invokevirtual android/content/Context.getApplicationInfo ()Landroid/content/pm/ApplicationInfo;
         // 019: getfield android/content/pm/ApplicationInfo.dataDir Ljava/lang/String;
         // 01c: ldc "shared_prefs"
         // 01e: invokespecial java/io/File.<init> (Ljava/lang/String;Ljava/lang/String;)V
         // 021: astore 6
         // 023: aload 6
         // 025: invokevirtual java/io/File.exists ()Z
         // 028: ifeq 128
         // 02b: aload 6
         // 02d: invokevirtual java/io/File.isDirectory ()Z
         // 030: ifeq 128
         // 033: aload 6
         // 035: new com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper$1
         // 038: dup
         // 039: aload 0
         // 03a: invokespecial com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper$1.<init> (Lcom/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper;)V
         // 03d: invokevirtual java/io/File.list (Ljava/io/FilenameFilter;)[Ljava/lang/String;
         // 040: astore 6
         // 042: aload 6
         // 044: arraylength
         // 045: istore 4
         // 047: bipush 0
         // 048: istore 2
         // 049: iload 2
         // 04a: iload 4
         // 04c: if_icmpge 128
         // 04f: aload 6
         // 051: iload 2
         // 052: aaload
         // 053: ldc "\\.xml"
         // 055: invokevirtual java/lang/String.split (Ljava/lang/String;)[Ljava/lang/String;
         // 058: bipush 0
         // 059: aaload
         // 05a: astore 7
         // 05c: aload 0
         // 05d: getfield com/mixpanel/android/mpmetrics/MPDbAdapter$MPDatabaseHelper.mContext Landroid/content/Context;
         // 060: aload 7
         // 062: bipush 0
         // 063: invokevirtual android/content/Context.getSharedPreferences (Ljava/lang/String;I)Landroid/content/SharedPreferences;
         // 066: astore 7
         // 068: aload 7
         // 06a: ldc "waiting_array"
         // 06c: aconst_null
         // 06d: invokeinterface android/content/SharedPreferences.getString (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String; 3
         // 072: astore 9
         // 074: aload 9
         // 076: ifnull 122
         // 079: new org/json/JSONArray
         // 07c: astore 8
         // 07e: aload 8
         // 080: aload 9
         // 082: invokespecial org/json/JSONArray.<init> (Ljava/lang/String;)V
         // 085: aload 1
         // 086: invokevirtual android/database/sqlite/SQLiteDatabase.beginTransaction ()V
         // 089: bipush 0
         // 08a: istore 3
         // 08b: aload 8
         // 08d: invokevirtual org/json/JSONArray.length ()I
         // 090: istore 5
         // 092: iload 3
         // 093: iload 5
         // 095: if_icmpge 0f4
         // 098: aload 8
         // 09a: iload 3
         // 09b: invokevirtual org/json/JSONArray.getJSONObject (I)Lorg/json/JSONObject;
         // 09e: astore 9
         // 0a0: aload 9
         // 0a2: ldc "$token"
         // 0a4: invokevirtual org/json/JSONObject.getString (Ljava/lang/String;)Ljava/lang/String;
         // 0a7: astore 10
         // 0a9: new android/content/ContentValues
         // 0ac: astore 11
         // 0ae: aload 11
         // 0b0: invokespecial android/content/ContentValues.<init> ()V
         // 0b3: aload 11
         // 0b5: ldc "data"
         // 0b7: aload 9
         // 0b9: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
         // 0bc: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
         // 0bf: aload 11
         // 0c1: ldc "created_at"
         // 0c3: invokestatic java/lang/System.currentTimeMillis ()J
         // 0c6: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
         // 0c9: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/Long;)V
         // 0cc: aload 11
         // 0ce: ldc "automatic_data"
         // 0d0: bipush 0
         // 0d1: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
         // 0d4: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/Boolean;)V
         // 0d7: aload 11
         // 0d9: ldc "token"
         // 0db: aload 10
         // 0dd: invokevirtual android/content/ContentValues.put (Ljava/lang/String;Ljava/lang/String;)V
         // 0e0: aload 1
         // 0e1: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.ANONYMOUS_PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
         // 0e4: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter$Table.getName ()Ljava/lang/String;
         // 0e7: aconst_null
         // 0e8: aload 11
         // 0ea: invokevirtual android/database/sqlite/SQLiteDatabase.insert (Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
         // 0ed: pop2
         // 0ee: iinc 3 1
         // 0f1: goto 08b
         // 0f4: aload 1
         // 0f5: invokevirtual android/database/sqlite/SQLiteDatabase.setTransactionSuccessful ()V
         // 0f8: aload 1
         // 0f9: invokevirtual android/database/sqlite/SQLiteDatabase.endTransaction ()V
         // 0fc: goto 108
         // 0ff: astore 8
         // 101: aload 1
         // 102: invokevirtual android/database/sqlite/SQLiteDatabase.endTransaction ()V
         // 105: aload 8
         // 107: athrow
         // 108: aload 7
         // 10a: invokeinterface android/content/SharedPreferences.edit ()Landroid/content/SharedPreferences$Editor; 1
         // 10f: astore 7
         // 111: aload 7
         // 113: ldc "waiting_array"
         // 115: invokeinterface android/content/SharedPreferences$Editor.remove (Ljava/lang/String;)Landroid/content/SharedPreferences$Editor; 2
         // 11a: pop
         // 11b: aload 7
         // 11d: invokeinterface android/content/SharedPreferences$Editor.apply ()V 1
         // 122: iinc 2 1
         // 125: goto 049
         // 128: return
         // 129: astore 8
         // 12b: goto 108
         // 12e: astore 9
         // 130: goto 0ee
      }

      public boolean aboveMemThreshold() {
         boolean var3 = this.mDatabaseFile.exists();
         boolean var2 = false;
         boolean var1 = var2;
         if (var3) {
            if (this.mDatabaseFile.length() <= Math.max(this.mDatabaseFile.getUsableSpace(), (long)this.mConfig.getMinimumDatabaseLimit())
               && this.mDatabaseFile.length() <= this.mConfig.getMaximumDatabaseLimit()) {
               return var2;
            }

            var1 = true;
         }

         return var1;
      }

      public void deleteDatabase() {
         this.close();
         this.mDatabaseFile.delete();
      }

      public void onCreate(SQLiteDatabase var1) {
         MPLog.v("MixpanelAPI.Database", "Creating a new Mixpanel events DB");
         var1.execSQL(MPDbAdapter.CREATE_EVENTS_TABLE);
         var1.execSQL(MPDbAdapter.CREATE_PEOPLE_TABLE);
         var1.execSQL(MPDbAdapter.CREATE_GROUPS_TABLE);
         var1.execSQL(MPDbAdapter.CREATE_ANONYMOUS_PEOPLE_TABLE);
         var1.execSQL(MPDbAdapter.EVENTS_TIME_INDEX);
         var1.execSQL(MPDbAdapter.PEOPLE_TIME_INDEX);
         var1.execSQL(MPDbAdapter.GROUPS_TIME_INDEX);
         var1.execSQL(MPDbAdapter.ANONYMOUS_PEOPLE_TIME_INDEX);
      }

      public void onUpgrade(SQLiteDatabase var1, int var2, int var3) {
         MPLog.v("MixpanelAPI.Database", "Upgrading app, replacing Mixpanel events DB");
         if (var2 >= 4 && var3 <= 7) {
            if (var2 == 4) {
               this.migrateTableFrom4To5(var1);
               this.migrateTableFrom5To6(var1);
               this.migrateTableFrom6To7(var1);
            }

            if (var2 == 5) {
               this.migrateTableFrom5To6(var1);
               this.migrateTableFrom6To7(var1);
            }

            if (var2 == 6) {
               this.migrateTableFrom6To7(var1);
            }
         } else {
            StringBuilder var4 = new StringBuilder("DROP TABLE IF EXISTS ");
            var4.append(MPDbAdapter.Table.EVENTS.getName());
            var1.execSQL(var4.toString());
            var4 = new StringBuilder("DROP TABLE IF EXISTS ");
            var4.append(MPDbAdapter.Table.PEOPLE.getName());
            var1.execSQL(var4.toString());
            var4 = new StringBuilder("DROP TABLE IF EXISTS ");
            var4.append(MPDbAdapter.Table.GROUPS.getName());
            var1.execSQL(var4.toString());
            var4 = new StringBuilder("DROP TABLE IF EXISTS ");
            var4.append(MPDbAdapter.Table.ANONYMOUS_PEOPLE.getName());
            var1.execSQL(var4.toString());
            var1.execSQL(MPDbAdapter.CREATE_EVENTS_TABLE);
            var1.execSQL(MPDbAdapter.CREATE_PEOPLE_TABLE);
            var1.execSQL(MPDbAdapter.CREATE_GROUPS_TABLE);
            var1.execSQL(MPDbAdapter.CREATE_ANONYMOUS_PEOPLE_TABLE);
            var1.execSQL(MPDbAdapter.EVENTS_TIME_INDEX);
            var1.execSQL(MPDbAdapter.PEOPLE_TIME_INDEX);
            var1.execSQL(MPDbAdapter.GROUPS_TIME_INDEX);
            var1.execSQL(MPDbAdapter.ANONYMOUS_PEOPLE_TIME_INDEX);
         }
      }
   }

   public static enum Table {
      ANONYMOUS_PEOPLE,
      EVENTS,
      GROUPS,
      PEOPLE;

      private static final MPDbAdapter.Table[] $VALUES;
      private final String mTableName;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         MPDbAdapter.Table var2 = new MPDbAdapter.Table("events");
         EVENTS = var2;
         MPDbAdapter.Table var1 = new MPDbAdapter.Table("people");
         PEOPLE = var1;
         MPDbAdapter.Table var3 = new MPDbAdapter.Table("anonymous_people");
         ANONYMOUS_PEOPLE = var3;
         MPDbAdapter.Table var0 = new MPDbAdapter.Table("groups");
         GROUPS = var0;
         $VALUES = new MPDbAdapter.Table[]{var2, var1, var3, var0};
      }

      private Table(String var3) {
         this.mTableName = var3;
      }

      public String getName() {
         return this.mTableName;
      }
   }
}
