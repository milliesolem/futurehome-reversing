package org.checkerframework.framework.qual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum LiteralKind {
   ALL,
   BOOLEAN,
   CHAR,
   DOUBLE,
   FLOAT,
   INT,
   LONG,
   NULL,
   PRIMITIVE,
   STRING;
   private static final LiteralKind[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      LiteralKind var3 = new LiteralKind();
      NULL = var3;
      LiteralKind var2 = new LiteralKind();
      INT = var2;
      LiteralKind var8 = new LiteralKind();
      LONG = var8;
      LiteralKind var4 = new LiteralKind();
      FLOAT = var4;
      LiteralKind var6 = new LiteralKind();
      DOUBLE = var6;
      LiteralKind var5 = new LiteralKind();
      BOOLEAN = var5;
      LiteralKind var9 = new LiteralKind();
      CHAR = var9;
      LiteralKind var0 = new LiteralKind();
      STRING = var0;
      LiteralKind var7 = new LiteralKind();
      ALL = var7;
      LiteralKind var1 = new LiteralKind();
      PRIMITIVE = var1;
      $VALUES = new LiteralKind[]{var3, var2, var8, var4, var6, var5, var9, var0, var7, var1};
   }

   public static List<LiteralKind> allLiteralKinds() {
      ArrayList var0 = new ArrayList<>(Arrays.asList(values()));
      var0.remove(ALL);
      var0.remove(PRIMITIVE);
      return var0;
   }

   public static List<LiteralKind> primitiveLiteralKinds() {
      return Arrays.asList(INT, LONG, FLOAT, DOUBLE, BOOLEAN, CHAR);
   }
}
