public class TestStarter {
    public static void main(String[] args) {
        testGameOrganizer();
        testDealer();
    }

    private static void testDealer() {
        DealerTest dealerTest = new DealerTest();
        dealerTest.testNextNum();
        dealerTest.testNoMoreNumbers();
    }

    private static void testGameOrganizer() {
        GameOrganizerTest organizer = new GameOrganizerTest();
        organizer.testSingletonInstance();
        organizer.testCheckGameOverFalse();
        organizer.testGetFinalResult();
        organizer.testCheckGameOverTrue();
    }
}
