package org.smr.saas.comp.mybatis.conf;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Druid配置
 */
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //白名单：
        bean.addInitParameter("allow","");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        bean.addInitParameter("deny","");
        //登录查看信息的账号密码.
        bean.addInitParameter("loginUsername","");
        bean.addInitParameter("loginPassword","");
        //是否能够重置数据.
        bean.addInitParameter("resetEnable","false");

        return bean;
    }

}
