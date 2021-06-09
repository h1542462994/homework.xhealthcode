package test.mock;

import ext.annotation.Primary;
import ext.annotation.Rename;

public class MockBean {
    private boolean value;

    @Primary
    @Rename(name = "text2")
    private String text;

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
