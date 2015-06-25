package satellite;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBaction {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url="jdbc:mysql://127.0.0.1:3306/satellite";
	private static String username="root";
	private static String password="mzy110";
	private static Connection conn=null;
	private static PreparedStatement prepare = null;
	private static String sql;
	private static ResultSet rs;
	private predicate p = new predicate();
	
	public DBaction()
	{
		try {
			Class.forName(driver);
			
			try {
				conn = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}

			try {
				if(!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public boolean logincheck(String id,String passwd)
	{
		sql = "select * from userinfo where id = ? and passwd = ?";
		try {
			prepare = conn.prepareStatement(sql);
			prepare.setString(1, id);
			prepare.setString(2, passwd);
			rs = prepare.executeQuery();
			return rs.next();	
	    }
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	 }
	
	public void  insertinfo1(String info)
	{
		sql = "insert into property values(?,?,?,?,?)";
		try {
			prepare = conn.prepareStatement(sql);
			String[] temp = info.split("#");
			
			for(int i =0; i< temp.length;i++)
			 if(i==0||i==1)
				 prepare.setString(i+1, temp[i]);
			 else if(i==4)
				 prepare.setInt(i+1, Integer.parseInt(temp[i]));
			 else
				 prepare.setDouble(i+1, Double.parseDouble(temp[i]));
			prepare.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertinfo2(String info,String id)
	{
		sql = "insert into orbitpara values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			prepare = conn.prepareStatement(sql);
			prepare.setString(1, id);
			String[] temp = info.split("#");
			prepare.setInt(2, Integer.parseInt(temp[0]));
			for(int i=1;i<temp.length;i++)
				prepare.setDouble(i+2, Double.parseDouble(temp[i]));
			prepare.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeinfo1(String info, String id)
	{
		String temp[] = info.split("#");
		sql = "update property set sname =?,weight=?,volume=?,worktime=? where id ="+id;
		try {
			prepare = conn.prepareStatement(sql);
			for(int i =1; i< temp.length;i++)
				 if(i==1)
					 prepare.setString(i, temp[i]);
				 else if(i==4)
					 prepare.setInt(i, Integer.parseInt(temp[i]));
				 else
					 prepare.setDouble(i, Double.parseDouble(temp[i]));
			prepare.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeinfo2(String info, String id)
	{
		String temp[] = info.split("#");
		sql = "update orbitpara set epoch=?,xincl=?,xnodeo=?,eo=?,omegao=?,xmo=?,xno=?,bstar=?,xndt2o=?,xndd6o=? where id="+id;
		try
		{
		prepare = conn.prepareStatement(sql);
		prepare.setInt(1, Integer.parseInt(temp[0]));
		for(int i=1;i<temp.length;i++)
			prepare.setDouble(i+1, Double.parseDouble(temp[i]));
		prepare.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public int deleteinfo(String info)
	{
		String temp[] = info.split("#");
		int total = 0;
		for(int i =0;i<temp.length;i++)
		{
			if(idexist(temp[i]))
			{
				total ++;
				sql = "delete from property where id =?";
				try {
					prepare = conn.prepareStatement(sql);
					prepare.setString(1, temp[i]);
					prepare.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return total;
	}
	public int numinfo()
	{
		sql = "select count(*) from property";
		int total = 0;
		try {
			prepare = conn.prepareStatement(sql);
			rs = prepare.executeQuery();
			rs.next();
			total = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return total;
	}
	
	public ArrayList getsateInfo()
	{
		PreparedStatement prepare2 = null;
		String sql2;
	     ResultSet rs2;
	     sql = "select * from property order by id";
	     sql2 = "select * from orbitpara order by id";
	     ArrayList l= new ArrayList();
	     try {
			prepare = conn.prepareStatement(sql);
			prepare2 = conn.prepareStatement(sql2);
			rs = prepare.executeQuery();
			rs2 = prepare2.executeQuery();
			
			while(rs.next())
			{
				satelliteInfo info = new satelliteInfo();
				rs2.next();
		       info.setId(rs.getString(1));
		       info.setName(rs.getString(2));
		       info.setWeight(rs.getDouble(3));
		       info.setVolume(rs.getDouble(4));
		       info.setWorktime(rs.getInt(5));
			   info.setEpoch(rs2.getInt(2));
			   info.setAngle(rs2.getDouble(3));
			   info.setXnode(rs2.getDouble(4));
			   info.setEo(rs2.getDouble(5));
			   info.setOmega(rs2.getDouble(6));
			   info.setXmo(rs2.getDouble(7));
			   info.setXno(rs2.getDouble(8));
			   l.add(info);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;	
	}
	
	public satelliteInfo getOneSatelliteinfo(String id)
	{
		PreparedStatement prepare2 = null;
		String sql2;
	     ResultSet rs2;
	     sql = "select * from property where id ="+id;
	     sql2 = "select * from orbitpara where id ="+id;
	     satelliteInfo info = new satelliteInfo();
	     try {
			prepare = conn.prepareStatement(sql);
			prepare2 = conn.prepareStatement(sql2);
			rs = prepare.executeQuery();
			rs2 = prepare2.executeQuery();
			   rs.next();
			   rs2.next();
		       info.setId(rs.getString(1));
		       info.setName(rs.getString(2));
		       info.setWeight(rs.getDouble(3));
		       info.setVolume(rs.getDouble(4));
		       info.setWorktime(rs.getInt(5));
			   info.setEpoch(rs2.getInt(2));
			   info.setAngle(rs2.getDouble(3));
			   info.setXnode(rs2.getDouble(4));
			   info.setEo(rs2.getDouble(5));
			   info.setOmega(rs2.getDouble(6));
			   info.setXmo(rs2.getDouble(7));
			   info.setXno(rs2.getDouble(8));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;	
		
		
	}
	public int timefromEpoch(String id,int year,int month,double day,int hour,int minute)
	{
		sql = "select epoch from orbitpara where id = ?";
		int totalminutes=0;
		try {
			prepare = conn.prepareStatement(sql);
			prepare.setString(1, id);
			rs = prepare.executeQuery();
			rs.next();
			int temp = rs.getInt(1);
			totalminutes+=minute-temp%100;
			temp/=100;
			totalminutes+=(hour-temp%100)*60;
			temp/=100;
			totalminutes+=(day-temp%100)*24*60;
			temp/=100;
			totalminutes+=(month-temp%100)*30*24*60;
			temp/=100;
			totalminutes+=(year-temp%100)*365*24*60;
			
		  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return totalminutes;
	}
	
	public boolean idexist(String id)
	{
		sql = "select * from property where id = ?";
		try {
			prepare = conn.prepareStatement(sql);
			prepare.setString(1, id);
			rs = prepare.executeQuery();
			if(rs.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public position predicate(String id,int minutes)
	{
		sql = "select * from orbitpara where id =?";
		position ps = new position();
		try {
			prepare = conn.prepareStatement(sql);
			prepare.setString(1, id);
			rs = prepare.executeQuery();
			rs.next();
			ps = p.SGP4(minutes,rs.getDouble(10) , rs.getDouble(11), rs.getDouble(9), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ps;
	}
	

}