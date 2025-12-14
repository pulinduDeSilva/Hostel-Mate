import java.util.Scanner;

class HostelMate {
    public static void main(String[] args) { // Main method
        Scanner input = new Scanner(System.in);
        String username;
        String password;
        String currentDate = "";
        int day;
        int month;
        int year;

        // row counts
        int[] roomCount = { 1 };
        int[] studentCount = { 1 };
        int[] allocationsCount = { 1 };
        int[] transferCount = { 1 };

        // rooms
        String[][] rooms = new String[1][6];
        rooms[0][0] = "roomId";
        rooms[0][1] = "Floor";
        rooms[0][2] = "roomNo";
        rooms[0][3] = "capacity";
        rooms[0][4] = "feePerDay";
        rooms[0][5] = "availableBeds";

        // Students
        String students[][] = new String[1][5];
        students[0][0] = "studentId";
        students[0][1] = "name";
        students[0][2] = "contact";
        students[0][3] = "email";
        students[0][4] = "status";

        // Allocations
        String allocations[][] = new String[1][5];
        allocations[0][0] = "studentId";
        allocations[0][1] = "roomId";
        allocations[0][2] = "bedIndex";
        allocations[0][3] = "checkInDate";
        allocations[0][4] = "dueDate";

        // Transfer logs
        String[] transferLogs = new String[1];

        while (true) { // Login

            System.out.println("=============   HostelMate Login   =============\n");
            System.out.print("Username : ");
            username = input.nextLine();
            System.out.print("Password : ");
            password = input.nextLine();

            if (username.equals("warden") && password.equals("1234")) {
                System.out.println("Login successful. Welcome, " + username + "!");
                pause();
                clearConsole();
                break;
            } else {
                System.out.println("Invalid credentials. Try again.");
                pause();
                clearConsole();
            }
        }

        // get current date
        while (true) {
            System.out.println("Please enter current Date ");
            System.out.print("Day : ");
            day = Integer.parseInt(input.nextLine());
            if (day > 31 || day < 1) {
                System.out.println("Date out of range ");
                continue;
            }

            System.out.print("Month : ");
            month = Integer.parseInt(input.nextLine());
            if (month > 12 || month < 1) {
                System.out.println("Month out of range ");
                continue;
            }

            System.out.print("Year : ");
            year = Integer.parseInt(input.nextLine());
            if (year < 2025) {
                System.out.println("Year out of range");
                continue;
            }

            String formattedDay = String.format("%02d", day);
            String formattedMonth = String.format("%02d", month);
            currentDate = year + "-" + formattedMonth + "-" + formattedDay;
            pause();
            clearConsole();
            break;
        }

        while (true) {
            System.out.println("\n=== HostelMate ===\n\n");
            System.out.println("1) Manage Rooms");
            System.out.println("2) Manage Students");
            System.out.println("3) Allocate Bed");
            System.out.println("4) Vacate Bed");
            System.out.println("5) Transfer");
            System.out.println("6) View Reports");
            System.out.println("7) Exit");

            System.out.print("Choose an option: ");
            String choice = input.nextLine().trim(); // trim removes any unnecessory space

            switch (choice) {
                case "1":
                    clearConsole();
                    rooms = manageRooms(rooms, allocations, input, roomCount, allocationsCount);
                    break;
                case "2":
                    clearConsole();
                    students = manageStudents(students, allocations, input, studentCount, allocationsCount);
                    break;
                case "3":
                    clearConsole();
                    allocations = allocateBed(allocations, students, rooms, input, allocationsCount, studentCount,
                            roomCount,
                            currentDate);
                    break;
                case "4":
                    clearConsole();
                    vacateBed(allocations, students, studentCount, allocationsCount, input, rooms, roomCount);
                    break;
                case "5":
                    clearConsole();
                    transferLogs = transferStudent(allocations, allocationsCount, input, rooms, roomCount, students,
                            studentCount, transferLogs, transferCount, currentDate);
                    break;
                case "6":
                    clearConsole();
                    viewReports(allocations, allocationsCount, input, rooms, roomCount, day, month, year);
                    break;
                case "7":
                    System.out.println("Exiting system... Bye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again!");
                    pause();
                    clearConsole();
            }
        }

    }

