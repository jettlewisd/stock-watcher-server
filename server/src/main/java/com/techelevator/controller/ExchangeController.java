package com.techelevator.controller;

import com.techelevator.dao.ExchangeDao;
import com.techelevator.dao.SymbolDao;
import com.techelevator.model.Exchange;
import com.techelevator.model.Symbol;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
@RequestMapping("/exchanges")
public class ExchangeController {

    private final ExchangeDao exchangeDao;

    private final SymbolDao symbolDao;

    public ExchangeController(ExchangeDao exchangeDao, SymbolDao symbolDao) {
        this.exchangeDao = exchangeDao;
        this.symbolDao = symbolDao;
    }


    @GetMapping
    public List<Exchange> getExchanges() {
        return exchangeDao.getExchanges();
    }

    @GetMapping (path = "/{exchangeId}/symbols")
    public List<Symbol> getSymbolsByExchangeId(@PathVariable long exchangeId) {
        return symbolDao.getSymbolsByExchangeId(exchangeId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{exchangeId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long exchangeId) {
        exchangeDao.deleteExchangeById(exchangeId);
    }
}
