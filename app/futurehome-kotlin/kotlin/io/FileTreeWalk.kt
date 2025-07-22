package kotlin.io

import java.io.File
import java.io.IOException
import java.util.ArrayDeque
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2

public class FileTreeWalk private constructor(start: File,
      direction: FileWalkDirection = FileWalkDirection.TOP_DOWN,
      onEnter: ((File) -> Boolean)?,
      onLeave: ((File) -> Unit)?,
      onFail: ((File, IOException) -> Unit)?,
      maxDepth: Int = Integer.MAX_VALUE
   ) :
   Sequence<File> {
   private final val start: File
   private final val direction: FileWalkDirection
   private final val onEnter: ((File) -> Boolean)?
   private final val onLeave: ((File) -> Unit)?
   private final val onFail: ((File, IOException) -> Unit)?
   private final val maxDepth: Int

   internal constructor(start: File, direction: FileWalkDirection = FileWalkDirection.TOP_DOWN) : this(var1, var2, null, null, null, 0, 32, null)
   init {
      super();
      this.start = var1;
      this.direction = var2;
      this.onEnter = var3;
      this.onLeave = var4;
      this.onFail = var5;
      this.maxDepth = var6;
   }

   public override operator fun iterator(): Iterator<File> {
      return new FileTreeWalk.FileTreeWalkIterator(this);
   }

   public fun maxDepth(depth: Int): FileTreeWalk {
      if (var1 > 0) {
         return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, this.onFail, var1);
      } else {
         val var2: StringBuilder = new StringBuilder("depth must be positive, but was ");
         var2.append(var1);
         var2.append('.');
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public fun onEnter(function: (File) -> Boolean): FileTreeWalk {
      return new FileTreeWalk(this.start, this.direction, var1, this.onLeave, this.onFail, this.maxDepth);
   }

   public fun onFail(function: (File, IOException) -> Unit): FileTreeWalk {
      return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, var1, this.maxDepth);
   }

   public fun onLeave(function: (File) -> Unit): FileTreeWalk {
      return new FileTreeWalk(this.start, this.direction, this.onEnter, var1, this.onFail, this.maxDepth);
   }

   private abstract class DirectoryState : FileTreeWalk.WalkState {
      open fun DirectoryState(var1: File) {
         super(var1);
      }
   }

   private inner class FileTreeWalkIterator : AbstractIterator<File> {
      private final val state: ArrayDeque<kotlin.io.FileTreeWalk.WalkState>

      init {
         this.this$0 = var1;
         val var2: ArrayDeque = new ArrayDeque();
         this.state = var2;
         if (FileTreeWalk.access$getStart$p(var1).isDirectory()) {
            var2.push(this.directoryState(FileTreeWalk.access$getStart$p(var1)));
         } else if (FileTreeWalk.access$getStart$p(var1).isFile()) {
            var2.push(new FileTreeWalk.FileTreeWalkIterator.SingleFileState(this, FileTreeWalk.access$getStart$p(var1)));
         } else {
            this.done();
         }
      }

      private fun directoryState(root: File): kotlin.io.FileTreeWalk.DirectoryState {
         val var2: Int = FileTreeWalk.FileTreeWalkIterator.WhenMappings.$EnumSwitchMapping$0[FileTreeWalk.access$getDirection$p(this.this$0).ordinal()];
         val var4: FileTreeWalk.DirectoryState;
         if (var2 != 1) {
            if (var2 != 2) {
               throw new NoWhenBranchMatchedException();
            }

            var4 = new FileTreeWalk.FileTreeWalkIterator.BottomUpDirectoryState(this, var1);
         } else {
            var4 = new FileTreeWalk.FileTreeWalkIterator.TopDownDirectoryState(this, var1);
         }

         return var4;
      }

      private tailrec fun gotoNext(): File? {
         while (true) {
            val var2: FileTreeWalk.WalkState = this.state.peek();
            if (var2 == null) {
               return null;
            }

            val var1: File = var2.step();
            if (var1 == null) {
               this.state.pop();
            } else {
               if (var1 == var2.getRoot() || !var1.isDirectory() || this.state.size() >= FileTreeWalk.access$getMaxDepth$p(this.this$0)) {
                  return var1;
               }

               this.state.push(this.directoryState(var1));
            }
         }
      }

      protected override fun computeNext() {
         val var1: File = this.gotoNext();
         if (var1 != null) {
            this.setNext(var1);
         } else {
            this.done();
         }
      }

      private inner class BottomUpDirectoryState(rootDir: File) : FileTreeWalk.DirectoryState(var2) {
         private final var rootVisited: Boolean
         private final var fileList: Array<File>?
         private final var fileIndex: Int
         private final var failed: Boolean

         init {
            this.this$0 = var1;
         }

         public override fun step(): File? {
            if (!this.failed && this.fileList == null) {
               val var2: Function1 = FileTreeWalk.access$getOnEnter$p(this.this$0.this$0);
               if (var2 != null && !var2.invoke(this.getRoot()) as java.lang.Boolean) {
                  return null;
               }

               val var4: Array<File> = this.getRoot().listFiles();
               this.fileList = var4;
               if (var4 == null) {
                  val var5: Function2 = FileTreeWalk.access$getOnFail$p(this.this$0.this$0);
                  if (var5 != null) {
                     var5.invoke(this.getRoot(), new AccessDeniedException(this.getRoot(), null, "Cannot list files in a directory", 2, null));
                  }

                  this.failed = true;
               }
            }

            var var6: Array<File> = this.fileList;
            if (this.fileList != null) {
               val var1: Int = this.fileIndex;
               if (var1 < var6.length) {
                  var6 = this.fileList;
                  return var6[this.fileIndex++];
               }
            }

            if (!this.rootVisited) {
               this.rootVisited = true;
               return this.getRoot();
            } else {
               val var7: Function1 = FileTreeWalk.access$getOnLeave$p(this.this$0.this$0);
               if (var7 != null) {
                  var7.invoke(this.getRoot());
               }

               return null;
            }
         }
      }

      private inner class SingleFileState(rootFile: File) : FileTreeWalk.WalkState(var2) {
         private final var visited: Boolean

         init {
            this.this$0 = var1;
         }

         public override fun step(): File? {
            if (this.visited) {
               return null;
            } else {
               this.visited = true;
               return this.getRoot();
            }
         }
      }

      private inner class TopDownDirectoryState(rootDir: File) : FileTreeWalk.DirectoryState(var2) {
         private final var rootVisited: Boolean
         private final var fileList: Array<File>?
         private final var fileIndex: Int

         init {
            this.this$0 = var1;
         }

         public override fun step(): File? {
            if (!this.rootVisited) {
               val var10: Function1 = FileTreeWalk.access$getOnEnter$p(this.this$0.this$0);
               if (var10 != null && !var10.invoke(this.getRoot()) as java.lang.Boolean) {
                  return null;
               } else {
                  this.rootVisited = true;
                  return this.getRoot();
               }
            } else {
               var var2: Array<File> = this.fileList;
               if (this.fileList != null) {
                  val var1: Int = this.fileIndex;
                  if (var1 >= var2.length) {
                     val var9: Function1 = FileTreeWalk.access$getOnLeave$p(this.this$0.this$0);
                     if (var9 != null) {
                        var9.invoke(this.getRoot());
                     }

                     return null;
                  }
               }

               label42:
               if (this.fileList == null) {
                  var2 = this.getRoot().listFiles();
                  this.fileList = var2;
                  if (var2 == null) {
                     val var5: Function2 = FileTreeWalk.access$getOnFail$p(this.this$0.this$0);
                     if (var5 != null) {
                        var5.invoke(this.getRoot(), new AccessDeniedException(this.getRoot(), null, "Cannot list files in a directory", 2, null));
                     }
                  }

                  var2 = this.fileList;
                  if (this.fileList != null) {
                     if (var2.length != 0) {
                        break label42;
                     }
                  }

                  val var7: Function1 = FileTreeWalk.access$getOnLeave$p(this.this$0.this$0);
                  if (var7 != null) {
                     var7.invoke(this.getRoot());
                  }

                  return null;
               }

               var2 = this.fileList;
               return var2[this.fileIndex++];
            }
         }
      }
   }

   private abstract class WalkState {
      public final val root: File

      open fun WalkState(var1: File) {
         this.root = var1;
      }

      public abstract fun step(): File? {
      }
   }
}
