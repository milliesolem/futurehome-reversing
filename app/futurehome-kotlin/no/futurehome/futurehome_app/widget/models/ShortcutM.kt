package no.futurehome.futurehome_app.widget.models

import android.content.Context

public data class ShortcutM(id: Int, name: String?) {
   public final val id: Int
   public final val name: String?

   init {
      this.id = var1;
      this.name = var2;
   }

   public operator fun component1(): Int {
      return this.id;
   }

   public operator fun component2(): String? {
      return this.name;
   }

   public fun copy(id: Int = var0.id, name: String? = var0.name): ShortcutM {
      return new ShortcutM(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is ShortcutM) {
         return false;
      } else {
         var1 = var1;
         if (this.id != var1.id) {
            return false;
         } else {
            return this.name == var1.name;
         }
      }
   }

   public fun getNameForLanguage(context: Context, language: Language): String {
      if (this.name != null) {
         return this.name;
      } else {
         var var3: Int = ShortcutM.WhenMappings.$EnumSwitchMapping$0[var2.ordinal()];
         val var6: java.lang.String;
         if (var3 != 1) {
            if (var3 != 2) {
               if (var3 != 3) {
                  if (var3 != 4) {
                     if (var3 != 5) {
                        throw new NoWhenBranchMatchedException();
                     }

                     val var11: java.lang.String = var1.getString(2131689625);
                     var3 = this.id;
                     val var5: StringBuilder = new StringBuilder();
                     var5.append(var11);
                     var5.append(" ");
                     var5.append(var3);
                     var6 = var5.toString();
                  } else {
                     val var12: java.lang.String = var1.getString(2131689540);
                     var3 = this.id;
                     val var7: StringBuilder = new StringBuilder();
                     var7.append(var12);
                     var7.append(" ");
                     var7.append(var3);
                     var6 = var7.toString();
                  }
               } else {
                  val var8: java.lang.String = var1.getString(2131689604);
                  var3 = this.id;
                  val var13: StringBuilder = new StringBuilder();
                  var13.append(var8);
                  var13.append(" ");
                  var13.append(var3);
                  var6 = var13.toString();
               }
            } else {
               val var14: java.lang.String = var1.getString(2131689578);
               var3 = this.id;
               val var9: StringBuilder = new StringBuilder();
               var9.append(var14);
               var9.append(" ");
               var9.append(var3);
               var6 = var9.toString();
            }
         } else {
            val var10: java.lang.String = var1.getString(2131689557);
            var3 = this.id;
            val var15: StringBuilder = new StringBuilder();
            var15.append(var10);
            var15.append(" ");
            var15.append(var3);
            var6 = var15.toString();
         }

         return var6;
      }
   }

   public override fun hashCode(): Int {
      val var1: Int;
      if (this.name == null) {
         var1 = 0;
      } else {
         var1 = this.name.hashCode();
      }

      return this.id * 31 + var1;
   }

   public override fun toString(): String {
      val var1: Int = this.id;
      val var3: java.lang.String = this.name;
      val var2: StringBuilder = new StringBuilder("ShortcutM(id=");
      var2.append(var1);
      var2.append(", name=");
      var2.append(var3);
      var2.append(")");
      return var2.toString();
   }
}
