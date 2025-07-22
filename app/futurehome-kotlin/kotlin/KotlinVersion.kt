package kotlin

public class KotlinVersion(major: Int, minor: Int, patch: Int) : java.lang.Comparable<KotlinVersion> {
   public final val major: Int
   public final val minor: Int
   public final val patch: Int
   private final val version: Int

   public constructor(major: Int, minor: Int) : this(var1, var2, 0)
   init {
      this.major = var1;
      this.minor = var2;
      this.patch = var3;
      this.version = this.versionOf(var1, var2, var3);
   }

   private fun versionOf(major: Int, minor: Int, patch: Int): Int {
      if (var1 >= 0 && var1 < 256 && var2 >= 0 && var2 < 256 && var3 >= 0 && var3 < 256) {
         return (var1 shl 16) + (var2 shl 8) + var3;
      } else {
         val var4: StringBuilder = new StringBuilder("Version components are out of range: ");
         var4.append(var1);
         var4.append('.');
         var4.append(var2);
         var4.append('.');
         var4.append(var3);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   public open operator fun compareTo(other: KotlinVersion): Int {
      return this.version - var1.version;
   }

   public override operator fun equals(other: Any?): Boolean {
      var var2: Boolean = true;
      if (this === var1) {
         return true;
      } else {
         if (var1 is KotlinVersion) {
            var1 = var1;
         } else {
            var1 = null;
         }

         if (var1 == null) {
            return false;
         } else {
            if (this.version != var1.version) {
               var2 = false;
            }

            return var2;
         }
      }
   }

   public override fun hashCode(): Int {
      return this.version;
   }

   public fun isAtLeast(major: Int, minor: Int): Boolean {
      val var4: Boolean;
      if (this.major > var1 || this.major == var1 && this.minor >= var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public fun isAtLeast(major: Int, minor: Int, patch: Int): Boolean {
      if (this.major <= var1) {
         if (this.major != var1) {
            return false;
         }

         if (this.minor <= var2 && (this.minor != var2 || this.patch < var3)) {
            return false;
         }
      }

      return true;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.major);
      var1.append('.');
      var1.append(this.minor);
      var1.append('.');
      var1.append(this.patch);
      return var1.toString();
   }

   public companion object {
      public const val MAX_COMPONENT_VALUE: Int
      public final val CURRENT: KotlinVersion
   }
}
