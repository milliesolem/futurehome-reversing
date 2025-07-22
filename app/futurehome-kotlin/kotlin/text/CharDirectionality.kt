package kotlin.text

import java.util.LinkedHashMap
import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class CharDirectionality(value: Int) {
   ARABIC_NUMBER(6),
   BOUNDARY_NEUTRAL(9),
   COMMON_NUMBER_SEPARATOR(7),
   EUROPEAN_NUMBER(3),
   EUROPEAN_NUMBER_SEPARATOR(4),
   EUROPEAN_NUMBER_TERMINATOR(5),
   LEFT_TO_RIGHT(0),
   LEFT_TO_RIGHT_EMBEDDING(14),
   LEFT_TO_RIGHT_OVERRIDE(15),
   NONSPACING_MARK(8),
   OTHER_NEUTRALS(13),
   PARAGRAPH_SEPARATOR(10),
   POP_DIRECTIONAL_FORMAT(18),
   RIGHT_TO_LEFT(1),
   RIGHT_TO_LEFT_ARABIC(2),
   RIGHT_TO_LEFT_EMBEDDING(16),
   RIGHT_TO_LEFT_OVERRIDE(17),
   SEGMENT_SEPARATOR(11),
   UNDEFINED(-1),
   WHITESPACE(12);


   public final val value: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private CharDirectionality[] $VALUES;
   @JvmStatic
   public CharDirectionality.Companion Companion = new CharDirectionality.Companion(null);

   @JvmStatic
   fun {
      val var0: Array<CharDirectionality> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.value = var3;
   }

   @JvmStatic
   fun `directionalityMap_delegate$lambda$1`(): java.util.Map {
      val var1: java.lang.Iterable = getEntries();
      val var0: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var1, 10)), 16));

      for (var1 : var1) {
         var0.put((var1 as CharDirectionality).value, var1);
      }

      return var0;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<CharDirectionality> {
      return $ENTRIES;
   }

   public companion object {
      private final val directionalityMap: Map<Int, CharDirectionality>
         private final get() {
            return CharDirectionality.access$getDirectionalityMap$delegate$cp().getValue() as MutableMap<Int, CharDirectionality>;
         }


      public fun valueOf(directionality: Int): CharDirectionality {
         val var2: CharDirectionality = this.getDirectionalityMap().get(var1);
         if (var2 != null) {
            return var2;
         } else {
            val var3: StringBuilder = new StringBuilder("Directionality #");
            var3.append(var1);
            var3.append(" is not defined.");
            throw new IllegalArgumentException(var3.toString());
         }
      }
   }
}
