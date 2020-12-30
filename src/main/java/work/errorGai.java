package work;

public class errorGai extends Exception
{
    private String message;
    public errorGai(String myMessage)
    {
        this.message = myMessage;
    }
    @Override
    public String getMessage()
    {
        return this.message;
    }
}
