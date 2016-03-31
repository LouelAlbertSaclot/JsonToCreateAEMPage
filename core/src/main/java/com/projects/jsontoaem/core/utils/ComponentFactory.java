package com.projects.jsontoaem.core.utils;

import com.projects.jsontoaem.core.models.*;

/**
 * A very simple factory class that will provide the matching model component.
 *
 * Created by albertsaclot on 30/03/2016.
 */
public class ComponentFactory {

    private ComponentFactory factory;
    private ComponentFactory() {
        factory = new ComponentFactory();
    }
    public  ComponentFactory getInstance() {
        return factory;
    }

    /**
     * Creates and provides the mapped model component being required.
     *
     * @param type The component model to create
     * @return The component model
     */
    public static IComponent createComponent(ComponentTypeEnum type) {
        switch (type) {
            case title :
                return new TitleComponent();
            case text:
                return new TextComponent();
            case image:
                return new ImageComponent();
            case textimage:
                return new TextImageComponent();
            default: return null;
        }
    }
}
