package strongpineapple.mazing.engine.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class VectorUtil {
	/**
	 * @param rayPoint
	 * @param rayDirection
	 * @param center
	 * @param radius
	 * @return 0, 1, or 2 intersection points
	 */
	public static List<Vector2> intersectRayCircle(Vector2 rayPoint, Vector2 rayDirection, Vector2 center, float radius) {
		List<Vector2> result = new ArrayList<Vector2>();
		
		Vector2 diffVector = center.cpy().sub(rayPoint);
		// Project the center of the circle onto the difference vector
		Vector2 diffProjDirection = rayDirection.cpy().nor();
		diffProjDirection.mul(diffVector.dot(rayDirection.cpy().nor()));
		// The closest point from the ray to the center is the ray point + the projection
		Vector2 closestPoint = rayPoint.cpy().add(diffProjDirection);
		float dst2ClosestPointToIntersection = radius * radius - closestPoint.dst2(center);
		if (dst2ClosestPointToIntersection < 0) {
			return result;
		}
		
		float dstClosestPointToIntersection = (float) Math.sqrt(dst2ClosestPointToIntersection);
		Vector2 diffClosestPointIntersection = rayDirection.cpy().nor().mul(dstClosestPointToIntersection);
		result.add(closestPoint.cpy().sub(diffClosestPointIntersection));
		
		if (dstClosestPointToIntersection != 0) {
			// There are 2 intersections
			result.add(closestPoint.cpy().add(diffClosestPointIntersection));
		}
		
		return result;
	}
}
