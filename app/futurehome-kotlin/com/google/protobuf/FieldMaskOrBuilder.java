package com.google.protobuf;

import java.util.List;

public interface FieldMaskOrBuilder extends MessageLiteOrBuilder {
   String getPaths(int var1);

   ByteString getPathsBytes(int var1);

   int getPathsCount();

   List<String> getPathsList();
}
