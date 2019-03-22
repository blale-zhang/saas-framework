package org.smr.ministore.security.properties.session;

public class SessionProperties {

    /**
     * 最大并发用户数
     */
    private int maximumSessions;

    /**
     * 达到最大session数是否阻止登录
     */
    private boolean maxSessionsPreventsLogin;

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public boolean isMaxSessionsPreventsLogin() {
        return maxSessionsPreventsLogin;
    }

    public void setMaxSessionsPreventsLogin(boolean maxSessionsPreventsLogin) {
        this.maxSessionsPreventsLogin = maxSessionsPreventsLogin;
    }
}
