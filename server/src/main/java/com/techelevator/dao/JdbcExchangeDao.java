package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Exchange;
import org.springframework.dao.DataIntegrityViolationException;
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
public class JdbcExchangeDao implements ExchangeDao {

    private final JdbcTemplate template;


    public JdbcExchangeDao(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Exchange> getExchanges() {
        List<Exchange> exchanges = new ArrayList<>();

        String sql = "SELECT * FROM exchange";

        SqlRowSet rs = template.queryForRowSet(sql);

        while (rs.next()) {
            long exchangeId = rs.getLong("exchange_id");
            String name = rs.getString("name");
            Exchange exchange = new Exchange(exchangeId, name);
            exchanges.add(exchange);
        }
        return exchanges;
    }

    @Override
    public long deleteExchangeById(long exchangeId) {

        try {
            String deleteExchangeSql = "DELETE FROM exchange WHERE exchange_id = ?;";

            return template.update(deleteExchangeSql, exchangeId);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting exchange " + exchangeId, e);
        }
    }
}
