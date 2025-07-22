package org.checkerframework.framework.qual;

public enum TypeUseLocation {
   ALL,
   CONSTRUCTOR_RESULT,
   EXCEPTION_PARAMETER,
   EXPLICIT_LOWER_BOUND,
   EXPLICIT_UPPER_BOUND,
   FIELD,
   IMPLICIT_LOWER_BOUND,
   IMPLICIT_UPPER_BOUND,
   LOCAL_VARIABLE,
   LOWER_BOUND,
   OTHERWISE,
   PARAMETER,
   RECEIVER,
   RESOURCE_VARIABLE,
   RETURN,
   UPPER_BOUND;
   private static final TypeUseLocation[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      TypeUseLocation var1 = new TypeUseLocation();
      FIELD = var1;
      TypeUseLocation var4 = new TypeUseLocation();
      LOCAL_VARIABLE = var4;
      TypeUseLocation var10 = new TypeUseLocation();
      RESOURCE_VARIABLE = var10;
      TypeUseLocation var15 = new TypeUseLocation();
      EXCEPTION_PARAMETER = var15;
      TypeUseLocation var2 = new TypeUseLocation();
      RECEIVER = var2;
      TypeUseLocation var5 = new TypeUseLocation();
      PARAMETER = var5;
      TypeUseLocation var9 = new TypeUseLocation();
      RETURN = var9;
      TypeUseLocation var6 = new TypeUseLocation();
      CONSTRUCTOR_RESULT = var6;
      TypeUseLocation var12 = new TypeUseLocation();
      LOWER_BOUND = var12;
      TypeUseLocation var11 = new TypeUseLocation();
      EXPLICIT_LOWER_BOUND = var11;
      TypeUseLocation var13 = new TypeUseLocation();
      IMPLICIT_LOWER_BOUND = var13;
      TypeUseLocation var0 = new TypeUseLocation();
      UPPER_BOUND = var0;
      TypeUseLocation var3 = new TypeUseLocation();
      EXPLICIT_UPPER_BOUND = var3;
      TypeUseLocation var7 = new TypeUseLocation();
      IMPLICIT_UPPER_BOUND = var7;
      TypeUseLocation var14 = new TypeUseLocation();
      OTHERWISE = var14;
      TypeUseLocation var8 = new TypeUseLocation();
      ALL = var8;
      $VALUES = new TypeUseLocation[]{var1, var4, var10, var15, var2, var5, var9, var6, var12, var11, var13, var0, var3, var7, var14, var8};
   }
}
