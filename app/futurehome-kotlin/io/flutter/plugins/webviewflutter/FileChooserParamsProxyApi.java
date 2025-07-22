package io.flutter.plugins.webviewflutter;

import android.webkit.WebChromeClient.FileChooserParams;
import java.util.Arrays;
import java.util.List;

public class FileChooserParamsProxyApi extends PigeonApiFileChooserParams {
   public FileChooserParamsProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public List<String> acceptTypes(FileChooserParams var1) {
      return Arrays.asList(var1.getAcceptTypes());
   }

   @Override
   public String filenameHint(FileChooserParams var1) {
      return var1.getFilenameHint();
   }

   @Override
   public boolean isCaptureEnabled(FileChooserParams var1) {
      return var1.isCaptureEnabled();
   }

   @Override
   public FileChooserMode mode(FileChooserParams var1) {
      int var2 = var1.getMode();
      if (var2 != 0) {
         if (var2 != 1) {
            return var2 != 3 ? FileChooserMode.UNKNOWN : FileChooserMode.SAVE;
         } else {
            return FileChooserMode.OPEN_MULTIPLE;
         }
      } else {
         return FileChooserMode.OPEN;
      }
   }
}
