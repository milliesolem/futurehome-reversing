package com.squareup.javawriter;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.lang.model.element.Modifier;

public final class JavaWriter implements Closeable {
   private static final String INDENT = "  ";
   private static final Pattern TYPE_PATTERN = Pattern.compile("(?:[\\w$]+\\.)*([\\w\\.*$]+)");
   private final Map<String, String> importedTypes = new LinkedHashMap<>();
   private final Writer out;
   private String packagePrefix;
   private final List<JavaWriter.Scope> scopes = new ArrayList<>();

   public JavaWriter(Writer var1) {
      this.out = var1;
   }

   private void checkInMethod() {
      JavaWriter.Scope var1 = this.peekScope();
      if (var1 != JavaWriter.Scope.NON_ABSTRACT_METHOD && var1 != JavaWriter.Scope.CONTROL_FLOW && var1 != JavaWriter.Scope.INITIALIZER) {
         throw new IllegalArgumentException();
      }
   }

   private JavaWriter emitAnnotationValue(Object var1) throws IOException {
      if (var1 instanceof Object[]) {
         this.out.write("{");
         this.pushScope(JavaWriter.Scope.ANNOTATION_ARRAY_VALUE);
         Object[] var5 = (Object[])var1;
         int var4 = var5.length;
         boolean var3 = true;

         for (int var2 = 0; var2 < var4; var2++) {
            var1 = var5[var2];
            if (var3) {
               this.out.write("\n");
               var3 = false;
            } else {
               this.out.write(",\n");
            }

            this.indent();
            this.out.write(var1.toString());
         }

         this.popScope(JavaWriter.Scope.ANNOTATION_ARRAY_VALUE);
         this.out.write("\n");
         this.indent();
         this.out.write("}");
      } else {
         this.out.write(var1.toString());
      }

      return this;
   }

   private void emitModifiers(Set<Modifier> var1) throws IOException {
      Object var2 = var1;
      if (!(var1 instanceof EnumSet)) {
         var2 = EnumSet.copyOf(var1);
      }

      for (Modifier var3 : var2) {
         this.out.append(var3.toString()).append(' ');
      }
   }

   private JavaWriter emitType(String var1) throws IOException {
      this.out.write(this.compressType(var1));
      return this;
   }

   private void hangingIndent() throws IOException {
      int var2 = this.scopes.size();

      for (int var1 = 0; var1 < var2 + 2; var1++) {
         this.out.write("  ");
      }
   }

   private void indent() throws IOException {
      int var2 = this.scopes.size();

      for (int var1 = 0; var1 < var2; var1++) {
         this.out.write("  ");
      }
   }

   private boolean isAmbiguous(String var1) {
      return this.importedTypes.values().contains(var1);
   }

   private boolean isClassInPackage(String var1) {
      if (var1.startsWith(this.packagePrefix)) {
         if (var1.indexOf(46, this.packagePrefix.length()) == -1) {
            return true;
         }

         int var2 = var1.indexOf(46);
         if (var1.substring(var2 + 1, var2 + 2).matches("[A-Z]")) {
            return true;
         }
      }

      return false;
   }

   private static EnumSet<Modifier> modifiersAsSet(int var0) {
      EnumSet var1 = EnumSet.noneOf(Modifier.class);
      if ((var0 & 1) != 0) {
         var1.add(Modifier.PUBLIC);
      }

      if ((var0 & 2) != 0) {
         var1.add(Modifier.PRIVATE);
      }

      if ((var0 & 4) != 0) {
         var1.add(Modifier.PROTECTED);
      }

      if ((var0 & 8) != 0) {
         var1.add(Modifier.STATIC);
      }

      if ((var0 & 16) != 0) {
         var1.add(Modifier.FINAL);
      }

      if ((var0 & 1024) != 0) {
         var1.add(Modifier.ABSTRACT);
      }

      if ((var0 & 32) != 0) {
         var1.add(Modifier.SYNCHRONIZED);
      }

      if ((var0 & 128) != 0) {
         var1.add(Modifier.TRANSIENT);
      }

      if ((var0 & 64) != 0) {
         var1.add(Modifier.VOLATILE);
      }

      return var1;
   }

