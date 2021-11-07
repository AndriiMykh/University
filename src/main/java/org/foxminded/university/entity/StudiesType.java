package org.foxminded.university.entity;

public enum  StudiesType {
    FULL_TIME, PART_TIME ;

    public static StudiesType getType(String type){
        if(type.equalsIgnoreCase("full-time")){
            return FULL_TIME;
        }else {
            return PART_TIME;
        }
    }
}
