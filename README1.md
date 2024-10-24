
## Introduction
This Excel add-in is designed for users and developers who work with ScalarDB, providing an interface to access and manage their databases and perform administrative tasks. With this tool, users can perform actions such as scanning, inserting, updating, deleting records, creating tables, and executing various database-related operations efficiently from within Excel.

## Scope
The scope of this add-in includes database management for users who rely on ScalarDB. It supports various database operations like CRUD actions (Create, Read, Update, Delete), table creation, and advanced administrative tasks such as managing database schemas, user permissions (to be implemented later), and querying data.  
It works with all the underlying databases that ScalarDB supports.

## System Architecture

### Objective
The primary objective of this Excel Add-in is to streamline database management for users interacting with underlying ScalarDB, making it more accessible and user-friendly. It aims to eliminate the need for complex database commands or external tools by integrating database operations directly into Excel, allowing users to manage their ScalarDB data in a familiar environment. 

This tool empowers both technical and non-technical users to efficiently perform tasks such as data querying, record management, and administrative operations, all while reducing the potential for errors and increasing productivity.

## Application Architecture

![System Design Diagram](Docs/System%20Design%20Diagram.drawio.png)

# Key Files and Their Explanations in the Application

## Key Configuration Files

### Manifest File
This file is essential for initializing the Excel Add-in. You can upload it to the "My Add-ins" section in Excel to get started.

### Proto Files
We use three Proto files:
- `scalardb-auth.proto`
- `scalardb-cluster.proto`
- `scalardb-cluster-sql.proto`

These files correspond to ScalarDB Cluster version 3.13.0 and define the communication structure between the client and ScalarDB via gRPC.

### Helm Values File
The `scalardb-cluster-custom-values.yaml` file is used to deploy the ScalarDB cluster using Helm. It defines the custom configurations required for creating the ScalarDB cluster.

