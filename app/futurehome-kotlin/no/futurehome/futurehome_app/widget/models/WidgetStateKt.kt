package no.futurehome.futurehome_app.widget.models

public fun stringToWidgetState(string: String): WidgetState {
   switch (var0.hashCode()) {
      case -1956736692:
         if (var0.equals("NoUser")) {
            return WidgetState.NoUser;
         }
         break;
      case -531426133:
         if (var0.equals("NoSites")) {
            return WidgetState.NoSites;
         }
         break;
      case -498562831:
         if (var0.equals("OpenAppError")) {
            return WidgetState.OpenAppError;
         }
         break;
      case 75414356:
         if (var0.equals("NoHub")) {
            return WidgetState.NoHub;
         }
         break;
      case 495868287:
         if (var0.equals("PresentingModesAndShortcuts")) {
            return WidgetState.PresentingModesAndShortcuts;
         }
         break;
      case 661546213:
         if (var0.equals("PresentingSites")) {
            return WidgetState.PresentingSites;
         }
         break;
      case 803225503:
         if (var0.equals("NoConnection")) {
            return WidgetState.NoConnection;
         }
         break;
      case 1124884302:
         if (var0.equals("HubOffline")) {
            return WidgetState.HubOffline;
         }
         break;
      case 1625128480:
         if (var0.equals("GeneralError")) {
            return WidgetState.GeneralError;
         }
         break;
      case 2001303836:
         if (var0.equals("Loading")) {
            return WidgetState.Loading;
         }
      default:
   }

   return WidgetState.GeneralError;
}

public fun widgetStateToString(state: WidgetState): String {
   var var1: java.lang.String;
   switch (WidgetStateKt.WhenMappings.$EnumSwitchMapping$0[var0.ordinal()]) {
      case 1:
         var1 = "NoConnection";
         break;
      case 2:
         var1 = "OpenAppError";
         break;
      case 3:
         var1 = "NoUser";
         break;
      case 4:
         var1 = "GeneralError";
         break;
      case 5:
         var1 = "PresentingSites";
         break;
      case 6:
         var1 = "NoSites";
         break;
      case 7:
         var1 = "NoHub";
         break;
      case 8:
         var1 = "HubOffline";
         break;
      case 9:
         var1 = "PresentingModesAndShortcuts";
         break;
      case 10:
         var1 = "Loading";
         break;
      default:
         throw new NoWhenBranchMatchedException();
   }

   return var1;
}
// $VF: Class flags could not be determined
@JvmSynthetic
internal class WhenMappings {
   @JvmStatic
   public int[] $EnumSwitchMapping$0;

   @JvmStatic
   fun {
      val var0: IntArray = new int[WidgetState.values().length];

      try {
         var0[WidgetState.NoConnection.ordinal()] = 1;
      } catch (var11: NoSuchFieldError) {
      }

      try {
         var0[WidgetState.OpenAppError.ordinal()] = 2;
      } catch (var10: NoSuchFieldError) {
      }

      try {
         var0[WidgetState.NoUser.ordinal()] = 3;
      } catch (var9: NoSuchFieldError) {
      }

      try {
         var0[WidgetState.GeneralError.ordinal()] = 4;
      } catch (var8: NoSuchFieldError) {
      }

      try {
         var0[WidgetState.PresentingSites.ordinal()] = 5;
      } catch (var7: NoSuchFieldError) {
      }

      try {
         var0[WidgetState.NoSites.ordinal()] = 6;
      } catch (var6: NoSuchFieldError) {
      }

      try {
         var0[WidgetState.NoHub.ordinal()] = 7;
      } catch (var5: NoSuchFieldError) {
      }

      try {
         var0[WidgetState.HubOffline.ordinal()] = 8;
      } catch (var4: NoSuchFieldError) {
      }

      try {
         var0[WidgetState.PresentingModesAndShortcuts.ordinal()] = 9;
      } catch (var3: NoSuchFieldError) {
      }

      try {
         var0[WidgetState.Loading.ordinal()] = 10;
      } catch (var2: NoSuchFieldError) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
