package com.techelevator.dao;

import com.techelevator.model.Symbol;

import java.util.List;

public interface SymbolDao {

    Symbol getSymbolById(long symbolId);

    List<Symbol> getSymbols();

    List<Symbol> getSymbolsByExchangeId(long exchangeId);

    Symbol createSymbol(Symbol newSymbol);

    Symbol updateSymbol(Symbol updatedSymbol);

    long deleteSymbolById(long symbolId);
}
