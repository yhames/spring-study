package com.example.core.member;

public class Member {
    private Long id;
    private String name;
    private Grade grade;

    public Member() {
    }

    public Member(Long id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

    static public class builder {
        private Long id;
        private String name;
        private Grade grade;

        public builder() {
        }

        public builder id(Long id) {
            this.id = id;
            return this;
        }

        public builder name(String name) {
            this.name = name;
            return this;
        }

        public builder grade(Grade grade) {
            this.grade = grade;
            return this;
        }

        public Member build() {
            return new Member(id, name, grade);
        }
    }
}