package org.sadtech.vkbot;

public enum SourceMessage {
    VK("VK"),
    FB("FB");

    private final String value;

    private SourceMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
