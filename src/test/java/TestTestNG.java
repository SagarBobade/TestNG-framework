import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTestNG {
  @Test
  public void f() {
	  String expected = "xyz";
	     String actual = "abc"; // Expected and actual values are different
	     Assert.assertEquals(actual, expected);
  }
}
