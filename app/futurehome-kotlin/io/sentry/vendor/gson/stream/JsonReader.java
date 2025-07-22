package io.sentry.vendor.gson.stream;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class JsonReader implements Closeable {
   private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
   private static final int NUMBER_CHAR_DECIMAL = 3;
   private static final int NUMBER_CHAR_DIGIT = 2;
   private static final int NUMBER_CHAR_EXP_DIGIT = 7;
   private static final int NUMBER_CHAR_EXP_E = 5;
   private static final int NUMBER_CHAR_EXP_SIGN = 6;
   private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
   private static final int NUMBER_CHAR_NONE = 0;
   private static final int NUMBER_CHAR_SIGN = 1;
   private static final int PEEKED_BEGIN_ARRAY = 3;
   private static final int PEEKED_BEGIN_OBJECT = 1;
   private static final int PEEKED_BUFFERED = 11;
   private static final int PEEKED_DOUBLE_QUOTED = 9;
   private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
   private static final int PEEKED_END_ARRAY = 4;
   private static final int PEEKED_END_OBJECT = 2;
   private static final int PEEKED_EOF = 17;
   private static final int PEEKED_FALSE = 6;
   private static final int PEEKED_LONG = 15;
   private static final int PEEKED_NONE = 0;
   private static final int PEEKED_NULL = 7;
   private static final int PEEKED_NUMBER = 16;
   private static final int PEEKED_SINGLE_QUOTED = 8;
   private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
   private static final int PEEKED_TRUE = 5;
   private static final int PEEKED_UNQUOTED = 10;
   private static final int PEEKED_UNQUOTED_NAME = 14;
   private final char[] buffer;
   private final Reader in;
   private boolean lenient = false;
   private int limit;
   private int lineNumber;
   private int lineStart;
   private int[] pathIndices;
   private String[] pathNames;
   int peeked;
   private long peekedLong;
   private int peekedNumberLength;
   private String peekedString;
   private int pos;
   private int[] stack;
   private int stackSize;

   public JsonReader(Reader var1) {
      this.buffer = new char[1024];
      this.pos = 0;
      this.limit = 0;
      this.lineNumber = 0;
      this.lineStart = 0;
      this.peeked = 0;
      int[] var2 = new int[32];
      this.stack = var2;
      this.stackSize = 1;
      var2[0] = 6;
      this.pathNames = new String[32];
      this.pathIndices = new int[32];
      if (var1 != null) {
         this.in = var1;
      } else {
         throw new NullPointerException("in == null");
      }
   }

   private void checkLenient() throws IOException {
      if (!this.lenient) {
         throw this.syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
      }
   }

   private void consumeNonExecutePrefix() throws IOException {
      this.nextNonWhitespace(true);
      int var2 = this.pos;
      int var1 = var2 - 1;
      this.pos = var1;
      if (var2 + 4 <= this.limit || this.fillBuffer(5)) {
         char[] var3 = this.buffer;
         if (var3[var1] == ')' && var3[var2] == ']' && var3[var2 + 1] == '}' && var3[var2 + 2] == '\'' && var3[var2 + 3] == '\n') {
            this.pos += 5;
         }
      }
   }

   private boolean fillBuffer(int var1) throws IOException {
      char[] var5 = this.buffer;
      int var3 = this.lineStart;
      int var2 = this.pos;
      this.lineStart = var3 - var2;
      var3 = this.limit;
      if (var3 != var2) {
         var3 -= var2;
         this.limit = var3;
         System.arraycopy(var5, var2, var5, 0, var3);
      } else {
         this.limit = 0;
      }

      this.pos = 0;

      do {
         Reader var6 = this.in;
         var2 = this.limit;
         var2 = var6.read(var5, var2, var5.length - var2);
         if (var2 == -1) {
            return false;
         }

         var3 = this.limit + var2;
         this.limit = var3;
         var2 = var1;
         if (this.lineNumber == 0) {
            int var4 = this.lineStart;
            var2 = var1;
            if (var4 == 0) {
               var2 = var1;
               if (var3 > 0) {
                  var2 = var1;
                  if (var5[0] == '\ufeff') {
                     this.pos++;
                     this.lineStart = var4 + 1;
                     var2 = var1 + 1;
                  }
               }
            }
         }

         var1 = var2;
      } while (var3 < var2);

      return true;
   }

   private boolean isLiteral(char var1) throws IOException {
      if (var1 != '\t' && var1 != '\n' && var1 != '\f' && var1 != '\r' && var1 != ' ') {
         if (var1 != '#') {
            if (var1 == ',') {
               return false;
            }

            if (var1 != '/' && var1 != '=') {
               if (var1 == '{' || var1 == '}' || var1 == ':') {
                  return false;
               }

               if (var1 != ';') {
                  switch (var1) {
                     case '[':
                     case ']':
                        return false;
                     case '\\':
                        break;
                     default:
                        return true;
                  }
               }
            }
         }

         this.checkLenient();
      }

      return false;
   }

   private int nextNonWhitespace(boolean var1) throws IOException {
      char[] var7 = this.buffer;
      int var2 = this.pos;
      int var3 = this.limit;

      while (true) {
         int var5 = var2;
         int var4 = var3;
         if (var2 == var3) {
            this.pos = var2;
            if (!this.fillBuffer(1)) {
               if (!var1) {
                  return -1;
               }

               StringBuilder var11 = new StringBuilder("End of input");
               var11.append(this.locationString());
               throw new EOFException(var11.toString());
            }

            var5 = this.pos;
            var4 = this.limit;
         }

         var2 = var5 + 1;
         char var9 = var7[var5];
         if (var9 == '\n') {
            this.lineNumber++;
            this.lineStart = var2;
         } else if (var9 != ' ' && var9 != '\r' && var9 != '\t') {
            if (var9 == '/') {
               this.pos = var2;
               if (var2 == var4) {
                  this.pos = var5;
                  boolean var6 = this.fillBuffer(2);
                  this.pos++;
                  if (!var6) {
                     return var9;
                  }
               }

               this.checkLenient();
               var4 = this.pos;
               char var8 = var7[var4];
               if (var8 != '*') {
                  if (var8 != '/') {
                     return var9;
                  }

                  this.pos = var4 + 1;
                  this.skipToEndOfLine();
                  var2 = this.pos;
                  var3 = this.limit;
               } else {
                  this.pos = var4 + 1;
                  if (!this.skipTo("*/")) {
                     throw this.syntaxError("Unterminated comment");
                  }

                  var2 = this.pos + 2;
                  var3 = this.limit;
               }
            } else {
               if (var9 != '#') {
                  this.pos = var2;
                  return var9;
               }

               this.pos = var2;
               this.checkLenient();
               this.skipToEndOfLine();
               var2 = this.pos;
               var3 = this.limit;
            }
            continue;
         }

         var3 = var4;
      }
   }

   private String nextQuotedValue(char var1) throws IOException {
      char[] var8 = this.buffer;
      StringBuilder var6 = null;

      label47:
      while (true) {
         int var2 = this.pos;
         int var4 = this.limit;

         while (true) {
            int var3 = var2;

            while (true) {
               int var5 = var3;
               if (var3 >= var4) {
                  StringBuilder var12 = var6;
                  if (var6 == null) {
                     var12 = new StringBuilder(Math.max((var3 - var2) * 2, 16));
                  }

                  var12.append(var8, var2, var3 - var2);
                  this.pos = var3;
                  if (!this.fillBuffer(1)) {
                     throw this.syntaxError("Unterminated string");
                  }

                  var6 = var12;
                  continue label47;
               }

               var3++;
               char var11 = var8[var5];
               if (var11 == var1) {
                  this.pos = var3;
                  var1 = var3 - var2 - 1;
                  if (var6 == null) {
                     return new String(var8, var2, var1);
                  }

                  var6.append(var8, var2, var1);
                  return var6.toString();
               }

               if (var11 == '\\') {
                  this.pos = var3;
                  var3 -= var2;
                  StringBuilder var7 = var6;
                  if (var6 == null) {
                     var7 = new StringBuilder(Math.max(var3 * 2, 16));
                  }

                  var7.append(var8, var2, var3 - 1);
                  var7.append(this.readEscapeCharacter());
                  var2 = this.pos;
                  var4 = this.limit;
                  var6 = var7;
                  break;
               }

               if (var11 == '\n') {
                  this.lineNumber++;
                  this.lineStart = var3;
               }
            }
         }
      }
   }

   private String nextUnquotedValue() throws IOException {
      StringBuilder var5 = null;
      byte var2 = 0;

      int var1;
      StringBuilder var4;
      label77:
      while (true) {
         var1 = 0;

         label74:
         while (true) {
            int var3 = this.pos;
            if (var3 + var1 < this.limit) {
               char var6 = this.buffer[var3 + var1];
               if (var6 == '\t' || var6 == '\n' || var6 == '\f' || var6 == '\r' || var6 == ' ') {
                  break;
               }

               if (var6 != '#') {
                  if (var6 == ',') {
                     break;
                  }

                  if (var6 != '/' && var6 != '=') {
                     if (var6 == '{' || var6 == '}' || var6 == ':') {
                        break;
                     }

                     if (var6 != ';') {
                        switch (var6) {
                           case '[':
                           case ']':
                              break label74;
                           case '\\':
                              break;
                           default:
                              var1++;
                              continue;
                        }
                     }
                  }
               }

               this.checkLenient();
               break;
            } else {
               if (var1 >= this.buffer.length) {
                  var4 = var5;
                  if (var5 == null) {
                     var4 = new StringBuilder(Math.max(var1, 16));
                  }

                  var4.append(this.buffer, this.pos, var1);
                  this.pos += var1;
                  var5 = var4;
                  if (this.fillBuffer(1)) {
                     continue label77;
                  }

                  var1 = var2;
                  break label77;
               }

               if (!this.fillBuffer(var1 + 1)) {
                  break;
               }
            }
         }

         var4 = var5;
         break;
      }

      String var7;
      if (var4 == null) {
         var7 = new String(this.buffer, this.pos, var1);
      } else {
         var4.append(this.buffer, this.pos, var1);
         var7 = var4.toString();
      }

      this.pos += var1;
      return var7;
   }

   private int peekKeyword() throws IOException {
      char var1 = this.buffer[this.pos];
      String var5;
      String var6;
      byte var7;
      if (var1 == 't' || var1 == 'T') {
         var5 = "true";
         var6 = "TRUE";
         var7 = 5;
      } else if (var1 != 'f' && var1 != 'F') {
         if (var1 != 'n' && var1 != 'N') {
            return 0;
         }

         var5 = "null";
         var6 = "NULL";
         var7 = 7;
      } else {
         var5 = "false";
         var6 = "FALSE";
         var7 = 6;
      }

      int var3 = var5.length();

      for (int var2 = 1; var2 < var3; var2++) {
         if (this.pos + var2 >= this.limit && !this.fillBuffer(var2 + 1)) {
            return 0;
         }

         char var4 = this.buffer[this.pos + var2];
         if (var4 != var5.charAt(var2) && var4 != var6.charAt(var2)) {
            return 0;
         }
      }

      if ((this.pos + var3 < this.limit || this.fillBuffer(var3 + 1)) && this.isLiteral(this.buffer[this.pos + var3])) {
         return 0;
      } else {
         this.pos += var3;
         this.peeked = var7;
         return var7;
      }
   }

   private int peekNumber() throws IOException {
      char[] var14 = this.buffer;
      int var9 = this.pos;
      int var4 = this.limit;
      int var5 = 0;
      byte var2 = 0;
      boolean var3 = true;
      long var10 = 0L;
      boolean var6 = false;

      while (true) {
         int var8 = var9;
         int var7 = var4;
         if (var9 + var5 == var4) {
            if (var5 == var14.length) {
               return 0;
            }

            if (!this.fillBuffer(var5 + 1)) {
               break;
            }

            var8 = this.pos;
            var7 = this.limit;
         }

         label153: {
            char var1 = var14[var8 + var5];
            if (var1 != '+') {
               if (var1 == 'E' || var1 == 'e') {
                  if (var2 != 2 && var2 != 4) {
                     return 0;
                  }

                  var2 = 5;
                  break label153;
               }

               if (var1 != '-') {
                  if (var1 != '.') {
                     if (var1 >= '0' && var1 <= '9') {
                        if (var2 != 1 && var2 != 0) {
                           long var12;
                           boolean var15;
                           if (var2 == 2) {
                              if (var10 == 0L) {
                                 return 0;
                              }

                              var12 = 10L * var10 - (var1 - '0');
                              long var18;
                              var15 = (var18 = var10 - -922337203685477580L) == 0L ? 0 : (var18 < 0L ? -1 : 1);
                              boolean var17;
                              if (var15 > 0 || var15 == 0 && var12 < var10) {
                                 var17 = true;
                              } else {
                                 var17 = false;
                              }

                              var15 = var3 & var17;
                           } else {
                              label169: {
                                 if (var2 == 3) {
                                    var2 = 4;
                                    break label153;
                                 }

                                 if (var2 != 5) {
                                    var15 = var3;
                                    var12 = var10;
                                    if (var2 != 6) {
                                       break label169;
                                    }
                                 }

                                 var2 = 7;
                                 break label153;
                              }
                           }

                           var3 = (boolean)var15;
                           var10 = var12;
                        } else {
                           var10 = -(var1 - '0');
                           var2 = 2;
                        }
                        break label153;
                     }

                     if (this.isLiteral(var1)) {
                        return 0;
                     }
                     break;
                  }

                  if (var2 != 2) {
                     return 0;
                  }

                  var2 = 3;
                  break label153;
               }

               if (var2 == 0) {
                  var2 = 1;
                  var6 = true;
                  break label153;
               }

               if (var2 != 5) {
                  return 0;
               }
            } else if (var2 != 5) {
               return 0;
            }

            var2 = 6;
         }

         var5++;
         var9 = var8;
         var4 = var7;
      }

      if (var2 != 2 || !var3 || var10 == Long.MIN_VALUE && !var6 || var10 == 0L && var6) {
         if (var2 != 2 && var2 != 4 && var2 != 7) {
            return 0;
         } else {
            this.peekedNumberLength = var5;
            this.peeked = 16;
            return 16;
         }
      } else {
         if (!var6) {
            var10 = -var10;
         }

         this.peekedLong = var10;
         this.pos += var5;
         this.peeked = 15;
         return 15;
      }
   }

   private void push(int var1) {
      int var2 = this.stackSize;
      int[] var3 = this.stack;
      if (var2 == var3.length) {
         var2 *= 2;
         this.stack = Arrays.copyOf(var3, var2);
         this.pathIndices = Arrays.copyOf(this.pathIndices, var2);
         this.pathNames = Arrays.copyOf(this.pathNames, var2);
      }

      var3 = this.stack;
      var2 = this.stackSize++;
      var3[var2] = var1;
   }

   private char readEscapeCharacter() throws IOException {
      if (this.pos == this.limit && !this.fillBuffer(1)) {
         throw this.syntaxError("Unterminated escape sequence");
      } else {
         char[] var6 = this.buffer;
         int var2 = this.pos;
         int var3 = var2 + 1;
         this.pos = var3;
         char var1 = var6[var2];
         if (var1 != '\n') {
            if (var1 != '"' && var1 != '\'' && var1 != '/' && var1 != '\\') {
               if (var1 == 'b') {
                  return '\b';
               }

               if (var1 == 'f') {
                  return '\f';
               }

               if (var1 == 'n') {
                  return '\n';
               }

               if (var1 == 'r') {
                  return '\r';
               }

               if (var1 == 't') {
                  return '\t';
               }

               if (var1 != 'u') {
                  throw this.syntaxError("Invalid escape sequence");
               }

               if (var2 + 5 > this.limit && !this.fillBuffer(4)) {
                  throw this.syntaxError("Unterminated escape sequence");
               }

               var3 = this.pos;
               var1 = '\u0000';

               for (int var8 = var3; var8 < var3 + 4; var8 += 1) {
                  int var9 = this.buffer[var8];
                  char var5 = (char)(var1 << 4);
                  if (var9 >= 48 && var9 <= 57) {
                     var9 -= 48;
                  } else if (var9 >= 97 && var9 <= 102) {
                     var9 -= 87;
                  } else {
                     if (var9 < 65 || var9 > 70) {
                        throw new NumberFormatException("\\u".concat(new String(this.buffer, this.pos, 4)));
                     }

                     var9 -= 55;
                  }

                  var1 = (char)(var5 + var9);
               }

               this.pos += 4;
               return var1;
            }
         } else {
            this.lineNumber++;
            this.lineStart = var3;
         }

         return var1;
      }
   }

   private void skipQuotedValue(char var1) throws IOException {
      char[] var5 = this.buffer;

      do {
         int var2 = this.pos;
         int var3 = this.limit;

         while (var2 < var3) {
            int var4 = var2 + 1;
            char var6 = var5[var2];
            if (var6 == var1) {
               this.pos = var4;
               return;
            }

            if (var6 == '\\') {
               this.pos = var4;
               this.readEscapeCharacter();
               var2 = this.pos;
               var3 = this.limit;
            } else {
               if (var6 == '\n') {
                  this.lineNumber++;
                  this.lineStart = var4;
               }

               var2 = var4;
            }
         }

         this.pos = var2;
      } while (this.fillBuffer(1));

      throw this.syntaxError("Unterminated string");
   }

   private boolean skipTo(String var1) throws IOException {
      int var3 = var1.length();

      while (true) {
         int var4 = this.pos;
         int var5 = this.limit;
         int var2 = 0;
         if (var4 + var3 > var5 && !this.fillBuffer(var3)) {
            return false;
         }

         char[] var6 = this.buffer;
         var4 = this.pos;
         if (var6[var4] == '\n') {
            this.lineNumber++;
            this.lineStart = var4 + 1;
         } else {
            while (true) {
               if (var2 >= var3) {
                  return true;
               }

               if (this.buffer[this.pos + var2] != var1.charAt(var2)) {
                  break;
               }

               var2++;
            }
         }

         this.pos++;
      }
   }

   private void skipToEndOfLine() throws IOException {
      while (this.pos < this.limit || this.fillBuffer(1)) {
         char[] var3 = this.buffer;
         int var2 = this.pos;
         int var1 = var2 + 1;
         this.pos = var1;
         char var4 = var3[var2];
         if (var4 == '\n') {
            this.lineNumber++;
            this.lineStart = var1;
         } else if (var4 != '\r') {
            continue;
         }
         break;
      }
   }

   private void skipUnquotedValue() throws IOException {
      label59:
      while (true) {
         int var1 = 0;

         label56:
         while (true) {
            int var2 = this.pos;
            if (var2 + var1 >= this.limit) {
               this.pos = var2 + var1;
               if (this.fillBuffer(1)) {
                  continue label59;
               }

               return;
            }

            char var3 = this.buffer[var2 + var1];
            if (var3 == '\t' || var3 == '\n' || var3 == '\f' || var3 == '\r' || var3 == ' ') {
               break;
            }

            if (var3 != '#') {
               if (var3 == ',') {
                  break;
               }

               if (var3 != '/' && var3 != '=') {
                  if (var3 == '{' || var3 == '}' || var3 == ':') {
                     break;
                  }

                  if (var3 != ';') {
                     switch (var3) {
                        case '[':
                        case ']':
                           break label56;
                        case '\\':
                           break;
                        default:
                           var1++;
                           continue;
                     }
                  }
               }
            }

            this.checkLenient();
            break;
         }

         this.pos += var1;
         return;
      }
   }

   private IOException syntaxError(String var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append(this.locationString());
      throw new MalformedJsonException(var2.toString());
   }

   public void beginArray() throws IOException {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 3) {
         this.push(1);
         this.pathIndices[this.stackSize - 1] = 0;
         this.peeked = 0;
      } else {
         StringBuilder var3 = new StringBuilder("Expected BEGIN_ARRAY but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   public void beginObject() throws IOException {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 1) {
         this.push(3);
         this.peeked = 0;
      } else {
         StringBuilder var3 = new StringBuilder("Expected BEGIN_OBJECT but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   @Override
   public void close() throws IOException {
      this.peeked = 0;
      this.stack[0] = 8;
      this.stackSize = 1;
      this.in.close();
   }

   int doPeek() throws IOException {
      int[] var3 = this.stack;
      int var2 = this.stackSize;
      int var1 = var3[var2 - 1];
      if (var1 == 1) {
         var3[var2 - 1] = 2;
      } else if (var1 == 2) {
         var2 = this.nextNonWhitespace(true);
         if (var2 != 44) {
            if (var2 != 59) {
               if (var2 == 93) {
                  this.peeked = 4;
                  return 4;
               }

               throw this.syntaxError("Unterminated array");
            }

            this.checkLenient();
         }
      } else {
         if (var1 == 3 || var1 == 5) {
            var3[var2 - 1] = 4;
            if (var1 == 5) {
               var2 = this.nextNonWhitespace(true);
               if (var2 != 44) {
                  if (var2 != 59) {
                     if (var2 == 125) {
                        this.peeked = 2;
                        return 2;
                     }

                     throw this.syntaxError("Unterminated object");
                  }

                  this.checkLenient();
               }
            }

            var2 = this.nextNonWhitespace(true);
            if (var2 != 34) {
               if (var2 != 39) {
                  if (var2 != 125) {
                     this.checkLenient();
                     this.pos--;
                     if (this.isLiteral((char)var2)) {
                        this.peeked = 14;
                        return 14;
                     }

                     throw this.syntaxError("Expected name");
                  }

                  if (var1 != 5) {
                     this.peeked = 2;
                     return 2;
                  }

                  throw this.syntaxError("Expected name");
               }

               this.checkLenient();
               this.peeked = 12;
               return 12;
            }

            this.peeked = 13;
            return 13;
         }

         if (var1 == 4) {
            var3[var2 - 1] = 5;
            var2 = this.nextNonWhitespace(true);
            if (var2 != 58) {
               if (var2 != 61) {
                  throw this.syntaxError("Expected ':'");
               }

               this.checkLenient();
               if (this.pos < this.limit || this.fillBuffer(1)) {
                  char[] var12 = this.buffer;
                  var2 = this.pos;
                  if (var12[var2] == '>') {
                     this.pos = var2 + 1;
                  }
               }
            }
         } else if (var1 == 6) {
            if (this.lenient) {
               this.consumeNonExecutePrefix();
            }

            this.stack[this.stackSize - 1] = 7;
         } else if (var1 == 7) {
            if (this.nextNonWhitespace(false) == -1) {
               this.peeked = 17;
               return 17;
            }

            this.checkLenient();
            this.pos--;
         } else if (var1 == 8) {
            throw new IllegalStateException("JsonReader is closed");
         }
      }

      var2 = this.nextNonWhitespace(true);
      if (var2 != 34) {
         if (var2 != 39) {
            if (var2 != 44 && var2 != 59) {
               if (var2 == 91) {
                  this.peeked = 3;
                  return 3;
               }

               if (var2 != 93) {
                  if (var2 != 123) {
                     this.pos--;
                     var1 = this.peekKeyword();
                     if (var1 != 0) {
                        return var1;
                     }

                     var1 = this.peekNumber();
                     if (var1 != 0) {
                        return var1;
                     }

                     if (this.isLiteral(this.buffer[this.pos])) {
                        this.checkLenient();
                        this.peeked = 10;
                        return 10;
                     }

                     throw this.syntaxError("Expected value");
                  }

                  this.peeked = 1;
                  return 1;
               }

               if (var1 == 1) {
                  this.peeked = 4;
                  return 4;
               }
            }

            if (var1 != 1 && var1 != 2) {
               throw this.syntaxError("Unexpected value");
            } else {
               this.checkLenient();
               this.pos--;
               this.peeked = 7;
               return 7;
            }
         } else {
            this.checkLenient();
            this.peeked = 8;
            return 8;
         }
      } else {
         this.peeked = 9;
         return 9;
      }
   }

   public void endArray() throws IOException {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 4) {
         var1 = this.stackSize--;
         int[] var6 = this.pathIndices;
         var1 -= 2;
         var6[var1]++;
         this.peeked = 0;
      } else {
         StringBuilder var3 = new StringBuilder("Expected END_ARRAY but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   public void endObject() throws IOException {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 2) {
         var1 = this.stackSize;
         var2 = var1 - 1;
         this.stackSize = var2;
         this.pathNames[var2] = null;
         int[] var7 = this.pathIndices;
         var1 -= 2;
         var7[var1]++;
         this.peeked = 0;
      } else {
         StringBuilder var3 = new StringBuilder("Expected END_OBJECT but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   public String getPath() {
      StringBuilder var4 = new StringBuilder("$");
      int var2 = this.stackSize;

      for (int var1 = 0; var1 < var2; var1++) {
         int var3 = this.stack[var1];
         if (var3 == 1 || var3 == 2) {
            var4.append('[');
            var4.append(this.pathIndices[var1]);
            var4.append(']');
         } else if (var3 == 3 || var3 == 4 || var3 == 5) {
            var4.append('.');
            String var5 = this.pathNames[var1];
            if (var5 != null) {
               var4.append(var5);
            }
         }
      }

      return var4.toString();
   }

   public boolean hasNext() throws IOException {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      boolean var3;
      if (var1 != 2 && var1 != 4) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final boolean isLenient() {
      return this.lenient;
   }

   String locationString() {
      int var3 = this.lineNumber;
      int var1 = this.pos;
      int var2 = this.lineStart;
      StringBuilder var4 = new StringBuilder(" at line ");
      var4.append(var3 + 1);
      var4.append(" column ");
      var4.append(var1 - var2 + 1);
      var4.append(" path ");
      var4.append(this.getPath());
      return var4.toString();
   }

   public boolean nextBoolean() throws IOException {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 5) {
         this.peeked = 0;
         int[] var7 = this.pathIndices;
         var1 = this.stackSize - 1;
         var7[var1]++;
         return true;
      } else if (var1 == 6) {
         this.peeked = 0;
         int[] var6 = this.pathIndices;
         var1 = this.stackSize - 1;
         var6[var1]++;
         return false;
      } else {
         StringBuilder var3 = new StringBuilder("Expected a boolean but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   public double nextDouble() throws IOException {
      int var5 = this.peeked;
      int var4 = var5;
      if (var5 == 0) {
         var4 = this.doPeek();
      }

      if (var4 == 15) {
         this.peeked = 0;
         int[] var11 = this.pathIndices;
         var4 = this.stackSize - 1;
         var11[var4]++;
         return this.peekedLong;
      } else {
         if (var4 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos = this.pos + this.peekedNumberLength;
         } else if (var4 != 8 && var4 != 9) {
            if (var4 == 10) {
               this.peekedString = this.nextUnquotedValue();
            } else if (var4 != 11) {
               StringBuilder var10 = new StringBuilder("Expected a double but was ");
               var10.append(this.peek());
               var10.append(this.locationString());
               throw new IllegalStateException(var10.toString());
            }
         } else {
            char var1;
            if (var4 == 8) {
               var1 = '\'';
            } else {
               var1 = '"';
            }

            this.peekedString = this.nextQuotedValue(var1);
         }

         this.peeked = 11;
         double var2 = Double.parseDouble(this.peekedString);
         if (this.lenient || !Double.isNaN(var2) && !Double.isInfinite(var2)) {
            this.peekedString = null;
            this.peeked = 0;
            int[] var9 = this.pathIndices;
            var4 = this.stackSize - 1;
            var9[var4]++;
            return var2;
         } else {
            StringBuilder var6 = new StringBuilder("JSON forbids NaN and infinities: ");
            var6.append(var2);
            var6.append(this.locationString());
            throw new MalformedJsonException(var6.toString());
         }
      }
   }

   public int nextInt() throws IOException {
      int var5 = this.peeked;
      int var4 = var5;
      if (var5 == 0) {
         var4 = this.doPeek();
      }

      if (var4 == 15) {
         long var6 = this.peekedLong;
         var4 = (int)var6;
         if (var6 == var4) {
            this.peeked = 0;
            int[] var20 = this.pathIndices;
            var5 = this.stackSize - 1;
            var20[var5]++;
            return var4;
         } else {
            StringBuilder var19 = new StringBuilder("Expected an int but was ");
            var19.append(this.peekedLong);
            var19.append(this.locationString());
            throw new NumberFormatException(var19.toString());
         }
      } else {
         if (var4 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos = this.pos + this.peekedNumberLength;
         } else {
            label58: {
               if (var4 != 8 && var4 != 9 && var4 != 10) {
                  StringBuilder var18 = new StringBuilder("Expected an int but was ");
                  var18.append(this.peek());
                  var18.append(this.locationString());
                  throw new IllegalStateException(var18.toString());
               }

               if (var4 == 10) {
                  this.peekedString = this.nextUnquotedValue();
               } else {
                  char var1;
                  if (var4 == 8) {
                     var1 = '\'';
                  } else {
                     var1 = '"';
                  }

                  this.peekedString = this.nextQuotedValue(var1);
               }

               int[] var17;
               try {
                  var4 = Integer.parseInt(this.peekedString);
                  this.peeked = 0;
                  var17 = this.pathIndices;
                  var5 = this.stackSize - 1;
               } catch (NumberFormatException var9) {
                  break label58;
               }

               var17[var5]++;
               return var4;
            }
         }

         this.peeked = 11;
         double var2 = Double.parseDouble(this.peekedString);
         var4 = (int)var2;
         if (var4 == var2) {
            this.peekedString = null;
            this.peeked = 0;
            int[] var16 = this.pathIndices;
            var5 = this.stackSize - 1;
            var16[var5]++;
            return var4;
         } else {
            StringBuilder var8 = new StringBuilder("Expected an int but was ");
            var8.append(this.peekedString);
            var8.append(this.locationString());
            throw new NumberFormatException(var8.toString());
         }
      }
   }

   public long nextLong() throws IOException {
      int var5 = this.peeked;
      int var4 = var5;
      if (var5 == 0) {
         var4 = this.doPeek();
      }

      if (var4 == 15) {
         this.peeked = 0;
         int[] var17 = this.pathIndices;
         var4 = this.stackSize - 1;
         var17[var4]++;
         return this.peekedLong;
      } else {
         if (var4 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos = this.pos + this.peekedNumberLength;
         } else {
            label54: {
               if (var4 != 8 && var4 != 9 && var4 != 10) {
                  StringBuilder var16 = new StringBuilder("Expected a long but was ");
                  var16.append(this.peek());
                  var16.append(this.locationString());
                  throw new IllegalStateException(var16.toString());
               }

               if (var4 == 10) {
                  this.peekedString = this.nextUnquotedValue();
               } else {
                  char var1;
                  if (var4 == 8) {
                     var1 = '\'';
                  } else {
                     var1 = '"';
                  }

                  this.peekedString = this.nextQuotedValue(var1);
               }

               long var13;
               int[] var15;
               try {
                  var13 = Long.parseLong(this.peekedString);
                  this.peeked = 0;
                  var15 = this.pathIndices;
                  var4 = this.stackSize - 1;
               } catch (NumberFormatException var9) {
                  break label54;
               }

               var15[var4]++;
               return var13;
            }
         }

         this.peeked = 11;
         double var2 = Double.parseDouble(this.peekedString);
         long var6 = (long)var2;
         if (var6 == var2) {
            this.peekedString = null;
            this.peeked = 0;
            int[] var14 = this.pathIndices;
            var4 = this.stackSize - 1;
            var14[var4]++;
            return var6;
         } else {
            StringBuilder var8 = new StringBuilder("Expected a long but was ");
            var8.append(this.peekedString);
            var8.append(this.locationString());
            throw new NumberFormatException(var8.toString());
         }
      }
   }

   public String nextName() throws IOException {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      String var3;
      if (var1 == 14) {
         var3 = this.nextUnquotedValue();
      } else if (var1 == 12) {
         var3 = this.nextQuotedValue('\'');
      } else {
         if (var1 != 13) {
            StringBuilder var4 = new StringBuilder("Expected a name but was ");
            var4.append(this.peek());
            var4.append(this.locationString());
            throw new IllegalStateException(var4.toString());
         }

         var3 = this.nextQuotedValue('"');
      }

      this.peeked = 0;
      this.pathNames[this.stackSize - 1] = var3;
      return var3;
   }

   public void nextNull() throws IOException {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 7) {
         this.peeked = 0;
         int[] var5 = this.pathIndices;
         var1 = this.stackSize - 1;
         var5[var1]++;
      } else {
         StringBuilder var3 = new StringBuilder("Expected null but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   public String nextString() throws IOException {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      String var3;
      if (var1 == 10) {
         var3 = this.nextUnquotedValue();
      } else if (var1 == 8) {
         var3 = this.nextQuotedValue('\'');
      } else if (var1 == 9) {
         var3 = this.nextQuotedValue('"');
      } else if (var1 == 11) {
         var3 = this.peekedString;
         this.peekedString = null;
      } else if (var1 == 15) {
         var3 = Long.toString(this.peekedLong);
      } else {
         if (var1 != 16) {
            StringBuilder var6 = new StringBuilder("Expected a string but was ");
            var6.append(this.peek());
            var6.append(this.locationString());
            throw new IllegalStateException(var6.toString());
         }

         var3 = new String(this.buffer, this.pos, this.peekedNumberLength);
         this.pos = this.pos + this.peekedNumberLength;
      }

      this.peeked = 0;
      int[] var4 = this.pathIndices;
      var1 = this.stackSize - 1;
      var4[var1]++;
      return var3;
   }

   public JsonToken peek() throws IOException {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      switch (var1) {
         case 1:
            return JsonToken.BEGIN_OBJECT;
         case 2:
            return JsonToken.END_OBJECT;
         case 3:
            return JsonToken.BEGIN_ARRAY;
         case 4:
            return JsonToken.END_ARRAY;
         case 5:
         case 6:
            return JsonToken.BOOLEAN;
         case 7:
            return JsonToken.NULL;
         case 8:
         case 9:
         case 10:
         case 11:
            return JsonToken.STRING;
         case 12:
         case 13:
         case 14:
            return JsonToken.NAME;
         case 15:
         case 16:
            return JsonToken.NUMBER;
         case 17:
            return JsonToken.END_DOCUMENT;
         default:
            throw new AssertionError();
      }
   }

   public final void setLenient(boolean var1) {
      this.lenient = var1;
   }

   public void skipValue() throws IOException {
      int var2 = 0;

      int var5;
      do {
         var5 = this.peeked;
         int var3 = var5;
         if (var5 == 0) {
            var3 = this.doPeek();
         }

         label58: {
            if (var3 == 3) {
               this.push(1);
            } else {
               if (var3 != 1) {
                  if (var3 == 4) {
                     this.stackSize--;
                  } else {
                     if (var3 != 2) {
                        if (var3 == 14 || var3 == 10) {
                           this.skipUnquotedValue();
                           var5 = var2;
                        } else if (var3 == 8 || var3 == 12) {
                           this.skipQuotedValue('\'');
                           var5 = var2;
                        } else if (var3 != 9 && var3 != 13) {
                           var5 = var2;
                           if (var3 == 16) {
                              this.pos = this.pos + this.peekedNumberLength;
                              var5 = var2;
                           }
                        } else {
                           this.skipQuotedValue('"');
                           var5 = var2;
                        }
                        break label58;
                     }

                     this.stackSize--;
                  }

                  var5 = var2 - 1;
                  break label58;
               }

               this.push(3);
            }

            var5 = var2 + 1;
         }

         this.peeked = 0;
         var2 = var5;
      } while (var5 != 0);

      int[] var4 = this.pathIndices;
      var2 = this.stackSize;
      var5 = var2 - 1;
      var4[var5]++;
      this.pathNames[var2 - 1] = "null";
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getClass().getSimpleName());
      var1.append(this.locationString());
      return var1.toString();
   }
}
