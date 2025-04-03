package com.techelevator.model;

public class Exchange {

    private final long exchangeId;
    private final String name;


    public Exchange(long exchangeId, String name) {
        this.exchangeId = exchangeId;
        this.name = name;
    }

    public long getExchangeId() {
        return exchangeId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "exchangeId=" + exchangeId +
                ", name='" + name + '\'' +
                '}';
    }
}
