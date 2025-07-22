package kotlin.text

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class RegexOption(value: Int, mask: Int = var3) : FlagEnum {
   CANON_EQ(128, 0, 2, null),
   COMMENTS(4, 0, 2, null),
   DOT_MATCHES_ALL(32, 0, 2, null),
   IGNORE_CASE(2, 0, 2, null),
   LITERAL(16, 0, 2, null),
   MULTILINE(8, 0, 2, null),
   UNIX_LINES(1, 0, 2, null)
   public open val value: Int
   public open val mask: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private RegexOption[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<RegexOption> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.value = var3;
      this.mask = var4;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<RegexOption> {
      return $ENTRIES;
   }
}
