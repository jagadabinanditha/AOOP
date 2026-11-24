import java.util.*;

class Waypoint {

    private final Map<CustomerId, Customer> customers =

        new HashMap<>();

    private final Map<ParkingSpotId, ParkingSpot> spots =

        new HashMap<>();

    private final Map<BookingId, Booking> bookings =

        new HashMap<>();

    private final Set<String> registeredVehicles =

        new HashSet<>();

    private int bookingCounter = 1;

    private int paymentCounter = 1;

    // --------------------------------------

    // Register customer

    // --------------------------------------

    public void registerCustomer(Customer customer) {

        if (customer == null) {

            throw new IllegalArgumentException(

                "Customer cannot be null"

            );

        }

        if (customers.containsKey(customer.getCustomerId())) {

            throw new IllegalArgumentException(

                "Customer already registered"

            );

        }

        customers.put(customer.getCustomerId(), customer);

    }

    // --------------------------------------

    // Add parking spot

    // --------------------------------------

    public void addParkingSpot(ParkingSpot spot) {

        if (spot == null) {

            throw new IllegalArgumentException(

                "Parking spot cannot be null"

            );

        }

        ParkingSpotId id = spot.getSnapshot().spotId();

        if (spots.containsKey(id)) {

            throw new IllegalArgumentException(

                "Parking spot already exists"

            );

        }

        spots.put(id, spot);

    }

    // --------------------------------------

    // Register vehicle

    // --------------------------------------

    public void registerVehicle(String vehicleNumber) {

        if (vehicleNumber == null ||

            vehicleNumber.isBlank()) {

            throw new IllegalArgumentException(

                "Invalid vehicle number"

            );

        }

        String normalized =

            vehicleNumber.trim().toUpperCase(Locale.ROOT);

        if (!registeredVehicles.add(normalized)) {

            throw new IllegalArgumentException(

                "Vehicle already registered"

            );

        }

    }

    // --------------------------------------

    // Display parking spots

    // --------------------------------------

    public List<ParkingSpotView> getParkingSpots() {

        List<ParkingSpotView> result = new ArrayList<>();

        for (ParkingSpot spot : spots.values()) {

            result.add(spot.getSnapshot());

        }

        // Return an unmodifiable copy, not internal objects.

        return List.copyOf(result);

    }

    // --------------------------------------

    // Reserve parking spot

    // --------------------------------------

    public Booking reserve(

        CustomerId customerId,

        ParkingSpotId spotId,

        String vehicleNumber,

        int hours,

        Money hourlyRate

    ) {

        if (!customers.containsKey(customerId)) {

            throw new IllegalArgumentException(

                "Customer not found"

            );

        }

        if (hours <= 0) {

            throw new IllegalArgumentException(

                "Booking hours must be greater than zero"

            );

        }

        if (hourlyRate == null) {

            throw new IllegalArgumentException(

                "Hourly rate cannot be null"

            );

        }

        String normalizedVehicle =

            vehicleNumber == null

                ? ""

                : vehicleNumber.trim().toUpperCase(Locale.ROOT);

        if (!registeredVehicles.contains(normalizedVehicle)) {

            throw new IllegalArgumentException(

                "Vehicle is not registered"

            );

        }

        ParkingSpot spot = spots.get(spotId);

        if (spot == null) {

            throw new IllegalArgumentException(

                "Parking spot not found"

            );

        }

        if (!spot.isAvailable()) {

            throw new IllegalStateException(

                "Parking spot is not available"

            );

        }

        Money totalFare = hourlyRate.times(hours);

        BookingId bookingId =

            new BookingId(bookingCounter++);

        PaymentId paymentId =

            new PaymentId(paymentCounter++);

        boolean vehicleParked = false;

        try {

            // Step 1: Reserve the parking spot.

            spot.parkVehicle(normalizedVehicle);

            vehicleParked = true;

            // Step 2: Create a PENDING booking.

            Booking booking = new Booking(

                bookingId,

                customerId,

                spotId,

                normalizedVehicle,

                totalFare

            );

            // Step 3: Simulate successful payment.

            Payment payment = new Payment(

                paymentId,

                bookingId,

                totalFare,

                true

            );

            // Step 4: Attach payment.

            // Booking changes from PENDING to CONFIRMED.

            booking.attachPayment(payment);

            // Step 5: Store the confirmed booking.

            bookings.put(bookingId, booking);

            return booking;

        } catch (RuntimeException exception) {

            // Roll back parking spot if booking fails.

            if (vehicleParked) {

                spot.releaseSpot();

            }

            throw exception;

        }

    }

    // --------------------------------------

    // Cancel booking

    // --------------------------------------

    public void cancelBooking(BookingId bookingId) {

        Booking booking = bookings.get(bookingId);

        if (booking == null) {

            throw new IllegalArgumentException(

                "Booking not found"

            );

        }

        // First validate and change booking state.

        booking.cancel();

        // Release the parking spot.

        ParkingSpot spot = spots.get(booking.getSpotId());

        spot.releaseSpot();

    }

    // --------------------------------------

    // Display all bookings

    // --------------------------------------

    public void displayBookings() {

        System.out.println("\n----- BOOKING DETAILS -----");

        for (Booking booking : bookings.values()) {

            System.out.println(

                "Booking ID: " + booking.getBookingId().value()

            );

            System.out.println(

                "Customer ID: " + booking.getCustomerId().value()

            );

            System.out.println(

                "Parking Spot ID: " + booking.getSpotId().value()

            );

            System.out.println(

                "Vehicle Number: " + booking.getVehicleNumber()

            );

            System.out.println(

                "Fare: " + booking.getFare()

            );

            System.out.println(

                "Booking Status: " + booking.getStatus()

            );

            System.out.println("---------------------------");

        }

    }

}

// ==========================================

// 10. MAIN CLASS

// ==========================================
