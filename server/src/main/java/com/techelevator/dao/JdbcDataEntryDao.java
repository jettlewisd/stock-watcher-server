package com.techelevator.dao;

import com.techelevator.model.DataEntry;
import com.techelevator.model.Symbol;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDataEntryDao implements DataEntryDao {

    private final JdbcTemplate template;

    public JdbcDataEntryDao(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);

    }

    @Override
    public List<DataEntry> getDataEntries() {
        List<DataEntry> dataEntries = new ArrayList<>();

        String sql = "SELECT * FROM data_entry";

        SqlRowSet rs = template.queryForRowSet(sql);

        while (rs.next()) {
            long dataEntryId = rs.getLong("data_entry_id");
            long symbolId = rs.getLong("symbol_id");
            LocalDate tradingDate = rs.getDate("trading_date").toLocalDate();
            BigDecimal openPrice = rs.getBigDecimal("open_price");
            BigDecimal closePrice = rs.getBigDecimal("close_price");
            BigDecimal highPrice = rs.getBigDecimal("high_price");
            BigDecimal lowPrice = rs.getBigDecimal("low_price");
            DataEntry dataEntry = new DataEntry(dataEntryId, symbolId, tradingDate, openPrice, closePrice, highPrice, lowPrice);
            dataEntries.add(dataEntry);
        }
        return dataEntries;
    }

    @Override
    public DataEntry getDataEntryById(long dataEntryId) {

        String sql = "SELECT data_entry_id, symbol_id, trading_date, open_price, close_price, high_price, low_price " +
                "FROM data_entry " +
                "WHERE data_entry_id = ?;";
        try {
            SqlRowSet rowSet = template.queryForRowSet(sql, dataEntryId);
            if (rowSet.next()) {
                return mapRowToDataEntry(rowSet);
            } else {
                return null;
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<DataEntry> getDataEntriesBySymbolId(long symbolId) {
        String sql = "SELECT * FROM data_entry WHERE symbol_id = ?;";
        return template.query(sql, MAPPER, symbolId);
    }

    private DataEntry mapRowToDataEntry(SqlRowSet rowSet) {
        DataEntry dataEntry = new DataEntry();
        dataEntry.setDataEntryId(rowSet.getLong("data_entry_id"));
        dataEntry.setSymbolId(rowSet.getLong("symbol_id"));
        dataEntry.setTradingDate(rowSet.getDate("trading_date").toLocalDate());
        dataEntry.setOpenPrice(rowSet.getBigDecimal("open_price"));
        dataEntry.setClosePrice(rowSet.getBigDecimal("close_price"));
        dataEntry.setHighPrice(rowSet.getBigDecimal("high_price"));
        dataEntry.setLowPrice(rowSet.getBigDecimal("low_price"));
        return dataEntry;
    }

    private static final RowMapper<DataEntry> MAPPER = new RowMapper<DataEntry>() {
        @Override
        public DataEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
            DataEntry dataEntry = new DataEntry();
            dataEntry.setDataEntryId(rs.getLong("data_entry_id"));
            dataEntry.setSymbolId(rs.getLong("symbol_id"));
            dataEntry.setTradingDate(rs.getDate("trading_date").toLocalDate());
            dataEntry.setOpenPrice(rs.getBigDecimal("open_price"));
            dataEntry.setClosePrice(rs.getBigDecimal("close_price"));
            dataEntry.setHighPrice(rs.getBigDecimal("high_price"));
            dataEntry.setLowPrice(rs.getBigDecimal("low_price"));
            return dataEntry;
        }
    };
}
