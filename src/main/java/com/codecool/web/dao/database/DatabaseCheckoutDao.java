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
    public void addToCart(Checkout item) throws SQLException {
        boolean autocommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO checkout (book_title, buyer, price) " +
            "VALUES(?, ?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, item.getBookTitle());
            ps.setString(2, item.getBuyer());
            ps.setInt(3, item.getPrice());
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

    @Override
    public List<Checkout> findAllCheckout() throws SQLException {
        List<Checkout> items = new ArrayList<>();
        String sql = "SELECT * FROM checkout";

        try(Statement ps = connection.createStatement();
            ResultSet rs = ps.executeQuery(sql)) {
            while(rs.next()){
                items.add(fetchCheckout(rs));
            }
        }
        return items;
    }

    @Override
    public List<Checkout> findCheckoutByUser(int userId) throws SQLException {
        List<Checkout> personalList = new ArrayList<>();
        String sql = "SELECT checkout_id, book_title, buyer, price FROM checkout " +
            "JOIN users ON users.email = checkout.buyer WHERE user_id = ?";
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
    public void deleteCheckout(int userId , int checkoutId) throws SQLException {
        String sql = "DELETE FROM user_checkout_table WHERE user_id = ? AND checkout_id = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, checkoutId);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean inCart(int userId, int checkoutId) throws SQLException {
        String sql =  "SELECT * FROM user_checkout_table WHERE user_id = ? AND checkout_id = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, checkoutId);
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
