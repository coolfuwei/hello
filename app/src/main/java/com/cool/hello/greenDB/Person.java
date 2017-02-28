package com.cool.hello.greenDB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.chinaso.greendaodemo.DaoSession;
import com.chinaso.greendaodemo.CardDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.chinaso.greendaodemo.PersonDao;

/**
 * Created by cool on 2017/2/15.
 */
@Entity
public class Person {
    @Id(autoincrement = true)
    private Long id;
    private long cardId;
    @ToOne(joinProperty = "cardId")
    private Card card;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 778611619)
    private transient PersonDao myDao;
    @Generated(hash = 800029270)
    public Person(Long id, long cardId) {
        this.id = id;
        this.cardId = cardId;
    }
    @Generated(hash = 1024547259)
    public Person() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getCardId() {
        return this.cardId;
    }
    public void setCardId(long cardId) {
        this.cardId = cardId;
    }
    @Generated(hash = 10293163)
    private transient Long card__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 476964834)
    public Card getCard() {
        long __key = this.cardId;
        if (card__resolvedKey == null || !card__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CardDao targetDao = daoSession.getCardDao();
            Card cardNew = targetDao.load(__key);
            synchronized (this) {
                card = cardNew;
                card__resolvedKey = __key;
            }
        }
        return card;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1676389215)
    public void setCard(@NotNull Card card) {
        if (card == null) {
            throw new DaoException(
                    "To-one property 'cardId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.card = card;
            cardId = card.getId();
            card__resolvedKey = cardId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2056799268)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPersonDao() : null;
    }
}
