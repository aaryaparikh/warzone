package DemoDriver;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit 5 Test Suite Class for running all the Test Cases of DemoDriver.
 *
 * @author Yurui
 */
@Suite
@SelectClasses({ Build1DemoTest.class, Build2DemoTest.class })
public class DemoDriverTestSuite {

}
