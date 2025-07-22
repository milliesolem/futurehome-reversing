package okio

import java.util.ArrayList
import kotlin.reflect.KClass
import kotlin.reflect.KClasses

public class FileMetadata(isRegularFile: Boolean = false,
   isDirectory: Boolean = false,
   symlinkTarget: Path? = null,
   size: Long? = null,
   createdAtMillis: Long? = null,
   lastModifiedAtMillis: Long? = null,
   lastAccessedAtMillis: Long? = null,
   extras: Map<KClass<*>, Any> = MapsKt.emptyMap()
) {
   public final val createdAtMillis: Long?
   public final val extras: Map<KClass<*>, Any>
   public final val isDirectory: Boolean
   public final val isRegularFile: Boolean
   public final val lastAccessedAtMillis: Long?
   public final val lastModifiedAtMillis: Long?
   public final val size: Long?
   public final val symlinkTarget: Path?

   fun FileMetadata() {
      this(false, false, null, null, null, null, null, null, 255, null);
   }

   init {
      this.isRegularFile = var1;
      this.isDirectory = var2;
      this.symlinkTarget = var3;
      this.size = var4;
      this.createdAtMillis = var5;
      this.lastModifiedAtMillis = var6;
      this.lastAccessedAtMillis = var7;
      this.extras = MapsKt.toMap(var8);
   }

   public fun copy(
      isRegularFile: Boolean = var0.isRegularFile,
      isDirectory: Boolean = var0.isDirectory,
      symlinkTarget: Path? = var0.symlinkTarget,
      size: Long? = var0.size,
      createdAtMillis: Long? = var0.createdAtMillis,
      lastModifiedAtMillis: Long? = var0.lastModifiedAtMillis,
      lastAccessedAtMillis: Long? = var0.lastAccessedAtMillis,
      extras: Map<KClass<*>, Any> = var0.extras
   ): FileMetadata {
      return new FileMetadata(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public fun <T : Any> extra(type: KClass<out T>): T? {
      val var2: Any = this.extras.get(var1);
      return (T)(if (var2 == null) null else KClasses.cast(var1, var2));
   }

   public override fun toString(): String {
      val var1: java.util.List = new ArrayList();
      if (this.isRegularFile) {
         var1.add("isRegularFile");
      }

      if (this.isDirectory) {
         var1.add("isDirectory");
      }

      if (this.size != null) {
         val var3: java.util.Collection = var1;
         val var2: StringBuilder = new StringBuilder("byteCount=");
         var2.append(this.size);
         var3.add(var2.toString());
      }

      if (this.createdAtMillis != null) {
         val var8: java.util.Collection = var1;
         val var4: StringBuilder = new StringBuilder("createdAt=");
         var4.append(this.createdAtMillis);
         var8.add(var4.toString());
      }

      if (this.lastModifiedAtMillis != null) {
         val var5: java.util.Collection = var1;
         val var9: StringBuilder = new StringBuilder("lastModifiedAt=");
         var9.append(this.lastModifiedAtMillis);
         var5.add(var9.toString());
      }

      if (this.lastAccessedAtMillis != null) {
         val var6: java.util.Collection = var1;
         val var10: StringBuilder = new StringBuilder("lastAccessedAt=");
         var10.append(this.lastAccessedAtMillis);
         var6.add(var10.toString());
      }

      if (!this.extras.isEmpty()) {
         val var7: java.util.Collection = var1;
         val var11: StringBuilder = new StringBuilder("extras=");
         var11.append(this.extras);
         var7.add(var11.toString());
      }

      return CollectionsKt.joinToString$default(var1, ", ", "FileMetadata(", ")", 0, null, null, 56, null);
   }
}
