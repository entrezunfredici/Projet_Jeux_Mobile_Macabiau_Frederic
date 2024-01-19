package com.example.projetjeuxechecs2024.container;

public class container {
    static node pTail=null;
    static node pHead=null;
    static int card=0;
    public container(){
        super();
        card=0;
        pTail=null;
        pHead=null;
    }
    public static <type> void containerPushBack(type pElem){
        if(card==0){
            pHead=pTail=new node(null,null,pElem);
        }else{
            pTail=new node(pTail, null, pElem);
        }
        card++;
    }
    public <type> type ContainerParse(int getElem){
        node pScan=pHead;
        for(int n=0; n<getElem; n++){
            if(pScan.pNext!=null){
                pScan=pScan.pNext;
            }
        }
        return (type) pScan.data;
    }
    public <type> type ContainerGetHead(){
        return (type) pHead.data;
    }
    public <type> type ContainerGetTail(){
        return (type) pTail.data;
    }
    public int getCard(){return card;}
}