   private JavaWriter.Scope peekScope() {
      List var1 = this.scopes;
      return (JavaWriter.Scope)var1.get(var1.size() - 1);
   }

   private JavaWriter.Scope popScope() {
      List var1 = this.scopes;
      return (JavaWriter.Scope)var1.remove(var1.size() - 1);
   }

   private void popScope(JavaWriter.Scope var1) {
      List var2 = this.scopes;
      if (var2.remove(var2.size() - 1) != var1) {
         throw new IllegalStateException();
      }
   }

   private void pushScope(JavaWriter.Scope var1) {
      this.scopes.add(var1);
   }

   public static String stringLiteral(String var0) {
      StringBuilder var3 = new StringBuilder("\"");

      for (int var2 = 0; var2 < var0.length(); var2++) {
         char var1 = var0.charAt(var2);
         if (var1 != '\f') {
            if (var1 != '\r') {
               if (var1 != '"') {
                  if (var1 != '\\') {
                     switch (var1) {
                        case '\b':
                           var3.append("\\b");
                           break;
                        case '\t':
                           var3.append("\\t");
                           break;
                        case '\n':
                           var3.append("\\n");
                           break;
                        default:
                           if (Character.isISOControl(var1)) {
                              var3.append(String.format("\\u%04x", Integer.valueOf(var1)));
                           } else {
                              var3.append(var1);
                           }
                     }
                  } else {
                     var3.append("\\\\");
                  }
               } else {
                  var3.append("\\\"");
               }
            } else {
               var3.append("\\r");
            }
         } else {
            var3.append("\\f");
         }
      }

      var3.append('"');
      return var3.toString();
   }

   public static String type(Class<?> var0, String... var1) {
      if (var1.length == 0) {
         return var0.getCanonicalName();
      } else if (var0.getTypeParameters().length != var1.length) {
         throw new IllegalArgumentException();
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append(var0.getCanonicalName());
         var3.append("<");
         var3.append(var1[0]);

         for (int var2 = 1; var2 < var1.length; var2++) {
            var3.append(", ");
            var3.append(var1[var2]);
         }

         var3.append(">");
         return var3.toString();
      }
   }

   public JavaWriter beginControlFlow(String var1) throws IOException {
      this.checkInMethod();
      this.indent();
      this.out.write(var1);
      this.out.write(" {\n");
      this.pushScope(JavaWriter.Scope.CONTROL_FLOW);
      return this;
   }

   public JavaWriter beginInitializer(boolean var1) throws IOException {
      this.indent();
      if (var1) {
         this.out.write("static");
         this.out.write(" {\n");
      } else {
         this.out.write("{\n");
      }

      this.pushScope(JavaWriter.Scope.INITIALIZER);
      return this;
   }

   @Deprecated
   public JavaWriter beginMethod(String var1, String var2, int var3, List<String> var4, List<String> var5) throws IOException {
      return this.beginMethod(var1, var2, modifiersAsSet(var3), var4, var5);
   }

   @Deprecated
   public JavaWriter beginMethod(String var1, String var2, int var3, String... var4) throws IOException {
      return this.beginMethod(var1, var2, modifiersAsSet(var3), Arrays.asList(var4), null);
   }

   public JavaWriter beginMethod(String var1, String var2, Set<Modifier> var3, List<String> var4, List<String> var5) throws IOException {
      this.indent();
      this.emitModifiers(var3);
      if (var1 != null) {
         this.emitType(var1);
         this.out.write(" ");
         this.out.write(var2);
      } else {
         this.emitType(var2);
      }

      this.out.write("(");
      byte var7 = 0;
      if (var4 != null) {
         byte var6 = 0;

         while (true) {
            byte var8 = var6;
            if (var6 >= var4.size()) {
               break;
            }

            if (var6 != 0) {
               this.out.write(", ");
            }

            this.emitType((String)var4.get(var6));
            this.out.write(" ");
            var6 += 2;
            this.emitType((String)var4.get(var8 + 1));
         }
      }

      this.out.write(")");
      if (var5 != null && var5.size() > 0) {
         this.out.write("\n");
         this.indent();
         this.out.write("    throws ");

         for (int var9 = var7; var9 < var5.size(); var9++) {
            if (var9 != 0) {
               this.out.write(", ");
            }

            this.emitType((String)var5.get(var9));
         }
      }

      if (var3.contains(Modifier.ABSTRACT)) {
         this.out.write(";\n");
         this.pushScope(JavaWriter.Scope.ABSTRACT_METHOD);
      } else {
         this.out.write(" {\n");
         this.pushScope(JavaWriter.Scope.NON_ABSTRACT_METHOD);
      }

      return this;
   }

