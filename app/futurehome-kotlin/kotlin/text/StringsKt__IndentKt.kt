package kotlin.text

import java.util.ArrayList
import kotlin.internal.PlatformImplementationsKt
import kotlin.jvm.functions.Function1

internal class StringsKt__IndentKt : StringsKt__AppendableKt {
   open fun StringsKt__IndentKt() {
   }

   @JvmStatic
   private fun getIndentFunction(indent: String): (String) -> String {
      val var1: Any;
      if (var0.length() == 0) {
         var1 = new StringsKt__IndentKt$$ExternalSyntheticLambda1();
      } else {
         var1 = new StringsKt__IndentKt$$ExternalSyntheticLambda2(var0);
      }

      return (Function1<java.lang.String, java.lang.String>)var1;
   }

   @JvmStatic
   fun `getIndentFunction$lambda$8$StringsKt__IndentKt`(var0: java.lang.String): java.lang.String {
      return var0;
   }

   @JvmStatic
   fun `getIndentFunction$lambda$9$StringsKt__IndentKt`(var0: java.lang.String, var1: java.lang.String): java.lang.String {
      val var2: StringBuilder = new StringBuilder();
      var2.append(var0);
      var2.append(var1);
      return var2.toString();
   }

   @JvmStatic
   private fun String.indentWidth(): Int {
      val var3: java.lang.CharSequence = var0;
      var var2: Int = var0.length();
      var var1: Int = 0;

      while (true) {
         if (var1 >= var2) {
            var1 = -1;
            break;
         }

         if (!CharsKt.isWhitespace(var3.charAt(var1))) {
            break;
         }

         var1++;
      }

      var2 = var1;
      if (var1 == -1) {
         var2 = var0.length();
      }

      return var2;
   }

   @JvmStatic
   public fun String.prependIndent(indent: String = "    "): String {
      return SequencesKt.joinToString$default(
         SequencesKt.map(StringsKt.lineSequence(var0), new StringsKt__IndentKt$$ExternalSyntheticLambda0(var1)), "\n", null, null, 0, null, null, 62, null
      );
   }

   @JvmStatic
   fun `prependIndent$lambda$5$StringsKt__IndentKt`(var0: java.lang.String, var1: java.lang.String): java.lang.String {
      if (StringsKt.isBlank(var1)) {
         if (var1.length() >= var0.length()) {
            var0 = var1;
         }
      } else {
         val var2: StringBuilder = new StringBuilder();
         var2.append(var0);
         var2.append(var1);
         var0 = var2.toString();
      }

      return var0;
   }

   @JvmStatic
   private inline fun List<String>.reindent(resultSizeEstimate: Int, indentAddFunction: (String) -> String, indentCutFunction: (String) -> String?): String {
      val var5: Int = CollectionsKt.getLastIndex(var0);
      val var10: java.lang.Iterable = var0;
      val var7: java.util.Collection = new ArrayList();
      val var8: java.util.Iterator = var10.iterator();

      for (int var4 = 0; var8.hasNext(); var4++) {
         var var11: Any = var8.next();
         if (var4 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         val var6: java.lang.String = var11 as java.lang.String;
         if ((var4 == 0 || var4 == var5) && StringsKt.isBlank(var11 as java.lang.String)) {
            var11 = null;
         } else {
            val var9: java.lang.String = var3.invoke(var6) as java.lang.String;
            var11 = var6;
            if (var9 != null) {
               var11 = var2.invoke(var9) as java.lang.String;
               if (var11 == null) {
                  var11 = var6;
               }
            }
         }

         if (var11 != null) {
            var7.add(var11);
         }
      }

      return (CollectionsKt.joinTo$default(var7 as java.util.List, new StringBuilder(var1), "\n", null, null, 0, null, null, 124, null) as StringBuilder)
         .toString();
   }

   @JvmStatic
   public fun String.replaceIndent(newIndent: String = ""): String {
      val var10: java.util.List = StringsKt.lines(var0);
      val var8: java.lang.Iterable = var10;
      val var9: java.util.Collection = new ArrayList();

      for (Object var11 : var8) {
         if (!StringsKt.isBlank(var11 as java.lang.String)) {
            var9.add(var11);
         }
      }

      val var17: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var9 as java.util.List, 10));
      val var22: java.util.Iterator = (var9 as java.util.List).iterator();

