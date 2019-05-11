package way2smskeyword;


import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Runnerclass 
{

	public static void main(String[] args) throws Exception
	{
		//connect to excel file
		File f=new File("way2smstestdata.xls");
		Workbook rwb=Workbook.getWorkbook(f);//0 for sheet1
		Sheet rsh1=rwb.getSheet(0);
		int nour1=rsh1.getRows();
		int nouc1=rsh1.getColumns();
		Sheet rsh2=rwb.getSheet(1);
		int nour2=rsh2.getRows();
		int nouc2=rsh2.getColumns();
		//open same excel file for writing
		WritableWorkbook wwb=Workbook.createWorkbook(f,rwb);
		WritableSheet wsh1=wwb.getSheet(0);
		WritableSheet wsh2=wwb.getSheet(1);
		WritableCellFormat cf=new WritableCellFormat();
		cf.setAlignment(Alignment.JUSTIFY);
		cf.setWrap(true);
		//Set name to result column in sheet2
		Date dt=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		String cname=sf.format(dt);
		//set name to result column in sheet1
		Label l1=new Label(nouc1,0,cname,cf);
		wsh1.addCell(l1);
		//set name to result column in sheet2
		Label l2=new Label(nouc2,0,cname,cf);
		wsh2.addCell(l2);
		//create object to method class
		Methodsclass ms=new Methodsclass();
		//collect methods info using methods class object
		Method m[]=ms.getClass().getMethods();
		//keyword driven
		try
		{
			//calling methods one after other
			//1st row(index is 0) have names of col in sheet1
			for(int i=1;i<nour1;i++)
			{
				int flag=0;
				//get tid and mode from sheet1
				String tid=rsh1.getCell(0,i).getContents();
				String mode=rsh1.getCell(2,i).getContents();
				if(mode.equalsIgnoreCase("yes"))
				{
					//1st row(index 0)have names of cols in sheet2
					//comparing stepid
					for(int j=1;j<nour2;j++)
					{
						String sid=rsh2.getCell(0,j).getContents();
						if(tid.equalsIgnoreCase(sid))
						{
							//take step details from sheet2
							String mn=rsh2.getCell(2,j).getContents();
							String l=rsh2.getCell(3,j).getContents();
							String d=rsh2.getCell(4,j).getContents();
							String c=rsh2.getCell(5,j).getContents();
							System.out.println(mn+" "+l+" "+d+" "+c);
							for(int k=0;k<m.length;k++)
							{
								//methods
								if(m[k].getName().equals(mn))
								{
									String r=(String) m[k].invoke(ms,l,d,c);
									Label lb=new Label(nouc2,j,r,cf);
									wsh2.addCell(lb);
									if(r.equalsIgnoreCase("unknown browser"))
									{
										wwb.write();
										wwb.close();
										rwb.close();
										System.exit(0);//stop run
									}
									if(r.contains("Failed")||r.contains("failed")||r.contains("interrupted"))
									{
										flag=1;
									}
									break;//terminate from loop k
								}//if closing for methods
							}//for k closing
							
						}// if closing for stepid
						else
						{
							break;
						}
					}//for j closing for stepid
						if(flag==0)
						{
							Label l=new Label(nouc1,i,"passed",cf);
							wsh1.addCell(l);
						}
						else
						{
							Label l=new Label(nouc1,i,"failed",cf);
							wsh1.addCell(l);
						}
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//save and close excel
		wwb.write();
		wwb.close();
		rwb.close();
	}

}
