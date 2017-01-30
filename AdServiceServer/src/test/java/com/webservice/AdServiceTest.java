package test.java.com.webservice;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

@Test(groups="integTests", dependsOnGroups="unitTests")
public class AdServiceTest {

	private String hostName="";
  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { 1, "a" },
      new Object[] { 2, "b" },
    };
  }
  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }

  @BeforeSuite
  public void beforeSuite() {
  }

  @AfterSuite
  public void afterSuite() {
  }
  
  private void initConnection() {
  }
  
  @Test
  public void createModelTest() {
	  
  }

}
