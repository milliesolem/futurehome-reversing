package org.hamcrest;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.hamcrest.collection.IsArray;
import org.hamcrest.collection.IsArrayContaining;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.hamcrest.collection.IsArrayContainingInOrder;
import org.hamcrest.collection.IsArrayWithSize;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.collection.IsIn;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.collection.IsIterableWithSize;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.CombinableMatcher;
import org.hamcrest.core.DescribedAs;
import org.hamcrest.core.Every;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringEndsWith;
import org.hamcrest.core.StringStartsWith;
import org.hamcrest.number.BigDecimalCloseTo;
import org.hamcrest.number.IsCloseTo;
import org.hamcrest.number.OrderingComparison;
import org.hamcrest.object.HasToString;
import org.hamcrest.object.IsCompatibleType;
import org.hamcrest.object.IsEventFrom;
import org.hamcrest.text.IsEmptyString;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.hamcrest.text.IsEqualIgnoringWhiteSpace;
import org.hamcrest.text.StringContainsInOrder;
import org.hamcrest.xml.HasXPath;
import org.w3c.dom.Node;

public class Matchers {
   public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> var0) {
      return AllOf.allOf(var0);
   }

   public static <T> Matcher<T> allOf(Matcher<? super T> var0, Matcher<? super T> var1) {
      return AllOf.allOf(var0, var1);
   }

   public static <T> Matcher<T> allOf(Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2) {
      return AllOf.allOf(var0, var1, var2);
   }

   public static <T> Matcher<T> allOf(Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3) {
      return AllOf.allOf(var0, var1, var2, var3);
   }

   public static <T> Matcher<T> allOf(
      Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4
   ) {
      return AllOf.allOf(var0, var1, var2, var3, var4);
   }

   public static <T> Matcher<T> allOf(
      Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4, Matcher<? super T> var5
   ) {
      return AllOf.allOf(var0, var1, var2, var3, var4, var5);
   }

   public static <T> Matcher<T> allOf(Matcher<? super T>... var0) {
      return AllOf.allOf(var0);
   }

   public static <T> Matcher<T> any(Class<T> var0) {
      return IsInstanceOf.any(var0);
   }

   public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> var0) {
      return AnyOf.anyOf(var0);
   }

   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1) {
      return AnyOf.anyOf(var0, var1);
   }

   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2) {
      return AnyOf.anyOf(var0, var1, var2);
   }

   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3) {
      return AnyOf.anyOf(var0, var1, var2, var3);
   }

   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4) {
      return AnyOf.anyOf(var0, var1, var2, var3, var4);
   }

   public static <T> AnyOf<T> anyOf(
      Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4, Matcher<? super T> var5
   ) {
      return AnyOf.anyOf(var0, var1, var2, var3, var4, var5);
   }

   public static <T> AnyOf<T> anyOf(Matcher<? super T>... var0) {
      return AnyOf.anyOf(var0);
   }

   public static Matcher<Object> anything() {
      return IsAnything.anything();
   }

   public static Matcher<Object> anything(String var0) {
      return IsAnything.anything(var0);
   }

   public static <T> IsArray<T> array(Matcher<? super T>... var0) {
      return IsArray.array(var0);
   }

   public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> var0) {
      return IsArrayContainingInOrder.arrayContaining(var0);
   }

   public static <E> Matcher<E[]> arrayContaining(E... var0) {
      return IsArrayContainingInOrder.arrayContaining((E[])var0);
   }

   public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... var0) {
      return IsArrayContainingInOrder.arrayContaining(var0);
   }

   public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> var0) {
      return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(var0);
   }

   public static <E> Matcher<E[]> arrayContainingInAnyOrder(E... var0) {
      return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder((E[])var0);
   }

   public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... var0) {
      return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(var0);
   }

   public static <E> Matcher<E[]> arrayWithSize(int var0) {
      return IsArrayWithSize.arrayWithSize(var0);
   }

   public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> var0) {
      return IsArrayWithSize.arrayWithSize(var0);
   }

   public static <LHS> CombinableMatcher.CombinableBothMatcher<LHS> both(Matcher<? super LHS> var0) {
      return CombinableMatcher.both(var0);
   }

   public static Matcher<Double> closeTo(double var0, double var2) {
      return IsCloseTo.closeTo(var0, var2);
   }

   public static Matcher<BigDecimal> closeTo(BigDecimal var0, BigDecimal var1) {
      return BigDecimalCloseTo.closeTo(var0, var1);
   }

   public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T var0) {
      return OrderingComparison.comparesEqualTo((T)var0);
   }

   public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> var0) {
      return IsIterableContainingInOrder.contains(var0);
   }

   public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> var0) {
      return IsIterableContainingInOrder.contains(var0);
   }

   public static <E> Matcher<Iterable<? extends E>> contains(E... var0) {
      return IsIterableContainingInOrder.contains((E[])var0);
   }

   public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... var0) {
      return IsIterableContainingInOrder.contains(var0);
   }

   public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> var0) {
      return IsIterableContainingInAnyOrder.containsInAnyOrder(var0);
   }

   public static <E> Matcher<Iterable<? extends E>> containsInAnyOrder(Matcher<? super E> var0) {
      return IsIterableContainingInAnyOrder.containsInAnyOrder(var0);
   }

   public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... var0) {
      return IsIterableContainingInAnyOrder.containsInAnyOrder((T[])var0);
   }

   public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... var0) {
      return IsIterableContainingInAnyOrder.containsInAnyOrder(var0);
   }

   public static Matcher<String> containsString(String var0) {
      return StringContains.containsString(var0);
   }

   public static <T> Matcher<T> describedAs(String var0, Matcher<T> var1, Object... var2) {
      return DescribedAs.describedAs(var0, var1, var2);
   }

   public static <LHS> CombinableMatcher.CombinableEitherMatcher<LHS> either(Matcher<? super LHS> var0) {
      return CombinableMatcher.either(var0);
   }

   public static <E> Matcher<Collection<? extends E>> empty() {
      return IsEmptyCollection.empty();
   }

   public static <E> Matcher<E[]> emptyArray() {
      return IsArrayWithSize.emptyArray();
   }

   public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> var0) {
      return IsEmptyCollection.emptyCollectionOf(var0);
   }

   public static <E> Matcher<Iterable<? extends E>> emptyIterable() {
      return IsEmptyIterable.emptyIterable();
   }

   public static <E> Matcher<Iterable<E>> emptyIterableOf(Class<E> var0) {
      return IsEmptyIterable.emptyIterableOf(var0);
   }

   public static Matcher<String> endsWith(String var0) {
      return StringEndsWith.endsWith(var0);
   }

   public static <T> Matcher<T> equalTo(T var0) {
      return IsEqual.equalTo((T)var0);
   }

   public static Matcher<String> equalToIgnoringCase(String var0) {
      return IsEqualIgnoringCase.equalToIgnoringCase(var0);
   }

   public static Matcher<String> equalToIgnoringWhiteSpace(String var0) {
      return IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(var0);
   }

   public static Matcher<EventObject> eventFrom(Class<? extends EventObject> var0, Object var1) {
      return IsEventFrom.eventFrom(var0, var1);
   }

   public static Matcher<EventObject> eventFrom(Object var0) {
      return IsEventFrom.eventFrom(var0);
   }

   public static <U> Matcher<Iterable<U>> everyItem(Matcher<U> var0) {
      return Every.everyItem(var0);
   }

   public static <T extends Comparable<T>> Matcher<T> greaterThan(T var0) {
      return OrderingComparison.greaterThan((T)var0);
   }

   public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T var0) {
      return OrderingComparison.greaterThanOrEqualTo((T)var0);
   }

   public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(K var0, V var1) {
      return IsMapContaining.hasEntry((K)var0, (V)var1);
   }

   public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(Matcher<? super K> var0, Matcher<? super V> var1) {
      return IsMapContaining.hasEntry(var0, var1);
   }

   public static <T> Matcher<Iterable<? super T>> hasItem(T var0) {
      return IsCollectionContaining.hasItem((T)var0);
   }

   public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> var0) {
      return IsCollectionContaining.hasItem(var0);
   }

   public static <T> Matcher<T[]> hasItemInArray(T var0) {
      return IsArrayContaining.hasItemInArray((T)var0);
   }

   public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> var0) {
      return IsArrayContaining.hasItemInArray(var0);
   }

   public static <T> Matcher<Iterable<T>> hasItems(T... var0) {
      return IsCollectionContaining.hasItems((T[])var0);
   }

   public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... var0) {
      return IsCollectionContaining.hasItems(var0);
   }

   public static <K> Matcher<Map<? extends K, ?>> hasKey(K var0) {
      return IsMapContaining.hasKey((K)var0);
   }

   public static <K> Matcher<Map<? extends K, ?>> hasKey(Matcher<? super K> var0) {
      return IsMapContaining.hasKey(var0);
   }

   public static <T> Matcher<T> hasProperty(String var0) {
      return HasProperty.hasProperty(var0);
   }

   public static <T> Matcher<T> hasProperty(String var0, Matcher<?> var1) {
      return HasPropertyWithValue.hasProperty(var0, var1);
   }

   public static <E> Matcher<Collection<? extends E>> hasSize(int var0) {
      return IsCollectionWithSize.hasSize(var0);
   }

   public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> var0) {
      return IsCollectionWithSize.hasSize(var0);
   }

   public static <T> Matcher<T> hasToString(String var0) {
      return HasToString.hasToString(var0);
   }

   public static <T> Matcher<T> hasToString(Matcher<? super String> var0) {
      return HasToString.hasToString(var0);
   }

   public static <V> Matcher<Map<?, ? extends V>> hasValue(V var0) {
      return IsMapContaining.hasValue((V)var0);
   }

   public static <V> Matcher<Map<?, ? extends V>> hasValue(Matcher<? super V> var0) {
      return IsMapContaining.hasValue(var0);
   }

   public static Matcher<Node> hasXPath(String var0) {
      return HasXPath.hasXPath(var0);
   }

   public static Matcher<Node> hasXPath(String var0, NamespaceContext var1) {
      return HasXPath.hasXPath(var0, var1);
   }

   public static Matcher<Node> hasXPath(String var0, NamespaceContext var1, Matcher<String> var2) {
      return HasXPath.hasXPath(var0, var1, var2);
   }

   public static Matcher<Node> hasXPath(String var0, Matcher<String> var1) {
      return HasXPath.hasXPath(var0, var1);
   }

   public static <T> Matcher<T> instanceOf(Class<?> var0) {
      return IsInstanceOf.instanceOf(var0);
   }

   public static <T> Matcher<T> is(Class<T> var0) {
      return Is.is(var0);
   }

   public static <T> Matcher<T> is(T var0) {
      return Is.is((T)var0);
   }

   public static <T> Matcher<T> is(Matcher<T> var0) {
      return Is.is(var0);
   }

   public static <T> Matcher<T> isA(Class<T> var0) {
      return Is.isA(var0);
   }

   public static Matcher<String> isEmptyOrNullString() {
      return IsEmptyString.isEmptyOrNullString();
   }

   public static Matcher<String> isEmptyString() {
      return IsEmptyString.isEmptyString();
   }

   public static <T> Matcher<T> isIn(Collection<T> var0) {
      return IsIn.isIn(var0);
   }

   public static <T> Matcher<T> isIn(T[] var0) {
      return IsIn.isIn((T[])var0);
   }

   public static <T> Matcher<T> isOneOf(T... var0) {
      return IsIn.isOneOf((T[])var0);
   }

   public static <E> Matcher<Iterable<E>> iterableWithSize(int var0) {
      return IsIterableWithSize.iterableWithSize(var0);
   }

   public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> var0) {
      return IsIterableWithSize.iterableWithSize(var0);
   }

   public static <T extends Comparable<T>> Matcher<T> lessThan(T var0) {
      return OrderingComparison.lessThan((T)var0);
   }

   public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T var0) {
      return OrderingComparison.lessThanOrEqualTo((T)var0);
   }

   public static <T> Matcher<T> not(T var0) {
      return IsNot.not((T)var0);
   }

   public static <T> Matcher<T> not(Matcher<T> var0) {
      return IsNot.not(var0);
   }

   public static Matcher<Object> notNullValue() {
      return IsNull.notNullValue();
   }

   public static <T> Matcher<T> notNullValue(Class<T> var0) {
      return IsNull.notNullValue(var0);
   }

   public static Matcher<Object> nullValue() {
      return IsNull.nullValue();
   }

   public static <T> Matcher<T> nullValue(Class<T> var0) {
      return IsNull.nullValue(var0);
   }

   public static <T> Matcher<T> sameInstance(T var0) {
      return IsSame.sameInstance((T)var0);
   }

   public static <T> Matcher<T> samePropertyValuesAs(T var0) {
      return SamePropertyValuesAs.samePropertyValuesAs((T)var0);
   }

   public static Matcher<String> startsWith(String var0) {
      return StringStartsWith.startsWith(var0);
   }

   public static Matcher<String> stringContainsInOrder(Iterable<String> var0) {
      return StringContainsInOrder.stringContainsInOrder(var0);
   }

   public static <T> Matcher<T> theInstance(T var0) {
      return IsSame.theInstance((T)var0);
   }

   public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> var0) {
      return IsCompatibleType.typeCompatibleWith(var0);
   }
}
