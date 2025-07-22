package io.sentry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public final class JsonObjectDeserializer {
   private final ArrayList<JsonObjectDeserializer.Token> tokens = new ArrayList<>();

   private JsonObjectDeserializer.Token getCurrentToken() {
      if (this.tokens.isEmpty()) {
         return null;
      } else {
         ArrayList var1 = this.tokens;
         return (JsonObjectDeserializer.Token)var1.get(var1.size() - 1);
      }
   }

   private boolean handleArrayOrMapEnd() {
      if (this.hasOneToken()) {
         return true;
      } else {
         JsonObjectDeserializer.Token var1 = this.getCurrentToken();
         this.popCurrentToken();
         if (this.getCurrentToken() instanceof JsonObjectDeserializer.TokenName) {
            JsonObjectDeserializer.TokenName var2 = (JsonObjectDeserializer.TokenName)this.getCurrentToken();
            this.popCurrentToken();
            JsonObjectDeserializer.TokenMap var3 = (JsonObjectDeserializer.TokenMap)this.getCurrentToken();
            if (var2 != null && var1 != null && var3 != null) {
               var3.value.put(var2.value, var1.getValue());
            }
         } else if (this.getCurrentToken() instanceof JsonObjectDeserializer.TokenArray) {
            JsonObjectDeserializer.TokenArray var4 = (JsonObjectDeserializer.TokenArray)this.getCurrentToken();
            if (var1 != null && var4 != null) {
               var4.value.add(var1.getValue());
            }
         }

         return false;
      }
   }

   private boolean handlePrimitive(JsonObjectDeserializer.NextValue var1) throws IOException {
      Object var2 = var1.nextValue();
      if (this.getCurrentToken() == null && var2 != null) {
         this.pushCurrentToken(new JsonObjectDeserializer.TokenPrimitive(var2));
         return true;
      } else {
         if (this.getCurrentToken() instanceof JsonObjectDeserializer.TokenName) {
            JsonObjectDeserializer.TokenName var3 = (JsonObjectDeserializer.TokenName)this.getCurrentToken();
            this.popCurrentToken();
            ((JsonObjectDeserializer.TokenMap)this.getCurrentToken()).value.put(var3.value, var2);
         } else if (this.getCurrentToken() instanceof JsonObjectDeserializer.TokenArray) {
            ((JsonObjectDeserializer.TokenArray)this.getCurrentToken()).value.add(var2);
         }

         return false;
      }
   }

   private boolean hasOneToken() {
      int var1 = this.tokens.size();
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   private Object nextNumber(JsonObjectReader var1) throws IOException {
      int var4;
      try {
         var4 = var1.nextInt();
      } catch (Exception var7) {
         double var2;
         try {
            var2 = var1.nextDouble();
         } catch (Exception var6) {
            return var1.nextLong();
         }

         return var2;
      }

      return var4;
   }

   private void parse(JsonObjectReader var1) throws IOException {
      boolean var2;
      label25: {
         switch (<unrepresentable>.$SwitchMap$io$sentry$vendor$gson$stream$JsonToken[var1.peek().ordinal()]) {
            case 1:
               var1.beginArray();
               this.pushCurrentToken(new JsonObjectDeserializer.TokenArray());
               break;
            case 2:
               var1.endArray();
               var2 = this.handleArrayOrMapEnd();
               break label25;
            case 3:
               var1.beginObject();
               this.pushCurrentToken(new JsonObjectDeserializer.TokenMap());
               break;
            case 4:
               var1.endObject();
               var2 = this.handleArrayOrMapEnd();
               break label25;
            case 5:
               this.pushCurrentToken(new JsonObjectDeserializer.TokenName(var1.nextName()));
               break;
            case 6:
               var2 = this.handlePrimitive(new JsonObjectDeserializer$$ExternalSyntheticLambda0(var1));
               break label25;
            case 7:
               var2 = this.handlePrimitive(new JsonObjectDeserializer$$ExternalSyntheticLambda1(this, var1));
               break label25;
            case 8:
               var2 = this.handlePrimitive(new JsonObjectDeserializer$$ExternalSyntheticLambda2(var1));
               break label25;
            case 9:
               var1.nextNull();
               var2 = this.handlePrimitive(new JsonObjectDeserializer$$ExternalSyntheticLambda3());
               break label25;
            case 10:
               var2 = true;
               break label25;
         }

         var2 = false;
      }

      if (!var2) {
         this.parse(var1);
      }
   }

   private void popCurrentToken() {
      if (!this.tokens.isEmpty()) {
         ArrayList var1 = this.tokens;
         var1.remove(var1.size() - 1);
      }
   }

   private void pushCurrentToken(JsonObjectDeserializer.Token var1) {
      this.tokens.add(var1);
   }

   public Object deserialize(JsonObjectReader var1) throws IOException {
      this.parse(var1);
      JsonObjectDeserializer.Token var2 = this.getCurrentToken();
      return var2 != null ? var2.getValue() : null;
   }

   private interface NextValue {
      Object nextValue() throws IOException;
   }

   private interface Token {
      Object getValue();
   }

   private static final class TokenArray implements JsonObjectDeserializer.Token {
      final ArrayList<Object> value = new ArrayList<>();

      private TokenArray() {
      }

      @Override
      public Object getValue() {
         return this.value;
      }
   }

   private static final class TokenMap implements JsonObjectDeserializer.Token {
      final HashMap<String, Object> value = new HashMap<>();

      private TokenMap() {
      }

      @Override
      public Object getValue() {
         return this.value;
      }
   }

   private static final class TokenName implements JsonObjectDeserializer.Token {
      final String value;

      TokenName(String var1) {
         this.value = var1;
      }

      @Override
      public Object getValue() {
         return this.value;
      }
   }

   private static final class TokenPrimitive implements JsonObjectDeserializer.Token {
      final Object value;

      TokenPrimitive(Object var1) {
         this.value = var1;
      }

      @Override
      public Object getValue() {
         return this.value;
      }
   }
}
