package org.example.splearn;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class SplearnApplicationTest {

    @Test
    void staticVerify() {
        try (MockedStatic<SplearnApplication> mock = Mockito.mockStatic(SplearnApplication.class)) {
            SplearnApplication.main(new String[0]);
            mock.verify(() -> SplearnApplication.main(new String[0]));
        }
    }

}