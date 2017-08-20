/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package service;

import service.client.HttpClientService;

/**
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class ClientManager implements iService
{
    private HttpClientService hcs;

    public ClientManager()
    {
	this.hcs = new HttpClientService();
    }
    
    /**
     * Give the the HTTP Client needed for all the HTTP request
     *
     * @return HttpClientService
     */
    public HttpClientService getHttpClientService()
    {
        return (this.hcs);
    }
}

