package com.jtbdevelopment.lirr.web.filters

import com.googlecode.htmlcompressor.compressor.HtmlCompressor

import javax.servlet.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponseWrapper

/**
 * Date: 8/24/14
 * Time: 8:06 PM
 *
 * Pretty much from http://www.byteslounge.com/tutorials/how-to-compress-response-html-in-java-web-application
 */
class HtmlCompressorFilter implements Filter {
    private HtmlCompressor htmlCompressor = new HtmlCompressor()

    @Override
    void init(final FilterConfig filterConfig) throws ServletException {
        htmlCompressor.compressCss = true;
        htmlCompressor.compressJavaScript = true;
    }

    @Override
    void doFilter(
            final ServletRequest request,
            final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        CharResponseWrapper responseWrapper = new CharResponseWrapper(
                (HttpServletResponse) response);

        chain.doFilter(request, responseWrapper);

        String servletResponse = new String(responseWrapper.toString());
        response.getWriter().write(htmlCompressor.compress(servletResponse));
    }

    @Override
    void destroy() {

    }

    private static class CharResponseWrapper extends HttpServletResponseWrapper {

        private final CharArrayWriter output;

        @Override
        public String toString() {
            return output.toString()
        }

        public CharResponseWrapper(HttpServletResponse response) {
            super(response);
            output = new CharArrayWriter()
        }

        @Override
        public PrintWriter getWriter() {
            return new PrintWriter(output)
        }

    }
}
