package com.thymeleaf.TestThymeleaf.event;

public class CustomGeneric<T> {
    private T what;
    protected boolean success;

    public CustomGeneric(T what, boolean success) {
        this.what = what;
        this.success = success;
    }
  
    public T getWhat()
    {
      return what;
    }
  
    public boolean getSuccess()
    {
      return success;
    }
}
