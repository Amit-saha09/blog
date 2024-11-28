/****author-->Simi****/
package com.example.blog.model;

// interface for Subject
public interface Subject
{
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}