   public JavaWriter beginMethod(String var1, String var2, Set<Modifier> var3, String... var4) throws IOException {
      return this.beginMethod(var1, var2, var3, Arrays.asList(var4), null);
   }

   public JavaWriter beginType(String var1, String var2) throws IOException {
      return this.beginType(var1, var2, EnumSet.noneOf(Modifier.class), null);
   }

   @Deprecated
   public JavaWriter beginType(String var1, String var2, int var3) throws IOException {
      return this.beginType(var1, var2, modifiersAsSet(var3), null);
   }

   @Deprecated
   public JavaWriter beginType(String var1, String var2, int var3, String var4, String... var5) throws IOException {
      return this.beginType(var1, var2, modifiersAsSet(var3), var4, var5);
   }

   public JavaWriter beginType(String var1, String var2, Set<Modifier> var3) throws IOException {
      return this.beginType(var1, var2, var3, null);
   }

   public JavaWriter beginType(String var1, String var2, Set<Modifier> var3, String var4, String... var5) throws IOException {
      this.indent();
      this.emitModifiers(var3);
      this.out.write(var2);
      this.out.write(" ");
      this.emitType(var1);
      if (var4 != null) {
         this.out.write(" extends ");
         this.emitType(var4);
      }

      if (var5.length > 0) {
         this.out.write("\n");
         this.indent();
         this.out.write("    implements ");

         for (int var6 = 0; var6 < var5.length; var6++) {
            if (var6 != 0) {
               this.out.write(", ");
            }

            this.emitType(var5[var6]);
         }
      }

      this.out.write(" {\n");
      this.pushScope(JavaWriter.Scope.TYPE_DECLARATION);
      return this;
   }

   @Override
   public void close() throws IOException {
      this.out.close();
   }

   public String compressType(String var1) {
      StringBuilder var6 = new StringBuilder();
      if (this.packagePrefix != null) {
         Matcher var7 = TYPE_PATTERN.matcher(var1);
         int var2 = 0;

         while (true) {
            boolean var4 = var7.find(var2);
            int var3;
            if (var4) {
               var3 = var7.start();
            } else {
               var3 = var1.length();
            }

            var6.append(var1, var2, var3);
            if (!var4) {
               return var6.toString();
            }

            String var5 = var7.group(0);
            String var8 = this.importedTypes.get(var5);
            if (var8 != null) {
               var6.append(var8);
            } else if (this.isClassInPackage(var5)) {
               var8 = var5.substring(this.packagePrefix.length());
               if (this.isAmbiguous(var8)) {
                  var6.append(var5);
               } else {
                  var6.append(var8);
               }
            } else if (var5.startsWith("java.lang.")) {
               var6.append(var5.substring(10));
            } else {
               var6.append(var5);
            }

            var2 = var7.end();
         }
      } else {
         throw new IllegalStateException();
      }
   }

   public JavaWriter emitAnnotation(Class<? extends Annotation> var1) throws IOException {
      return this.emitAnnotation(type(var1), Collections.emptyMap());
   }

   public JavaWriter emitAnnotation(Class<? extends Annotation> var1, Object var2) throws IOException {
      return this.emitAnnotation(type(var1), var2);
   }

   public JavaWriter emitAnnotation(Class<? extends Annotation> var1, Map<String, ?> var2) throws IOException {
      return this.emitAnnotation(type(var1), var2);
   }

   public JavaWriter emitAnnotation(String var1) throws IOException {
      return this.emitAnnotation(var1, Collections.emptyMap());
   }

   public JavaWriter emitAnnotation(String var1, Object var2) throws IOException {
      this.indent();
      this.out.write("@");
      this.emitType(var1);
      this.out.write("(");
      this.emitAnnotationValue(var2);
      this.out.write(")");
      this.out.write("\n");
      return this;
   }

