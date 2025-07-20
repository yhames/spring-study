package org.example.splearn.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import static java.util.Objects.requireNonNull;

@Getter
@ToString
public class Member {

    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    private Member() {
        // Default constructor for JPA
    }

    public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        Email email = new Email(createRequest.email());
        member.email = requireNonNull(email);
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = passwordEncoder.encode(createRequest.password());
        member.status = MemberStatus.PENDING;
        return member;
    }

    public void activate() {
        Assert.state(status == MemberStatus.PENDING, "Member is not in PENDING status");

        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        Assert.state(status == MemberStatus.ACTIVE, "Member is not in ACTIVE status");

        this.status = MemberStatus.DEACTIVATED;
    }

    public boolean verifyPassword(String secret, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(secret, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        Assert.hasText(nickname, "Nickname must not be empty");
        Assert.state(status == MemberStatus.ACTIVE, "Member must be ACTIVE to change nickname");

        this.nickname = requireNonNull(nickname);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        Assert.hasText(password, "New password must not be empty");
        Assert.state(status == MemberStatus.ACTIVE, "Member must be ACTIVE to change password");

        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
