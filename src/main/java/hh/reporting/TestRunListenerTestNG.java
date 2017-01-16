package hh.reporting;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import ru.yandex.qatools.allure.annotations.Attachment;
import org.testng.*;

import java.io.File;


public class TestRunListenerTestNG implements ITestListener, ISuiteListener, IInvokedMethodListener {

    /**
     * This belongs to IInvokedMethodListener and will execute before every method including @Before @After @Test
     *
     * @param iInvokedMethod
     * @param iTestResult
     */
    @Override
    public void beforeInvocation( IInvokedMethod iInvokedMethod, ITestResult iTestResult ) {
        String textMsg = "About to begin executing following method : " + returnMethodName( iInvokedMethod.getTestMethod() );
        Reporter.log(textMsg, true);
    }


    /**
     * This belongs to IInvokedMethodListener and will execute after every method including @Before @After @Test
     *
     * @param iInvokedMethod
     * @param iTestResult
     */
    @Override
    public void afterInvocation( IInvokedMethod iInvokedMethod, ITestResult iTestResult ) {
        String textMsg = "Completed executing following method : " + returnMethodName( iInvokedMethod.getTestMethod() );
        Reporter.log( textMsg, true );
    }


    /**
     * This belongs to ISuiteListener and will execute before the Suite start.
     *
     * @param iSuite
     */
    @Override
    public void onStart( ISuite iSuite ) {
        Reporter.log( "About to begin executing Suite " + iSuite.getName(), true );
    }


    /**
     * This belongs to ISuiteListener and will execute, once the Suite is finished.
     *
     * @param iSuite
     */
    @Override
    public void onFinish( ISuite iSuite ) {
        Reporter.log( "About to end executing Suite " + iSuite.getName(), true );
    }


    /**
     * This belongs to ITestListener and will execute before the main test start (@Test).
     *
     * @param iTestResult
     */
    @Override
    public void onTestStart( ITestResult iTestResult ) {
        System.out.println("The execution of the main test starts now");
    }


    /**
     * This belongs to ITestListener and will execute only when the test is pass.
     *
     * @param iTestResult
     */
    @Override
    public void onTestSuccess( ITestResult iTestResult ) {
        printTestResults(iTestResult);
    }


    /**
     * This belongs to ITestListener and will execute only on the event of fail test.
     *
     * @param iTestResult
     */
    @Override
    public void onTestFailure( ITestResult iTestResult ) {
        printTestResults( iTestResult );
        makeScreenshot();
    }


    /**
     * This belongs to ITestListener and will execute only if any of the main test(@Test) get skipped.
     *
     * @param iTestResult
     */
    @Override
    public void onTestSkipped( ITestResult iTestResult ) {
        printTestResults(iTestResult);
    }

    /**
     * No any actions for this.
     *
     * @param iTestResult
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage( ITestResult iTestResult ) {
    }


    /**
     * This belongs to ITestListener and will execute before starting of Test set/batch.
     *
     * @param iTestContext
     */
    @Override
    public void onStart( ITestContext iTestContext ) {
        Reporter.log( "About to begin executing Test " + iTestContext.getName(), true );
    }


    /**
     * This belongs to ITestListener and will execute, once the Test set/batch is finished.
     *
     * @param iTestContext
     */
    @Override
    public void onFinish( ITestContext iTestContext ) {
        Reporter.log( "Completed executing test " + iTestContext.getName(), true );

    }


    /**
     * This will return method names to the calling function
     *
     * @param testMethod - Test method.
     * @return
     */
    private String returnMethodName( ITestNGMethod testMethod ) {
        return testMethod.getRealClass().getSimpleName() + "." + testMethod.getMethodName();
    }


    /**
     * This is the method which will be executed in case of test pass or fail
     * This will provide the information on the test
     *
     * @param result
     */
    private void printTestResults( ITestResult result ) {

        Reporter.log( "Test Method resides in " + result.getTestClass().getName(), true );

        if (result.getParameters().length != 0) {
            String params = null;
            for (Object parameter : result.getParameters()) {
                params += parameter.toString() + ",";
            }

            Reporter.log( "Test Method had the following parameters : " + params, true );
        }

        String status = null;

        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                status = "Pass";
                break;

            case ITestResult.FAILURE:
                status = "Failed";
                break;

            case ITestResult.SKIP:
                status = "Skipped";
                break;
        }

        Reporter.log( "Test Status: " + status, true );

    }

    /**
     * Attaches screenshot to the Allure report.
     *
     * @return Byte array of the made screenshot.
     * @throws Exception
     */
    @Attachment( value = "Screenshot Attachment", type = "image/png" )
    public byte[] makeScreenshot() {
        File screenshot = Screenshots.takeScreenShotAsFile();

        /* Unable to take a screenshot when browser is not started. */
        if (screenshot == null)
            return (new byte[]{});

        try {
            return Files.toByteArray( screenshot );
        } catch (Exception e) {
            System.out.println( "[CUSTOM FAILURE]: Attempt to transform a screenshot into byte[] for Yandex.Allure report." );
            e.printStackTrace();
            return (new byte[]{});
        }
    }

}