    // ----------- Manage Rooms -------------
    private static String[][] manageRooms(String[][] rooms, String[][] allocations, Scanner input, int[] roomCount,
            int[] allocationsCount) {
        while (true) { // Rooms Loop
            int choiceRooms;
            boolean exist = false;
            String roomId;
            int floor;
            int roomNo;
            int capacity;
            int beds;
            String updateRoomId;
            int updateFloor;
            int updateRoomNo;
            int updateCapacity;
            double updateFee;

            clearConsole();
            System.out.println(
                    "=========== Manage Rooms ===========\n\n1)\tAdd Room \n2)\tUpdate room \n3)\tDelete room \n4)\tSearch room \n5)\tView all rooms \n6)\tBack\n");
            System.out.print("Choice\t: ");
            choiceRooms = Integer.parseInt(input.nextLine());
            if (choiceRooms > 7 || choiceRooms < 1) {
                System.out.println("invalid choice...");
                pause();
                clearConsole();
                continue;
            }
            clearConsole();

            if (choiceRooms == 1) { // Add room
                if (roomCount[0] == rooms.length) {
                    String[][] newRooms = new String[rooms.length * 2][6];
                    for (int i = 0; i < rooms.length; i++) {
                        for (int j = 0; j < rooms[i].length; j++) {
                            newRooms[i][j] = rooms[i][j];
                        }
                    }
                    rooms = newRooms;
                }

                System.out.println("=========== Add room ===========\n");
                while (true) {
                    System.out.print("Room ID ( format : r001 ): ");
                    roomId = input.nextLine();
                    if ((roomId.charAt(0) == 'r' || roomId.charAt(0) == 'R')
                            && roomId.length() > 1) {
                        break;
                    } else {
                        System.out.println("invalid format try again");
                        continue;
                    }
                }

                for (int i = 0; i < roomCount[0]; i++) {
                    if (rooms[i][0].equals(roomId)) {
                        System.out.println("Room id already exist ");
                        exist = true;
                        break;
                    }
                }
                if (exist == true) {
                    pause();
                    clearConsole();
                    continue;
                }

                System.out.print("Floor : ");
                floor = Integer.parseInt(input.nextLine());

                System.out.print("Room No : ");
                roomNo = Integer.parseInt(input.nextLine());

                System.out.print("Capacity : ");
                capacity = Integer.parseInt(input.nextLine());
                beds = capacity;

                if (capacity < 0) {
                    System.out.println("Capacity cannot be negative!");
                    break;
                }

                System.out.print("Fee/Day(LKR) : ");
                double fee = Double.parseDouble(input.nextLine());
                if (fee < 0) {
                    System.out.println("fee cannot be negative!");
                    pause();
                    continue;
                }

                rooms[roomCount[0]][0] = roomId;
                rooms[roomCount[0]][1] = Integer.toString(floor);
                rooms[roomCount[0]][2] = Integer.toString(roomNo);
                rooms[roomCount[0]][3] = Integer.toString(capacity);
                rooms[roomCount[0]][4] = Double.toString(fee);
                rooms[roomCount[0]][5] = Integer.toString(beds);
                roomCount[0]++;
                System.out.println("Room added. Available beds : " + capacity);
                pause();
                clearConsole();

                continue;

            }

            else if (choiceRooms == 2) {
                exist = false;
                System.out.println("=========== Update room ===========\n");
                System.out.print("Room ID : ");
                updateRoomId = input.nextLine();
                int row = 0;

                for (int i = 1; i < roomCount[0]; i++) { // check for availability
                    if (rooms[i][0].equals(updateRoomId)) {
                        exist = true;
                        row = i;
                        break;
                    }
                }

                if (exist == false) {
                    System.out.println("Room do not exist");
                    pause();
                    clearConsole();
                    continue;
                }

                System.out.print("Floor : ");
                updateFloor = Integer.parseInt(input.nextLine());

                System.out.print("Room No : ");
                updateRoomNo = Integer.parseInt(input.nextLine());

                System.out.print("Capacity : ");
                updateCapacity = Integer.parseInt(input.nextLine());
                int oldCapacity = Integer.parseInt(rooms[row][3]);
                int oldAvailable = Integer.parseInt(rooms[row][5]);
                int occupied = oldCapacity - oldAvailable;

                if (updateCapacity < occupied) {
                    System.out.println("Capacity cannot be less than current beds ");
                    pause();
                    clearConsole();
                    continue;
                }

                System.out.print("Fee/Day(LKR) : ");
                updateFee = Double.parseDouble(input.nextLine());

                if (updateFee < 0) {
                    System.out.println("Fee cannot be negative!");
                    pause();
                    clearConsole();
                    continue;
                }

                rooms[row][1] = Integer.toString(updateFloor);
                rooms[row][2] = Integer.toString(updateRoomNo);
                rooms[row][3] = Integer.toString(updateCapacity);
                rooms[row][4] = Double.toString(updateFee);
                int newAvailable = updateCapacity - occupied;
                rooms[row][5] = Integer.toString(newAvailable);

                System.out.println("Updated roomID " + rooms[row][0] + "| floor " + rooms[row][1]
                        + "| roomNo " + rooms[row][2] + "| capacity " + rooms[row][3] + "| fee "
                        + rooms[row][4] + "| available " + rooms[row][5]);
                pause();
                clearConsole();
            }

            else if (choiceRooms == 3) {
                exist = false;
                System.out.println("=========== Delete room ===========\n");
                int deleteIndex = -1;
                boolean allocAcitve = false;
                System.out.print("Room ID : ");
                roomId = input.nextLine();

                for (int i = 1; i < roomCount[0]; i++) {
                    if (rooms[i][0].equals(roomId)) {
                        deleteIndex = i;
                        break;
                    }
                }

                for (int i = 1; i < allocationsCount[0]; i++) {
                    if (allocations[i][1].equals(roomId)) {
                        allocAcitve = true;
                        break;
                    }
                }
                if (deleteIndex == -1) {
                    System.out.println("Room do not exist");
                    pause();
                    clearConsole();
                    continue;
                }
                if (allocAcitve == true) {
                    System.out.println("Room room have an active allocation");
                    pause();
                    clearConsole();
                    continue;
                }

                for (int i = deleteIndex; i < roomCount[0] - 1; i++) {
                    rooms[i] = rooms[i + 1];
                }

                for (int j = 0; j < rooms[0].length; j++) { // clear last row
                    rooms[roomCount[0] - 1][j] = null;
                }

                roomCount[0]--; // reduce count
                System.out.println("Deleted successfully.");
                pause();
                clearConsole();

            }

            else if (choiceRooms == 4) {
                System.out.println("=========== Search Rooms ===========\n\n");
                System.out.print("Room ID : ");
                roomId = input.nextLine();
                System.out.println();

                for (int i = 0; i < roomCount[0]; i++) {
                    if (i == 0) {
                        System.out.printf("%-10s %-15s %-15s %-25s %-10s%n",
                                rooms[i][0],
                                rooms[i][1],
                                rooms[i][2],
                                rooms[i][3],
                                rooms[i][4]);
                        System.out.println(
                                "----------------------------------------------------------------------------------");
                    } else if (rooms[i][0].equals(roomId)) {
                        System.out.printf("%-10s %-15s %-15s %-25s %-10s%n",
                                rooms[i][0],
                                rooms[i][1],
                                rooms[i][2],
                                rooms[i][3],
                                rooms[i][4]);
                    }
                }
                System.out.print("\n\nPress Enter to continue : ");
                input.nextLine();
                clearConsole();
            }

            else if (choiceRooms == 5) {
                System.out.println("=========== View all Rooms ===========\n\n");
                for (int i = 1; i < roomCount[0] - 1; i++) {
                    int maxIndex = i;

                    // Find the room with maximum available beds in remaining unsorted portion
                    for (int j = i + 1; j < roomCount[0]; j++) {
                        int availableMax = Integer.parseInt(rooms[maxIndex][5]);
                        int availableCurrent = Integer.parseInt(rooms[j][5]);

                        if (availableCurrent > availableMax) {
                            maxIndex = j;
                        }
                    }

                    // Swap rooms[i] with rooms[maxIndex]
                    if (maxIndex != i) {
                        String[] temp = rooms[i];
                        rooms[i] = rooms[maxIndex];
                        rooms[maxIndex] = temp;
                    }
                }
                for (int i = 0; i < roomCount[0]; i++) {
                    System.out.printf("%-10s %-15s %-15s %-15s %-10s %-10s%n",
                            rooms[i][0],
                            rooms[i][1],
                            rooms[i][2],
                            rooms[i][3],
                            rooms[i][4],
                            rooms[i][5]);
                    if (i == 0) {
                        System.out.println(
                                "---------------------------------------------------------------------------------");
                    }
                }

                System.out.println("\n(Sorted by Available Beds - Descending)");
                System.out.print("\n\nPress Enter to continue : ");
                input.nextLine();
                clearConsole();
            }

            else if (choiceRooms == 6) { // back
                clearConsole();
                break;
            }
        }
        return rooms;
    }

