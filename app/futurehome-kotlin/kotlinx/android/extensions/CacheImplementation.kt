package kotlinx.android.extensions

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class CacheImplementation {
   HASH_MAP,
   NO_CACHE,
   SPARSE_ARRAY   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private CacheImplementation[] $VALUES;
   @JvmStatic
   public CacheImplementation.Companion Companion = new CacheImplementation.Companion(null);
   @JvmStatic
   private CacheImplementation DEFAULT;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @JvmStatic
   fun {
      val var1: CacheImplementation = new CacheImplementation();
      HASH_MAP = var1;
      val var0: Array<CacheImplementation> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
      DEFAULT = var1;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<CacheImplementation> {
      return $ENTRIES;
   }

   public companion object {
      public final val DEFAULT: CacheImplementation
   }
}
