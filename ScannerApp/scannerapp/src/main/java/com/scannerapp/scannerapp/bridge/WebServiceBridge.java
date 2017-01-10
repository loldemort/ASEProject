package com.scannerapp.scannerapp.bridge;

/**
 * Created by oysteinhauan on 10/01/17.
 */
public class WebServiceBridge {

    public WebServiceBridge(String url){
        this.connect(url);
    }

    public void connect(String url){

        //TODO: connect to webservice REST

        try{
            //try connection
        } catch(Exception e){
            //if connection is unsuccesful
        }

    }

    public String receiveQRData(String something){
        return "";
    }

}
