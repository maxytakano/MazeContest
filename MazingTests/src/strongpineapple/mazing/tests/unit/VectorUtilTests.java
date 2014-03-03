package strongpineapple.mazing.tests.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import strongpineapple.mazing.engine.utils.VectorUtil;

import com.badlogic.gdx.math.Vector2;

public class VectorUtilTests {

	@Test
	public void testIntersectRayCircle() {
		Vector2 rayPoint = new Vector2(1, 2);
		Vector2 rayDirection = new Vector2(0, 1);
		Vector2 circleCenter = new Vector2(2, 10);
		float radius = 2;
		
		Vector2 firstIntersection = circleCenter.cpy().add(-1, -(float) Math.sqrt(radius * radius - 1));
		Vector2 secondIntersection = circleCenter.cpy().add(-1, (float) Math.sqrt(radius * radius - 1));
		List<Vector2> expected = new ArrayList<Vector2>();
		expected.add(firstIntersection);
		expected.add(secondIntersection);
		List<Vector2> actual = VectorUtil.intersectRayCircle(rayPoint, rayDirection, circleCenter, radius);
		assertArrayEquals(expected.toArray(), actual.toArray());
	}
}
