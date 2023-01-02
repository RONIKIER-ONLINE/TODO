package online.ronikier.todo.templete;

public abstract class SuperService implements SuperInterface {

    protected String userToken;

    public boolean securityCheckOK() {
        //return userToken != null;
        return true;
    }

   public void kill() {
       userToken = null;
    }


}
