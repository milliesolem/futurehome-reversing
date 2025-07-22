package no.futurehome.futurehome_app.widget.models

private const val en: String = "en"
private const val fi: String = "fi"
private const val nb: String = "nb"
private const val da: String = "da"
private const val sv: String = "sv"

public fun languageForString(string: String?): Language {
   if (var0 != null) {
      val var1: Int = var0.hashCode();
      if (var1 != 3197) {
         if (var1 != 3241) {
            if (var1 != 3267) {
               if (var1 != 3508) {
                  if (var1 == 3683 && var0.equals("sv")) {
                     return Language.SV;
                  }
               } else if (var0.equals("nb")) {
                  return Language.NB;
               }
            } else if (var0.equals("fi")) {
               return Language.FI;
            }
         } else if (var0.equals("en")) {
            return Language.EN;
         }
      } else if (var0.equals("da")) {
         return Language.DA;
      }
   }

   return Language.EN;
}
