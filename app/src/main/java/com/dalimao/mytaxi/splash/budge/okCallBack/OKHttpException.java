package com.dalimao.mytaxi.splash.budge.okCallBack;

public class OKHttpException {
    private static final long serialVersionUID=1L;

    //返回的异常状态码
    private int ecode;

    //返回的异常信息
    private Object emsg;

    public OKHttpException(int ecode,Object emsg){
        this.ecode=ecode;
        this.emsg=emsg;
    }

    public int getEcode(){
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
