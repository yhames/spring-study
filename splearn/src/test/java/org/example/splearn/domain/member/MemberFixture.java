package org.example.splearn.domain.member;

public class MemberFixture {

    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("test@example.com");
    }

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "testuser", "12345678");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }
}
