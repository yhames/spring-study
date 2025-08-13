package org.example.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileTest {

    @Test
    void profile() {
        Profile test = new Profile("test");
        assertThat(test).isNotNull();
    }

    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile(""))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        var profile = new Profile("test");

        assertThat(profile.url()).isEqualTo("@test");
    }

}