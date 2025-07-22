package kotlin.text

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class CharCategory(value: Int, code: String) {
   COMBINING_SPACING_MARK(8, "Mc"),
   CONNECTOR_PUNCTUATION(23, "Pc"),
   CONTROL(15, "Cc"),
   CURRENCY_SYMBOL(26, "Sc"),
   DASH_PUNCTUATION(20, "Pd"),
   DECIMAL_DIGIT_NUMBER(9, "Nd"),
   ENCLOSING_MARK(7, "Me"),
   END_PUNCTUATION(22, "Pe"),
   FINAL_QUOTE_PUNCTUATION(30, "Pf"),
   FORMAT(16, "Cf"),
   INITIAL_QUOTE_PUNCTUATION(29, "Pi"),
   LETTER_NUMBER(10, "Nl"),
   LINE_SEPARATOR(13, "Zl"),
   LOWERCASE_LETTER(2, "Ll"),
   MATH_SYMBOL(25, "Sm"),
   MODIFIER_LETTER(4, "Lm"),
   MODIFIER_SYMBOL(27, "Sk"),
   NON_SPACING_MARK(6, "Mn"),
   OTHER_LETTER(5, "Lo"),
   OTHER_NUMBER(11, "No"),
   OTHER_PUNCTUATION(24, "Po"),
   OTHER_SYMBOL(28, "So"),
   PARAGRAPH_SEPARATOR(14, "Zp"),
   PRIVATE_USE(18, "Co"),
   SPACE_SEPARATOR(12, "Zs"),
   START_PUNCTUATION(21, "Ps"),
   SURROGATE(19, "Cs"),
   TITLECASE_LETTER(3, "Lt"),
   UNASSIGNED(0, "Cn"),
   UPPERCASE_LETTER(1, "Lu")
   public final val value: Int
   public final val code: String
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private CharCategory[] $VALUES;
   @JvmStatic
   public CharCategory.Companion Companion = new CharCategory.Companion(null);

   @JvmStatic
   fun {
      val var0: Array<CharCategory> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.value = var3;
      this.code = var4;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<CharCategory> {
      return $ENTRIES;
   }

   public operator fun contains(char: Char): Boolean {
      val var2: Boolean;
      if (Character.getType(var1) == this.value) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public companion object {
      public fun valueOf(category: Int): CharCategory {
         val var2: CharCategory;
         if (var1 >= 0 && var1 < 17) {
            var2 = CharCategory.getEntries().get(var1);
         } else {
            if (18 > var1 || var1 >= 31) {
               val var3: StringBuilder = new StringBuilder("Category #");
               var3.append(var1);
               var3.append(" is not defined.");
               throw new IllegalArgumentException(var3.toString());
            }

            var2 = CharCategory.getEntries().get(var1 - 1);
         }

         return var2;
      }
   }
}
