package com.abnamro.assessment.shared.references;

public abstract class Reference {

    public abstract String getValue();

    @Override
    public final String toString() {
        return getValue();
    }

}
