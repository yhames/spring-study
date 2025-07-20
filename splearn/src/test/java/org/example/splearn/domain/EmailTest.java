package org.example.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @Test
    void equality() {
        Email email1 = new Email("test@test.com");
        Email email2 = new Email("test@test.com");
        assertThat(email1).isEqualTo(email2);
    }

}