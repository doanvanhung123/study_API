package test;

public class LearnEnum {

    public enum By{
        ENV,DEV,ESS;
    }
    public void useEnum(String e){
       By enumValue = By.valueOf(e);
       if(enumValue==By.DEV){
           System.out.println("true");
       }
    }

    public static void main(String[] args) {
        LearnEnum learnEnum = new LearnEnum();
        learnEnum.useEnum("DEV");
    }
}
