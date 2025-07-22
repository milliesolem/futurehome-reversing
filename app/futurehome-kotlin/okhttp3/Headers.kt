package okhttp3

import j..time.Instant
import j..util.DateRetargetClass
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Date
import java.util.Locale
import java.util.TreeMap
import java.util.TreeSet
import java.util.Map.Entry
import kotlin.jvm.internal.ArrayIteratorKt
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.StringCompanionObject
import kotlin.jvm.internal.markers.KMappedMarker
import okhttp3.internal.Util
import okhttp3.internal.http.DatesKt

public class Headers private constructor(vararg namesAndValues: Any) :
   java.lang.Iterable<Pair<? extends java.lang.String, ? extends java.lang.String>>,
   KMappedMarker {
   private final val namesAndValues: Array<String>

   public final val size: Int
      public final get() {
         return this.namesAndValues.length / 2;
      }


   init {
      this.namesAndValues = var1;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "size", imports = []))
   public fun size(): Int {
      return this.size();
   }

   public fun byteCount(): Long {
      var var3: Long = this.namesAndValues.length * 2;
      val var2: Int = this.namesAndValues.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3 += this.namesAndValues[var1].length();
      }

      return var3;
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is Headers && Arrays.equals((Object[])this.namesAndValues, (Object[])(var1 as Headers).namesAndValues)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public operator fun get(name: String): String? {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return Headers.Companion.access$get(Companion, this.namesAndValues, var1);
   }

   public fun getDate(name: String): Date? {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      var1 = this.get(var1);
      val var3: Date;
      if (var1 != null) {
         var3 = DatesKt.toHttpDateOrNull(var1);
      } else {
         var3 = null;
      }

      return var3;
   }

   public fun getInstant(name: String): Instant? {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      val var2: Date = this.getDate(var1);
      val var3: Instant;
      if (var2 != null) {
         var3 = DateRetargetClass.toInstant(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public override fun hashCode(): Int {
      return Arrays.hashCode((Object[])this.namesAndValues);
   }

   public override operator fun iterator(): Iterator<Pair<String, String>> {
      val var2: Int = this.size();
      val var3: Array<Pair> = new Pair[var2];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = TuplesKt.to(this.name(var1), this.value(var1));
      }

      return ArrayIteratorKt.iterator(var3);
   }

   public fun name(index: Int): String {
      return this.namesAndValues[var1 * 2];
   }

   public fun names(): Set<String> {
      val var3: TreeSet = new TreeSet<>(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
      val var2: Int = this.size();

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(this.name(var1));
      }

      val var4: java.util.Set = Collections.unmodifiableSet(var3);
      Intrinsics.checkExpressionValueIsNotNull(var4, "Collections.unmodifiableSet(result)");
      return var4;
   }

   public fun newBuilder(): okhttp3.Headers.Builder {
      val var1: Headers.Builder = new Headers.Builder();
      CollectionsKt.addAll(var1.getNamesAndValues$okhttp(), this.namesAndValues);
      return var1;
   }

   public fun toMultimap(): Map<String, List<String>> {
      val var5: TreeMap = new TreeMap<>(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
      val var2: Int = this.size();

      for (int var1 = 0; var1 < var2; var1++) {
         val var3: java.lang.String = this.name(var1);
         val var4: Locale = Locale.US;
         Intrinsics.checkExpressionValueIsNotNull(Locale.US, "Locale.US");
         if (var3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         val var6: java.lang.String = var3.toLowerCase(var4);
         Intrinsics.checkExpressionValueIsNotNull(var6, "(this as java.lang.String).toLowerCase(locale)");
         val var8: java.util.List = var5.get(var6) as java.util.List;
         var var7: java.util.List = var8;
         if (var8 == null) {
            var7 = new ArrayList(2);
            var5.put(var6, var7);
         }

         var7.add(this.value(var1));
      }

      return var5;
   }

   public override fun toString(): String {
      val var3: StringBuilder = new StringBuilder();
      val var2: Int = this.size();

      for (int var1 = 0; var1 < var2; var1++) {
         var3.append(this.name(var1));
         var3.append(": ");
         var3.append(this.value(var1));
         var3.append("\n");
      }

      val var4: java.lang.String = var3.toString();
      Intrinsics.checkExpressionValueIsNotNull(var4, "StringBuilder().apply(builderAction).toString()");
      return var4;
   }

   public fun value(index: Int): String {
      return this.namesAndValues[var1 * 2 + 1];
   }

   public fun values(name: String): List<String> {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      var var4: java.util.List = null;
      var var5: java.util.List = null as java.util.List;
      val var3: Int = this.size();
      var var2: Int = 0;

      while (var2 < var3) {
         var5 = var4;
         if (StringsKt.equals(var1, this.name(var2), true)) {
            var5 = var4;
            if (var4 == null) {
               var5 = new ArrayList(2);
            }

            var5.add(this.value(var2));
         }

         var2++;
         var4 = var5;
      }

      val var6: java.util.List;
      if (var4 != null) {
         var6 = Collections.unmodifiableList(var4);
         Intrinsics.checkExpressionValueIsNotNull(var6, "Collections.unmodifiableList(result)");
      } else {
         var6 = CollectionsKt.emptyList();
      }

      return var6;
   }

   public class Builder {
      internal final val namesAndValues: MutableList<String> = (new ArrayList(20)) as java.util.List

      public fun add(line: String): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "line");
         val var4: Headers.Builder = this;
         val var3: Int = StringsKt.indexOf$default(var1, ':', 0, false, 6, null);
         val var2: Boolean;
         if (var3 != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            val var7: java.lang.String = var1.substring(0, var3);
            Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            if (var7 != null) {
               val var8: java.lang.String = StringsKt.trim(var7).toString();
               var1 = var1.substring(var3 + 1);
               Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
               this.add(var8, var1);
               return this;
            } else {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
         } else {
            val var6: StringBuilder = new StringBuilder("Unexpected header: ");
            var6.append(var1);
            throw (new IllegalArgumentException(var6.toString().toString())) as java.lang.Throwable;
         }
      }

      public fun add(name: String, value: Instant): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Headers.Builder = this;
         this.add(var1, new Date(var2.toEpochMilli()));
         return this;
      }

      public fun add(name: String, value: String): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Headers.Builder = this;
         Headers.Companion.access$checkName(Headers.Companion, var1);
         Headers.Companion.access$checkValue(Headers.Companion, var2, var1);
         this.addLenient$okhttp(var1, var2);
         return this;
      }

      public fun add(name: String, value: Date): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Headers.Builder = this;
         this.add(var1, DatesKt.toHttpDateString(var2));
         return this;
      }

      public fun addAll(headers: Headers): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         val var4: Headers.Builder = this;
         val var3: Int = var1.size();

         for (int var2 = 0; var2 < var3; var2++) {
            this.addLenient$okhttp(var1.name(var2), var1.value(var2));
         }

         return this;
      }

      internal fun addLenient(line: String): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "line");
         val var3: Headers.Builder = this;
         val var2: Int = StringsKt.indexOf$default(var1, ':', 1, false, 4, null);
         if (var2 != -1) {
            val var6: java.lang.String = var1.substring(0, var2);
            Intrinsics.checkExpressionValueIsNotNull(var6, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            var1 = var1.substring(var2 + 1);
            Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
            this.addLenient$okhttp(var6, var1);
         } else if (var1.charAt(0) == ':') {
            var1 = var1.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
            this.addLenient$okhttp("", var1);
         } else {
            this.addLenient$okhttp("", var1);
         }

         return this;
      }

      internal fun addLenient(name: String, value: String): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Headers.Builder = this;
         this.namesAndValues.add(var1);
         this.namesAndValues.add(StringsKt.trim(var2).toString());
         return this;
      }

      public fun addUnsafeNonAscii(name: String, value: String): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Headers.Builder = this;
         Headers.Companion.access$checkName(Headers.Companion, var1);
         this.addLenient$okhttp(var1, var2);
         return this;
      }

      public fun build(): Headers {
         val var1: Array<Any> = this.namesAndValues.toArray(new java.lang.String[0]);
         if (var1 != null) {
            return new Headers(var1 as Array<java.lang.String>, null);
         } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }
      }

      public operator fun get(name: String): String? {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         val var5: IntProgression = RangesKt.step(RangesKt.downTo(this.namesAndValues.size() - 2, 0), 2);
         var var2: Int = var5.getFirst();
         val var4: Int = var5.getLast();
         val var3: Int = var5.getStep();
         if (if (var3 >= 0) var2 <= var4 else var2 >= var4) {
            while (true) {
               if (StringsKt.equals(var1, this.namesAndValues.get(var2), true)) {
                  return this.namesAndValues.get(var2 + 1);
               }

               if (var2 == var4) {
                  break;
               }

               var2 += var3;
            }
         }

         return null;
      }

      public fun removeAll(name: String): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         val var4: Headers.Builder = this;
         var var2: Int = 0;

         while (var2 < this.namesAndValues.size()) {
            var var3: Int = var2;
            if (StringsKt.equals(var1, this.namesAndValues.get(var2), true)) {
               this.namesAndValues.remove(var2);
               this.namesAndValues.remove(var2);
               var3 = var2 - 2;
            }

            var2 = var3 + 2;
         }

         return this;
      }

      public operator fun set(name: String, value: Instant): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Headers.Builder = this;
         return this.set(var1, new Date(var2.toEpochMilli()));
      }

      public operator fun set(name: String, value: String): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Headers.Builder = this;
         Headers.Companion.access$checkName(Headers.Companion, var1);
         Headers.Companion.access$checkValue(Headers.Companion, var2, var1);
         this.removeAll(var1);
         this.addLenient$okhttp(var1, var2);
         return this;
      }

      public operator fun set(name: String, value: Date): okhttp3.Headers.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Headers.Builder = this;
         this.set(var1, DatesKt.toHttpDateString(var2));
         return this;
      }
   }

   public companion object {
      private fun checkName(name: String) {
         val var2: Boolean;
         if (var1.length() > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            throw (new IllegalArgumentException("name is empty".toString())) as java.lang.Throwable;
         } else {
            val var4: Int = var1.length();

            for (int var6 = 0; var6 < var4; var6++) {
               val var5: Char = var1.charAt(var6);
               val var3: Boolean;
               if ('!' <= var5 && '~' >= var5) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (!var3) {
                  throw (
                     new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in header name: %s", Integer.valueOf(var5), var6, var1).toString())
                  ) as java.lang.Throwable;
               }
            }
         }
      }

      private fun checkValue(value: String, name: String) {
         val var5: Int = var1.length();

         for (int var3 = 0; var3 < var5; var3++) {
            val var6: Char = var1.charAt(var3);
            val var4: Boolean;
            if (var6 == '\t' || ' ' <= var6 && '~' >= var6) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (!var4) {
               throw (
                  new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in %s value: %s", Integer.valueOf(var6), var3, var2, var1).toString())
               ) as java.lang.Throwable;
            }
         }
      }

      private fun get(namesAndValues: Array<String>, name: String): String? {
         val var6: IntProgression = RangesKt.step(RangesKt.downTo(var1.length - 2, 0), 2);
         var var3: Int = var6.getFirst();
         val var4: Int = var6.getLast();
         val var5: Int = var6.getStep();
         if (if (var5 >= 0) var3 <= var4 else var3 >= var4) {
            while (true) {
               if (StringsKt.equals(var2, var1[var3], true)) {
                  return var1[var3 + 1];
               }

               if (var3 == var4) {
                  break;
               }

               var3 += var5;
            }
         }

         return null;
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "function moved to extension", replaceWith = @ReplaceWith(expression = "headers.toHeaders()", imports = []))
      public fun of(headers: Map<String, String>): Headers {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         val var2: Headers.Companion = this;
         return this.of(var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "function name changed", replaceWith = @ReplaceWith(expression = "headersOf(*namesAndValues)", imports = []))
      public fun of(vararg namesAndValues: String): Headers {
         Intrinsics.checkParameterIsNotNull(var1, "namesAndValues");
         val var2: Headers.Companion = this;
         return this.of(Arrays.copyOf(var1, var1.length));
      }

      public fun Map<String, String>.toHeaders(): Headers {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toHeaders");
         val var3: Array<java.lang.String> = new java.lang.String[var1.size() * 2];
         val var7: java.util.Iterator = var1.entrySet().iterator();

         for (int var2 = 0; var7.hasNext(); var2 += 2) {
            val var5: Entry = var7.next() as Entry;
            var var4: java.lang.String = var5.getKey() as java.lang.String;
            val var9: java.lang.String = var5.getValue() as java.lang.String;
            if (var4 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }

            var4 = StringsKt.trim(var4).toString();
            if (var9 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }

            val var6: java.lang.String = StringsKt.trim(var9).toString();
            val var10: Headers.Companion = this;
            this.checkName(var4);
            this.checkValue(var6, var4);
            var3[var2] = var4;
            var3[var2 + 1] = var6;
         }

         return new Headers(var3, null);
      }

      public fun headersOf(vararg namesAndValues: String): Headers {
         Intrinsics.checkParameterIsNotNull(var1, "namesAndValues");
         var var2: Boolean;
         if (var1.length % 2 == 0) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         if (!var2) {
            throw (new IllegalArgumentException("Expected alternating header names and values".toString())) as java.lang.Throwable;
         } else {
            val var8: Any = var1.clone();
            if (var8 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
            } else {
               val var9: Array<java.lang.String> = var8 as Array<java.lang.String>;
               var var4: Int = (var8 as Array<java.lang.String>).length;

               for (int var10 = 0; var10 < var4; var10++) {
                  val var5: java.lang.String = var9[var10];
                  val var3: Boolean;
                  if (var9[var10] != null) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  if (!var3) {
                     throw (new IllegalArgumentException("Headers cannot be null".toString())) as java.lang.Throwable;
                  }

                  if (var5 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                  }

                  var9[var10] = StringsKt.trim(var5).toString();
               }

               val var14: IntProgression = RangesKt.step(RangesKt.until(0, var9.length), 2);
               var2 = var14.getFirst();
               val var12: Int = var14.getLast();
               var4 = var14.getStep();
               if (if (var4 >= 0) var2 <= var12 else var2 >= var12) {
                  while (true) {
                     val var15: java.lang.String = var9[var2];
                     val var6: java.lang.String = var9[var2 + 1];
                     val var7: Headers.Companion = this;
                     this.checkName(var15);
                     this.checkValue(var6, var15);
                     if (var2 == var12) {
                        break;
                     }

                     var2 += var4;
                  }
               }

               return new Headers(var9, null);
            }
         }
      }
   }
}
