package Controller;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * ControllerTestSuite is a JUnit test suite for the Controller package.
 * It combines and runs the tests from the selected classes, such as TournamentTest and the GameEngine Test.
 *
 * This suite is annotated with JUnit's @Suite and @SelectClasses annotations
 * to specify the classes that should be included in the test suite.
 *
 * Usage example:
 * <pre>
 * {@code
 *     @RunWith(JUnitPlatform.class)
 *     @IncludeTags("controller")
 *     public class RunControllerTests {
 *         // This class remains empty. It is used only as a holder for the above annotations.
 *     }
 * }
 * </pre>
 *
 * @author Virag
 * @see org.junit.platform.suite.api.Suite
 * @see org.junit.platform.suite.api.SelectClasses
 */
@Suite
@SelectClasses({ GameEngineTest.class, TournamentTest.class })
public class ControllerTestSuite {

}
