package com.daney.bookfriends.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
public class LikeyId implements Serializable {
    /* jpa 엔티티 클래스에서 복합키를 정의하기 위해 사용되는 클래스!
     * jpa에서 두 개 이상의 컬럼을 기본키로 사용해야할 때  @IdClass나 @EmbeddedId를 사용하여 정의한다.
     *  */

    private String memberID;
    private ItemType itemType;
    private Integer itemID;

    public LikeyId() {}

    public LikeyId(String memberID, ItemType itemType, Integer itemID) {
        this.memberID = memberID;
        this.itemType = itemType;
        this.itemID = itemID;
    }

    // equals implementation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeyId likeyId = (LikeyId) o;
        return Objects.equals(memberID, likeyId.memberID) &&
                itemType == likeyId.itemType &&
                Objects.equals(itemID, likeyId.itemID);
    }

    // 해시코드
    @Override
    public int hashCode() {
        return Objects.hash(memberID, itemType, itemID);
    }

}
