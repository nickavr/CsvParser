import java.util.List;
import java.util.Map;

import org.config.IsoFieldValidator;
import org.config.JurisdictionNode;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class IsoFieldValidatorTest {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static final String NEWT = "NEWT";
    public static final String MODI = "MODI";
    public static final String CORR = "CORR";
    public static final String REVI = "REVI";
    public static final String TERM = "TERM";
    public static final String EROR = "EROR";
    public static final String VALU = "VALU";

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private IsoFieldValidator isoFieldValidator;
    private IsoFieldValidator isoFieldValidator2;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    public void performanceTestCsvMapInitialization() {
        final long startTime = System.currentTimeMillis();
        isoFieldValidator = IsoFieldValidator.getInstance();
        Map<String, List<JurisdictionNode>> map = isoFieldValidator.getIsoFieldMap();
        final long endTime = System.currentTimeMillis();

        System.out.println("Total time needed to init csv config map is: " + (endTime - startTime));
        assertNotNull(map);
    }

    @Test
    public void performanceTestCsvMapRetrieval() {
        isoFieldValidator = IsoFieldValidator.getInstance();
        assertNotNull(isoFieldValidator.getIsoFieldMap());
        final long startTime = System.currentTimeMillis();
        Boolean b0 = isoFieldValidator.retrieveFlag("Reporting timestamp", "EUEMIR", NEWT);
        Boolean b1 = isoFieldValidator.retrieveFlag("Reporting timestamp", "EUEMIR", TERM);
        Boolean b2 = isoFieldValidator.retrieveFlag("Reporting timestamp", "EUEMIR", EROR);

        Boolean b3 = isoFieldValidator.retrieveFlag("Reporting timestamp", "JFSA", MODI);
        Boolean b4 = isoFieldValidator.retrieveFlag("Reporting timestamp", "JFSA", TERM);
        Boolean b5 = isoFieldValidator.retrieveFlag("Reporting timestamp", "JFSA", EROR);

        Boolean b6 = isoFieldValidator.retrieveFlag("Reporting timestamp", "UKEMIR", CORR);
        Boolean b7 = isoFieldValidator.retrieveFlag("Reporting timestamp", "UKEMIR", TERM);
        Boolean b8 = isoFieldValidator.retrieveFlag("Reporting timestamp", "UKEMIR", EROR);

        Boolean b9 = isoFieldValidator.retrieveFlag("Clearing threshold of counterparty 1", "EUEMIR", REVI);
        Boolean b10 = isoFieldValidator.retrieveFlag("Clearing threshold of counterparty 1", "EUEMIR", TERM);
        Boolean b11 = isoFieldValidator.retrieveFlag("Clearing threshold of counterparty 1", "EUEMIR", EROR);

        Boolean b12 = isoFieldValidator.retrieveFlag("Clearing threshold of counterparty 1", "EUEMIR", NEWT);
        Boolean b13 = isoFieldValidator.retrieveFlag("Clearing threshold of counterparty 1", "EUEMIR", TERM);
        Boolean b14 = isoFieldValidator.retrieveFlag("Clearing threshold of counterparty 1", "EUEMIR", EROR);

        Boolean b15 = isoFieldValidator.retrieveFlag("Clearing threshold of counterparty 1", "EUEMIR", MODI);
        Boolean b16 = isoFieldValidator.retrieveFlag("Clearing threshold of counterparty 1", "EUEMIR", TERM);
        Boolean b17 = isoFieldValidator.retrieveFlag("Clearing threshold of counterparty 1", "EUEMIR", EROR);

        final long endTime = System.currentTimeMillis();
        System.out.println("Total time needed to retrieve data for 18 fields is: " + (endTime - startTime));
    }

    @Test
    public void givenTwoReferences_whenCompared_thenTheSameObjectIsContained() {
        isoFieldValidator = IsoFieldValidator.getInstance();
        isoFieldValidator2 = IsoFieldValidator.getInstance();
        assertNotNull(isoFieldValidator);
        assertNotNull(isoFieldValidator2);
        assertSame(isoFieldValidator, isoFieldValidator2);
    }

    @Test
    public void givenCorrectFlagMap_whenRetrieveValue_thenCorrectResultIsReturned() {
        isoFieldValidator = IsoFieldValidator.getInstance();
        Boolean b0 = isoFieldValidator.retrieveFlag("Reporting timestamp", "EUEMIR", CORR);
        Boolean b1 = isoFieldValidator.retrieveFlag("Reporting timestamp", "EUEMIR", TERM);

        assertTrue(b0);
        assertTrue(b1);
    }

    @Test
    public void givenCorrectFlagMap_whenRetrieveWrongFieldLabel_thenReturnNull() {
        isoFieldValidator = IsoFieldValidator.getInstance();
        Boolean flag = isoFieldValidator.retrieveFlag("Dummy label", "EUEMIR", REVI);
        assertNull(flag);
    }

    @Test
    public void givenCorrectFlagMap_whenRetrieveWrongJurisdiction_thenReturnNull() {
        isoFieldValidator = IsoFieldValidator.getInstance();
        Boolean flag = isoFieldValidator.retrieveFlag("Reporting timestamp", "Dummy jurisdiction", NEWT);
        assertNull(flag);
    }

    @Test
    public void givenCorrectFlagMap_whenRetrieveWrongFlag_thenReturnNull() {
        isoFieldValidator = IsoFieldValidator.getInstance();
        Boolean flag = isoFieldValidator.retrieveFlag("Reporting timestamp", "EUEMIR", null);
        assertNull(flag);
    }
}
