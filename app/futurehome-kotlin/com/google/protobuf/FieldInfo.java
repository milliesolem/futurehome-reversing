package com.google.protobuf;

@CheckReturnValue
final class FieldInfo implements Comparable<FieldInfo> {
   private final java.lang.reflect.Field cachedSizeField;
   private final boolean enforceUtf8;
   private final Internal.EnumVerifier enumVerifier;
   private final java.lang.reflect.Field field;
   private final int fieldNumber;
   private final Object mapDefaultEntry;
   private final Class<?> messageClass;
   private final OneofInfo oneof;
   private final Class<?> oneofStoredType;
   private final java.lang.reflect.Field presenceField;
   private final int presenceMask;
   private final boolean required;
   private final FieldType type;

   private FieldInfo(
      java.lang.reflect.Field var1,
      int var2,
      FieldType var3,
      Class<?> var4,
      java.lang.reflect.Field var5,
      int var6,
      boolean var7,
      boolean var8,
      OneofInfo var9,
      Class<?> var10,
      Object var11,
      Internal.EnumVerifier var12,
      java.lang.reflect.Field var13
   ) {
      this.field = var1;
      this.type = var3;
      this.messageClass = var4;
      this.fieldNumber = var2;
      this.presenceField = var5;
      this.presenceMask = var6;
      this.required = var7;
      this.enforceUtf8 = var8;
      this.oneof = var9;
      this.oneofStoredType = var10;
      this.mapDefaultEntry = var11;
      this.enumVerifier = var12;
      this.cachedSizeField = var13;
   }

