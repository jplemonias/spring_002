package com.thymeleaf.TestThymeleaf.model;

public class User {
    // private int id;
    private String name;
    private String champion;
    private int hp;
    

    public User(){
        this("Default Name");
    }
    
    public User(String name){
      // this(5, name, "Wawa", 10);
      this(name, "Wawa", 10);
    }

    public User(String name, String champion, int hp){
        // this.id = id;
        this.name = name;
        this.champion = champion;
        this.hp = hp;
    }
  
    // public int getId()
    // {
    //   return id;
    // }
    // public void setId(int id)
    // {
    //   this.id = id;
    // }
  
    public String getName()
    {
      return name;
    }
    public void setName(String name)
    {
      this.name = name;
    }
  
    public String getChampion()
    {
      return champion;
    }
    public void setChampion(String champion)
    {
      this.champion = champion;
    }
  
    public int getHp()
    {
      return hp;
    }
    
    public void setHp(int hp)
    {
      this.hp = hp;
    }

    @Override
    public String toString() {
        return "Le/La champion.ne s'appel " + name +
          ", avec " + hp + "points de vie " +
          "et est un.e "+ champion;
    }
}
