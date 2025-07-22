package io.flutter.plugins.sharedpreferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesKeys
import androidx.datastore.preferences.core.PreferencesKt
import androidx.datastore.preferences.core.Preferences.Key
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import java.util.ArrayList
import java.util.LinkedHashMap
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.Ref
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.FlowKt

public class SharedPreferencesPlugin : FlutterPlugin, SharedPreferencesAsyncApi {
   private final lateinit var context: Context
   private final var backend: SharedPreferencesBackend?
   private final var listEncoder: SharedPreferencesListEncoder = (new ListEncoder()) as SharedPreferencesListEncoder

   public constructor(listEncoder: SharedPreferencesListEncoder) : this() {
      this.listEncoder = var1;
   }

   private suspend fun dataStoreSetString(key: String, value: String) {
      val var5: Key = PreferencesKeys.stringKey(var1);
      var var6: Context = this.context;
      if (this.context == null) {
         Intrinsics.throwUninitializedPropertyAccessException("context");
         var6 = null;
      }

      val var7: Any = PreferencesKt.edit(
         SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var6),
         (new Function2<MutablePreferences, Continuation<? super Unit>, Object>(var5, var2, null) {
            final Key<java.lang.String> $stringKey;
            final java.lang.String $value;
            Object L$0;
            int label;

            {
               super(2, var3x);
               this.$stringKey = var1;
               this.$value = var2x;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               val var3: Function2 = new <anonymous constructor>(this.$stringKey, this.$value, var2);
               var3.L$0 = var1;
               return var3 as Continuation<Unit>;
            }

            public final Object invoke(MutablePreferences var1, Continuation<? super Unit> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               IntrinsicsKt.getCOROUTINE_SUSPENDED();
               if (this.label == 0) {
                  ResultKt.throwOnFailure(var1);
                  (this.L$0 as MutablePreferences).set(this.$stringKey, this.$value);
                  return Unit.INSTANCE;
               } else {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }
            }
         }) as Function2,
         var3
      );
      return if (var7 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var7 else Unit.INSTANCE;
   }

