package kotlin.text

public class HexFormat internal constructor(upperCase: Boolean, bytes: kotlin.text.HexFormat.BytesHexFormat, number: kotlin.text.HexFormat.NumberHexFormat) {
   public final val upperCase: Boolean
   public final val bytes: kotlin.text.HexFormat.BytesHexFormat
   public final val number: kotlin.text.HexFormat.NumberHexFormat

   init {
      this.upperCase = var1;
      this.bytes = var2;
      this.number = var3;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append("HexFormat(\n    upperCase = ");
      var1.append(this.upperCase);
      var1.append(",\n    bytes = BytesHexFormat(\n");
      this.bytes.appendOptionsTo$kotlin_stdlib(var1, "        ").append('\n');
      var1.append("    ),");
      var1.append('\n');
      var1.append("    number = NumberHexFormat(");
      var1.append('\n');
      this.number.appendOptionsTo$kotlin_stdlib(var1, "        ").append('\n');
      var1.append("    )");
      var1.append('\n');
      var1.append(")");
      return var1.toString();
   }

   public class Builder  internal constructor() {
      public final var upperCase: Boolean = HexFormat.Companion.getDefault().getUpperCase()
         internal set

      public final val bytes: kotlin.text.HexFormat.BytesHexFormat.Builder
         public final get() {
            if (this._bytes == null) {
               this._bytes = new HexFormat.BytesHexFormat.Builder();
            }

            val var1: HexFormat.BytesHexFormat.Builder = this._bytes;
            return var1;
         }


      private final var _bytes: kotlin.text.HexFormat.BytesHexFormat.Builder?

      public final val number: kotlin.text.HexFormat.NumberHexFormat.Builder
         public final get() {
            if (this._number == null) {
               this._number = new HexFormat.NumberHexFormat.Builder();
            }

            val var1: HexFormat.NumberHexFormat.Builder = this._number;
            return var1;
         }


      private final var _number: kotlin.text.HexFormat.NumberHexFormat.Builder?

      public inline fun bytes(builderAction: (kotlin.text.HexFormat.BytesHexFormat.Builder) -> Unit) {
         var1.invoke(this.getBytes());
      }

      public inline fun number(builderAction: (kotlin.text.HexFormat.NumberHexFormat.Builder) -> Unit) {
         var1.invoke(this.getNumber());
      }

      internal fun build(): HexFormat {
         var var1: Boolean;
         var var5: HexFormat.BytesHexFormat;
         label19: {
            var1 = this.upperCase;
            if (this._bytes != null) {
               val var3: HexFormat.BytesHexFormat = this._bytes.build$kotlin_stdlib();
               var5 = var3;
               if (var3 != null) {
                  break label19;
               }
            }

            var5 = HexFormat.BytesHexFormat.Companion.getDefault$kotlin_stdlib();
         }

         if (this._number != null) {
            val var4: HexFormat.NumberHexFormat = this._number.build$kotlin_stdlib();
            if (var4 != null) {
               return new HexFormat(var1, var5, var4);
            }
         }

         return new HexFormat(var1, var5, HexFormat.NumberHexFormat.Companion.getDefault$kotlin_stdlib());
      }
   }

