package kotlin.coroutines.jvm.internal

import java.lang.reflect.Field
import java.util.ArrayList

private const val COROUTINES_DEBUG_METADATA_VERSION: Int = 1

private fun checkDebugMetadataVersion(expected: Int, actual: Int) {
   if (var1 > var0) {
      val var2: StringBuilder = new StringBuilder("Debug metadata version mismatch. Expected: ");
      var2.append(var0);
      var2.append(", got ");
      var2.append(var1);
      var2.append(". Please update the Kotlin standard library.");
      throw new IllegalStateException(var2.toString().toString());
   }
}

private fun BaseContinuationImpl.getDebugMetadataAnnotation(): DebugMetadata? {
   return var0.getClass().getAnnotation(DebugMetadata.class);
}

private fun BaseContinuationImpl.getLabel(): Int {
   label29: {
      try {
         val var2: Field = var0.getClass().getDeclaredField("label");
         var2.setAccessible(true);
         val var5: Any = var2.get(var0);
         if (var5 is Int) {
            var6 = var5 as Int;
            break label29;
         }
      } catch (var4: Exception) {
         return -1;
      }

      var6 = null;
   }

   var var7: Int;
   if (var6 != null) {
      try {
         var7 = var6;
      } catch (var3: Exception) {
         return -1;
      }
   } else {
      var7 = 0;
   }

   return --var7;
}

internal fun BaseContinuationImpl.getSpilledVariableFieldMapping(): Array<String>? {
   val var5: DebugMetadata = getDebugMetadataAnnotation(var0);
   if (var5 == null) {
      return null;
   } else {
      checkDebugMetadataVersion(1, var5.v());
      val var4: ArrayList = new ArrayList();
      val var2: Int = getLabel(var0);
      val var6: IntArray = var5.i();
      val var3: Int = var6.length;

      for (int var1 = 0; var1 < var3; var1++) {
         if (var6[var1] == var2) {
            var4.add(var5.s()[var1]);
            var4.add(var5.n()[var1]);
         }
      }

      return var4.toArray(new java.lang.String[0]);
   }
}

internal fun BaseContinuationImpl.getStackTraceElementImpl(): StackTraceElement? {
   val var2: DebugMetadata = getDebugMetadataAnnotation(var0);
   if (var2 == null) {
      return null;
   } else {
      checkDebugMetadataVersion(1, var2.v());
      var var1: Int = getLabel(var0);
      if (var1 < 0) {
         var1 = -1;
      } else {
         var1 = var2.l()[var1];
      }

      val var4: java.lang.String = ModuleNameRetriever.INSTANCE.getModuleName(var0);
      val var5: java.lang.String;
      if (var4 == null) {
         var5 = var2.c();
      } else {
         val var3: StringBuilder = new StringBuilder();
         var3.append(var4);
         var3.append('/');
         var3.append(var2.c());
         var5 = var3.toString();
      }

      return new StackTraceElement(var5, var2.m(), var2.f(), var1);
   }
}
