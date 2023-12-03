package com.hlpl.hazira.fms7.accounting;

//Servlet Introduced By     : Samik Shah ...
//Servlet Introduced On     : 20th September, 2010 ...
//Code Reviewed By			:  
//Code Reviewed Date		:  
//Code Review Status  		:
//Last Modified By			: Samik Shah ...
//Last Modified Date		: 29th October, 2010 ...


import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.sql.*;
import com.hlpl.hazira.fms7.util.RuntimeConf;
import com.hlpl.hazira.fms7.util.escapeSingleQuotes;


public class Frm_Accounting extends HttpServlet
{ 
	public static Connection dbcon;
	
	public String servletName = "Frm_Accounting";
	public String methodName = "";
	public String option = "";
	public String url = "";
	public String form_name = "";
	public String msg = "";
	int count = 0;
	static escapeSingleQuotes obj = new escapeSingleQuotes();
	
	//Following NumberFormat Object is defined by Samik Shah ... On 2nd June, 2010 ...
	NumberFormat nf = new DecimalFormat("###########0.00");
	NumberFormat nf3 = new DecimalFormat("###########0.000");
	NumberFormat nf2 = new DecimalFormat("###0.0000"); //For Currency Purpose ... Defined By Samik Shah On 1st June, 2010 ...
	
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
				stmt3 = dbcon.createStatement();
				stmt4 = dbcon.createStatement();
			}
			
			option=req.getParameter("option")==null?"":req.getParameter("option");
			
			write_permission = req.getParameter("write_permission")==null?"N":req.getParameter("write_permission");
			delete_permission = req.getParameter("delete_permission")==null?"N":req.getParameter("delete_permission");
			print_permission = req.getParameter("print_permission")==null?"N":req.getParameter("print_permission");
			approve_permission = req.getParameter("approve_permission")==null?"N":req.getParameter("approve_permission");
			audit_permission = req.getParameter("audit_permission")==null?"N":req.getParameter("audit_permission");
			
			form_id = req.getParameter("form_id")==null?"0":req.getParameter("form_id");
			form_nm = req.getParameter("form_nm")==null?"":req.getParameter("form_nm");
			
			if(option.equalsIgnoreCase("HEDGE_REGISTER"))
			{
				HedgeRegister(req,res);
			}
			else if(option.equalsIgnoreCase("BANK_REMITTANCE"))
			{
				BankRemittance(req,res);
			}
			else if(option.equalsIgnoreCase("DR_CR_REGISTER"))
			{
				DebitCreditRegister(req,res);
			}
			else if(option.equalsIgnoreCase("OPENING_STOCK_DTLS"))
			{
				Opening_Stock_Dtls(req,res);		//Priyanka 310111				
			}
			else if(option.equalsIgnoreCase("CLOSING_STOCK_DTLS"))
			{
				Closing_Stock_Dtls_From_Monthly_Report(req,res); //Introduced By Samik Shah On 13th July, 2011 ...
			}
			else if(option.equalsIgnoreCase("REGAS_SUG_PERCENT"))
			{
				Regas_SUG_Percent(req,res);			//Priyanka 310111
			}
			else if(option.equalsIgnoreCase("RPT_STOCK_VALUATION")) //Priyanka 150311
			{
				Rpt_Stock_Valuation(req,res); //Whole Method Has Been Modified By Samik Shah On 19th July, 2011 ...			
			}
			else if(option.equalsIgnoreCase("RPT_STOCK_VALUATION2"))
			{
				Rpt_Stock_Valuation2(req,res); //New Method Has Been Introduced By Samik Shah On 19th July, 2011 ...			
			}
			else if(option.equalsIgnoreCase("RPT_SN_WISE_DTLS"))
			{
				saveExpectedSalesDetails(req,res); //Introduced By Samik Shah On 21st June, 2011 ...
			}
			else if(option.equalsIgnoreCase("RPT_SN_WISE_DTLS_DLNG"))
			{
				saveExpectedSalesDetails_DLNG(req,res); //Introduced By Samik Shah On 21st June, 2011 ...
			}
			else if(option.equalsIgnoreCase("Adv_Pay_Dtl"))
			{
				saveAdvancePaymentDetails(req,res); //Introduced By Samik Shah On 16th June, 2011 ...
			}
			else if(option.equalsIgnoreCase("CustomerAccountCodesMapping"))
			{
				saveCustomerAccountCodesMapping(req,res); //Introduced By Samik Shah On 4th August, 2011 ...
			}
			else if(option.equalsIgnoreCase("TraderAccountCodesMapping"))
			{
				saveTraderAccountCodesMapping(req,res); //Introduced By Samik Shah On 4th August, 2011 ...
			}
			else if(option.equalsIgnoreCase("TransporterAccountCodesMapping"))
			{
				saveTransporterAccountCodesMapping(req,res); //Introduced By Samik Shah On 4th August, 2011 ...
			}
			
			else if(option.equalsIgnoreCase("DEBIT_CREDIT_GENERATE"))
			{
				Debit_Credit_generate(req,res); //Introduced By NB 29/09/2014..
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
		System.out.println("url = "+url);
		res.sendRedirect(url);
	}
	
	public void Debit_Credit_generate(HttpServletRequest request,HttpServletResponse res) throws SQLException
	{
		form_name = "frm_credit_note_generate.jsp";
		System.out.println("form_name = "+form_name);
		HttpSession session = request.getSession();
		methodName = "Debit_Credit_generate()";
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		
		
		String Customer_tax=request.getParameter("customer_tax");
		
		
		String [] Final_dates = request.getParameterValues("final_dt");
		String net_amt_inr=request.getParameter("net_amt_inr");
		String grossamtinr=request.getParameter("grossamtinr");
		String grossamtusd=request.getParameter("grossamtrow2");
		String tax_struct_cd=request.getParameter("tax_struct_cd")==null?"0":request.getParameter("tax_struct_cd");
		String Difference_exchng_rate=request.getParameter("Difference_exchng_rate");
		String Exchange_rate_cd=request.getParameter("Exchange_rate_cd");
		String Quantity=request.getParameter("quantityinput");
		String rate="";
		String flag="";
		if(Customer_tax.equalsIgnoreCase("regas"))
		{
			rate=request.getParameter("rateregasinput");
			flag="R";
		}
		else if (Customer_tax.equalsIgnoreCase("cst") || Customer_tax.equalsIgnoreCase("vat"))
		{		
			rate=request.getParameter("rateinput");
			flag="C";
		}
		
		
		String credit_note_dt=request.getParameter("credit_note_dt");
		String credit_note_no=request.getParameter("credit_note_no");
		String bill_from_dt=request.getParameter("bill_from_dt");
		String bill_to_dt=request.getParameter("bill_to_dt");
		String invoice_no=request.getParameter("invoice_no");
		String inv_dt=request.getParameter("inv_dt");
		String header=request.getParameter("law");
		String customer_cd=request.getParameter("customer_cd");
		String customer_nm=request.getParameter("customer_nm");
		String boe_no=request.getParameter("boe_no")==null?"":request.getParameter("boe_no");
		String boe_dt=request.getParameter("boe_dt")==null?"":request.getParameter("boe_dt");
		String plant_cd=request.getParameter("plant_cd");
		
		
		
//		System.out.println("net_amt_inr----"+net_amt_inr);
//		System.out.println("grossamtinr----"+grossamtinr);
//		System.out.println("grossamtusd----"+grossamtusd);
//		System.out.println("tax_struct_cd----"+tax_struct_cd);
//		System.out.println("Difference_exchng_rate----"+Difference_exchng_rate);
//		System.out.println("Exchange_rate_cd----"+Exchange_rate_cd);
//		System.out.println("Quantity----"+Quantity);
//		System.out.println("credit_note_dt----"+credit_note_dt+"--credit_note_no--"+credit_note_no);
//		System.out.println("bill_from_dt----"+bill_from_dt+"--bill_to_dt--"+bill_to_dt);
//		System.out.println("invoice_no----"+invoice_no+"--inv_dt--"+inv_dt);
		
		
		String seq_no="";
		String cr_dr=request.getParameter("DRCRflag");
		String creditdebit="";
		if(cr_dr.equalsIgnoreCase("C"))
			{cr_dr="0";
			creditdebit="Credit";}
		else
			{cr_dr="1";
			creditdebit="Debit";}
		
		String temp_dt[]=credit_note_dt.split("/");
		String year=temp_dt[2];
		
		String dt1="",dt2="",dt3="",dt4="";
		
		if(Final_dates.length==2)
		{
			dt1=Final_dates[0];
			dt2=Final_dates[1];
			dt3="";
			dt4="";
		}
		else if(Final_dates.length>2)
		{
			dt1=Final_dates[0];
			dt2=Final_dates[1];
			dt3=Final_dates[2];
			dt4=Final_dates[3];
		}
				
		
		try
		{	
			net_amt_inr=""+(NumberFormat.getInstance().parse(net_amt_inr));
			
			String query="select nvl(max(seq_no),0)  from fms7_cr_dr_mst where" +
					" customer_cd='"+customer_cd+"' and cr_dr='"+cr_dr+"' and plant_seq_no='"+plant_cd+"'";
			
			rset=stmt.executeQuery(query);
			if(rset.next())
			{
				seq_no=rset.getString(1);
				seq_no=(Integer.parseInt(seq_no)+1)+"";
				
			}
			
			
			
			queryString="insert into fms7_cr_dr_mst (seq_no, customer_cd,cr_dr, cr_dr_no,"+ 
						" cr_dr_dt, period_start_dt, period_end_dt, year, inv_no, inv_dt, qty, rate, "+ 
						" exchg_rate_cd, exchg_rate_dt1, exchg_rate_dt2, exchg_rate_dt3, exchg_rate_dt4, "+
						" exchg_rate_diff, tax_struct_cd, gross_amt_usd, gross_amt_inr, net_amt_inr," +
						" header_dtl, enter_dt, enter_by, flag, plant_seq_no,boe_no,boe_dt) values ('"+seq_no+"','"+customer_cd+"','"+cr_dr+"'," +
						" '"+credit_note_no+"',to_date('"+credit_note_dt+"','dd/mm/yyyy'),to_date('"+bill_from_dt+"','dd/mm/yyyy'),"+ 
						" to_date('"+bill_to_dt+"','dd/mm/yyyy'),'"+year+"','"+invoice_no+"',to_date('"+inv_dt+"','dd/mm/yyyy'), "+
						" '"+Quantity+"','"+rate+"','"+Exchange_rate_cd+"',to_date('"+dt1+"','dd/mm/yyyy'),to_date('"+dt2+"','dd/mm/yyyy')," +
						"to_date('"+dt3+"','dd/mm/yyyy'),to_date('"+dt4+"','dd/mm/yyyy'), "+
						" '"+Difference_exchng_rate+"','"+tax_struct_cd+"','"+grossamtusd+"','"+grossamtinr+"','"+net_amt_inr+"','"+header+"', "+
						" to_date(sysdate,'dd/mm/yyyy'),'"+user_cd+"','"+flag+"','"+plant_cd+"','"+boe_no+"',to_date('"+boe_dt+"','dd/mm/yyyy'))";
			
			System.out.println("FMS7_CR_DR_MSt = "+queryString);
			stmt.executeUpdate(queryString);
			
			msg = creditdebit+" Note "+seq_no+" Generated Successfully for "+customer_nm+".";
			url = "../credit_debit/frm_credit_note_generate.jsp?msg="+msg+"&customer_cd="+customer_cd+"&tax="+Customer_tax+"&plant_cd="+plant_cd+"&DRCRflag="+creditdebit;
			dbcon.commit();
			System.out.println("COMMMITED");
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="CREDIT  Not Submitted !!!";
			e.printStackTrace();
			System.out.println("Exception in the "+methodName+" of "+servletName+" "+e);
			url = "../credit_debit/frm_credit_note_generate.jsp?msg="+msg+"&customer_cd="+customer_cd+"&tax="+Customer_tax+"&plant_cd="+plant_cd+"&DRCRflag="+creditdebit;
		}
	}
	public void saveTransporterAccountCodesMapping(HttpServletRequest request,HttpServletResponse res) throws SQLException
	{

		form_name = "frm_transporter_accounts_code_mapping.jsp";
		System.out.println("form_name = "+form_name);
		HttpSession session = request.getSession();
		methodName = "saveTransporterAccountCodesMapping()";
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		
		String [] trd_cd = request.getParameterValues("trd_cd");
		String [] trd_sun_account_cd = request.getParameterValues("trd_sun_account_cd");
		String [] trd_purchase_account_cd = request.getParameterValues("trd_purchase_account_cd");
		String [] trd_custom_account_cd = request.getParameterValues("trd_custom_account_cd");
		String [] shell_grp_flag = request.getParameterValues("shell_grp_flag");
				
		try
		{
			for(int i=0; i<trd_cd.length; i++)
			{
				queryString = "SELECT TRANSPORTER_CD FROM FMS7_TRANSPORTER_MAPPING WHERE TRANSPORTER_CD="+trd_cd[i]+"";
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					queryString1 = "DELETE FROM FMS7_TRANSPORTER_MAPPING WHERE TRANSPORTER_CD="+trd_cd[i]+"";
					stmt1.executeUpdate(queryString1);
				}
				
				queryString = "SELECT TRANSPORTER_CD FROM FMS7_PUR_TRANSPORTER_MAPPING WHERE TRANSPORTER_CD="+trd_cd[i]+"";
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					queryString1 = "DELETE FROM FMS7_PUR_TRANSPORTER_MAPPING WHERE TRANSPORTER_CD="+trd_cd[i]+"";
					stmt1.executeUpdate(queryString1);
				}
				
			}
			
			for(int i=0; i<trd_cd.length; i++)
			{
				if(trd_cd[i]!=null && !trd_cd[i].trim().equals("0") && !trd_cd[i].trim().equals(""))
				{
					if(trd_sun_account_cd[i]!=null && !trd_sun_account_cd[i].trim().equals("0") && !trd_sun_account_cd[i].trim().equals(""))
					{
						queryString = "INSERT INTO FMS7_TRANSPORTER_MAPPING(TRANSPORTER_CD, eff_dt, account_cd, " +
									  "account_type, emp_cd, ent_dt, flag) " +
									  "VALUES("+trd_cd[i]+", TO_DATE('01/01/2005','dd/mm/yyyy'), " +
									  "'"+trd_sun_account_cd[i]+"', 'T', '"+user_cd+"', sysdate, 'Y')";
						System.out.println("FMS7_TRANSPORTER_MAPPING Insert Query = "+queryString);
						stmt.executeUpdate(queryString);
					}
					
					if(trd_purchase_account_cd[i]!=null && !trd_purchase_account_cd[i].trim().equals("0") && !trd_purchase_account_cd[i].trim().equals(""))
					{
						queryString = "INSERT INTO FMS7_PUR_TRANSPORTER_MAPPING(TRANSPORTER_CD, eff_dt, account_cd, " +
									  "account_type, emp_cd, ent_dt, flag, shell_grp_flag) " +
									  "VALUES("+trd_cd[i]+", TO_DATE('01/01/2005','dd/mm/yyyy'), " +
									  "'"+trd_purchase_account_cd[i]+"', 'P', '"+user_cd+"', " +
									  "sysdate, 'Y', '"+shell_grp_flag[i]+"')";
						System.out.println("FMS7_PUR_TRANSPORTER_MAPPING Insert Query = "+queryString);
						stmt.executeUpdate(queryString);
					}
					
				}
			}
			
			msg = "TRANSPORTER(s) SUN Account Codes Submitted Successfully.";
			url = "../accounting/frm_transporter_accounts_code_mapping.jsp?msg="+msg+"&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
			dbcon.commit();
			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="TRANSPORTER(s) SUN Account Code Not Submitted !!!";
			e.printStackTrace();
			System.out.println("Exception in the "+methodName+" of "+servletName+" "+e);
			url = "../accounting/frm_transporter_accounts_code_mapping.jsp?msg="+msg+"&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
		}
	
	}
	
	//Following Method Has Been Introduced By Samik Shah On 5th August, 2011 ...
	public void saveTraderAccountCodesMapping(HttpServletRequest request,HttpServletResponse res) throws SQLException
	{
		form_name = "frm_trader_accounts_code_mapping.jsp";
		System.out.println("form_name = "+form_name);
		HttpSession session = request.getSession();
		methodName = "ssaveTraderAccountCodesMapping()";
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		
		String [] trd_cd = request.getParameterValues("trd_cd");
		String [] trd_sun_account_cd = request.getParameterValues("trd_sun_account_cd");
		String [] trd_purchase_account_cd = request.getParameterValues("trd_purchase_account_cd");
		String [] trd_custom_account_cd = request.getParameterValues("trd_custom_account_cd");
		String [] shell_grp_flag = request.getParameterValues("shell_grp_flag");
		String [] trd_pl_flag=request.getParameterValues("trd_pl_flag");
		String [] trd_pl_cd=request.getParameterValues("trd_pl_cd");
				
		try
		{
			for(int i=0; i<trd_cd.length; i++)
			{
				if(trd_pl_flag[i].equals("Y")){
					queryString = "SELECT TRADER_CD FROM FMS7_TRADER_PLANT_MAPPING WHERE TRADER_CD='"+trd_cd[i]+"' and plant_cd='"+trd_pl_cd[i]+"'";
					rset = stmt.executeQuery(queryString);
					if(rset.next())
					{
						queryString1 = "DELETE FROM FMS7_TRADER_PLANT_MAPPING WHERE TRADER_CD='"+trd_cd[i]+"' and plant_cd='"+trd_pl_cd[i]+"'";
						stmt1.executeUpdate(queryString1);
					}
					
					queryString = "SELECT TRADER_CD FROM FMS7_LNG_PUR_PLANT_MAPPING WHERE TRADER_CD='"+trd_cd[i]+"' and plant_cd='"+trd_pl_cd[i]+"'";
					rset = stmt.executeQuery(queryString);
					if(rset.next())
					{
						queryString1 = "DELETE FROM FMS7_LNG_PUR_PLANT_MAPPING WHERE TRADER_CD='"+trd_cd[i]+"' and plant_cd='"+trd_pl_cd[i]+"'";
						stmt1.executeUpdate(queryString1);
					}
				}else{
					queryString = "SELECT TRADER_CD FROM FMS7_TRADER_MAPPING WHERE TRADER_CD="+trd_cd[i]+"";
					rset = stmt.executeQuery(queryString);
					if(rset.next())
					{
						queryString1 = "DELETE FROM FMS7_TRADER_MAPPING WHERE TRADER_CD="+trd_cd[i]+"";
						stmt1.executeUpdate(queryString1);
					}
					
					queryString = "SELECT TRADER_CD FROM FMS7_LNG_PURCHASE_MAPPING WHERE TRADER_CD="+trd_cd[i]+"";
					rset = stmt.executeQuery(queryString);
					if(rset.next())
					{
						queryString1 = "DELETE FROM FMS7_LNG_PURCHASE_MAPPING WHERE TRADER_CD="+trd_cd[i]+"";
						stmt1.executeUpdate(queryString1);
					}
				}
				queryString = "SELECT TRADER_CD FROM FMS7_CUSTOM_DUTY_MAPPING WHERE TRADER_CD="+trd_cd[i]+"";
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					queryString1 = "DELETE FROM FMS7_CUSTOM_DUTY_MAPPING WHERE TRADER_CD="+trd_cd[i]+"";
					stmt1.executeUpdate(queryString1);
				}
			}
			
			for(int i=0; i<trd_cd.length; i++)
			{
				if(trd_cd[i]!=null && !trd_cd[i].trim().equals("0") && !trd_cd[i].trim().equals(""))
				{
					if(trd_sun_account_cd[i]!=null && !trd_sun_account_cd[i].trim().equals("0") && !trd_sun_account_cd[i].trim().equals(""))
					{
						if(trd_pl_flag[i].equals("Y")){
							queryString = "INSERT INTO FMS7_TRADER_PLANT_MAPPING(trader_cd, eff_dt, account_cd, " +
									  "account_type, emp_cd, ent_dt, flag,plant_cd) " +
									  "VALUES("+trd_cd[i]+", TO_DATE('01/01/2005','dd/mm/yyyy'), " +
									  "'"+trd_sun_account_cd[i]+"', 'T', '"+user_cd+"', sysdate, 'Y','"+trd_pl_cd[i]+"')";
							System.out.println("FMS7_TRADER_MAPPING Insert Query = "+queryString);
							stmt.executeUpdate(queryString);
						}else{
							queryString = "INSERT INTO FMS7_TRADER_MAPPING(trader_cd, eff_dt, account_cd, " +
										  "account_type, emp_cd, ent_dt, flag) " +
										  "VALUES("+trd_cd[i]+", TO_DATE('01/01/2005','dd/mm/yyyy'), " +
										  "'"+trd_sun_account_cd[i]+"', 'T', '"+user_cd+"', sysdate, 'Y')";
							System.out.println("FMS7_TRADER_MAPPING Insert Query = "+queryString);
							stmt.executeUpdate(queryString);
						}
					}
					
					if(trd_purchase_account_cd[i]!=null && !trd_purchase_account_cd[i].trim().equals("0") && !trd_purchase_account_cd[i].trim().equals(""))
					{
						if(trd_pl_flag[i].equals("Y")){
							queryString = "INSERT INTO FMS7_LNG_PUR_plANT_MAPPING(trader_cd, eff_dt, account_cd, " +
									  "account_type, emp_cd, ent_dt, flag, shell_grp_flag,plant_cd) " +
									  "VALUES("+trd_cd[i]+", TO_DATE('01/01/2005','dd/mm/yyyy'), " +
									  "'"+trd_purchase_account_cd[i]+"', 'P', '"+user_cd+"', " +
									  "sysdate, 'Y', '"+shell_grp_flag[i]+"','"+trd_pl_cd[i]+"')";
						System.out.println("FMS7_LNG_PURCHASE_MAPPING Insert Query = "+queryString);
						stmt.executeUpdate(queryString);
						}else{
							queryString = "INSERT INTO FMS7_LNG_PURCHASE_MAPPING(trader_cd, eff_dt, account_cd, " +
										  "account_type, emp_cd, ent_dt, flag, shell_grp_flag) " +
										  "VALUES("+trd_cd[i]+", TO_DATE('01/01/2005','dd/mm/yyyy'), " +
										  "'"+trd_purchase_account_cd[i]+"', 'P', '"+user_cd+"', " +
										  "sysdate, 'Y', '"+shell_grp_flag[i]+"')";
							System.out.println("FMS7_LNG_PURCHASE_MAPPING Insert Query = "+queryString);
							stmt.executeUpdate(queryString);
						}
					}
					
					if(trd_custom_account_cd[i]!=null && !trd_custom_account_cd[i].trim().equals("0") && !trd_custom_account_cd[i].trim().equals(""))
					{
						
							queryString = "INSERT INTO FMS7_CUSTOM_DUTY_MAPPING(trader_cd, eff_dt, account_cd, " +
										  "account_type, emp_cd, ent_dt, flag, shell_grp_flag) " +
										  "VALUES("+trd_cd[i]+", TO_DATE('01/01/2005','dd/mm/yyyy'), " +
										  "'"+trd_custom_account_cd[i]+"', 'P', '"+user_cd+"', " +
										  "sysdate, 'Y', '"+shell_grp_flag[i]+"')";
							System.out.println("FMS7_CUSTOM_DUTY_MAPPING Insert Query = "+queryString);
							stmt.executeUpdate(queryString);
						
					}
				}
			}
			
			msg = "Supplier(s) SUN Account Codes Submitted Successfully.";
			url = "../accounting/frm_trader_accounts_code_mapping.jsp?msg="+msg+"&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
			dbcon.commit();
			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="Supplier(s) SUN Account Code Not Submitted !!!";
			e.printStackTrace();
			System.out.println("Exception in the "+methodName+" of "+servletName+" "+e);
			url = "../accounting/frm_trader_accounts_code_mapping.jsp?msg="+msg+"&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
		}
	}
	
	
	//Following Method Has Been Introduced By Samik Shah On 4th August, 2011 ...
	public void saveCustomerAccountCodesMapping(HttpServletRequest request,HttpServletResponse res) throws SQLException
	{
		form_name = "frm_customer_accounts_code_mapping.jsp";
		System.out.println("form_name = "+form_name);
		HttpSession session = request.getSession();
		methodName = "ssaveCustomerAccountCodesMapping()";
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		
		String [] cust_cd = request.getParameterValues("cust_cd");
		String [] cust_sun_account_cd = request.getParameterValues("cust_sun_account_cd");
				
		try
		{
			for(int i=0; i<cust_cd.length; i++)
			{
				queryString = "SELECT CUSTOMER_CD FROM FMS7_CUSTOMER_MAPPING WHERE CUSTOMER_CD="+cust_cd[i]+"";
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					queryString1 = "DELETE FROM FMS7_CUSTOMER_MAPPING WHERE CUSTOMER_CD="+cust_cd[i]+"";
					stmt1.executeUpdate(queryString1);
				}
			}
			
			for(int i=0; i<cust_cd.length; i++)
			{
				if(cust_cd[i]!=null && !cust_cd[i].trim().equals("0") && !cust_cd[i].trim().equals("") && cust_sun_account_cd[i]!=null && !cust_sun_account_cd[i].trim().equals("0") && !cust_sun_account_cd[i].trim().equals(""))
				{
					queryString = "INSERT INTO FMS7_CUSTOMER_MAPPING(customer_cd, eff_dt, account_cd, " +
								  "account_type, emp_cd, ent_dt, flag) " +
								  "VALUES("+cust_cd[i]+", TO_DATE('01/01/2005','dd/mm/yyyy'), " +
								  "'"+cust_sun_account_cd[i]+"', 'T', '"+user_cd+"', sysdate, 'Y')";
					System.out.println("FMS7_CUSTOMER_MAPPING Insert Query = "+queryString);
					stmt.executeUpdate(queryString);
				}
			}
			
			msg = "Customer(s) SUN Account Codes Submitted Successfully.";
			url = "../accounting/frm_customer_accounts_code_mapping.jsp?msg="+msg+"&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
			dbcon.commit();
			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="Customer(s) SUN Account Code Not Submitted !!!";
			e.printStackTrace();
			System.out.println("Exception in the "+methodName+" of "+servletName+" "+e);
			url = "../accounting/frm_customer_accounts_code_mapping.jsp?msg="+msg+"&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
		}
	}
	
	
	//Following Method Has Been Introduced By Samik Shah On 16th June, 2011 ...
	public void saveAdvancePaymentDetails(HttpServletRequest request,HttpServletResponse res) throws SQLException
	{
		form_name = "frm_adv_receipt.jsp";
		System.out.println("form_name = "+form_name);
		HttpSession session = request.getSession();
		methodName = "saveAdvancePaymentDetails()";
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		
		String customer_cd = request.getParameter("customer_cd");
		String customer_nm = request.getParameter("customer_nm");
		String pay_dt = request.getParameter("pay_dt");
		String hidden_pay_dt = request.getParameter("hidden_pay_dt");
		String seq_no = request.getParameter("seq_no");
		String pay_amt = request.getParameter("pay_amt");
		String remark = request.getParameter("remark");
		String account_cd = request.getParameter("account_cd");
		String contract_type = request.getParameter("contract_type");
		String journal_no = request.getParameter("journal_no");
		String allocation_ref = request.getParameter("allocation_ref");
		String approve_dt = request.getParameter("approve_dt");
		String to_dt = request.getParameter("to_dt");
		
		try
		{
			if(!customer_cd.trim().equals("") && !customer_cd.trim().equals("0") && !seq_no.trim().equals("") && !seq_no.trim().equals("0") && !hidden_pay_dt.trim().equals(""))
			{
				queryString1 = "SELECT CUSTOMER_CD FROM FMS7_PAYMENT_DTL " +
							   "WHERE CUSTOMER_CD="+customer_cd.trim()+" AND " +
							   "PAY_DT=TO_DATE('"+hidden_pay_dt.trim()+"','DD/MM/YYYY') AND " +
							   "SEQ_NO="+seq_no.trim()+"";
				System.out.println("FMS7_PAYMENT_DTL Fetch Query = "+queryString1);
				rset1 = stmt1.executeQuery(queryString1);				
				if(rset1.next())
				{
					queryString = "DELETE FROM FMS7_PAYMENT_DTL " +
								  "WHERE CUSTOMER_CD="+customer_cd.trim()+" AND " +
								  "PAY_DT=TO_DATE('"+hidden_pay_dt.trim()+"','DD/MM/YYYY') AND " +
								  "SEQ_NO="+seq_no.trim()+"";
					System.out.println("Delete from FMS7_PAYMENT_DTL Query = "+queryString);
					stmt.executeUpdate(queryString);		
				}
			}
			
            seq_no = "1";
            
			queryString1 = "SELECT MAX(SEQ_NO) FROM FMS7_PAYMENT_DTL " +
            			   "WHERE customer_cd="+customer_cd.trim()+" AND " +
            			   "PAY_DT=TO_DATE('"+pay_dt.trim()+"','DD/MM/YYYY')";
			System.out.println("FMS7_PAYMENT_DTL Fetch Query = "+queryString1);
			rset1 = stmt1.executeQuery(queryString1);
			if(rset1.next())
			{
				if(rset1.getString(1)!=null && !(rset1.getString(1).trim().equals("")))
				{
					seq_no = ""+(Integer.parseInt(rset1.getString(1))+1);
				}
				else
				{
					seq_no = "1";
				}
			}
			else
			{
				seq_no = "1";
			}  
           
            queryString = "INSERT INTO FMS7_PAYMENT_DTL(ACCOUNT_CD, CUSTOMER_CD, SEQ_NO, " +
            			  "PAY_DT, PAY_AMT, PAY_TYPE, REMARK, " +
            			  "CONTRACT_TYPE, JOURNAL_NO, ALLOCATION_REF, " +
            			  "EMP_CD,ENT_DT,DOWNLOAD_DT,DOWNLOAD_FLAG) " +
            			  "VALUES('"+account_cd+"','"+customer_cd.trim()+"'," +
            			  "'"+seq_no+"',TO_DATE('"+pay_dt.trim()+"','DD/MM/YYYY'),'"+pay_amt+"'," +
            			  "'A','"+obj.replaceSingleQuotes(remark)+"','"+contract_type+"'," +
            			  "'"+journal_no+"','"+allocation_ref+"'," +
            			  ""+user_cd+",sysdate,sysdate,'Y')";
            System.out.println("FMS7_PAYMENT_DTL Insert Query = "+queryString);
			stmt.executeUpdate(queryString);
			
			msg = "Advance Payment Receipt details of Customer: "+customer_nm+" for receipt date: "+pay_dt.trim()+" has been successfully Submitted !!!";
			url = "../accounting/frm_adv_receipt.jsp?approve_dt="+approve_dt+"&to_dt="+to_dt+"&cust_cd="+customer_cd+"&msg="+msg+"&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
			dbcon.commit();
			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="Advance Receipt Details Not Submitted !!!";
			e.printStackTrace();
			System.out.println("Exception in the "+methodName+" of "+servletName+" "+e);
			url="../accounting/frm_adv_receipt.jsp?approve_dt="+approve_dt+"&to_dt="+to_dt+"&cust_cd="+customer_cd+"&msg="+msg+"&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
		}
	}
	
	
    //Function for Insert and Updating Bank Remittance on 16-09-2010 by Achal
	public void DebitCreditRegister(HttpServletRequest request,HttpServletResponse res) throws SQLException
	{
		form_name = "frm_credit_debit_notes_register.jsp";
		System.out.println("form_name = "+form_name);
		HttpSession session = request.getSession();
		methodName="DebitCreditRegister()";
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		
		String from_dt = request.getParameter("from_dt")==null?"":request.getParameter("from_dt");
		String to_dt = request.getParameter("to_dt")==null?"":request.getParameter("to_dt");
		String bscode = request.getParameter("cust_cd")==null?"0":request.getParameter("cust_cd");
		String dr_cr_flag = request.getParameter("dr_cr_flag")==null?"0":request.getParameter("dr_cr_flag");
		
		String customer = request.getParameter("customer")==null?"N":request.getParameter("customer");
		String date1 = request.getParameter("date1")==null?"N":request.getParameter("date1");
		String dr_cr = request.getParameter("dr_cr")==null?"N":request.getParameter("dr_cr");			
		
		String dr_cr_no [] = request.getParameterValues("dr_cr_no");
		String dr_cr_flg [] = request.getParameterValues("dr_cr_flg");				
		String dr_cr_fin_yr [] = request.getParameterValues("dr_cr_fin_yr");
		
		String customer_cd [] = request.getParameterValues("customer_cd");				
		String criteria [] = request.getParameterValues("criteria");
		String inv_dt [] =request.getParameterValues("inv_dt");					
		String due_dt [] = request.getParameterValues("due_dt");		
		String dr_cr_amt [] = request.getParameterValues("dr_cr_amt");			
		String dr_cr_dt [] = request.getParameterValues("dr_cr_dt");
		String paid_amt [] = request.getParameterValues("paid_amt");
		String inv_no [] = request.getParameterValues("inv_no");
		String fin_yr [] = request.getParameterValues("fin_yr");
		try
		{
			for(int i=0;i<customer_cd.length;i++)
			{
				queryString = "select * from FMS7_DR_CR_REGISTER WHERE dr_cr_no='"+dr_cr_no[i]+"' AND dr_cr_flag='"+dr_cr_flg[i]+"' AND DR_CR_FIN_YEAR='"+dr_cr_fin_yr [i]+"' ";
				System.out.println("select FMS7_DR_CR_REGISTER = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					//Delete Records ...
					queryString ="delete from FMS7_DR_CR_REGISTER where dr_cr_no='"+dr_cr_no[i]+"' AND dr_cr_flag='"+dr_cr_flg[i]+"' AND DR_CR_FIN_YEAR='"+dr_cr_fin_yr [i]+"' ";
					System.out.println("Delete Query (1) = "+queryString);		
					stmt.executeUpdate(queryString)	;					
				}
				query ="insert into FMS7_DR_CR_REGISTER (DR_CR_NO,DR_CR_FIN_YEAR,DR_CR_FLAG,CUSTOMER_CD," +
				"HLPL_INV_SEQ_NO,FINANCIAL_YEAR,INVOICE_DT,CRITERIA,DR_CR_DT,DR_CR_GROSS_AMT_INR," +
				"PAY_AMT,PAY_DT,EMP_CD,ENT_DT,FLAG) values " +
				"('"+dr_cr_no[i]+"','"+dr_cr_fin_yr[i]+"','"+dr_cr_flg[i]+"','"+customer_cd[i]+"'," +
				"'"+inv_no[i]+"','"+fin_yr[i]+"',to_date('"+inv_dt[i]+"','dd/mm/yyyy'),'"+criteria[i]+"'," +
				"to_date('"+dr_cr_dt[i]+"','dd/mm/yyyy'),'"+dr_cr_amt[i]+"','"+paid_amt[i]+"'," +
				"to_date('"+due_dt[i]+"','dd/mm/yyyy'),'"+user_cd+"',sysdate,'Y')";
				System.out.println("Insert into FMS7_DR_CR_REGISTER  : "+query);			
				stmt.executeUpdate(query);
			}	
			msg="Debit Credit Register Details Submitted Successfully !!!";
			url="../accounting/frm_credit_debit_notes_register.jsp?from_dt="+from_dt+"&to_dt="+to_dt+"&cust_cd="+bscode+"&dr_cr_flag="+dr_cr_flag+"&customer="+customer+"&date1="+date1+"&dr_cr="+dr_cr+"&msg="+msg+"&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
			dbcon.commit();
			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="Debit Credit Register Details Not Submitted !!!";
			e.printStackTrace();
			System.out.println("Exception in the "+methodName+" of "+servletName+" "+e);
			url="../accounting/frm_credit_debit_notes_register.jsp?from_dt="+from_dt+"&to_dt="+to_dt+"&cust_cd="+bscode+"&dr_cr_flag="+dr_cr_flag+"&customer="+customer+"&date1="+date1+"&dr_cr="+dr_cr+"&msg="+msg+"&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;			
		}
	}
	
	
	
	//Method for Inserting and Updating Bank Remittance Details By Samik Shah on 8th March, 2011 ...
	public void BankRemittance(HttpServletRequest request,HttpServletResponse res) throws SQLException
	{
		form_name = "frm_bank_remittance.jsp";		
		escapeSingleQuotes obj = new  escapeSingleQuotes();
		HttpSession session = request.getSession();
		String methodName="BankRemittance()";
		
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		String month = request.getParameter("month")==null?"":request.getParameter("month");
		String year = request.getParameter("year")==null?"":request.getParameter("year");			
		String flg = request.getParameter("flg")==null?"":request.getParameter("flg");
		
		String trader_abbr = request.getParameter("trader_abbr")==null?"":request.getParameter("trader_abbr");
		String trader_cd  = request.getParameter("trader_cd")==null?"":request.getParameter("trader_cd");				
		String fin_year  = request.getParameter("fin_year")==null?"":request.getParameter("fin_year");	
		String hedge_no = request.getParameter("hedge_no")==null?"":request.getParameter("hedge_no");
		String hedge_seq_no = request.getParameter("hedge_seq_no")==null?"0":request.getParameter("hedge_seq_no");
		String hedge_rollover_no = request.getParameter("hedge_rollover_no")==null?"0":request.getParameter("hedge_rollover_no");
					
		String remit_dt    	 =request.getParameter("remit_dt")==null?"":request.getParameter("remit_dt");
		String bank_charges	 =request.getParameter("bank_charges")==null?"":request.getParameter("bank_charges");			
		
		String hedge_payment = request.getParameter("hedge_payment")==null?"":request.getParameter("hedge_payment");		
		String total_payment = request.getParameter("total_payment")==null?"":request.getParameter("total_payment");			
		String payment_ref_no = request.getParameter("payment_ref_no")==null?"":request.getParameter("payment_ref_no");		
		String remark = request.getParameter("remark")==null?"":request.getParameter("remark");
		String remit_flag = request.getParameter("remit_flag")==null?"N":request.getParameter("remit_flag");
		String cancellation_flag = request.getParameter("cancellation_flag")==null?"N":request.getParameter("cancellation_flag");
		
		String cancellation_dt = request.getParameter("cancellation_dt")==null?"":request.getParameter("cancellation_dt");
		String cancel_remark = request.getParameter("cancel_remark")==null?"":request.getParameter("cancel_remark");
		cancel_remark = obj.replaceSingleQuotes(cancel_remark);
		
		String hedge_exg_rate_dt = request.getParameter("hedge_exg_rate_dt")==null?"":request.getParameter("hedge_exg_rate_dt");
		String diff_market = "";
		String market2 = "";
		remark = obj.replaceSingleQuotes(remark);
				
		//Following Varibles for displaying values of Hedge Data after inserting
		String bank_cd = request.getParameter("bank_cd")==null?"":request.getParameter("bank_cd");
		String bank_nm = request.getParameter("bank_nm")==null?"":request.getParameter("bank_nm");
		String bank_rating = request.getParameter("bank_rating")==null?"":request.getParameter("bank_rating");		
		String bank_contract_no = request.getParameter("bank_contract_no")==null?"":request.getParameter("bank_contract_no");				
		String hedge_contract_dt = request.getParameter("hedge_contract_dt")==null?"":request.getParameter("hedge_contract_dt");
		String hedge_value_dt = request.getParameter("hedge_value_dt")==null?"":request.getParameter("hedge_value_dt");
		String hedge_value = request.getParameter("hedge_value")==null?"":request.getParameter("hedge_value");
		String total = request.getParameter("hedge_rate")==null?"":request.getParameter("hedge_rate");
		//System.out.println("hedge_value  : "+hedge_value+", total : "+total+", hedge_exg_rate_dt : "+hedge_exg_rate_dt);
		if(!hedge_value.trim().equals("") && !total.trim().equals("") && !hedge_exg_rate_dt.trim().equals(""))
		{
			diff_market ="" + nf2.format(Double.parseDouble(hedge_exg_rate_dt) -  Double.parseDouble(total));
			market2  = "" + nf3.format(Double.parseDouble(diff_market) * Double.parseDouble(hedge_value));
		}
		else
		{
			diff_market = "";
			market2 = "";
		}
		//System.out.println("flg  : "+flg+", diff_market : "+diff_market+", market2 : "+market2);
		try
		{
			if(flg.equals("insert"))
			{
				query = "insert into FMS7_BANK_REMITTANCE_DTL (HEDGE_ROLLOVER_NO, HEDGE_SEQ_NO,FIN_YEAR,REMITTANCE_DT,BANK_CHARGES,HEDGE_PAYMENT,TOTAL_PAYMENT," +
						"PAYMENT_REF_NO,REMARK,REMITTANCE_FLAG,EMP_CD, ENT_DT, FLAG, HEDGE_EXG_RT_VAL_DT, CANCELLATION_FLAG, CANCEL_REMARK, CANCELLATION_DT) " + 
						"values('"+hedge_rollover_no+"','"+hedge_no+"','"+fin_year+"',to_date('"+remit_dt+"','dd/mm/yyyy'),'"+bank_charges+"','"+hedge_payment+"'," +
						"'"+total_payment+"','"+payment_ref_no+"','"+remark+"','"+remit_flag+"','"+user_cd+"',sysdate,'Y','"+hedge_exg_rate_dt+"'," +
						"'"+cancellation_flag.trim()+"','"+cancel_remark+"',to_date('"+cancellation_dt+"','dd/mm/yyyy'))";
				System.out.println("Insert into FMS7_BANK_REMITTANCE_DTL  : "+query);			
				stmt.executeUpdate(query);
				
				if(remit_flag.trim().equalsIgnoreCase("Y"))
				{
					query ="update FMS7_HEDGE_MST SET HEDGE_POS='N' WHERE FIN_YEAR='"+fin_year+"' AND HEDGE_SEQ_NO='"+hedge_no+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";					
					System.out.println("Update FMS7_HEDGE_DTL  : "+query);			
					stmt.executeUpdate(query);
				}
				else if(cancellation_flag.trim().equalsIgnoreCase("Y"))
				{
					query ="update FMS7_HEDGE_MST SET HEDGE_POS='C' WHERE FIN_YEAR='"+fin_year+"' AND HEDGE_SEQ_NO='"+hedge_no+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";					
					System.out.println("Update FMS7_HEDGE_DTL  : "+query);			
					stmt.executeUpdate(query);
				}
				else
				{
					query ="update FMS7_HEDGE_MST SET HEDGE_POS='Y' WHERE FIN_YEAR='"+fin_year+"' AND HEDGE_SEQ_NO='"+hedge_no+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";					
					System.out.println("Update FMS7_HEDGE_DTL  : "+query);			
					stmt.executeUpdate(query);
				}
				
				msg="Bank Remitance Details Inserted Successfully For The Hedge NO: "+hedge_no+" !!!";
				url="../accounting/frm_bank_remittance.jsp?hedge_no="+hedge_no+"&fin_year="+fin_year+"&remit_dt="+remit_dt+"&bank_charges="+bank_charges+"&trader_cd="+trader_cd+"&hedge_payment="+hedge_payment+"&month="+month+"&year="+year+"&fin_year="+fin_year+"&bank_cd="+bank_cd+"&bank_nm="+bank_nm+"&bank_rating="+bank_rating+"&bank_contract_no="+bank_contract_no+"&hedge_contract_dt="+hedge_contract_dt+"&hedge_value_dt="+hedge_value_dt+"&hedge_value="+hedge_value+"&hedge_rate="+total+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission+"&hedge_rollover_no="+hedge_rollover_no;
			}
			else if(flg.equals("update"))
			{
				queryString = "select HEDGE_SEQ_NO,FIN_YEAR,HEDGE_ROLLOVER_NO from FMS7_BANK_REMITTANCE_DTL WHERE fin_year='"+fin_year+"' AND HEDGE_SEQ_NO="+hedge_no+" AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";
				System.out.println("select FMS7_BANK_REMITTANCE_DTL = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					//Delete Records ...
					queryString ="delete from FMS7_BANK_REMITTANCE_DTL where HEDGE_SEQ_NO='"+hedge_no+"' AND FIN_YEAR='"+fin_year+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";
					System.out.println("Delete Query (1) = "+queryString);		
					stmt.executeUpdate(queryString)	;
				}
								
				//Insert Records ...
				query = "insert into FMS7_BANK_REMITTANCE_DTL (HEDGE_ROLLOVER_NO, HEDGE_SEQ_NO, FIN_YEAR, REMITTANCE_DT, BANK_CHARGES, HEDGE_PAYMENT, TOTAL_PAYMENT," +
						"PAYMENT_REF_NO, REMARK, REMITTANCE_FLAG, EMP_CD, ENT_DT, FLAG, HEDGE_EXG_RT_VAL_DT, CANCELLATION_FLAG, CANCEL_REMARK, CANCELLATION_DT) " + 
						"values('"+hedge_rollover_no+"','"+hedge_no+"','"+fin_year+"',to_date('"+remit_dt+"','dd/mm/yyyy'),'"+bank_charges+"','"+hedge_payment+"'," +
						"'"+total_payment+"','"+payment_ref_no+"','"+remark+"','"+remit_flag+"','"+user_cd+"',sysdate,'Y','"+hedge_exg_rate_dt+"'," +
						"'"+cancellation_flag.trim()+"','"+cancel_remark+"',to_date('"+cancellation_dt+"','dd/mm/yyyy'))";
				System.out.println("Update into FMS7_BANK_REMITTANCE_DTL  : "+query);			
				stmt.executeUpdate(query);
								
				if(remit_flag.trim().equalsIgnoreCase("Y"))
				{
					query ="update FMS7_HEDGE_MST SET HEDGE_POS='N' WHERE FIN_YEAR='"+fin_year+"' AND HEDGE_SEQ_NO='"+hedge_no+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";					
					System.out.println("Update FMS7_HEDGE_DTL  : "+query);			
					stmt.executeUpdate(query);
				}
				else if(cancellation_flag.trim().equalsIgnoreCase("Y"))
				{
					query ="update FMS7_HEDGE_MST SET HEDGE_POS='C' WHERE FIN_YEAR='"+fin_year+"' AND HEDGE_SEQ_NO='"+hedge_no+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";					
					System.out.println("Update FMS7_HEDGE_DTL  : "+query);			
					stmt.executeUpdate(query);
				}
				else
				{
					query ="update FMS7_HEDGE_MST SET HEDGE_POS='Y' WHERE FIN_YEAR='"+fin_year+"' AND HEDGE_SEQ_NO='"+hedge_no+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";					
					System.out.println("Update FMS7_HEDGE_DTL  : "+query);			
					stmt.executeUpdate(query);
				}
				
				msg="Bank Remitance Details Updated Successfully For The Hedge NO: "+hedge_no+" !!!";
				url="../accounting/frm_bank_remittance.jsp?hedge_no="+hedge_no+"&fin_year="+fin_year+"&remit_dt="+remit_dt+"&bank_charges="+bank_charges+"&trader_cd="+trader_cd+"&hedge_payment="+hedge_payment+"&month="+month+"&year="+year+"&fin_year="+fin_year+"&bank_cd="+bank_cd+"&bank_nm="+bank_nm+"&bank_rating="+bank_rating+"&bank_contract_no="+bank_contract_no+"&hedge_contract_dt="+hedge_contract_dt+"&hedge_value_dt="+hedge_value_dt+"&hedge_value="+hedge_value+"&hedge_rate="+total+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission+"&hedge_rollover_no="+hedge_rollover_no;								
			}
			dbcon.commit();
			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="Bank Remitance Details Failed To Submit !!!";
			e.printStackTrace();
			System.out.println("Exception in the "+methodName+" of "+servletName+" "+e);
			url="../accounting/frm_bank_remittance.jsp?msg="+msg+"&flg=insert&activity=insert&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;			
		}
	}
	
	
	//Function for Insert and Updating Hedge Register on 16-09-2010 by Achal
	public void HedgeRegister(HttpServletRequest request,HttpServletResponse res) throws SQLException
	{
		form_name = "frm_hedge_register.jsp";		
		//escapeSingleQuotes obj = new  escapeSingleQuotes();
		HttpSession session = request.getSession();
		String methodName="HedgeRegister()";
		
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		String month = request.getParameter("month")==null?"":request.getParameter("month");
		String year = request.getParameter("year")==null?"":request.getParameter("year");
		String fin_year = request.getParameter("fin_year")==null?"":request.getParameter("fin_year");
		
		String flg = request.getParameter("flg")==null?"":request.getParameter("flg");
		
		String [] cargo_ref_no_arr  = request.getParameterValues("cargo_ref_no");	
		String [] trd_cd_arr  = request.getParameterValues("trd_cd");				
		String [] trd_abbr_arr = request.getParameterValues("trd_abbr");
		String [] inv_no_arr = request.getParameterValues("inv_no");
		String [] inv_amt_arr = request.getParameterValues("inv_amt");		
		String [] inv_dt_arr = request.getParameterValues("inv_dt");
		String [] due_dt_arr = request.getParameterValues("due_dt");
		String [] final_invoice_value_arr = request.getParameterValues("final_invoice_value");
		
		String cargo_ref = "";
		String trader_cd = "";
		String trader_abbr = "";
		String invoice_value = "";
		String final_inv_no = "";
		String final_inv_dt = "";
		String due_dt = "";
		String final_invoice_value = "";
		
		for(int i=0; i<cargo_ref_no_arr.length; i++)
		{
			cargo_ref += cargo_ref_no_arr[i] + "~~";
			invoice_value += inv_amt_arr[i] + "~~";
			final_inv_no += inv_no_arr[i] + "~~";
			final_inv_dt += inv_dt_arr[i] + "~~";
			due_dt += due_dt_arr[i] + "~~";
			trader_abbr += trd_abbr_arr[i] + "~~";
			trader_cd += trd_cd_arr[i] + "~~";
			final_invoice_value += final_invoice_value_arr[i] + "~~";
		}
		
		String hedge_no = request.getParameter("hedge_no")==null?"":request.getParameter("hedge_no");
		String hedge_seq_no = request.getParameter("hedge_seq_no")==null?"0":request.getParameter("hedge_seq_no");
		String hedge_rollover_no = request.getParameter("hedge_rollover_no")==null?"0":request.getParameter("hedge_rollover_no");
		String bank_cd = request.getParameter("bank_cd")==null?"":request.getParameter("bank_cd");
		String bank_rating = request.getParameter("bank_rating")==null?"":request.getParameter("bank_rating");		
		String bank_contract_no = request.getParameter("bank_contract_no")==null?"":request.getParameter("bank_contract_no");
		
		String total_inv_value = request.getParameter("total_inv_value")==null?"":request.getParameter("total_inv_value");
		String total_hedge_value = request.getParameter("total_hedge_value")==null?"":request.getParameter("total_hedge_value");
		String hedge_contract_dt = request.getParameter("hedge_contract_dt")==null?"":request.getParameter("hedge_contract_dt");
		String hedge_value_dt = request.getParameter("hedge_value_dt")==null?"":request.getParameter("hedge_value_dt");
		
		String final_inv_amt = request.getParameter("final_inv_amt")==null?"":request.getParameter("final_inv_amt");
		String final_inv_amt_gain_loss = request.getParameter("final_inv_amt_gain_loss")==null?"":request.getParameter("final_inv_amt_gain_loss");
		String hedge_gain_loss = request.getParameter("hedge_gain_loss")==null?"":request.getParameter("hedge_gain_loss");
		String hedge_m2m_die = request.getParameter("hedge_m2m_die")==null?"":request.getParameter("hedge_m2m_die");
		
		String spot_rate = request.getParameter("spot_rate")==null?"":request.getParameter("spot_rate");		
		String premium = request.getParameter("premium")==null?"":request.getParameter("premium");		
		String margin = request.getParameter("margin")==null?"":request.getParameter("margin");
		String total = request.getParameter("total")==null?"":request.getParameter("total");		
		String hedge_underlying = request.getParameter("hedge_underlying")==null?"":request.getParameter("hedge_underlying");
		String hedge_pos = request.getParameter("rd3")==null?"Y":request.getParameter("rd3");		
		String hedge_exg_rate_dt = request.getParameter("hedge_exg_rate_dt")==null?"":request.getParameter("hedge_exg_rate_dt");
		String exg_rt_on_prov_inv_dt = request.getParameter("exg_rt_on_prov_inv_dt")==null?"":request.getParameter("exg_rt_on_prov_inv_dt");
		
		String hedge_die = request.getParameter("hedge_die")==null?"":request.getParameter("hedge_die");
		String hedge_m2m = request.getParameter("hedge_m2m")==null?"":request.getParameter("hedge_m2m");
		System.out.println("flg  : "+flg);
		
		try
		{
			if(flg.equals("insert"))
			{
				if(cargo_ref_no_arr.length>0)
				{
					if(inv_dt_arr[cargo_ref_no_arr.length-1].trim().length()>=10)
					{
						String year_part = inv_dt_arr[cargo_ref_no_arr.length-1].trim().substring(6);
						String month_part = inv_dt_arr[cargo_ref_no_arr.length-1].trim().substring(3,5);
						
						if(Integer.parseInt(month_part)<=3)
						{
							fin_year = ""+(Integer.parseInt(year_part)-1)+"-"+year_part;						
						}
						else if(Integer.parseInt(month_part)>3)
						{
							fin_year = ""+year_part+"-"+(Integer.parseInt(year_part)+1);
						}
					}
				
					query = "INSERT INTO FMS7_HEDGE_MST(HEDGE_SEQ_NO, FIN_YEAR, HEDGE_CON_DT, HEDGE_VAL_DT, " +
							"BANK_CD, CREDIT_RATING, BANK_CONTR_NO, HEDGE_VALUE ,HEDGE_EXG_RT_VAL_DT," +
							"EXG_RT_ON_PROV_INV_DT, HEDGE_SPOT_RT," +
							"HEDGE_PREMIUM,HEDGE_MARGIN, HEDGE_TOTAL, HEDGE_UNDER, " +
							"HEDGE_POS, DIFF_EXCHG, MARKET2,EMP_CD, ENT_DT, FLAG, " +
							"M2M_DIE, GAIN_LOSS, FINAL_INV_AMT, FINAL_INV_AMT_GAIN_LOSS) " + 
							"VALUES('"+hedge_no+"', '"+fin_year+"', to_date('"+hedge_contract_dt+"','dd/mm/yyyy'), " +
							"to_date('"+hedge_value_dt+"','dd/mm/yyyy'), '"+bank_cd+"', '"+bank_rating+"', " +
							"'"+bank_contract_no+"', '"+total_hedge_value+"', '"+hedge_exg_rate_dt+"', " +
							"'"+exg_rt_on_prov_inv_dt+"', '"+spot_rate+"', '"+premium+"', '"+margin+"', " +
							"'"+total+"', '"+hedge_underlying+"', '"+hedge_pos+"', '"+hedge_die+"', " +
							"'"+hedge_m2m+"', '"+user_cd+"', sysdate, 'Y', " +
							"'"+hedge_m2m_die+"', '"+hedge_gain_loss+"', '"+final_inv_amt+"', '"+final_inv_amt_gain_loss+"')";
					System.out.println("Query For Inserting Values In FMS7_HEDGE_MST = "+query);			
					stmt.executeUpdate(query);
					
					for(int i=0; i<cargo_ref_no_arr.length; i++)
					{
						query = "INSERT INTO FMS7_HEDGE_DTL(HEDGE_SEQ_NO, FIN_YEAR, TRADER_CD, " +
								"PROV_INVOICE_NO, CARGO_REF_NO, INVOICE_DT, DUE_DT, INVOICE_AMT, " +
								"EMP_CD, ENT_DT, FLAG) " + 
								"VALUES('"+hedge_no+"', '"+fin_year+"', '"+trd_cd_arr[i]+"', " +
								"'"+inv_no_arr[i]+"', '"+cargo_ref_no_arr[i]+"', " +
								"to_date('"+inv_dt_arr[i]+"','dd/mm/yyyy'), to_date('"+due_dt_arr[i]+"','dd/mm/yyyy'), " +
								"'"+inv_amt_arr[i]+"', '"+user_cd+"', sysdate, 'Y')";
						System.out.println("Query For Inserting Values In FMS7_HEDGE_DTL = "+query);			
						stmt.executeUpdate(query);
					}
					
					msg="Hedge Register Details Inserted Successfully For The Bank Hedge Contract NO: "+bank_contract_no+" !!!";
				}
				else
				{
					msg="Minimum One Provisional Invoice Should Exist For New Hedge Generation !!!";
				}				
				
				url="../accounting/frm_hedge_register.jsp?total_inv_value="+total_inv_value+"&cargo_ref="+cargo_ref+"&trader_abbr="+trader_abbr+"&invoice_value="+invoice_value+"&final_inv_no="+final_inv_no+"&final_inv_dt="+final_inv_dt+"&due_dt="+due_dt+"&trader_cd="+trader_cd+"&hedge_no="+hedge_no+"&month="+month+"&year="+year+"&fin_year="+fin_year+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission+"&final_invoice_value="+final_invoice_value;
			}
			else if(flg.equals("update"))
			{
				if(cargo_ref_no_arr.length>0)
				{
					queryString = "SELECT HEDGE_SEQ_NO, FIN_YEAR " +
								  "FROM FMS7_HEDGE_MST WHERE " +
								  "FIN_YEAR='"+fin_year+"' AND " +
								  "HEDGE_SEQ_NO="+hedge_no+" AND " +
								  "HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";
					System.out.println("Query For Selecting Record Of Existing HEDGE Sequence NO = "+queryString);
					rset = stmt.executeQuery(queryString);
					if(rset.next())
					{
						//Delete Records ...
						queryString = "DELETE FROM FMS7_HEDGE_MST WHERE HEDGE_SEQ_NO='"+hedge_no+"' AND FIN_YEAR='"+fin_year+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";
						System.out.println("Delete Query For FMS7_HEDGE_MST = "+queryString);		
						stmt.executeUpdate(queryString);
						
						queryString = "DELETE FROM FMS7_HEDGE_DTL WHERE HEDGE_SEQ_NO='"+hedge_no+"' AND FIN_YEAR='"+fin_year+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";
						System.out.println("Delete Query For FMS7_HEDGE_DTL = "+queryString);		
						stmt.executeUpdate(queryString);
					}
									
					//Insert Records ...
					query = "INSERT INTO FMS7_HEDGE_MST(HEDGE_SEQ_NO, FIN_YEAR, HEDGE_CON_DT, HEDGE_VAL_DT, " +
							"BANK_CD, CREDIT_RATING, BANK_CONTR_NO, HEDGE_VALUE ,HEDGE_EXG_RT_VAL_DT," +
							"EXG_RT_ON_PROV_INV_DT, HEDGE_SPOT_RT," +
							"HEDGE_PREMIUM,HEDGE_MARGIN, HEDGE_TOTAL, HEDGE_UNDER, " +
							"HEDGE_POS, DIFF_EXCHG, MARKET2,EMP_CD, ENT_DT, FLAG, HEDGE_ROLLOVER_NO, " +
							"M2M_DIE, GAIN_LOSS, FINAL_INV_AMT, FINAL_INV_AMT_GAIN_LOSS) " +  
							"VALUES('"+hedge_no+"', '"+fin_year+"', to_date('"+hedge_contract_dt+"','dd/mm/yyyy'), " +
							"to_date('"+hedge_value_dt+"','dd/mm/yyyy'), '"+bank_cd+"', '"+bank_rating+"', " +
							"'"+bank_contract_no+"', '"+total_hedge_value+"', '"+hedge_exg_rate_dt+"', " +
							"'"+exg_rt_on_prov_inv_dt+"', '"+spot_rate+"', '"+premium+"', '"+margin+"', " +
							"'"+total+"', '"+hedge_underlying+"', '"+hedge_pos+"', '"+hedge_die+"', " +
							"'"+hedge_m2m+"', '"+user_cd+"', sysdate, 'Y', '"+hedge_rollover_no+"', " +
							"'"+hedge_m2m_die+"', '"+hedge_gain_loss+"', '"+final_inv_amt+"', '"+final_inv_amt_gain_loss+"')";
					System.out.println("Query For Inserting Values In FMS7_HEDGE_MST = "+query);			
					stmt.executeUpdate(query);
					
					for(int i=0; i<cargo_ref_no_arr.length; i++)
					{
						query = "INSERT INTO FMS7_HEDGE_DTL(HEDGE_SEQ_NO, FIN_YEAR, TRADER_CD, " +
								"PROV_INVOICE_NO, CARGO_REF_NO, INVOICE_DT, DUE_DT, INVOICE_AMT, " +
								"EMP_CD, ENT_DT, FLAG, HEDGE_ROLLOVER_NO) " + 
								"VALUES('"+hedge_no+"', '"+fin_year+"', '"+trd_cd_arr[i]+"', " +
								"'"+inv_no_arr[i]+"', '"+cargo_ref_no_arr[i]+"', " +
								"to_date('"+inv_dt_arr[i]+"','dd/mm/yyyy'), to_date('"+due_dt_arr[i]+"','dd/mm/yyyy'), " +
								"'"+inv_amt_arr[i]+"', '"+user_cd+"', sysdate, 'Y', '"+hedge_rollover_no+"')";
						System.out.println("Query For Inserting Values In FMS7_HEDGE_DTL = "+query);			
						stmt.executeUpdate(query);
					}
					
					msg="Hedge Register Details Modified Successfully For The Bank Hedge Contract NO: "+bank_contract_no+" !!!";
				}
				else
				{
					msg="Minimum One Provisional Invoice Should Exist For Hedge Modification !!!";
				}								
				
				url="../accounting/frm_hedge_register.jsp?total_inv_value="+total_inv_value+"&cargo_ref="+cargo_ref+"&trader_abbr="+trader_abbr+"&invoice_value="+invoice_value+"&final_inv_no="+final_inv_no+"&final_inv_dt="+final_inv_dt+"&due_dt="+due_dt+"&trader_cd="+trader_cd+"&hedge_no="+hedge_no+"&month="+month+"&year="+year+"&fin_year="+fin_year+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission+"&hedge_rollover_no="+hedge_rollover_no+"&final_invoice_value="+final_invoice_value;				
			}
			else if(flg.equals("rollover"))
			{
				if(cargo_ref_no_arr.length>0)
				{
					hedge_rollover_no = ""+(Integer.parseInt(hedge_rollover_no)+1);
					
					queryString = "SELECT HEDGE_SEQ_NO, FIN_YEAR " +
								  "FROM FMS7_HEDGE_MST WHERE " +
								  "FIN_YEAR='"+fin_year+"' AND " +
								  "HEDGE_SEQ_NO="+hedge_no+" AND " +
								  "HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";
					System.out.println("Query For Selecting Record Of Existing HEDGE Sequence NO = "+queryString);
					rset = stmt.executeQuery(queryString);
					if(rset.next())
					{
						//Delete Records ...
						queryString = "DELETE FROM FMS7_HEDGE_MST WHERE HEDGE_SEQ_NO='"+hedge_no+"' AND FIN_YEAR='"+fin_year+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";
						System.out.println("Delete Query For FMS7_HEDGE_MST = "+queryString);		
						stmt.executeUpdate(queryString);
						
						queryString = "DELETE FROM FMS7_HEDGE_DTL WHERE HEDGE_SEQ_NO='"+hedge_no+"' AND FIN_YEAR='"+fin_year+"' AND HEDGE_ROLLOVER_NO='"+hedge_rollover_no+"'";
						System.out.println("Delete Query For FMS7_HEDGE_DTL = "+queryString);		
						stmt.executeUpdate(queryString);
					}
									
					//Insert Records ...
					query = "INSERT INTO FMS7_HEDGE_MST(HEDGE_SEQ_NO, FIN_YEAR, HEDGE_CON_DT, HEDGE_VAL_DT, " +
							"BANK_CD, CREDIT_RATING, BANK_CONTR_NO, HEDGE_VALUE ,HEDGE_EXG_RT_VAL_DT," +
							"EXG_RT_ON_PROV_INV_DT, HEDGE_SPOT_RT," +
							"HEDGE_PREMIUM,HEDGE_MARGIN, HEDGE_TOTAL, HEDGE_UNDER, " +
							"HEDGE_POS, DIFF_EXCHG, MARKET2,EMP_CD, ENT_DT, FLAG, HEDGE_ROLLOVER_NO, " +
							"M2M_DIE, GAIN_LOSS, FINAL_INV_AMT, FINAL_INV_AMT_GAIN_LOSS) " +  
							"VALUES('"+hedge_no+"', '"+fin_year+"', to_date('"+hedge_contract_dt+"','dd/mm/yyyy'), " +
							"to_date('"+hedge_value_dt+"','dd/mm/yyyy'), '"+bank_cd+"', '"+bank_rating+"', " +
							"'"+bank_contract_no+"', '"+total_hedge_value+"', '"+hedge_exg_rate_dt+"', " +
							"'"+exg_rt_on_prov_inv_dt+"', '"+spot_rate+"', '"+premium+"', '"+margin+"', " +
							"'"+total+"', '"+hedge_underlying+"', '"+hedge_pos+"', '"+hedge_die+"', " +
							"'"+hedge_m2m+"', '"+user_cd+"', sysdate, 'Y', '"+hedge_rollover_no+"', " +
							"'"+hedge_m2m_die+"', '"+hedge_gain_loss+"', '"+final_inv_amt+"', '"+final_inv_amt_gain_loss+"')";
					System.out.println("Query For Inserting Values In FMS7_HEDGE_MST = "+query);			
					stmt.executeUpdate(query);
					
					for(int i=0; i<cargo_ref_no_arr.length; i++)
					{
						query = "INSERT INTO FMS7_HEDGE_DTL(HEDGE_SEQ_NO, FIN_YEAR, TRADER_CD, " +
								"PROV_INVOICE_NO, CARGO_REF_NO, INVOICE_DT, DUE_DT, INVOICE_AMT, " +
								"EMP_CD, ENT_DT, FLAG, HEDGE_ROLLOVER_NO) " + 
								"VALUES('"+hedge_no+"', '"+fin_year+"', '"+trd_cd_arr[i]+"', " +
								"'"+inv_no_arr[i]+"', '"+cargo_ref_no_arr[i]+"', " +
								"to_date('"+inv_dt_arr[i]+"','dd/mm/yyyy'), to_date('"+due_dt_arr[i]+"','dd/mm/yyyy'), " +
								"'"+inv_amt_arr[i]+"', '"+user_cd+"', sysdate, 'Y', '"+hedge_rollover_no+"')";
						System.out.println("Query For Inserting Values In FMS7_HEDGE_DTL = "+query);			
						stmt.executeUpdate(query);
					}
					
					msg="Hedge Register Details Rollovered Successfully For The Bank Hedge Contract NO: "+bank_contract_no+" !!!";
				}
				else
				{
					msg="Minimum One Provisional Invoice Should Exist For Hedge Rollover !!!";
				}								
				
				url="../accounting/frm_hedge_register.jsp?total_inv_value="+total_inv_value+"&cargo_ref="+cargo_ref+"&trader_abbr="+trader_abbr+"&invoice_value="+invoice_value+"&final_inv_no="+final_inv_no+"&final_inv_dt="+final_inv_dt+"&due_dt="+due_dt+"&trader_cd="+trader_cd+"&hedge_no="+hedge_no+"&month="+month+"&year="+year+"&fin_year="+fin_year+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission+"&hedge_rollover_no="+hedge_rollover_no+"&final_invoice_value="+final_invoice_value;				
			}
			
			dbcon.commit();
			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="Hedge Register Details Not Submitted !!!";
			System.out.println("Exception in the "+methodName+" of "+servletName+": "+e.getMessage());
			e.printStackTrace();
			url="../accounting/frm_hedge_register.jsp?total_inv_value="+total_inv_value+"&cargo_ref="+cargo_ref+"&trader_abbr="+trader_abbr+"&invoice_value="+invoice_value+"&final_inv_no="+final_inv_no+"&final_inv_dt="+final_inv_dt+"&due_dt="+due_dt+"&trader_cd="+trader_cd+"&hedge_no="+hedge_no+"&month="+month+"&year="+year+"&fin_year="+fin_year+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission+"&final_invoice_value="+final_invoice_value;			
		}
	}
	

	public void Opening_Stock_Dtls(HttpServletRequest req,HttpServletResponse res) throws SQLException
	{
		form_name = "frm_LNG_quantitative_reconciliation.jsp";		
		escapeSingleQuotes obj = new  escapeSingleQuotes();
		HttpSession session = req.getSession();
		String methodName="Opening_Stock_Dtls()";	
		
		String month = req.getParameter("month")==null?"":req.getParameter("month");
		String year = req.getParameter("year")==null?"":req.getParameter("year");
		String op_bal_trading = req.getParameter("op_bal_trading")==null?"":req.getParameter("op_bal_trading");
		String op_bal_regas = req.getParameter("op_bal_regas")==null?"":req.getParameter("op_bal_regas");
		String actual_tank_reading = req.getParameter("actual_tank_reading")==null?"":req.getParameter("actual_tank_reading");
		String remark1 = req.getParameter("remark1")==null?"":req.getParameter("remark1");
		
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		remark1 = obj.replaceSingleQuotes(remark1);
		
		try
		{
			queryString = "SELECT OP_BAL_TRADING,OP_BAL_REGAS,ACTUAL_TANK_READING,REMARK FROM FMS7_OPENING_STOCK_DTLS " +
	          			  "WHERE MONTH = '"+month+"' AND YEAR = '"+year+"' ";	
			System.out.println("Select Opening Stock Details Query = "+queryString);
			rset = stmt.executeQuery(queryString);//	Insert Records ...

			if(rset.next())
			{
				queryString = "DELETE FROM FMS7_OPENING_STOCK_DTLS " +
          			          "WHERE MONTH = '"+month+"' AND YEAR = '"+year+"' ";
				System.out.println("Delete Query For FMS7_OPENING_STOCK_DTLS = "+queryString);		
				stmt.executeUpdate(queryString);					
				
				//	Update Record ...
				query = "INSERT INTO FMS7_OPENING_STOCK_DTLS(MONTH, YEAR, OP_BAL_TRADING, OP_BAL_REGAS, ACTUAL_TANK_READING, REMARK, EMP_CD, ENT_DT, FLAG) " + 
				        "VALUES('"+month+"', '"+year+"', '"+op_bal_trading+"', '"+op_bal_regas+"',  '"+actual_tank_reading+"', '"+remark1+"', " +
						"'"+user_cd+"', sysdate, 'Y')";
				System.out.println("Query For Inserting Values In FMS7_OPENING_STOCK_DTLS = "+query);			
				stmt.executeUpdate(query);
		
				msg="Opening Stock Approved Successfully For The Month: "+month+"  and Year : "+year+"!!!";
				url="../accounting/frm_LNG_quantitative_reconciliation.jsp?month="+month+"&year="+year+"&op_bal_trading="+op_bal_trading+"&op_bal_regas="+op_bal_regas+"&actual_tank_reading="+actual_tank_reading+"&remark1="+remark1+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			else
			{
			
				query = "INSERT INTO FMS7_OPENING_STOCK_DTLS(MONTH, YEAR, OP_BAL_TRADING, OP_BAL_REGAS, ACTUAL_TANK_READING, REMARK, EMP_CD, ENT_DT, FLAG) " + 
						"VALUES('"+month+"', '"+year+"', '"+op_bal_trading+"', '"+op_bal_regas+"', '"+actual_tank_reading+"', '"+remark1+"', " +
						"'"+user_cd+"', sysdate, 'Y')";
				System.out.println("Query For Inserting Values In FMS7_OPENING_STOCK_DTLS = "+query);			
				stmt.executeUpdate(query);
			
				msg="Opening Stock Approved Successfully For The Month: "+month+"  and Year : "+year+"!!!";
				url="../accounting/frm_LNG_quantitative_reconciliation.jsp?month="+month+"&year="+year+"&op_bal_trading="+op_bal_trading+"&op_bal_regas="+op_bal_regas+"&actual_tank_reading="+actual_tank_reading+"&remark1="+remark1+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			dbcon.commit();
			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="Opening Stock Details Not Submitted !!!";
			System.out.println("Exception in the "+methodName+" of "+servletName+": "+e.getMessage());
			e.printStackTrace();
			url="../accounting/frm_LNG_quantitative_reconciliation.jsp?month="+month+"&year="+year+"&op_bal_trading="+op_bal_trading+"&op_bal_regas="+op_bal_regas+"&remark1="+remark1+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;			
		}
	}
	
	
	public void Closing_Stock_Dtls_From_Monthly_Report(HttpServletRequest req,HttpServletResponse res) throws SQLException
	{
		form_name = "rpt_monthly_energy.jsp";		
		escapeSingleQuotes obj = new  escapeSingleQuotes();
		HttpSession session = req.getSession();
		String methodName = "Closing_Stock_Dtls_From_Monthly_Report()";	
		
		String month = req.getParameter("month")==null?"":req.getParameter("month");
		String year = req.getParameter("year")==null?"":req.getParameter("year");
		String op_bal_trading = req.getParameter("op_bal_trading")==null?"0.00":req.getParameter("op_bal_trading");
		String op_bal_regas = req.getParameter("op_bal_regas")==null?"0.00":req.getParameter("op_bal_regas");
		String actual_tank_reading = req.getParameter("actual_tank_reading")==null?"0.00":req.getParameter("actual_tank_reading");
		String Closing_Stock = req.getParameter("Closing_Stock")==null?"0.00":req.getParameter("Closing_Stock");
		String remark1 = req.getParameter("remark1")==null?"":req.getParameter("remark1");
		
		String unaccounted_loss = req.getParameter("unaccounted_loss")==null?"0.00":req.getParameter("unaccounted_loss");
		
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		remark1 = obj.replaceSingleQuotes(remark1);
		String[] cust_cd= req.getParameterValues("cust_cd");//JHP
		String month_int = "";
		String year_int = "";
		String month_int1 = month;//JHP
		String year_int1 = year;//JHP
		if(!month.trim().equals("") && !year.trim().equals(""))
		{
			if(Integer.parseInt(month.trim())==12)
			{
				month_int = "01";
				year_int = ""+(Integer.parseInt(year)+1);
			}
			else
			{
				month_int = ""+(Integer.parseInt(month.trim())+1);
				year_int = year;
				if(Integer.parseInt(month_int)<=9)
				{
					month_int = "0"+month_int;
				}
			}
		}
		
		try
		{
			if(month_int.trim().length()==2 && year_int.trim().length()==4)
			{
				queryString = "SELECT OP_BAL_TRADING,OP_BAL_REGAS,ACTUAL_TANK_READING,REMARK " +
							  "FROM FMS7_OPENING_STOCK_DTLS " +
		          			  "WHERE MONTH='"+month_int+"' AND YEAR='"+year_int+"'";	
				System.out.println("Select Opening Stock Details Query = "+queryString);
				rset = stmt.executeQuery(queryString);//	Insert Records ...
	
				if(rset.next())
				{
					queryString = "DELETE FROM FMS7_OPENING_STOCK_DTLS " +
	          			          "WHERE MONTH='"+month_int+"' AND YEAR='"+year_int+"' ";
					System.out.println("Delete Query For FMS7_OPENING_STOCK_DTLS = "+queryString);		
					stmt.executeUpdate(queryString);					
					
					//Update Record ...
					query = "INSERT INTO FMS7_OPENING_STOCK_DTLS(MONTH, YEAR, OP_BAL_TRADING, OP_BAL_REGAS, REMARK, EMP_CD, ENT_DT, FLAG) " + 
					        "VALUES('"+month_int+"', '"+year_int+"', '"+op_bal_trading+"', '"+op_bal_regas+"',  '"+remark1+"', " +
							"'"+user_cd+"', sysdate, 'Y')";
					System.out.println("Query For Inserting Values In FMS7_OPENING_STOCK_DTLS = "+query);			
					stmt.executeUpdate(query);
			
					msg="Closing Stock Approved Successfully For The Month: "+month+" And Year: "+year+" !!!";				
				}
				else
				{
					query = "INSERT INTO FMS7_OPENING_STOCK_DTLS(MONTH, YEAR, OP_BAL_TRADING, OP_BAL_REGAS, REMARK, EMP_CD, ENT_DT, FLAG) " + 
							"VALUES('"+month_int+"', '"+year_int+"', '"+op_bal_trading+"', '"+op_bal_regas+"', '"+remark1+"', " +
							"'"+user_cd+"', sysdate, 'Y')";
					System.out.println("Query For Inserting Values In FMS7_OPENING_STOCK_DTLS = "+query);			
					stmt.executeUpdate(query);
				
					msg="Closing Stock Details Approved Successfully For The Month: "+month+" And Year: "+year+" !!!";				
				}
				
				//Following Query With Whole If Loop Has Been Introduced By Samik Shah On 24th August, 2011 ...
				queryString = "SELECT OP_BAL_TRADING,OP_BAL_REGAS,ACTUAL_TANK_READING,REMARK " +
							  "FROM FMS7_OPENING_STOCK_DTLS " +
			    			  "WHERE MONTH='"+month.trim()+"' AND YEAR='"+year.trim()+"'";	
				System.out.println("Select Opening Stock Details Query = "+queryString);
				rset = stmt.executeQuery(queryString);//	Insert Records ...
			
				if(rset.next())
				{
					query = "UPDATE FMS7_OPENING_STOCK_DTLS SET ACTUAL_TANK_READING="+Closing_Stock+" " +
							"WHERE MONTH='"+month.trim()+"' AND YEAR='"+year.trim()+"'";
					stmt.executeUpdate(query);
					msg="Closing Stock Details Approved Successfully For The Month: "+month+" And Year: "+year+" !!!";
				}
				
				//Following Whole If Loop Is Commented By Samik Shah On 24th August, 2011 ...
				/*if(month.trim().length()==2 && year.trim().length()==4)
				{
					queryString = "SELECT TOTAL_CONSUMPTION FROM FMS7_INTERNAL_CONSUMPTION " +
			        			  "WHERE MONTH='"+Integer.parseInt(month.trim())+"' AND YEAR='"+year.trim()+"'";	
					System.out.println("Query For Selecting TOTAL_CONSUMPTION Details From Internal Consumption Details = "+queryString);
					rset = stmt.executeQuery(queryString);
			
					if(rset.next())
					{
						if(rset.getString(1)!=null)
						{
							query = "UPDATE FMS7_INTERNAL_CONSUMPTION SET MASS_BALANCING="+unaccounted_loss+" " +
									"WHERE MONTH='"+Integer.parseInt(month.trim())+"' AND YEAR='"+year.trim()+"'";
							stmt.executeUpdate(query);
							msg="Monthly Energy Report Approved Successfully For The Month: "+month+" And Year: "+year+" !!!";
						}
					}
				}*/
				//JHP Start
				for(int i=0;i<cust_cd.length;i++)
				{
					String temp="opening"+i;
					String temp1="closing"+i;
					
    					String opeing= req.getParameter(temp);
					    String closing= req.getParameter(temp1);
					
					queryString = "SELECT OP_BAL_REGAS,CL_BAL_REGAS " +
					  "FROM FMS7_OPENING_STOCK_REGAS_CUST " +
        			  "WHERE MONTH='"+month_int1+"' AND YEAR='"+year_int1+"' AND CUSTOMER_CD='"+cust_cd[i]+"'";	
						System.out.println("Select Opening Stock Details Query = "+queryString);
						rset = stmt.executeQuery(queryString);//	Insert Records ...
				
						if(rset.next())
						{
							queryString = "DELETE FROM FMS7_OPENING_STOCK_REGAS_CUST " +
				    			          "WHERE MONTH='"+month_int1+"' AND YEAR='"+year_int1+"' AND CUSTOMER_CD='"+cust_cd[i]+"' ";
							System.out.println("Delete Query For FMS7_OPENING_STOCK_DTLS = "+queryString);		
							stmt.executeUpdate(queryString);					
							
							//Update Record ...
							query = "INSERT INTO FMS7_OPENING_STOCK_REGAS_CUST(MONTH, YEAR, OP_BAL_REGAS, CL_BAL_REGAS,CUSTOMER_CD) " + 
							        "VALUES('"+month_int1+"', '"+year_int1+"', '"+opeing+"', '"+closing+"', '"+cust_cd[i]+"')";
							System.out.println("Query For Inserting Values In FMS7_OPENING_STOCK_REGAS_CUST = "+query);			
							stmt.executeUpdate(query);
					
							//msg="Closing Stock Approved Successfully For The Month: "+month+" And Year: "+year+" !!!";				
						}
						else
						{
							query = "INSERT INTO FMS7_OPENING_STOCK_REGAS_CUST(MONTH, YEAR, OP_BAL_REGAS, CL_BAL_REGAS,CUSTOMER_CD) " + 
					        "VALUES('"+month_int1+"', '"+year_int1+"', '"+opeing+"', '"+closing+"', '"+cust_cd[i]+"')";
							System.out.println("Query For Inserting Values In FMS7_OPENING_STOCK_REGAS_CUST = "+query);			
							stmt.executeUpdate(query);
						
							//msg="Closing Stock Details Approved Successfully For The Month: "+month+" And Year: "+year+" !!!";				
						}
				}
				
				//JHP END
				
				
				url="../accounting/rpt_monthly_energy.jsp?month="+month+"&year="+year+"&remark1="+remark1+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
				dbcon.commit();
				
				try
				{
					new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
				}
				catch(Exception infoLogger)
				{
					System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
					infoLogger.printStackTrace();
				}
			}
			else
			{
				msg = "Monthly Energy Report Approval Is Pending !!!";
				url="../accounting/rpt_monthly_energy.jsp?month="+month+"&year="+year+"&remark1="+remark1+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;
			}
		}
		catch(Exception e)
		{
			msg = "Monthly Report Approval Is Pending !!!";
			System.out.println("Exception in the "+methodName+" of "+servletName+":\n"+e.getMessage());
			e.printStackTrace();
			url="../accounting/rpt_monthly_energy.jsp?month="+month+"&year="+year+"&remark1="+remark1+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;			
		}
	}
	
	
	public void Regas_SUG_Percent(HttpServletRequest request,HttpServletResponse res) throws SQLException
	{	
		form_name = "frm_LNG_quantitative_reconciliation.jsp";		
		escapeSingleQuotes obj = new  escapeSingleQuotes();
		HttpSession session = request.getSession();
		String methodName="Regas_SUG_Percent()";	
		
		String eff_date = request.getParameter("eff_date")==null?"":request.getParameter("eff_date");
		String sug_percent = request.getParameter("sug_percent")==null?"":request.getParameter("sug_percent");
		String remark2 = request.getParameter("remark2")==null?"":request.getParameter("remark2");
		
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		remark2 = obj.replaceSingleQuotes(remark2);
		
		System.out.println("remark2 = "+remark2);
		
		try
		{
			queryString = "SELECT SUG_PERCENT,REMARK FROM FMS7_REGAS_SUG_PERCENT " +
	          			  "WHERE EFF_DT = to_date('"+eff_date+"','dd/mm/yyyy') ";	
			System.out.println("Select Opening Stock Details Query = "+queryString);
			rset = stmt.executeQuery(queryString);
			
			if(rset.next())
			{
				queryString ="DELETE FROM FMS7_REGAS_SUG_PERCENT " +
		          			  "WHERE EFF_DT = to_date('"+eff_date+"','dd/mm/yyyy') ";
				System.out.println("Delete Query For FMS7_REGAS_SUG_PERCENT = "+queryString);		
				stmt.executeUpdate(queryString);		
				
				
				//	Update Record ...
				query ="INSERT INTO FMS7_REGAS_SUG_PERCENT(EFF_DT, SUG_PERCENT, REMARK, EMP_CD, ENT_DT, FLAG) " + 
						"VALUES(to_date('"+eff_date+"','dd/mm/yyyy'), '"+sug_percent+"', '"+remark2+"', '"+user_cd+"', sysdate, 'Y')";
				System.out.println("Query For Inserting Values In FMS7_REGAS_SUG_PERCENT = "+query);			
				stmt.executeUpdate(query);
				
				msg="SUG Percentage Updated Successfully For The Date: "+eff_date+"!!!";
				url="../accounting/frm_LNG_quantitative_reconciliation.jsp?eff_date="+eff_date+"&sug_percent="+sug_percent+"&remark2="+remark2+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			else
			{
				//  Insert Records ...
				query ="INSERT INTO FMS7_REGAS_SUG_PERCENT(EFF_DT, SUG_PERCENT, REMARK, EMP_CD, ENT_DT, FLAG) " + 
						"VALUES(to_date('"+eff_date+"','dd/mm/yyyy'), '"+sug_percent+"', '"+remark2+"', '"+user_cd+"', sysdate, 'Y')";
				System.out.println("Query For Inserting Values In FMS7_REGAS_SUG_PERCENT = "+query);			
				stmt.executeUpdate(query);
				
				msg="SUG Percentage Inserted Successfully For The Date: "+eff_date+"!!!";
				url="../accounting/frm_LNG_quantitative_reconciliation.jsp?eff_date="+eff_date+"&sug_percent="+sug_percent+"&remark2="+remark2+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			
			dbcon.commit();
			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="SUG Percentage  Not Submitted !!!";
			System.out.println("Exception in the "+methodName+" of "+servletName+": "+e.getMessage());
			e.printStackTrace();
			url="../accounting/frm_LNG_quantitative_reconciliation.jsp?eff_date="+eff_date+"&sug_percent="+sug_percent+"&remark2="+remark2+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;			
		}		
	}
	
	
	public void Rpt_Stock_Valuation(HttpServletRequest request,HttpServletResponse res)
	{
		form_name = "rpt_stock_valuation.jsp";		
		escapeSingleQuotes obj = new  escapeSingleQuotes();
		HttpSession session = request.getSession();
		String methodName="Rpt_Stock_Valuation()";	
		
		String month = request.getParameter("month")==null?"":request.getParameter("month");
		String year = request.getParameter("year")==null?"":request.getParameter("year");
		String cost_sales_usd = request.getParameter("cost_sales_usd")==null?"0":request.getParameter("cost_sales_usd");
		String closing_stock_usd = request.getParameter("new_value_usd")==null?"0":request.getParameter("new_value_usd");
		String closing_stock_nrv_usd = request.getParameter("total_value_usd")==null?"0":request.getParameter("total_value_usd");
		String closing_stock_inr = request.getParameter("new_value_inr")==null?"0":request.getParameter("new_value_inr");
		String closing_stock_nrv_inr = request.getParameter("total_value_inr")==null?"0":request.getParameter("total_value_inr");
		
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		
		try
		{
			queryString = "SELECT COST_SALES_USD FROM FMS7_STOCK_VALUATION " +
						  "WHERE MONTH='"+month+"' AND YEAR='"+year+"'";	
			System.out.println("Select Stock Valuation Query = "+queryString);
			rset = stmt.executeQuery(queryString);
			
			if(rset.next())
			{
				queryString = "DELETE FROM FMS7_STOCK_VALUATION " +
						      "WHERE MONTH='"+month+"' AND YEAR='"+year+"'";
				System.out.println("Delete Query For FMS7_STOCK_VALUATION = "+queryString);		
				stmt.executeUpdate(queryString);		
				
				
				//Update Record ...
				query = "INSERT INTO FMS7_STOCK_VALUATION(MONTH, YEAR, COST_SALES_USD, " +
						"CLOSING_STOCK_USD, CLOSING_STOCK_NRV_USD, CLOSING_STOCK_INR, " +
						"CLOSING_STOCK_NRV_INR, EMP_CD, ENT_DT, FLAG, APPROVAL_FLAG) " + 
						"VALUES('"+month+"', '"+year+"', '"+cost_sales_usd+"', " +
						"'"+closing_stock_usd+"', '"+closing_stock_nrv_usd+"', '"+closing_stock_inr+"', " +
						"'"+closing_stock_nrv_inr+"', '"+user_cd+"', sysdate, 'Y', 'Y')";
				System.out.println("Query For Inserting Values In FMS7_STOCK_VALUATION = "+query);			
				stmt.executeUpdate(query);
				
				msg="Stock Valuation Details Approved Successfully For The Month: "+month+" AND YEAR : "+year+"!!!";
				url="../accounting/rpt_stock_valuation.jsp?month="+month+"&year="+year+"&cost_sales_usd="+cost_sales_usd+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			else
			{
				//  Insert Records ...
				query = "INSERT INTO FMS7_STOCK_VALUATION(MONTH, YEAR, COST_SALES_USD, " +
						"CLOSING_STOCK_USD, CLOSING_STOCK_NRV_USD, CLOSING_STOCK_INR, " +
						"CLOSING_STOCK_NRV_INR, EMP_CD, ENT_DT, FLAG, APPROVAL_FLAG) " + 
						"VALUES('"+month+"', '"+year+"', '"+cost_sales_usd+"', " +
						"'"+closing_stock_usd+"', '"+closing_stock_nrv_usd+"', '"+closing_stock_inr+"', " +
						"'"+closing_stock_nrv_inr+"', '"+user_cd+"', sysdate, 'Y', 'Y')";
				System.out.println("Query For Inserting Values In FMS7_STOCK_VALUATION = "+query);			
				stmt.executeUpdate(query);
				
				msg="Stock Valuation Details Approved Successfully For The Month: "+month+" AND YEAR : "+year+"!!!";
				url="../accounting/rpt_stock_valuation.jsp?month="+month+"&year="+year+"&cost_sales_usd="+cost_sales_usd+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			dbcon.commit();			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="Stock Valuation Not Approved !!!";
			System.out.println("Exception in the "+methodName+" of "+servletName+": "+e.getMessage());
			e.printStackTrace();
			url="../accounting/rpt_stock_valuation.jsp?month="+month+"&year="+year+"&cost_sales_usd="+cost_sales_usd+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;			
		}		
	}
	
	
	public void Rpt_Stock_Valuation2(HttpServletRequest request,HttpServletResponse res)
	{
		form_name = "rpt_stock_valuation.jsp";		
		escapeSingleQuotes obj = new  escapeSingleQuotes();
		HttpSession session = request.getSession();
		String methodName="Rpt_Stock_Valuation2()";	
		
		String month = request.getParameter("month")==null?"":request.getParameter("month");
		String year = request.getParameter("year")==null?"":request.getParameter("year");
		String cost_sales_usd = request.getParameter("cost_sales_usd")==null?"0":request.getParameter("cost_sales_usd");
		String closing_stock_usd = request.getParameter("new_value_usd")==null?"0":request.getParameter("new_value_usd");
		String closing_stock_nrv_usd = request.getParameter("total_value_usd")==null?"0":request.getParameter("total_value_usd");
		String closing_stock_inr = request.getParameter("new_value_inr")==null?"0":request.getParameter("new_value_inr");
		String closing_stock_nrv_inr = request.getParameter("total_value_inr")==null?"0":request.getParameter("total_value_inr");
		
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		
		try
		{
			queryString = "SELECT COST_SALES_USD FROM FMS7_STOCK_VALUATION " +
						  "WHERE MONTH='"+month+"' AND YEAR='"+year+"'";	
			System.out.println("Select Stock Valuation Query = "+queryString);
			rset = stmt.executeQuery(queryString);
			
			if(rset.next())
			{
				queryString = "DELETE FROM FMS7_STOCK_VALUATION " +
						      "WHERE MONTH='"+month+"' AND YEAR='"+year+"'";
				System.out.println("Delete Query For FMS7_STOCK_VALUATION = "+queryString);		
				stmt.executeUpdate(queryString);		
				
				
				//	Update Record ...
				query = "INSERT INTO FMS7_STOCK_VALUATION(MONTH, YEAR, COST_SALES_USD, " +
						"CLOSING_STOCK_USD, CLOSING_STOCK_NRV_USD, CLOSING_STOCK_INR, " +
						"CLOSING_STOCK_NRV_INR, EMP_CD, ENT_DT, FLAG, APPROVAL_FLAG) " + 
						"VALUES('"+month+"', '"+year+"', '"+cost_sales_usd+"', " +
						"'"+closing_stock_usd+"', '"+closing_stock_nrv_usd+"', '"+closing_stock_inr+"', " +
						"'"+closing_stock_nrv_inr+"', '"+user_cd+"', sysdate, 'Y', 'N')";
				System.out.println("Query For Inserting Values In FMS7_STOCK_VALUATION = "+query);			
				stmt.executeUpdate(query);
				
				msg="New Closing Stock USD/INR Values Under Stock Valuation Details Saved Successfully For The Month: "+month+" AND YEAR : "+year+"!!!";
				url="../accounting/rpt_stock_valuation.jsp?month="+month+"&year="+year+"&cost_sales_usd="+cost_sales_usd+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			else
			{
				//  Insert Records ...
				query = "INSERT INTO FMS7_STOCK_VALUATION(MONTH, YEAR, COST_SALES_USD, " +
						"CLOSING_STOCK_USD, CLOSING_STOCK_NRV_USD, CLOSING_STOCK_INR, " +
						"CLOSING_STOCK_NRV_INR, EMP_CD, ENT_DT, FLAG, APPROVAL_FLAG) " + 
						"VALUES('"+month+"', '"+year+"', '"+cost_sales_usd+"', " +
						"'"+closing_stock_usd+"', '"+closing_stock_nrv_usd+"', '"+closing_stock_inr+"', " +
						"'"+closing_stock_nrv_inr+"', '"+user_cd+"', sysdate, 'Y', 'N')";
				System.out.println("Query For Inserting Values In FMS7_STOCK_VALUATION = "+query);			
				stmt.executeUpdate(query);
				
				msg="New Closing Stock USD/INR Values Under Stock Valuation Details Approved Successfully For The Month: "+month+" AND YEAR : "+year+"!!!";
				url="../accounting/rpt_stock_valuation.jsp?month="+month+"&year="+year+"&cost_sales_usd="+cost_sales_usd+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			dbcon.commit();			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="New Closing Stock USD/INR Values Under Stock Valuation Failed To Submit !!!";
			System.out.println("Exception in the "+methodName+" of "+servletName+": "+e.getMessage());
			e.printStackTrace();
			url="../accounting/rpt_stock_valuation.jsp?month="+month+"&year="+year+"&cost_sales_usd="+cost_sales_usd+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;			
		}		
	}
	
	
	public void saveExpectedSalesDetails(HttpServletRequest request,HttpServletResponse res)
	{
		form_name = "rpt_SN_wise_dtls.jsp";		
		escapeSingleQuotes obj = new  escapeSingleQuotes();
		HttpSession session = request.getSession();
		String methodName="saveExpectedSalesDetails()";	
		
		String month = request.getParameter("month")==null?"":request.getParameter("month");
		String year = request.getParameter("year")==null?"":request.getParameter("year");
		String exp_sales_qty = request.getParameter("exp_sales_qty")==null?"0":request.getParameter("exp_sales_qty");
		String exp_sales_rate = request.getParameter("exp_sales_rate")==null?"0":request.getParameter("exp_sales_rate");
		String exp_sales_amt_usd = request.getParameter("exp_sales_amt_usd")==null?"0":request.getParameter("exp_sales_amt_usd");
				
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		
		try
		{
			queryString = "SELECT EXP_SALES_QTY FROM FMS7_EXP_SALES_DTLS " +
						  "WHERE MONTH='"+month+"' AND YEAR='"+year+"'";	
			System.out.println("Select FMS7_EXP_SALES_DTLS Query = "+queryString);
			rset = stmt.executeQuery(queryString);
			
			if(rset.next())
			{
				queryString = "DELETE FROM FMS7_EXP_SALES_DTLS " +
						      "WHERE MONTH='"+month+"' AND YEAR='"+year+"'";
				System.out.println("Delete Query For FMS7_EXP_SALES_DTLS = "+queryString);		
				stmt.executeUpdate(queryString);		
				
				
				//	Update Record ...
				query = "INSERT INTO FMS7_EXP_SALES_DTLS(MONTH, YEAR, EXP_SALES_QTY, " +
						"EXP_SALES_RATE, EXP_SALES_AMT_USD, EMP_CD, ENT_DT, FLAG) " + 
						"VALUES('"+month+"', '"+year+"', '"+exp_sales_qty+"', " +
						"'"+exp_sales_rate+"', '"+exp_sales_amt_usd+"', " +
						"'"+user_cd+"', sysdate, 'Y')";
				System.out.println("Query For Inserting Values In FMS7_EXP_SALES_DTLS = "+query);			
				stmt.executeUpdate(query);
				
				msg="Expected Sales Details Approved Successfully For The Month: "+month+" AND YEAR : "+year+"!!!";
				url="../accounting/rpt_SN_wise_dtls.jsp?month="+month+"&year="+year+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			else
			{
				//  Insert Records ...
				query = "INSERT INTO FMS7_EXP_SALES_DTLS(MONTH, YEAR, EXP_SALES_QTY, " +
				"EXP_SALES_RATE, EXP_SALES_AMT_USD, EMP_CD, ENT_DT, FLAG) " + 
				"VALUES('"+month+"', '"+year+"', '"+exp_sales_qty+"', " +
				"'"+exp_sales_rate+"', '"+exp_sales_amt_usd+"', " +
				"'"+user_cd+"', sysdate, 'Y')";
				System.out.println("Query For Inserting Values In FMS7_EXP_SALES_DTLS = "+query);			
				stmt.executeUpdate(query);
				
				msg="Expected Sales Details Approved Successfully For The Month: "+month+" AND YEAR : "+year+"!!!";
				url="../accounting/rpt_SN_wise_dtls.jsp?month="+month+"&year="+year+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			dbcon.commit();			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="Expected Sales Details Failed To Approve !!!";
			System.out.println("Exception in the "+methodName+" of "+servletName+": "+e.getMessage());
			e.printStackTrace();
			url="../accounting/rpt_SN_wise_dtls.jsp?month="+month+"&year="+year+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;			
		}		
	}
	public void saveExpectedSalesDetails_DLNG(HttpServletRequest request,HttpServletResponse res)
	{
		form_name = "rpt_SN_wise_dtls.jsp";		
		escapeSingleQuotes obj = new  escapeSingleQuotes();
		HttpSession session = request.getSession();
		String methodName="saveExpectedSalesDetails()";	
		
		String month = request.getParameter("month")==null?"":request.getParameter("month");
		String year = request.getParameter("year")==null?"":request.getParameter("year");
		String exp_sales_qty = request.getParameter("exp_sales_qty")==null?"0":request.getParameter("exp_sales_qty");
		String exp_sales_rate = request.getParameter("exp_sales_rate")==null?"0":request.getParameter("exp_sales_rate");
		String exp_sales_amt_usd = request.getParameter("exp_sales_amt_usd")==null?"0":request.getParameter("exp_sales_amt_usd");
				
		String user_cd = (String)session.getAttribute("user_cd")==null?"":(String)session.getAttribute("user_cd");
		
		try
		{
			queryString = "SELECT EXP_SALES_QTY FROM FMS7_EXP_SALES_DTLS " +
						  "WHERE MONTH='"+month+"' AND YEAR='"+year+"'";	
			System.out.println("Select FMS7_EXP_SALES_DTLS Query = "+queryString);
			rset = stmt.executeQuery(queryString);
			
			if(rset.next())
			{
				queryString = "DELETE FROM FMS7_EXP_SALES_DTLS " +
						      "WHERE MONTH='"+month+"' AND YEAR='"+year+"'";
				System.out.println("Delete Query For FMS7_EXP_SALES_DTLS = "+queryString);		
				stmt.executeUpdate(queryString);		
				
				
				//	Update Record ...
				query = "INSERT INTO FMS7_EXP_SALES_DTLS(MONTH, YEAR, EXP_SALES_QTY, " +
						"EXP_SALES_RATE, EXP_SALES_AMT_USD, EMP_CD, ENT_DT, FLAG) " + 
						"VALUES('"+month+"', '"+year+"', '"+exp_sales_qty+"', " +
						"'"+exp_sales_rate+"', '"+exp_sales_amt_usd+"', " +
						"'"+user_cd+"', sysdate, 'Y')";
				System.out.println("Query For Inserting Values In FMS7_EXP_SALES_DTLS = "+query);			
				stmt.executeUpdate(query);
				
				msg="Expected Sales Details Approved Successfully For The Month: "+month+" AND YEAR : "+year+"!!!";
				url="../accounting/rpt_SN_wise_dtls.jsp?month="+month+"&year="+year+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			else
			{
				//  Insert Records ...
				query = "INSERT INTO FMS7_EXP_SALES_DTLS(MONTH, YEAR, EXP_SALES_QTY, " +
				"EXP_SALES_RATE, EXP_SALES_AMT_USD, EMP_CD, ENT_DT, FLAG) " + 
				"VALUES('"+month+"', '"+year+"', '"+exp_sales_qty+"', " +
				"'"+exp_sales_rate+"', '"+exp_sales_amt_usd+"', " +
				"'"+user_cd+"', sysdate, 'Y')";
				System.out.println("Query For Inserting Values In FMS7_EXP_SALES_DTLS = "+query);			
				stmt.executeUpdate(query);
				
				msg="Expected Sales Details Approved Successfully For The Month: "+month+" AND YEAR : "+year+"!!!";
				url="../accounting/rpt_SN_wise_dtls.jsp?month="+month+"&year="+year+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;				
			}
			dbcon.commit();			
			try
			{
				new com.hlpl.hazira.fms7.util.InfoLogger().writelog("["+session.getAttribute("username")+"/"+session.getAttribute("ip")+"]: "+form_id+"@"+form_nm+"~: "+msg);
			}
			catch(Exception infoLogger)
			{
				System.out.println("Exception While Writing into InfoLogger Servlet From "+servletName+" Servlet -->\nUnder "+methodName+" Method -->\n"+infoLogger.getMessage());
				infoLogger.printStackTrace();
			}
		}
		catch(Exception e)
		{
			msg="Expected Sales Details Failed To Approve !!!";
			System.out.println("Exception in the "+methodName+" of "+servletName+": "+e.getMessage());
			e.printStackTrace();
			url="../accounting/rpt_SN_wise_dtls.jsp?month="+month+"&year="+year+"&msg="+msg+"&flg=update&activity=update&write_permission="+write_permission+"&delete_permission="+delete_permission+"&print_permission="+print_permission+"&approve_permission="+approve_permission+"&audit_permission="+audit_permission;			
		}		
	}
	
}//End Of Class Frm_Accounting ...