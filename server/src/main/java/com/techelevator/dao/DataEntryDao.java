package com.techelevator.dao;

import com.techelevator.model.DataEntry;

import java.util.List;

public interface DataEntryDao {


    public List<DataEntry> getDataEntries();

    public DataEntry getDataEntryById(long dataEntryId);

    public List <DataEntry> getDataEntriesBySymbolId(long symbolId);


}
