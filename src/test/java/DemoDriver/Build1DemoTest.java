package DemoDriver;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for Build1Demo driver.
 *
 * @author YURUI
 */
public class Build1DemoTest {

	/**
	 * InputStream instance
	 */
	private final InputStream d_originalSystemIn = System.in;

	/**
	 * PrintStream instance
	 */
	private final PrintStream d_originalSystemOut = System.out;

	/**
	 * ByteArrayOutputStream instance
	 */
	private final ByteArrayOutputStream d_systemOutContent = new ByteArrayOutputStream();

	/**
	 * Set up output stream
	 */
	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(d_systemOutContent));
	}

	/**
	 * Set down input and output stream
	 */
	@AfterEach
	public void tearDown() {
		System.setIn(d_originalSystemIn);
		System.setOut(d_originalSystemOut);
	}

	/**
	 * Test Driver Demo Start when refactoring.
	 */
	@Test
	public void testDriverDemoStart() {
		// Simulate user input
		String l_simulatedInput = "next" + System.lineSeparator() + "gameplayer -add Yurui" + System.lineSeparator()
				+ "\n" + System.lineSeparator() + "assigncountries" + System.lineSeparator() + "next"
				+ System.lineSeparator() + "end" + System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(l_simulatedInput.getBytes()));

		Build1Demo.main(null);

		assertTrue(d_systemOutContent.toString().contains("<<Game Start>>"));
	}

	/**
	 * Test Driver Demo Showmap when refactoring.
	 */
	@Test
	public void testDriverDemoShowmap() {
		// Simulate user input
		String l_simulatedInput = "next" + System.lineSeparator() + "showmap" + System.lineSeparator()
				+ "gameplayer -add Yurui" + System.lineSeparator() + "\n" + System.lineSeparator() + "assigncountries"
				+ System.lineSeparator() + "next" + System.lineSeparator() + "end" + System.lineSeparator() + "end"
				+ System.lineSeparator();
		System.setIn(new ByteArrayInputStream(l_simulatedInput.getBytes()));

		Build1Demo.main(null);

		assertTrue(d_systemOutContent.toString().contains("[Map]"));
	}

	/**
	 * Test Driver Demo End when refactoring.
	 */
	@Test
	public void testDriverDemoEnd() {
		// Simulate user input
		String l_simulatedInput = "next" + System.lineSeparator() + "gameplayer -add Yurui" + System.lineSeparator()
				+ "\n" + System.lineSeparator() + "assigncountries" + System.lineSeparator() + "next"
				+ System.lineSeparator() + "end" + System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(l_simulatedInput.getBytes()));

		Build1Demo.main(null);

		assertTrue(d_systemOutContent.toString().contains("<<EndPhase>>"));
	}
}
