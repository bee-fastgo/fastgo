package com.bee.team.fastgo.project.gitlab;

import com.bee.team.fastgo.project.constant.AuthMethod;
import com.bee.team.fastgo.project.constant.RequestMethod;
import com.bee.team.fastgo.project.constant.TokenType;
import com.bee.team.fastgo.project.model.GitlabCommit;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class GitlabHTTPRequestor {

    private static final Pattern PAGE_PATTERN = Pattern.compile("([&|?])page=(\\d+)");

    //请求基本信息
    private final GitlabAPI root;
    //请求默认方法类型
    private String method = "GET";
    //请求头或者请求体里的数据
    private Map<String, Object> data = new HashMap<String, Object>();
    //请求里附加的数据
    private Map<String, File> attachments = new HashMap<String, File>();
    //token值
    private String apiToken;
    //token类型，private_token/access_token
    private TokenType tokenType;
    //验证类型
    private AuthMethod authMethod;


    public GitlabHTTPRequestor(GitlabAPI root) {
        this.root = root;
    }

    /**
     * HTTP请求权限验证
     *
     * @param token
     * @param type
     * @param method
     * @return
     */
    public GitlabHTTPRequestor authenticate(String token, TokenType type, AuthMethod method) {
        this.apiToken = token;
        this.tokenType = type;
        this.authMethod = method;
        return this;
    }

    /**
     * 指定请求方法类型
     *
     * @param method
     * @return
     */
    public GitlabHTTPRequestor method(String method) {
        try {
            this.method = RequestMethod.valueOf(method).toString();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("无效的请求类型: " + method + "， 请输入指定类型" + RequestMethod.prettyValues());
        }
        return this;
    }

    /**
     * 添加请求数据
     *
     * @param key
     * @param value
     * @return
     */
    public GitlabHTTPRequestor addData(String key, Object value) {
        if (!StringUtils.isEmpty(key) && !ObjectUtils.isEmpty(value)) {
            data.put(key, value);
        }
        return this;
    }

    public <T> T execute(String tailAPIUrl) throws IOException {
        return execute(tailAPIUrl,null);
    }

    /**
     * 请求gitlab api 接口
     * @param tailAPIUrl
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> T execute(String tailAPIUrl, Class<T> type) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = setupConnection(root.getAPIUrl(tailAPIUrl));
            if (hasOutput()) {
                submitData(connection);
            } else if ("PUT".equals(method)) {
                connection.setDoOutput(true);
                connection.setFixedLengthStreamingMode(0);
            }
            try {
                return parse(connection, type);
            } catch (IOException e) {
                handleAPIError(e, connection);
            }

            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     *
     * @param connection
     * @throws IOException
     */
    private void submitData(HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        GitlabAPI.MAPPER.writeValue(connection.getOutputStream(), data);
    }

    private boolean hasOutput() {
        return method.equals("POST") || method.equals("PUT") && !data.isEmpty();
    }


    /**
     * 创建http连接
     * @param url
     * @return
     * @throws IOException
     */
    private HttpURLConnection setupConnection(URL url) throws IOException {
        if (apiToken != null && authMethod == AuthMethod.URL_PARAMETER) {
            String urlWithAuth = url.toString();
            urlWithAuth = urlWithAuth + (urlWithAuth.indexOf('?') > 0 ? '&' : '?') + tokenType.getTokenParamName() + "=" + apiToken;
            url = new URL(urlWithAuth);
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (apiToken != null && authMethod == AuthMethod.HEADER) {
            connection.setRequestProperty(tokenType.getTokenHeaderName(), String.format(tokenType.getTokenHeaderFormat(), apiToken));
        }
        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException e) {
            try {
                Field methodField = connection.getClass().getDeclaredField("method");
                methodField.setAccessible(true);
                methodField.set(connection, method);
            } catch (Exception x) {
                throw (IOException) new IOException("Failed to set the custom verb").initCause(x);
            }
        }
        connection.setRequestProperty("User-Agent", root.getUserAgent());
        connection.setRequestProperty("Accept-Encoding", "gzip");
        return connection;
    }

    /**
     *读取io流内容，并转换成对应的对象
     * @param connection
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    private <T> T parse(HttpURLConnection connection, Class<T> type) throws IOException {
        InputStreamReader reader = null;
        try {
            if (byte[].class == type) {
                return type.cast(IOUtils.toByteArray(wrapStream(connection, connection.getInputStream())));
            }
            reader = new InputStreamReader(wrapStream(connection, connection.getInputStream()), "UTF-8");
            String data = IOUtils.toString(reader);
            if (type != null && type != Void.class) {
                return GitlabAPI.MAPPER.readValue(data, type);
            } else {
                return null;
            }
        } catch (SSLHandshakeException e) {
            throw new SSLHandshakeException("You can disable certificate checking by setting ignoreCertificateErrors on GitlabHTTPRequestor. SSL Error: " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }


    /**
     * 判断io流类型
     * @param connection
     * @param inputStream
     * @return
     * @throws IOException
     */
    private InputStream wrapStream(HttpURLConnection connection, InputStream inputStream) throws IOException {
        String encoding = connection.getContentEncoding();

        if (encoding == null || inputStream == null) {
            return inputStream;
        } else if (encoding.equals("gzip")) {
            return new GZIPInputStream(inputStream);
        } else {
            throw new UnsupportedOperationException("Unexpected Content-Encoding: " + encoding);
        }
    }

    /**
     * io流类型异常类
     * @param e
     * @param connection
     * @throws IOException
     */
    private void handleAPIError(IOException e, HttpURLConnection connection) throws IOException {
        if (e instanceof FileNotFoundException) {
            throw e;    // pass through 404 Not Found to allow the caller to handle it intelligently
        }
        if (e instanceof SocketTimeoutException) {
            throw e;
        }

        InputStream es = wrapStream(connection, connection.getErrorStream());
        try {
            String error = null;
            if (es != null) {
                error = IOUtils.toString(es, "UTF-8");
            }
            //throw new Exception(error == null?"":error);
        } finally {
            IOUtils.closeQuietly(es);
        }
    }

    public <T> List<T> getAll(final String tailUrl, final Class<T[]> type) {
        List<T> results = new ArrayList<T>();
        Iterator<T[]> iterator = asIterator(tailUrl, type);

        while (iterator.hasNext()) {
            T[] requests = iterator.next();

            if (requests.length > 0) {
                results.addAll(Arrays.asList(requests));
            }
        }
        return results;
    }

    public <T> Iterator<T> asIterator(final String tailApiUrl, final Class<T> type) {
        // 只适合GET requests
        method("GET");

        // Ensure that we don't submit any data and alert the user
        if (!data.isEmpty()) {
            throw new IllegalStateException();
        }

        return new Iterator<T>() {
            T next;
            URL url;

            {
                try {
                    url = root.getAPIUrl(tailApiUrl);
                } catch (IOException e) {
                    throw new Error(e);
                }
            }

            @Override
            public boolean hasNext() {
                fetch();
                if (next != null && next.getClass().isArray()) {
                    Object[] arr = (Object[]) next;
                    return arr.length != 0;
                } else {
                    return next != null;
                }
            }

            @Override
            public T next() {
                fetch();
                T record = next;

                if (record == null) {
                    throw new NoSuchElementException();
                }

                next = null;
                return record;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private void fetch() {
                if (next != null) {
                    return;
                }

                if (url == null) {
                    return;
                }

                try {
                    HttpURLConnection connection = setupConnection(url);
                    try {
                        next = parse(connection, type);
                        assert next != null;
                        findNextUrl();
                    } catch (IOException e) {
                        handleAPIError(e, connection);
                    }
                } catch (IOException e) {
                    throw new Error(e);
                }
            }

            private void findNextUrl() throws MalformedURLException {
                String url = this.url.toString();

                this.url = null;
                /* Increment the page number for the url if a "page" property exists,
                 * otherwise, add the page property and increment it.
                 * The Gitlab API is not a compliant hypermedia REST api, so we use
                 * a naive implementation.
                 */
                Matcher matcher = PAGE_PATTERN.matcher(url);

                if (matcher.find()) {
                    Integer page = Integer.parseInt(matcher.group(2)) + 1;
                    this.url = new URL(matcher.replaceAll(matcher.group(1) + "page=" + page));
                } else {
                    if (GitlabCommit[].class == type) {
                        // there is a bug in the Gitlab CE API
                        // (https://gitlab.com/gitlab-org/gitlab-ce/issues/759)
                        // that starts pagination with page=0 for commits
                        this.url = new URL(url + (url.indexOf('?') > 0 ? '&' : '?') + "page=1");
                    } else {
                        // Since the page query was not present, its safe to assume that we just
                        // currently used the first page, so we can default to page 2
                        this.url = new URL(url + (url.indexOf('?') > 0 ? '&' : '?') + "&page=2");
                    }
                }
            }
        };
    }


}
