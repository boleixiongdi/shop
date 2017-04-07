package com.rt.shop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_orderform")
public class OrderForm implements Serializable {
	@TableField(exist = false)
	List<GoodsCart> gcs = new ArrayList();
	@TableField(exist = false)
	 private Payment payment;
	 @TableField(exist = false)
	 private ExpressCompany ec;
	   //退货的物流公司
	 @TableField(exist = false)
	   private ExpressCompany return_ec;
	   @TableField(exist = false)
	   private User user;
	   //店铺
	   @TableField(exist = false)
	     private Store store;
	     @TableField(exist = false)
	     private Address addr;
	     @TableField(exist = false)
	     private List<OrderLog> ofls = new ArrayList();
	     @TableField(exist = false)
	     private List<RefundLog> rls = new ArrayList();
	     @TableField(exist = false)
	     private List<GoodsReturnlog> grls = new ArrayList();
	     @TableField(exist = false)
	     private List<Evaluate> evas = new ArrayList();
	     @TableField(exist = false)
	     private List<Complaint> complaints = new ArrayList();
	      //优惠券
	     @TableField(exist = false)
	     private CouponInfo ci;
	     
	     
	public List<GoodsCart> getGcs() {
			return gcs;
		}

		public void setGcs(List<GoodsCart> gcs) {
			this.gcs = gcs;
		}

		public Payment getPayment() {
			return payment;
		}

		public void setPayment(Payment payment) {
			this.payment = payment;
		}

		public ExpressCompany getEc() {
			return ec;
		}

		public void setEc(ExpressCompany ec) {
			this.ec = ec;
		}

		public ExpressCompany getReturn_ec() {
			return return_ec;
		}

