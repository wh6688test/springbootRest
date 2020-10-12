package org.tutorials.wproject1.model;

import java.util.Objects;

public class Member {
    //private String id;
    private String id;
    private short rating;

    public Member() {
       super();
    }

    public Member(String id, short rating) {
        this.id=id;
        this.rating=rating;
    }


    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id=id;
    }

    public short getRating() {
        return this.rating;
    }
    public void setRating(short rating) {
        this.rating=rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return rating == member.rating &&
                Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Member{");
        sb.append("id=").append(id);
        sb.append(", rating='").append(rating);
        sb.append('}');
        return sb.toString();
    }

}
