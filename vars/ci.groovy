def call() {
    pipeline {
        agent {
            label 'workstation'
        }
        stages {
            stage('compile') {
                steps {
                    echo 'compile'
                }
            }
        }

    }

}