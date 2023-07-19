package c1220ftjavareact.gym;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import junit.framework.TestSuite;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class GymApplicationTests  extends TestCase {

	public GymApplicationTests() {
		super();
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static junit.framework.Test suite()
	{
		return new TestSuite(GymApplicationTests.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void testGymApplicationTests()
	{
		assertTrue( true );
	}
}
