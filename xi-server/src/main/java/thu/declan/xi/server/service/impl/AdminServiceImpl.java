package thu.declan.xi.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.AdminMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.Admin;
import thu.declan.xi.server.service.AdminService;
import thu.declan.xi.server.util.EncryptionUtils;

/**
 *
 * @author declan
 */
@Service("adminService")
public class AdminServiceImpl extends BaseTableServiceImpl<Admin> implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public Admin login(String username, String password) throws ServiceException {
		Admin admin = adminMapper.selectByUsername(username);
		if (admin == null) {
			throw new ServiceException(ServiceException.CODE_NO_SUCH_ELEMENT, "Wrong username.");
		}
		if (!admin.getPassword().equals(EncryptionUtils.md5(password))) {
			throw new ServiceException(ServiceException.CODE_WRONG_PASSWORD, "Wrong password.");
		}
		return admin;
	}
	
	@Override
	public void preAdd(Admin admin) throws ServiceException {
        if (adminMapper.selectCount(new Admin(admin.getUsername())) > 0) {
            throw new ServiceException(ServiceException.CODE_DUPLICATE_ELEMENT, "Username already exists.");
        }
		admin.setPassword(EncryptionUtils.md5(admin.getPassword()));
        admin.setPassword2(EncryptionUtils.md5(admin.getPassword2()));
	}
	
	@Override
	public void preUpdate(Admin update) {
		if (update.getPassword() != null) {
			update.setPassword(EncryptionUtils.md5(update.getPassword()));
		}
        if (update.getPassword2() != null) {
            update.setPassword2(EncryptionUtils.md5(update.getPassword2()));
        }
	}

	@Override
	protected BaseMapper getMapper() {
		return adminMapper;
	}

    @Override
    public Admin delete(int id) throws ServiceException {
        Admin admin = get(id);
        adminMapper.delete(id);
        return admin;
    }
	
}
