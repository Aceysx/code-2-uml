package com.util;

public class Constant {
    public final static String UML_TEXT_PATH = System.getProperty("user.dir")+"/uml_text.txt";

    public final static String START_TOKEN = "@startuml\n!pragma graphviz_dot jdot\n";
    public final static String DEFAULT_STYLE_TOKEN = "skinparam Rectangle{\n" +
        "\tBorderColor White\n" +
        "\tshadowing false\n" +
        "}\n";
    public final static String SPACE_TOKEN = " ";
    public final static String NEW_LINE = "\n";
    public final static String END_TOKEN = "@enduml";
    public final static String PUBLIC_MODIFIER_TOKEN = "public";
    public final static String PROTECTED_MODIFIER_TOKEN = "protected";
    public final static String PRIVATE_MODIFIER_TOKEN = "private";
    public final static String ANNOTATION_TOKEN = "annotation";
    public final static String INTERFACE_TOKEN = "interface";
    public final static String CLASS_TOKEN = "class";
    public final static String BLANK_STRING_TOKEN = "　";
    public final static String WRAP_CLASS_STYLE_TOKEN = "Rectangle";

}
