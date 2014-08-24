import grails.test.AbstractCliTestCase

class _EventTests extends AbstractCliTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_Event() {

        execute(["event"])

        assertEquals 0, waitForProcess()
        verifyHeader()
    }
}
