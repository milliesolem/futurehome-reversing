package com.google.protobuf;

public abstract class ExtensionLite<ContainingType extends MessageLite, Type> {
   public abstract Type getDefaultValue();

   public abstract WireFormat.FieldType getLiteType();

   public abstract MessageLite getMessageDefaultInstance();

   public abstract int getNumber();

   boolean isLite() {
      return true;
   }

   public abstract boolean isRepeated();
}
