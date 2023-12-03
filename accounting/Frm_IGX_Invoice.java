package com.hlpl.hazira.fms7.accounting;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.poi.util.SystemOutLogger;

import com.hlpl.hazira.fms7.mail.mail;
import com.hlpl.hazira.fms7.util.RuntimeConf;
import com.hlpl.hazira.fms7.util.escapeSingleQuotes;

public class Frm_IGX_Invoice extends HttpServlet{

	public static Connection dbcon;
	
	public String servletName = "Frm_Accounting";
	public String methodName = "";
	public String option = "";
	public String url = "";
	public String form_name = "";
	public String msg = "";
	
	static escapeSingleQuotes obj = new escapeSingleQuotes();
	
	NumberFormat nf = new DecimalFormat("###########0.00");
	NumberFormat nf2 = new DecimalFormat("##0.0000"); 
	NumberFormat nf4 = new DecimalFormat("##0.000");
	NumberFormat nff = new DecimalFormat("######,##0.00");
	NumberFormat nf5 = new DecimalFormat("############.##");
	
	private static String queryString = null;
	private static String queryString1 = null;
	private static String query = null;
	private static String queryString2 = null;
	private static String queryString3 = null;
	private static String queryString4 = null;
	private static Statement stmt = null;
	private static Statement stmt1 = null;
	private static Statement stmt2 = null;
	private static Statement stmt3 = null;
	private static Statement stmt4 = null;
	private ResultSet rset = null;
	private ResultSet rset1 = null;
	private ResultSet rset2 = null;
	private ResultSet rset3 = null;
	private ResultSet rset4 = null;
	
	public String write_permission = "N";
	public String delete_permission = "N";
	public String print_permission = "N";
	public String approve_permission = "N";
	public String audit_permission = "N";
	
