package kotlinx.coroutines.tasks

import java.util.concurrent.Executor

private object DirectExecutor : Executor {
   public override fun execute(r: Runnable) {
      var1.run();
   }
}
