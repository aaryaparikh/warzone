package DemoDriver;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * This JUnit 5 TestSuite class encompasses tests for the DemoDriver package, specifically focusing on different builds.
 * It includes test classes for Build1DemoTest and Build2DemoTest.
 *
 * Usage:
 * To execute all the demo driver tests, run this class as a JUnit test.
 *
 * @author Virag
 */
@Suite
@SelectClasses({ Build1DemoTest.class, Build2DemoTest.class })
public class DemoDriverTestSuite {

}
