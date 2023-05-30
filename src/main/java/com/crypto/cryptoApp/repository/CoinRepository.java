package com.crypto.cryptoApp.repository;

import com.crypto.cryptoApp.dto.CoinTransationDTO;
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

    private static String SELECT_BY_NAME = "select * from coin where name = ?";

    private static String DELETE = "delete from coin where id = ?";

    private static String UPDATE = "update coin set name = ?,price = ?,quantity = ? where id = ?";
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

    public Coin update(Coin coin){
        Object[] attr = new  Object[]{
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getId()
        };
        jdbcTemplate.update(UPDATE,attr);
        return  coin;
    }
    public List<CoinTransationDTO> listAll(){
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<CoinTransationDTO>() {
            @Override
            public CoinTransationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                CoinTransationDTO coinDTO = new CoinTransationDTO();

                coinDTO.setName(rs.getString("name"));
                coinDTO.setQuantity(rs.getBigDecimal("quantity"));
                return coinDTO;
            }
        });
    }

    public List<Coin> getByName(String name){
        return jdbcTemplate.query(SELECT_BY_NAME, new RowMapper<Coin>() {
            Object[] attr = new Object[] {name};
            @Override
            public Coin mapRow(ResultSet rs, int rowNum) throws SQLException {
                Coin coin = new Coin();
                coin.setId(rs.getInt("id"));
                coin.setName(rs.getString("name"));
                coin.setPrice(rs.getBigDecimal("price"));
                coin.setQuantity(rs.getBigDecimal("quantity"));
                coin.setDateTime(rs.getTimestamp("datetime"));

                return coin;
            }
        },name);
    }

    public int remove(int id){
        return jdbcTemplate.update(DELETE, id);
    }
}
