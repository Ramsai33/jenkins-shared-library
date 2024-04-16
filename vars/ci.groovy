def call() {
    pipeline {
        agent {
            label 'workstation'
        }
        stages {
            stage('compile') {
                steps {
                    script {
                        common.compile()
                    }

                }
            }
            stage('unit test') {
                steps {
                    echo 'unit test'
                }
            }
            stage('quality check') {
                environment {
                    SONAR_USER = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                    //SONAR_PASS = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                }
                steps {
                    script {
                        SONAR_PASS = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                        wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]) {
                            sh "sonar-scanner -Dsonar.host.url=http://172.31.18.167:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${component}"
                        }
                    }
                }
            }

            stage('storing') {
                steps {
                    echo 'storing'
                }
            }
        }

    }


}
