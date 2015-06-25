package satellite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class infoUI extends JFrame {
	private DBaction db= new DBaction();
	private JButton retu = new JButton("返回上一目录");
	private JButton jb = new JButton("查询 ");
	final JTextField j = new JTextField(10);
	 final DefaultTableModel dtm1 = new DefaultTableModel(new Object[1][12],new String[]{"卫星编号","名称","重量(吨)","体积(平方米)","服役时间(年)","发射时间","轨道倾角","升交点赤经","偏心率","近地点幅角","平近地点","日绕地圈数"});
	
	public infoUI()
	{
		JPanel p1  = new JPanel(new FlowLayout(FlowLayout.CENTER,5,15));
		p1.setBorder(new TitledBorder("卫星数据查询"));
		p1.add(new JLabel("卫星编号: "));
		j.setText("");
		p1.add(j);
		p1.add(jb);
	    JTable table1 = new JTable(dtm1);
	    table1.setEnabled(false);
		JScrollPane jj1 = new JScrollPane(table1);
		jj1.setPreferredSize(new Dimension(900,40));
		p1.add(jj1);
		final JLabel number = new JLabel("                              数据汇总                                                     ");
		p1.add(number);
		final Object[][] data1 = new Object[db.numinfo()+10][12];
	    final DefaultTableModel dtm3 = new DefaultTableModel(data1,new String[]{"卫星编号","名称","重量(吨)","体积(平方米)","服役时间(年)","发射时间","轨道倾角","升交点赤经","偏心率","近地点幅角","平近地点","日绕地圈数"});
		final JTable table3 = new JTable(dtm3);
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table3.setDefaultRenderer(Object.class, r);
		table1.setDefaultRenderer(Object.class, r);
		ArrayList al;
		satelliteInfo temp;
		al = db.getsateInfo();
		for(int i=0;i<al.size();i++)
		{
		
			temp = (satelliteInfo)al.get(i);
			dtm3.setValueAt(temp.getid(),i, 0);
			dtm3.setValueAt(temp.getname(),i, 1);
			dtm3.setValueAt(temp.getWeight(),i, 2);
			dtm3.setValueAt(temp.getVolume(),i, 3);
			dtm3.setValueAt(temp.getWorktime(),i, 4);
			dtm3.setValueAt(temp.getEpoch(),i, 5);
			dtm3.setValueAt(temp.getAngle(),i, 6);
			dtm3.setValueAt(temp.getXnode(),i, 7);
			dtm3.setValueAt(temp.getEo(),i, 8);
			dtm3.setValueAt(temp.getOmega(),i, 9);
			dtm3.setValueAt(temp.getXmo(),i, 10);
			dtm3.setValueAt(temp.getXno(),i, 11);
		}
		table3.setEnabled(false);
		final JScrollPane jj3 = new JScrollPane(table3);
		jj3.setPreferredSize(new Dimension(920, 200));
		p1.add(jj3);
	    add(p1,BorderLayout.CENTER);
		JPanel p3  = new JPanel(new FlowLayout(FlowLayout.CENTER,5,15));
		p3.add(retu);
		add(p3,BorderLayout.SOUTH);
		this.pack();
		this.setTitle("卫星管理系统");
	    this.setSize(1000,500);
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		listener listen = new listener();
		retu.addActionListener(listen);
	     jb.addActionListener(listen);	
	 
		
		
		
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
			   String id = j.getText();
			   
			   if(!id.equals(""))
			   if(db.idexist(id))
			   {		   
			   satelliteInfo temp = db.getOneSatelliteinfo(id);
			    int i =0;
			    System.out.println(id);
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
			   }
		   }
		
		}
		
		
	}
	

}