    // ----------- Manage Students --------------
    private static String[][] manageStudents(String[][] students, String[][] allocations, Scanner input,
            int[] studentCount,
            int[] allocationsCount) {
        int choiceStudents;
        boolean stExist = false;
        String studentId;
        String name;
        String email;
        int contact;
        String status;
        String updateStId;
        int updateContact;
        String updateEmail;

        while (true) {
            System.out.println(
                    "=========== Manage Students ===========\n\n1)\tAdd Student \n2)\tUpdate Student \n3)\tDelete Student \n4)\tSearch Student \n5)\tView all Students \n6)\tBack\n");
            System.out.print("Choice\t: ");
            choiceStudents = Integer.parseInt(input.nextLine());
            if (choiceStudents > 6 || choiceStudents < 1) {
                System.out.println("invalid choice...");
                pause();
                clearConsole();
                continue;
            }
            clearConsole();

            if (choiceStudents == 1) { // Add student
                if (studentCount[0] == students.length) {
                    String[][] newStudents = new String[students.length * 2][5];
                    for (int i = 0; i < students.length; i++) {
                        for (int j = 0; j < students[i].length; j++) {
                            newStudents[i][j] = students[i][j];
                        }
                    }
                    students = newStudents;
                }

                System.out.println("=========== Add Student ===========\n");

                while (true) {
                    System.out.print("Student ID ( format : S001 ): ");
                    studentId = input.nextLine();
                    if ((studentId.charAt(0) == 's' || studentId.charAt(0) == 'S') && studentId.length() > 1) {
                        break;
                    } else {
                        System.out.println("invalid format try again");
                        continue;
                    }
                }

                if (studentCount[0] > 1) {
                    for (int i = 0; i < studentCount[0]; i++) {
                        if (students[i][0].equals(studentId)) {
                            System.out.println("StudentId already exist ");
                            stExist = true;
                            break;
                        }
                    }
                }

                if (stExist == true) {
                    pause();
                    clearConsole();
                    continue;
                }

                System.out.print("Name : ");
                name = input.nextLine();

                while (true) {
                    System.out.print("Contact No : ");
                    contact = Integer.parseInt(input.nextLine());
                    String tempContact = "0" + Integer.toString(contact);
                    if (tempContact.length() == 10) {
                        if (tempContact.charAt(0) == '0') {
                            break;
                        } else {
                            System.out.println("Invalid contact. Try again");
                            continue;
                        }
                    } else {
                        System.out.println("Invalid contact. Try again");
                        continue;
                    }
                }

                while (true) {
                    System.out.print("Email : ");
                    email = input.nextLine();

                    int at = 0, dot = 0;
                    boolean valid = false;

                    if (email.length() > 2) {
                        for (int i = 0; i < email.length(); i++) {
                            if (email.charAt(i) == '@') {
                                at++;
                            }
                            if (email.charAt(i) == '.') {
                                dot++;
                            }
                        }

                        if (at == 1 && dot == 1) {
                            valid = true;
                        }
                    }

                    if (valid) {
                        break;
                    } else {
                        System.out.println("Invalid email address. Try again.");
                    }
                }

                while (true) {
                    System.out.print("Status : ");
                    status = input.nextLine();

                    if (status.toLowerCase().equals("active")) {
                        break;
                    } else {
                        System.out.println("invalid status input...");
                    }
                }

                students[studentCount[0]][0] = studentId;
                students[studentCount[0]][1] = name;
                students[studentCount[0]][2] = Integer.toString(contact);
                students[studentCount[0]][3] = email;
                students[studentCount[0]][4] = status;

                studentCount[0]++;
                System.out.println("Student added.");
                pause();
                clearConsole();

                continue;
            }

            else if (choiceStudents == 2) {
                stExist = false;
                int row = 0;
                System.out.println("=========== Update Student ===========\n");
                System.out.print("Student ID to update : ");
                updateStId = input.nextLine();

                for (int i = 1; i < studentCount[0]; i++) { // check for availability
                    if (students[i][0].equals(updateStId)) {
                        stExist = true;
                        row = i;
                    }
                }

                if (stExist == false) {
                    System.out.println("Student do not exist");
                    pause();
                    clearConsole();
                    continue;
                }

                while (true) { // ;contact validation
                    System.out.print("Contact No : ");
                    updateContact = Integer.parseInt(input.nextLine());
                    String tempContact = "0" + Integer.toString(updateContact);
                    if (tempContact.length() == 10) {
                        if (tempContact.charAt(0) == '0') {
                            break;
                        } else {
                            System.out.println("Invalid contact. Try again");
                            continue;
                        }
                    } else {
                        System.out.println("Invalid contact. Try again");
                        continue;
                    }
                }

                while (true) { // email validation
                    System.out.print("Email : ");
                    updateEmail = input.nextLine();

                    int at = 0, dot = 0;
                    boolean valid = false;

                    if (updateEmail.length() > 2) {
                        for (int i = 0; i < updateEmail.length(); i++) {
                            if (updateEmail.charAt(i) == '@') {
                                at++;
                            }
                            if (updateEmail.charAt(i) == '.') {
                                dot++;
                            }
                        }

                        if (at == 1 && dot == 1) {
                            valid = true;
                        }
                    }

                    if (valid) {
                        break;
                    } else {
                        System.out.println("Invalid email address. Try again.");
                    }
                }

                students[row][2] = Integer.toString(updateContact);
                students[row][3] = updateEmail;

                System.out.println("StudentID " + students[row][0] + "| name " + students[row][1]
                        + "| Contact " + students[row][2] + "| Email " + students[row][3] + "| Status "
                        + students[row][4]);
                pause();
                clearConsole();
            }

            else if (choiceStudents == 3) {
                stExist = false;
                System.out.println("=========== Delete Student ===========\n");
                int deleteIndex = -1;
                boolean activeAlloc = false;
                String dltStudentId;

                System.out.print("Student ID : ");
                dltStudentId = input.nextLine();
                for (int i = 1; i < studentCount[0]; i++) {
                    if (students[i][0].equals(dltStudentId)) {
                        deleteIndex = i;
                        break;
                    }
                }

                for (int i = 1; i < allocationsCount[0]; i++) {
                    if (allocations[i][0].equals(dltStudentId)) {
                        activeAlloc = true;
                        break;
                    }
                }

                if (deleteIndex == -1) {
                    System.out.println("Student do not exist");
                    pause();
                    clearConsole();
                    continue;
                }
                if (activeAlloc) {
                    System.out.println("Student have an acitve allocation");
                    pause();
                    clearConsole();
                    continue;
                }

                for (int i = deleteIndex; i < studentCount[0] - 1; i++) {
                    students[i] = students[i + 1];
                }

                for (int j = 0; j < students[0].length; j++) { // clear last row
                    students[studentCount[0] - 1][j] = null;
                }

                studentCount[0]--; // reduce count of rows
                System.out.println("Student deleted successfully.");
                pause();
                clearConsole();
            }

            else if (choiceStudents == 4) {
                System.out.println("=========== Search Student ===========\n\n");
                System.out.print("Student ID : ");
                studentId = input.nextLine();
                System.out.println();

                for (int i = 0; i < studentCount[0]; i++) {
                    if (i == 0) {
                        System.out.printf("%-10s %-15s %-15s %-25s %-10s%n",
                                students[i][0],
                                students[i][1],
                                students[i][2],
                                students[i][3],
                                students[i][4]);
                        System.out.println(
                                "----------------------------------------------------------------------------------");
                    } else if (students[i][0].equals(studentId)) {
                        System.out.printf("%-10s %-15s %-15s %-25s %-10s%n",
                                students[i][0],
                                students[i][1],
                                students[i][2],
                                students[i][3],
                                students[i][4]);
                    }
                }
                System.out.print("\n\nPress Enter to continue : ");
                input.nextLine();
                clearConsole();

            }

            else if (choiceStudents == 5) {
                System.out.println("=========== View all Students ===========\n\n");

                for (int i = 0; i < studentCount[0]; i++) {
                    if (i == 0) {
                        System.out.printf("%-10s %-15s %-15s %-25s %-10s%n",
                                students[i][0],
                                students[i][1],
                                students[i][2],
                                students[i][3],
                                students[i][4]);
                        System.out.println(
                                "----------------------------------------------------------------------------------");
                    } else {
                        System.out.printf("%-10s %-15s %-15s %-25s %-10s%n",
                                students[i][0],
                                students[i][1],
                                students[i][2],
                                students[i][3],
                                students[i][4]);
                    }
                }
                System.out.print("\n\nPress Enter to continue : ");
                input.nextLine();
                clearConsole();
            } else if (choiceStudents == 6) { // back
                clearConsole();
                break;
            }
        }
        return students;

    }

