package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BusinessCardManagerDao {
	
	private static String dburl = "jdbc:mysql://localhost:3306/connectdb?useSSL=false&serverTimezone=UTC";
	private static String dbUser = "connectuser";
	private static String dbpasswd = "connect123!@#";
	
    public List<BusinessCard> searchBusinessCard(String keyword){
    	
    	List<BusinessCard> cards = new ArrayList<>();
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql = "SELECT * FROM BusinessCard WHERE name LIKE ? OR phone LIKE ? OR companyName LIKE ?";
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			 ps.setString(1, "%" + keyword + "%");
			 ps.setString(2, "%" + keyword + "%");
			 ps.setString(3, "%" + keyword + "%"); // set parameter with wildCard.

			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				BusinessCard card = new BusinessCard(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4));
				cards.add(card);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return cards;
    }

    public BusinessCard addBusinessCard(BusinessCard businessCard){

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO BusinessCard (name, phone, companyName) VALUES ( ?, ?, ? )";

		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, businessCard.getName());
			ps.setString(2, businessCard.getPhone());
			ps.setString(3, businessCard.getCompanyName());

			ps.executeUpdate(); // insert, update, delete >> executeUpdate

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return businessCard;
    }
}
