/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package service.client;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The HTTP request can be done by HTTP Client Service
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class HttpClientService
{
    private final OkHttpClient client;
    
    private Request.Builder builder;
    
    private Request request;
    
    private Response response;
    
    private final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    
    private String url;
    
    private String body;
    
    public HttpClientService()
    {
        this.client = new OkHttpClient();
    }
    
    /**
     * Define a body to send
     *
     * @param _body JSON expected
     * @return body
     */
    public HttpClientService setBody(String _body)
    {
        this.body = _body;
        return (this);
    }
    
    /**
     * Get the JSON body
     *
     * @return body
     */
    public String getBody()
    {
        return (this.body);
    }
    
    /**
     * Define the url to use for the request
     *
     * @param _url full url for the request ej. http://DNS.com:PORT/PAGE
     * @return HttpClientService
     */
    public HttpClientService setUrl(String _url)
    {
        this.url = _url;
        this.builder.url(_url);
        return (this);
    }
    
    /**
     * Url used for the request
     *
     * @return url
     */
    public String getUrl()
    {
        return (this.url);
    }
    
    /**
     * Define a header for the request
     *
     * @param key key of the header
     * @param value value for the key defined
     * @return HttpClientService
     */
    public HttpClientService setHeader(String key, String value)
    {
        this.builder.header(key, value);
        return (this);
    }
    
    /**
     * The HTTPClientService had to be initialized before be build and execute
     *
     * @return HttpClientService
     */
    public HttpClientService init()
    {
        this.builder = new Request.Builder();
        return (this);
    }
    
    /**
     * Build the request and execute a post
     *
     * @throws IOException catch execution fail
     */
    public void buildAndExecutePost() throws IOException
    {
        this.request = this.builder
                .post(RequestBody.create(this.MEDIA_TYPE_MARKDOWN, this.body))
                .build();
        this.response = this.client.newCall(this.request).execute();
    }
    
    /**
     * Build the request and execute a get
     *
     * @throws IOException catch execution fail
     */
    public void buildAndExecuteGet() throws IOException
    {
        this.request = this.builder.build();
        this.response = this.client.newCall(this.request).execute();
    }
    
	/**
     * Build the request and execute a delete
     *
     * @throws IOException catch execution fail
     */
    public void buildAndExecuteDelete() throws IOException
    {
        this.request = this.builder.delete().build();
        this.response = this.client.newCall(this.request).execute();
    }
	
    /**
     * HTTP code receive by the server
     *
     * @return code
     */
    public int getReponseCode()
    {
        return (this.response.code());
    }
    
    /**
     * String return in the body by the server
     *
     * @return body response of the server
     * @throws IOException catch body reading fail
     */
    public String getResponseBody() throws IOException
    {
        return (this.response.body().string());
    }
}
