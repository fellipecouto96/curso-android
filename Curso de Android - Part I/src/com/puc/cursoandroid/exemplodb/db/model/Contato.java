package com.puc.cursoandroid.exemplodb.db.model;

public class Contato {
    
    //private variables
    int _id;
    String _nome;
    String _telefone;
     
    // Empty constructor
    public Contato(){
         
    }
    // constructor
    public Contato(int id, String nome, String _telefone){
        this._id = id;
        this._nome = nome;
        this._telefone = _telefone;
    }
     
    // constructor
    public Contato(String nome, String _telefone){
        this._nome = nome;
        this._telefone = _telefone;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting nome
    public String getNome(){
        return this._nome;
    }
     
    // setting nome
    public void setNome(String nome){
        this._nome = nome;
    }
     
    // getting phone number
    public String getPhoneNumber(){
        return this._telefone;
    }
     
    // setting phone number
    public void setPhoneNumber(String telefone){
        this._telefone = telefone;
    }
}
