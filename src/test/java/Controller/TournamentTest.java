package Controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DemoDriver.Build3Demo;

/**
 * JUnit 5 test class for checking whether the Tournament runs good.
 *
 * @author Dev
 */
class TournamentTest {
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
	 * Test to see whether the tournament is exited forcefully.
	 */
	@Test
	public void testTournamentEnded() {
		// Simulate user input
		String l_simulatedInput = "next" + System.lineSeparator()
				+ "tournament -M firstmap.txt secondmap.txt thirdmap.txt -P aggressive cheater random -G 3 -D 7"
				+ System.lineSeparator() + "back" + System.lineSeparator() + "next" + System.lineSeparator() + "end"
				+ System.lineSeparator() + "end";
		System.setIn(new ByteArrayInputStream(l_simulatedInput.getBytes()));

		Build3Demo.main(null);

		assertTrue(d_systemOutContent.toString().contains("<<EndPhase>><end>"));
	}

	/**
	 * Test to see whether the tournament can be bypassed
	 */
	@Test
	public void testToByPassTournament() {
		// Simulate user input
		String l_simulatedInput = "next" + System.lineSeparator() + "tournament" + System.lineSeparator() + "next"
				+ System.lineSeparator() + "end" + System.lineSeparator() + "end";
		System.setIn(new ByteArrayInputStream(l_simulatedInput.getBytes()));

		Build3Demo.main(null);

		assertTrue(d_systemOutContent.toString().contains("<<EndPhase>><end>"));
	}
}
