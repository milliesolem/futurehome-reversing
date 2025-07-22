package org.hamcrest.xml;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsAnything;
import org.w3c.dom.Node;

public class HasXPath extends TypeSafeDiagnosingMatcher<Node> {
   private static final Condition.Step<Object, String> NODE_EXISTS = nodeExists();
   public static final NamespaceContext NO_NAMESPACE_CONTEXT;
   private static final IsAnything<String> WITH_ANY_CONTENT = new IsAnything<>("");
   private final XPathExpression compiledXPath;
   private final QName evaluationMode;
   private final Matcher<String> valueMatcher;
   private final String xpathString;

   public HasXPath(String var1, NamespaceContext var2, Matcher<String> var3) {
      this(var1, var2, var3, XPathConstants.STRING);
   }

   private HasXPath(String var1, NamespaceContext var2, Matcher<String> var3, QName var4) {
      this.compiledXPath = compiledXPath(var1, var2);
      this.xpathString = var1;
      this.valueMatcher = var3;
      this.evaluationMode = var4;
   }

   public HasXPath(String var1, Matcher<String> var2) {
      this(var1, NO_NAMESPACE_CONTEXT, var2);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static XPathExpression compiledXPath(String var0, NamespaceContext var1) {
      XPath var6;
      try {
         var6 = XPathFactory.newInstance().newXPath();
      } catch (XPathExpressionException var5) {
         StringBuilder var2 = new StringBuilder("Invalid XPath : ");
         var2.append(var0);
         throw new IllegalArgumentException(var2.toString(), var5);
      }

      if (var1 != null) {
         try {
            var6.setNamespaceContext(var1);
         } catch (XPathExpressionException var4) {
            StringBuilder var7 = new StringBuilder("Invalid XPath : ");
            var7.append(var0);
            throw new IllegalArgumentException(var7.toString(), var4);
         }
      }

      try {
         return var6.compile(var0);
      } catch (XPathExpressionException var3) {
         StringBuilder var8 = new StringBuilder("Invalid XPath : ");
         var8.append(var0);
         throw new IllegalArgumentException(var8.toString(), var3);
      }
   }

   private Condition<Object> evaluated(Node var1, Description var2) {
      try {
         return Condition.matched(this.compiledXPath.evaluate(var1, this.evaluationMode), var2);
      } catch (XPathExpressionException var3) {
         var2.appendText(var3.getMessage());
         return Condition.notMatched();
      }
   }

   @Factory
   public static Matcher<Node> hasXPath(String var0) {
      return hasXPath(var0, NO_NAMESPACE_CONTEXT);
   }

   @Factory
   public static Matcher<Node> hasXPath(String var0, NamespaceContext var1) {
      return new HasXPath(var0, var1, WITH_ANY_CONTENT, XPathConstants.NODE);
   }

   @Factory
   public static Matcher<Node> hasXPath(String var0, NamespaceContext var1, Matcher<String> var2) {
      return new HasXPath(var0, var1, var2, XPathConstants.STRING);
   }

   @Factory
   public static Matcher<Node> hasXPath(String var0, Matcher<String> var1) {
      return hasXPath(var0, NO_NAMESPACE_CONTEXT, var1);
   }

   private static Condition.Step<Object, String> nodeExists() {
      return new Condition.Step<Object, String>() {
         @Override
         public Condition<String> apply(Object var1, Description var2) {
            if (var1 == null) {
               var2.appendText("xpath returned no results.");
               return Condition.notMatched();
            } else {
               return Condition.matched(String.valueOf(var1), var2);
            }
         }
      };
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("an XML document with XPath ").appendText(this.xpathString);
      if (this.valueMatcher != null) {
         var1.appendText(" ").appendDescriptionOf(this.valueMatcher);
      }
   }

   public boolean matchesSafely(Node var1, Description var2) {
      return this.evaluated(var1, var2).and(NODE_EXISTS).matching(this.valueMatcher);
   }
}
