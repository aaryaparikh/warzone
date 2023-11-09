/**
 * JUnit 5 TestSuite class for all the classes testing the order phase.
 *
 * @author Virag
 */
package Models.Orders;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses ({AdvanceOrderTest.class, AirliftOrderTest.class, BlockadeOrder.class, DeployOrderTest.class, DiplomacyOrderTest.class, AirliftOrderTest.class })
public class OrderTestSuite {

}

