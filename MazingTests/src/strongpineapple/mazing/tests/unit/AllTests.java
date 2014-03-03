package strongpineapple.mazing.tests.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ManyNodeTests.class, PathBlockTests.class, PathingTests.class,
		RunPhaseEngineTests.class, StandardMazes.class, VectorUtilTests.class })
public class AllTests {

}
