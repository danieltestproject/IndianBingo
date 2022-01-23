import java.util.List;

public class GameOrganizerTest {
    private static final int testNumberRange = 10;
    private static final int testPlayerNum = 5;
    private static final int testTicketRows = 2;
    private static final int testTicketCols = 6;
    private static final int testNumberPerRow = 5;
    private final GameOrganizer testClass;
    private final GameConfigData testConfigData;

    public GameOrganizerTest() {
        this.testConfigData = buildTestData();
        this.testClass = GameOrganizer.getInstance(testConfigData);
    }

    //Test
    public void testSingletonInstance() {
        GameOrganizer testData = GameOrganizer.getInstance(null);
        boolean res = testData == testClass;
        System.out.println("testSingletonInstance: expect = true, result = " + res);
    }

    //Test
    public void testCheckGameOverFalse() {
        boolean res = testClass.checkGameOver();
        System.out.println("testCheckGameOverFalse: expect = false, result = " + res);
    }

    //Test
    public void testGetFinalResult() {
        for (int i = 0; i < testNumberRange; i++) {
            testClass.callNumber();
        }

        List<String> testData = testClass.getFinalResult();

        boolean res = testData.size() == testPlayerNum;
        System.out.println("testGetFinalResult: expect = true, result = " + res);
    }

    //Test
    public void testCheckGameOverTrue() {
        boolean res = testClass.checkGameOver();
        System.out.println("testCheckGameOverFalse: expect = true, result = " + res);
    }

    private GameConfigData buildTestData() {
        GameConfigData testConfigData = new GameConfigData();
        testConfigData.setNumberPerRow(testNumberPerRow);
        testConfigData.setNumberRange(testNumberRange);
        testConfigData.setTicketCols(testTicketCols);
        testConfigData.setTicketRows(testTicketRows);
        testConfigData.setPlayerNum(testPlayerNum);

        return testConfigData;
    }
}
