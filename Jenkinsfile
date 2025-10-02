pipeline {
    agent {
        label 'win'
    }
    parameters {
        string(name: 'DOCKER_HUB_USER', defaultValue: 'hunghey', description: 'Docker Hub username')
        string(name: 'DOCKER_IMAGE_NAME', defaultValue: 'myimage', description: 'Docker image name')
    }
    environment {
        JAVA_HOME = tool name: 'JDK21', type: 'jdk'
        PATH = "${JAVA_HOME}\\bin;${env.PATH}"
        ALLURE_HOME = 'C:\\Program Files\\allure-2.34.1'
        DOCKER_HUB_USER = "${params.DOCKER_HUB_USER}"
        DOCKER_IMAGE_NAME = "${params.DOCKER_IMAGE_NAME}"
        DOCKER_PASSWORD = credentials('docker-hub-password')
    }
    tools {
        jdk 'JDK21'
        allure 'Allure'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Prepare Allure Directory') {
            steps {
                bat '''
                    if not exist allure-results mkdir allure-results
                    echo Allure results directory created at: %CD%\\allure-results
                '''
            }
        }
        stage('Run Tests in Docker') {
            steps {
                script {
                    bat """
                        docker run --rm -v "%CD%\\allure-results:/app/build/allure-results" ${DOCKER_HUB_USER}/${DOCKER_IMAGE_NAME}:latest
                   """
                }
            }
        }
    }
    post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'allure-report']]
            cleanWs()
        }
    }
}