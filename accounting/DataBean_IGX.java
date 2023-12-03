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
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.hlpl.hazira.fms7.util.RuntimeConf;

public class DataBean_IGX {

	private Statement stmt = null;
	private Statement stmt1 = null;
	private Statement stmt2 = null;
	private Statement stmt3 = null;
	private Statement stmt4 = null;
	private Statement stmt5 = null;
	private Connection conn = null;
	
	private ResultSet rset = null;
	private ResultSet rset1 = null;
	private ResultSet rset2 = null;
	private ResultSet rset3 = null;
	private ResultSet rset4 = null;
	private ResultSet rset5 = null;
	
	private String callFlag = ""; 
	private String queryString = "";
	
	
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
	    			
	    				checkForColumn();
	    			if(callFlag.equalsIgnoreCase("fetchIGXInvoiceDetails"))
					{
//	    				if(invstatus.equalsIgnoreCase("UNPAID")) {
	    					
	    					fetch_IGX_Invoice_Details();
	    					
//	    				}else if(invstatus.equalsIgnoreCase("PAID")) {
	    					
//	    					fetch_paid_IGX_Invoice_Details();
//	    				}
					}
	    			else if(callFlag.equalsIgnoreCase("PURCHASE_ACCOUNTING_IGX"))
					{
	    				//System.out.println("in-all ");
						get_Purchase_dtls_IGX();
						//System.out.println("in-all 1");
	    				get_Purchase_Summary_IGX(); //MD20111230
	    				//System.out.println("in-all 2");
					}
	    			else if(callFlag.equalsIgnoreCase("PURCHASE_ACCOUNTING_APPROVAL_TRANSPORTER"))
					{
	    				get_Purchase_dtls_Transporter_Approval();
					}
	    			
	    			else if(callFlag.equalsIgnoreCase("PURCHASE_ACCOUNTING_TRANSPORTER"))
					{
	    				//System.out.println("in-all ");
						get_Purchase_dtls_Transporter();
						//System.out.println("in-all 1");
	    				//get_Purchase_Summary_IGX(); //MD20111230
	    				//System.out.println("in-all 2");
					}
	    			else if(callFlag.equalsIgnoreCase("PURCHASE_ACCOUNTING_PARKING"))
					{
	    				//System.out.println("in-all ");
						get_Purchase_dtls_Parking();
						//System.out.println("in-all 1");
	    				//get_Purchase_Summary_IGX(); //MD20111230
	    				//System.out.println("in-all 2");
					}
					else if(callFlag.equalsIgnoreCase("PURCHASE_ACCOUNTING_APPROVAL_IGX"))
					{
	    				get_Purchase_dtls_IGX_Approval();
	    				get_Purchase_Summary_IGX_Approval();
	    				get_Purchase_dtls_IGX_Approval_DRCR();
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
					////System.out.println("rset4 is not close "+e);
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
					////System.out.println("stmt2 is not close "+e);
				}
				stmt5 = null;
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
	


	private void checkForColumn()throws Exception {
		int count_1 = 0;
		String query="SELECT COUNT(COLUMN_NAME) FROM COLS WHERE UPPER(TABLE_NAME) LIKE 'FMS10_IGX_PAYMENT_DTL' "
				+ "AND UPPER(COLUMN_NAME) in ('TAX_TDS_PERC','TAX_TDS_AMT') ";
		rset=stmt.executeQuery(query);
		if(rset.next())
		{
			count_1=rset.getInt(1);
		}
		if(count_1==0)
		{
			query="alter table FMS10_IGX_PAYMENT_DTL add"
					+ " (TAX_TDS_PERC NUMBER(3,2),"
					+ " TAX_TDS_AMT NUMBER(12,2))";
			stmt.executeUpdate(query);
			conn.commit();
		}
		
		query="SELECT COUNT(COLUMN_NAME) FROM COLS WHERE UPPER(TABLE_NAME) LIKE 'FMS7_INVOICE_MST' "
				+ "AND UPPER(COLUMN_NAME) in ('GROSS_TDS_PERC','GROSS_TDS_AMT') ";
		rset=stmt.executeQuery(query);
		if(rset.next())
		{
			count_1=rset.getInt(1);
		}
		if(count_1==0)
		{
			query="alter table FMS7_INVOICE_MST add"
					+ " (GROSS_TDS_PERC NUMBER(3,2),"
					+ " GROSS_TDS_AMT NUMBER(12,2))";
			stmt.executeUpdate(query);
			conn.commit();
		}
		
	}



	NumberFormat nf = new DecimalFormat("###########0.00");
	NumberFormat nf6 = new DecimalFormat("############.##");
	NumberFormat nf61 = new DecimalFormat("###########0.00");
	NumberFormat nf1 = new DecimalFormat("###########0");
	NumberFormat nf2 = new DecimalFormat("#########0.0000"); //For Currency Purpose ... Defined By Samik Shah On 1st June, 2010 ...
	NumberFormat nf3 = new DecimalFormat("###,###,###,##0.00");
	NumberFormat nf4 = new DecimalFormat("############0.000");
//	NumberFormat nf5 = new DecimalFormat("###,###,###,##0");
	NumberFormat nf5 = new DecimalFormat("###########0");
	
	public String year = "";
	public String to_month = "";
	public String to_year = "";
	public String month = "";
	String supplier_cd="1";
	Vector Vsupp_plant_Abbr_IGX=new Vector();
	Vector VImonth=new Vector();
	Vector VIcustomer_cd=new Vector();
	Vector VIcustomer_abbr=new Vector();
	Vector VIcont_typ=new Vector();
	Vector VIcustomer_name=new Vector();
	Vector VIsn_no=new Vector();
	Vector VIsn_rev_no=new Vector();
	Vector VIplant_seq_no=new Vector();
	Vector VIplant_seq_nm=new Vector();
	Vector VIperiod_st_dt=new Vector();
	Vector VIperiod_end_dt=new Vector();
	Vector VISales_rate =new Vector();
	Vector VIGross_amt =new Vector();
	Vector VITrans_charge =new Vector();
	Vector VIaggr_alloc_qty = new Vector();
	Vector VITrans_cgst =new Vector();
	Vector VITrans_sgst =new Vector();
	Vector VITrans_tot_amt =new Vector();
	Vector VIAllocCnt = new Vector();
	Vector VIinv_sub_sum_act_recv = new Vector();
	Vector VIinv_no =new Vector();
	Vector VIinv_hlpl_no =new Vector();
	Vector VIinv_fin_yr = new Vector();
	Vector VIinv_flag = new Vector();
	Vector VIinv_supp_state_cd = new Vector();
	Vector VIinv_dt =new Vector();
	Vector VIinv_net_inr = new Vector();
	Vector VIinv_due_dt = new Vector();
	Vector VIinv_due_days = new Vector();
	Vector VIinv_total_tds_amt = new Vector();
	Vector VIinv_gross_tds_perc = new Vector();
	Vector VIinv_alloc_sub_flg = new Vector();
	Vector VIinv_pay_recv_amt = new Vector();
	Vector VIinv_pay_short_recv = new Vector();
	Vector VIinv_pay_recv_dt = new Vector();
	Vector VIinv_pay_rmk = new Vector();
	Vector VIinv_tds_tax_perc = new Vector();
	Vector VIinv_tds_tax_amt = new Vector();
	Vector VIinv_view_flag = new Vector();
	Vector VIinv_gen_email = new Vector();
	Vector VIinv_chk_email = new Vector();
	Vector VIinv_aprv_email = new Vector();
	Vector VIinv_print_email = new Vector();
	Vector VIsub_rec_cnt = new Vector();
	Vector VIsub_alloc_qty = new Vector();
	Vector VIsub_Gross_amt =new Vector();
	Vector VIsub_Trans_charge =new Vector();
	Vector VIsub_Trans_cgst =new Vector();
	Vector VIsub_Trans_sgst =new Vector();
	Vector VIsub_alloc_dt =new Vector();
	Vector VIsub_due_dt =new Vector();
	Vector VIsub_nom_rev_no =new Vector();
	Vector  VIsub_tax_amt = new Vector();
	Vector  VIsub_tax_str = new Vector();
	Vector VIsub_net_payable = new Vector();
	
	Vector  VIsub_gross_tds_perc = new Vector();
	Vector  VIsub_gross_tds_amt = new Vector();
	Vector  VIsub_net_recv = new Vector();
	Vector  VIsub_actual_recv = new Vector();
	Vector  VIsub_short_recv = new Vector();
	Vector  VIsub_payment_dt = new Vector();
	Vector  VIsub_remark = new Vector();
	Vector  VIsub_due_days = new Vector();
	Vector  VIsub_tax_tds_perc = new Vector();
	Vector  VIsub_tax_tds_amt = new Vector();
	//For IGX
	String to_mon = "";
	String tds_perc="0.1";
	String mon = "";
	String plant_seq_no    	="";
	String trader_cd="";
	String exchg_rate="";
	String flag_accounting="";
	String cargo_ref_cd="";
	String all_exchg_rtdt="";
	String all_exchg_rtdt_igx="";
	String exchg_rt_dt="";
	String exchg_rt_dt_drcr="";
	Vector Dist_CARGO_REF_CD_IGX=new Vector();
	Vector Dist_INVOICE_NO_IGX=new Vector();
	Vector XML_GEN_FLAG_IGX=new Vector();
	Vector SUN_APPROVAL_IGX=new Vector();
	Vector TRANSACTION_DT_IGX=new Vector();
	Vector TOTAL_CD_AMT_NUMERIC_IGX=new Vector();
	Vector TOTAL_CD_AMT_IGX=new Vector();
	Vector SUMMRY_ACTUAL_UNLOADED_QTY_IGX=new Vector();
	Vector SUMMRY_TAX_AMT_INR_IGX=new Vector();
	Vector SUMMRY_TDS_AMT_INR_IGX=new Vector();
	Vector SUMMRY_TAX_AMT_USD_IGX=new Vector();
	Vector SUMMRY_CARGO_REF_CD_IGX=new Vector();
	Vector SUMMRY_USD_VAL_INVOICE_IGX=new Vector();
	Vector SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX=new Vector();
	Vector SUMMRY_TOTAL_CD_AMT_IGX=new Vector();
	Vector SUMMRY_TOTAL_PAID_REFUND_IGX=new Vector();
	Vector SUMMRY_CD_PAID_IGX=new Vector();
	Vector SUMMRY_INR_PER_MMBTU_IGX=new Vector();
	Vector CUSTOM_DUTY_DATE_RATE_NOTE_IGX=new Vector();
	Vector SUMMRY_CUSTOM_DUTY_USD_IGX=new Vector();
	Vector SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX=new Vector();
	Vector SUMMRY_COST_OF_PURCHASE_USD_IGX=new Vector();
	Vector SUMMRY_USD_PER_MMBTU_IGX=new Vector();
	Vector TOTAL_PAID_REFUND_NUMERIC_IGX=new Vector();
	Vector CUSTOM_DUTY_INTEREST_NUMERIC_IGX=new Vector();
	double custom_duty_interest_total_inr_igx=0;
	Vector TOTAL_PAID_REFUND_IGX=new Vector();
	Vector CD_PAID_IGX=new Vector();
	Vector CD_PAID_NUMERIC_IGX=new Vector();
	double purchase_total_addl_cd_inr_igx=0;
	Vector CUSTOM_DUTY_INTEREST_IGX=new Vector();
	Vector SPLIT_SEQ_IGX=new Vector();
	Vector ARRIVAL_DATE_RATE_NOTE_IGX=new Vector();
	double purchase_total_cost_inr_igx=0;
	double purchase_total_cd_inr_igx=0;
	Vector GROUP_FOREIGN_EXCHG_RATE_IGX=new Vector();
	Vector FINAL_SELLER_INV_AMT_INR_NUMERIC_IGX=new Vector();
	Vector GROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX=new Vector();
	Vector FINAL_SELLER_INV_AMT_INR_IGX=new Vector();
	Vector CARGO_REF_CD_IGX=new Vector();
	Vector CARGO_PRICE_IGX=new Vector();
	Vector CARGO_PRICE_FLAG=new Vector();
	Vector PAY_EXCHG_RT_DT_IGX=new Vector();
	Vector PAY_EXCHG_RT_VAL_IGX=new Vector();
	Vector Exchg_rt_Dt_IGX=new Vector();
	Vector PAY_EXCHG_RT_DT=new Vector();
	Vector PAY_EXCHG_RT_VAL=new Vector(); 
	Vector Vtitle=new Vector();
	Vector VESSEL_NM_IGX=new Vector();
	Vector inv_type_flag_IGX=new Vector();
	Vector contract_no_igx=new Vector();
	Vector contract_rev_no_igx=new Vector();
	Vector plant_cd_igx=new Vector();
	Vector TRD_CD_IGX=new Vector();
	Vector ACTUAL_UNLOADED_QTY_IGX=new Vector();
	Vector inv_type_flag=new Vector();
	Vector TCS_PERC_IGX=new Vector();
	Vector TDS_PERC_IGX=new Vector();
	Vector unloaded_qty_igx=new Vector();
	Vector ACTUAL_UNLOADED_QTY_NUMERIC_IGX=new Vector();
	Vector EXCHG_RATE_VALUE_IGX=new Vector();
	Vector INVOICE_AMT_IGX=new Vector();
	Vector INVOICE_TAX_AMT_IGX=new Vector();
	Vector ACT_ARRV_MONTH_IGX=new Vector();
	Vector INVOICE_AMT_TDS_IGX=new Vector();
	Vector INVOICE_AMT_TRANS_IGX=new Vector();
	Vector INVOICE_TAXAMT_TRANS_IGX=new Vector();
	Vector INVOICE_AMT_net_IGX=new Vector();
	Vector usdmmbtu=new Vector();
	Vector INR_PER_MMBTU_NUMERIC_IGX=new Vector();
	Vector CUSTOM_DUTY_USD_IGX=new Vector();
	Vector CUSTOM_DUTY_USD_NUMERIC_IGX=new Vector();
	double purchase_total_cd_usd_igx=0;
	Vector ADDL_CUSTOM_DUTY_USD_IGX=new Vector();
	Vector ADDL_CUSTOM_DUTY_USD_NUMERIC_IGX=new Vector();
	Vector INR_PER_MMBTU_IGX=new Vector();
	double purchase_total_addl_cd_usd_igx=0;
	double purchase_total_inv_value_usd_igx=0;
	Vector COST_OF_PURCHASE_USD_IGX=new Vector();
	Vector SUMMRY_ACT_ARRV_MONTH_IGX=new Vector();
	Vector COST_OF_PURCHASE_USD_NUMERIC_IGX=new Vector();
	double purchase_total_cost_usd_igx=0;
	Vector USD_PER_MMBTU_IGX=new Vector();
	Vector USD_PER_MMBTU_NUMERIC_IGX=new Vector();
	
	Vector ACT_ARRV_DT_IGX=new Vector();
	Vector REMARK_IGX=new Vector();
	Vector TAX_rmk_IGX=new Vector();
	Vector TAX_amt_usd_IGX=new Vector();
	Vector invoice_type_IGX=new Vector();
	Vector INVOICE_NO_IGX=new Vector();
	Vector INVOICE_DT_IGX=new Vector();
	Vector CONF_PRICE_IGX=new Vector();
	Vector CONF_PRICE_NUMERIC_IGX=new Vector();
	Vector USD_VAL_INVOICE_IGX=new Vector();
	Vector USD_VAL_INVOICE_NUMERIC_IGX=new Vector();
	Vector Gross_inr_IGX=new Vector();
	Vector Tax_inr_IGX=new Vector();
	Vector net_inr_IGX=new Vector();
	Vector payable_inr_IGX=new Vector();
	Vector DUE_DT_IGX=new Vector();
	Vector Check_flag_IGX=new Vector();
	Vector TRADER_INV_CURRENCY_IGX=new Vector();
	Vector Exchg_rt_cd=new Vector();
	Vector Authorize_flag_IGX=new Vector();
	Vector Approve_flag_IGX=new Vector();
	Vector duration_igx=new Vector();
	Vector TRADER_NAME_IGX=new Vector(); 
	Vector INVOICE_TCS_AMT_IGX=new Vector();
	Vector QTY_MSG_IGX=new Vector();
	Vector TRADER_ABBR_IGX=new Vector();
	double purchase_total_qty_igx=0;
	double purchase_total_invoice_value_igx=0;
	double purchase_total_inv_value_inr_igx = 0;
	double purchase_total_tax_inr_igx=0;
	Vector TRADER_INV_CURRENCY_DRCR=new Vector();
	Vector PAY_EXCHG_RT_DT_DRCR=new Vector();
	Vector Vsupp_plant_Abbr_DRCR=new Vector();
	Vector CARGO_REF_CD_IGX_DRCR=new Vector();
	Vector SUN_APPROVAL_IGX_DRCR=new Vector();
	Vector TRANSACTION_DT_DRCR=new Vector();
	Vector TCS_PERC_IGX_DRCR=new Vector();
	Vector TDS_PERC_IGX_DRCR=new Vector();
	String exchgrt="";
	Vector exchg_rt_val_DRCR=new Vector();
	Vector TCS_APP_FLAG_DRCR=new Vector();
	Vector TDS_APP_FLAG_DRCR=new Vector();
	Vector Gross_inr_IGX_DRCR=new Vector();
	Vector Tax_inr_IGX_DRCR=new Vector();
	Vector net_inr_IGX_DRCR=new Vector();
	Vector TCS_TDS_AMT_IGX_DRCR=new Vector();
	Vector FINAL_SELLER_INV_AMT_INR_IGX_DRCR=new Vector();
	Vector CD_PAID_IGX_DRCR=new Vector();
	Vector CD_PAID_NUMERIC_IGX_DRCR=new Vector();
	double purchase_total_inv_value_inr_igx_DRCR=0;
	double purchase_total_cost_inr_igx_DRCR=0;
	String ind="";
	Vector payable_inr_IGX_DRCR=new Vector();
	Vector PAY_EXCHG_RT_VAL_DRCR=new Vector();
	Vector VESSEL_NM_IGX_DRCR=new Vector();
	Vector inv_type_flag_DRCR=new Vector();
	Vector DR_CR_FLAG_IGX_DRCR=new Vector();
	Vector DR_CR_ORI_INV_NO=new Vector();
	Vector TRD_CD_IGX_DRCR=new Vector();
	Vector ACTUAL_UNLOADED_QTY_IGX_DRCR=new Vector();
	Vector ACTUAL_UNLOADED_QTY_NUMERIC_IGX_DRCR=new Vector();
	Vector Exchg_rt_cd_DRCR=new Vector();
	Vector TRADER_PAY_CURRENCY_DRCR=new Vector();
	Vector EXCHG_RATE_VALUE_IGX_DRCR=new Vector();
	Vector INVOICE_AMT_IGX_DRCR=new Vector();
	Vector INVOICE_TAX_AMT_IGX_DRCR=new Vector();
	Vector TRADER_PAY_CURRENCY_IGX=new Vector();
	Vector TCS_APP_FLAG_IGX=new Vector();
	Vector exchg_rt_val_IGX=new Vector();
	Vector TDS_APP_FLAG_IGX=new Vector();
	Vector ACT_ARRV_MONTH_IGX_DRCR=new Vector();
	Vector INVOICE_AMT_TDS_IGX_DRCR=new Vector();
	Vector ACT_ARRV_DT_IGX_DRCR=new Vector();
	Vector REMARK_IGX_DRCR=new Vector();
	Vector USD_VAL_INVOICE_IGX_DRCR=new Vector();
	Vector USD_VAL_INVOICE_NUMERIC_IGX_DRCR=new Vector();
	Vector TCS_TDS_AMT_IGX=new Vector();
	Vector TAX_rmk_IGX_DRCR=new Vector();
	Vector TAX_amt_usd_IGX_DRCR=new Vector();
	Vector invoice_type_IGX_DRCR=new Vector();
	Vector INVOICE_NO_IGX_DRCR=new Vector();
	Vector INVOICE_DT_IGX_DRCR=new Vector();
	Vector CONF_PRICE_IGX_DRCR=new Vector();
	Vector CONF_PRICE_NUMERIC_IGX_DRCR=new Vector();
	Vector DUE_DT_IGX_DRCR=new Vector();
	Vector Approve_flag_IGX_DRCR=new Vector();
	Vector duration_igx_DRCR=new Vector();
	Vector TRADER_NAME_IGX_DRCR=new Vector();
	Vector TRADER_ABBR_IGX_DRCR=new Vector();
	Vector INVOICE_TCS_AMT_DRCR=new Vector();
	Vector Vtitle_DRCR=new Vector();
	double purchase_total_qty_igx_DRCR=0;
	double purchase_total_invoice_value_igx_DRCR=0;
	String all_exchg_rtdt_drcr="";
	String all_exchg_rtdt_drcr_igx="";
	Vector Exchg_rt_Dt_DRCR=new Vector();
	Vector XML_GEN_FLAG_IGX_DRCR=new Vector();
	Vector Ventry_nom=new Vector();
	Vector Vcont_no=new Vector();
	Vector Vcont_agr_typ=new Vector();
	Vector Vcont_mappid=new Vector();
	Vector Vexit_nom=new Vector();
	Vector Ventry_sch=new Vector();
	Vector Vexit_sch=new Vector();
	Vector Ventry_alloc=new Vector();
	Vector Vexit_alloc=new Vector();
	Vector Vparty_abbr=new Vector();
	Vector duration_trans=new Vector();
	Vector Vinv_no=new Vector();
	Vector Vrate=new Vector();
	Vector Vqty=new Vector();
	Vector VTAXAMT=new Vector();
	Vector Vnetamt=new Vector();
	Vector Vcompo_nm=new Vector();
	Vector Vamt=new Vector();
	Vector Vmapp_id=new Vector();
	Vector Vaprv_by=new Vector();
	Vector Vverify_by=new Vector();
	double tot_entry_nom=0;
	double tot_exit_nom=0;
	double tot_entry_sch=0;
	double tot_exit_sch=0;
	double tot_entry_alloc=0;
	double tot_exit_alloc=0;
	double totamt=0;
	double totqty=0;
	Vector CONT_NO=new Vector();
	Vector TRADER_ABBR_TR=new Vector();
	Vector contract_no=new Vector();
	Vector contract_rev_no=new Vector();
	Vector INVOICE_DT_TR=new Vector();
	Vector DUE_DT_TR=new Vector();
	Vector INVOICE_MAPPING_ID_TR=new Vector();
	
	Vector REV_NO=new Vector();
	Vector PARTY_CD_TR=new Vector();
	Vector CONT_AGR_NO=new Vector();
	Vector CONT_AGR_TYPE=new Vector();
	Vector CONT_CUST_CD=new Vector();
	Vector INVOICE_NO_TR=new Vector();
	Vector INVOICE_GEN_FLAG_TR=new Vector();
	Vector GROSS_PAY_TR=new Vector();
	Vector QTY_TR=new Vector();
	Vector RATE_TR=new Vector();
	Vector TOTAL_TAX_AMT_TR=new Vector();
	Vector INVOICE_NET_AMT_TR=new Vector();
	Vector XML_GEN_FLAG_TR=new Vector();
	Vector SUN_APPROVAL_TR=new Vector();
	Vector TDS_PERC_TR=new Vector();
	Vector TDS_PERC_AMT=new Vector();
	
	//parking
	Vector Ventry_alloc_P=new Vector();
	Vector Vexit_alloc_P=new Vector();
	Vector Vcont_no_P=new Vector();
	Vector Vcont_agr_typ_P=new Vector();
	Vector Vcont_mappid_P=new Vector();
	Vector Vmapp_id_P=new Vector();
	Vector Vparty_abbr_P=new Vector();
	Vector duration_trans_P=new Vector();
	Vector Vinv_no_P=new Vector();
	Vector Vrate_P=new Vector();
	Vector Vamt_P=new Vector();
	Vector Vqty_P=new Vector();
	Vector Vaprv_by_P=new Vector();
	Vector Vverify_by_P=new Vector();
	Vector Ventry_nom_P=new Vector();
	Vector Vexit_nom_P=new Vector();
	Vector VTAXAMT_P=new Vector();
	Vector Vnetamt_P=new Vector();
	Vector Vcompo_nm_P=new Vector();
	Vector Ventry_sch_P=new Vector();
	Vector Vexit_sch_P=new Vector();
	double tot_entry_nom_P=0;
	double tot_exit_nom_P=0;
	double tot_entry_sch_P=0;
	double tot_exit_sch_P=0;
	double tot_entry_alloc_P=0;
	double tot_exit_alloc_P=0;
	double totamt_P=0;
	double totqty_P=0;
	String invstatus = "";
	private String customer_cd = "";
	
	public HttpServletRequest request = null;
	int Inv_roundoff;
	public int Read_All_Roundoff(){
		try {

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
			e.printStackTrace();
		}
		return Inv_roundoff;
	}
	
	public void get_Purchase_Summary_IGX()
	{
		if(!ACT_ARRV_MONTH_IGX.isEmpty())
		{
			//CARGO_REF_CD
			
			String chk1_igx = "";
			String chk1_tax_igx="";
			String chk1_taxusd_igx="";
			String chk1_tds_igx="";
			String chk2_igx = "";
			String chk3_igx = "";
			String chk4_igx = "";
			String chk5_igx = "";
			String chk6_igx = "";
			String chk7_igx = "";
			String chk8_igx = "";
			String chk9_igx = "";
			
			String arrv_month_igx = ACT_ARRV_MONTH_IGX.elementAt(0).toString().trim();
			double actual_unloaded_qty_igx = 0;
			double inv_taxamt = 0;
			double inv_taxamt_usd=0;
			double inv_tdsamt=0;
			String Scargo_ref_cd_igx = "";
			
			double usd_val_invoice_igx = 0;
			
			double final_seller_inv_amt_inr_igx = 0;
			double tot_cd_amt_igx = 0;
			double tot_paid_refund_igx = 0;
			double cd_paid_igx = 0;
			double custom_duty_usd_igx = 0;
			double addl_custom_duty_usd_igx = 0;
			double cost_of_purchase_usd_igx = 0;
			//COST_OF_PURCHASE_USD
			
			for(int i = 0; i<ACT_ARRV_MONTH_IGX.size(); i++)
			{
				//System.out.println(ACT_ARRV_MONTH_DOM.elementAt(i).toString().trim()+"---arrv_month_igx--"+arrv_month_igx);
				if(arrv_month_igx.equals(ACT_ARRV_MONTH_IGX.elementAt(i).toString().trim()))
				{
					//System.out.println(ACT_ARRV_MONTH.elementAt(i).toString().trim());
					Scargo_ref_cd_igx += CARGO_REF_CD_IGX.elementAt(i).toString().trim()+", ";
					
					if(!ACTUAL_UNLOADED_QTY_IGX.elementAt(i).toString().trim().equals("-"))
					{
						actual_unloaded_qty_igx += Double.parseDouble(ACTUAL_UNLOADED_QTY_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_igx += "y";
						
					}
					else
					{
						actual_unloaded_qty_igx += 0; 
						chk1_igx += "-";
					}
					if(!INVOICE_TAX_AMT_IGX.elementAt(i).toString().trim().equals("-"))
					{
						//System.out.println("-INVOICE_TAX_AMT_IGX.elementAt(i)-"+INVOICE_TAX_AMT_IGX.elementAt(i));
						inv_taxamt += Double.parseDouble(INVOICE_TAX_AMT_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_tax_igx += "y";
						
					}
					else
					{
						inv_taxamt += 0; 
						chk1_tax_igx += "-";
					}
					if(!INVOICE_AMT_TDS_IGX.elementAt(i).toString().trim().equals("-"))
					{
						//System.out.println("-INVOICE_TAX_AMT_IGX.elementAt(i)-"+INVOICE_TAX_AMT_IGX.elementAt(i));
						inv_tdsamt += Double.parseDouble(INVOICE_AMT_TDS_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_tds_igx += "y";
						
					}
					else
					{
						inv_tdsamt += 0; 
						chk1_tds_igx += "-";
					}
					if(!TAX_amt_usd_IGX.elementAt(i).toString().trim().equals("-"))
					{
						//System.out.println("-INVOICE_TAX_AMT_IGX.elementAt(i)-"+INVOICE_TAX_AMT_IGX.elementAt(i));
						inv_taxamt_usd += Double.parseDouble(TAX_amt_usd_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_taxusd_igx += "y";
						
					}
					else
					{
						inv_taxamt_usd += 0; 
						chk1_taxusd_igx += "-";
					}
					
					if(!USD_VAL_INVOICE_IGX.elementAt(i).toString().trim().equals("-"))
					{
						usd_val_invoice_igx += Double.parseDouble(USD_VAL_INVOICE_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk2_igx += "y";
					}
					else
					{
						usd_val_invoice_igx += 0; chk2_igx += "-";
					}
					
					if(!FINAL_SELLER_INV_AMT_INR_IGX.elementAt(i).toString().trim().equals("-"))
					{
						final_seller_inv_amt_inr_igx += Double.parseDouble(FINAL_SELLER_INV_AMT_INR_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk3_igx += "y";
					}
					else
					{
						final_seller_inv_amt_inr_igx += 0; chk3_igx += "-";
					}
					
					if(!TOTAL_CD_AMT_IGX.elementAt(i).toString().trim().equals("-"))
					{
						tot_cd_amt_igx += Double.parseDouble(TOTAL_CD_AMT_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk4_igx += "y";
					}
					else
					{
						tot_cd_amt_igx += 0; chk4_igx += "-";
					}
					
					if(!TOTAL_PAID_REFUND_IGX.elementAt(i).toString().trim().equals("-"))
					{
						tot_paid_refund_igx += Double.parseDouble(TOTAL_PAID_REFUND_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk5_igx += "y";
					}
					else
					{
						tot_paid_refund_igx += 0; chk5_igx += "-";
					}
					
					if(!CD_PAID_IGX.elementAt(i).toString().trim().equals("-"))
					{
						cd_paid_igx += Double.parseDouble(CD_PAID_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk6_igx += "y";
					}
					else
					{
						cd_paid_igx += 0; chk6_igx += "-";
					}
					
					if(!CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						custom_duty_usd_igx += Double.parseDouble(CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk7_igx += "y";
					}
					else
					{
						custom_duty_usd_igx += 0; chk7_igx += "-";
					}
					
					if(!ADDL_CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						addl_custom_duty_usd_igx += Double.parseDouble(ADDL_CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk8_igx += "y";
					}
					else
					{
						addl_custom_duty_usd_igx += 0; chk8_igx += "-";
					}
					
					if(!COST_OF_PURCHASE_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						cost_of_purchase_usd_igx += Double.parseDouble(COST_OF_PURCHASE_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk9_igx += "y";
					}
					else
					{
						cost_of_purchase_usd_igx += 0; chk9_igx += "-";
					}
					//
					
				}
				else
				{
					
					SUMMRY_ACT_ARRV_MONTH_IGX.add(""+arrv_month_igx);
					if(chk1_igx.contains("y"))
					{
						SUMMRY_ACTUAL_UNLOADED_QTY_IGX.add(nf3.format(actual_unloaded_qty_igx));
					}
					else
					{
						SUMMRY_ACTUAL_UNLOADED_QTY_IGX.add("-");
					}
					
					if(chk1_tax_igx.contains("y"))
					{
						SUMMRY_TAX_AMT_INR_IGX.add(nf3.format(inv_taxamt));
					}
					else
					{
						SUMMRY_TAX_AMT_INR_IGX.add("-");
					}
					if(chk1_tds_igx.contains("y"))
					{
						SUMMRY_TDS_AMT_INR_IGX.add(nf3.format(inv_tdsamt));
					}
					else
					{
						SUMMRY_TDS_AMT_INR_IGX.add("-");
					}
					if(chk1_taxusd_igx.contains("y"))
					{
						SUMMRY_TAX_AMT_USD_IGX.add(nf3.format(inv_taxamt_usd));
					}
					else
					{
						SUMMRY_TAX_AMT_USD_IGX.add("-");
					}
					
					SUMMRY_CARGO_REF_CD_IGX.add(Scargo_ref_cd_igx);
					if(chk2_igx.contains("y"))
					{
						SUMMRY_USD_VAL_INVOICE_IGX.add(nf3.format(usd_val_invoice_igx));
					}
					else
					{
						SUMMRY_USD_VAL_INVOICE_IGX.add("-");
					}
					if(chk3_igx.contains("y"))
					{
						SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX.add(nf3.format(final_seller_inv_amt_inr_igx));
					}
					else
					{
						SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX.add("-");
					}
					if(chk4_igx.contains("y"))
					{
						SUMMRY_TOTAL_CD_AMT_IGX.add(nf3.format(tot_cd_amt_igx));
					}
					else
					{
						SUMMRY_TOTAL_CD_AMT_IGX.add("-");
					}
					if(chk5_igx.contains("y"))
					{
						SUMMRY_TOTAL_PAID_REFUND_IGX.add(nf3.format(tot_paid_refund_igx));
					}
					else
					{
						SUMMRY_TOTAL_PAID_REFUND_IGX.add("-");
					}
					if(chk6_igx.contains("y"))
					{
						SUMMRY_CD_PAID_IGX.add(nf3.format(cd_paid_igx));
					}
					else
					{
						SUMMRY_CD_PAID_IGX.add("-");
					}
					if(actual_unloaded_qty_igx != 0)
					{
						SUMMRY_INR_PER_MMBTU_IGX.add(nf2.format(cd_paid_igx/actual_unloaded_qty_igx));
					}
					else
					{
						SUMMRY_INR_PER_MMBTU_IGX.add("-"); 
					}
					
					if(chk7_igx.contains("y"))
					{
						SUMMRY_CUSTOM_DUTY_USD_IGX.add(nf3.format(custom_duty_usd_igx));
					}
					else
					{
						SUMMRY_CUSTOM_DUTY_USD_IGX.add("-");
					}
					if(chk8_igx.contains("y"))
					{
						SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX.add(nf3.format(addl_custom_duty_usd_igx));
					}
					else
					{
						SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX.add("-");
					}
					if(chk9_igx.contains("y"))
					{
						SUMMRY_COST_OF_PURCHASE_USD_IGX.add(nf3.format(cost_of_purchase_usd_igx));
					}
					else
					{
						SUMMRY_COST_OF_PURCHASE_USD_IGX.add("-");
					}
					if(actual_unloaded_qty_igx != 0)
					{
//						System.out.println("cost_of_purchase_usd_igx---"+cost_of_purchase_usd_igx);
//						System.out.println("actual_unloaded_qty_igx---"+actual_unloaded_qty_igx);
						//SUMMRY_USD_PER_MMBTU_IGX.add(nf.format((cost_of_purchase_usd_igx/actual_unloaded_qty_igx)));
						SUMMRY_USD_PER_MMBTU_IGX.add("");
					}
					else
					{
						SUMMRY_USD_PER_MMBTU_IGX.add("-"); 
					}
					
					Scargo_ref_cd_igx = "";
					
					Scargo_ref_cd_igx = CARGO_REF_CD_IGX.elementAt(i).toString().trim()+", ";
					if(!ACTUAL_UNLOADED_QTY_IGX.elementAt(i).toString().trim().equals("-"))
					{
						actual_unloaded_qty_igx = Double.parseDouble(ACTUAL_UNLOADED_QTY_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_igx += "y";
					}
					else
					{
						actual_unloaded_qty_igx = 0; chk1_igx = "-";
						
					}
					if(!INVOICE_TAX_AMT_IGX.elementAt(i).toString().trim().equals("-"))
					{
						inv_taxamt = Double.parseDouble(INVOICE_TAX_AMT_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_tax_igx += "y";
					}
					else
					{
						inv_taxamt = 0; chk1_tax_igx = "-";
						
					}
					if(!INVOICE_AMT_TDS_IGX.elementAt(i).toString().trim().equals("-"))
					{
						inv_tdsamt = Double.parseDouble(INVOICE_AMT_TDS_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_tds_igx += "y";
					}
					else
					{
						inv_tdsamt = 0; chk1_tds_igx = "-";
						
					}
					if(!TAX_amt_usd_IGX.elementAt(i).toString().trim().equals("-"))
					{
						inv_taxamt_usd = Double.parseDouble(TAX_amt_usd_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_taxusd_igx += "y";
					}
					else
					{
						inv_taxamt_usd = 0; chk1_taxusd_igx = "-";
						
					}
					
					if(!USD_VAL_INVOICE_IGX.elementAt(i).toString().trim().equals("-"))
					{
						usd_val_invoice_igx = Double.parseDouble(USD_VAL_INVOICE_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk2_igx += "y";
					}
					else
					{
						usd_val_invoice_igx = 0; chk2_igx = "-";
					}
					
					if(!FINAL_SELLER_INV_AMT_INR_IGX.elementAt(i).toString().trim().equals("-"))
					{
						final_seller_inv_amt_inr_igx = Double.parseDouble(FINAL_SELLER_INV_AMT_INR_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk3_igx += "y";
					}
					else
					{
						final_seller_inv_amt_inr_igx = 0; chk3_igx = "-";
					}
					
					if(!TOTAL_CD_AMT_IGX.elementAt(i).toString().trim().equals("-"))
					{
						tot_cd_amt_igx = Double.parseDouble(TOTAL_CD_AMT_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk4_igx += "y";
					}
					else
					{
						tot_cd_amt_igx = 0; chk4_igx = "-";
					}
					
					if(!TOTAL_PAID_REFUND_IGX.elementAt(i).toString().trim().equals("-"))
					{
						tot_paid_refund_igx = Double.parseDouble(TOTAL_PAID_REFUND_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk5_igx += "y";
					}
					else
					{
						tot_paid_refund_igx = 0; chk5_igx = "-";
					}
					
					if(!CD_PAID_IGX.elementAt(i).toString().trim().equals("-"))
					{
						cd_paid_igx = Double.parseDouble(CD_PAID_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk6_igx += "y";
					}
					else
					{
						cd_paid_igx = 0; chk6_igx = "-";
					}
					
					if(!CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						custom_duty_usd_igx = Double.parseDouble(CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk7_igx += "y";
					}
					else
					{
						custom_duty_usd_igx = 0; chk7_igx = "-";
					}
					
					if(!ADDL_CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						addl_custom_duty_usd_igx = Double.parseDouble(ADDL_CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk8_igx += "y";
					}
					else
					{
						addl_custom_duty_usd_igx = 0; chk8_igx = "-";
					}
					
					if(!COST_OF_PURCHASE_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						cost_of_purchase_usd_igx = Double.parseDouble(COST_OF_PURCHASE_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk9_igx += "y";
					}
					else
					{
						cost_of_purchase_usd_igx = 0;
						chk9_igx = "-";
					}
					
				}
				arrv_month_igx = ACT_ARRV_MONTH_IGX.elementAt(i).toString().trim();
			}
			SUMMRY_ACT_ARRV_MONTH_IGX.add(""+arrv_month_igx);
			//SUMMRY_ACTUAL_UNLOADED_QTY.add(nf3.format(actual_unloaded_qty));
			SUMMRY_CARGO_REF_CD_IGX.add(Scargo_ref_cd_igx);
			
			/*SUMMRY_USD_VAL_INVOICE.add(nf3.format(usd_val_invoice));
			
			SUMMRY_FINAL_SELLER_INV_AMT_INR.add(nf3.format(final_seller_inv_amt_inr));
			SUMMRY_TOTAL_CD_AMT.add(nf3.format(tot_cd_amt));
			SUMMRY_TOTAL_PAID_REFUND.add(nf3.format(tot_paid_refund));
			SUMMRY_CD_PAID.add(nf3.format(cd_paid));*/
			SUMMRY_INR_PER_MMBTU_IGX.add(nf2.format((cd_paid_igx/actual_unloaded_qty_igx)));
			
			/*SUMMRY_CUSTOM_DUTY_USD.add(nf3.format(custom_duty_usd));
			SUMMRY_ADDL_CUSTOM_DUTY_USD.add(nf3.format(custom_duty_usd));
			SUMMRY_COST_OF_PURCHASE_USD.add(nf3.format(cost_of_purchase_usd));*/
			SUMMRY_USD_PER_MMBTU_IGX.add(nf2.format((cost_of_purchase_usd_igx/actual_unloaded_qty_igx)));
			
			if(chk1_igx.contains("y"))
			{
				SUMMRY_ACTUAL_UNLOADED_QTY_IGX.add(nf3.format(actual_unloaded_qty_igx));
			}
			else
			{
				SUMMRY_ACTUAL_UNLOADED_QTY_IGX.add("-");
			}
			if(chk1_tax_igx.contains("y"))
			{
				SUMMRY_TAX_AMT_INR_IGX.add(nf3.format(inv_taxamt));
			}
			else
			{
				SUMMRY_TAX_AMT_INR_IGX.add("-");
			}
			if(chk1_tds_igx.contains("y"))
			{
				SUMMRY_TDS_AMT_INR_IGX.add(nf3.format(inv_tdsamt));
			}
			else
			{
				SUMMRY_TDS_AMT_INR_IGX.add("-");
			}
			if(chk1_taxusd_igx.contains("y"))
			{
				SUMMRY_TAX_AMT_USD_IGX.add(nf3.format(inv_taxamt_usd));
			}
			else
			{
				SUMMRY_TAX_AMT_USD_IGX.add("-");
			}
			SUMMRY_CARGO_REF_CD_IGX.add(Scargo_ref_cd_igx);
			if(chk2_igx.contains("y"))
			{
				SUMMRY_USD_VAL_INVOICE_IGX.add(nf3.format(usd_val_invoice_igx));
			}
			else
			{
				SUMMRY_USD_VAL_INVOICE_IGX.add("-");
			}
			if(chk3_igx.contains("y"))
			{
				SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX.add(nf3.format(final_seller_inv_amt_inr_igx));
			}
			else
			{
				SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX.add("-");
			}
			if(chk4_igx.contains("y"))
			{
				SUMMRY_TOTAL_CD_AMT_IGX.add(nf3.format(tot_cd_amt_igx));
			}
			else
			{
				SUMMRY_TOTAL_CD_AMT_IGX.add("-");
			}
			if(chk5_igx.contains("y"))
			{
				SUMMRY_TOTAL_PAID_REFUND_IGX.add(nf3.format(tot_paid_refund_igx));
			}
			else
			{
				SUMMRY_TOTAL_PAID_REFUND_IGX.add("-");
			}
			if(chk6_igx.contains("y"))
			{
				SUMMRY_CD_PAID_IGX.add(nf3.format(cd_paid_igx));
			}
			else
			{
				SUMMRY_CD_PAID_IGX.add("-");
			}
			if(actual_unloaded_qty_igx != 0)
			{
				SUMMRY_INR_PER_MMBTU_IGX.add(nf2.format(cd_paid_igx/actual_unloaded_qty_igx));
			}
			else
			{
				SUMMRY_INR_PER_MMBTU_IGX.add("-"); 
			}
			
			if(chk7_igx.contains("y"))
			{
				SUMMRY_CUSTOM_DUTY_USD_IGX.add(nf3.format(custom_duty_usd_igx));
			}
			else
			{
				SUMMRY_CUSTOM_DUTY_USD_IGX.add("-");
			}
			if(chk8_igx.contains("y"))
			{
				SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX.add(nf3.format(custom_duty_usd_igx));
			}
			else
			{
				SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX.add("-");
			}
			if(chk9_igx.contains("y"))
			{
				SUMMRY_COST_OF_PURCHASE_USD_IGX.add(nf3.format(cost_of_purchase_usd_igx));
			}
			else
			{
				SUMMRY_COST_OF_PURCHASE_USD_IGX.add("-");
			}
			if(actual_unloaded_qty_igx != 0)
			{
				SUMMRY_USD_PER_MMBTU_IGX.add(nf2.format((cost_of_purchase_usd_igx/actual_unloaded_qty_igx)));
			}
			else
			{
				SUMMRY_USD_PER_MMBTU_IGX.add("-"); 
			}
			//System.out.println(" "+chk1);
		}
	}
	
	public void get_Purchase_dtls_Parking()
	{

		try {
			String from_dt_igx = "01/"+month+"/"+year;
			String to_dt_igx = "";
			queryString = "Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
			////System.out.println("Last Date Of The Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_dt_igx = rset.getString(1)==null?"0":rset.getString(1);
			}
			////System.out.println("From date "+from_dt+" To Date= "+to_dt);
			queryString = "Select To_char(to_date('"+from_dt_igx+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("From Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				mon = rset.getString(1)==null?"0":rset.getString(1);
			}
			
			queryString = "Select To_char(to_date('"+to_dt_igx+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("To The Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_mon = rset.getString(1)==null?"0":rset.getString(1);
			}
			Vector duration=new Vector();
			Vector duration2=new Vector();
			String dur1="";
				for(int jk=1;jk<=12;jk++){
					//System.out.println("dur11---"+month+"---"+to_month);
					//if(jk>=Integer.parseInt(month) && jk<=Integer.parseInt(to_month)){
						//System.out.println("dur11-->>>>>>>>>>>-");
						if(jk<10){
							dur1="01/0"+jk+"/"+year+"-15/0"+jk+"/"+year;
						}else{
							dur1="01/"+jk+"/"+year+"-15/"+jk+"/"+year;
						}
						//System.out.println("dur11---"+dur1);
						duration.add(dur1);
						 String to_dt="";
						 if(jk<10){
						    queryString = "Select To_char(Last_Day(to_date('0"+jk+"/"+year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
						 }else{
							 queryString = "Select To_char(Last_Day(to_date('"+jk+"/"+year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
						 }
							////System.out.println("Last Date Of The Selected Month = "+queryString);
							rset = stmt.executeQuery(queryString);			
							if(rset.next())
							{
								to_dt = rset.getString(1)==null?"0":rset.getString(1);
							}
							if(jk<10){
								//duration2.add("16/0"+jk+"/"+year+"-"+to_dt);
								duration.add("16/0"+jk+"/"+year+"-"+to_dt);
							}else{
								duration.add("16/"+jk+"/"+year+"-"+to_dt);
							}
				//	}
				}
				//System.out.println("durat-1-"+duration);
//				tot_entry_alloc=0;
//				tot_exit_alloc=0;
				
				for(int k=0;k<duration.size();k++){
					//System.out.println("durat-1-");
					int cnt=0;
					String temp1[]=duration.elementAt(k).toString().split("-");
					String frmdt=temp1[0];
					String todt=temp1[1];
					String frm_dt="01/"+month+"/"+year;
					String to_dt="02/"+to_month+"/"+to_year;
					String take_flag="";
					queryString = "SELECT TO_DATE('"+frmdt+"','dd/mm/yyyy') FROM DUAL WHERE to_date('"+frmdt+"','dd/mm/yyyy') BETWEEN "
								+ "  to_date('"+frm_dt+"','dd/mm/yyyy') and to_date('"+to_dt+"','dd/mm/yyyy')";
					//System.out.println("queryString--"+queryString);
					rset1 = stmt1.executeQuery(queryString);
					if(rset1.next())
					{
						take_flag="Y";
					}else{
						take_flag="";
					}
					
					if(take_flag.equals("Y")){
						queryString="SELECT CONT_NO,MAPPING_ID,CONT_AGR_NO,CONT_AGR_REV_NO,CONTRACT_NO,CONTRACT_REV_NO,CONT_AGR_TYPE,CONT_CUST_CD,PARTY_CD,cont_mapping_id FROM FMS_CONT_MST WHERE "
								+ "((CONT_ST_DT <= TO_DATE('"+frmdt+"','DD/MM/YYYY') OR CONT_ST_DT >=TO_DATE('"+todt+"','DD/MM/YYYY')) OR"
								//+ " (CONT_END_DT <= TO_DATE('"+frmdt+"','DD/MM/YYYY') OR CONT_END_DT >=TO_DATE('"+todt+"','DD/MM/YYYY'))) AND CONT_AGR_TYPE='Trans'";
								+ " (CONT_END_DT <= TO_DATE('"+frmdt+"','DD/MM/YYYY') OR CONT_END_DT >=TO_DATE('"+todt+"','DD/MM/YYYY'))) AND CONT_AGR_TYPE='Parking'";
						//System.out.println("queryString--"+queryString);
						rset=stmt.executeQuery(queryString);
						while(rset.next()){
							
							queryString="SELECT SUM(entry_TOT_ENE),SUM(EXIT_TOT_ENE) FROM FMS_ALLOC_MST A WHERE MAPPING_ID='"+rset.getString(2)+"' AND "
									+ "ALLOC_DT BETWEEN TO_DATE('"+frmdt+"','DD/MM/YYYY') AND TO_DATE('"+todt+"','DD/MM/YYYY') AND CONT_MAPPING_ID='1' ";
							rset1=stmt1.executeQuery(queryString);
							//System.out.println("queryString--"+queryString);
							if(rset1.next()){
								if(rset1.getString(1)!=null){
									queryString="SELECT count(b.INV_NO) FROM fms_inv_bill_dtl b WHERE  "
											+ "b.MAPPING_ID='"+rset.getString(2)+"' AND b.CONT_AGR_NO='"+rset.getString(3)+"'"
											+ " AND b.CONTRACT_NO='"+rset.getString(5)+"' AND b.CONT_CUST_CD='"+rset.getString(8)+"' AND CONT_AGR_TYPE='Parking' and bill_period_from "
											+ "between TO_DATE('"+frmdt+"','DD/MM/YYYY') AND TO_DATE('"+todt+"','DD/MM/YYYY') ";
									rset2=stmt2.executeQuery(queryString);
									if(rset2.next()){
										int cnt_inv=rset2.getInt(1);
										if(cnt_inv==0){
											Ventry_alloc_P.add(rset1.getString(1)==null?"0":nf6.format(Double.parseDouble(rset1.getString(1))));
											Vexit_alloc_P.add(rset1.getString(2)==null?"0":nf6.format(Double.parseDouble(rset1.getString(2))));
											tot_entry_alloc_P+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
											tot_exit_alloc_P+=Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2));
											Vcont_no_P.add(rset.getString(1)==null?"":rset.getString(1));
											Vcont_agr_typ_P.add(rset.getString(7)==null?"":rset.getString(7));
											Vcont_mappid_P.add(rset.getString(10)==null?"":rset.getString(10));
											Vmapp_id_P.add(rset.getString(2)==null?"":rset.getString(2));
											
											queryString="SELECT PARTY_ABR FROM FMS_PARTY_MST WHERE PARTY_CD='"+rset.getString(9)+"'";
											rset3=stmt3.executeQuery(queryString);
											if(rset3.next()){
												Vparty_abbr_P.add(rset3.getString(1)==null?"":rset3.getString(1));
											}else{
												Vparty_abbr_P.add("");
											}
											duration_trans_P.add(frmdt+" - "+todt);
											Vinv_no_P.add("");
											Vrate_P.add("");
											Vamt_P.add("");
											Vqty_P.add("");
											Vaprv_by_P.add("");
											Vverify_by_P.add("");
											Vcompo_nm_P.add("");
											Vnetamt_P.add("");
											VTAXAMT_P.add("");
										
										}else{
											queryString="SELECT b.INV_NO,b.aprv_by,b.inv_pay,b.bill_pay,b.bill_Amt,b.rev_no,bill_no,b.verify_by FROM fms_inv_bill_dtl b WHERE  "
													+ "b.MAPPING_ID='"+rset.getString(2)+"' AND b.CONT_AGR_NO='"+rset.getString(3)+"' AND CONT_AGR_TYPE='Parking' "
													+ " AND b.CONTRACT_NO='"+rset.getString(5)+"' AND b.CONT_CUST_CD='"+rset.getString(8)+"' and bill_period_from "
													+ "between TO_DATE('"+frmdt+"','DD/MM/YYYY') AND TO_DATE('"+todt+"','DD/MM/YYYY') ";
											rset3=stmt3.executeQuery(queryString);
											while(rset3.next()){
												String inv_no="",compo_nm="",comprate="",comp_qty="",comp_amt="",TAX_aMT="",net_Amt="";
												Ventry_alloc_P.add(rset1.getString(1)==null?"0":nf6.format(Double.parseDouble(rset1.getString(1))));
												Vexit_alloc_P.add(rset1.getString(2)==null?"0":nf6.format(Double.parseDouble(rset1.getString(2))));
												tot_entry_alloc_P+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
												tot_exit_alloc_P+=Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2));
												Vcont_no_P.add(rset.getString(1)==null?"":rset.getString(1));
												Vcont_agr_typ_P.add(rset.getString(7)==null?"":rset.getString(7));
												Vcont_mappid_P.add(rset.getString(10)==null?"":rset.getString(10));
												Vmapp_id_P.add(rset.getString(2)==null?"":rset.getString(2));
												
												queryString="SELECT PARTY_ABR FROM FMS_PARTY_MST WHERE PARTY_CD='"+rset.getString(9)+"'";
												rset4=stmt4.executeQuery(queryString);
												if(rset4.next()){
													Vparty_abbr_P.add(rset4.getString(1)==null?"":rset4.getString(1));
												}else{
													Vparty_abbr_P.add("");
												}
												duration_trans_P.add(frmdt+" - "+todt);
												
												queryString="SELECT a.INV_NO,a.RATE,a.AMT,A.COMPO_CD,a.BILL_RATE,a.QTY,a.bill_qty,a.bill_Amt,a.rev_no FROM "
														+ "FMS_COMPO_INV_DTL a WHERE  a.MAPPING_ID='"+rset.getString(2)+"' AND a.CONT_AGR_NO='"+rset.getString(3)+"'"
														+ " AND a.CONTRACT_NO='"+rset.getString(5)+"' AND a.CONT_CUST_CD='"+rset.getString(8)+"' and a.rev_no = '"+rset3.getString(6)+"'"
														+ " and inv_no='"+rset3.getString(1)+"' AND CONT_AGR_TYPE='Parking'";
												rset4=stmt4.executeQuery(queryString);
												while(rset4.next()){
													
													net_Amt=rset3.getString(5)==null?"":rset3.getString(5);
													queryString="SELECT DESCRIPTION FROM FMS_PRICE_MST WHERE PRICE_CD='"+rset4.getString(4)+"' and type_code='R'";
													rset5=stmt5.executeQuery(queryString);
													if(rset5.next()){
														compo_nm+=(rset5.getString(1)==null?"":rset5.getString(1))+"@";
													}else{
														compo_nm+="-"+"@";
													}
													queryString="SELECT TAX_CD,TAX_RATE,TAX_AMT FROM FMS_INV_TAX_DTL WHERE MAPPING_ID='"+rset.getString(2)+"' "
																+ "AND CONT_AGR_NO='"+rset.getString(3)+"'"
																+ " AND CONTRACT_NO='"+rset.getString(5)+"' AND CONT_CUST_CD='"+rset.getString(8)+"' "
																+ "AND REV_NO='"+rset3.getString(6)+"' and inv_no='"+rset3.getString(1)+"' AND CONT_AGR_TYPE='Parking'";
													rset5=stmt5.executeQuery(queryString);
													if(rset5.next()){
														TAX_aMT+=(rset5.getString(3)==null?"":rset5.getString(3))+"@";
													}
													if(rset3.getString(2)!=null){
														if(rset3.getString(3)!=null && (!rset3.getString(3).equalsIgnoreCase("N"))){
															comprate+=(rset4.getString(2)==null?"":rset4.getString(2))+"@";
															comp_qty+=(rset4.getString(6)==null?"":rset4.getString(6))+"@";
															comp_amt+=(rset4.getString(3)==null?"":rset4.getString(3))+"@";
															totqty_P+=Double.parseDouble(rset4.getString(6)==null?"0":rset4.getString(6));
															inv_no=rset4.getString(1)==null?"":rset4.getString(1);
														}else{
															comprate+=(rset4.getString(5)==null?"":rset4.getString(5))+"@";
															comp_qty+=(rset4.getString(7)==null?"":rset4.getString(7))+"@";
															comp_amt+=(rset4.getString(8)==null?"":rset4.getString(8))+"@";
															totqty_P+=Double.parseDouble(rset4.getString(7)==null?"0":rset4.getString(7));
															inv_no=rset3.getString(7)==null?"":rset3.getString(7);
														}
													}else{
														comprate+=(rset4.getString(2)==null?"":rset4.getString(2))+"@";
														comp_qty+=(rset4.getString(6)==null?"":rset4.getString(6))+"@";
														comp_amt+=(rset4.getString(3)==null?"":rset4.getString(3))+"@";
														totqty_P+=Double.parseDouble(rset4.getString(6)==null?"0":rset4.getString(6));
														inv_no=rset4.getString(1)==null?"":rset4.getString(1);
														
													}
													totamt_P+=Double.parseDouble(rset4.getString(3)==null?"0":rset4.getString(3));
													
												
												}
												if(!comprate.equals("")){
													comprate=comprate.substring(0,comprate.length()-1);
												}
												if(!comp_amt.equals("")){
													comp_amt=comp_amt.substring(0,comp_amt.length()-1);
												}
												if(!comp_qty.equals("")){
													comp_qty=comp_qty.substring(0,comp_qty.length()-1);
												}
												if(!compo_nm.equals("")){
													compo_nm=compo_nm.substring(0,compo_nm.length()-1);
												}
												if(!TAX_aMT.equals("")){
													TAX_aMT=TAX_aMT.substring(0,TAX_aMT.length()-1);
												}
												Vinv_no_P.add(inv_no);
												Vrate_P.add(comprate);
												Vamt_P.add(comp_amt);
												Vqty_P.add(comp_qty);
												//Vaprv_by.add(rset2.getString(4)==null?"":rset2.getString(4));
												Vcompo_nm_P.add(compo_nm);
												Vnetamt_P.add(net_Amt);
												VTAXAMT_P.add(TAX_aMT);
												Vaprv_by_P.add(rset3.getString(2)==null?"":rset3.getString(2));
												Vverify_by_P.add(rset3.getString(8)==null?"":rset3.getString(8));
												
											}
											
											
										}
									}
									
								}
							}
							
							
							
						}
//						tot_entry_nom_P=0;
//						tot_exit_nom_P=0;
//						tot_entry_sch_P=0;
//						tot_exit_sch_P=0;
						
						for(int i=0;i<Vmapp_id_P.size();i++){
							Vector Vrevno=new Vector();
							Vector Vnomdt=new Vector();
							Vector Vschrevno=new Vector();
							Vector Vschdt=new Vector();
							  double entry_nom=0,exit_nom=0;
							  double entry_sch=0,exit_sch=0;
								queryString="select max(rev_no),to_char(nom_dt,'dd/mm/yyyy') from fms_daily_nom where mapping_id='"+Vmapp_id_P.elementAt(i)+"' and "
	                                    +"nom_dt between to_date('"+frmdt+"','dd/mm/yyyy') and to_date('"+todt+"','dd/mm/yyyy') and cont_mapping_id='1' group by nom_dt" ;
					  
								  rset=stmt.executeQuery(queryString);
								  while (rset.next()){
	//								  Vrevno.add(rset.getString(1)==null?"":rset.getString(1));
	//								  Vnomdt.add(rset.getString(2)==null?"":rset.getString(2));
									  queryString="SELECT SUM(TOT_ENE),SUM(EXIT_TOT_ENE) FROM FMS_DAILY_NOM A WHERE mapping_id='"+Vmapp_id_P.elementAt(i)+"' and" +
												" nom_dt=to_date('"+rset.getString(2)+"','dd/mm/yyyy') and rev_no='"+rset.getString(1)+"' and cont_mapping_id='1'";
										//System.out.println("queryString-nom-"+queryString);
										rset1=stmt1.executeQuery(queryString);
										if(rset1.next()){
		//									Ventry_nom.add(rset1.getString(1)==null?"":rset1.getString(1));
		//									Vexit_nom.add(rset1.getString(2)==null?"":rset1.getString(2));
											entry_nom+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
											exit_nom+=Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2));
											tot_entry_nom_P+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
											tot_exit_nom_P+=Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2));
										}
								  }
							Ventry_nom_P.add(nf6.format(entry_nom));
							Vexit_nom_P.add(nf6.format(exit_nom));
							
							queryString="select max(rev_no),to_char(schedule_dt,'dd/mm/yyyy') from fms_daily_schedule where mapping_id='"+Vmapp_id_P.elementAt(i)+"' and "
                                    +"schedule_dt between to_date('"+frmdt+"','dd/mm/yyyy') and to_date('"+todt+"','dd/mm/yyyy') and cont_mapping_id='1'  group by schedule_dt" ;
							  rset=stmt.executeQuery(queryString);
							  while (rset.next()){
								  //Vschrevno.add(rset.getString(1)==null?"":rset.getString(1));
								  //Vschdt.add(rset.getString(2)==null?"":rset.getString(2));
								  queryString="SELECT SUM(entry_TOT_ENE),SUM(EXIT_TOT_ENE) FROM FMS_DAILY_SCHEDULE A WHERE mapping_id='"+Vmapp_id_P.elementAt(i)+"' and" +
									  		" schedule_dt=to_date('"+rset.getString(2)+"','dd/mm/yyyy') and rev_no='"+rset.getString(1)+"' and cont_mapping_id='1'";
											rset1=stmt1.executeQuery(queryString);
											if(rset1.next()){
//												Ventry_sch.add(rset1.getString(1)==null?"":rset1.getString(1));
//												Vexit_sch.add(rset1.getString(2)==null?"":rset1.getString(2));
												entry_sch+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
												exit_sch+=Double.parseDouble(rset1.getString(2)==null?"":rset1.getString(2));
												tot_entry_sch_P+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
												tot_exit_sch_P+=Double.parseDouble(rset1.getString(2)==null?"":rset1.getString(2));
											}
							  }
								Ventry_sch_P.add(nf6.format(entry_sch));
								Vexit_sch_P.add(nf6.format(exit_sch));
						}
						
					}
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}
	public void get_Purchase_dtls_Transporter()
	{
		try {
			String from_dt_igx = "01/"+month+"/"+year;
			String to_dt_igx = "";
			queryString = "Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
			////System.out.println("Last Date Of The Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_dt_igx = rset.getString(1)==null?"0":rset.getString(1);
			}
			////System.out.println("From date "+from_dt+" To Date= "+to_dt);
			queryString = "Select To_char(to_date('"+from_dt_igx+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("From Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				mon = rset.getString(1)==null?"0":rset.getString(1);
			}
			
			queryString = "Select To_char(to_date('"+to_dt_igx+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("To The Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_mon = rset.getString(1)==null?"0":rset.getString(1);
			}
			Vector duration=new Vector();
			Vector duration2=new Vector();
			String dur1="";
				for(int jk=1;jk<=12;jk++){
					//System.out.println("dur11---"+month+"---"+to_month);
					//if(jk>=Integer.parseInt(month) && jk<=Integer.parseInt(to_month)){
						//System.out.println("dur11-->>>>>>>>>>>-");
						if(jk<10){
							dur1="01/0"+jk+"/"+year+"-15/0"+jk+"/"+year;
						}else{
							dur1="01/"+jk+"/"+year+"-15/"+jk+"/"+year;
						}
						//System.out.println("dur11---"+dur1);
						duration.add(dur1);
						 String to_dt="";
						 if(jk<10){
						    queryString = "Select To_char(Last_Day(to_date('0"+jk+"/"+year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
						 }else{
							 queryString = "Select To_char(Last_Day(to_date('"+jk+"/"+year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
						 }
							////System.out.println("Last Date Of The Selected Month = "+queryString);
							rset = stmt.executeQuery(queryString);			
							if(rset.next())
							{
								to_dt = rset.getString(1)==null?"0":rset.getString(1);
							}
							if(jk<10){
								//duration2.add("16/0"+jk+"/"+year+"-"+to_dt);
								duration.add("16/0"+jk+"/"+year+"-"+to_dt);
							}else{
								duration.add("16/"+jk+"/"+year+"-"+to_dt);
							}
				//	}
				}
				//System.out.println("durat-1-"+duration);
				tot_entry_alloc=0;
				tot_exit_alloc=0;
				
				for(int k=0;k<duration.size();k++){
					//System.out.println("durat-1-");
					int cnt=0;
					String temp1[]=duration.elementAt(k).toString().split("-");
					String frmdt=temp1[0];
					String todt=temp1[1];
					String frm_dt="01/"+month+"/"+year;
					String to_dt="02/"+to_month+"/"+to_year;
					String take_flag="";
					queryString = "SELECT TO_DATE('"+frmdt+"','dd/mm/yyyy') FROM DUAL WHERE to_date('"+frmdt+"','dd/mm/yyyy') BETWEEN "
								+ "  to_date('"+frm_dt+"','dd/mm/yyyy') and to_date('"+to_dt+"','dd/mm/yyyy')";
					//System.out.println("queryString--"+queryString);
					rset1 = stmt1.executeQuery(queryString);
					if(rset1.next())
					{
						take_flag="Y";
					}else{
						take_flag="";
					}
					
					if(take_flag.equals("Y")){
						queryString="SELECT CONT_NO,MAPPING_ID,CONT_AGR_NO,CONT_AGR_REV_NO,CONTRACT_NO,CONTRACT_REV_NO,CONT_AGR_TYPE,CONT_CUST_CD,PARTY_CD,cont_mapping_id FROM FMS_CONT_MST WHERE "
								+ "((CONT_ST_DT <= TO_DATE('"+frmdt+"','DD/MM/YYYY') OR CONT_ST_DT >=TO_DATE('"+todt+"','DD/MM/YYYY')) OR"
								//+ " (CONT_END_DT <= TO_DATE('"+frmdt+"','DD/MM/YYYY') OR CONT_END_DT >=TO_DATE('"+todt+"','DD/MM/YYYY'))) AND CONT_AGR_TYPE='Trans'";
								+ " (CONT_END_DT <= TO_DATE('"+frmdt+"','DD/MM/YYYY') OR CONT_END_DT >=TO_DATE('"+todt+"','DD/MM/YYYY'))) AND CONT_AGR_TYPE='Trans'";
						//System.out.println("queryString--"+queryString);
						rset=stmt.executeQuery(queryString);
						while(rset.next()){
							
							queryString="SELECT SUM(entry_TOT_ENE),SUM(EXIT_TOT_ENE) FROM FMS_ALLOC_MST A WHERE MAPPING_ID='"+rset.getString(2)+"' AND "
									+ "ALLOC_DT BETWEEN TO_DATE('"+frmdt+"','DD/MM/YYYY') AND TO_DATE('"+todt+"','DD/MM/YYYY') AND CONT_MAPPING_ID='0' ";
							rset1=stmt1.executeQuery(queryString);
							//System.out.println("queryString--"+queryString);
							if(rset1.next()){
								if(rset1.getString(1)!=null){
									queryString="SELECT count(b.INV_NO) FROM fms_inv_bill_dtl b WHERE  "
											+ "b.MAPPING_ID='"+rset.getString(2)+"' AND b.CONT_AGR_NO='"+rset.getString(3)+"'"
											+ " AND b.CONTRACT_NO='"+rset.getString(5)+"' AND b.CONT_CUST_CD='"+rset.getString(8)+"' and bill_period_from "
											+ "between TO_DATE('"+frmdt+"','DD/MM/YYYY') AND TO_DATE('"+todt+"','DD/MM/YYYY') AND CONT_AGR_TYPE='Trans' ";
									rset2=stmt2.executeQuery(queryString);
									if(rset2.next()){
										int cnt_inv=rset2.getInt(1);
										if(cnt_inv==0){
											Ventry_alloc.add(rset1.getString(1)==null?"0":nf6.format(Double.parseDouble(rset1.getString(1))));
											Vexit_alloc.add(rset1.getString(2)==null?"0":nf6.format(Double.parseDouble(rset1.getString(2))));
											tot_entry_alloc+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
											tot_exit_alloc+=Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2));
											Vcont_no.add(rset.getString(1)==null?"":rset.getString(1));
											Vcont_agr_typ.add(rset.getString(7)==null?"":rset.getString(7));
											Vcont_mappid.add(rset.getString(10)==null?"":rset.getString(10));
											Vmapp_id.add(rset.getString(2)==null?"":rset.getString(2));
											
											queryString="SELECT PARTY_ABR FROM FMS_PARTY_MST WHERE PARTY_CD='"+rset.getString(9)+"'";
											rset3=stmt3.executeQuery(queryString);
											if(rset3.next()){
												Vparty_abbr.add(rset3.getString(1)==null?"":rset3.getString(1));
											}else{
												Vparty_abbr.add("");
											}
											duration_trans.add(frmdt+" - "+todt);
											Vinv_no.add("");
											Vrate.add("");
											Vamt.add("");
											Vqty.add("");
											Vaprv_by.add("");
											Vverify_by.add("");
											Vcompo_nm.add("");
											Vnetamt.add("");
											VTAXAMT.add("");
										
										}else{
											queryString="SELECT b.INV_NO,b.aprv_by,b.inv_pay,b.bill_pay,b.bill_Amt,b.rev_no,bill_no,b.verify_by FROM fms_inv_bill_dtl b WHERE  "
													+ "b.MAPPING_ID='"+rset.getString(2)+"' AND b.CONT_AGR_NO='"+rset.getString(3)+"'"
													+ " AND b.CONTRACT_NO='"+rset.getString(5)+"' AND b.CONT_CUST_CD='"+rset.getString(8)+"' and bill_period_from "
													+ "between TO_DATE('"+frmdt+"','DD/MM/YYYY') AND TO_DATE('"+todt+"','DD/MM/YYYY') AND CONT_AGR_TYPE='Trans'";
											rset3=stmt3.executeQuery(queryString);
											while(rset3.next()){
												String inv_no="",compo_nm="",comprate="",comp_qty="",comp_amt="",TAX_aMT="",net_Amt="";
												Ventry_alloc.add(rset1.getString(1)==null?"0":nf6.format(Double.parseDouble(rset1.getString(1))));
												Vexit_alloc.add(rset1.getString(2)==null?"0":nf6.format(Double.parseDouble(rset1.getString(2))));
												tot_entry_alloc+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
												tot_exit_alloc+=Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2));
												Vcont_no.add(rset.getString(1)==null?"":rset.getString(1));
												Vcont_agr_typ.add(rset.getString(7)==null?"":rset.getString(7));
												Vcont_mappid.add(rset.getString(10)==null?"":rset.getString(10));
												Vmapp_id.add(rset.getString(2)==null?"":rset.getString(2));
												
												queryString="SELECT PARTY_ABR FROM FMS_PARTY_MST WHERE PARTY_CD='"+rset.getString(9)+"'";
												rset4=stmt4.executeQuery(queryString);
												if(rset4.next()){
													Vparty_abbr.add(rset4.getString(1)==null?"":rset4.getString(1));
												}else{
													Vparty_abbr.add("");
												}
												duration_trans.add(frmdt+" - "+todt);
												
												queryString="SELECT a.INV_NO,a.RATE,a.AMT,A.COMPO_CD,a.BILL_RATE,a.QTY,a.bill_qty,a.bill_Amt,a.rev_no FROM "
														+ "FMS_COMPO_INV_DTL a WHERE  a.MAPPING_ID='"+rset.getString(2)+"' AND a.CONT_AGR_NO='"+rset.getString(3)+"'"
														+ " AND a.CONTRACT_NO='"+rset.getString(5)+"' AND a.CONT_CUST_CD='"+rset.getString(8)+"' and a.rev_no = '"+rset3.getString(6)+"'"
														+ " and inv_no='"+rset3.getString(1)+"' AND CONT_AGR_TYPE='Trans'";
												rset4=stmt4.executeQuery(queryString);
												while(rset4.next()){
													
													net_Amt=rset3.getString(5)==null?"":rset3.getString(5);
													queryString="SELECT DESCRIPTION FROM FMS_PRICE_MST WHERE PRICE_CD='"+rset4.getString(4)+"' and type_code='R'";
													rset5=stmt5.executeQuery(queryString);
													if(rset5.next()){
														compo_nm+=(rset5.getString(1)==null?"":rset5.getString(1))+"@";
													}else{
														compo_nm+="-"+"@";
													}
													queryString="SELECT TAX_CD,TAX_RATE,TAX_AMT FROM FMS_INV_TAX_DTL WHERE MAPPING_ID='"+rset.getString(2)+"' "
																+ "AND CONT_AGR_NO='"+rset.getString(3)+"'"
																+ " AND CONTRACT_NO='"+rset.getString(5)+"' AND CONT_CUST_CD='"+rset.getString(8)+"' "
																+ "AND REV_NO='"+rset3.getString(6)+"' and inv_no='"+rset3.getString(1)+"' AND CONT_AGR_TYPE='Trans'";
													rset5=stmt5.executeQuery(queryString);
													if(rset5.next()){
														TAX_aMT+=(rset5.getString(3)==null?"":rset5.getString(3))+"@";
													}
													if(rset3.getString(2)!=null){
														if(rset3.getString(3)!=null && (!rset3.getString(3).equalsIgnoreCase("N"))){
															comprate+=(rset4.getString(2)==null?"":rset4.getString(2))+"@";
															comp_qty+=(rset4.getString(6)==null?"":rset4.getString(6))+"@";
															comp_amt+=(rset4.getString(3)==null?"":rset4.getString(3))+"@";
															totqty+=Double.parseDouble(rset4.getString(6)==null?"0":rset4.getString(6));
															inv_no=rset4.getString(1)==null?"":rset4.getString(1);
														}else{
															comprate+=(rset4.getString(5)==null?"":rset4.getString(5))+"@";
															comp_qty+=(rset4.getString(7)==null?"":rset4.getString(7))+"@";
															comp_amt+=(rset4.getString(8)==null?"":rset4.getString(8))+"@";
															totqty+=Double.parseDouble(rset4.getString(7)==null?"0":rset4.getString(7));
															inv_no=rset3.getString(7)==null?"":rset3.getString(7);
														}
													}else{
														comprate+=(rset4.getString(2)==null?"":rset4.getString(2))+"@";
														comp_qty+=(rset4.getString(6)==null?"":rset4.getString(6))+"@";
														comp_amt+=(rset4.getString(3)==null?"":rset4.getString(3))+"@";
														totqty+=Double.parseDouble(rset4.getString(6)==null?"0":rset4.getString(6));
														inv_no=rset4.getString(1)==null?"":rset4.getString(1);
														
													}
													totamt+=Double.parseDouble(rset4.getString(3)==null?"0":rset4.getString(3));
													
												
												}
												if(!comprate.equals("")){
													comprate=comprate.substring(0,comprate.length()-1);
												}
												if(!comp_amt.equals("")){
													comp_amt=comp_amt.substring(0,comp_amt.length()-1);
												}
												if(!comp_qty.equals("")){
													comp_qty=comp_qty.substring(0,comp_qty.length()-1);
												}
												if(!compo_nm.equals("")){
													compo_nm=compo_nm.substring(0,compo_nm.length()-1);
												}
												if(!TAX_aMT.equals("")){
													TAX_aMT=TAX_aMT.substring(0,TAX_aMT.length()-1);
												}
												Vinv_no.add(inv_no);
												Vrate.add(comprate);
												Vamt.add(comp_amt);
												Vqty.add(comp_qty);
												//Vaprv_by.add(rset2.getString(4)==null?"":rset2.getString(4));
												Vcompo_nm.add(compo_nm);
												Vnetamt.add(net_Amt);
												VTAXAMT.add(TAX_aMT);
												Vaprv_by.add(rset3.getString(2)==null?"":rset3.getString(2));
												Vverify_by.add(rset3.getString(8)==null?"":rset3.getString(8));
												
											}
											
											
										}
									}
									
								}
							}
							
							
							
						}
						tot_entry_nom=0;
						tot_exit_nom=0;
						tot_entry_sch=0;
						tot_exit_sch=0;
						
						for(int i=0;i<Vmapp_id.size();i++){
							Vector Vrevno=new Vector();
							Vector Vnomdt=new Vector();
							Vector Vschrevno=new Vector();
							Vector Vschdt=new Vector();
							  double entry_nom=0,exit_nom=0;
							  double entry_sch=0,exit_sch=0;
							  if(Vcont_agr_typ.elementAt(i).equals("Trans")){
								queryString="select max(rev_no),to_char(nom_dt,'dd/mm/yyyy') from fms_daily_nom where mapping_id='"+Vmapp_id.elementAt(i)+"' and "
	                                    +"nom_dt between to_date('"+frmdt+"','dd/mm/yyyy') and to_date('"+todt+"','dd/mm/yyyy') and cont_mapping_id='0' group by nom_dt" ;
					  
								  rset=stmt.executeQuery(queryString);
								  while (rset.next()){
	//								  Vrevno.add(rset.getString(1)==null?"":rset.getString(1));
	//								  Vnomdt.add(rset.getString(2)==null?"":rset.getString(2));
									  queryString="SELECT SUM(TOT_ENE),SUM(EXIT_TOT_ENE) FROM FMS_DAILY_NOM A WHERE mapping_id='"+Vmapp_id.elementAt(i)+"' and" +
												" nom_dt=to_date('"+rset.getString(2)+"','dd/mm/yyyy') and rev_no='"+rset.getString(1)+"' and cont_mapping_id='0'";
										//System.out.println("queryString-nom-"+queryString);
										rset1=stmt1.executeQuery(queryString);
										if(rset1.next()){
		//									Ventry_nom.add(rset1.getString(1)==null?"":rset1.getString(1));
		//									Vexit_nom.add(rset1.getString(2)==null?"":rset1.getString(2));
											entry_nom+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
											exit_nom+=Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2));
											tot_entry_nom+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
											tot_exit_nom+=Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2));
										}
								  }
							  }else{
								  queryString="select max(nom_rev_no),to_char(gas_dt,'dd/mm/yyyy') from fms7_daily_transporter_nom_dtl where transporter_mapping_id='"+Vmapp_id.elementAt(i)+"' and "
		                                    +"gas_dt between to_date('"+frmdt+"','dd/mm/yyyy') and to_date('"+todt+"','dd/mm/yyyy') and mapping_id='"+Vcont_mappid.elementAt(i)+"'  group by gas_dt" ;
								  System.out.println("queryString-nom-"+queryString);
									  rset=stmt.executeQuery(queryString);
									  while (rset.next()){
		//								  Vrevno.add(rset.getString(1)==null?"":rset.getString(1));
		//								  Vnomdt.add(rset.getString(2)==null?"":rset.getString(2));
										  queryString="SELECT SUM(QTY_MMBTU_ENTRY),SUM(QTY_MMBTU_EXIT) FROM fms7_daily_transporter_nom_dtl A WHERE transporter_mapping_id='"+Vmapp_id.elementAt(i)+"' and" +
													" gas_dt=to_date('"+rset.getString(2)+"','dd/mm/yyyy') and nom_rev_no='"+rset.getString(1)+"' and mapping_id='"+Vcont_mappid.elementAt(i)+"'";
											//System.out.println("queryString-nom-"+queryString);
											rset1=stmt1.executeQuery(queryString);
											if(rset1.next()){
			//									Ventry_nom.add(rset1.getString(1)==null?"":rset1.getString(1));
			//									Vexit_nom.add(rset1.getString(2)==null?"":rset1.getString(2));
												entry_nom+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
												exit_nom+=Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2));
												tot_entry_nom+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
												tot_exit_nom+=Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2));
											}
									  } 
							  }
							Ventry_nom.add(nf6.format(entry_nom));
							Vexit_nom.add(nf6.format(exit_nom));
							
							queryString="select max(rev_no),to_char(schedule_dt,'dd/mm/yyyy') from fms_daily_schedule where mapping_id='"+Vmapp_id.elementAt(i)+"' and "
                                    +"schedule_dt between to_date('"+frmdt+"','dd/mm/yyyy') and to_date('"+todt+"','dd/mm/yyyy') and cont_mapping_id='0'  group by schedule_dt" ;
							  rset=stmt.executeQuery(queryString);
							  while (rset.next()){
								  //Vschrevno.add(rset.getString(1)==null?"":rset.getString(1));
								  //Vschdt.add(rset.getString(2)==null?"":rset.getString(2));
								  queryString="SELECT SUM(entry_TOT_ENE),SUM(EXIT_TOT_ENE) FROM FMS_DAILY_SCHEDULE A WHERE mapping_id='"+Vmapp_id.elementAt(i)+"' and" +
									  		" schedule_dt=to_date('"+rset.getString(2)+"','dd/mm/yyyy') and rev_no='"+rset.getString(1)+"' and cont_mapping_id='0'";
											rset1=stmt1.executeQuery(queryString);
											if(rset1.next()){
//												Ventry_sch.add(rset1.getString(1)==null?"":rset1.getString(1));
//												Vexit_sch.add(rset1.getString(2)==null?"":rset1.getString(2));
												entry_sch+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
												exit_sch+=Double.parseDouble(rset1.getString(2)==null?"":rset1.getString(2));
												tot_entry_sch+=Double.parseDouble(rset1.getString(1)==null?"0":rset1.getString(1));
												tot_exit_sch+=Double.parseDouble(rset1.getString(2)==null?"":rset1.getString(2));
											}
							  }
								Ventry_sch.add(nf6.format(entry_sch));
								Vexit_sch.add(nf6.format(exit_sch));
						}
						
					}
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private void get_Purchase_dtls_Transporter_Approval()throws Exception {
		try {

			if(!year.equals("0") && (!to_year.equals("0")) && (!month.equals("0")) && (!to_month.equals("0")) ){
				
				String st_dt="01/"+month+"/"+year;
				String end_dt="01/"+to_month+"/"+to_year;
				queryString = "Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
				////System.out.println("Last Date Of The Selected Month = "+queryString);
				rset = stmt.executeQuery(queryString);			
				if(rset.next())
				{
					end_dt = rset.getString(1)==null?"0":rset.getString(1);
				}
				String transInvSql = "" ;
					transInvSql = "SELECT mapping_id,cont_agr_no,cont_agr_rev_no,contract_no,contract_rev_no,rev_no,nvl(aprv_final_amt,0),"
							+ " to_char(bill_dt,'Month'),cont_agr_type,nvl(inv_pay,'N'),nvl(bill_pay,'N'),C.party_abr,C.party_name,"
							+ " inv_no,to_char(bill_dt,'dd/mm/yyyy'),cont_cust_cd,bill_no,to_char(bill_due_dt,'dd/mm/yyyy'),A.party_cd,inv_flag,"
							+ " (select email_id from hr_emp_mst where emp_cd = A.ENTER_BY) as inv_gen_by,"
							+ " (select email_id from hr_emp_mst where emp_cd = A.VERIFY_BY) as inv_chk_by,"
							+ " (select email_id from hr_emp_mst where emp_cd = A.APRV_BY) as inv_aprv_by,gross_tds_perc,gross_tds_amt "
							+ " FROM FMS_INV_BILL_DTL A,FMS_PARTY_MST C "
							+ " WHERE bill_dt between to_date('"+st_dt+"','dd/mm/yyyy') and to_date('"+end_dt+"','dd/mm/yyyy')"
							+ " AND A.party_cd = C.party_cd"
							+ " AND A.aprv_by > 0 and (aprv_reject_flag is null or aprv_reject_flag='N') ";	
					//System.out.println("transInvSql****"+transInvSql);
					rset = stmt.executeQuery(transInvSql);
					while(rset.next()) {
						
						String mapping_id = rset.getString(1)==null?"":rset.getString(1);
						String cont_agr_no = rset.getString(2)==null?"":rset.getString(2);
						String cont_no = rset.getString(4)==null?"":rset.getString(4);
						String cont_rev_no = rset.getString(5)==null?"":rset.getString(5);
						String cont_agr_type = rset.getString(9)==null?"":rset.getString(9);
						String cont_cust_cd = rset.getString(16)==null?"":rset.getString(16);
						String bill_no = rset.getString(17)==null?"":rset.getString(17);
						String aprv_inv_no = "";
						String inv_no = rset.getString(14)==null?"":rset.getString(14);
						String inv_pay = rset.getString(10)==null?"":rset.getString(10);
						String bill_pay = rset.getString(11)==null?"":rset.getString(11);
						String bill_due_dt = rset.getString(18)==null?"":rset.getString(18);
						double aprv_final_amt = rset.getDouble(7);
						String rev_no = rset.getString(6)==null?"":rset.getString(6);
						String req_column = "",qty_column="",rate_column="";
						String tax_column = "";
						String inv_gen_flag = "";
						String org_inv_column = "";
						
						if(inv_pay.equalsIgnoreCase("Y")) {
							req_column = "nvl(amt,0)"; // System Generated
							rate_column = "nvl(rate,0)"; // System Generated
							tax_column = "nvl(tax_amt,0)";
							aprv_inv_no = rset.getString(14)==null?"":rset.getString(14);
							inv_gen_flag = "S";
							org_inv_column = "AND INV_NO = '"+inv_no+"' ";
							qty_column="nvl(qty,0)";
						
						}else if(bill_pay.equalsIgnoreCase("Y")) {
							req_column = "nvl(bill_amt,0)"; // Party Generated
							tax_column = "nvl(bill_tax_amt,0)";
							aprv_inv_no = rset.getString(17)==null?"":rset.getString(17);
							inv_gen_flag = "P";
							org_inv_column = "AND INV_NO = '"+bill_no+"'";
							qty_column="nvl(bill_qty,0)";
							rate_column = "nvl(bill_rate,0)"; 
						}
						
						String dtlFlag = "N";
						
						String disp_cont_no = "";
						
						
							
							String contSql = "select CONT_NO from FMS_CONT_MST where MAPPING_ID = '"+mapping_id+"' "
									+ " AND CONT_AGR_NO = '"+cont_agr_no+"' AND CONTRACT_NO = '"+cont_no+"'"
									+ " AND CONT_CUST_CD = '"+cont_cust_cd+"' "
									+ " AND CONT_AGR_TYPE = '"+cont_agr_type+"'"
									+ " AND CONTRACT_REV_NO = '"+cont_rev_no+"' " ;
//							System.out.println("contSql****"+contSql);
							rset1 = stmt1.executeQuery(contSql);
							if(rset1.next()) {		
								disp_cont_no = rset1.getString(1)==null?"":rset1.getString(1);
							}
							
							CONT_NO.add(disp_cont_no);
							TRADER_ABBR_TR.add(rset.getString(12)==null?"":rset.getString(12));
							//TRADER_NAME_DOM.add(rset.getString(13)==null?"":rset.getString(13));
							contract_no.add(rset.getString(4)==null?"":rset.getString(4));
							contract_rev_no.add(rset.getString(5)==null?"":rset.getString(5));
							INVOICE_DT_TR.add(rset.getString(15)==null?"":rset.getString(15));
							DUE_DT_TR.add(bill_due_dt);
							INVOICE_MAPPING_ID_TR.add(mapping_id);
							
							REV_NO.add(rev_no);
							PARTY_CD_TR.add(rset.getString(19)==null?"":rset.getString(19));
							CONT_AGR_NO.add(cont_agr_no);
							CONT_AGR_TYPE.add(cont_agr_type);
							CONT_CUST_CD.add(cont_cust_cd);
							TDS_PERC_TR.add(rset.getString(24)==null?"":rset.getString(24));
							TDS_PERC_AMT.add(rset.getString(25)==null?"":rset.getString(25));
							INVOICE_NO_TR.add(aprv_inv_no);
							INVOICE_GEN_FLAG_TR.add(inv_gen_flag);
							
	//						for gross amount 
							double gross_amt = 0,qty=0,rate=0 ; 
							String invDtlSql = "SELECT sum("+req_column+")  FROM FMS_COMPO_INV_DTL A "
									+ " where mapping_id = '"+mapping_id+"' "
									+ " AND cont_agr_no = '"+cont_agr_no+"'"
									+ " AND contract_no = '"+cont_no+"'"
									+ " AND cont_agr_type = '"+cont_agr_type+"'"
									+ " AND cont_cust_cd = '"+cont_cust_cd+"'"
									+ " AND inv_no = '"+inv_no+"'"
									+ " AND A.rev_no = '"+rev_no+"'";
							//System.out.println("invDtlSql****"+invDtlSql);
							rset1 = stmt1.executeQuery(invDtlSql);
							if (rset1.next()) {
								gross_amt = rset1.getDouble(1);
							}
							invDtlSql = "SELECT "+qty_column+","+rate_column+"  FROM FMS_COMPO_INV_DTL A "
									+ " where mapping_id = '"+mapping_id+"' "
									+ " AND cont_agr_no = '"+cont_agr_no+"'"
									+ " AND contract_no = '"+cont_no+"'"
									+ " AND cont_agr_type = '"+cont_agr_type+"'"
									+ " AND cont_cust_cd = '"+cont_cust_cd+"'"
									+ " AND inv_no = '"+inv_no+"'"
									+ " AND A.rev_no = '"+rev_no+"'";
//							System.out.println("invDtlSql****"+invDtlSql);
							rset1 = stmt1.executeQuery(invDtlSql);
							if (rset1.next()) {
								qty = rset1.getDouble(1);
								rate = rset1.getDouble(2);
							}
	//						for tax amount 
							double tax_amt = 0 ;
							String taxDtlSql = "select sum("+tax_column+") from FMS_INV_TAX_DTL A"
									+ " where mapping_id = '"+mapping_id+"' "
									+ " AND cont_agr_no = '"+cont_agr_no+"'"
									+ " AND contract_no = '"+cont_no+"'"
									+ " AND cont_agr_type = '"+cont_agr_type+"'"
									+ " AND cont_cust_cd = '"+cont_cust_cd+"'"
									+ " AND inv_no = '"+inv_no+"'"
									+ " AND A.rev_no = '"+rev_no+"'";
//								System.out.println("taxDtlSql***"+taxDtlSql);
								rset1 = stmt1.executeQuery(taxDtlSql);
								if (rset1.next()) {
									tax_amt = rset1.getDouble(1);
								}
								GROSS_PAY_TR.add(nf6.format(gross_amt));
								TOTAL_TAX_AMT_TR.add(nf6.format(tax_amt));
								INVOICE_NET_AMT_TR.add(nf6.format(aprv_final_amt));
								QTY_TR.add(qty);
								RATE_TR.add(rate);
								
								String refno="";
								refno=""+aprv_inv_no;
								queryString = "SELECT XML_GEN_FLAG, SUN_APPROVAL FROM FMS7_ACCOUNT_APPROVED_DTL " +
								  			  "WHERE INV_CARGO_NO='"+refno+"' AND JOURNAL_TYPE='FMSPR'";
//								System.out.println("Confirmed FMS7_ACCOUNT_APPROVED_DTL Details Query = "+queryString);
								rset1 = stmt1.executeQuery(queryString);
								if(rset1.next())
								{
									XML_GEN_FLAG_TR.add(rset1.getString(1)==null?"N":rset1.getString(1));
									SUN_APPROVAL_TR.add(rset1.getString(2)==null?"N":rset1.getString(2)); //Introduced By Samik Shah On 23rd August, 2011 ...
								}
								else
								{
									XML_GEN_FLAG_TR.add("N");
									SUN_APPROVAL_TR.add("N"); //Introduced By Samik Shah On 23rd August, 2011 ...
								}
						
					}
					
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void get_Purchase_dtls_IGX()
	{

		try
		{		
			//System.out.println("Inside get_Purchase_dtls() Method ...");
			////System.out.println("From month "+month+" To month= "+to_month);
			Vector temp_usd_jbb_igx = new Vector();
			Vector temp_usd_invoice_igx = new Vector();
			Vector temp_usd_dr_cr_igx = new Vector();
			Vector temp_inv_amt_igx = new Vector();			
			Vector temp_tot_cd_amt_igx = new Vector();
			Vector temp_tot_pay_refund_igx = new Vector();
			Vector temp_cd_paid_igx = new Vector();
			Vector final_seller_amt_igx = new Vector();
			Vector unloaded_qty_igx = new Vector();
			String from_dt_igx = "01/"+month+"/"+year;
			String to_dt_igx = "";
			queryString = "Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
			////System.out.println("Last Date Of The Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_dt_igx = rset.getString(1)==null?"0":rset.getString(1);
			}
			////System.out.println("From date "+from_dt+" To Date= "+to_dt);
			queryString = "Select To_char(to_date('"+from_dt_igx+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("From Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				mon = rset.getString(1)==null?"0":rset.getString(1);
			}
			
			queryString = "Select To_char(to_date('"+to_dt_igx+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("To The Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_mon = rset.getString(1)==null?"0":rset.getString(1);
			}
			
			Vector temp_cargo_igx=new Vector();
			Vector temp_price_igx=new Vector();
			Vector temp_trd_cd=new Vector();
			Vector temp_seq_igx=new Vector();
			
			/*String q="SELECT A.CARGO_REF_NO , A.ACT_ARRV_DT,COUNT(A.SPLIT_SEQ) FROM FMS7_CARGO_ARRIVAL_DTL A " +
					  "WHERE A.ACT_ARRV_DT between (to_date('"+from_dt_igx+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_igx+"','dd/mm/yyyy')) GROUP BY A.ACT_ARRV_DT,A.CARGO_REF_NO ORDER BY A.ACT_ARRV_DT ";
			rset=stmt.executeQuery(q);
			while(rset.next())
			{
				temp_cargo_igx.add(rset.getString(1));
				int tempSeq=rset.getInt(3);
				if(tempSeq>1)
					temp_seq_igx.add("Y");
				else
					temp_seq_igx.add("N");
			}*/
			

			String trdcd=" AND C.TRD_CD="+trader_cd+" ";
			String split1="";
			split1=" AND SPLIT_SEQ='0'";
			queryString = "SELECT  B.CARGO_REF_cd, C.TRD_CD,price_unit FROM " +
					  "FMS7_MAN_CONFIRM_CARGO_DTL B, FMS7_MAN_REQ_MST C " +
					  "WHERE  B.MAN_CD=C.MAN_CD AND " +
					  "(B.DELV_From_DT between (to_date('"+from_dt_igx+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_igx+"','dd/mm/yyyy')) OR B.DELV_to_DT between (to_date('"+from_dt_igx+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_igx+"','dd/mm/yyyy')))  AND "
			  		+ "B.DOM_buy_flag='K' ";
			
			if(trader_cd!=null && !trader_cd.trim().equals("") && !trader_cd.trim().equals("0"))
			{
				queryString = queryString + trdcd;
			}
			queryString=queryString;
			
			//System.out.println("Confirmed Cargo Arrival Details Query = "+queryString);
			rset = stmt.executeQuery(queryString);
			while(rset.next())
			{
				temp_cargo_igx.add(rset.getString(1)==null?"":rset.getString(1));
				temp_trd_cd.add(rset.getString(2)==null?"":rset.getString(2));
				temp_price_igx.add(rset.getString(3)==null?"":rset.getString(3));
				if(flag_accounting.equalsIgnoreCase("Approval")){
					
					/*queryString = "SELECT A.INVOICE_NO FROM FMS7_FINAL_SELLER_PAY A WHERE A.CARGO_REF_NO='"+rset.getString(1)+"' "
							+ "AND SPLIT_SEQ='0' ";				
					//System.out.println("FMS7_FINAL_SELLER_PAY >>>>>>>>>>>>> Seller Final Invoice Detail Query = "+queryString);
					rset1 = stmt1.executeQuery(queryString);
					if(rset1.next())
					{

						CARGO_REF_CD_DOM.add(rset.getString(1)==null?"":rset.getString(1));
						TRD_CD_DOM.add(rset.getString(2)==null?"0":rset.getString(2));
						queryString = "SELECT VESSEL_NM, TO_CHAR(ACT_ARRV_DT,'DD/MM/YYYY'), " +
								  "TO_CHAR(ACT_ARRV_DT,'Month') FROM FMS7_CARGO_ARRIVAL_dTL WHERE CARGO_REF_NO='"+rset.getString(1)+"'";
							
							rset1 = stmt1.executeQuery(queryString);
							if(rset1.next())
							{
								VESSEL_NM_DOM.add(rset1.getString(1)==null?"":rset1.getString(1));
							}else{
								VESSEL_NM_DOM.add("");
							}
							queryString = "SELECT TO_CHAR(period_st_DT,'DD/MM/YYYY'), " +
									  "TO_CHAR(period_end_DT,'DD/MM/YYYY') FROM FMS7_DOM_PUR_INV_dTL WHERE CARGO_REF_NO='"+rset.getString(1)+"'";
								
								rset1 = stmt1.executeQuery(queryString);
								if(rset1.next())
								{
									String st_dt=rset1.getString(1)==null?"":rset1.getString(1);
									String end_dt=rset1.getString(2)==null?"":rset1.getString(2);
									duration_igx.add(st_dt+" - "+end_dt);
								}else{
									duration_igx.add("");
								}
					
					}*/
				}else{
					/*CARGO_REF_CD_DOM.add(rset.getString(1)==null?"":rset.getString(1));
					TRD_CD_DOM.add(rset.getString(2)==null?"0":rset.getString(2));*/
					//temp_cargo_igx.add(rset.getString(1)==null?"":rset.getString(1));
					/*queryString = "SELECT VESSEL_NM, TO_CHAR(ACT_ARRV_DT,'DD/MM/YYYY'), " +
							  "TO_CHAR(ACT_ARRV_DT,'Month') FROM FMS7_CARGO_ARRIVAL_dTL WHERE CARGO_REF_NO='"+rset.getString(1)+"'";
						
						rset1 = stmt1.executeQuery(queryString);
						if(rset1.next())
						{
							VESSEL_NM_DOM.add(rset1.getString(1)==null?"":rset1.getString(1));
						}else{
							VESSEL_NM_DOM.add("");
						}*/
						
				}
			}
			queryString = "SELECT  distinct B.CARGO_REF_no, B.PARTY_CD FROM " +
					  "FMS7_DOM_PUR_INV_DTL B WHERE   " +
					  "(B.period_st_dt between (to_date('"+from_dt_igx+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_igx+"','dd/mm/yyyy')) OR B.period_end_DT between (to_date('"+from_dt_igx+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_igx+"','dd/mm/yyyy')))  AND "
			  		+ "B.CARGO_REF_no like '99%' order by B.CARGO_REF_no";
			
			if(trader_cd!=null && !trader_cd.trim().equals("") && !trader_cd.trim().equals("0"))
			{
				queryString = queryString + trdcd;
			}
			//System.out.println("queryString--"+queryString);
			rset = stmt.executeQuery(queryString);
			while(rset.next()){
				String cargo_no=rset.getString(1)==null?"":rset.getString(1);
				queryString = "SELECT  price_unit FROM " +
						  "FMS7_MAN_CONFIRM_CARGO_DTL B, FMS7_MAN_REQ_MST C " +
						  "WHERE  B.MAN_CD=C.MAN_CD AND B.DOM_buy_flag='K' and cargo_ref_cd='"+cargo_no+"'";
				rset1 = stmt1.executeQuery(queryString);
				//System.out.println("queryString--"+queryString);
				if(rset1.next()){
					if(!temp_cargo_igx.contains(cargo_no)){
						temp_cargo_igx.add(rset.getString(1)==null?"":rset.getString(1));
						temp_trd_cd.add(rset.getString(2)==null?"":rset.getString(2));
						temp_price_igx.add(rset1.getString(1)==null?"":rset1.getString(1));
					}
				}else{
					if(!temp_cargo_igx.contains(cargo_no)){
						temp_cargo_igx.add(rset.getString(1)==null?"":rset.getString(1));
						temp_trd_cd.add(rset.getString(2)==null?"":rset.getString(2));
						temp_price_igx.add("");
					}
				}
				
				
			}
		//	}
//System.out.println("temp_cargo_igx---"+temp_cargo_igx);			
			
			double usd_mmbtu=0;
			String exchg_rate_2_igx = "";
			Vector duration=new Vector();
			Vector duration2=new Vector();
			String dur1="";
				for(int jk=1;jk<=12;jk++){
					//System.out.println("dur11---"+month+"---"+to_month);
					//if(jk>=Integer.parseInt(month) && jk<=Integer.parseInt(to_month)){
						//System.out.println("dur11-->>>>>>>>>>>-");
						if(jk<10){
							dur1="01/0"+jk+"/"+year+"-15/0"+jk+"/"+year;
						}else{
							dur1="01/"+jk+"/"+year+"-15/"+jk+"/"+year;
						}
						//System.out.println("dur11---"+dur1);
						duration.add(dur1);
						 String to_dt="";
						 if(jk<10){
						    queryString = "Select To_char(Last_Day(to_date('0"+jk+"/"+year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
						 }else{
							 queryString = "Select To_char(Last_Day(to_date('"+jk+"/"+year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
						 }
							////System.out.println("Last Date Of The Selected Month = "+queryString);
							rset = stmt.executeQuery(queryString);			
							if(rset.next())
							{
								to_dt = rset.getString(1)==null?"0":rset.getString(1);
							}
							if(jk<10){
								//duration2.add("16/0"+jk+"/"+year+"-"+to_dt);
								duration.add("16/0"+jk+"/"+year+"-"+to_dt);
							}else{
								duration.add("16/"+jk+"/"+year+"-"+to_dt);
							}
				//	}
				}
//				System.out.println("-1--"+duration);
//			    System.out.println("-2--"+duration2);
				String tds_flag="";
				String queryString1 = "SELECT TURNOVER_FLAG FROM FMS7_SUPPLIER_TURNOVER_DTL WHERE supplier_cd=1";
				//System.out.println("Seller Payment Details Query = "+queryString);
				rset1 = stmt1.executeQuery(queryString1);
				if(rset1.next())
				{
					tds_flag= rset1.getString(1)==null?"":rset1.getString(1);
				}
				/* for Transaction Charges */
				double trans_perc = 0,sgst = 0, cgst = 0,igst = 0 ; 
				String transChargeSql = "Select nvl(TRANSACTION_PERC,0),nvl(SGST,0),nvl(CGST,0),nvl(IGST,0)"
						+ "  from  FMS10_IGX_TRANSACTION_DTL "
						+ " where EFFECTIVE_DT = (select max(A.EFFECTIVE_DT) from  FMS10_IGX_TRANSACTION_DTL A)";
				rset1 = stmt1.executeQuery(transChargeSql);
				if(rset1.next()) {
					trans_perc = rset1.getDouble(1);
					sgst = rset1.getDouble(2);
					cgst = rset1.getDouble(3);
					igst = rset1.getDouble(4);
				}
			for(int i=0; i<temp_cargo_igx.size(); i++)
			{
				
				/*if(SPLIT_SEQ_DOM.elementAt(i).equals("1"))
				{
					refno=""+CARGO_REF_CD_DOM.elementAt(i)+"-"+"A";
				}
				else if(SPLIT_SEQ_DOM.elementAt(i).equals("2"))
				{
					refno=""+CARGO_REF_CD_DOM.elementAt(i)+"-"+"B";
				}
				else
				{*/
					
				
				
				
				//Logic for Final Seller Payment
				
				
				/*queryString = "SELECT A.INVOICE_NO,TO_CHAR(A.INVOICE_DT,'DD/MM/YYYY'), A.ACTUAL_UNLOADED_QTY, " +
							  "A.CONFIRM_PRICE, A.INVOICE_AMT, TO_CHAR(A.DUE_DT,'DD/MM/YYYY'),SUN_APPROVAL,CHECKED_FLAG,AUTHORIZED_FLAG,APPROVED_FLAG "+
				  			  "FROM FMS7_FINAL_SELLER_PAY A WHERE A.CARGO_REF_NO="+CARGO_REF_CD_DOM.elementAt(i)+" AND SPLIT_SEQ='0' ";		
				System.out.println("FMS7_FINAL_SELLER_PAY >>>>>>>>>>>>> Seller Final Invoice Detail Query = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					INVOICE_NO_DOM.add(rset.getString(1)==null?"":rset.getString(1));
					INVOICE_DT_DOM.add(rset.getString(2)==null?"":rset.getString(2));						
					
					CONF_PRICE_DOM.add(rset.getString(4)==null?"":nf2.format(Double.parseDouble(rset.getString(4))));
					CONF_PRICE_NUMERIC_DOM.add(rset.getString(4)==null?"0.0000":nf2.format(Double.parseDouble(rset.getString(4))));
					USD_VAL_INVOICE_DOM.add(rset.getString(5)==null?"-":nf3.format(Double.parseDouble(rset.getString(5))));
					
					USD_VAL_INVOICE_NUMERIC_DOM.add(rset.getString(5)==null?"0.00":nf.format(Double.parseDouble(rset.getString(5))));
					purchase_total_invoice_value_igx += Double.parseDouble(rset.getString(5)==null?"0":nf.format(Double.parseDouble(rset.getString(5))));
					DUE_DT_DOM.add(rset.getString(6)==null?"":rset.getString(6));
					Check_flag_DOM.add(rset.getString(8)==null?"":rset.getString(8));
					Authorize_flag_DOM.add(rset.getString(9)==null?"":rset.getString(9));
					Approve_flag_DOM.add(rset.getString(10)==null?"":rset.getString(10));*/
				
//				    System.out.println("-1--"+duration);
//				    System.out.println("-2--"+duration2);
				/*queryString = "SELECT CARGO_SEL FROM FMS7_TRADER_CONT_MST  ";
			//	System.out.println("query---"+queryString);
				rset1 = stmt1.executeQuery(queryString);
				while(rset1.next())
				{
					String cargo=rset1.getString(1)==null?"":rset1.getString(1);
					if(cargo.contains(""+temp_cargo_igx.elementAt(i))){
						queryString = "SELECT distinct(sn_no),customer_cd  "
							+ "FROM FMS7_trader_cont_mst WHERE cargo_sel='"+cargo+"' ";
						rset2=stmt2.executeQuery(queryString);
						while(rset2.next()){
							queryString = "SELECT billing_freq FROM FMS7_trader_billing_dtl WHERE sn_no='"+rset2.getString(1)+"' and "
									+ "customer_cd='"+rset2.getString(2)+"' ";
								rset3=stmt3.executeQuery(queryString);
								while(rset3.next()){
									bill_freq=rset3.getString(1)==null?"":rset3.getString(1);
								}
						}
						
						
					}
				}*/
					//for(int k=0;k<2;k++){
				//System.out.println("durat--"+duration.size());
						for(int k=0;k<duration.size();k++){
							//System.out.println("durat-1-");
							int cnt=0;
							String temp1[]=duration.elementAt(k).toString().split("-");
							String frmdt=temp1[0];
							String todt=temp1[1];
							String frm_dt="01/"+month+"/"+year;
							String to_dt="02/"+to_month+"/"+to_year;
							String take_flag="";
							queryString = "SELECT TO_DATE('"+frmdt+"','dd/mm/yyyy') FROM DUAL WHERE to_date('"+frmdt+"','dd/mm/yyyy') BETWEEN "
										+ "  to_date('"+frm_dt+"','dd/mm/yyyy') and to_date('"+to_dt+"','dd/mm/yyyy')";
							
							rset1 = stmt1.executeQuery(queryString);
							if(rset1.next())
							{
								take_flag="Y";
							}else{
								take_flag="";
							}
							
						if(take_flag.equals("Y")){	
							queryString = "SELECT ALLOC_QTY,EXCHG_RT_VAL,INVOICE_AMT,INVOICE_TAX_AMT,to_char(period_st_dt,'Month'),invoice_type"
									+ ",party_cd,remark,invoice_no,to_char(period_st_dt,'dd/mm/yyyy'),INVOICE_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY'),"
									+ "CONF_PRICE, INVOICE_AMT_USD, TO_CHAR(DUE_DT,'DD/MM/YYYY'),CHECKED_FLAG,AUTHORIZED_FLAG,APPROVED_FLAG,"
									+ "to_char(period_end_dt,'dd/mm/yyyy'),SPLIT_FLAG,CONTRACT_NO,CONTRACT_REV_NO,pLANT_cd,buyer_plant_cd "
								+ "FROM FMS7_DOM_PUR_INV_dTL WHERE CARGO_REF_NO='"+temp_cargo_igx.elementAt(i)+"' and period_st_dt=to_date('"+frmdt+"','dd/mm/yyyy')"
										+ " and period_end_dt=to_date('"+todt+"','dd/mm/yyyy') ";
//							System.out.println("temp_price_igx.elementAt(i)---"+temp_price_igx.elementAt(i));
							rset1 = stmt1.executeQuery(queryString);
							while(rset1.next())
							{
								//System.out.println("durat-2-");
								Vector sg_drcr_no=new Vector();
								String aprv_flag=rset1.getString(18)==null?"":rset1.getString(18);
								String cont_no=rset1.getString(21)==null?"0":rset1.getString(21);
								String cont_rev_no=rset1.getString(22)==null?"0":rset1.getString(22);
								String platcd=rset1.getString(23)==null?"0":rset1.getString(23);
								String bu_unit="";
								if(rset1.getString(24).equals("0")){
									bu_unit="SEIPL -REG(GJ)";
								}else{
									queryString1 = "SELECT PLANT_NAME,PLANT_SHORT_ABBR FROM FMS7_SUPPLIER_PLANT_DTL "
											+ "WHERE SUPPLIER_CD='"+supplier_cd+"' AND SEQ_NO='"+rset1.getString(24)+"'";
									rset2=stmt2.executeQuery(queryString1);
									if(rset2.next())
									{
										bu_unit=rset2.getString(2)==null?"":rset2.getString(2);
									}
								}
								if(aprv_flag.equalsIgnoreCase("") || aprv_flag.equalsIgnoreCase("N")){
									//System.out.println("durat-3-");
									Vector pg_drcr_no=new Vector();
									queryString = "SELECT ALLOC_QTY,EXCHG_RT_VAL,INVOICE_AMT,INVOICE_TAX_AMT,to_char(period_st_dt,'Month'),invoice_type"
											+ ",party_cd,remark,invoice_no,to_char(period_st_dt,'dd/mm/yyyy'),INVOICE_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY'),"
											+ "CONF_PRICE, INVOICE_AMT_USD, TO_CHAR(DUE_DT,'DD/MM/YYYY'),CHECKED_FLAG,AUTHORIZED_FLAG,APPROVED_FLAG,"
											+ "to_char(period_end_dt,'dd/mm/yyyy'),SPLIT_FLAG,CONTRACT_NO,CONTRACT_REV_NO,pLANT_cd,adj_sign,adj_amt "
											+ "FROM FMS7_DOM_PUR_INV_dTL_PG WHERE CARGO_REF_NO='"+temp_cargo_igx.elementAt(i)+"' and period_st_dt=to_date('"+frmdt+"','dd/mm/yyyy')"
											+ " and period_end_dt=to_date('"+todt+"','dd/mm/yyyy') and party_cd='"+rset1.getString(7)+"' and approved_flag='Y' "
											+ "and buyer_plant_cd='"+rset1.getString(24)+"'";
									//System.out.println("queryString---"+queryString);
									rset2 = stmt2.executeQuery(queryString);
									if(rset2.next()){
										
										//System.out.println("durat-4-");
										String adj_sign=rset2.getString(24)==null?"":rset2.getString(24);
										String adj_amt=rset2.getString(25)==null?"":rset2.getString(25);
										pg_drcr_no.add(rset2.getString(9)==null?"0":rset2.getString(9));
										cont_no=rset2.getString(21)==null?"0":rset2.getString(21);
										cont_rev_no=rset2.getString(22)==null?"0":rset2.getString(22);
										platcd=rset2.getString(23)==null?"0":rset2.getString(23);
										CARGO_REF_CD_IGX.add(temp_cargo_igx.elementAt(i));
										Vsupp_plant_Abbr_IGX.add(bu_unit);
										CARGO_PRICE_IGX.add(temp_price_igx.elementAt(i));
										VESSEL_NM_IGX.add("");
										contract_no_igx.add(rset2.getString(21)==null?"":rset2.getString(21));
										contract_rev_no_igx.add(rset2.getString(22)==null?"":rset2.getString(22));
										plant_cd_igx.add(rset2.getString(23)==null?"":rset2.getString(23));
										TRD_CD_IGX.add(rset2.getString(7)==null?"0":rset2.getString(7));
										ACTUAL_UNLOADED_QTY_IGX.add(rset2.getString(1)==null?"-":nf3.format(Double.parseDouble(rset2.getString(1))));
										unloaded_qty_igx.add(rset2.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset2.getString(1))));
										ACTUAL_UNLOADED_QTY_NUMERIC_IGX.add(rset2.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset2.getString(1))));
										purchase_total_qty_igx += Double.parseDouble(rset2.getString(1)==null?"0":nf.format(Double.parseDouble(rset2.getString(1))));
										exchg_rate_2_igx=rset2.getString(2)==null?"0":rset2.getString(2);
										String inv_currency="";
										if((exchg_rate_2_igx.equals("0") || exchg_rate_2_igx.equals("")) && (!temp_price_igx.elementAt(i).equals("1"))){
											TRADER_INV_CURRENCY_IGX.add("2");
											inv_currency="2";
										}else{
											TRADER_INV_CURRENCY_IGX.add("");
										}
										EXCHG_RATE_VALUE_IGX.add(rset2.getString(2)==null?"-":nf2.format(Double.parseDouble(rset2.getString(2))));
										INVOICE_AMT_IGX.add(rset2.getString(3)==null?"0":nf.format(Double.parseDouble(rset2.getString(3))));
										INVOICE_TAX_AMT_IGX.add(rset2.getString(4)==null?"0":nf.format(Double.parseDouble(rset2.getString(4))));
										String taxamt=rset2.getString(4)==null?"0":nf.format(Double.parseDouble(rset2.getString(4)));
										purchase_total_tax_inr_igx+=Double.parseDouble((taxamt));
										temp_inv_amt_igx.add(rset2.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset2.getString(3))));
										double NETAMT=Double.parseDouble(nf.format(Double.parseDouble(rset2.getString(3))))+Double.parseDouble((taxamt));
										INVOICE_AMT_net_IGX.add(nf.format(NETAMT));
										ACT_ARRV_MONTH_IGX.add(rset2.getString(5)==null?"":rset2.getString(5));
										String inv_type=rset2.getString(6)==null?"":rset2.getString(6);
										if(tds_flag.equals("Y")){
											//double tds_Amt=(Double.parseDouble(nf.format(Double.parseDouble(rset2.getString(3)==null?"0":rset2.getString(3))))* Double.parseDouble(tds_perc))/100;
											double tds_Amt=(Double.parseDouble(nf.format(NETAMT))* Double.parseDouble(tds_perc))/100;
											INVOICE_AMT_TDS_IGX.add(nf.format(tds_Amt));
										}else{
											INVOICE_AMT_TDS_IGX.add("-");
										}
										double trans_charge = (Double.parseDouble(rset2.getString(1)) * trans_perc);
										double total_trans_charge = ((trans_charge*cgst)/100) + ((trans_charge*sgst)/100);
										INVOICE_AMT_TRANS_IGX.add(trans_charge);
										INVOICE_TAXAMT_TRANS_IGX.add(total_trans_charge);
										
										if(inv_type.equals("1")){
											inv_type="Monthly";
										}else if(inv_type.equals("2")){
											inv_type="Fortnightly";
										}
										else if(inv_type.equals("3")){
											inv_type="Weekly";
										}
										ACT_ARRV_DT_IGX.add(rset2.getString(10)==null?"":rset2.getString(10));
										String rmk=rset2.getString(8)==null?"":rset2.getString(8);
										String usd_amt=rset2.getString(14)==null?"0.00":nf.format(Double.parseDouble(rset2.getString(14)));
										double amtadj=0;
										if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_currency.equals("2")){
											if(adj_sign.equals("+")){
												 amtadj=Double.parseDouble(usd_amt)+Double.parseDouble(adj_amt);
											}else if(adj_sign.equals("-")){
												amtadj=Double.parseDouble(usd_amt)-Double.parseDouble(adj_amt);
											}
											if(temp_price_igx.elementAt(i).equals("1")){
												USD_VAL_INVOICE_IGX.add("-");
												USD_VAL_INVOICE_NUMERIC_IGX.add("0");
											//	purchase_total_invoice_value_igx += Double.parseDouble(nf.format(amtadj));
											}else{
												USD_VAL_INVOICE_IGX.add(nf3.format(amtadj));
												USD_VAL_INVOICE_NUMERIC_IGX.add(nf.format(amtadj));
												purchase_total_invoice_value_igx += Double.parseDouble(nf.format(amtadj));
											}
											
										}else{
											if(temp_price_igx.elementAt(i).equals("1")){
												USD_VAL_INVOICE_IGX.add("-");
												USD_VAL_INVOICE_NUMERIC_IGX.add("0");
												//purchase_total_invoice_value_igx += Double.parseDouble(rset2.getString(14)==null?"0":nf.format(Double.parseDouble(rset2.getString(14))));
											}else{
												USD_VAL_INVOICE_IGX.add(rset2.getString(14)==null?"-":nf3.format(Double.parseDouble(rset2.getString(14))));
												
												USD_VAL_INVOICE_NUMERIC_IGX.add(rset2.getString(14)==null?"0.00":nf.format(Double.parseDouble(rset2.getString(14))));
												purchase_total_invoice_value_igx += Double.parseDouble(rset2.getString(14)==null?"0":nf.format(Double.parseDouble(rset2.getString(14))));
											}
										}
										double tax_amt_usd=0;
										if(!rmk.equals("")){
											TAX_rmk_IGX.add(rmk+"%");
											String temp[]=rmk.split("@");
											if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_currency.equals("2")){
												tax_amt_usd=(amtadj)* Double.parseDouble(temp[1].trim())/100;
											}else{
											 tax_amt_usd=(Double.parseDouble(rset2.getString(14)==null?"0":rset2.getString(14)))* Double.parseDouble(temp[1].trim())/100;
											}
											
										}else{
											TAX_rmk_IGX.add("");
										}
										TAX_amt_usd_IGX.add(nf.format(tax_amt_usd));
										invoice_type_IGX.add(inv_type);
										purchase_total_tax_inr_igx+=tax_amt_usd;
										usd_mmbtu=(tax_amt_usd+Double.parseDouble(rset2.getString(14)==null?"0":rset2.getString(14)))/Double.parseDouble(rset2.getString(1));
										usdmmbtu.add(usd_mmbtu);
										INVOICE_NO_IGX.add(rset2.getString(11)==null?"":rset2.getString(11));
										INVOICE_DT_IGX.add(rset2.getString(12)==null?"":rset2.getString(12));	
										
										CONF_PRICE_IGX.add(rset2.getString(13)==null?"":nf2.format(Double.parseDouble(rset2.getString(13))));
										CONF_PRICE_NUMERIC_IGX.add(rset2.getString(13)==null?"0.0000":nf2.format(Double.parseDouble(rset2.getString(13))));
										
										
										
										DUE_DT_IGX.add(rset2.getString(15)==null?"":rset2.getString(15));
										Check_flag_IGX.add(rset2.getString(16)==null?"":rset2.getString(16));
										Authorize_flag_IGX.add(rset2.getString(17)==null?"":rset2.getString(17));
										Approve_flag_IGX.add(rset2.getString(18)==null?"":rset2.getString(18));
										String st_dt=rset2.getString(10)==null?"":rset2.getString(10);
										String end_dt=rset2.getString(19)==null?"":rset2.getString(19);
										duration_igx.add(st_dt+" - "+end_dt);
										
										queryString = "SELECT TRADER_NAME,TRADER_ABBR FROM FMS7_TRADER_MST WHERE FLAG='T' AND TRADER_CD="+rset2.getString(7)+"";
										//System.out.println("TRADER Master query = "+queryString);
										rset = stmt.executeQuery(queryString);
										if(rset.next())
										{			
											TRADER_NAME_IGX.add(rset.getString(1)==null?"":rset.getString(1));
											TRADER_ABBR_IGX.add(rset.getString(2)==null?"":rset.getString(2));
										}
										else
										{
											TRADER_NAME_IGX.add("");
											TRADER_ABBR_IGX.add("");
										}
										cnt++;
										String split_flag=rset2.getString(20)==null?"":rset2.getString(20);
										if(split_flag.equals("Y")){
											String split_val="";
											queryString = "SELECT split_value from fms7_trader_plant_mst A where sn_no='"+rset2.getString(21)+"' "
													+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
													+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
													+ " customer_cd='"+rset2.getString(7)+"' and FLAG='K' ";
											//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
											rset = stmt.executeQuery(queryString);
											if(rset.next())
											{
												split_val=rset.getString(1)==null?"":rset.getString(1);
											}else{
												queryString = "SELECT split_value from fms7_trader_OTHER_plant_mst A where sn_no='"+rset2.getString(21)+"' "
													+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_OTHER_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
													+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
													+ " customer_cd='"+rset2.getString(7)+"' and FLAG='K'  ";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset = stmt.executeQuery(queryString);
												if(rset.next())
												{
													split_val=rset.getString(1)==null?"":rset.getString(1);
												}
											}
											
											
											queryString = "SELECT MAN_CD,MAN_CONF_CD,CARGO_SEQ_NO,price FROM FMS7_MAN_CONFIRM_CARGO_DTL WHERE CARGO_REF_CD='"+temp_cargo_igx.elementAt(i)+"' ";
											//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
											rset = stmt.executeQuery(queryString);
											if(rset.next())
											{
												String cont_mapp_id="B-%-"+rset.getString(2)+"-0-"+rset.getString(1)+"-"+rset.getString(3);
												String partycd="";
												queryString = "SELECT TRD_CD FROM FMS7_MAN_REQ_MST WHERE MAN_CD='"+rset.getString(1)+"' ";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset3 = stmt3.executeQuery(queryString);
												if(rset3.next())
												{
													partycd=rset3.getString(1)==null?"":rset3.getString(1);
												}
												
												//queryString = "SELECT nvl(SUM(EXIT_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
												queryString = "SELECT nvl(SUM(ENTRY_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
														+ " to_date('"+frmdt+"','dd/mm/yyyy') and  to_date('"+todt+"','dd/mm/yyyy') and party_cd='"+partycd+"' ";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset3 = stmt3.executeQuery(queryString);
												if(rset3.next())
												{
													QTY_MSG_IGX.add(split_val+" % of "+rset3.getDouble(1));
												}else{
													QTY_MSG_IGX.add("");
												}
											}else{
												QTY_MSG_IGX.add("");
											}
										}else{
											QTY_MSG_IGX.add("");
										}
										if(!adj_amt.equals("") && (!adj_amt.equals("0"))){
											String title="";
											double ori_gross=rset2.getDouble(1) * rset2.getDouble(13);
											//System.out.println("ori_gross = "+ori_gross);
											String gross="";
											String gros="";
											if(inv_currency.equals("2")){
												gross=nf.format(ori_gross);
											}else{
												gros=nf.format(ori_gross);
												//System.out.println("ori_gross = "+gros);
												gross=nf.format(Double.parseDouble(gros) * Double.parseDouble(rset2.getString(2)==null?"0":rset2.getString(2)));
											}
											if(adj_sign.equals("+")){
												title="Gross Amount ("+gross+") + Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
											}else if(adj_sign.equals("-")){
												title="Gross Amount ("+gross+") - Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
											}
											
											Vtitle.add(title);
										}else{
											Vtitle.add("");
										}
										//System.out.println("durat-41-"+pg_drcr_no.size());
										for(int kk=0;kk<pg_drcr_no.size();kk++){
											queryString="SELECT dr_cr_no FROM FMS7_DOM_PUR_INV_DRCR_DTL WHERE party_Cd='"+rset2.getString(7)+"'"
													+ " and cargo_ref_no='"+temp_cargo_igx.elementAt(i)+"' and contract_no='"+cont_no+"'"
													+ " and plant_cd='"+platcd+"' "
													+ " and buyer_plant_cd='"+rset1.getString(24)+"' and invoice_no='"+pg_drcr_no.elementAt(kk)+"' ";
											//System.out.println("queryString---"+queryString);
											rset3 = stmt3.executeQuery(queryString);
											while(rset3.next())
											{
												if(!pg_drcr_no.contains(rset3.getString(1)))
												pg_drcr_no.add(rset3.getString(1)==null?"":rset3.getString(1));
											}
										}
										//System.out.println("durat-41-end "+pg_drcr_no.size());
										for(int kk=0;kk<pg_drcr_no.size();kk++){
										//if(Approve_flag_IGX.elementAt(i).equals("Y")){
										queryString = "SELECT DR_CR_AMT_USD,DR_CR_AMT_INR,DR_CR_QTY,DR_CR_PRICE,DR_CR_NO,DR_CR_EXCHG_RT_VAL,DR_CR_TAX_AMT "
													+ ",TO_CHAR(DR_CR_DT,'DD/MM/YYYY'),TO_CHAR(DR_CR_due_DT,'DD/MM/YYYY'),approved_flag,adj_sign,adj_Amt "
													+ "FROM FMS7_DOM_PUR_INV_DRCR_DTL "
													+ " WHERE CARGO_REF_NO='"+temp_cargo_igx.elementAt(i)+"' and party_Cd ='"+rset2.getString(7)+"' "
													+ "and contract_no='"+cont_no+"' and contract_rev_no='"+cont_rev_no+"' "
													+ "and plant_cd='"+platcd+"' and dr_cr_no='"+pg_drcr_no.elementAt(kk)+"' and buyer_plant_cd='"+rset1.getString(24)+"'";
										//System.out.println("Confirmed FMS7_ACCOUNT_APPROVED_DTL Details Query = "+queryString);
										rset3 = stmt3.executeQuery(queryString);
										if(rset3.next())
										{
											//System.out.println("durat-42-start "+pg_drcr_no.size());
											//Vtitle.add("");
											adj_amt=rset3.getString(12)==null?"":rset3.getString(12);
											adj_sign=rset3.getString(11)==null?"":rset3.getString(11);
											CARGO_REF_CD_IGX.add(temp_cargo_igx.elementAt(i));
											Vsupp_plant_Abbr_IGX.add(bu_unit);
											CARGO_PRICE_IGX.add(temp_price_igx.elementAt(i));
											VESSEL_NM_IGX.add("");
											contract_no_igx.add("");
											contract_rev_no_igx.add("");
											plant_cd_igx.add("");
											TRD_CD_IGX.add(rset1.getString(7)==null?"0":rset1.getString(7));
											ACTUAL_UNLOADED_QTY_IGX.add(rset3.getString(3)==null?"-":nf3.format(Double.parseDouble(rset3.getString(3))));
											unloaded_qty_igx.add(rset3.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(3))));
											ACTUAL_UNLOADED_QTY_NUMERIC_IGX.add(rset3.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(3))));
											purchase_total_qty_igx += Double.parseDouble(rset3.getString(3)==null?"0":nf.format(Double.parseDouble(rset3.getString(3))));
											exchg_rate_2_igx=rset3.getString(6)==null?"0":rset3.getString(6);
											String inv_curr="";
											if((exchg_rate_2_igx.equals("0") || exchg_rate_2_igx.equals("")) && (!temp_price_igx.elementAt(i).equals("1"))){
												TRADER_INV_CURRENCY_IGX.add("2");
												inv_curr="2";
											}else{
												TRADER_INV_CURRENCY_IGX.add("");
											}
											EXCHG_RATE_VALUE_IGX.add(rset3.getString(6)==null?"-":nf2.format(Double.parseDouble(rset3.getString(6))));
											INVOICE_AMT_IGX.add(rset3.getString(2)==null?"0":nf.format(Double.parseDouble(rset3.getString(2))));
											INVOICE_TAX_AMT_IGX.add(rset3.getString(7)==null?"0":nf.format(Double.parseDouble(rset3.getString(7))));
											taxamt=rset3.getString(7)==null?"0":nf.format(Double.parseDouble(rset3.getString(7)));
											purchase_total_tax_inr_igx+=Double.parseDouble((taxamt));
											if(inv_curr.equals("2")){
												temp_inv_amt_igx.add("0");
											}else{
												temp_inv_amt_igx.add(rset3.getString(2)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(2))));
											}
											NETAMT=Double.parseDouble(nf.format(Double.parseDouble(rset3.getString(2))))+Double.parseDouble((taxamt));
											INVOICE_AMT_net_IGX.add(nf.format(NETAMT));
											ACT_ARRV_MONTH_IGX.add(rset1.getString(5)==null?"":rset1.getString(5));
											inv_type=rset1.getString(6)==null?"":rset1.getString(6);
											if(tds_flag.equals("Y")){
												if(inv_curr.equals("2")){
													INVOICE_AMT_TDS_IGX.add("-");
												}else{
												//double tds_Amt=((Double.parseDouble(rset3.getString(2)==null?"0":rset3.getString(2)))* Double.parseDouble(tds_perc))/100;
													double tds_Amt=((Double.parseDouble(nf.format(NETAMT)))* Double.parseDouble(tds_perc))/100;
												INVOICE_AMT_TDS_IGX.add(nf.format(tds_Amt));
												}
											}else{
												INVOICE_AMT_TDS_IGX.add("-");
											}
											trans_charge = (Double.parseDouble(rset3.getString(3)) * trans_perc);
											total_trans_charge = ((trans_charge*cgst)/100) + ((trans_charge*sgst)/100);
											INVOICE_AMT_TRANS_IGX.add(trans_charge);
											INVOICE_TAXAMT_TRANS_IGX.add(total_trans_charge);
											if(inv_type.equals("1")){
												inv_type="Monthly";
											}else if(inv_type.equals("2")){
												inv_type="Fortnightly";
											}
											else if(inv_type.equals("3")){
												inv_type="Weekly";
											}
											ACT_ARRV_DT_IGX.add(rset1.getString(10)==null?"":rset1.getString(10));
											rmk=rset1.getString(8)==null?"":rset1.getString(8);
											usd_amt=rset3.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(1)));
											amtadj=0;
											if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_currency.equals("2")){
												if(adj_sign.equals("+")){
													 amtadj=Double.parseDouble(usd_amt)+Double.parseDouble(adj_amt);
												}else if(adj_sign.equals("-")){
													amtadj=Double.parseDouble(usd_amt)-Double.parseDouble(adj_amt);
												}
												if(temp_price_igx.elementAt(i).equals("1")){
													USD_VAL_INVOICE_IGX.add("-");
													USD_VAL_INVOICE_NUMERIC_IGX.add("0");
												}else{
													USD_VAL_INVOICE_IGX.add(nf3.format(amtadj));
													USD_VAL_INVOICE_NUMERIC_IGX.add(nf.format(amtadj));
													purchase_total_invoice_value_igx += Double.parseDouble(nf.format(amtadj));
												}
											}else{
												if(temp_price_igx.elementAt(i).equals("1")){
													USD_VAL_INVOICE_IGX.add("-");
													USD_VAL_INVOICE_NUMERIC_IGX.add("0");
												}else{
												USD_VAL_INVOICE_IGX.add(rset3.getString(1)==null?"-":nf3.format(Double.parseDouble(rset3.getString(1))));
												USD_VAL_INVOICE_NUMERIC_IGX.add(rset3.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(1))));
												purchase_total_invoice_value_igx += Double.parseDouble(rset3.getString(1)==null?"0":nf.format(Double.parseDouble(rset3.getString(1))));
												}
											}
											tax_amt_usd=0;
											if(!rmk.equals("")){
												TAX_rmk_IGX.add(rmk+"%");
												String temp[]=rmk.split("@");
												if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_currency.equals("2")){
													tax_amt_usd=(amtadj)* Double.parseDouble(temp[1].trim())/100;
												}else{
												 tax_amt_usd=(Double.parseDouble(rset3.getString(1)==null?"0":rset3.getString(1)))* Double.parseDouble(temp[1].trim())/100;
												}
												
											}
											TAX_amt_usd_IGX.add(nf.format(tax_amt_usd));
											invoice_type_IGX.add(inv_type);
											purchase_total_tax_inr_igx+=tax_amt_usd;
											usd_mmbtu=(tax_amt_usd+Double.parseDouble(rset3.getString(1)==null?"0":rset3.getString(1)))/Double.parseDouble(rset3.getString(3));
											usdmmbtu.add(usd_mmbtu);
											INVOICE_NO_IGX.add(rset3.getString(5)==null?"":rset3.getString(5));
											INVOICE_DT_IGX.add(rset3.getString(8)==null?"":rset3.getString(8));	
											CONF_PRICE_IGX.add(rset3.getString(4)==null?"":nf2.format(Double.parseDouble(rset3.getString(4))));
											CONF_PRICE_NUMERIC_IGX.add(rset3.getString(4)==null?"0.0000":nf2.format(Double.parseDouble(rset3.getString(4))));
											//USD_VAL_INVOICE_IGX.add(rset3.getString(1)==null?"-":nf3.format(Double.parseDouble(rset3.getString(1))));
											//USD_VAL_INVOICE_NUMERIC_IGX.add(rset3.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(1))));
											//purchase_total_invoice_value_igx += Double.parseDouble(rset3.getString(1)==null?"0":nf.format(Double.parseDouble(rset3.getString(1))));
											
											DUE_DT_IGX.add(rset3.getString(9)==null?"":rset3.getString(9));
											Check_flag_IGX.add("");
											Authorize_flag_IGX.add("");
											Approve_flag_IGX.add(rset3.getString(10)==null?"":rset3.getString(10));
											st_dt=rset1.getString(10)==null?"":rset1.getString(10);
											end_dt=rset1.getString(19)==null?"":rset1.getString(19);
											duration_igx.add(st_dt+" - "+end_dt);
											
											queryString = "SELECT TRADER_NAME,TRADER_ABBR FROM FMS7_TRADER_MST WHERE FLAG='T' AND TRADER_CD="+rset1.getString(7)+"";
											//System.out.println("TRADER Master query = "+queryString);
											rset = stmt.executeQuery(queryString);
											if(rset.next())
											{			
												TRADER_NAME_IGX.add(rset.getString(1)==null?"":rset.getString(1));
												TRADER_ABBR_IGX.add(rset.getString(2)==null?"":rset.getString(2));
											}
											else
											{
												TRADER_NAME_IGX.add("");
												TRADER_ABBR_IGX.add("");
											}
											cnt++;
											split_flag=rset1.getString(20)==null?"":rset1.getString(20);
											if(split_flag.equals("Y")){
												String split_val="";
												queryString = "SELECT split_value from fms7_trader_plant_mst A where sn_no='"+rset1.getString(21)+"' "
														+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
														+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
														+ " customer_cd='"+rset1.getString(7)+"' AND FLAG='K' ";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset = stmt.executeQuery(queryString);
												if(rset.next())
												{
													split_val=rset.getString(1)==null?"":rset.getString(1);
												}else{
													queryString = "SELECT split_value from fms7_trader_OTHER_plant_mst A where sn_no='"+rset1.getString(21)+"' "
														+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_OTHER_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
														+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
														+ " customer_cd='"+rset1.getString(7)+"'  AND FLAG='K'";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset = stmt.executeQuery(queryString);
													if(rset.next())
													{
														split_val=rset.getString(1)==null?"":rset.getString(1);
													}
												}
												
												
												queryString = "SELECT MAN_CD,MAN_CONF_CD,CARGO_SEQ_NO,price FROM FMS7_MAN_CONFIRM_CARGO_DTL WHERE CARGO_REF_CD='"+temp_cargo_igx.elementAt(i)+"' ";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset = stmt.executeQuery(queryString);
												if(rset.next())
												{
													String cont_mapp_id="B-%-"+rset.getString(2)+"-0-"+rset.getString(1)+"-"+rset.getString(3);
													String partycd="";
													queryString = "SELECT TRD_CD FROM FMS7_MAN_REQ_MST WHERE MAN_CD='"+rset.getString(1)+"' ";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset4 = stmt4.executeQuery(queryString);
													if(rset4.next())
													{
														partycd=rset4.getString(1)==null?"":rset4.getString(1);
													}
													
													//queryString = "SELECT nvl(SUM(EXIT_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
													queryString = "SELECT nvl(SUM(ENTRY_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
															+ " to_date('"+frmdt+"','dd/mm/yyyy') and  to_date('"+todt+"','dd/mm/yyyy') and party_cd='"+partycd+"' ";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset4 = stmt4.executeQuery(queryString);
													if(rset4.next())
													{
														QTY_MSG_IGX.add(split_val+" % of "+rset4.getDouble(1));
													}else{
														QTY_MSG_IGX.add("");
													}
												}else{
													QTY_MSG_IGX.add("");
												}
											}else{
												QTY_MSG_IGX.add("");
											}
											
											if(!adj_amt.equals("") && (!adj_amt.equals("0"))){
												String title="";
												double ori_gross=rset3.getDouble(2);
												//System.out.println("ori_gross = "+ori_gross);
												String gross="";
												String gros="";
												if(inv_curr.equals("2")){
													gross=nf.format(rset3.getDouble(1));
												}else{
													gross=nf.format(ori_gross+Double.parseDouble(adj_amt));
												}
												if(adj_sign.equals("+")){
													title="Gross Amount ("+gross+") + Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
												}else if(adj_sign.equals("-")){
													title="Gross Amount ("+gross+") - Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
												}
												
												Vtitle.add(title);
											}else{
												Vtitle.add("");
											}
											//System.out.println("durat-42-end "+pg_drcr_no.size());
										}
										
									}
										//}
									}else{

										// System.out.println("durat-5-");
										 Vtitle.add("");
										 sg_drcr_no.add(rset1.getString(11)==null?"":rset1.getString(11));
										 CARGO_REF_CD_IGX.add(temp_cargo_igx.elementAt(i));
										 Vsupp_plant_Abbr_IGX.add(bu_unit);
										 CARGO_PRICE_IGX.add(temp_price_igx.elementAt(i));
										 //System.out.println("here "+temp_cargo_igx.elementAt(i));
											VESSEL_NM_IGX.add("");
											contract_no_igx.add(rset1.getString(21)==null?"":rset1.getString(21));
											contract_rev_no_igx.add(rset1.getString(22)==null?"":rset1.getString(22));
											plant_cd_igx.add(rset1.getString(23)==null?"":rset1.getString(23));
											TRD_CD_IGX.add(rset1.getString(7)==null?"0":rset1.getString(7));
											ACTUAL_UNLOADED_QTY_IGX.add(rset1.getString(1)==null?"-":nf3.format(Double.parseDouble(rset1.getString(1))));
											unloaded_qty_igx.add(rset1.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(1))));
											ACTUAL_UNLOADED_QTY_NUMERIC_IGX.add(rset1.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(1))));
											purchase_total_qty_igx += Double.parseDouble(rset1.getString(1)==null?"0":nf.format(Double.parseDouble(rset1.getString(1))));
											exchg_rate_2_igx=rset1.getString(2)==null?"0":rset1.getString(2);
											//System.out.println("here "+exchg_rate_2_igx+"--temp_price_igx.elementAt(i)--"+temp_price_igx.elementAt(i));
											if((exchg_rate_2_igx.equals("0") || exchg_rate_2_igx.equals("")) && (!temp_price_igx.elementAt(i).equals("1"))){
												TRADER_INV_CURRENCY_IGX.add("2");
											}else{
												TRADER_INV_CURRENCY_IGX.add("");
											}
											EXCHG_RATE_VALUE_IGX.add(rset1.getString(2)==null?"-":nf2.format(Double.parseDouble(rset1.getString(2))));
											INVOICE_AMT_IGX.add(rset1.getString(3)==null?"0":nf.format(Double.parseDouble(rset1.getString(3))));
											INVOICE_TAX_AMT_IGX.add(rset1.getString(4)==null?"0":nf.format(Double.parseDouble(rset1.getString(4))));
											String taxamt=rset1.getString(4)==null?"0":nf.format(Double.parseDouble(rset1.getString(4)));
											purchase_total_tax_inr_igx+=Double.parseDouble((taxamt));
											temp_inv_amt_igx.add(rset1.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(3))));
											double NETAMT=Double.parseDouble(nf.format(Double.parseDouble(rset1.getString(3))))+Double.parseDouble((taxamt));
											INVOICE_AMT_net_IGX.add(nf.format(NETAMT));
											ACT_ARRV_MONTH_IGX.add(rset1.getString(5)==null?"":rset1.getString(5));
											String inv_type=rset1.getString(6)==null?"":rset1.getString(6);
											if(tds_flag.equals("Y")){
												//double tds_Amt=(Double.parseDouble(rset1.getString(3)==null?"0":rset1.getString(3))* Double.parseDouble(tds_perc))/100;
												double tds_Amt=(Double.parseDouble(nf.format(NETAMT))* Double.parseDouble(tds_perc))/100;
												INVOICE_AMT_TDS_IGX.add(nf.format(tds_Amt));
											}else{
												INVOICE_AMT_TDS_IGX.add("-");
											}
											double trans_charge = (Double.parseDouble(rset1.getString(1)) * trans_perc);
											double total_trans_charge = ((trans_charge*cgst)/100) + ((trans_charge*sgst)/100);
											INVOICE_AMT_TRANS_IGX.add(trans_charge);
											INVOICE_TAXAMT_TRANS_IGX.add(total_trans_charge);
											if(inv_type.equals("1")){
												inv_type="Monthly";
											}else if(inv_type.equals("2")){
												inv_type="Fortnightly";
											}
											else if(inv_type.equals("3")){
												inv_type="Weekly";
											}
											ACT_ARRV_DT_IGX.add(rset1.getString(10)==null?"":rset1.getString(10));
											String rmk=rset1.getString(8)==null?"":rset1.getString(8);
											double tax_amt_usd=0;
											if(!rmk.equals("")){
												TAX_rmk_IGX.add(rmk+"%");
												String temp[]=rmk.split("@");
												 tax_amt_usd=(Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))* Double.parseDouble(temp[1].trim())/100;
												
											}else{
												TAX_rmk_IGX.add("");
											}
											TAX_amt_usd_IGX.add(nf.format(tax_amt_usd));
											invoice_type_IGX.add(inv_type);
											purchase_total_tax_inr_igx+=tax_amt_usd;
											usd_mmbtu=(tax_amt_usd+Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))/Double.parseDouble(rset1.getString(1));
											usdmmbtu.add(usd_mmbtu);
											INVOICE_NO_IGX.add(rset1.getString(11)==null?"":rset1.getString(11));
											INVOICE_DT_IGX.add(rset1.getString(12)==null?"":rset1.getString(12));	
											
											CONF_PRICE_IGX.add(rset1.getString(13)==null?"":nf2.format(Double.parseDouble(rset1.getString(13))));
											CONF_PRICE_NUMERIC_IGX.add(rset1.getString(13)==null?"0.0000":nf2.format(Double.parseDouble(rset1.getString(13))));
											if(temp_price_igx.elementAt(i).equals("1")){
												USD_VAL_INVOICE_IGX.add("-");
												USD_VAL_INVOICE_NUMERIC_IGX.add("0");
											}else{
											USD_VAL_INVOICE_IGX.add(rset1.getString(14)==null?"-":nf3.format(Double.parseDouble(rset1.getString(14))));
											
											USD_VAL_INVOICE_NUMERIC_IGX.add(rset1.getString(14)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(14))));
											purchase_total_invoice_value_igx += Double.parseDouble(rset1.getString(14)==null?"0":nf.format(Double.parseDouble(rset1.getString(14))));
											}
											
											DUE_DT_IGX.add(rset1.getString(15)==null?"":rset1.getString(15));
											Check_flag_IGX.add(rset1.getString(16)==null?"":rset1.getString(16));
											Authorize_flag_IGX.add(rset1.getString(17)==null?"":rset1.getString(17));
											Approve_flag_IGX.add(rset1.getString(18)==null?"":rset1.getString(18));
											String st_dt=rset1.getString(10)==null?"":rset1.getString(10);
											String end_dt=rset1.getString(19)==null?"":rset1.getString(19);
											duration_igx.add(st_dt+" - "+end_dt);
											
											queryString = "SELECT TRADER_NAME,TRADER_ABBR FROM FMS7_TRADER_MST WHERE FLAG='T' AND TRADER_CD="+rset1.getString(7)+"";
											//System.out.println("TRADER Master query = "+queryString);
											rset = stmt.executeQuery(queryString);
											if(rset.next())
											{			
												TRADER_NAME_IGX.add(rset.getString(1)==null?"":rset.getString(1));
												TRADER_ABBR_IGX.add(rset.getString(2)==null?"":rset.getString(2));
											}
											else
											{
												TRADER_NAME_IGX.add("");
												TRADER_ABBR_IGX.add("");
											}
											cnt++;
											String split_flag=rset1.getString(20)==null?"":rset1.getString(20);
											if(split_flag.equals("Y")){
												String split_val="";
												queryString = "SELECT split_value from fms7_trader_plant_mst A where sn_no='"+rset1.getString(21)+"' "
														+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
														+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
														+ " customer_cd='"+rset1.getString(7)+"' AND FLAG='K'";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset = stmt.executeQuery(queryString);
												if(rset.next())
												{
													split_val=rset.getString(1)==null?"":rset.getString(1);
												}else{
													queryString = "SELECT split_value from fms7_trader_OTHER_plant_mst A where sn_no='"+rset1.getString(21)+"' "
														+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_OTHER_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
														+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
														+ " customer_cd='"+rset1.getString(7)+"'  AND FLAG='K'";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset = stmt.executeQuery(queryString);
													if(rset.next())
													{
														split_val=rset.getString(1)==null?"":rset.getString(1);
													}
												}
												
												
												queryString = "SELECT MAN_CD,MAN_CONF_CD,CARGO_SEQ_NO,price FROM FMS7_MAN_CONFIRM_CARGO_DTL WHERE CARGO_REF_CD='"+temp_cargo_igx.elementAt(i)+"' ";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset = stmt.executeQuery(queryString);
												if(rset.next())
												{
													String cont_mapp_id="B-%-"+rset.getString(2)+"-0-"+rset.getString(1)+"-"+rset.getString(3);
													String partycd="";
													queryString = "SELECT TRD_CD FROM FMS7_MAN_REQ_MST WHERE MAN_CD='"+rset.getString(1)+"' ";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset2 = stmt2.executeQuery(queryString);
													if(rset2.next())
													{
														partycd=rset2.getString(1)==null?"":rset2.getString(1);
													}
													
													//queryString = "SELECT nvl(SUM(EXIT_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
													queryString = "SELECT nvl(SUM(ENTRY_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
															+ " to_date('"+frmdt+"','dd/mm/yyyy') and  to_date('"+todt+"','dd/mm/yyyy') and party_cd='"+partycd+"' ";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset2 = stmt2.executeQuery(queryString);
													if(rset2.next())
													{
														QTY_MSG_IGX.add(split_val+" % of "+rset2.getDouble(1));
													}else{
														QTY_MSG_IGX.add("");
													}
												}else{
													QTY_MSG_IGX.add("");
												}
											}else{
												QTY_MSG_IGX.add("");
											}
											
											//FOR DEBIT CREDIT
											//System.out.println("durat-51-start "+sg_drcr_no.size());
											for(int kk=0;kk<sg_drcr_no.size();kk++){
												queryString="SELECT dr_cr_no FROM FMS7_DOM_PUR_INV_DRCR_DTL WHERE party_Cd='"+rset1.getString(7)+"'"
														+ " and cargo_ref_no='"+temp_cargo_igx.elementAt(i)+"' and contract_no='"+cont_no+"'"
														+ " and plant_cd='"+platcd+"' "
														+ " and buyer_plant_cd='"+rset1.getString(24)+"' and invoice_no='"+sg_drcr_no.elementAt(kk)+"' ";
								//				System.out.println("queryString---"+queryString);
												rset3 = stmt3.executeQuery(queryString);
												while(rset3.next())
												{
													if(!sg_drcr_no.contains(rset3.getString(1)))
													sg_drcr_no.add(rset3.getString(1)==null?"":rset3.getString(1));
												}
											}
											//System.out.println("durat-51-end "+sg_drcr_no.size());
											for(int kk=0;kk<sg_drcr_no.size();kk++){
												queryString = "SELECT DR_CR_AMT_USD,DR_CR_AMT_INR,DR_CR_QTY,DR_CR_PRICE,DR_CR_NO,DR_CR_EXCHG_RT_VAL,DR_CR_TAX_AMT "
														+ ",TO_CHAR(DR_CR_DT,'DD/MM/YYYY'),TO_CHAR(DR_CR_due_DT,'DD/MM/YYYY'),approved_flag,adj_sign,adj_amt FROM FMS7_DOM_PUR_INV_DRCR_DTL "
														+ " WHERE CARGO_REF_NO='"+temp_cargo_igx.elementAt(i)+"' and party_Cd ='"+rset1.getString(7)+"' "
														+ "and contract_no='"+cont_no+"' "
														+ "and plant_cd='"+platcd+"' and dr_cr_no='"+sg_drcr_no.elementAt(kk)+"' and buyer_plant_cd='"+rset1.getString(24)+"'";
												//System.out.println("Confirmed Credit Debit Query= "+queryString);
												rset3 = stmt3.executeQuery(queryString);
												if(rset3.next())
												{
												//Vtitle.add("");
													//System.out.println("durat-51]2-start "+sg_drcr_no.size());
												String adj_amt=rset3.getString(12)==null?"":rset3.getString(12);
												String adj_sign=rset3.getString(11)==null?"":rset3.getString(11);
												CARGO_REF_CD_IGX.add(temp_cargo_igx.elementAt(i));
												Vsupp_plant_Abbr_IGX.add(bu_unit);
												CARGO_PRICE_IGX.add(temp_price_igx.elementAt(i));
												VESSEL_NM_IGX.add("");
												contract_no_igx.add("");
												contract_rev_no_igx.add("");
												plant_cd_igx.add("");
												TRD_CD_IGX.add(rset1.getString(7)==null?"0":rset1.getString(7));
												ACTUAL_UNLOADED_QTY_IGX.add(rset3.getString(3)==null?"-":nf3.format(Double.parseDouble(rset3.getString(3))));
												unloaded_qty_igx.add(rset3.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(3))));
												ACTUAL_UNLOADED_QTY_NUMERIC_IGX.add(rset3.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(3))));
												purchase_total_qty_igx += Double.parseDouble(rset3.getString(3)==null?"0":nf.format(Double.parseDouble(rset3.getString(3))));
												exchg_rate_2_igx=rset3.getString(6)==null?"0":rset3.getString(6);
												String inv_curr="";
												if((exchg_rate_2_igx.equals("0") || exchg_rate_2_igx.equals("")) && (!temp_price_igx.elementAt(i).equals("1"))){
													TRADER_INV_CURRENCY_IGX.add("2");
													inv_curr="2";
												}else{
													TRADER_INV_CURRENCY_IGX.add("");
												}
												EXCHG_RATE_VALUE_IGX.add(rset3.getString(6)==null?"-":nf2.format(Double.parseDouble(rset3.getString(6))));
												INVOICE_AMT_IGX.add(rset3.getString(2)==null?"0":nf.format(Double.parseDouble(rset3.getString(2))));
												INVOICE_TAX_AMT_IGX.add(rset3.getString(7)==null?"0":nf.format(Double.parseDouble(rset3.getString(7))));
												taxamt=rset3.getString(7)==null?"0":nf.format(Double.parseDouble(rset3.getString(7)));
												purchase_total_tax_inr_igx+=Double.parseDouble((taxamt));
												//temp_inv_amt_igx.add(rset3.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(1))));
												if(inv_curr.equals("2")){
													temp_inv_amt_igx.add("0");
												}else{
													temp_inv_amt_igx.add(rset3.getString(2)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(2))));
												}
												NETAMT=Double.parseDouble(nf.format(Double.parseDouble(rset3.getString(2))))+Double.parseDouble((taxamt));
												INVOICE_AMT_net_IGX.add(nf.format(NETAMT));
												ACT_ARRV_MONTH_IGX.add(rset1.getString(5)==null?"":rset1.getString(5));
												inv_type=rset1.getString(6)==null?"":rset1.getString(6);
												if(tds_flag.equals("Y")){
													if(inv_curr.equals("2")){
														INVOICE_AMT_TDS_IGX.add("-");
													}else{
														//double tds_Amt=((Double.parseDouble(rset3.getString(2)==null?"0":rset3.getString(2)))* Double.parseDouble(tds_perc))/100;
														double tds_Amt=((Double.parseDouble(nf.format(NETAMT)))* Double.parseDouble(tds_perc))/100;
														INVOICE_AMT_TDS_IGX.add(nf.format(tds_Amt));
													}
												}else{
													INVOICE_AMT_TDS_IGX.add("-");
												}
												trans_charge = (Double.parseDouble(rset3.getString(3)) * trans_perc);
												total_trans_charge = ((trans_charge*cgst)/100) + ((trans_charge*sgst)/100);
												INVOICE_AMT_TRANS_IGX.add(trans_charge);
												INVOICE_TAXAMT_TRANS_IGX.add(total_trans_charge);
												if(inv_type.equals("1")){
													inv_type="Monthly";
												}else if(inv_type.equals("2")){
													inv_type="Fortnightly";
												}
												else if(inv_type.equals("3")){
													inv_type="Weekly";
												}
												ACT_ARRV_DT_IGX.add(rset1.getString(10)==null?"":rset1.getString(10));
												rmk=rset1.getString(8)==null?"":rset1.getString(8);
												String usd_amt=rset3.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(1)));
												double amtadj=0;
												if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_curr.equals("2")){
													if(adj_sign.equals("+")){
														 amtadj=Double.parseDouble(usd_amt)+Double.parseDouble(adj_amt);
													}else if(adj_sign.equals("-")){
														amtadj=Double.parseDouble(usd_amt)-Double.parseDouble(adj_amt);
													}
													if(temp_price_igx.elementAt(i).equals("1")){
														USD_VAL_INVOICE_IGX.add("-");
														USD_VAL_INVOICE_NUMERIC_IGX.add("0");
													}else{
													USD_VAL_INVOICE_IGX.add(nf3.format(amtadj));
													
													USD_VAL_INVOICE_NUMERIC_IGX.add(nf.format(amtadj));
													purchase_total_invoice_value_igx += Double.parseDouble(nf.format(amtadj));
													}
												}else{
													if(temp_price_igx.elementAt(i).equals("1")){
														USD_VAL_INVOICE_IGX.add("-");
														USD_VAL_INVOICE_NUMERIC_IGX.add("0");
													}else{
												USD_VAL_INVOICE_IGX.add(rset3.getString(1)==null?"-":nf3.format(Double.parseDouble(rset3.getString(1))));
												USD_VAL_INVOICE_NUMERIC_IGX.add(rset3.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(1))));
												purchase_total_invoice_value_igx += Double.parseDouble(rset3.getString(1)==null?"0":nf.format(Double.parseDouble(rset3.getString(1))));
													}
												}
												tax_amt_usd=0;
												if(!rmk.equals("")){
													TAX_rmk_IGX.add(rmk+"%");
													String temp[]=rmk.split("@");
													if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_curr.equals("2")){
														tax_amt_usd=(amtadj)* Double.parseDouble(temp[1].trim())/100;
													}else{
													 tax_amt_usd=(Double.parseDouble(rset3.getString(1)==null?"0":rset3.getString(1)))* Double.parseDouble(temp[1].trim())/100;
													}
													
												}
												TAX_amt_usd_IGX.add(nf.format(tax_amt_usd));
												invoice_type_IGX.add(inv_type);
												purchase_total_tax_inr_igx+=tax_amt_usd;
												usd_mmbtu=(tax_amt_usd+Double.parseDouble(rset3.getString(1)==null?"0":rset3.getString(1)))/Double.parseDouble(rset3.getString(3));
												usdmmbtu.add(usd_mmbtu);
												INVOICE_NO_IGX.add(rset3.getString(5)==null?"":rset3.getString(5));
												INVOICE_DT_IGX.add(rset3.getString(8)==null?"":rset3.getString(8));	
												CONF_PRICE_IGX.add(rset3.getString(4)==null?"":nf2.format(Double.parseDouble(rset3.getString(4))));
												CONF_PRICE_NUMERIC_IGX.add(rset3.getString(4)==null?"0.0000":nf2.format(Double.parseDouble(rset3.getString(4))));
												
												DUE_DT_IGX.add(rset3.getString(9)==null?"":rset3.getString(9));
												Check_flag_IGX.add("");
												Authorize_flag_IGX.add("");
												Approve_flag_IGX.add(rset3.getString(10)==null?"":rset3.getString(10));
												st_dt=rset1.getString(10)==null?"":rset1.getString(10);
												end_dt=rset1.getString(19)==null?"":rset1.getString(19);
												duration_igx.add(st_dt+" - "+end_dt);
												queryString = "SELECT TRADER_NAME,TRADER_ABBR FROM FMS7_TRADER_MST WHERE FLAG='T' AND TRADER_CD="+rset1.getString(7)+"";
												//System.out.println("TRADER Master query = "+queryString);
												rset = stmt.executeQuery(queryString);
												if(rset.next())
												{			
													TRADER_NAME_IGX.add(rset.getString(1)==null?"":rset.getString(1));
													TRADER_ABBR_IGX.add(rset.getString(2)==null?"":rset.getString(2));
												}
												else
												{
													TRADER_NAME_IGX.add("");
													TRADER_ABBR_IGX.add("");
												}
												cnt++;
												split_flag=rset1.getString(20)==null?"":rset1.getString(20);
												if(split_flag.equals("Y")){
													String split_val="";
													queryString = "SELECT split_value from fms7_trader_plant_mst A where sn_no='"+rset1.getString(21)+"' "
															+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
															+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
															+ " customer_cd='"+rset1.getString(7)+"' AND FLAG='K'";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset = stmt.executeQuery(queryString);
													if(rset.next())
													{
														split_val=rset.getString(1)==null?"":rset.getString(1);
													}else{
														queryString = "SELECT split_value from fms7_trader_OTHER_plant_mst A where sn_no='"+rset1.getString(21)+"' "
															+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_OTHER_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
															+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
															+ " customer_cd='"+rset1.getString(7)+"'  AND FLAG='K'";
														//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
														rset = stmt.executeQuery(queryString);
														if(rset.next())
														{
															split_val=rset.getString(1)==null?"":rset.getString(1);
														}
													}
													
													
													queryString = "SELECT MAN_CD,MAN_CONF_CD,CARGO_SEQ_NO,price FROM FMS7_MAN_CONFIRM_CARGO_DTL WHERE CARGO_REF_CD='"+temp_cargo_igx.elementAt(i)+"' ";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset = stmt.executeQuery(queryString);
													if(rset.next())
													{
														String cont_mapp_id="B-%-"+rset.getString(2)+"-0-"+rset.getString(1)+"-"+rset.getString(3);
														String partycd="";
														queryString = "SELECT TRD_CD FROM FMS7_MAN_REQ_MST WHERE MAN_CD='"+rset.getString(1)+"' ";
														//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
														rset4 = stmt4.executeQuery(queryString);
														if(rset4.next())
														{
															partycd=rset4.getString(1)==null?"":rset4.getString(1);
														}
														
														//queryString = "SELECT nvl(SUM(EXIT_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
														queryString = "SELECT nvl(SUM(ENTRY_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
																+ " to_date('"+frmdt+"','dd/mm/yyyy') and  to_date('"+todt+"','dd/mm/yyyy') and party_cd='"+partycd+"' ";
														//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
														rset4 = stmt4.executeQuery(queryString);
														if(rset4.next())
														{
															QTY_MSG_IGX.add(split_val+" % of "+rset4.getDouble(1));
														}else{
															QTY_MSG_IGX.add("");
														}
													}else{
														QTY_MSG_IGX.add("");
													}
												}else{
													QTY_MSG_IGX.add("");
												}
												if(!adj_amt.equals("") && (!adj_amt.equals("0"))){
													String title="";
													double ori_gross=rset3.getDouble(2);
													//System.out.println("ori_gross = "+ori_gross);
													String gross="";
													String gros="";
													if(inv_curr.equals("2")){
														gross=nf.format(rset3.getDouble(1));
													}else{
														gross=nf.format(ori_gross+Double.parseDouble(adj_amt));
													}
													if(adj_sign.equals("+")){
														title="Gross Amount ("+gross+") + Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
													}else if(adj_sign.equals("-")){
														title="Gross Amount ("+gross+") - Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
													}
													
													Vtitle.add(title);
												}else{
													Vtitle.add("");
												}
											//	System.out.println("durat-51]2-end "+sg_drcr_no.size());
											}
									 }
									 
									}
								 }else{
									// System.out.println("durat-5-");
									 Vtitle.add("");
									 sg_drcr_no.add(rset1.getString(11)==null?"":rset1.getString(11));
									 CARGO_REF_CD_IGX.add(temp_cargo_igx.elementAt(i));
									 Vsupp_plant_Abbr_IGX.add(bu_unit);
									 CARGO_PRICE_IGX.add(temp_price_igx.elementAt(i));
									 //System.out.println("here "+temp_cargo_igx.elementAt(i));
										VESSEL_NM_IGX.add("");
										contract_no_igx.add(rset1.getString(21)==null?"":rset1.getString(21));
										contract_rev_no_igx.add(rset1.getString(22)==null?"":rset1.getString(22));
										plant_cd_igx.add(rset1.getString(23)==null?"":rset1.getString(23));
										TRD_CD_IGX.add(rset1.getString(7)==null?"0":rset1.getString(7));
										ACTUAL_UNLOADED_QTY_IGX.add(rset1.getString(1)==null?"-":nf3.format(Double.parseDouble(rset1.getString(1))));
										unloaded_qty_igx.add(rset1.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(1))));
										ACTUAL_UNLOADED_QTY_NUMERIC_IGX.add(rset1.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(1))));
										purchase_total_qty_igx += Double.parseDouble(rset1.getString(1)==null?"0":nf.format(Double.parseDouble(rset1.getString(1))));
										exchg_rate_2_igx=rset1.getString(2)==null?"0":rset1.getString(2);
										//System.out.println("here "+exchg_rate_2_igx+"--temp_price_igx.elementAt(i)--"+temp_price_igx.elementAt(i));
										if((exchg_rate_2_igx.equals("0") || exchg_rate_2_igx.equals("")) && (!temp_price_igx.elementAt(i).equals("1"))){
											TRADER_INV_CURRENCY_IGX.add("2");
										}else{
											TRADER_INV_CURRENCY_IGX.add("");
										}
										EXCHG_RATE_VALUE_IGX.add(rset1.getString(2)==null?"-":nf2.format(Double.parseDouble(rset1.getString(2))));
										INVOICE_AMT_IGX.add(rset1.getString(3)==null?"0":nf.format(Double.parseDouble(rset1.getString(3))));
										INVOICE_TAX_AMT_IGX.add(rset1.getString(4)==null?"0":nf.format(Double.parseDouble(rset1.getString(4))));
										String taxamt=rset1.getString(4)==null?"0":nf.format(Double.parseDouble(rset1.getString(4)));
										purchase_total_tax_inr_igx+=Double.parseDouble((taxamt));
										temp_inv_amt_igx.add(rset1.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(3))));
										double NETAMT=Double.parseDouble(nf.format(Double.parseDouble(rset1.getString(3))))+Double.parseDouble((taxamt));
										INVOICE_AMT_net_IGX.add(nf.format(NETAMT));
										ACT_ARRV_MONTH_IGX.add(rset1.getString(5)==null?"":rset1.getString(5));
										String inv_type=rset1.getString(6)==null?"":rset1.getString(6);
										if(tds_flag.equals("Y")){
											//double tds_Amt=(Double.parseDouble(rset1.getString(3)==null?"0":rset1.getString(3))* Double.parseDouble(tds_perc))/100;
											double tds_Amt=(Double.parseDouble(nf.format(NETAMT))* Double.parseDouble(tds_perc))/100;
											INVOICE_AMT_TDS_IGX.add(nf.format(tds_Amt));
										}else{
											INVOICE_AMT_TDS_IGX.add("-");
										}
										double trans_charge = (Double.parseDouble(rset1.getString(1)) * trans_perc);
										double total_trans_charge = ((trans_charge*cgst)/100) + ((trans_charge*sgst)/100);
										INVOICE_AMT_TRANS_IGX.add(trans_charge);
										INVOICE_TAXAMT_TRANS_IGX.add(total_trans_charge);
										if(inv_type.equals("1")){
											inv_type="Monthly";
										}else if(inv_type.equals("2")){
											inv_type="Fortnightly";
										}
										else if(inv_type.equals("3")){
											inv_type="Weekly";
										}
										ACT_ARRV_DT_IGX.add(rset1.getString(10)==null?"":rset1.getString(10));
										String rmk=rset1.getString(8)==null?"":rset1.getString(8);
										double tax_amt_usd=0;
										if(!rmk.equals("")){
											TAX_rmk_IGX.add(rmk+"%");
											String temp[]=rmk.split("@");
											 tax_amt_usd=(Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))* Double.parseDouble(temp[1].trim())/100;
											
										}else{
											TAX_rmk_IGX.add("");
										}
										TAX_amt_usd_IGX.add(nf.format(tax_amt_usd));
										invoice_type_IGX.add(inv_type);
										purchase_total_tax_inr_igx+=tax_amt_usd;
										usd_mmbtu=(tax_amt_usd+Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))/Double.parseDouble(rset1.getString(1));
										usdmmbtu.add(usd_mmbtu);
										INVOICE_NO_IGX.add(rset1.getString(11)==null?"":rset1.getString(11));
										INVOICE_DT_IGX.add(rset1.getString(12)==null?"":rset1.getString(12));	
										
										CONF_PRICE_IGX.add(rset1.getString(13)==null?"":nf2.format(Double.parseDouble(rset1.getString(13))));
										CONF_PRICE_NUMERIC_IGX.add(rset1.getString(13)==null?"0.0000":nf2.format(Double.parseDouble(rset1.getString(13))));
										if(temp_price_igx.elementAt(i).equals("1")){
											USD_VAL_INVOICE_IGX.add("-");
											USD_VAL_INVOICE_NUMERIC_IGX.add("0");
										}else{
										USD_VAL_INVOICE_IGX.add(rset1.getString(14)==null?"-":nf3.format(Double.parseDouble(rset1.getString(14))));
										
										USD_VAL_INVOICE_NUMERIC_IGX.add(rset1.getString(14)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(14))));
										purchase_total_invoice_value_igx += Double.parseDouble(rset1.getString(14)==null?"0":nf.format(Double.parseDouble(rset1.getString(14))));
										}
										
										DUE_DT_IGX.add(rset1.getString(15)==null?"":rset1.getString(15));
										Check_flag_IGX.add(rset1.getString(16)==null?"":rset1.getString(16));
										Authorize_flag_IGX.add(rset1.getString(17)==null?"":rset1.getString(17));
										Approve_flag_IGX.add(rset1.getString(18)==null?"":rset1.getString(18));
										String st_dt=rset1.getString(10)==null?"":rset1.getString(10);
										String end_dt=rset1.getString(19)==null?"":rset1.getString(19);
										duration_igx.add(st_dt+" - "+end_dt);
										
										queryString = "SELECT TRADER_NAME,TRADER_ABBR FROM FMS7_TRADER_MST WHERE FLAG='T' AND TRADER_CD="+rset1.getString(7)+"";
										//System.out.println("TRADER Master query = "+queryString);
										rset = stmt.executeQuery(queryString);
										if(rset.next())
										{			
											TRADER_NAME_IGX.add(rset.getString(1)==null?"":rset.getString(1));
											TRADER_ABBR_IGX.add(rset.getString(2)==null?"":rset.getString(2));
										}
										else
										{
											TRADER_NAME_IGX.add("");
											TRADER_ABBR_IGX.add("");
										}
										cnt++;
										String split_flag=rset1.getString(20)==null?"":rset1.getString(20);
										if(split_flag.equals("Y")){
											String split_val="";
											queryString = "SELECT split_value from fms7_trader_plant_mst A where sn_no='"+rset1.getString(21)+"' "
													+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
													+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
													+ " customer_cd='"+rset1.getString(7)+"' AND FLAG='K'";
											//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
											rset = stmt.executeQuery(queryString);
											if(rset.next())
											{
												split_val=rset.getString(1)==null?"":rset.getString(1);
											}else{
												queryString = "SELECT split_value from fms7_trader_OTHER_plant_mst A where sn_no='"+rset1.getString(21)+"' "
													+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_OTHER_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
													+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
													+ " customer_cd='"+rset1.getString(7)+"'  AND FLAG='K'";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset = stmt.executeQuery(queryString);
												if(rset.next())
												{
													split_val=rset.getString(1)==null?"":rset.getString(1);
												}
											}
											
											
											queryString = "SELECT MAN_CD,MAN_CONF_CD,CARGO_SEQ_NO,price FROM FMS7_MAN_CONFIRM_CARGO_DTL WHERE CARGO_REF_CD='"+temp_cargo_igx.elementAt(i)+"' ";
											//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
											rset = stmt.executeQuery(queryString);
											if(rset.next())
											{
												String cont_mapp_id="B-%-"+rset.getString(2)+"-0-"+rset.getString(1)+"-"+rset.getString(3);
												String partycd="";
												queryString = "SELECT TRD_CD FROM FMS7_MAN_REQ_MST WHERE MAN_CD='"+rset.getString(1)+"' ";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset2 = stmt2.executeQuery(queryString);
												if(rset2.next())
												{
													partycd=rset2.getString(1)==null?"":rset2.getString(1);
												}
												
												//queryString = "SELECT nvl(SUM(EXIT_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
												queryString = "SELECT nvl(SUM(ENTRY_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
														+ " to_date('"+frmdt+"','dd/mm/yyyy') and  to_date('"+todt+"','dd/mm/yyyy') and party_cd='"+partycd+"' ";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset2 = stmt2.executeQuery(queryString);
												if(rset2.next())
												{
													QTY_MSG_IGX.add(split_val+" % of "+rset2.getDouble(1));
												}else{
													QTY_MSG_IGX.add("");
												}
											}else{
												QTY_MSG_IGX.add("");
											}
										}else{
											QTY_MSG_IGX.add("");
										}
										
										//FOR DEBIT CREDIT
										//System.out.println("durat-51-start "+sg_drcr_no.size());
										for(int kk=0;kk<sg_drcr_no.size();kk++){
											queryString="SELECT dr_cr_no FROM FMS7_DOM_PUR_INV_DRCR_DTL WHERE party_Cd='"+rset1.getString(7)+"'"
													+ " and cargo_ref_no='"+temp_cargo_igx.elementAt(i)+"' and contract_no='"+cont_no+"'"
													+ " and plant_cd='"+platcd+"' "
													+ " and buyer_plant_cd='"+rset1.getString(24)+"' and invoice_no='"+sg_drcr_no.elementAt(kk)+"' ";
							//				System.out.println("queryString---"+queryString);
											rset3 = stmt3.executeQuery(queryString);
											while(rset3.next())
											{
												if(!sg_drcr_no.contains(rset3.getString(1)))
												sg_drcr_no.add(rset3.getString(1)==null?"":rset3.getString(1));
											}
										}
										//System.out.println("durat-51-end "+sg_drcr_no.size());
										for(int kk=0;kk<sg_drcr_no.size();kk++){
											queryString = "SELECT DR_CR_AMT_USD,DR_CR_AMT_INR,DR_CR_QTY,DR_CR_PRICE,DR_CR_NO,DR_CR_EXCHG_RT_VAL,DR_CR_TAX_AMT "
													+ ",TO_CHAR(DR_CR_DT,'DD/MM/YYYY'),TO_CHAR(DR_CR_due_DT,'DD/MM/YYYY'),approved_flag,adj_sign,adj_amt FROM FMS7_DOM_PUR_INV_DRCR_DTL "
													+ " WHERE CARGO_REF_NO='"+temp_cargo_igx.elementAt(i)+"' and party_Cd ='"+rset1.getString(7)+"' "
													+ "and contract_no='"+cont_no+"' "
													+ "and plant_cd='"+platcd+"' and dr_cr_no='"+sg_drcr_no.elementAt(kk)+"' and buyer_plant_cd='"+rset1.getString(24)+"'";
											//System.out.println("Confirmed Credit Debit Query= "+queryString);
											rset3 = stmt3.executeQuery(queryString);
											if(rset3.next())
											{
											//Vtitle.add("");
												//System.out.println("durat-51]2-start "+sg_drcr_no.size());
											String adj_amt=rset3.getString(12)==null?"":rset3.getString(12);
											String adj_sign=rset3.getString(11)==null?"":rset3.getString(11);
											CARGO_REF_CD_IGX.add(temp_cargo_igx.elementAt(i));
											Vsupp_plant_Abbr_IGX.add(bu_unit);
											CARGO_PRICE_IGX.add(temp_price_igx.elementAt(i));
											VESSEL_NM_IGX.add("");
											contract_no_igx.add("");
											contract_rev_no_igx.add("");
											plant_cd_igx.add("");
											TRD_CD_IGX.add(rset1.getString(7)==null?"0":rset1.getString(7));
											ACTUAL_UNLOADED_QTY_IGX.add(rset3.getString(3)==null?"-":nf3.format(Double.parseDouble(rset3.getString(3))));
											unloaded_qty_igx.add(rset3.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(3))));
											ACTUAL_UNLOADED_QTY_NUMERIC_IGX.add(rset3.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(3))));
											purchase_total_qty_igx += Double.parseDouble(rset3.getString(3)==null?"0":nf.format(Double.parseDouble(rset3.getString(3))));
											exchg_rate_2_igx=rset3.getString(6)==null?"0":rset3.getString(6);
											String inv_curr="";
											if((exchg_rate_2_igx.equals("0") || exchg_rate_2_igx.equals("")) && (!temp_price_igx.elementAt(i).equals("1"))){
												TRADER_INV_CURRENCY_IGX.add("2");
												inv_curr="2";
											}else{
												TRADER_INV_CURRENCY_IGX.add("");
											}
											EXCHG_RATE_VALUE_IGX.add(rset3.getString(6)==null?"-":nf2.format(Double.parseDouble(rset3.getString(6))));
											INVOICE_AMT_IGX.add(rset3.getString(2)==null?"0":nf.format(Double.parseDouble(rset3.getString(2))));
											INVOICE_TAX_AMT_IGX.add(rset3.getString(7)==null?"0":nf.format(Double.parseDouble(rset3.getString(7))));
											taxamt=rset3.getString(7)==null?"0":nf.format(Double.parseDouble(rset3.getString(7)));
											purchase_total_tax_inr_igx+=Double.parseDouble((taxamt));
											//temp_inv_amt_igx.add(rset3.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(1))));
											if(inv_curr.equals("2")){
												temp_inv_amt_igx.add("0");
											}else{
												temp_inv_amt_igx.add(rset3.getString(2)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(2))));
											}
											NETAMT=Double.parseDouble(nf.format(Double.parseDouble(rset3.getString(2))))+Double.parseDouble((taxamt));
											INVOICE_AMT_net_IGX.add(nf.format(NETAMT));
											ACT_ARRV_MONTH_IGX.add(rset1.getString(5)==null?"":rset1.getString(5));
											inv_type=rset1.getString(6)==null?"":rset1.getString(6);
											if(tds_flag.equals("Y")){
												if(inv_curr.equals("2")){
													INVOICE_AMT_TDS_IGX.add("-");
												}else{
													//double tds_Amt=((Double.parseDouble(rset3.getString(2)==null?"0":rset3.getString(2)))* Double.parseDouble(tds_perc))/100;
													double tds_Amt=((Double.parseDouble(nf.format(NETAMT)))* Double.parseDouble(tds_perc))/100;
													INVOICE_AMT_TDS_IGX.add(nf.format(tds_Amt));
												}
											}else{
												INVOICE_AMT_TDS_IGX.add("-");
											}
											trans_charge = (Double.parseDouble(rset3.getString(3)) * trans_perc);
											total_trans_charge = ((trans_charge*cgst)/100) + ((trans_charge*sgst)/100);
											INVOICE_AMT_TRANS_IGX.add(trans_charge);
											INVOICE_TAXAMT_TRANS_IGX.add(total_trans_charge);
											if(inv_type.equals("1")){
												inv_type="Monthly";
											}else if(inv_type.equals("2")){
												inv_type="Fortnightly";
											}
											else if(inv_type.equals("3")){
												inv_type="Weekly";
											}
											ACT_ARRV_DT_IGX.add(rset1.getString(10)==null?"":rset1.getString(10));
											rmk=rset1.getString(8)==null?"":rset1.getString(8);
											String usd_amt=rset3.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(1)));
											double amtadj=0;
											if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_curr.equals("2")){
												if(adj_sign.equals("+")){
													 amtadj=Double.parseDouble(usd_amt)+Double.parseDouble(adj_amt);
												}else if(adj_sign.equals("-")){
													amtadj=Double.parseDouble(usd_amt)-Double.parseDouble(adj_amt);
												}
												if(temp_price_igx.elementAt(i).equals("1")){
													USD_VAL_INVOICE_IGX.add("-");
													USD_VAL_INVOICE_NUMERIC_IGX.add("0");
												}else{
												USD_VAL_INVOICE_IGX.add(nf3.format(amtadj));
												
												USD_VAL_INVOICE_NUMERIC_IGX.add(nf.format(amtadj));
												purchase_total_invoice_value_igx += Double.parseDouble(nf.format(amtadj));
												}
											}else{
												if(temp_price_igx.elementAt(i).equals("1")){
													USD_VAL_INVOICE_IGX.add("-");
													USD_VAL_INVOICE_NUMERIC_IGX.add("0");
												}else{
											USD_VAL_INVOICE_IGX.add(rset3.getString(1)==null?"-":nf3.format(Double.parseDouble(rset3.getString(1))));
											USD_VAL_INVOICE_NUMERIC_IGX.add(rset3.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset3.getString(1))));
											purchase_total_invoice_value_igx += Double.parseDouble(rset3.getString(1)==null?"0":nf.format(Double.parseDouble(rset3.getString(1))));
												}
											}
											tax_amt_usd=0;
											if(!rmk.equals("")){
												TAX_rmk_IGX.add(rmk+"%");
												String temp[]=rmk.split("@");
												if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_curr.equals("2")){
													tax_amt_usd=(amtadj)* Double.parseDouble(temp[1].trim())/100;
												}else{
												 tax_amt_usd=(Double.parseDouble(rset3.getString(1)==null?"0":rset3.getString(1)))* Double.parseDouble(temp[1].trim())/100;
												}
												
											}
											TAX_amt_usd_IGX.add(nf.format(tax_amt_usd));
											invoice_type_IGX.add(inv_type);
											purchase_total_tax_inr_igx+=tax_amt_usd;
											usd_mmbtu=(tax_amt_usd+Double.parseDouble(rset3.getString(1)==null?"0":rset3.getString(1)))/Double.parseDouble(rset3.getString(3));
											usdmmbtu.add(usd_mmbtu);
											INVOICE_NO_IGX.add(rset3.getString(5)==null?"":rset3.getString(5));
											INVOICE_DT_IGX.add(rset3.getString(8)==null?"":rset3.getString(8));	
											CONF_PRICE_IGX.add(rset3.getString(4)==null?"":nf2.format(Double.parseDouble(rset3.getString(4))));
											CONF_PRICE_NUMERIC_IGX.add(rset3.getString(4)==null?"0.0000":nf2.format(Double.parseDouble(rset3.getString(4))));
											
											DUE_DT_IGX.add(rset3.getString(9)==null?"":rset3.getString(9));
											Check_flag_IGX.add("");
											Authorize_flag_IGX.add("");
											Approve_flag_IGX.add(rset3.getString(10)==null?"":rset3.getString(10));
											st_dt=rset1.getString(10)==null?"":rset1.getString(10);
											end_dt=rset1.getString(19)==null?"":rset1.getString(19);
											duration_igx.add(st_dt+" - "+end_dt);
											queryString = "SELECT TRADER_NAME,TRADER_ABBR FROM FMS7_TRADER_MST WHERE FLAG='T' AND TRADER_CD="+rset1.getString(7)+"";
											//System.out.println("TRADER Master query = "+queryString);
											rset = stmt.executeQuery(queryString);
											if(rset.next())
											{			
												TRADER_NAME_IGX.add(rset.getString(1)==null?"":rset.getString(1));
												TRADER_ABBR_IGX.add(rset.getString(2)==null?"":rset.getString(2));
											}
											else
											{
												TRADER_NAME_IGX.add("");
												TRADER_ABBR_IGX.add("");
											}
											cnt++;
											split_flag=rset1.getString(20)==null?"":rset1.getString(20);
											if(split_flag.equals("Y")){
												String split_val="";
												queryString = "SELECT split_value from fms7_trader_plant_mst A where sn_no='"+rset1.getString(21)+"' "
														+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
														+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
														+ " customer_cd='"+rset1.getString(7)+"' AND FLAG='K'";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset = stmt.executeQuery(queryString);
												if(rset.next())
												{
													split_val=rset.getString(1)==null?"":rset.getString(1);
												}else{
													queryString = "SELECT split_value from fms7_trader_OTHER_plant_mst A where sn_no='"+rset1.getString(21)+"' "
														+ " and sn_rev_no=(select max(sn_rev_no) from fms7_trader_OTHER_plant_mst B where A.sn_no=B.SN_NO AND A.SN_REV_NO=B.SN_REV_NO"
														+ " AND A.CUSTOMER_CD=B.CUSTOMER_CD ) and "
														+ " customer_cd='"+rset1.getString(7)+"'  AND FLAG='K'";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset = stmt.executeQuery(queryString);
													if(rset.next())
													{
														split_val=rset.getString(1)==null?"":rset.getString(1);
													}
												}
												
												
												queryString = "SELECT MAN_CD,MAN_CONF_CD,CARGO_SEQ_NO,price FROM FMS7_MAN_CONFIRM_CARGO_DTL WHERE CARGO_REF_CD='"+temp_cargo_igx.elementAt(i)+"' ";
												//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
												rset = stmt.executeQuery(queryString);
												if(rset.next())
												{
													String cont_mapp_id="B-%-"+rset.getString(2)+"-0-"+rset.getString(1)+"-"+rset.getString(3);
													String partycd="";
													queryString = "SELECT TRD_CD FROM FMS7_MAN_REQ_MST WHERE MAN_CD='"+rset.getString(1)+"' ";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset4 = stmt4.executeQuery(queryString);
													if(rset4.next())
													{
														partycd=rset4.getString(1)==null?"":rset4.getString(1);
													}
													
													//queryString = "SELECT nvl(SUM(EXIT_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
													queryString = "SELECT nvl(SUM(ENTRY_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
															+ " to_date('"+frmdt+"','dd/mm/yyyy') and  to_date('"+todt+"','dd/mm/yyyy') and party_cd='"+partycd+"' ";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset4 = stmt4.executeQuery(queryString);
													if(rset4.next())
													{
														QTY_MSG_IGX.add(split_val+" % of "+rset4.getDouble(1));
													}else{
														QTY_MSG_IGX.add("");
													}
												}else{
													QTY_MSG_IGX.add("");
												}
											}else{
												QTY_MSG_IGX.add("");
											}
											if(!adj_amt.equals("") && (!adj_amt.equals("0"))){
												String title="";
												double ori_gross=rset3.getDouble(2);
												//System.out.println("ori_gross = "+ori_gross);
												String gross="";
												String gros="";
												if(inv_curr.equals("2")){
													gross=nf.format(rset3.getDouble(1));
												}else{
													gross=nf.format(ori_gross+Double.parseDouble(adj_amt));
												}
												if(adj_sign.equals("+")){
													title="Gross Amount ("+gross+") + Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
												}else if(adj_sign.equals("-")){
													title="Gross Amount ("+gross+") - Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
												}
												
												Vtitle.add(title);
											}else{
												Vtitle.add("");
											}
										//	System.out.println("durat-51]2-end "+sg_drcr_no.size());
										}
								 }
								 }
							}
						}
							//System.out.println("msg--"+QTY_MSG_IGX);
							double alloC_qty=0;
							queryString = "SELECT MAN_CD,MAN_CONF_CD,CARGO_SEQ_NO,price FROM FMS7_MAN_CONFIRM_CARGO_DTL WHERE CARGO_REF_CD='"+temp_cargo_igx.elementAt(i)+"' ";
							//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
							rset = stmt.executeQuery(queryString);
							if(rset.next())
							{
								String cont_mapp_id="B-%-"+rset.getString(2)+"-0-"+rset.getString(1)+"-"+rset.getString(3);
								
								//queryString = "SELECT nvl(SUM(EXIT_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
								queryString = "SELECT nvl(SUM(ENTRY_TOT_ENE),'0') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
										+ " to_date('"+frmdt+"','dd/mm/yyyy') and  to_date('"+todt+"','dd/mm/yyyy') and party_cd='"+temp_trd_cd.elementAt(i)+"' ";
								//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
								rset1 = stmt1.executeQuery(queryString);
								while(rset1.next())
									{
										if(cnt==0){
											alloC_qty=rset1.getDouble(1);
											if(alloC_qty>0){
												Vtitle.add("");
											CARGO_REF_CD_IGX.add(temp_cargo_igx.elementAt(i));
											Vsupp_plant_Abbr_IGX.add("");
											CARGO_PRICE_IGX.add(temp_price_igx.elementAt(i));
											contract_no_igx.add("");
											contract_rev_no_igx.add("");
											plant_cd_igx.add("");
											VESSEL_NM_IGX.add("");
											usdmmbtu.add("");
											TRD_CD_IGX.add(temp_trd_cd.elementAt(i));
											ACTUAL_UNLOADED_QTY_IGX.add(nf3.format(alloC_qty));
											double trans_charge = (alloC_qty * trans_perc);
											double total_trans_charge = ((trans_charge*cgst)/100) + ((trans_charge*sgst)/100);
											INVOICE_AMT_TRANS_IGX.add(trans_charge);
											INVOICE_TAXAMT_TRANS_IGX.add(total_trans_charge);
											unloaded_qty_igx.add(nf.format(alloC_qty));
											ACTUAL_UNLOADED_QTY_NUMERIC_IGX.add(nf.format(alloC_qty));
											purchase_total_qty_igx += Double.parseDouble(nf.format(alloC_qty));
											exchg_rate_2_igx="0";
											
											TRADER_INV_CURRENCY_IGX.add("");
											EXCHG_RATE_VALUE_IGX.add("-");
											INVOICE_AMT_IGX.add("0");
											INVOICE_TAX_AMT_IGX.add("0");
											String taxamt="0";
											INVOICE_AMT_TDS_IGX.add("-");
											purchase_total_tax_inr_igx+=Double.parseDouble((taxamt));
											double gramt=Double.parseDouble(nf.format(alloC_qty)) * Double.parseDouble(rset.getString(4));
											//System.out.println("gramt--"+gramt+"--alloC_qty--"+alloC_qty+"-----"+Double.parseDouble(rset.getString(4)));
											//temp_inv_amt_igx.add("0");
											if(temp_price_igx.elementAt(i).equals("1")){
												temp_inv_amt_igx.add(nf.format(gramt));
											}else{
												temp_inv_amt_igx.add("0");
											}
											double NETAMT=Double.parseDouble(nf.format(gramt));
											INVOICE_AMT_net_IGX.add(nf.format(NETAMT));
											queryString = "Select To_char(to_date('"+frmdt+"','dd/mm/yyyy'),'Month') from dual";
											////System.out.println("From Selected Month = "+queryString);
											rset2 = stmt2.executeQuery(queryString);			
											if(rset2.next())
											{
												mon = rset2.getString(1)==null?"0":rset2.getString(1);
											}
											ACT_ARRV_MONTH_IGX.add(mon);
											String inv_type="2";
											if(inv_type.equals("1")){
												inv_type="Monthly";
											}else if(inv_type.equals("2")){
												inv_type="Fortnightly";
											}
											else if(inv_type.equals("3")){
												inv_type="Weekly";
											}
											ACT_ARRV_DT_IGX.add(frmdt);
											double amt_usd=alloC_qty* Double.parseDouble(rset.getString(4)==null?"0":rset.getString(4));
											String rmk="";
											double tax_amt_usd=0;
											/*if(!rmk.equals("")){
												TAX_rmk_IGX.add(rmk+"%");
												String temp[]=rmk.split("@");
												 tax_amt_usd=(amt_usd)* Double.parseDouble(temp[1].trim())/100;
												
											}*/
											
											
											invoice_type_IGX.add(inv_type);
											purchase_total_tax_inr_igx+=tax_amt_usd;
											//usd_mmbtu=(tax_amt_usd+Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))/alloC_qty);
											INVOICE_NO_IGX.add("");
											INVOICE_DT_IGX.add("");	
											
											CONF_PRICE_IGX.add(rset.getString(4)==null?"":nf2.format(Double.parseDouble(rset.getString(4))));
											//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+temp_price_igx);
											CONF_PRICE_NUMERIC_IGX.add(nf2.format(amt_usd));
											if(temp_price_igx.elementAt(i).equals("1")){
												USD_VAL_INVOICE_IGX.add("-");
												USD_VAL_INVOICE_NUMERIC_IGX.add("0");
											}else{
												USD_VAL_INVOICE_IGX.add(nf3.format(amt_usd));
												
												USD_VAL_INVOICE_NUMERIC_IGX.add(nf.format(amt_usd));
												purchase_total_invoice_value_igx += Double.parseDouble(nf.format(amt_usd));
											}
											
											DUE_DT_IGX.add("");
											Check_flag_IGX.add("");
											Authorize_flag_IGX.add("");
											Approve_flag_IGX.add("");
											QTY_MSG_IGX.add("");
	//										String st_dt=rset1.getString(10)==null?"":rset1.getString(10);
	//										String end_dt=rset1.getString(19)==null?"":rset1.getString(19);
											queryString = "SELECT to_char((sysdate),'dd/mm/yyyy') FROM dual where sysdate "
													+ "between to_date('"+frmdt+"','dd/mm/yyyy') and to_date('"+todt+"','dd/mm/yyyy') ";
											//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
											rset2 = stmt2.executeQuery(queryString);
											if(rset2.next())
												{
													queryString = "SELECT to_char(Max(alloc_dt),'dd/mm/yyyy') FROM FMS9_PO_ALLOC_MST WHERE CONT_MAPPING_ID like '"+cont_mapp_id+"' and alloc_dt between "
															+ " to_date('"+frmdt+"','dd/mm/yyyy') and  to_date('"+todt+"','dd/mm/yyyy') and party_cd='"+temp_trd_cd.elementAt(i)+"' ";
													//System.out.println("QRY-001: FMS7_CONT_EXCHG_RATE_MST: "+queryString);
													rset2 = stmt2.executeQuery(queryString);
													if(rset2.next())
														{
															duration_igx.add(frmdt+" - "+rset2.getString(1));
														}else{
															duration_igx.add(frmdt+" - "+todt);
														}
												}else{
													duration_igx.add(frmdt+" - "+todt);
												}
											
											
											
											queryString = "SELECT TRADER_NAME,TRADER_ABBR FROM FMS7_TRADER_MST WHERE FLAG='T' AND TRADER_CD="+temp_trd_cd.elementAt(i)+"";
											//System.out.println("TRADER Master query = "+queryString);
											rset2 = stmt2.executeQuery(queryString);
											if(rset2.next())
											{			
												TRADER_NAME_IGX.add(rset2.getString(1)==null?"":rset2.getString(1));
												TRADER_ABBR_IGX.add(rset2.getString(2)==null?"":rset2.getString(2));
											}
											else
											{
												TRADER_NAME_IGX.add("");
												TRADER_ABBR_IGX.add("");
											}
											//
											String nTax_Structure_dtl="";
											String nTax_struct_cd="";
											int tax_factor = 0;
											String tax_code="";
											String tax_abbr="";
											queryString = "SELECT A.TAX_STRUCT_DTL,A.TAX_STRUCT_CD FROM FMS7_TRADER_TAX_STRUCT_DTL A WHERE " +
													   "trader_cd='"+temp_trd_cd.elementAt(i)+"' AND plant_seq_no='"+plant_seq_no+"' AND " +
													   "A.tax_struct_dt=(SELECT MAX(B.tax_struct_dt) FROM FMS7_TRADER_TAX_STRUCT_DTL B WHERE " +
											 		  "A.trader_cd=B.trader_cd AND A.plant_seq_no=B.plant_seq_no AND " +
											 		  "B.tax_struct_dt<=TO_DATE('"+frmdt+"','DD/MM/YYYY'))";
											rset4 = stmt4.executeQuery(queryString);
//											System.out.println("queryString---"+queryString);
											if(rset4.next())
											{
												nTax_Structure_dtl = rset4.getString(1)==null?"":rset4.getString(1);
												nTax_struct_cd = rset4.getString(2)==null?"0":rset4.getString(2);
												
												double tax_amt=0;
												queryString = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0.00'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
														  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+nTax_struct_cd+"' AND " +
														  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+nTax_struct_cd+"' AND " +
														  "B.app_date<=TO_DATE('"+frmdt+"','DD/MM/YYYY')) ORDER BY A.tax_code";
												//System.out.println("STEP-1A.3:FMS7_TAX_STRUCTURE_DTL: "+queryString);
												rset2=stmt2.executeQuery(queryString);
												while(rset2.next())
												{
													tax_factor= Integer.parseInt((rset2.getString(2)));
													tax_code=rset2.getString(1)==null?"":rset2.getString(1);
													if(rset2.getString(3).equals("1"))
													{
														//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset.getString(2)))/100;
														tax_amt = (amt_usd*Double.parseDouble(rset2.getString(2)))/100;
//														System.out.println("tax_Amt"+invoice_amt);
														
													}
													else if(rset2.getString(3).equals("2"))
													{
														queryString1 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
																  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+nTax_struct_cd+"' AND " +
																  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+nTax_struct_cd+"' AND " +
																  "B.app_date<=TO_DATE('"+frmdt+"','DD/MM/YYYY')) AND A.tax_code="+rset2.getString(4)+"";
													//////System.out.println("Query For Finding Out Tax Which Is Dependent On Other Tax Value = "+queryString1);
												 		rset3=stmt3.executeQuery(queryString1);
												 		if(rset3.next())
												 		{
												 			if(rset3.getString(3).equals("1"))
															{
																//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset1.getString(2)))/100;
												 				tax_amt = (amt_usd*Double.parseDouble(rset3.getString(2)))/100;
															}
														
											 			tax_amt = (tax_amt*Double.parseDouble(rset2.getString(2)))/100;
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
													queryString = "SELECT sht_nm,tax_name FROM FMS7_TAX_MST WHERE " +
															  "tax_code='"+tax_code+"'";
													rset3 = stmt3.executeQuery(queryString);
													if(rset3.next())
													{
														tax_abbr = rset3.getString(1).trim()==null?"":rset3.getString(1).trim();
													}
												//customer_Invoice_Tax_Amt.add(nf.format(Math.round(tax_amt)));
												String tax_str=nf.format(tax_amt);
												String tax_line=tax_abbr+" @ "+tax_factor+" %";
												TAX_amt_usd_IGX.add(tax_str);
												TAX_rmk_IGX.add(tax_line);
											}
											}else{
												TAX_amt_usd_IGX.add("0");
												TAX_rmk_IGX.add("");
											}
										}
										}
								}
					}
					
						/*else{
							ACTUAL_UNLOADED_QTY_IGX.add("-");
							unloaded_qty_igx.add("0");
							ACTUAL_UNLOADED_QTY_NUMERIC_IGX.add("0");
							//exchg_rt_igx.add("");
							EXCHG_RATE_VALUE_IGX.add("-");
							INVOICE_AMT_IGX.add("0");
							INVOICE_TAX_AMT_IGX.add("0");
							temp_inv_amt_igx.add("0.00");
							ACT_ARRV_MONTH_IGX.add("");
							ACT_ARRV_DT_IGX.add("");
							TAX_rmk_IGX.add("");
							TAX_amt_usd_IGX.add("0");
							invoice_type_IGX.add("");
						}*/
					
					//SUN_APPROVAL.add(rset.getString(7)==null?"":rset.getString(7)); //Commented By Samik Shah On 23rd August, 2011 ...
				/*}
				else
				{
					INVOICE_NO_DOM.add("");
					INVOICE_DT_DOM.add("");						
					ACTUAL_UNLOADED_QTY_DOM.add("-");
					ACTUAL_UNLOADED_QTY_NUMERIC_DOM.add("0.00");
					CONF_PRICE_DOM.add("");
					CONF_PRICE_NUMERIC_DOM.add("0.0000");
					USD_VAL_INVOICE_DOM.add("-");
					USD_VAL_INVOICE_NUMERIC_DOM.add("0.00");
					DUE_DT_DOM.add("");
					temp_inv_amt_igx.add("0");
					unloaded_qty_igx.add("0");
					Check_flag_DOM.add("");
					Authorize_flag_DOM.add("");
					Approve_flag_DOM.add("");
				//	exchg_rt_igx.add("");
					EXCHG_RATE_VALUE_DOM.add("-");
					INVOICE_AMT_DOM.add("0");
					INVOICE_TAX_AMT_DOM.add("0");
					ACT_ARRV_MONTH_DOM.add("");
					ACT_ARRV_DT_DOM.add("");
					TAX_rmk_DOM.add("");
					TAX_amt_usd_DOM.add("0");
					invoice_type_DOM.add("");
					
					//SUN_APPROVAL.add(""); //Commented By Samik Shah On 23rd August, 2011 ...
				}*/
						}
			}
			//System.out.println("durat-all ");
//			System.out.println("---"+CARGO_REF_CD_DOM);
//			System.out.println("-USD_VAL_INVOICE_NUMERIC_DOM--"+USD_VAL_INVOICE_NUMERIC_IGX.size());
			for(int i=0; i<CARGO_REF_CD_IGX.size(); i++)
			{
				
				if(!Dist_CARGO_REF_CD_IGX.contains(CARGO_REF_CD_IGX.elementAt(i))){
					Dist_CARGO_REF_CD_IGX.add(CARGO_REF_CD_IGX.elementAt(i));
				}
				if(!Dist_INVOICE_NO_IGX.contains(INVOICE_NO_IGX.elementAt(i)) && (!INVOICE_NO_IGX.elementAt(i).equals(""))){
					Dist_INVOICE_NO_IGX.add(INVOICE_NO_IGX.elementAt(i));
				}
				
				String refno="";
				refno=""+CARGO_REF_CD_IGX.elementAt(i)+"-"+INVOICE_NO_IGX.elementAt(i);
				queryString = "SELECT XML_GEN_FLAG, SUN_APPROVAL FROM FMS7_ACCOUNT_APPROVED_DTL " +
				  			  "WHERE INV_CARGO_NO='"+refno+"' AND JOURNAL_TYPE='FMSPR'";
//				System.out.println("Confirmed FMS7_ACCOUNT_APPROVED_DTL Details Query = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					XML_GEN_FLAG_IGX.add(rset.getString(1)==null?"N":rset.getString(1));
					SUN_APPROVAL_IGX.add(rset.getString(2)==null?"N":rset.getString(2)); //Introduced By Samik Shah On 23rd August, 2011 ...
				}
				else
				{
					XML_GEN_FLAG_IGX.add("N");
					SUN_APPROVAL_IGX.add("N"); //Introduced By Samik Shah On 23rd August, 2011 ...
				}
				double sbi_tt_selling_exchg_rate_igx = 0;
				double foreign_exchg_rate_igx = 0;
				String exchg_rate_cd_igx = "2"; //For SBI TT Selling Exchange Rate ...
				String exchg_rate_cd_2_igx = "5"; //For Group Foreign Exchange Rate ...
				String exchg_rate_igx = "";
				
				
				String arr_dt_igx = ""+ACT_ARRV_DT_IGX.elementAt(i);
				String arrival_date_and_rate_note_igx = "";
				
				if(arr_dt_igx.trim().equals(""))
				{
					arrival_date_and_rate_note_igx += "\nActual Arrival Date of Cargo is missing."; 
				}
				
				String queryString2 = "SELECT EXCHG_VAL FROM FMS7_EXCHG_RATE_ENTRY WHERE EXCHG_RATE_CD='"+exchg_rate_cd_igx+"' AND EFF_DT=TO_DATE('"+ACT_ARRV_DT_IGX.elementAt(i)+"','DD/MM/YYYY')";
				//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
				rset2=stmt2.executeQuery(queryString2);
				if(rset2.next())
				{
					exchg_rate_igx = rset2.getString(1)==null?"0":rset2.getString(1);
					if(!exchg_rate_igx.equals("")){
						sbi_tt_selling_exchg_rate_igx = Double.parseDouble(exchg_rate_igx);
					}
				}
				
				if(sbi_tt_selling_exchg_rate_igx>0)
				{
					GROUP_FOREIGN_EXCHG_RATE_IGX.add(nf2.format(sbi_tt_selling_exchg_rate_igx));
					GROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX.add(nf2.format(sbi_tt_selling_exchg_rate_igx));
//					FINAL_SELLER_INV_AMT_INR_IGX.add(nf3.format(sbi_tt_selling_exchg_rate_igx*Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
					FINAL_SELLER_INV_AMT_INR_NUMERIC_IGX.add(nf.format(sbi_tt_selling_exchg_rate_igx*Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
					
					final_seller_amt_igx.add(nf.format(sbi_tt_selling_exchg_rate_igx*Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
					//purchase_total_inv_value_inr_igx += (sbi_tt_selling_exchg_rate_igx*Double.parseDouble(""+temp_inv_amt_igx.elementAt(i)));
					
					
				}
				else
				{
					GROUP_FOREIGN_EXCHG_RATE_IGX.add("-");
					GROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX.add("0.0000");
//					FINAL_SELLER_INV_AMT_INR_IGX.add("-");
					FINAL_SELLER_INV_AMT_INR_NUMERIC_IGX.add("0.00");
					final_seller_amt_igx.add("0.00");
					if(arrival_date_and_rate_note_igx.trim().length()<5)
					{
						arrival_date_and_rate_note_igx += "\nSBI TT Selling rate is missing for Actual Arrival Date: "+arr_dt_igx.trim();
					}
				}
				FINAL_SELLER_INV_AMT_INR_IGX.add(nf3.format(Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
				purchase_total_inv_value_inr_igx += (Double.parseDouble(""+temp_inv_amt_igx.elementAt(i)));
				if(!INVOICE_AMT_TDS_IGX.elementAt(i).equals("-")){
					purchase_total_cost_inr_igx += ((Double.parseDouble(""+INVOICE_AMT_IGX.elementAt(i))+Double.parseDouble(""+INVOICE_TAX_AMT_IGX.elementAt(i)))-Double.parseDouble(""+INVOICE_AMT_TDS_IGX.elementAt(i)));
				}else{
					purchase_total_cost_inr_igx += (Double.parseDouble(""+INVOICE_AMT_IGX.elementAt(i))+Double.parseDouble(""+INVOICE_TAX_AMT_IGX.elementAt(i)));
				}
				//FINAL_SELLER_INV_AMT_INR_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
				//System.out.println("FINAL_SELLER_INV_AMT_INR_IGX---"+FINAL_SELLER_INV_AMT_INR_IGX);
				ARRIVAL_DATE_RATE_NOTE_IGX.add(arrival_date_and_rate_note_igx.trim());
				
				String prov_cd_payment_date_igx = "";
				
				queryString = "SELECT EXCHG_RATE,CUSTOM_DUTY_PAY,TO_CHAR(CUSTOM_DUTY_DT,'dd/mm/yyyy') FROM FMS7_CUSTOM_DUTY WHERE CARGO_REF_NO="+CARGO_REF_CD_IGX.elementAt(i)+" ";
				//System.out.println("FMS7_CUSTOM_DUTY Query = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())	
				{
					if(SPLIT_SEQ_IGX.elementAt(i).equals("1") || SPLIT_SEQ_IGX.elementAt(i).equals("0"))
					{
					//EXCHG_RATE_VALUE.add(rset.getString(1)==null?"-":nf2.format(Double.parseDouble(rset.getString(1))));						
					temp_tot_cd_amt_igx.add(rset.getString(2)==null?"0.00":nf.format(Double.parseDouble(rset.getString(2))));
					if(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))>0)
					{
						purchase_total_cd_inr_igx += Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i));
						//purchase_total_cost_inr_igx += Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i));
						TOTAL_CD_AMT_IGX.add(nf3.format(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))));
						TOTAL_CD_AMT_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))));
					}
					else
					{
						TOTAL_CD_AMT_IGX.add("-");
						TOTAL_CD_AMT_NUMERIC_IGX.add("0.00");
					}
					prov_cd_payment_date_igx = rset.getString(3)==null?"":rset.getString(3);
					}
					else
					{
						TOTAL_CD_AMT_IGX.add("-");
						TOTAL_CD_AMT_NUMERIC_IGX.add("0.00");
						temp_tot_cd_amt_igx.add("0.00");
					}
				}
				else
				{
					//EXCHG_RATE_VALUE.add("-");	
					TOTAL_CD_AMT_IGX.add("-");
					TOTAL_CD_AMT_NUMERIC_IGX.add("0.00");
					temp_tot_cd_amt_igx.add("0.00");
				}
				
				String prov_cd_date_and_rate_note_igx = "";
				
				if(prov_cd_payment_date_igx.trim().equals(""))
				{
					prov_cd_date_and_rate_note_igx += "\n"+"Provisional Custom Duty Payment Date is missing."; 
				}
				
				queryString2 = "SELECT EXCHG_VAL FROM FMS7_EXCHG_RATE_ENTRY WHERE EXCHG_RATE_CD='"+exchg_rate_cd_2_igx+"' AND EFF_DT=TO_DATE('"+prov_cd_payment_date_igx+"','DD/MM/YYYY')";
				//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
				rset2=stmt2.executeQuery(queryString2);
				if(rset2.next())
				{
					exchg_rate_2_igx = rset2.getString(1)==null?"0":rset2.getString(1);
					foreign_exchg_rate_igx = Double.parseDouble(exchg_rate_2_igx);
					//EXCHG_RATE_VALUE_IGX.add(rset2.getString(1)==null?"-":nf2.format(Double.parseDouble(rset2.getString(1))));
					if(prov_cd_date_and_rate_note_igx.trim().length()<5 && foreign_exchg_rate_igx<0.0001)
					{
						prov_cd_date_and_rate_note_igx += "\n"+"Group Foreign Exchange rate is missing for Custom Payment Date: "+prov_cd_payment_date_igx.trim();
					}
				}
				else
				{
					//EXCHG_RATE_VALUE_IGX.add("-");
					if(prov_cd_date_and_rate_note_igx.trim().length()<5)
					{
						prov_cd_date_and_rate_note_igx += "\n"+"Group Foreign Exchange rate is missing for Custom Payment Date: "+prov_cd_payment_date_igx.trim();
					}
				}
				
				CUSTOM_DUTY_DATE_RATE_NOTE_IGX.add(prov_cd_date_and_rate_note_igx.trim());
				//System.out.println("arrival_date_and_rate_note = "+arrival_date_and_rate_note+",  prov_cd_date_and_rate_note = "+prov_cd_date_and_rate_note);
				
				//Logic for FMS7_FINAL_CUSTOM_DUTY
				queryString = "SELECT CUSTOM_DUTY_PAY_REFUND,INTERST_X_DAYS FROM FMS7_FINAL_CUSTOM_DUTY WHERE CARGO_REF_NO="+CARGO_REF_CD_IGX.elementAt(i)+" ";
				//System.out.println("FMS7_FINAL_CUSTOM_DUTY Query = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())	
				{
					//if(SPLIT_SEQ_IGX.elementAt(i).equals("1") || SPLIT_SEQ_IGX.elementAt(i).equals("0"))
					//{
					temp_tot_pay_refund_igx.add(nf.format(Double.parseDouble(rset.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset.getString(1))))-Double.parseDouble(rset.getString(2)==null?"0":nf.format(Double.parseDouble(rset.getString(2))))));
					if(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))!=0)
					{
						purchase_total_addl_cd_inr_igx += Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i));
						//purchase_total_cost_inr_igx += Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i));
						custom_duty_interest_total_inr_igx += Double.parseDouble(rset.getString(2)==null?"0":nf.format(Double.parseDouble(rset.getString(2))));
						TOTAL_PAID_REFUND_IGX.add(nf3.format(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))));
						TOTAL_PAID_REFUND_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))));
						CUSTOM_DUTY_INTEREST_IGX.add(nf3.format(Double.parseDouble(rset.getString(2)==null?"0":nf.format(Double.parseDouble(rset.getString(2))))));
						CUSTOM_DUTY_INTEREST_NUMERIC_IGX.add(nf.format(Double.parseDouble(rset.getString(2)==null?"0":nf.format(Double.parseDouble(rset.getString(2))))));
					}
					else
					{
						TOTAL_PAID_REFUND_IGX.add("-");
						TOTAL_PAID_REFUND_NUMERIC_IGX.add("0.00");
						CUSTOM_DUTY_INTEREST_IGX.add("-");
						CUSTOM_DUTY_INTEREST_NUMERIC_IGX.add("0.00");
					}
					/*}
					else
					{
						TOTAL_PAID_REFUND_IGX.add("-");
						TOTAL_PAID_REFUND_NUMERIC_IGX.add("0.00");
						CUSTOM_DUTY_INTEREST_IGX.add("-");
						CUSTOM_DUTY_INTEREST_NUMERIC_IGX.add("0.00");
						temp_tot_pay_refund_igx.add("0.00");
					}*/
				}
				else
				{
					TOTAL_PAID_REFUND_IGX.add("-");
					TOTAL_PAID_REFUND_NUMERIC_IGX.add("0.00");
					CUSTOM_DUTY_INTEREST_IGX.add("-");
					CUSTOM_DUTY_INTEREST_NUMERIC_IGX.add("0.00");
					temp_tot_pay_refund_igx.add("0.00");
				}
			}
			//System.out.println("Dist_CARGO_REF_CD_IGX--"+Dist_CARGO_REF_CD_IGX);
			for(int i=0; i<CARGO_REF_CD_IGX.size(); i++)
			{										
				if(!(""+FINAL_SELLER_INV_AMT_INR_IGX.elementAt(i)).equals("-") && temp_inv_amt_igx.elementAt(i)!=null && !temp_inv_amt_igx.elementAt(i).equals("") && !temp_inv_amt_igx.elementAt(i).equals("0") && temp_tot_cd_amt_igx.elementAt(i)!=null && !temp_tot_cd_amt_igx.elementAt(i).equals("") && !temp_tot_cd_amt_igx.elementAt(i).equals("0"))
				{
					temp_cd_paid_igx.add(nf.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
//					CD_PAID_IGX.add(nf3.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
//					CD_PAID_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
					
					double total_amt_igx = Double.parseDouble(""+INVOICE_AMT_IGX.elementAt(i));
					double total_tax_amt_igx = Double.parseDouble(""+INVOICE_TAX_AMT_IGX.elementAt(i));
					double total_trans_igx = Double.parseDouble(""+INVOICE_AMT_TRANS_IGX.elementAt(i));
					double total_tax_trans_igx = Double.parseDouble(""+INVOICE_TAXAMT_TRANS_IGX.elementAt(i));
					double tdsamt = 0;
					if(!(""+INVOICE_AMT_TDS_IGX.elementAt(i)).equals("-"))
					{
						tdsamt = Double.parseDouble(""+INVOICE_AMT_TDS_IGX.elementAt(i));
					}else{
						tdsamt = 0;
					}
					if(tds_flag.equalsIgnoreCase("Y")){
						//System.out.println("total_amt_igx---"+total_amt_igx);
//						CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx-tdsamt));
//						CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx-tdsamt));
						CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx));
						CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx));
					}else{
						CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx));
						CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx));
					}
					
					if(!(""+ACTUAL_UNLOADED_QTY_IGX.elementAt(i)).equals("-"))
					{
						if(Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))>0)
						{
							INR_PER_MMBTU_IGX.add(nf2.format((total_amt_igx+total_tax_amt_igx)/Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))));
							INR_PER_MMBTU_NUMERIC_IGX.add(nf2.format((total_amt_igx+total_tax_amt_igx)/Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))));
						}
						else
						{
							INR_PER_MMBTU_IGX.add("-");
							INR_PER_MMBTU_NUMERIC_IGX.add("0.0000");
						}
					}
					else
					{
						INR_PER_MMBTU_IGX.add("-");
						INR_PER_MMBTU_NUMERIC_IGX.add("0.0000");
					}
				}
				else
				{
					//CD_PAID_IGX.add(nf3.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
					double total_tax_amt_igx = Double.parseDouble(""+INVOICE_TAX_AMT_IGX.elementAt(i));
					double total_amt_igx = Double.parseDouble(""+INVOICE_AMT_IGX.elementAt(i));
					double total_trans_igx = Double.parseDouble(""+INVOICE_AMT_TRANS_IGX.elementAt(i));
					double total_tax_trans_igx = Double.parseDouble(""+INVOICE_TAXAMT_TRANS_IGX.elementAt(i));
					double tdsamt =0;
					if(!(""+INVOICE_AMT_TDS_IGX.elementAt(i)).equals("-"))
					{
						tdsamt = Double.parseDouble(""+INVOICE_AMT_TDS_IGX.elementAt(i));
					}else{
						tdsamt = 0;
					}
					
					if(tds_flag.equalsIgnoreCase("Y")){
//						CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx-tdsamt));
//						CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx-tdsamt));
						CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx));
						CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx));
					}else{
						CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx));
						CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx+total_trans_igx+total_tax_trans_igx));
					}
					//double total_amt_igx = Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)));
					
					temp_cd_paid_igx.add(nf.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
					//CD_PAID_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
					if(!(""+ACTUAL_UNLOADED_QTY_IGX.elementAt(i)).equals("-"))
					{
						if(Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))>0)
						{
							INR_PER_MMBTU_IGX.add(nf2.format((total_amt_igx+total_tax_amt_igx)/Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))));
							INR_PER_MMBTU_NUMERIC_IGX.add(nf2.format((total_amt_igx+total_tax_amt_igx)/Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))));
						}
						else
						{
							INR_PER_MMBTU_IGX.add("-");
							INR_PER_MMBTU_NUMERIC_IGX.add("0.0000");
						}
					}
					else
					{
						INR_PER_MMBTU_IGX.add("-");
						INR_PER_MMBTU_NUMERIC_IGX.add("0.0000");
					}
				}
				
				double cd_usd_igx = 0;
				double addl_cd_usd_igx = 0;
				double total_cost_igx = 0;
				double usd_per_mmbtu_igx = 0;
				
				
				if(!(""+temp_tot_cd_amt_igx.elementAt(i)).equals("0") && !(""+EXCHG_RATE_VALUE_IGX.elementAt(i)).equals("-"))
				{
					CUSTOM_DUTY_USD_IGX.add(nf3.format(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					CUSTOM_DUTY_USD_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					cd_usd_igx = Double.parseDouble(nf.format(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					purchase_total_cd_usd_igx += Double.parseDouble(nf.format(cd_usd_igx));
				}
				else
				{
					CUSTOM_DUTY_USD_IGX.add("-");
					CUSTOM_DUTY_USD_NUMERIC_IGX.add("0.00");
				}
				
				if(!(""+temp_tot_pay_refund_igx.elementAt(i)).equals("0") && !(""+EXCHG_RATE_VALUE_IGX.elementAt(i)).equals("-"))
				{
					ADDL_CUSTOM_DUTY_USD_IGX.add(nf3.format(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					ADDL_CUSTOM_DUTY_USD_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					addl_cd_usd_igx = Double.parseDouble(nf.format(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					purchase_total_addl_cd_usd_igx += Double.parseDouble(nf.format(addl_cd_usd_igx));
				}
				else
				{
					ADDL_CUSTOM_DUTY_USD_IGX.add("-");
					ADDL_CUSTOM_DUTY_USD_NUMERIC_IGX.add("0.00");
				}
				
				//total_cost_igx = Double.parseDouble(""+temp_inv_amt_igx.elementAt(i)) + cd_usd_igx + addl_cd_usd_igx;
				//System.out.println("USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i)--"+CARGO_PRICE_IGX);
				//System.out.println("TAX_amt_usd_IGX.elementAt(i)--"+TAX_amt_usd_IGX.elementAt(i));
				total_cost_igx = Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i)) + Double.parseDouble(""+TAX_amt_usd_IGX.elementAt(i));
				//purchase_total_inv_value_usd_igx += Double.parseDouble(nf.format(Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
				if(!USD_VAL_INVOICE_IGX.elementAt(i).equals("-")){
					if(!CARGO_PRICE_IGX.elementAt(i).equals("1")){
						purchase_total_inv_value_usd_igx += Double.parseDouble(nf.format(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i))));
					}
					
				}
				
				if(total_cost_igx>0 && Double.parseDouble(""+unloaded_qty_igx.elementAt(i))>0)
				{
					usd_per_mmbtu_igx = total_cost_igx/Double.parseDouble(""+unloaded_qty_igx.elementAt(i));
				}
				
				if(total_cost_igx>0)
				{
					COST_OF_PURCHASE_USD_IGX.add(nf3.format(total_cost_igx));
					COST_OF_PURCHASE_USD_NUMERIC_IGX.add(nf.format(total_cost_igx));
					if(!CARGO_PRICE_IGX.elementAt(i).equals("1")){
						purchase_total_cost_usd_igx += Double.parseDouble(nf.format(total_cost_igx));
					}
					
				}
				else
				{
					COST_OF_PURCHASE_USD_IGX.add("-");
					COST_OF_PURCHASE_USD_NUMERIC_IGX.add("0.00");
				}
				
				if(usd_per_mmbtu_igx>0)
				{
					//USD_PER_MMBTU_IGX.add(nf2.format(usd_per_mmbtu_igx));
					//USD_PER_MMBTU_NUMERIC_IGX.add(nf2.format(usd_per_mmbtu_igx));
					if(!usdmmbtu.elementAt(i).equals("")){
						USD_PER_MMBTU_IGX.add(nf2.format(Double.parseDouble(usdmmbtu.elementAt(i)+"")));
						USD_PER_MMBTU_NUMERIC_IGX.add(nf2.format(Double.parseDouble(usdmmbtu.elementAt(i)+"")));
					}else{
						USD_PER_MMBTU_IGX.add("-");
						USD_PER_MMBTU_NUMERIC_IGX.add("0.0000");
					}
				}
				else
				{
					USD_PER_MMBTU_IGX.add("-");
					USD_PER_MMBTU_NUMERIC_IGX.add("0.0000");
				}
			}
			//System.out.println("durat-all end");
		}
		catch(Exception e)
		{
			//System.out.println("EXCEPTION:Databean_Accounting --> get_Purchase_dtls() --> "+e.getMessage());
			e.printStackTrace();
		}
	}
	public void get_Purchase_dtls_IGX_Approval_DRCR()
	{
		try {
			////System.out.println("From month "+month+" To month= "+to_month);
			Vector temp_usd_jbb_igx = new Vector();
			Vector temp_usd_invoice_igx = new Vector();
			Vector temp_usd_dr_cr_igx = new Vector();
			Vector temp_inv_amt_igx_DRCR = new Vector();			
			Vector temp_tot_cd_amt_igx = new Vector();
			Vector temp_tot_pay_refund_igx = new Vector();
			Vector temp_cd_paid_igx = new Vector();
			Vector final_seller_amt_igx = new Vector();
			Vector unloaded_qty_igx = new Vector();
			String from_dt_igx = "01/"+month+"/"+year;
			String to_dt_igx = "";
			queryString = "Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
			////System.out.println("Last Date Of The Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_dt_igx = rset.getString(1)==null?"0":rset.getString(1);
			}
			////System.out.println("From date "+from_dt+" To Date= "+to_dt);
			queryString = "Select To_char(to_date('"+from_dt_igx+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("From Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				mon = rset.getString(1)==null?"0":rset.getString(1);
			}
			
			queryString = "Select To_char(to_date('"+to_dt_igx+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("To The Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_mon = rset.getString(1)==null?"0":rset.getString(1);
			}
			String tds_flag="";
			String queryString1 = "SELECT TURNOVER_FLAG FROM FMS7_SUPPLIER_TURNOVER_DTL WHERE supplier_cd=1";
			//System.out.println("Seller Payment Details Query = "+queryString);
			rset1 = stmt1.executeQuery(queryString1);
			if(rset1.next())
			{
				tds_flag= rset1.getString(1)==null?"":rset1.getString(1);
			}
			Vector temp_cargo_igx=new Vector();
			Vector temp_seq_igx=new Vector();
			
			/*String q="SELECT A.CARGO_REF_NO , A.ACT_ARRV_DT,COUNT(A.SPLIT_SEQ) FROM FMS7_CARGO_ARRIVAL_DTL A " +
					  "WHERE A.ACT_ARRV_DT between (to_date('"+from_dt_igx+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_igx+"','dd/mm/yyyy')) GROUP BY A.ACT_ARRV_DT,A.CARGO_REF_NO ORDER BY A.ACT_ARRV_DT ";
			rset=stmt.executeQuery(q);
			while(rset.next())
			{
				temp_cargo_igx.add(rset.getString(1));
				int tempSeq=rset.getInt(3);
				if(tempSeq>1)
					temp_seq_igx.add("Y");
				else
					temp_seq_igx.add("N");
			}*/
			

			String trdcd=" AND PARTY_CD="+trader_cd+" ";
			String split1="";
			split1=" AND SPLIT_SEQ='0'";
			/*queryString = "SELECT B.CARGO_REF_cd, C.TRD_CD FROM " +
					  "FMS7_MAN_CONFIRM_CARGO_DTL B, FMS7_MAN_REQ_MST C " +
					  "WHERE  B.MAN_CD=C.MAN_CD AND " +
					  "(B.DELV_From_DT between (to_date('"+from_dt_igx+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_dom+"','dd/mm/yyyy')) OR B.DELV_to_DT between (to_date('"+from_dt_dom+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_dom+"','dd/mm/yyyy')))  AND "
			  		+ "B.DOM_buy_flag='Y' ";
			
			if(trader_cd!=null && !trader_cd.trim().equals("") && !trader_cd.trim().equals("0"))
			{
				queryString = queryString + trdcd;
			}
			queryString=queryString;*/
			queryString = "SELECT CARGO_REF_NO,PARTY_CD FROM FMS7_DOM_PUR_INV_DRCR_dTL WHERE (DR_CR_DT between "
					+ " to_date('"+from_dt_igx+"','dd/mm/yyyy') and " +
					  "to_date('"+to_dt_igx+"','dd/mm/yyyy'))";
			if(trader_cd!=null && !trader_cd.trim().equals("") && !trader_cd.trim().equals("0"))
			{
				queryString = queryString + trdcd;
			}
			queryString=queryString;
			//System.out.println("Confirmed Cargo Arrival Details Query = "+queryString);
			rset = stmt.executeQuery(queryString);
			while(rset.next())
			{
				temp_cargo_igx.add(rset.getString(1)==null?"":rset.getString(1));
				if(flag_accounting.equalsIgnoreCase("Approval")){
					
					/*queryString = "SELECT A.INVOICE_NO FROM FMS7_FINAL_SELLER_PAY A WHERE A.CARGO_REF_NO='"+rset.getString(1)+"' "
							+ "AND SPLIT_SEQ='0' ";				
					//System.out.println("FMS7_FINAL_SELLER_PAY >>>>>>>>>>>>> Seller Final Invoice Detail Query = "+queryString);
					rset1 = stmt1.executeQuery(queryString);
					if(rset1.next())
					{

						CARGO_REF_CD_DOM.add(rset.getString(1)==null?"":rset.getString(1));
						TRD_CD_DOM.add(rset.getString(2)==null?"0":rset.getString(2));
						queryString = "SELECT VESSEL_NM, TO_CHAR(ACT_ARRV_DT,'DD/MM/YYYY'), " +
								  "TO_CHAR(ACT_ARRV_DT,'Month') FROM FMS7_CARGO_ARRIVAL_dTL WHERE CARGO_REF_NO='"+rset.getString(1)+"'";
							
							rset1 = stmt1.executeQuery(queryString);
							if(rset1.next())
							{
								VESSEL_NM_DOM.add(rset1.getString(1)==null?"":rset1.getString(1));
							}else{
								VESSEL_NM_DOM.add("");
							}
							queryString = "SELECT TO_CHAR(period_st_DT,'DD/MM/YYYY'), " +
									  "TO_CHAR(period_end_DT,'DD/MM/YYYY') FROM FMS7_DOM_PUR_INV_dTL WHERE CARGO_REF_NO='"+rset.getString(1)+"'";
								
								rset1 = stmt1.executeQuery(queryString);
								if(rset1.next())
								{
									String st_dt=rset1.getString(1)==null?"":rset1.getString(1);
									String end_dt=rset1.getString(2)==null?"":rset1.getString(2);
									duration_igx.add(st_dt+" - "+end_dt);
								}else{
									duration_dom.add("");
								}
					
					}*/
				}else{
					/*CARGO_REF_CD_DOM.add(rset.getString(1)==null?"":rset.getString(1));
					TRD_CD_DOM.add(rset.getString(2)==null?"0":rset.getString(2));*/
					//temp_cargo_dom.add(rset.getString(1)==null?"":rset.getString(1));
					/*queryString = "SELECT VESSEL_NM, TO_CHAR(ACT_ARRV_DT,'DD/MM/YYYY'), " +
							  "TO_CHAR(ACT_ARRV_DT,'Month') FROM FMS7_CARGO_ARRIVAL_dTL WHERE CARGO_REF_NO='"+rset.getString(1)+"'";
						
						rset1 = stmt1.executeQuery(queryString);
						if(rset1.next())
						{
							VESSEL_NM_DOM.add(rset1.getString(1)==null?"":rset1.getString(1));
						}else{
							VESSEL_NM_DOM.add("");
						}*/
						
				}
			}
		//	}
			
			
			double usd_mmbtu=0;
			String exchg_rate_2_igx = "";
			//for(int i=0; i<temp_cargo_dom.size(); i++)
			{
				
				/*if(SPLIT_SEQ_DOM.elementAt(i).equals("1"))
				{
					refno=""+CARGO_REF_CD_DOM.elementAt(i)+"-"+"A";
				}
				else if(SPLIT_SEQ_DOM.elementAt(i).equals("2"))
				{
					refno=""+CARGO_REF_CD_DOM.elementAt(i)+"-"+"B";
				}
				else
				{*/
					
					queryString = "SELECT DR_CR_QTY,DR_CR_EXCHG_RT_VAL,DR_CR_AMT_INR,DR_CR_TAX_AMT,to_char(DR_CR_DT,'Month'),DR_CR_QTY"
								+ ",party_cd,remark,DR_CR_no,to_char(DR_CR_DT,'dd/mm/yyyy'),DR_CR_no,TO_CHAR(DR_CR_DT,'DD/MM/YYYY'),"
								+ "DR_CR_PRICE, DR_CR_AMT_USD, TO_CHAR(DR_CR_DUE_DT,'DD/MM/YYYY'),APPROVED_FLAG,"
								+ "CARGO_REF_NO,contract_no,contract_rev_no,to_char(PAY_EXCHG_RT_DT,'dd/mm/yyyy'), PAY_EXCHG_RT_VAL"
								+ " ,invoice_no,plant_cd,DR_CR_FLAG,invoice_no,adj_sign,adj_amt,buyer_plant_cd "
								+ "FROM FMS7_DOM_PUR_INV_DRCR_dTL WHERE (DR_CR_DT between "
								+ " to_date('"+from_dt_igx+"','dd/mm/yyyy') and " +
								  "to_date('"+to_dt_igx+"','dd/mm/yyyy')) AND APPROVED_FLAG='Y' and cargo_ref_no like '99%'";
					if(trader_cd!=null && !trader_cd.trim().equals("") && !trader_cd.trim().equals("0"))
					{
						queryString = queryString + trdcd;
					}
						//System.out.println("query---"+queryString);
						rset1 = stmt1.executeQuery(queryString);
						while(rset1.next())
						{
							String adj_sign=rset1.getString(26)==null?"0":rset1.getString(26);
							String adj_amt=rset1.getString(27)==null?"0":rset1.getString(27);
							CARGO_REF_CD_IGX_DRCR.add(rset1.getString(17)==null?"0":rset1.getString(17));
							String bu_unit="";
							if(rset1.getString(28).equals("0")){
								bu_unit="SEIPL -REG(GJ)";
							}else{
								queryString1 = "SELECT PLANT_NAME,PLANT_SHORT_ABBR FROM FMS7_SUPPLIER_PLANT_DTL "
										+ "WHERE SUPPLIER_CD='"+supplier_cd+"' AND SEQ_NO='"+rset1.getString(28)+"'";
								rset2=stmt2.executeQuery(queryString1);
								if(rset2.next())
								{
									bu_unit=rset2.getString(2)==null?"":rset2.getString(2);
								}
							}
							Vsupp_plant_Abbr_DRCR.add(bu_unit);
							PAY_EXCHG_RT_DT_DRCR.add(rset1.getString(20)==null?"":rset1.getString(20));
							PAY_EXCHG_RT_VAL_DRCR.add(rset1.getString(21)==null?"":rset1.getString(21));
							VESSEL_NM_IGX_DRCR.add("");
							inv_type_flag_DRCR.add("SG");
							String flag=rset1.getString(24)==null?"":rset1.getString(24);
							if(flag.equals("D")){
								DR_CR_FLAG_IGX_DRCR.add("Debit");
							}else if(flag.equals("C")){
								DR_CR_FLAG_IGX_DRCR.add("Credit");
							}else{
								DR_CR_FLAG_IGX_DRCR.add("");
							}
							DR_CR_ORI_INV_NO.add(rset1.getString(25)==null?"":rset1.getString(25));
							String trader_cd=rset1.getString(7)==null?"0":rset1.getString(7);
							TRD_CD_IGX_DRCR.add(rset1.getString(7)==null?"0":rset1.getString(7));
							ACTUAL_UNLOADED_QTY_IGX_DRCR.add(rset1.getString(1)==null?"-":nf3.format(Double.parseDouble(rset1.getString(1))));
							unloaded_qty_igx.add(rset1.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(1))));
							ACTUAL_UNLOADED_QTY_NUMERIC_IGX_DRCR.add(rset1.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(1))));
							purchase_total_qty_igx_DRCR += Double.parseDouble(rset1.getString(1)==null?"0":nf.format(Double.parseDouble(rset1.getString(1))));
							exchg_rate_2_igx=rset1.getString(2)==null?"0":rset1.getString(2);
							String inv_curr="";
							
							String trd_cd="",price_unit="";
							queryString="SELECT MAN_CD,price_unit FROM FMS7_MAN_CONFIRM_CARGO_DTL WHERE CARGO_REF_CD='"+rset1.getString(17)+"'";
							rset2=stmt2.executeQuery(queryString);
							if(rset2.next()){
								queryString="SELECT trd_cd FROM FMS7_MAN_REQ_MST WHERE man_CD='"+rset2.getString(1)+"'";
								rset=stmt.executeQuery(queryString);
								if(rset.next()){
									trd_cd=rset.getString(1)==null?"":rset.getString(1);
								}
							}
							if((exchg_rate_2_igx.equals("0") || exchg_rate_2_igx.equals("")) && (!price_unit.equals("1"))){
								TRADER_INV_CURRENCY_DRCR.add("2");
								inv_curr="2";
							}else{
								TRADER_INV_CURRENCY_DRCR.add("");
								inv_curr="";
							}
							queryString = "SELECT EXCHNG_RATE_CD FROM FMS7_TRADER_BILLING_DTL WHERE customer_cd='"+trd_cd+"' "
									+ "and  SN_NO='"+rset1.getString(18)+"' and SN_REV_NO ='"+rset1.getString(19)+"' and cont_type='K' ";
							rset3 = stmt3.executeQuery(queryString);
							if(rset3.next())
							{
								Exchg_rt_cd_DRCR.add(rset3.getString(1)==null?"":rset3.getString(1));
							}else{
								Exchg_rt_cd_DRCR.add("");
							}
							if(!trd_cd.equals(trader_cd)){
								queryString = "SELECT payment_currency FROM FMS7_TRADER_OTHER_PLANT_MST A WHERE SN_NO='"+rset1.getString(18)+"' AND "
										+ " SN_REV_NO='"+rset1.getString(19)+"' and customer_cd='"+trader_cd+"' and flag='K'";
								//System.out.println("queryString--billing sg---"+queryString);
								rset3 = stmt3.executeQuery(queryString);
								if(rset3.next())
								{
									TRADER_PAY_CURRENCY_DRCR.add(rset3.getString(1)==null?"":rset3.getString(1));
								}else{
									TRADER_PAY_CURRENCY_DRCR.add("");
								}
							}else{
								queryString = "SELECT payment_cur_cd FROM FMS7_TRADER_BILLING_DTL WHERE customer_cd='"+trader_cd+"' "
										+ "and   SN_NO='"+rset1.getString(18)+"' and SN_REV_NO ='"+rset1.getString(19)+"' and cont_type='K' ";
								//System.out.println("queryString--billing sg-"+queryString);
								rset3 = stmt3.executeQuery(queryString);
								if(rset3.next())
								{
									TRADER_PAY_CURRENCY_DRCR.add(rset3.getString(1)==null?"":rset3.getString(1));
								}else{
									TRADER_PAY_CURRENCY_DRCR.add("");
								}
							}
							EXCHG_RATE_VALUE_IGX_DRCR.add(rset1.getString(2)==null?"-":nf2.format(Double.parseDouble(rset1.getString(2))));
							INVOICE_AMT_IGX_DRCR.add(rset1.getString(3)==null?"0":nf.format(Double.parseDouble(rset1.getString(3))));
							INVOICE_TAX_AMT_IGX_DRCR.add(rset1.getString(4)==null?"0":nf.format(Double.parseDouble(rset1.getString(4))));
							String taxamt=rset1.getString(4)==null?"0":nf.format(Double.parseDouble(rset1.getString(4)));
							purchase_total_tax_inr_igx+=Double.parseDouble((taxamt));
							temp_inv_amt_igx_DRCR.add(rset1.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(3))));
							ACT_ARRV_MONTH_IGX_DRCR.add(rset1.getString(5)==null?"":rset1.getString(5));
							String inv_type="";
							queryString = "SELECT INVOICE_TYPE FROM FMS7_DOM_PUR_INV_DTL WHERE "
										+ "INVOICE_NO='"+rset1.getString(22)+"' AND PARTY_CD='"+rset1.getString(7)+"' "
										+ " AND CARGO_REF_NO='"+rset1.getString(17)+"' AND PLANT_CD='"+rset1.getString(23)+"' "
										+ "AND CONTRACT_NO='"+rset1.getString(18)+"' AND "
										+ " CONTRACT_REV_NO='"+rset1.getString(19)+"' and cargo_ref_no like '99%' ";
							//System.out.println("TRADER Master query = "+queryString);
							rset = stmt.executeQuery(queryString);
							if(rset.next())
							{			
								inv_type=rset.getString(1)==null?"":rset.getString(1);
							}
							
							if(tds_flag.equals("Y")){
								double tds_Amt=(Double.parseDouble(rset1.getString(3)==null?"0":rset1.getString(3))* Double.parseDouble(tds_perc))/100;
								INVOICE_AMT_TDS_IGX_DRCR.add(nf.format(tds_Amt));
							}else{
								INVOICE_AMT_TDS_IGX_DRCR.add("-");
							}
							if(inv_type.equals("1")){
								inv_type="Monthly";
							}else if(inv_type.equals("2")){
								inv_type="Fortnightly";
							}
							else if(inv_type.equals("3")){
								inv_type="Weekly";
							}
							ACT_ARRV_DT_IGX_DRCR.add(rset1.getString(10)==null?"":rset1.getString(10));
							String rmk=rset1.getString(8)==null?"":rset1.getString(8);
							REMARK_IGX_DRCR.add(rmk);
							
							String usd_amt=rset1.getString(14)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(14)));
							double amtadj=0;
							if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_curr.equals("2")){
								if(adj_sign.equals("+")){
									 amtadj=Double.parseDouble(usd_amt)+Double.parseDouble(adj_amt);
								}else if(adj_sign.equals("-")){
									amtadj=Double.parseDouble(usd_amt)-Double.parseDouble(adj_amt);
								}
								USD_VAL_INVOICE_IGX_DRCR.add(nf3.format(amtadj));
								
								USD_VAL_INVOICE_NUMERIC_IGX_DRCR.add(nf.format(amtadj));
								purchase_total_invoice_value_igx_DRCR += Double.parseDouble(nf.format(amtadj));
							}else{
							USD_VAL_INVOICE_IGX_DRCR.add(rset1.getString(14)==null?"-":nf3.format(Double.parseDouble(rset1.getString(14))));
							
							USD_VAL_INVOICE_NUMERIC_IGX_DRCR.add(rset1.getString(14)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(14))));
							purchase_total_invoice_value_igx_DRCR += Double.parseDouble(rset1.getString(14)==null?"0":nf.format(Double.parseDouble(rset1.getString(14))));
							}
							double tax_amt_usd=0;
							if(!rmk.equals("")){
								TAX_rmk_IGX_DRCR.add(rmk+"%");
								String temp[]=rmk.split("@");
								if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_curr.equals("2")){
									tax_amt_usd=(amtadj)* Double.parseDouble(temp[1].trim())/100;
								}else{
								 tax_amt_usd=(Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))* Double.parseDouble(temp[1].trim())/100;
								}
								
							}else{
								TAX_rmk_IGX_DRCR.add("");
							}
							TAX_amt_usd_IGX_DRCR.add(nf.format(tax_amt_usd));
							invoice_type_IGX_DRCR.add(inv_type);
							purchase_total_tax_inr_igx+=tax_amt_usd;
							usd_mmbtu=(tax_amt_usd+Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))/Double.parseDouble(rset1.getString(1));
							INVOICE_NO_IGX_DRCR.add(rset1.getString(11)==null?"":rset1.getString(11));
							INVOICE_DT_IGX_DRCR.add(rset1.getString(12)==null?"":rset1.getString(12));	
							
							CONF_PRICE_IGX_DRCR.add(rset1.getString(13)==null?"":nf2.format(Double.parseDouble(rset1.getString(13))));
							CONF_PRICE_NUMERIC_IGX_DRCR.add(rset1.getString(13)==null?"0.0000":nf2.format(Double.parseDouble(rset1.getString(13))));
							
							
							
							DUE_DT_IGX_DRCR.add(rset1.getString(15)==null?"":rset1.getString(15));
							Approve_flag_IGX_DRCR.add(rset1.getString(18)==null?"":rset1.getString(18));
							//String st_dt=rset1.getString(10)==null?"":rset1.getString(10);
							//String end_dt=rset1.getString(19)==null?"":rset1.getString(19);
							duration_igx_DRCR.add("");
							
							queryString = "SELECT TRADER_NAME,TRADER_ABBR FROM FMS7_TRADER_MST WHERE FLAG='T' AND TRADER_CD="+rset1.getString(7)+"";
							//System.out.println("TRADER Master query = "+queryString);
							rset = stmt.executeQuery(queryString);
							if(rset.next())
							{			
								TRADER_NAME_IGX_DRCR.add(rset.getString(1)==null?"":rset.getString(1));
								TRADER_ABBR_IGX_DRCR.add(rset.getString(2)==null?"":rset.getString(2));
							}
							else
							{
								TRADER_NAME_IGX_DRCR.add("");
								TRADER_ABBR_IGX_DRCR.add("");
							}
							if(inv_curr.equals("2")){
								String tcs_perc="";
								queryString="SELECT TAX_CODE,FACTOR FROM FMS7_TAX_STRUCTURE_DTL A WHERE TAX_STR_CD='22' AND APP_DATE=(SELECT MAX(B.APP_DATE) "
										+ "FROM FMS7_TAX_STRUCTURE_DTL B WHERE A.TAX_STR_CD=B.TAX_STR_CD AND B.TAX_STR_CD='22' )";
										// "B.APP_DATE<=TO_DATE('01/08/2020','DD/MM/YYYY'))";
								rset=stmt.executeQuery(queryString);
								//System.out.println("queryString---"+queryString);
								if(rset.next()){
									tcs_perc=rset.getString(2)==null?"":nf2.format(Double.parseDouble(rset.getString(2)));
								}else{
								}
								
								int fin_yr = Integer.parseInt(rset1.getString(12).substring(6));
								int fin_mon = Integer.parseInt(rset1.getString(12).substring(3,5));
								String financial_year = "";
								int inv_no = 0;
								String invoice_no = "";
								
								if(fin_mon>3) {
									financial_year = ""+fin_yr+"-"+(fin_yr+1);
								} else {
									financial_year = ""+(fin_yr-1)+"-"+fin_yr;
								}
								String tcs_flag="";
								String queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TRADER_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+rset1.getString(7)+"'";
								//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
								rset2=stmt2.executeQuery(queryString2);
								if(rset2.next())
								{
									tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
								}
								if(tcs_flag.equals("Y")){
									String net="";
									if(!adj_amt.equals("") && (!adj_amt.equals("0"))){
										net=nf6.format(amtadj);
									}else{
										net=nf6.format(Double.parseDouble(rset1.getString(14)));
									}
									double tcs_amt=(Double.parseDouble(net)+Double.parseDouble(rset1.getString(4)))* Double.parseDouble(tcs_perc)/100;
									INVOICE_TCS_AMT_DRCR.add(nf6.format(tcs_amt));
								}else{
									INVOICE_TCS_AMT_DRCR.add("0");
								}
								
							}else{
								INVOICE_TCS_AMT_DRCR.add("0");
							}
							if(!adj_amt.equals("") && (!adj_amt.equals("0"))){
								String title="";
								double ori_gross=rset1.getDouble(3);
								//System.out.println("ori_gross = "+ori_gross);
								String gross="";
								String gros="";
								if(inv_curr.equals("2")){
									gross=nf.format(rset1.getDouble(14));
								}else{
									gross=nf.format(ori_gross+Double.parseDouble(adj_amt));
								}
								if(adj_sign.equals("+")){
									title="Gross Amount ("+gross+") + Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
								}else if(adj_sign.equals("-")){
									title="Gross Amount ("+gross+") - Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
								}
								
								
								Vtitle_DRCR.add(title);
							}else{
								Vtitle_DRCR.add("");
							}
						}
			}
//			for(int i=0; i<CARGO_REF_CD_IGX.size(); i++)
//			{
				if(!all_exchg_rtdt_drcr_igx.equals("")){
					//System.out.println("all_exchg_rtdt---"+all_exchg_rtdt);
					String temp_str[]=all_exchg_rtdt_drcr_igx.split("@");
					for(int k=0;k<temp_str.length;k++){
						if(temp_str[k].equals("-")){
							//System.out.println("PAY_EXCHG_RT_DT.elementAt(k)--in if-"+PAY_EXCHG_RT_DT.elementAt(k));
							Exchg_rt_Dt_DRCR.add(PAY_EXCHG_RT_DT_DRCR.elementAt(k));
						}else{
							//System.out.println("temp_str[k]--in if-"+temp_str[k]);
							Exchg_rt_Dt_DRCR.add(temp_str[k]);
						}
					}
					
				}else{
					for(int i=0; i<CARGO_REF_CD_IGX_DRCR.size(); i++)
						{
							Exchg_rt_Dt_DRCR.add(PAY_EXCHG_RT_DT_DRCR.elementAt(i));
						}
					}
			//}
			//System.out.println("Exchg_rt_Dt---"+Exchg_rt_Dt);
				
			for(int i=0; i<CARGO_REF_CD_IGX_DRCR.size(); i++)
			{
				String refno="";
				refno=""+CARGO_REF_CD_IGX_DRCR.elementAt(i)+"-"+INVOICE_NO_IGX_DRCR.elementAt(i)+"-"+TRD_CD_IGX_DRCR.elementAt(i);
				queryString = "SELECT XML_GEN_FLAG, SUN_APPROVAL,to_char(to_date(TRANSACTION_DT,'ddmmyyyy'),'dd/mm/yyyy') FROM FMS7_ACCOUNT_APPROVED_DTL " +
				  			  "WHERE INV_CARGO_NO='"+refno+"' AND JOURNAL_TYPE='FMSPR'";
				//System.out.println("Confirmed FMS7_ACCOUNT_APPROVED_DTL Details Query = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					XML_GEN_FLAG_IGX_DRCR.add(rset.getString(1)==null?"N":rset.getString(1));
					SUN_APPROVAL_IGX_DRCR.add(rset.getString(2)==null?"N":rset.getString(2)); 
					TRANSACTION_DT_DRCR.add(rset.getString(3)==null?"":rset.getString(3));
					//Introduced By Samik Shah On 23rd August, 2011 ...
				}
				else
				{
					XML_GEN_FLAG_IGX_DRCR.add("N");
					SUN_APPROVAL_IGX_DRCR.add("N"); //Introduced By Samik Shah On 23rd August, 2011 ...
					TRANSACTION_DT_DRCR.add(INVOICE_DT_IGX_DRCR.elementAt(i));
				}
				double sbi_tt_selling_exchg_rate_igx = 0;
				double foreign_exchg_rate_igx = 0;
				String exchg_rate_cd_igx = "2"; //For SBI TT Selling Exchange Rate ...
				String exchg_rate_cd_2_igx = "5"; //For Group Foreign Exchange Rate ...
				String exchg_rate_igx = "";
				
				
				String arr_dt_igx = ""+ACT_ARRV_DT_IGX_DRCR.elementAt(i);
				String arrival_date_and_rate_note_igx = "";
				
				if(arr_dt_igx.trim().equals(""))
				{
					arrival_date_and_rate_note_igx += "\nActual Arrival Date of Cargo is missing."; 
				}
				
				String queryString2 = "SELECT EXCHG_VAL FROM FMS7_EXCHG_RATE_ENTRY WHERE EXCHG_RATE_CD='"+exchg_rate_cd_igx+"' AND EFF_DT=TO_DATE('"+ACT_ARRV_DT_IGX_DRCR.elementAt(i)+"','DD/MM/YYYY')";
				//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
				rset2=stmt2.executeQuery(queryString2);
				if(rset2.next())
				{
					exchg_rate_igx = rset2.getString(1)==null?"0":rset2.getString(1);
					if(!exchg_rate_igx.equals("")){
						sbi_tt_selling_exchg_rate_igx = Double.parseDouble(exchg_rate_igx);
					}
				}
				String gross_amt_inr="";
				int fin_yr = Integer.parseInt(INVOICE_DT_IGX_DRCR.elementAt(i).toString().substring(6));
				int fin_mon = Integer.parseInt(INVOICE_DT_IGX_DRCR.elementAt(i).toString().substring(3,5));
				String financial_year = "";
				int inv_no = 0;
				String invoice_no = "";
				
				if(fin_mon>3) {
					financial_year = ""+fin_yr+"-"+(fin_yr+1);
				} else {
					financial_year = ""+(fin_yr-1)+"-"+fin_yr;
				}
				String tcs_flag="";
				String tcs_perc="";
				
				queryString="SELECT TAX_CODE,FACTOR FROM FMS7_TAX_STRUCTURE_DTL A WHERE TAX_STR_CD='22' AND APP_DATE=(SELECT MAX(B.APP_DATE) "
						+ "FROM FMS7_TAX_STRUCTURE_DTL B WHERE A.TAX_STR_CD=B.TAX_STR_CD AND B.TAX_STR_CD='22' )";
						// "B.APP_DATE<=TO_DATE('01/08/2020','DD/MM/YYYY'))";
				rset=stmt.executeQuery(queryString);
				//System.out.println("queryString---"+queryString);
				if(rset.next()){
					TCS_PERC_IGX_DRCR.add(rset.getString(2)==null?"":nf2.format(Double.parseDouble(rset.getString(2))));
					tcs_perc=rset.getString(2)==null?"":nf2.format(Double.parseDouble(rset.getString(2)));
				}else{
					TCS_PERC_IGX_DRCR.add("");
				}
				TDS_PERC_IGX_DRCR.add("0.1");
				
				queryString2 = "SELECT EXCHG_VAL FROM FMS7_EXCHG_RATE_ENTRY WHERE EXCHG_RATE_CD='"+Exchg_rt_cd_DRCR.elementAt(i)+"' AND EFF_DT=TO_DATE('"+Exchg_rt_Dt_DRCR.elementAt(i)+"','DD/MM/YYYY')";
				//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
				rset1=stmt1.executeQuery(queryString2);
				if(rset1.next())
				{
					exchgrt=rset1.getString(1)==null?"":rset1.getString(1);
					if(!exchgrt.equals("")){
						//exchg_rt_val_DRCR.add(nf6.format(Double.parseDouble(exchgrt)));
						exchg_rt_val_DRCR.add(String.format("%.2f", Double.parseDouble(exchgrt)));
					}else{
						exchg_rt_val_DRCR.add(exchgrt);
					}

					if(TRADER_INV_CURRENCY_DRCR.elementAt(i).equals("2") && TRADER_PAY_CURRENCY_DRCR.elementAt(i).equals("1")){
						//System.out.println("Finding Out EXCHG_RATE ====in this11======= "+i);
						queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TRADER_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+TRD_CD_IGX_DRCR.elementAt(i)+"'";
						//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
						rset2=stmt2.executeQuery(queryString2);
						if(rset2.next())
						{
							TCS_APP_FLAG_DRCR.add(rset2.getString(1)==null?"":rset2.getString(1));
							tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
							TDS_APP_FLAG_DRCR.add("");
						}else{
							TCS_APP_FLAG_DRCR.add("");
							TDS_APP_FLAG_DRCR.add("Y");
						}
						gross_amt_inr=nf.format(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX_DRCR.elementAt(i))*Double.parseDouble(exchgrt));
						String rmk[]=REMARK_IGX_DRCR.elementAt(i).toString().split("@");
						String tax_per=rmk[1].trim();
						String tax_amt=nf.format((Double.parseDouble(gross_amt_inr)* Double.parseDouble(tax_per))/100);
//						System.out.println("gross_amt_inr--"+gross_amt_inr);
//						System.out.println("tax_amt--"+tax_amt);
						double inv_amt_inr=Double.parseDouble(gross_amt_inr)+Double.parseDouble(tax_amt);
//						System.out.println("inv_amt_inr--"+inv_amt_inr);
						Gross_inr_IGX_DRCR.add(gross_amt_inr);
						Tax_inr_IGX_DRCR.add(tax_amt);
						net_inr_IGX_DRCR.add(nf.format(inv_amt_inr));
						if(tcs_flag.equals("Y")){
							double tcs_amt=(inv_amt_inr * Double.parseDouble(tcs_perc))/100;
							TCS_TDS_AMT_IGX_DRCR.add(nf.format(tcs_amt));
							payable_inr_IGX_DRCR.add(nf.format(inv_amt_inr+tcs_amt));
						}else{
							double tcs_amt=(Double.parseDouble(gross_amt_inr) * 0.1)/100;
							TCS_TDS_AMT_IGX_DRCR.add(nf.format(tcs_amt));
							payable_inr_IGX_DRCR.add(nf.format(inv_amt_inr-tcs_amt));
						}
					}else{
						//System.out.println("Finding Out EXCHG_RATE ====in this11=else====== ");
						Gross_inr_IGX_DRCR.add("-");
						Tax_inr_IGX_DRCR.add("-");
						net_inr_IGX_DRCR.add("-");
						payable_inr_IGX_DRCR.add("-");
						TCS_TDS_AMT_IGX_DRCR.add("-");
						TCS_APP_FLAG_DRCR.add("");
						TDS_APP_FLAG_DRCR.add("Y");
						//exchg_rt_val_DRCR.add("");
					}
				}else{
					//System.out.println("in thissss---"+i);
					exchgrt="";
					if(TRADER_INV_CURRENCY_DRCR.elementAt(i).equals("2") && TRADER_PAY_CURRENCY_DRCR.elementAt(i).equals("1")){
						if(!Exchg_rt_Dt_DRCR.elementAt(i).equals("")){
							exchg_rt_val_DRCR.add("NA");
						}else{
							exchg_rt_val_DRCR.add("");
						}
					}else{
						exchg_rt_val_DRCR.add("");
					}
					if(TRADER_INV_CURRENCY_DRCR.elementAt(i).equals("2") && TRADER_PAY_CURRENCY_DRCR.elementAt(i).equals("1")){
						//System.out.println("in thissss11---"+i);
						queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TRADER_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+TRD_CD_IGX_DRCR.elementAt(i)+"'";
						//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
						rset2=stmt2.executeQuery(queryString2);
						if(rset2.next())
						{
							TCS_APP_FLAG_DRCR.add(rset2.getString(1)==null?"":rset2.getString(1));
							tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
							TDS_APP_FLAG_DRCR.add("");
						}else{
							TCS_APP_FLAG_DRCR.add("");
							TDS_APP_FLAG_DRCR.add("Y");
						}
						if(!exchgrt.equals("")){
							//System.out.println("Finding Out EXCHG_RATE ====in this11=22====== ");
							gross_amt_inr=nf.format(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX_DRCR.elementAt(i))*Double.parseDouble(exchgrt));
							String rmk[]=REMARK_IGX_DRCR.elementAt(i).toString().split("@");
							String tax_per=rmk[1].trim();
							String tax_amt=nf.format((Double.parseDouble(gross_amt_inr)* Double.parseDouble(tax_per))/100);
							double inv_amt_inr=Double.parseDouble(gross_amt_inr)+Double.parseDouble(tax_amt);
							Gross_inr_IGX_DRCR.add(gross_amt_inr);
							Tax_inr_IGX_DRCR.add(tax_amt);
							net_inr_IGX_DRCR.add(nf.format(inv_amt_inr));
							if(tcs_flag.equals("Y")){
								double tcs_amt=(Double.parseDouble(gross_amt_inr) * Double.parseDouble(tcs_perc))/100;
								TCS_TDS_AMT_IGX_DRCR.add(nf.format(tcs_amt));
								payable_inr_IGX_DRCR.add(nf.format(inv_amt_inr+tcs_amt));
							}else{
								double tcs_amt=(Double.parseDouble(gross_amt_inr) * 0.1)/100;
								TCS_TDS_AMT_IGX_DRCR.add(nf.format(tcs_amt));
								payable_inr_IGX_DRCR.add(nf.format(inv_amt_inr-tcs_amt));
							}
						}else{
							TCS_TDS_AMT_IGX_DRCR.add("");
							//TCS_TDS_AMT_IGX.add("");
							Gross_inr_IGX_DRCR.add("-");
							Tax_inr_IGX_DRCR.add("-");
							net_inr_IGX_DRCR.add("-");
							payable_inr_IGX_DRCR.add("-");
							TCS_APP_FLAG_DRCR.add("");
							TDS_APP_FLAG_DRCR.add("Y");
						}
					}
					
					else if(TRADER_INV_CURRENCY_DRCR.elementAt(i).equals("2") && TRADER_PAY_CURRENCY_DRCR.elementAt(i).equals("2")){
//						System.out.println("in thissss-22--"+i);
						//System.out.println("Finding Out EXCHG_RATE ====in this33======= ");
						queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+TRD_CD_IGX_DRCR.elementAt(i)+"'";
						//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
						rset2=stmt2.executeQuery(queryString2);
						if(rset2.next())
						{
							TCS_APP_FLAG_DRCR.add(rset2.getString(1)==null?"":rset2.getString(1));
							tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
							TDS_APP_FLAG_DRCR.add("");
						}else{
							TCS_APP_FLAG_DRCR.add("");
							TDS_APP_FLAG_DRCR.add("Y");
						}
						double tcs_amt=0;
						if(tcs_flag.equals("Y")){
							tcs_amt=(Double.parseDouble(USD_VAL_INVOICE_NUMERIC_IGX_DRCR.elementAt(i)+"") * Double.parseDouble(tcs_perc))/100;
							TCS_TDS_AMT_IGX.add(tcs_amt);
						}else{
							tcs_amt=(Double.parseDouble(USD_VAL_INVOICE_NUMERIC_IGX_DRCR.elementAt(i)+"") * 0.1)/100;
							TCS_TDS_AMT_IGX_DRCR.add(tcs_amt);
						}
						Gross_inr_IGX_DRCR.add(USD_VAL_INVOICE_NUMERIC_IGX_DRCR.elementAt(i));
						Tax_inr_IGX_DRCR.add(TAX_amt_usd_IGX_DRCR.elementAt(i));
						net_inr_IGX_DRCR.add(nf.format(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX_DRCR.elementAt(i))+Double.parseDouble(""+TAX_amt_usd_IGX_DRCR.elementAt(i))));
						double totamt=0;
						if(tcs_flag.equals("Y")){
							totamt=Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX_DRCR.elementAt(i))+Double.parseDouble(""+TAX_amt_usd_IGX_DRCR.elementAt(i))+tcs_amt;
						}else{
							totamt=Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX_DRCR.elementAt(i))+Double.parseDouble(""+TAX_amt_usd_IGX_DRCR.elementAt(i))-+tcs_amt;
						}
						
						payable_inr_IGX_DRCR.add(nf6.format(totamt));
					}else{
//						System.out.println("Fhiselse 333======="+TCS_TDS_AMT_IGX.size());
//						System.out.println("in thissss-else-"+i);
						TCS_APP_FLAG_DRCR.add("");
						TDS_APP_FLAG_DRCR.add("Y");
						double net_amt=Double.parseDouble(INVOICE_AMT_IGX_DRCR.elementAt(i)+"");
						String tds_amt=nf6.format((net_amt * 0.1)/100);
						double totamt=(Double.parseDouble(INVOICE_TAX_AMT_IGX_DRCR.elementAt(i)+"")+ net_amt)-Double.parseDouble(tds_amt);
						TCS_TDS_AMT_IGX_DRCR.add(tds_amt);
						//TCS_TDS_AMT_IGX.add("");
						Gross_inr_IGX_DRCR.add(INVOICE_AMT_IGX_DRCR.elementAt(i)+"");
						Tax_inr_IGX_DRCR.add(INVOICE_TAX_AMT_IGX_DRCR.elementAt(i)+"");
						net_inr_IGX_DRCR.add(nf.format(Double.parseDouble(INVOICE_AMT_IGX_DRCR.elementAt(i)+"")+Double.parseDouble(INVOICE_TAX_AMT_IGX_DRCR.elementAt(i)+"")));
						/*if(TRADER_INV_CURRENCY.elementAt(i).equals("") && TRADER_PAY_CURRENCY.elementAt(i).equals("")){
							payable_inr_IGX.add("");
						}else{*/
							payable_inr_IGX_DRCR.add(nf6.format(totamt));
						//}
					}
				}
				
				
				
				if(!exchgrt.equals("") && (!ind.equals(""))){}else{}
				
				FINAL_SELLER_INV_AMT_INR_IGX_DRCR.add(nf3.format(Double.parseDouble(""+temp_inv_amt_igx_DRCR.elementAt(i))));
				purchase_total_inv_value_inr_igx_DRCR += (Double.parseDouble(""+temp_inv_amt_igx_DRCR.elementAt(i)));
				if(!INVOICE_AMT_TDS_IGX_DRCR.elementAt(i).equals("-")){
					purchase_total_cost_inr_igx_DRCR += ((Double.parseDouble(""+INVOICE_AMT_IGX_DRCR.elementAt(i))+Double.parseDouble(""+INVOICE_TAX_AMT_IGX_DRCR.elementAt(i)))-Double.parseDouble(""+INVOICE_AMT_TDS_IGX_DRCR.elementAt(i)));
				}else{
					purchase_total_cost_inr_igx_DRCR += (Double.parseDouble(""+INVOICE_AMT_IGX_DRCR.elementAt(i))+Double.parseDouble(""+INVOICE_TAX_AMT_IGX_DRCR.elementAt(i)));
				}
				
			}
			
			for(int i=0; i<CARGO_REF_CD_IGX_DRCR.size(); i++)
			{										
				if(!(""+FINAL_SELLER_INV_AMT_INR_IGX_DRCR.elementAt(i)).equals("-") && temp_inv_amt_igx_DRCR.elementAt(i)!=null && !temp_inv_amt_igx_DRCR.elementAt(i).equals("") && !temp_inv_amt_igx_DRCR.elementAt(i).equals("0") )
				{
					double total_amt_igx = Double.parseDouble(""+INVOICE_AMT_IGX_DRCR.elementAt(i));
					double total_tax_amt_igx = Double.parseDouble(""+INVOICE_TAX_AMT_IGX_DRCR.elementAt(i));
					double tdsamt =0;
					if(!(""+ACTUAL_UNLOADED_QTY_IGX_DRCR.elementAt(i)).equals("-"))
					{
						if(TRADER_INV_CURRENCY_DRCR.elementAt(i).equals("2")){
							if(TCS_APP_FLAG_DRCR.elementAt(i).equals("Y")){
								tdsamt=(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX_DRCR.elementAt(i))*Double.parseDouble(""+TCS_PERC_IGX_DRCR.elementAt(i)))/100;
							}
						}else{
							//tdsamt = Double.parseDouble(""+INVOICE_AMT_TDS_IGX.elementAt(i));
							tdsamt = 0;
						}
					}else{
						tdsamt = 0;
					}
					if(TRADER_INV_CURRENCY_DRCR.elementAt(i).equals("2")){
						if(TCS_APP_FLAG_DRCR.elementAt(i).equals("Y")){
							CD_PAID_IGX_DRCR.add(nf3.format(total_amt_igx+total_tax_amt_igx+Double.parseDouble(""+INVOICE_TCS_AMT_DRCR.elementAt(i))));
							CD_PAID_NUMERIC_IGX_DRCR.add(nf.format(total_amt_igx+total_tax_amt_igx+Double.parseDouble(""+INVOICE_TCS_AMT_DRCR.elementAt(i))));
						}else{
							CD_PAID_IGX_DRCR.add(nf3.format(total_amt_igx+total_tax_amt_igx-tdsamt));
							CD_PAID_NUMERIC_IGX_DRCR.add(nf.format(total_amt_igx+total_tax_amt_igx-tdsamt));
						}
					}else{
						CD_PAID_IGX_DRCR.add(nf3.format(total_amt_igx+total_tax_amt_igx-tdsamt));
						CD_PAID_NUMERIC_IGX_DRCR.add(nf.format(total_amt_igx+total_tax_amt_igx-tdsamt));
					}
				}
				else
				{
					//CD_PAID_IGX.add(nf3.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
					double total_tax_amt_igx = Double.parseDouble(""+INVOICE_TAX_AMT_IGX_DRCR.elementAt(i));
					double total_amt_igx = Double.parseDouble(""+INVOICE_AMT_IGX_DRCR.elementAt(i));
					double tdsamt =0;
					if(!(""+ACTUAL_UNLOADED_QTY_IGX_DRCR.elementAt(i)).equals("-"))
					{
						if(TRADER_INV_CURRENCY_DRCR.elementAt(i).equals("2")){
							if(TCS_APP_FLAG_DRCR.elementAt(i).equals("Y")){
								tdsamt=(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX_DRCR.elementAt(i))*Double.parseDouble(""+TCS_PERC_IGX_DRCR.elementAt(i)))/100;
							}
						}else{
							//tdsamt = Double.parseDouble(""+INVOICE_AMT_TDS_IGX.elementAt(i));
							tdsamt = 0;
						}
					}else{
						tdsamt = 0;
					}
					if(TRADER_INV_CURRENCY_DRCR.elementAt(i).equals("2")){
						if(TCS_APP_FLAG_DRCR.elementAt(i).equals("Y")){
							CD_PAID_IGX_DRCR.add(nf3.format(total_amt_igx+total_tax_amt_igx+Double.parseDouble(""+INVOICE_TCS_AMT_DRCR.elementAt(i))));
							CD_PAID_NUMERIC_IGX_DRCR.add(nf.format(total_amt_igx+total_tax_amt_igx+Double.parseDouble(""+INVOICE_TCS_AMT_DRCR.elementAt(i))));
						}else{
							CD_PAID_IGX_DRCR.add(nf3.format(total_amt_igx+total_tax_amt_igx-tdsamt));
							CD_PAID_NUMERIC_IGX_DRCR.add(nf.format(total_amt_igx+total_tax_amt_igx-tdsamt));
						}
					}else{
					CD_PAID_IGX_DRCR.add(nf3.format(total_amt_igx+total_tax_amt_igx-tdsamt));
					CD_PAID_NUMERIC_IGX_DRCR.add(nf.format(total_amt_igx+total_tax_amt_igx-tdsamt));
					}
				}
				
			}																						
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void get_Purchase_Summary_IGX_Approval()
	{
		if(!ACT_ARRV_MONTH_IGX.isEmpty())
		{
			//CARGO_REF_CD
			
			String chk1_igx = "";
			String chk1_tax_igx="";
			String chk1_tds_igx="";
			String chk1_taxusd_igx="";
			String chk2_igx = "";
			String chk3_igx = "";
			String chk4_igx = "";
			String chk5_igx = "";
			String chk6_igx = "";
			String chk7_igx = "";
			String chk8_igx = "";
			String chk9_igx = "";
			
			String arrv_month_igx = ACT_ARRV_MONTH_IGX.elementAt(0).toString().trim();
			double actual_unloaded_qty_igx = 0;
			double inv_taxamt = 0;
			double inv_tdsamt = 0;
			double inv_taxamt_usd=0;
			String Scargo_ref_cd_igx = "";
			
			double usd_val_invoice_igx = 0;
			
			double final_seller_inv_amt_inr_igx = 0;
			double tot_cd_amt_igx = 0;
			double tot_paid_refund_igx = 0;
			double cd_paid_igx = 0;
			double custom_duty_usd_igx = 0;
			double addl_custom_duty_usd_igx = 0;
			double cost_of_purchase_usd_igx = 0;
			//COST_OF_PURCHASE_USD
			
			for(int i = 0; i<ACT_ARRV_MONTH_IGX.size(); i++)
			{
				//System.out.println(ACT_ARRV_MONTH_IGX.elementAt(i).toString().trim()+"---arrv_month_igx--"+arrv_month_igx);
				if(arrv_month_igx.equals(ACT_ARRV_MONTH_IGX.elementAt(i).toString().trim()))
				{
					//System.out.println(ACT_ARRV_MONTH.elementAt(i).toString().trim());
					Scargo_ref_cd_igx += CARGO_REF_CD_IGX.elementAt(i).toString().trim()+", ";
					
					if(!ACTUAL_UNLOADED_QTY_IGX.elementAt(i).toString().trim().equals("-"))
					{
						actual_unloaded_qty_igx += Double.parseDouble(ACTUAL_UNLOADED_QTY_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_igx += "y";
						
					}
					else
					{
						actual_unloaded_qty_igx += 0; 
						chk1_igx += "-";
					}
					if(!INVOICE_TAX_AMT_IGX.elementAt(i).toString().trim().equals("-"))
					{
						//System.out.println("-INVOICE_TAX_AMT_IGX.elementAt(i)-"+INVOICE_TAX_AMT_IGX.elementAt(i));
						inv_taxamt += Double.parseDouble(INVOICE_TAX_AMT_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_tax_igx += "y";
						
					}
					else
					{
						inv_taxamt += 0; 
						chk1_tax_igx += "-";
					}
					if(!INVOICE_AMT_TDS_IGX.elementAt(i).toString().trim().equals("-"))
					{
						//System.out.println("-INVOICE_TAX_AMT_IGX.elementAt(i)-"+INVOICE_TAX_AMT_IGX.elementAt(i));
						inv_tdsamt += Double.parseDouble(INVOICE_AMT_TDS_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_tds_igx += "y";
						
					}
					else
					{
						inv_tdsamt += 0; 
						chk1_tds_igx += "-";
					}
					if(!TAX_amt_usd_IGX.elementAt(i).toString().trim().equals("-"))
					{
						//System.out.println("-INVOICE_TAX_AMT_IGX.elementAt(i)-"+INVOICE_TAX_AMT_IGX.elementAt(i));
						inv_taxamt_usd += Double.parseDouble(TAX_amt_usd_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_taxusd_igx += "y";
						
					}
					else
					{
						inv_taxamt_usd += 0; 
						chk1_taxusd_igx += "-";
					}
					
					if(!USD_VAL_INVOICE_IGX.elementAt(i).toString().trim().equals("-"))
					{
						usd_val_invoice_igx += Double.parseDouble(USD_VAL_INVOICE_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk2_igx += "y";
					}
					else
					{
						usd_val_invoice_igx += 0; chk2_igx += "-";
					}
					
					if(!FINAL_SELLER_INV_AMT_INR_IGX.elementAt(i).toString().trim().equals("-"))
					{
						final_seller_inv_amt_inr_igx += Double.parseDouble(FINAL_SELLER_INV_AMT_INR_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk3_igx += "y";
					}
					else
					{
						final_seller_inv_amt_inr_igx += 0; chk3_igx += "-";
					}
					
					if(!TOTAL_CD_AMT_IGX.elementAt(i).toString().trim().equals("-"))
					{
						tot_cd_amt_igx += Double.parseDouble(TOTAL_CD_AMT_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk4_igx += "y";
					}
					else
					{
						tot_cd_amt_igx += 0; chk4_igx += "-";
					}
					
					if(!TOTAL_PAID_REFUND_IGX.elementAt(i).toString().trim().equals("-"))
					{
						tot_paid_refund_igx += Double.parseDouble(TOTAL_PAID_REFUND_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk5_igx += "y";
					}
					else
					{
						tot_paid_refund_igx += 0; chk5_igx += "-";
					}
					
					if(!CD_PAID_IGX.elementAt(i).toString().trim().equals("-"))
					{
						cd_paid_igx += Double.parseDouble(CD_PAID_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk6_igx += "y";
					}
					else
					{
						cd_paid_igx += 0; chk6_igx += "-";
					}
					
					if(!CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						custom_duty_usd_igx += Double.parseDouble(CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk7_igx += "y";
					}
					else
					{
						custom_duty_usd_igx += 0; chk7_igx += "-";
					}
					
					if(!ADDL_CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						addl_custom_duty_usd_igx += Double.parseDouble(ADDL_CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk8_igx += "y";
					}
					else
					{
						addl_custom_duty_usd_igx += 0; chk8_igx += "-";
					}
					
					if(!COST_OF_PURCHASE_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						cost_of_purchase_usd_igx += Double.parseDouble(COST_OF_PURCHASE_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk9_igx += "y";
					}
					else
					{
						cost_of_purchase_usd_igx += 0; chk9_igx += "-";
					}
					//
					
				}
				else
				{
					
					SUMMRY_ACT_ARRV_MONTH_IGX.add(""+arrv_month_igx);
					if(chk1_igx.contains("y"))
					{
						SUMMRY_ACTUAL_UNLOADED_QTY_IGX.add(nf3.format(actual_unloaded_qty_igx));
					}
					else
					{
						SUMMRY_ACTUAL_UNLOADED_QTY_IGX.add("-");
					}
					
					if(chk1_tax_igx.contains("y"))
					{
						SUMMRY_TAX_AMT_INR_IGX.add(nf3.format(inv_taxamt));
					}
					else
					{
						SUMMRY_TAX_AMT_INR_IGX.add("-");
					}
					if(chk1_tds_igx.contains("y"))
					{
						SUMMRY_TDS_AMT_INR_IGX.add(nf3.format(inv_tdsamt));
					}
					else
					{
						SUMMRY_TDS_AMT_INR_IGX.add("-");
					}
					if(chk1_taxusd_igx.contains("y"))
					{
						SUMMRY_TAX_AMT_USD_IGX.add(nf3.format(inv_taxamt_usd));
					}
					else
					{
						SUMMRY_TAX_AMT_USD_IGX.add("-");
					}
					
					SUMMRY_CARGO_REF_CD_IGX.add(Scargo_ref_cd_igx);
					if(chk2_igx.contains("y"))
					{
						SUMMRY_USD_VAL_INVOICE_IGX.add(nf3.format(usd_val_invoice_igx));
					}
					else
					{
						SUMMRY_USD_VAL_INVOICE_IGX.add("-");
					}
					if(chk3_igx.contains("y"))
					{
						SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX.add(nf3.format(final_seller_inv_amt_inr_igx));
					}
					else
					{
						SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX.add("-");
					}
					if(chk4_igx.contains("y"))
					{
						SUMMRY_TOTAL_CD_AMT_IGX.add(nf3.format(tot_cd_amt_igx));
					}
					else
					{
						SUMMRY_TOTAL_CD_AMT_IGX.add("-");
					}
					if(chk5_igx.contains("y"))
					{
						SUMMRY_TOTAL_PAID_REFUND_IGX.add(nf3.format(tot_paid_refund_igx));
					}
					else
					{
						SUMMRY_TOTAL_PAID_REFUND_IGX.add("-");
					}
					if(chk6_igx.contains("y"))
					{
						SUMMRY_CD_PAID_IGX.add(nf3.format(cd_paid_igx));
					}
					else
					{
						SUMMRY_CD_PAID_IGX.add("-");
					}
					if(actual_unloaded_qty_igx != 0)
					{
						SUMMRY_INR_PER_MMBTU_IGX.add(nf2.format(cd_paid_igx/actual_unloaded_qty_igx));
					}
					else
					{
						SUMMRY_INR_PER_MMBTU_IGX.add("-"); 
					}
					
					if(chk7_igx.contains("y"))
					{
						SUMMRY_CUSTOM_DUTY_USD_IGX.add(nf3.format(custom_duty_usd_igx));
					}
					else
					{
						SUMMRY_CUSTOM_DUTY_USD_IGX.add("-");
					}
					if(chk8_igx.contains("y"))
					{
						SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX.add(nf3.format(addl_custom_duty_usd_igx));
					}
					else
					{
						SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX.add("-");
					}
					if(chk9_igx.contains("y"))
					{
						SUMMRY_COST_OF_PURCHASE_USD_IGX.add(nf3.format(cost_of_purchase_usd_igx));
					}
					else
					{
						SUMMRY_COST_OF_PURCHASE_USD_IGX.add("-");
					}
					if(actual_unloaded_qty_igx != 0)
					{
//						System.out.println("cost_of_purchase_usd_igx---"+cost_of_purchase_usd_igx);
//						System.out.println("actual_unloaded_qty_igx---"+actual_unloaded_qty_igx);
						//SUMMRY_USD_PER_MMBTU_IGX.add(nf.format((cost_of_purchase_usd_igx/actual_unloaded_qty_igx)));
						SUMMRY_USD_PER_MMBTU_IGX.add("");
					}
					else
					{
						SUMMRY_USD_PER_MMBTU_IGX.add("-"); 
					}
					
					Scargo_ref_cd_igx = "";
					
					Scargo_ref_cd_igx = CARGO_REF_CD_IGX.elementAt(i).toString().trim()+", ";
					if(!ACTUAL_UNLOADED_QTY_IGX.elementAt(i).toString().trim().equals("-"))
					{
						actual_unloaded_qty_igx = Double.parseDouble(ACTUAL_UNLOADED_QTY_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_igx += "y";
					}
					else
					{
						actual_unloaded_qty_igx = 0; chk1_igx = "-";
						
					}
					if(!INVOICE_TAX_AMT_IGX.elementAt(i).toString().trim().equals("-"))
					{
						inv_taxamt = Double.parseDouble(INVOICE_TAX_AMT_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_tax_igx += "y";
					}
					else
					{
						inv_taxamt = 0; chk1_tax_igx = "-";
						
					}
					if(!INVOICE_AMT_TDS_IGX.elementAt(i).toString().trim().equals("-"))
					{
						inv_tdsamt = Double.parseDouble(INVOICE_AMT_TDS_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_tds_igx += "y";
					}
					else
					{
						inv_tdsamt = 0; chk1_tds_igx = "-";
						
					}
					if(!TAX_amt_usd_IGX.elementAt(i).toString().trim().equals("-"))
					{
						inv_taxamt_usd = Double.parseDouble(TAX_amt_usd_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk1_taxusd_igx += "y";
					}
					else
					{
						inv_taxamt_usd = 0; chk1_taxusd_igx = "-";
						
					}
					
					if(!USD_VAL_INVOICE_IGX.elementAt(i).toString().trim().equals("-"))
					{
						usd_val_invoice_igx = Double.parseDouble(USD_VAL_INVOICE_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk2_igx += "y";
					}
					else
					{
						usd_val_invoice_igx = 0; chk2_igx = "-";
					}
					
					if(!FINAL_SELLER_INV_AMT_INR_IGX.elementAt(i).toString().trim().equals("-"))
					{
						final_seller_inv_amt_inr_igx = Double.parseDouble(FINAL_SELLER_INV_AMT_INR_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk3_igx += "y";
					}
					else
					{
						final_seller_inv_amt_inr_igx = 0; chk3_igx = "-";
					}
					
					if(!TOTAL_CD_AMT_IGX.elementAt(i).toString().trim().equals("-"))
					{
						tot_cd_amt_igx = Double.parseDouble(TOTAL_CD_AMT_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk4_igx += "y";
					}
					else
					{
						tot_cd_amt_igx = 0; chk4_igx = "-";
					}
					
					if(!TOTAL_PAID_REFUND_IGX.elementAt(i).toString().trim().equals("-"))
					{
						tot_paid_refund_igx = Double.parseDouble(TOTAL_PAID_REFUND_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk5_igx += "y";
					}
					else
					{
						tot_paid_refund_igx = 0; chk5_igx = "-";
					}
					
					if(!CD_PAID_IGX.elementAt(i).toString().trim().equals("-"))
					{
						cd_paid_igx = Double.parseDouble(CD_PAID_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk6_igx += "y";
					}
					else
					{
						cd_paid_igx = 0; chk6_igx = "-";
					}
					
					if(!CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						custom_duty_usd_igx = Double.parseDouble(CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk7_igx += "y";
					}
					else
					{
						custom_duty_usd_igx = 0; chk7_igx = "-";
					}
					
					if(!ADDL_CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						addl_custom_duty_usd_igx = Double.parseDouble(ADDL_CUSTOM_DUTY_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk8_igx += "y";
					}
					else
					{
						addl_custom_duty_usd_igx = 0; chk8_igx = "-";
					}
					
					if(!COST_OF_PURCHASE_USD_IGX.elementAt(i).toString().trim().equals("-"))
					{
						cost_of_purchase_usd_igx = Double.parseDouble(COST_OF_PURCHASE_USD_IGX.elementAt(i).toString().trim().replaceAll(",","").trim());
						chk9_igx += "y";
					}
					else
					{
						cost_of_purchase_usd_igx = 0;
						chk9_igx = "-";
					}
					
				}
				arrv_month_igx = ACT_ARRV_MONTH_IGX.elementAt(i).toString().trim();
			}
			SUMMRY_ACT_ARRV_MONTH_IGX.add(""+arrv_month_igx);
			//SUMMRY_ACTUAL_UNLOADED_QTY.add(nf3.format(actual_unloaded_qty));
			SUMMRY_CARGO_REF_CD_IGX.add(Scargo_ref_cd_igx);
			
			/*SUMMRY_USD_VAL_INVOICE.add(nf3.format(usd_val_invoice));
			
			SUMMRY_FINAL_SELLER_INV_AMT_INR.add(nf3.format(final_seller_inv_amt_inr));
			SUMMRY_TOTAL_CD_AMT.add(nf3.format(tot_cd_amt));
			SUMMRY_TOTAL_PAID_REFUND.add(nf3.format(tot_paid_refund));
			SUMMRY_CD_PAID.add(nf3.format(cd_paid));*/
			SUMMRY_INR_PER_MMBTU_IGX.add(nf2.format((cd_paid_igx/actual_unloaded_qty_igx)));
			
			/*SUMMRY_CUSTOM_DUTY_USD.add(nf3.format(custom_duty_usd));
			SUMMRY_ADDL_CUSTOM_DUTY_USD.add(nf3.format(custom_duty_usd));
			SUMMRY_COST_OF_PURCHASE_USD.add(nf3.format(cost_of_purchase_usd));*/
			SUMMRY_USD_PER_MMBTU_IGX.add(nf2.format((cost_of_purchase_usd_igx/actual_unloaded_qty_igx)));
			
			if(chk1_igx.contains("y"))
			{
				SUMMRY_ACTUAL_UNLOADED_QTY_IGX.add(nf3.format(actual_unloaded_qty_igx));
			}
			else
			{
				SUMMRY_ACTUAL_UNLOADED_QTY_IGX.add("-");
			}
			if(chk1_tax_igx.contains("y"))
			{
				SUMMRY_TAX_AMT_INR_IGX.add(nf3.format(inv_taxamt));
			}
			else
			{
				SUMMRY_TAX_AMT_INR_IGX.add("-");
			}
			if(chk1_tds_igx.contains("y"))
			{
				SUMMRY_TDS_AMT_INR_IGX.add(nf3.format(inv_tdsamt));
			}
			else
			{
				SUMMRY_TDS_AMT_INR_IGX.add("-");
			}
			if(chk1_taxusd_igx.contains("y"))
			{
				SUMMRY_TAX_AMT_USD_IGX.add(nf3.format(inv_taxamt_usd));
			}
			else
			{
				SUMMRY_TAX_AMT_USD_IGX.add("-");
			}
			SUMMRY_CARGO_REF_CD_IGX.add(Scargo_ref_cd_igx);
			if(chk2_igx.contains("y"))
			{
				SUMMRY_USD_VAL_INVOICE_IGX.add(nf3.format(usd_val_invoice_igx));
			}
			else
			{
				SUMMRY_USD_VAL_INVOICE_IGX.add("-");
			}
			if(chk3_igx.contains("y"))
			{
				SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX.add(nf3.format(final_seller_inv_amt_inr_igx));
			}
			else
			{
				SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX.add("-");
			}
			if(chk4_igx.contains("y"))
			{
				SUMMRY_TOTAL_CD_AMT_IGX.add(nf3.format(tot_cd_amt_igx));
			}
			else
			{
				SUMMRY_TOTAL_CD_AMT_IGX.add("-");
			}
			if(chk5_igx.contains("y"))
			{
				SUMMRY_TOTAL_PAID_REFUND_IGX.add(nf3.format(tot_paid_refund_igx));
			}
			else
			{
				SUMMRY_TOTAL_PAID_REFUND_IGX.add("-");
			}
			if(chk6_igx.contains("y"))
			{
				SUMMRY_CD_PAID_IGX.add(nf3.format(cd_paid_igx));
			}
			else
			{
				SUMMRY_CD_PAID_IGX.add("-");
			}
			if(actual_unloaded_qty_igx != 0)
			{
				SUMMRY_INR_PER_MMBTU_IGX.add(nf2.format(cd_paid_igx/actual_unloaded_qty_igx));
			}
			else
			{
				SUMMRY_INR_PER_MMBTU_IGX.add("-"); 
			}
			
			if(chk7_igx.contains("y"))
			{
				SUMMRY_CUSTOM_DUTY_USD_IGX.add(nf3.format(custom_duty_usd_igx));
			}
			else
			{
				SUMMRY_CUSTOM_DUTY_USD_IGX.add("-");
			}
			if(chk8_igx.contains("y"))
			{
				SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX.add(nf3.format(custom_duty_usd_igx));
			}
			else
			{
				SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX.add("-");
			}
			if(chk9_igx.contains("y"))
			{
				SUMMRY_COST_OF_PURCHASE_USD_IGX.add(nf3.format(cost_of_purchase_usd_igx));
			}
			else
			{
				SUMMRY_COST_OF_PURCHASE_USD_IGX.add("-");
			}
			if(actual_unloaded_qty_igx != 0)
			{
				SUMMRY_USD_PER_MMBTU_IGX.add(nf2.format((cost_of_purchase_usd_igx/actual_unloaded_qty_igx)));
			}
			else
			{
				SUMMRY_USD_PER_MMBTU_IGX.add("-"); 
			}
			//System.out.println(" "+chk1);
		}
	}
	public void get_Purchase_dtls_IGX_Approval()
	{
		try
		{		
			Vector temp_usd_jbb_igx = new Vector();
			Vector temp_usd_invoice_igx = new Vector();
			Vector temp_usd_dr_cr_igx = new Vector();
			Vector temp_inv_amt_igx = new Vector();			
			Vector temp_tot_cd_amt_igx = new Vector();
			Vector temp_tot_pay_refund_igx = new Vector();
			Vector temp_cd_paid_igx = new Vector();
			Vector final_seller_amt_igx = new Vector();
			Vector unloaded_qty_igx = new Vector();
			String from_dt_igx = "01/"+month+"/"+year;
			String to_dt_igx = "";
			queryString = "Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
			////System.out.println("Last Date Of The Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_dt_igx = rset.getString(1)==null?"0":rset.getString(1);
			}
			////System.out.println("From date "+from_dt+" To Date= "+to_dt);
			queryString = "Select To_char(to_date('"+from_dt_igx+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("From Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				mon = rset.getString(1)==null?"0":rset.getString(1);
			}
			
			queryString = "Select To_char(to_date('"+to_dt_igx+"','dd/mm/yyyy'),'Month') from dual";
			////System.out.println("To The Selected Month = "+queryString);
			rset = stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_mon = rset.getString(1)==null?"0":rset.getString(1);
			}
			String tds_flag="";
			String queryString1 = "SELECT TURNOVER_FLAG FROM FMS7_SUPPLIER_TURNOVER_DTL WHERE supplier_cd=1";
			//System.out.println("Seller Payment Details Query = "+queryString);
			rset1 = stmt1.executeQuery(queryString1);
			if(rset1.next())
			{
				tds_flag= rset1.getString(1)==null?"":rset1.getString(1);
			}
			Vector temp_cargo_igx=new Vector();
			Vector temp_seq_igx=new Vector();
			
			/*String q="SELECT A.CARGO_REF_NO , A.ACT_ARRV_DT,COUNT(A.SPLIT_SEQ) FROM FMS7_CARGO_ARRIVAL_DTL A " +
					  "WHERE A.ACT_ARRV_DT between (to_date('"+from_dt_igx+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_igx+"','dd/mm/yyyy')) GROUP BY A.ACT_ARRV_DT,A.CARGO_REF_NO ORDER BY A.ACT_ARRV_DT ";
			rset=stmt.executeQuery(q);
			while(rset.next())
			{
				temp_cargo_igx.add(rset.getString(1));
				int tempSeq=rset.getInt(3);
				if(tempSeq>1)
					temp_seq_igx.add("Y");
				else
					temp_seq_igx.add("N");
			}*/
			

			String trdcd=" AND PARTY_CD="+trader_cd+" ";
			String split1="";
			split1=" AND SPLIT_SEQ='0'";
			/*queryString = "SELECT B.CARGO_REF_cd, C.TRD_CD FROM " +
					  "FMS7_MAN_CONFIRM_CARGO_DTL B, FMS7_MAN_REQ_MST C " +
					  "WHERE  B.MAN_CD=C.MAN_CD AND " +
					  "(B.DELV_From_DT between (to_date('"+from_dt_igx+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_igx+"','dd/mm/yyyy')) OR B.DELV_to_DT between (to_date('"+from_dt_igx+"','dd/mm/yyyy')) and " +
					  "(to_date('"+to_dt_igx+"','dd/mm/yyyy')))  AND "
			  		+ "B.DOM_buy_flag='Y' ";
			
			if(trader_cd!=null && !trader_cd.trim().equals("") && !trader_cd.trim().equals("0"))
			{
				queryString = queryString + trdcd;
			}
			queryString=queryString;*/
			queryString = "SELECT CARGO_REF_NO,PARTY_CD FROM FMS7_DOM_PUR_INV_dTL WHERE (INVOICE_DT between "
					+ " to_date('"+from_dt_igx+"','dd/mm/yyyy') and " +
					  "to_date('"+to_dt_igx+"','dd/mm/yyyy'))";
			if(trader_cd!=null && !trader_cd.trim().equals("") && !trader_cd.trim().equals("0"))
			{
				queryString = queryString + trdcd;
			}
			queryString=queryString;
			//System.out.println("Confirmed Cargo Arrival Details Query = "+queryString);
			rset = stmt.executeQuery(queryString);
			while(rset.next())
			{
				temp_cargo_igx.add(rset.getString(1)==null?"":rset.getString(1));
				if(flag_accounting.equalsIgnoreCase("Approval")){
					
					/*queryString = "SELECT A.INVOICE_NO FROM FMS7_FINAL_SELLER_PAY A WHERE A.CARGO_REF_NO='"+rset.getString(1)+"' "
							+ "AND SPLIT_SEQ='0' ";				
					//System.out.println("FMS7_FINAL_SELLER_PAY >>>>>>>>>>>>> Seller Final Invoice Detail Query = "+queryString);
					rset1 = stmt1.executeQuery(queryString);
					if(rset1.next())
					{

						CARGO_REF_CD_DOM.add(rset.getString(1)==null?"":rset.getString(1));
						TRD_CD_DOM.add(rset.getString(2)==null?"0":rset.getString(2));
						queryString = "SELECT VESSEL_NM, TO_CHAR(ACT_ARRV_DT,'DD/MM/YYYY'), " +
								  "TO_CHAR(ACT_ARRV_DT,'Month') FROM FMS7_CARGO_ARRIVAL_dTL WHERE CARGO_REF_NO='"+rset.getString(1)+"'";
							
							rset1 = stmt1.executeQuery(queryString);
							if(rset1.next())
							{
								VESSEL_NM_DOM.add(rset1.getString(1)==null?"":rset1.getString(1));
							}else{
								VESSEL_NM_DOM.add("");
							}
							queryString = "SELECT TO_CHAR(period_st_DT,'DD/MM/YYYY'), " +
									  "TO_CHAR(period_end_DT,'DD/MM/YYYY') FROM FMS7_DOM_PUR_INV_dTL WHERE CARGO_REF_NO='"+rset.getString(1)+"'";
								
								rset1 = stmt1.executeQuery(queryString);
								if(rset1.next())
								{
									String st_dt=rset1.getString(1)==null?"":rset1.getString(1);
									String end_dt=rset1.getString(2)==null?"":rset1.getString(2);
									duration_igx.add(st_dt+" - "+end_dt);
								}else{
									duration_igx.add("");
								}
					
					}*/
				}else{
					/*CARGO_REF_CD_DOM.add(rset.getString(1)==null?"":rset.getString(1));
					TRD_CD_DOM.add(rset.getString(2)==null?"0":rset.getString(2));*/
					//temp_cargo_igx.add(rset.getString(1)==null?"":rset.getString(1));
					/*queryString = "SELECT VESSEL_NM, TO_CHAR(ACT_ARRV_DT,'DD/MM/YYYY'), " +
							  "TO_CHAR(ACT_ARRV_DT,'Month') FROM FMS7_CARGO_ARRIVAL_dTL WHERE CARGO_REF_NO='"+rset.getString(1)+"'";
						
						rset1 = stmt1.executeQuery(queryString);
						if(rset1.next())
						{
							VESSEL_NM_DOM.add(rset1.getString(1)==null?"":rset1.getString(1));
						}else{
							VESSEL_NM_DOM.add("");
						}*/
						
				}
			}
		//	}
			
			
			double usd_mmbtu=0;
			String exchg_rate_2_igx = "";
			//for(int i=0; i<temp_cargo_igx.size(); i++)
			{
				
				/*if(SPLIT_SEQ_DOM.elementAt(i).equals("1"))
				{
					refno=""+CARGO_REF_CD_DOM.elementAt(i)+"-"+"A";
				}
				else if(SPLIT_SEQ_DOM.elementAt(i).equals("2"))
				{
					refno=""+CARGO_REF_CD_DOM.elementAt(i)+"-"+"B";
				}
				else
				{*/
					
				
				
				
				//Logic for Final Seller Payment
				
				
				/*queryString = "SELECT A.INVOICE_NO,TO_CHAR(A.INVOICE_DT,'DD/MM/YYYY'), A.ACTUAL_UNLOADED_QTY, " +
							  "A.CONFIRM_PRICE, A.INVOICE_AMT, TO_CHAR(A.DUE_DT,'DD/MM/YYYY'),SUN_APPROVAL,CHECKED_FLAG,AUTHORIZED_FLAG,APPROVED_FLAG "+
				  			  "FROM FMS7_FINAL_SELLER_PAY A WHERE A.CARGO_REF_NO="+CARGO_REF_CD_DOM.elementAt(i)+" AND SPLIT_SEQ='0' ";		
				System.out.println("FMS7_FINAL_SELLER_PAY >>>>>>>>>>>>> Seller Final Invoice Detail Query = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					INVOICE_NO_DOM.add(rset.getString(1)==null?"":rset.getString(1));
					INVOICE_DT_DOM.add(rset.getString(2)==null?"":rset.getString(2));						
					
					CONF_PRICE_DOM.add(rset.getString(4)==null?"":nf2.format(Double.parseDouble(rset.getString(4))));
					CONF_PRICE_NUMERIC_DOM.add(rset.getString(4)==null?"0.0000":nf2.format(Double.parseDouble(rset.getString(4))));
					USD_VAL_INVOICE_DOM.add(rset.getString(5)==null?"-":nf3.format(Double.parseDouble(rset.getString(5))));
					
					USD_VAL_INVOICE_NUMERIC_DOM.add(rset.getString(5)==null?"0.00":nf.format(Double.parseDouble(rset.getString(5))));
					purchase_total_invoice_value_igx += Double.parseDouble(rset.getString(5)==null?"0":nf.format(Double.parseDouble(rset.getString(5))));
					DUE_DT_DOM.add(rset.getString(6)==null?"":rset.getString(6));
					Check_flag_DOM.add(rset.getString(8)==null?"":rset.getString(8));
					Authorize_flag_DOM.add(rset.getString(9)==null?"":rset.getString(9));
					Approve_flag_DOM.add(rset.getString(10)==null?"":rset.getString(10));*/
					
					queryString = "SELECT ALLOC_QTY,EXCHG_RT_VAL,INVOICE_AMT,INVOICE_TAX_AMT,to_char(period_st_dt,'Month'),invoice_type"
								+ ",party_cd,remark,invoice_no,to_char(period_st_dt,'dd/mm/yyyy'),INVOICE_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY'),"
								+ "CONF_PRICE, INVOICE_AMT_USD, TO_CHAR(DUE_DT,'DD/MM/YYYY'),CHECKED_FLAG,AUTHORIZED_FLAG,APPROVED_FLAG,"
								+ "to_char(period_end_dt,'dd/mm/yyyy'),CARGO_REF_NO,contract_no,contract_rev_no,to_char(PAY_EXCHG_RT_DT,'dd/mm/yyyy'), PAY_EXCHG_RT_VAL"
								+ ",BUYER_PLANT_CD "
								+ "FROM FMS7_DOM_PUR_INV_dTL WHERE (INVOICE_DT between "
								+ " to_date('"+from_dt_igx+"','dd/mm/yyyy') and " +
								  "to_date('"+to_dt_igx+"','dd/mm/yyyy')) AND APPROVED_FLAG='Y' and cargo_ref_no like '99%'";
					if(trader_cd!=null && !trader_cd.trim().equals("") && !trader_cd.trim().equals("0"))
					{
						queryString = queryString + trdcd;
					}
//						System.out.println("query---"+queryString);
						rset1 = stmt1.executeQuery(queryString);
						while(rset1.next())
						{
							Vtitle.add("");
							CARGO_REF_CD_IGX.add(rset1.getString(20)==null?"0":rset1.getString(20));
							String bu_unit="";
							if(rset1.getString(25).equals("0")){
								bu_unit="SEIPL -REG(GJ)";
							}else{
								queryString1 = "SELECT PLANT_NAME,PLANT_SHORT_ABBR FROM FMS7_SUPPLIER_PLANT_DTL "
										+ "WHERE SUPPLIER_CD='"+supplier_cd+"' AND SEQ_NO='"+rset1.getString(25)+"'";
								rset2=stmt2.executeQuery(queryString1);
								if(rset2.next())
								{
									bu_unit=rset2.getString(2)==null?"":rset2.getString(2);
								}
							}
							Vsupp_plant_Abbr_IGX.add(bu_unit);
							PAY_EXCHG_RT_DT_IGX.add(rset1.getString(23)==null?"":rset1.getString(23));
							PAY_EXCHG_RT_VAL_IGX.add(rset1.getString(24)==null?"":rset1.getString(24));
							VESSEL_NM_IGX.add("");
							inv_type_flag_IGX.add("SG");
							String trader_cd=rset1.getString(7)==null?"0":rset1.getString(7);
							TRD_CD_IGX.add(rset1.getString(7)==null?"0":rset1.getString(7));
							ACTUAL_UNLOADED_QTY_IGX.add(rset1.getString(1)==null?"-":nf3.format(Double.parseDouble(rset1.getString(1))));
							unloaded_qty_igx.add(rset1.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(1))));
							ACTUAL_UNLOADED_QTY_NUMERIC_IGX.add(rset1.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(1))));
							purchase_total_qty_igx += Double.parseDouble(rset1.getString(1)==null?"0":nf.format(Double.parseDouble(rset1.getString(1))));
							exchg_rate_2_igx=rset1.getString(2)==null?"0":rset1.getString(2);
							String inv_curr="";
							
							String trd_cd="",price_unit="";
							queryString="SELECT MAN_CD ,PRICE_UNIT FROM FMS7_MAN_CONFIRM_CARGO_DTL WHERE CARGO_REF_CD='"+rset1.getString(20)+"'";
							rset2=stmt2.executeQuery(queryString);
							if(rset2.next()){
								CARGO_PRICE_FLAG.add(rset2.getString(2)==null?"":rset2.getString(2));
								price_unit=rset2.getString(2)==null?"":rset2.getString(2);
								queryString="SELECT trd_cd FROM FMS7_MAN_REQ_MST WHERE man_CD='"+rset2.getString(1)+"'";
								rset=stmt.executeQuery(queryString);
								if(rset.next()){
									trd_cd=rset.getString(1)==null?"":rset.getString(1);
								}
							}else{
								CARGO_PRICE_FLAG.add("");
							}
							if((exchg_rate_2_igx.equals("0") || exchg_rate_2_igx.equals("")) && (!price_unit.equals("1"))){
								TRADER_INV_CURRENCY_IGX.add("2");
								inv_curr="2";
							}else{
								TRADER_INV_CURRENCY_IGX.add("");
								inv_curr="";
							}
							queryString = "SELECT EXCHNG_RATE_CD FROM FMS7_TRADER_BILLING_DTL WHERE customer_cd='"+trd_cd+"' "
									+ "and  SN_NO='"+rset1.getString(21)+"' and SN_REV_NO ='"+rset1.getString(22)+"' and cont_type='K' ";
							rset3 = stmt3.executeQuery(queryString);
							if(rset3.next())
							{
								Exchg_rt_cd.add(rset3.getString(1)==null?"":rset3.getString(1));
							}else{
								Exchg_rt_cd.add("");
							}
							if(!trd_cd.equals(trader_cd)){
								queryString = "SELECT payment_currency FROM FMS7_TRADER_OTHER_PLANT_MST A WHERE SN_NO='"+rset1.getString(21)+"' AND "
										+ " SN_REV_NO='"+rset1.getString(22)+"' and customer_cd='"+trader_cd+"' and flag='K'";
								//System.out.println("queryString--billing sg---"+queryString);
								rset3 = stmt3.executeQuery(queryString);
								if(rset3.next())
								{
									TRADER_PAY_CURRENCY_IGX.add(rset3.getString(1)==null?"":rset3.getString(1));
								}else{
									TRADER_PAY_CURRENCY_IGX.add("");
								}
							}else{
								queryString = "SELECT payment_cur_cd FROM FMS7_TRADER_BILLING_DTL WHERE customer_cd='"+trader_cd+"' "
										+ "and   SN_NO='"+rset1.getString(21)+"' and SN_REV_NO ='"+rset1.getString(22)+"'  and cont_type='K'";
								//System.out.println("queryString--billing sg-"+queryString);
								rset3 = stmt3.executeQuery(queryString);
								if(rset3.next())
								{
									TRADER_PAY_CURRENCY_IGX.add(rset3.getString(1)==null?"":rset3.getString(1));
								}else{
									TRADER_PAY_CURRENCY_IGX.add("");
								}
							}
							EXCHG_RATE_VALUE_IGX.add(rset1.getString(2)==null?"-":nf2.format(Double.parseDouble(rset1.getString(2))));
							INVOICE_AMT_IGX.add(rset1.getString(3)==null?"0":nf.format(Double.parseDouble(rset1.getString(3))));
							INVOICE_TAX_AMT_IGX.add(rset1.getString(4)==null?"0":nf.format(Double.parseDouble(rset1.getString(4))));
							String taxamt=rset1.getString(4)==null?"0":nf.format(Double.parseDouble(rset1.getString(4)));
							purchase_total_tax_inr_igx+=Double.parseDouble((taxamt));
							temp_inv_amt_igx.add(rset1.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(3))));
							ACT_ARRV_MONTH_IGX.add(rset1.getString(5)==null?"":rset1.getString(5));
							String inv_type=rset1.getString(6)==null?"":rset1.getString(6);
							if(tds_flag.equals("Y")){
								double tds_Amt=(Double.parseDouble(rset1.getString(3)==null?"0":rset1.getString(3))* Double.parseDouble(tds_perc))/100;
								INVOICE_AMT_TDS_IGX.add(nf.format(tds_Amt));
							}else{
								INVOICE_AMT_TDS_IGX.add("-");
							}
							if(inv_type.equals("1")){
								inv_type="Monthly";
							}else if(inv_type.equals("2")){
								inv_type="Fortnightly";
							}
							else if(inv_type.equals("3")){
								inv_type="Weekly";
							}
							ACT_ARRV_DT_IGX.add(rset1.getString(10)==null?"":rset1.getString(10));
							String rmk=rset1.getString(8)==null?"":rset1.getString(8);
							REMARK_IGX.add(rmk);
							double tax_amt_usd=0;
							if(!rmk.equals("")){
								TAX_rmk_IGX.add(rmk+"%");
								String temp[]=rmk.split("@");
								 tax_amt_usd=(Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))* Double.parseDouble(temp[1].trim())/100;
								
							}
							TAX_amt_usd_IGX.add(nf.format(tax_amt_usd));
							invoice_type_IGX.add(inv_type);
							purchase_total_tax_inr_igx+=tax_amt_usd;
							usd_mmbtu=(tax_amt_usd+Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))/Double.parseDouble(rset1.getString(1));
							INVOICE_NO_IGX.add(rset1.getString(11)==null?"":rset1.getString(11));
							INVOICE_DT_IGX.add(rset1.getString(12)==null?"":rset1.getString(12));	
							
							CONF_PRICE_IGX.add(rset1.getString(13)==null?"":nf2.format(Double.parseDouble(rset1.getString(13))));
							CONF_PRICE_NUMERIC_IGX.add(rset1.getString(13)==null?"0.0000":nf2.format(Double.parseDouble(rset1.getString(13))));
							USD_VAL_INVOICE_IGX.add(rset1.getString(14)==null?"-":nf3.format(Double.parseDouble(rset1.getString(14))));
							
							USD_VAL_INVOICE_NUMERIC_IGX.add(rset1.getString(14)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(14))));
							purchase_total_invoice_value_igx += Double.parseDouble(rset1.getString(14)==null?"0":nf.format(Double.parseDouble(rset1.getString(14))));
							
							DUE_DT_IGX.add(rset1.getString(15)==null?"":rset1.getString(15));
							Check_flag_IGX.add(rset1.getString(16)==null?"":rset1.getString(16));
							Authorize_flag_IGX.add(rset1.getString(17)==null?"":rset1.getString(17));
							Approve_flag_IGX.add(rset1.getString(18)==null?"":rset1.getString(18));
							String st_dt=rset1.getString(10)==null?"":rset1.getString(10);
							String end_dt=rset1.getString(19)==null?"":rset1.getString(19);
							duration_igx.add(st_dt+" - "+end_dt);
							
							queryString = "SELECT TRADER_NAME,TRADER_ABBR FROM FMS7_TRADER_MST WHERE FLAG='T' AND TRADER_CD="+rset1.getString(7)+"";
							//System.out.println("TRADER Master query = "+queryString);
							rset = stmt.executeQuery(queryString);
							if(rset.next())
							{			
								TRADER_NAME_IGX.add(rset.getString(1)==null?"":rset.getString(1));
								TRADER_ABBR_IGX.add(rset.getString(2)==null?"":rset.getString(2));
							}
							else
							{
								TRADER_NAME_IGX.add("");
								TRADER_ABBR_IGX.add("");
							}
							int fin_yr = Integer.parseInt(rset1.getString(12).substring(6));
							int fin_mon = Integer.parseInt(rset1.getString(12).substring(3,5));
							String financial_year = "";
							
							if(fin_mon>3) {
								financial_year = ""+fin_yr+"-"+(fin_yr+1);
							} else {
								financial_year = ""+(fin_yr-1)+"-"+fin_yr;
							}
							String tcs_flag="";
							String queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TRADER_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+rset1.getString(7)+"'";
							//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
							rset2=stmt2.executeQuery(queryString2);
							if(rset2.next())
							{
								tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
							}
							if(inv_curr.equals("2") || (inv_curr.equals("") && tcs_flag.equals("Y"))){
								String tcs_perc="";
								queryString="SELECT TAX_CODE,FACTOR FROM FMS7_TAX_STRUCTURE_DTL A WHERE TAX_STR_CD='22' AND APP_DATE=(SELECT MAX(B.APP_DATE) "
										+ "FROM FMS7_TAX_STRUCTURE_DTL B WHERE A.TAX_STR_CD=B.TAX_STR_CD AND B.TAX_STR_CD='22' )";
										// "B.APP_DATE<=TO_DATE('01/08/2020','DD/MM/YYYY'))";
								rset=stmt.executeQuery(queryString);
								//System.out.println("queryString---"+queryString);
								if(rset.next()){
									tcs_perc=rset.getString(2)==null?"":nf2.format(Double.parseDouble(rset.getString(2)));
								}else{
								}
								
								if(tcs_flag.equals("Y")){
									double tcs_amt=(Double.parseDouble(rset1.getString(14))+Double.parseDouble(rset1.getString(4)))* Double.parseDouble(tcs_perc)/100;
									INVOICE_TCS_AMT_IGX.add(nf6.format(tcs_amt));
								}else{
									INVOICE_TCS_AMT_IGX.add("0");
								}
								
							}else{
								INVOICE_TCS_AMT_IGX.add("0");
							}
						}
						
						queryString = "SELECT ALLOC_QTY,EXCHG_RT_VAL,INVOICE_AMT,INVOICE_TAX_AMT,to_char(period_st_dt,'Month'),invoice_type"
								+ ",party_cd,remark,invoice_no,to_char(period_st_dt,'dd/mm/yyyy'),INVOICE_NO,TO_CHAR(INVOICE_DT,'DD/MM/YYYY'),"
								+ "CONF_PRICE, INVOICE_AMT_USD, TO_CHAR(DUE_DT,'DD/MM/YYYY'),CHECKED_FLAG,AUTHORIZED_FLAG,APPROVED_FLAG,"
								+ "to_char(period_end_dt,'dd/mm/yyyy'),CARGO_REF_NO,contract_no,contract_rev_no,to_char(PAY_EXCHG_RT_DT,'dd/mm/yyyy'), PAY_EXCHG_RT_VAL,"
								+ "adj_sign,adj_amt ,buyer_plant_cd "
								+ "FROM FMS7_DOM_PUR_INV_dTL_PG WHERE (INVOICE_DT between "
								+ " to_date('"+from_dt_igx+"','dd/mm/yyyy') and " +
								  "to_date('"+to_dt_igx+"','dd/mm/yyyy')) AND APPROVED_FLAG='Y' and cargo_ref_no like '99%'";
					if(trader_cd!=null && !trader_cd.trim().equals("") && !trader_cd.trim().equals("0"))
					{
						queryString = queryString + trdcd;
					}
					//	System.out.println("query---"+queryString);
						rset1 = stmt1.executeQuery(queryString);
						while(rset1.next())
						{
							
							String adj_amt=rset1.getString(26)==null?"0":rset1.getString(26);
							String adj_sign=rset1.getString(25)==null?"0":rset1.getString(25);
							CARGO_REF_CD_IGX.add(rset1.getString(20)==null?"0":rset1.getString(20));
							String bu_unit="";
							if(rset1.getString(27).equals("0")){
								bu_unit="SEIPL -REG(GJ)";
							}else{
								queryString1 = "SELECT PLANT_NAME,PLANT_SHORT_ABBR FROM FMS7_SUPPLIER_PLANT_DTL "
										+ "WHERE SUPPLIER_CD='"+supplier_cd+"' AND SEQ_NO='"+rset1.getString(27)+"'";
								rset2=stmt2.executeQuery(queryString1);
								if(rset2.next())
								{
									bu_unit=rset2.getString(2)==null?"":rset2.getString(2);
								}
							}
							Vsupp_plant_Abbr_IGX.add(bu_unit);
							PAY_EXCHG_RT_DT_IGX.add(rset1.getString(23)==null?"":rset1.getString(23));
							PAY_EXCHG_RT_VAL_IGX.add(rset1.getString(24)==null?"":rset1.getString(24));
							VESSEL_NM_IGX.add("");
							inv_type_flag_IGX.add("PG");
							String trader_cd=rset1.getString(7)==null?"0":rset1.getString(7);
							TRD_CD_IGX.add(rset1.getString(7)==null?"0":rset1.getString(7));
							ACTUAL_UNLOADED_QTY_IGX.add(rset1.getString(1)==null?"-":nf3.format(Double.parseDouble(rset1.getString(1))));
							unloaded_qty_igx.add(rset1.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(1))));
							ACTUAL_UNLOADED_QTY_NUMERIC_IGX.add(rset1.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(1))));
							purchase_total_qty_igx += Double.parseDouble(rset1.getString(1)==null?"0":nf.format(Double.parseDouble(rset1.getString(1))));
							exchg_rate_2_igx=rset1.getString(2)==null?"0":rset1.getString(2);
							String inv_curr="";
							
							String trd_cd="",price_unit="";
							queryString="SELECT MAN_CD,price_unit FROM FMS7_MAN_CONFIRM_CARGO_DTL WHERE CARGO_REF_CD='"+rset1.getString(20)+"'";
							rset2=stmt2.executeQuery(queryString);
							if(rset2.next()){
								CARGO_PRICE_FLAG.add(rset2.getString(2)==null?"":rset2.getString(2));
								price_unit=rset2.getString(2)==null?"":rset2.getString(2);
								queryString="SELECT trd_cd FROM FMS7_MAN_REQ_MST WHERE man_CD='"+rset2.getString(1)+"'";
								rset=stmt.executeQuery(queryString);
								if(rset.next()){
									trd_cd=rset.getString(1)==null?"":rset.getString(1);
								}
							}else{
								CARGO_PRICE_FLAG.add("");
							}
							if((exchg_rate_2_igx.equals("0") || exchg_rate_2_igx.equals("")) && (!price_unit.equals("1"))){
								TRADER_INV_CURRENCY_IGX.add("2");
								inv_curr="2";
							}else{
								TRADER_INV_CURRENCY_IGX.add("");
								inv_curr="";
							}
							queryString = "SELECT EXCHNG_RATE_CD FROM FMS7_TRADER_BILLING_DTL WHERE customer_cd='"+trd_cd+"' "
									+ "and  SN_NO='"+rset1.getString(21)+"' and SN_REV_NO ='"+rset1.getString(22)+"'  and cont_type='K'";
							rset3 = stmt3.executeQuery(queryString);
							if(rset3.next())
							{
								Exchg_rt_cd.add(rset3.getString(1)==null?"":rset3.getString(1));
							}else{
								Exchg_rt_cd.add("");
							}
							if(!trd_cd.equals(trader_cd)){
								//System.out.println("trd_cd---"+trd_cd);
								queryString = "SELECT payment_currency FROM FMS7_TRADER_OTHER_PLANT_MST A WHERE SN_NO='"+rset1.getString(21)+"' AND "
										+ " SN_REV_NO='"+rset1.getString(22)+"' and customer_cd='"+trader_cd+"' and flag='K'";
								//System.out.println("queryString-pg-"+queryString);
								rset3 = stmt3.executeQuery(queryString);
								if(rset3.next())
								{
									TRADER_PAY_CURRENCY_IGX.add(rset3.getString(1)==null?"":rset3.getString(1));
								}else{
									TRADER_PAY_CURRENCY_IGX.add("");
								}
							}else{
								//System.out.println("trd_cd-else--"+trd_cd);
								queryString = "SELECT payment_cur_cd FROM FMS7_TRADER_BILLING_DTL WHERE customer_cd='"+trader_cd+"' "
										+ "and   SN_NO='"+rset1.getString(21)+"' and SN_REV_NO ='"+rset1.getString(22)+"'  and cont_type='K'";
								//System.out.println("queryString--billing pg-"+queryString);
								rset3 = stmt3.executeQuery(queryString);
								if(rset3.next())
								{
									TRADER_PAY_CURRENCY_IGX.add(rset3.getString(1)==null?"":rset3.getString(1));
								}else{
									TRADER_PAY_CURRENCY_IGX.add("");
								}
							}
							EXCHG_RATE_VALUE_IGX.add(rset1.getString(2)==null?"-":nf2.format(Double.parseDouble(rset1.getString(2))));
							INVOICE_AMT_IGX.add(rset1.getString(3)==null?"0":nf.format(Double.parseDouble(rset1.getString(3))));
							INVOICE_TAX_AMT_IGX.add(rset1.getString(4)==null?"0":nf.format(Double.parseDouble(rset1.getString(4))));
							String taxamt=rset1.getString(4)==null?"0":nf.format(Double.parseDouble(rset1.getString(4)));
							purchase_total_tax_inr_igx+=Double.parseDouble((taxamt));
							temp_inv_amt_igx.add(rset1.getString(3)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(3))));
							ACT_ARRV_MONTH_IGX.add(rset1.getString(5)==null?"":rset1.getString(5));
							String inv_type=rset1.getString(6)==null?"":rset1.getString(6);
							if(tds_flag.equals("Y")){
								double tds_Amt=(Double.parseDouble(rset1.getString(3)==null?"0":rset1.getString(3))* Double.parseDouble(tds_perc))/100;
								INVOICE_AMT_TDS_IGX.add(nf.format(tds_Amt));
							}else{
								INVOICE_AMT_TDS_IGX.add("-");
							}
							if(inv_type.equals("1")){
								inv_type="Monthly";
							}else if(inv_type.equals("2")){
								inv_type="Fortnightly";
							}
							else if(inv_type.equals("3")){
								inv_type="Weekly";
							}
							ACT_ARRV_DT_IGX.add(rset1.getString(10)==null?"":rset1.getString(10));
							String rmk=rset1.getString(8)==null?"":rset1.getString(8);
							REMARK_IGX.add(rmk);
							
							String usd_amt=rset1.getString(14)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(14)));
							double amtadj=0;
							if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_curr.equals("2")){
								if(adj_sign.equals("+")){
									 amtadj=Double.parseDouble(usd_amt)+Double.parseDouble(adj_amt);
								}else if(adj_sign.equals("-")){
									amtadj=Double.parseDouble(usd_amt)-Double.parseDouble(adj_amt);
								}
								USD_VAL_INVOICE_IGX.add(nf3.format(amtadj));
								
								USD_VAL_INVOICE_NUMERIC_IGX.add(nf.format(amtadj));
								purchase_total_invoice_value_igx += Double.parseDouble(nf.format(amtadj));
							}else{
								USD_VAL_INVOICE_IGX.add(rset1.getString(14)==null?"-":nf3.format(Double.parseDouble(rset1.getString(14))));
								
								USD_VAL_INVOICE_NUMERIC_IGX.add(rset1.getString(14)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(14))));
								purchase_total_invoice_value_igx += Double.parseDouble(rset1.getString(14)==null?"0":nf.format(Double.parseDouble(rset1.getString(14))));
							}
							double tax_amt_usd=0;
							if(!rmk.equals("")){
								TAX_rmk_IGX.add(rmk+"%");
								String temp[]=rmk.split("@");
								if(!adj_amt.equals("") && (!adj_amt.equals("0")) && inv_curr.equals("2")){
									tax_amt_usd=(amtadj)* Double.parseDouble(temp[1].trim())/100;
								}else{
								 tax_amt_usd=(Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))* Double.parseDouble(temp[1].trim())/100;
								}
								
							}
							TAX_amt_usd_IGX.add(nf.format(tax_amt_usd));
							invoice_type_IGX.add(inv_type);
							purchase_total_tax_inr_igx+=tax_amt_usd;
							usd_mmbtu=(tax_amt_usd+Double.parseDouble(rset1.getString(14)==null?"0":rset1.getString(14)))/Double.parseDouble(rset1.getString(1));
							INVOICE_NO_IGX.add(rset1.getString(11)==null?"":rset1.getString(11));
							INVOICE_DT_IGX.add(rset1.getString(12)==null?"":rset1.getString(12));	
							
							CONF_PRICE_IGX.add(rset1.getString(13)==null?"":nf2.format(Double.parseDouble(rset1.getString(13))));
							CONF_PRICE_NUMERIC_IGX.add(rset1.getString(13)==null?"0.0000":nf2.format(Double.parseDouble(rset1.getString(13))));
//							USD_VAL_INVOICE_IGX.add(rset1.getString(14)==null?"-":nf3.format(Double.parseDouble(rset1.getString(14))));
//							
//							USD_VAL_INVOICE_NUMERIC_IGX.add(rset1.getString(14)==null?"0.00":nf.format(Double.parseDouble(rset1.getString(14))));
//							purchase_total_invoice_value_igx += Double.parseDouble(rset1.getString(14)==null?"0":nf.format(Double.parseDouble(rset1.getString(14))));
							
							DUE_DT_IGX.add(rset1.getString(15)==null?"":rset1.getString(15));
							Check_flag_IGX.add(rset1.getString(16)==null?"":rset1.getString(16));
							Authorize_flag_IGX.add(rset1.getString(17)==null?"":rset1.getString(17));
							Approve_flag_IGX.add(rset1.getString(18)==null?"":rset1.getString(18));
							String st_dt=rset1.getString(10)==null?"":rset1.getString(10);
							String end_dt=rset1.getString(19)==null?"":rset1.getString(19);
							duration_igx.add(st_dt+" - "+end_dt);
							
							queryString = "SELECT TRADER_NAME,TRADER_ABBR FROM FMS7_TRADER_MST WHERE FLAG='T' AND TRADER_CD="+rset1.getString(7)+"";
							//System.out.println("TRADER Master query = "+queryString);
							rset = stmt.executeQuery(queryString);
							if(rset.next())
							{			
								TRADER_NAME_IGX.add(rset.getString(1)==null?"":rset.getString(1));
								TRADER_ABBR_IGX.add(rset.getString(2)==null?"":rset.getString(2));
							}
							else
							{
								TRADER_NAME_IGX.add("");
								TRADER_ABBR_IGX.add("");
							}
							int fin_yr = Integer.parseInt(rset1.getString(12).substring(6));
							int fin_mon = Integer.parseInt(rset1.getString(12).substring(3,5));
							String financial_year = "";
							
							if(fin_mon>3) {
								financial_year = ""+fin_yr+"-"+(fin_yr+1);
							} else {
								financial_year = ""+(fin_yr-1)+"-"+fin_yr;
							}
							String tcs_flag="";
							String queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TRADER_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+rset1.getString(7)+"'";
							//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
							rset2=stmt2.executeQuery(queryString2);
							if(rset2.next())
							{
								tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
							}
							if(inv_curr.equals("2") || (inv_curr.equals("") && tcs_flag.equals("Y")) ){
								String tcs_perc="";
								queryString="SELECT TAX_CODE,FACTOR FROM FMS7_TAX_STRUCTURE_DTL A WHERE TAX_STR_CD='22' AND APP_DATE=(SELECT MAX(B.APP_DATE) "
										+ "FROM FMS7_TAX_STRUCTURE_DTL B WHERE A.TAX_STR_CD=B.TAX_STR_CD AND B.TAX_STR_CD='22' )";
										// "B.APP_DATE<=TO_DATE('01/08/2020','DD/MM/YYYY'))";
								rset=stmt.executeQuery(queryString);
								//System.out.println("queryString---"+queryString);
								if(rset.next()){
									tcs_perc=rset.getString(2)==null?"":nf2.format(Double.parseDouble(rset.getString(2)));
								}else{
								}
								if(tcs_flag.equals("Y")){
									//System.out.println(""+);
									String tax=nf6.format(Double.parseDouble(rset1.getString(4)));
									String net="";
									if(!adj_amt.equals("") && (!adj_amt.equals("0"))){
										net=nf6.format(amtadj);
									}else{
										net=nf6.format(Double.parseDouble(rset1.getString(14)));
									}
									double tcs_amt=(Double.parseDouble(net)+Double.parseDouble(tax))* Double.parseDouble(tcs_perc)/100;
//									System.out.println("tax--"+tax);
									//System.out.println("net--"+net);
									INVOICE_TCS_AMT_IGX.add(nf6.format(tcs_amt));
								}else{
									INVOICE_TCS_AMT_IGX.add("0");
								}
								
							}else{
								INVOICE_TCS_AMT_IGX.add("0");
							}
							if(!adj_amt.equals("") && (!adj_amt.equals("0"))){
								String title="";
								double ori_gross=rset1.getDouble(1) * rset1.getDouble(13);
								//System.out.println("ori_gross = "+ori_gross);
								String gross="";
								String gros="";
								if(inv_curr.equals("2")){
									gross=nf.format(ori_gross);
								}else{
									gros=nf.format(ori_gross);
									//System.out.println("ori_gross = "+gros);
									gross=nf.format(Double.parseDouble(gros) * Double.parseDouble(rset1.getString(2)==null?"0":rset1.getString(2)));
								}
								if(adj_sign.equals("+")){
									title="Gross Amount ("+gross+") + Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
								}else if(adj_sign.equals("-")){
									title="Gross Amount ("+gross+") - Adjustment Amount ("+nf.format(Double.parseDouble(adj_amt))+")=Purchase Value";
								}
								
								Vtitle.add(title);
							}else{
								Vtitle.add("");
							}
						}
						/*else{
							ACTUAL_UNLOADED_QTY_DOM.add("-");
							unloaded_qty_igx.add("0");
							ACTUAL_UNLOADED_QTY_NUMERIC_DOM.add("0");
							//exchg_rt_igx.add("");
							EXCHG_RATE_VALUE_DOM.add("-");
							INVOICE_AMT_DOM.add("0");
							INVOICE_TAX_AMT_DOM.add("0");
							temp_inv_amt_igx.add("0.00");
							ACT_ARRV_MONTH_DOM.add("");
							ACT_ARRV_DT_DOM.add("");
							TAX_rmk_DOM.add("");
							TAX_amt_usd_DOM.add("0");
							invoice_type_DOM.add("");
						}*/
					
					//SUN_APPROVAL.add(rset.getString(7)==null?"":rset.getString(7)); //Commented By Samik Shah On 23rd August, 2011 ...
				/*}
				else
				{
					INVOICE_NO_DOM.add("");
					INVOICE_DT_DOM.add("");						
					ACTUAL_UNLOADED_QTY_DOM.add("-");
					ACTUAL_UNLOADED_QTY_NUMERIC_DOM.add("0.00");
					CONF_PRICE_DOM.add("");
					CONF_PRICE_NUMERIC_DOM.add("0.0000");
					USD_VAL_INVOICE_DOM.add("-");
					USD_VAL_INVOICE_NUMERIC_DOM.add("0.00");
					DUE_DT_DOM.add("");
					temp_inv_amt_igx.add("0");
					unloaded_qty_igx.add("0");
					Check_flag_DOM.add("");
					Authorize_flag_DOM.add("");
					Approve_flag_DOM.add("");
				//	exchg_rt_igx.add("");
					EXCHG_RATE_VALUE_DOM.add("-");
					INVOICE_AMT_DOM.add("0");
					INVOICE_TAX_AMT_DOM.add("0");
					ACT_ARRV_MONTH_DOM.add("");
					ACT_ARRV_DT_DOM.add("");
					TAX_rmk_DOM.add("");
					TAX_amt_usd_DOM.add("0");
					invoice_type_DOM.add("");
					
					//SUN_APPROVAL.add(""); //Commented By Samik Shah On 23rd August, 2011 ...
				}*/
			}
//			for(int i=0; i<CARGO_REF_CD_DOM.size(); i++)
//			{
			//System.out.println("all_exchg_rtdt_igx---"+all_exchg_rtdt_igx);
				if(!all_exchg_rtdt_igx.equals("")){
					//System.out.println("all_exchg_rtdt_igx---"+all_exchg_rtdt_igx);
					String temp_str[]=all_exchg_rtdt_igx.split("@");
					for(int k=0;k<temp_str.length;k++){
						if(temp_str[k].equals("-")){
							//System.out.println("PAY_EXCHG_RT_DT_IGX.elementAt(k)--in if-"+PAY_EXCHG_RT_DT_IGX.elementAt(k));
							Exchg_rt_Dt_IGX.add(PAY_EXCHG_RT_DT_IGX.elementAt(k));
						}else{
//							System.out.println("temp_str[k]--in if-"+temp_str[k]);
							Exchg_rt_Dt_IGX.add(temp_str[k]);
						}
					}
					
				}else{
					for(int i=0; i<CARGO_REF_CD_IGX.size(); i++)
						{
						Exchg_rt_Dt_IGX.add(PAY_EXCHG_RT_DT_IGX.elementAt(i));
						}
					}
			//}
			//System.out.println("Exchg_rt_Dt_IGX---"+Exchg_rt_Dt_IGX);
			for(int i=0; i<CARGO_REF_CD_IGX.size(); i++)
			{
				String refno="";
				refno=""+CARGO_REF_CD_IGX.elementAt(i)+"-"+INVOICE_NO_IGX.elementAt(i)+"-"+TRD_CD_IGX.elementAt(i);
				queryString = "SELECT XML_GEN_FLAG, SUN_APPROVAL,to_char(to_date(TRANSACTION_DT,'ddmmyyyy'),'dd/mm/yyyy') FROM FMS7_ACCOUNT_APPROVED_DTL " +
				  			  "WHERE INV_CARGO_NO='"+refno+"' AND JOURNAL_TYPE='FMSPR'";
				//System.out.println("Confirmed FMS7_ACCOUNT_APPROVED_DTL Details Query = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())
				{
					XML_GEN_FLAG_IGX.add(rset.getString(1)==null?"N":rset.getString(1));
					SUN_APPROVAL_IGX.add(rset.getString(2)==null?"N":rset.getString(2)); 
					TRANSACTION_DT_IGX.add(rset.getString(3)==null?"":rset.getString(3));
					//Introduced By Samik Shah On 23rd August, 2011 ...
				}
				else
				{
					XML_GEN_FLAG_IGX.add("N");
					SUN_APPROVAL_IGX.add("N"); //Introduced By Samik Shah On 23rd August, 2011 ...
					TRANSACTION_DT_IGX.add(INVOICE_DT_IGX.elementAt(i));
				}
				double sbi_tt_selling_exchg_rate_igx = 0;
				double foreign_exchg_rate_igx = 0;
				String exchg_rate_cd_igx = "2"; //For SBI TT Selling Exchange Rate ...
				String exchg_rate_cd_2_igx = "5"; //For Group Foreign Exchange Rate ...
				String exchg_rate_igx = "";
				
				
				String arr_dt_igx = ""+ACT_ARRV_DT_IGX.elementAt(i);
				String arrival_date_and_rate_note_igx = "";
				
				if(arr_dt_igx.trim().equals(""))
				{
					arrival_date_and_rate_note_igx += "\nActual Arrival Date of Cargo is missing."; 
				}
				
				String queryString2 = "SELECT EXCHG_VAL FROM FMS7_EXCHG_RATE_ENTRY WHERE EXCHG_RATE_CD='"+exchg_rate_cd_igx+"' AND EFF_DT=TO_DATE('"+ACT_ARRV_DT_IGX.elementAt(i)+"','DD/MM/YYYY')";
				//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
				rset2=stmt2.executeQuery(queryString2);
				if(rset2.next())
				{
					exchg_rate_igx = rset2.getString(1)==null?"0":rset2.getString(1);
					if(!exchg_rate_igx.equals("")){
					sbi_tt_selling_exchg_rate_igx = Double.parseDouble(exchg_rate_igx);
					}
				}
				String gross_amt_inr="";
				int fin_yr = Integer.parseInt(INVOICE_DT_IGX.elementAt(i).toString().substring(6));
				int fin_mon = Integer.parseInt(INVOICE_DT_IGX.elementAt(i).toString().substring(3,5));
				String financial_year = "";
				int inv_no = 0;
				String invoice_no = "";
				
				if(fin_mon>3) {
					financial_year = ""+fin_yr+"-"+(fin_yr+1);
				} else {
					financial_year = ""+(fin_yr-1)+"-"+fin_yr;
				}
				String tcs_flag="";
				String tcs_perc="";
				
				queryString="SELECT TAX_CODE,FACTOR FROM FMS7_TAX_STRUCTURE_DTL A WHERE TAX_STR_CD='22' AND APP_DATE=(SELECT MAX(B.APP_DATE) "
						+ "FROM FMS7_TAX_STRUCTURE_DTL B WHERE A.TAX_STR_CD=B.TAX_STR_CD AND B.TAX_STR_CD='22' )";
						// "B.APP_DATE<=TO_DATE('01/08/2020','DD/MM/YYYY'))";
				rset=stmt.executeQuery(queryString);
				//System.out.println("queryString---"+queryString);
				if(rset.next()){
					TCS_PERC_IGX.add(rset.getString(2)==null?"":nf2.format(Double.parseDouble(rset.getString(2))));
					tcs_perc=rset.getString(2)==null?"":nf2.format(Double.parseDouble(rset.getString(2)));
				}else{
					TCS_PERC_IGX.add("");
				}
				TDS_PERC_IGX.add("0.1");
				
				queryString2 = "SELECT EXCHG_VAL FROM FMS7_EXCHG_RATE_ENTRY WHERE EXCHG_RATE_CD='"+Exchg_rt_cd.elementAt(i)+"' AND EFF_DT=TO_DATE('"+Exchg_rt_Dt_IGX.elementAt(i)+"','DD/MM/YYYY')";
//				System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
				rset1=stmt1.executeQuery(queryString2);
				if(rset1.next())
				{
					exchgrt=rset1.getString(1)==null?"":rset1.getString(1);
					if(!exchgrt.equals("")){
						//exchg_rt_val_IGX.add(nf61.format(Double.parseDouble(exchgrt)));
						exchg_rt_val_IGX.add(String.format("%.2f", Double.parseDouble(exchgrt)));
					}else{
						exchg_rt_val_IGX.add(exchgrt);
					}
//					System.out.println("Finding Out EXCHG_RATE ====in this11======= "+nf61.format(Double.parseDouble(exchgrt)));
//					System.out.printf("%.2f",Double.parseDouble(exchgrt));

					if(TRADER_INV_CURRENCY_IGX.elementAt(i).equals("2") && TRADER_PAY_CURRENCY_IGX.elementAt(i).equals("1")){
						//System.out.println("Finding Out EXCHG_RATE ====in this11======= "+i);
						queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TRADER_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+TRD_CD_IGX.elementAt(i)+"'";
						//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
						rset2=stmt2.executeQuery(queryString2);
						if(rset2.next())
						{
							TCS_APP_FLAG_IGX.add(rset2.getString(1)==null?"":rset2.getString(1));
							tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
							TDS_APP_FLAG_IGX.add("");
						}else{
							TCS_APP_FLAG_IGX.add("");
							TDS_APP_FLAG_IGX.add("Y");
						}
						gross_amt_inr=nf.format(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i))*Double.parseDouble(exchgrt));
						String rmk[]=REMARK_IGX.elementAt(i).toString().split("@");
						String tax_per=rmk[1].trim();
						String tax_amt=nf.format((Double.parseDouble(gross_amt_inr)* Double.parseDouble(tax_per))/100);
//						System.out.println("gross_amt_inr--"+gross_amt_inr);
//						System.out.println("tax_amt--"+tax_amt);
						double inv_amt_inr=Double.parseDouble(gross_amt_inr)+Double.parseDouble(tax_amt);
//						System.out.println("inv_amt_inr--"+inv_amt_inr);
						Gross_inr_IGX.add(gross_amt_inr);
						Tax_inr_IGX.add(tax_amt);
						net_inr_IGX.add(nf.format(inv_amt_inr));
						if(tcs_flag.equals("Y")){
							double tcs_amt=(inv_amt_inr * Double.parseDouble(tcs_perc))/100;
							TCS_TDS_AMT_IGX.add(nf.format(tcs_amt));
							payable_inr_IGX.add(nf.format(inv_amt_inr+tcs_amt));
						}else{
							double tcs_amt=(Double.parseDouble(gross_amt_inr) * 0.1)/100;
							TCS_TDS_AMT_IGX.add(nf.format(tcs_amt));
							payable_inr_IGX.add(nf.format(inv_amt_inr-tcs_amt));
						}
					}else{
						//System.out.println("Finding Out EXCHG_RATE ====in this11=else====== ");
						Gross_inr_IGX.add("-");
						Tax_inr_IGX.add("-");
						net_inr_IGX.add("-");
						payable_inr_IGX.add("-");
						TCS_TDS_AMT_IGX.add("-");
//						TCS_APP_FLAG_IGX.add("");
//						TDS_APP_FLAG_IGX.add("Y");
						queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TRADER_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+TRD_CD_IGX.elementAt(i)+"'";
						//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
						rset2=stmt2.executeQuery(queryString2);
						if(rset2.next())
						{
							TCS_APP_FLAG_IGX.add(rset2.getString(1)==null?"":rset2.getString(1));
							tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
							TDS_APP_FLAG_IGX.add("");
						}else{
							TCS_APP_FLAG_IGX.add("");
							TDS_APP_FLAG_IGX.add("Y");
						}
						//exchg_rt_val_IGX.add("");
					}
				}else{
					//System.out.println("in thissss---"+i);
					exchgrt="";
					if(TRADER_INV_CURRENCY_IGX.elementAt(i).equals("2") && TRADER_PAY_CURRENCY_IGX.elementAt(i).equals("1")){
						if(!Exchg_rt_Dt_IGX.elementAt(i).equals("")){
							exchg_rt_val_IGX.add("NA");
						}else{
							exchg_rt_val_IGX.add("");
						}
					}else{
						exchg_rt_val_IGX.add("");
					}
					if(TRADER_INV_CURRENCY_IGX.elementAt(i).equals("2") && TRADER_PAY_CURRENCY_IGX.elementAt(i).equals("1")){
						//System.out.println("in thissss11---"+i);
						queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TRADER_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+TRD_CD_IGX.elementAt(i)+"'";
						//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
						rset2=stmt2.executeQuery(queryString2);
						if(rset2.next())
						{
							TCS_APP_FLAG_IGX.add(rset2.getString(1)==null?"":rset2.getString(1));
							tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
							TDS_APP_FLAG_IGX.add("");
						}else{
							TCS_APP_FLAG_IGX.add("");
							TDS_APP_FLAG_IGX.add("Y");
						}
						if(!exchgrt.equals("")){
							//System.out.println("Finding Out EXCHG_RATE ====in this11=22====== ");
							gross_amt_inr=nf.format(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i))*Double.parseDouble(exchgrt));
							String rmk[]=REMARK_IGX.elementAt(i).toString().split("@");
							String tax_per=rmk[1].trim();
							String tax_amt=nf.format((Double.parseDouble(gross_amt_inr)* Double.parseDouble(tax_per))/100);
							double inv_amt_inr=Double.parseDouble(gross_amt_inr)+Double.parseDouble(tax_amt);
							Gross_inr_IGX.add(gross_amt_inr);
							Tax_inr_IGX.add(tax_amt);
							net_inr_IGX.add(nf.format(inv_amt_inr));
							if(tcs_flag.equals("Y")){
								double tcs_amt=(Double.parseDouble(gross_amt_inr) * Double.parseDouble(tcs_perc))/100;
								TCS_TDS_AMT_IGX.add(nf.format(tcs_amt));
								payable_inr_IGX.add(nf.format(inv_amt_inr+tcs_amt));
							}else{
								double tcs_amt=(Double.parseDouble(gross_amt_inr) * 0.1)/100;
								TCS_TDS_AMT_IGX.add(nf.format(tcs_amt));
								payable_inr_IGX.add(nf.format(inv_amt_inr-tcs_amt));
							}
						}else{
							TCS_TDS_AMT_IGX.add("");
							//TCS_TDS_AMT_IGX.add("");
							Gross_inr_IGX.add("-");
							Tax_inr_IGX.add("-");
							net_inr_IGX.add("-");
							payable_inr_IGX.add("-");
							//TCS_APP_FLAG_IGX.add("");
							//TDS_APP_FLAG_IGX.add("Y");
						}
					}
					
					else if(TRADER_INV_CURRENCY_IGX.elementAt(i).equals("2") && TRADER_PAY_CURRENCY_IGX.elementAt(i).equals("2")){
//						System.out.println("in thissss-22--"+i);
						//System.out.println("Finding Out EXCHG_RATE ====in this33======= ");
						queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+TRD_CD_IGX.elementAt(i)+"'";
						//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
						rset2=stmt2.executeQuery(queryString2);
						if(rset2.next())
						{
							TCS_APP_FLAG_IGX.add(rset2.getString(1)==null?"":rset2.getString(1));
							tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
							TDS_APP_FLAG_IGX.add("");
						}else{
							TCS_APP_FLAG_IGX.add("");
							TDS_APP_FLAG_IGX.add("Y");
						}
						double tcs_amt=0;
						if(tcs_flag.equals("Y")){
							tcs_amt=(Double.parseDouble(USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i)+"") * Double.parseDouble(tcs_perc))/100;
							TCS_TDS_AMT_IGX.add(tcs_amt);
						}else{
							tcs_amt=(Double.parseDouble(USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i)+"") * 0.1)/100;
							TCS_TDS_AMT_IGX.add(tcs_amt);
						}
						Gross_inr_IGX.add(USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i));
						Tax_inr_IGX.add(TAX_amt_usd_IGX.elementAt(i));
						net_inr_IGX.add(nf.format(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i))+Double.parseDouble(""+TAX_amt_usd_IGX.elementAt(i))));
						double totamt=0;
						if(tcs_flag.equals("Y")){
							totamt=Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i))+Double.parseDouble(""+TAX_amt_usd_IGX.elementAt(i))+tcs_amt;
						}else{
							totamt=Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i))+Double.parseDouble(""+TAX_amt_usd_IGX.elementAt(i))-+tcs_amt;
						}
						
						payable_inr_IGX.add(nf6.format(totamt));
					}else{
//						System.out.println("Fhiselse 333======="+TCS_TDS_AMT_IGX.size());
//						System.out.println("in thissss-else-"+i);
//						TCS_APP_FLAG_IGX.add("");
//						TDS_APP_FLAG_IGX.add("Y");
						queryString2 = "SELECT TURNOVER_FLAG FROM FMS7_TRADER_TURNOVER_DTL WHERE financial_year='"+financial_year+"' and TRADER_cd='"+TRD_CD_IGX.elementAt(i)+"'";
						//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
						rset2=stmt2.executeQuery(queryString2);
						if(rset2.next())
						{
							TCS_APP_FLAG_IGX.add(rset2.getString(1)==null?"":rset2.getString(1));
							tcs_flag=rset2.getString(1)==null?"":rset2.getString(1);
							TDS_APP_FLAG_IGX.add("");
						}else{
							TCS_APP_FLAG_IGX.add("");
							TDS_APP_FLAG_IGX.add("Y");
						}
						double net_amt=Double.parseDouble(INVOICE_AMT_IGX.elementAt(i)+"");
						String tds_amt=nf6.format((net_amt * 0.1)/100);
						double totamt=(Double.parseDouble(INVOICE_TAX_AMT_IGX.elementAt(i)+"")+ net_amt)-Double.parseDouble(tds_amt);
						TCS_TDS_AMT_IGX.add(tds_amt);
						//TCS_TDS_AMT_IGX.add("");
						Gross_inr_IGX.add(INVOICE_AMT_IGX.elementAt(i)+"");
						Tax_inr_IGX.add(INVOICE_TAX_AMT_IGX.elementAt(i)+"");
						net_inr_IGX.add(nf.format(Double.parseDouble(INVOICE_AMT_IGX.elementAt(i)+"")+Double.parseDouble(INVOICE_TAX_AMT_IGX.elementAt(i)+"")));
						/*if(TRADER_INV_CURRENCY_IGX.elementAt(i).equals("") && TRADER_PAY_CURRENCY_IGX.elementAt(i).equals("")){
							payable_inr_IGX.add("");
						}else{*/
							payable_inr_IGX.add(nf6.format(totamt));
						//}
					}
				}
				
				
				if(!exchgrt.equals("") && (!ind.equals(""))){}else{}
				if(sbi_tt_selling_exchg_rate_igx>0)
				{
					GROUP_FOREIGN_EXCHG_RATE_IGX.add(nf2.format(sbi_tt_selling_exchg_rate_igx));
					GROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX.add(nf2.format(sbi_tt_selling_exchg_rate_igx));
//					FINAL_SELLER_INV_AMT_INR_IGX.add(nf3.format(sbi_tt_selling_exchg_rate_igx*Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
					FINAL_SELLER_INV_AMT_INR_NUMERIC_IGX.add(nf.format(sbi_tt_selling_exchg_rate_igx*Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
					
					final_seller_amt_igx.add(nf.format(sbi_tt_selling_exchg_rate_igx*Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
					//purchase_total_inv_value_inr_igx += (sbi_tt_selling_exchg_rate_igx*Double.parseDouble(""+temp_inv_amt_igx.elementAt(i)));
					
					
				}
				else
				{
					GROUP_FOREIGN_EXCHG_RATE_IGX.add("-");
					GROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX.add("0.0000");
//					FINAL_SELLER_INV_AMT_INR_IGX.add("-");
					FINAL_SELLER_INV_AMT_INR_NUMERIC_IGX.add("0.00");
					final_seller_amt_igx.add("0.00");
					if(arrival_date_and_rate_note_igx.trim().length()<5)
					{
						arrival_date_and_rate_note_igx += "\nSBI TT Selling rate is missing for Actual Arrival Date: "+arr_dt_igx.trim();
					}
				}
				FINAL_SELLER_INV_AMT_INR_IGX.add(nf3.format(Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
				purchase_total_inv_value_inr_igx += (Double.parseDouble(""+temp_inv_amt_igx.elementAt(i)));
				if(!INVOICE_AMT_TDS_IGX.elementAt(i).equals("-")){
					purchase_total_cost_inr_igx += ((Double.parseDouble(""+INVOICE_AMT_IGX.elementAt(i))+Double.parseDouble(""+INVOICE_TAX_AMT_IGX.elementAt(i)))-Double.parseDouble(""+INVOICE_AMT_TDS_IGX.elementAt(i)));
				}else{
					purchase_total_cost_inr_igx += (Double.parseDouble(""+INVOICE_AMT_IGX.elementAt(i))+Double.parseDouble(""+INVOICE_TAX_AMT_IGX.elementAt(i)));
				}
				//FINAL_SELLER_INV_AMT_INR_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
				//System.out.println("FINAL_SELLER_INV_AMT_INR_IGX---"+FINAL_SELLER_INV_AMT_INR_IGX);
				ARRIVAL_DATE_RATE_NOTE_IGX.add(arrival_date_and_rate_note_igx.trim());
				
				String prov_cd_payment_date_igx = "";
				
				queryString = "SELECT EXCHG_RATE,CUSTOM_DUTY_PAY,TO_CHAR(CUSTOM_DUTY_DT,'dd/mm/yyyy') FROM FMS7_CUSTOM_DUTY WHERE CARGO_REF_NO="+CARGO_REF_CD_IGX.elementAt(i)+" ";
				//System.out.println("FMS7_CUSTOM_DUTY Query = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())	
				{
					if(SPLIT_SEQ_IGX.elementAt(i).equals("1") || SPLIT_SEQ_IGX.elementAt(i).equals("0"))
					{
					//EXCHG_RATE_VALUE.add(rset.getString(1)==null?"-":nf2.format(Double.parseDouble(rset.getString(1))));						
					temp_tot_cd_amt_igx.add(rset.getString(2)==null?"0.00":nf.format(Double.parseDouble(rset.getString(2))));
					if(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))>0)
					{
						purchase_total_cd_inr_igx += Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i));
						//purchase_total_cost_inr_igx += Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i));
						TOTAL_CD_AMT_IGX.add(nf3.format(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))));
						TOTAL_CD_AMT_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))));
					}
					else
					{
						TOTAL_CD_AMT_IGX.add("-");
						TOTAL_CD_AMT_NUMERIC_IGX.add("0.00");
					}
					prov_cd_payment_date_igx = rset.getString(3)==null?"":rset.getString(3);
					}
					else
					{
						TOTAL_CD_AMT_IGX.add("-");
						TOTAL_CD_AMT_NUMERIC_IGX.add("0.00");
						temp_tot_cd_amt_igx.add("0.00");
					}
				}
				else
				{
					//EXCHG_RATE_VALUE.add("-");	
					TOTAL_CD_AMT_IGX.add("-");
					TOTAL_CD_AMT_NUMERIC_IGX.add("0.00");
					temp_tot_cd_amt_igx.add("0.00");
				}
				
				String prov_cd_date_and_rate_note_igx = "";
				
				if(prov_cd_payment_date_igx.trim().equals(""))
				{
					prov_cd_date_and_rate_note_igx += "\n"+"Provisional Custom Duty Payment Date is missing."; 
				}
				
				queryString2 = "SELECT EXCHG_VAL FROM FMS7_EXCHG_RATE_ENTRY WHERE EXCHG_RATE_CD='"+exchg_rate_cd_2_igx+"' AND EFF_DT=TO_DATE('"+prov_cd_payment_date_igx+"','DD/MM/YYYY')";
				//System.out.println("Finding Out EXCHG_RATE =========== "+queryString2);
				rset2=stmt2.executeQuery(queryString2);
				if(rset2.next())
				{
					exchg_rate_2_igx = rset2.getString(1)==null?"0":rset2.getString(1);
					foreign_exchg_rate_igx = Double.parseDouble(exchg_rate_2_igx);
					//EXCHG_RATE_VALUE_IGX.add(rset2.getString(1)==null?"-":nf2.format(Double.parseDouble(rset2.getString(1))));
					if(prov_cd_date_and_rate_note_igx.trim().length()<5 && foreign_exchg_rate_igx<0.0001)
					{
						prov_cd_date_and_rate_note_igx += "\n"+"Group Foreign Exchange rate is missing for Custom Payment Date: "+prov_cd_payment_date_igx.trim();
					}
				}
				else
				{
					//EXCHG_RATE_VALUE_IGX.add("-");
					if(prov_cd_date_and_rate_note_igx.trim().length()<5)
					{
						prov_cd_date_and_rate_note_igx += "\n"+"Group Foreign Exchange rate is missing for Custom Payment Date: "+prov_cd_payment_date_igx.trim();
					}
				}
				
				CUSTOM_DUTY_DATE_RATE_NOTE_IGX.add(prov_cd_date_and_rate_note_igx.trim());
				//System.out.println("arrival_date_and_rate_note = "+arrival_date_and_rate_note+",  prov_cd_date_and_rate_note = "+prov_cd_date_and_rate_note);
				
				//Logic for FMS7_FINAL_CUSTOM_DUTY
				queryString = "SELECT CUSTOM_DUTY_PAY_REFUND,INTERST_X_DAYS FROM FMS7_FINAL_CUSTOM_DUTY WHERE CARGO_REF_NO="+CARGO_REF_CD_IGX.elementAt(i)+" ";
				//System.out.println("FMS7_FINAL_CUSTOM_DUTY Query = "+queryString);
				rset = stmt.executeQuery(queryString);
				if(rset.next())	
				{
					//if(SPLIT_SEQ_IGX.elementAt(i).equals("1") || SPLIT_SEQ_IGX.elementAt(i).equals("0"))
					//{
					temp_tot_pay_refund_igx.add(nf.format(Double.parseDouble(rset.getString(1)==null?"0.00":nf.format(Double.parseDouble(rset.getString(1))))-Double.parseDouble(rset.getString(2)==null?"0":nf.format(Double.parseDouble(rset.getString(2))))));
					if(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))!=0)
					{
						purchase_total_addl_cd_inr_igx += Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i));
						//purchase_total_cost_inr_igx += Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i));
						custom_duty_interest_total_inr_igx += Double.parseDouble(rset.getString(2)==null?"0":nf.format(Double.parseDouble(rset.getString(2))));
						TOTAL_PAID_REFUND_IGX.add(nf3.format(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))));
						TOTAL_PAID_REFUND_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))));
						CUSTOM_DUTY_INTEREST_IGX.add(nf3.format(Double.parseDouble(rset.getString(2)==null?"0":nf.format(Double.parseDouble(rset.getString(2))))));
						CUSTOM_DUTY_INTEREST_NUMERIC_IGX.add(nf.format(Double.parseDouble(rset.getString(2)==null?"0":nf.format(Double.parseDouble(rset.getString(2))))));
					}
					else
					{
						TOTAL_PAID_REFUND_IGX.add("-");
						TOTAL_PAID_REFUND_NUMERIC_IGX.add("0.00");
						CUSTOM_DUTY_INTEREST_IGX.add("-");
						CUSTOM_DUTY_INTEREST_NUMERIC_IGX.add("0.00");
					}
					/*}
					else
					{
						TOTAL_PAID_REFUND_IGX.add("-");
						TOTAL_PAID_REFUND_NUMERIC_IGX.add("0.00");
						CUSTOM_DUTY_INTEREST_IGX.add("-");
						CUSTOM_DUTY_INTEREST_NUMERIC_IGX.add("0.00");
						temp_tot_pay_refund_igx.add("0.00");
					}*/
				}
				else
				{
					TOTAL_PAID_REFUND_IGX.add("-");
					TOTAL_PAID_REFUND_NUMERIC_IGX.add("0.00");
					CUSTOM_DUTY_INTEREST_IGX.add("-");
					CUSTOM_DUTY_INTEREST_NUMERIC_IGX.add("0.00");
					temp_tot_pay_refund_igx.add("0.00");
				}
			}
			
			for(int i=0; i<CARGO_REF_CD_IGX.size(); i++)
			{										
				if(!(""+FINAL_SELLER_INV_AMT_INR_IGX.elementAt(i)).equals("-") && temp_inv_amt_igx.elementAt(i)!=null && !temp_inv_amt_igx.elementAt(i).equals("") && !temp_inv_amt_igx.elementAt(i).equals("0") && temp_tot_cd_amt_igx.elementAt(i)!=null && !temp_tot_cd_amt_igx.elementAt(i).equals("") && !temp_tot_cd_amt_igx.elementAt(i).equals("0"))
				{
					temp_cd_paid_igx.add(nf.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
//					CD_PAID_IGX.add(nf3.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
//					CD_PAID_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
					
					double total_amt_igx = Double.parseDouble(""+INVOICE_AMT_IGX.elementAt(i));
					double total_tax_amt_igx = Double.parseDouble(""+INVOICE_TAX_AMT_IGX.elementAt(i));
					double tdsamt =0;
					if(!(""+ACTUAL_UNLOADED_QTY_IGX.elementAt(i)).equals("-"))
					{
						if(TRADER_INV_CURRENCY_IGX.elementAt(i).equals("2")){
							if(TCS_APP_FLAG_IGX.elementAt(i).equals("Y")){
								tdsamt=(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i))*Double.parseDouble(""+TCS_PERC_IGX.elementAt(i)))/100;
							}
						}else{
							//tdsamt = Double.parseDouble(""+INVOICE_AMT_TDS_IGX.elementAt(i));
							tdsamt = 0;
						}
					}else{
						tdsamt = 0;
					}
					if(TRADER_INV_CURRENCY_IGX.elementAt(i).equals("2")){
						if(TCS_APP_FLAG_IGX.elementAt(i).equals("Y")){
							CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx+Double.parseDouble(""+INVOICE_TCS_AMT_IGX.elementAt(i))));
							CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx+Double.parseDouble(""+INVOICE_TCS_AMT_IGX.elementAt(i))));
						}else{
							CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx-tdsamt));
							CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx-tdsamt));
						}
					}else{
						CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx-tdsamt));
						CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx-tdsamt));
					}
					if(!(""+ACTUAL_UNLOADED_QTY_IGX.elementAt(i)).equals("-"))
					{
						if(Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))>0)
						{
							INR_PER_MMBTU_IGX.add(nf2.format((total_amt_igx+total_tax_amt_igx)/Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))));
							INR_PER_MMBTU_NUMERIC_IGX.add(nf2.format((total_amt_igx+total_tax_amt_igx)/Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))));
						}
						else
						{
							INR_PER_MMBTU_IGX.add("-");
							INR_PER_MMBTU_NUMERIC_IGX.add("0.0000");
						}
					}
					else
					{
						INR_PER_MMBTU_IGX.add("-");
						INR_PER_MMBTU_NUMERIC_IGX.add("0.0000");
					}
				}
				else
				{
					//CD_PAID_IGX.add(nf3.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
					double total_tax_amt_igx = Double.parseDouble(""+INVOICE_TAX_AMT_IGX.elementAt(i));
					double total_amt_igx = Double.parseDouble(""+INVOICE_AMT_IGX.elementAt(i));
					double tdsamt =0;
					if(!(""+ACTUAL_UNLOADED_QTY_IGX.elementAt(i)).equals("-"))
					{
						if(TRADER_INV_CURRENCY_IGX.elementAt(i).equals("2")){
							if(TCS_APP_FLAG_IGX.elementAt(i).equals("Y")){
								tdsamt=(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i))*Double.parseDouble(""+TCS_PERC_IGX.elementAt(i)))/100;
							}
						}else{
							//tdsamt = Double.parseDouble(""+INVOICE_AMT_TDS_IGX.elementAt(i));
							tdsamt = 0;
						}
					}else{
						tdsamt = 0;
					}
					if(TRADER_INV_CURRENCY_IGX.elementAt(i).equals("2")){
						if(TCS_APP_FLAG_IGX.elementAt(i).equals("Y")){
							CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx+Double.parseDouble(""+INVOICE_TCS_AMT_IGX.elementAt(i))));
							CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx+Double.parseDouble(""+INVOICE_TCS_AMT_IGX.elementAt(i))));
						}else{
							CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx-tdsamt));
							CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx-tdsamt));
						}
					}else{
					CD_PAID_IGX.add(nf3.format(total_amt_igx+total_tax_amt_igx-tdsamt));
					CD_PAID_NUMERIC_IGX.add(nf.format(total_amt_igx+total_tax_amt_igx-tdsamt));
					}
					//double total_amt_igx = Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)));
					
					temp_cd_paid_igx.add(nf.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
					//CD_PAID_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+final_seller_amt_igx.elementAt(i))+(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i)))+(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i)))));
					if(!(""+ACTUAL_UNLOADED_QTY_IGX.elementAt(i)).equals("-"))
					{
						if(Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))>0)
						{
							INR_PER_MMBTU_IGX.add(nf2.format((total_amt_igx+total_tax_amt_igx)/Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))));
							INR_PER_MMBTU_NUMERIC_IGX.add(nf2.format((total_amt_igx+total_tax_amt_igx)/Double.parseDouble((""+unloaded_qty_igx.elementAt(i)))));
						}
						else
						{
							INR_PER_MMBTU_IGX.add("-");
							INR_PER_MMBTU_NUMERIC_IGX.add("0.0000");
						}
					}
					else
					{
						INR_PER_MMBTU_IGX.add("-");
						INR_PER_MMBTU_NUMERIC_IGX.add("0.0000");
					}
				}
				
				double cd_usd_igx = 0;
				double addl_cd_usd_igx = 0;
				double total_cost_igx = 0;
				double usd_per_mmbtu_igx = 0;
				
				if(!(""+temp_tot_cd_amt_igx.elementAt(i)).equals("0") && !(""+EXCHG_RATE_VALUE_IGX.elementAt(i)).equals("-"))
				{
					CUSTOM_DUTY_USD_IGX.add(nf3.format(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					CUSTOM_DUTY_USD_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					cd_usd_igx = Double.parseDouble(nf.format(Double.parseDouble(""+temp_tot_cd_amt_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					purchase_total_cd_usd_igx += Double.parseDouble(nf.format(cd_usd_igx));
				}
				else
				{
					CUSTOM_DUTY_USD_IGX.add("-");
					CUSTOM_DUTY_USD_NUMERIC_IGX.add("0.00");
				}
				
				if(!(""+temp_tot_pay_refund_igx.elementAt(i)).equals("0") && !(""+EXCHG_RATE_VALUE_IGX.elementAt(i)).equals("-"))
				{
					ADDL_CUSTOM_DUTY_USD_IGX.add(nf3.format(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					ADDL_CUSTOM_DUTY_USD_NUMERIC_IGX.add(nf.format(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					addl_cd_usd_igx = Double.parseDouble(nf.format(Double.parseDouble(""+temp_tot_pay_refund_igx.elementAt(i))/Double.parseDouble(""+EXCHG_RATE_VALUE_IGX.elementAt(i))));
					purchase_total_addl_cd_usd_igx += Double.parseDouble(nf.format(addl_cd_usd_igx));
				}
				else
				{
					ADDL_CUSTOM_DUTY_USD_IGX.add("-");
					ADDL_CUSTOM_DUTY_USD_NUMERIC_IGX.add("0.00");
				}
				
				//total_cost_igx = Double.parseDouble(""+temp_inv_amt_igx.elementAt(i)) + cd_usd_igx + addl_cd_usd_igx;
				total_cost_igx = Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i)) + Double.parseDouble(""+TAX_amt_usd_IGX.elementAt(i));
				//purchase_total_inv_value_usd_igx += Double.parseDouble(nf.format(Double.parseDouble(""+temp_inv_amt_igx.elementAt(i))));
				if(!USD_VAL_INVOICE_IGX.elementAt(i).equals("-"))
				purchase_total_inv_value_usd_igx += Double.parseDouble(nf.format(Double.parseDouble(""+USD_VAL_INVOICE_NUMERIC_IGX.elementAt(i))));
				
				if(total_cost_igx>0 && Double.parseDouble(""+unloaded_qty_igx.elementAt(i))>0)
				{
					usd_per_mmbtu_igx = total_cost_igx/Double.parseDouble(""+unloaded_qty_igx.elementAt(i));
				}
				
				if(total_cost_igx>0)
				{
					COST_OF_PURCHASE_USD_IGX.add(nf3.format(total_cost_igx));
					COST_OF_PURCHASE_USD_NUMERIC_IGX.add(nf.format(total_cost_igx));
					purchase_total_cost_usd_igx += Double.parseDouble(nf.format(total_cost_igx));
				}
				else
				{
					COST_OF_PURCHASE_USD_IGX.add("-");
					COST_OF_PURCHASE_USD_NUMERIC_IGX.add("0.00");
				}
				
				if(usd_per_mmbtu_igx>0)
				{
					//USD_PER_MMBTU_IGX.add(nf2.format(usd_per_mmbtu_igx));
					//USD_PER_MMBTU_NUMERIC_IGX.add(nf2.format(usd_per_mmbtu_igx));
					USD_PER_MMBTU_IGX.add(nf2.format(usd_mmbtu));
					USD_PER_MMBTU_NUMERIC_IGX.add(nf2.format(usd_mmbtu));
				}
				else
				{
					USD_PER_MMBTU_IGX.add("-");
					USD_PER_MMBTU_NUMERIC_IGX.add("0.0000");
				}
			}																						
		}
		catch(Exception e)
		{
			//System.out.println("EXCEPTION:Databean_Accounting --> get_Purchase_dtls() --> "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	double sub_sum_act_recv = 0 ;
	String agr_base = "";
	double transporation_charge = 0 ;
	double transp_charge = 0 ;
	double total_tds_tax_val = 0 ;
	double total_tds_amt = 0;
	public void fetch_IGX_Invoice_Details()throws SQLException,IOException {
		try {
			
			String from_dt="";
			String to_dt="";
			
			Vector v1stFort = new Vector();
			Vector v1stFort_1 = new Vector();
			
			Vector v2ndFort = new Vector();
			Vector v2ndFort_1 = new Vector();
			
			from_dt="01/"+month+"/"+year;
			double aggr_qty = 0 ;
			
			queryString="Select To_char(Last_Day(to_date('"+to_month+"/"+to_year+"','mm/yyyy')),'dd/mm/yyyy') from dual";
			rset=stmt.executeQuery(queryString);			
			if(rset.next())
			{
				to_dt=rset.getString(1)==null?"0":rset.getString(1);
			}
//			System.out.println("from_dt---"+from_dt+"***to_dt---"+to_dt);	
			int totMonth = 0;
			String totMonthSql = "SELECT MONTHS_BETWEEN "
					+ " (TO_DATE('"+to_dt+"','dd/mm/yyyy'),"
					+ " TO_DATE('"+from_dt+"','dd/mm/yyyy') )+1 Months"
					+ " FROM DUAL";
			rset = stmt.executeQuery(totMonthSql);
			if(rset.next()) {
				totMonth = rset.getInt(1);
			}
			int tempMonth = Integer.parseInt(month);
			int tempYear = Integer.parseInt(year);
			
			for(int i = 0 ; i < totMonth ; i++) {
				
				if(tempMonth > 12) {
					tempYear++;
					tempMonth = 01;
				}
				String appendZero = "";
				if(tempMonth < 10) {
					appendZero = "0";
				}
				
				String firstFort = "01/"+appendZero+""+tempMonth+"/"+tempYear;
				String firstFort_1 = "15/"+appendZero+""+tempMonth+"/"+tempYear;
				
				String secondFort = "16/"+appendZero+""+tempMonth+"/"+tempYear;
				String secondFort_1 = "";
				
				queryString="Select To_char(Last_Day(to_date('"+tempMonth+"/"+tempYear+"','mm/yyyy')),'dd/mm/yyyy') from dual";
				rset=stmt.executeQuery(queryString);			
				if(rset.next())
				{
					secondFort_1=rset.getString(1)==null?"0":rset.getString(1);
				} 
				
				tempMonth++;
				
				v1stFort.add(firstFort);
				v1stFort_1.add(firstFort_1);
				
				v2ndFort.add(secondFort);
				v2ndFort_1.add(secondFort_1);
				
//				System.out.println("firstFort****"+firstFort+"****"+firstFort_1);
//				System.out.println("secondFort****"+secondFort+"****"+secondFort_1);
			}
//			System.out.println("customer_cd****"+customer_cd);
			String custSqlStr = "";
			String custSqlStr_1 = "";
			if(customer_cd.equalsIgnoreCase("")) {
				
				custSqlStr = "and A.customer_cd like '%' ";
			}else{
				custSqlStr = "and A.customer_cd ='"+customer_cd+"' ";
			}
			
			if(customer_cd.equalsIgnoreCase("")) {
				
				custSqlStr_1 = "and B.customer_cd like '%' ";
			}else{
				custSqlStr_1 = "and B.customer_cd ='"+customer_cd+"' ";
			}
			
			Inv_roundoff = Read_All_Roundoff();
//			System.out.println("Inv_roundoff****"+Inv_roundoff);
			for(int i = 0 ; i < v1stFort.size(); i++) {
				
				int subRecord = 0;
				int allocRecord = 0;
				sub_sum_act_recv = 0 ;
				agr_base = "";
				transporation_charge = 0 ;
				transp_charge = 0 ;
				total_tds_tax_val = 0 ;
				
				String fetch1stContSql = "";
				if(invstatus.equalsIgnoreCase("UNPAID")) {
					
					fetch1stContSql = "select A.customer_cd, A.sn_no,A.sn_rev_no,A.plant_seq_no"
							+ " from FMS7_DAILY_ALLOCATION_DTL  A"
							+ " where A.CONTRACT_TYPE = 'K'"
							+ " "+custSqlStr+" "
							+ " and A.GAS_DT between to_date('"+v1stFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v1stFort_1.elementAt(i)+"','dd/mm/yyyy')"
							+ " and A.NOM_REV_NO = (select max(B.nom_rev_no) from  FMS7_DAILY_ALLOCATION_DTL  B"
							+ " where A.customer_cd = B.customer_cd and  A.sn_no = B.sn_no and A.sn_rev_no = B.sn_rev_no and A.plant_seq_no = B.plant_seq_no and B.contract_type = 'K')"
							+ " and a.customer_cd not in (select B.customer_cd from FMS7_INVOICE_MST B"
								+ " where B.CONTRACT_TYPE = 'K' "
								+ " and B.customer_cd = a.customer_cd"
								+ " and A.sn_no = B.sn_no "
								+ " and A.sn_rev_no = a.sn_rev_no"
								+ " and A.plant_seq_no = b.plant_seq_no  "
								+ " and b.period_start_dt between to_date('"+v1stFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v1stFort_1.elementAt(i)+"','dd/mm/yyyy')"
								+ " and b.period_end_dt between to_date('"+v1stFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v1stFort_1.elementAt(i)+"','dd/mm/yyyy')"
								+ " and b.APPROVED_FLAG='Y'"
								+ " and b.CHECKED_FLAG='Y'"
								+ " and b.FLAG!='X'"
								+ " and b.PDF_INV_DTL IS NOT NULL"
								+ " and b.PAY_RECV_AMT IS NOT NULL"
								+ " and b.PAY_INSERT_BY IS NOT NULL"
								+ " and b.new_inv_seq_no not in (select c.NEW_INV_SEQ_NO from FMS8_PAY_RECV_DTL c"
									+ " where b.new_inv_seq_no =  c.new_inv_seq_no "
									+ " and C.REV_NO = (select max(D.rev_no) from FMS8_PAY_RECV_DTL D where D.new_inv_seq_no=C.new_inv_seq_no and D.commodity_type='RLNG' )"
									+ " and SHORT_RECV_AMT >= '"+Inv_roundoff+"')"
								+ " group by b.customer_cd,b.sn_no,b.sn_rev_no,b.plant_seq_no)"
							+ " group by A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no order by  A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no";
					
					/*
					 * fetch1stContSql ="select A.customer_cd, A.sn_no,A.sn_rev_no,A.plant_seq_no" +
					 * " from FMS7_DAILY_ALLOCATION_DTL  A" + " where A.CONTRACT_TYPE = 'K' " +
					 * " "+custSqlStr+" " + " and A.GAS_DT between to_date('"+v1stFort.elementAt(i)
					 * +"','dd/mm/yyyy') and to_date('"+v1stFort_1.elementAt(i)+"','dd/mm/yyyy')" +
					 * " and A.NOM_REV_NO = (select max(B.nom_rev_no) from  FMS7_DAILY_ALLOCATION_DTL  B"
					 * +
					 * " where A.customer_cd = B.customer_cd and  A.sn_no = B.sn_no and A.sn_rev_no = B.sn_rev_no and A.plant_seq_no = B.plant_seq_no and B.contract_type = 'K')"
					 * +
					 * " group by A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no order by  A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no"
					 * ;
					 */
				}else if(invstatus.equalsIgnoreCase("PAID")) {

					fetch1stContSql ="select A.customer_cd, A.sn_no,A.sn_rev_no,A.plant_seq_no"
						+ " from FMS7_DAILY_ALLOCATION_DTL  A"
						+ " inner join FMS7_INVOICE_MST B"
						+ " ON A.customer_cd  = b.customer_cd"
						+ " and A.sn_no = B.sn_no"
						+ " and A.sn_rev_no = B.sn_rev_no"
						+ " and A.plant_seq_no = b.plant_seq_no"
						+ " and a.contract_type = b.contract_type"
						+ " and a.contract_type = 'K'"
						+ " "+custSqlStr+" "
						+ " and A.GAS_DT between to_date('"+v1stFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v1stFort_1.elementAt(i)+"','dd/mm/yyyy')"
						+ " and b.period_start_dt between to_date('"+v1stFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v1stFort_1.elementAt(i)+"','dd/mm/yyyy')"
						+ " and b.period_end_dt between to_date('"+v1stFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v1stFort_1.elementAt(i)+"','dd/mm/yyyy')"
						+ " and b.APPROVED_FLAG='Y'"
						+ " and b.CHECKED_FLAG='Y'"
						+ " and b.PAY_RECV_AMT IS NOT NULL"
						+ " and b.PAY_INSERT_BY IS NOT NULL AND (b.PDF_INV_DTL IS NOT NULL) "
						+ " group by A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no order by  A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no";
				}
				System.out.println("fetchContSql**1*"+fetch1stContSql);
				rset = stmt.executeQuery(fetch1stContSql);
				while (rset.next()) {
					
					subRecord = 0;
					aggr_qty = 0;
					total_tds_amt=0;
					String alloc_sub_flg = "N";
					allocRecord = 0 ;
					sub_sum_act_recv = 0 ;
					agr_base = "";
					transporation_charge = 0 ;
					transp_charge = 0 ;
					total_tds_tax_val = 0 ;
					/* for month name */
					String monthSql = "select to_char(to_date('"+v1stFort.elementAt(i)+"','dd/mm/yyyy'),'MON') as month from dual";
					rset1 = stmt1.executeQuery(monthSql);
					if(rset1.next()) {
						VImonth.add(rset1.getString(1)==null?"":rset1.getString(1));
					}else {
						VImonth.add("-");
					}
					
					VIperiod_st_dt.add(v1stFort.elementAt(i));
					VIperiod_end_dt.add(v1stFort_1.elementAt(i));
					
					VIcustomer_cd.add(rset.getString(1)==null?"":rset.getString(1));
					VIsn_no.add(rset.getString(2)==null?"":rset.getString(2));
					VIsn_rev_no.add(rset.getString(3)==null?"":rset.getString(3));
					VIplant_seq_no.add(rset.getString(4)==null?"":rset.getString(4));
					
					String temp_cust_cd = rset.getString(1)==null?"":rset.getString(1);
					String temp_sn = rset.getString(2)==null?"":rset.getString(2);
					String temp_sn_rev_no = rset.getString(3)==null?"":rset.getString(3);
					String temp_plant_seq_no = rset.getString(4)==null?"":rset.getString(4);
					
					String cont_map_id = "K-"+temp_cust_cd+"-0-0-"+temp_sn+"-"+temp_sn_rev_no+"-"+temp_plant_seq_no;
					/* for customer Name & Abbr */
					String queryString1 = "SELECT CUSTOMER_NAME,CUSTOMER_ABBR FROM FMS7_CUSTOMER_MST A WHERE CUSTOMER_CD='"+rset.getString(1)+"'"
							+ " AND " +
						  "A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_MST B WHERE A.customer_cd=B.customer_cd AND CUSTOMER_CD='"+rset.getString(1)+"')";
					rset1=stmt1.executeQuery(queryString1);
					if(rset1.next()){
						VIcustomer_abbr.add(rset1.getString(2)==null?"":rset1.getString(2));
						VIcustomer_name.add(rset1.getString(1)==null?"":rset1.getString(1));
					}else{
						VIcustomer_abbr.add("-");
						VIcustomer_name.add("-");
					}
					
					/* for sales price */
					double sales_rate = 0;
					String salePriceSql = "select nvl(RATE,0),RATE_UNIT,AGMT_BASE,nvl(TRANSPORTATION_CHARGE,0) from FMS10_IGX_MST "
							+ " where CUSTOMER_CD = '"+rset.getString(1)+"'"
							+ " and SN_NO = '"+rset.getString(2)+"'"
							+ " and SN_REV_NO = '"+rset.getString(3)+"'";
//					System.out.println("salePriceSql---"+salePriceSql);
					rset1 = stmt1.executeQuery(salePriceSql);
					if(rset1.next()) {
						sales_rate = rset1.getDouble(1);
						VISales_rate.add(nf2.format(sales_rate));
						agr_base = rset1.getString(3)==null?"":rset1.getString(3);
						transporation_charge = rset1.getDouble(4);
					}else {
						VISales_rate.add("0");
					}
					
					/* for Transaction Charges */
					double trans_perc = 0,sgst = 0, cgst = 0,igst = 0 ; 
					String transChargeSql = "Select nvl(TRANSACTION_PERC,0),nvl(SGST,0),nvl(CGST,0),nvl(IGST,0)"
							+ "  from  FMS10_IGX_TRANSACTION_DTL "
							+ " where EFFECTIVE_DT = (select max(A.EFFECTIVE_DT) from  FMS10_IGX_TRANSACTION_DTL A)";
					rset1 = stmt1.executeQuery(transChargeSql);
					if(rset1.next()) {
						trans_perc = rset1.getDouble(1);
						sgst = rset1.getDouble(2);
						cgst = rset1.getDouble(3);
						igst = rset1.getDouble(4);
					}
					
					String fetchAllocSql = "";
					if(agr_base.equalsIgnoreCase("D")) {
						
						fetchAllocSql ="select nvl(sum(exit_tot_ene),0),to_char(ALLOC_DT,'dd/mm/yyyy'),"
								+ " to_char(ALLOC_DT+2,'dd/mm/yyyy'),0 as alloc_rev_no "
								+ " from FMS_ALLOC_MST  A"
								+ " where CONT_MAPPING_ID = '"+cont_map_id+"' "
								+ " and A.ALLOC_DT between to_date('"+v1stFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v1stFort_1.elementAt(i)+"','dd/mm/yyyy')"
								+ " group by CONT_MAPPING_ID, A.ALLOC_DT"
								+ " order by CONT_MAPPING_ID, A.ALLOC_DT";
					}else{
						fetchAllocSql ="select nvl(sum(qty_mmbtu),0),to_char(GAS_DT,'dd/mm/yyyy'),"
							+ " to_char(GAS_DT+2,'dd/mm/yyyy'),nom_rev_no "
							+ " from FMS7_DAILY_ALLOCATION_DTL  A"
							+ " where A.CONTRACT_TYPE = 'K' "
							+ " AND A.CUSTOMER_CD = '"+temp_cust_cd+"'"
							+ " AND A.SN_NO = '"+temp_sn+"'"
							+ " AND A.SN_REV_NO = '"+temp_sn_rev_no+"'"
							+ " AND A.PLANT_SEQ_NO = '"+temp_plant_seq_no+"'"
							+ " and A.GAS_DT between to_date('"+v1stFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v1stFort_1.elementAt(i)+"','dd/mm/yyyy')"
							+ " and A.NOM_REV_NO = (select max(B.nom_rev_no) from  FMS7_DAILY_ALLOCATION_DTL  B"
							+ " where A.customer_cd = B.customer_cd and  A.sn_no = B.sn_no and A.sn_rev_no = B.sn_rev_no and A.plant_seq_no = B.plant_seq_no and B.contract_type = 'K')"
							+ " group by A.CONTRACT_TYPE,A.CUSTOMER_CD,A.SN_NO,A.SN_REV_NO,A.PLANT_SEQ_NO, A.GAS_DT,A.NOM_REV_NO"
							+ " order by  A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no,A.GAS_DT";
					}
//					System.out.println("fetchAllocSql***"+fetchAllocSql);
					rset1 = stmt1.executeQuery(fetchAllocSql);
					while (rset1.next()) {
						
						subRecord++;
						VIsub_alloc_qty.add(rset1.getString(1)==null?"":rset1.getString(1));
						VIsub_alloc_dt.add(rset1.getString(2)==null?"":rset1.getString(2));
						String alloc_dt = rset1.getString(2)==null?"":rset1.getString(2);
						double sub_alloc_qty = rset1.getDouble(1);
						
						aggr_qty+=sub_alloc_qty;
						double sub_transp_charge = 0 ;
						if(agr_base.equalsIgnoreCase("D")) {
							sub_transp_charge = (sub_alloc_qty * transporation_charge);
						}
						double sub_gross = sub_alloc_qty * sales_rate;
						VIsub_Gross_amt.add(sub_gross + sub_transp_charge);
						double total_sub_gross = sub_gross + sub_transp_charge;
						double trans_gross = (rset1.getDouble(1) * trans_perc);
//						System.out.println("trans_gross***"+trans_gross);
						VIsub_Trans_charge.add(trans_gross);
						VIsub_Trans_sgst.add((trans_gross*sgst)/100);
						VIsub_Trans_cgst.add((trans_gross*cgst)/100);
						
						double sub_net_pay = sub_gross + sub_transp_charge;// - (trans_gross + ((trans_gross*sgst)/100) + ((trans_gross*cgst)/100));

//						VIsub_due_dt.add(rset1.getString(3)==null?"":rset1.getString(3));
						VIsub_nom_rev_no.add(rset1.getString(4)==null?"":rset1.getString(4));
						String nom_rev_no = rset1.getString(4)==null?"":rset1.getString(4);
						
						String sub_due_dt = rset1.getString(3)==null?"":rset1.getString(3);
						String sub_pay_dt = "";
						/* fetching already submitted data */
						String fetchPaySql = "select nvl(GROSS_TDS_PERC,0),nvl(GROSS_TDS_AMT,0),nvl(NET_RECV,0),nvl(ACTUAL_RECV,0),"
								+ " nvl(SHORT_RECV,0),to_char(PAYMENT_DT,'dd/mm/yyyy'),REMARK,TAX_TDS_PERC,nvl(TAX_TDS_AMT,0)"
								+ " from FMS10_IGX_PAYMENT_DTL A"
								+ " where A.customer_cd = '"+temp_cust_cd+"'"
								+ " and A.cont_no = '"+temp_sn+"' "
								+ " and A.cont_rev_no = '"+temp_sn_rev_no+"'"
								+ " and A.plant_seq_no = '"+temp_plant_seq_no+"'"
								+ " and A.gas_dt = to_date('"+alloc_dt+"','dd/mm/yyyy')"
								+ " and A.nom_rev_no = '"+nom_rev_no+"'"
								+ " and A.contract_type = 'K'"
								+ " and A.rev_no = (select max(B.rev_no) from FMS10_IGX_PAYMENT_DTL B where"
									+ " A.customer_cd = B.customer_cd and  A.cont_no = B.cont_no "
									+ " and A.cont_rev_no = B.cont_rev_no and A.plant_seq_no = B.plant_seq_no "
									+ " and B.contract_type = 'K' and A.gas_dt = B.gas_dt)";
						
//						System.out.println("fetchPaySql****"+fetchPaySql);
						rset2 = stmt2.executeQuery(fetchPaySql);
						if(rset2.next() ) {
							allocRecord++;
							VIsub_gross_tds_perc.add(rset2.getString(1)==null?"":rset2.getString(1));
							VIsub_gross_tds_amt.add(rset2.getString(2)==null?"":rset2.getString(2));
							total_tds_amt+=rset2.getDouble(2);
							VIsub_net_recv.add(rset2.getString(3)==null?"":rset2.getString(3));
							VIsub_actual_recv.add(rset2.getString(4)==null?"":rset2.getString(4));
							VIsub_short_recv.add(rset2.getString(5)==null?"":rset2.getString(5));
							VIsub_payment_dt.add(rset2.getString(6)==null?"":rset2.getString(6));
							sub_pay_dt = rset2.getString(6)==null?"":rset2.getString(6);
							VIsub_remark.add(rset2.getString(7)==null?"":rset2.getString(7));
							VIsub_tax_tds_perc.add(rset2.getString(8)==null?"":rset2.getString(8));
							VIsub_tax_tds_amt.add(rset2.getString(9)==null?"":rset2.getString(9));
							total_tds_tax_val+=rset2.getDouble(9);
							
							alloc_sub_flg = "Y";
							sub_sum_act_recv+=rset2.getDouble(4);
						}else {
							VIsub_gross_tds_perc.add("");
							VIsub_gross_tds_amt.add("");
							VIsub_net_recv.add("");
							VIsub_actual_recv.add("");
							VIsub_short_recv.add("");
							VIsub_payment_dt.add("");
							VIsub_remark.add("");
							VIsub_tax_tds_perc.add("");
							VIsub_tax_tds_amt.add("");
						}
						
						/* fetching due days */
						String subDueDays = "";
						if(!sub_due_dt.equalsIgnoreCase("") && !sub_pay_dt.equalsIgnoreCase("") ) {
							
							subDueDays = extractDays(sub_due_dt,sub_pay_dt);
						}
//						System.out.println("subDueDays***"+subDueDays);
						VIsub_due_days.add(subDueDays);
						String due_date = calculate_due_date(rset1.getString(3),alloc_dt);
						VIsub_due_dt.add(due_date);
						
						//Fetching Tax Amount
						fetch_igx_tax_dtl (temp_cust_cd,temp_plant_seq_no,alloc_dt,total_sub_gross);
						VIsub_tax_amt.add(tax_amt);
						VIsub_tax_str.add(tax_line);
						double act_net_amt = sub_net_pay + tax_amt;
						VIsub_net_payable.add(act_net_amt) ;
					}
					
					VIinv_alloc_sub_flg.add(alloc_sub_flg);
					VIsub_rec_cnt.add(subRecord);
					VIaggr_alloc_qty.add(aggr_qty);
					double gross_amt = aggr_qty*sales_rate;
					
//					double trans_charge = (gross_amt*trans_perc)/100; 
					double trans_charge = (aggr_qty * trans_perc);
					VITrans_charge.add(trans_charge);
					VITrans_cgst.add((trans_charge*cgst)/100);
					VITrans_sgst.add((trans_charge*sgst)/100);
					double total_trans_charge = trans_charge+((trans_charge*cgst)/100) + ((trans_charge*sgst)/100);
					VITrans_tot_amt.add(total_trans_charge);
					VIAllocCnt.add(allocRecord);
					inv_gross_amt_inr = 0 ;
					inv_gen_flag = "N";
					
					if(agr_base.equalsIgnoreCase("D")) {
						transp_charge = (aggr_qty * transporation_charge);
					}
					
					inv_gen_flag = fetchingInvoiceDetails(temp_cust_cd,temp_sn,temp_sn_rev_no,temp_plant_seq_no,v1stFort.elementAt(i)+"",v1stFort_1.elementAt(i)+"",gross_amt);
					
					if(inv_gen_flag.equalsIgnoreCase("Y")) {
						VIGross_amt.add(inv_gross_amt_inr + transp_charge);
					}else {
						VIGross_amt.add(gross_amt + transp_charge);
					}
					VIinv_sub_sum_act_recv.add(nf.format(sub_sum_act_recv));
				}
				
				//for 2nd fortnite
			String fetch2ndContSql = "";
			if(invstatus.equalsIgnoreCase("UNPAID")) {
				
				fetch2ndContSql = "select A.customer_cd, A.sn_no,A.sn_rev_no,A.plant_seq_no"
						+ " from FMS7_DAILY_ALLOCATION_DTL  A"
						+ " where A.CONTRACT_TYPE = 'K'"
						+ " "+custSqlStr+" "
						+ " and A.GAS_DT between to_date('"+v2ndFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v2ndFort_1.elementAt(i)+"','dd/mm/yyyy')"
						+ " and A.NOM_REV_NO = (select max(B.nom_rev_no) from  FMS7_DAILY_ALLOCATION_DTL  B"
						+ " where A.customer_cd = B.customer_cd and  A.sn_no = B.sn_no and A.sn_rev_no = B.sn_rev_no and A.plant_seq_no = B.plant_seq_no and B.contract_type = 'K')"
						+ " and a.customer_cd not in (select B.customer_cd from FMS7_INVOICE_MST B"
							+ " where B.CONTRACT_TYPE = 'K' "
							+ " and B.customer_cd = a.customer_cd"
							+ " and A.sn_no = B.sn_no "
							+ " and A.sn_rev_no = a.sn_rev_no"
							+ " and A.plant_seq_no = b.plant_seq_no "
							+ " and b.period_start_dt between to_date('"+v2ndFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v2ndFort_1.elementAt(i)+"','dd/mm/yyyy')"
							+ " and b.period_end_dt between to_date('"+v2ndFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v2ndFort_1.elementAt(i)+"','dd/mm/yyyy')"
							+ " and b.APPROVED_FLAG='Y'"
							+ " and b.CHECKED_FLAG='Y'"
							+ " and b.FLAG!='X'"
							+ " and b.PDF_INV_DTL IS NOT NULL"
							+ " and b.PAY_RECV_AMT IS NOT NULL"
							+ " and b.PAY_INSERT_BY IS NOT NULL"
							+ " and b.new_inv_seq_no not in (select NEW_INV_SEQ_NO from FMS8_PAY_RECV_DTL c"
								+ " where b.new_inv_seq_no =  c.new_inv_seq_no "
								+ " and C.REV_NO = (select max(D.rev_no) from FMS8_PAY_RECV_DTL D where D.new_inv_seq_no=C.new_inv_seq_no and D.commodity_type='RLNG' )"
								+ " and SHORT_RECV_AMT >= '"+Inv_roundoff+"')"
							+ " group by b.customer_cd,b.sn_no,b.sn_rev_no,b.plant_seq_no)"
						+ " group by A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no order by  A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no";
				
			}else if(invstatus.equalsIgnoreCase("PAID")) {

				fetch2ndContSql ="select A.customer_cd, A.sn_no,A.sn_rev_no,A.plant_seq_no"
					+ " from FMS7_DAILY_ALLOCATION_DTL  A"
					+ " inner join FMS7_INVOICE_MST B"
					+ " ON A.customer_cd  = b.customer_cd"
					+ " and A.sn_no = B.sn_no"
					+ " and A.sn_rev_no = B.sn_rev_no"
					+ " and A.plant_seq_no = b.plant_seq_no"
					+ " and a.contract_type = b.contract_type"
					+ " and a.contract_type = 'K'"
					+ " "+custSqlStr+" "
					+ " and A.GAS_DT between to_date('"+v2ndFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v2ndFort_1.elementAt(i)+"','dd/mm/yyyy')"
					+ " and b.period_start_dt between to_date('"+v2ndFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v2ndFort_1.elementAt(i)+"','dd/mm/yyyy')"
					+ " and b.period_end_dt between to_date('"+v2ndFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v2ndFort_1.elementAt(i)+"','dd/mm/yyyy')"
					+ " and b.APPROVED_FLAG='Y'"
					+ " and b.CHECKED_FLAG='Y'"
					+ " and b.PAY_RECV_AMT IS NOT NULL"
					+ " and b.PAY_INSERT_BY IS NOT NULL AND (b.PDF_INV_DTL IS NOT NULL) "
					+ " group by A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no order by  A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no";
			}
//				System.out.println("fetch2ndContSql***"+fetch2ndContSql);
				rset = stmt.executeQuery(fetch2ndContSql);
				while (rset.next()) {
					
					subRecord = 0 ;
					aggr_qty = 0;
					total_tds_amt=0;
					String alloc_sub_flg = "N";
					allocRecord = 0 ;
					sub_sum_act_recv = 0 ;
					total_tds_tax_val = 0 ;
					/* for month name */
					String monthSql = "select to_char(to_date('"+v2ndFort.elementAt(i)+"','dd/mm/yyyy'),'MON') as month from dual";
					rset1 = stmt1.executeQuery(monthSql);
					if(rset1.next()) {
						VImonth.add(rset1.getString(1)==null?"":rset1.getString(1));
					}else {
						VImonth.add("-");
					}
					
					VIperiod_st_dt.add(v2ndFort.elementAt(i));
					VIperiod_end_dt.add(v2ndFort_1.elementAt(i));
					
					VIcustomer_cd.add(rset.getString(1)==null?"":rset.getString(1));
					VIsn_no.add(rset.getString(2)==null?"":rset.getString(2));
					VIsn_rev_no.add(rset.getString(3)==null?"":rset.getString(3));
					VIplant_seq_no.add(rset.getString(4)==null?"":rset.getString(4));
					
					String temp_cust_cd = rset.getString(1)==null?"":rset.getString(1);
					String temp_sn = rset.getString(2)==null?"":rset.getString(2);
					String temp_sn_rev_no = rset.getString(3)==null?"":rset.getString(3);
					String temp_plant_seq_no = rset.getString(4)==null?"":rset.getString(4);
					String cont_map_id = "K-"+temp_cust_cd+"-0-0-"+temp_sn+"-"+temp_sn_rev_no+"-"+temp_plant_seq_no;
					
					/* for customer Name & Abbr */
					String queryString1 = "SELECT CUSTOMER_NAME,CUSTOMER_ABBR FROM FMS7_CUSTOMER_MST A WHERE CUSTOMER_CD='"+rset.getString(1)+"'"
							+ " AND " +
						  "A.eff_dt=(SELECT MAX(B.eff_dt) FROM FMS7_CUSTOMER_MST B WHERE A.customer_cd=B.customer_cd AND CUSTOMER_CD='"+rset.getString(1)+"')";
					rset1=stmt1.executeQuery(queryString1);
					if(rset1.next()){
						VIcustomer_abbr.add(rset1.getString(2)==null?"":rset1.getString(2));
						VIcustomer_name.add(rset1.getString(1)==null?"":rset1.getString(1));
					}else{
						VIcustomer_abbr.add("-");
						VIcustomer_name.add("-");
					}
					
					/* for sales price */
					double sales_rate = 0 ; 
					String salePriceSql = "select nvl(RATE,0),RATE_UNIT,AGMT_BASE,nvl(TRANSPORTATION_CHARGE,0) from FMS10_IGX_MST "
							+ " where CUSTOMER_CD = '"+rset.getString(1)+"'"
							+ " and SN_NO = '"+rset.getString(2)+"'"
							+ " and SN_REV_NO = '"+rset.getString(3)+"'";
//					System.out.println("salePriceSql---"+salePriceSql);
					rset1 = stmt1.executeQuery(salePriceSql);
					if(rset1.next()) {
						sales_rate = rset1.getDouble(1);
						VISales_rate.add(nf2.format(sales_rate));
						agr_base = rset1.getString(3)==null?"":rset1.getString(3);
						transporation_charge = rset1.getDouble(4);
					}else {
						VISales_rate.add("0");
					}
					
					/* for Transaction Charges */
					double trans_perc = 0,sgst = 0, cgst = 0,igst = 0 ; 
					String transChargeSql = "Select nvl(TRANSACTION_PERC,0),nvl(SGST,0),nvl(CGST,0),nvl(IGST,0)"
							+ "  from  FMS10_IGX_TRANSACTION_DTL "
							+ " where EFFECTIVE_DT = (select max(A.EFFECTIVE_DT) from  FMS10_IGX_TRANSACTION_DTL A)";
					rset1 = stmt1.executeQuery(transChargeSql);
					if(rset1.next()) {
						trans_perc = rset1.getDouble(1);
						sgst = rset1.getDouble(2);
						cgst = rset1.getDouble(3);
						igst = rset1.getDouble(4);
					}
					//
					String fetchAllocSql = "";
					if(agr_base.equalsIgnoreCase("D")) {
						
						fetchAllocSql ="select nvl(sum(exit_tot_ene),0),to_char(ALLOC_DT,'dd/mm/yyyy'),"
								+ " to_char(ALLOC_DT+2,'dd/mm/yyyy'),0 as alloc_rev_no "
								+ " from FMS_ALLOC_MST  A"
								+ " where CONT_MAPPING_ID = '"+cont_map_id+"' "
								+ " and A.ALLOC_DT between to_date('"+v2ndFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v2ndFort_1.elementAt(i)+"','dd/mm/yyyy')"
								+ " group by CONT_MAPPING_ID, A.ALLOC_DT"
								+ " order by CONT_MAPPING_ID, A.ALLOC_DT";
					}else{
						fetchAllocSql ="select nvl(sum(qty_mmbtu),0),to_char(GAS_DT,'dd/mm/yyyy'),"
							+ " to_char(GAS_DT+2,'dd/mm/yyyy'),nom_rev_no "
							+ " from FMS7_DAILY_ALLOCATION_DTL  A"
							+ " where A.CONTRACT_TYPE = 'K' "
							+ " AND A.CUSTOMER_CD = '"+temp_cust_cd+"'"
							+ " AND A.SN_NO = '"+temp_sn+"'"
							+ " AND A.SN_REV_NO = '"+temp_sn_rev_no+"'"
							+ " AND A.PLANT_SEQ_NO = '"+temp_plant_seq_no+"'"
							+ " and A.GAS_DT between to_date('"+v2ndFort.elementAt(i)+"','dd/mm/yyyy') and to_date('"+v2ndFort_1.elementAt(i)+"','dd/mm/yyyy')"
							+ " and A.NOM_REV_NO = (select max(B.nom_rev_no) from  FMS7_DAILY_ALLOCATION_DTL  B"
							+ " where A.customer_cd = B.customer_cd and  A.sn_no = B.sn_no and A.sn_rev_no = B.sn_rev_no and A.plant_seq_no = B.plant_seq_no and B.contract_type = 'K')"
							+ " group by A.CONTRACT_TYPE,A.CUSTOMER_CD,A.SN_NO,A.SN_REV_NO,A.PLANT_SEQ_NO, A.GAS_DT,A.NOM_REV_NO"
							+ " order by  A.customer_cd,A.sn_no,A.sn_rev_no,A.plant_seq_no,A.GAS_DT";
					}
//					System.out.println("fetchAllocSql* 2nd**"+fetchAllocSql);
					rset1 = stmt1.executeQuery(fetchAllocSql);
					while (rset1.next()) {
						
						subRecord++;
						VIsub_alloc_qty.add(rset1.getString(1)==null?"":rset1.getString(1));
						VIsub_alloc_dt.add(rset1.getString(2)==null?"":rset1.getString(2));
						String alloc_dt = rset1.getString(2)==null?"":rset1.getString(2);
						double sub_alloc_qty = rset1.getDouble(1);
						
						aggr_qty+=sub_alloc_qty;
						double sub_transp_charge = 0 ;
						if(agr_base.equalsIgnoreCase("D")) {
							sub_transp_charge = (sub_alloc_qty * transporation_charge);
						}
						double sub_gross = sub_alloc_qty * sales_rate;
						VIsub_Gross_amt.add(sub_gross + sub_transp_charge);
						double total_sub_gross = sub_gross + sub_transp_charge;
						double trans_gross = (rset1.getDouble(1)*trans_perc);
						VIsub_Trans_charge.add(trans_gross);
						VIsub_Trans_sgst.add((trans_gross*sgst)/100);
						VIsub_Trans_cgst.add((trans_gross*cgst)/100);
						double sub_net_pay = sub_gross + sub_transp_charge;// - (trans_gross + ((trans_gross*sgst)/100) + ((trans_gross*cgst)/100));

//						VIsub_due_dt.add(rset1.getString(3)==null?"":rset1.getString(3));
						VIsub_nom_rev_no.add(rset1.getString(4)==null?"":rset1.getString(4));
						
						String sub_due_dt = rset1.getString(3)==null?"":rset1.getString(3);
						String sub_pay_dt = "";
						
						String nom_rev_no = rset1.getString(4)==null?"":rset1.getString(4);
						/* fetching already submitted data */
						String fetchPaySql = "select nvl(GROSS_TDS_PERC,0),nvl(GROSS_TDS_AMT,0),nvl(NET_RECV,0),nvl(ACTUAL_RECV,0),"
								+ " nvl(SHORT_RECV,0),to_char(PAYMENT_DT,'dd/mm/yyyy'),REMARK,TAX_TDS_PERC,nvl(TAX_TDS_AMT,0)"
								+ " from FMS10_IGX_PAYMENT_DTL A"
								+ " where A.customer_cd = '"+temp_cust_cd+"'"
								+ " and A.cont_no = '"+temp_sn+"' "
								+ " and A.cont_rev_no = '"+temp_sn_rev_no+"'"
								+ " and A.plant_seq_no = '"+temp_plant_seq_no+"'"
								+ " and A.gas_dt = to_date('"+alloc_dt+"','dd/mm/yyyy')"
								+ " and A.nom_rev_no = '"+nom_rev_no+"'"
								+ " and A.contract_type = 'K'"
								+ " and A.rev_no = (select max(B.rev_no) from FMS10_IGX_PAYMENT_DTL B where"
									+ " A.customer_cd = B.customer_cd and  A.cont_no = B.cont_no "
									+ " and A.cont_rev_no = B.cont_rev_no and A.plant_seq_no = B.plant_seq_no "
									+ " and B.contract_type = 'K' and A.gas_dt = B.gas_dt)";
						
//						System.out.println("fetchPaySql****"+fetchPaySql);
						rset2 = stmt2.executeQuery(fetchPaySql);
						if(rset2.next() ) {
							allocRecord++;
							VIsub_gross_tds_perc.add(rset2.getString(1)==null?"":rset2.getString(1));
							VIsub_gross_tds_amt.add(rset2.getString(2)==null?"":rset2.getString(2));
							total_tds_amt+=rset2.getDouble(2);
							VIsub_net_recv.add(rset2.getString(3)==null?"":rset2.getString(3));
							VIsub_actual_recv.add(rset2.getString(4)==null?"":rset2.getString(4));
							VIsub_short_recv.add(rset2.getString(5)==null?"":rset2.getString(5));
							VIsub_payment_dt.add(rset2.getString(6)==null?"":rset2.getString(6));
							sub_pay_dt = rset2.getString(6)==null?"":rset2.getString(6);
							VIsub_remark.add(rset2.getString(7)==null?"":rset2.getString(7));
							VIsub_tax_tds_perc.add(rset2.getString(8)==null?"":rset2.getString(8));
							VIsub_tax_tds_amt.add(rset2.getString(9)==null?"":rset2.getString(9));
							total_tds_tax_val+=rset2.getDouble(9);
							alloc_sub_flg = "Y";
							sub_sum_act_recv+=rset2.getDouble(4);
							
						}else {
							VIsub_gross_tds_perc.add("");
							VIsub_gross_tds_amt.add("");
							VIsub_net_recv.add("");
							VIsub_actual_recv.add("");
							VIsub_short_recv.add("");
							VIsub_payment_dt.add("");
							VIsub_remark.add("");
							VIsub_tax_tds_perc.add("");
							VIsub_tax_tds_amt.add("");
						}
						
						/* fetching due days */
						String subDueDays = "";
//						System.out.println("sub_due_dt****"+sub_due_dt+"**sub_due_dt***"+sub_due_dt);
						if(!sub_due_dt.equalsIgnoreCase("") && !sub_pay_dt.equalsIgnoreCase("") ) {
							
							subDueDays = extractDays(sub_due_dt,sub_pay_dt);
						}
						
						VIsub_due_days.add(subDueDays);
						String due_date = calculate_due_date(rset1.getString(3),alloc_dt);
						VIsub_due_dt.add(due_date);
						
						//Fetching Tax Amount
						fetch_igx_tax_dtl (temp_cust_cd,temp_plant_seq_no,alloc_dt,total_sub_gross);
						VIsub_tax_amt.add(tax_amt);
						VIsub_tax_str.add(tax_line);
						double act_net_amt = sub_net_pay + tax_amt;
						VIsub_net_payable.add(act_net_amt) ;
						
					}
					
//					VIinv_sub_sum_act_recv.add(sub_sum_act_recv);
					VIinv_alloc_sub_flg.add(alloc_sub_flg);
					VIsub_rec_cnt.add(subRecord);
					VIaggr_alloc_qty.add(aggr_qty);
					double gross_amt = aggr_qty*sales_rate;
					double trans_charge = (aggr_qty*trans_perc); 
					VITrans_charge.add(trans_charge);
					VITrans_cgst.add((trans_charge*cgst)/100);
					VITrans_sgst.add((trans_charge*sgst)/100);
					double total_trans_charge = trans_charge+((trans_charge*cgst)/100) + ((trans_charge*sgst)/100);
					VITrans_tot_amt.add(total_trans_charge);
					VIAllocCnt.add(allocRecord);
//					System.out.println("subRecord----"+subRecord);
					inv_gross_amt_inr = 0 ;
					inv_gen_flag = "N";
					
					if(agr_base.equalsIgnoreCase("D")) {
						transp_charge = (aggr_qty * transporation_charge);
					}
					
					inv_gen_flag = fetchingInvoiceDetails(temp_cust_cd,temp_sn,temp_sn_rev_no,temp_plant_seq_no,v2ndFort.elementAt(i)+"",v2ndFort_1.elementAt(i)+"",gross_amt);
//					System.out.println("inv_gross_amt_inr*****"+inv_gross_amt_inr);
					if(inv_gen_flag.equalsIgnoreCase("Y")) {
						VIGross_amt.add(inv_gross_amt_inr + transp_charge);
					}else {
						VIGross_amt.add(gross_amt + transp_charge);
					}
					VIinv_sub_sum_act_recv.add(nf.format(sub_sum_act_recv));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	double tax_amt = 0 ;
	String tax_line = "";
	private void fetch_igx_tax_dtl(String cust_cd, String plant_seq_no, String alloc_dt, double sub_gross)throws Exception {
		try {
			

			
			String nTax_Structure_dtl = "",nTax_struct_cd = "";
			tax_amt = 0 ;String tax_str = "";tax_line = "";
			
			queryString = "SELECT A.TAX_STRUCT_DTL,A.TAX_STRUCT_CD FROM FMS7_CUSTOMER_TAX_STRUCT_DTL A WHERE " +
					  "A.customer_cd="+cust_cd+" AND " +
					  "A.plant_seq_no="+plant_seq_no+" AND " +
			 		  "A.tax_struct_dt=(SELECT MAX(B.tax_struct_dt) FROM FMS7_CUSTOMER_TAX_STRUCT_DTL B WHERE " +
			 		  "A.customer_cd=B.customer_cd AND A.plant_seq_no=B.plant_seq_no AND " +
			 		  "B.tax_struct_dt<=TO_DATE('"+alloc_dt+"','DD/MM/YYYY'))";
//			System.out.println("queryString---"+queryString);
			rset2 = stmt2.executeQuery(queryString);
			if(rset2.next())
			{
				nTax_Structure_dtl = rset2.getString(1)==null?"":rset2.getString(1);
				nTax_struct_cd = rset2.getString(2)==null?"0":rset2.getString(2);
			}
			
			int tax_factor = 0;
			String tax_code="";
			String tax_abbr="";
//			System.out.println("--invoice_amt---"+invoice_amt);
			queryString = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0.00'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
					  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+nTax_struct_cd+"' AND " +
					  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+nTax_struct_cd+"' AND " +
					  "B.app_date<=TO_DATE('"+alloc_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code";
//			System.out.println("STEP-1A.3:FMS7_TAX_STRUCTURE_DTL: "+queryString);
			rset3=stmt3.executeQuery(queryString);
			while(rset3.next())
			{
				tax_factor= Integer.parseInt((rset3.getString(2)));
				tax_code=rset3.getString(1)==null?"":rset3.getString(1);
				if(rset3.getString(3).equals("1"))
				{
					tax_amt = (sub_gross*Double.parseDouble(rset3.getString(2)))/100;
//					System.out.println("invoice_amt***"+invoice_amt+"**"+rset3.getString(2));
				}
				else if(rset3.getString(3).equals("2"))
				{
					String queryString1 = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
							  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+nTax_struct_cd+"' AND " +
							  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+nTax_struct_cd+"' AND " +
							  "B.app_date<=TO_DATE('"+alloc_dt+"','DD/MM/YYYY')) AND A.tax_code="+rset3.getString(4)+"";
//				System.out.println("Query For Finding Out Tax Which Is Dependent On Other Tax Value = "+queryString1);
			 		rset4=stmt4.executeQuery(queryString1);
			 		if(rset4.next())
			 		{
			 			if(rset4.getString(3).equals("1"))
						{
							//tax_amt = (Double.parseDouble(customer_Invoice_Gross_Amt_INR)*Double.parseDouble(rset1.getString(2)))/100;
			 				tax_amt = (sub_gross*Double.parseDouble(rset4.getString(2)))/100;
						}
					
		 			tax_amt = (tax_amt*Double.parseDouble(rset3.getString(2)))/100;
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
				queryString = "SELECT sht_nm,tax_name FROM FMS7_TAX_MST WHERE " +
						  "tax_code='"+tax_code+"'";
//				System.out.println("queryString**"+queryString);
				rset4 = stmt4.executeQuery(queryString);
				if(rset4.next())
				{
					tax_abbr = rset4.getString(1).trim()==null?"":rset4.getString(1).trim();
				}
				tax_str=nf.format(Math.round(tax_amt))+"";
				tax_line=tax_abbr+" @ "+tax_factor+" %";
//				System.out.println("tax_line***"+tax_line);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public String calculate_due_date(String date,String alloc_dt)throws SQLException,IOException {
		
		String due_dt = "";
		String WeeklyOff = "";
//		System.out.println("date***"+date);
		try {
			queryString = "SELECT TO_CHAR(TO_DATE('"+date+"','DD/MM/YYYY'),'DAY') FROM DUAL";
//			System.out.println("Re checking Due date Get DAY Name: "+queryString);
            rset2 = stmt2.executeQuery(queryString);
            if(rset2.next()){
            	WeeklyOff = rset2.getString(1)==null?"":rset2.getString(1);
            }
            
//            System.out.println("WeeklyOff****"+WeeklyOff);
            if(WeeklyOff.trim().equalsIgnoreCase("SUNDAY")) {
            	
				/* check for 2nd or 4th saturday */
            	String satDt = "";
            	String satFallDt = "";
            	int dayCnt = 1;
            	
            	String satSql="select to_char(to_date('"+date+"','dd/mm/yyyy')- "+1+",'dd/mm/yyyy') from dual";
//                System.out.println("satSql*****"+satSql);
	        	rset2=stmt2.executeQuery(satSql);
	            if(rset2.next()){
	            	satDt = rset2.getString(1)==null?"":rset2.getString(1);
	             }
	            
	            String st_dt [] = date.split("/");
            	String start_dt = "01/"+st_dt[1]+"/"+st_dt[2];
            	String secSat = "",forSat = "";
				/* checking for 2nd Saturday */
            	String chk2ndSatSql = "SELECT to_char(NEXT_DAY(TRUNC(TO_DATE(TO_CHAR('"+start_dt+"'),'dd/mm/yyyy'),'MONTH')+6,'SATURDAY'),'dd/mm/yyyy') from dual";
//            	System.out.println("chk2ndSatSql***"+chk2ndSatSql);
            	rset2 = stmt2.executeQuery(chk2ndSatSql);
            	if(rset2.next()){
            		secSat = rset2.getString(1)==null?"":rset2.getString(1);
//            		System.out.println("satDt2nd"+satDt+"****secSat**"+secSat);
            		if(satDt.equalsIgnoreCase(secSat)) {
            			dayCnt = 1;
            		}
            	}
//            	System.out.println("due_dt*2nd**"+due_dt);
            	/* checking for 4th Saturday */
            	if(dayCnt == 1) { // if no record found from 2nd Saturday
	            	String chk4thSatSql = "SELECT to_char(NEXT_DAY(TRUNC(TO_DATE(TO_CHAR('"+start_dt+"'),'dd/mm/yyyy'),'MONTH')+20,'SATURDAY'),'dd/mm/yyyy') from dual"; 
//	            	System.out.println("chk4thSatSql***"+chk4thSatSql);
	            	rset2 = stmt2.executeQuery(chk4thSatSql);
	            	if(rset2.next()){
	            		forSat = rset2.getString(1)==null?"":rset2.getString(1);
//	            		System.out.println("satDt4th"+satDt+"****forSat**"+forSat);
	            		if(satDt.equalsIgnoreCase(forSat)) {
	            			dayCnt = 1;
	            		}
	            	}
            	}
//            	System.out.println("dayCnt*****"+dayCnt);
            	/////////////////////////////////////////
            	String query1="select to_char(to_date('"+date+"','dd/mm/yyyy')+ "+dayCnt+",'dd/mm/yyyy') from dual";
//                    System.out.println("query1*****"+query1);
            	rset2=stmt2.executeQuery(query1);
                if(rset2.next()){
                	 due_dt = rset2.getString(1)==null?"":rset2.getString(1);
                 }
           
            }else if(WeeklyOff.trim().equalsIgnoreCase("SATURDAY")) {
            	
            	String st_dt [] = date.split("/");
            	String start_dt = "01/"+st_dt[1]+"/"+st_dt[2];
            	String secSat = "",forSat = "";
            	
				/* checking for Thrusday */
            	String thrusDay = "";
            	int dayCnt = 0;
            	queryString = "SELECT TO_CHAR(TO_DATE('"+alloc_dt+"','DD/MM/YYYY'),'DAY') FROM DUAL";
//    			System.out.println("Re checking Thrusday Get DAY Name: "+queryString);
                rset2 = stmt2.executeQuery(queryString);
                if(rset2.next()){
                	thrusDay = rset2.getString(1)==null?"":rset2.getString(1);
                }
//                System.out.println("thrusDay*****"+thrusDay);
            	if(thrusDay.trim().equalsIgnoreCase("THURSDAY")) {
            		dayCnt = 2;
            	}else {
            		dayCnt = 2;
            	}
				/* checking for 2nd Saturday */
            	String chk2ndSatSql = "SELECT to_char(NEXT_DAY(TRUNC(TO_DATE(TO_CHAR('"+start_dt+"'),'dd/mm/yyyy'),'MONTH')+6,'SATURDAY'),'dd/mm/yyyy') from dual";
            	rset2 = stmt2.executeQuery(chk2ndSatSql);
            	if(rset2.next()){
            		secSat = rset2.getString(1)==null?"":rset2.getString(1);
//            		System.out.println("date**"+date+"****secSat**"+secSat);
            		if(date.equalsIgnoreCase(secSat)) {
            			String query1="select to_char(to_date('"+date+"','dd/mm/yyyy')+ "+dayCnt+",'dd/mm/yyyy') from dual";
//                        System.out.println("query1**2nd***"+query1);
            			rset3=stmt3.executeQuery(query1);
            			if(rset3.next()){
            				due_dt = rset3.getString(1)==null?"":rset3.getString(1);
	                    }
            		}
            	}
//            	System.out.println("due_dt*2nd**"+due_dt);
            	/* checking for 4th Saturday */
            	if(due_dt.equalsIgnoreCase("")) {
	            	String chk4thSatSql = "SELECT to_char(NEXT_DAY(TRUNC(TO_DATE(TO_CHAR('"+start_dt+"'),'dd/mm/yyyy'),'MONTH')+20,'SATURDAY'),'dd/mm/yyyy') from dual"; 
//	            	System.out.println("chk4thSatSql***"+chk4thSatSql);
	            	rset2 = stmt2.executeQuery(chk4thSatSql);
	            	if(rset2.next()){
	            		forSat = rset2.getString(1)==null?"":rset2.getString(1);
	            		if(date.equalsIgnoreCase(forSat)) {
	            			String query1="select to_char(to_date('"+date+"','dd/mm/yyyy')+ "+dayCnt+",'dd/mm/yyyy') from dual";
//	            			System.out.println("query1**4th***"+query1);
	            			rset3=stmt3.executeQuery(query1);
	            			if(rset3.next()){
	            				due_dt = rset3.getString(1)==null?"":rset3.getString(1);
		                    }
	            		}
	            	}
            	}
//            	System.out.println("WeeklyOff*sat***"+WeeklyOff+"***date**"+date);
            }
            if(due_dt.equalsIgnoreCase("")) { //for supplied on 1st,3rd,5th saturday
            	String satDay = "";
            	queryString = "SELECT TO_CHAR(TO_DATE('"+alloc_dt+"','DD/MM/YYYY'),'DAY') FROM DUAL";
//    			System.out.println("Re checking SATURDAY Get DAY Name: "+queryString);
                rset2 = stmt2.executeQuery(queryString);
                if(rset2.next()){
                	satDay = rset2.getString(1)==null?"":rset2.getString(1);
                }
                if(satDay.trim().equalsIgnoreCase("SATURDAY")) {
                	String query1="select to_char(to_date('"+date+"','dd/mm/yyyy')+ 1,'dd/mm/yyyy') from dual";
//        			System.out.println("query1**4th***"+query1);
        			rset3=stmt3.executeQuery(query1);
        			if(rset3.next()){
        				due_dt = rset3.getString(1)==null?"":rset3.getString(1);
                    }
                }
            }
//            System.out.println("due_dt****"+due_dt);
            if(due_dt.equalsIgnoreCase("")){
            	due_dt = date;
            }
//			System.out.println("due_dt***********"+due_dt);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return due_dt;
	}
	Vector VItax_code=new Vector();	//BK20160209
	Vector VItax_amt=new Vector();
	Vector VItaxnm=new Vector();
	Vector VItax_nm=new Vector();
	Vector VItaxcnt = new Vector();
	Vector VItcs_amt = new Vector();
	Vector VInet_recv = new Vector();
	
	String Itcs_nm="";
	String Itcs_cd="";
	String Ifact="";
	String Itcs_sht_nm="";
	String Itcs_fact="";
	String inv_gen_flag = "N";
	String exg_rate = "";
	double inv_gross_amt_inr = 0 ;
	
	private String fetchingInvoiceDetails(String cust_cd,String sn_no,String sn_rev_no,String plant_no,String from_dt,String to_date,double gross_amt_inr )throws SQLException,IOException {
		try {
			
			inv_gross_amt_inr = 0 ;
//			System.out.println("sub_sum_act_recv****"+sub_sum_act_recv);
			String tax_str_cd = "";
			String new_inv_seq_no = "";
			String financial_year = "";
			String hlpl_inv_seq_no = "";
			String flag = "";
			String sup_state_code = "";
			String pay_recv_amt = "0";
			String pay_recv_dt = "",due_dt = "";
			String pay_rmk = "";
			String tds_tax_perc = "";
			String tds_tax_amt = "0";
			double short_recv = 0 ;
			String dueDays = "";
			inv_gen_flag = "N";
			double gross_tds_amt = 0 ;
			double gross_tds_perc = 0 ;
			String inv_gen_email =  "";
			String inv_chk_email =  "";
			String inv_aprv_email =  "";
			String print_email_ori =  "";
			String print_email_dup =  "";
			String print_email_trip = "";
			
			String invDtlSql = "select NEW_INV_SEQ_NO,to_char(INVOICE_DT,'dd/mm/yyyy'),"
					+ " TAX_STRUCT_CD,FINANCIAL_YEAR,nvl(NET_AMT_INR,'0'),to_char(DUE_DT,'dd/mm/yyyy'),"
					+ " HLPL_INV_SEQ_NO,FLAG,SUP_STATE_CODE,nvl(PAY_RECV_AMT,0),to_char(PAY_RECV_DT,'dd/mm/yyyy'),"
					+ " PAY_REMARK,nvl(TDS_TAX_PERCENT,0),nvl(TDS_TAX_AMT,0),"
					+ " (select email_id from hr_emp_mst where emp_cd = a.EMP_CD) as inv_gen_by,"
					+ " (select email_id from hr_emp_mst where emp_cd = a.CHECKED_BY) as inv_chk_by,"
					+ " (select email_id from hr_emp_mst where emp_cd = a.APPROVED_BY) as inv_aprv_by,"
					+ " (select email_id from hr_emp_mst where emp_cd = a.PRINT_BY_ORI) as print_by_ori,"
					+ " (select email_id from hr_emp_mst where emp_cd = a.PRINT_BY_DUP) as print_by_dup,"
					+ " (select email_id from hr_emp_mst where emp_cd = a.PRINT_BY_TRI) as print_by_trip"
					+ " ,nvl(gross_amt_inr,0),nvl(total_qty,0),"
					+ " nvl(GROSS_TDS_PERC,0),nvl(GROSS_TDS_AMT,0)"
					+ " from FMS7_INVOICE_MST a"
					+ " where CUSTOMER_CD = '"+cust_cd+"' "
					+ " and SN_NO = '"+sn_no+"' and SN_REV_NO = '"+sn_rev_no+"'"
					+ " and PLANT_SEQ_NO = '"+plant_no+"' "
					+ " and (PERIOD_START_DT between to_date('"+from_dt+"','dd/mm/yyyy') and to_date('"+to_date+"','dd/mm/yyyy')) "
					+ " and (PERIOD_END_DT between to_date('"+from_dt+"','dd/mm/yyyy') and to_date('"+to_date+"','dd/mm/yyyy') )" 
//					+ " and INVOICE_DT between to_date('"+from_dt+"','dd/mm/yyyy') and to_date('"+to_date+"','dd/mm/yyyy')"
					+ " and CONTRACT_TYPE = 'K'"
					+ " and APPROVED_FLAG = 'Y'"
					+ " and CHECKED_FLAG = 'Y'"
					+ " and FLAG!='X'"
					+ " and (PDF_INV_DTL IS NOT NULL) ";
			System.out.println("invDtlSql----"+invDtlSql);
			rset1 = stmt1.executeQuery(invDtlSql);
			if(rset1.next()) {
				inv_gen_flag = "Y";
				VIinv_no.add(rset1.getString(1) == null?"":rset1.getString(1));
				VIinv_dt.add(rset1.getString(2) == null?"":rset1.getString(2));
				tax_str_cd = rset1.getString(3) == null?"":rset1.getString(3);
				new_inv_seq_no = rset1.getString(1) == null?"":rset1.getString(1);
				financial_year = rset1.getString(4) == null?"":rset1.getString(4);
				VIinv_net_inr.add(rset1.getString(5) == null?"":rset1.getString(5));
				VIinv_due_dt.add(rset1.getString(6) == null?"":rset1.getString(6));
				due_dt = rset1.getString(6) == null?"":rset1.getString(6);
				hlpl_inv_seq_no = rset1.getString(7) == null?"":rset1.getString(7);
				flag = rset1.getString(8) == null?"":rset1.getString(8);
				sup_state_code = rset1.getString(9) == null?"":rset1.getString(9);
				pay_recv_amt = rset1.getString(10) == null?"0":rset1.getString(10);
				pay_recv_dt = rset1.getString(11) == null?"":rset1.getString(11);
				pay_rmk = rset1.getString(12) == null?"":rset1.getString(12);
				tds_tax_perc = rset1.getString(13) == null?"":rset1.getString(13);
				tds_tax_amt = rset1.getString(14) == null?"0":rset1.getString(14);
				inv_gen_email = rset1.getString(15) == null?"":rset1.getString(15);
				inv_chk_email = rset1.getString(16) == null?"":rset1.getString(16);
				inv_aprv_email = rset1.getString(17) == null?"":rset1.getString(17);
				print_email_ori = rset1.getString(18) == null?"":rset1.getString(18);
				print_email_dup = rset1.getString(19) == null?"":rset1.getString(19);
				print_email_trip = rset1.getString(20) == null?"":rset1.getString(20);
				inv_gross_amt_inr = rset1.getDouble(21);
				gross_amt_inr = rset1.getDouble(21);
				gross_tds_perc = rset1.getDouble(23);
				gross_tds_amt = rset1.getDouble(24);
//				System.out.println("agr_base****"+agr_base+"**transporation_charge**"+transporation_charge);
//				System.out.println("transp_charge***"+transp_charge);
			}else {
				VIinv_no.add("");
				VIinv_dt.add("");
				VIinv_net_inr.add("0");
				VIinv_due_dt.add("");
			}
			if(gross_tds_perc > 0 ) {
				VIinv_total_tds_amt.add(gross_tds_amt);
			}else {
				VIinv_total_tds_amt.add(total_tds_amt);
			}
			
			VIinv_gross_tds_perc.add(gross_tds_perc);
			VIinv_gen_email.add(inv_gen_email);
			VIinv_chk_email.add(inv_chk_email);
			VIinv_aprv_email.add(inv_aprv_email);
			
			if(!print_email_trip.equalsIgnoreCase("")){
				VIinv_print_email.add(print_email_trip);
			}else if(!print_email_dup.equalsIgnoreCase("")){
				VIinv_print_email.add(print_email_dup);
			}else if(!print_email_ori.equalsIgnoreCase("")){
				VIinv_print_email.add(print_email_ori);
			}else {
				VIinv_print_email.add("-");
			}
			/* fetching paid details */
			String payDtlSql = "select nvl(A.SHORT_RECV_AMT,0) from FMS8_PAY_RECV_DTL A where"
					+ " A.NEW_INV_SEQ_NO = '"+new_inv_seq_no+"'"
					+ " and A.CONTRACT_TYPE = 'K' "
					+ " and A.COMMODITY_TYPE = 'RLNG'"
					+ " and A.REV_NO = (select max(B.rev_no) from FMS8_PAY_RECV_DTL B where "
						+ "	B.new_inv_seq_no='"+new_inv_seq_no+"' "
						+ " and B.contract_type='K' and B.commodity_type='RLNG' )";
//			System.out.println("payDtlSql----"+payDtlSql);
			rset1 = stmt1.executeQuery(payDtlSql);
			if(rset1.next()) {
				short_recv = rset1.getDouble(1);
			}
			
			/* fetching due days */
			if(!due_dt.equalsIgnoreCase("") && !pay_recv_dt.equalsIgnoreCase("") ) {
				
				dueDays = extractDays(due_dt,pay_recv_dt);
			}
			
			if(Double.parseDouble(pay_recv_amt) > 0 ) {
				VIinv_pay_recv_amt.add(nf.format(Double.parseDouble(pay_recv_amt+"")));
				VIinv_pay_short_recv.add(nf.format(short_recv));
				
			}else {
				VIinv_pay_recv_amt.add(nf.format(Double.parseDouble(sub_sum_act_recv+"")));
				VIinv_pay_short_recv.add(nf.format(short_recv));
			}
			VIinv_due_days.add(dueDays);
			VIinv_hlpl_no.add(hlpl_inv_seq_no);
			VIinv_fin_yr.add(financial_year);
			VIinv_flag.add(flag);
			VIinv_supp_state_cd.add(sup_state_code);
			
			VIinv_pay_recv_dt.add(pay_recv_dt);
			VIinv_pay_rmk.add(pay_rmk);
			VIinv_tds_tax_perc.add(tds_tax_perc);
			if(Double.parseDouble(tds_tax_amt+"") > 0) {
				VIinv_tds_tax_amt.add(tds_tax_amt);
			}else {
				VIinv_tds_tax_amt.add(total_tds_tax_val);
			}
			
			String tx_cd="",tx_amt="",tx_nm="";
			String tax_cd="";
			String tax_on="";
			String tax_nm="";
			double tax_amt=0;
			
			String fetchingTaxSql = "SELECT NVL(A.tax_code,'0'), NVL(A.factor,'0'), NVL(A.tax_on,'1'), NVL(A.tax_on_cd,'0'), " +
					  "TO_CHAR(A.app_date,'DD/MM/YYYY') FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
					  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
					  "B.app_date<=TO_DATE('"+from_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
//			System.out.println("fetchingTaxSql---"+fetchingTaxSql);
			rset1 = stmt1.executeQuery(fetchingTaxSql);
			if(rset1.next()) {
				
				tax_cd=rset1.getString(1);
				tax_on=rset1.getString(3);
				
				tax_amt = ((gross_amt_inr + transp_charge) *Double.parseDouble(rset1.getString(2)))/100;

				
				String taxNmSql="select sht_nm from fms7_tax_mst where tax_code='"+tax_cd+"'";
				rset2=stmt2.executeQuery(taxNmSql);
				while(rset2.next())
				{
					tax_nm=rset2.getString(1);
				}
				
				tx_cd+="@"+tax_cd;
				tx_amt+="@"+nf.format(tax_amt);
				tx_nm+="@"+tax_nm;
			}
			VItax_code.add(tx_cd.replaceFirst("@", ""));  	
			VItax_nm.add(tx_nm.replaceFirst("@", ""));
			VItax_amt.add(tx_amt.replaceFirst("@", ""));
//			System.out.println("VItax_code****"+VItax_code);
//			System.out.println("VItax_nm****"+VItax_nm);
			String taxCntSql = "SELECT COUNT(*) FROM FMS7_TAX_STRUCTURE_DTL A WHERE A.tax_str_cd='"+tax_str_cd+"' AND " +
					  "A.app_date=(SELECT MAX(B.app_date) FROM FMS7_TAX_STRUCTURE_DTL B WHERE B.tax_str_cd='"+tax_str_cd+"' AND " +
					  "B.app_date<=TO_DATE('"+from_dt+"','DD/MM/YYYY')) ORDER BY A.tax_code ASC";
//			System.out.println("taxCntSql****"+taxCntSql);
			rset1=stmt1.executeQuery(taxCntSql);
			if(rset1.next()){
				VItaxcnt.add(rset1.getInt(1));
			}else {
				VItaxcnt.add("0");
			}
			
			/* TCS details */
			String queryStrr="Select tcs_amt from fms7_invoice_tcs_dtl "
					+ " where hlpl_inv_seq_no='"+hlpl_inv_seq_no+"' "
					+ " and financial_year='"+financial_year+"' "
					+ " and customer_cd='"+cust_cd+"'"
					+ " and contract_type='K' "
					+ " and  invoice_type='SALES' AND FLAG='Y'";
//			System.out.println("queryStrr****"+queryStrr);
			rset1=stmt1.executeQuery(queryStrr);
			if(rset1.next()){
				VItcs_amt.add(rset1.getString(1)==null?"":rset1.getString(1));
			}else{
				VItcs_amt.add("");
			}
			
			queryString="SELECT TAX_CODE,FACTOR FROM FMS7_TAX_STRUCTURE_DTL A WHERE TAX_STR_CD='22' AND APP_DATE=(SELECT MAX(B.APP_DATE) "
					+ "FROM FMS7_TAX_STRUCTURE_DTL B WHERE A.TAX_STR_CD=B.TAX_STR_CD AND B.TAX_STR_CD='22' )";
			rset1=stmt1.executeQuery(queryString);
			if(rset1.next()){
				
				String queryString1 = "SELECT sht_nm,tax_name FROM FMS7_TAX_MST WHERE " +
						  "tax_code="+rset1.getString(1)+"";
				//System.out.println("Query For Fetching Tax Name = "+queryString);
				rset2 = stmt2.executeQuery(queryString1);
				if(rset2.next())
				{
					Itcs_nm=rset2.getString(2)==null?"":rset2.getString(2);
					Itcs_sht_nm=rset2.getString(1)==null?"":rset2.getString(1);
					Itcs_nm=Itcs_sht_nm;
				}
				Itcs_fact=rset1.getString(2)==null?"":rset1.getString(2);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return inv_gen_flag;
	}

	private String extractDays(String dueDt,String payDt)throws SQLException,IOException {
		String dueDays = "";
		try {
			String dueDaySql = "select trunc(to_date('"+payDt+"','dd/mm/yyyy') - to_date('"+dueDt+"', 'DD/MM/YYYY')) as days from dual";
//			System.out.println("dueDaySql***"+dueDaySql);
			rset2 = stmt2.executeQuery(dueDaySql);
			if(rset2.next()) {
				dueDays = rset2.getString(1)==null?"":rset2.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dueDays;
	}

	public String getCallFlag() {
		return callFlag;
	}

	public void setCallFlag(String callFlag) {
		this.callFlag = callFlag;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTo_month() {
		return to_month;
	}

	public void setTo_month(String to_month) {
		this.to_month = to_month;
	}

	public String getTo_year() {
		return to_year;
	}

	public void setTo_year(String to_year) {
		this.to_year = to_year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Vector getVImonth() {
		return VImonth;
	}

	public void setVImonth(Vector vImonth) {
		VImonth = vImonth;
	}

	public Vector getVIcustomer_cd() {
		return VIcustomer_cd;
	}

	public Vector getVIcustomer_abbr() {
		return VIcustomer_abbr;
	}

	public Vector getVIcont_typ() {
		return VIcont_typ;
	}

	public Vector getVIcustomer_name() {
		return VIcustomer_name;
	}

	public Vector getVIsn_no() {
		return VIsn_no;
	}

	public Vector getVIsn_rev_no() {
		return VIsn_rev_no;
	}

	public Vector getVIplant_seq_no() {
		return VIplant_seq_no;
	}

	public Vector getVIplant_seq_nm() {
		return VIplant_seq_nm;
	}

	public Vector getVIperiod_st_dt() {
		return VIperiod_st_dt;
	}

	public Vector getVIperiod_end_dt() {
		return VIperiod_end_dt;
	}

	public Vector getVIsub_rec_cnt() {
		return VIsub_rec_cnt;
	}

	public Vector getVIsub_alloc_qty() {
		return VIsub_alloc_qty;
	}

	public Vector getVIaggr_alloc_qty() {
		return VIaggr_alloc_qty;
	}

	public Vector getVISales_rate() {
		return VISales_rate;
	}

	public Vector getVIGross_amt() {
		return VIGross_amt;
	}

	public Vector getVIsub_Gross_amt() {
		return VIsub_Gross_amt;
	}

	public Vector getVITrans_charge() {
		return VITrans_charge;
	}

	public Vector getVIsub_Trans_charge() {
		return VIsub_Trans_charge;
	}

	public Vector getVITrans_cgst() {
		return VITrans_cgst;
	}

	public Vector getVITrans_sgst() {
		return VITrans_sgst;
	}

	public Vector getVIsub_Trans_cgst() {
		return VIsub_Trans_cgst;
	}

	public Vector getVIsub_Trans_sgst() {
		return VIsub_Trans_sgst;
	}

	public Vector getVIinv_no() {
		return VIinv_no;
	}

	public Vector getVIinv_dt() {
		return VIinv_dt;
	}

	public Vector getVItax_code() {
		return VItax_code;
	}

	public Vector getVItax_amt() {
		return VItax_amt;
	}

	public Vector getVItaxnm() {
		return VItaxnm;
	}

	public Vector getVItax_nm() {
		return VItax_nm;
	}

	public Vector getVItaxcnt() {
		return VItaxcnt;
	}

	public String getItcs_nm() {
		return Itcs_nm;
	}

	public String getItcs_cd() {
		return Itcs_cd;
	}

	public String getIfact() {
		return Ifact;
	}

	public String getItcs_sht_nm() {
		return Itcs_sht_nm;
	}

	public String getItcs_fact() {
		return Itcs_fact;
	}

	public Vector getVItcs_amt() {
		return VItcs_amt;
	}

	public Vector getVIsub_alloc_dt() {
		return VIsub_alloc_dt;
	}

	public Vector getVIinv_net_inr() {
		return VIinv_net_inr;
	}

	public Vector getVIinv_due_dt() {
		return VIinv_due_dt;
	}

	public Vector getVIsub_due_dt() {
		return VIsub_due_dt;
	}

	public Vector getVInet_recv() {
		return VInet_recv;
	}

	public Vector getVITrans_tot_amt() {
		return VITrans_tot_amt;
	}

	public Vector getVIsub_nom_rev_no() {
		return VIsub_nom_rev_no;
	}

	public Vector getVIsub_gross_tds_perc() {
		return VIsub_gross_tds_perc;
	}

	public Vector getVIsub_gross_tds_amt() {
		return VIsub_gross_tds_amt;
	}

	public Vector getVIsub_net_recv() {
		return VIsub_net_recv;
	}

	public Vector getVIsub_actual_recv() {
		return VIsub_actual_recv;
	}

	public Vector getVIsub_short_recv() {
		return VIsub_short_recv;
	}

	public Vector getVIsub_payment_dt() {
		return VIsub_payment_dt;
	}

	public Vector getVIsub_remark() {
		return VIsub_remark;
	}

	public Vector getVIinv_total_tds_amt() {
		return VIinv_total_tds_amt;
	}

	public Vector getVIinv_alloc_sub_flg() {
		return VIinv_alloc_sub_flg;
	}

	public Vector getVIinv_hlpl_no() {
		return VIinv_hlpl_no;
	}

	public Vector getVIinv_fin_yr() {
		return VIinv_fin_yr;
	}

	public Vector getVIinv_flag() {
		return VIinv_flag;
	}

	public Vector getVIinv_supp_state_cd() {
		return VIinv_supp_state_cd;
	}

	public Vector getVIinv_pay_recv_amt() {
		return VIinv_pay_recv_amt;
	}

	public Vector getVIinv_pay_recv_dt() {
		return VIinv_pay_recv_dt;
	}
	public Vector getVIinv_tds_tax_amt() {
		return VIinv_tds_tax_amt;
	}

	public Vector getVIinv_pay_rmk() {
		return VIinv_pay_rmk;
	}

	public Vector getVIinv_tds_tax_perc() {
		return VIinv_tds_tax_perc;
	}

	public String getInvstatus() {
		return invstatus;
	}

	public void setInvstatus(String invstatus) {
		this.invstatus = invstatus;
	}

	public Vector getVIinv_view_flag() {
		return VIinv_view_flag;
	}

	public Vector getVIinv_pay_short_recv() {
		return VIinv_pay_short_recv;
	}

	public Vector getVIinv_due_days() {
		return VIinv_due_days;
	}

	public Vector getVIsub_due_days() {
		return VIsub_due_days;
	}


	public String getCustomer_cd() {
		return customer_cd;
	}


	public void setCustomer_cd(String customer_cd) {
		this.customer_cd = customer_cd;
	}

	public Vector getVIAllocCnt() {
		return VIAllocCnt;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Vector getVIinv_sub_sum_act_recv() {
		return VIinv_sub_sum_act_recv;
	}

	public Vector getVIinv_gen_email() {
		return VIinv_gen_email;
	}

	public Vector getVIinv_chk_email() {
		return VIinv_chk_email;
	}

	public Vector getVIinv_aprv_email() {
		return VIinv_aprv_email;
	}

	public Vector getVIinv_print_email() {
		return VIinv_print_email;
	}

	public String getTo_mon() {
		return to_mon;
	}

	public void setTo_mon(String to_mon) {
		this.to_mon = to_mon;
	}

	public String getPlant_seq_no() {
		return plant_seq_no;
	}

	public void setPlant_seq_no(String plant_seq_no) {
		this.plant_seq_no = plant_seq_no;
	}

	public String getExchg_rate() {
		return exchg_rate;
	}

	public void setExchg_rate(String exchg_rate) {
		this.exchg_rate = exchg_rate;
	}

	public String getFlag_accounting() {
		return flag_accounting;
	}

	public void setFlag_accounting(String flag_accounting) {
		this.flag_accounting = flag_accounting;
	}

	public Vector getDist_CARGO_REF_CD_IGX() {
		return Dist_CARGO_REF_CD_IGX;
	}

	public void setDist_CARGO_REF_CD_IGX(Vector dist_CARGO_REF_CD_IGX) {
		Dist_CARGO_REF_CD_IGX = dist_CARGO_REF_CD_IGX;
	}

	public Vector getDist_INVOICE_NO_IGX() {
		return Dist_INVOICE_NO_IGX;
	}

	public void setDist_INVOICE_NO_IGX(Vector dist_INVOICE_NO_IGX) {
		Dist_INVOICE_NO_IGX = dist_INVOICE_NO_IGX;
	}

	public Vector getCUSTOM_DUTY_DATE_RATE_NOTE_IGX() {
		return CUSTOM_DUTY_DATE_RATE_NOTE_IGX;
	}

	public void setCUSTOM_DUTY_DATE_RATE_NOTE_IGX(Vector cUSTOM_DUTY_DATE_RATE_NOTE_IGX) {
		CUSTOM_DUTY_DATE_RATE_NOTE_IGX = cUSTOM_DUTY_DATE_RATE_NOTE_IGX;
	}

	public Vector getCUSTOM_DUTY_INTEREST_NUMERIC_IGX() {
		return CUSTOM_DUTY_INTEREST_NUMERIC_IGX;
	}

	public void setCUSTOM_DUTY_INTEREST_NUMERIC_IGX(Vector cUSTOM_DUTY_INTEREST_NUMERIC_IGX) {
		CUSTOM_DUTY_INTEREST_NUMERIC_IGX = cUSTOM_DUTY_INTEREST_NUMERIC_IGX;
	}

	public double getCustom_duty_interest_total_inr_igx() {
		return custom_duty_interest_total_inr_igx;
	}

	public void setCustom_duty_interest_total_inr_igx(double custom_duty_interest_total_inr_igx) {
		this.custom_duty_interest_total_inr_igx = custom_duty_interest_total_inr_igx;
	}

	public Vector getCD_PAID_IGX() {
		return CD_PAID_IGX;
	}

	public void setCD_PAID_IGX(Vector cD_PAID_IGX) {
		CD_PAID_IGX = cD_PAID_IGX;
	}

	public Vector getCD_PAID_NUMERIC_IGX() {
		return CD_PAID_NUMERIC_IGX;
	}

	public void setCD_PAID_NUMERIC_IGX(Vector cD_PAID_NUMERIC_IGX) {
		CD_PAID_NUMERIC_IGX = cD_PAID_NUMERIC_IGX;
	}

	public double getPurchase_total_addl_cd_inr_igx() {
		return purchase_total_addl_cd_inr_igx;
	}

	public void setPurchase_total_addl_cd_inr_igx(double purchase_total_addl_cd_inr_igx) {
		this.purchase_total_addl_cd_inr_igx = purchase_total_addl_cd_inr_igx;
	}

	public Vector getCUSTOM_DUTY_INTEREST_IGX() {
		return CUSTOM_DUTY_INTEREST_IGX;
	}

	public void setCUSTOM_DUTY_INTEREST_IGX(Vector cUSTOM_DUTY_INTEREST_IGX) {
		CUSTOM_DUTY_INTEREST_IGX = cUSTOM_DUTY_INTEREST_IGX;
	}

	public Vector getARRIVAL_DATE_RATE_NOTE_IGX() {
		return ARRIVAL_DATE_RATE_NOTE_IGX;
	}

	public void setARRIVAL_DATE_RATE_NOTE_IGX(Vector aRRIVAL_DATE_RATE_NOTE_IGX) {
		ARRIVAL_DATE_RATE_NOTE_IGX = aRRIVAL_DATE_RATE_NOTE_IGX;
	}

	public double getPurchase_total_cost_inr_igx() {
		return purchase_total_cost_inr_igx;
	}

	public void setPurchase_total_cost_inr_igx(double purchase_total_cost_inr_igx) {
		this.purchase_total_cost_inr_igx = purchase_total_cost_inr_igx;
	}

	public double getPurchase_total_cd_inr_igx() {
		return purchase_total_cd_inr_igx;
	}

	public void setPurchase_total_cd_inr_igx(double purchase_total_cd_inr_igx) {
		this.purchase_total_cd_inr_igx = purchase_total_cd_inr_igx;
	}

	public Vector getGROUP_FOREIGN_EXCHG_RATE_IGX() {
		return GROUP_FOREIGN_EXCHG_RATE_IGX;
	}

	public void setGROUP_FOREIGN_EXCHG_RATE_IGX(Vector gROUP_FOREIGN_EXCHG_RATE_IGX) {
		GROUP_FOREIGN_EXCHG_RATE_IGX = gROUP_FOREIGN_EXCHG_RATE_IGX;
	}

	public Vector getFINAL_SELLER_INV_AMT_INR_NUMERIC_IGX() {
		return FINAL_SELLER_INV_AMT_INR_NUMERIC_IGX;
	}

	public void setFINAL_SELLER_INV_AMT_INR_NUMERIC_IGX(Vector fINAL_SELLER_INV_AMT_INR_NUMERIC_IGX) {
		FINAL_SELLER_INV_AMT_INR_NUMERIC_IGX = fINAL_SELLER_INV_AMT_INR_NUMERIC_IGX;
	}

	public Vector getGROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX() {
		return GROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX;
	}

	public void setGROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX(Vector gROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX) {
		GROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX = gROUP_FOREIGN_EXCHG_RATE_NUMERIC_IGX;
	}

	public Vector getFINAL_SELLER_INV_AMT_INR_IGX() {
		return FINAL_SELLER_INV_AMT_INR_IGX;
	}

	public void setFINAL_SELLER_INV_AMT_INR_IGX(Vector fINAL_SELLER_INV_AMT_INR_IGX) {
		FINAL_SELLER_INV_AMT_INR_IGX = fINAL_SELLER_INV_AMT_INR_IGX;
	}

	public Vector getCARGO_REF_CD_IGX() {
		return CARGO_REF_CD_IGX;
	}

	public void setCARGO_REF_CD_IGX(Vector cARGO_REF_CD_IGX) {
		CARGO_REF_CD_IGX = cARGO_REF_CD_IGX;
	}

	public Vector getContract_no_igx() {
		return contract_no_igx;
	}

	public void setContract_no_igx(Vector contract_no_igx) {
		this.contract_no_igx = contract_no_igx;
	}

	public Vector getContract_rev_no_igx() {
		return contract_rev_no_igx;
	}

	public void setContract_rev_no_igx(Vector contract_rev_no_igx) {
		this.contract_rev_no_igx = contract_rev_no_igx;
	}

	public Vector getPlant_cd_igx() {
		return plant_cd_igx;
	}

	public void setPlant_cd_igx(Vector plant_cd_igx) {
		this.plant_cd_igx = plant_cd_igx;
	}

	public Vector getACTUAL_UNLOADED_QTY_IGX() {
		return ACTUAL_UNLOADED_QTY_IGX;
	}

	public void setACTUAL_UNLOADED_QTY_IGX(Vector aCTUAL_UNLOADED_QTY_IGX) {
		ACTUAL_UNLOADED_QTY_IGX = aCTUAL_UNLOADED_QTY_IGX;
	}

	public Vector getACTUAL_UNLOADED_QTY_NUMERIC_IGX() {
		return ACTUAL_UNLOADED_QTY_NUMERIC_IGX;
	}

	public void setACTUAL_UNLOADED_QTY_NUMERIC_IGX(Vector aCTUAL_UNLOADED_QTY_NUMERIC_IGX) {
		ACTUAL_UNLOADED_QTY_NUMERIC_IGX = aCTUAL_UNLOADED_QTY_NUMERIC_IGX;
	}

	public Vector getEXCHG_RATE_VALUE_IGX() {
		return EXCHG_RATE_VALUE_IGX;
	}

	public void setEXCHG_RATE_VALUE_IGX(Vector eXCHG_RATE_VALUE_IGX) {
		EXCHG_RATE_VALUE_IGX = eXCHG_RATE_VALUE_IGX;
	}

	public Vector getINVOICE_AMT_IGX() {
		return INVOICE_AMT_IGX;
	}

	public void setINVOICE_AMT_IGX(Vector iNVOICE_AMT_IGX) {
		INVOICE_AMT_IGX = iNVOICE_AMT_IGX;
	}

	public Vector getINVOICE_TAX_AMT_IGX() {
		return INVOICE_TAX_AMT_IGX;
	}

	public void setINVOICE_TAX_AMT_IGX(Vector iNVOICE_TAX_AMT_IGX) {
		INVOICE_TAX_AMT_IGX = iNVOICE_TAX_AMT_IGX;
	}

	public Vector getACT_ARRV_MONTH_IGX() {
		return ACT_ARRV_MONTH_IGX;
	}

	public void setACT_ARRV_MONTH_IGX(Vector aCT_ARRV_MONTH_IGX) {
		ACT_ARRV_MONTH_IGX = aCT_ARRV_MONTH_IGX;
	}

	public Vector getINVOICE_AMT_TDS_IGX() {
		return INVOICE_AMT_TDS_IGX;
	}

	public void setINVOICE_AMT_TDS_IGX(Vector iNVOICE_AMT_TDS_IGX) {
		INVOICE_AMT_TDS_IGX = iNVOICE_AMT_TDS_IGX;
	}

	public Vector getINR_PER_MMBTU_NUMERIC_IGX() {
		return INR_PER_MMBTU_NUMERIC_IGX;
	}

	public void setINR_PER_MMBTU_NUMERIC_IGX(Vector iNR_PER_MMBTU_NUMERIC_IGX) {
		INR_PER_MMBTU_NUMERIC_IGX = iNR_PER_MMBTU_NUMERIC_IGX;
	}

	public Vector getCUSTOM_DUTY_USD_IGX() {
		return CUSTOM_DUTY_USD_IGX;
	}

	public void setCUSTOM_DUTY_USD_IGX(Vector cUSTOM_DUTY_USD_IGX) {
		CUSTOM_DUTY_USD_IGX = cUSTOM_DUTY_USD_IGX;
	}

	public Vector getCUSTOM_DUTY_USD_NUMERIC_IGX() {
		return CUSTOM_DUTY_USD_NUMERIC_IGX;
	}

	public void setCUSTOM_DUTY_USD_NUMERIC_IGX(Vector cUSTOM_DUTY_USD_NUMERIC_IGX) {
		CUSTOM_DUTY_USD_NUMERIC_IGX = cUSTOM_DUTY_USD_NUMERIC_IGX;
	}

	public double getPurchase_total_cd_usd_igx() {
		return purchase_total_cd_usd_igx;
	}

	public void setPurchase_total_cd_usd_igx(double purchase_total_cd_usd_igx) {
		this.purchase_total_cd_usd_igx = purchase_total_cd_usd_igx;
	}

	public Vector getADDL_CUSTOM_DUTY_USD_IGX() {
		return ADDL_CUSTOM_DUTY_USD_IGX;
	}

	public void setADDL_CUSTOM_DUTY_USD_IGX(Vector aDDL_CUSTOM_DUTY_USD_IGX) {
		ADDL_CUSTOM_DUTY_USD_IGX = aDDL_CUSTOM_DUTY_USD_IGX;
	}

	public Vector getADDL_CUSTOM_DUTY_USD_NUMERIC_IGX() {
		return ADDL_CUSTOM_DUTY_USD_NUMERIC_IGX;
	}

	public void setADDL_CUSTOM_DUTY_USD_NUMERIC_IGX(Vector aDDL_CUSTOM_DUTY_USD_NUMERIC_IGX) {
		ADDL_CUSTOM_DUTY_USD_NUMERIC_IGX = aDDL_CUSTOM_DUTY_USD_NUMERIC_IGX;
	}

	public Vector getINR_PER_MMBTU_IGX() {
		return INR_PER_MMBTU_IGX;
	}

	public void setINR_PER_MMBTU_IGX(Vector iNR_PER_MMBTU_IGX) {
		INR_PER_MMBTU_IGX = iNR_PER_MMBTU_IGX;
	}

	public double getPurchase_total_addl_cd_usd_igx() {
		return purchase_total_addl_cd_usd_igx;
	}

	public void setPurchase_total_addl_cd_usd_igx(double purchase_total_addl_cd_usd_igx) {
		this.purchase_total_addl_cd_usd_igx = purchase_total_addl_cd_usd_igx;
	}

	public double getPurchase_total_inv_value_usd_igx() {
		return purchase_total_inv_value_usd_igx;
	}

	public void setPurchase_total_inv_value_usd_igx(double purchase_total_inv_value_usd_igx) {
		this.purchase_total_inv_value_usd_igx = purchase_total_inv_value_usd_igx;
	}

	public Vector getCOST_OF_PURCHASE_USD_IGX() {
		return COST_OF_PURCHASE_USD_IGX;
	}

	public void setCOST_OF_PURCHASE_USD_IGX(Vector cOST_OF_PURCHASE_USD_IGX) {
		COST_OF_PURCHASE_USD_IGX = cOST_OF_PURCHASE_USD_IGX;
	}

	public Vector getCOST_OF_PURCHASE_USD_NUMERIC_IGX() {
		return COST_OF_PURCHASE_USD_NUMERIC_IGX;
	}

	public void setCOST_OF_PURCHASE_USD_NUMERIC_IGX(Vector cOST_OF_PURCHASE_USD_NUMERIC_IGX) {
		COST_OF_PURCHASE_USD_NUMERIC_IGX = cOST_OF_PURCHASE_USD_NUMERIC_IGX;
	}

	public double getPurchase_total_cost_usd_igx() {
		return purchase_total_cost_usd_igx;
	}

	public void setPurchase_total_cost_usd_igx(double purchase_total_cost_usd_igx) {
		this.purchase_total_cost_usd_igx = purchase_total_cost_usd_igx;
	}

	public Vector getACT_ARRV_DT_IGX() {
		return ACT_ARRV_DT_IGX;
	}

	public void setACT_ARRV_DT_IGX(Vector aCT_ARRV_DT_IGX) {
		ACT_ARRV_DT_IGX = aCT_ARRV_DT_IGX;
	}

	public Vector getInvoice_type_IGX() {
		return invoice_type_IGX;
	}

	public void setInvoice_type_IGX(Vector invoice_type_IGX) {
		this.invoice_type_IGX = invoice_type_IGX;
	}

	public Vector getINVOICE_NO_IGX() {
		return INVOICE_NO_IGX;
	}

	public void setINVOICE_NO_IGX(Vector iNVOICE_NO_IGX) {
		INVOICE_NO_IGX = iNVOICE_NO_IGX;
	}

	public Vector getINVOICE_DT_IGX() {
		return INVOICE_DT_IGX;
	}

	public void setINVOICE_DT_IGX(Vector iNVOICE_DT_IGX) {
		INVOICE_DT_IGX = iNVOICE_DT_IGX;
	}

	public Vector getCONF_PRICE_IGX() {
		return CONF_PRICE_IGX;
	}

	public void setCONF_PRICE_IGX(Vector cONF_PRICE_IGX) {
		CONF_PRICE_IGX = cONF_PRICE_IGX;
	}

	public Vector getCONF_PRICE_NUMERIC_IGX() {
		return CONF_PRICE_NUMERIC_IGX;
	}

	public void setCONF_PRICE_NUMERIC_IGX(Vector cONF_PRICE_NUMERIC_IGX) {
		CONF_PRICE_NUMERIC_IGX = cONF_PRICE_NUMERIC_IGX;
	}

	public Vector getDUE_DT_IGX() {
		return DUE_DT_IGX;
	}

	public void setDUE_DT_IGX(Vector dUE_DT_IGX) {
		DUE_DT_IGX = dUE_DT_IGX;
	}

	public Vector getCheck_flag_IGX() {
		return Check_flag_IGX;
	}

	public void setCheck_flag_IGX(Vector check_flag_IGX) {
		Check_flag_IGX = check_flag_IGX;
	}

	public Vector getAuthorize_flag_IGX() {
		return Authorize_flag_IGX;
	}

	public void setAuthorize_flag_IGX(Vector authorize_flag_IGX) {
		Authorize_flag_IGX = authorize_flag_IGX;
	}

	public Vector getApprove_flag_IGX() {
		return Approve_flag_IGX;
	}

	public void setApprove_flag_IGX(Vector approve_flag_IGX) {
		Approve_flag_IGX = approve_flag_IGX;
	}

	public Vector getDuration_igx() {
		return duration_igx;
	}

	public void setDuration_igx(Vector duration_igx) {
		this.duration_igx = duration_igx;
	}

	public Vector getQTY_MSG_IGX() {
		return QTY_MSG_IGX;
	}

	public void setQTY_MSG_IGX(Vector qTY_MSG_IGX) {
		QTY_MSG_IGX = qTY_MSG_IGX;
	}

	public double getPurchase_total_qty_igx() {
		return purchase_total_qty_igx;
	}

	public void setPurchase_total_qty_igx(double purchase_total_qty_igx) {
		this.purchase_total_qty_igx = purchase_total_qty_igx;
	}

	public double getPurchase_total_invoice_value_igx() {
		return purchase_total_invoice_value_igx;
	}

	public void setPurchase_total_invoice_value_igx(double purchase_total_invoice_value_igx) {
		this.purchase_total_invoice_value_igx = purchase_total_invoice_value_igx;
	}

	public double getPurchase_total_inv_value_inr_igx() {
		return purchase_total_inv_value_inr_igx;
	}

	public void setPurchase_total_inv_value_inr_igx(double purchase_total_inv_value_inr_igx) {
		this.purchase_total_inv_value_inr_igx = purchase_total_inv_value_inr_igx;
	}

	public double getPurchase_total_tax_inr_igx() {
		return purchase_total_tax_inr_igx;
	}

	public void setPurchase_total_tax_inr_igx(double purchase_total_tax_inr_igx) {
		this.purchase_total_tax_inr_igx = purchase_total_tax_inr_igx;
	}

	public String getCargo_ref_cd() {
		return cargo_ref_cd;
	}

	public void setCargo_ref_cd(String cargo_ref_cd) {
		this.cargo_ref_cd = cargo_ref_cd;
	}

	public String getTrader_cd() {
		return trader_cd;
	}

	public void setTrader_cd(String trader_cd) {
		this.trader_cd = trader_cd;
	}

	public Vector getVESSEL_NM_IGX() {
		return VESSEL_NM_IGX;
	}

	public void setVESSEL_NM_IGX(Vector vESSEL_NM_IGX) {
		VESSEL_NM_IGX = vESSEL_NM_IGX;
	}

	public Vector getTRADER_ABBR_IGX() {
		return TRADER_ABBR_IGX;
	}

	public void setTRADER_ABBR_IGX(Vector tRADER_ABBR_IGX) {
		TRADER_ABBR_IGX = tRADER_ABBR_IGX;
	}

	public Vector getUSD_VAL_INVOICE_IGX() {
		return USD_VAL_INVOICE_IGX;
	}

	public void setUSD_VAL_INVOICE_IGX(Vector uSD_VAL_INVOICE_IGX) {
		USD_VAL_INVOICE_IGX = uSD_VAL_INVOICE_IGX;
	}

	public Vector getTOTAL_CD_AMT_IGX() {
		return TOTAL_CD_AMT_IGX;
	}

	public void setTOTAL_CD_AMT_IGX(Vector tOTAL_CD_AMT_IGX) {
		TOTAL_CD_AMT_IGX = tOTAL_CD_AMT_IGX;
	}

	public Vector getUSD_PER_MMBTU_IGX() {
		return USD_PER_MMBTU_IGX;
	}

	public void setUSD_PER_MMBTU_IGX(Vector uSD_PER_MMBTU_IGX) {
		USD_PER_MMBTU_IGX = uSD_PER_MMBTU_IGX;
	}

	public Vector getTOTAL_PAID_REFUND_IGX() {
		return TOTAL_PAID_REFUND_IGX;
	}

	public void setTOTAL_PAID_REFUND_IGX(Vector tOTAL_PAID_REFUND_IGX) {
		TOTAL_PAID_REFUND_IGX = tOTAL_PAID_REFUND_IGX;
	}

	public Vector getSUN_APPROVAL_IGX() {
		return SUN_APPROVAL_IGX;
	}

	public void setSUN_APPROVAL_IGX(Vector sUN_APPROVAL_IGX) {
		SUN_APPROVAL_IGX = sUN_APPROVAL_IGX;
	}

	public Vector getTOTAL_CD_AMT_NUMERIC_IGX() {
		return TOTAL_CD_AMT_NUMERIC_IGX;
	}

	public void setTOTAL_CD_AMT_NUMERIC_IGX(Vector tOTAL_CD_AMT_NUMERIC_IGX) {
		TOTAL_CD_AMT_NUMERIC_IGX = tOTAL_CD_AMT_NUMERIC_IGX;
	}

	public Vector getSUMMRY_ACTUAL_UNLOADED_QTY_IGX() {
		return SUMMRY_ACTUAL_UNLOADED_QTY_IGX;
	}

	public void setSUMMRY_ACTUAL_UNLOADED_QTY_IGX(Vector sUMMRY_ACTUAL_UNLOADED_QTY_IGX) {
		SUMMRY_ACTUAL_UNLOADED_QTY_IGX = sUMMRY_ACTUAL_UNLOADED_QTY_IGX;
	}

	public Vector getSUMMRY_TAX_AMT_INR_IGX() {
		return SUMMRY_TAX_AMT_INR_IGX;
	}

	public void setSUMMRY_TAX_AMT_INR_IGX(Vector sUMMRY_TAX_AMT_INR_IGX) {
		SUMMRY_TAX_AMT_INR_IGX = sUMMRY_TAX_AMT_INR_IGX;
	}

	public Vector getSUMMRY_TDS_AMT_INR_IGX() {
		return SUMMRY_TDS_AMT_INR_IGX;
	}

	public void setSUMMRY_TDS_AMT_INR_IGX(Vector sUMMRY_TDS_AMT_INR_IGX) {
		SUMMRY_TDS_AMT_INR_IGX = sUMMRY_TDS_AMT_INR_IGX;
	}

	public Vector getSUMMRY_TAX_AMT_USD_IGX() {
		return SUMMRY_TAX_AMT_USD_IGX;
	}

	public void setSUMMRY_TAX_AMT_USD_IGX(Vector sUMMRY_TAX_AMT_USD_IGX) {
		SUMMRY_TAX_AMT_USD_IGX = sUMMRY_TAX_AMT_USD_IGX;
	}

	public Vector getSUMMRY_CARGO_REF_CD_IGX() {
		return SUMMRY_CARGO_REF_CD_IGX;
	}

	public void setSUMMRY_CARGO_REF_CD_IGX(Vector sUMMRY_CARGO_REF_CD_IGX) {
		SUMMRY_CARGO_REF_CD_IGX = sUMMRY_CARGO_REF_CD_IGX;
	}

	public Vector getSUMMRY_USD_VAL_INVOICE_IGX() {
		return SUMMRY_USD_VAL_INVOICE_IGX;
	}

	public void setSUMMRY_USD_VAL_INVOICE_IGX(Vector sUMMRY_USD_VAL_INVOICE_IGX) {
		SUMMRY_USD_VAL_INVOICE_IGX = sUMMRY_USD_VAL_INVOICE_IGX;
	}

	public Vector getSUMMRY_FINAL_SELLER_INV_AMT_INR_IGX() {
		return SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX;
	}

	public void setSUMMRY_FINAL_SELLER_INV_AMT_INR_IGX(Vector sUMMRY_FINAL_SELLER_INV_AMT_INR_IGX) {
		SUMMRY_FINAL_SELLER_INV_AMT_INR_IGX = sUMMRY_FINAL_SELLER_INV_AMT_INR_IGX;
	}

	public Vector getSUMMRY_TOTAL_CD_AMT_IGX() {
		return SUMMRY_TOTAL_CD_AMT_IGX;
	}

	public void setSUMMRY_TOTAL_CD_AMT_IGX(Vector sUMMRY_TOTAL_CD_AMT_IGX) {
		SUMMRY_TOTAL_CD_AMT_IGX = sUMMRY_TOTAL_CD_AMT_IGX;
	}

	public Vector getSUMMRY_TOTAL_PAID_REFUND_IGX() {
		return SUMMRY_TOTAL_PAID_REFUND_IGX;
	}

	public void setSUMMRY_TOTAL_PAID_REFUND_IGX(Vector sUMMRY_TOTAL_PAID_REFUND_IGX) {
		SUMMRY_TOTAL_PAID_REFUND_IGX = sUMMRY_TOTAL_PAID_REFUND_IGX;
	}

	public Vector getSUMMRY_CD_PAID_IGX() {
		return SUMMRY_CD_PAID_IGX;
	}

	public void setSUMMRY_CD_PAID_IGX(Vector sUMMRY_CD_PAID_IGX) {
		SUMMRY_CD_PAID_IGX = sUMMRY_CD_PAID_IGX;
	}

	public Vector getSUMMRY_INR_PER_MMBTU_IGX() {
		return SUMMRY_INR_PER_MMBTU_IGX;
	}

	public void setSUMMRY_INR_PER_MMBTU_IGX(Vector sUMMRY_INR_PER_MMBTU_IGX) {
		SUMMRY_INR_PER_MMBTU_IGX = sUMMRY_INR_PER_MMBTU_IGX;
	}

	public Vector getSUMMRY_CUSTOM_DUTY_USD_IGX() {
		return SUMMRY_CUSTOM_DUTY_USD_IGX;
	}

	public void setSUMMRY_CUSTOM_DUTY_USD_IGX(Vector sUMMRY_CUSTOM_DUTY_USD_IGX) {
		SUMMRY_CUSTOM_DUTY_USD_IGX = sUMMRY_CUSTOM_DUTY_USD_IGX;
	}

	public Vector getSUMMRY_ADDL_CUSTOM_DUTY_USD_IGX() {
		return SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX;
	}

	public void setSUMMRY_ADDL_CUSTOM_DUTY_USD_IGX(Vector sUMMRY_ADDL_CUSTOM_DUTY_USD_IGX) {
		SUMMRY_ADDL_CUSTOM_DUTY_USD_IGX = sUMMRY_ADDL_CUSTOM_DUTY_USD_IGX;
	}

	public Vector getSUMMRY_COST_OF_PURCHASE_USD_IGX() {
		return SUMMRY_COST_OF_PURCHASE_USD_IGX;
	}

	public void setSUMMRY_COST_OF_PURCHASE_USD_IGX(Vector sUMMRY_COST_OF_PURCHASE_USD_IGX) {
		SUMMRY_COST_OF_PURCHASE_USD_IGX = sUMMRY_COST_OF_PURCHASE_USD_IGX;
	}

	public Vector getSUMMRY_USD_PER_MMBTU_IGX() {
		return SUMMRY_USD_PER_MMBTU_IGX;
	}

	public void setSUMMRY_USD_PER_MMBTU_IGX(Vector sUMMRY_USD_PER_MMBTU_IGX) {
		SUMMRY_USD_PER_MMBTU_IGX = sUMMRY_USD_PER_MMBTU_IGX;
	}

	public Vector getTOTAL_PAID_REFUND_NUMERIC_IGX() {
		return TOTAL_PAID_REFUND_NUMERIC_IGX;
	}

	public void setTOTAL_PAID_REFUND_NUMERIC_IGX(Vector tOTAL_PAID_REFUND_NUMERIC_IGX) {
		TOTAL_PAID_REFUND_NUMERIC_IGX = tOTAL_PAID_REFUND_NUMERIC_IGX;
	}

	public Vector getTRD_CD_IGX() {
		return TRD_CD_IGX;
	}

	public void setTRD_CD_IGX(Vector tRD_CD_IGX) {
		TRD_CD_IGX = tRD_CD_IGX;
	}

	public Vector getSUMMRY_ACT_ARRV_MONTH_IGX() {
		return SUMMRY_ACT_ARRV_MONTH_IGX;
	}

	public void setSUMMRY_ACT_ARRV_MONTH_IGX(Vector sUMMRY_ACT_ARRV_MONTH_IGX) {
		SUMMRY_ACT_ARRV_MONTH_IGX = sUMMRY_ACT_ARRV_MONTH_IGX;
	}

	public Vector getUSD_PER_MMBTU_NUMERIC_IGX() {
		return USD_PER_MMBTU_NUMERIC_IGX;
	}

	public void setUSD_PER_MMBTU_NUMERIC_IGX(Vector uSD_PER_MMBTU_NUMERIC_IGX) {
		USD_PER_MMBTU_NUMERIC_IGX = uSD_PER_MMBTU_NUMERIC_IGX;
	}

	public Vector getTAX_rmk_IGX() {
		return TAX_rmk_IGX;
	}

	public void setTAX_rmk_IGX(Vector tAX_rmk_IGX) {
		TAX_rmk_IGX = tAX_rmk_IGX;
	}

	public Vector getTAX_amt_usd_IGX() {
		return TAX_amt_usd_IGX;
	}

	public void setTAX_amt_usd_IGX(Vector tAX_amt_usd_IGX) {
		TAX_amt_usd_IGX = tAX_amt_usd_IGX;
	}

	public Vector getUSD_VAL_INVOICE_NUMERIC_IGX() {
		return USD_VAL_INVOICE_NUMERIC_IGX;
	}

	public void setUSD_VAL_INVOICE_NUMERIC_IGX(Vector uSD_VAL_INVOICE_NUMERIC_IGX) {
		USD_VAL_INVOICE_NUMERIC_IGX = uSD_VAL_INVOICE_NUMERIC_IGX;
	}

	public Vector getTRADER_NAME_IGX() {
		return TRADER_NAME_IGX;
	}

	public void setTRADER_NAME_IGX(Vector tRADER_NAME_IGX) {
		TRADER_NAME_IGX = tRADER_NAME_IGX;
	}

	public Vector getTRADER_INV_CURRENCY_IGX() {
		return TRADER_INV_CURRENCY_IGX;
	}

	public void setTRADER_INV_CURRENCY_IGX(Vector tRADER_INV_CURRENCY_IGX) {
		TRADER_INV_CURRENCY_IGX = tRADER_INV_CURRENCY_IGX;
	}

	public Vector getVtitle() {
		return Vtitle;
	}

	public void setVtitle(Vector vtitle) {
		Vtitle = vtitle;
	}

	public Vector getInv_type_flag() {
		return inv_type_flag;
	}

	public void setInv_type_flag(Vector inv_type_flag) {
		this.inv_type_flag = inv_type_flag;
	}

	public Vector getXML_GEN_FLAG_IGX() {
		return XML_GEN_FLAG_IGX;
	}

	public void setXML_GEN_FLAG_IGX(Vector xML_GEN_FLAG_IGX) {
		XML_GEN_FLAG_IGX = xML_GEN_FLAG_IGX;
	}

	public Vector getTRANSACTION_DT_IGX() {
		return TRANSACTION_DT_IGX;
	}

	public void setTRANSACTION_DT_IGX(Vector tRANSACTION_DT_IGX) {
		TRANSACTION_DT_IGX = tRANSACTION_DT_IGX;
	}

	public Vector getTCS_PERC_IGX() {
		return TCS_PERC_IGX;
	}

	public void setTCS_PERC_IGX(Vector tCS_PERC_IGX) {
		TCS_PERC_IGX = tCS_PERC_IGX;
	}

	public Vector getTax_inr_IGX() {
		return Tax_inr_IGX;
	}

	public void setTax_inr_IGX(Vector tax_inr_IGX) {
		Tax_inr_IGX = tax_inr_IGX;
	}

	public Vector getSUN_APPROVAL_IGX_DRCR() {
		return SUN_APPROVAL_IGX_DRCR;
	}

	public void setSUN_APPROVAL_IGX_DRCR(Vector sUN_APPROVAL_IGX_DRCR) {
		SUN_APPROVAL_IGX_DRCR = sUN_APPROVAL_IGX_DRCR;
	}

	public Vector getTax_inr_IGX_DRCR() {
		return Tax_inr_IGX_DRCR;
	}

	public void setTax_inr_IGX_DRCR(Vector tax_inr_IGX_DRCR) {
		Tax_inr_IGX_DRCR = tax_inr_IGX_DRCR;
	}

	public Vector getTCS_TDS_AMT_IGX_DRCR() {
		return TCS_TDS_AMT_IGX_DRCR;
	}

	public void setTCS_TDS_AMT_IGX_DRCR(Vector tCS_TDS_AMT_IGX_DRCR) {
		TCS_TDS_AMT_IGX_DRCR = tCS_TDS_AMT_IGX_DRCR;
	}

	public Vector getTCS_TDS_AMT_IGX() {
		return TCS_TDS_AMT_IGX;
	}

	public void setTCS_TDS_AMT_IGX(Vector tCS_TDS_AMT_IGX) {
		TCS_TDS_AMT_IGX = tCS_TDS_AMT_IGX;
	}

	public Vector getTAX_amt_usd_IGX_DRCR() {
		return TAX_amt_usd_IGX_DRCR;
	}

	public void setTAX_amt_usd_IGX_DRCR(Vector tAX_amt_usd_IGX_DRCR) {
		TAX_amt_usd_IGX_DRCR = tAX_amt_usd_IGX_DRCR;
	}

	public Vector getXML_GEN_FLAG_IGX_DRCR() {
		return XML_GEN_FLAG_IGX_DRCR;
	}

	public void setXML_GEN_FLAG_IGX_DRCR(Vector xML_GEN_FLAG_IGX_DRCR) {
		XML_GEN_FLAG_IGX_DRCR = xML_GEN_FLAG_IGX_DRCR;
	}

	public Vector getInv_type_flag_IGX() {
		return inv_type_flag_IGX;
	}

	public void setInv_type_flag_IGX(Vector inv_type_flag_IGX) {
		this.inv_type_flag_IGX = inv_type_flag_IGX;
	}

	public Vector getTRADER_PAY_CURRENCY_IGX() {
		return TRADER_PAY_CURRENCY_IGX;
	}

	public void setTRADER_PAY_CURRENCY_IGX(Vector tRADER_PAY_CURRENCY_IGX) {
		TRADER_PAY_CURRENCY_IGX = tRADER_PAY_CURRENCY_IGX;
	}

	public Vector getExchg_rt_val_IGX() {
		return exchg_rt_val_IGX;
	}

	public void setExchg_rt_val_IGX(Vector exchg_rt_val_IGX) {
		this.exchg_rt_val_IGX = exchg_rt_val_IGX;
	}

	public Vector getTDS_PERC_IGX() {
		return TDS_PERC_IGX;
	}

	public void setTDS_PERC_IGX(Vector tDS_PERC_IGX) {
		TDS_PERC_IGX = tDS_PERC_IGX;
	}

	public Vector getGross_inr_IGX() {
		return Gross_inr_IGX;
	}

	public void setGross_inr_IGX(Vector gross_inr_IGX) {
		Gross_inr_IGX = gross_inr_IGX;
	}

	public Vector getNet_inr_IGX() {
		return net_inr_IGX;
	}

	public void setNet_inr_IGX(Vector net_inr_IGX) {
		this.net_inr_IGX = net_inr_IGX;
	}

	public Vector getINVOICE_TCS_AMT_IGX() {
		return INVOICE_TCS_AMT_IGX;
	}

	public void setINVOICE_TCS_AMT_IGX(Vector iNVOICE_TCS_AMT_IGX) {
		INVOICE_TCS_AMT_IGX = iNVOICE_TCS_AMT_IGX;
	}

	public Vector getTRANSACTION_DT_DRCR() {
		return TRANSACTION_DT_DRCR;
	}

	public void setTRANSACTION_DT_DRCR(Vector tRANSACTION_DT_DRCR) {
		TRANSACTION_DT_DRCR = tRANSACTION_DT_DRCR;
	}

	public Vector getTCS_PERC_IGX_DRCR() {
		return TCS_PERC_IGX_DRCR;
	}

	public void setTCS_PERC_IGX_DRCR(Vector tCS_PERC_IGX_DRCR) {
		TCS_PERC_IGX_DRCR = tCS_PERC_IGX_DRCR;
	}

	public Vector getTDS_PERC_IGX_DRCR() {
		return TDS_PERC_IGX_DRCR;
	}

	public void setTDS_PERC_IGX_DRCR(Vector tDS_PERC_IGX_DRCR) {
		TDS_PERC_IGX_DRCR = tDS_PERC_IGX_DRCR;
	}

	public Vector getTCS_APP_FLAG_DRCR() {
		return TCS_APP_FLAG_DRCR;
	}

	public void setTCS_APP_FLAG_DRCR(Vector tCS_APP_FLAG_DRCR) {
		TCS_APP_FLAG_DRCR = tCS_APP_FLAG_DRCR;
	}

	public Vector getTDS_APP_FLAG_DRCR() {
		return TDS_APP_FLAG_DRCR;
	}

	public void setTDS_APP_FLAG_DRCR(Vector tDS_APP_FLAG_DRCR) {
		TDS_APP_FLAG_DRCR = tDS_APP_FLAG_DRCR;
	}

	public Vector getNet_inr_IGX_DRCR() {
		return net_inr_IGX_DRCR;
	}

	public void setNet_inr_IGX_DRCR(Vector net_inr_IGX_DRCR) {
		this.net_inr_IGX_DRCR = net_inr_IGX_DRCR;
	}

	public Vector getTRD_CD_IGX_DRCR() {
		return TRD_CD_IGX_DRCR;
	}

	public void setTRD_CD_IGX_DRCR(Vector tRD_CD_IGX_DRCR) {
		TRD_CD_IGX_DRCR = tRD_CD_IGX_DRCR;
	}

	public Vector getINVOICE_AMT_IGX_DRCR() {
		return INVOICE_AMT_IGX_DRCR;
	}

	public void setINVOICE_AMT_IGX_DRCR(Vector iNVOICE_AMT_IGX_DRCR) {
		INVOICE_AMT_IGX_DRCR = iNVOICE_AMT_IGX_DRCR;
	}

	public Vector getINVOICE_TAX_AMT_IGX_DRCR() {
		return INVOICE_TAX_AMT_IGX_DRCR;
	}

	public void setINVOICE_TAX_AMT_IGX_DRCR(Vector iNVOICE_TAX_AMT_IGX_DRCR) {
		INVOICE_TAX_AMT_IGX_DRCR = iNVOICE_TAX_AMT_IGX_DRCR;
	}

	public Vector getTCS_APP_FLAG_IGX() {
		return TCS_APP_FLAG_IGX;
	}

	public void setTCS_APP_FLAG_IGX(Vector tCS_APP_FLAG_IGX) {
		TCS_APP_FLAG_IGX = tCS_APP_FLAG_IGX;
	}

	public Vector getTDS_APP_FLAG_IGX() {
		return TDS_APP_FLAG_IGX;
	}

	public void setTDS_APP_FLAG_IGX(Vector tDS_APP_FLAG_IGX) {
		TDS_APP_FLAG_IGX = tDS_APP_FLAG_IGX;
	}

	public Vector getINVOICE_AMT_TDS_IGX_DRCR() {
		return INVOICE_AMT_TDS_IGX_DRCR;
	}

	public void setINVOICE_AMT_TDS_IGX_DRCR(Vector iNVOICE_AMT_TDS_IGX_DRCR) {
		INVOICE_AMT_TDS_IGX_DRCR = iNVOICE_AMT_TDS_IGX_DRCR;
	}

	public Vector getTAX_rmk_IGX_DRCR() {
		return TAX_rmk_IGX_DRCR;
	}

	public void setTAX_rmk_IGX_DRCR(Vector tAX_rmk_IGX_DRCR) {
		TAX_rmk_IGX_DRCR = tAX_rmk_IGX_DRCR;
	}

	public Vector getInvoice_type_IGX_DRCR() {
		return invoice_type_IGX_DRCR;
	}

	public void setInvoice_type_IGX_DRCR(Vector invoice_type_IGX_DRCR) {
		this.invoice_type_IGX_DRCR = invoice_type_IGX_DRCR;
	}

	public Vector getINVOICE_NO_IGX_DRCR() {
		return INVOICE_NO_IGX_DRCR;
	}

	public void setINVOICE_NO_IGX_DRCR(Vector iNVOICE_NO_IGX_DRCR) {
		INVOICE_NO_IGX_DRCR = iNVOICE_NO_IGX_DRCR;
	}

	public Vector getINVOICE_DT_IGX_DRCR() {
		return INVOICE_DT_IGX_DRCR;
	}

	public void setINVOICE_DT_IGX_DRCR(Vector iNVOICE_DT_IGX_DRCR) {
		INVOICE_DT_IGX_DRCR = iNVOICE_DT_IGX_DRCR;
	}

	public Vector getINVOICE_TCS_AMT_DRCR() {
		return INVOICE_TCS_AMT_DRCR;
	}

	public void setINVOICE_TCS_AMT_DRCR(Vector iNVOICE_TCS_AMT_DRCR) {
		INVOICE_TCS_AMT_DRCR = iNVOICE_TCS_AMT_DRCR;
	}

	public Vector getREMARK_IGX() {
		return REMARK_IGX;
	}

	public void setREMARK_IGX(Vector rEMARK_IGX) {
		REMARK_IGX = rEMARK_IGX;
	}

	public Vector getPayable_inr_IGX() {
		return payable_inr_IGX;
	}

	public void setPayable_inr_IGX(Vector payable_inr_IGX) {
		this.payable_inr_IGX = payable_inr_IGX;
	}

	public Vector getPAY_EXCHG_RT_DT_DRCR() {
		return PAY_EXCHG_RT_DT_DRCR;
	}

	public void setPAY_EXCHG_RT_DT_DRCR(Vector pAY_EXCHG_RT_DT_DRCR) {
		PAY_EXCHG_RT_DT_DRCR = pAY_EXCHG_RT_DT_DRCR;
	}

	public double getPurchase_total_inv_value_inr_igx_DRCR() {
		return purchase_total_inv_value_inr_igx_DRCR;
	}

	public void setPurchase_total_inv_value_inr_igx_DRCR(double purchase_total_inv_value_inr_igx_DRCR) {
		this.purchase_total_inv_value_inr_igx_DRCR = purchase_total_inv_value_inr_igx_DRCR;
	}

	public double getPurchase_total_cost_inr_igx_DRCR() {
		return purchase_total_cost_inr_igx_DRCR;
	}

	public void setPurchase_total_cost_inr_igx_DRCR(double purchase_total_cost_inr_igx_DRCR) {
		this.purchase_total_cost_inr_igx_DRCR = purchase_total_cost_inr_igx_DRCR;
	}

	public Vector getPayable_inr_IGX_DRCR() {
		return payable_inr_IGX_DRCR;
	}

	public void setPayable_inr_IGX_DRCR(Vector payable_inr_IGX_DRCR) {
		this.payable_inr_IGX_DRCR = payable_inr_IGX_DRCR;
	}

	public Vector getPAY_EXCHG_RT_VAL_DRCR() {
		return PAY_EXCHG_RT_VAL_DRCR;
	}

	public void setPAY_EXCHG_RT_VAL_DRCR(Vector pAY_EXCHG_RT_VAL_DRCR) {
		PAY_EXCHG_RT_VAL_DRCR = pAY_EXCHG_RT_VAL_DRCR;
	}

	public Vector getInv_type_flag_DRCR() {
		return inv_type_flag_DRCR;
	}

	public void setInv_type_flag_DRCR(Vector inv_type_flag_DRCR) {
		this.inv_type_flag_DRCR = inv_type_flag_DRCR;
	}

	public Vector getREMARK_IGX_DRCR() {
		return REMARK_IGX_DRCR;
	}

	public void setREMARK_IGX_DRCR(Vector rEMARK_IGX_DRCR) {
		REMARK_IGX_DRCR = rEMARK_IGX_DRCR;
	}

	public double getPurchase_total_qty_igx_DRCR() {
		return purchase_total_qty_igx_DRCR;
	}

	public void setPurchase_total_qty_igx_DRCR(double purchase_total_qty_igx_DRCR) {
		this.purchase_total_qty_igx_DRCR = purchase_total_qty_igx_DRCR;
	}

	public double getPurchase_total_invoice_value_igx_DRCR() {
		return purchase_total_invoice_value_igx_DRCR;
	}

	public void setPurchase_total_invoice_value_igx_DRCR(double purchase_total_invoice_value_igx_DRCR) {
		this.purchase_total_invoice_value_igx_DRCR = purchase_total_invoice_value_igx_DRCR;
	}

	public Vector getExchg_rt_Dt_IGX() {
		return Exchg_rt_Dt_IGX;
	}

	public void setExchg_rt_Dt_IGX(Vector exchg_rt_Dt_IGX) {
		Exchg_rt_Dt_IGX = exchg_rt_Dt_IGX;
	}

	public Vector getPAY_EXCHG_RT_DT_IGX() {
		return PAY_EXCHG_RT_DT_IGX;
	}

	public void setPAY_EXCHG_RT_DT_IGX(Vector pAY_EXCHG_RT_DT_IGX) {
		PAY_EXCHG_RT_DT_IGX = pAY_EXCHG_RT_DT_IGX;
	}

	public Vector getPAY_EXCHG_RT_VAL_IGX() {
		return PAY_EXCHG_RT_VAL_IGX;
	}

	public void setPAY_EXCHG_RT_VAL_IGX(Vector pAY_EXCHG_RT_VAL_IGX) {
		PAY_EXCHG_RT_VAL_IGX = pAY_EXCHG_RT_VAL_IGX;
	}

	public Vector getTRADER_INV_CURRENCY_DRCR() {
		return TRADER_INV_CURRENCY_DRCR;
	}

	public void setTRADER_INV_CURRENCY_DRCR(Vector tRADER_INV_CURRENCY_DRCR) {
		TRADER_INV_CURRENCY_DRCR = tRADER_INV_CURRENCY_DRCR;
	}

	public Vector getCARGO_REF_CD_IGX_DRCR() {
		return CARGO_REF_CD_IGX_DRCR;
	}

	public void setCARGO_REF_CD_IGX_DRCR(Vector cARGO_REF_CD_IGX_DRCR) {
		CARGO_REF_CD_IGX_DRCR = cARGO_REF_CD_IGX_DRCR;
	}

	public Vector getExchg_rt_val_DRCR() {
		return exchg_rt_val_DRCR;
	}

	public void setExchg_rt_val_DRCR(Vector exchg_rt_val_DRCR) {
		this.exchg_rt_val_DRCR = exchg_rt_val_DRCR;
	}

	public Vector getGross_inr_IGX_DRCR() {
		return Gross_inr_IGX_DRCR;
	}

	public void setGross_inr_IGX_DRCR(Vector gross_inr_IGX_DRCR) {
		Gross_inr_IGX_DRCR = gross_inr_IGX_DRCR;
	}

	public Vector getFINAL_SELLER_INV_AMT_INR_IGX_DRCR() {
		return FINAL_SELLER_INV_AMT_INR_IGX_DRCR;
	}

	public void setFINAL_SELLER_INV_AMT_INR_IGX_DRCR(Vector fINAL_SELLER_INV_AMT_INR_IGX_DRCR) {
		FINAL_SELLER_INV_AMT_INR_IGX_DRCR = fINAL_SELLER_INV_AMT_INR_IGX_DRCR;
	}

	public Vector getCD_PAID_IGX_DRCR() {
		return CD_PAID_IGX_DRCR;
	}

	public void setCD_PAID_IGX_DRCR(Vector cD_PAID_IGX_DRCR) {
		CD_PAID_IGX_DRCR = cD_PAID_IGX_DRCR;
	}

	public Vector getCD_PAID_NUMERIC_IGX_DRCR() {
		return CD_PAID_NUMERIC_IGX_DRCR;
	}

	public void setCD_PAID_NUMERIC_IGX_DRCR(Vector cD_PAID_NUMERIC_IGX_DRCR) {
		CD_PAID_NUMERIC_IGX_DRCR = cD_PAID_NUMERIC_IGX_DRCR;
	}

	public Vector getVESSEL_NM_IGX_DRCR() {
		return VESSEL_NM_IGX_DRCR;
	}

	public void setVESSEL_NM_IGX_DRCR(Vector vESSEL_NM_IGX_DRCR) {
		VESSEL_NM_IGX_DRCR = vESSEL_NM_IGX_DRCR;
	}

	public Vector getDR_CR_FLAG_IGX_DRCR() {
		return DR_CR_FLAG_IGX_DRCR;
	}

	public void setDR_CR_FLAG_IGX_DRCR(Vector dR_CR_FLAG_IGX_DRCR) {
		DR_CR_FLAG_IGX_DRCR = dR_CR_FLAG_IGX_DRCR;
	}

	public Vector getExchg_rt_cd_DRCR() {
		return Exchg_rt_cd_DRCR;
	}

	public void setExchg_rt_cd_DRCR(Vector exchg_rt_cd_DRCR) {
		Exchg_rt_cd_DRCR = exchg_rt_cd_DRCR;
	}

	public Vector getTRADER_PAY_CURRENCY_DRCR() {
		return TRADER_PAY_CURRENCY_DRCR;
	}

	public void setTRADER_PAY_CURRENCY_DRCR(Vector tRADER_PAY_CURRENCY_DRCR) {
		TRADER_PAY_CURRENCY_DRCR = tRADER_PAY_CURRENCY_DRCR;
	}

	public Vector getEXCHG_RATE_VALUE_IGX_DRCR() {
		return EXCHG_RATE_VALUE_IGX_DRCR;
	}

	public void setEXCHG_RATE_VALUE_IGX_DRCR(Vector eXCHG_RATE_VALUE_IGX_DRCR) {
		EXCHG_RATE_VALUE_IGX_DRCR = eXCHG_RATE_VALUE_IGX_DRCR;
	}

	public Vector getUSD_VAL_INVOICE_IGX_DRCR() {
		return USD_VAL_INVOICE_IGX_DRCR;
	}

	public void setUSD_VAL_INVOICE_IGX_DRCR(Vector uSD_VAL_INVOICE_IGX_DRCR) {
		USD_VAL_INVOICE_IGX_DRCR = uSD_VAL_INVOICE_IGX_DRCR;
	}

	public Vector getUSD_VAL_INVOICE_NUMERIC_IGX_DRCR() {
		return USD_VAL_INVOICE_NUMERIC_IGX_DRCR;
	}

	public void setUSD_VAL_INVOICE_NUMERIC_IGX_DRCR(Vector uSD_VAL_INVOICE_NUMERIC_IGX_DRCR) {
		USD_VAL_INVOICE_NUMERIC_IGX_DRCR = uSD_VAL_INVOICE_NUMERIC_IGX_DRCR;
	}

	public Vector getCONF_PRICE_IGX_DRCR() {
		return CONF_PRICE_IGX_DRCR;
	}

	public void setCONF_PRICE_IGX_DRCR(Vector cONF_PRICE_IGX_DRCR) {
		CONF_PRICE_IGX_DRCR = cONF_PRICE_IGX_DRCR;
	}

	public Vector getCONF_PRICE_NUMERIC_IGX_DRCR() {
		return CONF_PRICE_NUMERIC_IGX_DRCR;
	}

	public void setCONF_PRICE_NUMERIC_IGX_DRCR(Vector cONF_PRICE_NUMERIC_IGX_DRCR) {
		CONF_PRICE_NUMERIC_IGX_DRCR = cONF_PRICE_NUMERIC_IGX_DRCR;
	}

	public Vector getDUE_DT_IGX_DRCR() {
		return DUE_DT_IGX_DRCR;
	}

	public void setDUE_DT_IGX_DRCR(Vector dUE_DT_IGX_DRCR) {
		DUE_DT_IGX_DRCR = dUE_DT_IGX_DRCR;
	}

	public Vector getDuration_igx_DRCR() {
		return duration_igx_DRCR;
	}

	public void setDuration_igx_DRCR(Vector duration_igx_DRCR) {
		this.duration_igx_DRCR = duration_igx_DRCR;
	}

	public Vector getTRADER_NAME_IGX_DRCR() {
		return TRADER_NAME_IGX_DRCR;
	}

	public void setTRADER_NAME_IGX_DRCR(Vector tRADER_NAME_IGX_DRCR) {
		TRADER_NAME_IGX_DRCR = tRADER_NAME_IGX_DRCR;
	}

	public Vector getTRADER_ABBR_IGX_DRCR() {
		return TRADER_ABBR_IGX_DRCR;
	}

	public void setTRADER_ABBR_IGX_DRCR(Vector tRADER_ABBR_IGX_DRCR) {
		TRADER_ABBR_IGX_DRCR = tRADER_ABBR_IGX_DRCR;
	}

	public Vector getVtitle_DRCR() {
		return Vtitle_DRCR;
	}

	public void setVtitle_DRCR(Vector vtitle_DRCR) {
		Vtitle_DRCR = vtitle_DRCR;
	}

	public Vector getExchg_rt_Dt_DRCR() {
		return Exchg_rt_Dt_DRCR;
	}

	public void setExchg_rt_Dt_DRCR(Vector exchg_rt_Dt_DRCR) {
		Exchg_rt_Dt_DRCR = exchg_rt_Dt_DRCR;
	}

	public Vector getACTUAL_UNLOADED_QTY_IGX_DRCR() {
		return ACTUAL_UNLOADED_QTY_IGX_DRCR;
	}

	public void setACTUAL_UNLOADED_QTY_IGX_DRCR(Vector aCTUAL_UNLOADED_QTY_IGX_DRCR) {
		ACTUAL_UNLOADED_QTY_IGX_DRCR = aCTUAL_UNLOADED_QTY_IGX_DRCR;
	}

	public Vector getACTUAL_UNLOADED_QTY_NUMERIC_IGX_DRCR() {
		return ACTUAL_UNLOADED_QTY_NUMERIC_IGX_DRCR;
	}

	public void setACTUAL_UNLOADED_QTY_NUMERIC_IGX_DRCR(Vector aCTUAL_UNLOADED_QTY_NUMERIC_IGX_DRCR) {
		ACTUAL_UNLOADED_QTY_NUMERIC_IGX_DRCR = aCTUAL_UNLOADED_QTY_NUMERIC_IGX_DRCR;
	}

	public Vector getACT_ARRV_MONTH_IGX_DRCR() {
		return ACT_ARRV_MONTH_IGX_DRCR;
	}

	public void setACT_ARRV_MONTH_IGX_DRCR(Vector aCT_ARRV_MONTH_IGX_DRCR) {
		ACT_ARRV_MONTH_IGX_DRCR = aCT_ARRV_MONTH_IGX_DRCR;
	}

	public Vector getACT_ARRV_DT_IGX_DRCR() {
		return ACT_ARRV_DT_IGX_DRCR;
	}

	public void setACT_ARRV_DT_IGX_DRCR(Vector aCT_ARRV_DT_IGX_DRCR) {
		ACT_ARRV_DT_IGX_DRCR = aCT_ARRV_DT_IGX_DRCR;
	}

	public Vector getApprove_flag_IGX_DRCR() {
		return Approve_flag_IGX_DRCR;
	}

	public void setApprove_flag_IGX_DRCR(Vector approve_flag_IGX_DRCR) {
		Approve_flag_IGX_DRCR = approve_flag_IGX_DRCR;
	}

	public Vector getDR_CR_ORI_INV_NO() {
		return DR_CR_ORI_INV_NO;
	}

	public void setDR_CR_ORI_INV_NO(Vector dR_CR_ORI_INV_NO) {
		DR_CR_ORI_INV_NO = dR_CR_ORI_INV_NO;
	}

	public String getExchg_rt_dt() {
		return exchg_rt_dt;
	}

	public void setExchg_rt_dt(String exchg_rt_dt) {
		this.exchg_rt_dt = exchg_rt_dt;
	}

	public String getExchg_rt_dt_drcr() {
		return exchg_rt_dt_drcr;
	}

	public void setExchg_rt_dt_drcr(String exchg_rt_dt_drcr) {
		this.exchg_rt_dt_drcr = exchg_rt_dt_drcr;
	}

	public String getAll_exchg_rtdt() {
		return all_exchg_rtdt;
	}

	public void setAll_exchg_rtdt(String all_exchg_rtdt) {
		this.all_exchg_rtdt = all_exchg_rtdt;
	}

	public String getAll_exchg_rtdt_drcr() {
		return all_exchg_rtdt_drcr;
	}

	public void setAll_exchg_rtdt_drcr(String all_exchg_rtdt_drcr) {
		this.all_exchg_rtdt_drcr = all_exchg_rtdt_drcr;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public Vector getCARGO_PRICE_FLAG() {
		return CARGO_PRICE_FLAG;
	}

	public void setCARGO_PRICE_FLAG(Vector cARGO_PRICE_FLAG) {
		CARGO_PRICE_FLAG = cARGO_PRICE_FLAG;
	}

	public Vector getCARGO_PRICE_IGX() {
		return CARGO_PRICE_IGX;
	}

	public void setCARGO_PRICE_IGX(Vector cARGO_PRICE_IGX) {
		CARGO_PRICE_IGX = cARGO_PRICE_IGX;
	}

	public Vector getVsupp_plant_Abbr_IGX() {
		return Vsupp_plant_Abbr_IGX;
	}

	public void setVsupp_plant_Abbr_IGX(Vector Vsupp_plant_Abbr_IGX) {
		Vsupp_plant_Abbr_IGX = Vsupp_plant_Abbr_IGX;
	}

	public Vector getVsupp_plant_Abbr_DRCR() {
		return Vsupp_plant_Abbr_DRCR;
	}

	public void setVsupp_plant_Abbr_DRCR(Vector vsupp_plant_Abbr_DRCR) {
		Vsupp_plant_Abbr_DRCR = vsupp_plant_Abbr_DRCR;
	}

	public String getAll_exchg_rtdt_igx() {
		return all_exchg_rtdt_igx;
	}

	public void setAll_exchg_rtdt_igx(String all_exchg_rtdt_igx) {
		this.all_exchg_rtdt_igx = all_exchg_rtdt_igx;
	}

	public String getAll_exchg_rtdt_drcr_igx() {
		return all_exchg_rtdt_drcr_igx;
	}

	public void setAll_exchg_rtdt_drcr_igx(String all_exchg_rtdt_drcr_igx) {
		this.all_exchg_rtdt_drcr_igx = all_exchg_rtdt_drcr_igx;
	}

	public Vector getVIsub_tax_amt() {
		return VIsub_tax_amt;
	}

	public Vector getVIsub_tax_str() {
		return VIsub_tax_str;
	}

	public Vector getVIsub_net_payable() {
		return VIsub_net_payable;
	}



	public Vector getVIsub_tax_tds_perc() {
		return VIsub_tax_tds_perc;
	}



	public Vector getVIsub_tax_tds_amt() {
		return VIsub_tax_tds_amt;
	}



	public Vector getVIinv_gross_tds_perc() {
		return VIinv_gross_tds_perc;
	}



	public Vector getDuration_trans() {
		return duration_trans;
	}



	public void setDuration_trans(Vector duration_trans) {
		this.duration_trans = duration_trans;
	}



	public Vector getVparty_abbr() {
		return Vparty_abbr;
	}



	public void setVparty_abbr(Vector vparty_abbr) {
		Vparty_abbr = vparty_abbr;
	}



	public Vector getVentry_nom() {
		return Ventry_nom;
	}



	public void setVentry_nom(Vector ventry_nom) {
		Ventry_nom = ventry_nom;
	}



	public Vector getVexit_nom() {
		return Vexit_nom;
	}



	public void setVexit_nom(Vector vexit_nom) {
		Vexit_nom = vexit_nom;
	}



	public Vector getVentry_sch() {
		return Ventry_sch;
	}



	public void setVentry_sch(Vector ventry_sch) {
		Ventry_sch = ventry_sch;
	}



	public Vector getVexit_sch() {
		return Vexit_sch;
	}



	public void setVexit_sch(Vector vexit_sch) {
		Vexit_sch = vexit_sch;
	}



	public Vector getVentry_alloc() {
		return Ventry_alloc;
	}



	public void setVentry_alloc(Vector ventry_alloc) {
		Ventry_alloc = ventry_alloc;
	}



	public Vector getVexit_alloc() {
		return Vexit_alloc;
	}



	public void setVexit_alloc(Vector vexit_alloc) {
		Vexit_alloc = vexit_alloc;
	}



	public Vector getVcont_no() {
		return Vcont_no;
	}



	public void setVcont_no(Vector vcont_no) {
		Vcont_no = vcont_no;
	}



	public Vector getVinv_no() {
		return Vinv_no;
	}



	public void setVinv_no(Vector vinv_no) {
		Vinv_no = vinv_no;
	}



	public Vector getVrate() {
		return Vrate;
	}



	public void setVrate(Vector vrate) {
		Vrate = vrate;
	}



	public Vector getVamt() {
		return Vamt;
	}



	public void setVamt(Vector vamt) {
		Vamt = vamt;
	}



	public Vector getVaprv_by() {
		return Vaprv_by;
	}



	public void setVaprv_by(Vector vaprv_by) {
		Vaprv_by = vaprv_by;
	}



	public double getTot_entry_nom() {
		return tot_entry_nom;
	}



	public void setTot_entry_nom(double tot_entry_nom) {
		this.tot_entry_nom = tot_entry_nom;
	}



	public double getTot_exit_nom() {
		return tot_exit_nom;
	}



	public void setTot_exit_nom(double tot_exit_nom) {
		this.tot_exit_nom = tot_exit_nom;
	}



	public double getTot_entry_sch() {
		return tot_entry_sch;
	}



	public void setTot_entry_sch(double tot_entry_sch) {
		this.tot_entry_sch = tot_entry_sch;
	}



	public double getTot_exit_sch() {
		return tot_exit_sch;
	}



	public void setTot_exit_sch(double tot_exit_sch) {
		this.tot_exit_sch = tot_exit_sch;
	}



	public double getTot_entry_alloc() {
		return tot_entry_alloc;
	}



	public void setTot_entry_alloc(double tot_entry_alloc) {
		this.tot_entry_alloc = tot_entry_alloc;
	}



	public double getTot_exit_alloc() {
		return tot_exit_alloc;
	}



	public void setTot_exit_alloc(double tot_exit_alloc) {
		this.tot_exit_alloc = tot_exit_alloc;
	}



	public double getTotamt() {
		return totamt;
	}



	public void setTotamt(double totamt) {
		this.totamt = totamt;
	}



	public Vector getCONT_NO() {
		return CONT_NO;
	}



	public void setCONT_NO(Vector cONT_NO) {
		CONT_NO = cONT_NO;
	}



	public Vector getTRADER_ABBR_TR() {
		return TRADER_ABBR_TR;
	}



	public void setTRADER_ABBR_TR(Vector tRADER_ABBR_TR) {
		TRADER_ABBR_TR = tRADER_ABBR_TR;
	}



	public Vector getContract_no() {
		return contract_no;
	}



	public void setContract_no(Vector contract_no) {
		this.contract_no = contract_no;
	}



	public Vector getContract_rev_no() {
		return contract_rev_no;
	}



	public void setContract_rev_no(Vector contract_rev_no) {
		this.contract_rev_no = contract_rev_no;
	}



	public Vector getINVOICE_DT_TR() {
		return INVOICE_DT_TR;
	}



	public void setINVOICE_DT_TR(Vector iNVOICE_DT_TR) {
		INVOICE_DT_TR = iNVOICE_DT_TR;
	}



	public Vector getDUE_DT_TR() {
		return DUE_DT_TR;
	}



	public void setDUE_DT_TR(Vector dUE_DT_TR) {
		DUE_DT_TR = dUE_DT_TR;
	}



	public Vector getINVOICE_MAPPING_ID_TR() {
		return INVOICE_MAPPING_ID_TR;
	}



	public void setINVOICE_MAPPING_ID_TR(Vector iNVOICE_MAPPING_ID_TR) {
		INVOICE_MAPPING_ID_TR = iNVOICE_MAPPING_ID_TR;
	}



	public Vector getREV_NO() {
		return REV_NO;
	}



	public void setREV_NO(Vector rEV_NO) {
		REV_NO = rEV_NO;
	}



	public Vector getPARTY_CD_TR() {
		return PARTY_CD_TR;
	}



	public void setPARTY_CD_TR(Vector pARTY_CD_TR) {
		PARTY_CD_TR = pARTY_CD_TR;
	}



	public Vector getCONT_AGR_NO() {
		return CONT_AGR_NO;
	}



	public void setCONT_AGR_NO(Vector cONT_AGR_NO) {
		CONT_AGR_NO = cONT_AGR_NO;
	}



	public Vector getCONT_AGR_TYPE() {
		return CONT_AGR_TYPE;
	}



	public void setCONT_AGR_TYPE(Vector cONT_AGR_TYPE) {
		CONT_AGR_TYPE = cONT_AGR_TYPE;
	}



	public Vector getCONT_CUST_CD() {
		return CONT_CUST_CD;
	}



	public void setCONT_CUST_CD(Vector cONT_CUST_CD) {
		CONT_CUST_CD = cONT_CUST_CD;
	}



	public Vector getINVOICE_NO_TR() {
		return INVOICE_NO_TR;
	}



	public void setINVOICE_NO_TR(Vector iNVOICE_NO_TR) {
		INVOICE_NO_TR = iNVOICE_NO_TR;
	}



	public Vector getINVOICE_GEN_FLAG_TR() {
		return INVOICE_GEN_FLAG_TR;
	}



	public void setINVOICE_GEN_FLAG_TR(Vector iNVOICE_GEN_FLAG_TR) {
		INVOICE_GEN_FLAG_TR = iNVOICE_GEN_FLAG_TR;
	}



	public Vector getGROSS_PAY_TR() {
		return GROSS_PAY_TR;
	}



	public void setGROSS_PAY_TR(Vector gROSS_PAY_TR) {
		GROSS_PAY_TR = gROSS_PAY_TR;
	}



	public Vector getTOTAL_TAX_AMT_TR() {
		return TOTAL_TAX_AMT_TR;
	}



	public void setTOTAL_TAX_AMT_TR(Vector tOTAL_TAX_AMT_TR) {
		TOTAL_TAX_AMT_TR = tOTAL_TAX_AMT_TR;
	}



	public Vector getINVOICE_NET_AMT_TR() {
		return INVOICE_NET_AMT_TR;
	}



	public void setINVOICE_NET_AMT_TR(Vector iNVOICE_NET_AMT_TR) {
		INVOICE_NET_AMT_TR = iNVOICE_NET_AMT_TR;
	}



	public Vector getQTY_TR() {
		return QTY_TR;
	}



	public void setQTY_TR(Vector qTY_TR) {
		QTY_TR = qTY_TR;
	}



	public Vector getRATE_TR() {
		return RATE_TR;
	}



	public void setRATE_TR(Vector rATE_TR) {
		RATE_TR = rATE_TR;
	}



	public Vector getXML_GEN_FLAG_TR() {
		return XML_GEN_FLAG_TR;
	}



	public void setXML_GEN_FLAG_TR(Vector xML_GEN_FLAG_TR) {
		XML_GEN_FLAG_TR = xML_GEN_FLAG_TR;
	}



	public Vector getSUN_APPROVAL_TR() {
		return SUN_APPROVAL_TR;
	}



	public void setSUN_APPROVAL_TR(Vector sUN_APPROVAL_TR) {
		SUN_APPROVAL_TR = sUN_APPROVAL_TR;
	}



	public Vector getTDS_PERC_TR() {
		return TDS_PERC_TR;
	}



	public void setTDS_PERC_TR(Vector tDS_PERC_TR) {
		TDS_PERC_TR = tDS_PERC_TR;
	}



	public Vector getTDS_PERC_AMT() {
		return TDS_PERC_AMT;
	}



	public void setTDS_PERC_AMT(Vector tDS_PERC_AMT) {
		TDS_PERC_AMT = tDS_PERC_AMT;
	}



	public Vector getVqty() {
		return Vqty;
	}



	public void setVqty(Vector vqty) {
		Vqty = vqty;
	}



	public Vector getVcompo_nm() {
		return Vcompo_nm;
	}



	public void setVcompo_nm(Vector vcompo_nm) {
		Vcompo_nm = vcompo_nm;
	}



	public Vector getVTAXAMT() {
		return VTAXAMT;
	}



	public void setVTAXAMT(Vector vTAXAMT) {
		VTAXAMT = vTAXAMT;
	}



	public Vector getVnetamt() {
		return Vnetamt;
	}



	public void setVnetamt(Vector vnetamt) {
		Vnetamt = vnetamt;
	}



	public double getTotqty() {
		return totqty;
	}



	public void setTotqty(double totqty) {
		this.totqty = totqty;
	}



	public Vector getVverify_by() {
		return Vverify_by;
	}



	public void setVverify_by(Vector vverify_by) {
		Vverify_by = vverify_by;
	}



	public Vector getINVOICE_AMT_net_IGX() {
		return INVOICE_AMT_net_IGX;
	}



	public void setINVOICE_AMT_net_IGX(Vector iNVOICE_AMT_net_IGX) {
		INVOICE_AMT_net_IGX = iNVOICE_AMT_net_IGX;
	}



	public Vector getINVOICE_AMT_TRANS_IGX() {
		return INVOICE_AMT_TRANS_IGX;
	}



	public void setINVOICE_AMT_TRANS_IGX(Vector iNVOICE_AMT_TRANS_IGX) {
		INVOICE_AMT_TRANS_IGX = iNVOICE_AMT_TRANS_IGX;
	}



	public Vector getINVOICE_TAXAMT_TRANS_IGX() {
		return INVOICE_TAXAMT_TRANS_IGX;
	}



	public void setINVOICE_TAXAMT_TRANS_IGX(Vector iNVOICE_TAXAMT_TRANS_IGX) {
		INVOICE_TAXAMT_TRANS_IGX = iNVOICE_TAXAMT_TRANS_IGX;
	}



	public Vector getVcont_agr_typ() {
		return Vcont_agr_typ;
	}



	public void setVcont_agr_typ(Vector vcont_agr_typ) {
		Vcont_agr_typ = vcont_agr_typ;
	}



	public Vector getVcont_mappid() {
		return Vcont_mappid;
	}



	public void setVcont_mappid(Vector vcont_mappid) {
		Vcont_mappid = vcont_mappid;
	}



	public Vector getVentry_alloc_P() {
		return Ventry_alloc_P;
	}



	public void setVentry_alloc_P(Vector ventry_alloc_P) {
		Ventry_alloc_P = ventry_alloc_P;
	}



	public Vector getVexit_alloc_P() {
		return Vexit_alloc_P;
	}



	public void setVexit_alloc_P(Vector vexit_alloc_P) {
		Vexit_alloc_P = vexit_alloc_P;
	}



	public Vector getVcont_no_P() {
		return Vcont_no_P;
	}



	public void setVcont_no_P(Vector vcont_no_P) {
		Vcont_no_P = vcont_no_P;
	}



	public Vector getVcont_agr_typ_P() {
		return Vcont_agr_typ_P;
	}



	public void setVcont_agr_typ_P(Vector vcont_agr_typ_P) {
		Vcont_agr_typ_P = vcont_agr_typ_P;
	}



	public Vector getVcont_mappid_P() {
		return Vcont_mappid_P;
	}



	public void setVcont_mappid_P(Vector vcont_mappid_P) {
		Vcont_mappid_P = vcont_mappid_P;
	}



	public Vector getVmapp_id_P() {
		return Vmapp_id_P;
	}



	public void setVmapp_id_P(Vector vmapp_id_P) {
		Vmapp_id_P = vmapp_id_P;
	}



	public Vector getVparty_abbr_P() {
		return Vparty_abbr_P;
	}



	public void setVparty_abbr_P(Vector vparty_abbr_P) {
		Vparty_abbr_P = vparty_abbr_P;
	}



	public Vector getDuration_trans_P() {
		return duration_trans_P;
	}



	public void setDuration_trans_P(Vector duration_trans_P) {
		this.duration_trans_P = duration_trans_P;
	}



	public Vector getVrate_P() {
		return Vrate_P;
	}



	public void setVrate_P(Vector vrate_P) {
		Vrate_P = vrate_P;
	}



	public Vector getVamt_P() {
		return Vamt_P;
	}



	public void setVamt_P(Vector vamt_P) {
		Vamt_P = vamt_P;
	}



	public Vector getVqty_P() {
		return Vqty_P;
	}



	public void setVqty_P(Vector vqty_P) {
		Vqty_P = vqty_P;
	}



	public Vector getVaprv_by_P() {
		return Vaprv_by_P;
	}



	public void setVaprv_by_P(Vector vaprv_by_P) {
		Vaprv_by_P = vaprv_by_P;
	}



	public Vector getVverify_by_P() {
		return Vverify_by_P;
	}



	public void setVverify_by_P(Vector vverify_by_P) {
		Vverify_by_P = vverify_by_P;
	}



	public Vector getVentry_nom_P() {
		return Ventry_nom_P;
	}



	public void setVentry_nom_P(Vector ventry_nom_P) {
		Ventry_nom_P = ventry_nom_P;
	}



	public Vector getVexit_nom_P() {
		return Vexit_nom_P;
	}



	public void setVexit_nom_P(Vector vexit_nom_P) {
		Vexit_nom_P = vexit_nom_P;
	}



	public Vector getVTAXAMT_P() {
		return VTAXAMT_P;
	}



	public void setVTAXAMT_P(Vector vTAXAMT_P) {
		VTAXAMT_P = vTAXAMT_P;
	}



	public Vector getVnetamt_P() {
		return Vnetamt_P;
	}



	public void setVnetamt_P(Vector vnetamt_P) {
		Vnetamt_P = vnetamt_P;
	}



	public Vector getVcompo_nm_P() {
		return Vcompo_nm_P;
	}



	public void setVcompo_nm_P(Vector vcompo_nm_P) {
		Vcompo_nm_P = vcompo_nm_P;
	}



	public Vector getVentry_sch_P() {
		return Ventry_sch_P;
	}



	public void setVentry_sch_P(Vector ventry_sch_P) {
		Ventry_sch_P = ventry_sch_P;
	}



	public Vector getVexit_sch_P() {
		return Vexit_sch_P;
	}



	public void setVexit_sch_P(Vector vexit_sch_P) {
		Vexit_sch_P = vexit_sch_P;
	}



	public Vector getVinv_no_P() {
		return Vinv_no_P;
	}



	public void setVinv_no_P(Vector vinv_no_P) {
		Vinv_no_P = vinv_no_P;
	}



	public double getTot_entry_nom_P() {
		return tot_entry_nom_P;
	}



	public void setTot_entry_nom_P(double tot_entry_nom_P) {
		this.tot_entry_nom_P = tot_entry_nom_P;
	}



	public double getTot_exit_nom_P() {
		return tot_exit_nom_P;
	}



	public void setTot_exit_nom_P(double tot_exit_nom_P) {
		this.tot_exit_nom_P = tot_exit_nom_P;
	}



	public double getTot_entry_sch_P() {
		return tot_entry_sch_P;
	}



	public void setTot_entry_sch_P(double tot_entry_sch_P) {
		this.tot_entry_sch_P = tot_entry_sch_P;
	}



	public double getTot_exit_sch_P() {
		return tot_exit_sch_P;
	}



	public void setTot_exit_sch_P(double tot_exit_sch_P) {
		this.tot_exit_sch_P = tot_exit_sch_P;
	}



	public double getTot_entry_alloc_P() {
		return tot_entry_alloc_P;
	}



	public void setTot_entry_alloc_P(double tot_entry_alloc_P) {
		this.tot_entry_alloc_P = tot_entry_alloc_P;
	}



	public double getTot_exit_alloc_P() {
		return tot_exit_alloc_P;
	}



	public void setTot_exit_alloc_P(double tot_exit_alloc_P) {
		this.tot_exit_alloc_P = tot_exit_alloc_P;
	}



	public double getTotamt_P() {
		return totamt_P;
	}



	public void setTotamt_P(double totamt_P) {
		this.totamt_P = totamt_P;
	}



	public double getTotqty_P() {
		return totqty_P;
	}



	public void setTotqty_P(double totqty_P) {
		this.totqty_P = totqty_P;
	}
	
}


