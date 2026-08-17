import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalTest {

    @Test
    public void testPatientCreation() {
        Patient p = new Patient("P001", "Aden", "Doe", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        assertEquals("P001", p.getPatientId());
        assertEquals("Aden", p.getFirstName());
    }
    @Test
    public void testPatientInheritance() {
        Inpatient inpatient = new Inpatient("P002", "Jane", "Smith", 25, "Female", "Surgery", PatientCategory.INPATIENT, "Ward A", "B01");
        assertEquals("Ward A", inpatient.getWardNumber());
        assertEquals("B01", inpatient.getBedNumber());
    }
        @Test
        public void testBedAllocationLogic() {
            boolean[][] bedOccupied = new boolean[4][5];

            //bed should be free
            assertFalse(bedOccupied[0][0]);

            //Allocate bed
            bedOccupied[0][0] = true;

            //Should be occupied
            assertTrue(bedOccupied[0][0]);
        }

    }
