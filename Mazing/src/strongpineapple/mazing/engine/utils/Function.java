package strongpineapple.mazing.engine.utils;

/**
 * A utility class that defines a generic function with one input.
 * @author Dylan
 *
 * @param <O> The return type of the function.
 * @param <I> The input type of the function.
 */
public interface Function<O, I> {
	/**
	 * Evaluates this function.
	 * @return the result.
	 */
	O eval(I arg);
}
