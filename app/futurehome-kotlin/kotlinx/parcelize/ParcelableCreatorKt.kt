package kotlinx.parcelize

import android.os.Parcelable
import android.os.Parcelable.Creator
import java.lang.reflect.Field
import kotlin.jvm.internal.Intrinsics

@JvmSynthetic
public inline fun <reified T : Parcelable> parcelableCreator(): Creator<T> {
   Intrinsics.reifiedOperationMarker(4, "T");
   val var0: Class = Parcelable::class.java;
   val var1: Field = Parcelable.class.getDeclaredField("CREATOR");
   var var2: Creator = null;
   var var4: Any = var1.get(null);
   if (var4 is Creator) {
      var2 = var4 as Creator;
   }

   if (var2 != null) {
      var4 = var2;
      return var2;
   } else {
      val var3: StringBuilder = new StringBuilder("Could not access CREATOR field in class ");
      Intrinsics.reifiedOperationMarker(4, "T");
      var3.append((Parcelable::class).getSimpleName());
      throw new IllegalArgumentException(var3.toString());
   }
}
