package kotlin.reflect

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public interface KParameter : KAnnotatedElement {
   public val index: Int
   public val name: String?
   public val type: KType
   public val kind: kotlin.reflect.KParameter.Kind
   public val isOptional: Boolean
   public val isVararg: Boolean

   // $VF: Class flags could not be determined
   internal class DefaultImpls

   public enum class Kind {
      EXTENSION_RECEIVER,
      INSTANCE,
      VALUE      @JvmStatic
      private EnumEntries $ENTRIES;
      @JvmStatic
      private KParameter.Kind[] $VALUES;

      @JvmStatic
      fun {
         val var0: Array<KParameter.Kind> = $values();
         $VALUES = var0;
         $ENTRIES = EnumEntriesKt.enumEntries(var0);
      }

      @JvmStatic
      fun getEntries(): EnumEntries<KParameter.Kind> {
         return $ENTRIES;
      }
   }
}
