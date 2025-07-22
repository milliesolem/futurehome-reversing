package no.futurehome.futurehome_app

import com.polidea.rxandroidble2.exceptions.BleException
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugins.GeneratedPluginRegistrant
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import kotlin.jvm.functions.Function1

public class MainActivity : FlutterFragmentActivity {
   @JvmStatic
   fun `configureFlutterEngine$lambda$0`(var0: java.lang.Throwable): Unit {
      if (var0 is UndeliverableException && var0.getCause() is BleException) {
         return Unit.INSTANCE;
      } else {
         throw var0;
      }
   }

   @JvmStatic
   fun `configureFlutterEngine$lambda$1`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   public override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
      GeneratedPluginRegistrant.registerWith(var1);
      RxJavaPlugins.setErrorHandler(new MainActivity$$ExternalSyntheticLambda1(new MainActivity$$ExternalSyntheticLambda0()));
   }
}
