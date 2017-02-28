package com.cool.hello.greenDB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cool on 2017/2/15.
 */

@Entity
public class Card {
    @Id
    private Long id;
    private String cardId;
    private String cardName;
    @Generated(hash = 1747399964)
    public Card(Long id, String cardId, String cardName) {
        this.id = id;
        this.cardId = cardId;
        this.cardName = cardName;
    }
    @Generated(hash = 52700939)
    public Card() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCardId() {
        return this.cardId;
    }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public String getCardName() {
        return this.cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
