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
 * JUnit 5 test class for Build2Demo driver.
 *
 * @author YURUI
 */
public class Build2DemoTest {

	private final InputStream d_originalSystemIn = System.in;
	private final PrintStream d_originalSystemOut = System.out;
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
		String l_simulatedInput = "next" + System.lineSeparator() + "next" + System.lineSeparator() + "end"
				+ System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(l_simulatedInput.getBytes()));

		Build2Demo.main(null);

		assertTrue(d_systemOutContent.toString().contains("<<Game Start>>"));
	}

	/**
	 * Test Driver Demo Showmap when refactoring.
	 */
	@Test
	public void testDriverDemoShowmap() {
		// Simulate user input
		String l_simulatedInput = "next" + System.lineSeparator() + "showmap" + System.lineSeparator() + "next"
				+ System.lineSeparator() + "end" + System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(l_simulatedInput.getBytes()));

		Build2Demo.main(null);

		assertTrue(d_systemOutContent.toString().contains("[Map]"));
	}

	/**
	 * Test Driver Demo End when refactoring.
	 */
	@Test
	public void testDriverDemoEnd() {
		// Simulate user input
		String l_simulatedInput = "next" + System.lineSeparator() + "next" + System.lineSeparator() + "end"
				+ System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(l_simulatedInput.getBytes()));

		Build2Demo.main(null);

		assertTrue(d_systemOutContent.toString().contains("<<EndPhase>>"));
	}
}
