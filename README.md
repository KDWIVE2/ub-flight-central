# Flight Central Application

This is a Spring Boot application with PostgreSQL database, containerized with Docker and deployed on AWS EKS with Jenkins CI/CD pipeline.

## Prerequisites

1. AWS Account with appropriate permissions
2. AWS CLI configured with your credentials
3. kubectl installed
4. eksctl installed (for EKS cluster creation)
5. Docker installed
6. Jenkins server with necessary plugins (Docker, Kubernetes, AWS, Pipeline)

## AWS EKS Cluster Setup

1. Create an EKS cluster:
   ```bash
   eksctl create cluster \
   --name flight-central-cluster \
   --region <your-aws-region> \
   --nodegroup-name standard-workers \
   --node-type t3.medium \
   --nodes 2 \
   --nodes-min 1 \
   --nodes-max 3
   ```

2. Update kubeconfig:
   ```bash
   aws eks --region <your-aws-region> update-kubeconfig --name flight-central-cluster
   ```

3. Create an ECR repository:
   ```bash
   aws ecr create-repository --repository-name flight-central --region <your-aws-region>
   ```

## Deploy PostgreSQL

1. Apply the PostgreSQL configuration:
   ```bash
   kubectl apply -f k8s/postgres-configmap.yaml
   kubectl apply -f k8s/postgres-secret.yaml
   kubectl apply -f k8s/postgres-deployment.yaml
   ```

## Jenkins Setup

1. Install required plugins:
   - Kubernetes
   - Docker Pipeline
   - AWS ECR
   - Pipeline: AWS Steps
   - Kubernetes CLI

2. Add credentials in Jenkins:
   - Add AWS credentials (AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY) with ID 'aws-credentials'
   - Add kubeconfig file as a secret file with ID 'kubeconfig'

3. Create a new Pipeline job in Jenkins:
   - Select 'Pipeline script from SCM'
   - Choose your Git repository
   - Set the script path to 'Jenkinsfile'

## Application Deployment

1. Update the following in the Jenkinsfile:
   - Replace `<YOUR_AWS_ACCOUNT_ID>` with your AWS account ID
   - Replace `<YOUR_REGION>` with your AWS region (e.g., us-east-1)

2. Update the application deployment YAML with your ECR repository URI:
   - In `k8s/app-deployment.yaml`, replace `<YOUR_AWS_ACCOUNT_ID>` and `<YOUR_REGION>`

3. Push your code to trigger the Jenkins pipeline

## Accessing the Application

After successful deployment, get the LoadBalancer URL:

```bash
kubectl get svc flight-central-service
```

Access the application at the EXTERNAL-IP shown in the output.

## Monitoring

Check the status of your deployments:

```bash
kubectl get deployments
kubectl get pods
kubectl get services
```

## Cleanup

To delete all resources:

```bash
kubectl delete -f k8s/
eksctl delete cluster --name flight-central-cluster --region <your-aws-region>
```
