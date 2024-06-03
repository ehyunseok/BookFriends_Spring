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

    @Column(name = "userID")
    private String userID;

    @Enumerated(EnumType.STRING)
    @Column(name = "itemType")
    private ItemType itemType;

    @Column(name = "itemID")
    private Integer itemID;

    // Default constructor
    public LikeyId() {
    }

    // Parameterized constructor
    public LikeyId(String userID, ItemType itemType, Integer itemID) {
        this.userID = userID;
        this.itemType = itemType;
        this.itemID = itemID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeyId likeyId = (LikeyId) o;
        return Objects.equals(userID, likeyId.userID) &&
                itemType == likeyId.itemType &&
                Objects.equals(itemID, likeyId.itemID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, itemType, itemID);
    }
}
