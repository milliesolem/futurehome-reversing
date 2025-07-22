package com.google.protobuf;

import java.util.List;

public interface EnumOrBuilder extends MessageLiteOrBuilder {
   String getEdition();

   ByteString getEditionBytes();

   EnumValue getEnumvalue(int var1);

   int getEnumvalueCount();

   List<EnumValue> getEnumvalueList();

   String getName();

   ByteString getNameBytes();

   Option getOptions(int var1);

   int getOptionsCount();

   List<Option> getOptionsList();

   SourceContext getSourceContext();

   Syntax getSyntax();

   int getSyntaxValue();

   boolean hasSourceContext();
}