    // ----------- Allocate Beds --------------
    private static String[][] allocateBed(String[][] allocations, String[][] students, String[][] rooms, Scanner input,
            int[] allocationsCount, int[] studentCount, int[] roomCount, String currentDate) {
        String allocateStudentId;
        String allocateRoomId;
        String allocateDueDate;
        int allocateDay;
        int allocateMonth;
        int allocateYear;

        System.out.println("=========== Allocate bed ===========\n\n");
        System.out.print("Student ID : ");
        allocateStudentId = input.nextLine();

        boolean found = false;
        boolean active = false;
        boolean allocated = false;

        while (true) {
            if (allocationsCount[0] == allocations.length) {
                String[][] newAllocate = new String[allocations.length * 2][5];
                for (int i = 0; i < allocations.length; i++) {
                    for (int j = 0; j < allocations[i].length; j++) {
                        newAllocate[i][j] = allocations[i][j];
                    }
                }
                allocations = newAllocate;
            }

            for (int i = 1; i < studentCount[0]; i++) {
                if (students[i][0].equals(allocateStudentId)) {
                    found = true;
                    if (students[i][4].equalsIgnoreCase("active")) {
                        active = true;
                    }
                    break;
                }
            }

            for (int i = 1; i < allocationsCount[0]; i++) {
                if (allocations[i][0].equals(allocateStudentId)) {
                    allocated = true;
                    break;
                }
            }

            if (allocated || !active || !found) {
                System.out.println("Invalid studentId");
                pause();
                clearConsole();
                break;
            }

            found = false;
            boolean available = false;
            System.out.print("room Id : ");
            allocateRoomId = input.nextLine();
            int roomCapacity = 0;
            int availableBeds = 0;
            int bedIndex = 0;

            for (int i = 1; i < roomCount[0]; i++) { // Get available beds
                if (rooms[i][0].equals(allocateRoomId)) {
                    availableBeds = Integer.parseInt(rooms[i][5]);
                    roomCapacity = Integer.parseInt(rooms[i][3]);
                    break;
                }
            }

            // calculate lowest bedIndex
            bedIndex = roomCapacity - availableBeds;

            for (int i = 1; i < roomCount[0]; i++) {
                if (rooms[i][0].equals(allocateRoomId)) {
                    found = true;
                    if (Integer.parseInt(rooms[i][5]) > 0) {
                        available = true;
                    }
                    break;
                }
            }

            if (found == false) {
                System.out.println("Room do not exist");
                pause();
                clearConsole();
                continue;
            }
            if (available == false) {
                System.out.println("Beds are not available");
                pause();
                clearConsole();
                continue;
            }

            System.out.println("Due Date ");
            System.out.print("Day : ");
            allocateDay = Integer.parseInt(input.nextLine());
            if (allocateDay > 31 || allocateDay < 1) {
                System.out.println("Date out of range ");
                continue;
            }

            System.out.print("Month : ");
            allocateMonth = Integer.parseInt(input.nextLine());
            if (allocateMonth > 12 || allocateMonth < 1) {
                System.out.println("Month out of range ");
                continue;
            }

            System.out.print("Year : ");
            allocateYear = Integer.parseInt(input.nextLine());
            if (allocateYear < 2025) {
                System.out.println("Year out of range");
                continue;
            }

            String dueFormatDay = String.format("%02d", allocateDay);
            String dueFormatMonth = String.format("%02d", allocateMonth);

            allocateDueDate = allocateYear + "-" + dueFormatMonth + "-" + dueFormatDay;

            allocations[allocationsCount[0]][0] = allocateStudentId;
            allocations[allocationsCount[0]][1] = allocateRoomId;
            allocations[allocationsCount[0]][2] = Integer.toString(bedIndex);
            allocations[allocationsCount[0]][3] = currentDate;
            allocations[allocationsCount[0]][4] = allocateDueDate;
            allocationsCount[0]++;

            for (int i = 1; i < roomCount[0]; i++) { // update available beds
                if (rooms[i][0].equals(allocateRoomId)) {
                    int availBeds = Integer.parseInt(rooms[i][5]);
                    rooms[i][5] = String.valueOf(availBeds - 1);
                    break;
                }
            }

            System.out.println(
                    "Allocated : " + allocateStudentId + " -> Room" + allocateRoomId + " Bed " + bedIndex);
            pause();
            clearConsole();
            break;
        }
        return allocations;

    }

