pipeline {
    agent any
    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning Project'
                sh 'chmod +x gradlew'
                sh './gradlew clean'
            }
        }
        stage('Setup') {
            steps {
                echo 'Setting up Workspace'
                sh './gradlew setupdecompworkspace'
            }
        }
        stage('Build') {
            steps {
                echo 'Building'
                sh './gradlew build --refresh-dependencies'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying to Maven'
                sh './gradlew uploadArchives'
            }
        }
    }
}
