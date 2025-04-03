package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DataEntry {

    private long dataEntryId;
    private long symbolId;
    private LocalDate tradingDate;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;

    public DataEntry(long dataEntryId, long symbolId, LocalDate tradingDate, BigDecimal openPrice, BigDecimal closePrice,
    BigDecimal highPrice, BigDecimal lowPrice) {
        this.dataEntryId = dataEntryId;
        this.symbolId = symbolId;
        this.tradingDate = tradingDate;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    public void setDataEntryId(long dataEntryId) {
        this.dataEntryId = dataEntryId;
    }

    public DataEntry() {
    }

    public void setSymbolId(long symbolId) {
        this.symbolId = symbolId;
    }

    public void setTradingDate(LocalDate tradingDate) {
        this.tradingDate = tradingDate;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public long getDataEntryId() {
        return dataEntryId;
    }

    public long getSymbolId() {
        return symbolId;
    }

    public LocalDate getTradingDate() {
        return tradingDate;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    @Override
    public String toString() {
        return "DataEntry{" +
                "dataEntryId=" + dataEntryId +
                ", symbolId=" + symbolId +
                ", tradingDate=" + tradingDate +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                '}';
    }
}
