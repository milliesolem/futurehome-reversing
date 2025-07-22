package com.google.protobuf;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

final class MessageLiteToString {
   private static final String BUILDER_LIST_SUFFIX = "OrBuilderList";
   private static final String BYTES_SUFFIX = "Bytes";
   private static final char[] INDENT_BUFFER;
   private static final String LIST_SUFFIX = "List";
   private static final String MAP_SUFFIX = "Map";

   static {
      char[] var0 = new char[80];
      INDENT_BUFFER = var0;
      Arrays.fill(var0, ' ');
   }

   private MessageLiteToString() {
   }

   private static void indent(int var0, StringBuilder var1) {
      while (var0 > 0) {
         char[] var3 = INDENT_BUFFER;
         int var2;
         if (var0 > var3.length) {
            var2 = var3.length;
         } else {
            var2 = var0;
         }

         var1.append(var3, 0, var2);
         var0 -= var2;
      }
   }

   private static boolean isDefaultValue(Object var0) {
      boolean var6 = var0 instanceof Boolean;
      boolean var3 = true;
      boolean var1 = true;
      boolean var5 = true;
      boolean var4 = true;
      boolean var2 = true;
      if (var6) {
         return (Boolean)var0 ^ true;
      } else if (var0 instanceof Integer) {
         if ((Integer)var0 == 0) {
            var1 = var2;
         } else {
            var1 = false;
         }

         return var1;
      } else if (var0 instanceof Float) {
         if (Float.floatToRawIntBits((Float)var0) == 0) {
            var1 = var3;
         } else {
            var1 = false;
         }

         return var1;
      } else if (var0 instanceof Double) {
         if (Double.doubleToRawLongBits((Double)var0) != 0L) {
            var1 = false;
         }

         return var1;
      } else if (var0 instanceof String) {
         return var0.equals("");
      } else if (var0 instanceof ByteString) {
         return var0.equals(ByteString.EMPTY);
      } else if (var0 instanceof MessageLite) {
         if (var0 == ((MessageLite)var0).getDefaultInstanceForType()) {
            var1 = var5;
         } else {
            var1 = false;
         }

         return var1;
      } else if (var0 instanceof java.lang.Enum) {
         if (((java.lang.Enum)var0).ordinal() == 0) {
            var1 = var4;
         } else {
            var1 = false;
         }

         return var1;
      } else {
         return false;
      }
   }

   private static String pascalCaseToSnakeCase(String var0) {
      if (var0.isEmpty()) {
         return var0;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append(Character.toLowerCase(var0.charAt(0)));

         for (int var2 = 1; var2 < var0.length(); var2++) {
            char var1 = var0.charAt(var2);
            if (Character.isUpperCase(var1)) {
               var3.append("_");
            }

            var3.append(Character.toLowerCase(var1));
         }

         return var3.toString();
      }
   }

   static void printField(StringBuilder var0, int var1, String var2, Object var3) {
      if (var3 instanceof List) {
         var3 = ((List)var3).iterator();

         while (var3.hasNext()) {
            printField(var0, var1, var2, var3.next());
         }
      } else if (!(var3 instanceof Map)) {
         var0.append('\n');
         indent(var1, var0);
         var0.append(pascalCaseToSnakeCase(var2));
         if (var3 instanceof String) {
            var0.append(": \"");
            var0.append(TextFormatEscaper.escapeText((String)var3));
            var0.append('"');
         } else if (var3 instanceof ByteString) {
            var0.append(": \"");
            var0.append(TextFormatEscaper.escapeBytes((ByteString)var3));
            var0.append('"');
         } else if (var3 instanceof GeneratedMessageLite) {
            var0.append(" {");
            reflectivePrintWithIndent((GeneratedMessageLite)var3, var0, var1 + 2);
            var0.append("\n");
            indent(var1, var0);
            var0.append("}");
         } else if (var3 instanceof Entry) {
            var0.append(" {");
            Entry var5 = (Entry)var3;
            int var4 = var1 + 2;
            printField(var0, var4, "key", var5.getKey());
            printField(var0, var4, "value", var5.getValue());
            var0.append("\n");
            indent(var1, var0);
            var0.append("}");
         } else {
            var0.append(": ");
            var0.append(var3);
         }
      } else {
         var3 = ((Map)var3).entrySet().iterator();

         while (var3.hasNext()) {
            printField(var0, var1, var2, (Entry)var3.next());
         }
      }
   }

