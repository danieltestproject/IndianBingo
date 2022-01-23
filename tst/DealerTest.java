public class DealerTest {
    private final static int testNumRange = 10;
    Dealer testClass;
    public DealerTest() {
        testClass = new Dealer(testNumRange);
    }

    //Test
    public void testNextNum() {
        boolean res = true;
        for (int i = 0; i < testNumRange; i++) {
            if (testClass.nextNum() == 0) {
                res = false;
            }
        }

        System.out.println("testGetFinalResult: expect = true, result = " + res);

    }

    //Test
    public void testNoMoreNumbers() {
        //all numbers are exhausted in previous test.
        boolean res = testClass.noMoreNumbers();

        System.out.println("testGetFinalResult: expect = true, result = " + res);
    }
}