   private suspend fun getPrefs(allowList: List<String>?): Map<String, Any> {
      label65: {
         if (var2 is <unrepresentable>) {
            val var4: <unrepresentable> = var2 as <unrepresentable>;
            if ((var2.label and Integer.MIN_VALUE) != 0) {
               var4.label += Integer.MIN_VALUE;
               var2 = var4;
               break label65;
            }
         }

         var2 = new ContinuationImpl(this, var2) {
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            int label;
            Object result;
            final SharedPreferencesPlugin this$0;

            {
               super(var2);
               this.this$0 = var1;
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return SharedPreferencesPlugin.access$getPrefs(this.this$0, null, this);
            }
         };
      }

      var var6: SharedPreferencesPlugin = (SharedPreferencesPlugin)var2.result;
      val var15: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      var var7: java.util.Set;
      var var10: java.util.Iterator;
      var var12: Any;
      var var16: java.util.Map;
      if (var2.label != 0) {
         if (var2.label != 1) {
            if (var2.label != 2) {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            val var11: Key = var2.L$4 as Key;
            val var20: java.util.Iterator = var2.L$3 as java.util.Iterator;
            val var5: java.util.Map = var2.L$2 as java.util.Map;
            val var8: java.util.Set = var2.L$1 as java.util.Set;
            val var9: SharedPreferencesPlugin = var2.L$0 as SharedPreferencesPlugin;
            ResultKt.throwOnFailure(var6);
            var12 = var2;
            var10 = var20;
            var16 = var5;
            var7 = var8;
            var6 = var9;
            if (SharedPreferencesPluginKt.preferencesFilter(var11.toString(), var6, var8)) {
               val var34: Any = SharedPreferencesPluginKt.transformPref(var6, var9.listEncoder);
               var12 = var2;
               var10 = var20;
               var16 = var5;
               var7 = var8;
               var6 = var9;
               if (var34 != null) {
                  var5.put(var11.toString(), var34);
                  var12 = var2;
                  var10 = var20;
                  var16 = var5;
                  var7 = var8;
                  var6 = var9;
               }
            }
         } else {
            var16 = var2.L$2 as java.util.Map;
            val var23: java.util.Set = var2.L$1 as java.util.Set;
            val var21: SharedPreferencesPlugin = var2.L$0 as SharedPreferencesPlugin;
            ResultKt.throwOnFailure(var6);
            var7 = var6 as java.util.Set;
            if (var6 as java.util.Set == null) {
               return var16;
            }

            var10 = var7.iterator();
            var6 = var21;
            var7 = var23;
            var12 = var2;
         }
      } else {
         ResultKt.throwOnFailure(var6);
         val var17: java.util.Set;
         if (var1 != null) {
            var17 = CollectionsKt.toSet(var1);
         } else {
            var17 = null;
         }

         var16 = new LinkedHashMap();
         var2.L$0 = this;
         var2.L$1 = var17;
         var2.L$2 = var16;
         var2.label = 1;
         var6 = (SharedPreferencesPlugin)this.readAllKeys(var2);
         if (var6 === var15) {
            return var15;
         }

         var7 = var6 as java.util.Set;
         if (var6 as java.util.Set == null) {
            return var16;
         }

         var10 = var7.iterator();
         var6 = this;
         var7 = var17;
         var12 = var2;
      }

      while (var10.hasNext()) {
         val var33: Key = var10.next() as Key;
         ((<unrepresentable>)var12).L$0 = var6;
         ((<unrepresentable>)var12).L$1 = var7;
         ((<unrepresentable>)var12).L$2 = var16;
         ((<unrepresentable>)var12).L$3 = var10;
         ((<unrepresentable>)var12).L$4 = var33;
         ((<unrepresentable>)var12).label = 2;
         val var14: Any = var6.getValueByKey(var33, (Continuation<Object>)var12);
         val var19: Any = var12;
         val var22: java.util.Iterator = var10;
         val var25: java.util.Map = var16;
         val var31: java.util.Set = var7;
         val var32: SharedPreferencesPlugin = var6;
         if (var14 === var15) {
            return var15;
         }

         var12 = var12;
         var10 = var10;
         var16 = var16;
         var7 = var7;
         var6 = var6;
         if (SharedPreferencesPluginKt.preferencesFilter(var33.toString(), var14, var31)) {
            val var35: Any = SharedPreferencesPluginKt.transformPref(var14, var32.listEncoder);
            var12 = var19;
            var10 = var22;
            var16 = var25;
            var7 = var31;
            var6 = var32;
            if (var35 != null) {
               var25.put(var33.toString(), var35);
               var12 = var19;
               var10 = var22;
               var16 = var25;
               var7 = var31;
               var6 = var32;
            }
         }
      }

      return var16;
   }

   private suspend fun getValueByKey(key: Key<*>): Any? {
      var var3: Context = this.context;
      if (this.context == null) {
         Intrinsics.throwUninitializedPropertyAccessException("context");
         var3 = null;
      }

      return FlowKt.firstOrNull(new Flow<Object>(SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var3).getData(), var1) {
         final Key $key$inlined;
         final Flow $this_unsafeTransform$inlined;

         {
            this.$this_unsafeTransform$inlined = var1;
            this.$key$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$key$inlined) {
               final Key $key$inlined;
               final FlowCollector $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
                  this.$key$inlined = var2x;
               }

               @Override
               public final Object emit(Object var1, Continuation var2x) {
                  label23: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label23;
                        }
                     }

                     var2x = new ContinuationImpl(this, var2x) {
                        Object L$0;
                        int label;
                        Object result;
                        final <unrepresentable> this$0;

                        {
                           super(var2x);
                           this.this$0 = var1;
                        }

                        @Override
                        public final Object invokeSuspend(Object var1) {
                           this.result = var1;
                           this.label |= Integer.MIN_VALUE;
                           return this.this$0.emit(null, this);
                        }
                     };
                  }

                  var var5: Any = var2x.result;
                  val var9: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var5 = this.$this_unsafeFlow;
                     val var6: Continuation = var2x;
                     var1 = (var1 as Preferences).get(this.$key$inlined);
                     var2x.label = 1;
                     if (((FlowCollector<Object>)var5).emit(var1, var2x) === var9) {
                        return var9;
                     }
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      }, var2);
   }

   private suspend fun readAllKeys(): Set<Key<*>>? {
      var var2: Context = this.context;
      if (this.context == null) {
         Intrinsics.throwUninitializedPropertyAccessException("context");
         var2 = null;
      }

      return FlowKt.firstOrNull(new Flow<java.util.Set<? extends Key<?>>>(SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var2).getData()) {
         final Flow $this_unsafeTransform$inlined;

         {
            this.$this_unsafeTransform$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1) {
               final FlowCollector $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
               }

               @Override
               public final Object emit(Object var1, Continuation var2x) {
                  label23: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label23;
                        }
                     }

                     var2x = new ContinuationImpl(this, var2x) {
                        Object L$0;
                        int label;
                        Object result;
                        final <unrepresentable> this$0;

                        {
                           super(var2x);
                           this.this$0 = var1;
                        }

                        @Override
                        public final Object invokeSuspend(Object var1) {
                           this.result = var1;
                           this.label |= Integer.MIN_VALUE;
                           return this.this$0.emit(null, this);
                        }
                     };
                  }

                  var var5: Any = var2x.result;
                  val var9: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var5 = this.$this_unsafeFlow;
                     val var6: Continuation = var2x;
                     var1 = (var1 as Preferences).asMap().keySet();
                     var2x.label = 1;
                     if (((FlowCollector<java.util.Set>)var5).emit(var1, var2x) === var9) {
                        return var9;
                     }
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      }, var1);
   }

   private fun setUp(messenger: BinaryMessenger, context: Context) {
      this.context = var2;

      try {
         SharedPreferencesAsyncApi.Companion.setUp(var1, this, "data_store");
         this.backend = new SharedPreferencesBackend(var1, var2, this.listEncoder);
      } catch (var4: Exception) {
         Log.e("SharedPreferencesPlugin", "Received exception while setting up SharedPreferencesPlugin", var4);
      }
   }

   public override fun clear(allowList: List<String>?, options: SharedPreferencesPigeonOptions) {
      BuildersKt.runBlocking$default(null, (new Function2<CoroutineScope, Continuation<? super Preferences>, Object>(this, var1, null) {
         final java.util.List<java.lang.String> $allowList;
         int label;
         final SharedPreferencesPlugin this$0;

         {
            super(2, var3);
            this.this$0 = var1;
            this.$allowList = var2x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.this$0, this.$allowList, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Preferences> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               var var3: Context = SharedPreferencesPlugin.access$getContext$p(this.this$0);
               var1 = var3;
               if (var3 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("context");
                  var1 = null;
               }

               val var8: DataStore = SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var1);
               val var5: Function2 = (new Function2<MutablePreferences, Continuation<? super Unit>, Object>(this.$allowList, null) {
                  final java.util.List<java.lang.String> $allowList;
                  Object L$0;
                  int label;

                  {
                     super(2, var2x);
                     this.$allowList = var1;
                  }

                  @Override
                  public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                     val var3x: Function2 = new <anonymous constructor>(this.$allowList, var2);
                     var3x.L$0 = var1;
                     return var3x as Continuation<Unit>;
                  }

                  public final Object invoke(MutablePreferences var1, Continuation<? super Unit> var2x) {
                     return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     } else {
                        ResultKt.throwOnFailure(var1);
                        var1 = this.L$0 as MutablePreferences;
                        if (this.$allowList != null) {
                           val var4: java.util.Iterator = this.$allowList.iterator();

                           while (var4.hasNext()) {
                              var1.remove(PreferencesKeys.booleanKey(var4.next() as java.lang.String));
                           }
                        } else {
                           var1.clear();
                        }

                        return Unit.INSTANCE;
                     }
                  }
               }) as Function2;
               val var7: Continuation = this;
               this.label = 1;
               var3 = (Context)PreferencesKt.edit(var8, var5, var7);
               var1 = var3;
               if (var3 === var4) {
                  return var4;
               }
            }

            return var1;
         }
      }) as Function2, 1, null);
   }

   public override fun getAll(allowList: List<String>?, options: SharedPreferencesPigeonOptions): Map<String, Any> {
      return BuildersKt.runBlocking$default(
         null, (new Function2<CoroutineScope, Continuation<? super java.util.Map<java.lang.String, ? extends Object>>, Object>(this, var1, null) {
            final java.util.List<java.lang.String> $allowList;
            int label;
            final SharedPreferencesPlugin this$0;

            {
               super(2, var3);
               this.this$0 = var1;
               this.$allowList = var2x;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               return new <anonymous constructor>(this.this$0, this.$allowList, var2);
            }

            public final Object invoke(CoroutineScope var1, Continuation<? super java.util.Map<java.lang.String, ? extends Object>> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               if (this.label != 0) {
                  if (this.label != 1) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  ResultKt.throwOnFailure(var1);
               } else {
                  ResultKt.throwOnFailure(var1);
                  var1 = this.this$0;
                  var var3: java.util.List = this.$allowList;
                  val var5: Continuation = this;
                  this.label = 1;
                  var3 = (java.util.List)SharedPreferencesPlugin.access$getPrefs(var1, var3, var5);
                  var1 = var3;
                  if (var3 === var4) {
                     return var4;
                  }
               }

               return var1;
            }
         }) as Function2, 1, null
      ) as MutableMap<java.lang.String, Any>;
   }

   public override fun getBool(key: String, options: SharedPreferencesPigeonOptions): Boolean? {
      val var3: Ref.ObjectRef = new Ref.ObjectRef();
      BuildersKt.runBlocking$default(null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var1, this, var3, null) {
         final java.lang.String $key;
         final Ref.ObjectRef<java.lang.Boolean> $value;
         Object L$0;
         int label;
         final SharedPreferencesPlugin this$0;

         {
            super(2, var4);
            this.$key = var1;
            this.this$0 = var2x;
            this.$value = var3;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.$key, this.this$0, this.$value, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var3: Ref.ObjectRef;
            var var4: Any;
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var3 = this.L$0 as Ref.ObjectRef;
               ResultKt.throwOnFailure(var1);
               var4 = var1;
            } else {
               ResultKt.throwOnFailure(var1);
               var4 = PreferencesKeys.booleanKey(this.$key);
               val var8: Context = SharedPreferencesPlugin.access$getContext$p(this.this$0);
               var1 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("context");
                  var1 = null;
               }

               var4 = new Flow<java.lang.Boolean>(SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var1).getData(), (Key)var4) {
                  final Key $preferencesKey$inlined;
                  final Flow $this_unsafeTransform$inlined;

                  {
                     this.$this_unsafeTransform$inlined = var1;
                     this.$preferencesKey$inlined = var2x;
                  }

                  @Override
                  public Object collect(FlowCollector var1, Continuation var2x) {
                     val var3x: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$preferencesKey$inlined) {
                        final Key $preferencesKey$inlined;
                        final FlowCollector $this_unsafeFlow;

                        {
                           this.$this_unsafeFlow = var1;
                           this.$preferencesKey$inlined = var2x;
                        }

                        @Override
                        public final Object emit(Object var1, Continuation var2x) {
                           label23: {
                              if (var2x is <unrepresentable>) {
                                 val var4x: <unrepresentable> = var2x as <unrepresentable>;
                                 if ((var2x.label and Integer.MIN_VALUE) != 0) {
                                    var4x.label += Integer.MIN_VALUE;
                                    var2x = var4x;
                                    break label23;
                                 }
                              }

                              var2x = new ContinuationImpl(this, var2x) {
                                 Object L$0;
                                 int label;
                                 Object result;
                                 final <unrepresentable> this$0;

                                 {
                                    super(var2x);
                                    this.this$0 = var1;
                                 }

                                 @Override
                                 public final Object invokeSuspend(Object var1) {
                                    this.result = var1;
                                    this.label |= Integer.MIN_VALUE;
                                    return this.this$0.emit(null, this);
                                 }
                              };
                           }

                           var var5x: Any = var2x.result;
                           val var9: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                           if (var2x.label != 0) {
                              if (var2x.label != 1) {
                                 throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                              }

                              ResultKt.throwOnFailure(var5x);
                           } else {
                              ResultKt.throwOnFailure(var5x);
                              var5x = this.$this_unsafeFlow;
                              val var6: Continuation = var2x;
                              var1 = (var1 as Preferences).get(this.$preferencesKey$inlined);
                              var2x.label = 1;
                              if (((FlowCollector<Object>)var5x).emit(var1, var2x) === var9) {
                                 return var9;
                              }
                           }

                           return Unit.INSTANCE;
                        }
                     }, var2x);
                     return if (var3x === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3x else Unit.INSTANCE;
                  }
               };
               val var7: Ref.ObjectRef = this.$value;
               val var9: Continuation = this;
               this.L$0 = this.$value;
               this.label = 1;
               var4 = FlowKt.firstOrNull((Flow)var4, var9);
               if (var4 === var5) {
                  return var5;
               }

               var3 = var7;
            }

            var3.element = (T)var4;
            return Unit.INSTANCE;
         }
      }) as Function2, 1, null);
      return var3.element as java.lang.Boolean;
   }

   public override fun getDouble(key: String, options: SharedPreferencesPigeonOptions): Double? {
      val var3: Ref.ObjectRef = new Ref.ObjectRef();
      BuildersKt.runBlocking$default(
         null,
         (
            new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var1, this, var3, null) {
               final java.lang.String $key;
               final Ref.ObjectRef<java.lang.Double> $value;
               Object L$0;
               int label;
               final SharedPreferencesPlugin this$0;

               {
                  super(2, var4);
                  this.$key = var1;
                  this.this$0 = var2x;
                  this.$value = var3;
               }

               @Override
               public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                  return new <anonymous constructor>(this.$key, this.this$0, this.$value, var2);
               }

               public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
                  return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
               }

               @Override
               public final Object invokeSuspend(Object var1) {
                  val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var3: Ref.ObjectRef;
                  var var4: Any;
                  if (this.label != 0) {
                     if (this.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     var3 = this.L$0 as Ref.ObjectRef;
                     ResultKt.throwOnFailure(var1);
                     var4 = var1;
                  } else {
                     ResultKt.throwOnFailure(var1);
                     var4 = PreferencesKeys.stringKey(this.$key);
                     val var8: Context = SharedPreferencesPlugin.access$getContext$p(this.this$0);
                     var1 = var8;
                     if (var8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("context");
                        var1 = null;
                     }

                     val var9: Flow = new Flow<java.lang.Double>(
                        SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var1).getData(), (Key)var4, this.this$0
                     ) {
                        final Key $preferencesKey$inlined;
                        final Flow $this_unsafeTransform$inlined;
                        final SharedPreferencesPlugin this$0;

                        {
                           this.$this_unsafeTransform$inlined = var1;
                           this.$preferencesKey$inlined = var2x;
                           this.this$0 = var3x;
                        }

                        @Override
                        public Object collect(FlowCollector var1, Continuation var2x) {
                           val var3x: Any = this.$this_unsafeTransform$inlined
                              .collect(
                                 new FlowCollector(var1, this.$preferencesKey$inlined, this.this$0) {
                                    final Key $preferencesKey$inlined;
                                    final FlowCollector $this_unsafeFlow;
                                    final SharedPreferencesPlugin this$0;

                                    {
                                       this.$this_unsafeFlow = var1;
                                       this.$preferencesKey$inlined = var2x;
                                       this.this$0 = var3x;
                                    }

                                    @Override
                                    public final Object emit(Object var1, Continuation var2x) {
                                       label23: {
                                          if (var2x is <unrepresentable>) {
                                             val var4x: <unrepresentable> = var2x as <unrepresentable>;
                                             if ((var2x.label and Integer.MIN_VALUE) != 0) {
                                                var4x.label += Integer.MIN_VALUE;
                                                var2x = var4x;
                                                break label23;
                                             }
                                          }

                                          var2x = new ContinuationImpl(this, var2x) {
                                             Object L$0;
                                             int label;
                                             Object result;
                                             final <unrepresentable> this$0;

                                             {
                                                super(var2x);
                                                this.this$0 = var1;
                                             }

                                             @Override
                                             public final Object invokeSuspend(Object var1) {
                                                this.result = var1;
                                                this.label |= Integer.MIN_VALUE;
                                                return this.this$0.emit(null, this);
                                             }
                                          };
                                       }

                                       var var5x: Any = var2x.result;
                                       val var9x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                       if (var2x.label != 0) {
                                          if (var2x.label != 1) {
                                             throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                          }

                                          ResultKt.throwOnFailure(var5x);
                                       } else {
                                          ResultKt.throwOnFailure(var5x);
                                          var5x = this.$this_unsafeFlow;
                                          val var6: Continuation = var2x;
                                          var1 = SharedPreferencesPluginKt.transformPref(
                                             (var1 as Preferences).get(this.$preferencesKey$inlined),
                                             SharedPreferencesPlugin.access$getListEncoder$p(this.this$0)
                                          ) as java.lang.Double;
                                          var2x.label = 1;
                                          if (((FlowCollector<java.lang.Double>)var5x).emit(var1, var2x) === var9x) {
                                             return var9x;
                                          }
                                       }

                                       return Unit.INSTANCE;
                                    }
                                 },
                                 var2x
                              );
                           return if (var3x === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3x else Unit.INSTANCE;
                        }
                     };
                     val var7: Ref.ObjectRef = this.$value;
                     var4 = this;
                     this.L$0 = this.$value;
                     this.label = 1;
                     var4 = FlowKt.firstOrNull(var9, (Continuation)var4);
                     if (var4 === var5) {
                        return var5;
                     }

                     var3 = var7;
                  }

                  var3.element = (T)var4;
                  return Unit.INSTANCE;
               }
            }
         ) as Function2,
         1,
         null
      );
      return var3.element as java.lang.Double;
   }

   public override fun getInt(key: String, options: SharedPreferencesPigeonOptions): Long? {
      val var3: Ref.ObjectRef = new Ref.ObjectRef();
      BuildersKt.runBlocking$default(null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var1, this, var3, null) {
         final java.lang.String $key;
         final Ref.ObjectRef<java.lang.Long> $value;
         Object L$0;
         int label;
         final SharedPreferencesPlugin this$0;

         {
            super(2, var4);
            this.$key = var1;
            this.this$0 = var2x;
            this.$value = var3;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.$key, this.this$0, this.$value, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var3: Ref.ObjectRef;
            var var4: Any;
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var3 = this.L$0 as Ref.ObjectRef;
               ResultKt.throwOnFailure(var1);
               var4 = var1;
            } else {
               ResultKt.throwOnFailure(var1);
               var4 = PreferencesKeys.longKey(this.$key);
               val var8: Context = SharedPreferencesPlugin.access$getContext$p(this.this$0);
               var1 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("context");
                  var1 = null;
               }

               var4 = new Flow<java.lang.Long>(SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var1).getData(), (Key)var4) {
                  final Key $preferencesKey$inlined;
                  final Flow $this_unsafeTransform$inlined;

                  {
                     this.$this_unsafeTransform$inlined = var1;
                     this.$preferencesKey$inlined = var2x;
                  }

                  @Override
                  public Object collect(FlowCollector var1, Continuation var2x) {
                     val var3x: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$preferencesKey$inlined) {
                        final Key $preferencesKey$inlined;
                        final FlowCollector $this_unsafeFlow;

                        {
                           this.$this_unsafeFlow = var1;
                           this.$preferencesKey$inlined = var2x;
                        }

                        @Override
                        public final Object emit(Object var1, Continuation var2x) {
                           label23: {
                              if (var2x is <unrepresentable>) {
                                 val var4x: <unrepresentable> = var2x as <unrepresentable>;
                                 if ((var2x.label and Integer.MIN_VALUE) != 0) {
                                    var4x.label += Integer.MIN_VALUE;
                                    var2x = var4x;
                                    break label23;
                                 }
                              }

                              var2x = new ContinuationImpl(this, var2x) {
                                 Object L$0;
                                 int label;
                                 Object result;
                                 final <unrepresentable> this$0;

                                 {
                                    super(var2x);
                                    this.this$0 = var1;
                                 }

                                 @Override
                                 public final Object invokeSuspend(Object var1) {
                                    this.result = var1;
                                    this.label |= Integer.MIN_VALUE;
                                    return this.this$0.emit(null, this);
                                 }
                              };
                           }

                           var var5x: Any = var2x.result;
                           val var9: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                           if (var2x.label != 0) {
                              if (var2x.label != 1) {
                                 throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                              }

                              ResultKt.throwOnFailure(var5x);
                           } else {
                              ResultKt.throwOnFailure(var5x);
                              var5x = this.$this_unsafeFlow;
                              val var6: Continuation = var2x;
                              var1 = (var1 as Preferences).get(this.$preferencesKey$inlined);
                              var2x.label = 1;
                              if (((FlowCollector<Object>)var5x).emit(var1, var2x) === var9) {
                                 return var9;
                              }
                           }

                           return Unit.INSTANCE;
                        }
                     }, var2x);
                     return if (var3x === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3x else Unit.INSTANCE;
                  }
               };
               val var7: Ref.ObjectRef = this.$value;
               val var9: Continuation = this;
               this.L$0 = this.$value;
               this.label = 1;
               var4 = FlowKt.firstOrNull((Flow)var4, var9);
               if (var4 === var5) {
                  return var5;
               }

               var3 = var7;
            }

            var3.element = (T)var4;
            return Unit.INSTANCE;
         }
      }) as Function2, 1, null);
      return var3.element as java.lang.Long;
   }

   public override fun getKeys(allowList: List<String>?, options: SharedPreferencesPigeonOptions): List<String> {
      return CollectionsKt.toList(
         (BuildersKt.runBlocking$default(
               null, (new Function2<CoroutineScope, Continuation<? super java.util.Map<java.lang.String, ? extends Object>>, Object>(this, var1, null) {
                  final java.util.List<java.lang.String> $allowList;
                  int label;
                  final SharedPreferencesPlugin this$0;

                  {
                     super(2, var3);
                     this.this$0 = var1;
                     this.$allowList = var2x;
                  }

                  @Override
                  public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                     return new <anonymous constructor>(this.this$0, this.$allowList, var2);
                  }

                  public final Object invoke(CoroutineScope var1, Continuation<? super java.util.Map<java.lang.String, ? extends Object>> var2x) {
                     return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (this.label != 0) {
                        if (this.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var1);
                     } else {
                        ResultKt.throwOnFailure(var1);
                        val var5: SharedPreferencesPlugin = this.this$0;
                        var1 = this.$allowList;
                        var var3: Continuation = this;
                        this.label = 1;
                        var3 = (Continuation)SharedPreferencesPlugin.access$getPrefs(var5, var1, var3);
                        var1 = var3;
                        if (var3 === var4) {
                           return var4;
                        }
                     }

                     return var1;
                  }
               }) as Function2, 1, null
            ) as java.util.Map)
            .keySet()
      );
   }

   public override fun getPlatformEncodedStringList(key: String, options: SharedPreferencesPigeonOptions): List<String>? {
      val var3: java.lang.String = this.getString(var1, var2);
      var var4: java.util.List = null;
      if (var3 != null) {
         var4 = null;
         if (!StringsKt.startsWith$default(var3, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu!", false, 2, null)) {
            var4 = null;
            if (StringsKt.startsWith$default(var3, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu", false, 2, null)) {
               val var9: java.util.List = SharedPreferencesPluginKt.transformPref(var3, this.listEncoder) as java.util.List;
               var4 = null;
               if (var9 != null) {
                  val var7: java.lang.Iterable = var9;
                  val var5: java.util.Collection = new ArrayList();

                  for (Object var10 : var7) {
                     if (var10 is java.lang.String) {
                        var5.add(var10);
                     }
                  }

                  var4 = var5 as java.util.List;
               }
            }
         }
      }

      return var4;
   }

   public override fun getString(key: String, options: SharedPreferencesPigeonOptions): String? {
      val var3: Ref.ObjectRef = new Ref.ObjectRef();
      BuildersKt.runBlocking$default(null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var1, this, var3, null) {
         final java.lang.String $key;
         final Ref.ObjectRef<java.lang.String> $value;
         Object L$0;
         int label;
         final SharedPreferencesPlugin this$0;

         {
            super(2, var4);
            this.$key = var1;
            this.this$0 = var2x;
            this.$value = var3;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.$key, this.this$0, this.$value, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var3: Ref.ObjectRef;
            var var4: Any;
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var3 = this.L$0 as Ref.ObjectRef;
               ResultKt.throwOnFailure(var1);
               var4 = var1;
            } else {
               ResultKt.throwOnFailure(var1);
               var4 = PreferencesKeys.stringKey(this.$key);
               val var8: Context = SharedPreferencesPlugin.access$getContext$p(this.this$0);
               var1 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("context");
                  var1 = null;
               }

               var4 = new Flow<java.lang.String>(SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var1).getData(), (Key)var4) {
                  final Key $preferencesKey$inlined;
                  final Flow $this_unsafeTransform$inlined;

                  {
                     this.$this_unsafeTransform$inlined = var1;
                     this.$preferencesKey$inlined = var2x;
                  }

                  @Override
                  public Object collect(FlowCollector var1, Continuation var2x) {
                     val var3x: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$preferencesKey$inlined) {
                        final Key $preferencesKey$inlined;
                        final FlowCollector $this_unsafeFlow;

                        {
                           this.$this_unsafeFlow = var1;
                           this.$preferencesKey$inlined = var2x;
                        }

                        @Override
                        public final Object emit(Object var1, Continuation var2x) {
                           label23: {
                              if (var2x is <unrepresentable>) {
                                 val var4x: <unrepresentable> = var2x as <unrepresentable>;
                                 if ((var2x.label and Integer.MIN_VALUE) != 0) {
                                    var4x.label += Integer.MIN_VALUE;
                                    var2x = var4x;
                                    break label23;
                                 }
                              }

                              var2x = new ContinuationImpl(this, var2x) {
                                 Object L$0;
                                 int label;
                                 Object result;
                                 final <unrepresentable> this$0;

                                 {
                                    super(var2x);
                                    this.this$0 = var1;
                                 }

                                 @Override
                                 public final Object invokeSuspend(Object var1) {
                                    this.result = var1;
                                    this.label |= Integer.MIN_VALUE;
                                    return this.this$0.emit(null, this);
                                 }
                              };
                           }

                           var var5x: Any = var2x.result;
                           val var9: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                           if (var2x.label != 0) {
                              if (var2x.label != 1) {
                                 throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                              }

                              ResultKt.throwOnFailure(var5x);
                           } else {
                              ResultKt.throwOnFailure(var5x);
                              var5x = this.$this_unsafeFlow;
                              val var6: Continuation = var2x;
                              var1 = (var1 as Preferences).get(this.$preferencesKey$inlined);
                              var2x.label = 1;
                              if (((FlowCollector<Object>)var5x).emit(var1, var2x) === var9) {
                                 return var9;
                              }
                           }

                           return Unit.INSTANCE;
                        }
                     }, var2x);
                     return if (var3x === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3x else Unit.INSTANCE;
                  }
               };
               val var7: Ref.ObjectRef = this.$value;
               val var9: Continuation = this;
               this.L$0 = this.$value;
               this.label = 1;
               var4 = FlowKt.firstOrNull((Flow)var4, var9);
               if (var4 === var5) {
                  return var5;
               }

               var3 = var7;
            }

            var3.element = (T)var4;
            return Unit.INSTANCE;
         }
      }) as Function2, 1, null);
      return var3.element as java.lang.String;
   }

   public override fun getStringList(key: String, options: SharedPreferencesPigeonOptions): StringListResult? {
      val var4: java.lang.String = this.getString(var1, var2);
      var var3: StringListResult = null;
      if (var4 != null) {
         if (StringsKt.startsWith$default(var4, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu!", false, 2, null)) {
            var3 = new StringListResult(var4, StringListLookupResultType.JSON_ENCODED);
         } else if (StringsKt.startsWith$default(var4, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu", false, 2, null)) {
            var3 = new StringListResult(null, StringListLookupResultType.PLATFORM_ENCODED);
         } else {
            var3 = new StringListResult(null, StringListLookupResultType.UNEXPECTED_STRING);
         }
      }

      return var3;
   }

   public override fun onAttachedToEngine(binding: FlutterPluginBinding) {
      val var3: BinaryMessenger = var1.getBinaryMessenger();
      val var2: Context = var1.getApplicationContext();
      this.setUp(var3, var2);
      new LegacySharedPreferencesPlugin().onAttachedToEngine(var1);
   }

   public override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
      val var2: SharedPreferencesAsyncApi.Companion = SharedPreferencesAsyncApi.Companion;
      val var3: BinaryMessenger = var1.getBinaryMessenger();
      var2.setUp(var3, null, "data_store");
      if (this.backend != null) {
         this.backend.tearDown();
      }

      this.backend = null;
   }

   public override fun setBool(key: String, value: Boolean, options: SharedPreferencesPigeonOptions) {
      BuildersKt.runBlocking$default(null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var1, this, var2, null) {
         final java.lang.String $key;
         final boolean $value;
         int label;
         final SharedPreferencesPlugin this$0;

         {
            super(2, var4);
            this.$key = var1;
            this.this$0 = var2x;
            this.$value = var3;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.$key, this.this$0, this.$value, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               val var5: Key = PreferencesKeys.booleanKey(this.$key);
               val var3: Context = SharedPreferencesPlugin.access$getContext$p(this.this$0);
               var1 = var3;
               if (var3 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("context");
                  var1 = null;
               }

               val var7: DataStore = SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var1);
               val var8: Function2 = (new Function2<MutablePreferences, Continuation<? super Unit>, Object>(var5, this.$value, null) {
                  final Key<java.lang.Boolean> $boolKey;
                  final boolean $value;
                  Object L$0;
                  int label;

                  {
                     super(2, var3x);
                     this.$boolKey = var1;
                     this.$value = var2x;
                  }

                  @Override
                  public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                     val var3x: Function2 = new <anonymous constructor>(this.$boolKey, this.$value, var2);
                     var3x.L$0 = var1;
                     return var3x as Continuation<Unit>;
                  }

                  public final Object invoke(MutablePreferences var1, Continuation<? super Unit> var2x) {
                     return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (this.label == 0) {
                        ResultKt.throwOnFailure(var1);
                        (this.L$0 as MutablePreferences).set(this.$boolKey, Boxing.boxBoolean(this.$value));
                        return Unit.INSTANCE;
                     } else {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }
                  }
               }) as Function2;
               val var9: Continuation = this;
               this.label = 1;
               if (PreferencesKt.edit(var7, var8, var9) === var4) {
                  return var4;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2, 1, null);
   }

   @Deprecated(message = "This is just for testing, use `setEncodedStringList`")
   public override fun setDeprecatedStringList(key: String, value: List<String>, options: SharedPreferencesPigeonOptions) {
      val var5: java.lang.String = this.listEncoder.encode(var2);
      val var4: StringBuilder = new StringBuilder("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu");
      var4.append(var5);
      BuildersKt.runBlocking$default(null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, var1, var4.toString(), null) {
         final java.lang.String $key;
         final java.lang.String $valueString;
         int label;
         final SharedPreferencesPlugin this$0;

         {
            super(2, var4);
            this.this$0 = var1;
            this.$key = var2x;
            this.$valueString = var3;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.this$0, this.$key, this.$valueString, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var3: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               var1 = this.this$0;
               val var4: java.lang.String = this.$key;
               val var6: java.lang.String = this.$valueString;
               val var5: Continuation = this;
               this.label = 1;
               if (SharedPreferencesPlugin.access$dataStoreSetString(var1, var4, var6, var5) === var3) {
                  return var3;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2, 1, null);
   }

   public override fun setDouble(key: String, value: Double, options: SharedPreferencesPigeonOptions) {
      BuildersKt.runBlocking$default(null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var1, this, var2, null) {
         final java.lang.String $key;
         final double $value;
         int label;
         final SharedPreferencesPlugin this$0;

         {
            super(2, var5);
            this.$key = var1;
            this.this$0 = var2x;
            this.$value = var3;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.$key, this.this$0, this.$value, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               val var5: Key = PreferencesKeys.doubleKey(this.$key);
               val var3: Context = SharedPreferencesPlugin.access$getContext$p(this.this$0);
               var1 = var3;
               if (var3 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("context");
                  var1 = null;
               }

               val var7: DataStore = SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var1);
               val var9: Function2 = (new Function2<MutablePreferences, Continuation<? super Unit>, Object>(var5, this.$value, null) {
                  final Key<java.lang.Double> $doubleKey;
                  final double $value;
                  Object L$0;
                  int label;

                  {
                     super(2, var4x);
                     this.$doubleKey = var1;
                     this.$value = var2x;
                  }

                  @Override
                  public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                     val var3x: Function2 = new <anonymous constructor>(this.$doubleKey, this.$value, var2);
                     var3x.L$0 = var1;
                     return var3x as Continuation<Unit>;
                  }

                  public final Object invoke(MutablePreferences var1, Continuation<? super Unit> var2x) {
                     return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (this.label == 0) {
                        ResultKt.throwOnFailure(var1);
                        (this.L$0 as MutablePreferences).set(this.$doubleKey, Boxing.boxDouble(this.$value));
                        return Unit.INSTANCE;
                     } else {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }
                  }
               }) as Function2;
               val var8: Continuation = this;
               this.label = 1;
               if (PreferencesKt.edit(var7, var9, var8) === var4) {
                  return var4;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2, 1, null);
   }

   public override fun setEncodedStringList(key: String, value: String, options: SharedPreferencesPigeonOptions) {
      BuildersKt.runBlocking$default(null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, var1, var2, null) {
         final java.lang.String $key;
         final java.lang.String $value;
         int label;
         final SharedPreferencesPlugin this$0;

         {
            super(2, var4);
            this.this$0 = var1;
            this.$key = var2x;
            this.$value = var3;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.this$0, this.$key, this.$value, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var3: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               val var5: SharedPreferencesPlugin = this.this$0;
               val var6: java.lang.String = this.$key;
               var1 = this.$value;
               val var4: Continuation = this;
               this.label = 1;
               if (SharedPreferencesPlugin.access$dataStoreSetString(var5, var6, var1, var4) === var3) {
                  return var3;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2, 1, null);
   }

   public override fun setInt(key: String, value: Long, options: SharedPreferencesPigeonOptions) {
      BuildersKt.runBlocking$default(null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var1, this, var2, null) {
         final java.lang.String $key;
         final long $value;
         int label;
         final SharedPreferencesPlugin this$0;

         {
            super(2, var5);
            this.$key = var1;
            this.this$0 = var2x;
            this.$value = var3;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.$key, this.this$0, this.$value, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               val var5: Key = PreferencesKeys.longKey(this.$key);
               val var3: Context = SharedPreferencesPlugin.access$getContext$p(this.this$0);
               var1 = var3;
               if (var3 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("context");
                  var1 = null;
               }

               val var7: DataStore = SharedPreferencesPluginKt.access$getSharedPreferencesDataStore(var1);
               val var8: Function2 = (new Function2<MutablePreferences, Continuation<? super Unit>, Object>(var5, this.$value, null) {
                  final Key<java.lang.Long> $intKey;
                  final long $value;
                  Object L$0;
                  int label;

                  {
                     super(2, var4x);
                     this.$intKey = var1;
                     this.$value = var2x;
                  }

                  @Override
                  public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                     val var3x: Function2 = new <anonymous constructor>(this.$intKey, this.$value, var2);
                     var3x.L$0 = var1;
                     return var3x as Continuation<Unit>;
                  }

                  public final Object invoke(MutablePreferences var1, Continuation<? super Unit> var2x) {
                     return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (this.label == 0) {
                        ResultKt.throwOnFailure(var1);
                        (this.L$0 as MutablePreferences).set(this.$intKey, Boxing.boxLong(this.$value));
                        return Unit.INSTANCE;
                     } else {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }
                  }
               }) as Function2;
               val var9: Continuation = this;
               this.label = 1;
               if (PreferencesKt.edit(var7, var8, var9) === var4) {
                  return var4;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2, 1, null);
   }

   public override fun setString(key: String, value: String, options: SharedPreferencesPigeonOptions) {
      BuildersKt.runBlocking$default(null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, var1, var2, null) {
         final java.lang.String $key;
         final java.lang.String $value;
         int label;
         final SharedPreferencesPlugin this$0;

         {
            super(2, var4);
            this.this$0 = var1;
            this.$key = var2x;
            this.$value = var3;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.this$0, this.$key, this.$value, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var3: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               val var5: SharedPreferencesPlugin = this.this$0;
               val var6: java.lang.String = this.$key;
               var1 = this.$value;
               val var4: Continuation = this;
               this.label = 1;
               if (SharedPreferencesPlugin.access$dataStoreSetString(var5, var6, var1, var4) === var3) {
                  return var3;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2, 1, null);
   }
}
