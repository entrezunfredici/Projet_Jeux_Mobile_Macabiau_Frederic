package com.example.projetjeuxechecs2024.container;

public class node <type>{
    node pPrev;
    node pNext;
    type data;

    public node(node init_pPrev, node init_pNext, type initData) {
        super();
        pPrev=init_pPrev;
        pNext=init_pNext;
        data=initData;
        if(pNext!=null)pNext.pPrev=this;
        if(pPrev!=null)pPrev.pNext=this;
    }
    public void delNode(){
        if(pNext!=null)pNext.pPrev=pPrev;
        if(pPrev!=null)pPrev.pNext=pNext;
    }
}
