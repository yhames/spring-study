package org.example.splearn.domain.member;

import java.util.regex.Pattern;

public record Profile(String address) {

    private static final Pattern PROFILE_ADDRESS_PATTERN = Pattern.compile("^[a-z0-9]+");

    public Profile {
        if (address == null) {
            throw new IllegalArgumentException("프로필 주소는 비어 있을 수 없습니다.");
        }
        if (!address.isEmpty() && !PROFILE_ADDRESS_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("프로필 주소 형식이 올바르지 않습니다: " + address);
        }

        if (address.length() > 15) {
            throw new IllegalArgumentException("프로필 주소는 최대 155자 이하이어야 합니다: ");
        }
    }

    public String url() {
        return "@" + address;
    }
}
