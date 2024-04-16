def call() {
    pipeline {
        agent {
            label 'workstation'
        }
        stages {
            stage('compile') {
                steps {
                    script{
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
                steps {
                    script{
                        `SONAR_PASS = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value`

                        `SONAR_USER = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value`

                       sh "sonar-scanner -Dsonar.host.url=http://172.31.18.167:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=${component}"
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