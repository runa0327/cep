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

    private String pCcPrjIds;

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

    public String getpCcPrjIds() {
        return pCcPrjIds;
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
        this.pCcPrjIds = requestHeaderContextBuild.pCcPrjIds;
        setContext(this);
    }

    public static class RequestHeaderContextBuild {
        private String userSession;

        private String userId;

        private String userName;

        private String userCode;

        private String pCcPrjIds;

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

        public RequestHeaderContextBuild pCcPrjIds(String pCcPrjIds) {
            this.pCcPrjIds = pCcPrjIds;
            return this;
        }

        public RequestHeaderContext bulid() {
            return new RequestHeaderContext(this);
        }
    }
}
