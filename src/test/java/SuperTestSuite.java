import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import DemoDriver.DemoDriverTestSuite;
import MapTest.InvalidMapTest;
import Models.ModelTestSuite;
import Models.Orders.OrderTestSuite;
import StartupPhase.StartupPhaseTest;
import Utils.UtilsTestSuite;

@Suite
@SelectClasses({ModelTestSuite.class, OrderTestSuite.class, StartupPhaseTest.class, InvalidMapTest.class, UtilsTestSuite.class, DemoDriverTestSuite.class})
public class SuperTestSuite {

}
