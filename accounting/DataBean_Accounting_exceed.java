package com.hlpl.hazira.fms7.accounting;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.hlpl.hazira.fms7.util.RuntimeConf;
public class DataBean_Accounting_exceed {
	private Statement stmt = null;
	private Statement stmt1 = null;
	private Statement stmt2 = null;
	private Statement stmt3 = null;
	private Statement stmt4 = null;
	private Statement stmt5 = null;
	private Statement stmt6 = null;
	private Connection conn = null;
	
	private ResultSet rset = null;
	private ResultSet rset1 = null;
	private ResultSet rset2 = null;
	private ResultSet rset3 = null;
	private ResultSet rset4 = null;
	private ResultSet rset5 = null;
	private ResultSet rset6 = null;
	int tax_cform=15;
	double tds_limit_amt = 5000000; //Hiren_20210624
	Vector tax_diff_val=new Vector();
	private String callFlag = ""; 
	String to_year = "";
	String methodName = "";
	NumberFormat nf = new DecimalFormat("###########0.00");
	NumberFormat nf6 = new DecimalFormat("############.##");
	NumberFormat nf61 = new DecimalFormat("###########.00");
	NumberFormat nf1 = new DecimalFormat("###########0");
	NumberFormat nf2 = new DecimalFormat("#########0.0000"); //For Currency Purpose ... Defined By Samik Shah On 1st June, 2010 ...
	NumberFormat nf3 = new DecimalFormat("###,###,###,##0.00");
	NumberFormat nf4 = new DecimalFormat("############0.000");
//	NumberFormat nf5 = new DecimalFormat("###,###,###,##0");
	NumberFormat nf5 = new DecimalFormat("###########0");
	String queryString = "";
	String queryString1 = "";
	String queryString2 = "";
	String queryString3 = "";
	String queryString4 = "";
	String queryString5 = "";
	String queryString6 = "";
	public String invstatus="";		//BK20160206
	public String segment="";		//BK20160206
	String month = "";
	String year = "";
	String to_month = "";
	String to_mon = "";
	String mon = "";
	String activity="";
	String from_dt = "";
	public String For1 = "";
	public String contract_type   	="";
	public String hlpl_inv_seq_no="";
	public String tax_struct_cd="";
	public String period_start_dt="";
	public String gross_amt_inr="";
	Vector VADJUST_FLAG_SN=new Vector();
	Vector VCFORM_FLAG=new Vector();
	public String customer_cd="";
	String to_dt = "";
	Vector Vmonth=new Vector();
	Vector Vcustomer_cd=new Vector();
	Vector Vcustomer_abbr=new Vector();
	Vector Vcont_typ=new Vector();
	Vector Vcustomer_name=new Vector();
	Vector Vhlplinvseqno=new Vector();
	Vector Vfinancial_year=new Vector();
	Vector Vdiff_tcs = new Vector();
	Vector Vdiff_tcs_flg = new Vector();
	Vector Vinv_dt=new Vector();
	Vector Vdue_dt=new Vector();
	Vector Vsales_value=new Vector();
	Vector Vtax_nm=new Vector();
	Vector Vtax_cd=new Vector();
	Vector Vinv_value=new Vector();
	Vector Vcont_type=new Vector();
	Vector Vxml_gen_flag=new Vector();
	Vector Vhlpl_inv_seq=new Vector();
	Vector Vtax_str_cd=new Vector();
	Vector Vtds_app_flag = new Vector(); 
	Vector Vtds_app_amt = new Vector();
	String tds_app_amt = "0";
	Vector tax_code=new Vector();
	Vector Vtax_on=new Vector();
	Vector tax_amount=new Vector();
	Vector tax_amount_usd=new Vector();
	
	Vector Vinvseqno=new Vector();		//BK20160208
	Vector Vtaxamtinr=new Vector();
	Vector Vinvamtinr=new Vector();
	Vector VADJUST_AMT_SN=new Vector();
	Vector VADJUST_balc_SN=new Vector();
	Vector VADJUST_CUR_SN=new Vector();
	Vector Vtax_code=new Vector();	//BK20160209
	Vector Vtax_amt=new Vector();
	Vector Vtaxnm=new Vector();
	Vector Vtaxcnt=new Vector();
	String allInvoiceCount="";
	String vat_code = "0";
	String cst_code = "0";
	String addl_code = "0";
	String st_code = "0";
	String sbc_code = "0";
	String ecs_code = "0";
	String hecs_code = "0";
	String zvat_code = "0";
	String igst_code = "0";
	String sgst_code = "0";
	String cgst_code = "0";
	String zgst_code = "0";
	Vector Vtcs_amt = new Vector();
	Vector flag_inv = new Vector();
	Vector VPAY_NEW_INV_SEQ_NO = new Vector();
	Vector Vdrcrcriteria=new Vector();
	Vector Vdrcrflag=new Vector();
	Vector Vgross_trans_inr = new Vector();
	Vector Vtds_tax_percent=new Vector();
	Vector Vtds_tax_amt=new Vector();
	Vector VAgreement_no = new Vector();
	Vector VAgreement_rev_no = new Vector();
	Vector Vgross_trans=new Vector();
	Vector Vcont_no=new Vector();
	Vector Vcont_rev_no=new Vector();
	Vector VPAY_FLAG = new Vector();
	Vector Vsup_state_cd = new Vector(); //Hiren_20210703
	//BK20160217
	Vector Vpay_actual_recv_amt=new Vector();
	Vector Vpay_recv_dt=new Vector();
	Vector Vpay_remark=new Vector();
	Vector Vpay_short_recv_amt=new Vector();
	Vector Vpayflag=new Vector();
	String emp_cd="";
	String update_flag="";
	
	Vector Vpay_update_dt=new Vector();
	Vector Vpay_update_cnt=new Vector();
	
	String logged_on_user_emailid="";
	String formname="",formcd="";
	Vector Vtottax=new Vector();
	
	//FGSA_NO,FGSA_REV_NO,SN_REV_NO,PLANT_SEQ_NO,PERIOD_START_DT,PERIOD_END_DT,EXCHG_RATE_CD,
	//EXCHG_RATE_TYPE,CUST_INV_SEQ_NO,APPROVED_FLAG
	public Vector Agreement_base= new Vector();
	public Vector trans_charges= new Vector();
	public Vector trans_gross_inr= new Vector();
	double tot_trans_grossinr=0.0;
	String agr_base="";
	String tcs_nm="";
	String tcs_cd="";
	String fact="";
	String tcs_sht_nm="";
	String tcs_fact="";
	Vector inv_flag=new Vector();
	Vector Vfgsa_no=new Vector();
	Vector Vfgsa_rev_no=new Vector();
	Vector Vsn_no=new Vector();
	Vector Vsn_rev_no=new Vector();
	Vector Vplant_seq_no=new Vector();
	Vector Vplant_seq_nm=new Vector();
	Vector Vperiod_st_dt=new Vector();
	Vector Vperiod_end_dt=new Vector();
	Vector Vexchg_rate_cd=new Vector();
	Vector Vexchg_rate_type=new Vector();
	Vector Vcust_inv_seq_no=new Vector();
	Vector Vapproved_flag=new Vector();
	Vector VAgreement_base=new Vector();
	Vector Vinv_adj_flag=new Vector();
	Vector Vtax_adj_flag=new Vector();
	String btnFlag="";
	Vector Vinv_gen_by_cd=new Vector();
	Vector Vchecked_by_cd=new Vector();
	Vector Vapproved_by_cd=new Vector();
	
	Vector Vinv_gen_by_emailid=new Vector();
	Vector Vchecked_by_emailid=new Vector();
	Vector Vapproved_by_emailid=new Vector();
	
	Vector Vinv_gen_by_nm=new Vector();
	Vector Vchecked_by_nm=new Vector();
	Vector Vapproved_by_nm=new Vector();
	
	Vector Vpdf_inv_dtl=new Vector();
	Vector Vprint_by_cd=new Vector();
	Vector Vprint_by_emailid=new Vector();
	Vector Vprint_by_nm=new Vector();
	Vector Vtds_per=new Vector();
	public void init()
	{
	    try
	    {
	    	Context initContext = new InitialContext();
	    	if(initContext == null)
	    	{
	    		throw new Exception("Boom - No Context");
	    	}
		  
	    	Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    	DataSource ds = (DataSource)envContext.lookup(RuntimeConf.security_database);
	    	if(ds != null) 
	    	{
	    		conn = ds.getConnection();       
	    		if(conn != null)  
	    		{
	    			stmt = conn.createStatement();
	    			stmt1 = conn.createStatement();
	    			stmt2 = conn.createStatement();
	    			stmt3 = conn.createStatement();
	    			stmt4 = conn.createStatement();
	    			stmt5 = conn.createStatement();
	    			stmt6 = conn.createStatement();
	    			
	    			if(callFlag.equalsIgnoreCase("fetchInvoiceDetails"))
	    			{
	    				CREATE_COLUMN();
	    				Read_All_Roundoff();
	    				fetch_Invoice_Details();	//BK20160309
	    			}
	    			conn.close();
	    			conn = null;
	    		}
	    	}
	    }
	    catch(Exception e)
	    {
	    	////System.out.println("Exception In : ("+databeanName+") - (init()): "+e.getMessage());
	    	e.printStackTrace();
	    }
	    finally
	    {
	    	if(rset != null)
	    	{
				try
				{
					rset.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("rset is not close "+e);
				}
			}
	    	if(rset1 != null)
	    	{
				try
				{
					rset1.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("rset1 is not close "+e);
				}
			}
	    	if(rset2 != null)
	    	{
				try
				{
					rset2.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("rset2 is not close "+e);
				}
			}
	    	if(rset3 != null)
	    	{
				try
				{
					rset3.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("rset3 is not close "+e);
				}
			}
	    	if(rset4 != null)
	    	{
				try
				{
					rset4.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("rset4 is not close "+e);
				}
			}
	    	if(rset5 != null)
	    	{
				try
				{
					rset5.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("rset5 is not close "+e);
				}
			}
	    	if(rset6 != null)
	    	{
				try
				{
					rset6.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("rset6 is not close "+e);
				}
			}
			if(stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("stmt is not close "+e);
				}
			}
			if(stmt1 != null)
			{
				try
				{
					stmt1.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("stmt1 is not close "+e);
				}
			}
			if(stmt2 != null)
			{
				try
				{
					stmt2.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("stmt2 is not close "+e);
				}
			}
			if(stmt3 != null)
			{
				try
				{
					stmt3.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("stmt2 is not close "+e);
				}
				stmt3 = null;
			}
			if(stmt4 != null)
			{
				try
				{
					stmt4.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("stmt2 is not close "+e);
				}
				stmt4 = null;
			}
			if(stmt5 != null)
			{
				try
				{
					stmt5.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("stmt5 is not close "+e);
				}
			}
			if(stmt6 != null)
			{
				try
				{
					stmt6.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("stmt6 is not close "+e);
				}
			}
			if(conn != null)
			{
				try
				{
					conn.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					////System.out.println("conn is not close "+e);
				}
			}
	    }
	}
	public HttpServletRequest request = null;
	int Inv_roundoff;
	public void Read_All_Roundoff(){
		try {

			//stmt = dbcon.createStatement();
    	//stmt1 = dbcon.createStatement();
				String strline = "";
			    
			    File fsetup=new File(request.getRealPath("/accounting/Inv_Roundoff.txt"));
				String mail_list_path=fsetup.getAbsolutePath();
				
				//System.out.println("mail_list_path--"+mail_list_path);
				FileInputStream f1 = new FileInputStream(mail_list_path);
				DataInputStream in = new DataInputStream(f1);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				while((strline = br.readLine())!=null)
				{
					if(strline.startsWith("Invoice"))
					{
						String  tmp[]=strline.split("Invoice:");
						Inv_roundoff = Integer.parseInt(tmp[1]);
					}
				}
					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private String checkforTDSApplicable(String invoice_date,String cust_cd,String fin_yr,String hlpl_inv_seq_no,String commodity,String inv_type,String sup_state_cd)throws SQLException {

		
		String prev_fin_Yr = "";
		String tds_app_flag = "N";
		tds_app_amt = "0";
		try {
			/*String prev_finYr = "select  "
					+ " (TO_NUMBER(TO_CHAR(ADD_MONTHS (to_date('"+invoice_date+"','dd/mm/yyyy'), -3),'YYYY'))-1) "
					+ " || '-' || "
					+ " (TO_NUMBER(TO_CHAR(ADD_MONTHS (to_date('"+invoice_date+"','dd/mm/yyyy'), 9),'YYYY'))-1) prev_fin_yr FROM DUAL";
//			System.out.println("prev_finYr----"+prev_finYr);
			rset = stmt.executeQuery(prev_finYr);
			if(rset.next()) {
				prev_fin_Yr = rset.getString(1)==null?"":rset.getString(1);
			}
			
			String TDSFlagSql = "select nvl(TURNOVER_FLAG,'N') from FMS7_CUSTOMER_TURNOVER_DTL"
					+ " where CUSTOMER_CD= '"+cust_cd+"' "
					+ " and FINANCIAL_YEAR = '"+prev_fin_Yr+"' "
					+ " and TURNOVER_CD = '1'"
					+ " and TURNOVER_FLAG = 'Y' "; 
//			System.out.println("TDSFlagSql-----"+TDSFlagSql);
			rset = stmt.executeQuery(TDSFlagSql);
			if(rset.next()) {
				tds_app_flag = rset.getString(1)==null?"N":rset.getString(1);
			}*/
		
			/*checking transaction amount for TDS */
			String tdsCntSql = "select nvl(INVOICE_GROSS_AMT,0) from FMS7_INVOICE_TDS_DTL WHERE "
					+ " CUSTOMER_CD='"+cust_cd+"'"
					+ " AND FINANCIAL_YEAR='"+fin_yr+"'"
					+ " AND HLPL_INV_SEQ_NO = '"+hlpl_inv_seq_no+"'"
					+ " AND COMMODITY_TYPE = '"+commodity+"'"
					+ " AND INVOICE_TYPE = '"+inv_type+"' "
					+ " AND CONTRACT_TYPE in ('S','L') "
					+ " AND FLAG='Y'"
					+ " AND SUP_STATE_CODE = '"+sup_state_cd+"' ";
			//System.out.println("tdsCntSql----"+tdsCntSql);
			rset3 = stmt3.executeQuery(tdsCntSql);
			if(rset3.next()) {
				tds_app_amt = rset3.getString(1)==null?"0":rset3.getString(1);
				tds_app_flag = "Y";
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tds_app_flag;
	
	}
	public void fetch_DLNG_Invoice_Details()throws SQLException,IOException {

		
		String queryString123 = "";
		String from_dt="";
		String to_dt="";
		if(btnFlag.equalsIgnoreCase("Y"))
		{
			from_dt="01/"+month+"/"+year;
			
			queryString="Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
			////System.out.println("Last Date Of The Selected Month = "+queryString);
			rset=stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_dt=rset.getString(1)==null?"0":rset.getString(1);
			}
			////System.out.println("From date "+from_dt+" To Date= "+to_dt);
			
			queryString="Select To_char(to_date('"+from_dt+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("From Selected Month = "+queryString);
			rset=stmt.executeQuery(queryString);			
			if(rset.next())
			{
				mon=rset.getString(1)==null?"0":rset.getString(1);
			}
			
			queryString="Select To_char(to_date('"+to_dt+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("To The Selected Month = "+queryString);
			rset=stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_mon=rset.getString(1)==null?"0":rset.getString(1);
			}
			btnFlag="Y";
		}
		else
		{
			
			//RG20191031 segment="LTCORA_CN";
			if(segment.equalsIgnoreCase("LTCORA_CN")){
				segment="LTCORA_CN";
			}else if(segment.equalsIgnoreCase("SALES")){
				segment="SALES";
			}else if(segment.equalsIgnoreCase("REGAS")){
				segment="REGAS";
			}else if(segment.equalsIgnoreCase("LOA")){
				segment="LOA";
			}else if(segment.equalsIgnoreCase("SDLNG")){
				segment="SDLNG";
			}else if(segment.equalsIgnoreCase("LDLNG")){
				segment="LDLNG";
			}else if(segment.equalsIgnoreCase("DLNG_SER")){
				segment="DLNG_SER";
			}else if(segment.equalsIgnoreCase("DLNG_LM")){
				segment="DLNG_LM";
			}else if(segment.equalsIgnoreCase("DLNG_Interest"))
			{
				segment="DLNG_Interest";
			}
			btnFlag="Y";
			
			from_dt="01/"+month+"/"+year;
			
			queryString="Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
			////System.out.println("Last Date Of The Selected Month = "+queryString);
			rset=stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_dt=rset.getString(1)==null?"0":rset.getString(1);
			}
			////System.out.println("From date "+from_dt+" To Date= "+to_dt);
			
			queryString="Select To_char(to_date('"+from_dt+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("From Selected Month = "+queryString);
			rset=stmt.executeQuery(queryString);			
			if(rset.next())
			{
				mon=rset.getString(1)==null?"0":rset.getString(1);
			}
			
			queryString="Select To_char(to_date('"+to_dt+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("To The Selected Month = "+queryString);
			rset=stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_mon=rset.getString(1)==null?"0":rset.getString(1);
			}
			
		}
		
		String cnt_typ="";
		
		if(segment.equalsIgnoreCase("SALES"))
		{
			contract_type="S";
			cnt_typ="contract_type in ('S')"; 
		}
		else if(segment.equalsIgnoreCase("REGAS"))
		{
			contract_type="R";
			cnt_typ="contract_type in ('R')";
		}
		else if(segment.equalsIgnoreCase("LTCORA_CN"))
		{
			contract_type="C";
			cnt_typ="contract_type in ('C','T','M','B','E')";
		} 
		else if(segment.equalsIgnoreCase("LOA"))
		{
			contract_type="L";
			cnt_typ="contract_type in ('L')";
		}
		else if(segment.equalsIgnoreCase("Interest"))
		{
			contract_type="I";
			cnt_typ="contract_type in ('I')";
		}
		else if(segment.equalsIgnoreCase("DLNG_Interest"))
		{
			contract_type="I";
			cnt_typ="contract_type in ('I')";
		}
		else if(segment.equalsIgnoreCase("SDLNG"))
		{
			contract_type="S";
			cnt_typ="contract_type in ('S')";
		}else if(segment.equalsIgnoreCase("LDLNG"))
		{
			contract_type="L";
			cnt_typ="contract_type in ('L')";
		}
		else if(segment.equalsIgnoreCase("DLNG_SER"))
		{
			contract_type="V";
			cnt_typ="contract_type in ('V')";
		}
		else if(segment.equalsIgnoreCase("DLNG_LM"))
		{
			contract_type="M";
			cnt_typ="contract_type in ('M')";
		}
		Vector Vadvflag=new Vector();
		Vector Vmapid=new Vector();
		
		String tax_cd="";
		String tax_on="";
		String tax_nm="";
		
		double tax_amt=0;
		double adj_tax_inr=0;
		String Adj_Tax_amt="0";
		//String amt="";
		String hlpl_inv_no_disp="";
		String xml_gen_flag="";
		String approval_dt="";
		String tax_adj_amt="";
		String taxadvflag="";
		String inv_adj_amt="";
		String advflag="";
		String sn_no="";
		String taxvalue="";
		String gross_amt_inr="";
		String gross_amt_trans="";
		String invamtinr="";
		String fyr="";
		double total_amt_inr=0;
		String tax_str_cd="";
		
		int cnt=0;
		String pdf_inv_dtl="";
		String print_by_ori="";
		String print_by_dup="";
		String print_by_tri="";
		double short_recv=0;
	try {
		if(segment.equalsIgnoreCase("SDLNG") || segment.equalsIgnoreCase("LDLNG") || segment.equalsIgnoreCase("DLNG_Interest")){
		if(!customer_cd.equalsIgnoreCase(""))
		{
			if(invstatus.equalsIgnoreCase("PAID"))
			{
				queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
							 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
							 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,PAY_RECV_AMT,"
							 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,CHECKED_BY,APPROVED_BY"
							 + ",PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
							 //RG20191108 Commented for getting mapping_id + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
							 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,SUP_STATE_CODE,TDS_TAX_PERCENT,TDS_TAX_AMT " +
							// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "FROM DLNG_INVOICE_MST WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') "
							 		//+ "AND APPROVED_FLAG='Y' AND CHECKED_FLAG='Y' "
							 		+ "AND PAY_RECV_AMT IS NOT NULL AND "
							 + "PAY_INSERT_BY IS NOT NULL AND (PDF_INV_DTL IS NOT NULL) "
							 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
			}
			else
			{
				queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
						 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,FINANCIAL_YEAR,TAX_STRUCT_CD,"
						 + "TAX_AMT_INR,INV_AMT_INR,SN_NO,EMP_CD,CHECKED_BY,APPROVED_BY,PDF_INV_DTL,PRINT_BY_ORI,"
						 + "PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,"
						//RG20191108 Commented for getting mapping_id + "TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
						 + "TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,PAY_RECV_AMT,SUP_STATE_CODE,PAY_REMARK,TDS_TAX_PERCENT,TDS_TAX_AMT  " +
						// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
						 "FROM DLNG_INVOICE_MST WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
						 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') "
						 		//+ "AND APPROVED_FLAG='Y' AND CHECKED_FLAG='Y' "
				 		//+ "AND PAY_RECV_AMT IS NULL AND PAY_INSERT_BY IS NULL "
				 		+ "AND (PDF_INV_DTL IS NOT NULL) "
						 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
			}
		}
		else
		{
			if(invstatus.equalsIgnoreCase("PAID"))
			{
				queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
							 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
							 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,PAY_RECV_AMT,"
							 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,CHECKED_BY,APPROVED_BY,"
							 + "PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
							 //+ "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
							 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,SUP_STATE_CODE,TDS_TAX_PERCENT,TDS_TAX_AMT  " +
							// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "FROM DLNG_INVOICE_MST WHERE "+cnt_typ+" AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') "
							 		//+ "AND APPROVED_FLAG='Y' AND CHECKED_FLAG='Y' "
							 		+ "AND PAY_RECV_AMT IS NOT NULL AND "
							 + "PAY_INSERT_BY IS NOT NULL AND (PDF_INV_DTL IS NOT NULL) "
							 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
			}
			else
			{
				queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
						 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
						 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,EMP_CD,CHECKED_BY,"
						 + "APPROVED_BY,PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
						 //+ "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
						 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,PAY_RECV_AMT,SUP_STATE_CODE,PAY_REMARK ,TDS_TAX_PERCENT,TDS_TAX_AMT " +
						// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
						 "FROM DLNG_INVOICE_MST WHERE "+cnt_typ+" AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
						 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') "
						 		//+ "AND APPROVED_FLAG='Y' AND CHECKED_FLAG='Y' "
						 		//+ "AND PAY_RECV_AMT IS NULL AND PAY_INSERT_BY IS NULL "
						 + "AND (PDF_INV_DTL IS NOT NULL) "
						 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
			}
		}
		
		//System.out.println("DLNG===fetch Invoice queryString123===: "+queryString123);
		rset1=stmt1.executeQuery(queryString123);
		while(rset1.next())
		{
			Vgross_trans_inr.add("");
			Vdrcrcriteria.add("");
			Vdrcrflag.add("");
			if(invstatus.equalsIgnoreCase("PAID")){
				Vtds_tax_percent.add(rset1.getString(32)==null?"":rset1.getString(32));
				Vtds_tax_amt.add(rset1.getString(33)==null?"":rset1.getString(33));
			}else{
				Vtds_tax_percent.add(rset1.getString(31)==null?"":rset1.getString(31));
				Vtds_tax_amt.add(rset1.getString(32)==null?"":rset1.getString(32));
			}
			String payamt="",inv_take_flag="Y",sup_state_cd = "";String short_recv_="0";
			if(invstatus.equalsIgnoreCase("PAID"))
			{
				payamt=rset1.getString(14)==null?"":rset1.getString(14);
				inv_take_flag="Y";
				//Vsup_state_cd.add(rset1.getString(31)==null?"":rset1.getString(31));
				//sup_state_cd = rset1.getString(31)==null?"":rset1.getString(31);
			}else{
				payamt=rset1.getString(28)==null?"":rset1.getString(28);
				//Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
				//sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
//				System.out.println("payamt---"+payamt);
				if(payamt.equals("")){
					inv_take_flag="Y";
				}else{
					if(rset1.getString(21)!=null)
					{
						double tdsper=Double.parseDouble(rset1.getString(21));
						double salesval=Double.parseDouble(rset1.getString(5));
						double tdsval=(tdsper*salesval)/100;
						double invval=Double.parseDouble(rset1.getString(6));
						//double netrecv=invval-tdsval;
						String netrecv="";
						if(rset1.getString(31)!=null)
						{
							netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(32)));
						}else{
							netrecv=nf6.format(invval-tdsval);
						}
						double actval=Double.parseDouble(rset1.getString(28));
						short_recv_=nf6.format(Double.parseDouble(netrecv)-actval);
//						System.out.println("short_recv_-in else--"+short_recv_);
					}
					else
					{
						//System.out.println("short_recv_-in else--"+short_recv_);
						short_recv_=nf6.format(Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt));
//						System.out.println("short_recv_-in else--"+short_recv_);
					}
//					System.out.println("short_recv_---"+short_recv_);
					
					if(Double.parseDouble(short_recv_) <= Double.parseDouble(Inv_roundoff+"")){
						
						//		System.out.println("in thisssssssss--->>"+rset1.getString(3)+"<<-->>"+Inv_roundoff+"<<---->>"+Double.parseDouble(short_recv_));
								inv_take_flag="N";
							}
							else{
								inv_take_flag="Y";
							}
				}
			}
		if(inv_take_flag.equalsIgnoreCase("Y")){
			cnt++;
			String temp_flag = "";
			if(invstatus.equalsIgnoreCase("PAID"))
			{
				VPAY_NEW_INV_SEQ_NO.add(rset1.getString(28)==null?"":rset1.getString(28));
				VPAY_FLAG.add(rset1.getString(29)==null?"Y":rset1.getString(29));
				temp_flag = rset1.getString(29)==null?"Y":rset1.getString(29);
				Vsup_state_cd.add(rset1.getString(31)==null?"":rset1.getString(31));
				sup_state_cd = rset1.getString(31)==null?"":rset1.getString(31);
			} else {
				VPAY_NEW_INV_SEQ_NO.add(rset1.getString(25)==null?"":rset1.getString(25));
				VPAY_FLAG.add(rset1.getString(26)==null?"Y":rset1.getString(26));
				temp_flag = rset1.getString(26)==null?"Y":rset1.getString(26);
				Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
				sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
			}
			flag_inv.add("I");
				
			//for tcs
			String query="select tcs_amt from fms7_invoice_tcs_dtl where hlpl_inv_seq_no='"+rset1.getString(3)+"' and contract_type='"+rset1.getString(8)+"' and "
					+ "financial_year='"+rset1.getString(9)+"' and customer_cd='"+rset1.getString(2)+"' AND INVOICE_TYPE='SALES' AND FLAG='Y' and COMMODITY_TYPE='DLNG'"
							+ " and sup_state_code='"+sup_state_cd+"' ";
			//System.out.println("query---"+query);
			rset=stmt.executeQuery(query);
			if(rset.next()){
				Vtcs_amt.add(rset.getString(1)==null?"":rset.getString(1));
			}else{
				Vtcs_amt.add("");
			}
			
			//Hiren_20210621 for TDS calc.
			String tds_app_flag = "N";
			tds_app_amt = "0";
			if(rset1.getString(8).equals("S") || rset1.getString(8).equals("L")) {
				tds_app_flag = checkforTDSApplicable(rset1.getString(4),rset1.getString(2),rset1.getString(9),rset1.getString(3),"DLNG","SALES",sup_state_cd);
				
			}
			if(segment.equalsIgnoreCase("LTCORA_CN")) {
				Vtds_app_flag.add("Y");
				Vtds_app_amt.add(rset1.getString(5)==null?"0":rset1.getString(5));
			}else {
				Vtds_app_flag.add(tds_app_flag);
				Vtds_app_amt.add(tds_app_amt);
			}
			////////
			//
			
			////System.out.println("===JAVA===1");
			if(rset1.getString(1)!=null)
			{
				Vmonth.add(rset1.getString(1));
			}
			else
			{
				Vmonth.add("-");
			}
			
			if(rset1.getString(2)!=null)
			{
				Vcustomer_cd.add(rset1.getString(2));
			}
			else
			{
				Vcustomer_cd.add("-");
			}
			
			String queryString1 = "SELECT CUSTOMER_NAME,CUSTOMER_ABBR FROM FMS7_CUSTOMER_MST A WHERE CUSTOMER_CD='"+rset1.getString(2)+"'"
					+ " AND " +
				  "A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_MST B " +
				  "WHERE A.customer_cd=B.customer_cd AND " +
				  "B.eff_dt<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY') and CUSTOMER_CD='"+rset1.getString(2)+"')";
			rset=stmt.executeQuery(queryString1);
			while(rset.next())
			{
				if(rset.getString(2)!=null)
				{
					Vcustomer_abbr.add(rset.getString(2));
					Vcustomer_name.add(rset.getString(1));
				}
				else
				{
					Vcustomer_abbr.add("-");
					Vcustomer_name.add("-");
				}
			}
			
			
			hlpl_inv_seq_no=rset1.getString(3);
			if(rset1.getString(3)!=null)
			{
				Vhlplinvseqno.add(rset1.getString(3));
			}
			else
			{
				Vhlplinvseqno.add("-");
			}
			Vdiff_tcs.add("0");
			Vdiff_tcs_flg.add("N");
			
			String tmp_fgsa_no="",tmp_fgsa_rev_no="",tmp_sn_no="",tmp_sn_rev_no="",TMP_QTY="";
			
			String queryString456="SELECT FGSA_NO,FGSA_REV_NO,SN_REV_NO,PLANT_SEQ_NO,TO_CHAR(PERIOD_START_DT,'DD/MM/YYYY'),"
					+ "TO_CHAR(PERIOD_END_DT,'DD/MM/YYYY'),EXCHG_RATE_CD,EXCHG_RATE_TYPE,CUST_INV_SEQ_NO,APPROVED_FLAG,TOTAL_QTY " + 
					 "FROM DLNG_INVOICE_MST WHERE HLPL_INV_SEQ_NO='"+hlpl_inv_seq_no+"' "
					 + "AND FINANCIAL_YEAR='"+rset1.getString(9)+"' AND "
					 + "CONTRACT_TYPE='"+rset1.getString(8)+"' AND "
					 + "INVOICE_DT=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy') "
					 + "AND FLAG='"+temp_flag+"' and sup_state_code='"+sup_state_cd+"' ";
			rset=stmt.executeQuery(queryString456);
			//System.out.println("---queryString456---"+queryString456);
			if(rset.next())
			{
				if(rset.getString(1)!=null)
					Vfgsa_no.add(rset.getString(1));
				else
					Vfgsa_no.add("-");
				
				if(rset.getString(2)!=null)
					Vfgsa_rev_no.add(rset.getString(2));
				else
					Vfgsa_rev_no.add("-");
					
				if(rset.getString(3)!=null)
					Vsn_rev_no.add(rset.getString(3));
				else
					Vsn_rev_no.add("-");
				
				if(rset.getString(4)!=null)
					Vplant_seq_no.add(rset.getString(4));
				else
					Vplant_seq_no.add("-");
				
				tmp_fgsa_no=rset.getString(1)==null?"":rset.getString(1);
				tmp_fgsa_rev_no=rset.getString(2)==null?"":rset.getString(2);
				tmp_sn_no=rset1.getString(13)==null?"":rset1.getString(13);
				tmp_sn_rev_no=rset.getString(3)==null?"":rset.getString(3);
				TMP_QTY=rset.getString(11)==null?"":rset.getString(11);
				
				String q1="SELECT NVL(A.plant_name,' ') FROM FMS7_CUSTOMER_PLANT_DTL A " +
						   "WHERE A.customer_cd="+rset1.getString(2)+" AND A.seq_no="+rset.getString(4)+" " +
						   "AND A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_PLANT_DTL B " +
						   "WHERE A.customer_cd=B.customer_cd AND A.seq_no=B.seq_no " +
						   "AND B.eff_dt<=TO_DATE('"+rset.getString(5)+"','DD/MM/YYYY'))";
				
				//String q1="SELECT PLANT_NAME FROM FMS7_CUSTOMER_PLANT_DTL  WHERE CUSTOMER_CD='"+rset1.getString(2)+"' AND SEQ_NO='"+rset.getString(4)+"'";
				////System.out.println("---PLANT_NAME QUERY---"+q1);
				rset6=stmt6.executeQuery(q1);
				
				while(rset6.next())
				{
					if(rset6.getString(1)!=null)
						Vplant_seq_nm.add(rset6.getString(1));
					else
						Vplant_seq_nm.add("-");
					
					////System.out.println("---Vplant_seq_nm---:"+rset6.getString(1));
				}
				
				if(rset.getString(5)!=null)
					Vperiod_st_dt.add(rset.getString(5));
				else
					Vperiod_st_dt.add("-");
					
				if(rset.getString(6)!=null)
					Vperiod_end_dt.add(rset.getString(6));
				else
					Vperiod_end_dt.add("-");
				
				if(rset.getString(7)!=null)
					Vexchg_rate_cd.add(rset.getString(7));
				else
					Vexchg_rate_cd.add("-");
				
				if(rset.getString(8)!=null)
					Vexchg_rate_type.add(rset.getString(8));
				else
					Vexchg_rate_type.add("-");
				
				if(rset.getString(9)!=null)
					Vcust_inv_seq_no.add(rset.getString(9));
				else
					Vcust_inv_seq_no.add("-");
				
				if(rset.getString(10)!=null)
					Vapproved_flag.add(rset.getString(10));
				else
					Vapproved_flag.add("-");
				
				String queryString_agr_base="";
				if(rset1.getString(8).equals("S")){
					queryString_agr_base="SELECT FLSA_BASE FROM DLNG_FLSA_MST WHERE FLSA_NO='"+rset.getString(1)+"' AND REV_NO='"+rset.getString(2)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
					//System.out.println("queryString_agr_base--"+queryString_agr_base);
					rset2=stmt2.executeQuery(queryString_agr_base);
					if(rset2.next()){
						agr_base=rset2.getString(1)==null?"":rset2.getString(1);
					}
				}else if(rset1.getString(8).equals("L")){
					queryString_agr_base="SELECT TENDER_BASE FROM DLNG_TENDER_MST WHERE TENDER_NO='"+rset.getString(1)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
					//System.out.println("queryString_agr_base--"+queryString_agr_base);
					rset2=stmt2.executeQuery(queryString_agr_base);
					if(rset2.next()){
						agr_base=rset2.getString(1)==null?"":rset2.getString(1);
					}
				}
			}
			VAgreement_base.add(agr_base);
			if(rset1.getString(4)!=null)
			{
				Vinv_dt.add(rset1.getString(4));
			}
			else
			{
				Vinv_dt.add("-");
			}
			
			if(agr_base.equalsIgnoreCase("D")){

				String queryString_tr="";
				String transp_charges="";
				String temp_grossamt="";
				if(rset1.getString(8).equals("S")){
					queryString_tr="SELECT TRANSPORTATION_CHARGE FROM DLNG_SN_MST WHERE FLSA_NO='"+tmp_fgsa_no+"' AND FLSA_REV_NO='"+tmp_fgsa_rev_no+"' AND SN_NO='"+tmp_sn_no+"' AND SN_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
				}else if(rset1.getString(8).equals("L")){
					queryString_tr="SELECT TRANSPORTATION_CHARGE FROM DLNG_LOA_MST WHERE TENDER_NO='"+tmp_fgsa_no+"' AND LOA_NO='"+tmp_sn_no+"' AND LOA_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
					
				}
				rset2=stmt2.executeQuery(queryString_tr);
				//System.out.println("queryString--"+queryString_tr);
				if(rset2.next()){
					transp_charges=rset2.getString(1)==null?"":rset2.getString(1);
				}
				//System.out.println("---rset.getString(7)--"+rset1.getString(5)+"--transp_charges--"+transp_charges+"--TMP_QTY---"+TMP_QTY);
				if(!transp_charges.equals("")){
					temp_grossamt=""+(Double.parseDouble(rset1.getString(5)==null?"":rset1.getString(5))+Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
					Vsales_value.add(temp_grossamt);
					gross_amt_inr=temp_grossamt;
					//System.out.println("---temp====="+temp_grossamt);
				}else{
					Vsales_value.add(rset1.getString(5));
					gross_amt_inr=rset1.getString(5);
				}
				
			
			}else{
			
				if(rset1.getString(5)!=null)
				{
					Vsales_value.add(rset1.getString(5));
				}
				else
				{
					Vsales_value.add("-");
				}
					
				gross_amt_inr=rset1.getString(5);
			}
			Vgross_trans.add("");
			if(rset1.getString(6)!=null)
			{
				Vinv_value.add(rset1.getString(6));
			}
			else
			{
				Vinv_value.add("-");
			}
			
			if(rset1.getString(7)!=null)
			{
				Vdue_dt.add(rset1.getString(7));
			}
			else
			{
				Vdue_dt.add("-");
			}
			if(rset1.getString(8)!=null)
			{
				Vcont_type.add(rset1.getString(8));
			}
			else
			{
				Vcont_type.add("-");
			}
			
			if(rset1.getString(9)!=null)
			{
				Vfinancial_year.add(rset1.getString(9));
			}
			else
			{
				Vfinancial_year.add("-");
			}
			
			if(rset1.getString(10)!=null)
			{
				Vtax_str_cd.add(rset1.getString(10));
			}
			else
			{
				Vtax_str_cd.add("-");
			}
			
			if(rset1.getString(11)!=null)
			{
				Vtaxamtinr.add(rset1.getString(11));
			}
			else
			{
				Vtaxamtinr.add("-");
			}
			
			if(rset1.getString(12)!=null)
			{
				Vinvamtinr.add(rset1.getString(12));
			}
			else
			{
				Vinvamtinr.add("-");
			}
			
			invamtinr=rset1.getString(12);
			sn_no=rset1.getString(13);
			taxvalue=rset1.getString(11);
			fyr=rset1.getString(9);
			total_amt_inr=Double.parseDouble(rset1.getString(6));
			tax_str_cd=rset1.getString(10)==null?"":rset1.getString(10);
			
			Vsn_no.add(sn_no);
			
			//String invseq=contract_type+":"+financial_year+":"+hlpl_inv_seq_no+":%";
			String invseq=rset1.getString(8)+":"+rset1.getString(9)+":"+rset1.getString(3)+":"+rset1.getString(4);
			if(!invseq.equalsIgnoreCase(""))
			{
				Vinvseqno.add(invseq);
			}
			else
			{
				Vinvseqno.add("-");
			}
			
			////////tax_adj_amt////////
			
			String q1="select flag,amount from fms7_inv_compo_dtl where price_cd IN ('6','10','11','12','13') and inv_seq_no='"+invseq+"'";
			////System.out.println("JAVA check adjustmn query q1: "+q1);
			rset2=stmt2.executeQuery(q1);
			if(rset2.next())
			{}
			else
			{
				taxadvflag="N";
				tax_adj_amt="0";
				Vtax_adj_flag.add(taxadvflag);
			}
			
			////System.out.println("---JAVA here taxadvflag---: "+taxadvflag+"---tax_adj_amt---: "+tax_adj_amt);
			double balance_adjust_amt=0,tot_adjust_amt=0;
			//String mapping_id=""+rset1.getString(2)+"-"+tmp_fgsa_no+"-"+tmp_fgsa_rev_no+"-"+tmp_sn_no+"-"+tmp_sn_rev_no+"-"+rset1.getString(8);
			String mapping_id="%"+rset1.getString(2)+"-"+tmp_fgsa_no+"-%-"+tmp_sn_no+"-%-"+rset1.getString(8)+"%";
			queryString = "select price_rate,currency_cd,flag " +
					"FROM dlng_cont_price_dtl WHERE mapping_id like '"+mapping_id+"' AND " +
					"price_cd='1' AND FLAG='Y' and pay_type='AP' ";
//			System.out.println("Fetching flag of sn..."+queryString);
			rset2=stmt2.executeQuery(queryString);
			if(rset2.next())
			{
				VADJUST_FLAG_SN.add(rset2.getString(3)==null?"N":rset2.getString(3));
				tot_adjust_amt=rset2.getDouble(1);
				VADJUST_AMT_SN.add(rset2.getString(1)==null?"":rset2.getString(1));
				VADJUST_CUR_SN.add(rset2.getString(2));
				if(rset1.getString(8).equals("S")){
					queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
							+ "CONTRACT_NO='"+tmp_sn_no+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND AGR_REV_NO='"+rset.getString(2)+"' "
							+ "AND CONTRACT_TYPE='"+rset1.getString(8)+"' ";
//					System.out.println("Fetching flag of sn..."+queryString);
					
				}else{
					queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
							+ "CONTRACT_NO='"+tmp_sn_no+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' ";
//					System.out.println("Fetching flag of sn..."+queryString);
					
				}
				rset3=stmt3.executeQuery(queryString1);	
				if(rset3.next()){
					VCFORM_FLAG.add(rset3.getString(1)==null?"":rset3.getString(1));
				}else{
					VCFORM_FLAG.add("");
				}
			}else{
				VADJUST_FLAG_SN.add("");
				VADJUST_AMT_SN.add("");
				VADJUST_CUR_SN.add("");
				VCFORM_FLAG.add("");
			}
			queryString = "select sum(adjusted_amt) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
					+ "AND adjust_FLAG='Y' and commodity_type='DLNG' and contract_type='"+rset1.getString(8)+"' ";
		//System.out.println("Fetching flag of sn..."+queryString);
			rset2=stmt2.executeQuery(queryString);
			if(rset2.next())
			{
				balance_adjust_amt=tot_adjust_amt-rset2.getDouble(1);
			}else{
				balance_adjust_amt=tot_adjust_amt;
			}
			queryString = "select sum(hold_amount) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
					+ "AND commodity_type='DLNG' and contract_type='"+rset1.getString(8)+"' and release_flag='Y' ";
		//System.out.println("Fetching flag of sn..."+queryString);
			rset2=stmt2.executeQuery(queryString);
			if(rset2.next())
			{
				balance_adjust_amt+=rset2.getDouble(1);
			}
			
			VADJUST_balc_SN.add(nf6.format(balance_adjust_amt));
			/////HERE
			if(!payamt.equals("")){
				short_recv=0;
				if(invstatus.equalsIgnoreCase("PAID")){
					if(rset1.getString(24)!=null)
					{
//						System.out.println("short_recv-in if--"+short_recv);
						double tdsper=Double.parseDouble(rset1.getString(24));
						double salesval=Double.parseDouble(rset1.getString(5));
						String tdsval=  nf.format((tdsper*salesval)/100);
//						double invval=Double.parseDouble(rset1.getString(6));
//						double netrecv=invval-tdsval;
//						double actval=Double.parseDouble(payamt);
						//System.out.println(rset1.getString(6)+"**********"+payamt+"*********"+tdsval+"---rset1.getString(32)--"+rset1.getString(32));
						double netrecv=0;
						if(rset1.getString(32)!=null)
						{
							short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt) - Double.parseDouble(tdsval+"")-Double.parseDouble(rset1.getString(33));
						}else{
							short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt) - Double.parseDouble(tdsval+"");
						}
						//System.out.println("short_recv-in if--"+short_recv);
					}
					else
					{
						//System.out.println(rset1.getString(6)+"**********"+payamt+"****else*****");
						short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt);
						//System.out.println("short_recv-in else--"+short_recv);
					}
				}else{
					double tdsper=Double.parseDouble(rset1.getString(21)==null?"0":rset1.getString(21));
					double salesval=Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5));
					double tds_tax_amt=Double.parseDouble(rset1.getString(32)==null?"0":rset1.getString(32));
					String tdsval=  nf.format((tdsper*salesval)/100);
					short_recv=Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))-Double.parseDouble(payamt)-Double.parseDouble(tdsval+"")-Double.parseDouble(tds_tax_amt+"");
				}
					
				Vpay_short_recv_amt.add(short_recv+"");
			}else{
				Vpay_short_recv_amt.add("-");
			}
			
			if(invstatus.equalsIgnoreCase("PAID"))
			{
				if(rset1.getString(14)!=null)
				{
					Vpay_actual_recv_amt.add(rset1.getString(14));
				}
				else
				{
					Vpay_actual_recv_amt.add("-");
				}
				
				if(rset1.getString(15)!=null)
				{
					Vpay_recv_dt.add(rset1.getString(15));
				}
				else
				{
					Vpay_recv_dt.add("-");
				}
				
				if(rset1.getString(16)!=null)
				{
					Vpay_remark.add(rset1.getString(16));
				}
				else
				{
					Vpay_remark.add("-");
				}
				
				/*if(rset1.getString(24)!=null)
				{
					double tdsper=Double.parseDouble(rset1.getString(24));
					double salesval=Double.parseDouble(rset1.getString(5));
					double tdsval=(tdsper*salesval)/100;
					double invval=Double.parseDouble(rset1.getString(6));
					double netrecv=invval-tdsval;
					double actval=Double.parseDouble(rset1.getString(14));
					short_recv=netrecv-actval;
				}
				else
				{
					short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(rset1.getString(14));
				}*/
				
			
				Vpayflag.add("PAID");
				
				if(rset1.getString(17)!=null)
					Vinv_gen_by_cd.add(rset1.getString(17));
				else
					Vinv_gen_by_cd.add("00");
					
				if(rset1.getString(18)!=null)
					Vchecked_by_cd.add(rset1.getString(18));
				else
					Vchecked_by_cd.add("00");
				
				if(rset1.getString(19)!=null)
					Vapproved_by_cd.add(rset1.getString(19));
				else
					Vapproved_by_cd.add("00");
				
				if(rset1.getString(20)!=null)
				{
					Vpdf_inv_dtl.add(rset1.getString(20));
				}
				else
				{
					Vpdf_inv_dtl.add("-");
				}
				
				pdf_inv_dtl=rset1.getString(20);
				
				if(rset1.getString(21)!=null)
					print_by_ori=rset1.getString(21);
				else
					print_by_ori="00";
				
				if(rset1.getString(22)!=null)
					print_by_dup=rset1.getString(22);
				else
					print_by_dup="00";
				
				if(rset1.getString(23)!=null)
					print_by_tri=rset1.getString(23);
				else
					print_by_tri="00";
				
				////System.out.println("rset1.getString(24): "+rset1.getString(24));
				
				if(rset1.getString(24)!=null)
					Vtds_per.add(rset1.getString(24));
				else
					Vtds_per.add("-");
				
				if(pdf_inv_dtl.contains("T"))
				{
					Vprint_by_cd.add(print_by_tri);
				}
				else if(pdf_inv_dtl.contains("D"))
				{
					Vprint_by_cd.add(print_by_dup);
				}
				else if(pdf_inv_dtl.contains("O"))
				{
					Vprint_by_cd.add(print_by_ori);
				}
				else
				{
					Vprint_by_cd.add("-");
				}
				
				if(rset1.getString(25)!=null)
					Vpay_update_dt.add(rset1.getString(25));
				else
					Vpay_update_dt.add("-");
				
				if(rset1.getString(26)!=null)
					Vpay_update_cnt.add(rset1.getString(26));
				else
					Vpay_update_cnt.add("-");
				
				if(taxadvflag.equalsIgnoreCase("Y"))
				{
					////System.out.println("HERE IN IF");
					if(rset1.getString(27)!=null)
					{
						double t=Double.parseDouble(rset1.getString(27))-Double.parseDouble(tax_adj_amt);
						////System.out.println("IN IF---t---: "+t);
						Vtottax.add(t);
					}	
					else
					{
						Vtottax.add("-");
					}
				}
				else
				{
					if(rset1.getString(27)!=null)
					{
						////System.out.println("IN ELSE---rset---: "+rset1.getString(27));
						Vtottax.add(rset1.getString(27));
					}	
					else
					{
						Vtottax.add("-");
					}
				}
			}
			else
			{
				Vpay_actual_recv_amt.add("-");
				Vpay_recv_dt.add("-");
				Vpay_remark.add("-");
				//Vpay_short_recv_amt.add("-");
				Vpayflag.add("UNPAID");
				
				if(rset1.getString(14)!=null)
					Vinv_gen_by_cd.add(rset1.getString(14));
				else
					Vinv_gen_by_cd.add("00");
				
				if(rset1.getString(15)!=null)
					Vchecked_by_cd.add(rset1.getString(15));
				else
					Vchecked_by_cd.add("00");
					
				if(rset1.getString(16)!=null)
					Vapproved_by_cd.add(rset1.getString(16));
				else
					Vapproved_by_cd.add("00");
					
				if(rset1.getString(17)!=null)
				{
					Vpdf_inv_dtl.add(rset1.getString(17));
				}
				else
				{
					Vpdf_inv_dtl.add("-");
				}
				
				pdf_inv_dtl=rset1.getString(17);
				
				if(rset1.getString(18)!=null)
					print_by_ori=rset1.getString(18);
				else
					print_by_ori="00";
				
				if(rset1.getString(19)!=null)
					print_by_dup=rset1.getString(19);
				else
					print_by_dup="00";
				
				if(rset1.getString(20)!=null)
					print_by_tri=rset1.getString(20);
				else
					print_by_tri="00";
					
				if(rset1.getString(21)!=null)
				{
					Vtds_per.add(rset1.getString(21));
				}
				else
				{
					Vtds_per.add("-");
				}
				//System.out.println("===pdf-inv_dtl=="+pdf_inv_dtl);
				
				if(pdf_inv_dtl.contains("T"))
				{
					Vprint_by_cd.add(print_by_tri);
				}
				else if(pdf_inv_dtl.contains("D"))
				{
					Vprint_by_cd.add(print_by_dup);
				}
				else if(pdf_inv_dtl.contains("O"))
				{
					Vprint_by_cd.add(print_by_ori);
				}
				else
				{
					Vprint_by_cd.add("-");
				}
				
				if(rset1.getString(22)!=null)
					Vpay_update_dt.add(rset1.getString(22));
				else
					Vpay_update_dt.add("-");
				
				if(rset1.getString(23)!=null)
					Vpay_update_cnt.add(rset1.getString(23));
				else
					Vpay_update_cnt.add("-");
				
				if(taxadvflag.equalsIgnoreCase("Y"))
				{
					if(rset1.getString(24)!=null)
					{
						double t=Double.parseDouble(rset1.getString(24))-Double.parseDouble(tax_adj_amt);
						Vtottax.add(t);
					}	
					else
					{
						Vtottax.add("-");
					}
				}
				else
				{
					if(rset1.getString(24)!=null)
					{
						Vtottax.add(rset1.getString(24));
					}	
					else
					{
						Vtottax.add("-");
					}
				}
			}
			
			////System.out.println("===JAVA===2");
			/////////Vhlpl_inv_seq//////////
			
			if(Integer.parseInt(rset1.getString(3))<10)
			{
				hlpl_inv_no_disp="000"+rset1.getString(3)+"/"+rset1.getString(9);
			}
			else if(Integer.parseInt(rset1.getString(3))<100) 
			{
				hlpl_inv_no_disp="00"+rset1.getString(3)+"/"+rset1.getString(9);
			}
			else if(Integer.parseInt(rset1.getString(3))<1000) 
			{
				hlpl_inv_no_disp="0"+rset1.getString(3)+"/"+rset1.getString(9);
			}
			else
			{
				hlpl_inv_no_disp=rset1.getString(3)+"/"+rset1.getString(9);
			}
			
			if(!hlpl_inv_no_disp.equalsIgnoreCase(""))
			{
				Vhlpl_inv_seq.add(hlpl_inv_no_disp);
			}
			else
			{
				Vhlpl_inv_seq.add("-");
			}
			
			////System.out.println("===JAVA===3");
			
			///////////XML_GEN_FLAG/////////
			
			String queryString2 = "SELECT XML_GEN_FLAG,to_char(APPROVAL_DT,'dd/mm/yyyy') FROM FMS7_ACCOUNT_APPROVED_DTL " +
					  "WHERE INV_CARGO_NO='"+hlpl_inv_no_disp+"' AND JOURNAL_TYPE='FMSSL' " +
					  "AND CONTRACT_TYPE='"+contract_type+"' ";
				//	//System.out.println("Confirmed FMS7_PROV_SELLER_PAY Details Query = "+queryString);
				rset = stmt.executeQuery(queryString2);
				if(rset.next())
				{
					xml_gen_flag=rset.getString(1)==null?"N":rset.getString(1);
					approval_dt=rset.getString(2)==null?"":rset.getString(2);
					Vxml_gen_flag.add(xml_gen_flag);
				}
				else
				{
					xml_gen_flag="N";
					approval_dt="";
					Vxml_gen_flag.add(xml_gen_flag);
				}
					
				////System.out.println("===JAVA===4");
				
				///tax_adj
				
				////System.out.println("===JAVA===5");
				
				///////////inv_adj_amt///////////
				
				String q2="select flag,amount from fms7_inv_compo_dtl where price_cd='1' and inv_seq_no='"+invseq+"'";
				////System.out.println("JAVA check adjustmn query q2: "+q2);
				rset3=stmt3.executeQuery(q2);
				if(rset3.next())
				{}
				else
				{
					advflag="N";
					inv_adj_amt="0";
					Vinv_adj_flag.add(advflag);
				}
				////System.out.println("---JAVA here advflag: "+advflag);
							
				////System.out.println("===JAVA===6");
				
				////////////inv_amt///////////
				
				String inv_amt="";
				if(advflag.equalsIgnoreCase("Y") && taxadvflag.equalsIgnoreCase("Y"))
				{
					//query2="select INV_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
					inv_amt=invamtinr;
				}
				else
				{
					//query2="select GROSS_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
					inv_amt=gross_amt_inr;
				}
				////System.out.println("---JAVA inv_amt---: "+inv_amt);
						
				////System.out.println("===JAVA===7");
				
				String amt = "0";
				
				String map_id=contract_type+":"+fyr+":"+hlpl_inv_seq_no+":%";
				Vmapid.add(map_id);
				
				if(contract_type.equalsIgnoreCase("T") || contract_type.equalsIgnoreCase("C"))		//ADDED FOR LTCORA AND CN
				{}
				
				////System.out.println("===JAVA===8");
				
				String queryString12 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
						  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
						  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
						  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
			 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
				
				//System.out.println("===VTAXAMT QUERY===: "+queryString12);
				rset5=stmt5.executeQuery(queryString12);
				////System.out.println("===JAVA===9");
				int cnt1=0;
				String tx_cd="",tx_amt="",tx_nm="";
				while(rset5.next())
				{
					tax_cd=rset5.getString(1);
					tax_on=rset5.getString(3);
					////System.out.println("---tax_on---"+tax_on);
					if(rset5.getString(3).equals("1")) 
					{
						cnt1++;
						////System.out.println("===IN IF=== "+cnt1);
						tax_amt = (Double.parseDouble(gross_amt_inr)*Double.parseDouble(rset5.getString(2)))/100;
						////System.out.println(Double.parseDouble(gross_amt_inr)+"---total_amt_inr-IN IF--"+tax_amt);
						////System.out.println("---rset5.getString(2)-IN IF--"+rset5.getString(2));
					}
					else if(rset5.getString(3).equals("2"))
					{
						cnt1++;
						////System.out.println("===IN ELSE IF=== "+cnt1);
						String q3 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
									   "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
									   "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
									   "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) AND A.tax_code='"+rset5.getString(4)+"'";
						
						////System.out.println("===TAXAMT Query q3 === "+q3);
						rset6=stmt6.executeQuery(q3);
			 	 		
						if(rset6.next())
			 	 		{
			 	 			if(rset6.getString(3).equals("1"))
			 				{
			 					tax_amt = (Double.parseDouble(gross_amt_inr)*Double.parseDouble(rset6.getString(2)))/100;
			 				}	
			 	 			////System.out.println("---total_amt_inr-IN ELSE IF--"+total_amt_inr);
							////System.out.println("---rset5.getString(2)-IN ELSE IF--"+rset5.getString(2));
			 	 			tax_amt = (tax_amt*Double.parseDouble(rset5.getString(2)))/100;
			 	 		}
			 	 		else
			 	 		{
			 	 			tax_amt=0;
			 	 		}
					}
					else
					{
						tax_amt = 0;
					}
					
					double tax_diff=0;
					double tax_fact=Double.parseDouble(rset5.getString(2));
					if(tax_fact<tax_cform){
						tax_diff=tax_cform-tax_fact;
						tax_diff_val.add(tax_diff);
					}else{
						tax_diff_val.add("");
					}
					
					////System.out.println("===JAVA===9");
					
					////////////////////////////BK:SB20151203//////////////////
		 			
					if(!Adj_Tax_amt.equalsIgnoreCase("") && taxadvflag.equalsIgnoreCase("Y"))
		 			{
						if(Double.parseDouble(Adj_Tax_amt)>0)
			 			{
							////System.out.println("---Adj Amt---: "+Adj_Tax_amt);
			 				tax_amt = tax_amt - Double.parseDouble(Adj_Tax_amt);
			 				Adj_Tax_amt = "0";
			 			}
						else
			 			{
							if(tax_on.equalsIgnoreCase("2"))
							{
								//taxamt=nf.format(tax_amt)+"";
								//Tot_Tax_amt = Tot_Tax_amt + Double.parseDouble(taxamt);
							}
			 			}
		 			}
					
					////System.out.println("===IN WHILE================= "+cnt1);
					
					///////////////////////////////////////////////////////
					queryString6="select sht_nm from fms7_tax_mst where tax_code='"+tax_cd+"'";
					rset6=stmt6.executeQuery(queryString6);
					while(rset6.next())
					{
						tax_nm=rset6.getString(1);
					}
					
					tx_cd+="@"+tax_cd;
					tx_amt+="@"+nf.format(tax_amt);
					tx_nm+="@"+tax_nm;
					
					////System.out.println("===JAVA tax_amt after=== "+tax_amt);
				}
				Vtax_code.add(tx_cd.replaceFirst("@", ""));  	
				Vtax_amt.add(tx_amt.replaceFirst("@", ""));
				Vtax_nm.add(tx_nm.replaceFirst("@", ""));
				////System.out.println("Vtax_code-"+Vtax_code);
				////System.out.println("Vtax_amt-"+Vtax_amt);
				
				String q123 = "SELECT COUNT(*) FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
						  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
						  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
			 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
				rset=stmt.executeQuery(q123);
				while(rset.next())
				{
					//cnt1=rset.getInt(1);
					int count=rset.getInt(1);
					Vtaxcnt.add(count);
				}
				
				//////////////////////////////////////////////////////////
				///fOR GETTING REFERENCE NO FOR CONTRACT_NO AND AGREEMENT_NO///
				String mapp_id="";
				if(rset1.getString(8).equals("C") || rset1.getString(8).equals("M") || rset1.getString(8).equals("B")  || rset1.getString(8).equals("E")){
					if(invstatus.equalsIgnoreCase("PAID"))
					{
						mapp_id=rset1.getString(30)==null?"":rset1.getString(30);
					}else{
						mapp_id=rset1.getString(27)==null?"":rset1.getString(27);
					}
					//System.out.println("--mapp_id--"+mapp_id);
					String temp_mapp_id[]=mapp_id.split("-");
					VAgreement_no.add(temp_mapp_id[1]+"");
					VAgreement_rev_no.add(temp_mapp_id[2]+"");
					Vcont_no.add(temp_mapp_id[3]+"");
					Vcont_rev_no.add(temp_mapp_id[4]+"");
					if(""+temp_mapp_id[3]!=""){
						int temp_cont_no=Integer.parseInt(temp_mapp_id[3]+"");
						if(temp_cont_no>999){
							Vcont_typ.add("LTCORA (Period)");
						}else{
							Vcont_typ.add("LTCORA (CN)");
						}
					}else{
						Vcont_typ.add("LTCORA (CN)");
					}
					
				}else if(rset1.getString(8).equals("S")){
					VAgreement_no.add("");
					VAgreement_rev_no.add("");
					Vcont_no.add("");
					Vcont_rev_no.add("");
					Vcont_typ.add("SN");
				}else if(rset1.getString(8).equals("L")){
					VAgreement_no.add("");
					VAgreement_rev_no.add("");
					Vcont_no.add("");
					Vcont_rev_no.add("");
					Vcont_typ.add("LOA");
				}
				else if(rset1.getString(8).equals("I")){
					VAgreement_no.add("");
					VAgreement_rev_no.add("");
					Vcont_no.add("");
					Vcont_rev_no.add("");
					Vcont_typ.add("Interest");
				}
				else if(rset1.getString(8).equals("R")){
					Vcont_typ.add("Regas");
				}
			}
		}
		//FOR GETTING DEBIT NOTE FROM SYSTEM
		if(!customer_cd.equalsIgnoreCase(""))
		{
			if(invstatus.equalsIgnoreCase("PAID"))
			{
				queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
							 "DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
							 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
							 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
							 + ",TDS_PERCENT,"
							 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,FLAG,TO_CHAR(DUE_DT,'dd/mm/yyyy'),DR_CR_FLAG,DR_CR_NO,SUP_STATE_CODE ,TDS_TAX_PERCENT,TDS_TAX_AMT " +
							 "FROM DLNG_DR_CR_NOTE WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APrv_by is not null AND "
							 + " PAY_RECV_AMT IS NOT NULL AND "
							 + "PAY_INSERT_BY IS NOT NULL and dr_cr_flag='dr' "
							 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
			}
			else
			{
				queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
							 "DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
							 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
							 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
							 + ",TDS_PERCENT,"
							 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,FLAG,TO_CHAR(DUE_DT,'dd/mm/yyyy'),DR_CR_FLAG,DR_CR_NO,SUP_STATE_CODE  ,TDS_TAX_PERCENT,TDS_TAX_AMT " +
							 "FROM DLNG_DR_CR_NOTE WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') and  APrv_by is not null "
							 		//+ "AND "
							// + " PAY_RECV_AMT IS NULL AND PAY_INSERT_BY IS NULL "
							 + "and dr_cr_flag='dr' "
							 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
			}
		}
		else
		{
			if(invstatus.equalsIgnoreCase("PAID"))
			{
				queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
							 "DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
							 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
							 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
							 + ",TDS_PERCENT,"
							 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,FLAG,TO_CHAR(DUE_DT,'dd/mm/yyyy'),DR_CR_FLAG,DR_CR_NO,SUP_STATE_CODE ,TDS_TAX_PERCENT,TDS_TAX_AMT " +
							 "FROM DLNG_DR_CR_NOTE WHERE "+cnt_typ+" AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APrv_by is not null AND "
							 + " PAY_RECV_AMT IS NOT NULL AND "
							 + "PAY_INSERT_BY IS NOT NULL and dr_cr_flag='dr' "
							 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
			}
			else
			{
				queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
							 "DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
							 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
							 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
							 + ",TDS_PERCENT,"
							 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,FLAG,TO_CHAR(DUE_DT,'dd/mm/yyyy'),DR_CR_FLAG,DR_CR_NO,SUP_STATE_CODE  ,TDS_TAX_PERCENT,TDS_TAX_AMT " +
							 "FROM DLNG_DR_CR_NOTE WHERE "+cnt_typ+" AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY')  AND APrv_by is not null "
							 		//+ "AND "
							// + " PAY_RECV_AMT IS NULL AND PAY_INSERT_BY IS NULL "
							 + "and dr_cr_flag='dr' "
							 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
			}
		}
//		System.out.println("===fetch Invoice queryString123===: "+queryString123);
		rset1=stmt1.executeQuery(queryString123);
		while(rset1.next())
		{
			Vgross_trans_inr.add("");
			Vdrcrcriteria.add("");
			Vdrcrflag.add("");
			if(invstatus.equalsIgnoreCase("PAID")){
				Vtds_tax_percent.add(rset1.getString(27)==null?"":rset1.getString(27));
				Vtds_tax_amt.add(rset1.getString(28)==null?"":rset1.getString(28));
			}else{
				Vtds_tax_percent.add(rset1.getString(27)==null?"":rset1.getString(27));
				Vtds_tax_amt.add(rset1.getString(28)==null?"":rset1.getString(28));
			}
			String payamt="",inv_take_flag="Y",sup_state_cd="";String short_recv_="0";
			if(invstatus.equalsIgnoreCase("PAID"))
			{
				payamt=rset1.getString(12)==null?"":rset1.getString(12);
				inv_take_flag="Y";
				//Vsup_state_cd.add(rset1.getString(26)==null?"":rset1.getString(26));
				//sup_state_cd = rset1.getString(26)==null?"":rset1.getString(26);
			}else{
				payamt=rset1.getString(12)==null?"":rset1.getString(12);
				//Vsup_state_cd.add(rset1.getString(26)==null?"":rset1.getString(26));
				//sup_state_cd = rset1.getString(26)==null?"":rset1.getString(26);
//				System.out.println("payamt---"+payamt);
				if(payamt.equals("")){
					inv_take_flag="Y";
				}else{
					if(rset1.getString(17)!=null)
					{
						double tdsper=Double.parseDouble(rset1.getString(17));
						double salesval=Double.parseDouble(rset1.getString(5));
						double tdsval=(tdsper*salesval)/100;
						double invval=Double.parseDouble(rset1.getString(6));
						//double netrecv=invval-tdsval;
						String netrecv="";
						if(rset1.getString(27)!=null)
						{
							netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(28)));
						}else{
							netrecv=nf6.format(invval-tdsval);
						}
						double actval=Double.parseDouble(rset1.getString(12));
						short_recv_=nf6.format(Double.parseDouble(netrecv)-actval);
					}
					else
					{
						//System.out.println("short_recv_-in else--"+short_recv_);
						short_recv_=nf6.format(Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt));
					}
				//	System.out.println("short_recv_---"+short_recv_);
					
							if(Double.parseDouble(short_recv_) <= Double.parseDouble(Inv_roundoff+"")){
						
						//		System.out.println("in thisssssssss--->>"+rset1.getString(3)+"<<-->>"+Inv_roundoff+"<<---->>"+Double.parseDouble(short_recv_));
								inv_take_flag="N";
							}
							else{
								inv_take_flag="Y";
							}
				}
			}
		if(inv_take_flag.equalsIgnoreCase("Y")){
		cnt++;
		String temp_flag = "";
		if(invstatus.equalsIgnoreCase("PAID"))
		{
			VPAY_NEW_INV_SEQ_NO.add(rset1.getString(21)==null?"":rset1.getString(21));
			VPAY_FLAG.add(rset1.getString(22)==null?"Y":rset1.getString(22));
			temp_flag = rset1.getString(22)==null?"Y":rset1.getString(22);
			Vsup_state_cd.add(rset1.getString(26)==null?"":rset1.getString(26));
			sup_state_cd = rset1.getString(26)==null?"":rset1.getString(26);
		} else {
			VPAY_NEW_INV_SEQ_NO.add(rset1.getString(21)==null?"":rset1.getString(21));
			VPAY_FLAG.add(rset1.getString(22)==null?"Y":rset1.getString(22));
			temp_flag = rset1.getString(22)==null?"Y":rset1.getString(22);
			Vsup_state_cd.add(rset1.getString(26)==null?"":rset1.getString(26));
			sup_state_cd = rset1.getString(26)==null?"":rset1.getString(26);
		}
		flag_inv.add("D");
			
		//for tcs
		String query="select tcs_amt from fms7_invoice_tcs_dtl where hlpl_inv_seq_no='"+rset1.getString(25)+"' and contract_type='"+rset1.getString(7)+"' and "
				+ "financial_year='"+rset1.getString(8)+"' and customer_cd='"+rset1.getString(2)+"' and invoice_type ='DEBIT' AND FLAG='Y' and COMMODITY_TYPE='DLNG'"
				+ " and sup_state_code='"+sup_state_cd+"' ";
		rset=stmt.executeQuery(query);
		//System.out.println("===JAVA===1"+query);
		if(rset.next()){
			Vtcs_amt.add(rset.getString(1)==null?"":rset.getString(1));
		}else{
			Vtcs_amt.add("");
		}
		
		//Hiren_20210621 for TDS calc.
		String tds_app_flag = "N";
		tds_app_amt = "0";
		if(rset1.getString(7).equals("S") || rset1.getString(7).equals("L")) {
			tds_app_flag = checkforTDSApplicable(rset1.getString(4),rset1.getString(2),rset1.getString(8),rset1.getString(25),"DLNG","DEBIT",sup_state_cd);
			
		}
		if(segment.equalsIgnoreCase("LTCORA_CN")) {
			Vtds_app_flag.add("Y");
			Vtds_app_amt.add(rset1.getString(5)==null?"0":rset1.getString(5));
		}else {
			Vtds_app_flag.add(tds_app_flag);
			Vtds_app_amt.add(tds_app_amt);
		}
		////////
		////System.out.println("===JAVA===1");
		if(rset1.getString(1)!=null)
		{
			Vmonth.add(rset1.getString(1));
		}
		else
		{
			Vmonth.add("-");
		}
		
		if(rset1.getString(2)!=null)
		{
			Vcustomer_cd.add(rset1.getString(2));
		}
		else
		{
			Vcustomer_cd.add("-");
		}
		
		String queryString1 = "SELECT CUSTOMER_NAME,CUSTOMER_ABBR FROM FMS7_CUSTOMER_MST A WHERE CUSTOMER_CD='"+rset1.getString(2)+"'  AND " +
				  "A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_MST B " +
				  "WHERE A.customer_cd=B.customer_cd AND " +
				  "B.eff_dt<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY') and CUSTOMER_CD='"+rset1.getString(2)+"')";
		rset=stmt.executeQuery(queryString1);
		while(rset.next())
		{
			if(rset.getString(2)!=null)
			{
				Vcustomer_abbr.add(rset.getString(2));
				Vcustomer_name.add(rset.getString(1));
			}
			else
			{
				Vcustomer_abbr.add("-");
				Vcustomer_name.add("-");
			}
		}
		
		
		hlpl_inv_seq_no=rset1.getString(3);
		if(rset1.getString(3)!=null)
		{
			Vhlplinvseqno.add(rset1.getString(3));
		}
		else
		{
			Vhlplinvseqno.add("-");
		}
		
		Vdiff_tcs.add("0");
		Vdiff_tcs_flg.add("N");
		
		String tmp_fgsa_no="",tmp_fgsa_rev_no="",tmp_sn_no="",tmp_sn_rev_no="",TMP_QTY="";
		String Chk_by="",pdfinvdtl="",print_ori="",print_dupli="",print_trip="",mappid="";
		String queryString456="SELECT FGSA_NO,FGSA_REV_NO,SN_REV_NO,PLANT_SEQ_NO,TO_CHAR(PERIOD_START_DT,'DD/MM/YYYY'),"
				+ "TO_CHAR(PERIOD_END_DT,'DD/MM/YYYY'),EXCHG_RATE_CD,EXCHG_RATE_TYPE,CUST_INV_SEQ_NO,APPROVED_FLAG,TOTAL_QTY,checked_by,"
				+ "PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,mapping_id,sn_no " + 
				 "FROM DLNG_INVOICE_MST WHERE HLPL_INV_SEQ_NO='"+hlpl_inv_seq_no+"' "
				 + "AND FINANCIAL_YEAR='"+rset1.getString(8)+"' AND "
				 + "CONTRACT_TYPE='"+rset1.getString(7)+"'  ";
		rset=stmt.executeQuery(queryString456);
		//System.out.println("---queryString456---"+queryString456);
		if(rset.next())
		{
			if(rset.getString(1)!=null)
				Vfgsa_no.add(rset.getString(1));
			else
				Vfgsa_no.add("-");
			
			if(rset.getString(2)!=null)
				Vfgsa_rev_no.add(rset.getString(2));
			else
				Vfgsa_rev_no.add("-");
				
			if(rset.getString(3)!=null)
				Vsn_rev_no.add(rset.getString(3));
			else
				Vsn_rev_no.add("-");
			
			if(rset.getString(4)!=null)
				Vplant_seq_no.add(rset.getString(4));
			else
				Vplant_seq_no.add("-");
			
			tmp_fgsa_no=rset.getString(1)==null?"":rset.getString(1);
			tmp_fgsa_rev_no=rset.getString(2)==null?"":rset.getString(2);
			tmp_sn_no=rset.getString(18)==null?"":rset.getString(18);
			tmp_sn_rev_no=rset.getString(3)==null?"":rset.getString(3);
			TMP_QTY=rset.getString(11)==null?"":rset.getString(11);
			Chk_by= rset.getString(12)==null?"":rset.getString(12);
			pdfinvdtl=rset.getString(13)==null?"":rset.getString(13);
			print_ori=rset.getString(14)==null?"":rset.getString(14);
			print_dupli=rset.getString(15)==null?"":rset.getString(15);
			print_trip=rset.getString(16)==null?"":rset.getString(16);
			mappid=rset.getString(17)==null?"":rset.getString(17);
			
			String q1="SELECT NVL(A.plant_name,' ') FROM FMS7_CUSTOMER_PLANT_DTL A " +
					   "WHERE A.customer_cd="+rset1.getString(2)+" AND A.seq_no="+rset.getString(4)+" " +
					   "AND A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_PLANT_DTL B " +
					   "WHERE A.customer_cd=B.customer_cd AND A.seq_no=B.seq_no " +
					   "AND B.eff_dt<=TO_DATE('"+rset.getString(5)+"','DD/MM/YYYY'))";
			
			//String q1="SELECT PLANT_NAME FROM FMS7_CUSTOMER_PLANT_DTL  WHERE CUSTOMER_CD='"+rset1.getString(2)+"' AND SEQ_NO='"+rset.getString(4)+"'";
			////System.out.println("---PLANT_NAME QUERY---"+q1);
			rset6=stmt6.executeQuery(q1);
			
			while(rset6.next())
			{
				if(rset6.getString(1)!=null)
					Vplant_seq_nm.add(rset6.getString(1));
				else
					Vplant_seq_nm.add("-");
				
				////System.out.println("---Vplant_seq_nm---:"+rset6.getString(1));
			}
			
			if(rset.getString(5)!=null)
				Vperiod_st_dt.add(rset.getString(5));
			else
				Vperiod_st_dt.add("-");
				
			if(rset.getString(6)!=null)
				Vperiod_end_dt.add(rset.getString(6));
			else
				Vperiod_end_dt.add("-");
			
			if(rset.getString(7)!=null)
				Vexchg_rate_cd.add(rset.getString(7));
			else
				Vexchg_rate_cd.add("-");
			
			if(rset.getString(8)!=null)
				Vexchg_rate_type.add(rset.getString(8));
			else
				Vexchg_rate_type.add("-");
			
			if(rset.getString(9)!=null)
				Vcust_inv_seq_no.add(rset.getString(9));
			else
				Vcust_inv_seq_no.add("-");
			
			if(rset.getString(10)!=null)
				Vapproved_flag.add(rset.getString(10));
			else
				Vapproved_flag.add("-");
			
			String queryString_agr_base="";
			if(rset1.getString(8).equals("S")){
				queryString_agr_base="SELECT FLSA_BASE FROM DLNG_FLSA_MST WHERE FLSA_NO='"+rset.getString(1)+"' AND REV_NO='"+rset.getString(2)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
				rset2=stmt2.executeQuery(queryString_agr_base);
				if(rset2.next()){
					agr_base=rset2.getString(1)==null?"":rset2.getString(1);
				}
			}else if(rset1.getString(8).equals("L")){
				queryString_agr_base="SELECT TENDER_BASE FROM DLNG_TENDER_MST WHERE TENDER_NO='"+rset.getString(1)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
				rset2=stmt2.executeQuery(queryString_agr_base);
				if(rset2.next()){
					agr_base=rset2.getString(1)==null?"":rset2.getString(1);
				}
			}
		}
		VAgreement_base.add(agr_base);
		if(rset1.getString(4)!=null)
		{
			Vinv_dt.add(rset1.getString(4));
		}
		else
		{
			Vinv_dt.add("-");
		}
		
		if(agr_base.equalsIgnoreCase("D")){

			String queryString_tr="";
			String transp_charges="";
			String temp_grossamt="";
			if(rset1.getString(7).equals("S")){
				queryString_tr="SELECT TRANSPORTATION_CHARGE FROM DLNG_SN_MST WHERE FLSA_NO='"+tmp_fgsa_no+"' AND FLSA_REV_NO='"+tmp_fgsa_rev_no+"' AND SN_NO='"+tmp_sn_no+"' AND SN_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
			}else if(rset1.getString(7).equals("L")){
				queryString_tr="SELECT TRANSPORTATION_CHARGE FROM DLNG_LOA_MST WHERE TENDER_NO='"+tmp_fgsa_no+"' AND LOA_NO='"+tmp_sn_no+"' AND LOA_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
				
			}
			rset2=stmt2.executeQuery(queryString_tr);
			//System.out.println("queryString--"+queryString_tr);
			if(rset2.next()){
				transp_charges=rset2.getString(1)==null?"":rset2.getString(1);
			}
			//System.out.println("---rset.getString(7)--"+rset1.getString(5)+"--transp_charges--"+transp_charges+"--TMP_QTY---"+TMP_QTY);
			if(!transp_charges.equals("")){
				temp_grossamt=""+(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))+Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
				Vsales_value.add(temp_grossamt);
				gross_amt_inr=temp_grossamt;
				//System.out.println("---temp====="+temp_grossamt);
			}else{
				Vsales_value.add(rset1.getString(5)==null?"0":rset1.getString(5));
				gross_amt_inr=rset1.getString(5);
			}
		
		}else{
		
			if(rset1.getString(5)!=null)
			{
				Vsales_value.add(rset1.getString(5));
			}
			else
			{
				Vsales_value.add("0");
			}
				
			gross_amt_inr=rset1.getString(5);
		}
		Vgross_trans.add("");
		if(rset1.getString(6)!=null)
		{
			Vinv_value.add(rset1.getString(6));
		}
		else
		{
			Vinv_value.add("-");
		}
		
		String duedt=rset1.getString(23)==null?"":rset1.getString(23);
		String drcr_flg=rset1.getString(24)==null?"":rset1.getString(24);
		if(drcr_flg.equals("dr")){
			Vdue_dt.add(duedt);
		}else{
			Vdue_dt.add("-");
		}
		
		
		if(rset1.getString(7)!=null)
		{
			Vcont_type.add(rset1.getString(7));
		}
		else
		{
			Vcont_type.add("-");
		}
		
		if(rset1.getString(8)!=null)
		{
			Vfinancial_year.add(rset1.getString(8));
		}
		else
		{
			Vfinancial_year.add("-");
		}
		
		if(rset1.getString(9)!=null)
		{
			Vtax_str_cd.add(rset1.getString(9));
		}
		else
		{
			Vtax_str_cd.add("-");
		}
		
		if(rset1.getString(10)!=null)
		{
			Vtaxamtinr.add(rset1.getString(10));
		}
		else
		{
			Vtaxamtinr.add("-");
		}
		
		
		Vinvamtinr.add("-");
		
		
		//invamtinr=rset1.getString(12);
		sn_no=tmp_sn_no;
		taxvalue=rset1.getString(10)==null?"":rset1.getString(10);
		fyr=rset1.getString(8)==null?"":rset1.getString(8);
		total_amt_inr=Double.parseDouble(rset1.getString(5));
		tax_str_cd=rset1.getString(9)==null?"":rset1.getString(9);
		
		Vsn_no.add(sn_no);
		
		//String invseq=contract_type+":"+financial_year+":"+hlpl_inv_seq_no+":%";
		String invseq=rset1.getString(7)+":"+rset1.getString(8)+":"+rset1.getString(3)+":"+rset1.getString(4);
		if(!invseq.equalsIgnoreCase(""))
		{
			Vinvseqno.add(invseq);
		}
		else
		{
			Vinvseqno.add("-");
		}
		
		////////tax_adj_amt////////
		
		/*String q1="select flag,amount from fms7_inv_compo_dtl where price_cd IN ('6','10','11','12','13') and inv_seq_no='"+invseq+"'";
		////System.out.println("JAVA check adjustmn query q1: "+q1);
		rset2=stmt2.executeQuery(q1);
		if(rset2.next())
		{
			if(rset2.getString(1)!=null)
			{
				taxadvflag=rset2.getString(1);
			}
			else
			{
				taxadvflag="N";
			}
			
			Vtax_adj_flag.add(taxadvflag);
						
			if(rset2.getString(2)!=null)
			{
				tax_adj_amt=nf.format(Double.parseDouble(rset2.getString(2)));
			}
			else
			{
				tax_adj_amt="0";
			}
		}
		else
		{*/
			taxadvflag="N";
			tax_adj_amt="0";
			Vtax_adj_flag.add(taxadvflag);
		//}
		
		////System.out.println("---JAVA here taxadvflag---: "+taxadvflag+"---tax_adj_amt---: "+tax_adj_amt);	
			double balance_adjust_amt=0,tot_adjust_amt=0;
			//String mapping_id=""+rset1.getString(2)+"-"+tmp_fgsa_no+"-"+tmp_fgsa_rev_no+"-"+tmp_sn_no+"-"+tmp_sn_rev_no+"-"+rset1.getString(7);
			String mapping_id="%"+rset1.getString(2)+"-"+tmp_fgsa_no+"-%-"+tmp_sn_no+"-%-"+rset1.getString(7)+"%";
			queryString = "select price_rate,currency_cd,flag " +
					"FROM dlng_cont_price_dtl WHERE mapping_id like '"+mapping_id+"' AND " +
					"price_cd='1' AND FLAG='Y' and pay_type='AP' ";
			//System.out.println("Fetching flag of sn..."+queryString);
			rset2=stmt2.executeQuery(queryString);
			if(rset2.next())
			{
				VADJUST_FLAG_SN.add(rset2.getString(3)==null?"N":rset2.getString(3));
				tot_adjust_amt=rset2.getDouble(1);
				VADJUST_AMT_SN.add(rset2.getString(1)==null?"":rset2.getString(1));
				VADJUST_CUR_SN.add(rset2.getString(2));
				if(rset1.getString(7).equals("S")){
					queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' "
							+ "AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
							+ "CONTRACT_NO='"+tmp_sn_no+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND "
							+ "AGR_NO='"+rset.getString(1)+"' AND AGR_REV_NO='"+rset.getString(2)+"' "
							+ "AND CONTRACT_TYPE='"+rset1.getString(7)+"' ";
//					System.out.println("Fetching flag of sn..."+queryString);
					
				}else{
					queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' "
							+ "AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
							+ "CONTRACT_NO='"+tmp_sn_no+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND "
									+ "AGR_NO='"+rset.getString(1)+"' AND CONTRACT_TYPE='"+rset1.getString(7)+"' ";
//					System.out.println("Fetching flag of sn..."+queryString);
					
				}
				rset3=stmt3.executeQuery(queryString1);	
				if(rset3.next()){
					VCFORM_FLAG.add(rset3.getString(1)==null?"":rset3.getString(1));
				}else{
					VCFORM_FLAG.add("");
				}
			}else{
				VADJUST_FLAG_SN.add("");
				VADJUST_AMT_SN.add("");
				VADJUST_CUR_SN.add("");
				VCFORM_FLAG.add("");
			}
			queryString = "select sum(adjusted_amt) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
					+ "AND adjust_FLAG='Y' and commodity_type='DLNG' and contract_type='"+rset1.getString(7)+"'  ";
		//System.out.println("Fetching flag of sn..."+queryString);
			rset2=stmt2.executeQuery(queryString);
			if(rset2.next())
			{
				balance_adjust_amt=tot_adjust_amt-rset2.getDouble(1);
			}else{
				balance_adjust_amt=tot_adjust_amt;
			}
			queryString = "select sum(hold_amount) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
					+ "AND commodity_type='DLNG' and contract_type='"+rset1.getString(7)+"' and release_flag='Y' ";
		//System.out.println("Fetching flag of sn..."+queryString);
			rset2=stmt2.executeQuery(queryString);
			if(rset2.next())
			{
				balance_adjust_amt+=rset2.getDouble(1);
			}
			VADJUST_balc_SN.add(nf6.format(balance_adjust_amt));
		/////HERE
			if(!payamt.equals("")){
				if(rset1.getString(17)!=null)
				{
					
					double tdsper=Double.parseDouble(rset1.getString(17));
					double salesval=Double.parseDouble(rset1.getString(5));
					String tdsval=nf.format((tdsper*salesval)/100);
//					double invval=Double.parseDouble(rset1.getString(6));
//					double netrecv=invval-tdsval;
//					double actval=Double.parseDouble(payamt);
					//System.out.println("rset1.getString(6)******"+rset1.getString(6)+"***payamt***"+payamt+"**tdsval***"+tdsval);
					double netrecv=0;
					if(rset1.getString(27)!=null)
					{
						short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt) - Double.parseDouble(tdsval+"")-Double.parseDouble(rset1.getString(28));
					}else{
						short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt) - Double.parseDouble(tdsval+"");
					}
					
				}
				else
				{
					//System.out.println("short_recv_-in else--"+short_recv_);
					short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt);
				}
				Vpay_short_recv_amt.add(short_recv);
			}else{
				Vpay_short_recv_amt.add("-");
			}
		if(invstatus.equalsIgnoreCase("PAID"))
		{
			if(rset1.getString(12)!=null)
			{
				Vpay_actual_recv_amt.add(rset1.getString(12));
			}
			else
			{
				Vpay_actual_recv_amt.add("-");
			}
			
			if(rset1.getString(13)!=null)
			{
				Vpay_recv_dt.add(rset1.getString(13));
			}
			else
			{
				Vpay_recv_dt.add("-");
			}
			
			if(rset1.getString(14)!=null)
			{
				Vpay_remark.add(rset1.getString(14));
			}
			else
			{
				Vpay_remark.add("-");
			}
			
			/*if(rset1.getString(17)!=null)
			{
				double tdsper=Double.parseDouble(rset1.getString(17));
				double salesval=Double.parseDouble(rset1.getString(5));
				double tdsval=(tdsper*salesval)/100;
				double invval=Double.parseDouble(rset1.getString(6));
				double netrecv=invval-tdsval;
				double actval=Double.parseDouble(rset1.getString(12));
				short_recv=netrecv-actval;
			}
			else
			{
				short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(rset1.getString(12));
			}
			
			Vpay_short_recv_amt.add(short_recv+"");*/
		
			Vpayflag.add("PAID");
			
			if(rset1.getString(15)!=null)
				Vinv_gen_by_cd.add(rset1.getString(15));
			else
				Vinv_gen_by_cd.add("00");
				
			if(!Chk_by.equals(""))
				Vchecked_by_cd.add(Chk_by);
			else
				Vchecked_by_cd.add("00");
			
			if(rset1.getString(16)!=null)
				Vapproved_by_cd.add(rset1.getString(16));
			else
				Vapproved_by_cd.add("00");
			
			if(!pdfinvdtl.equals(""))
			{
				Vpdf_inv_dtl.add(pdfinvdtl);
			}
			else
			{
				Vpdf_inv_dtl.add("-");
			}
			
			pdf_inv_dtl=pdfinvdtl;
			
			if(!print_ori.equals(""))
				print_by_ori=print_ori;
			else
				print_by_ori="00";
			
			if(!print_dupli.equals(""))
				print_by_dup=print_dupli;
			else
				print_by_dup="00";
			
			if(!print_trip.equals(""))
				print_by_tri=print_trip;
			else
				print_by_tri="00";
			
			////System.out.println("rset1.getString(24): "+rset1.getString(24));
			
			if(rset1.getString(17)!=null)
				Vtds_per.add(rset1.getString(17));
			else
				Vtds_per.add("-");
			
			if(pdf_inv_dtl.contains("T"))
			{
				Vprint_by_cd.add(print_by_tri);
			}
			else if(pdf_inv_dtl.contains("D"))
			{
				Vprint_by_cd.add(print_by_dup);
			}
			else if(pdf_inv_dtl.contains("O"))
			{
				Vprint_by_cd.add(print_by_ori);
			}
			else
			{
				Vprint_by_cd.add("");
			}
			
			if(rset1.getString(18)!=null)
				Vpay_update_dt.add(rset1.getString(18));
			else
				Vpay_update_dt.add("-");
			
			if(rset1.getString(19)!=null)
				Vpay_update_cnt.add(rset1.getString(19));
			else
				Vpay_update_cnt.add("-");
			
			/*if(taxadvflag.equalsIgnoreCase("Y"))
			{
				////System.out.println("HERE IN IF");
				if(rset1.getString(10)!=null)
				{
					double t=Double.parseDouble(rset1.getString(10))-Double.parseDouble(tax_adj_amt);
					////System.out.println("IN IF---t---: "+t);
					Vtottax.add(t);
				}	
				else
				{
					Vtottax.add("-");
				}
			}
			else
			{
				if(rset1.getString(10)!=null)
				{
					////System.out.println("IN ELSE---rset---: "+rset1.getString(27));
					Vtottax.add(rset1.getString(10));
				}	
				else
				{
					Vtottax.add("-");
				}
			}*/
			double taxtot=0;
			double gross_inr=Double.parseDouble(nf6.format(Double.parseDouble(rset1.getString(5)==null?"":rset1.getString(5))));
			queryString = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0.00'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
					  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
					  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
					  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) ORDER BY A.tax_code";
			////System.out.println("STEP-1A.3:FMS7_TAX_STRUCTURE_DTL: "+queryString);
			rset=stmt.executeQuery(queryString);
			while(rset.next())
			{
				//tax_factor+= Integer.parseInt((rset.getString(2)));
				if(rset.getString(3).equals("1"))
				{
					//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset.getString(2)))/100;
					tax_amt = (gross_inr*Double.parseDouble(rset.getString(2)))/100;
					
				}
				else if(rset.getString(3).equals("2"))
				{
					queryString1 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) AND A.tax_code='"+rset.getString(4)+"'";
				//////System.out.println("Query For Finding Out Tax Which Is Dependent On Other Tax Value = "+queryString1);
			 		rset2=stmt2.executeQuery(queryString1);
			 		if(rset2.next())
			 		{
			 			if(rset2.getString(3).equals("1"))
						{
							//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset1.getString(2)))/100;
			 				tax_amt = (gross_inr*Double.parseDouble(rset2.getString(2)))/100;
						}
					
		 			tax_amt = (tax_amt*Double.parseDouble(rset.getString(2)))/100;
			 		}
			 		else
			 		{
			 			tax_amt = 0;
			 		}
				}
				else
				{
					tax_amt = 0;
				}
				
			//customer_Invoice_Tax_Amt.add(nf.format(Math.round(tax_amt)));
			
			taxtot+=Math.round(tax_amt);
			//net_amt.add(net_amt_inr[i]);
		}
			Vtottax.add(taxtot);
		}
		else
		{
			Vpay_actual_recv_amt.add("-");
			Vpay_recv_dt.add("-");
			Vpay_remark.add("-");
			//Vpay_short_recv_amt.add("-");
			Vpayflag.add("UNPAID");
			
			if(rset1.getString(15)!=null)
				Vinv_gen_by_cd.add(rset1.getString(15));
			else
				Vinv_gen_by_cd.add("00");
			
			if(!Chk_by.equals(""))
				Vchecked_by_cd.add(Chk_by);
			else
				Vchecked_by_cd.add("00");
				
			if(rset1.getString(16)!=null)
				Vapproved_by_cd.add(rset1.getString(16));
			else
				Vapproved_by_cd.add("00");
				
			if(!pdfinvdtl.equals(""))
			{
				Vpdf_inv_dtl.add(pdfinvdtl);
			}
			else
			{
				Vpdf_inv_dtl.add("-");
			}
			
			pdf_inv_dtl=pdfinvdtl;
			
			if(!print_ori.equals(""))
				print_by_ori=print_ori;
			else
				print_by_ori="00";
			
			if(!print_dupli.equals(""))
				print_by_dup=print_dupli;
			else
				print_by_dup="00";
			
			if(!print_trip.equals(""))
				print_by_tri=print_trip;
			else
				print_by_tri="00";
				
			if(rset1.getString(17)!=null)
			{
				Vtds_per.add(rset1.getString(17));
			}
			else
			{
				Vtds_per.add("-");
			}
			//System.out.println("===pdf-inv_dtl=="+pdf_inv_dtl);
			
			if(pdf_inv_dtl.contains("T"))
			{
				Vprint_by_cd.add(print_by_tri);
			}
			else if(pdf_inv_dtl.contains("D"))
			{
				Vprint_by_cd.add(print_by_dup);
			}
			else if(pdf_inv_dtl.contains("O"))
			{
				Vprint_by_cd.add(print_by_ori);
			}
			else
			{
				Vprint_by_cd.add("");
			}
			
			if(rset1.getString(18)!=null)
				Vpay_update_dt.add(rset1.getString(18));
			else
				Vpay_update_dt.add("-");
			
			if(rset1.getString(19)!=null)
				Vpay_update_cnt.add(rset1.getString(19));
			else
				Vpay_update_cnt.add("-");
			
			/*if(taxadvflag.equalsIgnoreCase("Y"))
			{
//				if(rset1.getString(24)!=null)
//				{
//					double t=Double.parseDouble(rset1.getString(24))-Double.parseDouble(tax_adj_amt);
//					Vtottax.add(t);
//				}	
//				else
//				{
					Vtottax.add("-");
				//}
			}
			else
			{
				if(rset1.getString(24)!=null)
				{
					Vtottax.add(rset1.getString(24));
				}	
				else
				{
					Vtottax.add("-");
				//}
			}*/
			
			double taxtot=0;
			double gross_inr=Double.parseDouble(nf6.format(Double.parseDouble(rset1.getString(5)==null?"":rset1.getString(5))));
			
			queryString = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0.00'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
					  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
					  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
					  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) ORDER BY A.tax_code";
			//System.out.println("STEP-1A.3:FMS7_TAX_STRUCTURE_DTL: "+queryString);
			rset=stmt.executeQuery(queryString);
			while(rset.next())
			{
				//tax_factor+= Integer.parseInt((rset.getString(2)));
				if(rset.getString(3).equals("1"))
				{
					//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset.getString(2)))/100;
					tax_amt = (gross_inr*Double.parseDouble(rset.getString(2)))/100;
					
				}
				else if(rset.getString(3).equals("2"))
				{
					queryString1 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) AND A.tax_code='"+rset.getString(4)+"'";
				//////System.out.println("Query For Finding Out Tax Which Is Dependent On Other Tax Value = "+queryString1);
			 		rset2=stmt2.executeQuery(queryString1);
			 		if(rset2.next())
			 		{
			 			if(rset2.getString(3).equals("1"))
						{
							//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset1.getString(2)))/100;
			 				tax_amt = (gross_inr*Double.parseDouble(rset2.getString(2)))/100;
						}
					
		 			tax_amt = (tax_amt*Double.parseDouble(rset.getString(2)))/100;
			 		}
			 		else
			 		{
			 			tax_amt = 0;
			 		}
				}
				else
				{
					tax_amt = 0;
				}
				
			//customer_Invoice_Tax_Amt.add(nf.format(Math.round(tax_amt)));
			
			taxtot+=Math.round(tax_amt);
			//net_amt.add(net_amt_inr[i]);
		}
			Vtottax.add(taxtot);
		}
		
		////System.out.println("===JAVA===2");
		/////////Vhlpl_inv_seq//////////
		
		if(Integer.parseInt(rset1.getString(3))<10)
		{
			hlpl_inv_no_disp="000"+rset1.getString(3)+"/"+rset1.getString(8);
		}
		else if(Integer.parseInt(rset1.getString(3))<100) 
		{
			hlpl_inv_no_disp="00"+rset1.getString(3)+"/"+rset1.getString(8);
		}
		else if(Integer.parseInt(rset1.getString(3))<1000) 
		{
			hlpl_inv_no_disp="0"+rset1.getString(3)+"/"+rset1.getString(8);
		}
		else
		{
			hlpl_inv_no_disp=rset1.getString(3)+"/"+rset1.getString(8);
		}
		hlpl_inv_no_disp=rset1.getString(21)==null?"":rset1.getString(21);
		if(!hlpl_inv_no_disp.equalsIgnoreCase(""))
		{
			Vhlpl_inv_seq.add(rset1.getString(21)==null?"":rset1.getString(21));
		}
		else
		{
			Vhlpl_inv_seq.add("-");
		}
		
		////System.out.println("===JAVA===3");
		
		///////////XML_GEN_FLAG/////////
		
		String queryString2 = "SELECT XML_GEN_FLAG,to_char(APPROVAL_DT,'dd/mm/yyyy') FROM FMS7_ACCOUNT_APPROVED_DTL " +
				  "WHERE INV_CARGO_NO='"+hlpl_inv_no_disp+"' AND JOURNAL_TYPE='FMSSL' " +
				  "AND CONTRACT_TYPE='"+contract_type+"' ";
			//	//System.out.println("Confirmed FMS7_PROV_SELLER_PAY Details Query = "+queryString);
			rset = stmt.executeQuery(queryString2);
			if(rset.next())
			{
				xml_gen_flag=rset.getString(1)==null?"N":rset.getString(1);
				approval_dt=rset.getString(2)==null?"":rset.getString(2);
				Vxml_gen_flag.add(xml_gen_flag);
			}
			else
			{
				xml_gen_flag="N";
				approval_dt="";
				Vxml_gen_flag.add(xml_gen_flag);
			}
				
			////System.out.println("===JAVA===4");
			
			///tax_adj
			
			////System.out.println("===JAVA===5");
			
			///////////inv_adj_amt///////////
			
			/*String q2="select flag,amount from fms7_inv_compo_dtl where price_cd='1' and inv_seq_no='"+invseq+"'";
			////System.out.println("JAVA check adjustmn query q2: "+q2);
			rset3=stmt3.executeQuery(q2);
			if(rset3.next())
			{
				if(rset3.getString(1)!=null)
				{
					advflag=rset3.getString(1);
				}
				else
				{
					advflag="N";
				}
				
				Vinv_adj_flag.add(advflag);
				
				if(rset3.getString(2)!=null)
				{
					inv_adj_amt=nf.format(Double.parseDouble(rset3.getString(2)));
				}
				else
				{
					inv_adj_amt="0";
				}
			}
			else
			{*/
				advflag="N";
				inv_adj_amt="0";
				Vinv_adj_flag.add(advflag);
			//}
			////System.out.println("---JAVA here advflag: "+advflag);
						
			////System.out.println("===JAVA===6");
			
			////////////inv_amt///////////
			
			String inv_amt="";
			if(advflag.equalsIgnoreCase("Y") && taxadvflag.equalsIgnoreCase("Y"))
			{
				//query2="select INV_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
				inv_amt=invamtinr;
			}
			else
			{
				//query2="select GROSS_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
				inv_amt=gross_amt_inr;
			}
			////System.out.println("---JAVA inv_amt---: "+inv_amt);
					
			////System.out.println("===JAVA===7");
			
			String amt = "0";
			
			String map_id=contract_type+":"+fyr+":"+hlpl_inv_seq_no+":%";
			Vmapid.add(map_id);
			
		/*	if(contract_type.equalsIgnoreCase("T") || contract_type.equalsIgnoreCase("C"))		//ADDED FOR LTCORA AND CN
			{
				// String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no like" +
				//	" '"+map_id+"'and price_cd='6' and (flag_temp not like 'T' or flag_temp is null)";
				
				String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no='"+invseq+"' and price_cd IN ('6','10','11','12','13') "
						+ "and (flag_temp not like 'T' or flag_temp is null)";
				
				rset4=stmt4.executeQuery(queryTax);
				////System.out.println("---JAVA queryTax---: "+queryTax);
				if(rset4.next())
				{
					amt = ""+java.text.NumberFormat.getInstance().parse(rset4.getString(1));
					Adj_Tax_amt=amt;	
					taxvalue = ""+(Double.parseDouble(taxvalue) - Double.parseDouble(amt));
				}
			}*/
			
			////System.out.println("===JAVA===8");
			
			String queryString12 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
					  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
					  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
					  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
		 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
			
			////System.out.println("===VTAXAMT QUERY===: "+queryString12);
			rset5=stmt5.executeQuery(queryString12);
			////System.out.println("===JAVA===9");
			int cnt1=0;
			String tx_cd="",tx_amt="",tx_nm="";
			while(rset5.next())
			{
				tax_cd=rset5.getString(1);
				tax_on=rset5.getString(3);
				////System.out.println("---tax_on---"+tax_on);
				if(rset5.getString(3).equals("1")) 
				{
					cnt1++;
					////System.out.println("===IN IF=== "+cnt1);
					tax_amt = (Double.parseDouble(gross_amt_inr)*Double.parseDouble(rset5.getString(2)))/100;
					////System.out.println(Double.parseDouble(gross_amt_inr)+"---total_amt_inr-IN IF--"+tax_amt);
					////System.out.println("---rset5.getString(2)-IN IF--"+rset5.getString(2));
				}
				else if(rset5.getString(3).equals("2"))
				{
					cnt1++;
					////System.out.println("===IN ELSE IF=== "+cnt1);
					String q3 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
								   "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
								   "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
								   "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) AND A.tax_code='"+rset5.getString(4)+"'";
					
					////System.out.println("===TAXAMT Query q3 === "+q3);
					rset6=stmt6.executeQuery(q3);
		 	 		
					if(rset6.next())
		 	 		{
		 	 			if(rset6.getString(3).equals("1"))
		 				{
		 					tax_amt = (Double.parseDouble(gross_amt_inr)*Double.parseDouble(rset6.getString(2)))/100;
		 				}	
		 	 			////System.out.println("---total_amt_inr-IN ELSE IF--"+total_amt_inr);
						////System.out.println("---rset5.getString(2)-IN ELSE IF--"+rset5.getString(2));
		 	 			tax_amt = (tax_amt*Double.parseDouble(rset5.getString(2)))/100;
		 	 		}
		 	 		else
		 	 		{
		 	 			tax_amt=0;
		 	 		}
				}
				else
				{
					tax_amt = 0;
				}
				double tax_diff=0;
				double tax_fact=Double.parseDouble(rset5.getString(2));
				if(tax_fact<tax_cform){
					tax_diff=tax_cform-tax_fact;
					tax_diff_val.add(tax_diff);
				}else{
					tax_diff_val.add("");
				}
				////System.out.println("===JAVA===9");
				
				////////////////////////////BK:SB20151203//////////////////
	 			
				if(!Adj_Tax_amt.equalsIgnoreCase("") && taxadvflag.equalsIgnoreCase("Y"))
	 			{
					if(Double.parseDouble(Adj_Tax_amt)>0)
		 			{
						////System.out.println("---Adj Amt---: "+Adj_Tax_amt);
		 				tax_amt = tax_amt - Double.parseDouble(Adj_Tax_amt);
		 				Adj_Tax_amt = "0";
		 			}
					else
		 			{
						if(tax_on.equalsIgnoreCase("2"))
						{
							//taxamt=nf.format(tax_amt)+"";
							//Tot_Tax_amt = Tot_Tax_amt + Double.parseDouble(taxamt);
						}
		 			}
	 			}
				
				////System.out.println("===IN WHILE================= "+cnt1);
				
				///////////////////////////////////////////////////////
				queryString6="select sht_nm from fms7_tax_mst where tax_code='"+tax_cd+"'";
				rset6=stmt6.executeQuery(queryString6);
				while(rset6.next())
				{
					tax_nm=rset6.getString(1);
				}
				
				tx_cd+="@"+tax_cd;
				tx_amt+="@"+nf.format(tax_amt);
				tx_nm+="@"+tax_nm;
				
				////System.out.println("===JAVA tax_amt after=== "+tax_amt);
			}
			Vtax_code.add(tx_cd.replaceFirst("@", ""));  	
			Vtax_amt.add(tx_amt.replaceFirst("@", ""));
			Vtax_nm.add(tx_nm.replaceFirst("@", ""));
			////System.out.println("Vtax_code-"+Vtax_code);
			////System.out.println("Vtax_amt-"+Vtax_amt);
			
			String q123 = "SELECT COUNT(*) FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
					  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
					  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
		 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
			rset=stmt.executeQuery(q123);
			while(rset.next())
			{
				//cnt1=rset.getInt(1);
				int count=rset.getInt(1);
				Vtaxcnt.add(count);
			}
			
			//////////////////////////////////////////////////////////
			///fOR GETTING REFERENCE NO FOR CONTRACT_NO AND AGREEMENT_NO///
			String mapp_id="";
			if(rset1.getString(7).equals("C") || rset1.getString(7).equals("M") || rset1.getString(7).equals("B")){
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					mapp_id=mappid;
				}else{
					mapp_id=mappid;
				}
				//System.out.println("--mapp_id--"+mapp_id);
				String temp_mapp_id[]=mapp_id.split("-");
				VAgreement_no.add(temp_mapp_id[1]+"");
				VAgreement_rev_no.add(temp_mapp_id[2]+"");
				Vcont_no.add(temp_mapp_id[3]+"");
				Vcont_rev_no.add(temp_mapp_id[4]+"");
				if(""+temp_mapp_id[3]!=""){
					int temp_cont_no=Integer.parseInt(temp_mapp_id[3]+"");
					if(temp_cont_no>999){
						Vcont_typ.add("LTCORA (Period)");
					}else{
						Vcont_typ.add("LTCORA (CN)");
					}
				}else{
					Vcont_typ.add("LTCORA (CN)");
				}
				
			}else if(rset1.getString(7).equals("S")){
				VAgreement_no.add("");
				VAgreement_rev_no.add("");
				Vcont_no.add("");
				Vcont_rev_no.add("");
				Vcont_typ.add("SN");
			}else if(rset1.getString(7).equals("L")){
				VAgreement_no.add("");
				VAgreement_rev_no.add("");
				Vcont_no.add("");
				Vcont_rev_no.add("");
				Vcont_typ.add("LOA");
			}
			else if(rset1.getString(7).equals("I")){
				VAgreement_no.add("");
				VAgreement_rev_no.add("");
				Vcont_no.add("");
				Vcont_rev_no.add("");
				Vcont_typ.add("Interest");
			}
			else if(rset1.getString(7).equals("R")){
				Vcont_typ.add("Regas");
			}
		 }
		}
		}else{
			if(!customer_cd.equalsIgnoreCase(""))
			{
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
								 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,CHECKED_BY,APPROVED_BY"
								 + ",PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
								 //RG20191108 Commented for getting mapping_id + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,SUP_STATE_CODE,tDS_TAX_PERCENT,TDS_TAX_AMT " +
								// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "FROM DLNG_INVOICE_MST WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
								 + "CHECKED_FLAG='Y' AND PAY_RECV_AMT IS NOT NULL AND "
								 + "PAY_INSERT_BY IS NOT NULL AND (PDF_INV_DTL IS NOT NULL) "
								 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
				else
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
							 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,FINANCIAL_YEAR,TAX_STRUCT_CD,"
							 + "TAX_AMT_INR,INV_AMT_INR,SN_NO,EMP_CD,CHECKED_BY,APPROVED_BY,PDF_INV_DTL,PRINT_BY_ORI,"
							 + "PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,"
							//RG20191108 Commented for getting mapping_id + "TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
							 + "TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,PAY_RECV_AMT,SUP_STATE_CODE,tDS_TAX_PERCENT,TDS_TAX_AMT " +
							// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "FROM DLNG_INVOICE_MST WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
							 + "CHECKED_FLAG='Y' AND "
//							 + "PAY_RECV_AMT IS NULL AND "
//							 + "PAY_INSERT_BY IS NULL AND"
							 + " (PDF_INV_DTL IS NOT NULL) "
							 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
			}
			else
			{
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
								 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,CHECKED_BY,APPROVED_BY,"
								 + "PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
								 //+ "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,SUP_STATE_CODE,tDS_TAX_PERCENT,TDS_TAX_AMT " +
								// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "FROM DLNG_INVOICE_MST WHERE "+cnt_typ+" AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
								 + "CHECKED_FLAG='Y' AND PAY_RECV_AMT IS NOT NULL AND "
								 + "PAY_INSERT_BY IS NOT NULL AND (PDF_INV_DTL IS NOT NULL) "
								 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
				else
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
							 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
							 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,EMP_CD,CHECKED_BY,"
							 + "APPROVED_BY,PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
							 //+ "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
							 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,PAY_RECV_AMT,SUP_STATE_CODE,tDS_TAX_PERCENT,TDS_TAX_AMT " +
							// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "FROM DLNG_INVOICE_MST WHERE "+cnt_typ+" AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
							 + "CHECKED_FLAG='Y' AND "
							 //+ "PAY_RECV_AMT IS NULL AND PAY_INSERT_BY IS NULL AND 
							 +"(PDF_INV_DTL IS NOT NULL) "
							 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
			}
			
			//System.out.println("===fetch Invoice queryString123==Late payment=: "+queryString123);
			rset1=stmt1.executeQuery(queryString123);
			while(rset1.next())
			{

				Vdrcrcriteria.add("");
				Vdrcrflag.add("");
				if(invstatus.equalsIgnoreCase("PAID")){
					Vtds_tax_percent.add(rset1.getString(32)==null?"":rset1.getString(32));
					Vtds_tax_amt.add(rset1.getString(33)==null?"":rset1.getString(33));
				}else{
					Vtds_tax_percent.add(rset1.getString(30)==null?"":rset1.getString(30));
					Vtds_tax_amt.add(rset1.getString(31)==null?"":rset1.getString(31));
				}
				String payamt="",inv_take_flag="Y",sup_state_cd = "";String short_recv_="0";
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					payamt=rset1.getString(14)==null?"":rset1.getString(14);
					inv_take_flag="Y";
					//Vsup_state_cd.add(rset1.getString(31)==null?"":rset1.getString(31));
					//sup_state_cd = rset1.getString(31)==null?"":rset1.getString(31);
				}else{
					payamt=rset1.getString(28)==null?"":rset1.getString(28);
					//Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
					//sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
//					System.out.println("payamt---"+payamt);
					if(payamt.equals("")){
						inv_take_flag="Y";
					}else{
						if(rset1.getString(21)!=null)
						{
							double tdsper=Double.parseDouble(rset1.getString(21));
							double salesval=Double.parseDouble(rset1.getString(5));
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//double netrecv=invval-tdsval;
							String netrecv="";
							if(rset1.getString(30)!=null)
							{
								netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(31)));
							}else{
								netrecv=nf6.format(invval-tdsval);
							}
							double actval=Double.parseDouble(rset1.getString(28));
							short_recv_=nf6.format(Double.parseDouble(netrecv)-actval);
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv_=nf6.format(Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt));
						}
					//	System.out.println("short_recv_---"+short_recv_);
						if(Double.parseDouble(short_recv_) <= Double.parseDouble(Inv_roundoff+"")){
							
							//		System.out.println("in thisssssssss--->>"+rset1.getString(3)+"<<-->>"+Inv_roundoff+"<<---->>"+Double.parseDouble(short_recv_));
									inv_take_flag="N";
								}
								else{
									inv_take_flag="Y";
								}
					}
				}
				if(inv_take_flag.equals("Y")){
				cnt++;
				String temp_flag = "";
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					VPAY_NEW_INV_SEQ_NO.add(rset1.getString(28)==null?"":rset1.getString(28));
					VPAY_FLAG.add(rset1.getString(29)==null?"Y":rset1.getString(29));
					temp_flag = rset1.getString(29)==null?"Y":rset1.getString(29);
					Vsup_state_cd.add(rset1.getString(31)==null?"":rset1.getString(31));
					sup_state_cd = rset1.getString(31)==null?"":rset1.getString(31);
				} else {
					VPAY_NEW_INV_SEQ_NO.add(rset1.getString(25)==null?"":rset1.getString(25));
					VPAY_FLAG.add(rset1.getString(26)==null?"Y":rset1.getString(26));
					temp_flag = rset1.getString(26)==null?"Y":rset1.getString(26);
					Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
					sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
				}
				flag_inv.add("I");
				//for tcs
				String query="select tcs_amt from fms7_invoice_tcs_dtl where hlpl_inv_seq_no='"+rset1.getString(3)+"' and contract_type='"+rset1.getString(8)+"' and "
						+ "financial_year='"+rset1.getString(9)+"' and customer_cd='"+rset1.getString(2)+"' AND INVOICE_TYPE='SALES' AND FLAG='Y' and COMMODITY_TYPE='DLNG'"
						+ " and sup_state_code='"+sup_state_cd+"' ";		
				rset=stmt.executeQuery(query);
				if(rset.next()){
					Vtcs_amt.add(rset.getString(1)==null?"":rset.getString(1));
				}else{
					Vtcs_amt.add("");
				}
				
				//Hiren_20210621 for TDS calc.
				String tds_app_flag = "N";
				tds_app_amt = "0";
				if(rset1.getString(8).equals("S") || rset1.getString(8).equals("L")) {
					
					tds_app_flag = checkforTDSApplicable(rset1.getString(4),rset1.getString(2),rset1.getString(9),rset1.getString(3),"RLNG","SALES",sup_state_cd);
				}
				if(segment.equalsIgnoreCase("LTCORA_CN") || segment.equalsIgnoreCase("DLNG_SER") || segment.equalsIgnoreCase("DLNG_LM")) {
					Vtds_app_flag.add("Y");
					Vtds_app_amt.add(rset1.getString(5)==null?"0":rset1.getString(5));
				}else {
					Vtds_app_flag.add(tds_app_flag);
					Vtds_app_amt.add(tds_app_amt);
				}
				////////
				//
				
				////System.out.println("===JAVA===1");
				if(rset1.getString(1)!=null)
				{
					Vmonth.add(rset1.getString(1));
				}
				else
				{
					Vmonth.add("-");
				}
				
				if(rset1.getString(2)!=null)
				{
					Vcustomer_cd.add(rset1.getString(2));
				}
				else
				{
					Vcustomer_cd.add("-");
				}
				
				String queryString1 = "SELECT CUSTOMER_NAME,CUSTOMER_ABBR FROM FMS7_CUSTOMER_MST A WHERE CUSTOMER_CD='"+rset1.getString(2)+"'  AND " +
					  "A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_MST B " +
					  "WHERE A.customer_cd=B.customer_cd AND " +
					  "B.eff_dt<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY') and CUSTOMER_CD='"+rset1.getString(2)+"')";
				rset=stmt.executeQuery(queryString1);
				while(rset.next())
				{
					if(rset.getString(2)!=null)
					{
						Vcustomer_abbr.add(rset.getString(2));
						Vcustomer_name.add(rset.getString(1));
					}
					else
					{
						Vcustomer_abbr.add("-");
						Vcustomer_name.add("-");
					}
				}
				
				
				hlpl_inv_seq_no=rset1.getString(3);
				if(rset1.getString(3)!=null)
				{
					Vhlplinvseqno.add(rset1.getString(3));
				}
				else
				{
					Vhlplinvseqno.add("-");
				}
				
				Vdiff_tcs.add("0");
				Vdiff_tcs_flg.add("N");
				
				String tmp_fgsa_no="",tmp_fgsa_rev_no="",tmp_sn_no="",tmp_sn_rev_no="",TMP_QTY="";
				
				String queryString456="SELECT FGSA_NO,FGSA_REV_NO,SN_REV_NO,PLANT_SEQ_NO,TO_CHAR(PERIOD_START_DT,'DD/MM/YYYY'),"
						+ "TO_CHAR(PERIOD_END_DT,'DD/MM/YYYY'),EXCHG_RATE_CD,EXCHG_RATE_TYPE,CUST_INV_SEQ_NO,APPROVED_FLAG,TOTAL_QTY " + 
						 "FROM DLNG_INVOICE_MST WHERE HLPL_INV_SEQ_NO='"+hlpl_inv_seq_no+"' "
						 + "AND FINANCIAL_YEAR='"+rset1.getString(9)+"' AND "
						 + "CONTRACT_TYPE='"+rset1.getString(8)+"' AND "
						 + "INVOICE_DT=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy') "
						 + "AND FLAG='"+temp_flag+"'"
						 + " and sup_state_code='"+sup_state_cd+"' ";
				rset=stmt.executeQuery(queryString456);
				//System.out.println("---queryString456---"+queryString456);
				if(rset.next())
				{
					if(rset.getString(1)!=null)
						Vfgsa_no.add(rset.getString(1));
					else
						Vfgsa_no.add("-");
					
					if(rset.getString(2)!=null)
						Vfgsa_rev_no.add(rset.getString(2));
					else
						Vfgsa_rev_no.add("-");
						
					if(rset.getString(3)!=null)
						Vsn_rev_no.add(rset.getString(3));
					else
						Vsn_rev_no.add("-");
					
					if(rset.getString(4)!=null)
						Vplant_seq_no.add(rset.getString(4));
					else
						Vplant_seq_no.add("-");
					
					tmp_fgsa_no=rset.getString(1)==null?"":rset.getString(1);
					tmp_fgsa_rev_no=rset.getString(2)==null?"":rset.getString(2);
					tmp_sn_no=rset1.getString(13)==null?"":rset1.getString(13);
					tmp_sn_rev_no=rset.getString(3)==null?"":rset.getString(3);
					TMP_QTY=rset.getString(11)==null?"":rset.getString(11);
					
					String q1="SELECT NVL(A.plant_name,' ') FROM FMS7_CUSTOMER_PLANT_DTL A " +
							   "WHERE A.customer_cd="+rset1.getString(2)+" AND A.seq_no="+rset.getString(4)+" " +
							   "AND A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_PLANT_DTL B " +
							   "WHERE A.customer_cd=B.customer_cd AND A.seq_no=B.seq_no " +
							   "AND B.eff_dt<=TO_DATE('"+rset.getString(5)+"','DD/MM/YYYY'))";
					
					//String q1="SELECT PLANT_NAME FROM FMS7_CUSTOMER_PLANT_DTL  WHERE CUSTOMER_CD='"+rset1.getString(2)+"' AND SEQ_NO='"+rset.getString(4)+"'";
					////System.out.println("---PLANT_NAME QUERY---"+q1);
					rset6=stmt6.executeQuery(q1);
					
					while(rset6.next())
					{
						if(rset6.getString(1)!=null)
							Vplant_seq_nm.add(rset6.getString(1));
						else
							Vplant_seq_nm.add("-");
						
						////System.out.println("---Vplant_seq_nm---:"+rset6.getString(1));
					}
					
					if(rset.getString(5)!=null)
						Vperiod_st_dt.add(rset.getString(5));
					else
						Vperiod_st_dt.add("-");
						
					if(rset.getString(6)!=null)
						Vperiod_end_dt.add(rset.getString(6));
					else
						Vperiod_end_dt.add("-");
					
					if(rset.getString(7)!=null)
						Vexchg_rate_cd.add(rset.getString(7));
					else
						Vexchg_rate_cd.add("-");
					
					if(rset.getString(8)!=null)
						Vexchg_rate_type.add(rset.getString(8));
					else
						Vexchg_rate_type.add("-");
					
					if(rset.getString(9)!=null)
						Vcust_inv_seq_no.add(rset.getString(9));
					else
						Vcust_inv_seq_no.add("-");
					
					if(rset.getString(10)!=null)
						Vapproved_flag.add(rset.getString(10));
					else
						Vapproved_flag.add("-");
					
					String queryString_agr_base="";
					if(rset1.getString(8).equals("S")){
						queryString_agr_base="SELECT FGSA_BASE FROM DLNG_FLSA_MST WHERE FLSA_NO='"+rset.getString(1)+"' AND REV_NO='"+rset.getString(2)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
						}
					}else if(rset1.getString(8).equals("L")){
						queryString_agr_base="SELECT TENDER_BASE FROM DLNG_TENDER_MST WHERE TENDER_NO='"+rset.getString(1)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
						}
					}
				}
				
				VAgreement_base.add(agr_base);
				if(rset1.getString(4)!=null)
				{
					Vinv_dt.add(rset1.getString(4));
				}
				else
				{
					Vinv_dt.add("-");
				}
				
				if(agr_base.equalsIgnoreCase("D")){

					String queryString_tr="";
					String transp_charges="";
					String temp_grossamt="";
					if(rset1.getString(8).equals("S")){
						queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+tmp_fgsa_no+"' AND FGSA_REV_NO='"+tmp_fgsa_rev_no+"' AND SN_NO='"+tmp_sn_no+"' AND SN_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
					}else if(rset1.getString(8).equals("L")){
						queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+tmp_fgsa_no+"' AND LOA_NO='"+tmp_sn_no+"' AND LOA_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
						
					}
					rset2=stmt2.executeQuery(queryString_tr);
					//System.out.println("queryString--"+queryString_tr);
					if(rset2.next()){
						transp_charges=rset2.getString(1)==null?"":rset2.getString(1);
					}
					//System.out.println("---rset.getString(7)--"+rset1.getString(5)+"--transp_charges--"+transp_charges+"--TMP_QTY---"+TMP_QTY);
					if(!transp_charges.equals("")){
						temp_grossamt=""+(Double.parseDouble(rset1.getString(5)==null?"":rset1.getString(5))+Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
						Vsales_value.add(temp_grossamt);
						gross_amt_inr=temp_grossamt;
						gross_amt_trans=""+(Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
						//System.out.println("---temp====="+temp_grossamt);
					}else{
						Vsales_value.add(rset1.getString(5));
						gross_amt_inr=rset1.getString(5);
						gross_amt_trans=rset1.getString(5)==null?"":rset1.getString(5);
					}
				}else{
				
					if(rset1.getString(5)!=null)
					{
						Vsales_value.add(rset1.getString(5));
					}
					else
					{
						Vsales_value.add("-");
					}
						
					gross_amt_inr=rset1.getString(5);
				}
				Vgross_trans.add(gross_amt_trans);
				if(rset1.getString(6)!=null)
				{
					Vinv_value.add(rset1.getString(6));
				}
				else
				{
					Vinv_value.add("-");
				}
				
				if(rset1.getString(7)!=null)
				{
					Vdue_dt.add(rset1.getString(7));
				}
				else
				{
					Vdue_dt.add("-");
				}
				if(rset1.getString(8)!=null)
				{
					Vcont_type.add(rset1.getString(8));
				}
				else
				{
					Vcont_type.add("-");
				}
				
				if(rset1.getString(9)!=null)
				{
					Vfinancial_year.add(rset1.getString(9));
				}
				else
				{
					Vfinancial_year.add("-");
				}
				
				if(rset1.getString(10)!=null)
				{
					Vtax_str_cd.add(rset1.getString(10));
				}
				else
				{
					Vtax_str_cd.add("-");
				}
				
				if(rset1.getString(11)!=null)
				{
					Vtaxamtinr.add(rset1.getString(11));
				}
				else
				{
					Vtaxamtinr.add("-");
				}
				
				if(rset1.getString(12)!=null)
				{
					Vinvamtinr.add(rset1.getString(12));
				}
				else
				{
					Vinvamtinr.add("-");
				}
				
				invamtinr=rset1.getString(12);
				sn_no=rset1.getString(13);
				taxvalue=rset1.getString(11);
				fyr=rset1.getString(9);
				total_amt_inr=Double.parseDouble(rset1.getString(6));
				tax_str_cd=rset1.getString(10)==null?"":rset1.getString(10);
				
				Vsn_no.add(sn_no);
				
				//String invseq=contract_type+":"+financial_year+":"+hlpl_inv_seq_no+":%";
				String invseq=rset1.getString(8)+":"+rset1.getString(9)+":"+rset1.getString(3)+":"+rset1.getString(4);
				if(!invseq.equalsIgnoreCase(""))
				{
					Vinvseqno.add(invseq);
				}
				else
				{
					Vinvseqno.add("-");
				}
				
				////////tax_adj_amt////////
				
				String q1="select flag,amount from fms7_inv_compo_dtl where price_cd IN ('6','10','11','12','13') and inv_seq_no='"+invseq+"'";
				////System.out.println("JAVA check adjustmn query q1: "+q1);
				rset2=stmt2.executeQuery(q1);
				if(rset2.next())
				{
					if(rset2.getString(1)!=null)
					{
						taxadvflag=rset2.getString(1);
					}
					else
					{
						taxadvflag="N";
					}
					
					Vtax_adj_flag.add(taxadvflag);
								
					if(rset2.getString(2)!=null)
					{
						tax_adj_amt=nf.format(Double.parseDouble(rset2.getString(2)));
					}
					else
					{
						tax_adj_amt="0";
					}
				}
				else
				{
					taxadvflag="N";
					tax_adj_amt="0";
					Vtax_adj_flag.add(taxadvflag);
				}
				
				////System.out.println("---JAVA here taxadvflag---: "+taxadvflag+"---tax_adj_amt---: "+tax_adj_amt);	
				String mapping_id="%"+rset1.getString(2)+"-"+tmp_fgsa_no+"-%-"+tmp_sn_no+"-%-"+rset1.getString(8)+"%";
				double tot_adjust_amt=0,balance_adjust_amt=0;
				queryString = "select price_rate,currency_cd,flag " +
						"FROM fms7_SALES_cont_price_dtl WHERE mapping_id like '"+mapping_id+"' AND " +
						"price_cd='1' AND FLAG='Y' and pay_type='AP' ";
				//System.out.println("Fetching flag of sn..ad."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					VADJUST_FLAG_SN.add(rset2.getString(3)==null?"N":rset2.getString(3));
					tot_adjust_amt=rset2.getDouble(1);
					VADJUST_AMT_SN.add(rset2.getString(1)==null?"":rset2.getString(1));
					VADJUST_CUR_SN.add(rset2.getString(2));
					if(rset1.getString(8).equals("S")){
						queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
								+ "CONTRACT_NO='"+tmp_sn_no+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND AGR_REV_NO='"+rset.getString(2)+"' "
								+ "AND CONTRACT_TYPE='"+rset1.getString(8)+"' ";
						//System.out.println("Fetching flag of sn..."+queryString1);
						
					}else{
						queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
								+ "CONTRACT_NO='"+tmp_sn_no+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' ";
	//					System.out.println("Fetching flag of sn..."+queryString);
						
					}
					rset3=stmt3.executeQuery(queryString1);	
					if(rset3.next()){
						VCFORM_FLAG.add(rset3.getString(1)==null?"":rset3.getString(1));
					}else{
						VCFORM_FLAG.add("");
					}
				}else{
					VADJUST_FLAG_SN.add("");
					VADJUST_AMT_SN.add("");
					VADJUST_CUR_SN.add("");
					VCFORM_FLAG.add("");
				}
				queryString = "select sum(adjusted_amt) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
							+ "AND adjust_FLAG='Y' and commodity_type='DLNG' and contract_type='"+rset1.getString(8)+"'  ";
//				System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					balance_adjust_amt=tot_adjust_amt-rset2.getDouble(1);
				}else{
					balance_adjust_amt=tot_adjust_amt;
				}
				queryString = "select sum(hold_amount) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
						+ "AND commodity_type='DLNG' and contract_type='"+rset1.getString(8)+"' and release_flag='Y' ";
			//System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					balance_adjust_amt+=rset2.getDouble(1);
				}
				VADJUST_balc_SN.add(nf6.format(balance_adjust_amt));
				/////HERE
				if(!payamt.equals("")){
					if(invstatus.equalsIgnoreCase("PAID")){
						if(rset1.getString(24)!=null)
						{
							
							double tdsper=Double.parseDouble(rset1.getString(24));
							double salesval=0;
							if(agr_base.equalsIgnoreCase("D")){
								salesval=Double.parseDouble(gross_amt_trans);
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//double netrecv=invval-tdsval;
							double netrecv=0;
							
							if(rset1.getString(32)!=null)
							{
								netrecv=invval-tdsval-Double.parseDouble(rset1.getString(33));
							}else{
								netrecv=invval-tdsval;
							}
							double actval=Double.parseDouble(payamt);
							short_recv=netrecv-actval;
							//System.out.println("short_recv_-in else-->>"+actval+"<<--netrecv-->>"+netrecv);
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt);
						}
					}else{

						if(rset1.getString(21)!=null)
						{
							double tdsper=Double.parseDouble(rset1.getString(21));
							//double salesval=Double.parseDouble(rset1.getString(5));
							double salesval=0;
							if(agr_base.equalsIgnoreCase("D")){
								salesval=Double.parseDouble(gross_amt_trans);
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//double netrecv=invval-tdsval;
							String netrecv="";
							if(rset1.getString(30)!=null)
							{
								netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(31)));
							}else{
								netrecv=nf6.format(invval-tdsval);
							}
							double actval=Double.parseDouble(payamt);
							short_recv=Double.parseDouble(netrecv)-actval;
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt);
						}
					
					}
					Vpay_short_recv_amt.add(short_recv);
				}else{
					Vpay_short_recv_amt.add("-");
				}
				Vgross_trans_inr.add(gross_amt_trans);
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					if(rset1.getString(14)!=null)
					{
						Vpay_actual_recv_amt.add(rset1.getString(14));
					}
					else
					{
						Vpay_actual_recv_amt.add("-");
					}
					
					if(rset1.getString(15)!=null)
					{
						Vpay_recv_dt.add(rset1.getString(15));
					}
					else
					{
						Vpay_recv_dt.add("-");
					}
					
					if(rset1.getString(16)!=null)
					{
						Vpay_remark.add(rset1.getString(16));
					}
					else
					{
						Vpay_remark.add("-");
					}
					
					/*if(rset1.getString(24)!=null)
					{
						double tdsper=Double.parseDouble(rset1.getString(24));
						double salesval=Double.parseDouble(rset1.getString(5));
						double tdsval=(tdsper*salesval)/100;
						double invval=Double.parseDouble(rset1.getString(6));
						double netrecv=invval-tdsval;
						double actval=Double.parseDouble(rset1.getString(14));
						short_recv=netrecv-actval;
					}
					else
					{
						short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(rset1.getString(14));
					}
					
					Vpay_short_recv_amt.add(short_recv+"");*/
				
					Vpayflag.add("PAID");
					
					if(rset1.getString(17)!=null)
						Vinv_gen_by_cd.add(rset1.getString(17));
					else
						Vinv_gen_by_cd.add("00");
						
					if(rset1.getString(18)!=null)
						Vchecked_by_cd.add(rset1.getString(18));
					else
						Vchecked_by_cd.add("00");
					
					if(rset1.getString(19)!=null)
						Vapproved_by_cd.add(rset1.getString(19));
					else
						Vapproved_by_cd.add("00");
					
					if(rset1.getString(20)!=null)
					{
						Vpdf_inv_dtl.add(rset1.getString(20));
					}
					else
					{
						Vpdf_inv_dtl.add("-");
					}
					
					pdf_inv_dtl=rset1.getString(20);
					
					if(rset1.getString(21)!=null)
						print_by_ori=rset1.getString(21);
					else
						print_by_ori="00";
					
					if(rset1.getString(22)!=null)
						print_by_dup=rset1.getString(22);
					else
						print_by_dup="00";
					
					if(rset1.getString(23)!=null)
						print_by_tri=rset1.getString(23);
					else
						print_by_tri="00";
					
					////System.out.println("rset1.getString(24): "+rset1.getString(24));
					
					if(rset1.getString(24)!=null)
						Vtds_per.add(rset1.getString(24));
					else
						Vtds_per.add("-");
					
					if(pdf_inv_dtl.contains("T"))
					{
						Vprint_by_cd.add(print_by_tri);
					}
					else if(pdf_inv_dtl.contains("D"))
					{
						Vprint_by_cd.add(print_by_dup);
					}
					else if(pdf_inv_dtl.contains("O"))
					{
						Vprint_by_cd.add(print_by_ori);
					}
					else
					{
						Vprint_by_cd.add("-");
					}
					
					if(rset1.getString(25)!=null)
						Vpay_update_dt.add(rset1.getString(25));
					else
						Vpay_update_dt.add("-");
					
					if(rset1.getString(26)!=null)
						Vpay_update_cnt.add(rset1.getString(26));
					else
						Vpay_update_cnt.add("-");
					
					if(taxadvflag.equalsIgnoreCase("Y"))
					{
						////System.out.println("HERE IN IF");
						if(rset1.getString(27)!=null)
						{
							double t=Double.parseDouble(rset1.getString(27))-Double.parseDouble(tax_adj_amt);
							////System.out.println("IN IF---t---: "+t);
							Vtottax.add(t);
						}	
						else
						{
							Vtottax.add("-");
						}
					}
					else
					{
						if(rset1.getString(27)!=null)
						{
							////System.out.println("IN ELSE---rset---: "+rset1.getString(27));
							Vtottax.add(rset1.getString(27));
						}	
						else
						{
							Vtottax.add("-");
						}
					}
				}
				else
				{
					Vpay_actual_recv_amt.add("-");
					Vpay_recv_dt.add("-");
					Vpay_remark.add("-");
					//Vpay_short_recv_amt.add("-");
					Vpayflag.add("UNPAID");
					
					if(rset1.getString(14)!=null)
						Vinv_gen_by_cd.add(rset1.getString(14));
					else
						Vinv_gen_by_cd.add("00");
					
					if(rset1.getString(15)!=null)
						Vchecked_by_cd.add(rset1.getString(15));
					else
						Vchecked_by_cd.add("00");
						
					if(rset1.getString(16)!=null)
						Vapproved_by_cd.add(rset1.getString(16));
					else
						Vapproved_by_cd.add("00");
						
					if(rset1.getString(17)!=null)
					{
						Vpdf_inv_dtl.add(rset1.getString(17));
					}
					else
					{
						Vpdf_inv_dtl.add("-");
					}
					
					pdf_inv_dtl=rset1.getString(17);
					
					if(rset1.getString(18)!=null)
						print_by_ori=rset1.getString(18);
					else
						print_by_ori="00";
					
					if(rset1.getString(19)!=null)
						print_by_dup=rset1.getString(19);
					else
						print_by_dup="00";
					
					if(rset1.getString(20)!=null)
						print_by_tri=rset1.getString(20);
					else
						print_by_tri="00";
						
					if(rset1.getString(21)!=null)
					{
						Vtds_per.add(rset1.getString(21));
					}
					else
					{
						Vtds_per.add("-");
					}
					//System.out.println("===pdf-inv_dtl=="+pdf_inv_dtl);
					
					if(pdf_inv_dtl.contains("T"))
					{
						Vprint_by_cd.add(print_by_tri);
					}
					else if(pdf_inv_dtl.contains("D"))
					{
						Vprint_by_cd.add(print_by_dup);
					}
					else if(pdf_inv_dtl.contains("O"))
					{
						Vprint_by_cd.add(print_by_ori);
					}
					else
					{
						Vprint_by_cd.add("-");
					}
					
					if(rset1.getString(22)!=null)
						Vpay_update_dt.add(rset1.getString(22));
					else
						Vpay_update_dt.add("-");
					
					if(rset1.getString(23)!=null)
						Vpay_update_cnt.add(rset1.getString(23));
					else
						Vpay_update_cnt.add("-");
					
					if(taxadvflag.equalsIgnoreCase("Y"))
					{
						if(rset1.getString(24)!=null)
						{
							double t=Double.parseDouble(rset1.getString(24))-Double.parseDouble(tax_adj_amt);
							Vtottax.add(t);
						}	
						else
						{
							Vtottax.add("-");
						}
					}
					else
					{
						if(rset1.getString(24)!=null)
						{
							Vtottax.add(rset1.getString(24));
						}	
						else
						{
							Vtottax.add("-");
						}
					}
				}
				
				////System.out.println("===JAVA===2");
				/////////Vhlpl_inv_seq//////////
				
				if(Integer.parseInt(rset1.getString(3))<10)
				{
					hlpl_inv_no_disp="000"+rset1.getString(3)+"/"+rset1.getString(9);
				}
				else if(Integer.parseInt(rset1.getString(3))<100) 
				{
					hlpl_inv_no_disp="00"+rset1.getString(3)+"/"+rset1.getString(9);
				}
				else if(Integer.parseInt(rset1.getString(3))<1000) 
				{
					hlpl_inv_no_disp="0"+rset1.getString(3)+"/"+rset1.getString(9);
				}
				else
				{
					hlpl_inv_no_disp=rset1.getString(3)+"/"+rset1.getString(9);
				}
				
				if(!hlpl_inv_no_disp.equalsIgnoreCase(""))
				{
					Vhlpl_inv_seq.add(hlpl_inv_no_disp);
				}
				else
				{
					Vhlpl_inv_seq.add("-");
				}
				
				////System.out.println("===JAVA===3");
				
				///////////XML_GEN_FLAG/////////
				
				String queryString2 = "SELECT XML_GEN_FLAG,to_char(APPROVAL_DT,'dd/mm/yyyy') FROM FMS7_ACCOUNT_APPROVED_DTL " +
						  "WHERE INV_CARGO_NO='"+hlpl_inv_no_disp+"' AND JOURNAL_TYPE='FMSSL' " +
						  "AND CONTRACT_TYPE='"+contract_type+"' ";
					//	//System.out.println("Confirmed FMS7_PROV_SELLER_PAY Details Query = "+queryString);
					rset = stmt.executeQuery(queryString2);
					if(rset.next())
					{
						xml_gen_flag=rset.getString(1)==null?"N":rset.getString(1);
						approval_dt=rset.getString(2)==null?"":rset.getString(2);
						Vxml_gen_flag.add(xml_gen_flag);
					}
					else
					{
						xml_gen_flag="N";
						approval_dt="";
						Vxml_gen_flag.add(xml_gen_flag);
					}
						
					////System.out.println("===JAVA===4");
					
					///tax_adj
					
					////System.out.println("===JAVA===5");
					
					///////////inv_adj_amt///////////
					
					String q2="select flag,amount from fms7_inv_compo_dtl where price_cd='1' and inv_seq_no='"+invseq+"'";
					////System.out.println("JAVA check adjustmn query q2: "+q2);
					rset3=stmt3.executeQuery(q2);
					if(rset3.next())
					{
						if(rset3.getString(1)!=null)
						{
							advflag=rset3.getString(1);
						}
						else
						{
							advflag="N";
						}
						
						Vinv_adj_flag.add(advflag);
						
						if(rset3.getString(2)!=null)
						{
							inv_adj_amt=nf.format(Double.parseDouble(rset3.getString(2)));
						}
						else
						{
							inv_adj_amt="0";
						}
					}
					else
					{
						advflag="N";
						inv_adj_amt="0";
						Vinv_adj_flag.add(advflag);
					}
					////System.out.println("---JAVA here advflag: "+advflag);
								
					////System.out.println("===JAVA===6");
					
					////////////inv_amt///////////
					
					String inv_amt="";
					if(advflag.equalsIgnoreCase("Y") && taxadvflag.equalsIgnoreCase("Y"))
					{
						//query2="select INV_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
						inv_amt=invamtinr;
					}
					else
					{
						//query2="select GROSS_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
						inv_amt=gross_amt_inr;
					}
					////System.out.println("---JAVA inv_amt---: "+inv_amt);
							
					////System.out.println("===JAVA===7");
					
					String amt = "0";
					
					String map_id=contract_type+":"+fyr+":"+hlpl_inv_seq_no+":%";
					Vmapid.add(map_id);
					
					if(contract_type.equalsIgnoreCase("T") || contract_type.equalsIgnoreCase("C"))		//ADDED FOR LTCORA AND CN
					{
						// String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no like" +
						//	" '"+map_id+"'and price_cd='6' and (flag_temp not like 'T' or flag_temp is null)";
						
						String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no='"+invseq+"' and price_cd IN ('6','10','11','12','13') "
								+ "and (flag_temp not like 'T' or flag_temp is null)";
						
						rset4=stmt4.executeQuery(queryTax);
						////System.out.println("---JAVA queryTax---: "+queryTax);
						if(rset4.next())
						{
							amt = ""+java.text.NumberFormat.getInstance().parse(rset4.getString(1));
							Adj_Tax_amt=amt;	
							taxvalue = ""+(Double.parseDouble(taxvalue) - Double.parseDouble(amt));
						}
					}
					
					////System.out.println("===JAVA===8");
					
					String queryString12 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
				 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
					
					////System.out.println("===VTAXAMT QUERY===: "+queryString12);
					rset5=stmt5.executeQuery(queryString12);
					////System.out.println("===JAVA===9");
					int cnt1=0;
					String tx_cd="",tx_amt="",tx_nm="";
					while(rset5.next())
					{
						tax_cd=rset5.getString(1);
						tax_on=rset5.getString(3);
						////System.out.println("---tax_on---"+tax_on);
						if(rset5.getString(3).equals("1")) 
						{
							cnt1++;
							////System.out.println("===IN IF=== "+cnt1);
							tax_amt = (Double.parseDouble(gross_amt_inr)*Double.parseDouble(rset5.getString(2)))/100;
							////System.out.println(Double.parseDouble(gross_amt_inr)+"---total_amt_inr-IN IF--"+tax_amt);
							////System.out.println("---rset5.getString(2)-IN IF--"+rset5.getString(2));
						}
						else if(rset5.getString(3).equals("2"))
						{
							cnt1++;
							////System.out.println("===IN ELSE IF=== "+cnt1);
							String q3 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
										   "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
										   "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
										   "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) AND A.tax_code='"+rset5.getString(4)+"'";
							
							////System.out.println("===TAXAMT Query q3 === "+q3);
							rset6=stmt6.executeQuery(q3);
				 	 		
							if(rset6.next())
				 	 		{
				 	 			if(rset6.getString(3).equals("1"))
				 				{
				 					tax_amt = (Double.parseDouble(gross_amt_inr)*Double.parseDouble(rset6.getString(2)))/100;
				 				}	
				 	 			////System.out.println("---total_amt_inr-IN ELSE IF--"+total_amt_inr);
								////System.out.println("---rset5.getString(2)-IN ELSE IF--"+rset5.getString(2));
				 	 			tax_amt = (tax_amt*Double.parseDouble(rset5.getString(2)))/100;
				 	 		}
				 	 		else
				 	 		{
				 	 			tax_amt=0;
				 	 		}
						}
						else
						{
							tax_amt = 0;
						}
						////System.out.println("===JAVA===9");
						
						////////////////////////////BK:SB20151203//////////////////
			 			
						if(!Adj_Tax_amt.equalsIgnoreCase("") && taxadvflag.equalsIgnoreCase("Y"))
			 			{
							if(Double.parseDouble(Adj_Tax_amt)>0)
				 			{
								////System.out.println("---Adj Amt---: "+Adj_Tax_amt);
				 				tax_amt = tax_amt - Double.parseDouble(Adj_Tax_amt);
				 				Adj_Tax_amt = "0";
				 			}
							else
				 			{
								if(tax_on.equalsIgnoreCase("2"))
								{
									//taxamt=nf.format(tax_amt)+"";
									//Tot_Tax_amt = Tot_Tax_amt + Double.parseDouble(taxamt);
								}
				 			}
			 			}
						
						////System.out.println("===IN WHILE================= "+cnt1);
						
						///////////////////////////////////////////////////////
						queryString6="select sht_nm from fms7_tax_mst where tax_code='"+tax_cd+"'";
						rset6=stmt6.executeQuery(queryString6);
						while(rset6.next())
						{
							tax_nm=rset6.getString(1);
						}
						
						tx_cd+="@"+tax_cd;
						tx_amt+="@"+nf.format(tax_amt);
						tx_nm+="@"+tax_nm;
						double tax_diff=0;
						//int tax_fact=Integer.parseInt(rset5.getString(2));
						double tax_fact=Double.parseDouble(rset5.getString(2));
						if(tax_fact<tax_cform){
							tax_diff=tax_cform-tax_fact;
							tax_diff_val.add(tax_diff);
						}else{
							tax_diff_val.add("");
						}
						
						////System.out.println("===JAVA tax_amt after=== "+tax_amt);
					}
					Vtax_code.add(tx_cd.replaceFirst("@", ""));  	
					Vtax_amt.add(tx_amt.replaceFirst("@", ""));
					Vtax_nm.add(tx_nm.replaceFirst("@", ""));
					////System.out.println("Vtax_code-"+Vtax_code);
					////System.out.println("Vtax_amt-"+Vtax_amt);
					
					String q123 = "SELECT COUNT(*) FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
				 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
					rset=stmt.executeQuery(q123);
					while(rset.next())
					{
						//cnt1=rset.getInt(1);
						int count=rset.getInt(1);
						Vtaxcnt.add(count);
					}
					
					//////////////////////////////////////////////////////////
					///fOR GETTING REFERENCE NO FOR CONTRACT_NO AND AGREEMENT_NO///
					String mapp_id="";
					if(rset1.getString(8).equals("C") || rset1.getString(8).equals("M") || rset1.getString(8).equals("B") || rset1.getString(8).equals("E")){
						if(invstatus.equalsIgnoreCase("PAID"))
						{
							mapp_id=rset1.getString(30)==null?"":rset1.getString(30);
						}else{
							mapp_id=rset1.getString(27)==null?"":rset1.getString(27);
						}
						//System.out.println("--mapp_id--"+mapp_id);
						String temp_mapp_id[]=mapp_id.split("-");
						VAgreement_no.add(temp_mapp_id[1]+"");
						VAgreement_rev_no.add(temp_mapp_id[2]+"");
						Vcont_no.add(temp_mapp_id[3]+"");
						Vcont_rev_no.add(temp_mapp_id[4]+"");
						if(""+temp_mapp_id[3]!=""){
							int temp_cont_no=Integer.parseInt(temp_mapp_id[3]+"");
							if(temp_cont_no>999){
								Vcont_typ.add("LTCORA (Period)");
							}else{
								Vcont_typ.add("LTCORA (CN)");
							}
						}else{
							Vcont_typ.add("LTCORA (CN)");
						}
						
					}else if(rset1.getString(8).equals("S")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("SN");
					}else if(rset1.getString(8).equals("L")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("LOA");
					}
					else if(rset1.getString(8).equals("I")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("Interest");
					}
					else if(rset1.getString(8).equals("R")){
						Vcont_typ.add("Regas");
					} else if(rset1.getString(8).equals("V")){
						if(invstatus.equalsIgnoreCase("PAID"))
						{
							mapp_id=rset1.getString(30)==null?"":rset1.getString(30);
						}else{
							mapp_id=rset1.getString(27)==null?"":rset1.getString(27);
						}
						//System.out.println("--mapp_id--"+mapp_id);
						String temp_mapp_id[]=mapp_id.split("-");
						VAgreement_no.add(temp_mapp_id[1]+"");
						VAgreement_rev_no.add(temp_mapp_id[2]+"");
						Vcont_no.add(temp_mapp_id[3]+"");
						Vcont_rev_no.add(temp_mapp_id[4]+"");
//						if(""+temp_mapp_id[3]!=""){
//							int temp_cont_no=Integer.parseInt(temp_mapp_id[3]+"");
//							if(temp_cont_no>999){
//								Vcont_typ.add("LTCORA (Period)");
//							}else{
//								Vcont_typ.add("LTCORA (CN)");
//							}
//						}else{
							Vcont_typ.add("DLNG_SER");
						//}
					}
					
				}
					//////
				
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	public void fetch_Invoice_Details() 						//BK20160309
	{

		try
		{
			String from_dt="";
			String to_dt="";
			if(btnFlag.equalsIgnoreCase("Y"))
			{
				from_dt="01/"+month+"/"+year;
				
				queryString="Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
				////System.out.println("Last Date Of The Selected Month = "+queryString);
				rset=stmt.executeQuery(queryString);			
				if(rset.next())
				{
					to_dt=rset.getString(1)==null?"0":rset.getString(1);
				}
				////System.out.println("From date "+from_dt+" To Date= "+to_dt);
				
				queryString="Select To_char(to_date('"+from_dt+"','dd/mm/yyyy'),'Month') from dual";
				////System.out.println("From Selected Month = "+queryString);
				rset=stmt.executeQuery(queryString);			
				if(rset.next())
				{
					mon=rset.getString(1)==null?"0":rset.getString(1);
				}
				
				queryString="Select To_char(to_date('"+to_dt+"','dd/mm/yyyy'),'Month') from dual";
				////System.out.println("To The Selected Month = "+queryString);
				rset=stmt.executeQuery(queryString);			
				if(rset.next())
				{
					to_mon=rset.getString(1)==null?"0":rset.getString(1);
				}
				btnFlag="Y";
			}
			else
			{
				
				//RG20191031 segment="LTCORA_CN";
				if(segment.equalsIgnoreCase("LTCORA_CN")){
					segment="LTCORA_CN";
				}else if(segment.equalsIgnoreCase("SALES")){
					segment="SALES";
				}else if(segment.equalsIgnoreCase("REGAS")){
					segment="REGAS";
				}else if(segment.equalsIgnoreCase("LOA")){
					segment="LOA";
				}else if(segment.equalsIgnoreCase("SDLNG")){
					segment="SDLNG";
				}else if(segment.equalsIgnoreCase("LDLNG")){
					segment="LDLNG";
				}
				else if(segment.equalsIgnoreCase("DLNG_SER")){
					segment="DLNG_SER";
				}else if(segment.equalsIgnoreCase("DLNG_LM")){
					segment="DLNG_LM";
				}
				btnFlag="Y";
				
				from_dt="01/"+month+"/"+year;
				
				queryString="Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
				////System.out.println("Last Date Of The Selected Month = "+queryString);
				rset=stmt.executeQuery(queryString);			
				if(rset.next())
				{
					to_dt=rset.getString(1)==null?"0":rset.getString(1);
				}
				////System.out.println("From date "+from_dt+" To Date= "+to_dt);
				
				queryString="Select To_char(to_date('"+from_dt+"','dd/mm/yyyy'),'Month') from dual";
				////System.out.println("From Selected Month = "+queryString);
				rset=stmt.executeQuery(queryString);			
				if(rset.next())
				{
					mon=rset.getString(1)==null?"0":rset.getString(1);
				}
				
				queryString="Select To_char(to_date('"+to_dt+"','dd/mm/yyyy'),'Month') from dual";
				////System.out.println("To The Selected Month = "+queryString);
				rset=stmt.executeQuery(queryString);			
				if(rset.next())
				{
					to_mon=rset.getString(1)==null?"0":rset.getString(1);
				}
				
			}
			
			////System.out.println("-JAVA--from_dt---:"+from_dt);
			////System.out.println("-JAVA--to_dt---:"+to_dt);
			////System.out.println("-JAVA--mon---:"+mon);
			////System.out.println("-JAVA--to_mon---:"+to_mon);
			String cnt_typ="";
			
			if(segment.equalsIgnoreCase("SALES"))
			{
				contract_type="S";
				cnt_typ="contract_type in ('S')"; 
			}
			else if(segment.equalsIgnoreCase("REGAS"))
			{
				contract_type="R";
				cnt_typ="contract_type in ('R')";
			}
			else if(segment.equalsIgnoreCase("LTCORA_CN"))
			{
				contract_type="C";
				cnt_typ="contract_type in ('C','T','M','B','E')";
			} 
			else if(segment.equalsIgnoreCase("LOA"))
			{
				contract_type="L";
				cnt_typ="contract_type in ('L')";
			}
			else if(segment.equalsIgnoreCase("Interest"))
			{
				contract_type="I";
				cnt_typ="contract_type in ('I')";
			}
			else if(segment.equalsIgnoreCase("SDLNG"))
			{
				contract_type="S";
				cnt_typ="contract_type in ('S')";
			}else if(segment.equalsIgnoreCase("LDLNG"))
			{
				contract_type="L";
				cnt_typ="contract_type in ('L')";
			}
			else if(segment.equalsIgnoreCase("DLNG_SER"))
			{
				contract_type="V";
				cnt_typ="contract_type in ('V')";
			}else if(segment.equalsIgnoreCase("DLNG_LM"))
			{
				contract_type="M";
				cnt_typ="contract_type in ('M')";
			}
			else if(segment.equalsIgnoreCase("DLNG_Interest"))
			{
				contract_type="I";
				cnt_typ="contract_type in ('I')";
			}
			
			Vector Vadvflag=new Vector();
			Vector Vmapid=new Vector();
			
			String tax_cd="";
			String tax_on="";
			String tax_nm="";
			
			double tax_amt=0;
			double adj_tax_inr=0;
			String Adj_Tax_amt="0";
			//String amt="";
			String hlpl_inv_no_disp="";
			String xml_gen_flag="";
			String approval_dt="";
			String tax_adj_amt="";
			String taxadvflag="";
			String inv_adj_amt="";
			String advflag="";
			String sn_no="";
			String taxvalue="";
			String gross_amt_inr="";
			String gross_amt_trans="";
			String invamtinr="";
			String fyr="";
			double total_amt_inr=0;
			String tax_str_cd="";
			
			int cnt=0;
			String queryString123="";
			String pdf_inv_dtl="";
			String print_by_ori="";
			String print_by_dup="";
			String print_by_tri="";
			double short_recv=0;
			////System.out.println("---invstatus---:"+invstatus);
			////System.out.println("---customer_cd---:"+customer_cd);
		if(segment.equalsIgnoreCase("SDLNG") || segment.equalsIgnoreCase("LDLNG") || segment.equalsIgnoreCase("DLNG_SER") || segment.equalsIgnoreCase("DLNG_LM")|| segment.equalsIgnoreCase("DLNG_Interest")){
			fetch_DLNG_Invoice_Details();
		}else{
			if(!customer_cd.equalsIgnoreCase(""))
			{
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
								 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,CHECKED_BY,APPROVED_BY"
								 + ",PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
								 //RG20191108 Commented for getting mapping_id + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,"
								 + "SUP_STATE_CODE,TDS_TAX_PERCENT,TDS_TAX_AMT,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,gross_Amt_transportation " +
								// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "FROM FMS7_INVOICE_MST WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
								 + "CHECKED_FLAG='Y' AND PAY_RECV_AMT IS NOT NULL AND "
								 + "PAY_INSERT_BY IS NOT NULL AND (PDF_INV_DTL IS NOT NULL) "
								 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
				else
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
							 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,FINANCIAL_YEAR,TAX_STRUCT_CD,"
							 + "TAX_AMT_INR,INV_AMT_INR,SN_NO,EMP_CD,CHECKED_BY,APPROVED_BY,PDF_INV_DTL,PRINT_BY_ORI,"
							 + "PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,"
							//RG20191108 Commented for getting mapping_id + "TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
							 + "TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,PAY_RECV_AMT,SUP_STATE_CODE,TDS_TAX_PERCENT,TDS_TAX_AMT,"
							 + "FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,TOTAL_QTY,gross_Amt_transportation " +
							// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "FROM FMS7_INVOICE_MST WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
							 + "CHECKED_FLAG='Y' AND "
//							 + "PAY_RECV_AMT IS NULL AND "
//							 + "PAY_INSERT_BY IS NULL AND"
							 + " (PDF_INV_DTL IS NOT NULL) "
							 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
			}
			else
			{
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
								 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,CHECKED_BY,APPROVED_BY,"
								 + "PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
								 //+ "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,"
								 + "SUP_STATE_CODE,TDS_TAX_PERCENT,TDS_TAX_AMT,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,gross_Amt_transportation " +
								// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "FROM FMS7_INVOICE_MST WHERE "+cnt_typ+" AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
								 + "CHECKED_FLAG='Y' AND PAY_RECV_AMT IS NOT NULL AND "
								 + "PAY_INSERT_BY IS NOT NULL AND (PDF_INV_DTL IS NOT NULL) "
								 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
				else
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
							 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
							 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,EMP_CD,CHECKED_BY,"
							 + "APPROVED_BY,PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
							 //+ "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
							 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,"
							 + "PAY_RECV_AMT,SUP_STATE_CODE,TDS_TAX_PERCENT,TDS_TAX_AMT,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,TOTAL_QTY,gross_Amt_transportation " +
							// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "FROM FMS7_INVOICE_MST WHERE "+cnt_typ+" AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
							 + "CHECKED_FLAG='Y' AND "
							 //+ "PAY_RECV_AMT IS NULL AND PAY_INSERT_BY IS NULL AND 
							 +"(PDF_INV_DTL IS NOT NULL) "
							 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
			}
			
			//System.out.println("===fetch Invoice queryString123===: "+queryString123);
			rset1=stmt1.executeQuery(queryString123);
			while(rset1.next())
			{
				Vdrcrcriteria.add("");
				if(invstatus.equalsIgnoreCase("PAID")){
					Vtds_tax_percent.add(rset1.getString(32)==null?"":rset1.getString(32));
					Vtds_tax_amt.add(rset1.getString(33)==null?"":rset1.getString(33));
				}else{
					Vtds_tax_percent.add(rset1.getString(30)==null?"":rset1.getString(30));
					Vtds_tax_amt.add(rset1.getString(31)==null?"":rset1.getString(31));
				}
				String gross_Amt_transportation="";
				if(invstatus.equalsIgnoreCase("PAID")){
					gross_Amt_transportation=rset1.getString(38)==null?"":rset1.getString(38);
				}else{
					gross_Amt_transportation=rset1.getString(37)==null?"":rset1.getString(37);
				}
				
				Vdrcrflag.add("");
				String payamt="",inv_take_flag="Y",sup_state_cd = "";String short_recv_="0";
				String queryString_agr_base="";String transp_charges="0";
				if(!gross_Amt_transportation.equals("")){
					agr_base="D";
				}else{
					if(invstatus.equalsIgnoreCase("PAID")){
						if(rset1.getString(8).equals("S")){
							queryString_agr_base="SELECT CONT_BASE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(34)+"' AND FGSA_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND SN_NO='"+rset1.getString(36)+"' AND SN_REV_NO='"+rset1.getString(37)+"'";
							//System.out.println("queryString_agr_base---"+queryString_agr_base);
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
								if(agr_base.equals("")){
									queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(34)+"' AND REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
									//System.out.println("queryString_agr_base---"+queryString_agr_base);
									rset3=stmt3.executeQuery(queryString_agr_base);
									if(rset3.next()){
										agr_base=rset3.getString(1)==null?"":rset3.getString(1);
									}	
								}
							}else{
								queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(34)+"' AND REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								//System.out.println("queryString_agr_base---"+queryString_agr_base);
								rset2=stmt2.executeQuery(queryString_agr_base);
								if(rset2.next()){
									agr_base=rset2.getString(1)==null?"":rset2.getString(1);
								}
							}
						}else if(rset1.getString(8).equals("L")){
							queryString_agr_base="SELECT CONT_BASE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(34)+"' AND LOA_REV_NO='"+rset1.getString(37)+"' AND LOA_NO='"+rset1.getString(36)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
								if(agr_base.equals("")){
									queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(34)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
									rset3=stmt3.executeQuery(queryString_agr_base);
									if(rset3.next()){
										agr_base=rset3.getString(1)==null?"":rset3.getString(1);
									}
								}
							}else{
								queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(34)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset2=stmt2.executeQuery(queryString_agr_base);
								if(rset2.next()){
									agr_base=rset2.getString(1)==null?"":rset2.getString(1);
								}
							}
						}
						if(agr_base.equals("D")){
							String queryString_tr="";
							if(rset1.getString(8).equals("S")){
								queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(34)+"' AND FGSA_REV_NO='"+rset1.getString(35)+"' AND SN_NO='"+rset1.getString(36)+"' AND SN_REV_NO='"+rset1.getString(37)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							}else if(rset1.getString(8).equals("L")){
								queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(34)+"' AND LOA_NO='"+rset1.getString(36)+"' AND LOA_REV_NO='"+rset1.getString(37)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
								
							}
							rset2=stmt2.executeQuery(queryString_tr);
						
						//System.out.println("queryString--"+queryString_tr);
							if(rset2.next()){
								transp_charges=rset2.getString(1)==null?"0":rset2.getString(1);
							}
						}
						}else{
						if(rset1.getString(8).equals("S")){
							queryString_agr_base="SELECT CONT_BASE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND FGSA_REV_NO='"+rset1.getString(33)+"' AND SN_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND SN_NO='"+rset1.getString(36)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
								if(agr_base.equals("")){
	queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
	
	}
							}else{
								queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset2=stmt2.executeQuery(queryString_agr_base);
								if(rset2.next()){
									agr_base=rset2.getString(1)==null?"":rset2.getString(1);
								}
							}
						}else if(rset1.getString(8).equals("L")){
							queryString_agr_base="SELECT CONT_BASE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND LOA_REV_NO='"+rset1.getString(35)+"' AND LOA_NO='"+rset1.getString(34)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
	if(agr_base.equals("")){
	queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
	}
							}else{
								queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset2=stmt2.executeQuery(queryString_agr_base);
								if(rset2.next()){
									agr_base=rset2.getString(1)==null?"":rset2.getString(1);
								}
							}
						}
						if(agr_base.equals("D")){
							String queryString_tr="";
							if(rset1.getString(8).equals("S")){
								queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND FGSA_REV_NO='"+rset1.getString(33)+"' AND SN_NO='"+rset1.getString(34)+"' AND SN_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							}else if(rset1.getString(8).equals("L")){
								queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND LOA_NO='"+rset1.getString(34)+"' AND LOA_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
								
							}
							rset2=stmt2.executeQuery(queryString_tr);
							//System.out.println("queryString--"+queryString_tr);
							if(rset2.next()){
								transp_charges=rset2.getString(1)==null?"0":rset2.getString(1);
							}
						}
					}
			}
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					payamt=rset1.getString(14)==null?"":rset1.getString(14);
					inv_take_flag="Y";
					//Vsup_state_cd.add(rset1.getString(31)==null?"":rset1.getString(31));
					//sup_state_cd = rset1.getString(31)==null?"":rset1.getString(31);
				}else{
					
					
					payamt=rset1.getString(28)==null?"":rset1.getString(28);
				//	Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
					//sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
				//	System.out.println("payamt---"+payamt+"--"+rset1.getString(3));
					if(payamt.equals("")){
						inv_take_flag="Y";
					}
					else{
						if(rset1.getString(21)!=null)
						{
							//System.out.println("rset1.getString(21)---"+rset1.getString(21)+"--"+rset1.getString(3));
							double salesval=0;
							double tdsper=Double.parseDouble(rset1.getString(21));
							if(agr_base.equals("D") && Double.parseDouble(transp_charges)>0){
								if(!gross_Amt_transportation.equals("")){
									double temp_grossamt=Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))+Double.parseDouble(gross_Amt_transportation);
									//System.out.println("transp_charges--if-"+transp_charges+"--rset1.getString(5)--"+rset1.getString(5)+"--"+rset1.getString(3)+"-gross_Amt_transportation--"+gross_Amt_transportation);
									salesval=temp_grossamt;
								}else{
									//System.out.println("transp_charges---"+transp_charges+"--rset1.getString(5)--"+rset1.getString(5)+"--"+rset1.getString(3)+"-rset1.getString(36)--"+rset1.getString(36));
									double temp_grossamt=(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))+Double.parseDouble(rset1.getString(36)) * Double.parseDouble(transp_charges));
									salesval=temp_grossamt;
									//System.out.println("salesval---"+salesval+"---"+rset1.getString(3));
								}
								
							}else{
								if(!gross_Amt_transportation.equals("")){
									double temp_grossamt=Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))+Double.parseDouble(gross_Amt_transportation);
									//System.out.println("transp_charges-else if--"+transp_charges+"--rset1.getString(5)--"+rset1.getString(5)+"--"+rset1.getString(3)+"-gross_Amt_transportation--"+gross_Amt_transportation);
									salesval=temp_grossamt;
								}else{
									salesval=Double.parseDouble(rset1.getString(5));
									//System.out.println("salesval-else--"+salesval+"---"+rset1.getString(3));
								}
							}
							
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//System.out.println("invval-in else--"+invval+"---"+rset1.getString(3)+"--rset1.getString(30)--"+rset1.getString(30)+"--rset1.getString(31)-"+rset1.getString(31));
							String netrecv="";
							if(rset1.getString(30)!=null)
							{
								netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(31)));
							}else{
								netrecv=nf6.format(invval-tdsval);
							}
							//System.out.println("netrecv-in else--"+netrecv);
							//double netrecv=invval-tdsval;
							double actval=Double.parseDouble(rset1.getString(28));
							short_recv_=nf6.format(Double.parseDouble(netrecv)-actval);
							//System.out.println("short_recv_-in else--"+short_recv_);
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv_=nf6.format(Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt));
							//System.out.println("short_recv_-in elsen of else--"+short_recv_);
						}
						//System.out.println("short_recv_--->>"+(Double.parseDouble(short_recv_)<=Double.parseDouble(Inv_roundoff+""))+"<<--->>"+rset1.getString(3));
						/*if(Double.parseDouble(short_recv_)>0){
							inv_take_flag="Y";
						}
						else*/
							if(Double.parseDouble(short_recv_) <= Double.parseDouble(Inv_roundoff+"")){
							
							//System.out.println("in thisssssssss--->>"+rset1.getString(3)+"<<-->>"+Inv_roundoff+"<<---->>"+Double.parseDouble(short_recv_));
							inv_take_flag="N";
						}
						else{
							inv_take_flag="Y";
						}
					}
				}
				//System.out.println("iv_take_flag---"+inv_take_flag+"----"+rset1.getString(3));
				if(inv_take_flag.equals("Y")){
				cnt++;
				String temp_flag = "";
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					VPAY_NEW_INV_SEQ_NO.add(rset1.getString(28)==null?"":rset1.getString(28));
					VPAY_FLAG.add(rset1.getString(29)==null?"Y":rset1.getString(29));
					temp_flag = rset1.getString(29)==null?"Y":rset1.getString(29);
					Vsup_state_cd.add(rset1.getString(31)==null?"":rset1.getString(31));
					sup_state_cd = rset1.getString(31)==null?"":rset1.getString(31);
				} else {
					VPAY_NEW_INV_SEQ_NO.add(rset1.getString(25)==null?"":rset1.getString(25));
					VPAY_FLAG.add(rset1.getString(26)==null?"Y":rset1.getString(26));
					temp_flag = rset1.getString(26)==null?"Y":rset1.getString(26);
					Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
					sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
				}
				flag_inv.add("I");
				//for tcs
				String query="select tcs_amt from fms7_invoice_tcs_dtl where hlpl_inv_seq_no='"+rset1.getString(3)+"' and contract_type='"+rset1.getString(8)+"' and "
						+ "financial_year='"+rset1.getString(9)+"' and customer_cd='"+rset1.getString(2)+"' AND INVOICE_TYPE='SALES' AND FLAG='Y' and COMMODITY_TYPE='RLNG'"
						+ " and sup_state_code='"+sup_state_cd+"' ";		
				rset=stmt.executeQuery(query);
				if(rset.next()){
					Vtcs_amt.add(rset.getString(1)==null?"":rset.getString(1));
				}else{
					Vtcs_amt.add("");
				}
				
				//Hiren_20210621 for TDS calc.
				String tds_app_flag = "N";
				tds_app_amt = "0";
				if(rset1.getString(8).equals("S") || rset1.getString(8).equals("L")) {
					
					tds_app_flag = checkforTDSApplicable(rset1.getString(4),rset1.getString(2),rset1.getString(9),rset1.getString(3),"RLNG","SALES",sup_state_cd);
				}
				if(segment.equalsIgnoreCase("LTCORA_CN")) {
					Vtds_app_flag.add("Y");
					Vtds_app_amt.add(rset1.getString(5)==null?"0":rset1.getString(5));
				}else {
					Vtds_app_flag.add(tds_app_flag);
					Vtds_app_amt.add(tds_app_amt);
				}
				////////
				//
				
				////System.out.println("===JAVA===1");
				if(rset1.getString(1)!=null)
				{
					Vmonth.add(rset1.getString(1));
				}
				else
				{
					Vmonth.add("-");
				}
				
				if(rset1.getString(2)!=null)
				{
					Vcustomer_cd.add(rset1.getString(2));
				}
				else
				{
					Vcustomer_cd.add("-");
				}
				
				String queryString1 = "SELECT CUSTOMER_NAME,CUSTOMER_ABBR FROM FMS7_CUSTOMER_MST A WHERE CUSTOMER_CD='"+rset1.getString(2)+"'  AND " +
					  "A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_MST B " +
					  "WHERE A.customer_cd=B.customer_cd AND " +
					  "B.eff_dt<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY') and CUSTOMER_CD='"+rset1.getString(2)+"')";
				rset=stmt.executeQuery(queryString1);
				while(rset.next())
				{
					if(rset.getString(2)!=null)
					{
						Vcustomer_abbr.add(rset.getString(2));
						Vcustomer_name.add(rset.getString(1));
					}
					else
					{
						Vcustomer_abbr.add("-");
						Vcustomer_name.add("-");
					}
				}
				
				
				hlpl_inv_seq_no=rset1.getString(3);
				if(rset1.getString(3)!=null)
				{
					Vhlplinvseqno.add(rset1.getString(3));
				}
				else
				{
					Vhlplinvseqno.add("-");
				}
				
				Vdiff_tcs.add("0");
				Vdiff_tcs_flg.add("N");
				
				String tmp_fgsa_no="",tmp_fgsa_rev_no="",tmp_sn_no="",tmp_sn_rev_no="",TMP_QTY="";
				
				String queryString456="SELECT FGSA_NO,FGSA_REV_NO,SN_REV_NO,PLANT_SEQ_NO,TO_CHAR(PERIOD_START_DT,'DD/MM/YYYY'),"
						+ "TO_CHAR(PERIOD_END_DT,'DD/MM/YYYY'),EXCHG_RATE_CD,EXCHG_RATE_TYPE,CUST_INV_SEQ_NO,APPROVED_FLAG,TOTAL_QTY " + 
						 "FROM FMS7_INVOICE_MST WHERE HLPL_INV_SEQ_NO='"+hlpl_inv_seq_no+"' "
						 + "AND FINANCIAL_YEAR='"+rset1.getString(9)+"' AND "
						 + "CONTRACT_TYPE='"+rset1.getString(8)+"' AND "
						 + "INVOICE_DT=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy') "
						 + "AND FLAG='"+temp_flag+"'"
						 + " and sup_state_code='"+sup_state_cd+"' ";
				rset=stmt.executeQuery(queryString456);
				//System.out.println("---queryString456---"+queryString456);
				if(rset.next())
				{
					if(rset.getString(1)!=null)
						Vfgsa_no.add(rset.getString(1));
					else
						Vfgsa_no.add("-");
					
					if(rset.getString(2)!=null)
						Vfgsa_rev_no.add(rset.getString(2));
					else
						Vfgsa_rev_no.add("-");
						
					if(rset.getString(3)!=null)
						Vsn_rev_no.add(rset.getString(3));
					else
						Vsn_rev_no.add("-");
					
					if(rset.getString(4)!=null)
						Vplant_seq_no.add(rset.getString(4));
					else
						Vplant_seq_no.add("-");
					
					tmp_fgsa_no=rset.getString(1)==null?"":rset.getString(1);
					tmp_fgsa_rev_no=rset.getString(2)==null?"":rset.getString(2);
					tmp_sn_no=rset1.getString(13)==null?"":rset1.getString(13);
					tmp_sn_rev_no=rset.getString(3)==null?"":rset.getString(3);
					TMP_QTY=rset.getString(11)==null?"":rset.getString(11);
					
					String q1="SELECT NVL(A.plant_name,' ') FROM FMS7_CUSTOMER_PLANT_DTL A " +
							   "WHERE A.customer_cd="+rset1.getString(2)+" AND A.seq_no="+rset.getString(4)+" " +
							   "AND A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_PLANT_DTL B " +
							   "WHERE A.customer_cd=B.customer_cd AND A.seq_no=B.seq_no " +
							   "AND B.eff_dt<=TO_DATE('"+rset.getString(5)+"','DD/MM/YYYY'))";
					
					//String q1="SELECT PLANT_NAME FROM FMS7_CUSTOMER_PLANT_DTL  WHERE CUSTOMER_CD='"+rset1.getString(2)+"' AND SEQ_NO='"+rset.getString(4)+"'";
//					System.out.println("---PLANT_NAME QUERY---"+q1);
					rset6=stmt6.executeQuery(q1);
					
					while(rset6.next())
					{
						if(rset6.getString(1)!=null)
							Vplant_seq_nm.add(rset6.getString(1));
						else
							Vplant_seq_nm.add("-");
						
						////System.out.println("---Vplant_seq_nm---:"+rset6.getString(1));
					}
					
					if(rset.getString(5)!=null)
						Vperiod_st_dt.add(rset.getString(5));
					else
						Vperiod_st_dt.add("-");
						
					if(rset.getString(6)!=null)
						Vperiod_end_dt.add(rset.getString(6));
					else
						Vperiod_end_dt.add("-");
					
					if(rset.getString(7)!=null)
						Vexchg_rate_cd.add(rset.getString(7));
					else
						Vexchg_rate_cd.add("-");
					
					if(rset.getString(8)!=null)
						Vexchg_rate_type.add(rset.getString(8));
					else
						Vexchg_rate_type.add("-");
					
					if(rset.getString(9)!=null)
						Vcust_inv_seq_no.add(rset.getString(9));
					else
						Vcust_inv_seq_no.add("-");
					
					if(rset.getString(10)!=null)
						Vapproved_flag.add(rset.getString(10));
					else
						Vapproved_flag.add("-");
					
					/*String queryString_agr_base="";
					if(rset1.getString(8).equals("S")){
						queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset.getString(1)+"' AND REV_NO='"+rset.getString(2)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
						}
					}else if(rset1.getString(8).equals("L")){
						queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset.getString(1)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
						}
					}*/
				}
				
					VAgreement_base.add(agr_base);
				
				if(rset1.getString(4)!=null)
				{
					Vinv_dt.add(rset1.getString(4));
				}
				else
				{
					Vinv_dt.add("-");
				}
				if(agr_base.equalsIgnoreCase("D")){
					String queryString_tr="";
					String temp_grossamt="";
					if(!gross_Amt_transportation.equals("")){
						temp_grossamt=""+(Double.parseDouble(rset1.getString(5)==null?"":rset1.getString(5)) + Double.parseDouble(gross_Amt_transportation));
						Vsales_value.add(temp_grossamt);
						gross_amt_inr=temp_grossamt;
						gross_amt_trans=temp_grossamt;
					}else{
						if(!transp_charges.equals("")){
							String gross_temp="";
							temp_grossamt=""+(Double.parseDouble(rset1.getString(5)==null?"":rset1.getString(5))+Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
							gross_amt_inr=temp_grossamt;
							String calcnetamt=nf6.format(Double.parseDouble(temp_grossamt)+Double.parseDouble(rset1.getString(11)==null?"0":rset1.getString(11)));
							if(Double.parseDouble(calcnetamt)==Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))){
								Vsales_value.add(temp_grossamt);
							}else{
								String diff="";
								diff=nf6.format(Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))-Double.parseDouble(calcnetamt));
								gross_temp=nf6.format(Double.parseDouble(temp_grossamt)+(Double.parseDouble(diff)));
								Vsales_value.add(gross_temp);
								
							}
							if(Double.parseDouble(transp_charges)>0){
								if(gross_temp.equals("")){
									gross_amt_inr=temp_grossamt;
									gross_amt_trans=temp_grossamt;//RG20230424
								}else{
									gross_amt_inr=gross_temp;
									gross_amt_trans=gross_temp;//RG20230424
								}
							}else{
								gross_amt_trans=rset1.getString(5)==null?"":rset1.getString(5);
							}
						}else{
							Vsales_value.add(rset1.getString(5));
							gross_amt_inr=rset1.getString(5);
							gross_amt_trans=rset1.getString(5)==null?"":rset1.getString(5);
						}
					}
				}else{
					if(rset1.getString(5)!=null)
					{
						Vsales_value.add(rset1.getString(5));
					}
					else
					{
						Vsales_value.add("-");
					}
						
					gross_amt_inr=rset1.getString(5);
				}
				Vgross_trans.add(gross_amt_trans);
				if(rset1.getString(6)!=null)
				{
					Vinv_value.add(rset1.getString(6));
				}
				else
				{
					Vinv_value.add("-");
				}
				
				if(rset1.getString(7)!=null)
				{
					Vdue_dt.add(rset1.getString(7));
				}
				else
				{
					Vdue_dt.add("-");
				}
				if(rset1.getString(8)!=null)
				{
					Vcont_type.add(rset1.getString(8));
				}
				else
				{
					Vcont_type.add("-");
				}
				
				if(rset1.getString(9)!=null)
				{
					Vfinancial_year.add(rset1.getString(9));
				}
				else
				{
					Vfinancial_year.add("-");
				}
				
				if(rset1.getString(10)!=null)
				{
					Vtax_str_cd.add(rset1.getString(10));
				}
				else
				{
					Vtax_str_cd.add("-");
				}
				
				if(rset1.getString(11)!=null)
				{
					Vtaxamtinr.add(rset1.getString(11));
				}
				else
				{
					Vtaxamtinr.add("-");
				}
				
				if(rset1.getString(12)!=null)
				{
					Vinvamtinr.add(rset1.getString(12));
				}
				else
				{
					Vinvamtinr.add("-");
				}
				
				invamtinr=rset1.getString(12)==null?"":rset1.getString(12);
				sn_no=rset1.getString(13)==null?"":rset1.getString(13);
				taxvalue=rset1.getString(11)==null?"":rset1.getString(11);
				fyr=rset1.getString(9)==null?"":rset1.getString(9);
				total_amt_inr=Double.parseDouble(rset1.getString(6));
				tax_str_cd=rset1.getString(10)==null?"":rset1.getString(10);
				
				Vsn_no.add(sn_no);
				
				//String invseq=contract_type+":"+financial_year+":"+hlpl_inv_seq_no+":%";
				String invseq=rset1.getString(8)+":"+rset1.getString(9)+":"+rset1.getString(3)+":"+rset1.getString(4);
				if(!invseq.equalsIgnoreCase(""))
				{
					Vinvseqno.add(invseq);
				}
				else
				{
					Vinvseqno.add("-");
				}
				
				////////tax_adj_amt////////
				
				String q1="select flag,amount from fms7_inv_compo_dtl where price_cd IN ('6','10','11','12','13') and inv_seq_no='"+invseq+"'";
				////System.out.println("JAVA check adjustmn query q1: "+q1);
				rset2=stmt2.executeQuery(q1);
				if(rset2.next())
				{
					if(rset2.getString(1)!=null)
					{
						taxadvflag=rset2.getString(1);
					}
					else
					{
						taxadvflag="N";
					}
					
					Vtax_adj_flag.add(taxadvflag);
								
					if(rset2.getString(2)!=null)
					{
						tax_adj_amt=nf.format(Double.parseDouble(rset2.getString(2)));
					}
					else
					{
						tax_adj_amt="0";
					}
				}
				else
				{
					taxadvflag="N";
					tax_adj_amt="0";
					Vtax_adj_flag.add(taxadvflag);
				}
				
				////System.out.println("---JAVA here taxadvflag---: "+taxadvflag+"---tax_adj_amt---: "+tax_adj_amt);	
				String mapping_id="%"+rset1.getString(2)+"-"+tmp_fgsa_no+"-%-"+tmp_sn_no+"-%-"+rset1.getString(8)+"%";
				double tot_adjust_amt=0,balance_adjust_amt=0;
				queryString = "select price_rate,currency_cd,flag " +
						"FROM fms7_SALES_cont_price_dtl WHERE mapping_id like '"+mapping_id+"' AND " +
						"price_cd='1' AND FLAG='Y' and pay_type='AP' ";
				//System.out.println("Fetching flag of sn..ad."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					VADJUST_FLAG_SN.add(rset2.getString(3)==null?"N":rset2.getString(3));
					tot_adjust_amt=rset2.getDouble(1);
					VADJUST_AMT_SN.add(rset2.getString(1)==null?"":rset2.getString(1));
					VADJUST_CUR_SN.add(rset2.getString(2));
					if(rset1.getString(8).equals("S")){
						queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
								+ "CONTRACT_NO='"+rset1.getString(13)+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND AGR_REV_NO='"+rset.getString(2)+"' "
								+ "AND CONTRACT_TYPE='"+rset1.getString(8)+"' ";
						//System.out.println("Fetching flag of sn..."+queryString1);
						
					}else{
						queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
								+ "CONTRACT_NO='"+rset1.getString(13)+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' ";
	//					System.out.println("Fetching flag of sn..."+queryString);
						
					}
					rset3=stmt3.executeQuery(queryString1);	
					if(rset3.next()){
						VCFORM_FLAG.add(rset3.getString(1)==null?"":rset3.getString(1));
					}else{
						VCFORM_FLAG.add("");
					}
				}else{
					VADJUST_FLAG_SN.add("");
					VADJUST_AMT_SN.add("");
					VADJUST_CUR_SN.add("");
					VCFORM_FLAG.add("");
				}
				queryString = "select sum(adjusted_amt) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
							+ "AND adjust_FLAG='Y' and commodity_type='RLNG' and contract_type='"+rset1.getString(8)+"'  ";
//				System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					balance_adjust_amt=tot_adjust_amt-rset2.getDouble(1);
				}else{
					balance_adjust_amt=tot_adjust_amt;
				}
				queryString = "select sum(hold_amount) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
						+ "AND commodity_type='RLNG' and contract_type='"+rset1.getString(8)+"' and release_flag='Y' ";
			//System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					balance_adjust_amt+=rset2.getDouble(1);
				}
				VADJUST_balc_SN.add(nf6.format(balance_adjust_amt));
				/////HERE
				if(!payamt.equals("")){
					//System.out.println("in this tds calaculation----"+rset1.getString(21));
					if(invstatus.equalsIgnoreCase("PAID")){
						//System.out.println("in this tds calaculation--PAID--");
						if(rset1.getString(24)!=null)
						{
							
							double tdsper=Double.parseDouble(rset1.getString(24));
							double salesval=0;
							if(agr_base.equalsIgnoreCase("D")){
								salesval=Double.parseDouble(gross_amt_trans);
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							double netrecv=0;
							if(rset1.getString(32)!=null)
							{
								netrecv=invval-tdsval-Double.parseDouble(rset1.getString(33));
							}else{
								netrecv=invval-tdsval;
							}
							double actval=Double.parseDouble(payamt);
							
							short_recv=netrecv-actval;
							
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt);
						}
					}else{
						//System.out.println("in this tds calaculation--UNPAID-tdsper-"+rset1.getString(25));
						if(rset1.getString(21)!=null)
						{
							double tdsper=Double.parseDouble(rset1.getString(21));
							//System.out.println("in this tds calaculation--UNPAID--"+tdsper);
							//double salesval=Double.parseDouble(rset1.getString(5));
							double salesval=0;
							if(agr_base.equalsIgnoreCase("D")){
								salesval=Double.parseDouble(gross_amt_trans);
								//System.out.println("in this salesval calaculation--UNPAID--"+salesval);
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
								//System.out.println("in this salesval calaculation-ele-UNPAID--"+salesval);
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//System.out.println("in this invval calaculation-ele-UNPAID--"+invval);
							//double netrecv=invval-tdsval;
							String netrecv="";
							if(rset1.getString(30)!=null)
							{
								
								netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(31)));
								//System.out.println("in this netrecv calaculation--UNPAID--"+netrecv);
							}else{
								netrecv=nf6.format(invval-tdsval);
								//System.out.println("in this netrecv calaculation-ele-UNPAID--"+netrecv);
							}
							double actval=Double.parseDouble(payamt);
							//System.out.println("short_recv_-in else-->>"+actval+"<<--netrecv-->>"+netrecv);
							short_recv=Double.parseDouble(netrecv)-actval;
							//System.out.println("in this short_recvshort_recv-ele-UNPAID--"+short_recv);
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt);
						}
					
					}
					Vpay_short_recv_amt.add(short_recv);
				}else{
					Vpay_short_recv_amt.add("-");
				}
				Vgross_trans_inr.add(gross_amt_trans);
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					if(rset1.getString(14)!=null)
					{
						Vpay_actual_recv_amt.add(rset1.getString(14));
					}
					else
					{
						Vpay_actual_recv_amt.add("-");
					}
					
					if(rset1.getString(15)!=null)
					{
						Vpay_recv_dt.add(rset1.getString(15));
					}
					else
					{
						Vpay_recv_dt.add("-");
					}
					
					if(rset1.getString(16)!=null)
					{
						Vpay_remark.add(rset1.getString(16));
					}
					else
					{
						Vpay_remark.add("-");
					}
					
					/*if(rset1.getString(24)!=null)
					{
						double tdsper=Double.parseDouble(rset1.getString(24));
						double salesval=Double.parseDouble(rset1.getString(5));
						double tdsval=(tdsper*salesval)/100;
						double invval=Double.parseDouble(rset1.getString(6));
						double netrecv=invval-tdsval;
						double actval=Double.parseDouble(rset1.getString(14));
						short_recv=netrecv-actval;
					}
					else
					{
						short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(rset1.getString(14));
					}
					
					Vpay_short_recv_amt.add(short_recv+"");*/
				
					Vpayflag.add("PAID");
					
					if(rset1.getString(17)!=null)
						Vinv_gen_by_cd.add(rset1.getString(17));
					else
						Vinv_gen_by_cd.add("00");
						
					if(rset1.getString(18)!=null)
						Vchecked_by_cd.add(rset1.getString(18));
					else
						Vchecked_by_cd.add("00");
					
					if(rset1.getString(19)!=null)
						Vapproved_by_cd.add(rset1.getString(19));
					else
						Vapproved_by_cd.add("00");
					
					if(rset1.getString(20)!=null)
					{
						Vpdf_inv_dtl.add(rset1.getString(20));
					}
					else
					{
						Vpdf_inv_dtl.add("-");
					}
					
					pdf_inv_dtl=rset1.getString(20);
					
					if(rset1.getString(21)!=null)
						print_by_ori=rset1.getString(21);
					else
						print_by_ori="00";
					
					if(rset1.getString(22)!=null)
						print_by_dup=rset1.getString(22);
					else
						print_by_dup="00";
					
					if(rset1.getString(23)!=null)
						print_by_tri=rset1.getString(23);
					else
						print_by_tri="00";
					
					////System.out.println("rset1.getString(24): "+rset1.getString(24));
					
					if(rset1.getString(24)!=null)
						Vtds_per.add(rset1.getString(24));
					else
						Vtds_per.add("-");
					
					if(pdf_inv_dtl.contains("T"))
					{
						Vprint_by_cd.add(print_by_tri);
					}
					else if(pdf_inv_dtl.contains("D"))
					{
						Vprint_by_cd.add(print_by_dup);
					}
					else if(pdf_inv_dtl.contains("O"))
					{
						Vprint_by_cd.add(print_by_ori);
					}
					else
					{
						Vprint_by_cd.add("-");
					}
					
					if(rset1.getString(25)!=null)
						Vpay_update_dt.add(rset1.getString(25));
					else
						Vpay_update_dt.add("-");
					
					if(rset1.getString(26)!=null)
						Vpay_update_cnt.add(rset1.getString(26));
					else
						Vpay_update_cnt.add("-");
					
					if(taxadvflag.equalsIgnoreCase("Y"))
					{
						////System.out.println("HERE IN IF");
						if(rset1.getString(27)!=null)
						{
							double t=Double.parseDouble(rset1.getString(27))-Double.parseDouble(tax_adj_amt);
							////System.out.println("IN IF---t---: "+t);
							Vtottax.add(t);
						}	
						else
						{
							Vtottax.add("-");
						}
					}
					else
					{
						if(rset1.getString(27)!=null)
						{
							////System.out.println("IN ELSE---rset---: "+rset1.getString(27));
							Vtottax.add(rset1.getString(27));
						}	
						else
						{
							Vtottax.add("-");
						}
					}
				}
				else
				{
					Vpay_actual_recv_amt.add("-");
					Vpay_recv_dt.add("-");
					Vpay_remark.add("-");
					//Vpay_short_recv_amt.add("-");
					Vpayflag.add("UNPAID");
					
					if(rset1.getString(14)!=null)
						Vinv_gen_by_cd.add(rset1.getString(14));
					else
						Vinv_gen_by_cd.add("00");
					
					if(rset1.getString(15)!=null)
						Vchecked_by_cd.add(rset1.getString(15));
					else
						Vchecked_by_cd.add("00");
						
					if(rset1.getString(16)!=null)
						Vapproved_by_cd.add(rset1.getString(16));
					else
						Vapproved_by_cd.add("00");
						
					if(rset1.getString(17)!=null)
					{
						Vpdf_inv_dtl.add(rset1.getString(17));
					}
					else
					{
						Vpdf_inv_dtl.add("-");
					}
					
					pdf_inv_dtl=rset1.getString(17);
					
					if(rset1.getString(18)!=null)
						print_by_ori=rset1.getString(18);
					else
						print_by_ori="00";
					
					if(rset1.getString(19)!=null)
						print_by_dup=rset1.getString(19);
					else
						print_by_dup="00";
					
					if(rset1.getString(20)!=null)
						print_by_tri=rset1.getString(20);
					else
						print_by_tri="00";
						
					if(rset1.getString(21)!=null)
					{
						Vtds_per.add(rset1.getString(21));
					}
					else
					{
						Vtds_per.add("-");
					}
					//System.out.println("===pdf-inv_dtl=="+pdf_inv_dtl);
					
					if(pdf_inv_dtl.contains("T"))
					{
						Vprint_by_cd.add(print_by_tri);
					}
					else if(pdf_inv_dtl.contains("D"))
					{
						Vprint_by_cd.add(print_by_dup);
					}
					else if(pdf_inv_dtl.contains("O"))
					{
						Vprint_by_cd.add(print_by_ori);
					}
					else
					{
						Vprint_by_cd.add("-");
					}
					
					if(rset1.getString(22)!=null)
						Vpay_update_dt.add(rset1.getString(22));
					else
						Vpay_update_dt.add("-");
					
					if(rset1.getString(23)!=null)
						Vpay_update_cnt.add(rset1.getString(23));
					else
						Vpay_update_cnt.add("-");
					
					if(taxadvflag.equalsIgnoreCase("Y"))
					{
						if(rset1.getString(24)!=null)
						{
							double t=Double.parseDouble(rset1.getString(24))-Double.parseDouble(tax_adj_amt);
							Vtottax.add(t);
						}	
						else
						{
							Vtottax.add("-");
						}
					}
					else
					{
						if(rset1.getString(24)!=null)
						{
							Vtottax.add(rset1.getString(24));
						}	
						else
						{
							Vtottax.add("-");
						}
					}
				}
				
				////System.out.println("===JAVA===2");
				/////////Vhlpl_inv_seq//////////
				
				if(Integer.parseInt(rset1.getString(3))<10)
				{
					hlpl_inv_no_disp="000"+rset1.getString(3)+"/"+rset1.getString(9);
				}
				else if(Integer.parseInt(rset1.getString(3))<100) 
				{
					hlpl_inv_no_disp="00"+rset1.getString(3)+"/"+rset1.getString(9);
				}
				else if(Integer.parseInt(rset1.getString(3))<1000) 
				{
					hlpl_inv_no_disp="0"+rset1.getString(3)+"/"+rset1.getString(9);
				}
				else
				{
					hlpl_inv_no_disp=rset1.getString(3)+"/"+rset1.getString(9);
				}
				
				if(!hlpl_inv_no_disp.equalsIgnoreCase(""))
				{
					Vhlpl_inv_seq.add(hlpl_inv_no_disp);
				}
				else
				{
					Vhlpl_inv_seq.add("-");
				}
				
				////System.out.println("===JAVA===3");
				
				///////////XML_GEN_FLAG/////////
				
				String queryString2 = "SELECT XML_GEN_FLAG,to_char(APPROVAL_DT,'dd/mm/yyyy') FROM FMS7_ACCOUNT_APPROVED_DTL " +
						  "WHERE INV_CARGO_NO='"+hlpl_inv_no_disp+"' AND JOURNAL_TYPE='FMSSL' " +
						  "AND CONTRACT_TYPE='"+contract_type+"' ";
					//	//System.out.println("Confirmed FMS7_PROV_SELLER_PAY Details Query = "+queryString);
					rset = stmt.executeQuery(queryString2);
					if(rset.next())
					{
						xml_gen_flag=rset.getString(1)==null?"N":rset.getString(1);
						approval_dt=rset.getString(2)==null?"":rset.getString(2);
						Vxml_gen_flag.add(xml_gen_flag);
					}
					else
					{
						xml_gen_flag="N";
						approval_dt="";
						Vxml_gen_flag.add(xml_gen_flag);
					}
						
					////System.out.println("===JAVA===4");
					
					///tax_adj
					
					////System.out.println("===JAVA===5");
					
					///////////inv_adj_amt///////////
					
					String q2="select flag,amount from fms7_inv_compo_dtl where price_cd='1' and inv_seq_no='"+invseq+"'";
					////System.out.println("JAVA check adjustmn query q2: "+q2);
					rset3=stmt3.executeQuery(q2);
					if(rset3.next())
					{
						if(rset3.getString(1)!=null)
						{
							advflag=rset3.getString(1);
						}
						else
						{
							advflag="N";
						}
						
						Vinv_adj_flag.add(advflag);
						
						if(rset3.getString(2)!=null)
						{
							inv_adj_amt=nf.format(Double.parseDouble(rset3.getString(2)));
						}
						else
						{
							inv_adj_amt="0";
						}
					}
					else
					{
						advflag="N";
						inv_adj_amt="0";
						Vinv_adj_flag.add(advflag);
					}
					////System.out.println("---JAVA here advflag: "+advflag);
								
					////System.out.println("===JAVA===6");
					
					////////////inv_amt///////////
					
					String inv_amt="";
					if(advflag.equalsIgnoreCase("Y") && taxadvflag.equalsIgnoreCase("Y"))
					{
						//query2="select INV_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
						inv_amt=invamtinr;
					}
					else
					{
						//query2="select GROSS_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
						inv_amt=gross_amt_inr;
					}
					////System.out.println("---JAVA inv_amt---: "+inv_amt);
							
					////System.out.println("===JAVA===7");
					
					String amt = "0";
					
					String map_id=contract_type+":"+fyr+":"+hlpl_inv_seq_no+":%";
					Vmapid.add(map_id);
					
					if(contract_type.equalsIgnoreCase("T") || contract_type.equalsIgnoreCase("C"))		//ADDED FOR LTCORA AND CN
					{
						// String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no like" +
						//	" '"+map_id+"'and price_cd='6' and (flag_temp not like 'T' or flag_temp is null)";
						
						String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no='"+invseq+"' and price_cd IN ('6','10','11','12','13') "
								+ "and (flag_temp not like 'T' or flag_temp is null)";
						
						rset4=stmt4.executeQuery(queryTax);
						////System.out.println("---JAVA queryTax---: "+queryTax);
						if(rset4.next())
						{
							amt = ""+java.text.NumberFormat.getInstance().parse(rset4.getString(1));
							Adj_Tax_amt=amt;	
							taxvalue = ""+(Double.parseDouble(taxvalue) - Double.parseDouble(amt));
						}
					}
					
					////System.out.println("===JAVA===8");
					
					String queryString12 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
				 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
					
					////System.out.println("===VTAXAMT QUERY===: "+queryString12);
					rset5=stmt5.executeQuery(queryString12);
					////System.out.println("===JAVA===9");
					int cnt1=0;
					String tx_cd="",tx_amt="",tx_nm="";
					while(rset5.next())
					{
						tax_cd=rset5.getString(1);
						tax_on=rset5.getString(3);
						////System.out.println("---tax_on---"+tax_on);
						if(rset5.getString(3).equals("1")) 
						{
							cnt1++;
							////System.out.println("===IN IF=== "+cnt1);
							tax_amt = (Double.parseDouble(gross_amt_inr)*Double.parseDouble(rset5.getString(2)))/100;
							////System.out.println(Double.parseDouble(gross_amt_inr)+"---total_amt_inr-IN IF--"+tax_amt);
							////System.out.println("---rset5.getString(2)-IN IF--"+rset5.getString(2));
						}
						else if(rset5.getString(3).equals("2"))
						{
							cnt1++;
							////System.out.println("===IN ELSE IF=== "+cnt1);
							String q3 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
										   "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
										   "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
										   "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) AND A.tax_code='"+rset5.getString(4)+"'";
							
							////System.out.println("===TAXAMT Query q3 === "+q3);
							rset6=stmt6.executeQuery(q3);
				 	 		
							if(rset6.next())
				 	 		{
				 	 			if(rset6.getString(3).equals("1"))
				 				{
				 					tax_amt = (Double.parseDouble(gross_amt_inr)*Double.parseDouble(rset6.getString(2)))/100;
				 				}	
				 	 			////System.out.println("---total_amt_inr-IN ELSE IF--"+total_amt_inr);
								////System.out.println("---rset5.getString(2)-IN ELSE IF--"+rset5.getString(2));
				 	 			tax_amt = (tax_amt*Double.parseDouble(rset5.getString(2)))/100;
				 	 		}
				 	 		else
				 	 		{
				 	 			tax_amt=0;
				 	 		}
						}
						else
						{
							tax_amt = 0;
						}
						////System.out.println("===JAVA===9");
						
						////////////////////////////BK:SB20151203//////////////////
			 			
						if(!Adj_Tax_amt.equalsIgnoreCase("") && taxadvflag.equalsIgnoreCase("Y"))
			 			{
							if(Double.parseDouble(Adj_Tax_amt)>0)
				 			{
								////System.out.println("---Adj Amt---: "+Adj_Tax_amt);
				 				tax_amt = tax_amt - Double.parseDouble(Adj_Tax_amt);
				 				Adj_Tax_amt = "0";
				 			}
							else
				 			{
								if(tax_on.equalsIgnoreCase("2"))
								{
									//taxamt=nf.format(tax_amt)+"";
									//Tot_Tax_amt = Tot_Tax_amt + Double.parseDouble(taxamt);
								}
				 			}
			 			}
						
						////System.out.println("===IN WHILE================= "+cnt1);
						
						///////////////////////////////////////////////////////
						queryString6="select sht_nm from fms7_tax_mst where tax_code='"+tax_cd+"'";
						rset6=stmt6.executeQuery(queryString6);
						while(rset6.next())
						{
							tax_nm=rset6.getString(1);
						}
						
						tx_cd+="@"+tax_cd;
						tx_amt+="@"+nf.format(tax_amt);
						tx_nm+="@"+tax_nm;
						double tax_diff=0;
						//int tax_fact=Integer.parseInt(rset5.getString(2));
						double tax_fact=Double.parseDouble(rset5.getString(2));
						if(tax_fact<tax_cform){
							tax_diff=tax_cform-tax_fact;
							tax_diff_val.add(tax_diff);
						}else{
							tax_diff_val.add("");
						}
						
						////System.out.println("===JAVA tax_amt after=== "+tax_amt);
					}
					Vtax_code.add(tx_cd.replaceFirst("@", ""));  	
					Vtax_amt.add(tx_amt.replaceFirst("@", ""));
					//System.out.println("===JAVA tax_amt after=== "+tx_nm);
					Vtax_nm.add(tx_nm.replaceFirst("@", ""));
					
					String q123 = "SELECT COUNT(*) FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
				 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
					rset=stmt.executeQuery(q123);
					while(rset.next())
					{
						//cnt1=rset.getInt(1);
						int count=rset.getInt(1);
						Vtaxcnt.add(count);
					}
					
					//////////////////////////////////////////////////////////
					///fOR GETTING REFERENCE NO FOR CONTRACT_NO AND AGREEMENT_NO///
					String mapp_id="";
					if(rset1.getString(8).equals("C") || rset1.getString(8).equals("M") || rset1.getString(8).equals("B") || rset1.getString(8).equals("E")){
						if(invstatus.equalsIgnoreCase("PAID"))
						{
							mapp_id=rset1.getString(30)==null?"":rset1.getString(30);
						}else{
							mapp_id=rset1.getString(27)==null?"":rset1.getString(27);
						}
						//System.out.println("--mapp_id--"+mapp_id);
						String temp_mapp_id[]=mapp_id.split("-");
						VAgreement_no.add(temp_mapp_id[1]+"");
						VAgreement_rev_no.add(temp_mapp_id[2]+"");
						Vcont_no.add(temp_mapp_id[3]+"");
						Vcont_rev_no.add(temp_mapp_id[4]+"");
						if(""+temp_mapp_id[3]!=""){
							int temp_cont_no=Integer.parseInt(temp_mapp_id[3]+"");
							if(temp_cont_no>999){
								Vcont_typ.add("LTCORA (Period)");
							}else{
								Vcont_typ.add("LTCORA (CN)");
							}
						}else{
							Vcont_typ.add("LTCORA (CN)");
						}
						
					}else if(rset1.getString(8).equals("S")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("SN");
					}else if(rset1.getString(8).equals("L")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("LOA");
					}
					else if(rset1.getString(8).equals("I")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("Interest");
					}
					else if(rset1.getString(8).equals("R")){
						Vcont_typ.add("Regas");
					}
					
				}
					//////
				}
			
			//for getting MANUAL INVOICE from system
			if(!customer_cd.equalsIgnoreCase(""))
			{
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
								 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,CHECKED_BY,APPROVED_BY"
								 + ",PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
								 //RG20191108 Commented for getting mapping_id + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,"
								 + "SUP_STATE_CODE,tds_tax_percent,tds_tax_amt,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO " +
								// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "FROM FMS7_MANUAL_INVOICE_MST WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
								 + "CHECKED_FLAG='Y' AND PAY_RECV_AMT IS NOT NULL AND "
								 + "PAY_INSERT_BY IS NOT NULL AND (PDF_INV_DTL IS NOT NULL) "
								 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
				else
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
							 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,FINANCIAL_YEAR,TAX_STRUCT_CD,"
							 + "TAX_AMT_INR,INV_AMT_INR,SN_NO,EMP_CD,CHECKED_BY,APPROVED_BY,PDF_INV_DTL,PRINT_BY_ORI,"
							 + "PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,"
							//RG20191108 Commented for getting mapping_id + "TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
							 + "TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,PAY_RECV_AMT,SUP_STATE_CODE,tds_tax_percent,tds_tax_amt,"
							 + "FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,TOTAL_QTY " +
							// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "FROM FMS7_MANUAL_INVOICE_MST WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
							 + "CHECKED_FLAG='Y' "
//							 + "AND PAY_RECV_AMT IS NULL AND "
//							 + "PAY_INSERT_BY IS NULL "
							 + "AND (PDF_INV_DTL IS NOT NULL) "
							 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
			}
			else
			{
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
								 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,CHECKED_BY,APPROVED_BY,"
								 + "PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
								 //+ "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID"
								 + ",SUP_STATE_CODE,tds_tax_percent,tds_tax_amt ,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO " +
								// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "FROM FMS7_MANUAL_INVOICE_MST WHERE "+cnt_typ+" AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
								 + "CHECKED_FLAG='Y' "
								 + "AND PAY_RECV_AMT IS NOT NULL AND "
								 + "PAY_INSERT_BY IS NOT NULL AND (PDF_INV_DTL IS NOT NULL) "
								 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
				else
				{
					queryString123="select TO_CHAR(INVOICE_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY')," +
							 "GROSS_AMT_INR,NET_AMT_INR,TO_CHAR(DUE_DT,'DD/MM/YYYY'),CONTRACT_TYPE,"
							 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,INV_AMT_INR,SN_NO,EMP_CD,CHECKED_BY,"
							 + "APPROVED_BY,PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,TDS_PERCENT,"
							 //+ "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG " +
							 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,NEW_INV_SEQ_NO,FLAG,MAPPING_ID,PAY_RECV_AMT"
							 + ",SUP_STATE_CODE,tds_tax_percent,tds_tax_amt,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,TOTAL_QTY " +
							// "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "FROM FMS7_MANUAL_INVOICE_MST WHERE "+cnt_typ+" AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
							 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND "
							 + "CHECKED_FLAG='Y' "
							 //+ "AND PAY_RECV_AMT IS NULL AND PAY_INSERT_BY IS NULL "
							 + "AND (PDF_INV_DTL IS NOT NULL) "
							 + "ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
			}
			//System.out.println("===fetch Invoice queryString123===: "+queryString123);
			rset1=stmt1.executeQuery(queryString123);
			while(rset1.next()){
				Vdrcrcriteria.add("");
				Vdrcrflag.add("");
				if(invstatus.equalsIgnoreCase("PAID")){
					Vtds_tax_percent.add(rset1.getString(32)==null?"":rset1.getString(32));
					Vtds_tax_amt.add(rset1.getString(33)==null?"":rset1.getString(33));
				}else{
					Vtds_tax_percent.add(rset1.getString(30)==null?"":rset1.getString(30));
					Vtds_tax_amt.add(rset1.getString(31)==null?"":rset1.getString(31));
				}
				String payamt="",inv_take_flag="Y",sup_state_cd ="";String short_recv_="0";
				String queryString_agr_base="";String transp_charges="0";
				if(invstatus.equalsIgnoreCase("PAID")){
					if(rset1.getString(8).equals("S")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(34)+"' AND FGSA_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND SN_NO='"+rset1.getString(36)+"' AND SN_REV_NO='"+rset1.getString(37)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(34)+"' AND REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(34)+"' AND REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}else if(rset1.getString(8).equals("L")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(34)+"' AND LOA_NO='"+rset1.getString(36)+"' AND LOA_REV_NO='"+rset1.getString(37)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(34)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(34)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}
					if(agr_base.equals("D")){
						String queryString_tr="";
						if(rset1.getString(8).equals("S")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(34)+"' AND FGSA_REV_NO='"+rset1.getString(35)+"' AND SN_NO='"+rset1.getString(36)+"' AND SN_REV_NO='"+rset1.getString(37)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
						}else if(rset1.getString(8).equals("L")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(34)+"' AND LOA_NO='"+rset1.getString(36)+"' AND LOA_REV_NO='"+rset1.getString(37)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							
						}
						rset2=stmt2.executeQuery(queryString_tr);
					
					//System.out.println("queryString--"+queryString_tr);
						if(rset2.next()){
							transp_charges=rset2.getString(1)==null?"0":rset2.getString(1);
						}
					}
				}else{
					if(rset1.getString(8).equals("S")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND FGSA_REV_NO='"+rset1.getString(33)+"' AND SN_NO='"+rset1.getString(34)+"' AND SN_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}else if(rset1.getString(8).equals("L")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND LOA_NO='"+rset1.getString(34)+"' AND LOA_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}
					if(agr_base.equals("D")){
						String queryString_tr="";
						if(rset1.getString(8).equals("S")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND FGSA_REV_NO='"+rset1.getString(33)+"' AND SN_NO='"+rset1.getString(34)+"' AND SN_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
						}else if(rset1.getString(8).equals("L")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND LOA_NO='"+rset1.getString(34)+"' AND LOA_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							
						}
						rset2=stmt2.executeQuery(queryString_tr);
						//System.out.println("queryString--"+queryString_tr);
						if(rset2.next()){
							transp_charges=rset2.getString(1)==null?"0":rset2.getString(1);
						}
					}
				}
				
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					payamt=rset1.getString(14)==null?"":rset1.getString(14);
					inv_take_flag="Y";
					//Vsup_state_cd.add(rset1.getString(31)==null?"":rset1.getString(31));
					//sup_state_cd = rset1.getString(31)==null?"":rset1.getString(31);
				}else{
					payamt=rset1.getString(28)==null?"":rset1.getString(28);
					//Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
					//sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
					
				//	System.out.println("payamt---"+payamt);
					if(payamt.equals("")){
						inv_take_flag="Y";
					}else{
						if(rset1.getString(21)!=null)
						{
							double salesval=0;
							double tdsper=Double.parseDouble(rset1.getString(21));
							if(agr_base.equals("D") && Double.parseDouble(transp_charges)>0){
								double temp_grossamt=(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))+Double.parseDouble(rset1.getString(36)) * Double.parseDouble(transp_charges));
								salesval=temp_grossamt;
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//double netrecv=invval-tdsval;
							String netrecv="";
							if(rset1.getString(30)!=null)
							{
								netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(31)));
							}else{
								netrecv=nf6.format(invval-tdsval);
							}
							double actval=Double.parseDouble(rset1.getString(28));
							short_recv_=nf6.format(Double.parseDouble(netrecv)-actval);
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv_=nf6.format(Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt));
						}
					//	System.out.println("short_recv_---"+short_recv_);
						if(Double.parseDouble(short_recv_) <= Double.parseDouble(Inv_roundoff+"")){
							//		System.out.println("in thisssssssss--->>"+rset1.getString(3)+"<<-->>"+Inv_roundoff+"<<---->>"+Double.parseDouble(short_recv_));
									inv_take_flag="N";
								}
								else{
									inv_take_flag="Y";
								}
					}
				}
				if(inv_take_flag.equals("Y")){
				cnt++;
				String temp_flag = "";
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					VPAY_NEW_INV_SEQ_NO.add(rset1.getString(28)==null?"":rset1.getString(28));
					VPAY_FLAG.add(rset1.getString(29)==null?"Y":rset1.getString(29));
					temp_flag = rset1.getString(29)==null?"Y":rset1.getString(29);
					Vsup_state_cd.add(rset1.getString(31)==null?"":rset1.getString(31));
					sup_state_cd = rset1.getString(31)==null?"":rset1.getString(31);
				} else {
					VPAY_NEW_INV_SEQ_NO.add(rset1.getString(25)==null?"":rset1.getString(25));
					VPAY_FLAG.add(rset1.getString(26)==null?"Y":rset1.getString(26));
					temp_flag = rset1.getString(26)==null?"Y":rset1.getString(26);
					Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
					sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
				}
				flag_inv.add("MI");
					
				//for tcs
				String query="select tcs_amt from fms7_invoice_tcs_dtl where hlpl_inv_seq_no='"+rset1.getString(3)+"' and contract_type='"+rset1.getString(8)+"' and "
						+ "financial_year='"+rset1.getString(9)+"' and customer_cd='"+rset1.getString(2)+"' AND INVOICE_TYPE='SALES' AND FLAG='Y' and COMMODITY_TYPE='RLNG'"
						+ " and sup_state_code='"+sup_state_cd+"' ";
								
				rset=stmt.executeQuery(query);
				if(rset.next()){
					Vtcs_amt.add(rset.getString(1)==null?"":rset.getString(1));
				}else{
					Vtcs_amt.add("");
				}
				
				//Hiren_20210621 for TDS calc.
				String tds_app_flag = "N";
				tds_app_amt = "0";
				if(rset1.getString(8).equals("S") || rset1.getString(8).equals("L")) {
					tds_app_flag = checkforTDSApplicable(rset1.getString(4),rset1.getString(2),rset1.getString(9),rset1.getString(3),"RLNG","SALES",sup_state_cd);
					
				}
				if(segment.equalsIgnoreCase("LTCORA_CN")) {
					Vtds_app_flag.add("Y");
					Vtds_app_amt.add(rset1.getString(5)==null?"0":rset1.getString(5));
				}else {
					Vtds_app_flag.add(tds_app_flag);
					Vtds_app_amt.add(tds_app_amt);
				}
				////////
				////System.out.println("===JAVA===1");
				if(rset1.getString(1)!=null)
				{
					Vmonth.add(rset1.getString(1));
				}
				else
				{
					Vmonth.add("-");
				}
				
				if(rset1.getString(2)!=null)
				{
					Vcustomer_cd.add(rset1.getString(2));
				}
				else
				{
					Vcustomer_cd.add("-");
				}
				
				String queryString1 = "SELECT CUSTOMER_NAME,CUSTOMER_ABBR FROM FMS7_CUSTOMER_MST A WHERE CUSTOMER_CD='"+rset1.getString(2)+"'"
						+ "  AND " +
					  "A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_MST B " +
					  "WHERE A.customer_cd=B.customer_cd AND " +
					  "B.eff_dt<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY') and CUSTOMER_CD='"+rset1.getString(2)+"')";
				rset=stmt.executeQuery(queryString1);
				while(rset.next())
				{
					if(rset.getString(2)!=null)
					{
						Vcustomer_abbr.add(rset.getString(2));
						Vcustomer_name.add(rset.getString(1));
					}
					else
					{
						Vcustomer_abbr.add("-");
						Vcustomer_name.add("-");
					}
				}
				
				
				hlpl_inv_seq_no=rset1.getString(3);
				if(rset1.getString(3)!=null)
				{
					Vhlplinvseqno.add(rset1.getString(3));
				}
				else
				{
					Vhlplinvseqno.add("-");
				}
				Vdiff_tcs.add("0");
				Vdiff_tcs_flg.add("N");
				String tmp_fgsa_no="",tmp_fgsa_rev_no="",tmp_sn_no="",tmp_sn_rev_no="",TMP_QTY="";
				
				String queryString456="SELECT FGSA_NO,FGSA_REV_NO,SN_REV_NO,PLANT_SEQ_NO,TO_CHAR(PERIOD_START_DT,'DD/MM/YYYY'),"
						+ "TO_CHAR(PERIOD_END_DT,'DD/MM/YYYY'),EXCHG_RATE_CD,EXCHG_RATE_TYPE,CUST_INV_SEQ_NO,APPROVED_FLAG,TOTAL_QTY " + 
						 "FROM FMS7_MANUAL_INVOICE_MST WHERE HLPL_INV_SEQ_NO='"+hlpl_inv_seq_no+"' "
						 + "AND FINANCIAL_YEAR='"+rset1.getString(9)+"' AND "
						 + "CONTRACT_TYPE='"+rset1.getString(8)+"' AND "
						 + "INVOICE_DT=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy') "
						 + "AND FLAG='"+temp_flag+"' "
						 + " and sup_state_code='"+sup_state_cd+"' ";
				rset=stmt.executeQuery(queryString456);
				//System.out.println("---queryString456---"+queryString456);
				if(rset.next())
				{
					if(rset.getString(1)!=null)
						Vfgsa_no.add(rset.getString(1));
					else
						Vfgsa_no.add("-");
					
					if(rset.getString(2)!=null)
						Vfgsa_rev_no.add(rset.getString(2));
					else
						Vfgsa_rev_no.add("-");
						
					if(rset.getString(3)!=null)
						Vsn_rev_no.add(rset.getString(3));
					else
						Vsn_rev_no.add("-");
					
					if(rset.getString(4)!=null)
						Vplant_seq_no.add(rset.getString(4));
					else
						Vplant_seq_no.add("-");
					
					tmp_fgsa_no=rset.getString(1)==null?"":rset.getString(1);
					tmp_fgsa_rev_no=rset.getString(2)==null?"":rset.getString(2);
					tmp_sn_no=rset1.getString(13)==null?"":rset1.getString(13);
					tmp_sn_rev_no=rset.getString(3)==null?"":rset.getString(3);
					TMP_QTY=rset.getString(11)==null?"":rset.getString(11);
					
					String q1="SELECT NVL(A.plant_name,' ') FROM FMS7_CUSTOMER_PLANT_DTL A " +
							   "WHERE A.customer_cd="+rset1.getString(2)+" AND A.seq_no="+rset.getString(4)+" " +
							   "AND A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_PLANT_DTL B " +
							   "WHERE A.customer_cd=B.customer_cd AND A.seq_no=B.seq_no " +
							   "AND B.eff_dt<=TO_DATE('"+rset.getString(5)+"','DD/MM/YYYY'))";
					
					//String q1="SELECT PLANT_NAME FROM FMS7_CUSTOMER_PLANT_DTL  WHERE CUSTOMER_CD='"+rset1.getString(2)+"' AND SEQ_NO='"+rset.getString(4)+"'";
					////System.out.println("---PLANT_NAME QUERY---"+q1);
					rset6=stmt6.executeQuery(q1);
					
					while(rset6.next())
					{
						if(rset6.getString(1)!=null)
							Vplant_seq_nm.add(rset6.getString(1));
						else
							Vplant_seq_nm.add("-");
						
						////System.out.println("---Vplant_seq_nm---:"+rset6.getString(1));
					}
					
					if(rset.getString(5)!=null)
						Vperiod_st_dt.add(rset.getString(5));
					else
						Vperiod_st_dt.add("-");
						
					if(rset.getString(6)!=null)
						Vperiod_end_dt.add(rset.getString(6));
					else
						Vperiod_end_dt.add("-");
					
					if(rset.getString(7)!=null)
						Vexchg_rate_cd.add(rset.getString(7));
					else
						Vexchg_rate_cd.add("-");
					
					if(rset.getString(8)!=null)
						Vexchg_rate_type.add(rset.getString(8));
					else
						Vexchg_rate_type.add("-");
					
					if(rset.getString(9)!=null)
						Vcust_inv_seq_no.add(rset.getString(9));
					else
						Vcust_inv_seq_no.add("-");
					
					if(rset.getString(10)!=null)
						Vapproved_flag.add(rset.getString(10));
					else
						Vapproved_flag.add("-");
					
					/*String queryString_agr_base="";
					if(rset1.getString(8).equals("S")){
						queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset.getString(1)+"' AND REV_NO='"+rset.getString(2)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
						}
					}else if(rset1.getString(8).equals("L")){
						queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset.getString(1)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
						}
					}*/
				}
				VAgreement_base.add(agr_base);
				if(rset1.getString(4)!=null)
				{
					Vinv_dt.add(rset1.getString(4));
				}
				else
				{
					Vinv_dt.add("-");
				}
				
				if(agr_base.equalsIgnoreCase("D")){

					String queryString_tr="";
					//String transp_charges="";
					String temp_grossamt="";
					/*if(rset1.getString(8).equals("S")){
						queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+tmp_fgsa_no+"' AND FGSA_REV_NO='"+tmp_fgsa_rev_no+"' AND SN_NO='"+tmp_sn_no+"' AND SN_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
					}else if(rset1.getString(8).equals("L")){
						queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+tmp_fgsa_no+"' AND LOA_NO='"+tmp_sn_no+"' AND LOA_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
						
					}
					rset2=stmt2.executeQuery(queryString_tr);
					//System.out.println("queryString--"+queryString_tr);
					if(rset2.next()){
						transp_charges=rset2.getString(1)==null?"":rset2.getString(1);
					}*/
					//System.out.println("---rset.getString(7)--"+rset1.getString(5)+"--transp_charges--"+transp_charges+"--TMP_QTY---"+TMP_QTY);
					if(!transp_charges.equals("")){
						String gross_temp="";
						temp_grossamt=""+(Double.parseDouble(rset1.getString(5)==null?"":rset1.getString(5))+Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
						//Vsales_value.add(temp_grossamt);
						gross_amt_inr=temp_grossamt;
						
						String calcnetamt=nf6.format(Double.parseDouble(temp_grossamt)+Double.parseDouble(rset1.getString(11)==null?"0":rset1.getString(11)));
						//System.out.println("---calcnetamt--"+calcnetamt+"---"+rset1.getString(6));
						if(Double.parseDouble(calcnetamt)==Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))){
							Vsales_value.add(temp_grossamt);
						}else{
							String diff="";
							//if(Double.parseDouble(calcnetamt)>Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))){
								
								diff=nf6.format(Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))-Double.parseDouble(calcnetamt));
								System.out.println("---diff-if-"+diff);
							/*}else{
								diff=nf6.format(Double.parseDouble(calcnetamt)-Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6)));
								System.out.println("---diff-else-"+diff);
							}*/
							
							gross_temp=nf6.format(Double.parseDouble(temp_grossamt)+(Double.parseDouble(diff)));
							System.out.println("---gross_temp--"+gross_temp+"--"+temp_grossamt);
							Vsales_value.add(gross_temp);
							
						}
						if(Double.parseDouble(transp_charges)>0){
							//gross_amt_trans=""+(Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
							//gross_amt_trans=gross_amt_inr;
							//gross_amt_trans=gross_temp;
							if(gross_temp.equals("")){
								gross_amt_inr=temp_grossamt;
								gross_amt_trans=temp_grossamt;//RG20230424
							}else{
								gross_amt_inr=gross_temp;
							}
						}else{
							gross_amt_trans=rset1.getString(5)==null?"":rset1.getString(5);
						}
						//System.out.println("---temp====="+temp_grossamt);
					}else{
						Vsales_value.add(rset1.getString(5));
						gross_amt_inr=rset1.getString(5);
						gross_amt_trans=rset1.getString(5)==null?"":rset1.getString(5);
					}
					
				
				}else{
				
					if(rset1.getString(5)!=null)
					{
						Vsales_value.add(rset1.getString(5));
					}
					else
					{
						Vsales_value.add("-");
					}
						
					gross_amt_inr=rset1.getString(5);
				}
				Vgross_trans.add(gross_amt_trans);
				if(rset1.getString(6)!=null)
				{
					Vinv_value.add(rset1.getString(6));
				}
				else
				{
					Vinv_value.add("-");
				}
				
				if(rset1.getString(7)!=null)
				{
					Vdue_dt.add(rset1.getString(7));
				}
				else
				{
					Vdue_dt.add("-");
				}
				if(rset1.getString(8)!=null)
				{
					Vcont_type.add(rset1.getString(8));
				}
				else
				{
					Vcont_type.add("-");
				}
				
				if(rset1.getString(9)!=null)
				{
					Vfinancial_year.add(rset1.getString(9));
				}
				else
				{
					Vfinancial_year.add("-");
				}
				
				if(rset1.getString(10)!=null)
				{
					Vtax_str_cd.add(rset1.getString(10));
				}
				else
				{
					Vtax_str_cd.add("-");
				}
				
				if(rset1.getString(11)!=null)
				{
					Vtaxamtinr.add(rset1.getString(11));
				}
				else
				{
					Vtaxamtinr.add("-");
				}
				
				if(rset1.getString(12)!=null)
				{
					Vinvamtinr.add(rset1.getString(12));
				}
				else
				{
					Vinvamtinr.add("-");
				}
				
				invamtinr=rset1.getString(12);
				sn_no=rset1.getString(13);
				taxvalue=rset1.getString(11);
				fyr=rset1.getString(9);
				total_amt_inr=Double.parseDouble(rset1.getString(6));
				tax_str_cd=rset1.getString(10)==null?"":rset1.getString(10);
				
				Vsn_no.add(sn_no);
				
				//String invseq=contract_type+":"+financial_year+":"+hlpl_inv_seq_no+":%";
				String invseq=rset1.getString(8)+":"+rset1.getString(9)+":"+rset1.getString(3)+":"+rset1.getString(4);
				if(!invseq.equalsIgnoreCase(""))
				{
					Vinvseqno.add(invseq);
				}
				else
				{
					Vinvseqno.add("-");
				}
				
				////////tax_adj_amt////////
				
				String q1="select flag,amount from fms7_inv_compo_dtl where price_cd IN ('6','10','11','12','13') and inv_seq_no='"+invseq+"'";
				////System.out.println("JAVA check adjustmn query q1: "+q1);
				rset2=stmt2.executeQuery(q1);
				if(rset2.next())
				{
					if(rset2.getString(1)!=null)
					{
						taxadvflag=rset2.getString(1);
					}
					else
					{
						taxadvflag="N";
					}
					
					Vtax_adj_flag.add(taxadvflag);
								
					if(rset2.getString(2)!=null)
					{
						tax_adj_amt=nf.format(Double.parseDouble(rset2.getString(2)));
					}
					else
					{
						tax_adj_amt="0";
					}
				}
				else
				{
					taxadvflag="N";
					tax_adj_amt="0";
					Vtax_adj_flag.add(taxadvflag);
				}
				
				////System.out.println("---JAVA here taxadvflag---: "+taxadvflag+"---tax_adj_amt---: "+tax_adj_amt);	
				double tot_adjust_amt=0,balance_adjust_amt=0;
				//String mapping_id=""+rset1.getString(2)+"-"+tmp_fgsa_no+"-"+tmp_fgsa_rev_no+"-"+tmp_sn_no+"-"+tmp_sn_rev_no+"-"+rset1.getString(8);
				String mapping_id="%"+rset1.getString(2)+"-"+tmp_fgsa_no+"-%-"+tmp_sn_no+"-%-"+rset1.getString(8)+"%";
				queryString = "select price_rate,currency_cd,flag " +
						"FROM fms7_SALES_cont_price_dtl WHERE mapping_id like '"+mapping_id+"' AND " +
						"price_cd='1' AND FLAG='Y' and pay_type='AP' ";
			//	System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					VADJUST_FLAG_SN.add(rset2.getString(3)==null?"N":rset2.getString(3));
					tot_adjust_amt=rset2.getDouble(1);
					VADJUST_AMT_SN.add(rset2.getString(1)==null?"":rset2.getString(1));
					VADJUST_CUR_SN.add(rset2.getString(2));
					
					if(rset1.getString(8).equals("S")){
						queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
								+ "CONTRACT_NO='"+rset1.getString(13)+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND AGR_REV_NO='"+rset.getString(2)+"' "
								+ "AND CONTRACT_TYPE='"+rset1.getString(8)+"' ";
	//					System.out.println("Fetching flag of sn..."+queryString);
						
					}else{
						queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
								+ "CONTRACT_NO='"+rset1.getString(13)+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' ";
	//					System.out.println("Fetching flag of sn..."+queryString);
						
					}
					rset3=stmt3.executeQuery(queryString1);	
					if(rset3.next()){
						VCFORM_FLAG.add(rset3.getString(1)==null?"":rset3.getString(1));
					}else{
						VCFORM_FLAG.add("");
					}
				}else{
					VADJUST_FLAG_SN.add("");
					VADJUST_AMT_SN.add("");
					VADJUST_CUR_SN.add("");
					VCFORM_FLAG.add("");
				}
				queryString = "select sum(adjusted_amt) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
						+ "AND adjust_FLAG='Y' and commodity_type='RLNG' and contract_type='"+rset1.getString(8)+"'  ";
		//	System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					balance_adjust_amt=tot_adjust_amt-rset2.getDouble(1);
				}else{
					balance_adjust_amt=tot_adjust_amt;
				}
				queryString = "select sum(hold_amount) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
						+ "AND commodity_type='RLNG' and contract_type='"+rset1.getString(8)+"' and release_flag='Y' ";
			//System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					balance_adjust_amt+=rset2.getDouble(1);
				}
				VADJUST_balc_SN.add(nf6.format(balance_adjust_amt));
				/////HERE
				if(!payamt.equals("")){
					if(invstatus.equalsIgnoreCase("PAID")){

						if(rset1.getString(24)!=null)
						{
							double tdsper=Double.parseDouble(rset1.getString(24));
							double salesval=0;
							if(agr_base.equalsIgnoreCase("D")){
								salesval=Double.parseDouble(gross_amt_trans);
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//double netrecv=invval-tdsval;
							double netrecv=0;
							if(rset1.getString(32)!=null)
							{
								netrecv=invval-tdsval-Double.parseDouble(rset1.getString(33));
							}else{
								netrecv=invval-tdsval;
							}
							double actval=Double.parseDouble(payamt);
							short_recv=netrecv-actval;
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt);
						}
					
					}else{
						if(rset1.getString(21)!=null)
						{
							double tdsper=Double.parseDouble(rset1.getString(21));
							double salesval=0;
							if(agr_base.equalsIgnoreCase("D")){
								salesval=Double.parseDouble(gross_amt_trans);
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//double netrecv=invval-tdsval;
							String netrecv="";
							if(rset1.getString(30)!=null)
							{
								netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(31)));
							}else{
								netrecv=nf6.format(invval-tdsval);
							}
							double actval=Double.parseDouble(payamt);
							short_recv=Double.parseDouble(netrecv)-actval;
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt);
						}
					}
					Vpay_short_recv_amt.add(short_recv);
				}else{
					Vpay_short_recv_amt.add("-");
				}
				Vgross_trans_inr.add(gross_amt_trans);
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					if(rset1.getString(14)!=null)
					{
						Vpay_actual_recv_amt.add(rset1.getString(14));
					}
					else
					{
						Vpay_actual_recv_amt.add("-");
					}
					
					if(rset1.getString(15)!=null)
					{
						Vpay_recv_dt.add(rset1.getString(15));
					}
					else
					{
						Vpay_recv_dt.add("-");
					}
					
					if(rset1.getString(16)!=null)
					{
						Vpay_remark.add(rset1.getString(16));
					}
					else
					{
						Vpay_remark.add("-");
					}
					
					/*if(rset1.getString(24)!=null)
					{
						double tdsper=Double.parseDouble(rset1.getString(24));
						double salesval=Double.parseDouble(rset1.getString(5));
						double tdsval=(tdsper*salesval)/100;
						double invval=Double.parseDouble(rset1.getString(6));
						double netrecv=invval-tdsval;
						double actval=Double.parseDouble(rset1.getString(14));
						short_recv=netrecv-actval;
					}
					else
					{
						short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(rset1.getString(14));
					}
					
					Vpay_short_recv_amt.add(short_recv+"");*/
				
					Vpayflag.add("PAID");
					
					if(rset1.getString(17)!=null)
						Vinv_gen_by_cd.add(rset1.getString(17));
					else
						Vinv_gen_by_cd.add("00");
						
					if(rset1.getString(18)!=null)
						Vchecked_by_cd.add(rset1.getString(18));
					else
						Vchecked_by_cd.add("00");
					
					if(rset1.getString(19)!=null)
						Vapproved_by_cd.add(rset1.getString(19));
					else
						Vapproved_by_cd.add("00");
					
					if(rset1.getString(20)!=null)
					{
						Vpdf_inv_dtl.add(rset1.getString(20));
					}
					else
					{
						Vpdf_inv_dtl.add("-");
					}
					
					pdf_inv_dtl=rset1.getString(20);
					
					if(rset1.getString(21)!=null)
						print_by_ori=rset1.getString(21);
					else
						print_by_ori="00";
					
					if(rset1.getString(22)!=null)
						print_by_dup=rset1.getString(22);
					else
						print_by_dup="00";
					
					if(rset1.getString(23)!=null)
						print_by_tri=rset1.getString(23);
					else
						print_by_tri="00";
					
					////System.out.println("rset1.getString(24): "+rset1.getString(24));
					
					if(rset1.getString(24)!=null)
						Vtds_per.add(rset1.getString(24));
					else
						Vtds_per.add("-");
					
					if(pdf_inv_dtl.contains("T"))
					{
						Vprint_by_cd.add(print_by_tri);
					}
					else if(pdf_inv_dtl.contains("D"))
					{
						Vprint_by_cd.add(print_by_dup);
					}
					else if(pdf_inv_dtl.contains("O"))
					{
						Vprint_by_cd.add(print_by_ori);
					}
					else
					{
						Vprint_by_cd.add("-");
					}
					
					if(rset1.getString(25)!=null)
						Vpay_update_dt.add(rset1.getString(25));
					else
						Vpay_update_dt.add("-");
					
					if(rset1.getString(26)!=null)
						Vpay_update_cnt.add(rset1.getString(26));
					else
						Vpay_update_cnt.add("-");
					
					if(taxadvflag.equalsIgnoreCase("Y"))
					{
						////System.out.println("HERE IN IF");
						if(rset1.getString(27)!=null)
						{
							double t=Double.parseDouble(rset1.getString(27))-Double.parseDouble(tax_adj_amt);
							////System.out.println("IN IF---t---: "+t);
							Vtottax.add(t);
						}	
						else
						{
							Vtottax.add("-");
						}
					}
					else
					{
						if(rset1.getString(27)!=null)
						{
							////System.out.println("IN ELSE---rset---: "+rset1.getString(27));
							Vtottax.add(rset1.getString(27));
						}	
						else
						{
							Vtottax.add("-");
						}
					}
				}
				else
				{
					Vpay_actual_recv_amt.add("-");
					Vpay_recv_dt.add("-");
					Vpay_remark.add("-");
					//Vpay_short_recv_amt.add("-");
					Vpayflag.add("UNPAID");
					
					if(rset1.getString(14)!=null)
						Vinv_gen_by_cd.add(rset1.getString(14));
					else
						Vinv_gen_by_cd.add("00");
					
					if(rset1.getString(15)!=null)
						Vchecked_by_cd.add(rset1.getString(15));
					else
						Vchecked_by_cd.add("00");
						
					if(rset1.getString(16)!=null)
						Vapproved_by_cd.add(rset1.getString(16));
					else
						Vapproved_by_cd.add("00");
						
					if(rset1.getString(17)!=null)
					{
						Vpdf_inv_dtl.add(rset1.getString(17));
					}
					else
					{
						Vpdf_inv_dtl.add("-");
					}
					
					pdf_inv_dtl=rset1.getString(17);
					
					if(rset1.getString(18)!=null)
						print_by_ori=rset1.getString(18);
					else
						print_by_ori="00";
					
					if(rset1.getString(19)!=null)
						print_by_dup=rset1.getString(19);
					else
						print_by_dup="00";
					
					if(rset1.getString(20)!=null)
						print_by_tri=rset1.getString(20);
					else
						print_by_tri="00";
						
					if(rset1.getString(21)!=null)
					{
						Vtds_per.add(rset1.getString(21));
					}
					else
					{
						Vtds_per.add("-");
					}
					//System.out.println("===pdf-inv_dtl=="+pdf_inv_dtl);
					
					if(pdf_inv_dtl.contains("T"))
					{
						Vprint_by_cd.add(print_by_tri);
					}
					else if(pdf_inv_dtl.contains("D"))
					{
						Vprint_by_cd.add(print_by_dup);
					}
					else if(pdf_inv_dtl.contains("O"))
					{
						Vprint_by_cd.add(print_by_ori);
					}
					else
					{
						Vprint_by_cd.add("-");
					}
					
					if(rset1.getString(22)!=null)
						Vpay_update_dt.add(rset1.getString(22));
					else
						Vpay_update_dt.add("-");
					
					if(rset1.getString(23)!=null)
						Vpay_update_cnt.add(rset1.getString(23));
					else
						Vpay_update_cnt.add("-");
					
					if(taxadvflag.equalsIgnoreCase("Y"))
					{
						if(rset1.getString(24)!=null)
						{
							double t=Double.parseDouble(rset1.getString(24))-Double.parseDouble(tax_adj_amt);
							Vtottax.add(t);
						}	
						else
						{
							Vtottax.add("-");
						}
					}
					else
					{
						if(rset1.getString(24)!=null)
						{
							Vtottax.add(rset1.getString(24));
						}	
						else
						{
							Vtottax.add("-");
						}
					}
				}
				
				////System.out.println("===JAVA===2");
				/////////Vhlpl_inv_seq//////////
				
				if(Integer.parseInt(rset1.getString(3))<10)
				{
					hlpl_inv_no_disp="000"+rset1.getString(3)+"/"+rset1.getString(9);
				}
				else if(Integer.parseInt(rset1.getString(3))<100) 
				{
					hlpl_inv_no_disp="00"+rset1.getString(3)+"/"+rset1.getString(9);
				}
				else if(Integer.parseInt(rset1.getString(3))<1000) 
				{
					hlpl_inv_no_disp="0"+rset1.getString(3)+"/"+rset1.getString(9);
				}
				else
				{
					hlpl_inv_no_disp=rset1.getString(3)+"/"+rset1.getString(9);
				}
				
				if(!hlpl_inv_no_disp.equalsIgnoreCase(""))
				{
					Vhlpl_inv_seq.add(hlpl_inv_no_disp);
				}
				else
				{
					Vhlpl_inv_seq.add("-");
				}
				
				////System.out.println("===JAVA===3");
				
				///////////XML_GEN_FLAG/////////
				
				String queryString2 = "SELECT XML_GEN_FLAG,to_char(APPROVAL_DT,'dd/mm/yyyy') FROM FMS7_ACCOUNT_APPROVED_DTL " +
						  "WHERE INV_CARGO_NO='"+hlpl_inv_no_disp+"' AND JOURNAL_TYPE='FMSSL' " +
						  "AND CONTRACT_TYPE='"+contract_type+"' ";
					//	//System.out.println("Confirmed FMS7_PROV_SELLER_PAY Details Query = "+queryString);
					rset = stmt.executeQuery(queryString2);
					if(rset.next())
					{
						xml_gen_flag=rset.getString(1)==null?"N":rset.getString(1);
						approval_dt=rset.getString(2)==null?"":rset.getString(2);
						Vxml_gen_flag.add(xml_gen_flag);
					}
					else
					{
						xml_gen_flag="N";
						approval_dt="";
						Vxml_gen_flag.add(xml_gen_flag);
					}
						
					////System.out.println("===JAVA===4");
					
					///tax_adj
					
					////System.out.println("===JAVA===5");
					
					///////////inv_adj_amt///////////
					
					String q2="select flag,amount from fms7_inv_compo_dtl where price_cd='1' and inv_seq_no='"+invseq+"'";
					////System.out.println("JAVA check adjustmn query q2: "+q2);
					rset3=stmt3.executeQuery(q2);
					if(rset3.next())
					{
						if(rset3.getString(1)!=null)
						{
							advflag=rset3.getString(1);
						}
						else
						{
							advflag="N";
						}
						
						Vinv_adj_flag.add(advflag);
						
						if(rset3.getString(2)!=null)
						{
							inv_adj_amt=nf.format(Double.parseDouble(rset3.getString(2)));
						}
						else
						{
							inv_adj_amt="0";
						}
					}
					else
					{
						advflag="N";
						inv_adj_amt="0";
						Vinv_adj_flag.add(advflag);
					}
					////System.out.println("---JAVA here advflag: "+advflag);
								
					////System.out.println("===JAVA===6");
					
					////////////inv_amt///////////
					
					String inv_amt="";
					if(advflag.equalsIgnoreCase("Y") && taxadvflag.equalsIgnoreCase("Y"))
					{
						//query2="select INV_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
						inv_amt=invamtinr;
					}
					else
					{
						//query2="select GROSS_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
						inv_amt=gross_amt_inr;
					}
					////System.out.println("---JAVA inv_amt---: "+inv_amt);
							
					////System.out.println("===JAVA===7");
					
					String amt = "0";
					
					String map_id=contract_type+":"+fyr+":"+hlpl_inv_seq_no+":%";
					Vmapid.add(map_id);
					
					if(contract_type.equalsIgnoreCase("T") || contract_type.equalsIgnoreCase("C"))		//ADDED FOR LTCORA AND CN
					{
						// String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no like" +
						//	" '"+map_id+"'and price_cd='6' and (flag_temp not like 'T' or flag_temp is null)";
						
						String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no='"+invseq+"' and price_cd IN ('6','10','11','12','13') "
								+ "and (flag_temp not like 'T' or flag_temp is null)";
						
						rset4=stmt4.executeQuery(queryTax);
						////System.out.println("---JAVA queryTax---: "+queryTax);
						if(rset4.next())
						{
							amt = ""+java.text.NumberFormat.getInstance().parse(rset4.getString(1));
							Adj_Tax_amt=amt;	
							taxvalue = ""+(Double.parseDouble(taxvalue) - Double.parseDouble(amt));
						}
					}
					
					////System.out.println("===JAVA===8");
					
					String queryString12 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
				 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
					
					////System.out.println("===VTAXAMT QUERY===: "+queryString12);
					rset5=stmt5.executeQuery(queryString12);
					////System.out.println("===JAVA===9");
					int cnt1=0;
					String tx_cd="",tx_amt="",tx_nm="";
					while(rset5.next())
					{
						tax_cd=rset5.getString(1);
						tax_on=rset5.getString(3);
						////System.out.println("---tax_on---"+tax_on);
						if(rset5.getString(3).equals("1")) 
						{
							cnt1++;
							////System.out.println("===IN IF=== "+cnt1);
							tax_amt = (Double.parseDouble(gross_amt_inr)*Double.parseDouble(rset5.getString(2)))/100;
							////System.out.println(Double.parseDouble(gross_amt_inr)+"---total_amt_inr-IN IF--"+tax_amt);
							////System.out.println("---rset5.getString(2)-IN IF--"+rset5.getString(2));
						}
						else if(rset5.getString(3).equals("2"))
						{
							cnt1++;
							////System.out.println("===IN ELSE IF=== "+cnt1);
							String q3 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
										   "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
										   "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
										   "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) AND A.tax_code='"+rset5.getString(4)+"'";
							
							////System.out.println("===TAXAMT Query q3 === "+q3);
							rset6=stmt6.executeQuery(q3);
				 	 		
							if(rset6.next())
				 	 		{
				 	 			if(rset6.getString(3).equals("1"))
				 				{
				 					tax_amt = (Double.parseDouble(gross_amt_inr)*Double.parseDouble(rset6.getString(2)))/100;
				 				}	
				 	 			////System.out.println("---total_amt_inr-IN ELSE IF--"+total_amt_inr);
								////System.out.println("---rset5.getString(2)-IN ELSE IF--"+rset5.getString(2));
				 	 			tax_amt = (tax_amt*Double.parseDouble(rset5.getString(2)))/100;
				 	 		}
				 	 		else
				 	 		{
				 	 			tax_amt=0;
				 	 		}
						}
						else
						{
							tax_amt = 0;
						}
						////System.out.println("===JAVA===9");
						double tax_diff=0;
						double tax_fact=Double.parseDouble(rset5.getString(2));
						if(tax_fact<tax_cform){
							tax_diff=tax_cform-tax_fact;
							tax_diff_val.add(tax_diff);
						}else{
							tax_diff_val.add("");
						}
						
						////////////////////////////BK:SB20151203//////////////////
			 			
						if(!Adj_Tax_amt.equalsIgnoreCase("") && taxadvflag.equalsIgnoreCase("Y"))
			 			{
							if(Double.parseDouble(Adj_Tax_amt)>0)
				 			{
								////System.out.println("---Adj Amt---: "+Adj_Tax_amt);
				 				tax_amt = tax_amt - Double.parseDouble(Adj_Tax_amt);
				 				Adj_Tax_amt = "0";
				 			}
							else
				 			{
								if(tax_on.equalsIgnoreCase("2"))
								{
									//taxamt=nf.format(tax_amt)+"";
									//Tot_Tax_amt = Tot_Tax_amt + Double.parseDouble(taxamt);
								}
				 			}
			 			}
						
						////System.out.println("===IN WHILE================= "+cnt1);
						
						///////////////////////////////////////////////////////
						queryString6="select sht_nm from fms7_tax_mst where tax_code='"+tax_cd+"'";
						rset6=stmt6.executeQuery(queryString6);
						while(rset6.next())
						{
							tax_nm=rset6.getString(1);
						}
						
						tx_cd+="@"+tax_cd;
						tx_amt+="@"+nf.format(tax_amt);
						tx_nm+="@"+tax_nm;
						
						////System.out.println("===JAVA tax_amt after=== "+tax_amt);
					}
					Vtax_code.add(tx_cd.replaceFirst("@", ""));  	
					Vtax_amt.add(tx_amt.replaceFirst("@", ""));
					Vtax_nm.add(tx_nm.replaceFirst("@", ""));
					////System.out.println("Vtax_code-"+Vtax_code);
					////System.out.println("Vtax_amt-"+Vtax_amt);
					
					String q123 = "SELECT COUNT(*) FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
				 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
					rset=stmt.executeQuery(q123);
					while(rset.next())
					{
						//cnt1=rset.getInt(1);
						int count=rset.getInt(1);
						Vtaxcnt.add(count);
					}
					
					//////////////////////////////////////////////////////////
					///fOR GETTING REFERENCE NO FOR CONTRACT_NO AND AGREEMENT_NO///
					String mapp_id="";
					if(rset1.getString(8).equals("C") || rset1.getString(8).equals("M") || rset1.getString(8).equals("B") || rset1.getString(8).equals("E")){
						if(invstatus.equalsIgnoreCase("PAID"))
						{
							mapp_id=rset1.getString(30)==null?"":rset1.getString(30);
						}else{
							mapp_id=rset1.getString(27)==null?"":rset1.getString(27);
						}
						//System.out.println("--mapp_id--"+mapp_id);
						String temp_mapp_id[]=mapp_id.split("-");
						VAgreement_no.add(temp_mapp_id[1]+"");
						VAgreement_rev_no.add(temp_mapp_id[2]+"");
						Vcont_no.add(temp_mapp_id[3]+"");
						Vcont_rev_no.add(temp_mapp_id[4]+"");
						if(""+temp_mapp_id[3]!=""){
							int temp_cont_no=Integer.parseInt(temp_mapp_id[3]+"");
							if(temp_cont_no>999){
								Vcont_typ.add("LTCORA (Period)");
							}else{
								Vcont_typ.add("LTCORA (CN)");
							}
						}else{
							Vcont_typ.add("LTCORA (CN)");
						}
						
					}else if(rset1.getString(8).equals("S")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("SN");
					}else if(rset1.getString(8).equals("L")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("LOA");
					}
					else if(rset1.getString(8).equals("I")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("Interest");
					}
					else if(rset1.getString(8).equals("R")){
						Vcont_typ.add("Regas");
					}
				}
					//////
			}
			
			//
			//FOR GETTING DEBIT NOTE FROM SYSTEM
			if(!customer_cd.equalsIgnoreCase(""))
			{
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
								 " DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
								 + ",TDS_PERCENT,"
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,"
								 + "FLAG,TO_CHAR(DUE_DT,'dd/mm/yyyy'),DR_CR_FLAG,DR_CR_NO,GROSS_AMT_INR,CRITERIA,REMARK,SUP_STATE_CODE,"
								 + "tds_tax_percent,tds_tax_amt,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,TOTAL_QTY " +
								 "FROM FMS7_DR_CR_NOTE WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APrv_by is not null AND "
								 + " PAY_RECV_AMT IS NOT NULL AND "
								 + "PAY_INSERT_BY IS NOT NULL and dr_cr_flag='dr' "
								 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
				else
				{
					queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
								 " DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
								 + ",TDS_PERCENT,"
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,"
								 + "FLAG,TO_CHAR(DUE_DT,'dd/mm/yyyy'),DR_CR_FLAG,DR_CR_NO,GROSS_AMT_INR,CRITERIA,REMARK,SUP_STATE_CODE,"
								 + "tds_tax_percent,tds_tax_amt,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,TOTAL_QTY " +
								 "FROM FMS7_DR_CR_NOTE WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') and  APrv_by is not null AND "
//								 + " PAY_RECV_AMT IS NULL AND "
//								 + "PAY_INSERT_BY IS NULL and "
								 + "dr_cr_flag='dr' "
								 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
			}
			else
			{
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
								 "DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
								 + ",TDS_PERCENT,"
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,"
								 + " FLAG,TO_CHAR(DUE_DT,'dd/mm/yyyy'),DR_CR_FLAG,DR_CR_NO,GROSS_AMT_INR,CRITERIA,REMARK,"
								 + "SUP_STATE_CODE,tds_tax_percent,tds_tax_amt,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,TOTAL_QTY " +
								 "FROM FMS7_DR_CR_NOTE WHERE "+cnt_typ+" AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APrv_by is not null AND "
								 + " PAY_RECV_AMT IS NOT NULL AND "
								 + "PAY_INSERT_BY IS NOT NULL and dr_cr_flag='dr' "
								 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
				else
				{
					queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
								 "DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
								 + ",TDS_PERCENT,"
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,"
								 + " FLAG,TO_CHAR(DUE_DT,'dd/mm/yyyy'),DR_CR_FLAG,DR_CR_NO,GROSS_AMT_INR,CRITERIA,REMARK,SUP_STATE_CODE,"
								 + "tds_tax_percent,tds_tax_amt,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,TOTAL_QTY " +
								 "FROM FMS7_DR_CR_NOTE WHERE "+cnt_typ+" AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY')  AND APrv_by is not null AND "
								 //+ " PAY_RECV_AMT IS NULL AND PAY_INSERT_BY IS NULL and "
								 + "dr_cr_flag='dr' "
								 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
			}
			//System.out.println("===fetch DR CR queryString123===: "+queryString123);
			rset1=stmt1.executeQuery(queryString123);
			while(rset1.next())
			{
				Vdrcrcriteria.add(rset1.getString(27)==null?"":rset1.getString(27));
				Vdrcrflag.add(rset1.getString(24)==null?"":rset1.getString(24));
				if(invstatus.equalsIgnoreCase("PAID")){
					Vtds_tax_percent.add(rset1.getString(30)==null?"":rset1.getString(30));
					Vtds_tax_amt.add(rset1.getString(31)==null?"":rset1.getString(31));
				}else{
					Vtds_tax_percent.add(rset1.getString(30)==null?"":rset1.getString(30));
					Vtds_tax_amt.add(rset1.getString(31)==null?"":rset1.getString(31));
				}
				String payamt="",inv_take_flag="Y",sup_state_cd ="";String short_recv_="0";
				String queryString_agr_base="";String transp_charges="0";
				if(invstatus.equalsIgnoreCase("PAID")){
					if(rset1.getString(7).equals("S")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND FGSA_REV_NO='"+rset1.getString(33)+"' AND SN_NO='"+rset1.getString(34)+"' AND SN_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}else if(rset1.getString(7).equals("L")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND LOA_REV_NO='"+rset1.getString(35)+"' AND LOA_NO='"+rset1.getString(34)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}
					if(agr_base.equals("D")){
						String queryString_tr="";
						if(rset1.getString(7).equals("S")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND FGSA_REV_NO='"+rset1.getString(33)+"' AND SN_NO='"+rset1.getString(34)+"' AND SN_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							rset2=stmt2.executeQuery(queryString_tr);
						}else if(rset1.getString(7).equals("L")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND LOA_NO='"+rset1.getString(34)+"' AND LOA_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							rset2=stmt2.executeQuery(queryString_tr);
						}
						//System.out.println("queryString-PAID-"+queryString_tr);
						if(rset2.next()){
							transp_charges=rset2.getString(1)==null?"0":rset2.getString(1);
						}
					
					
					}
				}else{
					if(rset1.getString(7).equals("S")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND FGSA_REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND SN_NO='"+rset1.getString(34)+"' AND SN_REV_NO='"+rset1.getString(35)+"'";
						//System.out.println("queryString_agr_base--"+queryString_agr_base);
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								//System.out.println("queryString_agr_base-if-"+queryString_agr_base);
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							//System.out.println("queryString_agr_base-else-"+queryString_agr_base);
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}else if(rset1.getString(7).equals("L")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND LOA_NO='"+rset1.getString(34)+"' AND LOA_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						//System.out.println("queryString_agr_base--"+queryString_agr_base);
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(32)+"'  AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								//System.out.println("queryString_agr_base-if-"+queryString_agr_base);
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(32)+"'  AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							//System.out.println("queryString_agr_base-else-"+queryString_agr_base);
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}
					//System.out.println("agr_base---"+agr_base+"---"+rset1.getString(8));
					if(agr_base.equals("D")){
						String queryString_tr="";
						if(rset1.getString(8).equals("S")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(32)+"' AND FGSA_REV_NO='"+rset1.getString(33)+"' AND SN_NO='"+rset1.getString(34)+"' AND SN_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							//System.out.println("queryString-UNPAID-if --"+queryString_tr);
							rset3=stmt3.executeQuery(queryString_tr);
							//System.out.println("queryString-UNPAID-"+queryString_tr);
							if(rset3.next()){
								transp_charges=rset3.getString(1)==null?"0":rset3.getString(1);
							}
						}else if(rset1.getString(8).equals("L")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(32)+"' AND LOA_NO='"+rset1.getString(34)+"' AND LOA_REV_NO='"+rset1.getString(35)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							//System.out.println("queryString-UNPAID-else --"+queryString_tr);
							rset3=stmt3.executeQuery(queryString_tr);
							if(rset3.next()){
								transp_charges=rset3.getString(1)==null?"0":rset3.getString(1);
							}
						}
					}
				}
				//System.out.println("transp_charges------"+transp_charges+"--"+rset1.getString(3));
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					payamt=rset1.getString(12)==null?"":rset1.getString(12);
					inv_take_flag="Y";
					//Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
					//sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
				}else{
					payamt=rset1.getString(12)==null?"":rset1.getString(12);
					//Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
					//sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
				//	System.out.println("payamt---"+payamt);
					if(payamt.equals("")){
						inv_take_flag="Y";
					}else{
						if(rset1.getString(17)!=null)
						{
							double tdsper=Double.parseDouble(rset1.getString(17));
							//double salesval=Double.parseDouble(rset1.getString(5));
							double salesval=0;
							if(agr_base.equals("D") && Double.parseDouble(transp_charges)>0){
								double temp_grossamt=(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))+Double.parseDouble(rset1.getString(36)) * Double.parseDouble(transp_charges));
								salesval=temp_grossamt;
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//double netrecv=invval-tdsval;
							String netrecv="";
							if(rset1.getString(30)!=null)
							{
								netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(31)));
							}else{
								netrecv=nf6.format(invval-tdsval);
							}
							double actval=Double.parseDouble(rset1.getString(12));
							short_recv_=nf6.format(Double.parseDouble(netrecv)-actval);
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv_=nf6.format(Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt));
						}
					//	System.out.println("short_recv_---"+short_recv_);
								if(Double.parseDouble(short_recv_) <= Double.parseDouble(Inv_roundoff+"")){
									inv_take_flag="N";
								}
								else{
									inv_take_flag="Y";
								}
					}
				}
				if(inv_take_flag.equals("Y")){
				cnt++;
				String temp_flag = "";
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					VPAY_NEW_INV_SEQ_NO.add(rset1.getString(21)==null?"":rset1.getString(21));
					VPAY_FLAG.add(rset1.getString(22)==null?"Y":rset1.getString(22));
					temp_flag = rset1.getString(22)==null?"Y":rset1.getString(22);
					Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
					sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
				} else {
					VPAY_NEW_INV_SEQ_NO.add(rset1.getString(21)==null?"":rset1.getString(21));
					VPAY_FLAG.add(rset1.getString(22)==null?"Y":rset1.getString(22));
					temp_flag = rset1.getString(22)==null?"Y":rset1.getString(22);
					Vsup_state_cd.add(rset1.getString(29)==null?"":rset1.getString(29));
					sup_state_cd = rset1.getString(29)==null?"":rset1.getString(29);
				}
				flag_inv.add("D");
					
				//for tcs
				String query="select tcs_amt from fms7_invoice_tcs_dtl where hlpl_inv_seq_no='"+rset1.getString(25)+"' and contract_type='"+rset1.getString(7)+"' and "
						+ "financial_year='"+rset1.getString(8)+"' and customer_cd='"+rset1.getString(2)+"' and invoice_type ='DEBIT' AND FLAG='Y' and COMMODITY_TYPE='RLNG'"
						+ " and sup_state_code='"+sup_state_cd+"' ";
				rset=stmt.executeQuery(query);
//				System.out.println("===JAVA===1"+query);
				
				if(rset.next()){
					Vtcs_amt.add(rset.getString(1)==null?"":rset.getString(1));
				}else{
					if(rset1.getString(27).equalsIgnoreCase("TCS")) {
						Vtcs_amt.add(rset1.getString(6));
					}else {
						Vtcs_amt.add("");
					}
				}
				
				//Hiren_20210621 for TDS calc.
				String tds_app_flag = "N";
				tds_app_amt = "0";
				if(rset1.getString(7).equals("S") || rset1.getString(7).equals("L")) {
					tds_app_flag = checkforTDSApplicable(rset1.getString(4),rset1.getString(2),rset1.getString(8),rset1.getString(25),"RLNG","DEBIT",sup_state_cd);
					
				}
				if(segment.equalsIgnoreCase("LTCORA_CN")) {
					Vtds_app_flag.add("Y");
					Vtds_app_amt.add(rset1.getString(5)==null?"0":rset1.getString(5));
				}else {
					Vtds_app_flag.add(tds_app_flag);
					Vtds_app_amt.add(tds_app_amt);
				}
				////////
				////System.out.println("===JAVA===1");
				if(rset1.getString(1)!=null)
				{
					Vmonth.add(rset1.getString(1));
				}
				else
				{
					Vmonth.add("-");
				}
				
				if(rset1.getString(2)!=null)
				{
					Vcustomer_cd.add(rset1.getString(2));
				}
				else
				{
					Vcustomer_cd.add("-");
				}
				
				String queryString1 = "SELECT CUSTOMER_NAME,CUSTOMER_ABBR FROM FMS7_CUSTOMER_MST A WHERE CUSTOMER_CD='"+rset1.getString(2)+"'"
						+ "  AND " +
					  "A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_MST B " +
					  "WHERE A.customer_cd=B.customer_cd AND " +
					  "B.eff_dt<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY') and CUSTOMER_CD='"+rset1.getString(2)+"')";
				rset=stmt.executeQuery(queryString1);
				while(rset.next())
				{
					if(rset.getString(2)!=null)
					{
						Vcustomer_abbr.add(rset.getString(2));
						Vcustomer_name.add(rset.getString(1));
					}
					else
					{
						Vcustomer_abbr.add("-");
						Vcustomer_name.add("-");
					}
				}
				
				
				hlpl_inv_seq_no=rset1.getString(3);
				if(rset1.getString(3)!=null)
				{
					Vhlplinvseqno.add(rset1.getString(3));
				}
				else
				{
					Vhlplinvseqno.add("-");
				}
				
				
				String tmp_fgsa_no="",tmp_fgsa_rev_no="",tmp_sn_no="",tmp_sn_rev_no="",TMP_QTY="";
				String Chk_by="",pdfinvdtl="",print_ori="",print_dupli="",print_trip="",mappid="";
				String queryString456="SELECT FGSA_NO,FGSA_REV_NO,SN_REV_NO,PLANT_SEQ_NO,TO_CHAR(PERIOD_START_DT,'DD/MM/YYYY'),"
						+ "TO_CHAR(PERIOD_END_DT,'DD/MM/YYYY'),EXCHG_RATE_CD,EXCHG_RATE_TYPE,CUST_INV_SEQ_NO,APPROVED_FLAG,TOTAL_QTY,checked_by,"
						+ "PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,mapping_id,sn_no " + 
						 "FROM FMS7_INVOICE_MST WHERE HLPL_INV_SEQ_NO='"+hlpl_inv_seq_no+"' "
						 + "AND FINANCIAL_YEAR='"+rset1.getString(8)+"' AND "
						 + "CONTRACT_TYPE='"+rset1.getString(7)+"' "
						 + " and sup_state_code='"+sup_state_cd+"' ";
				rset=stmt.executeQuery(queryString456);
				//System.out.println("---queryString456---"+queryString456);
				if(rset.next())
				{
					if(rset.getString(1)!=null)
						Vfgsa_no.add(rset.getString(1));
					else
						Vfgsa_no.add("-");
					
					if(rset.getString(2)!=null)
						Vfgsa_rev_no.add(rset.getString(2));
					else
						Vfgsa_rev_no.add("-");
						
					if(rset.getString(3)!=null)
						Vsn_rev_no.add(rset.getString(3));
					else
						Vsn_rev_no.add("-");
					
					if(rset.getString(4)!=null)
						Vplant_seq_no.add(rset.getString(4));
					else
						Vplant_seq_no.add("-");
					
					tmp_fgsa_no=rset.getString(1)==null?"":rset.getString(1);
					tmp_fgsa_rev_no=rset.getString(2)==null?"":rset.getString(2);
					tmp_sn_no=rset.getString(18)==null?"":rset.getString(18);
					tmp_sn_rev_no=rset.getString(3)==null?"":rset.getString(3);
					TMP_QTY=rset.getString(11)==null?"":rset.getString(11);
					Chk_by= rset.getString(12)==null?"":rset.getString(12);
					pdfinvdtl=rset.getString(13)==null?"":rset.getString(13);
					print_ori=rset.getString(14)==null?"":rset.getString(14);
					print_dupli=rset.getString(15)==null?"":rset.getString(15);
					print_trip=rset.getString(16)==null?"":rset.getString(16);
					mappid=rset.getString(17)==null?"":rset.getString(17);
					
					String q1="SELECT NVL(A.plant_name,' ') FROM FMS7_CUSTOMER_PLANT_DTL A " +
							   "WHERE A.customer_cd="+rset1.getString(2)+" AND A.seq_no="+rset.getString(4)+" " +
							   "AND A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_PLANT_DTL B " +
							   "WHERE A.customer_cd=B.customer_cd AND A.seq_no=B.seq_no " +
							   "AND B.eff_dt<=TO_DATE('"+rset.getString(5)+"','DD/MM/YYYY'))";
					
					//String q1="SELECT PLANT_NAME FROM FMS7_CUSTOMER_PLANT_DTL  WHERE CUSTOMER_CD='"+rset1.getString(2)+"' AND SEQ_NO='"+rset.getString(4)+"'";
					////System.out.println("---PLANT_NAME QUERY---"+q1);
					rset6=stmt6.executeQuery(q1);
					
					while(rset6.next())
					{
						if(rset6.getString(1)!=null)
							Vplant_seq_nm.add(rset6.getString(1));
						else
							Vplant_seq_nm.add("-");
						
						////System.out.println("---Vplant_seq_nm---:"+rset6.getString(1));
					}
					
					if(rset.getString(5)!=null)
						Vperiod_st_dt.add(rset.getString(5));
					else
						Vperiod_st_dt.add("-");
						
					if(rset.getString(6)!=null)
						Vperiod_end_dt.add(rset.getString(6));
					else
						Vperiod_end_dt.add("-");
					
					if(rset.getString(7)!=null)
						Vexchg_rate_cd.add(rset.getString(7));
					else
						Vexchg_rate_cd.add("-");
					
					if(rset.getString(8)!=null)
						Vexchg_rate_type.add(rset.getString(8));
					else
						Vexchg_rate_type.add("-");
					
					if(rset.getString(9)!=null)
						Vcust_inv_seq_no.add(rset.getString(9));
					else
						Vcust_inv_seq_no.add("-");
					
					if(rset.getString(10)!=null)
						Vapproved_flag.add(rset.getString(10));
					else
						Vapproved_flag.add("-");
					
					/*String queryString_agr_base="";
					if(rset1.getString(7).equals("S")){
						queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset.getString(1)+"' AND REV_NO='"+rset.getString(2)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						//System.out.println("queryString_agr_base---"+queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
						}
					}else if(rset1.getString(7).equals("L")){
						queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset.getString(1)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
						}
					}*/
				}else{
					Vsn_rev_no.add("-");
					Vapproved_flag.add("-");
					Vcust_inv_seq_no.add("-");
					Vexchg_rate_type.add("-");
					Vexchg_rate_cd.add("-");
					Vperiod_end_dt.add("-");
					Vperiod_st_dt.add("-");
					Vplant_seq_nm.add("-");
					Vplant_seq_no.add("-");
					Vfgsa_rev_no.add("-");
					Vfgsa_no.add("-");
				}
				//System.out.println("agr_base--"+agr_base);
				VAgreement_base.add(agr_base);
				if(rset1.getString(4)!=null)
				{
					Vinv_dt.add(rset1.getString(4));
				}
				else
				{
					Vinv_dt.add("-");
				}
				
				if(agr_base.equalsIgnoreCase("D")){

					String queryString_tr="";
					//String transp_charges="";
					String temp_grossamt="";
					/*if(rset1.getString(7).equals("S")){
						queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+tmp_fgsa_no+"' AND FGSA_REV_NO='"+tmp_fgsa_rev_no+"' AND SN_NO='"+tmp_sn_no+"' AND SN_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
					}else if(rset1.getString(7).equals("L")){
						queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+tmp_fgsa_no+"' AND LOA_NO='"+tmp_sn_no+"' AND LOA_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
					}
					rset2=stmt2.executeQuery(queryString_tr);
					//System.out.println("queryString--"+queryString_tr);
					if(rset2.next()){
						transp_charges=rset2.getString(1)==null?"":rset2.getString(1);
					}*/
					//System.out.println("---rset.getString(7)--"+rset1.getString(5)+"--transp_charges--"+transp_charges+"--TMP_QTY---"+TMP_QTY);
					if(!transp_charges.equals("")){
						if(rset1.getString(27).equalsIgnoreCase("TCS")) {
							
							temp_grossamt=""+(Double.parseDouble(rset1.getString(26)==null?"0":rset1.getString(26))+Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
							//Vsales_value.add(temp_grossamt);
							Vsales_value.add(rset1.getString(5)==null?"":rset1.getString(5));
							gross_amt_inr=temp_grossamt;
							if(Double.parseDouble(transp_charges)>0){
								//gross_amt_trans=""+(Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
								gross_amt_trans=gross_amt_inr;
							}else
							{
								gross_amt_trans=rset1.getString(26)==null?"":rset1.getString(26);
							}
							
						}else {
							//for tax//
							double taxtot=0;
							String grs=rset1.getString(5)==null?"":rset1.getString(5);
							if(grs.equals("")){
								grs="0";
							}
							double gross_inr=Double.parseDouble(nf6.format(Double.parseDouble(grs)));
							queryString = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0.00'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
									  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
									  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
									  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) ORDER BY A.tax_code";
							////System.out.println("STEP-1A.3:FMS7_TAX_STRUCTURE_DTL: "+queryString);
							rset=stmt.executeQuery(queryString);
							while(rset.next())
							{
								//tax_factor+= Integer.parseInt((rset.getString(2)));
								if(rset.getString(3).equals("1"))
								{
									//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset.getString(2)))/100;
									tax_amt = (gross_inr*Double.parseDouble(rset.getString(2)))/100;
									
								}
								else if(rset.getString(3).equals("2"))
								{
									queryString1 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
											  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
											  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
											  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) AND A.tax_code='"+rset.getString(4)+"'";
								//////System.out.println("Query For Finding Out Tax Which Is Dependent On Other Tax Value = "+queryString1);
							 		rset2=stmt2.executeQuery(queryString1);
							 		if(rset2.next())
							 		{
							 			if(rset2.getString(3).equals("1"))
										{
											//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset1.getString(2)))/100;
							 				tax_amt = (gross_inr*Double.parseDouble(rset2.getString(2)))/100;
										}
									
						 			tax_amt = (tax_amt*Double.parseDouble(rset.getString(2)))/100;
							 		}
							 		else
							 		{
							 			tax_amt = 0;
							 		}
								}
								else
								{
									tax_amt = 0;
								}
							
							//customer_Invoice_Tax_Amt.add(nf.format(Math.round(tax_amt)));
							
							taxtot+=Math.round(tax_amt);
							//net_amt.add(net_amt_inr[i]);
						}
							//
							temp_grossamt=""+(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))+Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
							//Vsales_value.add(temp_grossamt);
							//Vsales_value.add(rset1.getString(5)==null?"":rset1.getString(5));
							gross_amt_inr=temp_grossamt;
							String gross_temp="";
							String calcnetamt=nf6.format(Double.parseDouble(temp_grossamt)+taxtot);
							//System.out.println("---calcnetamt--"+calcnetamt+"---"+rset1.getString(6)+"----"+temp_grossamt+"-----"+taxtot);
							if(Double.parseDouble(calcnetamt)==Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))){
								Vsales_value.add(temp_grossamt);
							}else{
								String diff="";
								//if(Double.parseDouble(calcnetamt)>Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))){
									
									diff=nf6.format(Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))-Double.parseDouble(calcnetamt));
									//System.out.println("---diff-if-"+diff);
								/*}else{
									diff=nf6.format(Double.parseDouble(calcnetamt)-Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6)));
									System.out.println("---diff-else-"+diff);
								}*/
								
								gross_temp=nf6.format(Double.parseDouble(temp_grossamt)+(Double.parseDouble(diff)));
								//System.out.println("---gross_temp--"+gross_temp+"--"+temp_grossamt);
								Vsales_value.add(gross_temp);
								
							}
							
							if(Double.parseDouble(transp_charges)>0){
								//gross_amt_trans=""+(Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
								//gross_amt_trans=gross_amt_inr;
								//gross_amt_trans=gross_temp;
								if(gross_temp.equals("")){
									gross_amt_inr=temp_grossamt;
									gross_amt_trans=temp_grossamt;//RG20230424
								}else{
									gross_amt_inr=gross_temp;
								}
							}else
							{
								gross_amt_trans=rset1.getString(5)==null?"":rset1.getString(5);
							}
						}
						//System.out.println("---temp====="+temp_grossamt);
					}else{
						if(rset1.getString(27).equalsIgnoreCase("TCS")) {
							Vsales_value.add(rset1.getString(26)==null?"0":rset1.getString(26));
							gross_amt_inr=rset1.getString(26);
							gross_amt_trans=rset1.getString(26)==null?"":rset1.getString(26);
						}else {
							Vsales_value.add(rset1.getString(5)==null?"0":rset1.getString(5));
							gross_amt_inr=rset1.getString(5);
							gross_amt_trans=rset1.getString(5)==null?"":rset1.getString(5);
						}
					}
				
				}else{
					if(rset1.getString(27).equalsIgnoreCase("TCS")) {
						
						if(rset1.getString(26)!=null)
						{
							Vsales_value.add(rset1.getString(26));
						}
						else
						{
							Vsales_value.add("0");
						}
							
						gross_amt_inr=rset1.getString(26);
					}else {
						
						if(rset1.getString(5)!=null)
						{
							Vsales_value.add(rset1.getString(5));
						}
						else
						{
							Vsales_value.add("0");
						}
							
						gross_amt_inr=rset1.getString(5);
					}
				}
				Vgross_trans.add(gross_amt_trans);
				String diff_tcs = "0";
				String diff_tcs_flg = "N";
				/*Hiren_20210407 for TCS Perc*/
				if(rset1.getString(27).equalsIgnoreCase("TCS")) {
				
					if(rset1.getString(28).contains("--")) {
						 String temp_arr[] = rset1.getString(28).split("--");
						 diff_tcs = temp_arr[1];
					}
					Vdiff_tcs.add("As per TCS: "+nf2.format(Double.parseDouble(diff_tcs+""))+" %");
					Vdiff_tcs_flg.add("Y");
				}else {
					Vdiff_tcs.add("0");
					Vdiff_tcs_flg.add("N");
				}
				
				
				////
				
				if(rset1.getString(6)!=null)
				{
					Vinv_value.add(rset1.getString(6));
				}
				else
				{
					Vinv_value.add("-");
				}
				
				String duedt=rset1.getString(23)==null?"":rset1.getString(23);
				String drcr_flg=rset1.getString(24)==null?"":rset1.getString(24);
				if(drcr_flg.equals("dr")){
					Vdue_dt.add(duedt);
				}else{
					Vdue_dt.add("-");
				}
				
				
				if(rset1.getString(7)!=null)
				{
					Vcont_type.add(rset1.getString(7));
				}
				else
				{
					Vcont_type.add("-");
				}
				
				if(rset1.getString(8)!=null)
				{
					Vfinancial_year.add(rset1.getString(8));
				}
				else
				{
					Vfinancial_year.add("-");
				}
				
				if(rset1.getString(9)!=null)
				{
					Vtax_str_cd.add(rset1.getString(9));
				}
				else
				{
					Vtax_str_cd.add("-");
				}
				
				if(rset1.getString(10)!=null)
				{
					Vtaxamtinr.add(rset1.getString(10));
				}
				else
				{
					Vtaxamtinr.add("-");
				}
				
				
				Vinvamtinr.add("-");
				
				
				//invamtinr=rset1.getString(12);
				sn_no=tmp_sn_no;
				taxvalue=rset1.getString(10);
				fyr=rset1.getString(8);
				String grs=rset1.getString(5)==null?"0":rset1.getString(5);
				if(grs.equals("")){
					grs="0";
				}
				total_amt_inr=Double.parseDouble(grs);
				tax_str_cd=rset1.getString(9)==null?"":rset1.getString(9);
				
				Vsn_no.add(sn_no);
				
				//String invseq=contract_type+":"+financial_year+":"+hlpl_inv_seq_no+":%";
				String invseq=rset1.getString(7)+":"+rset1.getString(8)+":"+rset1.getString(3)+":"+rset1.getString(4);
				if(!invseq.equalsIgnoreCase(""))
				{
					Vinvseqno.add(invseq);
				}
				else
				{
					Vinvseqno.add("-");
				}
				
				////////tax_adj_amt////////
				
				String q1="select flag,amount from fms7_inv_compo_dtl where price_cd IN ('6','10','11','12','13') and inv_seq_no='"+invseq+"'";
				////System.out.println("JAVA check adjustmn query q1: "+q1);
				rset2=stmt2.executeQuery(q1);
				if(rset2.next())
				{
					if(rset2.getString(1)!=null)
					{
						taxadvflag=rset2.getString(1);
					}
					else
					{
						taxadvflag="N";
					}
					
					Vtax_adj_flag.add(taxadvflag);
								
					if(rset2.getString(2)!=null)
					{
						tax_adj_amt=nf.format(Double.parseDouble(rset2.getString(2)));
					}
					else
					{
						tax_adj_amt="0";
					}
				}
				else
				{
					taxadvflag="N";
					tax_adj_amt="0";
					Vtax_adj_flag.add(taxadvflag);
				}
				
				////System.out.println("---JAVA here taxadvflag---: "+taxadvflag+"---tax_adj_amt---: "+tax_adj_amt);
				double balance_adjust_amt=0,tot_adjust_amt=0;
				//String mapping_id=""+rset1.getString(2)+"-"+tmp_fgsa_no+"-"+tmp_fgsa_rev_no+"-"+tmp_sn_no+"-"+tmp_sn_rev_no+"-"+rset1.getString(7);
				String mapping_id="%"+rset1.getString(2)+"-"+tmp_fgsa_no+"-%-"+tmp_sn_no+"-%-"+rset1.getString(7)+"%";
				queryString = "select price_rate,currency_cd,flag " +
						"FROM fms7_SALES_cont_price_dtl WHERE mapping_id like '"+mapping_id+"' AND " +
						"price_cd='1' AND FLAG='Y' and pay_type='AP' ";
			//	System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					VADJUST_FLAG_SN.add(rset2.getString(3)==null?"N":rset2.getString(3));
					tot_adjust_amt=rset2.getDouble(1);
					VADJUST_AMT_SN.add(rset2.getString(1)==null?"":rset2.getString(1));
					VADJUST_CUR_SN.add(rset2.getString(2));
					
					if(rset1.getString(7).equals("S")){
						queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
								+ "CONTRACT_NO='"+rset1.getString(11)+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND AGR_REV_NO='"+rset.getString(2)+"' "
								+ "AND CONTRACT_TYPE='"+rset1.getString(7)+"' ";
	//					System.out.println("Fetching flag of sn..."+queryString);
						
					}else{
						queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
								+ "CONTRACT_NO='"+rset1.getString(13)+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND CONTRACT_TYPE='"+rset1.getString(7)+"' ";
	//					System.out.println("Fetching flag of sn..."+queryString);
						
					}
					rset3=stmt3.executeQuery(queryString1);	
					if(rset3.next()){
						VCFORM_FLAG.add(rset3.getString(1)==null?"":rset3.getString(1));
					}else{
						VCFORM_FLAG.add("");
					}
				}else{
					VADJUST_FLAG_SN.add("");
					VADJUST_AMT_SN.add("");
					VADJUST_CUR_SN.add("");
					VCFORM_FLAG.add("");
				}
				queryString = "select sum(adjusted_amt) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
						+ "AND adjust_FLAG='Y' and commodity_type='RLNG' and contract_type='"+rset1.getString(7)+"'  ";
		//	System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					balance_adjust_amt=tot_adjust_amt-rset2.getDouble(1);
				}else{
					balance_adjust_amt=tot_adjust_amt;
				}
				queryString = "select sum(hold_amount) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
						+ "AND commodity_type='RLNG' and contract_type='"+rset1.getString(7)+"' and release_flag='Y' ";
			//System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					balance_adjust_amt+=rset2.getDouble(1);
				}
				VADJUST_balc_SN.add(nf6.format(balance_adjust_amt));
				/////HERE
				if(!payamt.equals("")){
					if(rset1.getString(17)!=null)
					{
						if(agr_base.equals("D")){
							if(rset1.getString(26).equals("DIFF-QTY")){
								double tdsper=Double.parseDouble(rset1.getString(17));
								double salesval=0;
								if(agr_base.equalsIgnoreCase("D")){
									salesval=Double.parseDouble(gross_amt_trans);
								}else{
									salesval=Double.parseDouble(rset1.getString(5));
								}
								double tdsval=(tdsper*salesval)/100;
								double invval=Double.parseDouble(rset1.getString(6));
								//double netrecv=invval-tdsval;
								double netrecv=0;
								if(rset1.getString(30)!=null)
								{
									netrecv=invval-tdsval-Double.parseDouble(rset1.getString(31));
								}else{
									netrecv=invval-tdsval;
								}
								double actval=Double.parseDouble(payamt);
								short_recv=netrecv-actval;
							}
						}else{
							double tdsper=Double.parseDouble(rset1.getString(17));
							double salesval=0;
							if(agr_base.equalsIgnoreCase("D")){
								salesval=Double.parseDouble(gross_amt_trans);
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//double netrecv=invval-tdsval;
							String netrecv="";
							if(rset1.getString(30)!=null)
							{
								netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(31)));
							}else{
								netrecv=nf6.format(invval-tdsval);
							}
							double actval=Double.parseDouble(payamt);
							short_recv=Double.parseDouble(netrecv)-actval;
						}
					}
					else
					{
						//System.out.println("short_recv_-in else--"+short_recv_);
						short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt);
					}
					Vpay_short_recv_amt.add(short_recv);
				}else{
					Vpay_short_recv_amt.add("-");
				}
				Vgross_trans_inr.add(gross_amt_trans);
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					if(rset1.getString(12)!=null)
					{
						Vpay_actual_recv_amt.add(rset1.getString(12));
					}
					else
					{
						Vpay_actual_recv_amt.add("-");
					}
					
					if(rset1.getString(13)!=null)
					{
						Vpay_recv_dt.add(rset1.getString(13));
					}
					else
					{
						Vpay_recv_dt.add("-");
					}
					
					if(rset1.getString(14)!=null)
					{
						Vpay_remark.add(rset1.getString(14));
					}
					else
					{
						Vpay_remark.add("-");
					}
					
					/*if(rset1.getString(17)!=null)
					{
						double tdsper=Double.parseDouble(rset1.getString(17));
						double salesval=Double.parseDouble(rset1.getString(5));
						double tdsval=(tdsper*salesval)/100;
						double invval=Double.parseDouble(rset1.getString(6));
						double netrecv=invval-tdsval;
						double actval=Double.parseDouble(rset1.getString(12));
						short_recv=netrecv-actval;
					}
					else
					{
						short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(rset1.getString(12));
					}
					
					Vpay_short_recv_amt.add(short_recv+"");*/
				
					Vpayflag.add("PAID");
					
					if(rset1.getString(15)!=null)
						Vinv_gen_by_cd.add(rset1.getString(15));
					else
						Vinv_gen_by_cd.add("00");
						
					if(!Chk_by.equals(""))
						Vchecked_by_cd.add(Chk_by);
					else
						Vchecked_by_cd.add("00");
					
					if(rset1.getString(16)!=null)
						Vapproved_by_cd.add(rset1.getString(16));
					else
						Vapproved_by_cd.add("00");
					
					if(!pdfinvdtl.equals(""))
					{
						Vpdf_inv_dtl.add(pdfinvdtl);
					}
					else
					{
						Vpdf_inv_dtl.add("-");
					}
					
					pdf_inv_dtl=pdfinvdtl;
					
					if(!print_ori.equals(""))
						print_by_ori=print_ori;
					else
						print_by_ori="00";
					
					if(!print_dupli.equals(""))
						print_by_dup=print_dupli;
					else
						print_by_dup="00";
					
					if(!print_trip.equals(""))
						print_by_tri=print_trip;
					else
						print_by_tri="00";
					
					////System.out.println("rset1.getString(24): "+rset1.getString(24));
					
					if(rset1.getString(17)!=null)
						Vtds_per.add(rset1.getString(17));
					else
						Vtds_per.add("-");
					
					if(pdf_inv_dtl.contains("T"))
					{
						Vprint_by_cd.add(print_by_tri);
					}
					else if(pdf_inv_dtl.contains("D"))
					{
						Vprint_by_cd.add(print_by_dup);
					}
					else if(pdf_inv_dtl.contains("O"))
					{
						Vprint_by_cd.add(print_by_ori);
					}
					else
					{
						Vprint_by_cd.add("");
					}
					
					if(rset1.getString(18)!=null)
						Vpay_update_dt.add(rset1.getString(18));
					else
						Vpay_update_dt.add("-");
					
					if(rset1.getString(19)!=null)
						Vpay_update_cnt.add(rset1.getString(19));
					else
						Vpay_update_cnt.add("-");
					
					/*if(taxadvflag.equalsIgnoreCase("Y"))
					{
						////System.out.println("HERE IN IF");
						if(rset1.getString(10)!=null)
						{
							double t=Double.parseDouble(rset1.getString(10))-Double.parseDouble(tax_adj_amt);
							////System.out.println("IN IF---t---: "+t);
							Vtottax.add(t);
						}	
						else
						{
							Vtottax.add("-");
						}
					}
					else
					{
						if(rset1.getString(10)!=null)
						{
							////System.out.println("IN ELSE---rset---: "+rset1.getString(27));
							Vtottax.add(rset1.getString(10));
						}	
						else
						{
							Vtottax.add("-");
						}
					}*/
					double taxtot=0;
					double gross_inr=Double.parseDouble(nf6.format(Double.parseDouble(grs)));
					queryString = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0.00'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) ORDER BY A.tax_code";
					////System.out.println("STEP-1A.3:FMS7_TAX_STRUCTURE_DTL: "+queryString);
					rset=stmt.executeQuery(queryString);
					while(rset.next())
					{
						//tax_factor+= Integer.parseInt((rset.getString(2)));
						if(rset.getString(3).equals("1"))
						{
							//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset.getString(2)))/100;
							tax_amt = (gross_inr*Double.parseDouble(rset.getString(2)))/100;
							
						}
						else if(rset.getString(3).equals("2"))
						{
							queryString1 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
									  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
									  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
									  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) AND A.tax_code='"+rset.getString(4)+"'";
						//////System.out.println("Query For Finding Out Tax Which Is Dependent On Other Tax Value = "+queryString1);
					 		rset2=stmt2.executeQuery(queryString1);
					 		if(rset2.next())
					 		{
					 			if(rset2.getString(3).equals("1"))
								{
									//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset1.getString(2)))/100;
					 				tax_amt = (gross_inr*Double.parseDouble(rset2.getString(2)))/100;
								}
							
				 			tax_amt = (tax_amt*Double.parseDouble(rset.getString(2)))/100;
					 		}
					 		else
					 		{
					 			tax_amt = 0;
					 		}
						}
						else
						{
							tax_amt = 0;
						}
					
					//customer_Invoice_Tax_Amt.add(nf.format(Math.round(tax_amt)));
					
					taxtot+=Math.round(tax_amt);
					//net_amt.add(net_amt_inr[i]);
				}
					Vtottax.add(taxtot);
				}
				else
				{
					Vpay_actual_recv_amt.add("-");
					Vpay_recv_dt.add("-");
					Vpay_remark.add("-");
					//Vpay_short_recv_amt.add("-");
					Vpayflag.add("UNPAID");
					
					if(rset1.getString(15)!=null)
						Vinv_gen_by_cd.add(rset1.getString(15));
					else
						Vinv_gen_by_cd.add("00");
					
					if(!Chk_by.equals(""))
						Vchecked_by_cd.add(Chk_by);
					else
						Vchecked_by_cd.add("00");
						
					if(rset1.getString(16)!=null)
						Vapproved_by_cd.add(rset1.getString(16));
					else
						Vapproved_by_cd.add("00");
						
					if(!pdfinvdtl.equals(""))
					{
						Vpdf_inv_dtl.add(pdfinvdtl);
					}
					else
					{
						Vpdf_inv_dtl.add("-");
					}
					
					pdf_inv_dtl=pdfinvdtl;
					
					if(!print_ori.equals(""))
						print_by_ori=print_ori;
					else
						print_by_ori="00";
					
					if(!print_dupli.equals(""))
						print_by_dup=print_dupli;
					else
						print_by_dup="00";
					
					if(!print_trip.equals(""))
						print_by_tri=print_trip;
					else
						print_by_tri="00";
						
					if(rset1.getString(17)!=null)
					{
						Vtds_per.add(rset1.getString(17));
					}
					else
					{
						Vtds_per.add("-");
					}
					//System.out.println("===pdf-inv_dtl=="+pdf_inv_dtl);
					
					if(pdf_inv_dtl.contains("T"))
					{
						Vprint_by_cd.add(print_by_tri);
					}
					else if(pdf_inv_dtl.contains("D"))
					{
						Vprint_by_cd.add(print_by_dup);
					}
					else if(pdf_inv_dtl.contains("O"))
					{
						Vprint_by_cd.add(print_by_ori);
					}
					else
					{
						Vprint_by_cd.add("");
					}
					
					if(rset1.getString(18)!=null)
						Vpay_update_dt.add(rset1.getString(18));
					else
						Vpay_update_dt.add("-");
					
					if(rset1.getString(19)!=null)
						Vpay_update_cnt.add(rset1.getString(19));
					else
						Vpay_update_cnt.add("-");
					
					/*if(taxadvflag.equalsIgnoreCase("Y"))
					{
//						if(rset1.getString(24)!=null)
//						{
//							double t=Double.parseDouble(rset1.getString(24))-Double.parseDouble(tax_adj_amt);
//							Vtottax.add(t);
//						}	
//						else
//						{
							Vtottax.add("-");
						//}
					}
					else
					{
						if(rset1.getString(24)!=null)
						{
							Vtottax.add(rset1.getString(24));
						}	
						else
						{
							Vtottax.add("-");
						//}
					}*/
					
					double taxtot=0;
					
					double gross_inr=Double.parseDouble(nf6.format(Double.parseDouble(grs)));
					queryString = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0.00'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) ORDER BY A.tax_code";
					//System.out.println("tax structure----: "+queryString);
					rset=stmt.executeQuery(queryString);
					while(rset.next())
					{
						//tax_factor+= Integer.parseInt((rset.getString(2)));
						if(rset.getString(3).equals("1"))
						{
							//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset.getString(2)))/100;
							tax_amt = (gross_inr*Double.parseDouble(rset.getString(2)))/100;
							
						}
						else if(rset.getString(3).equals("2"))
						{
							queryString1 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
									  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
									  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
									  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) AND A.tax_code='"+rset.getString(4)+"'";
						//////System.out.println("Query For Finding Out Tax Which Is Dependent On Other Tax Value = "+queryString1);
					 		rset2=stmt2.executeQuery(queryString1);
					 		if(rset2.next())
					 		{
					 			if(rset2.getString(3).equals("1"))
								{
									//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset1.getString(2)))/100;
					 				tax_amt = (gross_inr*Double.parseDouble(rset2.getString(2)))/100;
								}
							
				 			tax_amt = (tax_amt*Double.parseDouble(rset.getString(2)))/100;
					 		}
					 		else
					 		{
					 			tax_amt = 0;
					 		}
						}
						else
						{
							tax_amt = 0;
						}
					
					//customer_Invoice_Tax_Amt.add(nf.format(Math.round(tax_amt)));
					
					taxtot+=Math.round(tax_amt);
					//net_amt.add(net_amt_inr[i]);
				}
					Vtottax.add(taxtot);
				}
				
				//System.out.println("===JAVA===2--"+tax_amt);
				/////////Vhlpl_inv_seq//////////
				
				if(Integer.parseInt(rset1.getString(3))<10)
				{
					hlpl_inv_no_disp="000"+rset1.getString(3)+"/"+rset1.getString(8);
				}
				else if(Integer.parseInt(rset1.getString(3))<100) 
				{
					hlpl_inv_no_disp="00"+rset1.getString(3)+"/"+rset1.getString(8);
				}
				else if(Integer.parseInt(rset1.getString(3))<1000) 
				{
					hlpl_inv_no_disp="0"+rset1.getString(3)+"/"+rset1.getString(8);
				}
				else
				{
					hlpl_inv_no_disp=rset1.getString(3)+"/"+rset1.getString(8);
				}
				hlpl_inv_no_disp=rset1.getString(21)==null?"":rset1.getString(21);
				if(!hlpl_inv_no_disp.equalsIgnoreCase(""))
				{
					Vhlpl_inv_seq.add(rset1.getString(21)==null?"":rset1.getString(21));
				}
				else
				{
					Vhlpl_inv_seq.add("-");
				}
				
				////System.out.println("===JAVA===3");
				
				///////////XML_GEN_FLAG/////////
				
				String queryString2 = "SELECT XML_GEN_FLAG,to_char(APPROVAL_DT,'dd/mm/yyyy') FROM FMS7_ACCOUNT_APPROVED_DTL " +
						  "WHERE INV_CARGO_NO='"+hlpl_inv_no_disp+"' AND JOURNAL_TYPE='FMSSL' " +
						  "AND CONTRACT_TYPE='"+contract_type+"' ";
					//	//System.out.println("Confirmed FMS7_PROV_SELLER_PAY Details Query = "+queryString);
					rset = stmt.executeQuery(queryString2);
					if(rset.next())
					{
						xml_gen_flag=rset.getString(1)==null?"N":rset.getString(1);
						approval_dt=rset.getString(2)==null?"":rset.getString(2);
						Vxml_gen_flag.add(xml_gen_flag);
					}
					else
					{
						xml_gen_flag="N";
						approval_dt="";
						Vxml_gen_flag.add(xml_gen_flag);
					}
						
					////System.out.println("===JAVA===4");
					
					///tax_adj
					
					////System.out.println("===JAVA===5");
					
					///////////inv_adj_amt///////////
					
					String q2="select flag,amount from fms7_inv_compo_dtl where price_cd='1' and inv_seq_no='"+invseq+"'";
					////System.out.println("JAVA check adjustmn query q2: "+q2);
					rset3=stmt3.executeQuery(q2);
					if(rset3.next())
					{
						if(rset3.getString(1)!=null)
						{
							advflag=rset3.getString(1);
						}
						else
						{
							advflag="N";
						}
						
						Vinv_adj_flag.add(advflag);
						
						if(rset3.getString(2)!=null)
						{
							inv_adj_amt=nf.format(Double.parseDouble(rset3.getString(2)));
						}
						else
						{
							inv_adj_amt="0";
						}
					}
					else
					{
						advflag="N";
						inv_adj_amt="0";
						Vinv_adj_flag.add(advflag);
					}
					////System.out.println("---JAVA here advflag: "+advflag);
								
					////System.out.println("===JAVA===6");
					
					////////////inv_amt///////////
					
					String inv_amt="";
					if(advflag.equalsIgnoreCase("Y") && taxadvflag.equalsIgnoreCase("Y"))
					{
						//query2="select INV_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
						inv_amt=invamtinr;
					}
					else
					{
						//query2="select GROSS_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
						inv_amt=gross_amt_inr;
					}
					////System.out.println("---JAVA inv_amt---: "+inv_amt);
							
					////System.out.println("===JAVA===7");
					
					String amt = "0";
					
					String map_id=contract_type+":"+fyr+":"+hlpl_inv_seq_no+":%";
					Vmapid.add(map_id);
					
					if(contract_type.equalsIgnoreCase("T") || contract_type.equalsIgnoreCase("C"))		//ADDED FOR LTCORA AND CN
					{
						// String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no like" +
						//	" '"+map_id+"'and price_cd='6' and (flag_temp not like 'T' or flag_temp is null)";
						
						String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no='"+invseq+"' and price_cd IN ('6','10','11','12','13') "
								+ "and (flag_temp not like 'T' or flag_temp is null)";
						
						rset4=stmt4.executeQuery(queryTax);
						////System.out.println("---JAVA queryTax---: "+queryTax);
						if(rset4.next())
						{
							amt = ""+java.text.NumberFormat.getInstance().parse(rset4.getString(1));
							Adj_Tax_amt=amt;	
							taxvalue = ""+(Double.parseDouble(taxvalue) - Double.parseDouble(amt));
						}
					}
					
					////System.out.println("===JAVA===8");
					double gross_inr=Double.parseDouble(nf6.format(Double.parseDouble(grs)));
					String queryString12 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
				 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
					
					//System.out.println("===VTAXAMT QUERY===: "+queryString12);
					rset5=stmt5.executeQuery(queryString12);
					////System.out.println("===JAVA===9");
					int cnt1=0;
					String tx_cd="",tx_amt="",tx_nm="";
					while(rset5.next())
					{
						tax_cd=rset5.getString(1);
						tax_on=rset5.getString(3);
						////System.out.println("---tax_on---"+tax_on);
						if(rset5.getString(3).equals("1")) 
						{
							cnt1++;
							////System.out.println("===IN IF=== "+cnt1);
							tax_amt = (gross_inr*Double.parseDouble(rset5.getString(2)))/100;
							////System.out.println(Double.parseDouble(gross_amt_inr)+"---total_amt_inr-IN IF--"+tax_amt);
							////System.out.println("---rset5.getString(2)-IN IF--"+rset5.getString(2));
						}
						else if(rset5.getString(3).equals("2"))
						{
							cnt1++;
							////System.out.println("===IN ELSE IF=== "+cnt1);
							String q3 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
										   "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
										   "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
										   "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) AND A.tax_code='"+rset5.getString(4)+"'";
							
							////System.out.println("===TAXAMT Query q3 === "+q3);
							rset6=stmt6.executeQuery(q3);
				 	 		
							if(rset6.next())
				 	 		{
				 	 			if(rset6.getString(3).equals("1"))
				 				{
				 					tax_amt = (gross_inr*Double.parseDouble(rset6.getString(2)))/100;
				 				}	
				 	 			////System.out.println("---total_amt_inr-IN ELSE IF--"+total_amt_inr);
								////System.out.println("---rset5.getString(2)-IN ELSE IF--"+rset5.getString(2));
				 	 			tax_amt = (tax_amt*Double.parseDouble(rset5.getString(2)))/100;
				 	 		}
				 	 		else
				 	 		{
				 	 			tax_amt=0;
				 	 		}
						}
						else
						{
							tax_amt = 0;
						}
						////System.out.println("===JAVA===9");
						double tax_diff=0;
						double tax_fact=Double.parseDouble(rset5.getString(2));
						if(tax_fact<tax_cform){
							tax_diff=tax_cform-tax_fact;
							tax_diff_val.add(tax_diff);
						}else{
							tax_diff_val.add("");
						}
						
						////////////////////////////BK:SB20151203//////////////////
			 			
						if(!Adj_Tax_amt.equalsIgnoreCase("") && taxadvflag.equalsIgnoreCase("Y"))
			 			{
							if(Double.parseDouble(Adj_Tax_amt)>0)
				 			{
								////System.out.println("---Adj Amt---: "+Adj_Tax_amt);
				 				tax_amt = tax_amt - Double.parseDouble(Adj_Tax_amt);
				 				Adj_Tax_amt = "0";
				 			}
							else
				 			{
								if(tax_on.equalsIgnoreCase("2"))
								{
									//taxamt=nf.format(tax_amt)+"";
									//Tot_Tax_amt = Tot_Tax_amt + Double.parseDouble(taxamt);
								}
				 			}
			 			}
						
						////System.out.println("===IN WHILE================= "+cnt1);
						
						///////////////////////////////////////////////////////
						queryString6="select sht_nm from fms7_tax_mst where tax_code='"+tax_cd+"'";
						rset6=stmt6.executeQuery(queryString6);
						while(rset6.next())
						{
							tax_nm=rset6.getString(1);
						}
						
						tx_cd+="@"+tax_cd;
						tx_amt+="@"+nf.format(tax_amt);
						tx_nm+="@"+tax_nm;
						
						////System.out.println("===JAVA tax_amt after=== "+tax_amt);
					}
//					System.out.println("===VTAXAMT tx_amt===: "+tx_amt);
					Vtax_code.add(tx_cd.replaceFirst("@", ""));  	
					Vtax_amt.add(tx_amt.replaceFirst("@", ""));
					Vtax_nm.add(tx_nm.replaceFirst("@", ""));
					////System.out.println("Vtax_code-"+Vtax_code);
					////System.out.println("Vtax_amt-"+Vtax_amt);
					
					String q123 = "SELECT COUNT(*) FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
				 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
					rset=stmt.executeQuery(q123);
					while(rset.next())
					{
						//cnt1=rset.getInt(1);
						int count=rset.getInt(1);
						Vtaxcnt.add(count);
					}
					
					//////////////////////////////////////////////////////////
					///fOR GETTING REFERENCE NO FOR CONTRACT_NO AND AGREEMENT_NO///
					String mapp_id="";
					if(rset1.getString(7).equals("C") || rset1.getString(7).equals("M") || rset1.getString(7).equals("B")){
						if(invstatus.equalsIgnoreCase("PAID"))
						{
							mapp_id=mappid;
						}else{
							mapp_id=mappid;
						}
						//System.out.println("--mapp_id--"+mapp_id);
						String temp_mapp_id[]=mapp_id.split("-");
						VAgreement_no.add(temp_mapp_id[1]+"");
						VAgreement_rev_no.add(temp_mapp_id[2]+"");
						Vcont_no.add(temp_mapp_id[3]+"");
						Vcont_rev_no.add(temp_mapp_id[4]+"");
						if(""+temp_mapp_id[3]!=""){
							int temp_cont_no=Integer.parseInt(temp_mapp_id[3]+"");
							if(temp_cont_no>999){
								Vcont_typ.add("LTCORA (Period)");
							}else{
								Vcont_typ.add("LTCORA (CN)");
							}
						}else{
							Vcont_typ.add("LTCORA (CN)");
						}
						
					}else if(rset1.getString(7).equals("S")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("SN");
					}else if(rset1.getString(7).equals("L")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("LOA");
					}
					else if(rset1.getString(7).equals("I")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("Interest");
					}
					else if(rset1.getString(7).equals("R")){
						Vcont_typ.add("Regas");
					}
				}
			}
			//
			//FOR GETTING manual DEBIT NOTE FROM SYSTEM
			if(!customer_cd.equalsIgnoreCase(""))
			{
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
								 "DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
								 + ",TDS_PERCENT,"
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,FLAG,to_char(due_dt,'dd/mm/yyyy'),"
								 + "dr_cr_flag,DR_CR_NO,CRITERIA,SUP_STATE_CODE ,tds_tax_percent,tds_tax_amt,FGSA_NO,FGSA_REV_NO,"
								 + "SN_NO,SN_REV_NO,TOTAL_QTY " +
								 "FROM FMS7_manual_DR_CR_NOTE WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND DR_CR_DT "
						 		+ "BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APrv_by is not null AND "
								 + " PAY_RECV_AMT IS NOT NULL AND "
								 + "PAY_INSERT_BY IS NOT NULL and dr_cr_flag='dr' "
								 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
				else
				{
					queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
								 "DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
								 + ",TDS_PERCENT,"
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,FLAG,to_char(due_dt,'dd/mm/yyyy'),"
								 + "dr_cr_flag,DR_CR_NO,CRITERIA,SUP_STATE_CODE,tds_tax_percent,tds_tax_amt,FGSA_NO,FGSA_REV_NO,SN_NO"
								 + ",SN_REV_NO,TOTAL_QTY " +
								 "FROM FMS7_manual_DR_CR_NOTE WHERE "+cnt_typ+" AND CUSTOMER_CD='"+customer_cd+"' AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') and  APrv_by is not null AND "
								 + " PAY_RECV_AMT IS NULL AND "
								 + "PAY_INSERT_BY IS NULL and dr_cr_flag='dr' "
								 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
			}
			else
			{
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
								 "DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
								 + ",TDS_PERCENT,"
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,FLAG,to_char(due_dt,'dd/mm/yyyy'),"
								 + "dr_cr_flag,DR_CR_NO,CRITERIA,SUP_STATE_CODE,tds_tax_percent,tds_tax_amt,FGSA_NO,FGSA_REV_NO"
								 + ",SN_NO,SN_REV_NO,TOTAL_QTY " +
								 "FROM FMS7_manual_DR_CR_NOTE WHERE "+cnt_typ+" AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APrv_by is not null AND "
								 + " PAY_RECV_AMT IS NOT NULL AND "
								 + "PAY_INSERT_BY IS NOT NULL and dr_cr_flag='dr' "
								 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
				else
				{
					queryString123="select TO_CHAR(DR_CR_DT,'MON'),CUSTOMER_CD,HLPL_INV_SEQ_NO,TO_CHAR(DR_CR_DT,'DD/MM/YYYY')," +
								 "DR_CR_GROSS_AMT_INR,DR_CR_NET_AMT_INR,CONTRACT_TYPE,"
								 + "FINANCIAL_YEAR,TAX_STRUCT_CD,TAX_AMT_INR,SN_NO,PAY_RECV_AMT,"
								 + "TO_CHAR(PAY_RECV_DT,'DD/MM/YYYY'),PAY_REMARK,EMP_CD,APrv_by"
								 + ",TDS_PERCENT,"
								 + "TO_CHAR(PAY_UPDATE_DT,'DD/MM/YYYY'),PAY_UPDATE_CNT,TAX_AMT_INR,dr_cr_doc_no,FLAG,"
								 + "to_char(due_dt,'dd/mm/yyyy'),dr_cr_flag,DR_CR_NO,CRITERIA,SUP_STATE_CODE,tds_tax_percent,"
								 + "tds_tax_amt,FGSA_NO,FGSA_REV_NO,SN_NO,SN_REV_NO,TOTAL_QTY " +
								 "FROM FMS7_manual_DR_CR_NOTE WHERE "+cnt_typ+" AND DR_CR_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY')  AND APrv_by is not null AND "
								 + " PAY_RECV_AMT IS NULL AND PAY_INSERT_BY IS NULL and dr_cr_flag='dr' "
								 + "ORDER BY TO_CHAR(DR_CR_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
				}
			}
			//System.out.println("===fetch Invoice queryString123===: "+queryString123);
			rset1=stmt1.executeQuery(queryString123);
			while(rset1.next())
			{
				Vdrcrcriteria.add(rset1.getString(26)==null?"":rset1.getString(26));
				Vdrcrflag.add(rset1.getString(24)==null?"":rset1.getString(24));
				String payamt="",inv_take_flag="Y",sup_state_cd="";String short_recv_="0";
				if(invstatus.equalsIgnoreCase("PAID")){
					Vtds_tax_percent.add(rset1.getString(28)==null?"":rset1.getString(28));
					Vtds_tax_amt.add(rset1.getString(29)==null?"":rset1.getString(29));
				}else{
					Vtds_tax_percent.add(rset1.getString(28)==null?"":rset1.getString(28));
					Vtds_tax_amt.add(rset1.getString(29)==null?"":rset1.getString(29));
				}
				String queryString_agr_base="",transp_charges="0";
				if(invstatus.equalsIgnoreCase("PAID")){
					if(rset1.getString(7).equals("S")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(30)+"' AND FGSA_REV_NO='"+rset1.getString(31)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND SN_NO='"+rset1.getString(32)+"' AND SN_REV_NO='"+rset1.getString(33)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(30)+"' AND REV_NO='"+rset1.getString(31)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(30)+"' AND REV_NO='"+rset1.getString(31)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}else if(rset1.getString(7).equals("L")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(30)+"' AND LOA_REV_NO='"+rset1.getString(33)+"' AND LOA_NO='"+rset1.getString(32)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(30)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(30)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}
					if(agr_base.equals("D")){
						String queryString_tr="";
						if(rset1.getString(7).equals("S")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(30)+"' AND FGSA_REV_NO='"+rset1.getString(31)+"' AND SN_NO='"+rset1.getString(32)+"' AND SN_REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
						}else if(rset1.getString(7).equals("L")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(30)+"' AND LOA_NO='"+rset1.getString(32)+"' AND LOA_REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							
						}
						rset2=stmt2.executeQuery(queryString_tr);
					
					//System.out.println("queryString--"+queryString_tr);
						if(rset2.next()){
							transp_charges=rset2.getString(1)==null?"0":rset2.getString(1);
						}
					}
				}else{
					if(rset1.getString(7).equals("S")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(30)+"' AND FGSA_REV_NO='"+rset1.getString(31)+"' AND SN_NO='"+rset1.getString(32)+"' AND SN_REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(30)+"' AND REV_NO='"+rset1.getString(31)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset1.getString(30)+"' AND REV_NO='"+rset1.getString(31)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}else if(rset1.getString(7).equals("L")){
						queryString_agr_base="SELECT CONT_BASE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(30)+"' AND LOA_NO='"+rset1.getString(32)+"' AND LOA_REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							if(agr_base.equals("")){
								queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(30)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
								rset3=stmt3.executeQuery(queryString_agr_base);
								if(rset3.next()){
									agr_base=rset3.getString(1)==null?"":rset3.getString(1);
								}
							}
						}else{
							queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset1.getString(30)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}
					}
					if(agr_base.equals("D")){
						String queryString_tr="";
						if(rset1.getString(8).equals("S")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+rset1.getString(30)+"' AND FGSA_REV_NO='"+rset1.getString(31)+"' AND SN_NO='"+rset1.getString(32)+"' AND SN_REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							rset2=stmt2.executeQuery(queryString_tr);
						}else if(rset1.getString(8).equals("L")){
							queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+rset1.getString(30)+"' AND LOA_NO='"+rset1.getString(32)+"' AND LOA_REV_NO='"+rset1.getString(33)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
							rset2=stmt2.executeQuery(queryString_tr);
						}
						//System.out.println("queryString--"+queryString_tr);
						if(rset2.next()){
							transp_charges=rset2.getString(1)==null?"0":rset2.getString(1);
						}
					}
				}
				
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					payamt=rset1.getString(12)==null?"":rset1.getString(12);
					inv_take_flag="Y";
					//Vsup_state_cd.add(rset1.getString(27)==null?"":rset1.getString(27));
					//sup_state_cd = rset1.getString(27)==null?"":rset1.getString(27);
				}else{
					payamt=rset1.getString(12)==null?"":rset1.getString(12);
					//Vsup_state_cd.add(rset1.getString(27)==null?"":rset1.getString(27));
					//sup_state_cd = rset1.getString(27)==null?"":rset1.getString(27);
				//	System.out.println("payamt---"+payamt);
					if(payamt.equals("")){
						inv_take_flag="Y";
					}else{
						if(rset1.getString(17)!=null)
						{
							double tdsper=Double.parseDouble(rset1.getString(17));
							//double salesval=Double.parseDouble(rset1.getString(5));
							double salesval=0;
							if(agr_base.equals("D") && Double.parseDouble(transp_charges)>0){
								double temp_grossamt=(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))+Double.parseDouble(rset1.getString(34)) * Double.parseDouble(transp_charges));
								salesval=temp_grossamt;
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//double netrecv=invval-tdsval;
							String netrecv="";
							if(rset1.getString(28)!=null)
							{
								netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(29)));
							}else{
								netrecv=nf6.format(invval-tdsval);
							}
							double actval=Double.parseDouble(rset1.getString(12));
							short_recv_=nf6.format(Double.parseDouble(netrecv)-actval);
						}
						else
						{
							//System.out.println("short_recv_-in else--"+short_recv_);
							short_recv_=nf6.format(Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt));
						}
					//	System.out.println("short_recv_---"+short_recv_);
						if(Double.parseDouble(short_recv_) <= Double.parseDouble(Inv_roundoff+"")){
							inv_take_flag="N";
						}
						else{
							inv_take_flag="Y";
						}
					}
				}
				if(inv_take_flag.equals("Y")){
				cnt++;
				String temp_flag = "";
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					VPAY_NEW_INV_SEQ_NO.add(rset1.getString(21)==null?"":rset1.getString(21));
					VPAY_FLAG.add(rset1.getString(22)==null?"Y":rset1.getString(22));
					temp_flag = rset1.getString(22)==null?"Y":rset1.getString(22);
					Vsup_state_cd.add(rset1.getString(27)==null?"":rset1.getString(27));
					sup_state_cd = rset1.getString(27)==null?"":rset1.getString(27);
				} else {
					VPAY_NEW_INV_SEQ_NO.add(rset1.getString(21)==null?"":rset1.getString(21));
					VPAY_FLAG.add(rset1.getString(22)==null?"Y":rset1.getString(22));
					temp_flag = rset1.getString(22)==null?"Y":rset1.getString(22);
					Vsup_state_cd.add(rset1.getString(27)==null?"":rset1.getString(27));
					sup_state_cd = rset1.getString(27)==null?"":rset1.getString(27);
				}
				flag_inv.add("MD");
				//for tcs
				String query="select tcs_amt from fms7_invoice_tcs_dtl where hlpl_inv_seq_no='"+rset1.getString(25)+"' and contract_type='"+rset1.getString(7)+"' and "
						+ "financial_year='"+rset1.getString(8)+"' and customer_cd='"+rset1.getString(2)+"' and invoice_type ='DEBIT' AND FLAG='Y' and COMMODITY_TYPE='RLNG'"
						+ " and sup_state_code='"+sup_state_cd+"' ";		
				rset=stmt.executeQuery(query);
				if(rset.next()){
					Vtcs_amt.add(rset.getString(1)==null?"":rset.getString(1));
				}else{
					Vtcs_amt.add("");
				}
				//Hiren_20210621 for TDS calc.
				String tds_app_flag = "N";
				tds_app_amt = "0";
				if(rset1.getString(7).equals("S") || rset1.getString(7).equals("L")) {
					tds_app_flag = checkforTDSApplicable(rset1.getString(4),rset1.getString(2),rset1.getString(8),rset1.getString(3),"RLNG","DEBIT",sup_state_cd);
					
				}
				if(segment.equalsIgnoreCase("LTCORA_CN")) {
					Vtds_app_flag.add("Y");
					Vtds_app_amt.add(rset1.getString(5)==null?"0":rset1.getString(5));
				}else {
					Vtds_app_flag.add(tds_app_flag);
					Vtds_app_amt.add(tds_app_amt);
				}
				////////
				////System.out.println("===JAVA===1");
				if(rset1.getString(1)!=null)
				{
					Vmonth.add(rset1.getString(1));
				}
				else
				{
					Vmonth.add("-");
				}
				
				if(rset1.getString(2)!=null)
				{
					Vcustomer_cd.add(rset1.getString(2));
				}
				else
				{
					Vcustomer_cd.add("-");
				}
				
				String queryString1 = "SELECT CUSTOMER_NAME,CUSTOMER_ABBR FROM FMS7_CUSTOMER_MST A WHERE CUSTOMER_CD='"+rset1.getString(2)+"'"
						+ "  AND " +
					  "A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_MST B " +
					  "WHERE A.customer_cd=B.customer_cd AND " +
					  "B.eff_dt<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY') and CUSTOMER_CD='"+rset1.getString(2)+"')";
				rset=stmt.executeQuery(queryString1);
				while(rset.next())
				{
					if(rset.getString(2)!=null)
					{
						Vcustomer_abbr.add(rset.getString(2));
						Vcustomer_name.add(rset.getString(1));
					}
					else
					{
						Vcustomer_abbr.add("-");
						Vcustomer_name.add("-");
					}
				}
				
				
				hlpl_inv_seq_no=rset1.getString(3);
				if(rset1.getString(3)!=null)
				{
					Vhlplinvseqno.add(rset1.getString(3));
				}
				else
				{
					Vhlplinvseqno.add("-");
				}
				
				Vdiff_tcs.add("0");
				Vdiff_tcs_flg.add("N");
				
				String tmp_fgsa_no="",tmp_fgsa_rev_no="",tmp_sn_no="",tmp_sn_rev_no="",TMP_QTY="";
				String Chk_by="",pdfinvdtl="",print_ori="",print_dupli="",print_trip="",mappid="";
				String queryString456="SELECT FGSA_NO,FGSA_REV_NO,SN_REV_NO,PLANT_SEQ_NO,TO_CHAR(PERIOD_START_DT,'DD/MM/YYYY'),"
						+ "TO_CHAR(PERIOD_END_DT,'DD/MM/YYYY'),EXCHG_RATE_CD,EXCHG_RATE_TYPE,CUST_INV_SEQ_NO,APPROVED_FLAG,TOTAL_QTY,checked_by,"
						+ "PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,mapping_id,sn_no " + 
						 "FROM FMS7_INVOICE_MST WHERE HLPL_INV_SEQ_NO='"+hlpl_inv_seq_no+"' "
						 + "AND FINANCIAL_YEAR='"+rset1.getString(8)+"' AND "
						 + "CONTRACT_TYPE='"+rset1.getString(7)+"' "
						 + " and sup_state_code='"+sup_state_cd+"' ";
				rset=stmt.executeQuery(queryString456);
				//System.out.println("---queryString456---"+queryString456);
				if(rset.next())
				{
					if(rset.getString(1)!=null)
						Vfgsa_no.add(rset.getString(1));
					else
						Vfgsa_no.add("-");
					
					if(rset.getString(2)!=null)
						Vfgsa_rev_no.add(rset.getString(2));
					else
						Vfgsa_rev_no.add("-");
						
					if(rset.getString(3)!=null)
						Vsn_rev_no.add(rset.getString(3));
					else
						Vsn_rev_no.add("-");
					
					if(rset.getString(4)!=null)
						Vplant_seq_no.add(rset.getString(4));
					else
						Vplant_seq_no.add("-");
					
					tmp_fgsa_no=rset.getString(1)==null?"":rset.getString(1);
					tmp_fgsa_rev_no=rset.getString(2)==null?"":rset.getString(2);
					tmp_sn_no=rset.getString(18)==null?"":rset.getString(18);
					tmp_sn_rev_no=rset.getString(3)==null?"":rset.getString(3);
					TMP_QTY=rset.getString(11)==null?"":rset.getString(11);
					Chk_by= rset.getString(12)==null?"":rset.getString(12);
					pdfinvdtl=rset.getString(13)==null?"":rset.getString(13);
					print_ori=rset.getString(14)==null?"":rset.getString(14);
					print_dupli=rset.getString(15)==null?"":rset.getString(15);
					print_trip=rset.getString(16)==null?"":rset.getString(16);
					mappid=rset.getString(17)==null?"":rset.getString(17);
					
					String q1="SELECT NVL(A.plant_name,' ') FROM FMS7_CUSTOMER_PLANT_DTL A " +
							   "WHERE A.customer_cd="+rset1.getString(2)+" AND A.seq_no="+rset.getString(4)+" " +
							   "AND A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_PLANT_DTL B " +
							   "WHERE A.customer_cd=B.customer_cd AND A.seq_no=B.seq_no " +
							   "AND B.eff_dt<=TO_DATE('"+rset.getString(5)+"','DD/MM/YYYY'))";
					
					//String q1="SELECT PLANT_NAME FROM FMS7_CUSTOMER_PLANT_DTL  WHERE CUSTOMER_CD='"+rset1.getString(2)+"' AND SEQ_NO='"+rset.getString(4)+"'";
					////System.out.println("---PLANT_NAME QUERY---"+q1);
					rset6=stmt6.executeQuery(q1);
					
					while(rset6.next())
					{
						if(rset6.getString(1)!=null)
							Vplant_seq_nm.add(rset6.getString(1));
						else
							Vplant_seq_nm.add("-");
						
						////System.out.println("---Vplant_seq_nm---:"+rset6.getString(1));
					}
					
					if(rset.getString(5)!=null)
						Vperiod_st_dt.add(rset.getString(5));
					else
						Vperiod_st_dt.add("-");
						
					if(rset.getString(6)!=null)
						Vperiod_end_dt.add(rset.getString(6));
					else
						Vperiod_end_dt.add("-");
					
					if(rset.getString(7)!=null)
						Vexchg_rate_cd.add(rset.getString(7));
					else
						Vexchg_rate_cd.add("-");
					
					if(rset.getString(8)!=null)
						Vexchg_rate_type.add(rset.getString(8));
					else
						Vexchg_rate_type.add("-");
					
					if(rset.getString(9)!=null)
						Vcust_inv_seq_no.add(rset.getString(9));
					else
						Vcust_inv_seq_no.add("-");
					
					if(rset.getString(10)!=null)
						Vapproved_flag.add(rset.getString(10));
					else
						Vapproved_flag.add("-");
					
					/*String queryString_agr_base="";
					if(rset1.getString(7).equals("S")){
						queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset.getString(1)+"' AND REV_NO='"+rset.getString(2)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
						}
					}else if(rset1.getString(7).equals("L")){
						queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset.getString(1)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
						rset2=stmt2.executeQuery(queryString_agr_base);
						if(rset2.next()){
							agr_base=rset2.getString(1)==null?"":rset2.getString(1);
						}
					}*/
				}else{
					queryString456="SELECT FGSA_NO,FGSA_REV_NO,SN_REV_NO,PLANT_SEQ_NO,TO_CHAR(PERIOD_START_DT,'DD/MM/YYYY'),"
							+ "TO_CHAR(PERIOD_END_DT,'DD/MM/YYYY'),EXCHG_RATE_CD,EXCHG_RATE_TYPE,CUST_INV_SEQ_NO,APPROVED_FLAG,TOTAL_QTY,checked_by,"
							+ "PDF_INV_DTL,PRINT_BY_ORI,PRINT_BY_DUP,PRINT_BY_TRI,mapping_id,sn_no " + 
							 "FROM FMS7_manual_INVOICE_MST WHERE HLPL_INV_SEQ_NO='"+hlpl_inv_seq_no+"' "
							 + "AND FINANCIAL_YEAR='"+rset1.getString(8)+"' AND "
							 + "CONTRACT_TYPE='"+rset1.getString(7)+"'"
							 + " and sup_state_code='"+sup_state_cd+"' ";
					rset=stmt.executeQuery(queryString456);
					//System.out.println("---queryString456---"+queryString456);
					if(rset.next()){

						if(rset.getString(1)!=null)
							Vfgsa_no.add(rset.getString(1));
						else
							Vfgsa_no.add("-");
						
						if(rset.getString(2)!=null)
							Vfgsa_rev_no.add(rset.getString(2));
						else
							Vfgsa_rev_no.add("-");
							
						if(rset.getString(3)!=null)
							Vsn_rev_no.add(rset.getString(3));
						else
							Vsn_rev_no.add("-");
						
						if(rset.getString(4)!=null)
							Vplant_seq_no.add(rset.getString(4));
						else
							Vplant_seq_no.add("-");
						
						tmp_fgsa_no=rset.getString(1)==null?"":rset.getString(1);
						tmp_fgsa_rev_no=rset.getString(2)==null?"":rset.getString(2);
						tmp_sn_no=rset.getString(18)==null?"":rset.getString(18);
						tmp_sn_rev_no=rset.getString(3)==null?"":rset.getString(3);
						TMP_QTY=rset.getString(11)==null?"":rset.getString(11);
						Chk_by= rset.getString(12)==null?"":rset.getString(12);
						pdfinvdtl=rset.getString(13)==null?"":rset.getString(13);
						print_ori=rset.getString(14)==null?"":rset.getString(14);
						print_dupli=rset.getString(15)==null?"":rset.getString(15);
						print_trip=rset.getString(16)==null?"":rset.getString(16);
						mappid=rset.getString(17)==null?"":rset.getString(17);
						
						String q1="SELECT NVL(A.plant_name,' ') FROM FMS7_CUSTOMER_PLANT_DTL A " +
								   "WHERE A.customer_cd="+rset1.getString(2)+" AND A.seq_no="+rset.getString(4)+" " +
								   "AND A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_PLANT_DTL B " +
								   "WHERE A.customer_cd=B.customer_cd AND A.seq_no=B.seq_no " +
								   "AND B.eff_dt<=TO_DATE('"+rset.getString(5)+"','DD/MM/YYYY'))";
						
						//String q1="SELECT PLANT_NAME FROM FMS7_CUSTOMER_PLANT_DTL  WHERE CUSTOMER_CD='"+rset1.getString(2)+"' AND SEQ_NO='"+rset.getString(4)+"'";
						////System.out.println("---PLANT_NAME QUERY---"+q1);
						rset6=stmt6.executeQuery(q1);
						
						while(rset6.next())
						{
							if(rset6.getString(1)!=null)
								Vplant_seq_nm.add(rset6.getString(1));
							else
								Vplant_seq_nm.add("-");
							
							////System.out.println("---Vplant_seq_nm---:"+rset6.getString(1));
						}
						
						if(rset.getString(5)!=null)
							Vperiod_st_dt.add(rset.getString(5));
						else
							Vperiod_st_dt.add("-");
							
						if(rset.getString(6)!=null)
							Vperiod_end_dt.add(rset.getString(6));
						else
							Vperiod_end_dt.add("-");
						
						if(rset.getString(7)!=null)
							Vexchg_rate_cd.add(rset.getString(7));
						else
							Vexchg_rate_cd.add("-");
						
						if(rset.getString(8)!=null)
							Vexchg_rate_type.add(rset.getString(8));
						else
							Vexchg_rate_type.add("-");
						
						if(rset.getString(9)!=null)
							Vcust_inv_seq_no.add(rset.getString(9));
						else
							Vcust_inv_seq_no.add("-");
						
						if(rset.getString(10)!=null)
							Vapproved_flag.add(rset.getString(10));
						else
							Vapproved_flag.add("-");
						
						/*String queryString_agr_base="";
						if(rset1.getString(7).equals("S")){
							queryString_agr_base="SELECT FGSA_BASE FROM FMS7_FGSA_MST WHERE FGSA_NO='"+rset.getString(1)+"' AND REV_NO='"+rset.getString(2)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}else if(rset1.getString(7).equals("L")){
							queryString_agr_base="SELECT TENDER_BASE FROM FMS7_TENDER_MST WHERE TENDER_NO='"+rset.getString(1)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"'";
							rset2=stmt2.executeQuery(queryString_agr_base);
							if(rset2.next()){
								agr_base=rset2.getString(1)==null?"":rset2.getString(1);
							}
						}*/
					}else{
						Vfgsa_no.add("-");
						Vfgsa_rev_no.add("-");
						Vsn_rev_no.add("-");
						Vplant_seq_no.add("-");
						Vplant_seq_nm.add("-");
						Vperiod_st_dt.add("-");
						Vperiod_end_dt.add("-");
						Vexchg_rate_cd.add("-");
						Vexchg_rate_type.add("-");
						Vcust_inv_seq_no.add("-");
						Vapproved_flag.add("-");
					}
				}
				VAgreement_base.add(agr_base);
				if(rset1.getString(4)!=null)
				{
					Vinv_dt.add(rset1.getString(4));
				}
				else
				{
					Vinv_dt.add("-");
				}
				
				if(agr_base.equalsIgnoreCase("D")){

					String queryString_tr="";
					//String transp_charges="";
					String temp_grossamt="";
					/*if(rset1.getString(7).equals("S")){
						queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_SN_MST WHERE FGSA_NO='"+tmp_fgsa_no+"' AND FGSA_REV_NO='"+tmp_fgsa_rev_no+"' AND SN_NO='"+tmp_sn_no+"' AND SN_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
					}else if(rset1.getString(7).equals("L")){
						queryString_tr="SELECT TRANSPORTATION_CHARGE FROM FMS7_LOA_MST WHERE TENDER_NO='"+tmp_fgsa_no+"' AND LOA_NO='"+tmp_sn_no+"' AND LOA_REV_NO='"+tmp_sn_rev_no+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' ";
						
					}
					rset2=stmt2.executeQuery(queryString_tr);
					//System.out.println("queryString--"+queryString_tr);
					if(rset2.next()){
						transp_charges=rset2.getString(1)==null?"":rset2.getString(1);
					}*/
					//System.out.println("---rset.getString(7)--"+rset1.getString(5)+"--transp_charges--"+transp_charges+"--TMP_QTY---"+TMP_QTY);
					if(!transp_charges.equals("")){
						double taxtot=0;
						double gross_inr=Double.parseDouble(nf6.format(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))));
						queryString = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0.00'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
								  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
								  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
								  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) ORDER BY A.tax_code";
						////System.out.println("STEP-1A.3:FMS7_TAX_STRUCTURE_DTL: "+queryString);
						rset=stmt.executeQuery(queryString);
						while(rset.next())
						{
							//tax_factor+= Integer.parseInt((rset.getString(2)));
							if(rset.getString(3).equals("1"))
							{
								//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset.getString(2)))/100;
								tax_amt = (gross_inr*Double.parseDouble(rset.getString(2)))/100;
								
							}
							else if(rset.getString(3).equals("2"))
							{
								queryString1 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
										  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
										  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
										  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) AND A.tax_code='"+rset.getString(4)+"'";
							//////System.out.println("Query For Finding Out Tax Which Is Dependent On Other Tax Value = "+queryString1);
						 		rset2=stmt2.executeQuery(queryString1);
						 		if(rset2.next())
						 		{
						 			if(rset2.getString(3).equals("1"))
									{
										//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset1.getString(2)))/100;
						 				tax_amt = (gross_inr*Double.parseDouble(rset2.getString(2)))/100;
									}
								
					 			tax_amt = (tax_amt*Double.parseDouble(rset.getString(2)))/100;
						 		}
						 		else
						 		{
						 			tax_amt = 0;
						 		}
							}
							else
							{
								tax_amt = 0;
							}
						
						//customer_Invoice_Tax_Amt.add(nf.format(Math.round(tax_amt)));
						
						taxtot+=Math.round(tax_amt);
						//net_amt.add(net_amt_inr[i]);
					}
						temp_grossamt=""+(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))+Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
						//Vsales_value.add(temp_grossamt);
						//Vsales_value.add(rset1.getString(5)==null?"0":rset1.getString(5));
						gross_amt_inr=temp_grossamt;
						String gross_temp="";
						String calcnetamt=nf6.format(Double.parseDouble(temp_grossamt)+taxtot);
						//System.out.println("---calcnetamt--"+calcnetamt+"---"+rset1.getString(6));
						if(Double.parseDouble(calcnetamt)==Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))){
							Vsales_value.add(temp_grossamt);
						}else{
							String diff="";
							//if(Double.parseDouble(calcnetamt)>Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))){
								
								diff=nf6.format(Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6))-Double.parseDouble(calcnetamt));
								//System.out.println("---diff-if-"+diff);
							/*}else{
								diff=nf6.format(Double.parseDouble(calcnetamt)-Double.parseDouble(rset1.getString(6)==null?"0":rset1.getString(6)));
								System.out.println("---diff-else-"+diff);
							}*/
							
							gross_temp=nf6.format(Double.parseDouble(temp_grossamt)+(Double.parseDouble(diff)));
						//	System.out.println("---gross_temp--"+gross_temp+"--"+temp_grossamt);
							Vsales_value.add(gross_temp);
							
						}
						if(Double.parseDouble(transp_charges)>0){
							//gross_amt_trans=""+(Double.parseDouble(TMP_QTY) * Double.parseDouble(transp_charges));
							//gross_amt_trans=gross_amt_inr;
							//gross_amt_trans=gross_temp;
							if(gross_temp.equals("")){
								gross_amt_inr=temp_grossamt;
								gross_amt_trans=temp_grossamt;//RG20230424
							}else{
								gross_amt_inr=gross_temp;
							}
						}else{
							gross_amt_trans=rset1.getString(5)==null?"":rset1.getString(5);
						}
						//System.out.println("---temp====="+temp_grossamt);
					}else{
						Vsales_value.add(rset1.getString(5)==null?"0":rset1.getString(5));
						gross_amt_inr=rset1.getString(5)==null?"0":rset1.getString(5);
						gross_amt_trans=rset1.getString(5)==null?"":rset1.getString(5);
					}
				
				}else{
				
					if(rset1.getString(5)!=null)
					{
						Vsales_value.add(rset1.getString(5)==null?"0":rset1.getString(5));
					}
					else
					{
						Vsales_value.add("0");
					}
						
					gross_amt_inr=rset1.getString(5)==null?"":rset1.getString(5);
				}
				Vgross_trans.add(gross_amt_trans);
				if(rset1.getString(6)!=null)
				{
					Vinv_value.add(rset1.getString(6));
				}
				else
				{
					Vinv_value.add("-");
				}
				
				
				String duedt=rset1.getString(23)==null?"":rset1.getString(23);
				String drcr_flg=rset1.getString(24)==null?"":rset1.getString(24);
				if(drcr_flg.equals("dr")){
					Vdue_dt.add(duedt);
				}else{
					Vdue_dt.add("-");
				}
				
				if(rset1.getString(7)!=null)
				{
					Vcont_type.add(rset1.getString(7));
				}
				else
				{
					Vcont_type.add("-");
				}
				
				if(rset1.getString(8)!=null)
				{
					Vfinancial_year.add(rset1.getString(8));
				}
				else
				{
					Vfinancial_year.add("-");
				}
				
				if(rset1.getString(9)!=null)
				{
					Vtax_str_cd.add(rset1.getString(9));
				}
				else
				{
					Vtax_str_cd.add("-");
				}
				
				if(rset1.getString(10)!=null)
				{
					Vtaxamtinr.add(rset1.getString(10));
				}
				else
				{
					Vtaxamtinr.add("-");
				}
				
				
				Vinvamtinr.add("-");
				
				
				//invamtinr=rset1.getString(12);
				sn_no=tmp_sn_no;
				taxvalue=rset1.getString(10);
				fyr=rset1.getString(8);
				if(rset1.getString(5)!=null){
					
					total_amt_inr=Double.parseDouble(rset1.getString(5));
				}else{
					total_amt_inr=0;
				}
				
				tax_str_cd=rset1.getString(9)==null?"":rset1.getString(9);
				
				Vsn_no.add(sn_no);
				
				//String invseq=contract_type+":"+financial_year+":"+hlpl_inv_seq_no+":%";
				String invseq=rset1.getString(7)+":"+rset1.getString(8)+":"+rset1.getString(3)+":"+rset1.getString(4);
				if(!invseq.equalsIgnoreCase(""))
				{
					Vinvseqno.add(invseq);
				}
				else
				{
					Vinvseqno.add("-");
				}
				
				////////tax_adj_amt////////
				
				String q1="select flag,amount from fms7_inv_compo_dtl where price_cd IN ('6','10','11','12','13') and inv_seq_no='"+invseq+"'";
				////System.out.println("JAVA check adjustmn query q1: "+q1);
				rset2=stmt2.executeQuery(q1);
				if(rset2.next())
				{
					if(rset2.getString(1)!=null)
					{
						taxadvflag=rset2.getString(1);
					}
					else
					{
						taxadvflag="N";
					}
					
					Vtax_adj_flag.add(taxadvflag);
								
					if(rset2.getString(2)!=null)
					{
						tax_adj_amt=nf.format(Double.parseDouble(rset2.getString(2)));
					}
					else
					{
						tax_adj_amt="0";
					}
				}
				else
				{
					taxadvflag="N";
					tax_adj_amt="0";
					Vtax_adj_flag.add(taxadvflag);
				}
				
				////System.out.println("---JAVA here taxadvflag---: "+taxadvflag+"---tax_adj_amt---: "+tax_adj_amt);
				double balance_adjust_amt=0,tot_adjust_amt=0;
				//String mapping_id=""+rset1.getString(2)+"-"+tmp_fgsa_no+"-"+tmp_fgsa_rev_no+"-"+tmp_sn_no+"-"+tmp_sn_rev_no+"-"+rset1.getString(7);
				String mapping_id="%"+rset1.getString(2)+"-"+tmp_fgsa_no+"-%-"+tmp_sn_no+"-%-"+rset1.getString(7)+"%";
				queryString = "select price_rate,currency_cd,flag " +
						"FROM fms7_SALES_cont_price_dtl WHERE mapping_id like '"+mapping_id+"' AND " +
						"price_cd='1' AND FLAG='Y' and pay_type='AP' ";
			//	System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					VADJUST_FLAG_SN.add(rset2.getString(3)==null?"N":rset2.getString(3));
					tot_adjust_amt=rset2.getDouble(1);
					VADJUST_AMT_SN.add(rset2.getString(1)==null?"":rset2.getString(1));
					VADJUST_CUR_SN.add(rset2.getString(2));
					if(rset1.getString(7).equals("S")){
						queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
								+ "CONTRACT_NO='"+rset1.getString(11)+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND AGR_REV_NO='"+rset.getString(2)+"' "
								+ "AND CONTRACT_TYPE='"+rset1.getString(8)+"' ";
	//					System.out.println("Fetching flag of sn..."+queryString);
						
					}else{
						queryString1 = "SELECT CFORM_FLAG  FROM FMS7_CFORM_CONTRACT_DTL WHERE PLANT_SEQ_NO='"+rset.getString(4)+"' AND CUSTOMER_CD='"+rset1.getString(2)+"' AND "
								+ "CONTRACT_NO='"+rset1.getString(13)+"' AND CONTRACT_REV_NO='"+rset.getString(3)+"' AND AGR_NO='"+rset.getString(1)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' ";
	//					System.out.println("Fetching flag of sn..."+queryString);
						
					}
					rset3=stmt3.executeQuery(queryString1);	
					if(rset3.next()){
						VCFORM_FLAG.add(rset3.getString(1)==null?"":rset3.getString(1));
					}else{
						VCFORM_FLAG.add("");
					}
				}else{
					VADJUST_FLAG_SN.add("");
					VADJUST_AMT_SN.add("");
					VADJUST_CUR_SN.add("");
					VCFORM_FLAG.add("");
				}
				queryString = "select sum(adjusted_amt) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
						+ "AND adjust_FLAG='Y' and commodity_type='RLNG' and contract_type='"+rset1.getString(7)+"'  ";
		//	System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					balance_adjust_amt=tot_adjust_amt-rset2.getDouble(1);
				}else{
					balance_adjust_amt=tot_adjust_amt;
				}
				queryString = "select sum(hold_amount) FROM fms8_pay_recv_dtl WHERE mapping_id like '"+mapping_id+"'  "
						+ "AND commodity_type='RLNG' and contract_type='"+rset1.getString(7)+"' and release_flag='Y' ";
//			System.out.println("Fetching flag of sn..."+queryString);
				rset2=stmt2.executeQuery(queryString);
				if(rset2.next())
				{
					balance_adjust_amt+=rset2.getDouble(1);
				}
				VADJUST_balc_SN.add(nf6.format(balance_adjust_amt));
				/////HERE
				if(!payamt.equals("")){
					if(rset1.getString(17)!=null)
					{
						if(agr_base.equals("D")){
							if(rset1.getString(26).equals("DIFF-QTY")){
								double tdsper=Double.parseDouble(rset1.getString(17));
								double salesval=0;
								if(agr_base.equalsIgnoreCase("D")){
									salesval=Double.parseDouble(gross_amt_trans);
								}else{
									salesval=Double.parseDouble(rset1.getString(5));
								}
								double tdsval=(tdsper*salesval)/100;
								double invval=Double.parseDouble(rset1.getString(6));
								//double netrecv=invval-tdsval;
								String netrecv="";
								if(rset1.getString(28)!=null)
								{
									netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(29)));
								}else{
									netrecv=nf6.format(invval-tdsval);
								}
								double actval=Double.parseDouble(payamt);
								short_recv=Double.parseDouble(netrecv)-actval;
							}
						}else{
							double tdsper=Double.parseDouble(rset1.getString(17));
							double salesval=0;
							if(agr_base.equalsIgnoreCase("D")){
								salesval=Double.parseDouble(gross_amt_trans);
							}else{
								salesval=Double.parseDouble(rset1.getString(5));
							}
							double tdsval=(tdsper*salesval)/100;
							double invval=Double.parseDouble(rset1.getString(6));
							//double netrecv=invval-tdsval;
							String netrecv="";
							if(rset1.getString(28)!=null)
							{
								netrecv=nf6.format(invval-tdsval-Double.parseDouble(rset1.getString(29)));
							}else{
								netrecv=nf6.format(invval-tdsval);
							}
							double actval=Double.parseDouble(payamt);
							short_recv=Double.parseDouble(netrecv)-actval;
						}
					}
					else
					{
						//System.out.println("short_recv_-in else--"+short_recv_);
						short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(payamt);
					}
					Vpay_short_recv_amt.add(short_recv);
				}else{
					Vpay_short_recv_amt.add("-");
				}
				Vgross_trans_inr.add(gross_amt_trans);
				if(invstatus.equalsIgnoreCase("PAID"))
				{
					if(rset1.getString(12)!=null)
					{
						Vpay_actual_recv_amt.add(rset1.getString(12));
					}
					else
					{
						Vpay_actual_recv_amt.add("-");
					}
					
					if(rset1.getString(13)!=null)
					{
						Vpay_recv_dt.add(rset1.getString(13));
					}
					else
					{
						Vpay_recv_dt.add("-");
					}
					
					if(rset1.getString(14)!=null)
					{
						Vpay_remark.add(rset1.getString(14));
					}
					else
					{
						Vpay_remark.add("-");
					}
					
					/*if(rset1.getString(17)!=null)
					{
						double tdsper=Double.parseDouble(rset1.getString(17));
						double salesval=Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5));
						double tdsval=(tdsper*salesval)/100;
						double invval=Double.parseDouble(rset1.getString(6));
						double netrecv=invval-tdsval;
						double actval=Double.parseDouble(rset1.getString(12)==null?"":rset1.getString(12));
						short_recv=netrecv-actval;
					}
					else
					{
						short_recv=Double.parseDouble(rset1.getString(6))-Double.parseDouble(rset1.getString(12)==null?"":rset1.getString(12));
					}
					
					Vpay_short_recv_amt.add(short_recv+"");*/
				
					Vpayflag.add("PAID");
					
					if(rset1.getString(15)!=null)
						Vinv_gen_by_cd.add(rset1.getString(15));
					else
						Vinv_gen_by_cd.add("00");
						
					if(!Chk_by.equals(""))
						Vchecked_by_cd.add(Chk_by);
					else
						Vchecked_by_cd.add("00");
					
					if(rset1.getString(16)!=null)
						Vapproved_by_cd.add(rset1.getString(16));
					else
						Vapproved_by_cd.add("00");
					
					if(!pdfinvdtl.equals(""))
					{
						Vpdf_inv_dtl.add(pdfinvdtl);
					}
					else
					{
						Vpdf_inv_dtl.add("-");
					}
					
					pdf_inv_dtl=pdfinvdtl;
					
					if(!print_ori.equals(""))
						print_by_ori=print_ori;
					else
						print_by_ori="00";
					
					if(!print_dupli.equals(""))
						print_by_dup=print_dupli;
					else
						print_by_dup="00";
					
					if(!print_trip.equals(""))
						print_by_tri=print_trip;
					else
						print_by_tri="00";
					
					////System.out.println("rset1.getString(24): "+rset1.getString(24));
					
					if(rset1.getString(17)!=null)
						Vtds_per.add(rset1.getString(17));
					else
						Vtds_per.add("-");
					
					if(pdf_inv_dtl.contains("T"))
					{
						Vprint_by_cd.add(print_by_tri);
					}
					else if(pdf_inv_dtl.contains("D"))
					{
						Vprint_by_cd.add(print_by_dup);
					}
					else if(pdf_inv_dtl.contains("O"))
					{
						Vprint_by_cd.add(print_by_ori);
					}
					else
					{
						Vprint_by_cd.add("");
					}
					
					if(rset1.getString(18)!=null)
						Vpay_update_dt.add(rset1.getString(18));
					else
						Vpay_update_dt.add("-");
					
					if(rset1.getString(19)!=null)
						Vpay_update_cnt.add(rset1.getString(19));
					else
						Vpay_update_cnt.add("-");
					
					/*if(taxadvflag.equalsIgnoreCase("Y"))
					{
						////System.out.println("HERE IN IF");
						if(rset1.getString(10)!=null)
						{
							double t=Double.parseDouble(rset1.getString(10))-Double.parseDouble(tax_adj_amt);
							////System.out.println("IN IF---t---: "+t);
							Vtottax.add(t);
						}	
						else
						{
							Vtottax.add("-");
						}
					}
					else
					{
						if(rset1.getString(10)!=null)
						{
							////System.out.println("IN ELSE---rset---: "+rset1.getString(27));
							Vtottax.add(rset1.getString(10));
						}	
						else
						{
							Vtottax.add("-");
						}
					}*/
					double taxtot=0;
					double gross_inr=Double.parseDouble(nf6.format(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))));
					queryString = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0.00'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) ORDER BY A.tax_code";
					////System.out.println("STEP-1A.3:FMS7_TAX_STRUCTURE_DTL: "+queryString);
					rset=stmt.executeQuery(queryString);
					while(rset.next())
					{
						//tax_factor+= Integer.parseInt((rset.getString(2)));
						if(rset.getString(3).equals("1"))
						{
							//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset.getString(2)))/100;
							tax_amt = (gross_inr*Double.parseDouble(rset.getString(2)))/100;
							
						}
						else if(rset.getString(3).equals("2"))
						{
							queryString1 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
									  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
									  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
									  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) AND A.tax_code='"+rset.getString(4)+"'";
						//////System.out.println("Query For Finding Out Tax Which Is Dependent On Other Tax Value = "+queryString1);
					 		rset2=stmt2.executeQuery(queryString1);
					 		if(rset2.next())
					 		{
					 			if(rset2.getString(3).equals("1"))
								{
									//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset1.getString(2)))/100;
					 				tax_amt = (gross_inr*Double.parseDouble(rset2.getString(2)))/100;
								}
							
				 			tax_amt = (tax_amt*Double.parseDouble(rset.getString(2)))/100;
					 		}
					 		else
					 		{
					 			tax_amt = 0;
					 		}
						}
						else
						{
							tax_amt = 0;
						}
					
					//customer_Invoice_Tax_Amt.add(nf.format(Math.round(tax_amt)));
					
					taxtot+=Math.round(tax_amt);
					//net_amt.add(net_amt_inr[i]);
				}
					Vtottax.add(taxtot);
				}
				else
				{
					Vpay_actual_recv_amt.add("-");
					Vpay_recv_dt.add("-");
					Vpay_remark.add("-");
					//Vpay_short_recv_amt.add("-");
					Vpayflag.add("UNPAID");
					
					if(rset1.getString(15)!=null)
						Vinv_gen_by_cd.add(rset1.getString(15));
					else
						Vinv_gen_by_cd.add("00");
					
					if(!Chk_by.equals(""))
						Vchecked_by_cd.add(Chk_by);
					else
						Vchecked_by_cd.add("00");
						
					if(rset1.getString(16)!=null)
						Vapproved_by_cd.add(rset1.getString(16));
					else
						Vapproved_by_cd.add("00");
						
					if(!pdfinvdtl.equals(""))
					{
						Vpdf_inv_dtl.add(pdfinvdtl);
					}
					else
					{
						Vpdf_inv_dtl.add("-");
					}
					
					pdf_inv_dtl=pdfinvdtl;
					
					if(!print_ori.equals(""))
						print_by_ori=print_ori;
					else
						print_by_ori="00";
					
					if(!print_dupli.equals(""))
						print_by_dup=print_dupli;
					else
						print_by_dup="00";
					
					if(!print_trip.equals(""))
						print_by_tri=print_trip;
					else
						print_by_tri="00";
						
					if(rset1.getString(17)!=null)
					{
						Vtds_per.add(rset1.getString(17));
					}
					else
					{
						Vtds_per.add("-");
					}
					//System.out.println("===pdf-inv_dtl=="+pdf_inv_dtl);
					
					if(pdf_inv_dtl.contains("T"))
					{
						Vprint_by_cd.add(print_by_tri);
					}
					else if(pdf_inv_dtl.contains("D"))
					{
						Vprint_by_cd.add(print_by_dup);
					}
					else if(pdf_inv_dtl.contains("O"))
					{
						Vprint_by_cd.add(print_by_ori);
					}
					else
					{
						Vprint_by_cd.add("");
					}
					
					if(rset1.getString(18)!=null)
						Vpay_update_dt.add(rset1.getString(18));
					else
						Vpay_update_dt.add("-");
					
					if(rset1.getString(19)!=null)
						Vpay_update_cnt.add(rset1.getString(19));
					else
						Vpay_update_cnt.add("-");
					
					/*if(taxadvflag.equalsIgnoreCase("Y"))
					{
//						if(rset1.getString(24)!=null)
//						{
//							double t=Double.parseDouble(rset1.getString(24))-Double.parseDouble(tax_adj_amt);
//							Vtottax.add(t);
//						}	
//						else
//						{
							Vtottax.add("-");
						//}
					}
					else
					{
						if(rset1.getString(24)!=null)
						{
							Vtottax.add(rset1.getString(24));
						}	
						else
						{
							Vtottax.add("-");
						//}
					}*/
					
					double taxtot=0;
					double gross_inr=Double.parseDouble(nf6.format(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))));
					queryString = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0.00'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
							  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) ORDER BY A.tax_code";
					////System.out.println("STEP-1A.3:FMS7_TAX_STRUCTURE_DTL: "+queryString);
					rset=stmt.executeQuery(queryString);
					while(rset.next())
					{
						//tax_factor+= Integer.parseInt((rset.getString(2)));
						if(rset.getString(3).equals("1"))
						{
							//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset.getString(2)))/100;
							tax_amt = (gross_inr*Double.parseDouble(rset.getString(2)))/100;
							
						}
						else if(rset.getString(3).equals("2"))
						{
							queryString1 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
									  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+rset1.getString(9)+"' AND " +
									  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+rset1.getString(9)+"' AND " +
									  "B.app_date<=TO_DATE('"+rset1.getString(4)+"','DD/MM/YYYY')) AND A.tax_code='"+rset.getString(4)+"'";
						//////System.out.println("Query For Finding Out Tax Which Is Dependent On Other Tax Value = "+queryString1);
					 		rset2=stmt2.executeQuery(queryString1);
					 		if(rset2.next())
					 		{
					 			if(rset2.getString(3).equals("1"))
								{
									//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset1.getString(2)))/100;
					 				tax_amt = (gross_inr*Double.parseDouble(rset2.getString(2)))/100;
								}
							
				 			tax_amt = (tax_amt*Double.parseDouble(rset.getString(2)))/100;
					 		}
					 		else
					 		{
					 			tax_amt = 0;
					 		}
						}
						else
						{
							tax_amt = 0;
						}
					
					//customer_Invoice_Tax_Amt.add(nf.format(Math.round(tax_amt)));
					
					taxtot+=Math.round(tax_amt);
					//net_amt.add(net_amt_inr[i]);
				}
					Vtottax.add(taxtot);
				}
				
				////System.out.println("===JAVA===2");
				/////////Vhlpl_inv_seq//////////
				
				if(Integer.parseInt(rset1.getString(3))<10)
				{
					hlpl_inv_no_disp="000"+rset1.getString(3)+"/"+rset1.getString(8);
				}
				else if(Integer.parseInt(rset1.getString(3))<100) 
				{
					hlpl_inv_no_disp="00"+rset1.getString(3)+"/"+rset1.getString(8);
				}
				else if(Integer.parseInt(rset1.getString(3))<1000) 
				{
					hlpl_inv_no_disp="0"+rset1.getString(3)+"/"+rset1.getString(8);
				}
				else
				{
					hlpl_inv_no_disp=rset1.getString(3)+"/"+rset1.getString(8);
				}
				hlpl_inv_no_disp=rset1.getString(21)==null?"":rset1.getString(21);
				if(!hlpl_inv_no_disp.equalsIgnoreCase(""))
				{
					Vhlpl_inv_seq.add(rset1.getString(21)==null?"":rset1.getString(21));
				}
				else
				{
					Vhlpl_inv_seq.add("-");
				}
				
				////System.out.println("===JAVA===3");
				
				///////////XML_GEN_FLAG/////////
				
				String queryString2 = "SELECT XML_GEN_FLAG,to_char(APPROVAL_DT,'dd/mm/yyyy') FROM FMS7_ACCOUNT_APPROVED_DTL " +
						  "WHERE INV_CARGO_NO='"+hlpl_inv_no_disp+"' AND JOURNAL_TYPE='FMSSL' " +
						  "AND CONTRACT_TYPE='"+contract_type+"' ";
					//	//System.out.println("Confirmed FMS7_PROV_SELLER_PAY Details Query = "+queryString);
					rset = stmt.executeQuery(queryString2);
					if(rset.next())
					{
						xml_gen_flag=rset.getString(1)==null?"N":rset.getString(1);
						approval_dt=rset.getString(2)==null?"":rset.getString(2);
						Vxml_gen_flag.add(xml_gen_flag);
					}
					else
					{
						xml_gen_flag="N";
						approval_dt="";
						Vxml_gen_flag.add(xml_gen_flag);
					}
						
					////System.out.println("===JAVA===4");
					
					///tax_adj
					
					////System.out.println("===JAVA===5");
					
					///////////inv_adj_amt///////////
					
					String q2="select flag,amount from fms7_inv_compo_dtl where price_cd='1' and inv_seq_no='"+invseq+"'";
					////System.out.println("JAVA check adjustmn query q2: "+q2);
					rset3=stmt3.executeQuery(q2);
					if(rset3.next())
					{
						if(rset3.getString(1)!=null)
						{
							advflag=rset3.getString(1);
						}
						else
						{
							advflag="N";
						}
						
						Vinv_adj_flag.add(advflag);
						
						if(rset3.getString(2)!=null)
						{
							inv_adj_amt=nf.format(Double.parseDouble(rset3.getString(2)));
						}
						else
						{
							inv_adj_amt="0";
						}
					}
					else
					{
						advflag="N";
						inv_adj_amt="0";
						Vinv_adj_flag.add(advflag);
					}
					////System.out.println("---JAVA here advflag: "+advflag);
								
					////System.out.println("===JAVA===6");
					
					////////////inv_amt///////////
					
					String inv_amt="";
					if(advflag.equalsIgnoreCase("Y") && taxadvflag.equalsIgnoreCase("Y"))
					{
						//query2="select INV_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
						inv_amt=invamtinr;
					}
					else
					{
						//query2="select GROSS_AMT_INR from fms7_invoice_mst where CUSTOMER_CD='"+rset1.getString(2)+"' AND CONTRACT_TYPE='"+rset1.getString(8)+"' AND SN_NO='"+sn_no+"' AND HLPL_INV_SEQ_NO='"+rset1.getString(3)+"' and invoice_dt=TO_DATE('"+rset1.getString(4)+"','dd/mm/yyyy')";
						inv_amt=gross_amt_inr;
					}
					////System.out.println("---JAVA inv_amt---: "+inv_amt);
							
					////System.out.println("===JAVA===7");
					
					String amt = "0";
					
					String map_id=contract_type+":"+fyr+":"+hlpl_inv_seq_no+":%";
					Vmapid.add(map_id);
					
					if(contract_type.equalsIgnoreCase("T") || contract_type.equalsIgnoreCase("C"))		//ADDED FOR LTCORA AND CN
					{
						// String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no like" +
						//	" '"+map_id+"'and price_cd='6' and (flag_temp not like 'T' or flag_temp is null)";
						
						String queryTax = "select amount from fms7_inv_compo_dtl where inv_seq_no='"+invseq+"' and price_cd IN ('6','10','11','12','13') "
								+ "and (flag_temp not like 'T' or flag_temp is null)";
						
						rset4=stmt4.executeQuery(queryTax);
						////System.out.println("---JAVA queryTax---: "+queryTax);
						if(rset4.next())
						{
							amt = ""+java.text.NumberFormat.getInstance().parse(rset4.getString(1));
							Adj_Tax_amt=amt;	
							taxvalue = ""+(Double.parseDouble(taxvalue) - Double.parseDouble(amt));
						}
					}
					
					////System.out.println("===JAVA===8");
					double gross_inr=Double.parseDouble(nf6.format(Double.parseDouble(rset1.getString(5)==null?"0":rset1.getString(5))));
					String queryString12 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
				 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
					
					////System.out.println("===VTAXAMT QUERY===: "+queryString12);
					rset5=stmt5.executeQuery(queryString12);
					////System.out.println("===JAVA===9");
					int cnt1=0;
					String tx_cd="",tx_amt="",tx_nm="";
					while(rset5.next())
					{
						tax_cd=rset5.getString(1);
						tax_on=rset5.getString(3);
						////System.out.println("---tax_on---"+tax_on);
						if(rset5.getString(3).equals("1")) 
						{
							cnt1++;
							////System.out.println("===IN IF=== "+cnt1);
							tax_amt = (gross_inr*Double.parseDouble(rset5.getString(2)))/100;
							////System.out.println(Double.parseDouble(gross_amt_inr)+"---total_amt_inr-IN IF--"+tax_amt);
							////System.out.println("---rset5.getString(2)-IN IF--"+rset5.getString(2));
						}
						else if(rset5.getString(3).equals("2"))
						{
							cnt1++;
							////System.out.println("===IN ELSE IF=== "+cnt1);
							String q3 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
										   "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
										   "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
										   "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) AND A.tax_code='"+rset5.getString(4)+"'";
							
							////System.out.println("===TAXAMT Query q3 === "+q3);
							rset6=stmt6.executeQuery(q3);
				 	 		
							if(rset6.next())
				 	 		{
				 	 			if(rset6.getString(3).equals("1"))
				 				{
				 					tax_amt = (gross_inr*Double.parseDouble(rset6.getString(2)))/100;
				 				}	
				 	 			////System.out.println("---total_amt_inr-IN ELSE IF--"+total_amt_inr);
								////System.out.println("---rset5.getString(2)-IN ELSE IF--"+rset5.getString(2));
				 	 			tax_amt = (tax_amt*Double.parseDouble(rset5.getString(2)))/100;
				 	 		}
				 	 		else
				 	 		{
				 	 			tax_amt=0;
				 	 		}
						}
						else
						{
							tax_amt = 0;
						}
						////System.out.println("===JAVA===9");
						double tax_diff=0;
						double tax_fact=Double.parseDouble(rset5.getString(2));
						if(tax_fact<tax_cform){
							tax_diff=tax_cform-tax_fact;
							tax_diff_val.add(tax_diff);
						}else{
							tax_diff_val.add("");
						}
						
						////////////////////////////BK:SB20151203//////////////////
			 			
						if(!Adj_Tax_amt.equalsIgnoreCase("") && taxadvflag.equalsIgnoreCase("Y"))
			 			{
							if(Double.parseDouble(Adj_Tax_amt)>0)
				 			{
								////System.out.println("---Adj Amt---: "+Adj_Tax_amt);
				 				tax_amt = tax_amt - Double.parseDouble(Adj_Tax_amt);
				 				Adj_Tax_amt = "0";
				 			}
							else
				 			{
								if(tax_on.equalsIgnoreCase("2"))
								{
									//taxamt=nf.format(tax_amt)+"";
									//Tot_Tax_amt = Tot_Tax_amt + Double.parseDouble(taxamt);
								}
				 			}
			 			}
						
						////System.out.println("===IN WHILE================= "+cnt1);
						
						///////////////////////////////////////////////////////
						queryString6="select sht_nm from fms7_tax_mst where tax_code='"+tax_cd+"'";
						rset6=stmt6.executeQuery(queryString6);
						while(rset6.next())
						{
							tax_nm=rset6.getString(1);
						}
						
						tx_cd+="@"+tax_cd;
						tx_amt+="@"+nf.format(tax_amt);
						tx_nm+="@"+tax_nm;
						
						////System.out.println("===JAVA tax_amt after=== "+tax_amt);
					}
					Vtax_code.add(tx_cd.replaceFirst("@", ""));  	
					Vtax_amt.add(tx_amt.replaceFirst("@", ""));
					Vtax_nm.add(tx_nm.replaceFirst("@", ""));
					////System.out.println("Vtax_code-"+Vtax_code);
					////System.out.println("Vtax_amt-"+Vtax_amt);
					
					String q123 = "SELECT COUNT(*) FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+to_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
				 			  //"B.app_date<=TO_DATE('"+bill_period_end_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";	//BK20151203
					rset=stmt.executeQuery(q123);
					while(rset.next())
					{
						//cnt1=rset.getInt(1);
						int count=rset.getInt(1);
						Vtaxcnt.add(count);
					}
					
					//////////////////////////////////////////////////////////
					///fOR GETTING REFERENCE NO FOR CONTRACT_NO AND AGREEMENT_NO///
					String mapp_id="";
					if(rset1.getString(7).equals("C") || rset1.getString(7).equals("M") || rset1.getString(7).equals("B")){
						if(invstatus.equalsIgnoreCase("PAID"))
						{
							mapp_id=mappid;
						}else{
							mapp_id=mappid;
						}
						//System.out.println("--mapp_id--"+mapp_id);
						String temp_mapp_id[]=mapp_id.split("-");
						VAgreement_no.add(temp_mapp_id[1]+"");
						VAgreement_rev_no.add(temp_mapp_id[2]+"");
						Vcont_no.add(temp_mapp_id[3]+"");
						Vcont_rev_no.add(temp_mapp_id[4]+"");
						if(""+temp_mapp_id[3]!=""){
							int temp_cont_no=Integer.parseInt(temp_mapp_id[3]+"");
							if(temp_cont_no>999){
								Vcont_typ.add("LTCORA (Period)");
							}else{
								Vcont_typ.add("LTCORA (CN)");
							}
						}else{
							Vcont_typ.add("LTCORA (CN)");
						}
						
					}else if(rset1.getString(7).equals("S")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("SN");
					}else if(rset1.getString(7).equals("L")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("LOA");
					}
					else if(rset1.getString(7).equals("I")){
						VAgreement_no.add("");
						VAgreement_rev_no.add("");
						Vcont_no.add("");
						Vcont_rev_no.add("");
						Vcont_typ.add("Interest");
					}
					else if(rset1.getString(7).equals("R")){
						Vcont_typ.add("Regas");
					}
				}
			}
//
			
				
				if(Vhlplinvseqno.size()==0) {
					queryString123="select count(*)" +
								 "FROM FMS7_INVOICE_MST WHERE CONTRACT_TYPE='"+contract_type+"' AND INVOICE_DT BETWEEN TO_DATE('"+from_dt+"','DD/MM/YYYY') " +
								 "AND TO_DATE('"+to_dt+"','DD/MM/YYYY') AND APPROVED_FLAG='Y' AND CHECKED_FLAG='Y' AND (PDF_INV_DTL IS NOT NULL) AND FLAG='Y' AND (NEW_INV_SEQ_NO NOT LIKE 'D%' OR NEW_INV_SEQ_NO IS NULL) ORDER BY TO_CHAR(INVOICE_DT,'YYYYMMDD') DESC"; //AND SUN_APPROVAL='Y'
					////System.out.println("queryString123 inv count: "+queryString123);
					rset = stmt.executeQuery(queryString123);
					while(rset.next()) {
						allInvoiceCount=rset.getString(1);
					}
				}
		}
				////System.out.println("JAVA Vhlplinvseqno.size(): "+Vhlplinvseqno.size());
				////System.out.println("JAVA Vhlplinvseqno: "+Vhlplinvseqno);
		fetch_Invoice_Details_exceedcode();
				
		}
		catch(Exception e)
		{
			////System.out.println("JAVA fetch_Invoice_Details() Exception---> "+e);
			e.printStackTrace();
		}
	
	}
	public void fetch_Invoice_Details_exceedcode() 						//BK20160309
	{

		try
		{

			Vector tmp_group_gross_usd_amt = new Vector();
			
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='VAT'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				vat_code = rset.getString(1)==null?"0":rset.getString(1);
			}			
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='CST'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				cst_code = rset.getString(1)==null?"0":rset.getString(1);
			}			
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='ADD. VAT'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				addl_code = rset.getString(1)==null?"0":rset.getString(1);
			}			
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='ST'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				st_code = rset.getString(1)==null?"0":rset.getString(1);
			}	
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='SBT' or UPPER(sht_nm)='SBC'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				sbc_code = rset.getString(1)==null?"0":rset.getString(1);
			}	
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='ECS'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				ecs_code = rset.getString(1)==null?"0":rset.getString(1);
			}			
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='HECS'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				hecs_code = rset.getString(1)==null?"0":rset.getString(1);
			}
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='ZVAT'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				zvat_code = rset.getString(1)==null?"0":rset.getString(1);
			}
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='IGST'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				igst_code = rset.getString(1)==null?"0":rset.getString(1);
			}
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='SGST'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				sgst_code = rset.getString(1)==null?"0":rset.getString(1);
			}
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='CGST'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				cgst_code = rset.getString(1)==null?"0":rset.getString(1);
			}
			queryString = "SELECT tax_code FROM FMS7_TAX_MST WHERE UPPER(sht_nm)='ZGST'";
			rset = stmt.executeQuery(queryString);
			if(rset.next())
			{
				zgst_code = rset.getString(1)==null?"0":rset.getString(1);
			}
			
			queryString="SELECT TAX_CODE,FACTOR FROM FMS7_TAX_STRUCTURE_DTL A WHERE TAX_STR_CD='22' AND APP_DATE=(SELECT MAX(B.APP_DATE) "
					+ "FROM FMS7_TAX_STRUCTURE_DTL B WHERE A.TAX_STR_CD=B.TAX_STR_CD AND B.TAX_STR_CD='22' )";
					// "B.APP_DATE<=TO_DATE('01/08/2020','DD/MM/YYYY'))";
			rset=stmt.executeQuery(queryString);
			//System.out.println("queryString---"+queryString);
			if(rset.next()){
				
				queryString1 = "SELECT sht_nm,tax_name FROM FMS7_TAX_MST WHERE " +
						  "tax_code="+rset.getString(1)+"";
				//System.out.println("Query For Fetching Tax Name = "+queryString);
				rset1 = stmt1.executeQuery(queryString1);
				if(rset1.next())
				{
					tcs_nm=rset1.getString(2)==null?"":rset1.getString(2);
					tcs_sht_nm=rset1.getString(1)==null?"":rset1.getString(1);
					tcs_nm=tcs_sht_nm;
				}
				tcs_fact=rset.getString(2)==null?"":rset.getString(2);
			}
			
			for(int j=0;j<Vinv_gen_by_cd.size();j++)
			{
				queryString2="select emp_nm,email_id from hr_emp_mst where emp_cd='"+Vinv_gen_by_cd.elementAt(j)+"'";
				rset2=stmt2.executeQuery(queryString2);
				if(rset2.next())
				{
					if(rset2.getString(1)!=null)
						Vinv_gen_by_nm.add(rset2.getString(1));
					else
						Vinv_gen_by_nm.add("-");
								
					if(rset2.getString(2)!=null)
						Vinv_gen_by_emailid.add(rset2.getString(2));
					else
						Vinv_gen_by_emailid.add("-");
				}
				else
				{
					Vinv_gen_by_nm.add("-");
					Vinv_gen_by_emailid.add("-");
				}
				
				queryString3="select emp_nm,email_id from hr_emp_mst where emp_cd='"+Vchecked_by_cd.elementAt(j)+"'";
				rset3=stmt3.executeQuery(queryString3);
				if(rset3.next())
				{
					if(rset3.getString(1)!=null)
						Vchecked_by_nm.add(rset3.getString(1));
					else
						Vchecked_by_nm.add("-");
					
					if(rset3.getString(2)!=null)
						Vchecked_by_emailid.add(rset3.getString(2));
					else
						Vchecked_by_emailid.add("-");
				}
				else
				{
					Vchecked_by_nm.add("-");
					Vchecked_by_emailid.add("-");
				}
				
				queryString4="select emp_nm,email_id from hr_emp_mst where emp_cd='"+Vapproved_by_cd.elementAt(j)+"'";
				//System.out.println("querysss----"+queryString4);
				rset4=stmt4.executeQuery(queryString4);
				if(rset4.next())
				{
					if(rset4.getString(1)!=null)
						Vapproved_by_nm.add(rset4.getString(1));
					else
						Vapproved_by_nm.add("-");
					
					if(rset4.getString(2)!=null)
						Vapproved_by_emailid.add(rset4.getString(2));
					else
						Vapproved_by_emailid.add("-");
				}
				else
				{
					Vapproved_by_nm.add("-");
					Vapproved_by_emailid.add("-");
				}
				//System.out.println("Vprint_by_cd---"+Vprint_by_cd);
				String printcd="";
				if(Vprint_by_cd.elementAt(j).equals("-")){
					printcd="";
				}else{
					printcd=Vprint_by_cd.elementAt(j)+"";
				}
				queryString5="select emp_nm,email_id from hr_emp_mst where emp_cd='"+printcd+"'";
				//System.out.println("query---"+queryString5);
				rset5=stmt5.executeQuery(queryString5);
				if(rset5.next())
				{
					if(rset5.getString(1)!=null)
						Vprint_by_nm.add(rset5.getString(1)==null?"":rset5.getString(1));
					else
						Vprint_by_nm.add("-");
								
					if(rset5.getString(2)!=null)
						Vprint_by_emailid.add(rset5.getString(2)==null?"":rset5.getString(2));
					else
						Vprint_by_emailid.add("-");
				}
				else
				{
					Vprint_by_nm.add("-");
					Vprint_by_emailid.add("-");
				}
			}
			
			String logged_on_user_nm="";
			
			queryString2="select emp_nm,email_id from hr_emp_mst where emp_cd='"+emp_cd+"'";
			rset2=stmt2.executeQuery(queryString2);
			while(rset2.next())
			{
				if(rset2.getString(1)!=null)
					logged_on_user_nm=rset2.getString(1);
				else
					logged_on_user_nm="-";
				
				if(rset2.getString(2)!=null)
					logged_on_user_emailid=rset2.getString(2);
				else
					logged_on_user_emailid="";
			}
			
			if(For1.equalsIgnoreCase("insert"))
			{
				queryString2="select form_cd,form_name from sec_form_mst where form_name like 'Invoice Payment Entry%'";
				rset2=stmt2.executeQuery(queryString2);
				while(rset2.next())
				{
					formcd=rset2.getString(1);
					formname=rset2.getString(2);
				}
			}
			else if(For1.equalsIgnoreCase("update"))
			{
				queryString2="select form_cd,form_name from sec_form_mst where form_name like 'Invoice Payment Update%'";
				rset2=stmt2.executeQuery(queryString2);
				while(rset2.next())
				{
					formcd=rset2.getString(1);
					formname=rset2.getString(2);
				}
			}
		}
		catch(Exception e)
		{
			////System.out.println("JAVA fetch_Invoice_Details() Exception---> "+e);
			e.printStackTrace();
		}
	
	}
	public void CREATE_COLUMN() 						
	{
		try {
			int count=0;
			String query="SELECT COUNT(COLUMN_NAME) FROM COLS WHERE UPPER(TABLE_NAME) LIKE 'FMS7_DR_CR_NOTE' "
					+ "AND UPPER(COLUMN_NAME) LIKE 'PAY_RECV_AMT' ";
			rset=stmt.executeQuery(query);
			if(rset.next())
			{
				count=rset.getInt(1);
			}
			if(count==0)
			{
				query="alter table FMS7_DR_CR_NOTE add PAY_RECV_AMT NUMBER(12,2)";
				stmt.executeUpdate(query);
				
				query="alter table FMS7_DR_CR_NOTE add PAY_RECV_DT DATE";
				stmt.executeUpdate(query);
				
				query="alter table FMS7_DR_CR_NOTE add PAY_REMARK VARCHAR2(100)";
				stmt.executeUpdate(query);
				
				query="alter table FMS7_DR_CR_NOTE add PAY_UPDATE_CNT NUMBER(1)";
				stmt.executeUpdate(query);
				
				query="alter table FMS7_DR_CR_NOTE add PAY_UPDATE_DT DATE";
				stmt.executeUpdate(query);
				query="alter table FMS7_DR_CR_NOTE add PAY_INSERT_DT DATE";
				stmt.executeUpdate(query);
				query="alter table FMS7_DR_CR_NOTE add PAY_INSERT_BY NUMBER(6)";
				stmt.executeUpdate(query);
				query="alter table FMS7_DR_CR_NOTE add PAY_UPDATE_BY NUMBER(6)";
				stmt.executeUpdate(query);
				
				query="alter table FMS7_DR_CR_NOTE add tds_percent NUMBER(4,2)";
				stmt.executeUpdate(query);
				
				conn.commit();
			}
			int cnt1=0;
			 query="SELECT COUNT(COLUMN_NAME) FROM COLS WHERE UPPER(TABLE_NAME) LIKE 'FMS8_PAY_RECV_DTL' "
						+ "AND UPPER(COLUMN_NAME) LIKE 'HOLD_AMOUNT' ";
				rset=stmt.executeQuery(query);
				if(rset.next())
				{
					cnt1=rset.getInt(1);
				}
				if(cnt1==0)
				{
					query="alter table FMS8_PAY_RECV_DTL add HOLD_AMOUNT NUMBER(11,2)";
					stmt.executeUpdate(query);
					
					conn.commit();
				}
			int count1=0;
			 query="SELECT COUNT(COLUMN_NAME) FROM COLS WHERE UPPER(TABLE_NAME) LIKE 'DLNG_DR_CR_NOTE' "
					+ "AND UPPER(COLUMN_NAME) LIKE 'PAY_INSERT_BY' ";
			rset=stmt.executeQuery(query);
			if(rset.next())
			{
				count1=rset.getInt(1);
			}
			if(count1==0)
			{
//				query="alter table DLNG_DR_CR_NOTE add PAY_RECV_AMT NUMBER(12,2)";
//				stmt.executeUpdate(query);
//				
//				query="alter table DLNG_DR_CR_NOTE add PAY_RECV_DT DATE";
//				stmt.executeUpdate(query);
				
				query="alter table DLNG_DR_CR_NOTE add PAY_REMARK VARCHAR2(100)";
				stmt.executeUpdate(query);
				
				query="alter table DLNG_DR_CR_NOTE add PAY_UPDATE_CNT NUMBER(1)";
				stmt.executeUpdate(query);
				
				query="alter table DLNG_DR_CR_NOTE add PAY_UPDATE_DT DATE";
				stmt.executeUpdate(query);
				query="alter table DLNG_DR_CR_NOTE add PAY_INSERT_DT DATE";
				stmt.executeUpdate(query);
				query="alter table DLNG_DR_CR_NOTE add PAY_INSERT_BY NUMBER(6)";
				stmt.executeUpdate(query);
				query="alter table DLNG_DR_CR_NOTE add PAY_UPDATE_BY NUMBER(6)";
				stmt.executeUpdate(query);
				
				query="alter table DLNG_DR_CR_NOTE add tds_percent NUMBER(4,2)";
				stmt.executeUpdate(query);
				
				conn.commit();
			}
			int cnt=0;
			query="SELECT COUNT(COLUMN_NAME) FROM COLS WHERE UPPER(TABLE_NAME) LIKE 'FMS7_MANUAL_DR_CR_NOTE' "
					+ "AND UPPER(COLUMN_NAME) LIKE 'PAY_RECV_AMT' ";
			rset=stmt.executeQuery(query);
			if(rset.next())
			{
				cnt=rset.getInt(1);
			}
			if(cnt==0)
			{
				query="alter table FMS7_MANUAL_DR_CR_NOTE add PAY_RECV_AMT NUMBER(12,2)";
				stmt.executeUpdate(query);
				
				query="alter table FMS7_MANUAL_DR_CR_NOTE add PAY_RECV_DT DATE";
				stmt.executeUpdate(query);
				
				query="alter table FMS7_MANUAL_DR_CR_NOTE add PAY_REMARK VARCHAR2(100)";
				stmt.executeUpdate(query);
				
				query="alter table FMS7_MANUAL_DR_CR_NOTE add PAY_UPDATE_CNT NUMBER(1)";
				stmt.executeUpdate(query);
				
				query="alter table FMS7_MANUAL_DR_CR_NOTE add PAY_UPDATE_DT DATE";
				stmt.executeUpdate(query);
				query="alter table FMS7_MANUAL_DR_CR_NOTE add PAY_INSERT_DT DATE";
				stmt.executeUpdate(query);
				query="alter table FMS7_MANUAL_DR_CR_NOTE add PAY_INSERT_BY NUMBER(6)";
				stmt.executeUpdate(query);
				query="alter table FMS7_MANUAL_DR_CR_NOTE add PAY_UPDATE_BY NUMBER(6)";
				stmt.executeUpdate(query);
				
				query="alter table FMS7_MANUAL_DR_CR_NOTE add tds_percent NUMBER(4,2)";
				stmt.executeUpdate(query);
				
				conn.commit();
			}
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public String getTo_dt() {
		return to_dt;
	}
	public void setTo_dt(String to_dt) {
		this.to_dt = to_dt;
	}
	public String getCallFlag() {
		return callFlag;
	}
	public void setCallFlag(String callFlag) {
		this.callFlag = callFlag;
	}
	public int getTax_cform() {
		return tax_cform;
	}
	public void setTax_cform(int tax_cform) {
		this.tax_cform = tax_cform;
	}
	public double getTds_limit_amt() {
		return tds_limit_amt;
	}
	public void setTds_limit_amt(double tds_limit_amt) {
		this.tds_limit_amt = tds_limit_amt;
	}
	public Vector getTax_diff_val() {
		return tax_diff_val;
	}
	public void setTax_diff_val(Vector tax_diff_val) {
		this.tax_diff_val = tax_diff_val;
	}
	public String getTo_year() {
		return to_year;
	}
	public void setTo_year(String to_year) {
		this.to_year = to_year;
	}
	public String getInvstatus() {
		return invstatus;
	}
	public void setInvstatus(String invstatus) {
		this.invstatus = invstatus;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTo_month() {
		return to_month;
	}
	public void setTo_month(String to_month) {
		this.to_month = to_month;
	}
	public String getTo_mon() {
		return to_mon;
	}
	public void setTo_mon(String to_mon) {
		this.to_mon = to_mon;
	}
	public String getMon() {
		return mon;
	}
	public void setMon(String mon) {
		this.mon = mon;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getFrom_dt() {
		return from_dt;
	}
	public void setFrom_dt(String from_dt) {
		this.from_dt = from_dt;
	}
	public String getFor1() {
		return For1;
	}
	public void setFor1(String for1) {
		For1 = for1;
	}
	public String getContract_type() {
		return contract_type;
	}
	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}
	public String getHlpl_inv_seq_no() {
		return hlpl_inv_seq_no;
	}
	public void setHlpl_inv_seq_no(String hlpl_inv_seq_no) {
		this.hlpl_inv_seq_no = hlpl_inv_seq_no;
	}
	public String getTax_struct_cd() {
		return tax_struct_cd;
	}
	public void setTax_struct_cd(String tax_struct_cd) {
		this.tax_struct_cd = tax_struct_cd;
	}
	public String getPeriod_start_dt() {
		return period_start_dt;
	}
	public void setPeriod_start_dt(String period_start_dt) {
		this.period_start_dt = period_start_dt;
	}
	public String getGross_amt_inr() {
		return gross_amt_inr;
	}
	public void setGross_amt_inr(String gross_amt_inr) {
		this.gross_amt_inr = gross_amt_inr;
	}
	public Vector getVADJUST_FLAG_SN() {
		return VADJUST_FLAG_SN;
	}
	public void setVADJUST_FLAG_SN(Vector vADJUST_FLAG_SN) {
		VADJUST_FLAG_SN = vADJUST_FLAG_SN;
	}
	public Vector getVCFORM_FLAG() {
		return VCFORM_FLAG;
	}
	public void setVCFORM_FLAG(Vector vCFORM_FLAG) {
		VCFORM_FLAG = vCFORM_FLAG;
	}
	public String getCustomer_cd() {
		return customer_cd;
	}
	public void setCustomer_cd(String customer_cd) {
		this.customer_cd = customer_cd;
	}
	public Vector getVcustomer_cd() {
		return Vcustomer_cd;
	}
	public void setVcustomer_cd(Vector vcustomer_cd) {
		Vcustomer_cd = vcustomer_cd;
	}
	public Vector getVcustomer_abbr() {
		return Vcustomer_abbr;
	}
	public void setVcustomer_abbr(Vector vcustomer_abbr) {
		Vcustomer_abbr = vcustomer_abbr;
	}
	public Vector getVcont_typ() {
		return Vcont_typ;
	}
	public void setVcont_typ(Vector vcont_typ) {
		Vcont_typ = vcont_typ;
	}
	public Vector getVcustomer_name() {
		return Vcustomer_name;
	}
	public void setVcustomer_name(Vector vcustomer_name) {
		Vcustomer_name = vcustomer_name;
	}
	public Vector getVcont_type() {
		return Vcont_type;
	}
	public void setVcont_type(Vector vcont_type) {
		Vcont_type = vcont_type;
	}
	public String getTds_app_amt() {
		return tds_app_amt;
	}
	public void setTds_app_amt(String tds_app_amt) {
		this.tds_app_amt = tds_app_amt;
	}
	public Vector getTax_code() {
		return tax_code;
	}
	public void setTax_code(Vector tax_code) {
		this.tax_code = tax_code;
	}
	public Vector getTax_amount() {
		return tax_amount;
	}
	public void setTax_amount(Vector tax_amount) {
		this.tax_amount = tax_amount;
	}
	public Vector getTax_amount_usd() {
		return tax_amount_usd;
	}
	public void setTax_amount_usd(Vector tax_amount_usd) {
		this.tax_amount_usd = tax_amount_usd;
	}
	public Vector getVADJUST_AMT_SN() {
		return VADJUST_AMT_SN;
	}
	public void setVADJUST_AMT_SN(Vector vADJUST_AMT_SN) {
		VADJUST_AMT_SN = vADJUST_AMT_SN;
	}
	public Vector getVADJUST_balc_SN() {
		return VADJUST_balc_SN;
	}
	public void setVADJUST_balc_SN(Vector vADJUST_balc_SN) {
		VADJUST_balc_SN = vADJUST_balc_SN;
	}
	public Vector getVADJUST_CUR_SN() {
		return VADJUST_CUR_SN;
	}
	public void setVADJUST_CUR_SN(Vector vADJUST_CUR_SN) {
		VADJUST_CUR_SN = vADJUST_CUR_SN;
	}
	public String getAllInvoiceCount() {
		return allInvoiceCount;
	}
	public void setAllInvoiceCount(String allInvoiceCount) {
		this.allInvoiceCount = allInvoiceCount;
	}
	public String getVat_code() {
		return vat_code;
	}
	public void setVat_code(String vat_code) {
		this.vat_code = vat_code;
	}
	public String getCst_code() {
		return cst_code;
	}
	public void setCst_code(String cst_code) {
		this.cst_code = cst_code;
	}
	public String getAddl_code() {
		return addl_code;
	}
	public void setAddl_code(String addl_code) {
		this.addl_code = addl_code;
	}
	public String getSt_code() {
		return st_code;
	}
	public void setSt_code(String st_code) {
		this.st_code = st_code;
	}
	public String getSbc_code() {
		return sbc_code;
	}
	public void setSbc_code(String sbc_code) {
		this.sbc_code = sbc_code;
	}
	public String getEcs_code() {
		return ecs_code;
	}
	public void setEcs_code(String ecs_code) {
		this.ecs_code = ecs_code;
	}
	public String getHecs_code() {
		return hecs_code;
	}
	public void setHecs_code(String hecs_code) {
		this.hecs_code = hecs_code;
	}
	public String getIgst_code() {
		return igst_code;
	}
	public void setIgst_code(String igst_code) {
		this.igst_code = igst_code;
	}
	public String getSgst_code() {
		return sgst_code;
	}
	public void setSgst_code(String sgst_code) {
		this.sgst_code = sgst_code;
	}
	public String getCgst_code() {
		return cgst_code;
	}
	public void setCgst_code(String cgst_code) {
		this.cgst_code = cgst_code;
	}
	public Vector getFlag_inv() {
		return flag_inv;
	}
	public void setFlag_inv(Vector flag_inv) {
		this.flag_inv = flag_inv;
	}
	public Vector getVAgreement_no() {
		return VAgreement_no;
	}
	public void setVAgreement_no(Vector vAgreement_no) {
		VAgreement_no = vAgreement_no;
	}
	public Vector getVAgreement_rev_no() {
		return VAgreement_rev_no;
	}
	public void setVAgreement_rev_no(Vector vAgreement_rev_no) {
		VAgreement_rev_no = vAgreement_rev_no;
	}
	public Vector getVcont_no() {
		return Vcont_no;
	}
	public void setVcont_no(Vector vcont_no) {
		Vcont_no = vcont_no;
	}
	public Vector getVcont_rev_no() {
		return Vcont_rev_no;
	}
	public void setVcont_rev_no(Vector vcont_rev_no) {
		Vcont_rev_no = vcont_rev_no;
	}
	public String getEmp_cd() {
		return emp_cd;
	}
	public void setEmp_cd(String emp_cd) {
		this.emp_cd = emp_cd;
	}
	public String getUpdate_flag() {
		return update_flag;
	}
	public void setUpdate_flag(String update_flag) {
		this.update_flag = update_flag;
	}
	public String getLogged_on_user_emailid() {
		return logged_on_user_emailid;
	}
	public void setLogged_on_user_emailid(String logged_on_user_emailid) {
		this.logged_on_user_emailid = logged_on_user_emailid;
	}
	public Vector getAgreement_base() {
		return Agreement_base;
	}
	public void setAgreement_base(Vector agreement_base) {
		Agreement_base = agreement_base;
	}
	public Vector getTrans_charges() {
		return trans_charges;
	}
	public void setTrans_charges(Vector trans_charges) {
		this.trans_charges = trans_charges;
	}
	public Vector getTrans_gross_inr() {
		return trans_gross_inr;
	}
	public void setTrans_gross_inr(Vector trans_gross_inr) {
		this.trans_gross_inr = trans_gross_inr;
	}
	public double getTot_trans_grossinr() {
		return tot_trans_grossinr;
	}
	public void setTot_trans_grossinr(double tot_trans_grossinr) {
		this.tot_trans_grossinr = tot_trans_grossinr;
	}
	public String getAgr_base() {
		return agr_base;
	}
	public void setAgr_base(String agr_base) {
		this.agr_base = agr_base;
	}
	public String getTcs_nm() {
		return tcs_nm;
	}
	public void setTcs_nm(String tcs_nm) {
		this.tcs_nm = tcs_nm;
	}
	public String getTcs_cd() {
		return tcs_cd;
	}
	public void setTcs_cd(String tcs_cd) {
		this.tcs_cd = tcs_cd;
	}
	public String getFact() {
		return fact;
	}
	public void setFact(String fact) {
		this.fact = fact;
	}
	public String getTcs_sht_nm() {
		return tcs_sht_nm;
	}
	public void setTcs_sht_nm(String tcs_sht_nm) {
		this.tcs_sht_nm = tcs_sht_nm;
	}
	public String getTcs_fact() {
		return tcs_fact;
	}
	public void setTcs_fact(String tcs_fact) {
		this.tcs_fact = tcs_fact;
	}
	public Vector getInv_flag() {
		return inv_flag;
	}
	public void setInv_flag(Vector inv_flag) {
		this.inv_flag = inv_flag;
	}
	public Vector getVcust_inv_seq_no() {
		return Vcust_inv_seq_no;
	}
	public void setVcust_inv_seq_no(Vector vcust_inv_seq_no) {
		Vcust_inv_seq_no = vcust_inv_seq_no;
	}
	public Vector getVapproved_flag() {
		return Vapproved_flag;
	}
	public void setVapproved_flag(Vector vapproved_flag) {
		Vapproved_flag = vapproved_flag;
	}
	public Vector getVAgreement_base() {
		return VAgreement_base;
	}
	public void setVAgreement_base(Vector vAgreement_base) {
		VAgreement_base = vAgreement_base;
	}
	public String getBtnFlag() {
		return btnFlag;
	}
	public void setBtnFlag(String btnFlag) {
		this.btnFlag = btnFlag;
	}
	public Vector getVchecked_by_cd() {
		return Vchecked_by_cd;
	}
	public void setVchecked_by_cd(Vector vchecked_by_cd) {
		Vchecked_by_cd = vchecked_by_cd;
	}
	public Vector getVapproved_by_cd() {
		return Vapproved_by_cd;
	}
	public void setVapproved_by_cd(Vector vapproved_by_cd) {
		Vapproved_by_cd = vapproved_by_cd;
	}
	public Vector getVchecked_by_emailid() {
		return Vchecked_by_emailid;
	}
	public void setVchecked_by_emailid(Vector vchecked_by_emailid) {
		Vchecked_by_emailid = vchecked_by_emailid;
	}
	public Vector getVapproved_by_emailid() {
		return Vapproved_by_emailid;
	}
	public void setVapproved_by_emailid(Vector vapproved_by_emailid) {
		Vapproved_by_emailid = vapproved_by_emailid;
	}
	public Vector getVchecked_by_nm() {
		return Vchecked_by_nm;
	}
	public void setVchecked_by_nm(Vector vchecked_by_nm) {
		Vchecked_by_nm = vchecked_by_nm;
	}
	public Vector getVapproved_by_nm() {
		return Vapproved_by_nm;
	}
	public void setVapproved_by_nm(Vector vapproved_by_nm) {
		Vapproved_by_nm = vapproved_by_nm;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public int getInv_roundoff() {
		return Inv_roundoff;
	}
	public void setInv_roundoff(int inv_roundoff) {
		Inv_roundoff = inv_roundoff;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Vector getVmonth() {
		return Vmonth;
	}
	public void setVmonth(Vector vmonth) {
		Vmonth = vmonth;
	}
	public Vector getVhlplinvseqno() {
		return Vhlplinvseqno;
	}
	public void setVhlplinvseqno(Vector vhlplinvseqno) {
		Vhlplinvseqno = vhlplinvseqno;
	}
	public Vector getVfinancial_year() {
		return Vfinancial_year;
	}
	public void setVfinancial_year(Vector vfinancial_year) {
		Vfinancial_year = vfinancial_year;
	}
	public Vector getVdiff_tcs() {
		return Vdiff_tcs;
	}
	public void setVdiff_tcs(Vector vdiff_tcs) {
		Vdiff_tcs = vdiff_tcs;
	}
	public Vector getVdiff_tcs_flg() {
		return Vdiff_tcs_flg;
	}
	public void setVdiff_tcs_flg(Vector vdiff_tcs_flg) {
		Vdiff_tcs_flg = vdiff_tcs_flg;
	}
	public Vector getVinv_dt() {
		return Vinv_dt;
	}
	public void setVinv_dt(Vector vinv_dt) {
		Vinv_dt = vinv_dt;
	}
	public Vector getVdue_dt() {
		return Vdue_dt;
	}
	public void setVdue_dt(Vector vdue_dt) {
		Vdue_dt = vdue_dt;
	}
	public Vector getVsales_value() {
		return Vsales_value;
	}
	public void setVsales_value(Vector vsales_value) {
		Vsales_value = vsales_value;
	}
	public Vector getVtax_nm() {
		return Vtax_nm;
	}
	public void setVtax_nm(Vector vtax_nm) {
		Vtax_nm = vtax_nm;
	}
	public Vector getVtax_cd() {
		return Vtax_cd;
	}
	public void setVtax_cd(Vector vtax_cd) {
		Vtax_cd = vtax_cd;
	}
	public Vector getVinv_value() {
		return Vinv_value;
	}
	public void setVinv_value(Vector vinv_value) {
		Vinv_value = vinv_value;
	}
	public Vector getVxml_gen_flag() {
		return Vxml_gen_flag;
	}
	public void setVxml_gen_flag(Vector vxml_gen_flag) {
		Vxml_gen_flag = vxml_gen_flag;
	}
	public Vector getVhlpl_inv_seq() {
		return Vhlpl_inv_seq;
	}
	public void setVhlpl_inv_seq(Vector vhlpl_inv_seq) {
		Vhlpl_inv_seq = vhlpl_inv_seq;
	}
	public Vector getVtax_str_cd() {
		return Vtax_str_cd;
	}
	public void setVtax_str_cd(Vector vtax_str_cd) {
		Vtax_str_cd = vtax_str_cd;
	}
	public Vector getVtds_app_flag() {
		return Vtds_app_flag;
	}
	public void setVtds_app_flag(Vector vtds_app_flag) {
		Vtds_app_flag = vtds_app_flag;
	}
	public Vector getVtds_app_amt() {
		return Vtds_app_amt;
	}
	public void setVtds_app_amt(Vector vtds_app_amt) {
		Vtds_app_amt = vtds_app_amt;
	}
	public Vector getVtax_on() {
		return Vtax_on;
	}
	public void setVtax_on(Vector vtax_on) {
		Vtax_on = vtax_on;
	}
	public Vector getVinvseqno() {
		return Vinvseqno;
	}
	public void setVinvseqno(Vector vinvseqno) {
		Vinvseqno = vinvseqno;
	}
	public Vector getVtaxamtinr() {
		return Vtaxamtinr;
	}
	public void setVtaxamtinr(Vector vtaxamtinr) {
		Vtaxamtinr = vtaxamtinr;
	}
	public Vector getVinvamtinr() {
		return Vinvamtinr;
	}
	public void setVinvamtinr(Vector vinvamtinr) {
		Vinvamtinr = vinvamtinr;
	}
	public Vector getVtax_code() {
		return Vtax_code;
	}
	public void setVtax_code(Vector vtax_code) {
		Vtax_code = vtax_code;
	}
	public Vector getVtax_amt() {
		return Vtax_amt;
	}
	public void setVtax_amt(Vector vtax_amt) {
		Vtax_amt = vtax_amt;
	}
	public Vector getVtaxnm() {
		return Vtaxnm;
	}
	public void setVtaxnm(Vector vtaxnm) {
		Vtaxnm = vtaxnm;
	}
	public Vector getVtaxcnt() {
		return Vtaxcnt;
	}
	public void setVtaxcnt(Vector vtaxcnt) {
		Vtaxcnt = vtaxcnt;
	}
	public String getZvat_code() {
		return zvat_code;
	}
	public void setZvat_code(String zvat_code) {
		this.zvat_code = zvat_code;
	}
	public String getZgst_code() {
		return zgst_code;
	}
	public void setZgst_code(String zgst_code) {
		this.zgst_code = zgst_code;
	}
	public Vector getVtcs_amt() {
		return Vtcs_amt;
	}
	public void setVtcs_amt(Vector vtcs_amt) {
		Vtcs_amt = vtcs_amt;
	}
	public Vector getVPAY_NEW_INV_SEQ_NO() {
		return VPAY_NEW_INV_SEQ_NO;
	}
	public void setVPAY_NEW_INV_SEQ_NO(Vector vPAY_NEW_INV_SEQ_NO) {
		VPAY_NEW_INV_SEQ_NO = vPAY_NEW_INV_SEQ_NO;
	}
	public Vector getVdrcrcriteria() {
		return Vdrcrcriteria;
	}
	public void setVdrcrcriteria(Vector vdrcrcriteria) {
		Vdrcrcriteria = vdrcrcriteria;
	}
	public Vector getVdrcrflag() {
		return Vdrcrflag;
	}
	public void setVdrcrflag(Vector vdrcrflag) {
		Vdrcrflag = vdrcrflag;
	}
	public Vector getVgross_trans_inr() {
		return Vgross_trans_inr;
	}
	public void setVgross_trans_inr(Vector vgross_trans_inr) {
		Vgross_trans_inr = vgross_trans_inr;
	}
	public Vector getVtds_tax_percent() {
		return Vtds_tax_percent;
	}
	public void setVtds_tax_percent(Vector vtds_tax_percent) {
		Vtds_tax_percent = vtds_tax_percent;
	}
	public Vector getVtds_tax_amt() {
		return Vtds_tax_amt;
	}
	public void setVtds_tax_amt(Vector vtds_tax_amt) {
		Vtds_tax_amt = vtds_tax_amt;
	}
	public Vector getVgross_trans() {
		return Vgross_trans;
	}
	public void setVgross_trans(Vector vgross_trans) {
		Vgross_trans = vgross_trans;
	}
	public Vector getVPAY_FLAG() {
		return VPAY_FLAG;
	}
	public void setVPAY_FLAG(Vector vPAY_FLAG) {
		VPAY_FLAG = vPAY_FLAG;
	}
	public Vector getVsup_state_cd() {
		return Vsup_state_cd;
	}
	public void setVsup_state_cd(Vector vsup_state_cd) {
		Vsup_state_cd = vsup_state_cd;
	}
	public Vector getVpay_actual_recv_amt() {
		return Vpay_actual_recv_amt;
	}
	public void setVpay_actual_recv_amt(Vector vpay_actual_recv_amt) {
		Vpay_actual_recv_amt = vpay_actual_recv_amt;
	}
	public Vector getVpay_recv_dt() {
		return Vpay_recv_dt;
	}
	public void setVpay_recv_dt(Vector vpay_recv_dt) {
		Vpay_recv_dt = vpay_recv_dt;
	}
	public Vector getVpay_remark() {
		return Vpay_remark;
	}
	public void setVpay_remark(Vector vpay_remark) {
		Vpay_remark = vpay_remark;
	}
	public Vector getVpay_short_recv_amt() {
		return Vpay_short_recv_amt;
	}
	public void setVpay_short_recv_amt(Vector vpay_short_recv_amt) {
		Vpay_short_recv_amt = vpay_short_recv_amt;
	}
	public Vector getVpayflag() {
		return Vpayflag;
	}
	public void setVpayflag(Vector vpayflag) {
		Vpayflag = vpayflag;
	}
	public Vector getVpay_update_dt() {
		return Vpay_update_dt;
	}
	public void setVpay_update_dt(Vector vpay_update_dt) {
		Vpay_update_dt = vpay_update_dt;
	}
	public Vector getVpay_update_cnt() {
		return Vpay_update_cnt;
	}
	public void setVpay_update_cnt(Vector vpay_update_cnt) {
		Vpay_update_cnt = vpay_update_cnt;
	}
	public Vector getVtottax() {
		return Vtottax;
	}
	public void setVtottax(Vector vtottax) {
		Vtottax = vtottax;
	}
	public Vector getVfgsa_no() {
		return Vfgsa_no;
	}
	public void setVfgsa_no(Vector vfgsa_no) {
		Vfgsa_no = vfgsa_no;
	}
	public Vector getVfgsa_rev_no() {
		return Vfgsa_rev_no;
	}
	public void setVfgsa_rev_no(Vector vfgsa_rev_no) {
		Vfgsa_rev_no = vfgsa_rev_no;
	}
	public Vector getVsn_no() {
		return Vsn_no;
	}
	public void setVsn_no(Vector vsn_no) {
		Vsn_no = vsn_no;
	}
	public Vector getVsn_rev_no() {
		return Vsn_rev_no;
	}
	public void setVsn_rev_no(Vector vsn_rev_no) {
		Vsn_rev_no = vsn_rev_no;
	}
	public Vector getVplant_seq_no() {
		return Vplant_seq_no;
	}
	public void setVplant_seq_no(Vector vplant_seq_no) {
		Vplant_seq_no = vplant_seq_no;
	}
	public Vector getVplant_seq_nm() {
		return Vplant_seq_nm;
	}
	public void setVplant_seq_nm(Vector vplant_seq_nm) {
		Vplant_seq_nm = vplant_seq_nm;
	}
	public Vector getVperiod_st_dt() {
		return Vperiod_st_dt;
	}
	public void setVperiod_st_dt(Vector vperiod_st_dt) {
		Vperiod_st_dt = vperiod_st_dt;
	}
	public Vector getVperiod_end_dt() {
		return Vperiod_end_dt;
	}
	public void setVperiod_end_dt(Vector vperiod_end_dt) {
		Vperiod_end_dt = vperiod_end_dt;
	}
	public Vector getVexchg_rate_cd() {
		return Vexchg_rate_cd;
	}
	public void setVexchg_rate_cd(Vector vexchg_rate_cd) {
		Vexchg_rate_cd = vexchg_rate_cd;
	}
	public Vector getVexchg_rate_type() {
		return Vexchg_rate_type;
	}
	public void setVexchg_rate_type(Vector vexchg_rate_type) {
		Vexchg_rate_type = vexchg_rate_type;
	}
	public Vector getVinv_adj_flag() {
		return Vinv_adj_flag;
	}
	public void setVinv_adj_flag(Vector vinv_adj_flag) {
		Vinv_adj_flag = vinv_adj_flag;
	}
	public Vector getVtax_adj_flag() {
		return Vtax_adj_flag;
	}
	public void setVtax_adj_flag(Vector vtax_adj_flag) {
		Vtax_adj_flag = vtax_adj_flag;
	}
	public Vector getVinv_gen_by_cd() {
		return Vinv_gen_by_cd;
	}
	public void setVinv_gen_by_cd(Vector vinv_gen_by_cd) {
		Vinv_gen_by_cd = vinv_gen_by_cd;
	}
	public Vector getVinv_gen_by_emailid() {
		return Vinv_gen_by_emailid;
	}
	public void setVinv_gen_by_emailid(Vector vinv_gen_by_emailid) {
		Vinv_gen_by_emailid = vinv_gen_by_emailid;
	}
	public Vector getVinv_gen_by_nm() {
		return Vinv_gen_by_nm;
	}
	public void setVinv_gen_by_nm(Vector vinv_gen_by_nm) {
		Vinv_gen_by_nm = vinv_gen_by_nm;
	}
	public Vector getVpdf_inv_dtl() {
		return Vpdf_inv_dtl;
	}
	public void setVpdf_inv_dtl(Vector vpdf_inv_dtl) {
		Vpdf_inv_dtl = vpdf_inv_dtl;
	}
	public Vector getVprint_by_cd() {
		return Vprint_by_cd;
	}
	public void setVprint_by_cd(Vector vprint_by_cd) {
		Vprint_by_cd = vprint_by_cd;
	}
	public Vector getVprint_by_emailid() {
		return Vprint_by_emailid;
	}
	public void setVprint_by_emailid(Vector vprint_by_emailid) {
		Vprint_by_emailid = vprint_by_emailid;
	}
	public Vector getVprint_by_nm() {
		return Vprint_by_nm;
	}
	public void setVprint_by_nm(Vector vprint_by_nm) {
		Vprint_by_nm = vprint_by_nm;
	}
	public Vector getVtds_per() {
		return Vtds_per;
	}
	public void setVtds_per(Vector vtds_per) {
		Vtds_per = vtds_per;
	}
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
	public String getFormcd() {
		return formcd;
	}
	public void setFormcd(String formcd) {
		this.formcd = formcd;
	}
	
}