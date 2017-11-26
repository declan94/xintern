package thu.declan.xi.server.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thu.declan.xi.server.Constant;
import thu.declan.xi.server.exception.ApiException;
import thu.declan.xi.server.model.UploadResult;
import thu.declan.xi.server.util.EncryptionUtils;

/**
 *
 * @author declan
 */
@Path("upload")
@RolesAllowed({Constant.ROLE_ADMIN, Constant.ROLE_COMPANY, Constant.ROLE_STUDENT})
public class UploadResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadResource.class);
    
	@POST
	@PermitAll
	@Path("log")
	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
	public String uploadLog(String log) {
		LOGGER.error("Frontend Log: " + log);
		return log;
	}
	
    @POST
	@Path("{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public UploadResult uploadImage(@PathParam("type") String type,
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@Context UriInfo ui,
			@Context HttpServletRequest request) throws ApiException {
        LOGGER.debug("==================== enter UploadResource uploadImage ====================");
		String oldName = fileDetail.getFileName();
		System.out.println(oldName);
		String [] tmp = oldName.split("\\.");
		String fileType = tmp[tmp.length-1];
		String newFileName = (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_")).format(new Date())
				+ EncryptionUtils.randomPassword(5) + "." + fileType;
		String uploadDir = Constant.UPLOAD_DIR + "/" + type;
		File uploadDirF = new File(uploadDir);
		if (!uploadDirF.exists() && !uploadDirF.mkdirs()) {
			throw new ApiException(500, "Failed to create folder: " + uploadDir, "内部错误");
		}
		String fullPath = uploadDir + "/" + newFileName;
		URI uri = ui.getBaseUri();
		String url = request.getScheme() + "://" + uri.getHost();
		if (uri.getPort() > -1 && uri.getPort() != 80 && uri.getPort() != 443) {
			url = url + ":" + uri.getPort();
		}
		url = url + Constant.UPLOAD_CONTEXT_PATH + "/" + type + "/" + newFileName;
		UploadResult result = new UploadResult(url);
        try (OutputStream out = new FileOutputStream(new File(fullPath))) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = fileInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        } catch (IOException e) {
			result.setSuccess(false);
			result.setMsg("文件上传失败");
			return result;
        }
        LOGGER.debug("==================== leave UploadResource uploadImage ====================");
		return result;
    }
    
}
