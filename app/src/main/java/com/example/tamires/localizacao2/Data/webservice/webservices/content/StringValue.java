package com.example.tamires.localizacao2.Data.webservice.webservices.content;

import java.io.Serializable;

public class StringValue implements Serializable
{
    private String iv;

    public StringValue(String iv)
    {
        this.iv = iv;
    }

    public String getIv()
    {
        return iv;
    }

    public void setIv(String iv)
    {
        this.iv = iv;
    }
}
