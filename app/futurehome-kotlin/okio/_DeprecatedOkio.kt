package okio

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.nio.file.OpenOption
import java.util.Arrays

@Deprecated(message = "changed in Okio 2.x")
public object _DeprecatedOkio {
   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "file.appendingSink()", imports = ["okio.appendingSink"]))
   public fun appendingSink(file: File): Sink {
      return Okio.appendingSink(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "blackholeSink()", imports = ["okio.blackholeSink"]))
   public fun blackhole(): Sink {
      return Okio.blackhole();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "sink.buffer()", imports = ["okio.buffer"]))
   public fun buffer(sink: Sink): BufferedSink {
      return Okio.buffer(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "source.buffer()", imports = ["okio.buffer"]))
   public fun buffer(source: Source): BufferedSource {
      return Okio.buffer(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "file.sink()", imports = ["okio.sink"]))
   public fun sink(file: File): Sink {
      return Okio.sink$default(var1, false, 1, null);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "outputStream.sink()", imports = ["okio.sink"]))
   public fun sink(outputStream: OutputStream): Sink {
      return Okio.sink(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "socket.sink()", imports = ["okio.sink"]))
   public fun sink(socket: Socket): Sink {
      return Okio.sink(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "path.sink(*options)", imports = ["okio.sink"]))
   public fun sink(path: java.nio.file.Path, vararg options: OpenOption): Sink {
      return Okio.sink(var1, Arrays.copyOf(var2, var2.length));
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "file.source()", imports = ["okio.source"]))
   public fun source(file: File): Source {
      return Okio.source(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "inputStream.source()", imports = ["okio.source"]))
   public fun source(inputStream: InputStream): Source {
      return Okio.source(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "socket.source()", imports = ["okio.source"]))
   public fun source(socket: Socket): Source {
      return Okio.source(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "path.source(*options)", imports = ["okio.source"]))
   public fun source(path: java.nio.file.Path, vararg options: OpenOption): Source {
      return Okio.source(var1, Arrays.copyOf(var2, var2.length));
   }
}
