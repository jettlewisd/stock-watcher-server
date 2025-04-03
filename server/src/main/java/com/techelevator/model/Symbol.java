package com.techelevator.model;

public class Symbol {

    private long symbolId;
    private long exchangeId;
    private String name;
    private String tickerSymbol;

    public Symbol() {
    }

    public void setSymbolId(long symbolId) {
        this.symbolId = symbolId;
    }

    public void setExchangeId(long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    private boolean priority;

    public Symbol(long symbolId, long exchangeId, String name, String tickerSymbol, boolean priority) {
            this.symbolId = symbolId;
            this.exchangeId = exchangeId;
            this.name = name;
            this.tickerSymbol = tickerSymbol;
            this.priority = priority;
    }

    public long getSymbolId() {
        return symbolId;
    }

    public long getExchangeId() {
        return exchangeId;
    }

    public String getName() {
        return name;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public boolean isPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "symbolId=" + symbolId +
                ", exchangeId=" + exchangeId +
                ", name='" + name + '\'' +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                ", priority=" + priority +
                '}';
    }
}
