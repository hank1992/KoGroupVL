package vl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.math.BigDecimal;

public class VL {

	public static void main(String[] args) {
		FileReader fr= null;
		BufferedReader br= null;
        FileWriter fw = null;
        BufferedWriter bw = null;
		
		try {
			fr= new FileReader("row_HIV viral load.txt"); //Filename here
			br= new BufferedReader(fr);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("FileNotFoundException");
		}

		String data;
		int count= 0;

		try {
			while ((data= br.readLine()) != null) {
				count++;
				if (count > 1) {
					Patient patient= new Patient();
					//System.out.println(data);
					patient.chartNo=data.substring(0,9).trim();
                    //System.out.println(patient.chartNo);
					patient.id=data.substring(11, 23).trim();
                    //System.out.println(patient.id);
					String dateString=data.substring(30, 42).trim();
                    //System.out.println(dateString);
					patient.labDate=Date.valueOf(dateString);
                    //System.out.println(patient.labDate.toString());
                    
                    //檢驗結果
                    if(data.contains("not detected")){
						patient.result="Not detected";
                    }else if(data.contains("Not Detected")){
						patient.result="Not detected";
					}else if(data.contains("Not detected")){
						patient.result="Not detected";
					}else if(data.contains("<20")){
						patient.result="<20";
					}else if(data.contains("<2.00E1")){
						patient.result="<20";
					}else if(data.contains("> 1.0E7")){
						patient.result=">10000000";
					}else if(data.contains("負荷量):")){
						int i1=data.indexOf("負荷量):");
						data=data.substring(i1+6);
						if(data.substring(0,20).contains("HIV-1")){
							int i2=data.indexOf("HIV-1");
							data=data.substring(0,i2).trim();
							BigDecimal bd=new BigDecimal(data);
							patient.result=bd.toPlainString();
							//System.out.println(patient.result);
						}else if(data.substring(0,20).contains("copies")){
							int i3=data.indexOf("copies");
							data=data.substring(0,i3).trim();
							BigDecimal bd2=new BigDecimal(data);
							patient.result=bd2.toPlainString();
							//System.out.println(patient.result);
						}else if(data.substring(0,20).contains("IU")){
							int i4=data.indexOf("IU");
							data=data.substring(0,i4).trim();
							BigDecimal bd3=new BigDecimal(data);
							patient.result=bd3.toPlainString();
							//System.out.println(patient.result);
						}else{
							System.out.println("不含HIV-1, copies, IU: "+data);
						}
					}else{
						System.out.println("不含not detected, Not Detected, Not detected, <20, <2.00E1, > 1.0E7, 負荷量): "+data);
					}
					
					//System.out.println(patient.chartNo+"\t"+patient.id+"\t"+patient.labDate.toString()+"\t"+patient.result);
                    fw = new FileWriter("VL_Output.txt", true);
                    bw = new BufferedWriter(fw);
                    bw.write(patient.chartNo + "\t" + patient.id+"\t"+patient.labDate.toString() + "\t" + patient.result);
                    bw.newLine();
                    bw.close();
				}
			}	
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException");
		}
	}
}
