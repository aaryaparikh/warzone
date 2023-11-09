package Models;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * This JUnit 5 Test Suite Class is responsible for running all the test cases
 * related to the Models package. It includes test classes for Continent,
 * Country, GameMap, and Player.
 *
 * Usage: To execute all the Models package tests, run this class as a JUnit
 * test.
 *
 * Example:
 * 
 * <pre>
 * {@code
 * ModelTestSuite suite = new ModelTestSuite();
 * JUnitCore.runClasses(suite.getClass());
 * }
 * </pre>
 *
 * @author <b>Virag</b>
 */
@Suite
@SelectClasses({ ContinentTest.class, CountryTest.class, GameMapTest.class, PlayerTest.class })
public class ModelTestSuite {

}
