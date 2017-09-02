package thu.declan.xi.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.mapper.ResumeMapper;
import thu.declan.xi.server.mapper.BaseMapper;
import thu.declan.xi.server.model.Resume;
import thu.declan.xi.server.service.ResumeService;

/**
 *
 * @author declan
 */
@Service("resumeService")
public class ResumeServiceImpl extends BaseTableServiceImpl<Resume> implements ResumeService {

	@Autowired
	ResumeMapper resumeMapper;
	
	@Override
	protected BaseMapper<Resume> getMapper() {
		return resumeMapper;
	}
	
	@Override
	public void preAdd(Resume resume) throws ServiceException {
        
	}
	
	@Override
	public void preUpdate(Resume update) {
		
	}

	
}
