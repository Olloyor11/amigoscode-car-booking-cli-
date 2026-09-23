import com.ollayor.booking.CarBooking;
import com.ollayor.booking.CarBookingDAO;
import com.ollayor.booking.CarBookingService;
import com.ollayor.car.Car;
import com.ollayor.car.CarDAO;
import com.ollayor.car.CarService;
import com.ollayor.user.User;
import com.ollayor.user.UserDAO;
import com.ollayor.user.UserService;

void main() {

    UserDAO userDAO = new UserDAO();
    CarDAO carDAO = new CarDAO();
    CarBookingDAO carBookingDAO = new CarBookingDAO();
    UserService userService = new UserService(userDAO);
    CarService carService = new CarService(carDAO);
    CarBookingService carBookingService = new CarBookingService(carBookingDAO, userService, carService);


    System.out.println();
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.println(
                " \n   1 - Book Car\n" +
                        "    2 - Delete Booking\n" +
                        "    3 - View All User Booked Cars\n" +
                        "    4 - View All Bookings\n" +
                        "    5 - View Available Cars\n" +
                        "    6 - View Available Electric Cars\n" +
                        "    7 - View All Users\n" +
                        "    8 - Exit");

        System.out.println("\nChoose one number from menu!");

        int nextInt = scanner.nextInt();
        scanner.nextLine();
        if (nextInt == 1) {
            System.out.println("\nEnter your Id!");
            UUID userid;
            try {
                userid = UUID.fromString(scanner.nextLine());

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input");
                continue;
            }
            try {
                User user = userService.findUserById(userid);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no user with id: " + userid);
                continue;
            }

            Car[] allAvailableCars = carService.getAllAvailableCars();

            System.out.println("\nAll available cars:\n");
            for (int i = 0; i < allAvailableCars.length; i++) {
                System.out.println(i + 1 + " - " + allAvailableCars[i]);
            }
//            for (Car allAvailableCar : allAvailableCars) {
//                System.out.println(allAvailableCar);
//
//            }

            System.out.println("\nPlease enter your selected car id number!");
            UUID carid;
            try {
                carid = UUID.fromString(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input");
                continue;
            }
            Car car;
            try {
                car = carService.getCarById(carid);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input");
                continue;
            }


            System.out.println("\nPlease enter a date from when you want to book this car: \n" + car);
            System.out.println("Please enter date in this format: yyyy-mm-dd \n");
            LocalDate startDate;
            try {
                startDate = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Date should be in good format as it is shown!");
                continue;
            }
            System.out.println("\nPlease enter a date till when you want to book the car");
            System.out.println("Please enter date in this format: yyyy-mm-dd ");
            LocalDate endDate;
            try {
                endDate = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Date should be in good format as it is shown!");
                continue;
            }

            CarBooking carBooking = carBookingService.bookCar(userid, carid, startDate, endDate, LocalDateTime.now());

        }
        if (nextInt == 2) {
            System.out.println("Please enter booking Id to delete it:");
            UUID bookingId;
            try {
                bookingId = UUID.fromString(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input");
                continue;
            }
            try {
                carBookingDAO.deleteBooking(bookingId);

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Input");
                continue;
            }
            System.out.println("Your booking has been deleted!\n");
        }
        if (nextInt == 3) {
            System.out.println("Please enter your Id to see all your bookings!");
            UUID userId;
            try {
                userId = UUID.fromString(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input");
                continue;
            }
            CarBooking[] userBookings = carBookingService.getUserBookings(userId);
            System.out.println("\nAll cars booked by you:\n");
            if (userBookings.length == 0) {
                System.out.println("No Active Booking Found");
            } else {
                for (int i = 0; i < userBookings.length; i++) {
                    System.out.println(i + 1 + " - " + userBookings[i]);
                }
            }
        }
        if (nextInt == 4) {
            System.out.println("\nAll bookings:\n");
            CarBooking[] allBookings = carBookingService.getAllBookings();
            if (allBookings.length == 0){
                System.out.println("There is no any Active bookings available!");
            }
            for (int i = 0; i < allBookings.length; i++) {
                System.out.println(i + 1 + " - " + allBookings[i]);
            }

        }
        if (nextInt == 5) {
            System.out.println("\nAll Available Cars:\n");
            Car[] allAvailableCars = carService.getAllAvailableCars();
            for (int i = 0; i < allAvailableCars.length; i++) {
                System.out.println(i + 1 + " - " + allAvailableCars[i]);
            }

        }
        if (nextInt == 6) {
            System.out.println("\nAll Available Electric Cars:\n");
            Car[] allAvailableElectricCars = carService.getAllAvailableElectricCars();
            for (int i = 0; i < allAvailableElectricCars.length; i++) {
                System.out.println(i + 1 + " - " + allAvailableElectricCars[i]);
            }

        }
        if (nextInt == 7) {
            System.out.println("All Users");
            User[] allUsers = userService.getAllUsers();
            for (int i = 0; i < allUsers.length; i++) {
                System.out.println(i + 1 + " - " + allUsers[i]);
            }

        }
        if (nextInt == 8) {
            break;
        }


    }
}
