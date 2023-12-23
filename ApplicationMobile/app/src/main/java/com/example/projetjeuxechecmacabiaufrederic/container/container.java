package com.example.projetjeuxechecmacabiaufrederic.container;

public class container {
    element pTail=null;
    element pHead=null;
    element element=null;
    void add_Element(element newElement){
        if(pTail!=null){
            element=newElement;
            element.pNext=null;
            element.pPrev=pTail;
            pTail.pNext=element;
            pTail=element;
        }else{
            pHead=pTail=newElement;
        }
    }
    void del_Elem(element DelElem){
        
    }
}
