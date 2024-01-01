package com.example.projetjeuxechecmacabiaufrederic;

import android.widget.Button;

public class PartySelecter {
    private String mDefaultButton;
    private int iParty;
    private int mLink;
    private String text;

    public PartySelecter(String DefaultButton,int Link, String initText){
        mDefaultButton=DefaultButton;
        mLink=Link;
        text = initText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDefaultButton(){
        return mDefaultButton;
    }

    public int getLink(){
        return mLink;
    }

    public int getiParty(){
        return iParty;
    }

    public void setIParty(int init){iParty=init;}
}
