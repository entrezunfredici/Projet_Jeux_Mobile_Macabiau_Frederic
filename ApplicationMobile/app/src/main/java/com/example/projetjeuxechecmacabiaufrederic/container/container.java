package com.example.projetjeuxechecmacabiaufrederic.container;

public class container {
    static element pTail=null;
    static element pHead=null;
    static element element=null;
    static void add_Element(element newElement){
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
    static void del_Elem(element DelElem){
        if(DelElem.pPrev!=null & DelElem.pNext!=null){
            DelElem.pNext.pPrev=DelElem.pPrev;
            DelElem.pPrev.pNext=DelElem.pNext;
        }else if(DelElem.pPrev!=null){
            DelElem.pNext.pPrev=null;
            pHead=DelElem.pPrev;
        }else if(DelElem.pNext!=null){
            DelElem.pPrev.pNext=null;
            pTail=DelElem.pPrev;
        }
        DelElem=null;
    }
}
