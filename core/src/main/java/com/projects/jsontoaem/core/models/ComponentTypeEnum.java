package com.projects.jsontoaem.core.models;

/**
 * Enum that maps to the available components that this project can create.
 *
 * Created by albertsaclot on 23/03/2016.
 */
public enum ComponentTypeEnum {
    title("title"),
    text("text"),
    image("image"),
    textimage("textimage");

    private String type;
    private ComponentTypeEnum(String type) {
        this.type = type;
    }
}
