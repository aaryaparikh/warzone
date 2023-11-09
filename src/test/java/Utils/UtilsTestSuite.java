package Utils;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit 5 TestSuite class for all the classes testing the Utils.
 *
 * @author Yurui
 */
@Suite
@SelectClasses({ MapCommandHandlerTest.class, PlayerCommandHandlerTest.class })
public class UtilsTestSuite {

}
