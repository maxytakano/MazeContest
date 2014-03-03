
package strongpineapple.mazing.engine.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * An integer point. For floating point representation and advanced vector
 * operations, use {@link com.badlogic.gdx.math.Vector2}.
 * 
 * @author Dylan
 * 
 */
public class Point {

	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates an integer point by truncating the x and y fields of the Vector2.
	 */
	public Point(Vector2 vector) {
		this.x = (int) vector.x;
		this.y = (int) vector.y;
	}
	
	public Vector2 toVector2() {
		return new Vector2(x, y);
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	/**
	 * Adds another Point to this Point.
	 * @param x The x coordinate of the other Point
	 * @param y The y coordinate of the other Point
	 * @return A new Point representing the result of the addition.
	 */
	public Point add(int x, int y) {
		return new Point(this.x + x, this.y + y);
	}

	public static double distance(Point p1, Point p2) {
		return Math.pow(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2), .5);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point))
			return false;
		
		Point other = (Point) obj;
		return this.x == other.x && this.y == other.y;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public String toString() {
		return String.format("(%d,  %d)", x, y);
	}
}
