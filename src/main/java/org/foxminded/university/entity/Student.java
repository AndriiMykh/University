package org.foxminded.university.entity;

public class Student extends Person {
    private final Group group;
    private final String studiesType;

    protected Student(PersonBuilder<? extends PersonBuilder> personPersonBuilder, Group group, String studiesType) {
        super(personPersonBuilder);
        this.group = group;
        this.studiesType = studiesType;
    }

    public Group getGroup() {
        return group;
    }

    public String getStudiesType() {
        return studiesType;
    }

    @Override
    public String toString() {
        return "Student" +
                super.toString() +
                ", group=" + group +
                ", studiesType='" + studiesType + '\'' +
                '}';
    }

    public static class StudentBuilder extends PersonBuilder<StudentBuilder>{
        private Group group;
        private String studiesType;

        public StudentBuilder() {
        }

        @Override
        public StudentBuilder self() {
            return this;
        }

        public Student build() {
            return new Student(self(), group, studiesType);
        }

        public StudentBuilder withGroup(Group group){
            this.group = group;
            return self();
        }

        public StudentBuilder withStudiesType(String studiesType){
            this.studiesType = studiesType;
            return self();
        }
    }
}
