import java.util.ArrayList;
    import java.util.Scanner;
    public class HospitalManagementSystem {
        private static ArrayList<Patient> patients = new ArrayList();
        private static String[][] beds = {
                {"B01", "B02", "B03", "B04", "B05"},
                {"B06", "B07", "B08", "B09", "B10"},
                {"B11", "B12", "B13", "B14", "B15"},
                {"B16", "B17", "B18", "B19", "B20"}
        };

        private static boolean[][] bedOccupied = new boolean[4][5];

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n--- Medicare Hospital System ---");
                System.out.println("1. Register Patient");
                System.out.println("2. Display All Patients");
                System.out.println("3. Display Ward Layout & Beds");
                System.out.println("4. Allocate Bed to Inpatient");
                System.out.println("5. Generate reports");
                System.out.println("6. Exit");
                System.out.println("Enter your choice: ");

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registerPatient(scanner);
                        break;
                    case 2:
                        displayPatients();
                        break;
                    case 3:
                        displayWardLayout();
                        break;
                    case 4:
                        allocateBed(scanner);
                        break;
                    case 5:
                        generateReports();
                        break;
                    case 6:
                        System.out.println("Existing system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again");
                }
            } while (choice != 6);

            scanner.close();
        }

        private static void registerPatient(Scanner scanner) {
            System.out.print("Enter Patient ID: ");
            String id = scanner.nextLine();

            for (Patient p : patients) {
                if (p.getPatientId().equalsIgnoreCase(id)) {
                    System.out.println("Error: A patient with this iD already exists!");
                    return;
                }
            }

            System.out.println("Enter First Name: ");
            String fName = scanner.nextLine();
            System.out.print("Enter Last Name: ");
            String lName = scanner.nextLine();
            System.out.println("Enter Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Gender: ");
            String gender = scanner.nextLine();
            System.out.print("Enter Medical Conditional: ");
            String condition = scanner.nextLine();

            System.out.println("Select Category (1: INPATIENT, 2: OUTPATIENT, 3: EMERGENCY): ");
            int catChoice = scanner.nextInt();
            scanner.nextLine();

            PatientCategory category;
            if (catChoice == 1) {
                category = PatientCategory.INPATIENT;
                System.out.print("Enter Ward Number: ");
                String ward = scanner.nextLine();
                System.out.print("Enter Bed Number (e.g., B01); ");
                String bed = scanner.nextLine();

                patients.add(new Inpatient(id, fName, lName, age, gender, condition, category, ward, bed));
            } else if (catChoice == 2) {
                category = PatientCategory.OUTPATIENT;
                patients.add(new Patient(id, fName, lName, age, gender, condition, category));
            } else {
                category = PatientCategory.EMERGENCY;
                patients.add(new Patient(id, fName, lName, age, gender, condition, category));
            }

            System.out.println("Patient registered successfully!");
        }

        private static void displayPatients() {
            if (patients.isEmpty()) {
                System.out.println("No registered patients found.");
                return;
            }
            System.out.println("\n--- Registered Patients ---");
            for (Patient p : patients) {
                p.displayDetails();
                System.out.println("----------------------------");
            }
        }

        private static void displayWardLayout() {
            System.out.println("\n--- Hospital Ward Layout (4x5) ---");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    String status = bedOccupied[i][j] ? "[OCCUPIED" : "[FREE]";
                    System.out.print(beds[i][j] + " " + status + "\t");
                }
                System.out.println();
            }
        }

        private static void allocateBed(Scanner scanner) {
            System.out.print("Enter Patient ID to allocate bed: ");
            String id = scanner.nextLine();

            Patient target = null;
            for (Patient p : patients) {
                if (p.getPatientId().equalsIgnoreCase(id)) {
                    target = p;
                    break;
                }
            }

            if (target == null) {
                System.out.println("Patient not found.");
                return;
            }

            displayWardLayout();
            System.out.print("Enter row index (0-3) and column index (0-4) to assign: ");
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            scanner.nextLine();

            if (r < 0 || r > 3 || c < 0 || c > 4) {
                System.out.println("Invalid bed position");
                return;
            }

            if (bedOccupied[r][c]) {
                System.out.println("Invalid bed position.");
                return;
            }

            if (bedOccupied[r][c]) {
                System.out.println("Error: This bed is already occupied!");
            } else {
                bedOccupied[r][c] = true;
                System.out.println("Bed " + beds[r][c] + " successfully allocated to " + target.getFirstName());
            }
        }

        private static void generateReports() {
            int totalPatients = patients.size();
            int occupiedCount = 0;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if (bedOccupied[i][j]) occupiedCount++;
                }
            }

            double occupancyPercentage = ((double) occupiedCount / 20) * 100;

            System.out.println("\n--- Ward Reports ---");
            System.out.println("Total registered Patients: " + totalPatients);
            System.out.println("Total Occupied Beds: " + occupiedCount + " / 20");
            System.out.println("Ward Occupancy percentage: " + occupancyPercentage + "%");

            }
        }