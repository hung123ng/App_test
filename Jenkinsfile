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
        // stage('Build Project') {
        //     steps {
        //         bat 'gradlew.bat clean build -x test'  // Build trên Windows
        //     }
        // }
        // stage('Build Docker Image') {
        //     steps {
        //         script {
        //             bat """
        //                 docker build -t ${DOCKER_HUB_USER}/${DOCKER_IMAGE_NAME}:latest .
        //             """
        //         }
        //     }
        // // }
        // stage('Push Docker Image') {
        //     steps {
        //         script {
        //             bat """
        //                 docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_PASSWORD}
        //                 docker push ${DOCKER_HUB_USER}/${DOCKER_IMAGE_NAME}:latest
        //             """
        //         }
        //     }
        // }
        stage('Pull Docker Image') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'docker-hub-password', variable: 'DOCKER_TOKEN')]) {
                        bat """
                            echo Logging in with user: ${DOCKER_HUB_USER}
                            docker login -u ${DOCKER_HUB_USER} -p %DOCKER_TOKEN%
                            if errorlevel 1 (
                                echo Docker login failed! Check username and token.
                                exit /b 1
                            )
                            docker pull ${DOCKER_HUB_USER}/${DOCKER_IMAGE_NAME}:latest
                        """
                    }
                }
            }
        }
        stage('Run Tests in Docker') {
            steps {
                script {
                    bat """
                        docker run --rm -v "%WORKSPACE%\\allure-results:/app/allure-results" ${DOCKER_HUB_USER}/${DOCKER_IMAGE_NAME}:latest gradle test --info
                        if not exist "%WORKSPACE%\\allure-results" (
                            echo Allure results directory not found in %WORKSPACE%! Check testNG.xml, Gradle config, or Docker volume.
                            exit /b 1
                        ) else (
                            dir "%WORKSPACE%\\allure-results"
                        )
                    """
                }
            }
        }
        stage('Generate Allure Report') {
            steps {
                bat """
                    if exist "%WORKSPACE%\\allure-results" (
                        "${ALLURE_HOME}\\bin\\allure.bat" generate "%WORKSPACE%\\allure-results" --clean -o "%WORKSPACE%\\allure-report"
                    ) else (
                        echo Allure results not found in %WORKSPACE%, skipping report generation.
                    )
                """
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
