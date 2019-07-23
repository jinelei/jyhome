package cn.jinelei.jyhome.model;

public class User {
    private String nickname;
    private String avator;
    private String phoneNumber;
    private Gender gender;

    public static enum Gender {
        MALE("male", 0), FEMALE("female", 1);
        private String name;
        private int value;

        Gender(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", avator='" + avator + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                '}';
    }
}
