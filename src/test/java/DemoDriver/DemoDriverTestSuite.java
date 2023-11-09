package DemoDriver;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ Build1DemoTest.class, Build2DemoTest.class })
public class DemoDriverTestSuite {

}