	public String form_id = "0";
	public String form_nm = "";
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException
	{
		try
		{
			Context Context = new InitialContext();
			if(Context == null) 
			{
				throw new Exception("Boom - No Context");
			}
			
			Context envContext  = (Context)Context.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup(RuntimeConf.security_database);
			
			if(ds != null)
			{
				dbcon = ds.getConnection();
			}
			else
			{
				System.out.println("Data Source Not Found - Error In "+servletName+" Servlet !!!");
			}
			
			if(dbcon != null)
			{
				dbcon.setAutoCommit(false);
				stmt = dbcon.createStatement(); 
				stmt1 = dbcon.createStatement();
				stmt2 = dbcon.createStatement(); 
			}
			
			option=req.getParameter("option")==null?"":req.getParameter("option");
			
			write_permission = req.getParameter("write_permission")==null?"N":req.getParameter("write_permission");
			delete_permission = req.getParameter("delete_permission")==null?"N":req.getParameter("delete_permission");
			print_permission = req.getParameter("print_permission")==null?"N":req.getParameter("print_permission");
			approve_permission = req.getParameter("approve_permission")==null?"N":req.getParameter("approve_permission");
			audit_permission = req.getParameter("audit_permission")==null?"N":req.getParameter("audit_permission");
			
			form_id = req.getParameter("form_id")==null?"0":req.getParameter("form_id");
			form_nm = req.getParameter("form_nm")==null?"":req.getParameter("form_nm");
			
			if(option.equalsIgnoreCase("InsertIGXPaymentDetails"))
			{
				InsertIGXPaymentDetails(req);
			}else if(option.equalsIgnoreCase("UpdateInvoicePaymentDetails")) {
				updateInvoicePaymentDetails(req,res);
			}
			
			
		}		   
		catch(Exception e)
		{
			System.out.println("Exception In "+servletName+" - (doPost())-(CONNECTION) : "+e.getMessage());
			e.printStackTrace();
			url="../home/ExceptionHandler.jsp?excp="+e.getMessage()+"&modulename=sales_invoice&formname="+form_name;
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
					System.out.println("rset is not close "+e.getMessage());
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
					System.out.println("rset1 is not close "+e.getMessage());
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
					System.out.println("rset2 is not close "+e.getMessage());
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
					System.out.println("rset3 is not close "+e.getMessage());
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
					System.out.println("rset4 is not close "+e.getMessage());
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
					System.out.println("stmt is not close "+e.getMessage());
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
					System.out.println("stmt1 is not close "+e.getMessage());
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
					System.out.println("stmt2 is not close "+e.getMessage());
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
					System.out.println("stmt3 is not close "+e.getMessage());
				}
			}
			if(stmt4 != null)
			{
				try
				{
					stmt4.close();
				}
				catch(SQLException e)
				{
					System.out.println("stmt4 is not close "+e.getMessage());
				}
			}
			if(dbcon != null)
			{
				try
				{
					dbcon.close();
				}
				catch(SQLException e)
				{
					System.out.println("dbcon is not close "+e.getMessage());
				}
			}
		}
		res.sendRedirect(url);
	}


	private void updateInvoicePaymentDetails(HttpServletRequest request, HttpServletResponse res)throws SQLException,IOException {
		

		String month=request.getParameter("month")==null?"":request.getParameter("month");
		String year=request.getParameter("year")==null?"":request.getParameter("year");
		String to_month=request.getParameter("to_month")==null?"":request.getParameter("to_month");
		String to_year=request.getParameter("to_year")==null?"":request.getParameter("to_year");
		String cust_name=request.getParameter("cust_name")==null?"":request.getParameter("cust_name");
		String cust_cd=request.getParameter("cust_cd")==null?"":request.getParameter("cust_cd");
		String invstatus=request.getParameter("invstatus")==null?"":request.getParameter("invstatus");
		String segment=request.getParameter("segment")==null?"":request.getParameter("segment");
		String customer_abbr=request.getParameter("cust_name")==null?"":request.getParameter("cust_name");
		
		String formcd=request.getParameter("formcd")==null?"":request.getParameter("formcd");
		String formname=request.getParameter("formname")==null?"":request.getParameter("formname");
		String mid=request.getRemoteHost();


		try
		{
			String tcd[]=new String[3];
			String tamt[]=new String[3];
//			////System.out.println("===in frm===");
			HttpSession session = request.getSession();
			String user_cd = (String)session.getAttribute("user_cd")==null?"0":(String)session.getAttribute("user_cd");		
			//String month=request.getParameter("month")==null?"":request.getParameter("month");
			String chk[]=request.getParameterValues("chkFlg");
			String actual_rec[]=request.getParameterValues("act_recv");
			String payment_rec_date[]=request.getParameterValues("payment_rec_date");
			String remark[]=request.getParameterValues("remark");
			String hlplinvseqno[]=request.getParameterValues("VIinv_hlpl_no");
			String financialyear[]=request.getParameterValues("VIinv_fin_yr");
			String contracttype[]=request.getParameterValues("contracttype");
			String flgall[]=request.getParameterValues("flgall");
			
			String inv_gen_by_emailid[]=request.getParameterValues("inv_gen_by_emailid");
			String checked_by_emailid[]=request.getParameterValues("checked_by_emailid");
			//String approved_by_emailid[]=request.getParameterValues("approved_by_emailid");
			
			String print_by_emailid[]=request.getParameterValues("print_by_emailid");
			String pdf_inv_dtl[]=request.getParameterValues("pdf_inv_dtl");
			String tds_per[]=request.getParameterValues("tds_per");
			String tds_per_tax[]=request.getParameterValues("tax_tds_perc");
			String tds_val_tax[]=request.getParameterValues("tax_tds_val");
			String short_rec[]=request.getParameterValues("short_rec");
			String tds_val[]=request.getParameterValues("tds_val");
			String mailflag_inv[]=request.getParameterValues("mailflag_inv");
			String VIinv_no [] = request.getParameterValues("VIinv_no");
			String flag_type []  = request.getParameterValues("VIinv_flag");
			String sup_state_cd []  = request.getParameterValues("VIinv_supp_state_cd");
			String VIcustomer_cd[] = request.getParameterValues("VIcustomer_cd");
			
			String mailVcustomer_abbr[]=request.getParameterValues("mailVcustomer_abbr");
//			String mailVhlpl_inv_seq[]=request.getParameterValues("mailVhlpl_inv_seq");
			String mailVinv_dt[]=request.getParameterValues("mailVinv_dt");
			String mailVsales_value[]=request.getParameterValues("mailVsales_value");
			String mailVtaxcd[]=request.getParameterValues("mailVtaxcd");
			String mailVtaxamt[]=request.getParameterValues("mailVtaxamt");
			String mailVtaxcnt[]=request.getParameterValues("mailVtaxcnt");
			String mailVinv_value[]=request.getParameterValues("net_recv");
			String mailVdue_dt[]=request.getParameterValues("mailVdue_dt");
			String netrec[]=request.getParameterValues("net_recv");
			String adjust_balc_hid[]=request.getParameterValues("adjust_balc_hid");
			String adjusted_amt_hid[]=request.getParameterValues("adjusted_amt_hid");
			String hold_amt_hid[]=request.getParameterValues("hold_amt_hid");
			String agree_base[]=request.getParameterValues("Vagree_base");
			String cust_cd_hid[]=request.getParameterValues("cust_cd_hid");
			String new_inv_seq_no [] = request.getParameterValues("VIinv_no");
			String tax_tds_perc [] = request.getParameterValues("tax_tds_perc");
			String pay_dt [] = request.getParameterValues("pay_dt");
//			String payment_rec_date[]=request.getParameterValues("payment_rec_date");
//			String tds_val_tax[]=request.getParameterValues("tax_tds_val");
			
			HashMap m = new HashMap();
			Vector v=new Vector();
			
			String adjust_flag="";
			int rev_no=1;
			//--Mail--//
			
			String logged_on_user_nm="";
			String logged_on_user_emailid="";
			
			queryString2="select emp_nm,email_id from hr_emp_mst where emp_cd='"+user_cd+"'";
			rset2=stmt2.executeQuery(queryString2);
			while(rset2.next())
			{
				logged_on_user_nm=rset2.getString(1);
				logged_on_user_emailid=rset2.getString(2);
			}
			
			Timestamp ts = new Timestamp(System.currentTimeMillis());
	        String tsString = ts.toString().substring(0, 19);
	        String tsDate = tsString.substring(0, 10);
	        String time = tsString.substring(11, 19);
	        String tds="";
	        
	        int updCnt_1 = 0 , updCnt = 0 ;
			
			for(int i=0;i<hlplinvseqno.length;i++)
			{
				if(chk[i].equalsIgnoreCase("Y")) {
					int updcnt=0;
					queryString1="select pay_update_cnt from fms7_invoice_mst where hlpl_inv_seq_no='"+hlplinvseqno[i]+"' and financial_year='"+financialyear[i]+"' and contract_type='K' ";
					rset1=stmt1.executeQuery(queryString1);
					while(rset1.next())
					{
						if(rset1.getString(1)==null)
						{
							updcnt=1;
						}
						else
						{
							updcnt=Integer.parseInt(rset1.getString(1))+1;
						}
					}
					
					double pay_recv=0;
					queryString1="select nvl(PAY_RECV_AMT,'0') from fms7_invoice_mst where "
							+ "hlpl_inv_seq_no='"+hlplinvseqno[i]+"' and financial_year='"+financialyear[i]+"' "
							+ "and contract_type='K' and flag='"+flag_type[i]+"' "
							+ " and sup_state_code='"+sup_state_cd[i]+"' and customer_cd='"+VIcustomer_cd[i]+"'";
//					System.out.println("check exist------"+queryString1);
					rset1=stmt1.executeQuery(queryString1);
					if(rset1.next()){
						pay_recv=rset1.getDouble(1)+Double.parseDouble(actual_rec[i]);
					}else {
						pay_recv = Double.parseDouble(actual_rec[i]);
					}
					queryString="update fms7_invoice_mst set pay_recv_amt='"+pay_recv+"',pay_update_dt=to_date(sysdate,'dd/mm/yy HH24:MI'),pay_remark='"+remark[i]+"',pay_update_by='"+user_cd+"',pay_update_cnt='"+updcnt+"',tds_tax_percent='"+tds_per_tax[i]+"',tds_tax_amt='"+tds_val_tax[i]+"' where hlpl_inv_seq_no='"+hlplinvseqno[i]+"' and financial_year='"+financialyear[i]+"' and contract_type='K' ";
//					System.out.println("---update payment query---"+queryString);
					updCnt = stmt.executeUpdate(queryString);
					
					
					String no = new_inv_seq_no[i];
					
					
					String ids="";
					
					String remarks="inserted-->K/"+no+"-->"+ids;
					String q1="insert into SEC_LOG_DETAILS (LOG_DT,LOG_TIME,LOG_UID,REMARKS,EMP_CD,FORM_CD,FORM_NAME,LOG_MACH_ID) " +
						 " VALUES(to_date(sysdate,'dd/mm/yy HH24:MI'),'"+time+"','"+logged_on_user_nm+"','"+remarks+"','"+user_cd+"','"+formcd+"','"+formname+"','"+mid+"') ";
//					System.out.println("q1*****"+q1);
					stmt1.executeUpdate(q1);
					
				}
			}
			
			if(updCnt > 0 && updCnt_1 > 0) 
			{
				dbcon.commit();
			} 
			else
			{
				dbcon.rollback();
			}	

			msg="Data Updated Successfully..!";
		}
		catch(Exception e)
		{
			try {
				dbcon.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			msg="Data Not Updated..!";
		}
		url = "../sales_invoice/frm_update_invoice_payment_dtl.jsp?year="+year+"&month="+month+"&to_year="+to_year+"&to_month="+to_month+"&segment="+segment+"&invstatus="+invstatus+"&cust_name="+cust_name+"&customer_cd="+cust_cd+"&msg="+msg+"&btnflag=Y&customer_abbr="+customer_abbr;
	}


	private void InsertIGXPaymentDetails(HttpServletRequest req)throws SQLException,IOException {
		
		HttpSession session = req.getSession();
		String user_cd = (String)session.getAttribute("user_cd")==null?"0":(String)session.getAttribute("user_cd");
		String month=req.getParameter("month")==null?"":req.getParameter("month");
		String year=req.getParameter("year")==null?"":req.getParameter("year");
		String to_month=req.getParameter("to_month")==null?"":req.getParameter("to_month");
		String to_year=req.getParameter("to_year")==null?"":req.getParameter("to_year");
		String cust_name=req.getParameter("cust_name")==null?"":req.getParameter("cust_name");
		String cust_cd=req.getParameter("cust_cd")==null?"":req.getParameter("cust_cd");
		String invstatus=req.getParameter("invstatus")==null?"":req.getParameter("invstatus");
		String segment=req.getParameter("segment")==null?"":req.getParameter("segment");
		String customer_abbr=req.getParameter("cust_name")==null?"":req.getParameter("cust_name");
		
		String formcd=req.getParameter("formcd")==null?"":req.getParameter("formcd");
		String formname=req.getParameter("formname")==null?"":req.getParameter("formname");
		String mid=req.getRemoteHost();

		
		String VIcustomer_cd[] = req.getParameterValues("VIcustomer_cd");
		String cont_no[] = req.getParameterValues("VIsn_no");
		String cont_rev_no[] = req.getParameterValues("VIsn_rev_no");
		String plant_seq_no[] = req.getParameterValues("VIplant_seq_no");
		String VIsub_rec_cnt[] = req.getParameterValues("VIsub_rec_cnt");
		String chkFlg [] = req.getParameterValues("chkFlg");
		String new_inv_seq_no [] = req.getParameterValues("VIinv_no");
		String actual_rec [] = req.getParameterValues("act_recv");
		String pay_dt [] = req.getParameterValues("pay_dt");
		String remark [] = req.getParameterValues("remark");
		String mappid_hid [] = req.getParameterValues("mappid");
		String short_rec [] = req.getParameterValues("short_recv");
		String tax_tds_perc [] = req.getParameterValues("tax_tds_perc");
		String tax_tds_val [] = req.getParameterValues("tax_tds_val");
		String hlplinvseqno []  = req.getParameterValues("VIinv_hlpl_no");
		String financialyear []  = req.getParameterValues("VIinv_fin_yr");
		String flag_type []  = req.getParameterValues("VIinv_flag");
		String sup_state_cd []  = req.getParameterValues("VIinv_supp_state_cd");
		String sub_sum_act_recv [] = req.getParameterValues("VIinv_sub_sum_act_recv");
//		String VIinv_view_flag[] = req.getParameterValues("VIinv_view_flag");
		
		//--Mail--//
		String mailVcustomer_abbr[]=req.getParameterValues("mailVcustomer_abbr");
		String mailVinv_dt[]=req.getParameterValues("mailVinv_dt");
		String mailVsales_value[]=req.getParameterValues("net_recv_email");
		String mailVinv_value[]=req.getParameterValues("net_recv");
		String mailVdue_dt[]=req.getParameterValues("mailVdue_dt");
		String netrec[]=req.getParameterValues("net_recv");
		String tds_val[]=req.getParameterValues("tds_val");
		String tds_perc[]=req.getParameterValues("gross_tds_perc");
		String inv_gen_by_emailid[]=req.getParameterValues("inv_gen_by_emailid");
		String checked_by_emailid[]=req.getParameterValues("checked_by_emailid");
		String print_by_emailid[]=req.getParameterValues("print_by_emailid");
		
		HashMap m = new HashMap();
		Vector v=new Vector();
		
		String adjust_flag="";
		int rev_no=1;
		//--Mail--//
		
		String logged_on_user_nm="";
		String logged_on_user_emailid="";
		
		queryString2="select emp_nm,email_id from hr_emp_mst where emp_cd='"+user_cd+"'";
		rset2=stmt2.executeQuery(queryString2);
		while(rset2.next())
		{
			logged_on_user_nm=rset2.getString(1);
			logged_on_user_emailid=rset2.getString(2);
		}
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsString = ts.toString().substring(0, 19);
        String tsDate = tsString.substring(0, 10);
        String time = tsString.substring(11, 19);
        String tds="";
        HashSet<String> allocUniqEmail = new HashSet<String>();
        Vector allocData = new Vector ();
        allocUniqEmail.add(logged_on_user_emailid);
        
		int insSubCnt= 0,insCnt = 0, updCnt = 0 ;
		try {
			
			for(int i = 0 ; i < VIcustomer_cd.length ; i++) {
//				if(VIinv_view_flag[i].equals("Y")) {
					if(chkFlg[i].equals("Y")) {
						
						queryString="select max(rev_no) from FMS8_PAY_RECV_DTL where new_inv_seq_no='"+new_inv_seq_no[i]+"' and contract_type='K' and commodity_type='RLNG' ";
						rset=stmt.executeQuery(queryString);
						if(rset.next()){
							rev_no=rset.getInt(1)+1;
						}else{
							rev_no=1;
						}
						
						queryString1="select hlpl_inv_seq_no,nvl(PAY_RECV_AMT,0) from fms7_invoice_mst where "
								+ "hlpl_inv_seq_no='"+hlplinvseqno[i]+"' and financial_year='"+financialyear[i]+"' "
								+ "and contract_type='K' and flag='"+flag_type[i]+"' "
								+ " and sup_state_code='"+sup_state_cd[i]+"' and customer_cd='"+VIcustomer_cd[i]+"'";
//						System.out.println("check exist------"+queryString1);
						rset1=stmt1.executeQuery(queryString1);
						if(rset1.next()){
							double pay_recv=0;
							if(rset1.getDouble(2) > 0 ) {
								pay_recv=rset1.getDouble(2)+Double.parseDouble(actual_rec[i]);
							}else {
								pay_recv=rset1.getDouble(2)+Double.parseDouble(actual_rec[i]) + Double.parseDouble(sub_sum_act_recv[i]);
							}
//							System.out.println("pay_recv------"+pay_recv);
							
							queryString2="update fms7_invoice_mst set pay_recv_amt='"+pay_recv+"',pay_recv_dt=to_date('"+pay_dt[i]+"','dd/mm/yy'),"
									+ " pay_remark='"+remark[i]+"',pay_insert_by='"+user_cd+"',"
									+ " pay_insert_dt=to_date(sysdate,'dd/mm/yy HH24:MI'),"
									+ " tds_tax_percent='"+tax_tds_perc[i]+"',tds_tax_amt='"+tax_tds_val[i]+"',"
									+ " gross_tds_perc = '"+tds_perc[i]+"',gross_tds_amt = '"+tds_val[i]+"'"
									+ " where hlpl_inv_seq_no='"+hlplinvseqno[i]+"' and financial_year='"+financialyear[i]+"' "
									+ " and contract_type='K' and flag='"+flag_type[i]+"' "
									+ " and sup_state_code='"+sup_state_cd[i]+"' and customer_cd='"+VIcustomer_cd[i]+"'";
//							System.out.println("UPDATE queryString2------"+queryString2);
							updCnt = stmt2.executeUpdate(queryString2);
						}
						double act_recv = Double.parseDouble(sub_sum_act_recv[i]) + Double.parseDouble(actual_rec[i]);
//						System.out.println("act_recv_____"+act_recv);
						queryString="INSERT INTO FMS8_PAY_RECV_DTL (NEW_INV_SEQ_NO,COMMODITY_TYPE,CONTRACT_TYPE,PAY_RECV_AMT,"
								+ "PAY_RECV_DT,PAY_REMARK,PAY_INSERT_BY,REV_NO,MAPPING_ID,SHORT_RECV_AMT,ADJUSTED_AMT,ADJUST_BALANCE_AMT,ADJUST_FLAG,HOLD_AMOUNT) VALUES "
								+ "('"+new_inv_seq_no[i]+"','RLNG','K','"+act_recv+"',"
								+ "to_date(sysdate,'dd/mm/yy HH24:MI'),'"+remark[i]+"','"+user_cd+"','"+rev_no+"','"+mappid_hid[i]+"',"
								+ "'"+short_rec[i].replaceAll(",","")+"','0','0','','0')";
//						System.out.println("INSERT----"+queryString);
						insCnt = stmt.executeUpdate(queryString);
						
						String no = new_inv_seq_no[i];
//						String temp_no = mailVhlpl_inv_seq[i];
						String temp_no = "";
						if(!new_inv_seq_no[i].equals("")) {
							temp_no = new_inv_seq_no[i];
						}
						/*System.out.println("mailVcustomer_abbr[i]--->>"+mailVcustomer_abbr.length);  
						System.out.println("mailVinv_dt[i]--->>"+mailVinv_dt.length);  
						System.out.println("mailVsales_value[i]--->>"+mailVsales_value.length);  
						System.out.println("mailVinv_value[i]--->>"+mailVinv_value.length); 
						System.out.println("mailVdue_dt[i]--->>"+mailVdue_dt.length); 
						System.out.println("actual_rec[i]--->>"+actual_rec.length); 
						System.out.println("pay_dt[i]--->>"+pay_dt.length); 
						*/
						String wholeInvoice=mailVcustomer_abbr[i]+"!@#"+temp_no+"!@#"+mailVinv_dt[i]+"!@#"+mailVsales_value[i]+"!@#"+mailVinv_value[i];
					    	   wholeInvoice+="!@#"+mailVdue_dt[i]+"!@#"+actual_rec[i]+"!@#"+pay_dt[i]+"!@#";
					    if(!segment.equalsIgnoreCase("Interest")){
					    	if((!tax_tds_perc[i].equals(""))){ 	   
						    //if(!segment.equalsIgnoreCase("SALES") && (!segment.equalsIgnoreCase("LOA")) && (!segment.equalsIgnoreCase("Interest"))  && (!segment.equalsIgnoreCase("SDLNG"))  && (!segment.equalsIgnoreCase("LDLNG"))){
						    	String total_tds=nf5.format(Double.parseDouble(tds_val[i])+Double.parseDouble(tax_tds_perc[i]));
						    	wholeInvoice+= netrec[i]+"!@#"+total_tds;
//						    }
//						    else{
//						    	wholeInvoice+= mailVinv_value[i]+"!@#-";
//						    }
						    }else{
						    	//if(!segment.equalsIgnoreCase("SALES") && (!segment.equalsIgnoreCase("LOA")) && (!segment.equalsIgnoreCase("Interest"))  && (!segment.equalsIgnoreCase("SDLNG"))  && (!segment.equalsIgnoreCase("LDLNG")))
						    	if(segment.equalsIgnoreCase("IGX"))
							    	wholeInvoice+= netrec[i]+"!@#"+tds_val[i]+"!@#"+tds_perc[i];
							    else
							    	wholeInvoice+= mailVinv_value[i]+"!@#-";
						    }
					    }else{
					    	wholeInvoice+= mailVinv_value[i]+"!@#-";
					    }
						String ids="";
						ids+=inv_gen_by_emailid[i];
						if(!inv_gen_by_emailid[i].equals(checked_by_emailid[i]))
							ids+="#@#"+checked_by_emailid[i];
						if(!inv_gen_by_emailid[i].equals(print_by_emailid[i]) 
								&& !checked_by_emailid[i].equals(print_by_emailid[i]))
							ids+="#@#"+print_by_emailid[i];
						if(!inv_gen_by_emailid[i].equals(logged_on_user_emailid) 
								&& !checked_by_emailid[i].equals(logged_on_user_emailid) 
								&& !print_by_emailid[i].equals(logged_on_user_emailid))
							ids+="#@#"+logged_on_user_emailid;
						if(m.containsKey(ids)) {
							String d= (String) m.get(ids);
							d+="@@@"+wholeInvoice;
							m.put(ids,d);
						} else {
							v.addElement(ids);
							m.put(ids,wholeInvoice);
						}
						
						String remarks="inserted-->K/"+no+"-->"+ids;
						String q1="insert into SEC_LOG_DETAILS (LOG_DT,LOG_TIME,LOG_UID,REMARKS,EMP_CD,FORM_CD,FORM_NAME,LOG_MACH_ID) " +
							 " VALUES(to_date(sysdate,'dd/mm/yy HH24:MI'),'"+time+"','"+logged_on_user_nm+"','"+remarks+"','"+user_cd+"','"+formcd+"','"+formname+"','"+mid+"') ";
//						System.out.println("q1*****"+q1);
						stmt1.executeUpdate(q1);
					}
					
					for(int j = 0 ; j < Integer.parseInt(VIsub_rec_cnt[i]+"") ; j++) {
//						System.out.println("subChkFlg*****"+i+"--"+j);
						String subChkFlg = req.getParameter("subChkFlg"+i+""+j) ==null?"":req.getParameter("subChkFlg"+i+""+j);
						String subGasDt = req.getParameter("subGasDt"+i+""+j);
						String subNomRevNo = req.getParameter("subNomRevNo"+i+""+j);
						String gross_tds_perc = req.getParameter("sub_tds_perc"+i+""+j);
						String gross_tds_val = req.getParameter("sub_tds_val"+i+""+j);
						String net_recv = req.getParameter("sub_net_recv"+i+""+j);
						String sub_act_recv = req.getParameter("sub_act_recv"+i+""+j);
						String sub_short_recv = req.getParameter("sub_short_recv"+i+""+j);
						String sub_pay_dt = req.getParameter("sub_pay_dt"+i+""+j);
						String sub_remark = req.getParameter("sub_remark"+i+""+j) ==null?"-":req.getParameter("sub_remark"+i+""+j);
						String sub_due_dt = req.getParameter("subDueDt"+i+""+j);
						String sub_alloc_qty = req.getParameter("subAllocQty"+i+""+j);
						String sub_sales_rt = req.getParameter("subSalesRt"+i+""+j);
						String sub_gross_amt = req.getParameter("sub_gross_amt"+i+""+j);
						String sub_trans_amt = req.getParameter("sub_trans_amt"+i+""+j);
						String sub_trans_cgst = req.getParameter("sub_trans_cgst"+i+""+j);
						String sub_trans_sgst = req.getParameter("sub_trans_sgst"+i+""+j);
						String sub_over_due_day = req.getParameter("sub_overDue_day"+i+""+j);
						String sub_tax_amt = req.getParameter("subTaxAmt"+i+""+j);
						String sub_tax_str = req.getParameter("subTaxStr"+i+""+j);
						String sub_tax_tds_perc = req.getParameter("sub_tax_tds_perc"+i+""+j);
						String sub_tax_tds_val = req.getParameter("sub_tax_tds_val"+i+""+j);
						
//						System.out.println("sub_tax_str*****"+sub_tax_str);
						  if(subChkFlg.equalsIgnoreCase("Y")) { //
	//						  System.out.println("VIcustomer_cd[]----"+VIcustomer_cd[i]);
							  int subMaxRev = 0;
							  String maxRevSql = "select nvl(max(rev_no+1),0) from FMS10_IGX_PAYMENT_DTL"
								+ " where customer_cd = '"+VIcustomer_cd[i]+"'"
								+ " and cont_no = '"+cont_no[i]+"' "
								+ " and cont_rev_no = '"+cont_rev_no[i]+"'"
								+ " and plant_seq_no = '"+plant_seq_no[i]+"'" +
								  " and gas_dt = to_date('"+subGasDt+"','dd/mm/yyyy')" +
								  " and nom_rev_no = '"+subNomRevNo+"'"
								+ " and contract_type = 'K'";
//							  System.out.println("maxRevSql---"+maxRevSql);
							  rset = stmt.executeQuery(maxRevSql);
							  if(rset.next()) {
								  subMaxRev = rset.getInt(1);
							  }
							  
//							  System.out.println("subMaxRev---"+subMaxRev);
							  String subInsSql = "insert into FMS10_IGX_PAYMENT_DTL (CUSTOMER_CD,CONT_NO,CONT_REV_NO,PLANT_SEQ_NO,GAS_DT,NOM_REV_NO,CONTRACT_TYPE,REV_NO,"
							  		+ " GROSS_TDS_PERC,GROSS_TDS_AMT,NET_RECV,SHORT_RECV,ACTUAL_RECV,PAYMENT_DT,REMARK,ENT_BY,ENT_DT,TAX_TDS_PERC,TAX_TDS_AMT)"
							  		+ " VALUES (?,?,?,?,to_date('"+subGasDt+"','dd/mm/yyyy'),?,?,?,?,?,?,?,?,to_date('"+sub_pay_dt+"','dd/mm/yyyy'),?,?,sysdate,'"+sub_tax_tds_perc+"','"+sub_tax_tds_val+"')";
							  PreparedStatement ps = dbcon.prepareStatement(subInsSql);
							  ps.setString(1, VIcustomer_cd[i]);
							  ps.setString(2, cont_no[i]);
							  ps.setString(3, cont_rev_no[i]);
							  ps.setString(4, plant_seq_no[i]);
							  ps.setString(5, subNomRevNo);
							  ps.setString(6, "K");
							  ps.setInt(7, subMaxRev);
							  ps.setString(8, gross_tds_perc);
							  ps.setString(9, gross_tds_val);
							  ps.setString(10, net_recv);
							  ps.setString(11, sub_short_recv);
							  ps.setString(12, sub_act_recv);
							  ps.setString(13, sub_remark);
							  ps.setString(14, user_cd);
							 
							  insSubCnt = ps.executeUpdate();
							  						 
	//						  System.out.println("insCnt****"+insCnt);
							  
						/* to fetching Buyer,Seller nomination submitted user code */
							  String buySql = "select distinct(Y_VAL) from("
							  			+ "select a.emp_cd as X_VAL,nvl(c.email_id,'')  as Y_VAL from FMS7_DAILY_BUYER_NOM_DTL a,HR_EMP_MST c"
										+ " where a.customer_cd = '"+VIcustomer_cd[i]+"'"
										+ " and a.sn_no = '"+cont_no[i]+"' "
										+ " and a.sn_rev_no = '"+cont_rev_no[i]+"'"
										+ " and a.plant_seq_no = '"+plant_seq_no[i]+"'" +
										  " and a.gas_dt = to_date('"+subGasDt+"','dd/mm/yyyy')" 
										+ " and a.contract_type = 'K' "
										+ " and a.nom_rev_no = (select max(nom_rev_no) from FMS7_DAILY_BUYER_NOM_DTL b"
											+ " where b.customer_cd = a.customer_cd"
											+ " and b.sn_no = a.sn_no "
											+ " and b.plant_seq_no = a.plant_seq_no"
											+ " and b.gas_dt = a.gas_dt"
											+ " and b.contract_type = 'K')"
										+ " and a.emp_cd != '"+user_cd+"' "
										+ " and a.emp_cd = c.emp_cd "
									+ " UNION ALL "
										+ " select a.emp_cd as X_VAL,nvl(c.email_id,'')  as Y_VAL from FMS7_DAILY_SELLER_NOM_DTL a,HR_EMP_MST c"
										+ " where a.customer_cd = '"+VIcustomer_cd[i]+"'"
										+ " and a.sn_no = '"+cont_no[i]+"' "
										+ " and a.sn_rev_no = '"+cont_rev_no[i]+"'"
										+ " and a.plant_seq_no = '"+plant_seq_no[i]+"'"
										+ " and a.gas_dt = to_date('"+subGasDt+"','dd/mm/yyyy')" 
										+ " and a.contract_type = 'K' "
										+ " and a.nom_rev_no = (select max(nom_rev_no) from FMS7_DAILY_SELLER_NOM_DTL b"
											+ " where b.customer_cd = a.customer_cd"
											+ " and b.sn_no = a.sn_no "
											+ " and b.plant_seq_no = a.plant_seq_no"
											+ " and b.gas_dt = a.gas_dt"
											+ " and b.contract_type = 'K')"
										+ " and a.emp_cd != '"+user_cd+"' "
										+ " and a.emp_cd = c.emp_cd )";
//							  System.out.println("buySql---"+buySql);
							  rset = stmt.executeQuery(buySql);
							  while(rset.next()) {
								  if(!rset.getString(1).equalsIgnoreCase("")) {
									  allocUniqEmail.add(rset.getString(1) ==null?"":rset.getString(1));
								  }
							  }
							  if(sub_remark.equalsIgnoreCase("")) {
								  sub_remark = "-";
							  }
							  allocData.add(subGasDt+"!@#"+sub_due_dt+"!@#"+mailVcustomer_abbr[i]+"!@#"+sub_alloc_qty+
									  "!@#"+sub_sales_rt+"!@#"+sub_gross_amt+"!@#"+sub_trans_amt+"!@#"+sub_trans_cgst+"!@#"+sub_trans_sgst+
									  "!@#"+gross_tds_perc+"!@#"+gross_tds_val+"!@#"+net_recv+"!@#"+sub_act_recv+"!@#"+sub_pay_dt+
									  "!@#"+sub_over_due_day+"!@#"+sub_remark+"!@#"+sub_tax_amt+"!@#"+sub_tax_str+"!@#"+sub_tax_tds_perc+"!@#"+sub_tax_tds_val);
						  }
//					}
				}
			}
			
			HashMap hm =new HashMap();
			Vector v1=new Vector();
			for(int i=0;i<m.size();i++) {
				//////System.out.println(i+"--m-"+m.get(v.elementAt(i)));
				String value=m.get(v.elementAt(i)).toString();
				String sd[]=v.elementAt(i).toString().split("#@#");
				for(int j=0;j<sd.length;j++) {
					if(hm.containsKey(sd[j])) {
						String fd=hm.get(sd[j]).toString();
						hm.put(sd[j],fd+"@@@"+value);
					} else {
						v1.add(sd[j]);
						hm.put(sd[j],value);
					}
				}
			}
			mail m1=new mail();
			String mail_list_path="";
			
			File fsetup=new File(req.getRealPath("WEB-INF//classes//com//hlpl//hazira//fms7//sales_invoice//InvoiceSetup.txt"));
			mail_list_path=fsetup.getAbsolutePath();
			
			FileInputStream f1 = new FileInputStream(mail_list_path);
			DataInputStream in = new DataInputStream(f1);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String strline="",host="",from_mail="",dbline="",username="",encrypted="",password="",cc_list="";
		
			while((strline = br.readLine())!=null)
			{
				if(strline.startsWith("host"))
				{
					String  tmp[]=strline.split("host:");
					host = tmp[1].toString();
				}
				if(strline.startsWith("from"))
				{
					String  tmp[]=strline.split("from:");
					from_mail = tmp[1].toString();
				}
				if(strline.startsWith("pwd"))
				{
 					String  tmp[]=strline.split("pwd:");
                    encrypted = tmp[1].toString();
                    password=encrypted;
				}
				if(strline.startsWith("dbline"))
				{
					String  tmp[]=strline.split("dbline:");
					dbline = tmp[1].toString();
				}
				if(strline.startsWith("username"))
				{
					String  tmp[]=strline.split("username:");
					username = tmp[1].toString();
				}
				if(strline.startsWith("password"))
				{
                    String  tmp[]=strline.split("password:");
                    encrypted = tmp[1].toString();
                  
				}
				if(strline.startsWith("cc"))
				{
//					String  tmp[]=strline.split("cc:");
//					cc_list = tmp[1].toString();
                  
				}
			}
			
			int x_1 = 1 ; 
			/* for distribution email list */
			String to_mail_dist="";
			
			File distSetup=new File(req.getRealPath("sales_invoice//IGX_Recipient.txt"));
			if(distSetup.exists()) {
				to_mail_dist=distSetup.getAbsolutePath();
				
				FileInputStream f1_1 = new FileInputStream(to_mail_dist);
				DataInputStream in_1 = new DataInputStream(f1_1);
				BufferedReader br_1 = new BufferedReader(new InputStreamReader(in_1));
				
				String email_line = "";
				
				while((email_line = br_1.readLine())!=null)
				{
					if(email_line.startsWith("to"))
					{
						String  tmp[]=email_line.split("to:");
						if(!allocUniqEmail.contains(tmp[1])) {
							allocUniqEmail.add(tmp[1]);       
						}
					}
				}
//				System.out.println("allocUniqEmail***"+allocUniqEmail);
				String to_email_list = "";
				
				Iterator<String> itr=allocUniqEmail.iterator();  
				while(itr.hasNext()) {  
					String tempEmail = itr.next();
					to_email_list+=tempEmail+",";
	            }  
	//			System.out.println("to_email_list****"+to_email_list);
				if(to_email_list.length() > 0 && insSubCnt > 0) {
					
					to_email_list = to_email_list.substring(0,to_email_list.length()-1);
//					System.out.println("final to_email_list**"+to_email_list);
					String allocMailBody = "";
					allocMailBody="<h4>Dear Recipient(s),</h4><br>We have received payment for the following allocation(s):<br><br><table align='center' bordercolor='blue' width='100%' style='font-size: x-small;' border='1' cellpadding='0' cellspacing='0'>"
							+ "<tr bgcolor='green'>";
					allocMailBody+="<th align='center' width='5%' rowspan='2' colspan = '1'><font size='2' color='white'>ALLOCATION DATE</font></th>"
							+ " <th align='center' width='5%' rowspan='2' colspan = '1'><font size='2' color='white'>DUE DATE</font></th>"
							+ " <th align='center' width='5%' rowspan='2' colspan = '1'><font size='2' color='white'>NAME OF PARTY</font></th>"
							+ " <th align='center' width='5%' rowspan='2' colspan = '1'><font size='2' color='white'>ALLOCATION QUANTITY</font></th>"
							+ " <th align='center' width='5%' rowspan='2' colspan = '1'><font size='2' color='white'>SALES PRICE (INR)</font></th>"
							+ " <th align='center' width='5%' rowspan='2' colspan = '1'><font size='2' color='white'>SALES VALUE (INR)</font></th>"
							+ " <th align='center' width='15%' colspan = '3' rowspan='1'><font size='2' color='white'>TRANSACTION CHARGES (INR)</font></th>"
							+ " <th align='center' width='10%'  rowspan='1' colspan = '2'><font size='2' color='white'> TDS ON GROSS (INR) </font></th>"
							+ " <th align='center' width='5%'  rowspan='2' colspan = '1'><font size='2' color='white'> VAT (INR) </font></th>"
							+ " <th align='center' width='5%'  rowspan='2' colspan = '1'><font size='2' color='white'> CST (INR) </font></th>"
							+ " <th align='center' width='10%'  rowspan='1' colspan = '2'><font size='2' color='white'> TDS ON TAX (INR) </font></th>"
							+ " <th align='center' width='5%'  rowspan='2' colspan = '1'><font size='2' color='white'> NET RECEIVABLE (INR) </font></th>"
							+ " <th align='center' width='5%'  rowspan='2' colspan = '1'><font size='2' color='white'> ACTUAL RECEIVED (INR) </font></th>"
							+ " <th align='center' width='5%' rowspan='2' colspan = '1'><font size='2' color='white'>PAYMENT RECEIPT DATE</font></th>"
							+ " <th align='center' width='3%' rowspan='2' colspan = '1'><font size='2' color='white'>OVERDUE IN DAYS</font></th>"
							+ " <th align='center' width='8%' rowspan='2' colspan = '1'><font size='2' color='white'>REMARKS</font></th>";
					allocMailBody+="</tr>";
					allocMailBody+="<tr bgcolor='green'>"
							+ " <th align='center' width='5%'  colspan = '1'><font size='2' color='white'> AMOUNT </font></th>"
							+ " <th align='center' width='5%' 	colspan = '1'><font size='2' color='white'> CGST</font></th>"
							+ " <th align='center' width='5%'	colspan = '1'><font size='2' color='white'> SGST</font></th>"
							+ " <th align='center' width='3%'  colspan = '1'><font size='2' color='white'> TDS (%) </font></th>"
							+ " <th align='center' width='5%'  colspan = '1'><font size='2' color='white'> TDS VALUE </font></th>"
							+ " <th align='center' width='3%'  colspan = '1'><font size='2' color='white'> TDS (%) </font></th>"
							+ " <th align='center' width='5%'  colspan = '1'><font size='2' color='white'> TDS VALUE </font></th>";
					allocMailBody+="</tr>";
					
					for(int j = 0 ; j < allocData.size() ; j++) {
						
						String alloc_data[]=allocData.elementAt(j).toString().split("!@#");
						
						String bg="";
						if(j%2!=0) 
							bg="#E0E0E0";
						else 
							bg="white";
						
						String tds_on_gross_val = "0" ;
						if(alloc_data[10] != null && !alloc_data[10].toString().equalsIgnoreCase("")) {
							tds_on_gross_val = nff.format(Double.parseDouble(alloc_data[10]));
						}else {
							tds_on_gross_val = alloc_data[10].toString(); 
						}
						String vat_amt = "",cst_amt = "" ;
//						System.out.println("alloc_data[17]****"+alloc_data[17]);
//						System.out.println("alloc_data[16]****"+alloc_data[16]);
						if(alloc_data[17].toString().contains("VAT")) {
							vat_amt = nff.format(Double.parseDouble(alloc_data[16]));
						
						}else if(alloc_data[17].toString().contains("CST")){
							cst_amt = nff.format(Double.parseDouble(alloc_data[16]));
						}
						String tds_tax_perc = "",tds_tax_val = "" ;
						if(!alloc_data[18].toString().equalsIgnoreCase("") && !alloc_data[18].toString().equalsIgnoreCase("")) {
							tds_tax_perc = nf.format(Double.parseDouble(alloc_data[18]));
							tds_tax_val = nff.format(Double.parseDouble(alloc_data[19]));
						}
						
						
						allocMailBody+="<tr bgcolor="+bg+">";
							allocMailBody+="<td align='center' width='5%'><font size='2' color='black'><b>"+alloc_data[0]+"</b></font></td>"
									+ " <td align='center' width='5%'><font size='2' color='black'><b>"+alloc_data[1]+"</b></font></td>"
									+ " <td align='center' width='5%'><font size='2' color='black'><b>"+alloc_data[2]+"</b></font></td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+alloc_data[3]+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+nff.format(Double.parseDouble(alloc_data[4]))+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+nff.format(Double.parseDouble(alloc_data[5]))+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+nff.format(Double.parseDouble(alloc_data[6]))+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+nff.format(Double.parseDouble(alloc_data[7]))+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+nff.format(Double.parseDouble(alloc_data[8]))+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='3%'><font size='2' color='black'><b>"+alloc_data[9]+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+tds_on_gross_val+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+vat_amt+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+cst_amt+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+tds_tax_perc+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+tds_tax_val+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+nff.format(Double.parseDouble(alloc_data[11]))+"</b></font>&nbsp;</td>"
									+ " <td align='right' width='5%'><font size='2' color='black'><b>"+nff.format(Double.parseDouble(alloc_data[12]))+"</b></font>&nbsp;</td>"
									+ " <td align='center' width='5%'><font size='2' color='black'><b>"+alloc_data[13]+"</b></font></td>"
									+ " <td align='right' width='3%'><font size='2' color='black'><b>"+alloc_data[14]+"</b></font>&nbsp;</td>"
									+ " <td align='left' width='8%'>&nbsp;<font size='2' color='black'><b>"+alloc_data[15]+"</b></font></td>";
	
						allocMailBody+="</tr>";
					}
					x_1 *=m1.sendMail1("IGX Receipt Confirmation",allocMailBody,"",to_email_list,from_mail,from_mail,"",host,from_mail,password,false,cc_list);
				}
				br_1.close();
				in_1.close();
				f1_1.close();
			}
			///////////////////
			
			br.close();
			in.close();
			f1.close();
			////////////////////////////
			
			boolean actual_rec_flag=false;
			String MailBody="";
			int x=1;
			boolean flg=false;
			for(int i=0;i<hm.size();i++) 
			{
				String Value[] = ((String) hm.get(v1.elementAt(i))).split("@@@");
				
				for(int xx=0;xx<Value.length;xx++) 
				{
					String Invoice[]=Value[xx].split("!@#");
				}
				//#9E9E9E
				
				MailBody="<h4>Dear Recipient(s),</h4><br>We have received payment for the following invoice(s):<br><br><table align='center' bordercolor='blue' width='100%' style='font-size: x-small;' border='1' cellpadding='0' cellspacing='0'><tr bgcolor='green'>";
								
				MailBody+="<th align='center' width='13%'><font size='2' color='white'>INVOICE NUMBER</font></th>" +
						  "<th align='center' width='8%'><font size='2' color='white'>INVOICE DATE</font></th>" +
						  "<th align='center' width='8%'><font size='2' color='white'>DUE DATE</font></th>" +
						  "<th align='center' width='8%'><font size='2' color='white'>NAME OF PARTY</font></th>" +
						  "<th align='center' width='15%'><font size='2' color='white'>INVOICE AMOUNT (INR)</font></th>";	
					
					if(segment.equalsIgnoreCase("IGX"))
						{
							
							MailBody+="<th align='center' width='10%'><font size='2' color='white'>TDS VALUE (INR)</font></th>";
						}				
							MailBody+="<th align='center' width='15%'><font size='2' color='white'>NET RECEIVABLE (INR)</font></th>";						
							MailBody+="<th align='center' width='15%'><font size='2' color='white'>ACTUAL RECEIVED (INR)</font></th>" +
							"<th align='center' width='8%'><font size='2' color='white'>PAYMENT RECEIPT DATE</font></th>";					
				MailBody+="</tr>";
				String actrecv="";
				String netrecv="";
				String tdsval="";
		//		 abbr-invno-invdt-salesvalue-invvalue-duedt-actrec-payrecdt-netrec-tds
				for(int xx=0;xx<Value.length;xx++) 
				{
					String bg="";
					if(xx%2!=0) 
						bg="#E0E0E0";
					else 
						bg="white";
					MailBody+="<tr bgcolor="+bg+">";
					String Invoice[]=Value[xx].split("!@#");
		//			System.out.println("in this value--"+Value[xx]);
					
					
					MailBody+="<td align='center' width='13%'><font size='2' color='black'><b>"+Invoice[1]+"</b></font></td>";		//INVOICE NUMBER
					MailBody+="<td align='center' width='8%'><font size='2' color='black'><b>"+Invoice[2]+"</b></font></td>";		//INVOICE DATE
					
					//DUE DATE
					MailBody+="<td align='center' width='8%'><font size='2' color='black'><b>"+Invoice[5]+"</b></font></td>";
					
					//NAME OF PARTY
					MailBody+="<td align='center' width='8%'><font size='2' color='black'><b>"+Invoice[0]+"</b></font></td>";		//NAME OF PARTY
					
					//INVOICE AMOUNT
					MailBody+="<td align='right' width='15%'><font size='2' color='black'><b>"+nff.format(Double.parseDouble(Invoice[4]))+"</b>&nbsp;</font></td>";
					
					//TDS VALUE
					//if(!segment.equalsIgnoreCase("SALES") && (!segment.equalsIgnoreCase("LOA")) && (!segment.equalsIgnoreCase("Interest")) && (!segment.equalsIgnoreCase("SDLNG"))  && (!segment.equalsIgnoreCase("LDLNG")))
					//System.out.println("Invoice[9]-1--"+Invoice[9]+"--Invoice[1]--"+Invoice[1]);
					if(segment.equalsIgnoreCase("IGX"))
					{
		//				System.out.println("tdsval-1--"+tdsval+"----"+Invoice[9]);
						if(!Invoice[9].equalsIgnoreCase("-") && !Invoice[9].equalsIgnoreCase("")) { //Hiren_20210623
							tdsval=nff.format(Double.parseDouble(Invoice[9]));
							MailBody+="<td align='right' width='10%'><font size='2' color='black'><b>"+tdsval+"&nbsp;</b></font></td>";
						}else {
							MailBody+="<td align='right' width='10%'><font size='2' color='black'></font></td>";
						}
					}
					
					//NET REC
		//			System.out.println("tds---"+Value[xx]);
					if(tds.equals("")){
						netrecv=nff.format(Double.parseDouble(Invoice[6].replaceAll(",", "")));
					}else{
						if(!Invoice[8].equals("-"))
							netrecv=nff.format(Double.parseDouble(Invoice[8].replaceAll(",", "")));
					}
					MailBody+="<td align='right' width='15%'><font size='2' color='black'><b>"+nff.format(Double.parseDouble(Invoice[3]))+"&nbsp;</b></font></td>";	//NET RECEIVABLE			
					
					//ACT REC
					if(!Invoice[6].equals("-"))
						actrecv=nff.format(Double.parseDouble(Invoice[6].replaceAll(",", "")));
					MailBody+="<td align='right' width='15%'><font size='2' color='blue'><b>"+actrecv+"&nbsp;</b></font></td>";	//ACTUAL RECEIVED AMOUNT
					
					MailBody+="<td align='center' width='8%'><font size='2' color='blue'><b>"+Invoice[7]+"</b></font></td>";	//PAYMENT RECEIPT DATE
				}
				
				MailBody+="</tr>";
				flg=true;
				MailBody+="</table><br><br>";
				MailBody+="Please maintain confidentiality.<br> NOTE: This notification has been auto-generated - PLEASE DO NOT REPLY. <br>If you have any queries contact finance.";
				MailBody+="<br><br><font style='font-size: x-small'>Thanks</font><br><font style='font-size: xx-small; color: gray'>- This is System Generated Email.</font>";
//				System.out.println("MailBody---"+MailBody);
				if(!v1.elementAt(i).toString().equalsIgnoreCase("-"))
				{
					if(v1.elementAt(i).toString().equalsIgnoreCase(logged_on_user_emailid)){
						cc_list=cc_list;
					}else{
						cc_list="";
					}
					if(segment.equalsIgnoreCase("IGX"))
					{
//						System.out.println("v1.elementAt(i)---"+v1.elementAt(i));
						x *=m1.sendMail1("IGX Receipt Confirmation",MailBody,"",v1.elementAt(i).toString(),from_mail,from_mail,"",host,from_mail,password,false,cc_list);
					}
				}
			}
//			System.out.println("x_1*********"+x_1);
			if(((insSubCnt > 0) || (insCnt > 0 && updCnt > 0) && x==1 && x_1==1)) {
				dbcon.commit();
				msg="Data Submitted Successfully..!";
			}else {
				dbcon.rollback();
				if(x==2) 
					msg="ERR in Sending Email..!";
				else
					msg="ERR in Submission..!";
			}
		} catch (Exception e) {
			dbcon.rollback();
			e.printStackTrace();
			msg="Failed: Please Contact IT Administrator!";
		}
		url = "../sales_invoice/frm_invoice_payment_dtl.jsp?year="+year+"&month="+month+"&to_year="+to_year+"&to_month="+to_month+"&segment="+segment+"&invstatus="+invstatus+"&cust_name="+cust_name+"&customer_cd="+cust_cd+"&msg="+msg+"&btnflag=Y&customer_abbr="+customer_abbr;
	}
}
