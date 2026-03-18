package com.repository;

import com.model.CartItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

@Repository
public class CartRepository {

    private JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        public void insert(CartItem item) {

            String sql = "insert into user(name,membership) values (?,?)";

            jdbcTemplate.update(sql,
                    item.getUser().getName(),
                    item.getUser().getMembership().toString());

            String itemSql = "insert into cart_item(name,price,qty,user_id) values (?,?,?,?)";

            jdbcTemplate.update(itemSql,
                    item.getName(),
                    item.getPrice(),
                    item.getQty(),
                    item.getUser().getId());
        }

        public int deleteById(int id) {
            String sql = "DELETE from cart_item where id=?";
            return jdbcTemplate.update(sql, id);
        }

        public List<CartItem> fetchAllItems() {
            String sql = "select * from cart_item c join user u on c.user_id = u.id";

            return jdbcTemplate.query(sql,new RowMapper<CartItem>() {
                @Override
                public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {

                    CartItem item = new CartItem();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setQty(rs.getInt("qty"));

                    return item;
                }
            });
        }

        public List<CartItem> fetchByUsername(String name) {
            String sql = "select c.* from cart_item c join user u on c.user_id=u.id where u.name=?";

            return jdbcTemplate.query(sql, new RowMapper<CartItem>() {
                @Override
                public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {

                    CartItem item = new CartItem();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setQty(rs.getInt("qty"));

                    return item;
                }
            }, name);
        }

        public void sample() {
            System.out.println("Repository AOP sample method");
        }
    }

