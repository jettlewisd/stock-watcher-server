package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Exchange;
import com.techelevator.model.Symbol;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcSymbolDao implements SymbolDao {

    private final JdbcTemplate template;

   /*
   public JdbcSymbolDao(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }
     */

    public JdbcSymbolDao(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Symbol> getSymbols() {
        List<Symbol> symbols = new ArrayList<>();

        String sql = "SELECT * FROM symbol";

        SqlRowSet rs = template.queryForRowSet(sql);

        while (rs.next()) {
            long symbolId = rs.getLong("symbol_id");
            long exchangeId = rs.getLong("exchange_id");
            String name = rs.getString("name");
            String tickerSymbol = rs.getString("ticker_symbol");
            Boolean priority = rs.getBoolean("priority");
            Symbol symbol = new Symbol(symbolId, exchangeId, name, tickerSymbol, priority);
            symbols.add(symbol);
        }
        return symbols;
    }

    @Override
    public Symbol getSymbolById(long symbolId) {
        Symbol symbol = null;
        String sql = "SELECT symbol_id, exchange_id, name, ticker_symbol, priority " +
                "FROM symbol " +
                "WHERE symbol_id = ?;";
        SqlRowSet results = template.queryForRowSet(sql, symbolId);
        if (results.next()) {
            symbol = mapRowToSymbol(results);
        }
        return symbol;
    }

    @Override
    public List<Symbol> getSymbolsByExchangeId(long exchangeId) {
        String sql = "SELECT * FROM symbol WHERE exchange_id = ?;";
        return template.query(sql, MAPPER, exchangeId);
    }

    @Override
    public Symbol createSymbol(Symbol newSymbol) {
        String sql = "INSERT INTO symbol (exchange_id, name, ticker_symbol, priority) VALUES (?, ?, ?, ?) RETURNING symbol_id;";

        long symbolId = template.queryForObject(sql, long.class, newSymbol.getExchangeId(), newSymbol.getName(),
                newSymbol.getTickerSymbol(), newSymbol.isPriority());

        return getSymbolById(symbolId);
    }

    @Override
    public Symbol updateSymbol(Symbol updatedSymbol) {
        String sql = "UPDATE symbol " +
                "SET exchange_id=?, name=?, ticker_symbol=?, priority=? " +
                "WHERE symbol_id=?;";
       try{
           template.update(sql, updatedSymbol.getExchangeId(), updatedSymbol.getName(), updatedSymbol.getTickerSymbol(),
                   updatedSymbol.isPriority(), updatedSymbol.getSymbolId());

           updatedSymbol = getSymbolById(updatedSymbol.getSymbolId());

       } catch (CannotGetJdbcConnectionException e) {
           throw new DaoException("Unable to connect to server or database", e);
       } catch (DataIntegrityViolationException e) {
           throw new DaoException("Data integrity violation", e);
       }
        return updatedSymbol;
    }

    @Override
    public long deleteSymbolById(long symbolId) {
        try {
            String deleteSymbolSql = "DELETE FROM symbol WHERE symbol_id = ?;";

            return template.update(deleteSymbolSql, symbolId);

        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting symbol " + symbolId, e);
        }
    }

    private Symbol mapRowToSymbol(SqlRowSet rowSet) {
        Symbol symbol = new Symbol();
        symbol.setSymbolId(rowSet.getLong("symbol_id"));
        symbol.setExchangeId(rowSet.getLong("exchange_id"));
        symbol.setName(rowSet.getString("name"));
        symbol.setTickerSymbol(rowSet.getString("ticker_symbol"));
        symbol.setPriority(rowSet.getBoolean("priority"));
        return symbol;
    }

    private static final RowMapper<Symbol> MAPPER = new RowMapper<Symbol>() {
        @Override
        public Symbol mapRow(ResultSet rs, int rowNum) throws SQLException {
            Symbol symbol = new Symbol();
            symbol.setSymbolId(rs.getLong("symbol_id"));
            symbol.setExchangeId(rs.getLong("exchange_id"));
            symbol.setName(rs.getString("name"));
            symbol.setTickerSymbol(rs.getString("ticker_symbol"));
            symbol.setPriority(rs.getBoolean("priority"));
            return symbol;
        }
    };
}
