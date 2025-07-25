package com.google.protobuf;

import java.util.List;

public interface FieldOrBuilder extends MessageLiteOrBuilder {
   Field.Cardinality getCardinality();

   int getCardinalityValue();

   String getDefaultValue();

   ByteString getDefaultValueBytes();

   String getJsonName();

   ByteString getJsonNameBytes();

   Field.Kind getKind();

   int getKindValue();

   String getName();

   ByteString getNameBytes();

   int getNumber();

   int getOneofIndex();

   Option getOptions(int var1);

   int getOptionsCount();

   List<Option> getOptionsList();

   boolean getPacked();

   String getTypeUrl();

   ByteString getTypeUrlBytes();
}
