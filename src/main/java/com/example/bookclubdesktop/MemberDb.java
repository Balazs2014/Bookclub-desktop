package com.example.bookclubdesktop;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDb {
    Connection conn;

    public MemberDb() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vizsga-konyvklub", "root", "");
    }

    public List<Member> getMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            String gender = result.getString("gender");
            LocalDate birth_date = result.getDate("birth_date").toLocalDate();
            boolean banned = result.getBoolean("banned");
            Member m = new Member(id, name, gender, birth_date, banned);
            members.add(m);
        }
        return members;
    }

    public boolean banMember(Member m) throws SQLException {
        String sql = "UPDATE members SET banned = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        if (m.isBanned()) {
            stmt.setBoolean(1, false);
            stmt.setInt(2, m.getId());
        } else {
            stmt.setBoolean(1, true);
            stmt.setInt(2, m.getId());
        }
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
}
