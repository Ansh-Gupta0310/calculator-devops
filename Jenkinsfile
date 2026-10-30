pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'anshgupta0310'
        IMAGE_NAME = "${DOCKERHUB_USER}/calculator-devops"
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        FULL_IMAGE = "${IMAGE_NAME}:${IMAGE_TAG}"
    }

    options {
        // gives every log line a timestamp
        timestamps()

        timeout(time: 20, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '15'))
    }

    stages{
        stage('1. Checkout'){
            steps{
                checkout scm
                sh 'git log -1 --oneline'
            }
        }

        stage('2. Build'){
            steps{
                sh 'mvn -B -ntp clean package -DskipTests'
            }
        }

        stage('3. Test'){
            steps{
                sh 'mvn -B -ntp test'
            }
            post{
                always{
                    // Publishing JUnit XML even when tests fail
                    junit testResults: 'target/surefire-reports/*.xml',
                        allowEmptyResults: false
                }
            }
        }

        stage('4. Docker Build'){
            steps{
                sh "docker build -t ${FULL_IMAGE} -t ${IMAGE_NAME}:latest ."
                sh "docker images ${IMAGE_NAME}"
            }
        }

        stage('5. Push'){
            steps{
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DH_USER', passwordVariable: 'DH_PASS')]) {
                    sh '''
                        echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin
                        docker push "$IMAGE_NAME:$IMAGE_TAG"
                        docker push "$IMAGE_NAME:latest"
                        docker logout
                    '''
                }
            }
        }

        stage('6. Deploy'){
            steps{
                // Give compose the exact tag we just pushed
                sh '''
                    export DOCKER_IMAGE="$IMAGE_NAME:$IMAGE_TAG"
                    echo "Deploying $DOCKER_IMAGE ..."
                    docker compose pull
                    docker compose up -d --remove-orphans
                    sleep 15
                    docker compose ps

                    # health check through the proxy
                    curl -fsS http://localhost/actuator/health
                '''
            }
        }
    }

    post{
        success{
            echo "Build #${env.BUILD_NUMBER} succeeded. Image: ${FULL_IMAGE}"
            emailext (
                subject: "[Jenkins] SUCCESS: ${JOB_NAME} #${BUILD_NUMBER}",
                body: """
                    <h2 style="color:#2E8B57">Build succeeded</h2>
                    <ul>
                        <li><b>Job:</b> ${JOB_NAME}</li>
                        <li><b>Build:</b> #${BUILD_NUMBER}</li>
                        <li><b>Image:</b> ${FULL_IMAGE}</li>
                        <li><b>URL:</b> <a href="${BUILD_URL}">${BUILD_URL}</a></li>
                    </ul>
                """,
                to: 'ansh.gupta@iiitb.ac.in',
                mimeType: 'text/html'
            )
        }
        failure{
            echo "Build #${env.BUILD_NUMBER} FAILED."
            emailext (
                subject: "[Jenkins] FAILED: ${JOB_NAME} #${BUILD_NUMBER}",
                body: """
                    <h2 style="color:#B23A48">Build failed</h2>
                    <ul>
                        <li><b>Job:</b> ${JOB_NAME}</li>
                        <li><b>Build:</b> #${BUILD_NUMBER}</li>
                        <li><b>Console:</b> <a href="${BUILD_URL}console">${BUILD_URL}console</a></li>
                    </ul>
                """,
                to: 'ansh.gupta@iiitb.ac.in',
                mimeType: 'text/html'
            )
        }
        always{
            // remove stale images from the Jenkins agent so disk doesn't fill up
            sh 'docker image prune -f --filter "until=72h" || true'
        }
    }
}