   private static void checkFieldNumber(int var0) {
      if (var0 <= 0) {
         StringBuilder var1 = new StringBuilder("fieldNumber must be positive: ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      }
   }

   public static FieldInfo forExplicitPresenceField(
      java.lang.reflect.Field var0, int var1, FieldType var2, java.lang.reflect.Field var3, int var4, boolean var5, Internal.EnumVerifier var6
   ) {
      checkFieldNumber(var1);
      Internal.checkNotNull(var0, "field");
      Internal.checkNotNull(var2, "fieldType");
      Internal.checkNotNull(var3, "presenceField");
      if (var3 != null && !isExactlyOneBitSet(var4)) {
         StringBuilder var7 = new StringBuilder("presenceMask must have exactly one bit set: ");
         var7.append(var4);
         throw new IllegalArgumentException(var7.toString());
      } else {
         return new FieldInfo(var0, var1, var2, null, var3, var4, false, var5, null, null, null, var6, null);
      }
   }

   public static FieldInfo forField(java.lang.reflect.Field var0, int var1, FieldType var2, boolean var3) {
      checkFieldNumber(var1);
      Internal.checkNotNull(var0, "field");
      Internal.checkNotNull(var2, "fieldType");
      if (var2 != FieldType.MESSAGE_LIST && var2 != FieldType.GROUP_LIST) {
         return new FieldInfo(var0, var1, var2, null, null, 0, false, var3, null, null, null, null, null);
      } else {
         throw new IllegalStateException("Shouldn't be called for repeated message fields.");
      }
   }

   public static FieldInfo forFieldWithEnumVerifier(java.lang.reflect.Field var0, int var1, FieldType var2, Internal.EnumVerifier var3) {
      checkFieldNumber(var1);
      Internal.checkNotNull(var0, "field");
      return new FieldInfo(var0, var1, var2, null, null, 0, false, false, null, null, null, var3, null);
   }

   public static FieldInfo forLegacyRequiredField(
      java.lang.reflect.Field var0, int var1, FieldType var2, java.lang.reflect.Field var3, int var4, boolean var5, Internal.EnumVerifier var6
   ) {
      checkFieldNumber(var1);
      Internal.checkNotNull(var0, "field");
      Internal.checkNotNull(var2, "fieldType");
      Internal.checkNotNull(var3, "presenceField");
      if (var3 != null && !isExactlyOneBitSet(var4)) {
         StringBuilder var7 = new StringBuilder("presenceMask must have exactly one bit set: ");
         var7.append(var4);
         throw new IllegalArgumentException(var7.toString());
      } else {
         return new FieldInfo(var0, var1, var2, null, var3, var4, true, var5, null, null, null, var6, null);
      }
   }

   public static FieldInfo forMapField(java.lang.reflect.Field var0, int var1, Object var2, Internal.EnumVerifier var3) {
      Internal.checkNotNull(var2, "mapDefaultEntry");
      checkFieldNumber(var1);
      Internal.checkNotNull(var0, "field");
      return new FieldInfo(var0, var1, FieldType.MAP, null, null, 0, false, true, null, null, var2, var3, null);
   }

   public static FieldInfo forOneofMemberField(int var0, FieldType var1, OneofInfo var2, Class<?> var3, boolean var4, Internal.EnumVerifier var5) {
      checkFieldNumber(var0);
      Internal.checkNotNull(var1, "fieldType");
      Internal.checkNotNull(var2, "oneof");
      Internal.checkNotNull(var3, "oneofStoredType");
      if (var1.isScalar()) {
         return new FieldInfo(null, var0, var1, null, null, 0, false, var4, var2, var3, null, var5, null);
      } else {
         StringBuilder var6 = new StringBuilder("Oneof is only supported for scalar fields. Field ");
         var6.append(var0);
         var6.append(" is of type ");
         var6.append(var1);
         throw new IllegalArgumentException(var6.toString());
      }
   }

   public static FieldInfo forPackedField(java.lang.reflect.Field var0, int var1, FieldType var2, java.lang.reflect.Field var3) {
      checkFieldNumber(var1);
      Internal.checkNotNull(var0, "field");
      Internal.checkNotNull(var2, "fieldType");
      if (var2 != FieldType.MESSAGE_LIST && var2 != FieldType.GROUP_LIST) {
         return new FieldInfo(var0, var1, var2, null, null, 0, false, false, null, null, null, null, var3);
      } else {
         throw new IllegalStateException("Shouldn't be called for repeated message fields.");
      }
   }

   public static FieldInfo forPackedFieldWithEnumVerifier(
      java.lang.reflect.Field var0, int var1, FieldType var2, Internal.EnumVerifier var3, java.lang.reflect.Field var4
   ) {
      checkFieldNumber(var1);
      Internal.checkNotNull(var0, "field");
      return new FieldInfo(var0, var1, var2, null, null, 0, false, false, null, null, null, var3, var4);
   }

   public static FieldInfo forRepeatedMessageField(java.lang.reflect.Field var0, int var1, FieldType var2, Class<?> var3) {
      checkFieldNumber(var1);
      Internal.checkNotNull(var0, "field");
      Internal.checkNotNull(var2, "fieldType");
      Internal.checkNotNull(var3, "messageClass");
      return new FieldInfo(var0, var1, var2, var3, null, 0, false, false, null, null, null, null, null);
   }

   private static boolean isExactlyOneBitSet(int var0) {
      boolean var1;
      if (var0 != 0 && (var0 & var0 - 1) == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static FieldInfo.Builder newBuilder() {
      return new FieldInfo.Builder();
   }

   public int compareTo(FieldInfo var1) {
      return this.fieldNumber - var1.fieldNumber;
   }

   public java.lang.reflect.Field getCachedSizeField() {
      return this.cachedSizeField;
   }

   public Internal.EnumVerifier getEnumVerifier() {
      return this.enumVerifier;
   }

   public java.lang.reflect.Field getField() {
      return this.field;
   }

   public int getFieldNumber() {
      return this.fieldNumber;
   }

   public Class<?> getListElementType() {
      return this.messageClass;
   }

   public Object getMapDefaultEntry() {
      return this.mapDefaultEntry;
   }

   public Class<?> getMessageFieldClass() {
      int var1 = <unrepresentable>.$SwitchMap$com$google$protobuf$FieldType[this.type.ordinal()];
      if (var1 == 1 || var1 == 2) {
         java.lang.reflect.Field var2 = this.field;
         Class var3;
         if (var2 != null) {
            var3 = var2.getType();
         } else {
            var3 = this.oneofStoredType;
         }

         return var3;
      } else {
         return var1 != 3 && var1 != 4 ? null : this.messageClass;
      }
   }

   public OneofInfo getOneof() {
      return this.oneof;
   }

   public Class<?> getOneofStoredType() {
      return this.oneofStoredType;
   }

   public java.lang.reflect.Field getPresenceField() {
      return this.presenceField;
   }

   public int getPresenceMask() {
      return this.presenceMask;
   }

   public FieldType getType() {
      return this.type;
   }

   public boolean isEnforceUtf8() {
      return this.enforceUtf8;
   }

   public boolean isRequired() {
      return this.required;
   }

   public static final class Builder {
      private java.lang.reflect.Field cachedSizeField;
      private boolean enforceUtf8;
      private Internal.EnumVerifier enumVerifier;
      private java.lang.reflect.Field field;
      private int fieldNumber;
      private Object mapDefaultEntry;
      private OneofInfo oneof;
      private Class<?> oneofStoredType;
      private java.lang.reflect.Field presenceField;
      private int presenceMask;
      private boolean required;
      private FieldType type;

      private Builder() {
      }

      public FieldInfo build() {
         OneofInfo var1 = this.oneof;
         if (var1 != null) {
            return FieldInfo.forOneofMemberField(this.fieldNumber, this.type, var1, this.oneofStoredType, this.enforceUtf8, this.enumVerifier);
         } else {
            var1 = (OneofInfo)this.mapDefaultEntry;
            if (var1 != null) {
               return FieldInfo.forMapField(this.field, this.fieldNumber, var1, this.enumVerifier);
            } else {
               java.lang.reflect.Field var4 = this.presenceField;
               if (var4 != null) {
                  return this.required
                     ? FieldInfo.forLegacyRequiredField(this.field, this.fieldNumber, this.type, var4, this.presenceMask, this.enforceUtf8, this.enumVerifier)
                     : FieldInfo.forExplicitPresenceField(this.field, this.fieldNumber, this.type, var4, this.presenceMask, this.enforceUtf8, this.enumVerifier);
               } else {
                  Internal.EnumVerifier var2 = this.enumVerifier;
                  if (var2 != null) {
                     java.lang.reflect.Field var6 = this.cachedSizeField;
                     return var6 == null
                        ? FieldInfo.forFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, var2)
                        : FieldInfo.forPackedFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, var2, var6);
                  } else {
                     java.lang.reflect.Field var5 = this.cachedSizeField;
                     return var5 == null
                        ? FieldInfo.forField(this.field, this.fieldNumber, this.type, this.enforceUtf8)
                        : FieldInfo.forPackedField(this.field, this.fieldNumber, this.type, var5);
                  }
               }
            }
         }
      }

      public FieldInfo.Builder withCachedSizeField(java.lang.reflect.Field var1) {
         this.cachedSizeField = var1;
         return this;
      }

      public FieldInfo.Builder withEnforceUtf8(boolean var1) {
         this.enforceUtf8 = var1;
         return this;
      }

      public FieldInfo.Builder withEnumVerifier(Internal.EnumVerifier var1) {
         this.enumVerifier = var1;
         return this;
      }

      public FieldInfo.Builder withField(java.lang.reflect.Field var1) {
         if (this.oneof == null) {
            this.field = var1;
            return this;
         } else {
            throw new IllegalStateException("Cannot set field when building a oneof.");
         }
      }

      public FieldInfo.Builder withFieldNumber(int var1) {
         this.fieldNumber = var1;
         return this;
      }

      public FieldInfo.Builder withMapDefaultEntry(Object var1) {
         this.mapDefaultEntry = var1;
         return this;
      }

      public FieldInfo.Builder withOneof(OneofInfo var1, Class<?> var2) {
         if (this.field == null && this.presenceField == null) {
            this.oneof = var1;
            this.oneofStoredType = var2;
            return this;
         } else {
            throw new IllegalStateException("Cannot set oneof when field or presenceField have been provided");
         }
      }

      public FieldInfo.Builder withPresence(java.lang.reflect.Field var1, int var2) {
         this.presenceField = Internal.checkNotNull(var1, "presenceField");
         this.presenceMask = var2;
         return this;
      }

      public FieldInfo.Builder withRequired(boolean var1) {
         this.required = var1;
         return this;
      }

      public FieldInfo.Builder withType(FieldType var1) {
         this.type = var1;
         return this;
      }
   }
}
