package io.flutter.plugin.mouse;

import android.view.PointerIcon;
import io.flutter.embedding.engine.systemchannels.MouseCursorChannel;
import j..util.Map._EL;
import java.util.HashMap;

public class MouseCursorPlugin {
   private static HashMap<String, Integer> systemCursorConstants;
   private final MouseCursorPlugin.MouseCursorViewDelegate mView;
   private final MouseCursorChannel mouseCursorChannel;

   public MouseCursorPlugin(MouseCursorPlugin.MouseCursorViewDelegate var1, MouseCursorChannel var2) {
      this.mView = var1;
      this.mouseCursorChannel = var2;
      var2.setMethodHandler(new MouseCursorChannel.MouseCursorMethodHandler(this) {
         final MouseCursorPlugin this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void activateSystemCursor(String var1) {
            this.this$0.mView.setPointerIcon(this.this$0.resolveSystemCursor(var1));
         }
      });
   }

   private PointerIcon resolveSystemCursor(String var1) {
      if (systemCursorConstants == null) {
         systemCursorConstants = new HashMap<String, Integer>(this) {
            private static final long serialVersionUID = 1L;
            final MouseCursorPlugin this$0;

            {
               this.this$0 = var1;
               this.put("alias", 1010);
               Integer var2 = 1013;
               this.put("allScroll", var2);
               this.put("basic", 1000);
               this.put("cell", 1006);
               this.put("click", 1002);
               this.put("contextMenu", 1001);
               this.put("copy", 1011);
               Integer var5 = 1012;
               this.put("forbidden", var5);
               this.put("grab", 1020);
               this.put("grabbing", 1021);
               this.put("help", 1003);
               this.put("move", var2);
               this.put("none", 0);
               this.put("noDrop", var5);
               this.put("precise", 1007);
               this.put("text", 1008);
               Integer var4 = 1014;
               this.put("resizeColumn", var4);
               var2 = 1015;
               this.put("resizeDown", var2);
               Integer var3 = 1016;
               this.put("resizeUpLeft", var3);
               Integer var6 = 1017;
               this.put("resizeDownRight", var6);
               this.put("resizeLeft", var4);
               this.put("resizeLeftRight", var4);
               this.put("resizeRight", var4);
               this.put("resizeRow", var2);
               this.put("resizeUp", var2);
               this.put("resizeUpDown", var2);
               this.put("resizeUpLeft", var6);
               this.put("resizeUpRight", var3);
               this.put("resizeUpLeftDownRight", var6);
               this.put("resizeUpRightDownLeft", var3);
               this.put("verticalText", 1009);
               this.put("wait", 1004);
               this.put("zoomIn", 1018);
               this.put("zoomOut", 1019);
            }
         };
      }

      int var2 = (Integer)_EL.getOrDefault(systemCursorConstants, var1, 1000);
      return this.mView.getSystemPointerIcon(var2);
   }

   public void destroy() {
      this.mouseCursorChannel.setMethodHandler(null);
   }

   public interface MouseCursorViewDelegate {
      PointerIcon getSystemPointerIcon(int var1);

      void setPointerIcon(PointerIcon var1);
   }
}
