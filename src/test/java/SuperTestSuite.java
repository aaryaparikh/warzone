import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import Controller.ControllerTestSuite;
import DemoDriver.DemoDriverTestSuite;
import MapTest.InvalidMapTest;
import Models.ModelTestSuite;
import Models.Orders.OrderTestSuite;
import StartupPhase.StartupPhaseTest;
import Utils.UtilsTestSuite;

/**
 * The SuperTestSuite class is the top-level JUnit 5 test suite for the entire
 * project. It includes various sub-test suites covering different aspects of
 * the project's functionality.
 *
 *
 * <b>Sub-suites included in this SuperTestSuite:</b>
 * <ul>
 * <li>ModelTestSuite: Tests for the Models package, covering data structures
 * and game components.</li>
 * <li>OrderTestSuite: Tests for order-related functionality.</li>
 * <li>StartupPhaseTest: Tests for the game startup phase and
 * initialization.</li>
 * <li>InvalidMapTest: Tests for invalid map configurations.</li>
 * <li>UtilsTestSuite: Tests for utility classes and helpers.</li>
 * <li>DemoDriverTestSuite: Tests for the demo driver or user interface
 * components.</li>
 * </ul>
 *
 * Usage: To execute all the tests for the entire project, run this class as a
 * JUnit test.
 *
 * @author Virag
 */
@Suite
@SelectClasses({ ModelTestSuite.class, OrderTestSuite.class, StartupPhaseTest.class, InvalidMapTest.class,
		UtilsTestSuite.class, DemoDriverTestSuite.class, ControllerTestSuite.class })
public class SuperTestSuite {

}
