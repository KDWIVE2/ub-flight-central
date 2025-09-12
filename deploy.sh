#!/bin/bash
set -e

# Configuration
AWS_REGION="us-east-1"
ECR_REPOSITORY="flight-central"
IMAGE_TAG="latest"

# Get AWS account ID
AWS_ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
ECR_URI="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"

# Build the application
mvn clean package

# Build Docker image
docker build -t ${ECR_REPOSITORY}:${IMAGE_TAG} .

# Login to ECR
aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_URI}

# Create ECR repository if it doesn't exist
aws ecr describe-repositories --repository-names ${ECR_REPOSITORY} > /dev/null 2>&1 || \
    aws ecr create-repository --repository-name ${ECR_REPOSITORY}

# Tag and push the image
docker tag ${ECR_REPOSITORY}:${IMAGE_TAG} ${ECR_URI}/${ECR_REPOSITORY}:${IMAGE_TAG}
docker push ${ECR_URI}/${ECR_REPOSITORY}:${IMAGE_TAG}

echo "Image pushed to: ${ECR_URI}/${ECR_REPOSITORY}:${IMAGE_TAG}"

echo "To deploy to EC2, run this command on your EC2 instance:"
echo "------------------------------------------------"
echo "docker run -d \\"
echo "  -p 8090:8090 \\"
echo "  -e SPRING_PROFILES_ACTIVE=prod \\"
echo "  -e REDIS_HOST=172.31.23.73
        -e REDIS_PORT=6379 \\"
echo "  ${ECR_URI}/${ECR_REPOSITORY}:${IMAGE_TAG}"
echo "------------------------------------------------"
