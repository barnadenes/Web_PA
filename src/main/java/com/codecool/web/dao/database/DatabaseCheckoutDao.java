package com.codecool.web.dao.database;

import com.codecool.web.dao.CheckoutDao;
import com.codecool.web.model.Checkout;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCheckoutDao extends AbstractDao implements CheckoutDao {

    public DatabaseCheckoutDao(Connection connection) {
        super(connection);
    }

    @Override
    public Integer addToCart(String title, String buyer, int price) throws SQLException {
        String sql = "INSERT INTO checkout (book_title, buyer, price) " +
            "VALUES(?, ?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setString(2, buyer);
            ps.setInt(3, price);

            executeInsert(ps);
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                return rs.getInt("checkout_id");
            }
        }
        return null;
    }

    @Override
    public List<Checkout> findCheckoutByUser(int userId) throws SQLException {
        List<Checkout> personalList = new ArrayList<>();
        String sql = "SELECT  users.user_id, checkout.checkout_id, book_title, buyer, price FROM checkout " +
        "JOIN user_checkout_table ON user_checkout_table.checkout_id = checkout.checkout_id " +
        "JOIN users ON user_checkout_table.user_id = users.user_id " +
        "WHERE users.user_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs =  ps.executeQuery();
            while(rs.next()) {
                personalList.add(fetchCheckout(rs));
            }
        }
        return personalList;
    }

    @Override
    public Checkout findCheckoutById(int checkout_id) throws SQLException {
        String sql = "SELECT checkout_id, book_title, buyer, price FROM checkout " +
            "WHERE checkout_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, checkout_id);
            ResultSet rs =  ps.executeQuery();
            if(rs.next()) {
                return fetchCheckout(rs);
            }
        }
        return null;
    }

    @Override
    public void deleteCheckout(int userId , int checkoutId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM user_checkout_table WHERE user_id = ? AND checkout_id = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, checkoutId);
            executeInsert(ps);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void deleteCheckoutMain(int checkout_id, String buyer) throws SQLException {
        boolean autocommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM checkout WHERE checkout_id = ? AND buyer = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, checkout_id);
            ps.setString(2, buyer);
            executeInsert(ps);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(autocommit);
        }
    }

    @Override
    public boolean inCart(String title, String buyer) throws SQLException {
        String sql =  "SELECT * FROM checkout WHERE book_title = ? AND buyer = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, buyer);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
    }

    @Override
    public void addToUserCheckoutTable(int userId, int checkoutId) throws SQLException{
        boolean autocommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO user_checkout_table (user_id, checkout_id) " +
            "VALUES(?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, checkoutId);
            executeInsert(ps);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        finally {
            connection.setAutoCommit(autocommit);
        }
    }


    private Checkout fetchCheckout(ResultSet rs) throws SQLException {
        int id = rs.getInt("checkout_id");
        String title = rs.getString("book_title");
        String buyer = rs.getString("buyer");
        int price = rs.getInt("price");
        return new Checkout(id, title, buyer, price);
    }
}
