package com.example.lgh.simpleinputmethod.model;

public class Symbol {
    public static final String SYMBOL_OFTEN = "常用";
    public static final String SYMBOL_CH = "中文";
    public static final String SYMBOL_EN = "英文";

    String tag;
    String value;
    int priority;
    public Symbol(String tag, String value, int priority){
        this.tag = tag;
        this.value = value;
        this.priority = priority;
    }

    @Override
    public String toString(){
        return tag + " " + value + " " + priority;
    }

    public String getValue() {
        return value;
    }
}
