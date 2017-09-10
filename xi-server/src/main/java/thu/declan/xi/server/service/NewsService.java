package thu.declan.xi.server.service;

import thu.declan.xi.server.model.News;

/**
 *
 * @author declan
 */
public interface NewsService extends BaseTableService<News> {
	
	public void delete(int id);
	
	public void incView(int id);
	
}
