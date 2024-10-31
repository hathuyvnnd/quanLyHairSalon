import org.testng.Assert;
import org.testng.IRetryAnalyzer;

import org.testng.annotations.CustomAttribute;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class BaoCao implements org.testng.internal.annotations.ITest{
private String param;
	public BaoCao(String p) {
		this.param= p;
	}
	
	@Override
	public String getSuiteName() {
		
		return  getClass().getSimpleName() + "-" + param;
	}
	@Factory
	public Object[] create() {
		return new Object [] {new BaoCao("Test Salon ACADEMY"),new BaoCao("Report")};
	}
	
	@Test
	public void t() {
		if(param.equals("Report")) {
			Assert.assertTrue(false);
		}
	}
	@Override
	public int getInvocationCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setInvocationCount(int l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getThreadPoolSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setThreadPoolSize(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSuccessPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSuccessPercentage(int s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getAlwaysRun() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAlwaysRun(boolean f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?>[] getExpectedExceptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExpectedExceptions(Class<?>[] e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getExpectedExceptionsMessageRegExp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExpectedExceptionsMessageRegExp(String e) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void setSuiteName(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTestName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTestName(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getSingleThreaded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSingleThreaded(boolean f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDataProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataProvider(String v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getDataProviderClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataProviderClass(Class<?> v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDataProviderDynamicClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataProviderDynamicClass(String v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRetryAnalyzer(Class<? extends IRetryAnalyzer> c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<? extends IRetryAnalyzer> getRetryAnalyzerClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean skipFailedInvocations() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSkipFailedInvocations(boolean skip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long invocationTimeOut() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setInvocationTimeOut(long timeOut) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean ignoreMissingDependencies() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIgnoreMissingDependencies(boolean ignore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPriority(int priority) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CustomAttribute[] getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttributes(CustomAttribute[] attributes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getTimeOut() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTimeOut(long l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGroups(String[] groups) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getDependsOnGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDependsOnGroups(String[] groups) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getDependsOnMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDependsOnMethods(String[] dependsOnMethods) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		
	}

	
}