		public void setReturn_ec(ExpressCompany return_ec) {
			this.return_ec = return_ec;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Store getStore() {
			return store;
		}

		public void setStore(Store store) {
			this.store = store;
		}

		public Address getAddr() {
			return addr;
		}

		public void setAddr(Address addr) {
			this.addr = addr;
		}

		public List<OrderLog> getOfls() {
			return ofls;
		}

		public void setOfls(List<OrderLog> ofls) {
			this.ofls = ofls;
		}

		public List<RefundLog> getRls() {
			return rls;
		}

		public void setRls(List<RefundLog> rls) {
			this.rls = rls;
		}

		public List<GoodsReturnlog> getGrls() {
			return grls;
		}

		public void setGrls(List<GoodsReturnlog> grls) {
			this.grls = grls;
		}

		public List<Evaluate> getEvas() {
			return evas;
		}

		public void setEvas(List<Evaluate> evas) {
			this.evas = evas;
		}

		public List<Complaint> getComplaints() {
			return complaints;
		}

		public void setComplaints(List<Complaint> complaints) {
			this.complaints = complaints;
		}

		public CouponInfo getCi() {
			return ci;
		}

		public void setCi(CouponInfo ci) {
			this.ci = ci;
		}

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	private Date finishTime;

	/**  */
	@TableField(value = "goods_amount")
	private BigDecimal goods_amount;

	/**  */
	private String invoice;

	/**  */
	private Integer invoiceType;

	/**  */
	private String msg;

	/**  */
	@TableField(value = "order_id")
	private String order_id;

	/**  */
	@TableField(value = "order_status")
	private Integer order_status;

	/**  */
	private Date payTime;

	/**  */
	@TableField(value = "pay_msg")
	private String pay_msg;

	/**  */
	private BigDecimal refund;

	/**  */
	@TableField(value = "refund_type")
	private String refund_type;

	/**  */
	private String shipCode;

	/**  */
	private Date shipTime;

	/**  */
	@TableField(value = "ship_price")
	private BigDecimal ship_price;

	/**  */
	private BigDecimal totalPrice;

	/**  */
	@TableField(value = "addr_id")
	private Long addr_id;

	/**  */
	@TableField(value = "payment_id")
	private Long payment_id;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

	/**  */
	@TableField(value = "user_id")
	private Long user_id;

	/**  */
	@TableField(value = "auto_confirm_email")
	private Boolean auto_confirm_email;

	/**  */
	@TableField(value = "auto_confirm_sms")
	private Boolean auto_confirm_sms;

	/**  */
	private String transport;

	/**  */
	@TableField(value = "out_order_id")
	private String out_order_id;

	/**  */
	@TableField(value = "ec_id")
	private Long ec_id;

	/**  */
	@TableField(value = "ci_id")
	private Long ci_id;

	/**  */
	@TableField(value = "order_seller_intro")
	private String order_seller_intro;

	/**  */
	@TableField(value = "return_shipCode")
	private String return_shipCode;

	/**  */
	@TableField(value = "return_ec_id")
	private Long return_ec_id;

	/**  */
	@TableField(value = "return_content")
	private String return_content;

	/**  */
	@TableField(value = "return_shipTime")
	private Date return_shipTime;

	/**  */
	@TableField(value = "order_type")
	private String order_type;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Boolean getDeleteStatus() {
		return this.deleteStatus;
	}

	public void setDeleteStatus(Boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public BigDecimal getGoods_amount() {
		return this.goods_amount;
	}

	public void setGoods_amount(BigDecimal goods_amount) {
		this.goods_amount = goods_amount;
	}

	public String getInvoice() {
		return this.invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public Integer getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOrder_id() {
		return this.order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public Integer getOrder_status() {
		return this.order_status;
	}

	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPay_msg() {
		return this.pay_msg;
	}

	public void setPay_msg(String pay_msg) {
		this.pay_msg = pay_msg;
	}

	public BigDecimal getRefund() {
		return this.refund;
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

	public String getRefund_type() {
		return this.refund_type;
	}

	public void setRefund_type(String refund_type) {
		this.refund_type = refund_type;
	}

	public String getShipCode() {
		return this.shipCode;
	}

	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

	public Date getShipTime() {
		return this.shipTime;
	}

	public void setShipTime(Date shipTime) {
		this.shipTime = shipTime;
	}

	public BigDecimal getShip_price() {
		return this.ship_price;
	}

	public void setShip_price(BigDecimal ship_price) {
		this.ship_price = ship_price;
	}

	public BigDecimal getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getAddr_id() {
		return this.addr_id;
	}

	public void setAddr_id(Long addr_id) {
		this.addr_id = addr_id;
	}

	public Long getPayment_id() {
		return this.payment_id;
	}

	public void setPayment_id(Long payment_id) {
		this.payment_id = payment_id;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Boolean getAuto_confirm_email() {
		return this.auto_confirm_email;
	}

	public void setAuto_confirm_email(Boolean auto_confirm_email) {
		this.auto_confirm_email = auto_confirm_email;
	}

	public Boolean getAuto_confirm_sms() {
		return this.auto_confirm_sms;
	}

	public void setAuto_confirm_sms(Boolean auto_confirm_sms) {
		this.auto_confirm_sms = auto_confirm_sms;
	}

	public String getTransport() {
		return this.transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getOut_order_id() {
		return this.out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	public Long getEc_id() {
		return this.ec_id;
	}

	public void setEc_id(Long ec_id) {
		this.ec_id = ec_id;
	}

	public Long getCi_id() {
		return this.ci_id;
	}

	public void setCi_id(Long ci_id) {
		this.ci_id = ci_id;
	}

	public String getOrder_seller_intro() {
		return this.order_seller_intro;
	}

	public void setOrder_seller_intro(String order_seller_intro) {
		this.order_seller_intro = order_seller_intro;
	}

	public String getReturn_shipCode() {
		return this.return_shipCode;
	}

	public void setReturn_shipCode(String return_shipCode) {
		this.return_shipCode = return_shipCode;
	}

	public Long getReturn_ec_id() {
		return this.return_ec_id;
	}

	public void setReturn_ec_id(Long return_ec_id) {
		this.return_ec_id = return_ec_id;
	}

	public String getReturn_content() {
		return this.return_content;
	}

	public void setReturn_content(String return_content) {
		this.return_content = return_content;
	}

	public Date getReturn_shipTime() {
		return this.return_shipTime;
	}

	public void setReturn_shipTime(Date return_shipTime) {
		this.return_shipTime = return_shipTime;
	}

	public String getOrder_type() {
		return this.order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

}
