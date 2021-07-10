#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        jdk "jdk8u292-b10"
    }
    
    stages {
        stage('Clean') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    echo 'Cleaning Project'
                    sh 'chmod +x gradlew'
                    sh './gradlew clean'
                }
            }
        }
        stage('Setup') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    echo 'Setting up Workspace'
                    sh './gradlew setupciworkspace genGitChangelog'
                }
            }
        }
        stage('Build and Deploy') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    echo 'Building and Deploying to Maven'
                    script {
                        if (env.BRANCH_NAME.contains("develop")) {
                            sh './gradlew build --refresh-dependencies -Pbranch=Snapshot uploadArchives'
                        } else if (env.BRANCH_NAME.contains("release")) {
                            sh './gradlew build --refresh-dependencies uploadArchives curseForge'
                        } else {
                            sh './gradlew build --refresh-dependencies -Pbranch=' + env.BRANCH_NAME + ' uploadArchives'
                        }
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
