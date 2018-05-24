package com.jjlb.service.redis.impl;

import com.jjlb.dao.fuiou.SysFuiouNoticeLogDAO;
import com.jjlb.dao.member.DrMemberDAO;
import com.jjlb.dao.member.DrMemberFuiouDAO;
import com.jjlb.model.entity.fuiou.SysFuiouNoticeLog;
import com.jjlb.model.entity.member.DrMember;
import com.jjlb.model.entity.member.DrMemberFuIou;
import com.jjlb.service.redis.SysFuiouNoticeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class SysFuiouNoticeLogServiceImpl implements SysFuiouNoticeLogService {

	@Autowired
	private SysFuiouNoticeLogDAO sysFuiouNoticeLogDAO;
	@Autowired
	private DrMemberFuiouDAO drMemberFuiouDAO;
	@Autowired
	private DrMemberDAO drMemberDAO;
	

	@Override
	public void insert(SysFuiouNoticeLog sysFuiouNoticeLog) {
		sysFuiouNoticeLogDAO.insert(sysFuiouNoticeLog);
	}
	@Override
	public SysFuiouNoticeLog selectObjectBy_txn_ssn(String mchnt_txn_ssn) {
		
		return sysFuiouNoticeLogDAO.selectObjectBy_txn_ssn(mchnt_txn_ssn);
	}

	@Override
	public void update(SysFuiouNoticeLog sysFuiouNoticeLog) {
		sysFuiouNoticeLogDAO.update(sysFuiouNoticeLog);
	}
	@Override
	public void updateByMchnt_txn_ssn(SysFuiouNoticeLog sysFuiouNoticeLog) {
		sysFuiouNoticeLogDAO.updateByMchnt_txn_ssn(sysFuiouNoticeLog);
	}
	@Override
	public void insertAbnormalFiling(SysFuiouNoticeLog fnl, String type) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("mchnt_txn_ssn", fnl.getMchnt_txn_ssn());
			map.put("mchTime", fnl.getMchnt_txn_ssn().substring(0, 8));
			map.put("type", type);
			map.put("out_cust_no", fnl.getUser_id());
			DrMemberFuIou fuiou = drMemberFuiouDAO.getDrMemberFuiouByFuiouId(fnl.getUser_id());
			DrMember member = drMemberDAO.selectByPrimaryKey(fuiou.getUserId());
			String realname = sysFuiouNoticeLogDAO.selectMembmerOutCustRealname(member.getMobilephone());
			if (realname == null || realname == "") {
				realname = sysFuiouNoticeLogDAO.selectCompanyOutCustRealname(fnl.getUser_id());
			}
			if (realname == null || realname == "") {
				System.out.println("冻结出账人姓名为空");
				
				return ;
			}
			
			map.put("realname", realname);
			map.put("amount", fnl.getAmt());
			
			sysFuiouNoticeLogDAO.insertAbnormalFiling(map);
		} catch (Exception e) {
		}
		
		
	}
}