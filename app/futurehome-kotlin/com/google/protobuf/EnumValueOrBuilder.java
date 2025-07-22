package com.google.protobuf;

import java.util.List;

public interface EnumValueOrBuilder extends MessageLiteOrBuilder {
   String getName();

   ByteString getNameBytes();

   int getNumber();

   Option getOptions(int var1);

   int getOptionsCount();

   List<Option> getOptionsList();
}
