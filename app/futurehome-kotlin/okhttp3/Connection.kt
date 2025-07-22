package okhttp3

import java.net.Socket

public interface Connection {
   public abstract fun handshake(): Handshake? {
   }

   public abstract fun protocol(): Protocol {
   }

   public abstract fun route(): Route {
   }

   public abstract fun socket(): Socket {
   }
}
