pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "116981767400.dkr.ecr.us-east-1.amazonaws.com/flight-central:${env.BUILD_NUMBER}"
        KUBECONFIG = credentials('kubeconfig')
        AWS_CREDENTIALS = credentials('aws-credentials')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://116981767400.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:aws-credentials') {
                        def customImage = docker.build("${DOCKER_IMAGE}")
                        customImage.push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig']) {
                    sh """
                    # Update the deployment with the new image
                    kubectl set image deployment/flight-central-app flight-central=${DOCKER_IMAGE} --record

                    # Check rollout status
                    kubectl rollout status deployment/flight-central-app

                    # Verify the deployment
                    kubectl get pods -l app=flight-central
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment to Kubernetes completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check the logs for details.'
        }
    }
}
