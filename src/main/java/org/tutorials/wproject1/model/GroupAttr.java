package org.tutorials.wproject1.model;
import java.util.*;

public class GroupAttr {
    private Map<String, String> attributes;

    public GroupAttr() {
        this.attributes=new HashMap<>();
    }

    public GroupAttr(Map attributes) {
        this.attributes=new HashMap<>();
        this.attributes.putAll(attributes);
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, String>attrs) {
        this.attributes.putAll(attrs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupAttr attrs = (GroupAttr) o;
        return Objects.equals(this.attributes, attrs.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Group attributes{");
        sb.append("attributes keys =").append(attributes.keySet());
        sb.append("attributes values =").append(attributes.values());
        sb.append('}');
        return sb.toString();
    }


}
