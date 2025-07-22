package kotlin.text

import kotlin.contracts.InvocationKind

internal class StringsKt__StringBuilderKt : StringsKt__StringBuilderJVMKt {
   open fun StringsKt__StringBuilderKt() {
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "Use append(value: Any?) instead", replaceWith = @ReplaceWith(expression = "append(value = obj)", imports = []))
   @JvmStatic
   public inline fun StringBuilder.append(obj: Any?): StringBuilder {
      var0.append(var1);
      return var0;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use appendRange instead.", replaceWith = @ReplaceWith(expression = "this.appendRange(str, offset, offset + len)", imports = []))
   @JvmStatic
   public inline fun StringBuilder.append(str: CharArray, offset: Int, len: Int): StringBuilder {
      throw new NotImplementedError(null, 1, null);
   }

   @JvmStatic
   public fun StringBuilder.append(vararg value: Any?): StringBuilder {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0.append(var1[var2]);
      }

      return var0;
   }

   @JvmStatic
   public fun StringBuilder.append(vararg value: String?): StringBuilder {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0.append(var1[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(): StringBuilder {
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: Char): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: CharSequence?): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: Any?): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: String?): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: Boolean): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: CharArray): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun buildString(capacity: Int, builderAction: (StringBuilder) -> Unit): String {
      contract {
         callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE)
      }

      val var2: StringBuilder = new StringBuilder(var0);
      var1.invoke(var2);
      return var2.toString();
   }

   @JvmStatic
   public inline fun buildString(builderAction: (StringBuilder) -> Unit): String {
      contract {
         callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE)
      }

      val var1: StringBuilder = new StringBuilder();
      var0.invoke(var1);
      return var1.toString();
   }
}
