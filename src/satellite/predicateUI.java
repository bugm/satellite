package satellite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class predicateUI extends JFrame {
	private String id="";
	private DBaction db= new DBaction();
	private JButton retu = new JButton("返回上一目录");
	private JButton jb = new JButton("选择 ");
	final JTextField j = new JTextField(10);
	 final DefaultTableModel dtm1 = new DefaultTableModel(new Object[1][13],new String[]{"卫星编号","名称","体积","重量","服役时间","发射时间","轨道倾角","升交点赤经","偏心率","近地点幅角","平近地点","日绕地圈数"});
	 JButton jb2 = new JButton("预测");
	 JTextField j1 = new JTextField(5);
	 JLabel jl1 = new JLabel("x:");
		JLabel jl2 = new JLabel("y:");
		JLabel jl3 = new JLabel("z:");
	public predicateUI()
	{
		
		JPanel p1  = new JPanel(new FlowLayout(FlowLayout.CENTER,15,15));
		p1.setBorder(new TitledBorder("卫星位置预测"));
		p1.add(new JLabel("卫星编号: "));
		j.setText("");
		p1.add(j);
		p1.add(jb);
		JPanel p2  = new JPanel(new GridLayout(4,1));
		JLabel number = new JLabel("当前选择卫星参数",JLabel.CENTER);
		p2.add(number);
	    JTable table1 = new JTable(dtm1);
	    table1.setEnabled(false);
		JScrollPane jj1 = new JScrollPane(table1);
		jj1.setPreferredSize(new Dimension(900,5));
		p2.add(jj1);
		JPanel p4  = new JPanel(new FlowLayout(FlowLayout.CENTER,15,15));
		p4.add(new JLabel("预测时间（天）"));
		p4.add(j1);
		p4.add(jb2);
		JPanel p5  = new JPanel(new FlowLayout(FlowLayout.CENTER,30,15));
		p5.add(jl1);
		p5.add(jl2);
		p5.add(jl3);
     	p2.add(p4);
	    p2.add(p5);
		p1.add(p2);
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table1.setDefaultRenderer(Object.class, r);
	    add(p1,BorderLayout.CENTER);
		JPanel p3  = new JPanel(new FlowLayout(FlowLayout.CENTER,15,15));
		p3.add(retu);
		add(p3,BorderLayout.SOUTH);
		this.pack();
		this.setTitle("卫星管理系统");
	    this.setSize(1000,400);
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		listener listen = new listener();
		retu.addActionListener(listen);
	     jb.addActionListener(listen);	
	     jb2.addActionListener(listen);
	    
		
		
	}
	
	public class listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource().equals(retu))
			{
				dispose();
				loginUI ui = new loginUI();
			}
			
		   if(e.getSource().equals(jb))
		   {
			  
			   String tempId = j.getText();
			   if(!tempId.equals(""))
			   if(db.idexist(tempId))
			   {		   
				  id = tempId;
			   satelliteInfo temp = db.getOneSatelliteinfo(id);
			   int i =0;
			   dtm1.setValueAt(temp.getid(),i, 0);
				dtm1.setValueAt(temp.getname(),i, 1);
				dtm1.setValueAt(temp.getWeight(),i, 2);
				dtm1.setValueAt(temp.getVolume(),i, 3);
				dtm1.setValueAt(temp.getWorktime(),i, 4);
				dtm1.setValueAt(temp.getEpoch(),i, 5);
				dtm1.setValueAt(temp.getAngle(),i, 6);
				dtm1.setValueAt(temp.getXnode(),i, 7);
				dtm1.setValueAt(temp.getEo(),i, 8);
				dtm1.setValueAt(temp.getOmega(),i, 9);
				dtm1.setValueAt(temp.getXmo(),i, 10);
				dtm1.setValueAt(temp.getXno(),i, 11);
			   }
			   else
			   {
				   JOptionPane.showMessageDialog(rootPane, "该卫星编号不存在");	
				   j.setText(id);
			   }
		   }
		   
		   if(e.getSource().equals(jb2))
		   {
			   if(id!="")
			   {  String temp = j1.getText();
			   if(!temp.equals(""))
			   {
				   double day = Double.parseDouble(temp);
				  if(day<=5)
				  {
					  Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
					     int year = c.get(Calendar.YEAR); 
					     int month = c.get(Calendar.MONTH); 
					     int date = c.get(Calendar.DATE); 
					     int hour = c.get(Calendar.HOUR_OF_DAY); 
					     int minute = c.get(Calendar.MINUTE); 
					     int minutes = db.timefromEpoch(id, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)+day, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
				       int m = db.timefromEpoch(id, year, month, day, hour, minutes);
				       position p = db.predicate(id, m);
				      double xkmper = 6.378135E3;
				       jl1.setText("X:"+p.getX()*xkmper);
				       jl2.setText("Y:"+p.getY()*xkmper);
				       jl3.setText("Z:"+p.getZ()*xkmper);
				
				  
				  }
				  else
				   {
					   JOptionPane.showMessageDialog(rootPane, "请输入5天以内数字");	
				   }
				   
			   }
		   }
			   else
			   {
				   JOptionPane.showMessageDialog(rootPane, "请首先选择卫星");	
			   }
		   }
		
		}
		
		
	}
	

}
