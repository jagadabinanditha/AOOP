import java.util.*;

class Booking {

    private final BookingId bookingId;

    private final CustomerId customerId;

    private final ParkingSpotId spotId;

    private final String vehicleNumber;

    private final Money fare;

    private BookingStatus status;

    private Payment payment;

    public Booking(

        BookingId bookingId,

        CustomerId customerId,

        ParkingSpotId spotId,

        String vehicleNumber,

        Money fare

    ) {

        if (bookingId == null ||

            customerId == null ||

            spotId == null ||

            vehicleNumber == null ||

            vehicleNumber.isBlank() ||

            fare == null) {

            throw new IllegalArgumentException(

                "Invalid booking details"

            );

        }

        this.bookingId = bookingId;

        this.customerId = customerId;

        this.spotId = spotId;

        this.vehicleNumber = vehicleNumber;

        this.fare = fare;

        // A new booking always starts as PENDING.

        this.status = BookingStatus.PENDING;

        this.payment = null;

    }

    // Payment must be successful before confirmation.

    public void attachPayment(Payment payment) {

        if (status != BookingStatus.PENDING) {

            throw new IllegalStateException(

                "Payment can only be attached to a pending booking"

            );

        }

        if (payment == null || !payment.successful()) {

            throw new IllegalArgumentException(

                "Payment was unsuccessful"

            );

        }

        if (!payment.bookingId().equals(bookingId)) {

            throw new IllegalArgumentException(

                "Payment belongs to another booking"

            );

        }

        if (!payment.amount().equals(fare)) {

            throw new IllegalArgumentException(

                "Payment amount does not match booking fare"

            );

        }

        this.payment = payment;

        // State transition: PENDING -> CONFIRMED

        this.status = BookingStatus.CONFIRMED;

    }

    // Only confirmed bookings can be cancelled.

    public void cancel() {

        if (status != BookingStatus.CONFIRMED) {

            throw new IllegalStateException(

                "Only confirmed bookings can be cancelled"

            );

        }

        // State transition: CONFIRMED -> CANCELLED

        this.status = BookingStatus.CANCELLED;

    }

    public BookingId getBookingId() {

        return bookingId;

    }

    public CustomerId getCustomerId() {

        return customerId;

    }

    public ParkingSpotId getSpotId() {

        return spotId;

    }

    public String getVehicleNumber() {

        return vehicleNumber;

    }

    public Money getFare() {

        return fare;

    }

    public BookingStatus getStatus() {

        return status;

    }

    public Payment getPayment() {

        return payment;

    }

}

// ==========================================

// 9. WAYPOINT APPLICATION

// ==========================================
