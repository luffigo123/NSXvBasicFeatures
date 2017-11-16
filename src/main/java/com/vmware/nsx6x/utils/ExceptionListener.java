package com.vmware.nsx6x.utils;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.vmware.nsx6x.utils.Log;

public class ExceptionListener extends TestListenerAdapter{
	
	
	@Override
	public void onTestFailure(ITestResult testResult) {
		Throwable exceptionThrowable = testResult.getThrowable();
		if (exceptionThrowable!=null) {
			Log.getInstance().handleException(exceptionThrowable);
		}
	}

	@Override
	public void onConfigurationFailure(ITestResult testResult) {
		Throwable exceptionThrowable = testResult.getThrowable();
		if (exceptionThrowable!=null) {
			Log.getInstance().handleException(exceptionThrowable);
		}
		if (testResult.getMethod().getMethodName().equals("suiteSetUp")) {
			TestBase.suiteCleanUp();
		}
	}
}