    // ----------- Vacate Beds --------------
    private static void vacateBed(String[][] allocations, String[][] students, int[] studentCount,
            int[] allocationsCount, Scanner input, String[][] rooms,
            int[] roomCount) {

        while (true) {
            System.out.println("=========== Vacate Bed ===========\n\n");

            System.out.print("Student ID : ( -  to back ) : ");
            String vacateStId = input.nextLine();
            boolean studentExist = false;

            if (vacateStId.equals("-")) {
                clearConsole();
                break;
            }

            for (int i = 1; i < allocationsCount[0]; i++) {
                if (allocations[i][0].equals(vacateStId)) {
                    studentExist = true;
                    break;
                }
            }

            if (!studentExist) {
                System.out.println("No matching allocation found for that Student!");
                pause();
                clearConsole();
                break;
            }

            System.out.print("Room ID : ");
            String vacateRoomId = input.nextLine();

            if (vacateRoomId.equals("-")) {
                clearConsole();
                break;
            }

            int allocIndex = -1;
            int roomIndex = -1;
            double fee = 0;
            int beds = 0;

            // Find allocation
            for (int i = 1; i < allocationsCount[0]; i++) {
                if (allocations[i][1].equals(vacateRoomId)) {
                    allocIndex = i;
                    break;
                }
            }

            // Find room
            for (int i = 1; i < roomCount[0]; i++) {
                if (rooms[i][0].equals(vacateRoomId)) {
                    roomIndex = i;
                    fee = Double.parseDouble(rooms[i][4]);
                    beds = Integer.parseInt(rooms[i][5]);
                    break;
                }
            }

            // Validation
            if (allocIndex == -1 || roomIndex == -1) {
                System.out.println("No matching allocation found for that room!");
                pause();
                clearConsole();
                continue;
            }

            // Free bed
            rooms[roomIndex][5] = Integer.toString(beds + 1);

            // Extract dates from String
            int curYear = Integer.parseInt(allocations[allocIndex][3].substring(0, 4));
            int curMonth = Integer.parseInt(allocations[allocIndex][3].substring(5, 7));
            int curDay = Integer.parseInt(allocations[allocIndex][3].substring(8, 10));

            int dueYear = Integer.parseInt(allocations[allocIndex][4].substring(0, 4));
            int dueMonth = Integer.parseInt(allocations[allocIndex][4].substring(5, 7));
            int dueDay = Integer.parseInt(allocations[allocIndex][4].substring(8, 10));

            // Convert to total days
            int totalCurDays = curYear * 372 + curMonth * 31 + curDay;
            int totalDueDays = dueYear * 372 + dueMonth * 31 + dueDay;
            int overdueDays = totalCurDays - totalDueDays;

            // Remove allocation
            for (int i = allocIndex; i < allocationsCount[0] - 1; i++) {
                allocations[i] = allocations[i + 1];
            }
            for (int j = 0; j < allocations[0].length; j++) {
                allocations[allocationsCount[0] - 1][j] = null;
            }
            allocationsCount[0]--;

            if (overdueDays > 0) {
                System.out.println("Overdue days: " + overdueDays + " | Fee/Day: " + fee + " | Fine: "
                        + (overdueDays * fee));
            } else {
                System.out.println("No overdue. Fee/Day: " + fee);
            }
            System.out.println(
                    "Checkout completed. Bed freed. Available beds (" + vacateRoomId + "): " + (beds + 1));

            pause();
            clearConsole();
        }

    }

