package com.google.protobuf;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UninitializedMessageException extends RuntimeException {
   private static final long serialVersionUID = -7466929953374883507L;
   private final List<String> missingFields;

   public UninitializedMessageException(MessageLite var1) {
      super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
      this.missingFields = null;
   }

   public UninitializedMessageException(List<String> var1) {
      super(buildDescription(var1));
      this.missingFields = var1;
   }

   private static String buildDescription(List<String> var0) {
      StringBuilder var2 = new StringBuilder("Message missing required fields: ");
      Iterator var4 = var0.iterator();
      boolean var1 = true;

      while (var4.hasNext()) {
         String var3 = (String)var4.next();
         if (var1) {
            var1 = false;
         } else {
            var2.append(", ");
         }

         var2.append(var3);
      }

      return var2.toString();
   }

   public InvalidProtocolBufferException asInvalidProtocolBufferException() {
      return new InvalidProtocolBufferException(this.getMessage());
   }

   public List<String> getMissingFields() {
      return Collections.unmodifiableList(this.missingFields);
   }
}