   public class BytesHexFormat internal constructor(bytesPerLine: Int,
      bytesPerGroup: Int,
      groupSeparator: String,
      byteSeparator: String,
      bytePrefix: String,
      byteSuffix: String
   ) {
      public final val bytesPerLine: Int
      public final val bytesPerGroup: Int
      public final val groupSeparator: String
      public final val byteSeparator: String
      public final val bytePrefix: String
      public final val byteSuffix: String
      internal final val noLineAndGroupSeparator: Boolean
      internal final val shortByteSeparatorNoPrefixAndSuffix: Boolean
      internal final val ignoreCase: Boolean

      init {
         this.bytesPerLine = var1;
         this.bytesPerGroup = var2;
         this.groupSeparator = var3;
         this.byteSeparator = var4;
         this.bytePrefix = var5;
         this.byteSuffix = var6;
         var var7: Boolean;
         if (var1 == Integer.MAX_VALUE && var2 == Integer.MAX_VALUE) {
            var7 = true;
         } else {
            var7 = false;
         }

         this.noLineAndGroupSeparator = var7;
         if (var5.length() == 0 && var6.length() == 0 && var4.length() <= 1) {
            var7 = true;
         } else {
            var7 = false;
         }

         label24: {
            this.shortByteSeparatorNoPrefixAndSuffix = var7;
            if (!HexFormatKt.access$isCaseSensitive(var3) && !HexFormatKt.access$isCaseSensitive(var4) && !HexFormatKt.access$isCaseSensitive(var5)) {
               var7 = false;
               if (!HexFormatKt.access$isCaseSensitive(var6)) {
                  break label24;
               }
            }

            var7 = true;
         }

         this.ignoreCase = var7;
      }

      internal fun appendOptionsTo(sb: StringBuilder, indent: String): StringBuilder {
         var1.append(var2);
         var1.append("bytesPerLine = ");
         var1.append(this.bytesPerLine);
         var1.append(",");
         var1.append('\n');
         var1.append(var2);
         var1.append("bytesPerGroup = ");
         var1.append(this.bytesPerGroup);
         var1.append(",");
         var1.append('\n');
         var1.append(var2);
         var1.append("groupSeparator = \"");
         var1.append(this.groupSeparator);
         var1.append("\",");
         var1.append('\n');
         var1.append(var2);
         var1.append("byteSeparator = \"");
         var1.append(this.byteSeparator);
         var1.append("\",");
         var1.append('\n');
         var1.append(var2);
         var1.append("bytePrefix = \"");
         var1.append(this.bytePrefix);
         var1.append("\",");
         var1.append('\n');
         var1.append(var2);
         var1.append("byteSuffix = \"");
         var1.append(this.byteSuffix);
         var1.append("\"");
         return var1;
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder();
         var1.append("BytesHexFormat(\n");
         this.appendOptionsTo$kotlin_stdlib(var1, "    ").append('\n');
         var1.append(")");
         return var1.toString();
      }

      public class Builder internal constructor() {
         public final var bytesPerLine: Int = HexFormat.BytesHexFormat.Companion.getDefault$kotlin_stdlib().getBytesPerLine()
            internal final set(value) {
               if (var1 > 0) {
                  this.bytesPerLine = var1;
               } else {
                  val var2: StringBuilder = new StringBuilder("Non-positive values are prohibited for bytesPerLine, but was ");
                  var2.append(var1);
                  throw new IllegalArgumentException(var2.toString());
               }
            }


         public final var bytesPerGroup: Int
            internal final set(value) {
               if (var1 > 0) {
                  this.bytesPerGroup = var1;
               } else {
                  val var2: StringBuilder = new StringBuilder("Non-positive values are prohibited for bytesPerGroup, but was ");
                  var2.append(var1);
                  throw new IllegalArgumentException(var2.toString());
               }
            }


         public final var groupSeparator: String
            internal set

         public final var byteSeparator: String
            internal final set(value) {
               if (!StringsKt.contains$default(var1, '\n', false, 2, null) && !StringsKt.contains$default(var1, '\r', false, 2, null)) {
                  this.byteSeparator = var1;
               } else {
                  val var3: StringBuilder = new StringBuilder("LF and CR characters are prohibited in byteSeparator, but was ");
                  var3.append(var1);
                  throw new IllegalArgumentException(var3.toString());
               }
            }


         public final var bytePrefix: String
            internal final set(value) {
               if (!StringsKt.contains$default(var1, '\n', false, 2, null) && !StringsKt.contains$default(var1, '\r', false, 2, null)) {
                  this.bytePrefix = var1;
               } else {
                  val var3: StringBuilder = new StringBuilder("LF and CR characters are prohibited in bytePrefix, but was ");
                  var3.append(var1);
                  throw new IllegalArgumentException(var3.toString());
               }
            }


         public final var byteSuffix: String
            internal final set(value) {
               if (!StringsKt.contains$default(var1, '\n', false, 2, null) && !StringsKt.contains$default(var1, '\r', false, 2, null)) {
                  this.byteSuffix = var1;
               } else {
                  val var3: StringBuilder = new StringBuilder("LF and CR characters are prohibited in byteSuffix, but was ");
                  var3.append(var1);
                  throw new IllegalArgumentException(var3.toString());
               }
            }


         internal fun build(): kotlin.text.HexFormat.BytesHexFormat {
            return new HexFormat.BytesHexFormat(
               this.bytesPerLine, this.bytesPerGroup, this.groupSeparator, this.byteSeparator, this.bytePrefix, this.byteSuffix
            );
         }
      }

      internal companion object {
         internal final val Default: kotlin.text.HexFormat.BytesHexFormat
      }
   }

