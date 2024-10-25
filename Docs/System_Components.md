# Functionality Flow Diagrams
Thorough understanding of how the system operates
 What the components do, their responsibilities, and how they interact.

 ### Authentication Flow
![Create User](create-user.png)

Create User is a sub-functionality of User Management, allowing the Admin to create a user in the database.  
At each layer, the following operations are performed:

1. **CreateUserContainer**: Collects the username, password, namespace name, and privileges from the user (Role Admin) and calls the `createUser` and `grantPrivileges` methods in `GrpcAdminApiService`.
2. The `createUser` and `grantPrivileges` methods make a gRPC request to the Scalar cluster and return the response.

![Delete User](delete-user.png)

Delete User is a sub-functionality of User Management, allowing the Admin to delete a user.  
At each layer, the following operations are performed:

1. **UserManagementPage**: When the user clicks on the delete icon, it takes the username and calls the `dropUser` method in `GrpcAdminApiService`.
2. The `dropUser` method makes a gRPC request to the Scalar cluster and returns the response.

![Get All Users](getAllusers.png)

Get All Users is a sub-functionality of User Management, allowing the Admin to see the list of users.  
At each layer, the following operations are performed:

1. **UserManagementPage**: It takes the namespace name and calls the `getAllUsers` method in `GrpcAdminApiService`.
2. The `getAllUsers` method makes a gRPC request to the Scalar cluster and returns the response.

![Update User](update-user.png)

Update User is a sub-functionality of User Management, allowing the Admin to update a user in the database.  
At each layer, the following operations are performed:

1. **UpdateUserContainer**: Collects the namespace name, table name, and privileges from the user (Role Admin) and calls the `revokePrivileges` and `grantPrivileges` methods in `GrpcAdminApiService`.
2. The `revokePrivileges` and `grantPrivileges` methods make a gRPC request to the Scalar cluster and return the response.


### Database Operations
![Excel Add-In Database Operations](excel-add-in-database-operations.png)
![Excel Add-in Database Operations](excel-add-in-database-operations2.png)

### Database Management
![Excel Add-In Database Management Create Table](Docs/excel-add-in-database-management-create-table.png)

Create Table is a sub-functionality of Database Management, allowing the Admin to create a table in the database.  
At each layer, the following operations are performed:

1. **CreateTableContainer**: Collects the table name and column details from the user and calls the `createTable` method in `GrpcAdminApiService`.
2. The `createTable` method makes a gRPC request to the Scalar cluster and returns the response.
   
![Get All Tables](Docs/getAllTables.png)

Get All Tables is a sub-functionality of User Management, allowing the Admin to see the list of tables.  
At each layer, the following operations are performed:

1. **DatabaseManagementPage**: It takes the namespace name and calls the `getAllTables` method in `GrpcAdminApiService`.
2. The `getAllTables` method makes a gRPC request to the Scalar cluster and returns the response.
3. 
![Excel Add-In Database Management Alter Table](Docs/excel-add-in-database-management-alter-table.png)

Alter Table is a sub-functionality of Database Management, allowing the Admin to alter a table in the database.  
At each layer, the following operations are performed:

1. **CreateTableContainer**: Collects the column details, drop index, and create index details from the user and calls the `addNewColumnToTable`, `dropIndex`, and `createIndex` methods in `GrpcAdminApiService`.
2. The `addNewColumnToTable`, `dropIndex`, and `createIndex` methods make a gRPC request to the Scalar cluster and return the response.

![Excel Add-In Database Management Drop Table](Docs/excel-add-in-database-management-drop-table.png)

Drop Table is a sub-functionality of Database Management, allowing the Admin to drop a table in the database.  
At each layer, the following operations are performed:

1. **DatabaseManagementPage**: When the user clicks on the delete icon, it takes the table name and database name and calls the `dropTable` method in `GrpcAdminApiService`.
2. The `dropTable` method makes a gRPC request to the Scalar cluster and returns the response.


### User Management
![Excel Add-in User Management](excel-add-in-user-management.png)

### Flow Diagrams of Important Functionalities

**User Flow**

![User Flow](UserFlow.jpg)

**Database Operations**
- Admin/Other user
![Database Operations](DatabaseOperations.jpg)


**Database Management**
![Database Management](Database%20Management.jpg)

**User management**
![User Management](UserManagement.jpg)






