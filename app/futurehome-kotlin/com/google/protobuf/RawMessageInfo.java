package com.google.protobuf;

@CheckReturnValue
final class RawMessageInfo implements MessageInfo {
   private static final int IS_EDITION_BIT = 4;
   private static final int IS_PROTO2_BIT = 1;
   private final MessageLite defaultInstance;
   private final int flags;
   private final String info;
   private final Object[] objects;

   RawMessageInfo(MessageLite var1, String var2, Object[] var3) {
      this.defaultInstance = var1;
      this.info = var2;
      this.objects = var3;
      int var4 = var2.charAt(0);
      if (var4 < 55296) {
         this.flags = var4;
      } else {
         int var5 = var4 & 8191;
         byte var6 = 13;
         var4 = 1;

         while (true) {
            char var7 = var2.charAt(var4);
            if (var7 < '\ud800') {
               this.flags = var5 | var7 << var6;
               break;
            }

            var5 |= (var7 & 8191) << var6;
            var6 += 13;
            var4++;
         }
      }
   }

   @Override
   public MessageLite getDefaultInstance() {
      return this.defaultInstance;
   }

   Object[] getObjects() {
      return this.objects;
   }

   String getStringInfo() {
      return this.info;
   }

   @Override
   public ProtoSyntax getSyntax() {
      int var1 = this.flags;
      if ((var1 & 1) != 0) {
         return ProtoSyntax.PROTO2;
      } else {
         return (var1 & 4) == 4 ? ProtoSyntax.EDITIONS : ProtoSyntax.PROTO3;
      }
   }

   @Override
   public boolean isMessageSetWireFormat() {
      boolean var1;
      if ((this.flags & 2) == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
