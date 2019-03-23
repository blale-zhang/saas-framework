package org.smr.ministore.security.properties;


import org.smr.ministore.security.properties.browser.BrowserProperties;
import org.smr.ministore.security.properties.session.SessionProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sys.security")
public class SecurityProperties {

    /**
     * 登录页面
     */
    private String loginPage;

    /**
     * 登录接口
     */
    private String loginProcessingUrl;

    /**
     *
     */
    private String loginHtml;

    /**
     * 登出接口
     */
    private String loginOut;

    /**
     * 登录成功后跳转页面
     */
    private String loginSuccessUrl;

    /**
     * session失效
     */
    private String sessionInvalidUrl;

    /**
     * 最大并发用户数
     */
    private int maximumSessions;


    private SessionProperties session = new SessionProperties();


    private BrowserProperties browser = new BrowserProperties();

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }

    public void setLoginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
    }

    public String getLoginHtml() {
        return loginHtml;
    }

    public void setLoginHtml(String loginHtml) {
        this.loginHtml = loginHtml;
    }

    public String getLoginOut() {
        return loginOut;
    }

    public void setLoginOut(String loginOut) {
        this.loginOut = loginOut;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public String getSessionInvalidUrl() {
        return sessionInvalidUrl;
    }

    public void setSessionInvalidUrl(String sessionInvalidUrl) {
        this.sessionInvalidUrl = sessionInvalidUrl;
    }

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
