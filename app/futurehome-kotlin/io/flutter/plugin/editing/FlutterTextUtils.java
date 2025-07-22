package io.flutter.plugin.editing;

import io.flutter.embedding.engine.FlutterJNI;

class FlutterTextUtils {
   public static final int CANCEL_TAG = 917631;
   public static final int CARRIAGE_RETURN = 13;
   public static final int COMBINING_ENCLOSING_KEYCAP = 8419;
   public static final int LINE_FEED = 10;
   public static final int ZERO_WIDTH_JOINER = 8205;
   private final FlutterJNI flutterJNI;

   public FlutterTextUtils(FlutterJNI var1) {
      this.flutterJNI = var1;
   }

   public int getOffsetAfter(CharSequence var1, int var2) {
      int var11 = var1.length();
      int var5 = var11 - 1;
      if (var2 >= var5) {
         return var11;
      } else {
         int var7 = Character.codePointAt(var1, var2);
         int var4 = Character.charCount(var7);
         int var6 = var2 + var4;
         int var3 = 0;
         if (var6 == 0) {
            return 0;
         } else if (var7 == 10) {
            var3 = var4;
            if (Character.codePointAt(var1, var6) == 13) {
               var3 = var4 + 1;
            }

            return var2 + var3;
         } else if (this.isRegionalIndicatorSymbol(var7)) {
            if (var6 < var5 && this.isRegionalIndicatorSymbol(Character.codePointAt(var1, var6))) {
               for (int var26 = var2; var26 > 0 && this.isRegionalIndicatorSymbol(Character.codePointBefore(var1, var2)); var3++) {
                  var26 -= Character.charCount(Character.codePointBefore(var1, var2));
               }

               var5 = var4;
               if (var3 % 2 == 0) {
                  var5 = var4 + 2;
               }

               return var2 + var5;
            } else {
               return var6;
            }
         } else {
            var3 = var4;
            if (this.isKeycapBase(var7)) {
               var3 = var4 + Character.charCount(var7);
            }

            if (var7 == 8419) {
               var5 = Character.codePointBefore(var1, var6);
               var4 = var6 + Character.charCount(var5);
               if (var4 < var11 && this.isVariationSelector(var5)) {
                  var6 = Character.codePointAt(var1, var4);
                  var4 = var3;
                  if (this.isKeycapBase(var6)) {
                     var4 = var3 + Character.charCount(var5) + Character.charCount(var6);
                  }
               } else {
                  var4 = var3;
                  if (this.isKeycapBase(var5)) {
                     var4 = var3 + Character.charCount(var5);
                  }
               }

               return var2 + var4;
            } else {
               var5 = var3;
               if (this.isEmoji(var7)) {
                  boolean var8 = false;
                  int var9 = 0;
                  int var10 = var3;
                  var5 = var7;

                  while (true) {
                     var3 = var10;
                     int var31 = var8;
                     if (var8) {
                        var3 = var10 + Character.charCount(var5) + var9 + 1;
                        var31 = 0;
                     }

                     if (this.isEmojiModifier(var5)) {
                        return var2 + var3;
                     }

                     label124: {
                        var4 = var5;
                        var5 = var6;
                        var8 = (boolean)var31;
                        if (var6 < var11) {
                           var9 = Character.codePointAt(var1, var6);
                           var6 += Character.charCount(var9);
                           if (var9 == 8419) {
                              var5 = Character.codePointBefore(var1, var6);
                              var4 = var6 + Character.charCount(var5);
                              if (var4 < var11 && this.isVariationSelector(var5)) {
                                 var6 = Character.codePointAt(var1, var4);
                                 var4 = var3;
                                 if (this.isKeycapBase(var6)) {
                                    var4 = var3 + Character.charCount(var5) + Character.charCount(var6);
                                 }
                              } else {
                                 var4 = var3;
                                 if (this.isKeycapBase(var5)) {
                                    var4 = var3 + Character.charCount(var5);
                                 }
                              }

                              return var2 + var4;
                           }

                           if (this.isEmojiModifier(var9)) {
                              var4 = Character.charCount(var9);
                              break;
                           }

                           if (this.isVariationSelector(var9)) {
                              var4 = Character.charCount(var9);
                              break;
                           }

                           var4 = var9;
                           var5 = var6;
                           var8 = (boolean)var31;
                           if (var9 == 8205) {
                              var4 = Character.codePointAt(var1, var6);
                              var5 = var6 + Character.charCount(var4);
                              if (var5 < var11 && this.isVariationSelector(var4)) {
                                 var4 = Character.codePointAt(var1, var5);
                                 var31 = Character.charCount(var4);
                                 var6 = var5 + Character.charCount(var4);
                                 var8 = true;
                                 break label124;
                              }

                              var8 = true;
                           }
                        }

                        var31 = 0;
                        var6 = var5;
                     }

                     if (var6 >= var11) {
                        return var2 + var3;
                     }

                     if (!var8) {
                        return var2 + var3;
                     }

                     var5 = var4;
                     var10 = var3;
                     var9 = var31;
                     if (!this.isEmoji(var4)) {
                        return var2 + var3;
                     }
                  }

                  var5 = var3 + var4;
               }

               return var2 + var5;
            }
         }
      }
   }

