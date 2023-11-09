
package Models.Orders;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * This JUnit 5 TestSuite class encompasses tests for the various order-related
 * functionalities in the 'Models.Orders' package. It includes test classes for
 * AdvanceOrder, AirliftOrder, BlockadeOrder, DeployOrder, and DiplomacyOrder.
 *
 * Usage: To execute all the order-related tests, run this class as a JUnit
 * test.
 *
 * Example:
 * 
 * <pre>
 * {@code
 * OrderTestSuite suite = new OrderTestSuite();
 * JUnitCore.runClasses(suite.getClass());
 * }
 * </pre>
 *
 * @author Virag
 */
@Suite
@SelectClasses({ AdvanceOrderTest.class, AirliftOrderTest.class, BlockadeOrder.class, DeployOrderTest.class,
		DiplomacyOrderTest.class, AirliftOrderTest.class })
public class OrderTestSuite {

}
