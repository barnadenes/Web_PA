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
    public void addToCart(int id, String title, String buyer, int price) throws SQLException {
        boolean autocommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO checkout (checkout_id, book_title, buyer, price) " +
            "VALUES(?, ?, ?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, buyer);
            ps.setInt(4, price);
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
    public void deleteCheckoutMain(int checkoutId) throws SQLException {
        boolean autocommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM checkout WHERE checkout_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, checkoutId);
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
