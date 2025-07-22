package kotlin.text

public interface MatchNamedGroupCollection : MatchGroupCollection {
   public abstract operator fun get(name: String): MatchGroup? {
   }
}