      while (var22.hasNext()) {
         var17.add(indentWidth$StringsKt__IndentKt(var22.next() as java.lang.String));
      }

      val var18: Int = CollectionsKt.minOrNull(var17);
      var var3: Int = 0;
      val var2: Int;
      if (var18 != null) {
         var2 = var18;
      } else {
         var2 = 0;
      }

      val var6: Int = var0.length();
      val var7: Int = var1.length();
      val var5: Int = var10.size();
      val var19: Function1 = getIndentFunction$StringsKt__IndentKt(var1);
      val var4: Int = CollectionsKt.getLastIndex(var10);
      val var20: java.util.Collection = new ArrayList();

      for (Object var13 : var8) {
         if (var3 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var1 = var13 as java.lang.String;
         if ((var3 == 0 || var3 == var4) && StringsKt.isBlank(var13 as java.lang.String)) {
            var0 = null;
         } else {
            val var23: java.lang.String = StringsKt.drop(var1, var2);
            var0 = var1;
            if (var23 != null) {
               var0 = var19.invoke(var23) as java.lang.String;
               if (var0 == null) {
                  var0 = var1;
               }
            }
         }

         if (var0 != null) {
            var20.add(var0);
         }

         var3++;
      }

      return (CollectionsKt.joinTo$default(var20 as java.util.List, new StringBuilder(var6 + var7 * var5), "\n", null, null, 0, null, null, 124, null) as StringBuilder)
         .toString();
   }

   @JvmStatic
   public fun String.replaceIndentByMargin(newIndent: String = "", marginPrefix: String = "|"): String {
      if (StringsKt.isBlank(var2)) {
         throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
      } else {
         val var10: java.util.List = StringsKt.lines(var0);
         val var7: Int = var0.length();
         val var8: Int = var1.length();
         val var6: Int = var10.size();
         val var11: Function1 = getIndentFunction$StringsKt__IndentKt(var1);
         val var5: Int = CollectionsKt.getLastIndex(var10);
         val var14: java.lang.Iterable = var10;
         val var12: java.util.Collection = new ArrayList();
         val var13: java.util.Iterator = var14.iterator();

         for (int var4 = 0; var13.hasNext(); var4++) {
            var var15: Any = var13.next();
            if (var4 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            val var20: java.lang.String = var15 as java.lang.String;
            var1 = null;
            if ((var4 == 0 || var4 == var5) && StringsKt.isBlank(var20)) {
               var15 = null;
            } else {
               var15 = var20;
               var var9: Int = var20.length();
               var var3: Int = 0;

               while (true) {
                  if (var3 >= var9) {
                     var3 = -1;
                     break;
                  }

                  if (!CharsKt.isWhitespace(var15.charAt(var3))) {
                     break;
                  }

                  var3++;
               }

               if (var3 != -1 && StringsKt.startsWith$default(var20, var2, var3, false, 4, null)) {
                  var9 = var2.length();
                  var1 = var20.substring(var3 + var9);
               }

               var15 = var20;
               if (var1 != null) {
                  var15 = var11.invoke(var1) as java.lang.String;
                  if (var15 == null) {
                     var15 = var20;
                  }
               }
            }

            if (var15 != null) {
               var12.add(var15);
            }
         }

         return (CollectionsKt.joinTo$default(var12 as java.util.List, new StringBuilder(var7 + var8 * var6), "\n", null, null, 0, null, null, 124, null) as StringBuilder)
            .toString();
      }
   }

   @JvmStatic
   public fun String.trimIndent(): String {
      return StringsKt.replaceIndent(var0, "");
   }

   @JvmStatic
   public fun String.trimMargin(marginPrefix: String = "|"): String {
      return StringsKt.replaceIndentByMargin(var0, "", var1);
   }
}
