package com.google.protobuf;

class GeneratedMessageInfoFactory implements MessageInfoFactory {
   private static final GeneratedMessageInfoFactory instance = new GeneratedMessageInfoFactory();

   private GeneratedMessageInfoFactory() {
   }

   public static GeneratedMessageInfoFactory getInstance() {
      return instance;
   }

   @Override
   public boolean isSupported(Class<?> var1) {
      return GeneratedMessageLite.class.isAssignableFrom(var1);
   }

   @Override
   public MessageInfo messageInfoFor(Class<?> var1) {
      if (GeneratedMessageLite.class.isAssignableFrom(var1)) {
         try {
            return (MessageInfo)GeneratedMessageLite.getDefaultInstance(var1.asSubclass(GeneratedMessageLite.class)).buildMessageInfo();
         } catch (Exception var4) {
            StringBuilder var3 = new StringBuilder("Unable to get message info for ");
            var3.append(var1.getName());
            throw new RuntimeException(var3.toString(), var4);
         }
      } else {
         StringBuilder var2 = new StringBuilder("Unsupported message type: ");
         var2.append(var1.getName());
         throw new IllegalArgumentException(var2.toString());
      }
   }
}
