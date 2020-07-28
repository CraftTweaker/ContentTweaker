#!/usr/bin/env groovy

pipeline {
    agent any
    stages {

        stage('Clean') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    echo 'Cleaning Project'
                    dir('CraftTweaker') {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean'
                    }
                    sh 'chmod +x gradlew'
                    sh './gradlew clean'
                }
            }
        }
        stage('Build') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    dir('CraftTweaker') {
                        echo 'Building CraftTweaker'
                        sh './gradlew build'
                    }
                    echo 'Building'
                    script {
                        if(env.BRANCH_NAME.startsWith("develop")) {
                            sh './gradlew -Pbranch=develop build'
                        } else if(env.BRANCH_NAME.startsWith("release")) {
                            sh './gradlew build'
                        } else {
                            sh './gradlew -Pbranch=' + env.BRANCH_NAME + ' build'
                        }
                    }
                }
            }
        }
        stage('Git Changelog') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    sh './gradlew genGitChangelog'
                }
            }
        }

        stage('Publish') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    //echo 'Updating version'
                    //sh './gradlew updateVersionTracker'

                    echo 'Deploying to CurseForge and Maven'
                    script {
                        if(env.BRANCH_NAME.startsWith("develop")) {
                            sh './gradlew -Pbranch=develop :publish :curseForge'
                        } else if(env.BRANCH_NAME.startsWith("release")) {
                            sh './gradlew :publish :curseForge'
                        } else {
                            echo "Not Deploying to CurseForge because branch is ${env.BRANCH_NAME} is neither develop nor release"
                            sh './gradlew -Pbranch=' + env.BRANCH_NAME + ' :publish'
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            archive 'build/libs/**.jar'
            archive 'changelog_cot.md'
        }
    }
}