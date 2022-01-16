package com.abnamro.assessment.shared.references;

import java.util.Comparator;
import java.util.Objects;

public abstract class Reference implements Comparable<Reference> {

    public abstract String getValue();

    @Override
    public final String toString() {
        return getValue();
    }

    @Override
    public int compareTo(Reference other) {
        return Comparator.comparing(obj -> obj.getClass().getName())
                         .thenComparing(Object::toString)
                         .compare(this, other);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Reference other = (Reference) obj;
        return this.getValue().equals(other.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValue());
    }
}
