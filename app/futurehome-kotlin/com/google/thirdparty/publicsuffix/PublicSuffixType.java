package com.google.thirdparty.publicsuffix;

public enum PublicSuffixType {
   PRIVATE(':', ','),
   REGISTRY('!', '?');

   private static final PublicSuffixType[] $VALUES = $values();
   private final char innerNodeCode;
   private final char leafNodeCode;

   private PublicSuffixType(char var3, char var4) {
      this.innerNodeCode = var3;
      this.leafNodeCode = var4;
   }

   static PublicSuffixType fromCode(char var0) {
      for (PublicSuffixType var4 : values()) {
         if (var4.getInnerNodeCode() == var0 || var4.getLeafNodeCode() == var0) {
            return var4;
         }
      }

      StringBuilder var5 = new StringBuilder(38);
      var5.append("No enum corresponding to given code: ");
      var5.append(var0);
      throw new IllegalArgumentException(var5.toString());
   }

   char getInnerNodeCode() {
      return this.innerNodeCode;
   }

   char getLeafNodeCode() {
      return this.leafNodeCode;
   }
}
