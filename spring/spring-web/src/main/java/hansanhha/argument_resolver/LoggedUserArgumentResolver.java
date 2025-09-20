package hansanhha.argument_resolver;

import org.apache.catalina.User;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

public class LoggedUserArgumentResolver implements HandlerMethodArgumentResolver {

    // 컨트롤러 메서드 파라미터가 @LoggedUser를 선언하고 UserPrincipal 타입인 경우
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoggedUser.class)
                && UserPrincipal.class.isAssignableFrom(parameter.getParameterType());
    }

    // HTTP 요청 헤더 정보를 기반으로 UserPrincipal 객체를 생성한다
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = request.getHeader("X-Auth-Token");
        if (token == null) {
            return null;
        }
        return new UserPrincipal(token);
    }
    
}
