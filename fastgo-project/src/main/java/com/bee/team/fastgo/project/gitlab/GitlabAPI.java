package com.bee.team.fastgo.project.gitlab;

import com.bee.team.fastgo.project.constant.AuthMethod;
import com.bee.team.fastgo.project.constant.TokenType;
import com.bee.team.fastgo.project.model.GitlabBranch;
import com.bee.team.fastgo.project.model.GitlabProjectDo;
import com.bee.team.fastgo.project.model.GitlabProjectHook;
import com.bee.team.fastgo.project.model.GitlabUser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
@Service
public class GitlabAPI {

    public static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final String API_NAMESPACE = "/api/v4";
    private static final String PARAM_SUDO = "sudo";
    private  String hostUrl;

    private  String apiToken;
    //默认请求方式
    private  TokenType tokenType = TokenType.PRIVATE_TOKEN;
    private AuthMethod authMethod = AuthMethod.URL_PARAMETER;
    private String userAgent = GitlabAPI.class.getCanonicalName() + "/" + System.getProperty("java.version");

    public GitlabAPI(){};

    public GitlabAPI(String hostUrl, String apiToken) {
        this.hostUrl = hostUrl.endsWith("/") ? hostUrl.replaceAll("/$", "") : hostUrl;
        this.apiToken = apiToken;
    }

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
     * delete类型请求
     *
     * @return
     */
    public GitlabHTTPRequestor deleteRequest() {
        return new GitlabHTTPRequestor(this).authenticate(apiToken, tokenType, authMethod).method("DELETE");
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
        return getRequest().execute(tailUrl,GitlabProjectDo.class);
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
        return getRequest().execute(tailUrl,GitlabProjectDo.class);
    }

    /**
     * 获取所有项目的信息
     */
    public List<GitlabProjectDo> getAllProject() throws IOException {
        String tailUrl = "projects";
        return getRequest().getAll(tailUrl, GitlabProjectDo[].class);
    }

    /**
     * 创建一个新的项目
     * @param projectName
     * @return
     * @throws IOException
     */
    public GitlabProjectDo createNewProject(String projectName,String projectDesc) throws IOException {
        String tailUrl = "projects";
        return postRequest().addData("name",projectName).addData("description",projectDesc).execute(tailUrl,GitlabProjectDo.class);
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
        return getRequest().addData("ref",ref).execute(tailUrl,GitlabProjectDo.class);
    }

    /**
     * 新建一个webHook项目push事件
     * @param id
     * @return
     * @throws IOException
     */
    public GitlabProjectHook addWebhook(Integer id,String url) throws IOException {
        String tailUrl = "projects/"+ id + "/hooks";
        return postRequest().addData("url",url).execute(tailUrl,GitlabProjectHook.class);
    }

    /**
     * 查询项目所有webhook
     * @param id
     * @return
     * @throws IOException
     */
    public List<GitlabProjectHook> getAllWebhook(Integer id) throws IOException {
        String tailUrl = "projects/"+ id + "/hooks";
        GitlabProjectHook[] gitlabProjectHooks = getRequest().execute(tailUrl,GitlabProjectHook[].class);
        return Arrays.asList(gitlabProjectHooks);
    }

    /**
     * 删除项目webhook
     * @param id，hookId
     * @return
     * @throws IOException
     */
    public GitlabProjectHook deleteWebhook(Integer id,Integer hookId) throws IOException {
        String tailUrl = "projects/"+ id + "/hooks/"+hookId;
        return deleteRequest().execute(tailUrl,GitlabProjectHook.class);
    }

    /**
     * @param id 项目id
     * @return {@link List< GitlabBranch>}
     * @author hs
     * @date 2020/7/27
     * @desc 查询项目所有分支信息
     */

    public List<GitlabBranch> queryAllBranchInfo(Integer id) throws IOException {
        String tailUrl = "projects/"+ id + "/repository/branches";
        GitlabBranch[] branches = getRequest().execute(tailUrl,GitlabBranch[].class);
        return Arrays.asList(branches);
    }

    /**
     * @param
     * @return {@link GitlabUser}
     * @author hs
     * @date 2020/8/3
     * @desc 新建用户
     */
    public GitlabUser createUser(String name,String email,String username,String password) throws IOException {
        String tailUrl = "/users";
        GitlabUser gitlabUser = postRequest().addData("email",email).addData("username",username).addData("name",name).addData("password",password).execute(tailUrl,GitlabUser.class);
        return gitlabUser;
    }

    /**
     * @param
     * @return {@link GitlabUser}
     * @author hs
     * @date 2020/8/3
     * @desc 锁定gitlab账号
     */
    /*public GitlabUser updateUserStatus(String name) throws IOException {
        String tailUrl = "/users";
        GitlabUser gitlabUser = getRequest().addData("email",email).addData("username",username).addData("name",name).addData("password",password).execute(tailUrl,GitlabUser.class);
        return gitlabUser;
    }*/

    /**
     * @param
     * @return {@link GitlabUser}
     * @author hs
     * @date 2020/8/3
     * @desc 修改gitlab用户信息
     */
    public GitlabUser deleteUser(String id) throws IOException {
        String tailUrl = "/users/" + id;
        GitlabUser gitlabUser = deleteRequest().execute(tailUrl,GitlabUser.class);
        return gitlabUser;
    }





    public static void main(String[] args) throws IOException {
        GitlabAPI gitlabAPI = new GitlabAPI("http://172.22.5.242","n5_nL3oQUj_3wAZaQKC6");
        //List<GitlabProjectDo> list = gitlabAPI.getAllProject();
        //GitlabProjectDo gitlabProjectDo = gitlabAPI.createNewProject("hsFirst","hs the first project");
        GitlabProjectHook gitlabProjectHook = gitlabAPI.deleteWebhook(136,66);
        //GitlabProjectDo gitlabProjectDo = gitlabAPI.getProject(136);
        List<GitlabProjectHook> gitlabProjectHookList = gitlabAPI.getAllWebhook(136);
        //GitlabUser gitlabUser = gitlabAPI.createUser("test","15871341469@163.com","test001","123456");
        System.out.println(gitlabProjectHook);
    }

}
