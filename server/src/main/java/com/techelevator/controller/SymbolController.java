package com.techelevator.controller;

import com.techelevator.dao.DataEntryDao;
import com.techelevator.dao.ExchangeDao;
import com.techelevator.dao.SymbolDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.DataEntry;
import com.techelevator.model.Symbol;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
// @PreAuthorize("isAuthenticated()")
@RequestMapping("/symbols")
public class SymbolController {

    private final SymbolDao symbolDao;
    private final ExchangeDao exchangeDao;

    private final DataEntryDao dataEntryDao;

    public SymbolController(SymbolDao symbolDao, ExchangeDao exchangeDao, DataEntryDao dataEntryDao) {
        this.symbolDao = symbolDao;
        this.exchangeDao = exchangeDao;
        this.dataEntryDao = dataEntryDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Symbol> symbols() {
        return symbolDao.getSymbols();
    }

    @RequestMapping(path = "/{symbolId}", method = RequestMethod.GET)
    public Symbol getSymbolById(@PathVariable Long symbolId) {
        Symbol symbol = symbolDao.getSymbolById(symbolId);
        if (symbol == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Symbol not found");
        } else {
            return symbol;
        }
    }

    @GetMapping (path = "/{symbolId}/data_entries")
    public List<DataEntry> getDataEntriesBySymbolId(@PathVariable long symbolId) {
        return dataEntryDao.getDataEntriesBySymbolId(symbolId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Symbol createSymbol(@Valid @RequestBody Symbol symbol) {
        return symbolDao.createSymbol(symbol);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{symbolId}", method = RequestMethod.PUT)
    public Symbol updateSymbol(@Valid @RequestBody Symbol symbol, @PathVariable long symbolId) {
        symbol.setSymbolId(symbolId);

        try {
            return symbolDao.updateSymbol(symbol);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Symbol not found");
            }


    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{symbolId}", method = RequestMethod.DELETE)
    public void deleteSymbolById(@PathVariable Long symbolId) {
        symbolDao.deleteSymbolById(symbolId);
    }
}
