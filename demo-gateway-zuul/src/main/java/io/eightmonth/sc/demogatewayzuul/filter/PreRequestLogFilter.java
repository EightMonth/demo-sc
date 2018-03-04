package io.eightmonth.sc.demogatewayzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 此类配置后需要加入spring容器
 * 本例做法在启动类注册
 */
public class PreRequestLogFilter extends ZuulFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreRequestLogFilter.class);

    @Override
    public String filterType() {
        // 可返回值 pre post error route
        // pre 路由之前
        // route 构建发送服务请求时(路由时)
        // post 路由后
        // error 发生错误时
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 参考spring AOP order
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行此过滤
        return true;
    }

    @Override
    public Object run() {
        // 过滤内容
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        LOGGER.info("SEND {} REQUEST TO {}", request.getMethod(), request.getRequestURL().toString());
        return null;
    }
}
