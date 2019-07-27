package cn.jinelei.jyhome.model;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class User {
    public static final String DEFAULT_NICKNAME = "nickname";
    public static final String DEFAULT_AVATOR = "avator";
    public static final String DEFAULT_PHONENUMBER = "phoneNumber";
    public static final Gender DEFAULT_GENDER = Gender.MALE;
    public static final double DEFAULT_HEIGHT = 180.0;
    public static final double DEFAULT_WEIGHT = 60.0;
    public static final long DEFAULT_BIRTH = Calendar.getInstance().getTimeInMillis();
    public static final String FIELD_NICKNAME = "nickname";
    public static final String FIELD_AVATOR = "avator";
    public static final String FIELD_PHONENUMBER = "phoneNumber";
    public static final String FIELD_GENDER = "gender";
    public static final String FIELD_HEIGHT = "height";
    public static final String FIELD_WEIGHT = "weight";
    public static final String FIELD_BIRTH = "birth";
    private String nickname;
    private String avator;
    private String phoneNumber;
    private Gender gender;
    private double height;
    private double weight;
    private long birth;

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

        public static Gender forObject(@NotNull Object object) {
            for (Gender gender : Gender.class.getEnumConstants()) {
                try {
                    if (gender.getName().equals(object) || Integer.parseInt(String.valueOf(object)) == gender.getValue()) {
                        return gender;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getBirth() {
        return birth;
    }

    public void setBirth(Long birth) {
        this.birth = birth;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public User() {
        this.nickname = DEFAULT_NICKNAME;
        this.avator = DEFAULT_AVATOR;
        this.phoneNumber = DEFAULT_PHONENUMBER;
        this.gender = DEFAULT_GENDER;
        this.height = DEFAULT_HEIGHT;
        this.weight = DEFAULT_WEIGHT;
        this.birth = DEFAULT_BIRTH;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", avator='" + avator + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                ", weight=" + weight +
                ", birth=" + birth +
                '}';
    }

}
