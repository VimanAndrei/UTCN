package bll.validators;
import model.Clients;

public class NameValidator implements Validators<Clients>{

    @Override
    public void validate(Clients c) {
        if(c.getName().charAt(0) >= 'A' && c.getName().charAt(0) <= 'Z')
        {
            throw new IllegalArgumentException("Numele nu incepe cu litera mare!!");
        }

    }
}
