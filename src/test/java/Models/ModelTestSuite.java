package Models;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit 5 Test Suite Class for running all the Test Cases of Models.
 *
 * @author Virag
 */
@Suite
@SelectClasses({ ContinentTest.class, CountryTest.class, GameMapTest.class, PlayerTest.class })
public class ModelTestSuite {

}
