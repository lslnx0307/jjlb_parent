package com.jjlb.service.main;

import com.jjlb.common.BaseResult;
import com.jjlb.model.dto.fuiou.OnlineBankingRechargeResultDto;
import com.jjlb.model.dto.member.DrMemberBankDto;
import com.jjlb.model.dto.member.DrMemberFastRechargeDto;
import com.jjlb.model.dto.member.DrMemberOnlineBankingRechargeDto;
import com.jjlb.model.dto.member.DrMemberWithdrawalsDto;
import com.jjlb.model.dto.product.InvestDto;

/**
 *
 * @author lslnx0307
 * @date 2018/5/22
 */
public interface MainService {

    /**
     * 开户
     * @param dto
     * @return
     */
    BaseResult<String> openAccount(DrMemberBankDto dto);


    /**
     * 快捷充值
     * @param dto
     * @return
     */
    BaseResult<String> fastRecharge(DrMemberFastRechargeDto dto) throws Exception;

    /**
     * 网银充值
     * @param dto
     * @return
     */
    BaseResult<OnlineBankingRechargeResultDto> onlineBankingRecharge(DrMemberOnlineBankingRechargeDto dto);

    /**
     * 投资接口
     * @param dto
     * @return
     */
    BaseResult<String> invest(InvestDto dto);

    /**
     * 提现
     * @param dto
     * @return
     */
    BaseResult<String> withdraw(DrMemberWithdrawalsDto dto);

    /**
     * 计息
     * @return
     */
    void interestRate();

    void productRepay();


}
