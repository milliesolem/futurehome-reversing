package com.google.protobuf;

@CheckReturnValue
final class ManifestSchemaFactory implements SchemaFactory {
   private static final MessageInfoFactory EMPTY_FACTORY = new MessageInfoFactory() {
      @Override
      public boolean isSupported(Class<?> var1) {
         return false;
      }

      @Override
      public MessageInfo messageInfoFor(Class<?> var1) {
         throw new IllegalStateException("This should never be called.");
      }
   };
   private final MessageInfoFactory messageInfoFactory;

   public ManifestSchemaFactory() {
      this(getDefaultMessageInfoFactory());
   }

   private ManifestSchemaFactory(MessageInfoFactory var1) {
      this.messageInfoFactory = Internal.checkNotNull(var1, "messageInfoFactory");
   }

   private static boolean allowExtensions(MessageInfo var0) {
      return <unrepresentable>.$SwitchMap$com$google$protobuf$ProtoSyntax[var0.getSyntax().ordinal()] != 1;
   }

   private static MessageInfoFactory getDefaultMessageInfoFactory() {
      return new ManifestSchemaFactory.CompositeMessageInfoFactory(GeneratedMessageInfoFactory.getInstance(), getDescriptorMessageInfoFactory());
   }

   private static MessageInfoFactory getDescriptorMessageInfoFactory() {
      try {
         return (MessageInfoFactory)Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", null).invoke(null, null);
      } catch (Exception var1) {
         return EMPTY_FACTORY;
      }
   }

   private static <T> Schema<T> newSchema(Class<T> var0, MessageInfo var1) {
      if (GeneratedMessageLite.class.isAssignableFrom(var0)) {
         MessageSchema var3;
         if (allowExtensions(var1)) {
            var3 = MessageSchema.newSchema(
               var0,
               var1,
               NewInstanceSchemas.lite(),
               ListFieldSchema.lite(),
               SchemaUtil.unknownFieldSetLiteSchema(),
               ExtensionSchemas.lite(),
               MapFieldSchemas.lite()
            );
         } else {
            var3 = MessageSchema.newSchema(
               var0, var1, NewInstanceSchemas.lite(), ListFieldSchema.lite(), SchemaUtil.unknownFieldSetLiteSchema(), null, MapFieldSchemas.lite()
            );
         }

         return var3;
      } else {
         MessageSchema var2;
         if (allowExtensions(var1)) {
            var2 = MessageSchema.newSchema(
               var0,
               var1,
               NewInstanceSchemas.full(),
               ListFieldSchema.full(),
               SchemaUtil.unknownFieldSetFullSchema(),
               ExtensionSchemas.full(),
               MapFieldSchemas.full()
            );
         } else {
            var2 = MessageSchema.newSchema(
               var0, var1, NewInstanceSchemas.full(), ListFieldSchema.full(), SchemaUtil.unknownFieldSetFullSchema(), null, MapFieldSchemas.full()
            );
         }

         return var2;
      }
   }

   @Override
   public <T> Schema<T> createSchema(Class<T> var1) {
      SchemaUtil.requireGeneratedMessage(var1);
      MessageInfo var2 = this.messageInfoFactory.messageInfoFor(var1);
      if (var2.isMessageSetWireFormat()) {
         return GeneratedMessageLite.class.isAssignableFrom(var1)
            ? MessageSetSchema.newSchema(SchemaUtil.unknownFieldSetLiteSchema(), ExtensionSchemas.lite(), var2.getDefaultInstance())
            : MessageSetSchema.newSchema(SchemaUtil.unknownFieldSetFullSchema(), ExtensionSchemas.full(), var2.getDefaultInstance());
      } else {
         return newSchema(var1, var2);
      }
   }

   private static class CompositeMessageInfoFactory implements MessageInfoFactory {
      private MessageInfoFactory[] factories;

      CompositeMessageInfoFactory(MessageInfoFactory... var1) {
         this.factories = var1;
      }

      @Override
      public boolean isSupported(Class<?> var1) {
         MessageInfoFactory[] var4 = this.factories;
         int var3 = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            if (var4[var2].isSupported(var1)) {
               return true;
            }
         }

         return false;
      }

      @Override
      public MessageInfo messageInfoFor(Class<?> var1) {
         for (MessageInfoFactory var5 : this.factories) {
            if (var5.isSupported(var1)) {
               return var5.messageInfoFor(var1);
            }
         }

         StringBuilder var6 = new StringBuilder("No factory is available for message type: ");
         var6.append(var1.getName());
         throw new UnsupportedOperationException(var6.toString());
      }
   }
}
