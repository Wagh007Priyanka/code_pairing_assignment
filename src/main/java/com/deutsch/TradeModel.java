package com.deutsch;
 import java.text.*;
 import java.util.*;


public class TradeModel {

    private String tradeId;
    private int version;
    private String counterPartyId;
    private String bookId;
    private Date maturityDate;
    private Date createdDate;
    private char expired;

    public TradeModel(java.lang.String tradeId, int version, java.lang.String counterPartyId, java.lang.String bookId, java.util.Date maturityDate, java.util.Date createdDate, char expired) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;
    }

    public java.lang.String getTradeId() {
        return tradeId;
    }

    public void setTradeId(java.lang.String tradeId) {
        this.tradeId = tradeId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public java.lang.String getCounterPartyId() {
        return counterPartyId;
    }

    public void setCounterPartyId(java.lang.String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public java.lang.String getBookId() {
        return bookId;
    }

    public void setBookId(java.lang.String bookId) {
        this.bookId = bookId;
    }

    public java.util.Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(java.util.Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public java.util.Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public char getExpired() {
        return expired;
    }

    public void setExpired(char expired) {
        this.expired = expired;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "TradeModel{" +
                "tradeId=" + tradeId +
                ", version=" + version +
                ", counterPartyId=" + counterPartyId +
                ", bookId=" + bookId +
                ", maturityDate=" + maturityDate +
                ", createdDate=" + createdDate +
                ", expired=" + expired +
                '}';
    }
}