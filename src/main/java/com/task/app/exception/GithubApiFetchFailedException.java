package com.task.app.exception;

public class GithubApiFetchFailedException extends Exception{
    public GithubApiFetchFailedException(String message){
        super(message);
    }
}
