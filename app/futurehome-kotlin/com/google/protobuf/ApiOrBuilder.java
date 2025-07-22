package com.google.protobuf;

import java.util.List;

public interface ApiOrBuilder extends MessageLiteOrBuilder {
   Method getMethods(int var1);

   int getMethodsCount();

   List<Method> getMethodsList();

   Mixin getMixins(int var1);

   int getMixinsCount();

   List<Mixin> getMixinsList();

   String getName();

   ByteString getNameBytes();

   Option getOptions(int var1);

   int getOptionsCount();

   List<Option> getOptionsList();

   SourceContext getSourceContext();

   Syntax getSyntax();

   int getSyntaxValue();

   String getVersion();

   ByteString getVersionBytes();

   boolean hasSourceContext();
}
