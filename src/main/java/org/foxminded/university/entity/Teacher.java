package org.foxminded.university.entity;

public class Teacher extends Person {
    private final String linkedinUrl;

    protected Teacher(PersonBuilder<? extends PersonBuilder> personPersonBuilder, String linkedinUrl) {
        super(personPersonBuilder);
        this.linkedinUrl = linkedinUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    @Override
    public String toString() {
        return "Teacher" +
                super.toString() +
                "linkedinUrl='" + linkedinUrl + '\'' +
                '}';
    }

    public static class TeacherBuilder extends PersonBuilder<TeacherBuilder> {
        private String linkedinUrl;

        private TeacherBuilder() {
        }

        @Override
        public TeacherBuilder self() {
            return this;
        }

        public Teacher build() {
            return new Teacher(self(), linkedinUrl);
        }

        public TeacherBuilder withLinkedinUrl(String linkedinUrl) {
            this.linkedinUrl = linkedinUrl;
            return self();
        }
    }

    public static TeacherBuilder builder() {
        return new TeacherBuilder();
    }
}
