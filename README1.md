
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


# Interface Description

This application leverages the ScalarDB Cluster gRPC API to perform various database operations. Client stub code was generated in React from Proto files to facilitate interaction with the ScalarDB cluster. The front-end, built using React, ensures seamless integration with the ScalarDB cluster, enabling efficient execution of database operations via the gRPC API.

The application also utilizes Excel APIs to present and manage data within Excel. This involves populating spreadsheets with data, displaying column headers, and offering a user-friendly interface that allows users to interact with the data directly in Excel.

The project is built using React version 18.2.0 and requires the ScalarDB Cluster to be deployed and configured with the appropriate database setup.

## Software and Library Versions

- **Excel Version**: Greater than 2019
- **React Version**: 18.2.0
- **Protocol Buffers**: v27.3
- **ScalarDB Cluster Product Version**: 3.12.2


