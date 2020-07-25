package com.bee.team.fastgo.project.gitlab;

import com.bee.team.fastgo.project.constant.AuthMethod;
import com.bee.team.fastgo.project.constant.TokenType;
import com.bee.team.fastgo.project.model.GitlabProjectDo;
import com.bee.team.fastgo.project.model.GitlabProjectHook;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
@Service
public class GitlabAPI {

    public static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final String API_NAMESPACE = "/api/v4";
    private static final String PARAM_SUDO = "sudo";
    private  String hostUrl;

    private  String apiToken;
    private  TokenType tokenType;
    private AuthMethod authMethod;
    private String userAgent = GitlabAPI.class.getCanonicalName() + "/" + System.getProperty("java.version");

    public GitlabAPI(){};

    public GitlabAPI(String hostUrl, String apiToken, TokenType tokenType, AuthMethod method) {
        this.hostUrl = hostUrl.endsWith("/") ? hostUrl.replaceAll("/$", "") : hostUrl;
        this.apiToken = apiToken;
        this.tokenType = tokenType;
        this.authMethod = method;
    }


    /**
     * 默认get请求请求
     *
     * @return
     */
    public GitlabHTTPRequestor getRequest() {
        return new GitlabHTTPRequestor(this).authenticate(apiToken, tokenType, authMethod);
    }

    /**
     * post类型请求
     *
     * @return
     */
    public GitlabHTTPRequestor postRequest() {
        return new GitlabHTTPRequestor(this).authenticate(apiToken, tokenType, authMethod).method("POST");
    }

    /**
     * 获取请求路径
     *
     * @param tailAPIUrl
     * @return
     * @throws IOException
     */
    public URL getAPIUrl(String tailAPIUrl) throws IOException {
        if (!tailAPIUrl.startsWith("/")) {
            tailAPIUrl = "/" + tailAPIUrl;
        }
        return new URL(hostUrl + API_NAMESPACE + tailAPIUrl);
    }

    /**
     * gitlab api 请求连接
     *
     * @param hostUrl  请求地址
     * @param apiToken 请求token
     * @return
     */
    public static GitlabAPI connect(String hostUrl, String apiToken) {
        return new GitlabAPI(hostUrl, apiToken, TokenType.PRIVATE_TOKEN, AuthMethod.HEADER);
    }

    /**
     * @param hostUrl   请求地址
     * @param apiToken  请求token
     * @param tokenType token类型
     * @return
     */
    public static GitlabAPI connect(String hostUrl, String apiToken, TokenType tokenType) {
        return new GitlabAPI(hostUrl, apiToken, tokenType, AuthMethod.HEADER);
    }

    /**
     * @param hostUrl   请求地址
     * @param apiToken  请求token
     * @param tokenType token类型
     * @param method    验证方法
     * @return
     */
    public static GitlabAPI connect(String hostUrl, String apiToken, TokenType tokenType, AuthMethod method) {
        return new GitlabAPI(hostUrl, apiToken, tokenType, method);
    }

    public String getUserAgent(){
        return this.userAgent;
    }

    /**
     * 根据项目id获取项目信息
     * @param id
     * @return
     * @throws IOException
     */
    public GitlabProjectDo getProject(Integer id) throws IOException {
        String tailUrl = "projects/" + id;
        return getRequest().authenticate("n5_nL3oQUj_3wAZaQKC6",TokenType.PRIVATE_TOKEN,AuthMethod.URL_PARAMETER).execute(tailUrl,GitlabProjectDo.class);
    }

    /**
     * 根据项目名和命名空间查询项目信息（涉及到问题：https://blog.csdn.net/qq_31772441/article/details/107094856）
     * @param projectName
     * @param nameSpace
     * @return
     * @throws IOException
     */
    public GitlabProjectDo getProject(String projectName,String nameSpace) throws IOException {
        String tailUrl = "projects/"+nameSpace+"%2F"+projectName;
        return getRequest().authenticate("n5_nL3oQUj_3wAZaQKC6",TokenType.PRIVATE_TOKEN,AuthMethod.URL_PARAMETER).execute(tailUrl,GitlabProjectDo.class);
    }

    /**
     * 获取所有项目的信息
     */
    public List<GitlabProjectDo> getAllProject() throws IOException {
        String tailUrl = "projects";
        return getRequest().authenticate("n5_nL3oQUj_3wAZaQKC6",TokenType.PRIVATE_TOKEN,AuthMethod.URL_PARAMETER).getAll(tailUrl, GitlabProjectDo[].class);
    }

    /**
     * 创建一个新的项目
     * @param projectName
     * @return
     * @throws IOException
     */
    public GitlabProjectDo createNewProject(String projectName,String projectDesc) throws IOException {
        String tailUrl = "projects";
        return postRequest().authenticate("n5_nL3oQUj_3wAZaQKC6",TokenType.PRIVATE_TOKEN,AuthMethod.URL_PARAMETER).addData("name",projectName).addData("description",projectDesc).execute(tailUrl,GitlabProjectDo.class);
    }

    /**
     * http://172.22.5.242/api/v4/projects/2/pipelines?private_token=n5_nL3oQUj_3wAZaQKC6&ref=dev1.0
     *
     * 获取项目指定分支所有的pipelines
     * @param id
     * @return
     * @throws IOException
     */
    public GitlabProjectDo getAllPipelines(Integer id,String ref) throws IOException {
        String tailUrl = "projects/" + id + "/pipelines";
        return getRequest().authenticate("n5_nL3oQUj_3wAZaQKC6",TokenType.PRIVATE_TOKEN,AuthMethod.URL_PARAMETER).addData("ref",ref).execute(tailUrl,GitlabProjectDo.class);
    }

    /**
     * 新建一个项目push事件
     * @param id
     * @return
     * @throws IOException
     */
    public GitlabProjectHook addWebhook(Integer id,String url) throws IOException {
        String tailUrl = "projects/"+ id + "/hooks";
        return postRequest().authenticate("n5_nL3oQUj_3wAZaQKC6",TokenType.PRIVATE_TOKEN,AuthMethod.URL_PARAMETER).addData("url",url).execute(tailUrl,GitlabProjectHook.class);
    }




    public static void main(String[] args) throws IOException {
        GitlabAPI gitlabAPI = new GitlabAPI("http://172.22.5.242",null,null,null);
        //List<GitlabProjectDo> list = gitlabAPI.getAllProject();
        GitlabProjectDo gitlabProjectDo = gitlabAPI.createNewProject("hsFirst","hs the first project");
        System.out.println(gitlabProjectDo);
    }

}
