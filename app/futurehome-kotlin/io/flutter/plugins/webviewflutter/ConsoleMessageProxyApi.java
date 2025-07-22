package io.flutter.plugins.webviewflutter;

import android.webkit.ConsoleMessage;

public class ConsoleMessageProxyApi extends PigeonApiConsoleMessage {
   public ConsoleMessageProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public ConsoleMessageLevel level(ConsoleMessage var1) {
      int var2 = <unrepresentable>.$SwitchMap$android$webkit$ConsoleMessage$MessageLevel[var1.messageLevel().ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  return var2 != 5 ? ConsoleMessageLevel.UNKNOWN : ConsoleMessageLevel.DEBUG;
               } else {
                  return ConsoleMessageLevel.ERROR;
               }
            } else {
               return ConsoleMessageLevel.WARNING;
            }
         } else {
            return ConsoleMessageLevel.LOG;
         }
      } else {
         return ConsoleMessageLevel.TIP;
      }
   }

   @Override
   public long lineNumber(ConsoleMessage var1) {
      return var1.lineNumber();
   }

   @Override
   public String message(ConsoleMessage var1) {
      return var1.message();
   }

   @Override
   public String sourceId(ConsoleMessage var1) {
      return var1.sourceId();
   }
}
