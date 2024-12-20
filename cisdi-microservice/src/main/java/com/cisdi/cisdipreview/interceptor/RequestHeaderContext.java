package com.cisdi.cisdipreview.interceptor;

/**
 * 规定所有的Http头中必须携带session 来验证用户身份，采用在拦截器用户名信息绑定到ThreadLocal，以供后续方法使用
 * 声明 上下文
 */

public class RequestHeaderContext {

    private static final ThreadLocal<RequestHeaderContext> REQUEST_HEADER_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    private String userSession;

    private String userId;

    private String userName;

    private String userCode;

    public String getUserSession() {
        return userSession;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public static RequestHeaderContext getInstance() {
        return REQUEST_HEADER_CONTEXT_THREAD_LOCAL.get();
    }

    public void setContext(RequestHeaderContext context) {
        REQUEST_HEADER_CONTEXT_THREAD_LOCAL.set(context);
    }

    public static void clean() {
        REQUEST_HEADER_CONTEXT_THREAD_LOCAL.remove();
    }

    private RequestHeaderContext(RequestHeaderContextBuild requestHeaderContextBuild) {
        this.userSession = requestHeaderContextBuild.userSession;
        this.userId = requestHeaderContextBuild.userId;
        this.userCode = requestHeaderContextBuild.userCode;
        this.userName = requestHeaderContextBuild.userName;
        setContext(this);
    }

    public static class RequestHeaderContextBuild {
        private String userSession;

        private String userId;

        private String userName;

        private String userCode;

        public RequestHeaderContextBuild userSession(String userSession) {
            this.userSession = userSession;
            return this;
        }

        public RequestHeaderContextBuild userId(String userId) {
            this.userId = userId;
            return this;
        }

        public RequestHeaderContextBuild userName(String userName) {
            this.userName = userName;
            return this;
        }

        public RequestHeaderContextBuild userCode(String userCode) {
            this.userCode = userCode;
            return this;
        }

        public RequestHeaderContext bulid() {
            return new RequestHeaderContext(this);
        }
    }
}
