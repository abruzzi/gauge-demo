import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StaffingStepImplementation {
    List<EmployeeObject> employees = new ArrayList<EmployeeObject>();

    @Step("目前西安办公室有这样一些同事 <table>")
    public void setBackground(Table table) {
        for (TableRow row : table.getTableRows()) {
            String name = row.getCell("name");
            String currentProject = row.getCell("currentProject");
            String role = row.getCell("role");
            
            employees.add(new EmployeeObject(name, currentProject, role));
        }
    }

    @Step("下面这几个人是可以出台的 <table>")
    public void theFollowingPSShouldBeAssignable(Table employees) {
        
    }
}
