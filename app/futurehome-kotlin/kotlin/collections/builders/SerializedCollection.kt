package kotlin.collections.builders

import java.io.Externalizable
import java.io.InvalidObjectException
import java.io.ObjectInput
import java.io.ObjectOutput

internal class SerializedCollection(collection: Collection<*>, tag: Int) : Externalizable {
   private final var collection: Collection<*>
   private final val tag: Int

   public constructor() : this(CollectionsKt.emptyList(), 0)
   init {
      this.collection = var1;
      this.tag = var2;
   }

   private fun readResolve(): Any {
      return this.collection;
   }

   public override fun readExternal(input: ObjectInput) {
      var var2: Int = var1.readByte();
      val var5: Int = var2 and 1;
      if ((var2 and -2) == 0) {
         val var4: Int = var1.readInt();
         if (var4 < 0) {
            val var10: StringBuilder = new StringBuilder("Illegal size value: ");
            var10.append(var4);
            var10.append('.');
            throw new InvalidObjectException(var10.toString());
         } else {
            var2 = 0;
            val var8: java.util.Collection;
            if (var5 != 0) {
               if (var5 != 1) {
                  val var9: StringBuilder = new StringBuilder("Unsupported collection type tag: ");
                  var9.append(var5);
                  var9.append('.');
                  throw new InvalidObjectException(var9.toString());
               }

               val var6: java.util.Set;
               for (var6 = SetsKt.createSetBuilder(var4); var2 < var4; var2++) {
                  var6.add(var1.readObject());
               }

               var8 = SetsKt.build(var6);
            } else {
               val var13: java.util.List = CollectionsKt.createListBuilder(var4);

               for (int var12 = 0; var12 < var4; var12++) {
                  var13.add(var1.readObject());
               }

               var8 = CollectionsKt.build(var13);
            }

            this.collection = var8;
         }
      } else {
         val var7: StringBuilder = new StringBuilder("Unsupported flags value: ");
         var7.append(var2);
         var7.append('.');
         throw new InvalidObjectException(var7.toString());
      }
   }

   public override fun writeExternal(output: ObjectOutput) {
      var1.writeByte(this.tag);
      var1.writeInt(this.collection.size());
      val var2: java.util.Iterator = this.collection.iterator();

      while (var2.hasNext()) {
         var1.writeObject(var2.next());
      }
   }

   public companion object {
      private const val serialVersionUID: Long
      public const val tagList: Int
      public const val tagSet: Int
   }
}