   public companion object {
      public final val Default: HexFormat
      public final val UpperCase: HexFormat
   }

   public class NumberHexFormat internal constructor(prefix: String, suffix: String, removeLeadingZeros: Boolean, minLength: Int) {
      public final val prefix: String
      public final val suffix: String
      public final val removeLeadingZeros: Boolean
      public final val minLength: Int
      internal final val isDigitsOnly: Boolean
      internal final val isDigitsOnlyAndNoPadding: Boolean
      internal final val ignoreCase: Boolean

      init {
         this.prefix = var1;
         this.suffix = var2;
         this.removeLeadingZeros = var3;
         this.minLength = var4;
         if (var1.length() == 0 && var2.length() == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         this.isDigitsOnly = var3;
         if (var3 && var4 == 1) {
            var3 = true;
         } else {
            var3 = false;
         }

         label19: {
            this.isDigitsOnlyAndNoPadding = var3;
            if (!HexFormatKt.access$isCaseSensitive(var1)) {
               var3 = false;
               if (!HexFormatKt.access$isCaseSensitive(var2)) {
                  break label19;
               }
            }

            var3 = true;
         }

         this.ignoreCase = var3;
      }

      internal fun appendOptionsTo(sb: StringBuilder, indent: String): StringBuilder {
         var1.append(var2);
         var1.append("prefix = \"");
         var1.append(this.prefix);
         var1.append("\",");
         var1.append('\n');
         var1.append(var2);
         var1.append("suffix = \"");
         var1.append(this.suffix);
         var1.append("\",");
         var1.append('\n');
         var1.append(var2);
         var1.append("removeLeadingZeros = ");
         var1.append(this.removeLeadingZeros);
         var1.append(',');
         var1.append('\n');
         var1.append(var2);
         var1.append("minLength = ");
         var1.append(this.minLength);
         return var1;
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder();
         var1.append("NumberHexFormat(\n");
         this.appendOptionsTo$kotlin_stdlib(var1, "    ").append('\n');
         var1.append(")");
         return var1.toString();
      }

      public class Builder internal constructor() {
         public final var prefix: String = HexFormat.NumberHexFormat.Companion.getDefault$kotlin_stdlib().getPrefix()
            internal final set(value) {
               if (!StringsKt.contains$default(var1, '\n', false, 2, null) && !StringsKt.contains$default(var1, '\r', false, 2, null)) {
                  this.prefix = var1;
               } else {
                  val var3: StringBuilder = new StringBuilder("LF and CR characters are prohibited in prefix, but was ");
                  var3.append(var1);
                  throw new IllegalArgumentException(var3.toString());
               }
            }


         public final var suffix: String = HexFormat.NumberHexFormat.Companion.getDefault$kotlin_stdlib().getSuffix()
            internal final set(value) {
               if (!StringsKt.contains$default(var1, '\n', false, 2, null) && !StringsKt.contains$default(var1, '\r', false, 2, null)) {
                  this.suffix = var1;
               } else {
                  val var3: StringBuilder = new StringBuilder("LF and CR characters are prohibited in suffix, but was ");
                  var3.append(var1);
                  throw new IllegalArgumentException(var3.toString());
               }
            }


         public final var removeLeadingZeros: Boolean
            internal set

         public final var minLength: Int
            public final set(value) {
               if (var1 > 0) {
                  this.minLength = var1;
               } else {
                  val var2: StringBuilder = new StringBuilder("Non-positive values are prohibited for minLength, but was ");
                  var2.append(var1);
                  throw new IllegalArgumentException(var2.toString().toString());
               }
            }


         internal fun build(): kotlin.text.HexFormat.NumberHexFormat {
            return new HexFormat.NumberHexFormat(this.prefix, this.suffix, this.removeLeadingZeros, this.minLength);
         }
      }

      internal companion object {
         internal final val Default: kotlin.text.HexFormat.NumberHexFormat
      }
   }
}
