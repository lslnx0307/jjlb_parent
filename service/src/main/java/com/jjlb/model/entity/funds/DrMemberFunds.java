package com.jjlb.model.entity.funds;

import java.math.BigDecimal;

public class DrMemberFunds {
	
	 private Integer uid;

    private BigDecimal crushCount;

    private BigDecimal carryCount;

    private BigDecimal investAmount;

    private BigDecimal balance;

    private BigDecimal freeze;

    private BigDecimal wprincipal;

    private BigDecimal winterest;

    private BigDecimal wpenalty;
    
    private BigDecimal investProfit;//投资收益
    
    private BigDecimal spreadProfit;//其他收益
    
    private BigDecimal fuiou_crushcount;//恒丰充值总额
    private BigDecimal fuiou_carrycount;//恒丰提现总额
    private BigDecimal fuiou_investAmount;//恒丰投资总额
    private BigDecimal fuiou_balance;//恒丰可用余额
    private BigDecimal fuiou_freeze;//恒丰冻结金额
    private BigDecimal fuiou_wprincipal;//恒丰待收本金
    private BigDecimal fuiou_winterest;//恒丰待收利息
    private BigDecimal fuiou_wpenalty;//恒丰待收罚息 (改为代收补偿金)
    private BigDecimal fuiou_investProfit;//恒丰投资获得收益
    private BigDecimal fuiou_spreadProfit;//恒丰推广收益
    
    /**
     * 存管待收利息+平台待收利息
     * @return
     */
    public void getWinterestAddFuiou(){
    	winterest = this.fuiou_winterest.add(this.winterest);
    }

    /**
     * 存管待收本金+平台待收本金
     * @return
     */
    public void getWprincipalAddFuiou(){
    	wprincipal = this.fuiou_wprincipal.add(this.wprincipal);
    }
    
    public BigDecimal getFuiou_crushcount() {
		return fuiou_crushcount;
	}

	public void setFuiou_crushcount(BigDecimal fuiou_crushcount) {
		this.fuiou_crushcount = fuiou_crushcount;
	}

	public BigDecimal getFuiou_carrycount() {
		return fuiou_carrycount;
	}

	public void setFuiou_carrycount(BigDecimal fuiou_carrycount) {
		this.fuiou_carrycount = fuiou_carrycount;
	}

	public BigDecimal getFuiou_investAmount() {
		return fuiou_investAmount;
	}

	public void setFuiou_investAmount(BigDecimal fuiou_investAmount) {
		this.fuiou_investAmount = fuiou_investAmount;
	}

	public BigDecimal getFuiou_balance() {
		return fuiou_balance;
	}

	public void setFuiou_balance(BigDecimal fuiou_balance) {
		this.fuiou_balance = fuiou_balance;
	}

	public BigDecimal getFuiou_freeze() {
		return fuiou_freeze;
	}

	public void setFuiou_freeze(BigDecimal fuiou_freeze) {
		this.fuiou_freeze = fuiou_freeze;
	}

	public BigDecimal getFuiou_wprincipal() {
		return fuiou_wprincipal;
	}

	public void setFuiou_wprincipal(BigDecimal fuiou_wprincipal) {
		this.fuiou_wprincipal = fuiou_wprincipal;
	}

	public BigDecimal getFuiou_winterest() {
		return fuiou_winterest;
	}

	public void setFuiou_winterest(BigDecimal fuiou_winterest) {
		this.fuiou_winterest = fuiou_winterest;
	}

	public BigDecimal getFuiou_wpenalty() {
		return fuiou_wpenalty;
	}

	public void setFuiou_wpenalty(BigDecimal fuiou_wpenalty) {
		this.fuiou_wpenalty = fuiou_wpenalty;
	}

	public BigDecimal getFuiou_investProfit() {
		return fuiou_investProfit;
	}

	public void setFuiou_investProfit(BigDecimal fuiou_investProfit) {
		this.fuiou_investProfit = fuiou_investProfit;
	}

	public BigDecimal getFuiou_spreadProfit() {
		return fuiou_spreadProfit;
	}

	public void setFuiou_spreadProfit(BigDecimal fuiou_spreadProfit) {
		this.fuiou_spreadProfit = fuiou_spreadProfit;
	}

	/**
     * 交易变动金额
     */
    private BigDecimal changeAmount;
	public DrMemberFunds() {
		super();
	}

	public DrMemberFunds(Integer uid, BigDecimal crushCount,
			BigDecimal carryCount, BigDecimal investAmount,
			BigDecimal balance, BigDecimal freeze, BigDecimal wprincipal,
			BigDecimal winterest, BigDecimal wpenalty) {
		super();
		this.uid = uid;
		this.crushCount = crushCount;
		this.carryCount = carryCount;
		this.setInvestAmount(investAmount);
		this.balance = balance;
		this.freeze = freeze;
		this.wprincipal = wprincipal;
		this.winterest = winterest;
		this.wpenalty = wpenalty;
	}
	
	
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreeze() {
        return freeze;
    }

    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
    }

    public BigDecimal getWprincipal() {
        return wprincipal;
    }

    public void setWprincipal(BigDecimal wprincipal) {
        this.wprincipal = wprincipal;
    }

    public BigDecimal getWinterest() {
        return winterest;
    }

    public void setWinterest(BigDecimal winterest) {
        this.winterest = winterest;
    }

    public BigDecimal getWpenalty() {
        return wpenalty;
    }

    public void setWpenalty(BigDecimal wpenalty) {
        this.wpenalty = wpenalty;
    }

	public BigDecimal getCarryCount() {
		return carryCount;
	}

	public void setCarryCount(BigDecimal carryCount) {
		this.carryCount = carryCount;
	}

	public BigDecimal getCrushCount() {
		return crushCount;
	}

	public void setCrushCount(BigDecimal crushCount) {
		this.crushCount = crushCount;
	}

	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	public BigDecimal getInvestProfit() {
		return investProfit;
	}

	public void setInvestProfit(BigDecimal investProfit) {
		this.investProfit = investProfit;
	}

	public BigDecimal getSpreadProfit() {
		return spreadProfit;
	}

	public void setSpreadProfit(BigDecimal spreadProfit) {
		this.spreadProfit = spreadProfit;
	}

}