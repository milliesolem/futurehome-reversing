package com.google.protobuf;

import java.util.List;

public interface TypeOrBuilder extends MessageLiteOrBuilder {
   String getEdition();

   ByteString getEditionBytes();

   Field getFields(int var1);

   int getFieldsCount();

   List<Field> getFieldsList();

   String getName();

   ByteString getNameBytes();

   String getOneofs(int var1);

   ByteString getOneofsBytes(int var1);

   int getOneofsCount();

   List<String> getOneofsList();

   Option getOptions(int var1);

   int getOptionsCount();

   List<Option> getOptionsList();

   SourceContext getSourceContext();

   Syntax getSyntax();

   int getSyntaxValue();

   boolean hasSourceContext();
}
