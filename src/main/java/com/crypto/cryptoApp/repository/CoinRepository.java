package com.crypto.cryptoApp.repository;

import com.crypto.cryptoApp.entity.Coin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepository {
    private JdbcTemplate jdbcTemplate;

    private static String INSERT = "insert into coin(name, price, quantity, dateTime)values(?,?,?,?)";
    private static String SELECT_ALL = "select name, sum(quantity) as quantity from coin group by name";
    public CoinRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Coin insert(Coin coin){
        Object[] attr = new Object[]{
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getDateTime()
        };
        jdbcTemplate.update(INSERT,attr);
        return coin;
    }
    public List<Coin> getAll(){
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<Coin>() {
            @Override
            public Coin mapRow(ResultSet rs, int rowNum) throws SQLException {
                Coin coin = new Coin();
                coin.setName(rs.getString("name"));
                coin.setQuantity(rs.getBigDecimal("quantity"));
                return coin;
            }
        });
    }
}
