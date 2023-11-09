package Utils;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
/**
 * This class represents a JUnit 5 test suite for the Utils package. 
 * It combines multiple test classes related to the Utils package into a single suite.
 * The included test classes are MapCommandHandlerTest and PlayerCommandHandlerTest.
 * 
 * Usage:
 * To execute all the tests within this suite, run this class as a JUnit test.
 * 
 * @author Virag
 */
@Suite
@SelectClasses({ MapCommandHandlerTest.class, PlayerCommandHandlerTest.class })
public class UtilsTestSuite {

}
