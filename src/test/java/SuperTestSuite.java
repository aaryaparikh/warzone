import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import MapTest.InvalidMapTest;
import Models.ModelTestSuite;
import Models.Orders.OrderTestSuite;
import StartupPhase.StartupPhaseTest;

@Suite
@SelectClasses({ModelTestSuite.class, OrderTestSuite.class, StartupPhaseTest.class, InvalidMapTest.class})
public class SuperTestSuite {

}
