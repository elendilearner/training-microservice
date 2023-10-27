# Training Microservice

This is an educational project designed to test and explore microservice architecture functionalities.

## Installation

Follow these steps to get your development environment set up and the application running:

### 1. Clone the Repository

First, clone the repository to your local machine using SSH:

```shell
git clone git@github.com:elendilearner/training-microservice.git
```

### 2. Switch to the 'Develop' Branch

```shell
git checkout develop
```

### 3. Install Helm

If you don't have Helm installed, you can run the following script to install it:

```shell
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh
```

### 4. Navigate to the Project Directory

Change your current directory to the project's chart directory:

```shell
cd helm/training-microservice-chart
```

### 5. Set Up Kubernetes Configuration

Ensure your Kubernetes configuration is properly set up:

```shell
export KUBECONFIG=/var/snap/microk8s/current/credentials/client.config 
```

### 6. Install or Upgrade Your Release

If you are installing for the first time, use the following command:

```shell
helm install release-1 .
```

If you are updating an existing release, use:

```shell
helm upgrade release-1 .
```

Wait for the installation to complete.

### 7. Running the Tests

After setting up, navigate to the test directory:

```shell
cd ../../postmantest 
```

Run the tests using Newman:

```shell
newman run user_service.postman_collection.json
```

This process will execute the collection of Postman tests defined for the user service.