   public JavaWriter emitAnnotation(String var1, Map<String, ?> var2) throws IOException {
      this.indent();
      this.out.write("@");
      this.emitType(var1);
      int var4 = var2.size();
      label29:
      if (var4 != 0) {
         boolean var3 = true;
         if (var4 == 1) {
            Entry var5 = (Entry)var2.entrySet().iterator().next();
            if ("value".equals(var5.getKey())) {
               this.out.write("(");
               this.emitAnnotationValue(var5.getValue());
               this.out.write(")");
               break label29;
            }
         }

         this.out.write("(");
         this.pushScope(JavaWriter.Scope.ANNOTATION_ATTRIBUTE);

         for (Entry var7 : var2.entrySet()) {
            if (var3) {
               this.out.write("\n");
               var3 = false;
            } else {
               this.out.write(",\n");
            }

            this.indent();
            this.out.write((String)var7.getKey());
            this.out.write(" = ");
            this.emitAnnotationValue(var7.getValue());
         }

         this.popScope(JavaWriter.Scope.ANNOTATION_ATTRIBUTE);
         this.out.write("\n");
         this.indent();
         this.out.write(")");
      }

      this.out.write("\n");
      return this;
   }

   public JavaWriter emitEmptyLine() throws IOException {
      this.out.write("\n");
      return this;
   }

   public JavaWriter emitEnumValue(String var1) throws IOException {
      this.indent();
      this.out.write(var1);
      this.out.write(",\n");
      return this;
   }

   public JavaWriter emitField(String var1, String var2) throws IOException {
      return this.emitField(var1, var2, EnumSet.noneOf(Modifier.class), null);
   }

   @Deprecated
   public JavaWriter emitField(String var1, String var2, int var3) throws IOException {
      return this.emitField(var1, var2, modifiersAsSet(var3), null);
   }

   @Deprecated
   public JavaWriter emitField(String var1, String var2, int var3, String var4) throws IOException {
      return this.emitField(var1, var2, modifiersAsSet(var3), var4);
   }

   public JavaWriter emitField(String var1, String var2, Set<Modifier> var3) throws IOException {
      return this.emitField(var1, var2, var3, null);
   }

   public JavaWriter emitField(String var1, String var2, Set<Modifier> var3, String var4) throws IOException {
      this.indent();
      this.emitModifiers(var3);
      this.emitType(var1);
      this.out.write(" ");
      this.out.write(var2);
      if (var4 != null) {
         this.out.write(" = ");
         this.out.write(var4);
      }

      this.out.write(";\n");
      return this;
   }

   public JavaWriter emitImports(Collection<String> var1) throws IOException {
      for (String var4 : new TreeSet(var1)) {
         Matcher var2 = TYPE_PATTERN.matcher(var4);
         if (!var2.matches()) {
            throw new IllegalArgumentException(var4);
         }

         if (this.importedTypes.put(var4, var2.group(1)) != null) {
            throw new IllegalArgumentException(var4);
         }

         this.out.write("import ");
         this.out.write(var4);
         this.out.write(";\n");
      }

      return this;
   }

   public JavaWriter emitImports(String... var1) throws IOException {
      return this.emitImports(Arrays.asList(var1));
   }

   public JavaWriter emitJavadoc(String var1, Object... var2) throws IOException {
      var1 = String.format(var1, var2);
      this.indent();
      this.out.write("/**\n");

      for (String var7 : var1.split("\n")) {
         this.indent();
         this.out.write(" * ");
         this.out.write(var7);
         this.out.write("\n");
      }

      this.indent();
      this.out.write(" */\n");
      return this;
   }