   public int getOffsetBefore(CharSequence var1, int var2) {
      byte var11 = 0;
      int var4 = 1;
      if (var2 <= 1) {
         return 0;
      } else {
         int var6 = Character.codePointBefore(var1, var2);
         int var3 = Character.charCount(var6);
         int var5 = var2 - var3;
         if (var5 == 0) {
            return 0;
         } else if (var6 == 10) {
            var4 = var3;
            if (Character.codePointBefore(var1, var5) == 13) {
               var4 = var3 + 1;
            }

            return var2 - var4;
         } else if (this.isRegionalIndicatorSymbol(var6)) {
            var6 = Character.codePointBefore(var1, var5);

            for (int var28 = var5 - Character.charCount(var6); var28 > 0 && this.isRegionalIndicatorSymbol(var6); var4++) {
               var6 = Character.codePointBefore(var1, var28);
               var28 -= Character.charCount(var6);
            }

            var5 = var3;
            if (var4 % 2 == 0) {
               var5 = var3 + 2;
            }

            return var2 - var5;
         } else if (var6 == 8419) {
            var6 = Character.codePointBefore(var1, var5);
            var4 = var5 - Character.charCount(var6);
            if (var4 > 0 && this.isVariationSelector(var6)) {
               var5 = Character.codePointBefore(var1, var4);
               var4 = var3;
               if (this.isKeycapBase(var5)) {
                  var4 = var3 + Character.charCount(var6) + Character.charCount(var5);
               }
            } else {
               var4 = var3;
               if (this.isKeycapBase(var6)) {
                  var4 = var3 + Character.charCount(var6);
               }
            }

            return var2 - var4;
         } else {
            int var8 = var6;
            int var7 = var3;
            var4 = var5;
            if (var6 == 917631) {
               var7 = Character.codePointBefore(var1, var5);
               var6 = Character.charCount(var7);
               var4 = var3;
               var3 = var7;

               while (true) {
                  var5 -= var6;
                  if (var5 <= 0 || !this.isTagSpecChar(var3)) {
                     if (!this.isEmoji(var3)) {
                        return var2 - 2;
                     }

                     var7 = var4 + Character.charCount(var3);
                     var4 = var5;
                     var8 = var3;
                     break;
                  }

                  var4 += Character.charCount(var3);
                  var3 = Character.codePointBefore(var1, var5);
                  var6 = Character.charCount(var3);
               }
            }

            var5 = var8;
            var3 = var7;
            var6 = var4;
            if (this.isVariationSelector(var8)) {
               var5 = Character.codePointBefore(var1, var4);
               if (!this.isEmoji(var5)) {
                  return var2 - var7;
               }

               var3 = var7 + Character.charCount(var5);
               var6 = var4 - var3;
            }

            var4 = var3;
            if (this.isEmoji(var5)) {
               boolean var40 = false;
               int var10 = 0;
               int var9 = var3;
               var4 = var5;

               while (true) {
                  var5 = var9;
                  int var37 = var40;
                  if (var40) {
                     var5 = var9 + Character.charCount(var4) + var10 + 1;
                     var37 = 0;
                  }

                  if (this.isEmojiModifier(var4)) {
                     var4 = Character.codePointBefore(var1, var6);
                     var37 = var6 - Character.charCount(var4);
                     var6 = var11;
                     var3 = var4;
                     if (var37 > 0) {
                        var6 = var11;
                        var3 = var4;
                        if (this.isVariationSelector(var4)) {
                           var3 = Character.codePointBefore(var1, var37);
                           if (!this.isEmoji(var3)) {
                              return var2 - var5;
                           }

                           var6 = Character.charCount(var3);
                           Character.charCount(var3);
                        }
                     }

                     var4 = var5;
                     if (this.isEmojiModifierBase(var3)) {
                        var4 = var5 + var6 + Character.charCount(var3);
                     }
                     break;
                  }

                  label117: {
                     var3 = var4;
                     var4 = var6;
                     var40 = (boolean)var37;
                     if (var6 > 0) {
                        var9 = Character.codePointBefore(var1, var6);
                        var6 -= Character.charCount(var9);
                        var3 = var9;
                        var4 = var6;
                        var40 = (boolean)var37;
                        if (var9 == 8205) {
                           var3 = Character.codePointBefore(var1, var6);
                           var4 = var6 - Character.charCount(var3);
                           if (var4 > 0 && this.isVariationSelector(var3)) {
                              var3 = Character.codePointBefore(var1, var4);
                              var37 = Character.charCount(var3);
                              var6 = var4 - Character.charCount(var3);
                              var40 = true;
                              break label117;
                           }

                           var40 = true;
                        }
                     }

                     var37 = 0;
                     var6 = var4;
                  }

                  if (var6 == 0) {
                     var4 = var5;
                     break;
                  }

                  var4 = var5;
                  if (!var40) {
                     break;
                  }

                  var4 = var3;
                  var9 = var5;
                  var10 = var37;
                  if (!this.isEmoji(var3)) {
                     var4 = var5;
                     break;
                  }
               }
            }

            return var2 - var4;
         }
      }
   }

   public boolean isEmoji(int var1) {
      return this.flutterJNI.isCodePointEmoji(var1);
   }

   public boolean isEmojiModifier(int var1) {
      return this.flutterJNI.isCodePointEmojiModifier(var1);
   }

   public boolean isEmojiModifierBase(int var1) {
      return this.flutterJNI.isCodePointEmojiModifierBase(var1);
   }

   public boolean isKeycapBase(int var1) {
      boolean var2;
      if ((48 > var1 || var1 > 57) && var1 != 35 && var1 != 42) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public boolean isRegionalIndicatorSymbol(int var1) {
      return this.flutterJNI.isCodePointRegionalIndicator(var1);
   }

   public boolean isTagSpecChar(int var1) {
      boolean var2;
      if (917536 <= var1 && var1 <= 917630) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isVariationSelector(int var1) {
      return this.flutterJNI.isCodePointVariantSelector(var1);
   }
}
