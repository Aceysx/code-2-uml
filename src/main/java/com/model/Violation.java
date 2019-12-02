package com.model;

import java.util.Objects;

public class Violation {
    private String rule;
    private String className;
    private String code;
    private String start;
    private String end;

    public static Violation build(String className, String code, String start, String end, String rule) {
        Violation violation = new Violation();
        violation.setCode(code);
        violation.setRule(rule);
        violation.setClassName(className);
        violation.setStart(start);
        violation.setEnd(end);
        return violation;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "\n" + "rule: " + getRule() + "\n"+
                "class:" + getClassName() + ".java[" + getStart() + "-" + getEnd() + "]\n" + getCode();
    }

    @Override
    public int hashCode() {
        return 31 + ((code == null) ? 0 : code.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (obj instanceof Violation) {
            Violation knowledgePoint = (Violation) obj;
            return Objects.equals(knowledgePoint.className, this.className) &&
                    Objects.equals(knowledgePoint.start, this.start) &&
                    Objects.equals(knowledgePoint.end, this.end);
        }

        return false;
    }

    private String getCode() {
        return code;
    }


    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
