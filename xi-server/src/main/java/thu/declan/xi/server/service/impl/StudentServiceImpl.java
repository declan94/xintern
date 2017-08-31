package thu.declan.xi.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.StudentMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.Student;
import thu.declan.xi.server.service.StudentService;

/**
 *
 * @author declan
 */
@Service("studentService")
public class StudentServiceImpl extends BaseTableServiceImpl<Student> implements StudentService {

	@Autowired
	private StudentMapper studentMapper;

	@Override
	protected BaseMapper getMapper() {
		return studentMapper;
	}

	@Override
	public Student getByAccountId(int accountId) throws ServiceException {
		Student comp = studentMapper.selectByAccountId(accountId);
		if (comp == null) {
			throw new ServiceException(ServiceException.CODE_NO_SUCH_ELEMENT, "No such student");
		}			
		return comp;
	}
	
}
