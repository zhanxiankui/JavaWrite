
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadTxt {
	
	/**
	 * read file
	 * @param fileName
	 */
    
    public void readFile(String fileName)
    {
    	
    	//获取数据库的链接
    	ConnDb cb=new ConnDb();
        Connection connection=null;
        //采用  PreparedStatement 效率高，安全
 	    PreparedStatement  prestmt=null; 	 
    	 try {
			 connection= cb.getConnection();
		     connection.setAutoCommit(false);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			};
    	
    	
    	int count=0;//作为计数器
     
        try {
            File file=new File(fileName);
            
            InputStreamReader in;
            in = new InputStreamReader(new FileInputStream(file),"GBK");
            
             
            BufferedReader br=new BufferedReader(in);
            
             String line="";
            
              line=br.readLine();
              prestmt=connection.prepareStatement("insert into studentd(sid,sname,classes,course,score) values(?,?,?,?,?)");
              
              long t1=System.currentTimeMillis(); //获取开始运行的时间
              
              while(line!=null)
              {
                  count++;
                  
                  String[] data=line.split(",");    
                  insertData(data,prestmt,connection,count);
                  
//                  System.out.println(line);
                  line=br.readLine();  
                  
                  if(count%1000==0)
                  {
                	  long t2=System.currentTimeMillis(); //获取时间
                	  System.out.println("程序插入1000条的时间： "+(t2-t1)+"ms");
                  }
              }  
              
              System.out.println("一共有的数据量为:"+count);
              
              connection.setAutoCommit(true);
              //关闭所有的流
              br.close();
              prestmt.close();
          	  connection.close();
            
        } catch (Exception e) {  
            e.printStackTrace();
        } 
 
    }
    

    //100条数据进行插入数据库
public void insertData(String[] das, PreparedStatement  prestmt,Connection connection,int times){
		 
		try {
			prestmt.setString(1, das[0]);
			prestmt.setString(2, das[1]);
			prestmt.setString(3, das[2]);
			prestmt.setString(4, das[3]);
			prestmt.setString(5, das[4]);
			
			//批量提交
			prestmt.addBatch();
			
			if(times%100==0)
			{
				prestmt.executeBatch();
				System.out.println(times+"提交数据成功");
				connection.commit();
			}	
						
//			prestmt.executeUpdate();
//			connection.commit();	
//			 System.out.println("成功插入");

		} catch (Exception e) {
			
		  System.out.println("字符串有问题"+e);
		}
			
	} 
    
    
public void selectDataWrite()
{
	String sql="select  sid,sname,classes,course,score from   student";
	String path="src/studentd.txt";
	
	PreparedStatement paret=null;
	ResultSet rs=null;
	ConnDb cb=new ConnDb();
    Connection connection=null;

    //文件的东西
    File f=new File(path);
    BufferedWriter bw=null;
    StringBuilder sb=new StringBuilder();
    
		try {
			 if(!f.exists())
			 {
			   f.createNewFile();
			   System.out.println("create file succeed");
			 }
			 
			bw=new BufferedWriter(new FileWriter(f));
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
    
	
	try {
		connection=cb.getConnection();
		paret=connection.prepareStatement(sql);
		rs=paret.executeQuery();
		//行数
		int col = rs.getMetaData().getColumnCount();
		
	 long t1=System.currentTimeMillis(); //获取开始运行的时间	
		while(rs.next())
		{
			
			for (int i = 1; i <= col; i++)
			{	
                System.out.print(rs.getString(i) + "\t");
                sb.append(rs.getString(i));          
			}
			sb.append("\r\n").append("--------------------------").append("\r\n");
			bw.write(sb.toString());
			System.out.println();
			
			//清空字符串
		    sb.setLength(0);
		}
		
		 long t2=System.currentTimeMillis(); //获取时间
   	     System.out.println("写文件的时间为： "+(t2-t1)+"ms");
		
		//关闭所有的流
		bw.close();
		paret.close();
		connection.close();
				
	}catch(SQLException | IOException e){
		e.printStackTrace();
	}
		
}

    
}   

