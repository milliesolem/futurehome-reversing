package kotlin.contracts

public interface SimpleEffect : Effect {
   public abstract infix fun implies(booleanExpression: Boolean): ConditionalEffect {
   }
}
