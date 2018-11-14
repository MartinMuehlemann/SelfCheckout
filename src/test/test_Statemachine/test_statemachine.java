package test.test_Statemachine;

import com.swisshof.selfcheckout.ResourceProvider;
import com.swisshof.selfcheckout.SelfCheckoutContext;

public class test_statemachine {
	
	protected SelfCheckoutContext context = null;

	public void before()
	{
		context = new SelfCheckoutContext();
		context.setResourceProvider(new ResourceProvider());

	}
}
