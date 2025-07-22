package org.checkerframework.framework.qual;

public enum TypeKind {
   ARRAY,
   BOOLEAN,
   BYTE,
   CHAR,
   DECLARED,
   DOUBLE,
   ERROR,
   EXECUTABLE,
   FLOAT,
   INT,
   INTERSECTION,
   LONG,
   NONE,
   NULL,
   OTHER,
   PACKAGE,
   SHORT,
   TYPEVAR,
   UNION,
   VOID,
   WILDCARD;
   private static final TypeKind[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      TypeKind var8 = new TypeKind();
      BOOLEAN = var8;
      TypeKind var11 = new TypeKind();
      BYTE = var11;
      TypeKind var0 = new TypeKind();
      SHORT = var0;
      TypeKind var5 = new TypeKind();
      INT = var5;
      TypeKind var17 = new TypeKind();
      LONG = var17;
      TypeKind var1 = new TypeKind();
      CHAR = var1;
      TypeKind var9 = new TypeKind();
      FLOAT = var9;
      TypeKind var7 = new TypeKind();
      DOUBLE = var7;
      TypeKind var10 = new TypeKind();
      VOID = var10;
      TypeKind var12 = new TypeKind();
      NONE = var12;
      TypeKind var3 = new TypeKind();
      NULL = var3;
      TypeKind var4 = new TypeKind();
      ARRAY = var4;
      TypeKind var20 = new TypeKind();
      DECLARED = var20;
      TypeKind var13 = new TypeKind();
      ERROR = var13;
      TypeKind var6 = new TypeKind();
      TYPEVAR = var6;
      TypeKind var2 = new TypeKind();
      WILDCARD = var2;
      TypeKind var14 = new TypeKind();
      PACKAGE = var14;
      TypeKind var18 = new TypeKind();
      EXECUTABLE = var18;
      TypeKind var19 = new TypeKind();
      OTHER = var19;
      TypeKind var15 = new TypeKind();
      UNION = var15;
      TypeKind var16 = new TypeKind();
      INTERSECTION = var16;
      $VALUES = new TypeKind[]{
         var8, var11, var0, var5, var17, var1, var9, var7, var10, var12, var3, var4, var20, var13, var6, var2, var14, var18, var19, var15, var16
      };
   }
}
