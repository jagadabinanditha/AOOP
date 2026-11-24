import java.util.*;

record PaymentId(int value) {

    public PaymentId {

        if (value <= 0) {

            throw new IllegalArgumentException("Invalid Payment ID");

        }

    }

}

// ==========================================

// 2. MONEY - IMMUTABLE VALUE OBJECT

// ==========================================
