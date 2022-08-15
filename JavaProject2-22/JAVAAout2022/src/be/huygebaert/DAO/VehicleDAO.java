package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.huygebaert.POJO.Vehicle;

public class VehicleDAO extends DAO<Vehicle>{
	public VehicleDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Vehicle vehicle) {
		try(PreparedStatement ps0 = this.connect.prepareStatement("INSERT INTO Vehicle VALUES (?,?,?,?)")) {
		    ps0.setInt(1, 0);
	        ps0.setInt(2, vehicle.getTotalMemberSeats());
	        ps0.setInt(3, vehicle.getTotalVeloSeats());
	        ps0.setInt(4, vehicle.getDriver().getId());

	        if(ps0.executeUpdate() > 0) {
	        	return true;
	        }
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Vehicle obj) {
		return false;
	}

	@Override
	public boolean update(Vehicle vehicle) {
		return false;
	}

	@Override
	public Vehicle find(int id) {
		Vehicle vehicle = null;
		MemberDAO memberDAO = new MemberDAO(this.connect);
		ResultSet result = null;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Vehicle WHERE IdVehicle ="+id);
			while(result.next()){
				vehicle = new Vehicle(result.getInt("TotalMemberSeats"),result.getInt("TotalVeloSeats"),memberDAO.find(result.getInt("IdDriver")));
				vehicle.setNum(result.getInt("IdVehicle"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vehicle;
	}

	@Override
	public List<Vehicle> findAll() {
		List<Vehicle> allVehicles = new ArrayList <Vehicle>();
		Vehicle vehicle;
		MemberDAO memberDAO = new MemberDAO(this.connect);
		ResultSet result = null;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Vehicle");
			while(result.next()){
				vehicle = new Vehicle(result.getInt("TotalMemberSeats"),result.getInt("TotalVeloSeats"),memberDAO.find(result.getInt("IdDriver")));
				vehicle.setNum(result.getInt("IdVehicle"));
				allVehicles.add(vehicle);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allVehicles;
	}
}