   private static void reflectivePrintWithIndent(MessageLite var0, StringBuilder var1, int var2) {
      HashSet var7 = new HashSet();
      HashMap var8 = new HashMap();
      TreeMap var6 = new TreeMap();

      for (java.lang.reflect.Method var9 : var0.getClass().getDeclaredMethods()) {
         if (!Modifier.isStatic(var9.getModifiers()) && var9.getName().length() >= 3) {
            if (var9.getName().startsWith("set")) {
               var7.add(var9.getName());
            } else if (Modifier.isPublic(var9.getModifiers()) && var9.getParameterTypes().length == 0) {
               if (var9.getName().startsWith("has")) {
                  var8.put(var9.getName(), var9);
               } else if (var9.getName().startsWith("get")) {
                  var6.put(var9.getName(), var9);
               }
            }
         }
      }

      for (Entry var11 : var6.entrySet()) {
         String var17 = ((String)var11.getKey()).substring(3);
         if (var17.endsWith("List") && !var17.endsWith("OrBuilderList") && !var17.equals("List")) {
            java.lang.reflect.Method var12 = (java.lang.reflect.Method)var11.getValue();
            if (var12 != null && var12.getReturnType().equals(List.class)) {
               printField(var1, var2, var17.substring(0, var17.length() - 4), GeneratedMessageLite.invokeOrDie(var12, var0));
               continue;
            }
         }

         if (var17.endsWith("Map") && !var17.equals("Map")) {
            java.lang.reflect.Method var21 = (java.lang.reflect.Method)var11.getValue();
            if (var21 != null
               && var21.getReturnType().equals(Map.class)
               && !var21.isAnnotationPresent(Deprecated.class)
               && Modifier.isPublic(var21.getModifiers())) {
               printField(var1, var2, var17.substring(0, var17.length() - 3), GeneratedMessageLite.invokeOrDie(var21, var0));
               continue;
            }
         }

         StringBuilder var22 = new StringBuilder("set");
         var22.append(var17);
         if (var7.contains(var22.toString())) {
            if (var17.endsWith("Bytes")) {
               var22 = new StringBuilder("get");
               var22.append(var17.substring(0, var17.length() - 5));
               if (var6.containsKey(var22.toString())) {
                  continue;
               }
            }

            java.lang.reflect.Method var24 = (java.lang.reflect.Method)var11.getValue();
            StringBuilder var19 = new StringBuilder("has");
            var19.append(var17);
            java.lang.reflect.Method var20 = (java.lang.reflect.Method)var8.get(var19.toString());
            if (var24 != null) {
               Object var25 = GeneratedMessageLite.invokeOrDie(var24, var0);
               boolean var5;
               if (var20 == null) {
                  if (!isDefaultValue(var25)) {
                     var5 = true;
                  } else {
                     var5 = false;
                  }
               } else {
                  var5 = (Boolean)GeneratedMessageLite.invokeOrDie(var20, var0);
               }

               if (var5) {
                  printField(var1, var2, var17, var25);
               }
            }
         }
      }

      if (var0 instanceof GeneratedMessageLite.ExtendableMessage) {
         for (Entry var15 : ((GeneratedMessageLite.ExtendableMessage)var0).extensions) {
            StringBuilder var14 = new StringBuilder("[");
            var14.append(((GeneratedMessageLite.ExtensionDescriptor)var15.getKey()).getNumber());
            var14.append("]");
            printField(var1, var2, var14.toString(), var15.getValue());
         }
      }

      GeneratedMessageLite var13 = (GeneratedMessageLite)var0;
      if (var13.unknownFields != null) {
         var13.unknownFields.printWithIndent(var1, var2);
      }
   }

   static String toString(MessageLite var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("# ");
      var2.append(var1);
      reflectivePrintWithIndent(var0, var2, 0);
      return var2.toString();
   }
}
