package com.b07.store.frontend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.NotEnoughResourcesException;
import com.b07.exceptions.UnauthorizedException;
import com.b07.store.AdminFunctions;
import com.b07.store.CustomerFunctions;
import com.b07.store.EmployeeFunctions;
import com.b07.store.FindWantedData;
import com.b07.users.User;
import com.example.teambriancanweswitchourname.DatabaseDriverAndroid;

/**
 * Allows users to access different options depending on whether they are admin, employee, or
 * customer.
 * 
 */
public class GeneralLogIn {
  

  /**
   * Checks that user passes in a valid ID (integer) and a valid password.
   * 
   * @throws NumberFormatException
   * @throws IOException
   * @throws SQLException
   * @throws DatabaseInsertException
   * @throws UnauthorizedException
   * @throws NotEnoughResourcesException
   */
  public static int checkUserIdAndPassword(DatabaseDriverAndroid mydb) throws NumberFormatException, IOException,
      SQLException, DatabaseInsertException, UnauthorizedException, NotEnoughResourcesException {

    int userId = UserInput.getInputUserId(mydb);
    User user = (User) mydb.getUserDetails(userId);
    boolean authenticated = user.authenticate(UserInput.getInputUserPassword(), mydb);
    if (authenticated) {
      return userId;
    } else {
      return 0;
    }

  }

  /**
   * Shows user correct menu depending on what they are (admin, employee, or customer)
   *
   * @param mydb
   * @throws NumberFormatException
   * @throws IOException
   * @throws SQLException
   * @throws DatabaseInsertException
   * @throws UnauthorizedException
   * @throws NotEnoughResourcesException
   * @throws ConnectionFailedException 
   */
  public static DatabaseDriverAndroid passCorrectMenu(DatabaseDriverAndroid mydb) throws NumberFormatException, IOException, SQLException,
      DatabaseInsertException, UnauthorizedException, NotEnoughResourcesException, ConnectionFailedException {
    int userId = UserInput.getInputUserId(mydb);
    User user = (User) mydb.getUserDetails(userId);
    int roleId = mydb.getUserRole(userId);
    boolean authenticated = user.authenticate(UserInput.getInputUserPassword(), mydb);
    
    if (authenticated) {
      if (FindWantedData.findRoleId("CUSTOMER", mydb) == roleId) {
        CustomerFunctions.setCurrentCustomer(userId, mydb);
      } else if (FindWantedData.findRoleId("ADMIN",mydb) == roleId) {
        AdminFunctions.setCurrentAdmin(userId, mydb);
      } else if (FindWantedData.findRoleId("EMPLOYEE", mydb) == roleId) {
        EmployeeFunctions.setCurrentEmployee(userId, mydb);
      }
    } else {
      System.out.println("Incorrect password session ended.");
    }
    return mydb;
  }


}
