package io.sentry.vendor.gson.internal.bind.util;

import j..util.DesugarTimeZone;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils {
   public static final TimeZone TIMEZONE_UTC = DesugarTimeZone.getTimeZone("UTC");
   private static final String UTC_ID = "UTC";

   private static boolean checkOffset(String var0, int var1, char var2) {
      boolean var3;
      if (var1 < var0.length() && var0.charAt(var1) == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static String format(Date var0) {
      return format(var0, false, TIMEZONE_UTC);
   }

   public static String format(Date var0, boolean var1) {
      return format(var0, var1, TIMEZONE_UTC);
   }

   public static String format(Date var0, boolean var1, TimeZone var2) {
      GregorianCalendar var7 = new GregorianCalendar(var2, Locale.US);
      var7.setTime(var0);
      byte var4;
      if (var1) {
         var4 = 4;
      } else {
         var4 = 0;
      }

      byte var5;
      if (var2.getRawOffset() == 0) {
         var5 = 1;
      } else {
         var5 = 6;
      }

      StringBuilder var8 = new StringBuilder(19 + var4 + var5);
      padInt(var8, var7.get(1), 4);
      char var3 = '-';
      var8.append('-');
      padInt(var8, var7.get(2) + 1, 2);
      var8.append('-');
      padInt(var8, var7.get(5), 2);
      var8.append('T');
      padInt(var8, var7.get(11), 2);
      var8.append(':');
      padInt(var8, var7.get(12), 2);
      var8.append(':');
      padInt(var8, var7.get(13), 2);
      if (var1) {
         var8.append('.');
         padInt(var8, var7.get(14), 3);
      }

      var4 = var2.getOffset(var7.getTimeInMillis());
      if (var4 != 0) {
         int var6 = var4 / 60000;
         var5 = Math.abs(var6 / 60);
         var6 = Math.abs(var6 % 60);
         if (var4 >= 0) {
            var3 = '+';
         }

         var8.append(var3);
         padInt(var8, var5, 2);
         var8.append(':');
         padInt(var8, var6, 2);
      } else {
         var8.append('Z');
      }

      return var8.toString();
   }

   private static int indexOfNonDigit(String var0, int var1) {
      while (var1 < var0.length()) {
         char var2 = var0.charAt(var1);
         if (var2 < '0' || var2 > '9') {
            return var1;
         }

         var1++;
      }

      return var0.length();
   }

   private static void padInt(StringBuilder var0, int var1, int var2) {
      String var3 = Integer.toString(var1);

      for (int var4 = var2 - var3.length(); var4 > 0; var4--) {
         var0.append('0');
      }

      var0.append(var3);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static Date parse(String var0, ParsePosition var1) throws ParseException {
      IllegalArgumentException var14;
      label379: {
         int var5;
         try {
            var5 = var1.getIndex();
         } catch (IndexOutOfBoundsException var93) {
            var14 = var93;
            break label379;
         } catch (NumberFormatException var94) {
            var14 = var94;
            break label379;
         } catch (IllegalArgumentException var95) {
            var14 = var95;
            break label379;
         }

         int var3 = var5 + 4;

         int var10;
         try {
            var10 = parseInt(var0, var5, var3);
         } catch (IndexOutOfBoundsException var90) {
            var14 = var90;
            break label379;
         } catch (NumberFormatException var91) {
            var14 = var91;
            break label379;
         } catch (IllegalArgumentException var92) {
            var14 = var92;
            break label379;
         }

         int var4 = var3;

         label367: {
            try {
               if (!checkOffset(var0, var3, '-')) {
                  break label367;
               }
            } catch (IndexOutOfBoundsException var87) {
               var14 = var87;
               break label379;
            } catch (NumberFormatException var88) {
               var14 = var88;
               break label379;
            } catch (IllegalArgumentException var89) {
               var14 = var89;
               break label379;
            }

            var4 = var5 + 5;
         }

         var5 = var4 + 2;

         int var11;
         try {
            var11 = parseInt(var0, var4, var5);
         } catch (IndexOutOfBoundsException var84) {
            var14 = var84;
            break label379;
         } catch (NumberFormatException var85) {
            var14 = var85;
            break label379;
         } catch (IllegalArgumentException var86) {
            var14 = var86;
            break label379;
         }

         var3 = var5;

         label356: {
            try {
               if (!checkOffset(var0, var5, '-')) {
                  break label356;
               }
            } catch (IndexOutOfBoundsException var81) {
               var14 = var81;
               break label379;
            } catch (NumberFormatException var82) {
               var14 = var82;
               break label379;
            } catch (IllegalArgumentException var83) {
               var14 = var83;
               break label379;
            }

            var3 = var4 + 3;
         }

         var5 = var3 + 2;

         int var12;
         boolean var13;
         try {
            var12 = parseInt(var0, var3, var5);
            var13 = checkOffset(var0, var5, 'T');
         } catch (IndexOutOfBoundsException var78) {
            var14 = var78;
            break label379;
         } catch (NumberFormatException var79) {
            var14 = var79;
            break label379;
         } catch (IllegalArgumentException var80) {
            var14 = var80;
            break label379;
         }

         if (!var13) {
            try {
               if (var0.length() <= var5) {
                  GregorianCalendar var124 = new GregorianCalendar(var10, var11 - 1, var12);
                  var1.setIndex(var5);
                  return var124.getTime();
               }
            } catch (IndexOutOfBoundsException var75) {
               var14 = var75;
               break label379;
            } catch (NumberFormatException var76) {
               var14 = var76;
               break label379;
            } catch (IllegalArgumentException var77) {
               var14 = var77;
               break label379;
            }
         }

         int var113;
         int var115;
         label342:
         if (var13) {
            var5 = var3 + 5;

            try {
               var4 = parseInt(var0, var3 + 3, var5);
            } catch (IndexOutOfBoundsException var60) {
               var14 = var60;
               break label379;
            } catch (NumberFormatException var61) {
               var14 = var61;
               break label379;
            } catch (IllegalArgumentException var62) {
               var14 = var62;
               break label379;
            }

            var113 = var5;

            label339: {
               try {
                  if (!checkOffset(var0, var5, ':')) {
                     break label339;
                  }
               } catch (IndexOutOfBoundsException var72) {
                  var14 = var72;
                  break label379;
               } catch (NumberFormatException var73) {
                  var14 = var73;
                  break label379;
               } catch (IllegalArgumentException var74) {
                  var14 = var74;
                  break label379;
               }

               var113 = var3 + 6;
            }

            var115 = var113 + 2;

            try {
               var5 = parseInt(var0, var113, var115);
            } catch (IndexOutOfBoundsException var57) {
               var14 = var57;
               break label379;
            } catch (NumberFormatException var58) {
               var14 = var58;
               break label379;
            } catch (IllegalArgumentException var59) {
               var14 = var59;
               break label379;
            }

            var3 = var115;

            label333: {
               try {
                  if (!checkOffset(var0, var115, ':')) {
                     break label333;
                  }
               } catch (IndexOutOfBoundsException var69) {
                  var14 = var69;
                  break label379;
               } catch (NumberFormatException var70) {
                  var14 = var70;
                  break label379;
               } catch (IllegalArgumentException var71) {
                  var14 = var71;
                  break label379;
               }

               var3 = var113 + 3;
            }

            label328: {
               try {
                  if (var0.length() <= var3) {
                     break label328;
                  }

                  var109 = var0.charAt(var3);
               } catch (IndexOutOfBoundsException var66) {
                  var14 = var66;
                  break label379;
               } catch (NumberFormatException var67) {
                  var14 = var67;
                  break label379;
               } catch (IllegalArgumentException var68) {
                  var14 = var68;
                  break label379;
               }

               if (var109 != 'Z' && var109 != '+' && var109 != '-') {
                  var113 = var3 + 2;

                  int var8;
                  try {
                     var8 = parseInt(var0, var3, var113);
                  } catch (IndexOutOfBoundsException var54) {
                     var14 = var54;
                     break label379;
                  } catch (NumberFormatException var55) {
                     var14 = var55;
                     break label379;
                  } catch (IllegalArgumentException var56) {
                     var14 = var56;
                     break label379;
                  }

                  var115 = var8;
                  if (var8 > 59) {
                     var115 = var8;
                     if (var8 < 63) {
                        var115 = 59;
                     }
                  }

                  label382: {
                     try {
                        if (!checkOffset(var0, var113, '.')) {
                           break label382;
                        }
                     } catch (IndexOutOfBoundsException var63) {
                        var14 = var63;
                        break label379;
                     } catch (NumberFormatException var64) {
                        var14 = var64;
                        break label379;
                     } catch (IllegalArgumentException var65) {
                        var14 = var65;
                        break label379;
                     }

                     var113 = var3 + 3;

                     int var9;
                     try {
                        var8 = indexOfNonDigit(var0, var3 + 4);
                        var9 = Math.min(var8, var3 + 6);
                        var3 = parseInt(var0, var113, var9);
                     } catch (IndexOutOfBoundsException var51) {
                        var14 = var51;
                        break label379;
                     } catch (NumberFormatException var52) {
                        var14 = var52;
                        break label379;
                     } catch (IllegalArgumentException var53) {
                        var14 = var53;
                        break label379;
                     }

                     var113 = var9 - var113;
                     if (var113 != 1) {
                        if (var113 == 2) {
                           var3 *= 10;
                        }
                     } else {
                        var3 *= 100;
                     }

                     var113 = var3;
                     var3 = var4;
                     var5 = var8;
                     var4 = var5;
                     break label342;
                  }

                  var3 = var4;
                  var4 = var5;
                  byte var117 = 0;
                  var5 = var113;
                  var113 = var117;
                  break label342;
               }
            }

            byte var118 = 0;
            var115 = 0;
            var5 = var3;
            var3 = var4;
            var4 = var5;
            var113 = var118;
         } else {
            var3 = 0;
            var4 = 0;
            var113 = 0;
            var115 = 0;
         }

         label383: {
            char var2;
            try {
               if (var0.length() <= var5) {
                  break label383;
               }

               var2 = var0.charAt(var5);
            } catch (IndexOutOfBoundsException var48) {
               var14 = var48;
               break label379;
            } catch (NumberFormatException var49) {
               var14 = var49;
               break label379;
            } catch (IllegalArgumentException var50) {
               var14 = var50;
               break label379;
            }

            String var126;
            label388: {
               if (var2 == 'Z') {
                  try {
                     var121 = TIMEZONE_UTC;
                  } catch (IndexOutOfBoundsException var36) {
                     var14 = var36;
                     break label379;
                  } catch (NumberFormatException var37) {
                     var14 = var37;
                     break label379;
                  } catch (IllegalArgumentException var38) {
                     var14 = var38;
                     break label379;
                  }

                  var5++;
               } else {
                  label387: {
                     if (var2 != '+' && var2 != '-') {
                        try {
                           StringBuilder var122 = new StringBuilder("Invalid time zone indicator '");
                           var122.append(var2);
                           var122.append("'");
                           IndexOutOfBoundsException var127 = new IndexOutOfBoundsException(var122.toString());
                           throw var127;
                        } catch (IndexOutOfBoundsException var18) {
                           var14 = var18;
                           break label379;
                        } catch (NumberFormatException var19) {
                           var14 = var19;
                           break label379;
                        } catch (IllegalArgumentException var20) {
                           var14 = var20;
                           break label379;
                        }
                     }

                     label285: {
                        try {
                           var120 = var0.substring(var5);
                           if (var120.length() >= 5) {
                              break label285;
                           }
                        } catch (IndexOutOfBoundsException var45) {
                           var14 = var45;
                           break label379;
                        } catch (NumberFormatException var46) {
                           var14 = var46;
                           break label379;
                        } catch (IllegalArgumentException var47) {
                           var14 = var47;
                           break label379;
                        }

                        try {
                           StringBuilder var15 = new StringBuilder();
                           var15.append(var120);
                           var15.append("00");
                           var120 = var15.toString();
                        } catch (IndexOutOfBoundsException var33) {
                           var14 = var33;
                           break label379;
                        } catch (NumberFormatException var34) {
                           var14 = var34;
                           break label379;
                        } catch (IllegalArgumentException var35) {
                           var14 = var35;
                           break label379;
                        }
                     }

                     label389: {
                        label277:
                        try {
                           var5 += var120.length();
                           if (!"+0000".equals(var120) && !"+00:00".equals(var120)) {
                              break label277;
                           }
                           break label389;
                        } catch (IndexOutOfBoundsException var42) {
                           var14 = var42;
                           break label379;
                        } catch (NumberFormatException var43) {
                           var14 = var43;
                           break label379;
                        } catch (IllegalArgumentException var44) {
                           var14 = var44;
                           break label379;
                        }

                        try {
                           StringBuilder var125 = new StringBuilder("GMT");
                           var125.append(var120);
                           var126 = var125.toString();
                           var121 = DesugarTimeZone.getTimeZone(var126);
                           String var16 = var121.getID();
                           if (!var16.equals(var126) && !var16.replace(":", "").equals(var126)) {
                              break label388;
                           }
                           break label387;
                        } catch (IndexOutOfBoundsException var39) {
                           var14 = var39;
                           break label379;
                        } catch (NumberFormatException var40) {
                           var14 = var40;
                           break label379;
                        } catch (IllegalArgumentException var41) {
                           var14 = var41;
                           break label379;
                        }
                     }

                     try {
                        var121 = TIMEZONE_UTC;
                     } catch (IndexOutOfBoundsException var30) {
                        var14 = var30;
                        break label379;
                     } catch (NumberFormatException var31) {
                        var14 = var31;
                        break label379;
                     } catch (IllegalArgumentException var32) {
                        var14 = var32;
                        break label379;
                     }
                  }
               }

               try {
                  GregorianCalendar var128 = new GregorianCalendar(var121);
                  var128.setLenient(false);
                  var128.set(1, var10);
                  var128.set(2, var11 - 1);
                  var128.set(5, var12);
                  var128.set(11, var3);
                  var128.set(12, var4);
                  var128.set(13, var115);
                  var128.set(14, var113);
                  var1.setIndex(var5);
                  return var128.getTime();
               } catch (IndexOutOfBoundsException var24) {
                  var14 = var24;
                  break label379;
               } catch (NumberFormatException var25) {
                  var14 = var25;
                  break label379;
               } catch (IllegalArgumentException var26) {
                  var14 = var26;
                  break label379;
               }
            }

            try {
               StringBuilder var17 = new StringBuilder("Mismatching time zone indicator: ");
               var17.append(var126);
               var17.append(" given, resolves to ");
               var17.append(var121.getID());
               IndexOutOfBoundsException var132 = new IndexOutOfBoundsException(var17.toString());
               throw var132;
            } catch (IndexOutOfBoundsException var21) {
               var14 = var21;
               break label379;
            } catch (NumberFormatException var22) {
               var14 = var22;
               break label379;
            } catch (IllegalArgumentException var23) {
               var14 = var23;
               break label379;
            }
         }

         try {
            var14 = new IllegalArgumentException("No time zone indicator");
            throw var14;
         } catch (IndexOutOfBoundsException var27) {
            var14 = var27;
         } catch (NumberFormatException var28) {
            var14 = var28;
         } catch (IllegalArgumentException var29) {
            var14 = var29;
         }
      }

      if (var0 == null) {
         var0 = null;
      } else {
         StringBuilder var129 = new StringBuilder("\"");
         var129.append(var0);
         var129.append('"');
         var0 = var129.toString();
      }

      String var130;
      label254: {
         String var133 = var14.getMessage();
         if (var133 != null) {
            var130 = var133;
            if (!var133.isEmpty()) {
               break label254;
            }
         }

         StringBuilder var131 = new StringBuilder("(");
         var131.append(var14.getClass().getName());
         var131.append(")");
         var130 = var131.toString();
      }

      StringBuilder var134 = new StringBuilder("Failed to parse date [");
      var134.append(var0);
      var134.append("]: ");
      var134.append(var130);
      ParseException var97 = new ParseException(var134.toString(), var1.getIndex());
      var97.initCause(var14);
      throw var97;
   }

   private static int parseInt(String var0, int var1, int var2) throws NumberFormatException {
      if (var1 >= 0 && var2 <= var0.length() && var1 <= var2) {
         int var3;
         int var7;
         if (var1 < var2) {
            var3 = var1 + 1;
            var7 = Character.digit(var0.charAt(var1), 10);
            if (var7 < 0) {
               StringBuilder var6 = new StringBuilder("Invalid number: ");
               var6.append(var0.substring(var1, var2));
               throw new NumberFormatException(var6.toString());
            }

            var7 = -var7;
         } else {
            var7 = 0;
            var3 = var1;
         }

         while (var3 < var2) {
            int var5 = Character.digit(var0.charAt(var3), 10);
            if (var5 < 0) {
               StringBuilder var8 = new StringBuilder("Invalid number: ");
               var8.append(var0.substring(var1, var2));
               throw new NumberFormatException(var8.toString());
            }

            var7 = var7 * 10 - var5;
            var3++;
         }

         return -var7;
      } else {
         throw new NumberFormatException(var0);
      }
   }
}
