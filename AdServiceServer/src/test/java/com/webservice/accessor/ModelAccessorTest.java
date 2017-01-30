package test.java.com.webservice.accessor;

import org.testng.annotations.Test;

import main.java.com.webservice.accessor.ModelAccessor;
import main.java.com.webservice.model.AdModel;
import main.java.com.webservice.util.ResultObject;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

@Test(groups="unitTests")
public class ModelAccessorTest {
	
  private ModelAccessor modelAccessor_;
  
  @DataProvider(name="insertModelDataProvider")
  public Object[][] insertModelDataProvider() {
    Object[][] result = new Object[][] { { "AdCampaign1001", "content for AdCampaign1001",60 },
                                         { "AdCampaign1002", "content for AdCampaign1002",60 },
                                         {null,"content for null",60}
                                         };
    return result;
  }
  
  @DataProvider(name="getModelDataProvider")
  public Object[][] getModelDataProvider() {
    Object[][] result = new Object[][] { { "AdCampaign2001", "content for AdCampaign1001",60 },
                                         { "AdCampaign2002", "content for AdCampaign1002",60 },
                                         {"AdCampaign2003","content for AdCampaign2003",60},
                                         {"AdCampaign2004","content for AdCampaign2004",60},
                                         {"AdCampaign2005","content for AdCampaign2005",60},
                                         {"AdCampaign2006","content for AdCampaign2006",60},
                                         {"AdCampaign2007","content for AdCampaign2007",60},
                                         {"AdCampaign2008","content for AdCampaign2008",60},
                                         {"AdCampaign2009","content for AdCampaign2009",60},
                                         {"AdCampaign2010","content for AdCampaign2010",60}
                                         };
    return result;
  }
  
  
  @BeforeClass
  public void beforeClass() {
	  //initialize the modelaccessor
	  modelAccessor_ = ModelAccessor.getInstance();
	  modelAccessor_.clearModelCache();
  }

  @AfterClass
  public void afterClass() {
	  modelAccessor_.clearModelCache();
  }


  @Test(dependsOnMethods="getModel")
  public void getAllModels() {
	  String method="getAllModels()";
	  ResultObject result = modelAccessor_.getAllModels();
	  Assert.assertNotNull(result, method+" test failed , return object is null");
	  Assert.assertNotNull(result.getMessage(), "test failed , message in the return object is null");
	  
    
  }

  @Test(dataProvider="getModelDataProvider",dependsOnMethods="insertModel")
  public void getModel(String partnerId, String adContent, float duration) {
    String method = "getModel()";
    AdModel model = new AdModel();
    model.setPartnerId(partnerId);
    model.setAdContent(adContent);
    model.setDuration(duration);
    ResultObject result = modelAccessor_.insertModel(model);
    Assert.assertNotNull(result, method+" test failed , inserting the model failed");
    Assert.assertNotNull(result.getMessage(), "test failed , inserting the model failed");
    //use get api to retrieve the data
    result = modelAccessor_.getModel(partnerId);
    Assert.assertNotNull(result, method+" test failed , received a null result");
    Assert.assertNotNull(result.getMessage(), "test failed , receieved a null message in the result");
    String message = result.getMessage();
    assert message.contains(partnerId):method+" wrong ad object retrieved";
    
  }

  @Test(dataProvider="insertModelDataProvider")
  public void insertModel(String partnerId, String adContent, float duration) {
	String method = "insertModel()";
    AdModel model = new AdModel();
    model.setPartnerId(partnerId);
    model.setAdContent(adContent);
    model.setDuration(duration);
    ResultObject result = modelAccessor_.insertModel(model);
    Assert.assertNotNull(result, method+" test failed , received a null result object");
    Assert.assertNotNull(result.getMessage(), "test failed , message expected in the result but null value is received");
    
  }
}
