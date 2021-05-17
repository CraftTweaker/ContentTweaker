#!/usr/bin/env groovy

def docsOutDir = 'docsOut'
def docsRepositoryUrl = 'git@github.com:CraftTweaker/CraftTweaker-Documentation.git'
def shouldPushDocumentation = env.BRANCH_NAME.startsWith("develop") || env.BRANCH_NAME.startsWith("release")
def docsRepositoryBranch = shouldPushDocumentation ? env.BRANCH_NAME.substring(8) : null
def gitSshCredentialsId = 'crt_git_ssh_key'
def botUsername = 'crafttweakerbot'
def botEmail = 'crafttweakerbot@gmail.com'

def documentationDir = 'CrafttweakerDocumentation'
def exportDirInRepo = 'docs_exported/contenttweaker'

pipeline {
    agent any

    environment {
        ORG_GRADLE_PROJECT_secretFile = credentials('mod_build_secrets')
    }

    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning Project'
                sh 'chmod +x gradlew'
                sh './gradlew clean'
            }
        }
        stage('Build') {
            steps {
                echo 'Building'
                script {
                    if (env.BRANCH_NAME.startsWith("develop")) {
                        sh './gradlew -Pbranch=develop build'
                    } else if (env.BRANCH_NAME.startsWith("release")) {
                        sh './gradlew build'
                    } else {
                        sh "./gradlew -Pbranch=$env.BRANCH_NAME build"
                    }
                }
            }
        }
        stage('Git Changelog') {
            steps {
                sh './gradlew genGitChangelog'
            }
        }

        stage('Publish') {
            steps {
                //echo 'Updating version'
                //sh './gradlew updateVersionTracker'

                echo 'Deploying to CurseForge and Maven'
                script {
                    if (env.BRANCH_NAME.startsWith("develop")) {
                        sh './gradlew -Pbranch=develop :publish :curseForge'
                    } else if (env.BRANCH_NAME.startsWith("release")) {
                        sh './gradlew :publish :curseForge'
                    } else {
                        echo "Not Deploying to CurseForge because branch is ${env.BRANCH_NAME} is neither develop nor release"
                        sh "./gradlew -Pbranch=$env.BRANCH_NAME :publish"
                    }
                }
            }
        }

        stage('Exporting Documentation') {
            when {
                expression {
                    return shouldPushDocumentation
                }
            }

            steps {
                echo "Cloning Repository at Branch $docsRepositoryBranch"
                dir(documentationDir) {
                    git credentialsId: gitSshCredentialsId, url: docsRepositoryUrl, branch: docsRepositoryBranch, changelog: false
                }


                echo "Clearing existing Documentation export"
                dir(documentationDir) {
                    sh "rm --recursive --force ./$exportDirInRepo"
                }


                echo "Moving Generated Documentation to Local Clone"
                sh "mkdir --parents ./$documentationDir/$exportDirInRepo"
                sh "mv ./$docsOutDir/* ./$documentationDir/$exportDirInRepo/"


                echo "Committing and Pushing to the repository"
                dir(documentationDir) {
                    sshagent([gitSshCredentialsId]) {
                        sh "git config user.name $botUsername"
                        sh "git config user.email $botEmail"
                        sh 'git add -A'
                        //Either nothing to commit, or we create a commit
                        sh "git diff-index --quiet HEAD || git commit -m 'CI Doc export for build ${env.BRANCH_NAME}-${env.BUILD_NUMBER}\n\nMatches git commit ${env.GIT_COMMIT}'"
                        sh "git push origin $docsRepositoryBranch"
                    }
                }
            }
        }
    }
    post {
        always {
            archiveArtifacts 'build/libs/**.jar'
            archiveArtifacts 'changelog.md'
        }
    }
}