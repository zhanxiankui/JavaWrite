
public class Test {

	public static void main(String[] args) {
		
     
		
		String fileName="src/stu.txt";
		
		ReadTxt txt=new ReadTxt();
		
		txt.readFile(fileName);
		
		ConnDb  cb=new ConnDb();
//		cb.getConnection();

		txt.selectDataWrite();
		
	

	}

}
