#!/usr/bin/env groovy

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
        stage('Build and Deploy') {
            steps {
                echo 'Building and Deploying to Maven'
                script {
                    if (env.BRANCH_NAME.contains("develop")) {
                        sh './gradlew build --refresh-dependencies -Pbranch=Snapshot uploadArchives'
                    } else if (env.BRANCH_NAME.contains("release")) {
                        sh './gradlew build --refresh-dependencies uploadArchives'
                    } else {
                        sh './gradlew build --refresh-dependencies -Pbranch=' + env.BRANCH_NAME + ' uploadArchives'
                    }
                }
            }
        }
    }
    post {
        always {
            archive 'build/libs/**.jar'
        }
    }
}
