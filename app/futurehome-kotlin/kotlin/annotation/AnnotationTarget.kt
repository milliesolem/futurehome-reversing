package kotlin.annotation

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class AnnotationTarget {
   ANNOTATION_CLASS,
   CLASS,
   CONSTRUCTOR,
   EXPRESSION,
   FIELD,
   FILE,
   FUNCTION,
   LOCAL_VARIABLE,
   PROPERTY,
   PROPERTY_GETTER,
   PROPERTY_SETTER,
   TYPE,
   TYPEALIAS,
   TYPE_PARAMETER,
   VALUE_PARAMETER   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private AnnotationTarget[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<AnnotationTarget> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<AnnotationTarget> {
      return $ENTRIES;
   }
}
