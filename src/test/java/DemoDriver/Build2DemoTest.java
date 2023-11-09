package DemoDriver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

/**
 * JUnit 5 test class for Build2Demo driver.
 * 
 * @author YURUI
 */
public class Build2DemoTest {

	private final InputStream originalSystemIn = System.in;
	private final PrintStream originalSystemOut = System.out;
	private final ByteArrayOutputStream systemOutContent = new ByteArrayOutputStream();

	/**
	 * Set up output stream
	 */
	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(systemOutContent));
	}

	/**
	 * Set down input and output stream
	 */
	@AfterEach
	public void tearDown() {
		System.setIn(originalSystemIn);
		System.setOut(originalSystemOut);
	}

	/**
	 * Test Driver Demo Start when refactoring.
	 */
	@Test
	public void testDriverDemoStart() {
		// Simulate user input
		String simulatedInput = "next" + System.lineSeparator() + "next" + System.lineSeparator() + "end"
				+ System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		Build2Demo.main(null);

		assertTrue(systemOutContent.toString().contains("<<Game Start>>"));
	}

	/**
	 * Test Driver Demo Showmap when refactoring.
	 */
	@Test
	public void testDriverDemoShowmap() {
		// Simulate user input
		String simulatedInput = "next" + System.lineSeparator() + "showmap" + System.lineSeparator() + "next"
				+ System.lineSeparator() + "end" + System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		Build2Demo.main(null);

		assertTrue(systemOutContent.toString().contains("[Map]"));
	}

	/**
	 * Test Driver Demo End when refactoring.
	 */
	@Test
	public void testDriverDemoEnd() {
		// Simulate user input
		String simulatedInput = "next" + System.lineSeparator() + "next" + System.lineSeparator() + "end"
				+ System.lineSeparator() + "end" + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

		Build2Demo.main(null);

		assertTrue(systemOutContent.toString().contains("<<EndPhase>>"));
	}
}
