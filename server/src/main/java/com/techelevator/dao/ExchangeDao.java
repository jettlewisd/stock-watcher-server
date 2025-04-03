package com.techelevator.dao;

import com.techelevator.model.Exchange;

import java.util.List;

public interface ExchangeDao {

    List<Exchange> getExchanges();


    long deleteExchangeById(long exchangeId);

}