   public JavaWriter emitPackage(String var1) throws IOException {
      if (this.packagePrefix == null) {
         if (var1.isEmpty()) {
            this.packagePrefix = "";
         } else {
            this.out.write("package ");
            this.out.write(var1);
            this.out.write(";\n\n");
            StringBuilder var2 = new StringBuilder();
            var2.append(var1);
            var2.append(".");
            this.packagePrefix = var2.toString();
         }

         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   public JavaWriter emitSingleLineComment(String var1, Object... var2) throws IOException {
      this.indent();
      this.out.write("// ");
      this.out.write(String.format(var1, var2));
      this.out.write("\n");
      return this;
   }

   public JavaWriter emitStatement(String var1, Object... var2) throws IOException {
      this.checkInMethod();
      String[] var4 = String.format(var1, var2).split("\n", -1);
      this.indent();
      this.out.write(var4[0]);

      for (int var3 = 1; var3 < var4.length; var3++) {
         this.out.write("\n");
         this.hangingIndent();
         this.out.write(var4[var3]);
      }

      this.out.write(";\n");
      return this;
   }

   public JavaWriter emitStaticImports(Collection<String> var1) throws IOException {
      for (String var4 : new TreeSet(var1)) {
         Matcher var2 = TYPE_PATTERN.matcher(var4);
         if (!var2.matches()) {
            throw new IllegalArgumentException(var4);
         }

         if (this.importedTypes.put(var4, var2.group(1)) != null) {
            throw new IllegalArgumentException(var4);
         }

         this.out.write("import static ");
         this.out.write(var4);
         this.out.write(";\n");
      }

      return this;
   }

   public JavaWriter emitStaticImports(String... var1) throws IOException {
      return this.emitStaticImports(Arrays.asList(var1));
   }

   public JavaWriter endControlFlow() throws IOException {
      return this.endControlFlow(null);
   }

   public JavaWriter endControlFlow(String var1) throws IOException {
      this.popScope(JavaWriter.Scope.CONTROL_FLOW);
      this.indent();
      if (var1 != null) {
         this.out.write("} ");
         this.out.write(var1);
         this.out.write(";\n");
      } else {
         this.out.write("}\n");
      }

      return this;
   }

   public JavaWriter endInitializer() throws IOException {
      this.popScope(JavaWriter.Scope.INITIALIZER);
      this.indent();
      this.out.write("}\n");
      return this;
   }

   public JavaWriter endMethod() throws IOException {
      JavaWriter.Scope var1 = this.popScope();
      if (var1 == JavaWriter.Scope.NON_ABSTRACT_METHOD) {
         this.indent();
         this.out.write("}\n");
      } else if (var1 != JavaWriter.Scope.ABSTRACT_METHOD) {
         throw new IllegalStateException();
      }

      return this;
   }

   public JavaWriter endType() throws IOException {
      this.popScope(JavaWriter.Scope.TYPE_DECLARATION);
      this.indent();
      this.out.write("}\n");
      return this;
   }

   public JavaWriter nextControlFlow(String var1) throws IOException {
      this.popScope(JavaWriter.Scope.CONTROL_FLOW);
      this.indent();
      this.pushScope(JavaWriter.Scope.CONTROL_FLOW);
      this.out.write("} ");
      this.out.write(var1);
      this.out.write(" {\n");
      return this;
   }

   private static enum Scope {
      ABSTRACT_METHOD,
      ANNOTATION_ARRAY_VALUE,
      ANNOTATION_ATTRIBUTE,
      CONTROL_FLOW,
      INITIALIZER,
      NON_ABSTRACT_METHOD,
      TYPE_DECLARATION;
      private static final JavaWriter.Scope[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         JavaWriter.Scope var0 = new JavaWriter.Scope();
         TYPE_DECLARATION = var0;
         JavaWriter.Scope var3 = new JavaWriter.Scope();
         ABSTRACT_METHOD = var3;
         JavaWriter.Scope var4 = new JavaWriter.Scope();
         NON_ABSTRACT_METHOD = var4;
         JavaWriter.Scope var5 = new JavaWriter.Scope();
         CONTROL_FLOW = var5;
         JavaWriter.Scope var1 = new JavaWriter.Scope();
         ANNOTATION_ATTRIBUTE = var1;
         JavaWriter.Scope var6 = new JavaWriter.Scope();
         ANNOTATION_ARRAY_VALUE = var6;
         JavaWriter.Scope var2 = new JavaWriter.Scope();
         INITIALIZER = var2;
         $VALUES = new JavaWriter.Scope[]{var0, var3, var4, var5, var1, var6, var2};
      }
   }
}
