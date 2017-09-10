package thu.declan.xi.server.mapper;

import thu.declan.xi.server.model.News;

/**
 *
 * @author declan
 */
public interface NewsMapper extends BaseMapper<News> {
	
	void incViewCnt(int id);
}
