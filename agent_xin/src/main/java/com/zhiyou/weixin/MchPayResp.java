package com.zhiyou.weixin;

public class MchPayResp {

    private boolean ok;
    private String return_code;
    private String return_msg;
    
    private String result_code;
    private String partner_trade_no;
    private String payment_no;
    private String payment_time;
    private String err_code;
    private String err_code_des;
    public boolean isOk() {
        return ok;
    }
    public void setOk(boolean ok) {
        this.ok = ok;
    }
    public String getReturn_code() {
        return return_code;
    }
    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }
    public String getReturn_msg() {
        return return_msg;
    }
    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
    public String getResult_code() {
        return result_code;
    }
    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }
    public String getPartner_trade_no() {
        return partner_trade_no;
    }
    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }
    public String getPayment_no() {
        return payment_no;
    }
    public void setPayment_no(String payment_no) {
        this.payment_no = payment_no;
    }
    public String getPayment_time() {
        return payment_time;
    }
    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }
    public String getErr_code() {
        return err_code;
    }
    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }
    public String getErr_code_des() {
        return err_code_des;
    }
    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }
    @Override
    public String toString() {
        return "MchPayResp [ok=" + ok + ", return_code=" + return_code + ", return_msg=" + return_msg + ", result_code="
                + result_code + ", partner_trade_no=" + partner_trade_no + ", payment_no=" + payment_no
                + ", payment_time=" + payment_time + ", err_code=" + err_code + ", err_code_des=" + err_code_des + "]";
    }
    
    
}
