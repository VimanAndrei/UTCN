package ViewModel;


import Model.UserService;
import ViewModel.CommandsAdministrator.DeleteCommand;
import ViewModel.CommandsAdministrator.InsertCommand;
import ViewModel.CommandsAdministrator.ListAllCommand;
import ViewModel.CommandsAdministrator.UpdateCommand;
import lombok.Getter;
import lombok.Setter;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;


@Getter
@Setter
public class VMAdministrator {
    private Property<String> username = PropertyFactory.createProperty("username", this, String.class);
    private Property<String> password = PropertyFactory.createProperty("password", this, String.class);
    private Property<String> role = PropertyFactory.createProperty("role", this, String.class);
    private String selectedUsername ;
    private String selectedPassword ;
    private String selectedRole;

    private UserService userService;
    private Object[][] data = null;

    public ICommand listAllCommand;
    public ICommand updateCommand;
    public ICommand insertCommand;
    public ICommand deleteCommand;


    public VMAdministrator()
    {
        this.userService = new UserService();
        listAllCommand = new ListAllCommand(this,userService);
        updateCommand = new UpdateCommand(this,userService);
        insertCommand = new InsertCommand(this,userService);
        deleteCommand = new DeleteCommand(this,userService);
    }
}
