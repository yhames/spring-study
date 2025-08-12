package org.example.splearn.adapter.integration;

import org.example.splearn.domain.shared.Email;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void consoleAssert(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();
        Email email = new Email("test@test.com");
        dummyEmailSender.send(email, "Test Subject", "Test Body");

        assertThat(out.capturedLines()[0])
                .isEqualTo("Dummy Email Sender: Sending email to " + email);
    }

}