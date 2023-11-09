package Models;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ContinentTest.class, CountryTest.class, GameMapTest.class, PlayerTest.class })
public class ModelTestSuite {

}