    // ----------- Transfer Students --------------
    private static String[] transferStudent(String[][] allocations, int[] allocationsCount, Scanner input,
            String[][] rooms,
            int[] roomCount, String[][] students, int[] studentCount, String[] transferLogs, int[] transferCount,
            String currentDate) {

        String transStId;
        String transFromRoomId;
        String transToRoomId;
        boolean studentFound = false;
        boolean hasAllocation = false;
        boolean fromRoomFound = false;
        boolean toRoomFound = false;
        boolean toRoomAvailable = false;

        if (transferCount[0] == transferLogs.length) {
            String[] newTransfer = new String[transferLogs.length * 2];
            for (int i = 0; i < transferLogs.length; i++) {
                newTransfer[i] = transferLogs[i];
            }
            transferLogs = newTransfer;
        }

        while (true) {
            System.out.println("=========== Transfers ===========\n\n");
            System.out.print("Student ID : ");
            transStId = input.nextLine();

            for (int i = 1; i < studentCount[0]; i++) {
                if (students[i][0].equals(transStId)) {
                    studentFound = true;
                }
            }
            for (int i = 1; i < allocationsCount[0]; i++) { // Check for allocations
                if (allocations[i][0].equals(transStId)) {
                    hasAllocation = true;
                    break;
                }
            }

            if (!studentFound) {
                System.out.println("Student do not exist");
                pause();
                clearConsole();
                break;
            }

            if (!hasAllocation) {
                System.out.println("Student do not have any allocations");
                pause();
                clearConsole();
                break;
            }

            System.out.print("From room : ");
            transFromRoomId = input.nextLine();

            for (int i = 1; i < roomCount[0]; i++) {
                if (rooms[i][0].equals(transFromRoomId)) {
                    fromRoomFound = true;
                    break;
                }
            }

            if (!fromRoomFound) {
                System.out.println("From Room do not exist");
                pause();
                clearConsole();
                break;
            }

            boolean correctFromRoom = false;

            for (int i = 1; i < allocationsCount[0]; i++) {
                if (allocations[i][0].equals(transStId) && allocations[i][1].equals(transFromRoomId)) {
                    correctFromRoom = true;
                    break;
                }
            }

            if (!correctFromRoom) {
                System.out.println("Student is not allocated to that room!");
                pause();
                clearConsole();
                break;
            }

            System.out.print("To room : ");
            transToRoomId = input.nextLine();

            for (int i = 1; i < roomCount[0]; i++) {
                if (rooms[i][0].equals(transToRoomId)) {
                    toRoomFound = true;
                    if (Integer.parseInt(rooms[i][5]) > 0) {
                        toRoomAvailable = true;
                    }
                    break;
                }
            }

            if (!toRoomFound) {
                System.out.println("To Room do not exist");
                pause();
                clearConsole();
                break;
            }

            if (!toRoomAvailable) {
                System.out.println("To Room do not have any free beds ");
                pause();
                clearConsole();
                break;
            }

            int transRoomCapacity = 0;
            int transAvailableBeds = 0;
            int transBedIndex = 0;

            for (int i = 1; i < roomCount[0]; i++) { // Get available beds
                if (rooms[i][0].equals(transToRoomId)) {
                    transAvailableBeds = Integer.parseInt(rooms[i][5]);
                    transRoomCapacity = Integer.parseInt(rooms[i][3]);
                    break;
                }
            }

            // calculate lowest bedIndex
            transBedIndex = transRoomCapacity - transAvailableBeds;

            for (int i = 1; i < allocationsCount[0]; i++) { // update allocations
                if (allocations[i][0].equals(transStId)) {
                    allocations[i][1] = transToRoomId;
                    allocations[i][2] = Integer.toString(transBedIndex);
                }
            }

            for (int i = 1; i < roomCount[0]; i++) { // update bed availability
                if (rooms[i][0].equals(transToRoomId)) {
                    rooms[i][5] = Integer.toString(Integer.parseInt(rooms[i][5]) - 1);
                }
                if (rooms[i][0].equals(transFromRoomId)) {
                    rooms[i][5] = Integer.toString(Integer.parseInt(rooms[i][5]) + 1);
                }
            }
            transferLogs[transferCount[0]] = currentDate;
            System.out.println("Student transferred successfully!");

            pause();
            clearConsole();
            break;
        }
        return transferLogs;
    }

