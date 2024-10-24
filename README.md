
# Deployment Documentation

## Prerequisites

- Setting Up Kubernetes (EKS) for the ScalarDB Cluster  
- EC2 for Frontend Deployment  
- AWS Route 53 for Domain Setup and Record Creation  

## Deployment Architecture Overview
![Deployment Diagram](Deployment Diagram.drawio .png)

## Deploy Frontend/React to EC2 Instance
Begin by deploying your React-based front end to an EC2 instance.



## Deploy ScalarDB Cluster to EKS
Set up your ScalarDB cluster on EKS (Elastic Kubernetes Service) using Helm. Detailed deployment steps can be found in the following guide: [Helm ScalarDB Cluster Configuration](https://scalardb.scalar-labs.com/docs/latest/scalardb-cluster/setup-scalardb-cluster-on-kubernetes-by-using-helm-chart/)

### Applying HTTPS
Both the frontend (React) and the ScalarDB cluster must have HTTPS configured to ensure secure communication. This is essential because, without HTTPS, you cannot share or use the Excel add-in within Microsoft 365. Once HTTPS is properly set up for both components, you can then proceed to share the updated `manifest.xml` for the add-in deployment.



## Steps to Deploy a React Front-end on EC2 with Apache and SSL

### 1. Prepare Your EC2 Instance
- Launch an EC2 instance with the desired specifications.
- Navigate to the EC2 dashboard and search for Elastic IPs under the Network & Security section.
- Allocate an Elastic IP address to your EC2 instance. You can refer to the guide at the following link for detailed steps: [How to add a static IP to an AWS EC2 instance](https://dev.to/bashirk/how-to-add-a-static-ip-to-an-aws-ec2-instance-2hea)
- Use PuTTY (or any SSH client) to log in to your EC2 instance using its public IP, the .ppk file, and the username (usually `ubuntu`).
  
**Note**: The Elastic IP allocated here will be used in the next section.

### 2. Set Up Your Domain in AWS Route 53
- Go to your AWS Route 53 console.
- Now, go to your AWS account and navigate to Route 53 and select the Hosted Zone for your domain (e.g., `abc.com`).
- Inside the Hosted Zone, create a new record with the following details:
  - **Record Name**: Leave this field empty.
  - **Record Type**: A
  - **Value**: Enter the public Elastic IP of your EC2 instance. Keep the remaining settings as default and click "Create Record."

### 3. Install Apache on EC2 Instance
```bash
sudo apt update
sudo apt install apache2
```

### 4. Configure Apache
- Navigate to the Apache configuration directory:
```bash
cd/etc/apache2/sites-available
```
- Edit the `000-default.conf` file:
```bash
sudo nano 000-default.conf
```
- Add the following:
```
ServerAdmin your_email
ServerName your_domain (e.g., abc.com)
ProxyRequests off
ProxyPreserveHost on
<Directory "/var/www/html">
      Options Indexes FollowSymLinks
      AllowOverride None
      Order allow,deny
      Allow from all
</Directory>
```

### 5. Enable SSL and Apply Certificates
- Install Certbot to obtain SSL certificates:
```bash
sudo apt install certbot python3-certbot-apache
```
- Test the Apache configuration:
```bash
sudo apache2ctl configtest
sudo a2enmod proxy
sudo systemctl restart apache2
```
- Reload the configuration:
```bash
sudo systemctl reload apache2
sudo ufw allow 'Apache Full'
```
- Verify the virtual host configuration:
```bash
apachectl -t -D DUMP_VHOSTS
```

### 6. Obtain SSL Certificates
- Acquire the SSL certificate for your domain:
```bash
sudo certbot --apache
```
- Enter your email and domain name when prompted.
- Restart Apache:
```bash
sudo systemctl restart apache2
```
- SSL certificates will be stored at:
```
/etc/letsencrypt/live/your-domain-name/
```

### 7. Deploy a React Front-end on EC2
#### Clone the Project
Clone the project from GitLab:
[Excel Add-In Repository](https://gitlab.com/scalarlabs/partners/percept-consulting-services/excel-add-in.git)

#### Navigate to the Project Directory
Move to the `excel-addin-in-react` branch.

#### Install Dependencies
Execute the following command:
```bash
npm install
```

#### Update the Base Path
Open the `package.json` file, locate the scripts section, and update the base path in the build script to reflect your domain:
```json
"build": "cross-env BASE_URL=https://<your-domain>/scalar-excel-addin/ webpack --mode production",
```

#### Build the Project
Run the build command:
```bash
npm run build
```
This will create a `dist` folder containing the build files.

#### Prepare Deployment Directory
Create a folder named `scalar-excel-addin` in `/var/www/html/`:
```bash
sudo mkdir /var/www/html/scalar-excel-addin
```

#### Move Files to Deployment Directory
Copy all files and folders from the `dist` folder to `/var/www/html/scalar-excel-addin/`:
```bash
sudo cp -r dist/* /var/www/html/scalar-excel-addin/
```

#### Test the Frontend
Open your web browser and navigate to:
```
https://your_domain/scalar-excel-addin/taskpane.html
```
If the page loads successfully, your frontend deployment is working fine.

Proceed to ScalarDB Cluster Deployment
Now, you can move on to deploying the ScalarDB cluster.

---

## Helm ScalarDB Cluster Configuration
For setting up the ScalarDB cluster on Kubernetes using the Helm chart, please refer to the following link: [Helm ScalarDB Cluster Configuration](https://scalardb.scalar-labs.com/docs/latest/scalardb-cluster/setup-scalardb-cluster-on-kubernetes-by-using-helm-chart/)

For example, the file will be named scalardb-cluster-custom-values.yaml, containing configurations for all databases, ScalarDB authentication, and ScalarDB Cluster SQL.

```yaml
envoy:
  enabled: true
  service:
    type: "ClusterIP"   # Changed from LoadBalancer to ClusterIP

scalardbCluster:
  image:
    repository: "ghcr.io/scalar-labs/scalardb-cluster-node-byol-premium"
  scalardbClusterNodeProperties: |
    scalar.db.cluster.node.licensing.license_key={"organization_name":"Scalar Inc / Kanzen Demo","expiration_date_time":null,"product_name":"ScalarDB Cluster","product_version":3,"license_type":"enterprise.premium","signature":"MEYCIQCdHYuqyuEGZOql5eIFsmIiE4p7z/gMlWItTutOEz/t8AIhAJt4nV/b6lkZHJh0z4aU9lial3fkSTJXXfkw0SXyZb5q"}
    scalar.db.cluster.node.licensing.license_check_cert_pem=-----BEGIN CERTIFICATE-----\nMIICKzCCAdKgAwIBAgIIBXxj3s8NU+owCgYIKoZIzj0EAwIwbDELMAkGA1UEBhMC\nSlAxDjAMBgNVBAgTBVRva3lvMREwDwYDVQQHEwhTaGluanVrdTEVMBMGA1UEChMM\nU2NhbGFyLCBJbmMuMSMwIQYDVQQDExplbnRlcnByaXNlLnNjYWxhci1sYWJzLmNv\nbTAeFw0yMzExMTYwNzExNTdaFw0yNDAyMTUxMzE2NTdaMGwxCzAJBgNVBAYTAkpQ\nMQ4wDAYDVQQIEwVUb2t5bzERMA8GA1UEBxMIU2hpbmp1a3UxFTATBgNVBAoTDFNj\nYWxhciwgSW5jLjEjMCEGA1UEAxMaZW50ZXJwcmlzZS5zY2FsYXItbGFicy5jb20w\nWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAATJx5gvAr+GZAHcBpUvDFDsUlFo4GNw\npRfsntzwStIP8ac3dew7HT4KbGBWei0BvIthleaqpv0AEP7JT6eYAkNvo14wXDAO\nBgNVHQ8BAf8EBAMCBaAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMAwG\nA1UdEwEB/wQCMAAwHQYDVR0OBBYEFMIe+XuuZcnDX1c3TmUPlu3kNv/wMAoGCCqG\nSM49BAMCA0cAMEQCIGGlqKpgv+KW+Z1ZkjfMHjSGeUZKBLwfMtErVyc9aTdIAiAy\nvsZyZP6Or9o40x3l3pw/BT7wvy93Jm0T4vtVQH6Zuw==\n-----END CERTIFICATE-----

    # ScalarDB Cluster configurations
    scalar.db.cluster.membership.type=KUBERNETES
    scalar.db.cluster.membership.kubernetes.endpoint.namespace_name=${env:SCALAR_DB_CLUSTER_MEMBERSHIP_KUBERNETES_ENDPOINT_NAMESPACE_NAME}
    scalar.db.cluster.membership.kubernetes.endpoint.name=${env:SCALAR_DB_CLUSTER_MEMBERSHIP_KUBERNETES_ENDPOINT_NAME}

    # ScalarDB - Auth
    scalar.db.cluster.auth.enabled=true

    # For ScalarDB Cluster SQL .
    scalar.db.sql.enabled=true
    scalar.db.cross_partition_scan.enabled=true
    scalar.db.cross_partition_scan.filtering.enabled=true

    # You have to use Single-Storage or Multi-storage 

    # Single Storage configurations
    scalar.db.storage=<STOARGE>
    scalar.db.contact_points=<CONTACT_POINTS>
    scalar.db.username=<USERNAME>
    scalar.db.password=<PASSWORD>

    # Multi Storage configurations( suppose I have mysql and cassandra, this is how configuration is Done.)
    # scalar.db.storage=multi-storage
    # scalar.db.multi_storage.storages=mysql,cassandra
    # scalar.db.multi_storage.storages.mysql.storage=jdbc
    ## for eg YOUR_JDBC_URI=jdbc:mysql://my-release-mysql.default.svc.cluster.local:3306/
    # scalar.db.multi_storage.storages.mysql.contact_points=<YOUR_JDBC_URI>        
    # scalar.db.multi_storage.storages.mysql.username=<USERNAME>
    # scalar.db.multi_storage.storages.mysql.password=<PASSWORD>
    # scalar.db.multi_storage.storages.cassandra.storage=cassandra
    # scalar.db.multi_storage.storages.cassandra.contact_points=<CASSANDRA_CONTACT_POINTS>
    # scalar.db.multi_storage.storages.cassandra.username=<USERNAME>
    # scalar.db.multi_storage.storages.cassandra.password=<PASSWORD>
    ## format Assign namespace:storage_name
    # scalar.db.multi_storage.namespace_mapping=coordinator:mysql
    ## Assign default_storage-> one of the storage
    # scalar.db.multi_storage.default_storage=mysql

```
---


## Step-by-Step Configuration in Kubernetes

### 1. Ingress Controller Setup (NGINX Ingress)
If you don't have an Ingress controller installed, you must install one. Here’s how you can install the NGINX Ingress Controller:
```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml
```
This will deploy the NGINX Ingress controller to your cluster, created in the `ingress-nginx` namespace.

### 2. Cert-Manager Setup for SSL
To automatically obtain SSL certificates (e.g., from Let's Encrypt), you’ll need Cert-Manager. Install it using the following command:
```bash
kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.13.0/cert-manager.yaml
```
Then, create a `ClusterIssuer` for Let's Encrypt (production environment):

#### `cluster-issuer.yaml`
```yaml
apiVersion: cert-manager.io/v1
kind: ClusterIssuer
metadata:
  name: letsencrypt-prod
spec:
  acme:
    server: https://acme-v02.api.letsencrypt.org/directory
    email: your-email@example.com
    privateKeySecretRef:
      name: letsencrypt-prod
    solvers:
    - http01:
        ingress:
          class: nginx
```

Apply the above configuration:
```bash
kubectl apply -f cluster-issuer.yaml
```

### 3. Ingress Configuration for SSL Termination
Create an Ingress resource that handles routing traffic from your domain (`envoy.exceladdinbyscalar.com`) and applies SSL certificates via Let's Encrypt.

#### `scalardb-envoy-ingress.yaml`
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: scalardb-envoy-ingress
  namespace: default
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    cert-manager.io/cluster-issuer: "letsencrypt-prod"
    nginx.ingress.kubernetes.io/ssl-redirect: "true"
spec:
  ingressClassName: nginx  # Specify the ingress class
  tls:
  - hosts:
    - envoy.exceladdinbyscalar.com
    secretName: scalardb-envoy-tls  # Secret for TLS
  rules:
  - host: envoy.exceladdinbyscalar.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: scalardb-cluster-envoy  # Ensure service is in default namespace
            port:
              number: 60053  # Port to forward traffic to
```

Retrieve the address of the `scalardb-envoy-ingress` deployment by running the following command:
```bash
kubectl get ingress
```
Copy the address for future use.

---

### 4. DNS Configuration in AWS Route 53
Now that the Kubernetes configurations are set up, you'll need to configure DNS:
- Go to your Route 53 Hosted Zone.
- Create a CNAME record that points your custom domain to the Load Balancer’s DNS name:
  - **Name**: `envoy.exceladdinbyscalar.com`
  - **Value**: (paste the address of `scalardb-envoy-ingress`)
  - **Type**: CNAME
- Save the record.

---




