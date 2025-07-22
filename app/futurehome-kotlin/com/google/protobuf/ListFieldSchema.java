package com.google.protobuf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CheckReturnValue
abstract class ListFieldSchema {
   private static final ListFieldSchema FULL_INSTANCE = new ListFieldSchema.ListFieldSchemaFull();
   private static final ListFieldSchema LITE_INSTANCE = new ListFieldSchema.ListFieldSchemaLite();

   private ListFieldSchema() {
   }

   static ListFieldSchema full() {
      return FULL_INSTANCE;
   }

   static ListFieldSchema lite() {
      return LITE_INSTANCE;
   }

   abstract void makeImmutableListAt(Object var1, long var2);

   abstract <L> void mergeListsAt(Object var1, Object var2, long var3);

   abstract <L> List<L> mutableListAt(Object var1, long var2);

   private static final class ListFieldSchemaFull extends ListFieldSchema {
      private static final Class<?> UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(Collections.emptyList()).getClass();

      private ListFieldSchemaFull() {
      }

      static <E> List<E> getList(Object var0, long var1) {
         return (List<E>)UnsafeUtil.getObject(var0, var1);
      }

      private static <L> List<L> mutableListAt(Object var0, long var1, int var3) {
         List var5 = getList(var0, var1);
         Object var4;
         if (var5.isEmpty()) {
            if (var5 instanceof LazyStringList) {
               var4 = new LazyStringArrayList(var3);
            } else if (var5 instanceof PrimitiveNonBoxingCollection && var5 instanceof Internal.ProtobufList) {
               var4 = ((Internal.ProtobufList)var5).mutableCopyWithCapacity(var3);
            } else {
               var4 = new ArrayList(var3);
            }

            UnsafeUtil.putObject(var0, var1, var4);
         } else {
            if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(var5.getClass())) {
               var4 = new ArrayList(var5.size() + var3);
               var4.addAll(var5);
               UnsafeUtil.putObject(var0, var1, var4);
               var0 = var4;
            } else {
               if (!(var5 instanceof UnmodifiableLazyStringList)) {
                  var4 = var5;
                  if (var5 instanceof PrimitiveNonBoxingCollection) {
                     var4 = var5;
                     if (var5 instanceof Internal.ProtobufList) {
                        Internal.ProtobufList var6 = (Internal.ProtobufList)var5;
                        var4 = var5;
                        if (!var6.isModifiable()) {
                           var4 = var6.mutableCopyWithCapacity(var5.size() + var3);
                           UnsafeUtil.putObject(var0, var1, var4);
                        }

                        return var4;
                     }
                  }

                  return var4;
               }

               LazyStringArrayList var9 = new LazyStringArrayList(var5.size() + var3);
               var9.addAll(var5);
               UnsafeUtil.putObject(var0, var1, var9);
               var0 = var9;
            }

            var4 = (List)var0;
         }

         return var4;
      }

      @Override
      void makeImmutableListAt(Object var1, long var2) {
         List var4 = (List)UnsafeUtil.getObject(var1, var2);
         if (var4 instanceof LazyStringList) {
            var4 = ((LazyStringList)var4).getUnmodifiableView();
         } else {
            if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(var4.getClass())) {
               return;
            }

            if (var4 instanceof PrimitiveNonBoxingCollection && var4 instanceof Internal.ProtobufList) {
               var1 = (Internal.ProtobufList)var4;
               if (var1.isModifiable()) {
                  var1.makeImmutable();
               }

               return;
            }

            var4 = Collections.unmodifiableList(var4);
         }

         UnsafeUtil.putObject(var1, var2, var4);
      }

      @Override
      <E> void mergeListsAt(Object var1, Object var2, long var3) {
         var2 = getList(var2, var3);
         List var7 = mutableListAt(var1, var3, var2.size());
         int var6 = var7.size();
         int var5 = var2.size();
         if (var6 > 0 && var5 > 0) {
            var7.addAll(var2);
         }

         if (var6 > 0) {
            var2 = var7;
         }

         UnsafeUtil.putObject(var1, var3, var2);
      }

      @Override
      <L> List<L> mutableListAt(Object var1, long var2) {
         return mutableListAt(var1, var2, 10);
      }
   }

   private static final class ListFieldSchemaLite extends ListFieldSchema {
      private ListFieldSchemaLite() {
      }

      static <E> Internal.ProtobufList<E> getProtobufList(Object var0, long var1) {
         return (Internal.ProtobufList<E>)UnsafeUtil.getObject(var0, var1);
      }

      @Override
      void makeImmutableListAt(Object var1, long var2) {
         getProtobufList(var1, var2).makeImmutable();
      }

      @Override
      <E> void mergeListsAt(Object var1, Object var2, long var3) {
         Internal.ProtobufList var7 = getProtobufList(var1, var3);
         Internal.ProtobufList var8 = getProtobufList(var2, var3);
         int var5 = var7.size();
         int var6 = var8.size();
         var2 = var7;
         if (var5 > 0) {
            var2 = var7;
            if (var6 > 0) {
               var2 = var7;
               if (!var7.isModifiable()) {
                  var2 = var7.mutableCopyWithCapacity(var6 + var5);
               }

               var2.addAll(var8);
            }
         }

         var7 = var8;
         if (var5 > 0) {
            var7 = var2;
         }

         UnsafeUtil.putObject(var1, var3, var7);
      }

      @Override
      <L> List<L> mutableListAt(Object var1, long var2) {
         Internal.ProtobufList var6 = getProtobufList(var1, var2);
         Internal.ProtobufList var5 = var6;
         if (!var6.isModifiable()) {
            int var4 = var6.size();
            if (var4 == 0) {
               var4 = 10;
            } else {
               var4 *= 2;
            }

            var5 = var6.mutableCopyWithCapacity(var4);
            UnsafeUtil.putObject(var1, var2, var5);
         }

         return var5;
      }
   }
}