    // ----------- View Reports --------------
    private static void viewReports(String[][] allocations, int[] allocationsCount, Scanner input, String[][] rooms,
            int[] roomCount, int day, int month, int year) {
        while (true) {
            int choiceReports;
            System.out.println(
                    "=========== View Reports ===========\n\n1)\tOccupancy Map\n2)\tVacant Beds by Floor\n3)\tStudents per Room\n4)\tOverdue Dues\n5)\tRevenue Projection\n6)\tBack\n");
            System.out.print("Choice\t: ");
            choiceReports = Integer.parseInt(input.nextLine());
            if (choiceReports > 7 || choiceReports < 1) {
                System.out.println("invalid choice...");
                pause();
                clearConsole();
                continue;
            }
            clearConsole();

            if (choiceReports == 1) { // Occupancy Map
                System.out.println("=========== Occupancy Map ===========\n");
                System.out.println("RoomID\t\tBeds (StudentID or EMPTY)");
                System.out.println("-----------------------------------------------------------");

                for (int i = 1; i < roomCount[0]; i++) {
                    String roomId = rooms[i][0];
                    int capacity = Integer.parseInt(rooms[i][3]);

                    System.out.print(roomId + "\t\t[");

                    // Create array to track which beds are occupied
                    String[] bedOccupancy = new String[capacity];
                    for (int b = 0; b < capacity; b++) {
                        bedOccupancy[b] = "EMPTY";
                    }

                    // Fill in allocated students
                    for (int j = 1; j < allocationsCount[0]; j++) {
                        if (allocations[j][1].equals(roomId)) {
                            int bedIdx = Integer.parseInt(allocations[j][2]);
                            if (bedIdx < capacity) {
                                bedOccupancy[bedIdx] = allocations[j][0];
                            }
                        }
                    }

                    // Print bed occupancy
                    for (int b = 0; b < capacity; b++) {
                        System.out.print(bedOccupancy[b]);
                        if (b < capacity - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println("]");
                }

                System.out.print("\n\nPress Enter to continue : ");
                input.nextLine();
                clearConsole();
            }

            else if (choiceReports == 2) { // Vacant Beds by Floor
                System.out.println("=========== Vacant Beds by Floor ===========\n");
                System.out.printf("%-10s %-15s %-15s %-15s %-15s%n",
                        "Floor", "TotalRooms", "TotalBeds", "Occupied", "Vacant");
                System.out.println("-----------------------------------------------------------------------");

                // Find unique floors and calculate stats
                // First, determine max floor number
                int maxFloor = 0;
                for (int i = 1; i < roomCount[0]; i++) {
                    int floor = Integer.parseInt(rooms[i][1]);
                    if (floor > maxFloor) {
                        maxFloor = floor;
                    }
                }

                // Process each floor
                for (int floor = 1; floor <= maxFloor; floor++) {
                    int totalRooms = 0;
                    int totalBeds = 0;
                    int vacant = 0;

                    for (int i = 1; i < roomCount[0]; i++) {
                        if (Integer.parseInt(rooms[i][1]) == floor) {
                            totalRooms++;
                            int capacity = Integer.parseInt(rooms[i][3]);
                            int available = Integer.parseInt(rooms[i][5]);
                            totalBeds += capacity;
                            vacant += available;
                        }
                    }

                    if (totalRooms > 0) {
                        int occupied = totalBeds - vacant;
                        System.out.printf("%-10d %-15d %-15d %-15d %-15d%n",
                                floor, totalRooms, totalBeds, occupied, vacant);
                    }
                }

                System.out.print("\n\nPress Enter to continue : ");
                input.nextLine();
                clearConsole();
            }

            else if (choiceReports == 3) { // Students per Room
                System.out.println("=========== Students per Room ===========\n");
                System.out.printf("%-15s %-10s %-30s%n", "Room", "Count", "Students");
                System.out.println("---------------------------------------------------------------");

                for (int i = 1; i < roomCount[0]; i++) {
                    String roomId = rooms[i][0];
                    int count = 0;
                    String studentList = "";

                    // Count students in this room
                    for (int j = 1; j < allocationsCount[0]; j++) {
                        if (allocations[j][1].equals(roomId)) {
                            if (count > 0) {
                                studentList += ", ";
                            }
                            studentList += allocations[j][0];
                            count++;
                        }
                    }

                    if (count == 0) {
                        studentList = "None";
                    }

                    System.out.printf("%-15s %-10d %-30s%n", roomId, count, studentList);
                }

                System.out.print("\n\nPress Enter to continue : ");
                input.nextLine();
                clearConsole();
            }

            else if (choiceReports == 4) { // Overdue Dues
                System.out.println("=========== Overdue Dues ===========\n");
                System.out.printf("%-15s %-15s %-15s %-15s %-15s%n",
                        "Student", "Room", "DaysOverdue", "Fee/Day", "EstimatedFine");
                System.out.println(
                        "---------------------------------------------------------------------------------");

                boolean hasOverdue = false;

                for (int i = 1; i < allocationsCount[0]; i++) {
                    String studentId = allocations[i][0];
                    String roomId = allocations[i][1];

                    // Extract due date
                    int dueYear = Integer.parseInt(allocations[i][4].substring(0, 4));
                    int dueMonth = Integer.parseInt(allocations[i][4].substring(5, 7));
                    int dueDay = Integer.parseInt(allocations[i][4].substring(8, 10));

                    // Convert current date and due date to total days
                    int totalCurDays = year * 372 + month * 31 + day;
                    int totalDueDays = dueYear * 372 + dueMonth * 31 + dueDay;
                    int overdueDays = totalCurDays - totalDueDays;

                    if (overdueDays > 0) {
                        hasOverdue = true;

                        // Find fee for this room
                        double feePerDay = 0;
                        for (int j = 1; j < roomCount[0]; j++) {
                            if (rooms[j][0].equals(roomId)) {
                                feePerDay = Double.parseDouble(rooms[j][4]);
                                break;
                            }
                        }

                        double fine = overdueDays * feePerDay;
                        System.out.printf("%-15s %-15s %-15d %-15.2f %-15.2f%n",
                                studentId, roomId, overdueDays, feePerDay, fine);
                    }
                }

                if (!hasOverdue) {
                    System.out.println("No overdue students.");
                }

                System.out.print("\n\nPress Enter to continue : ");
                input.nextLine();
                clearConsole();
            }

            else if (choiceReports == 5) { // Revenue Projection
                System.out.println("=========== Revenue Projection (Daily) ===========\n");

                double totalRevenue = 0;
                int totalOccupied = 0;

                // Calculate revenue from all occupied beds
                for (int i = 1; i < allocationsCount[0]; i++) {
                    String roomId = allocations[i][1];

                    // Find fee for this room
                    for (int j = 1; j < roomCount[0]; j++) {
                        if (rooms[j][0].equals(roomId)) {
                            double feePerDay = Double.parseDouble(rooms[j][4]);
                            totalRevenue += feePerDay;
                            totalOccupied++;
                            break;
                        }
                    }
                }

                System.out.println("Total Occupied Beds: " + totalOccupied);
                System.out.printf("Daily Revenue Projection: %.2f LKR%n", totalRevenue);

                System.out.print("\n\nPress Enter to continue : ");
                input.nextLine();
                clearConsole();
            }

            else if (choiceReports == 6) { // Back
                clearConsole();
                break;
            }
        }
    }

    // --------- Additional Methods ----------------

    private static void pause() { // Method to pause program for 1ms
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void clearConsole() { // Method to clear the Console
        final String os = System.getProperty("os.name");
        try {
            if (os.contains("Linux"))
                System.out.print("\033\143");
            else if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
