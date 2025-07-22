package kotlin.uuid

import java.security.SecureRandom

private object SecureRandomHolder {
   public final val instance: SecureRandom = new SecureRandom()
}
