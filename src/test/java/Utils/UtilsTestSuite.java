package Utils;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ MapCommandHandlerTest.class, PlayerCommandHandlerTest.class })
public class UtilsTestSuite {

}
