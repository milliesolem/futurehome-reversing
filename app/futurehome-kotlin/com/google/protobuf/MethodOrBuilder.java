package com.google.protobuf;

import java.util.List;

public interface MethodOrBuilder extends MessageLiteOrBuilder {
   String getName();

   ByteString getNameBytes();

   Option getOptions(int var1);

   int getOptionsCount();

   List<Option> getOptionsList();

   boolean getRequestStreaming();

   String getRequestTypeUrl();

   ByteString getRequestTypeUrlBytes();

   boolean getResponseStreaming();

   String getResponseTypeUrl();

   ByteString getResponseTypeUrlBytes();

   Syntax getSyntax();

   int getSyntaxValue();
}
