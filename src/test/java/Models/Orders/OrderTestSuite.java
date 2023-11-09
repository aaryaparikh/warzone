package Models.Orders;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit 5 TestSuite class for all the classes testing the order phase.
 *
 * @author Virag
 */
@Suite
@SelectClasses({ AdvanceOrderTest.class, AirliftOrderTest.class, BlockadeOrder.class, DeployOrderTest.class,
		DiplomacyOrderTest.class, AirliftOrderTest.class })
public class OrderTestSuite {

}
