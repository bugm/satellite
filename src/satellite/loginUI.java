package satellite;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Utilities;



public class loginUI extends JFrame {
	JPanel p0  = new JPanel(new GridLayout(4,1));
	JPanel p  = new JPanel(new FlowLayout(FlowLayout.CENTER,15,15));
	JButton jb = new JButton("查看卫星数据");
	JButton jb1 = new JButton("录入/更新卫星数据");
	JButton jb2 = new JButton("删除卫星数据");
	JButton jb3 = new JButton("卫星位置预测");
	BufferedReader reader;
	DBaction db = new DBaction();
  public loginUI()
  {
	  
	  this.setLayout(new BorderLayout());
		p.add(jb);
		JPanel p1  = new JPanel(new FlowLayout(FlowLayout.CENTER,15,15));
		p1.add(jb1);
		JPanel p2  = new JPanel(new FlowLayout(FlowLayout.CENTER,15,15));
		p2.add(jb2);
		JPanel p3  = new JPanel(new FlowLayout(FlowLayout.CENTER,15,15));
		p3.add(jb3);
		p0.add(p);p0.add(p1);p0.add(p2);p0.add(p3);
		add(p0,BorderLayout.CENTER);
		this.pack();
		this.setTitle("卫星管理系统");
	    this.setSize(350,350);
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		ActionListener listen = new listener();
	    jb.addActionListener(listen);
		jb1.addActionListener(listen);
        jb2.addActionListener(listen);
        jb3.addActionListener(listen);
	
	
  }
  public class listener implements ActionListener
  {
	  public void actionPerformed(ActionEvent e)
	  {
		  if(e.getSource().equals(jb))
		  {
			  dispose();
			  infoUI ui = new infoUI();
			  
		  }
		  
	 	 if(e.getSource().equals(jb1))
	 	 {
	 			JFileChooser jf = new JFileChooser();
	 			jf.addChoosableFileFilter(new txtfilter());
	 			jf.setDialogTitle("选择数据录入文件（txt）");
	 			int returnVal = jf.showOpenDialog(null);
	 			if(returnVal==0)
	 			{
	 			     String filename = jf.getSelectedFile().getPath();
	 		         if(!filename.endsWith(".txt"))
	 				    JOptionPane.showMessageDialog(rootPane, "请选择正确的文件！");	
	 		         else
	 		         {
	 		    	     File f = jf.getSelectedFile();
	 		    	     try {
							reader = new BufferedReader(new FileReader(f));
							String temp =null;
							String id = null;
							int total1=0;
							int total2 =0;
								while((temp = reader.readLine())!=null)
								{
							          
									
									  id = temp.split("#")[0];
									  System.out.println(id);
									  if(db.idexist(id))
									  {
										  System.out.println("yes");
										  db.changeinfo1(temp, id);
										  temp = reader.readLine();
										  db.changeinfo2(temp, id);
										  total1++;
									  }
									  else
									  {
										  System.out.println("no");
									  db.insertinfo1(temp);
							          temp = reader.readLine();
							          db.insertinfo2(temp,id);
							          total2++;
									  }
							}
								 JOptionPane.showMessageDialog(rootPane, "共插入卫星数据:"+total2+"条\n"+"更新卫星数据:"+total1+"条");	
							}
	 		    	    catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
						}
	 		    	     finally
	 		    	     {
	 		    	    	 if(reader!=null)
								try {
									reader.close();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	 		    	    	 
	 		    	     }
	 		         }
	 			}
	 	 }
	 	    if(e.getSource().equals(jb2))
	 	  {
	 			JFileChooser jf = new JFileChooser();
	 			jf.addChoosableFileFilter(new txtfilter());
	 			jf.setDialogTitle("选择要删除id文件（txt）");
	 			int returnVal = jf.showOpenDialog(null);
	 			if(returnVal==0)
	 			{
	 			     String filename = jf.getSelectedFile().getPath();
	 		         if(!filename.endsWith(".txt"))
	 				    JOptionPane.showMessageDialog(rootPane, "请选择正确的文件！");
	 		     
	 			else
	 			{
	 				 File f = jf.getSelectedFile();
	 				 int total = 0;
	 				try {
						reader = new BufferedReader(new FileReader(f));
						String temp =null;
							while((temp = reader.readLine())!=null)
							{
						          
								total +=db.deleteinfo(temp);
								
						}
					} 
	 				catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	 				catch(IOException e1)
	 				{
	 					
	 				}
	 				  JOptionPane.showMessageDialog(rootPane, "共删除记录:"+total+"条");	
	 			}
	 			}
	      }
	 	    if(e.getSource().equals(jb3))
	 	    {
	 	    	dispose();
	 	    	predicateUI temp = new predicateUI();
	 	    }
      }
 
  }
  

 public class txtfilter extends FileFilter
  {

	@Override
	public boolean accept(File f) {
		String filename = f.getPath();
		// TODO Auto-generated method stub
		return filename.endsWith(".txt");
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "选择txt文件";
	}


  	
  	
  }
  
  
}


