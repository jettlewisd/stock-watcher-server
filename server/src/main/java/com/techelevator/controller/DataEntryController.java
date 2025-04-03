package com.techelevator.controller;

import com.techelevator.dao.DataEntryDao;
import com.techelevator.dao.SymbolDao;
import com.techelevator.model.DataEntry;
import com.techelevator.model.Exchange;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
// @PreAuthorize("isAuthenticated()")
@RequestMapping("/data_entries")
public class DataEntryController {

    private final DataEntryDao dataEntryDao;
    private final SymbolDao symbolDao;

    public DataEntryController(DataEntryDao dataEntryDao, SymbolDao symbolDao) {
        this.dataEntryDao = dataEntryDao;
        this.symbolDao = symbolDao;
    }

    @GetMapping
    public List<DataEntry> getDataEntries() {
        return dataEntryDao.getDataEntries();
    }
}
