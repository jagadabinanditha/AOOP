import java.util.*;

class Customer {

    private final CustomerId customerId;

    private final String name;

    public Customer(CustomerId customerId, String name) {

        if (customerId == null || name == null ||

            name.isBlank()) {

            throw new IllegalArgumentException(

                "Invalid customer details"

            );

        }

        this.customerId = customerId;

        this.name = name;

    }

    public CustomerId getCustomerId() {

        return customerId;

    }

    public String getName() {

        return name;

    }

}

// ==========================================

// 4. PARKING SPOT

// ==========================================
