package sample;
import java.sql.*;

public class DBConnect {

    public Connection con;
    public Statement st;


    public static Connection getConnection() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finaljava?useTimezone=true&serverTimezone=UTC", "root", "");

        return connection;

    }

    DBConnect() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            // Create Connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/finaljava?useTimezone=true&serverTimezone=UTC", "root", "");
            //"jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12294102","sql12294102","YrBYNJeaNR"
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

    public String getResult(String title , String choice){
        String result = "";
        try {
            Connection con = DBConnect.getConnection();
            String sql = String.format("select * from %s where choice = '%s'" , title , choice);
            ResultSet rs =  con.createStatement().executeQuery(sql);
            while (rs.next()) {
                result = rs.getString("result");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void insertVoters(String username , String title){
        String sql = String.format("insert into voters(username , vote) VALUES('%s' ,'%s')" , username , title);

        try{
            st = con.createStatement();
            st.executeUpdate(sql);
            System.out.println("Voters Registered");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}