package thu.declan.xi.server.filter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import thu.declan.xi.server.exception.ApiException;
import thu.declan.xi.server.exception.ErrorMessage;
import thu.declan.xi.server.exception.ServiceException;
import thu.declan.xi.server.model.Admin;
import thu.declan.xi.server.service.AdminService;
import thu.declan.xi.server.service.LoginService;

/**
 * This filter verify the access permissions for a user based on username and
 * passowrd provided in request
 *
 * @author declan
 */
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Context
    private ResourceInfo resourceInfo;

    @Autowired
    AdminService adminService;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Override
    public void filter(ContainerRequestContext requestContext) {

        // Let OPTIONS request go. for CORS request.
        // Maybe we should delete this before release.
        if (requestContext.getMethod().equals("OPTIONS")) {
            return;
        }

        LOGGER.debug("-------------AuthorizationFilter begin------------");

        Method method = resourceInfo.getResourceMethod();
        Class cls = resourceInfo.getResourceClass();

        boolean permitAll = method.isAnnotationPresent(PermitAll.class);
        boolean denyAll = method.isAnnotationPresent(DenyAll.class);
        Set<String> rolesSet = null;

        if (!denyAll && !permitAll) {
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));
            } else {
                denyAll = cls.isAnnotationPresent(DenyAll.class);
                permitAll = cls.isAnnotationPresent(PermitAll.class);
                if (!permitAll && !denyAll && cls.isAnnotationPresent(RolesAllowed.class)) {
                    RolesAllowed rolesAnnotation = (RolesAllowed) cls.getAnnotation(RolesAllowed.class);
                    rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));
                }
            }
        }

        if (permitAll) {
            return;
        }

        LOGGER.debug("roles set: " + rolesSet);

        if (denyAll || rolesSet == null) {
            final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("Access Forbidden")
                    .build();
            requestContext.abortWith(ACCESS_FORBIDDEN);
            return;
        }

        //Get request headers
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();

        //Fetch authorization header
        List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

        final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
                .type(MediaType.TEXT_PLAIN)
                .entity("Access Denied")
                .build();

        //If no authorization information present; block access
        if (authorization == null || authorization.isEmpty()) {
            authorization = requestContext.getUriInfo().getQueryParameters().get(AUTHORIZATION_PROPERTY);
            if (authorization == null || authorization.isEmpty()) {
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }
        }

        //Get encoded username and password
        final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        //Decode username and password
        String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        if (!tokenizer.hasMoreTokens()) {
            requestContext.abortWith(ACCESS_DENIED);
            return;
        }
        final String username = tokenizer.nextToken();
        if (!tokenizer.hasMoreTokens()) {
            requestContext.abortWith(ACCESS_DENIED);
            return;
        }
        final String password = tokenizer.nextToken();
        boolean isAdmin = tokenizer.hasMoreTokens();
        boolean authorized = false;

        LoginService loginService = isAdmin ? adminService : null;
        Object obj;
        try {
            obj = loginService.login(username, password);
        } catch (ServiceException ex) {
            String devMsg = "Service Error [" + ex.getCode() + "]: " + ex.getReason();
            String userMsg;
            switch (ex.getCode()) {
                case ServiceException.CODE_NO_SUCH_ELEMENT:
                    userMsg = "用户名错误.";
                    break;
                case ServiceException.CODE_WRONG_PASSWORD:
                    userMsg = "密码错误.";
                    break;
                case ServiceException.CODE_ACCOUNT_FROZEN:
                    userMsg = "账户已冻结，请联系管理员.";
                    break;
                default:
                    userMsg = "未知错误.";
            }
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ErrorMessage(new ApiException(401, devMsg, userMsg)))
                    .build());
            return;
        }
        if (isAdmin) {
            Admin admin = (Admin) obj;
            if (null != admin) {
                if (rolesSet.contains(admin.getRole().toString())) {
                    authorized = true;
                    requestContext.setProperty("admin", admin);
                }
            }
        } else {
//            Member member = (Member) obj;
//            if (member != null && rolesSet.contains(Constant.ROLE_USER)) {
//                authorized = true;
//                requestContext.setProperty("member", member);
//            }
        }

        if (!authorized) {
            requestContext.abortWith(ACCESS_DENIED);
        }

        LOGGER.debug("-------------AuthorizationFilter end------------");
    }
}
