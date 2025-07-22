package kotlin.io.path

import java.nio.file.LinkOption
import java.nio.file.Path
import java.util.Arrays
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2

internal class PathTreeWalk(start: Path, vararg options: Any) : Sequence<Path> {
   private final val start: Path
   private final val options: Array<out PathWalkOption>

   private final val followLinks: Boolean
      private final get() {
         return ArraysKt.contains(this.options, PathWalkOption.FOLLOW_LINKS);
      }


   private final val linkOptions: Array<LinkOption>
      private final get() {
         return LinkFollowing.INSTANCE.toLinkOptions(this.getFollowLinks());
      }


   private final val includeDirectories: Boolean
      private final get() {
         return ArraysKt.contains(this.options, PathWalkOption.INCLUDE_DIRECTORIES);
      }


   private final val isBFS: Boolean
      private final get() {
         return ArraysKt.contains(this.options, PathWalkOption.BREADTH_FIRST);
      }


   init {
      this.start = var1;
      this.options = var2;
   }

   private fun bfsIterator(): Iterator<Path> {
      return SequencesKt.iterator(
         (
            new Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object>(this, null) {
               private Object L$0;
               Object L$1;
               Object L$2;
               Object L$3;
               Object L$4;
               Object L$5;
               int label;
               final PathTreeWalk this$0;

               {
                  super(2, var2x);
                  this.this$0 = var1;
               }

               @Override
               public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                  val var3: Function2 = new <anonymous constructor>(this.this$0, var2);
                  var3.L$0 = var1;
                  return var3 as Continuation<Unit>;
               }

               public final Object invoke(SequenceScope<? super Path> var1, Continuation<? super Unit> var2x) {
                  return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
               }

               // $VF: Irreducible bytecode was duplicated to produce valid code
               @Override
               public final Object invokeSuspend(Object var1) {
                  val var14: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  var var3x: DirectoryEntriesReader;
                  var var4: SequenceScope;
                  if (this.label != 0) {
                     if (this.label != 1) {
                        if (this.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        var3x = this.L$2 as DirectoryEntriesReader;
                        val var5: ArrayDeque = this.L$1 as ArrayDeque;
                        var4 = this.L$0 as SequenceScope;
                        ResultKt.throwOnFailure(var1);
                        var1 = var5;
                     } else {
                        val var21: Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.L$5);
                        val var19: PathTreeWalk = this.L$4 as PathTreeWalk;
                        val var7: PathNode = this.L$3 as PathNode;
                        val var20: DirectoryEntriesReader = this.L$2 as DirectoryEntriesReader;
                        val var8: ArrayDeque = this.L$1 as ArrayDeque;
                        val var6: SequenceScope = this.L$0 as SequenceScope;
                        ResultKt.throwOnFailure(var1);
                        val var17: Array<LinkOption> = PathTreeWalk.access$getLinkOptions(var19);
                        val var22: Array<LinkOption> = Arrays.copyOf(var17, var17.length);
                        var3x = var20;
                        var1 = var8;
                        var4 = var6;
                        if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var21, Arrays.copyOf(var22, var22.length))) {
                           var8.addAll(var20.readEntries(var7));
                           var3x = var20;
                           var1 = var8;
                           var4 = var6;
                        }
                     }
                  } else {
                     ResultKt.throwOnFailure(var1);
                     var4 = this.L$0 as SequenceScope;
                     var1 = new ArrayDeque();
                     var3x = new DirectoryEntriesReader(PathTreeWalk.access$getFollowLinks(this.this$0));
                     var1.addLast(
                        new PathNode(
                           PathTreeWalk.access$getStart$p(this.this$0),
                           PathTreeWalkKt.access$keyOf(PathTreeWalk.access$getStart$p(this.this$0), PathTreeWalk.access$getLinkOptions(this.this$0)),
                           null
                        )
                     );
                  }

                  while (!var1.isEmpty()) {
                     val var10: PathNode = var1.removeFirst() as PathNode;
                     val var23: PathTreeWalk = this.this$0;
                     val var26: Path = var10.getPath();
                     if (var10.getParent() != null) {
                        PathsKt.checkFileName(var26);
                     }

                     var var27: Array<LinkOption> = PathTreeWalk.access$getLinkOptions(var23);
                     var27 = Arrays.copyOf(var27, var27.length);
                     if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var26, Arrays.copyOf(var27, var27.length))) {
                        if (PathTreeWalkKt.access$createsCycle(var10)) {
                           PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1();
                           throw PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var26.toString());
                        }

                        var var34: DirectoryEntriesReader = var3x;
                        var var33: ArrayDeque = var1;
                        var var9: SequenceScope = var4;
                        var var11: PathTreeWalk = var23;
                        var var32: Path = var26;
                        var var29: PathNode = var10;
                        if (PathTreeWalk.access$getIncludeDirectories(var23)) {
                           val var30: Continuation = this;
                           this.L$0 = var4;
                           this.L$1 = var1;
                           this.L$2 = var3x;
                           this.L$3 = var10;
                           this.L$4 = var23;
                           this.L$5 = var26;
                           this.label = 1;
                           if (var4.yield(var26, var30) === var14) {
                              return var14;
                           }

                           var29 = var10;
                           var32 = var26;
                           var11 = var23;
                           var9 = var4;
                           var33 = var1;
                           var34 = var3x;
                        }

                        val var18: Array<LinkOption> = PathTreeWalk.access$getLinkOptions(var11);
                        val var24: Array<LinkOption> = Arrays.copyOf(var18, var18.length);
                        var3x = var34;
                        var1 = var33;
                        var4 = var9;
                        if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var32, Arrays.copyOf(var24, var24.length))) {
                           var33.addAll(var34.readEntries(var29));
                           var3x = var34;
                           var1 = var33;
                           var4 = var9;
                        }
                     } else if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(
                        var26, Arrays.copyOf(new LinkOption[]{PathTreeWalk$$ExternalSyntheticApiModelOutline0.m()}, 1)
                     )) {
                        val var25: Continuation = this;
                        this.L$0 = var4;
                        this.L$1 = var1;
                        this.L$2 = var3x;
                        this.L$3 = null;
                        this.L$4 = null;
                        this.L$5 = null;
                        this.label = 2;
                        if (var4.yield(var26, var25) === var14) {
                           return var14;
                        }
                     }
                  }

                  return Unit.INSTANCE;
               }
            }
         ) as (SequenceScope<? super Path>?, Continuation<? super Unit>?) -> Any
      );
   }

   private fun dfsIterator(): Iterator<Path> {
      return SequencesKt.iterator(
         (
            new Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object>(this, null) {
               private Object L$0;
               Object L$1;
               Object L$2;
               Object L$3;
               Object L$4;
               Object L$5;
               int label;
               final PathTreeWalk this$0;

               {
                  super(2, var2x);
                  this.this$0 = var1;
               }

               @Override
               public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                  val var3: Function2 = new <anonymous constructor>(this.this$0, var2);
                  var3.L$0 = var1;
                  return var3 as Continuation<Unit>;
               }

               public final Object invoke(SequenceScope<? super Path> var1, Continuation<? super Unit> var2x) {
                  return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
               }

               // $VF: Irreducible bytecode was duplicated to produce valid code
               @Override
               public final Object invokeSuspend(Object var1) {
                  var var14: Any;
                  var var21: SequenceScope;
                  var var25: DirectoryEntriesReader;
                  label133: {
                     var14 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     var var9: PathTreeWalk;
                     var var12: Path;
                     var var13: PathNode;
                     var var29: DirectoryEntriesReader;
                     var var43: SequenceScope;
                     var var50: ArrayDeque;
                     if (this.label != 0) {
                        if (this.label != 1) {
                           if (this.label != 2) {
                              if (this.label != 3) {
                                 if (this.label != 4) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                 }

                                 var25 = this.L$2 as DirectoryEntriesReader;
                                 val var30: ArrayDeque = this.L$1 as ArrayDeque;
                                 var21 = this.L$0 as SequenceScope;
                                 ResultKt.throwOnFailure(var1);
                                 var1 = var30;
                                 if (var30.isEmpty()) {
                                    return Unit.INSTANCE;
                                 }
                              } else {
                                 val var51: Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.L$5);
                                 val var22: PathTreeWalk = this.L$4 as PathTreeWalk;
                                 val var31: PathNode = this.L$3 as PathNode;
                                 val var44: DirectoryEntriesReader = this.L$2 as DirectoryEntriesReader;
                                 val var54: ArrayDeque = this.L$1 as ArrayDeque;
                                 val var26: SequenceScope = this.L$0 as SequenceScope;
                                 ResultKt.throwOnFailure(var1);
                                 val var16: Array<LinkOption> = PathTreeWalk.access$getLinkOptions(var22);
                                 val var32: Array<LinkOption> = Arrays.copyOf(var16, var16.length);
                                 var25 = var44;
                                 var1 = var54;
                                 var21 = var26;
                                 if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var51, Arrays.copyOf(var32, var32.length))) {
                                    var31.setContentIterator(var44.readEntries(var31).iterator());
                                    var54.addLast(var31);
                                    var25 = var44;
                                    var1 = var54;
                                    var21 = var26;
                                 }

                                 if (var1.isEmpty()) {
                                    return Unit.INSTANCE;
                                 }
                              }
                           } else {
                              var25 = this.L$2 as DirectoryEntriesReader;
                              val var33: ArrayDeque = this.L$1 as ArrayDeque;
                              var21 = this.L$0 as SequenceScope;
                              ResultKt.throwOnFailure(var1);
                              var1 = var33;
                              if (var33.isEmpty()) {
                                 return Unit.INSTANCE;
                              }
                           }
                           break label133;
                        }

                        val var7: Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.L$5);
                        val var3x: PathTreeWalk = this.L$4 as PathTreeWalk;
                        val var5: PathNode = this.L$3 as PathNode;
                        val var8: DirectoryEntriesReader = this.L$2 as DirectoryEntriesReader;
                        val var6: ArrayDeque = this.L$1 as ArrayDeque;
                        val var4: SequenceScope = this.L$0 as SequenceScope;
                        ResultKt.throwOnFailure(var1);
                        var12 = var7;
                        var50 = var6;
                        var9 = var3x;
                        var13 = var5;
                        var29 = var8;
                        var43 = var4;
                     } else {
                        ResultKt.throwOnFailure(var1);
                        val var27: SequenceScope = this.L$0 as SequenceScope;
                        val var23: ArrayDeque = new ArrayDeque();
                        val var17: DirectoryEntriesReader = new DirectoryEntriesReader(PathTreeWalk.access$getFollowLinks(this.this$0));
                        val var55: PathNode = new PathNode(
                           PathTreeWalk.access$getStart$p(this.this$0),
                           PathTreeWalkKt.access$keyOf(PathTreeWalk.access$getStart$p(this.this$0), PathTreeWalk.access$getLinkOptions(this.this$0)),
                           null
                        );
                        val var10: PathTreeWalk = this.this$0;
                        val var66: Path = var55.getPath();
                        if (var55.getParent() != null) {
                           PathsKt.checkFileName(var66);
                        }

                        val var34: Array<LinkOption> = PathTreeWalk.access$getLinkOptions(var10);
                        val var35: Array<LinkOption> = Arrays.copyOf(var34, var34.length);
                        if (!PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var66, Arrays.copyOf(var35, var35.length))) {
                           var var64: ArrayDeque = var23;
                           var var57: SequenceScope = var27;
                           var var61: DirectoryEntriesReader = var17;
                           if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(
                              var66, Arrays.copyOf(new LinkOption[]{PathTreeWalk$$ExternalSyntheticApiModelOutline0.m()}, 1)
                           )) {
                              val var37: Continuation = this;
                              this.L$0 = var27;
                              this.L$1 = var23;
                              this.L$2 = var17;
                              this.label = 2;
                              var64 = var23;
                              var57 = var27;
                              var61 = var17;
                              if (var27.yield(var66, var37) === var14) {
                                 return var14;
                              }
                           }

                           var1 = var64;
                           var25 = var61;
                           var21 = var57;
                           if (var64.isEmpty()) {
                              return Unit.INSTANCE;
                           }
                           break label133;
                        }

                        if (PathTreeWalkKt.access$createsCycle(var55)) {
                           PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1();
                           throw PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var66.toString());
                        }

                        var50 = var23;
                        var43 = var27;
                        var29 = var17;
                        var13 = var55;
                        var9 = var10;
                        var12 = var66;
                        if (PathTreeWalk.access$getIncludeDirectories(var10)) {
                           val var36: Continuation = this;
                           this.L$0 = var27;
                           this.L$1 = var23;
                           this.L$2 = var17;
                           this.L$3 = var55;
                           this.L$4 = var10;
                           this.L$5 = var66;
                           this.label = 1;
                           if (var27.yield(var66, var36) === var14) {
                              return var14;
                           }

                           var12 = var66;
                           var50 = var23;
                           var9 = var10;
                           var13 = var55;
                           var29 = var17;
                           var43 = var27;
                        }
                     }

                     val var18: Array<LinkOption> = PathTreeWalk.access$getLinkOptions(var9);
                     val var19: Array<LinkOption> = Arrays.copyOf(var18, var18.length);
                     var var63: ArrayDeque = var50;
                     var var56: SequenceScope = var43;
                     var var60: DirectoryEntriesReader = var29;
                     if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var12, Arrays.copyOf(var19, var19.length))) {
                        var13.setContentIterator(var29.readEntries(var13).iterator());
                        var50.addLast(var13);
                        var63 = var50;
                        var56 = var43;
                        var60 = var29;
                     }

                     var1 = var63;
                     var25 = var60;
                     var21 = var56;
                     if (var63.isEmpty()) {
                        return Unit.INSTANCE;
                     }
                  }

                  while (true) {
                     val var38: java.util.Iterator = (var1.last() as PathNode).getContentIterator();
                     if (var38.hasNext()) {
                        val var52: PathNode = var38.next() as PathNode;
                        val var39: PathTreeWalk = this.this$0;
                        val var58: Path = var52.getPath();
                        if (var52.getParent() != null) {
                           PathsKt.checkFileName(var58);
                        }

                        var var45: Array<LinkOption> = PathTreeWalk.access$getLinkOptions(var39);
                        var45 = Arrays.copyOf(var45, var45.length);
                        if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var58, Arrays.copyOf(var45, var45.length))) {
                           if (PathTreeWalkKt.access$createsCycle(var52)) {
                              PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1();
                              throw PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var58.toString());
                           }

                           var var69: DirectoryEntriesReader = var25;
                           var var68: ArrayDeque = var1;
                           var var67: SequenceScope = var21;
                           var var62: PathNode = var52;
                           var var65: PathTreeWalk = var39;
                           var var47: Path = var58;
                           if (PathTreeWalk.access$getIncludeDirectories(var39)) {
                              val var48: Continuation = this;
                              this.L$0 = var21;
                              this.L$1 = var1;
                              this.L$2 = var25;
                              this.L$3 = var52;
                              this.L$4 = var39;
                              this.L$5 = var58;
                              this.label = 3;
                              if (var21.yield(var58, var48) === var14) {
                                 return var14;
                              }

                              var47 = var58;
                              var65 = var39;
                              var62 = var52;
                              var67 = var21;
                              var68 = var1;
                              var69 = var25;
                           }

                           val var20: Array<LinkOption> = PathTreeWalk.access$getLinkOptions(var65);
                           val var41: Array<LinkOption> = Arrays.copyOf(var20, var20.length);
                           var25 = var69;
                           var1 = var68;
                           var21 = var67;
                           if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var47, Arrays.copyOf(var41, var41.length))) {
                              var62.setContentIterator(var69.readEntries(var62).iterator());
                              var68.addLast(var62);
                              var25 = var69;
                              var1 = var68;
                              var21 = var67;
                           }

                           if (var1.isEmpty()) {
                              return Unit.INSTANCE;
                           }
                        } else {
                           if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(
                              var58, Arrays.copyOf(new LinkOption[]{PathTreeWalk$$ExternalSyntheticApiModelOutline0.m()}, 1)
                           )) {
                              val var42: Continuation = this;
                              this.L$0 = var21;
                              this.L$1 = var1;
                              this.L$2 = var25;
                              this.L$3 = null;
                              this.L$4 = null;
                              this.L$5 = null;
                              this.label = 4;
                              if (var21.yield(var58, var42) === var14) {
                                 return var14;
                              }
                           }

                           if (var1.isEmpty()) {
                              return Unit.INSTANCE;
                           }
                        }
                     } else {
                        var1.removeLast();
                        if (var1.isEmpty()) {
                           return Unit.INSTANCE;
                        }
                     }
                  }
               }
            }
         ) as (SequenceScope<? super Path>?, Continuation<? super Unit>?) -> Any
      );
   }

   private suspend inline fun SequenceScope<Path>.yieldIfNeeded(node: PathNode, entriesReader: DirectoryEntriesReader, entriesAction: (List<PathNode>) -> Unit) {
      val var6: Path = var2.getPath();
      if (var2.getParent() != null) {
         PathsKt.checkFileName(var6);
      }

      var var7: Array<LinkOption> = access$getLinkOptions(this);
      var7 = Arrays.copyOf(var7, var7.length);
      if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var6, Arrays.copyOf(var7, var7.length))) {
         if (PathTreeWalkKt.access$createsCycle(var2)) {
            PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1();
            throw PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var6.toString());
         }

         if (access$getIncludeDirectories(this)) {
            var1.yield(var6, var5);
         }

         val var8: Array<LinkOption> = access$getLinkOptions(this);
         val var9: Array<LinkOption> = Arrays.copyOf(var8, var8.length);
         if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var6, Arrays.copyOf(var9, var9.length))) {
            var4.invoke(var3.readEntries(var2));
         }
      } else if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(
         var6, Arrays.copyOf(new LinkOption[]{PathTreeWalk$$ExternalSyntheticApiModelOutline0.m()}, 1)
      )) {
         var1.yield(var6, var5);
         return Unit.INSTANCE;
      }

      return Unit.INSTANCE;
   }

   public override operator fun iterator(): Iterator<Path> {
      val var1: java.util.Iterator;
      if (this.isBFS()) {
         var1 = this.bfsIterator();
      } else {
         var1 = this.dfsIterator();
      }

      return var1;
   }
}
