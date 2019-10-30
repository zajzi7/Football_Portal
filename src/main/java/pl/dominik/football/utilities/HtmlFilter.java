//package pl.dominik.football.utilities;
//
//import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
////Class needed for html minification
//@Component
//public class HtmlFilter implements Filter {
//    protected FilterConfig config;
//
//    public void init(FilterConfig config) throws ServletException {
//        this.config = config;
//    }
//
//    public void destroy() {
//    }
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        ServletResponse newResponse = response;
//
//        if (request instanceof HttpServletRequest) {
//            newResponse = new CharResponseWrapper((HttpServletResponse) response);
//        }
//
//        chain.doFilter(request, newResponse);
//
//        if (newResponse instanceof CharResponseWrapper) {
//            String text = newResponse.toString();
//            if (text != null) {
//                HtmlCompressor htmlCompressor = new HtmlCompressor();
//                response.getWriter().write(htmlCompressor.compress(text));
//            }
//        }
//    }
//}