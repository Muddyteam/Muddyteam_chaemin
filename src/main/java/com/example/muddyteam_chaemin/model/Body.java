package com.example.muddyteam_chaemin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {
    @JacksonXmlProperty(localName = "items")
    private Items items;

    // Getters and Setters
    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
