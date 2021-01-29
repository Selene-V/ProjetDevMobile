package fr.ul.iutmetz.wmce.td1.modele;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.Triplet;

public class Panier implements Serializable {

    public ArrayList<Triplet<Produit, Taille, Integer>> basketContent;

    public Panier(Context context, int i, List<Panier> panier) {
    }

    public ArrayList<Triplet<Produit, Taille, Integer>> getBasketContent() {
        return basketContent;
    }

    public void setBasketContent(ArrayList<Triplet<Produit, Taille, Integer>> basketContent) {
        this.basketContent = basketContent;
    }

    public Panier(ArrayList<Triplet<Produit, Taille, Integer>> basketContent) {
        this.basketContent = basketContent;
    }

    public int getBasketSize(){
        return this.basketContent.size();
    }

    public int articleInBasket(int idProduct){
        for(int i = 0; i < getBasketSize(); i++){
            if(this.getBasketContent().get(i).getProduit().getId() == idProduct){
                return i;
            }
        }
        return -1;
    }

    public void updateArticleQuantity(int productId, int newQuantite){
        int index = articleInBasket(productId);
        this.basketContent.get(index).setQuantite(this.basketContent.get(index).getQuantite() + newQuantite);
    }
}
