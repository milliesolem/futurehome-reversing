package dev.flutter.plugins.integration_test;

import android.util.Log;
import androidx.test.rule.ActivityTestRule;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class FlutterTestRunner extends Runner {
   private static final String TAG = "FlutterTestRunner";
   TestRule rule = null;
   final Class<?> testClass;

   public FlutterTestRunner(Class<?> var1) {
      this.testClass = var1;

      for (Field var5 : var1.getDeclaredFields()) {
         if (var5.isAnnotationPresent(Rule.class)) {
            try {
               Object var6 = var1.getDeclaredConstructor(null).newInstance(null);
               if (var5.get(var6) instanceof ActivityTestRule) {
                  this.rule = (TestRule)var5.get(var6);
                  break;
               }
               continue;
            } catch (NoSuchMethodException | InvocationTargetException var7) {
               StringBuilder var11 = new StringBuilder("Unable to construct ");
               var11.append(var1.getName());
               var11.append(" object for testing");
               throw new RuntimeException(var11.toString());
            } catch (InstantiationException var8) {
               var10 = var8;
            } catch (IllegalAccessException var9) {
               var10 = var9;
            }

            throw new RuntimeException("Unable to access activity rule", (Throwable)var10);
         }
      }
   }

   public Description getDescription() {
      return Description.createTestDescription(this.testClass, "Flutter Tests");
   }

   public void run(RunNotifier var1) {
      TestRule var2 = this.rule;
      if (var2 != null) {
         try {
            if (var2 instanceof ActivityTestRule) {
               ((ActivityTestRule)var2).launchActivity(null);
            }
         } catch (RuntimeException var7) {
            StringBuilder var3 = new StringBuilder("launchActivity failed, possibly because the activity was already running. ");
            var3.append(var7);
            Log.v("FlutterTestRunner", var3.toString());
            Log.v("FlutterTestRunner", "Try disabling auto-launch of the activity, e.g. ActivityTestRule<>(MainActivity.class, true, false);");
         }

         Map var4;
         try {
            var4 = IntegrationTestPlugin.testResults.get();
         } catch (InterruptedException | ExecutionException var6) {
            throw new IllegalThreadStateException("Unable to get test results");
         }

         for (String var5 : var4.keySet()) {
            Description var9 = Description.createTestDescription(this.testClass, var5);
            var1.fireTestStarted(var9);
            var5 = (String)var4.get(var5);
            if (!var5.equals("success")) {
               var1.fireTestFailure(new Failure(var9, new Exception(var5)));
            }

            var1.fireTestFinished(var9);
         }
      } else {
         throw new RuntimeException("Unable to run tests due to missing activity rule");
      }
   }
}
