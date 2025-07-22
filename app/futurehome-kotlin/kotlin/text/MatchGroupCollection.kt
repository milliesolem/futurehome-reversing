package kotlin.text

import kotlin.jvm.internal.markers.KMappedMarker

public interface MatchGroupCollection : java.util.Collection<MatchGroup>, KMappedMarker {
   public abstract operator fun get(index: Int): MatchGroup? {
   }
}
