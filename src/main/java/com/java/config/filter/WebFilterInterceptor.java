package com.java.config.filter;


import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.java.config.InstanceFactory;
import com.java.moudle.common.message.JsonResult;
import com.java.moudle.common.message.ProcessStatus;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.service.SysUserService;
import com.java.until.SysUtil;
import com.java.until.cache.CacheUntil;
import com.java.until.cache.RedisCacheEmun;

@Configuration
public  class WebFilterInterceptor extends WebMvcConfigurationSupport  {

	@Value("${trepath}")
    private String trepath;
	
	
	@Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
	
    @Override
	public void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**");
	}
    
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }
    
    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    @Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		return corsConfiguration;
	}

	@Override
    public void addInterceptors(InterceptorRegistry registry){
        
        /**
         * 	平台验证
         */
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            	String method = request.getMethod();
				if(method.equalsIgnoreCase("OPTIONS")){
					response.getOutputStream().write("Success".getBytes("utf-8"));

				}
				//从header里获取参数client+username+token
				Map<String, Object> headerMap = SysUtil.getSecurityKey(request);
				if(headerMap == null) {
					onAuthFail(request, response);
					return false;
				}
				// 客户端请求的缓存key
				String securitykey = (String) headerMap.get("securitykey");
				// 验证缓存中无数据，需要重新获取缓存信息
				String jsonStr = CacheUntil.get(RedisCacheEmun.USER_CACHE, securitykey, String.class);
				if (null == jsonStr) {
					onAuthFail(request, response);
					return false;
				}
				//获取登录用户信息，为空查询数据库并保存到redis中
				String token = (String) headerMap.get("securitytoken");
				SysUser user = CacheUntil.get(RedisCacheEmun.USER_CACHE, token, SysUser.class);
				if(user == null) {
					//从缓存中获取token (key的值待定)
					String refreshToken = "refresh_auth:" + JSONObject.parseObject(jsonStr).getString("refreshToken");
					String authParamStr = CacheUntil.get(RedisCacheEmun.USER_CACHE, refreshToken, String.class);
					//通过用户id获取登录者信息
					String principalStr = JSONObject.parseObject(authParamStr).getString("principal");
					String userId = JSONObject.parseObject(principalStr).getString("id");
					
					user = InstanceFactory.getInstance(SysUserService.class).getUserInfo(userId);
					CacheUntil.put(RedisCacheEmun.USER_CACHE, token, user);
				}
            	return true;
            }
        }).addPathPatterns(String.format("%s/**", trepath));
    }
	
	/**
	 * 
	 * <li>描述:身份认证错误默认返回401状态码</li>
	 * <li>方法名称:onAuthFail</li>
	 * <li>参数:@param response
	 * <li>参数:@throws Exception</li>
	 * <li>返回类型:void</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	private void onAuthFail(ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("jsonp");
        JsonResult ret = new JsonResult("请重新认证用户信息", ProcessStatus.AUTH_ERROR);
        String resultString = request.getParameter("callback") + "(" + JSON.toJSONString(ret, SerializerFeature.WriteDateUseDateFormat) + ")";
        PrintWriter out = null ;
        out = response.getWriter();
        out.write(resultString);
        out.flush();
        out.close();
	}
}
