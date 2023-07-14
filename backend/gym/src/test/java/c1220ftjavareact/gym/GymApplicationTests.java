package c1220ftjavareact.gym;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = "c1220ftjavareact.gym")
class GymApplicationTests extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public GymApplicationTests( String testName )
	{
		super( testName );
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( GymApplicationTests.class );
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testGymApplicationTests()
	{
		assertTrue( true );
	}

}
