package org.tutorials.wproject1.model;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Optional;
import java.util.HashMap;
import java.util.Objects;

public class Group {

    @NotNull
    private Long gid;
    private Map<String, String> attributes;
    private Map<String, Member>members;

    public Group() {
        this.gid=1L;
        this.attributes=new HashMap<>();
        this.members=new HashMap<>();

    }
    public Group(Long gid) {
        this.gid=gid;
        this.attributes=new HashMap<>();
        this.members=new HashMap<>();
    }



    public Group(Long gid, Map<String, String> attributes) {
        this.gid=gid;
        this.attributes=new HashMap<>();
        this.attributes.putAll(attributes);
        this.members=new HashMap<>();
    }

    public Group(Long gid, Map<String, String> attributes, Member member) {
        this.gid=gid;
        this.attributes=new HashMap<>();
        this.attributes.putAll(attributes);
        this.members=new HashMap<>();
        this.members.put(member.getId(), member);
    }


    public Long getGid() {
        return this.gid;
    }

    public void setGid(Long gid) {
        this.gid=gid;
    }


    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, String> attrs) {
        this.attributes.putAll(attrs);
    }


    public Map<String, Member> getMembers() {
        return this.members;
    }

    public void setMembers(Member member)  {
        this.members.put(member.getId(), member);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return  Objects.equals(gid, group.gid) &&
                Objects.equals(attributes, group.attributes) && attributes.size() == attributes.size() &&
                Objects.equals(members, group.members) && members.size() == group.members.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(gid, attributes, members);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Group{");
        sb.append("gid=").append(gid);
        sb.append(", attributes='").append(attributes.toString());
        sb.append(", members='").append(members.toString());
        sb.append('}');
        return sb.toString();
    }

}
