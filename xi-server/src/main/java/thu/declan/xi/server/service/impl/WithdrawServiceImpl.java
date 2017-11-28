package thu.declan.xi.server.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.AccountMapper;
import thu.declan.xi.server.mapper.WithdrawMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.Account;
import thu.declan.xi.server.model.Withdraw;
import thu.declan.xi.server.service.WithdrawService;

/**
 *
 * @author declan
 */
@Service("withdrawService")
public class WithdrawServiceImpl extends BaseTableServiceImpl<Withdraw> implements WithdrawService {

    @Autowired
    private WithdrawMapper withdrawMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    protected BaseMapper getMapper() {
        return withdrawMapper;
    }

    @Override
    protected void preAdd(Withdraw withdraw) throws ServiceException {
        int accId = withdraw.getAccountId();
        Account acc = accountMapper.selectOne(accId);
        double value = withdraw.getValue();
        if (acc.getBalance() < value) {
            throw new ServiceException(ServiceException.CODE_VERIFY_FAILED, "Score not enough");
        }
        accountMapper.addBalance(accId, -value);
    }

    @Override
    protected void postGetList(List<Withdraw> withdraws) {
        for (Withdraw withdraw : withdraws) {
            postGet(withdraw);
        }
    }

    @Override
    protected void postGet(Withdraw withdraw) {
        withdraw.setAccount(accountMapper.selectOne(withdraw.getAccountId()));
    }

}
