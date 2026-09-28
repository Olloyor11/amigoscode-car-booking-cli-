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
                " \n 1 - Book Car\n" +
                        "    2 - Delete Booking\n" +
                        "    3 - View All User Booked Cars\n" +
                        "    4 - View All Bookings\n" +
                        "    5 - View Available Cars\n" +
                        "    6 - View Available Electric Cars\n" +
                        "    7 - View All Users\n" +
                        "    8 - Exit");

        System.out.println("\nChoose one number from menu!");

        int nextInt;
        try {
            nextInt = Integer.parseInt(scanner.next());
            scanner.nextLine();

        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            System.out.println("Try again!👇");
            continue;
        }

        if (nextInt == 1) {
            UUID userid;
            while (true) {
                System.out.println("\nEnter your Id!");
                try {
                    userid = UUID.fromString(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input! ");
                    System.out.println("Try again!👇");
                    continue;
                }
                try {
                    userService.findUserById(userid);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("There is no user with id: " + userid);
                }
            }


            Car[] allAvailableCars = carService.getAllAvailableCars();

            System.out.println("\nAll available cars:\n");
            for (int i = 0; i < allAvailableCars.length; i++) {
                System.out.println(i + 1 + " - " + allAvailableCars[i]);
            }


            UUID carid;
            Car car = null;
            while (true) {
                System.out.println("\nPlease enter your selected car id number!");
                try {
                    carid = UUID.fromString(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input");
                    System.out.println("Try again!👇");
                    continue;
                }
                try {
                    car = carService.getCarById(carid);
                    boolean bookedAlready = false;
                    for (CarBooking booking : carBookingService.getAllBookings()) {
                        if (booking != null &&
                                bookedAlready == false &&
                                booking.getCar().getRegNumber().equalsIgnoreCase(car.getRegNumber())) {
                            bookedAlready = true;
                            break;
                        }
                    }
                    if (bookedAlready) {
                        System.out.println("You can not book this car!");
                        System.out.println("This car already booked!");
                        System.out.println("Choose another car!");
                        continue;

                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input");
                    System.out.println("Try again!👇");

                }

            }

            LocalDate startDate;
            while (true) {
                System.out.println("\nPlease enter a date from when you want to book this car: \n" + car);
                System.out.println("Please enter date in this format: yyyy-mm-dd \n");
                try {
                    startDate = LocalDate.parse(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Wrong format Try again!👇");
                    continue;
                }
            }
            LocalDate endDate;
            while (true) {
                System.out.println("\nPlease enter a date till when you want to book the car");
                System.out.println("Please enter date: yyyy-mm-dd ");
                try {
                    endDate = LocalDate.parse(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Wrong format Try again!👇!");
                    continue;
                }
            }



            CarBooking carBooking = carBookingService.bookCar(userid, carid, startDate, endDate, LocalDateTime.now());

            System.out.println("You have booked " + carBooking.getCar().getBrand() + " with registration number - " + carBooking.getCar().getRegNumber() + " successfully!");
            System.out.println("Your total booking cost is: " + carBooking.getPriceOfBooking());

            UUID id = carBooking.getId();
            System.out.println("Your booking id is: " + id);

        }
            if (nextInt == 2) {
                UUID bookingId;
                while (true) {
                    System.out.println("Please enter booking Id to delete it:");
                    try {
                        bookingId = UUID.fromString(scanner.nextLine());
                        carBookingService.cancelBooking(bookingId);
                        System.out.println("Your booking has been deleted!\n");
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid input");
                        System.out.println("Try again!👇");
                        continue;
                    }
                }
            }
            if (nextInt == 3) {
                UUID userId;
                while (true) {
                    System.out.println("Please enter your Id to see all your bookings!");
                    try {
                        userId = UUID.fromString(scanner.nextLine());
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid input");
                        System.out.println("Try again!👇");
                        continue;
                    }
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
                if (allBookings.length == 0) {
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